package kr.co.auiot.common.log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.CallHistoryInfoDto;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.common.ComSqlParameterDto;
import kr.co.abacus.common.util.DateUtils;
import kr.co.abacus.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CallLogFileWriter {
	
	
	//public static final Marker OMS_CALL_LOG = MarkerManager.getMarker("OMSCALLLOG");

	protected String id;
	protected PrintWriter writer;
	protected String path;
	protected String fileName;
	protected long delay;
	protected Integer sequence;
	protected Timer timer;
	protected long lastCreateTime;
	protected int rolling;

	private final String YML_DEFAULT_PATH = "log.call.";

	@Autowired
	private Environment env = null;

	@Autowired
	private ObjectMapper objectMapper = new ObjectMapper();

	@PostConstruct
	private void initConstruct() {
		String calllogInstanceName = env.getProperty("log.instanceName");
		String calllogInstanceCode = env.getProperty("log.instanceCode");
		Integer calllogRolling = env.getProperty(YML_DEFAULT_PATH + "rolling", Integer.class);
		String calllogPath = env.getProperty(YML_DEFAULT_PATH + "filePath");

		this.id = calllogInstanceName;
		this.path = (calllogPath.endsWith(File.separator)) ? calllogPath : calllogPath + File.separator;
		this.fileName = calllogInstanceName + "." + calllogInstanceCode + ".";
		//파일 갱신주기
		//this.delay = 1000*60;
		this.delay = 1000;

		this.sequence = new Integer(0);
		this.rolling = calllogRolling;
		if (0 == rolling)
			rolling = 5;

		try {
			this.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getSeq() {
		return newSequenceID();
	}

	@Async
	public void asyncWrite(ComInfoDto comInfoDto, String comResponseDto) throws InterruptedException {
		String result = makeCallLog(comInfoDto, comResponseDto);
		log.debug(result);
		writer.println(result);
	}

	public void write(ComInfoDto comInfoDto, String comResponseDto) throws InterruptedException {
		String result = makeCallLog(comInfoDto, comResponseDto);
		log.debug(result);
		writer.println(result);
	}

	public String makeCallLog(ComInfoDto commonInfoDto, String comResponseDto) {
		StringBuffer sb = new StringBuffer();

		String logKey = commonInfoDto.getLogKey();
		sb.append(MessageFormat.format("[{0}]==	START CALL LOG	==================================================\n", logKey));

		String uri = commonInfoDto.getRequestUri();
		String reqDatetime = commonInfoDto.getReqDatetime();
		sb.append(MessageFormat.format("[{0}][IOT REQUEST] [{1}] URI : {2}\n", logKey, reqDatetime, uri));

		Map<String, String> headers = commonInfoDto.getHeader();
		sb.append(MessageFormat.format("[{0}]	[QUERY STRING] [{1}]\n", logKey, commonInfoDto.getQueryString()));

		sb.append(MessageFormat.format("[{0}]	[HTTP HEADER] [{1}]\n", logKey, headers));

		String requestBodyString = commonInfoDto.getBodyString();
		sb.append(MessageFormat.format("[{0}]	[HTTP BODY] [{1}]\n", logKey, requestBodyString));

		makeSasHistoryLog(logKey, commonInfoDto, sb);
		String resDatetime = commonInfoDto.getResDatetime();

		if (commonInfoDto.getIsException()) {
			sb.append(MessageFormat.format("[{0}]     [IOT ERROR MESSAGE] [{1}] {2}\n", logKey, resDatetime, commonInfoDto.getErrorMsg()));
			sb.append(MessageFormat.format("[{0}][IOT RESPONSE EXCEPTION] [{1}] {2}\n", logKey, resDatetime, comResponseDto));
		} else {
			sb.append(MessageFormat.format("[{0}][IOT RESPONSE] [{1}] {2}\n", logKey, resDatetime, comResponseDto));
		}

		if (!StringUtils.isEmpty(commonInfoDto.getMsg())) {
			sb.append(MessageFormat.format("[{0}][IOT MSG] [{1}] {2}\n", logKey, resDatetime, commonInfoDto.getMsg()));
		}
		
		sb.append(MessageFormat.format("[{0}]==	END CALL LOG	==================================================", logKey));
		return sb.toString();
	}

	private void makeSasHistoryLog(String logKey, ComInfoDto commonInfoDto, StringBuffer sb) {
		List<CallHistoryInfoDto> callHistoryInfoDtoList = commonInfoDto.getCallHistoryInfoList();
		if (callHistoryInfoDtoList.size() > 0) {
			for (int i = 0; i < callHistoryInfoDtoList.size(); i++) {
				CallHistoryInfoDto callHistoryInfoDto = callHistoryInfoDtoList.get(i);

				if (ComConstant.AGS_CALL_SAS_HISTORY_TYPE.equals(callHistoryInfoDto.getCallHistoryType())) {
					makeAgsCalllog(logKey, sb, callHistoryInfoDto);
				} else if (ComConstant.DB_ACCESS_HISTORY_TYPE.equals(callHistoryInfoDto.getCallHistoryType())) {
					makeDbAccesslog(logKey, sb, callHistoryInfoDto);
				}
			}
		}
	}

	private void makeDbAccesslog(String logKey, StringBuffer sb, CallHistoryInfoDto callHistoryInfoDto) {
		String dbCallReqDatetime = callHistoryInfoDto.getReqDatetime();
		String sqlId = callHistoryInfoDto.getSqlId();
		sb.append(MessageFormat.format("[{0}]	[QUERY] [{1}] [{2}]\n", logKey, dbCallReqDatetime, sqlId));

		String sql = callHistoryInfoDto.getSql();
		sb.append(MessageFormat.format("[{0}]		[SQL] [{1}]\n", logKey, sql));

		List<ComSqlParameterDto> comSqlParameterDtoList = callHistoryInfoDto.getComSqlParameterDtoList();
		StringBuffer param = new StringBuffer();

		for (int i = 0; i < comSqlParameterDtoList.size(); i++) {
			ComSqlParameterDto comSqlParameterDto = comSqlParameterDtoList.get(i);
			param.append(MessageFormat.format("[{0} : {1}]", comSqlParameterDto.getPropertyName(), comSqlParameterDto.getValue()));
		}

		sb.append(MessageFormat.format("[{0}]		[PARAM] {1}\n", logKey, param));
	}

	private void makeAgsCalllog(String logKey, StringBuffer sb, CallHistoryInfoDto callHistoryInfoDto) {
		String agsReqDatetime = callHistoryInfoDto.getReqDatetime();
		sb.append(MessageFormat.format("[{0}]	[CALL METHOD] [{1}]\n", logKey, agsReqDatetime));

		String agsUrl = callHistoryInfoDto.getUrl();
		sb.append(MessageFormat.format("[{0}]		[API REQUEST] [{1}] URL : {2}\n", logKey, agsReqDatetime, agsUrl));

		Map<String, String> agsHeaders = callHistoryInfoDto.getRequestHeaders();
		sb.append(MessageFormat.format("[{0}]			[HEADER] {1}\n", logKey, agsHeaders));

		String agsRequestBodyString = "";
		try {
			Object objTemp = callHistoryInfoDto.getRequestBody();
			if (objTemp != null) {
				agsRequestBodyString = objectMapper.writeValueAsString(callHistoryInfoDto.getRequestBody());
			}
		} catch (Exception e) {
			log.error("[OBJECT MAPPER CONVERT ERROR : {}]", e);
		}

		sb.append(MessageFormat.format("[{0}]			[BODY] {1}\n", logKey, agsRequestBodyString));

		if (!callHistoryInfoDto.getIsException()) {
			String agsResDatetime = StringUtils.ifNullToEmpty(callHistoryInfoDto.getResDatetime());
			sb.append(MessageFormat.format("[{0}]		[API RESPONSE] [{1}]\n", logKey, agsResDatetime));

			String objTemp = callHistoryInfoDto.getResponseBody();
			sb.append(MessageFormat.format("[{0}]			[BODY] {1}\n", logKey, objTemp));
		}
	}

	@PreDestroy
	public void close() {
		timer.cancel();
		timer.purge();
	}

	protected void init() throws Exception {
		String callPath = this.path + DateUtils.timeStamp(8) + File.separator;
		String callSerial = DateUtils.timeStamp(12);
		int cognTime = Integer.parseInt(callSerial.substring(10));

		//최초 파일이름을 5분단위로 보정
		String callFile = fileName + callSerial.substring(0, 10) + String.format("%02d", (cognTime - cognTime % rolling)) + ".log";
		try {
			new File(callPath).mkdirs();
			writer = new PrintWriter(new FileWriter(new File(callPath + callFile), true), true);
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

		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				long thisTime = Integer.parseInt(DateUtils.timeStamp(12).substring(8));
				thisTime = getMin(thisTime);

				//5분단위 롤링
				long term = thisTime % (rolling);

				if (0 == term && thisTime != lastCreateTime) {
					try {
						String callPath = path + DateUtils.timeStamp(8) + File.separator;
						String callFile = fileName + DateUtils.timeStamp(12) + ".log";
						createLogFile(callPath, callFile);
						lastCreateTime = thisTime;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}, delay, delay);
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
