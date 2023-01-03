package kr.co.auiot.common.util;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.common.ResultDescDto;
import kr.co.abacus.common.util.DateUtils;
import kr.co.abacus.common.util.LinkedHashMap;
import kr.co.abacus.common.util.StringUtils;

import java.util.List;

public class OmsUtils {

	public static String CHANNEL_ONEM2M = "ONEM2M";
	public static String CHANNEL_DB = "DB";
	public static String CHANNEL_MAP = "MAP";
	public static String CHANNEL_TYPE_OUT = "OUT";
	public static String CHANNEL_TYPE_IN = "IN";

	public static void addOmsData(String key, String value) {
		ReqContextComponent.getOmsLog().put(key, value);
	}

	public static String getOmsData(String key) {
		return ReqContextComponent.getOmsLog().get(key);
	}

	public static ComInfoDto addInnerOms() {
		return addInnerOms(CHANNEL_TYPE_IN, null, null);
	}

	public static ComInfoDto addInnerOms(String channel) {
		return addInnerOms(channel, null, null);
	}

	public static ComInfoDto addInnerOms(String channel, String channelType) {
		return addInnerOms(channel, channelType, null);
	}

	public static ComInfoDto addInnerOms(String channel, String channelType, String funcId) {
		ComInfoDto temp = new ComInfoDto(ComConstant.COM_OMS_LOG);

		ComInfoDto comInfo = ReqContextComponent.getComInfoDto();
		List<ComInfoDto> comInfos = comInfo.getComInfos();
		comInfos.add(temp);

		temp.setReqDatetime(DateUtils.timeStamp(ComConstant.OMS_LOG_DATE_FORMAT)); // RSQ_TIME
		temp.setIsException(true);

		if (StringUtils.isNotEmpty(channel)) { // CHANNEL
			temp.setChannel(channel);
		}

		if (StringUtils.isNotEmpty(channelType)) { // CHANNEL_TYPE
			temp.setCmd(channelType);
		}

		if (StringUtils.isNotEmpty(funcId)) { // FUNC_ID
			temp.setRequestUri(funcId);
		} else {
			StackTraceElement[] s = new Throwable().getStackTrace();
			StackTraceElement before = null;
			for (int i = 1; i < 3; i++) {
				before = s[i];
				if (!"addInnerOms".equals(before.getMethodName())) {
					break;
				}
			}
			temp.setRequestUri(before.getClassName() + "." + before.getMethodName());
		}

		// def status
		ResultDescDto rd = new ResultDescDto();
		rd.setStatus("20000000");
		temp.setResultDescDto(rd);

		return temp;
	}

	public static ComInfoDto addInnerOms(ComInfoDto comInfo, String channel, String channelType, String funcId) {
		ComInfoDto temp = new ComInfoDto(ComConstant.COM_OMS_LOG);

		// ComInfoDto comInfo = ReqContextComponent.getComInfoDto();
		List<ComInfoDto> comInfos = comInfo.getComInfos();
		comInfos.add(temp);

		temp.setReqDatetime(DateUtils.timeStamp(ComConstant.OMS_LOG_DATE_FORMAT)); // RSQ_TIME
		temp.setIsException(true);

		if (StringUtils.isNotEmpty(channel)) { // CHANNEL
			temp.setChannel(channel);
		}

		if (StringUtils.isNotEmpty(channelType)) { // CHANNEL_TYPE
			temp.setCmd(channelType);
		}

		if (StringUtils.isNotEmpty(funcId)) { // FUNC_ID
			temp.setRequestUri(funcId);
		} else {
			StackTraceElement[] s = new Throwable().getStackTrace();
			StackTraceElement before = null;
			for (int i = 1; i < 3; i++) {
				before = s[i];
				if (!"addInnerOms".equals(before.getMethodName())) {
					break;
				}
			}
			temp.setRequestUri(before.getClassName() + "." + before.getMethodName());
		}

		// def status
		ResultDescDto rd = new ResultDescDto();
		rd.setStatus("20000000");
		temp.setResultDescDto(rd);

		return temp;
	}

	public static void expInnerOms(ComInfoDto temp, Exception e) {
		
		// DB 이외는 화면에서 받아서 처리 함 
		temp.getResultDescDto().setStatus("50000000");		
		if (!StringUtils.isEmpty(temp.getAsyncConectFailCode()) ) {
			temp.getResultDescDto().setStatus(temp.getAsyncConectFailCode());
		} 
		temp.setAsyncConectFailCode("");
		
		temp.setResDatetime(DateUtils.timeStamp(ComConstant.OMS_LOG_DATE_FORMAT)); // RSP_TIME
	}

	public static void endInnerOms(ComInfoDto temp) {
		temp.setResDatetime(DateUtils.timeStamp(ComConstant.OMS_LOG_DATE_FORMAT)); // RSP_TIME
	}

	public static void endInnerOms(ComInfoDto temp, ComInfoDto comInfoDto, LinkedHashMap<String, String> omsLog) {
		temp.setResDatetime(DateUtils.timeStamp(ComConstant.OMS_LOG_DATE_FORMAT)); // RSP_TIME
	}

	public static void expInnerOms(ComInfoDto temp, ComInfoDto comInfoDto, LinkedHashMap<String, String> omsLog, Exception e) {
		
		temp.getResultDescDto().setStatus("50000000");		
		if (!StringUtils.isEmpty(temp.getAsyncConectFailCode()) ) {
			temp.getResultDescDto().setStatus(temp.getAsyncConectFailCode());
		} 
		temp.setAsyncConectFailCode("");
		
		temp.setResDatetime(DateUtils.timeStamp(ComConstant.OMS_LOG_DATE_FORMAT)); // RSP_TIME
	}


}
