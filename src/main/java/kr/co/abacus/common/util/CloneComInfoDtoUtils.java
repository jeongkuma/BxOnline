package kr.co.abacus.common.util;

import kr.co.abacus.common.dto.common.ComInfoDto;

import java.util.Map;

public class CloneComInfoDtoUtils {

	public static ComInfoDto comInfoDtoClone(ComInfoDto comInfoDto) {
		ComInfoDto retComInfoDto = new ComInfoDto();

		// 수정 19.11.26

		retComInfoDto.setAsyncConectFailCode(comInfoDto.getAsyncConectFailCode());
		retComInfoDto.setAuth(comInfoDto.getAuth());
		retComInfoDto.setBodyJson(comInfoDto.getBodyJson());
		retComInfoDto.setBodyString(comInfoDto.getBodyString());
		retComInfoDto.setCallHistoryInfoList(comInfoDto.getCallHistoryInfoList());
		retComInfoDto.setCallHistoryType(comInfoDto.getCallHistoryType());
		retComInfoDto.setCallType(comInfoDto.getCallType());
		retComInfoDto.setChannel(comInfoDto.getChannel());
		retComInfoDto.setClientIp(comInfoDto.getClientIp());
		retComInfoDto.setCmd(comInfoDto.getCmd());
		retComInfoDto.setComInfos(comInfoDto.getComInfos());
		retComInfoDto.setCommandType(comInfoDto.getCommandType());
		retComInfoDto.setDevClsCd(comInfoDto.getDevClsCd());
		retComInfoDto.setDevMdlCd(comInfoDto.getDevMdlCd());
		retComInfoDto.setErrorMsg(comInfoDto.getErrorMsg());
		retComInfoDto.setFuncId(comInfoDto.getFuncId());
		retComInfoDto.setIsException(comInfoDto.getIsException());
		retComInfoDto.setLogKey(comInfoDto.getLogKey());
		retComInfoDto.setMid(comInfoDto.getMid());
		retComInfoDto.setMsg(comInfoDto.getMsg());
		retComInfoDto.setParsedBodyJson(comInfoDto.getParsedBodyJson());
		retComInfoDto.setParsedHeader(comInfoDto.getParsedHeader());
		retComInfoDto.setParsedQueryString(comInfoDto.getParsedQueryString());
		retComInfoDto.setQueryString(comInfoDto.getQueryString());
		retComInfoDto.setReqDatetime(comInfoDto.getReqDatetime());
		retComInfoDto.setRequestUri(comInfoDto.getRequestUri());
		retComInfoDto.setResDatetime(comInfoDto.getResDatetime());
		retComInfoDto.setResultDescDto(comInfoDto.getResultDescDto());
		retComInfoDto.setService(comInfoDto.getService());
		retComInfoDto.setTargetMapper(comInfoDto.getTargetMapper());
		retComInfoDto.setTcpController(comInfoDto.getTcpController());
		retComInfoDto.setTemp(comInfoDto.getTemp());
		retComInfoDto.setUuid(comInfoDto.getUuid());
		retComInfoDto.setVname(comInfoDto.getVname());
		retComInfoDto.setXlang(comInfoDto.getXlang());
		retComInfoDto.setXservice(comInfoDto.getXservice());

		Map<String, String> header = comInfoDto.getHeader();
		Map<String, String> newHeader = new CaseInsensitiveMap<String, String>();
//		Map<String, String> newHeader = new LinkedHashMap<>();
		newHeader.putAll(header);
		retComInfoDto.setHeader(newHeader);

		return retComInfoDto;
	}

}
