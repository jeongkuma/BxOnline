package kr.co.auiot.common.log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import kr.co.abacus.common.util.DateUtils;

@Component
public class HangCheckFileWriter {

	protected String id;
	protected PrintWriter writer;
	protected String path;
	protected String fileName;
	protected String serviceName;
	protected long delay;
	protected Integer sequence;
	protected Timer timer;

	protected long lastCreateTime;

	protected int rolling;
	protected int writerTime;
	protected Timer hangCheckTimer;

	private final String YML_DEFAULT_PATH = "log.hangCheck.";

	@Autowired
	private Environment env = null;

	public String getSeq() {
		return newSequenceID();
	}

	private void write(String oms) throws Exception {
		writeHangCheck(oms);
	}

	@PreDestroy
	public void close() {
		timer.cancel();
		timer.purge();
		hangCheckTimer.cancel();
		hangCheckTimer.purge();
	}

	@PostConstruct
	private void initConstruct() {
		String serviceName = env.getProperty("log.instanceName");
		Integer writerTime = env.getProperty(YML_DEFAULT_PATH + "writerTime", Integer.class);
		Integer rolling = env.getProperty(YML_DEFAULT_PATH + "rolling", Integer.class);
		String rootPath = env.getProperty(YML_DEFAULT_PATH + "filePath");
		this.id = serviceName;
		this.path = (rootPath.endsWith(File.separator)) ? rootPath : rootPath + File.separator;
		this.fileName = serviceName + ".";
		this.delay = 1000;
		this.sequence = new Integer(0);
		this.writerTime = writerTime;

		this.rolling = rolling;
		if (0 == rolling)
			rolling = 5;

		try {
			this.init();
		} catch (Exception e) {
		}
	}

	protected void init() throws Exception {
		String hangCheckPath = this.path + DateUtils.timeStamp(8) + File.separator;
		String hangCheck = DateUtils.timeStamp(12);
		int ognTime = Integer.parseInt(hangCheck.substring(10));

		// 최초 파일이름을 5분단위로 보정
		String hangCheckFile = fileName + hangCheck.substring(0, 10)
				+ String.format("%02d", (ognTime - ognTime % rolling)) + "00.log";
		try {
			new File(hangCheckPath).mkdirs();
			writer = new PrintWriter(new FileWriter(new File(hangCheckPath + hangCheckFile), true), true);
		} catch (Exception e) {
			if (null != writer) {
				try {
					writer.close();
				} catch (Exception ee) {
				}
			}
			throw e;
		}

		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				long thisTime = Integer.parseInt(DateUtils.timeStamp(12).substring(8));

				thisTime = getMin(thisTime);

				// 5분단위 롤링
				long term = thisTime % (rolling);

				if (0 == term && thisTime != lastCreateTime) {
					try {
						String hangCheckPath = path + DateUtils.timeStamp(8) + File.separator;
						String hangCheckFile = fileName + DateUtils.timeStamp(12) + "00.log";
						createLogFile(hangCheckPath, hangCheckFile);
						lastCreateTime = thisTime;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

		}, delay, delay);

		int wait = (60 - Integer.valueOf(DateUtils.timeStamp(14).substring(12, 14))) * 1000;

		hangCheckTimer = new Timer(true);
		hangCheckTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					StringBuilder hangLog = new StringBuilder();
					hangLog.append(DateUtils.timeStamp(12));
					hangLog.append("00");
					hangLog.append("|");
					hangLog.append(id.toUpperCase());
					hangLog.append("|OMS||");
//					String hangLog = DateUtils.timeStamp(12) + "00" + "|" + id.toUpperCase() + "|OMS||";
					write(hangLog.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, wait, writerTime * 60 * 1000);
	}

	protected void createLogFile(String path, String fileName) throws Exception {

		try {
			synchronized (writer) {
				new File(path).mkdirs();
				if (null != writer)
					writer.close();
				writer = new PrintWriter(new FileWriter(new File(path + fileName), true), true);
			}
		} catch (Exception e) {
			if (null != writer) {
				try {
					writer.close();
				} catch (Exception ee) {
					e.printStackTrace();
				}
			}
			throw e;
		}
	}

	public void writeHangCheck(String oms) throws Exception {
		writer.println(oms);
	}

	public String newSequenceID() {

		String seq = null;
		synchronized (sequence) {
			if (++sequence > 9999)
				sequence = 1;
			seq = String.format("%04d", sequence);
		}
		return DateUtils.timeStamp() + seq;
	}

	public static long getMin(long time) {

		long result = 0;
		if (time / 100 > 0) {
			result = (time / 100) * 60;
		}
		if (time % 100 > 0) {
			result += (time % 100);
		}
		return result;
	}

}
