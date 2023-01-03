package kr.co.auiot.common.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.CallHistoryInfoDto;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.common.ResultDescDto;
import kr.co.abacus.common.dto.req.ComRequestDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.util.DateUtils;
import kr.co.abacus.common.util.JsonUtils;
import kr.co.abacus.common.util.LinkedHashMap;
import kr.co.abacus.common.util.StringUtils;
import kr.co.auiot.common.spring.OmsConfig;
import kr.co.auiot.common.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OmsFileWriter {

	//private final Logger logger = LogManager.getLogger(this.getClass());
	//public static final Marker OMS_CALL_LOG = MarkerManager.getMarker("OMSCALLLOG");

	private boolean omsIsAsync = false;

	protected String id;
	protected PrintWriter writer;
	protected String path;
	protected String fileName;
	protected long delay;
	protected Integer sequence;
	protected Timer timer;
	protected long lastCreateTime;
	protected int rolling;
	private final String YML_DEFAULT_PATH = "log.oms.";
	private boolean isContainResponse = false;

	@Autowired
	private Environment env;

	@Autowired
	private OmsConfig omsConfig = null;

	@Autowired
	private ObjectMapper objectMapper = null;

	@PostConstruct
	private void initConstruct() {
		this.omsIsAsync = env.getProperty("log.oms.isAsync", Boolean.class, false);

		if (StringUtils.objectIfNullToEmpty(YML_DEFAULT_PATH).length() < 1) {
			log.info("==== OmsFileWriter :: Error ==> Not OmsFileWriter");
		} else {
			try {
				this.isContainResponse = env.getProperty(YML_DEFAULT_PATH + "isContainResponse", Boolean.class);
				String omsInstanceName = env.getProperty("log.instanceName");
				String omsInstanceCode = env.getProperty("log.instanceCode");
				Integer omsRolling = env.getProperty(YML_DEFAULT_PATH + "rolling", Integer.class);
				String omsPath = env.getProperty(YML_DEFAULT_PATH + "filePath");

				this.id = omsInstanceName;
				this.path = (omsPath.endsWith(File.separator)) ? omsPath : omsPath + File.separator;
				this.fileName = omsInstanceName + "." + omsInstanceCode + ".";
				// 파일 갱신주기
				// this.delay = 1000*60;
				this.delay = 1000;

				this.sequence = new Integer(0);
				this.rolling = omsRolling;
				if (0 == rolling)
					rolling = 5;

				try {
					this.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				log.debug(YML_DEFAULT_PATH + " does not exist");
			}
		}
	}

	public String getSeq(String logType) {
		return newSequenceID();
	}

	@PreDestroy
	public void close() {
		timer.cancel();
		timer.purge();
	}

	protected void init() throws Exception {
		String omsPath = this.path + DateUtils.timeStamp(8) + File.separator;
		String oms = DateUtils.timeStamp(12);
		int ognTime = Integer.parseInt(oms.substring(10));

		// 최초 파일이름을 5분단위로 보정
		String omsFile = fileName + oms.substring(0, 10) + String.format("%02d", (ognTime - ognTime % rolling))
				+ ".log";
		try {
			new File(omsPath).mkdirs();
			writer = new PrintWriter(new FileWriter(new File(omsPath + omsFile), true), true);
		} catch (Exception e) {
			if (null != writer) {
				try {
					writer.close();
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
			throw e;
		}

		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				long thisTime = Integer.parseInt(DateUtils.timeStamp(12).substring(8)); // 시간분 -> 분으로 연산 해야됨!!

				// 5분단위 롤링
				long term = thisTime % (rolling);

				if (0 == term && thisTime != lastCreateTime) {
					try {
						String omsPath = path + DateUtils.timeStamp(8) + File.separator;
						String omsFile = fileName + DateUtils.timeStamp(12) + ".log";
						createLogFile(omsPath, omsFile);
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
				if (writer != null)
					writer.close();
				writer = new PrintWriter(new FileWriter(new File(path + fileName), true), true);
			}
		} catch (Exception e) {
			if (null != writer) {
				try {
					writer.close();
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}
			throw e;
		}
	}

	@Async
	public void asyncWrite(LinkedHashMap<String, String> omsLog, ComInfoDto comInfoDto) {
		makeOmsLog(omsLog, comInfoDto);
//		String writerLogString = omsLog.toString();
//		writer.println(writerLogString);
//		log.info(OMS_CALL_LOG, writerLogString);
	}

	public String write(LinkedHashMap<String, String> omsLog, ComInfoDto comInfoDto) {
		return makeOmsLog(omsLog, comInfoDto);
//		String writerLogString = omsLog.toString();
//		if (isContainResponse) {
//			ComResponseDto<?> comResponseDto = ReqContextComponent.getComResponseDto();
//			comResponseDto.setOms(writerLogString);
//			System.out.println(comResponseDto);
//		}
//		writer.println(writerLogString);
//		log.info(OMS_CALL_LOG, writerLogString);
	}

	private String makeOmsLog(LinkedHashMap<String, String> omsLog, ComInfoDto comInfoDto) {

		StringBuilder strContainResOms = new StringBuilder();

		String requestUri = comInfoDto.getRequestUri();

		String serverContextPath = env.getProperty("server.servlet.context-path");
		String omsConfigUri = "";
		try {
			omsConfigUri = requestUri.replaceFirst(serverContextPath, "");
		} catch (Exception e) {
			return "";
		}
		if (omsConfigUri.startsWith("/")) {
			omsConfigUri = omsConfigUri.substring(1);
		}
		String vname = comInfoDto.getVname();
		if (omsConfigUri.indexOf(".") > -1) {
			omsConfigUri = omsConfigUri.substring(0, omsConfigUri.lastIndexOf("."));
		}
		omsConfigUri = omsConfigUri.replaceAll("/", ".");
		Map<String, Object> omsUrlConfig = omsConfig.getOmsConfigData(omsConfigUri, vname);
		ComRequestDto comRequestDto = null;
		try {
			comRequestDto = objectMapper.readValue(comInfoDto.getBodyString(), ComRequestDto.class);
		} catch (IOException e) {
		}
		addOmsLog(omsLog, comInfoDto, omsUrlConfig, comRequestDto);

		ResultDescDto resultDescDto = comInfoDto.getResultDescDto();
		String resultCode = "20000000";
		if (resultDescDto != null) {
			resultCode = resultDescDto.getCode();
		}
		omsLog.put("RESULT_CODE", resultCode);
		String writerLogString = omsLog.toString();
		writer.println(writerLogString);

		strContainResOms.append(writerLogString).append("\r\n");

		log.debug(writerLogString);
		// common db & omsutil
		List<ComInfoDto> comInfoDtos = comInfoDto.getComInfos();

//		for (int i = comInfoDtos.size() - 1; i >= 0; i--) {
		for (int i = 0; i < comInfoDtos.size(); i++) {
			String timeString = DateUtils.timeStamp();
			ComInfoDto temp = comInfoDtos.get(i);
//			if (temp.getCallHistoryType().equals(ComConstant.DB_OMS_LOG)) {
//				omsLog.put("REQ_TIME", temp.getReqDatetime());
//				omsLog.put("RSP_TIME", temp.getResDatetime());
//				if (StringUtils.isNotEmpty(temp.getErrorMsg())) {
//					omsLog.put("RESULT_CODE", String.valueOf(temp.getErrorMsg()));
//				} else {
//					omsLog.put("RESULT_CODE", "200");
//				}
//				try {
//					String funcId = temp.getRequestUri();
//					omsLog.put("FUNC_ID", funcId.split(".dao.")[1]);
//				} catch (Exception e) {
//					omsLog.put("FUNC_ID", temp.getRequestUri());
//				}
//				omsLog.put("CHANNEL_TYPE", "OUT");
//				omsLog.put("CHANNEL", "DB");
//				omsLog.put("LOG_TIME", timeString);
//				writerLogString = omsLog.toString();
//				writer.println(writerLogString);
//				strContainResOms.append(writerLogString).append("\r\n");
//				log.info(OMS_CALL_LOG, writerLogString);
//
//			} else 
			if (temp.getCallHistoryType().equals(ComConstant.COM_OMS_LOG)) {
				omsLog.put("REQ_TIME", temp.getReqDatetime());
				omsLog.put("RSP_TIME", temp.getResDatetime());
				omsLog.put("RESULT_CODE", temp.getResultDescDto().getStatus());

				if (StringUtils.isNotEmpty(temp.getRequestUri())) { // requestUri
					omsLog.put("FUNC_ID", temp.getRequestUri());
				}
				if (StringUtils.isNotEmpty(temp.getCmd())) { // cmd
					omsLog.put("CHANNEL_TYPE", temp.getCmd());
				}
				if (StringUtils.isNotEmpty(temp.getVname())) { // vname
					omsLog.put("CHANNEL", temp.getVname());
				}
				omsLog.put("LOG_TIME", DateUtils.timeHHmmss());
				writerLogString = omsLog.toString();
				writer.println(writerLogString);
				strContainResOms.append(writerLogString).append("\r\n");
				log.debug(writerLogString);
			}
		}

		// inner
//		List<ComInfoDto> comInfoDtos = comInfoDto.getComInfos();
//		for (int i = 0; i < comInfoDtos.size(); i++) {
////		for (int i = comInfoDtos.size() - 1; i >= 0; i--) {
//			ComInfoDto temp = comInfoDtos.get(i);
//			omsLog.put("REQ_TIME", temp.getReqDatetime());
//			omsLog.put("RSP_TIME", temp.getResDatetime());
//			omsLog.put("RESULT_CODE", temp.getResultDescDto().getStatus());
//
//			if (StringUtils.isNotEmpty(temp.getRequestUri())) { // requestUri
//				omsLog.put("FUNC_ID", temp.getRequestUri());
//			}
//			if (StringUtils.isNotEmpty(temp.getCmd())) { // cmd
//				omsLog.put("CHANNEL_TYPE", temp.getCmd());
//			}
//			if (StringUtils.isNotEmpty(temp.getVname())) { // vname
//				omsLog.put("CHANNEL", temp.getVname());
//			}
//
//			writerLogString = omsLog.toString();
//			writer.println(writerLogString);
//			log.info(OMS_CALL_LOG, writerLogString);
//		}

		// db outer
//		List<CallHistoryInfoDto> callInfoDto = comInfoDto.getCallHistoryInfoList();
//		for (int i = callInfoDto.size() - 1; i >= 0; i--) {
//			CallHistoryInfoDto temp = callInfoDto.get(i);
//			if (ComConstant.DB_ACCESS_HISTORY_TYPE.equals(temp.getCallHistoryType())) {
//				omsLog.put("REQ_TIME", temp.getReqDatetime());
//				omsLog.put("RSP_TIME", temp.getResDatetime());
//				omsLog.put("RESULT_CODE", String.valueOf(temp.getHttpStatus()));
//				try {
//					String funcId = temp.getSqlId();
//					omsLog.put("FUNC_ID", funcId.split(".dao.")[1]);
//				} catch (Exception e) {
//					omsLog.put("FUNC_ID", temp.getSqlId());
//				}
//				omsLog.put("CHANNEL", "DB");
//				omsLog.put("CHANNEL_TYPE", "OUT");
//
//				writerLogString = omsLog.toString();
//				writer.println(writerLogString);
//				log.info(OMS_CALL_LOG, writerLogString);
//			}
//		}
		
		List<CallHistoryInfoDto> callInfoDto = comInfoDto.getCallHistoryInfoList();
		if (null != callInfoDto) {
			for (int i = callInfoDto.size() - 1; i >= 0; i--) {
				CallHistoryInfoDto temp = callInfoDto.get(i);
				if (ComConstant.AGS_CALL_SAS_HISTORY_TYPE.equals(temp.getCallHistoryType())) {
					String reqDate = null;
					String resDate = null;
					try {
						reqDate = DateUtils.getDateByFormatToString(temp.getReqDatetime(), ComConstant.CALL_LOG_DATE_FORMAT, ComConstant.OMS_LOG_DATE_FORMAT);
					} catch (ParseException e) {
					}
					try {
						if (StringUtils.isEmpty(temp.getResDatetime())) {
							resDate = DateUtils.getNowDate(ComConstant.OMS_LOG_DATE_FORMAT);
						} else {
							resDate = DateUtils.getDateByFormatToString(temp.getResDatetime(), ComConstant.CALL_LOG_DATE_FORMAT, ComConstant.OMS_LOG_DATE_FORMAT);
						}
					} catch (ParseException e) {
					}

					omsLog.put("REQ_TIME", reqDate);
					omsLog.put("RSP_TIME", resDate);

					if (null == temp.getHttpStatus()) {
						omsLog.put("RESULT_CODE", String.valueOf(resultCode));
					} else {
						omsLog.put("RESULT_CODE", String.valueOf(temp.getHttpStatus()));
					}
					omsLog.put("FUNC_ID", temp.getUrl()); // reuqest uri
					if (comInfoDto.getChannel().equals("OUIF")) {
						omsLog.put("FUNC_ID", "FN00004"); // 외부연동
					}
					omsLog.put("CHANNEL", comInfoDto.getChannel());
					omsLog.put("CHANNEL_TYPE", "OUT");
					writerLogString = omsLog.toString();
					writer.println(writerLogString);
					log.debug("OMS_CALL_LOG" + writerLogString);
				}
			}
		}

		if (isContainResponse) {
			try {
				ComResponseDto<?> comResponseDto = ReqContextComponent.getComResponseDto();
				comResponseDto.setOms(strContainResOms.toString());
			} catch (Exception e) {
			}
		}
		
		return writerLogString;

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

	private void addOmsLog(LinkedHashMap<String, String> omsLog, ComInfoDto comInfoDto,
			Map<String, Object> omsUrlConfig, ComRequestDto comRequestDto) {
		String timeString = DateUtils.timeStamp();

		omsLog.put("SEQ_ID", timeString + StringUtils.getRandomStr(4) + StringUtils.getRandomStr(4));
		omsLog.put("LOG_TYPE", "SVC");
		omsLog.put("SVC_NAME", env.getProperty("log.instanceName") + "_" + env.getProperty("log.instanceCode"));
		omsLog.put("FUNC_ID", comInfoDto.getRequestUri().replace(env.getProperty("server.servlet.context-path"), ""));
		omsLog.put("CHANNEL_TYPE", "IN");

		// base header user-agent
		String agent = null;
		Map<String, String> infoHeaders = comInfoDto.getHeader();
		agent = infoHeaders.get("user-agent");
		omsLog.put("DEV_INFO", getBrower(agent));
		omsLog.put("OS_INFO", getOs(agent));
		omsLog.put("NW_INFO", "ETC");

		// yml에러 읽어서 표시
		if (omsUrlConfig != null) {
			Set<String> setList = omsUrlConfig.keySet();
			Map<String, String> headers = comInfoDto.getHeader();

			for (String key : setList) {
				String value = (String) omsUrlConfig.get(key);

				/**
				 * - O_REQ_H : Origin Request Header (MAP<String,String>) - O_REQ_Q : Origin
				 * Request QueryString (MAP<String,String>) - O_REQ_B : Origin Request Body
				 * (JSON) - P_REQ_H : Parsed Request Header (MAP<String,String>) - P_REQ_Q :
				 * Parsed Request QueryString (MAP<String,String>) - P_REQ_B : Parsed Request
				 * Body (JSON)
				 */
				if (value.startsWith("DEFAULT")) {
					// 고정값으로 omsLog에 셋팅
					value = value.substring(8);
				} else if (value.startsWith("O_REQ_H") || value.startsWith("P_REQ_H")) {
					value = value.substring(8);
					value = headers.get(value.toLowerCase());
				} else if (value.startsWith("O_REQ_Q") || value.startsWith("P_REQ_Q")) {
					value = value.substring(8);
				} else if (value.startsWith("O_REQ_B") || value.startsWith("P_REQ_B")) {
					value = value.substring(8);
					JsonNode targetNode = comInfoDto.getBodyJson();
					value = JsonUtils.getFindValue(value, targetNode).asText();
				}
				omsLog.put(key, value);
			}
			
			if (!StringUtils.isEmptyChk(comInfoDto.getFuncId())) {			
				omsLog.put("FUNC_ID", comInfoDto.getFuncId());
				comInfoDto.setFuncId("");
			}
		}

		omsLog.put("LOG_TIME", DateUtils.timeHHmmss());

		try {
			if (StringUtils.isNotEmpty(AuthUtils.getUser().getUserId())) {
				omsLog.put("SID", AuthUtils.getUser().getUserId());
			}
		} catch (Exception e) {
			omsLog.put("SID", "");
		}

		// RESULT_CODE
		String reqDatetime = comInfoDto.getReqDatetime();
		try {
			reqDatetime = DateUtils.getDateByFormatToString(reqDatetime, ComConstant.CALL_LOG_DATE_FORMAT,
					ComConstant.OMS_LOG_DATE_FORMAT);
		} catch (Exception e) {
			reqDatetime = "";
		}
		omsLog.put("REQ_TIME", reqDatetime);
		String resDatetime = comInfoDto.getResDatetime();
		try {
			resDatetime = DateUtils.getDateByFormatToString(resDatetime, ComConstant.CALL_LOG_DATE_FORMAT,
					ComConstant.OMS_LOG_DATE_FORMAT);
		} catch (Exception e) {
			resDatetime = "";
		}
		omsLog.put("RSP_TIME", resDatetime);

		omsLog.put("CLIENT_IP", comInfoDto.getClientIp());
		omsLog.put("DEV_MODEL", comInfoDto.getVname());
		omsLog.put("LOG_KEY", comInfoDto.getLogKey());

		omsLog.put("CHANNEL", comInfoDto.getChannel());
		omsLog.put("MID", comInfoDto.getMid());

		try {
			if (StringUtils.isNotEmpty(AuthUtils.getUser().getCustId())) {
				omsLog.put("CUST_ID", AuthUtils.getUser().getCustId());
			}
		} catch (Exception e) {
			omsLog.put("CUST_ID", "");
		}

		try {
			if (StringUtils.isNotEmpty(AuthUtils.getUser().getRoleCdId())) {
				omsLog.put("GRP_CODE", AuthUtils.getUser().getRoleCdId());
			}
		} catch (Exception e) {
			omsLog.put("GRP_CODE", "");
		}

	}

	/**
	 * @return
	 */
	public boolean isOmsIsAsync() {
		return omsIsAsync;
	}

	/**
	 * @param omsIsAsync
	 */
	public void setOmsIsAsync(boolean omsIsAsync) {
		this.omsIsAsync = omsIsAsync;
	}

	/**
	 * 브라우져 구분
	 * 
	 * @param agent
	 * @return
	 */
	private String getBrower(String agent) {
		StringBuilder brower = new StringBuilder();
		if (null != agent) {
			if (agent.indexOf("Trident") > -1) {
				brower.append("MSIE");
			} else if (agent.indexOf("Chrome") > -1) {
				brower.append("Chrome");
			} else if (agent.indexOf("Opera") > -1) {
				brower.append("Opera");
			} else if (agent.indexOf("iPhone") > -1 && agent.indexOf("Mobile") > -1) {
				brower.append("iPhone");
			} else if (agent.indexOf("Android") > -1 && agent.indexOf("Mobile") > -1) {
				brower.append("Android");
			} else {
				brower.append("");
			}
		}
		return brower.toString();
	}

	/**
	 * OS 구분
	 * 
	 * @param agent
	 * @return
	 */
	private String getOs(String agent) {
		StringBuilder os = new StringBuilder();
		if (null != agent) {
			if (agent.indexOf("NT 6.0") != -1) {
				os.append("Windows Vista/Server 2008");
			} else if (agent.indexOf("NT 5.2") != -1) {
				os.append("Windows Server 2003");
			} else if (agent.indexOf("NT 5.1") != -1) {
				os.append("Windows XP");
			} else if (agent.indexOf("NT 5.0") != -1) {
				os.append("Windows 2000");
			} else if (agent.indexOf("NT") != -1) {
				os.append("Windows NT");
			} else if (agent.indexOf("9x 4.90") != -1) {
				os.append("Windows Me");
			} else if (agent.indexOf("98") != -1) {
				os.append("Windows 98");
			} else if (agent.indexOf("95") != -1) {
				os.append("Windows 95");
			} else if (agent.indexOf("Win16") != -1) {
				os.append("Windows 3.x");
			} else if (agent.indexOf("Windows") != -1) {
				os.append("Windows");
			} else if (agent.indexOf("Linux") != -1) {
				os.append("Linux");
			} else if (agent.indexOf("Macintosh") != -1) {
				os.append("Macintosh");
			} else {
				os.append("");
			}
		}
		return os.toString();
	}

}
