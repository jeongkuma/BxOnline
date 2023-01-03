package kr.co.abacus.common.processing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.processing.config.ProcessingInfoConfig;
import kr.co.abacus.common.rule.RuleConstant;
import kr.co.abacus.common.rule.RuleConversion;
import kr.co.abacus.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "unchecked" })
@Slf4j
@Component
public class Processing {
	

	@Autowired
	private Environment env;

	@Autowired
	private ProcessingInfoConfig processingConfig;

	@Autowired
	private RuleConversion ruleConversion;

	@Autowired
	private ObjectMapper mapper;

	private ArrayList<String> arrayException;

	private List<ComInfoDto> comInfoDtolist;

	/**
	 * parse
	 * 
	 * @param commonInfo
	 * @throws Exception
	 */
	public List<ComInfoDto> process(ComInfoDto commonInfo) throws Exception {
		Map<String, Object> ymlMap = getPreRequestParserConfig(commonInfo);

		if (ymlMap == null) {
			return comInfoDtolist;
		}

		List<Map<String, Object>> ymlParsing = (List<Map<String, Object>>) ymlMap.get(RuleConstant.K_PARSING);
		List<Map<String, Object>> ymlMapping = (List<Map<String, Object>>) ymlMap.get(RuleConstant.K_MAPPING);
		List<Map<String, Object>> parseList = null;

		if (ymlParsing != null) {
			parseList = parseSection(ymlParsing.get(0), commonInfo);
		}

		String contextPath = env.getProperty("server.servlet.context-path");

		if (ymlMapping != null) {
			comInfoDtolist = new ArrayList<ComInfoDto>();
			for (Map<String, Object> parseMap : parseList) {
				ComInfoDto dto = createComInfoDto(commonInfo);
				for (Map<String, Object> map : ymlMapping) {
					mapping(dto, map, parseMap);
				}
				dto.setRequestUri(contextPath + "/" + dto.getHeader().get(ComConstant.CMD));
				dto.setVname(dto.getHeader().get(ComConstant.THINGS_NAME));
				dto.setParsedHeader(ObjectUtils.clone(dto.getHeader()));
				dto.setParsedQueryString(ObjectUtils.clone(dto.getQueryString()));
				dto.setParsedBodyJson(mapper.readValue(dto.getBodyJson().toString(), JsonNode.class));
				comInfoDtolist.add(dto);
			}
		}

		return comInfoDtolist;
	}

	public ComInfoDto createComInfoDto(ComInfoDto commonInfo) {
		ComInfoDto dto = new ComInfoDto();
		Map<String, String> header = new LinkedHashMap<String, String>();
		Map<String, String> queryString = new LinkedHashMap<String, String>();
		JsonNode bodyJson = mapper.createObjectNode();
		dto.setHeader(header);
		dto.setQueryString(queryString);
		dto.setBodyJson(bodyJson);
		dto.setService(commonInfo.getService());
		return dto;
	}

	/**
	 * parse section
	 * 
	 * @param ymlMap
	 * @param section
	 * @param commonInfo
	 * @throws Exception
	 */
	public List<Map<String, Object>> parseSection(Map<String, Object> ymlMap, ComInfoDto commonInfo) throws Exception {
		Map<String, Object> conMap = null;
		String type = "";
		String file = "";
		String function = "";
		String arg = "";
		String args[] = null;

		for (String ymlKey : ymlMap.keySet()) {
			if (ymlKey.equals(RuleConstant.K_CONVERSION)) {
				conMap = (Map<String, Object>) ymlMap.get(ymlKey);
			}
		}

		if (conMap == null) {
			arrayException = new ArrayList<String>();
			arrayException.add(RuleConstant.K_CONVERSION);
			log.debug("[empty_key : {}]", RuleConstant.K_CONVERSION);
			throw new BizException("empty_key", arrayException);
		}

		if (conMap.containsKey(RuleConstant.K_TYPE)) {
			type = String.valueOf(conMap.get(RuleConstant.K_TYPE));
		} else {
			arrayException = new ArrayList<String>();
			arrayException.add(RuleConstant.K_TYPE);
			log.debug("[empty_key : {}]", RuleConstant.K_TYPE);
			throw new BizException("empty_key", arrayException);
		}

		if (!type.equals(RuleConstant.V_RAW)) {
			if (conMap.containsKey(RuleConstant.K_FILE)) {
				file = String.valueOf(conMap.get(RuleConstant.K_FILE));
			} else {
				arrayException = new ArrayList<String>();
				arrayException.add(RuleConstant.K_FILE);
				log.debug("[empty_key : {}]", RuleConstant.K_FILE);
				throw new BizException("empty_key", arrayException);
			}

			if (conMap.containsKey(RuleConstant.K_FUNCTION)) {
				function = String.valueOf(conMap.get(RuleConstant.K_FUNCTION));
			} else {
				arrayException = new ArrayList<String>();
				arrayException.add(RuleConstant.K_FUNCTION);
				log.debug("[empty_key : {}]", RuleConstant.K_FUNCTION);
				throw new BizException("empty_key", arrayException);
			}
		}

		if (conMap.containsKey(RuleConstant.K_ARGS)) {
			arg = StringUtils.objectIfNullToEmpty(conMap.get(RuleConstant.K_ARGS));
			if (StringUtils.isNotEmpty(arg)) {
				if (type.equals(RuleConstant.V_RAW)) {
					args = new String[] { arg };
				} else {
					args = ruleConversion.setArgs(arg);
				}
			}
		} else {
			arrayException = new ArrayList<String>();
			arrayException.add(RuleConstant.K_ARGS);
			log.debug("[empty_key : {}]", RuleConstant.K_ARGS);
			throw new BizException("empty_key", arrayException);
		}

		Object[] params = ruleConversion.setArgs(args, commonInfo);
		Object val = ruleConversion.convertValue(type, file, function, params);

		return (List<Map<String, Object>>) val;
	}

	/**
	 * parse section
	 * 
	 * @param ymlMap
	 * @param section
	 * @param commonInfo
	 * @throws Exception
	 */
	public void mapping(ComInfoDto commonInfo, Map<String, Object> ymlMap, Map<String, Object> map) throws Exception {
		Map<String, Object> conMap = null;
		String targetKey = "";
		String arg = "";

		for (String ymlKey : ymlMap.keySet()) {
			if (ymlKey.equals(RuleConstant.K_CONVERSION)) {
				conMap = (Map<String, Object>) ymlMap.get(ymlKey);
			}
		}

		if (conMap == null) {
			arrayException = new ArrayList<String>();
			arrayException.add(RuleConstant.K_CONVERSION);
			log.debug("[empty_key : {}]", RuleConstant.K_CONVERSION);
			throw new BizException("empty_key", arrayException);
		}

		if (conMap.containsKey(RuleConstant.K_ARGS)) {
			arg = StringUtils.objectIfNullToEmpty(conMap.get(RuleConstant.K_ARGS));
		} else {
			arrayException = new ArrayList<String>();
			arrayException.add(RuleConstant.K_ARGS);
			log.debug("[empty_key : {}]", RuleConstant.K_ARGS);
			throw new BizException("empty_key", arrayException);
		}

		if (conMap.containsKey(RuleConstant.K_TARGET_KEY)) {
			targetKey = String.valueOf(conMap.get(RuleConstant.K_TARGET_KEY));
		} else {
			arrayException = new ArrayList<String>();
			arrayException.add(RuleConstant.K_ARGS);
			log.debug("[empty_key : {}]", RuleConstant.K_ARGS);
			throw new BizException("empty_key", arrayException);
		}

		Object val = map.get(arg);

		if (targetKey.startsWith(RuleConstant.V_O_REQ_H)) {
			commonInfo.getHeader().put(targetKey.substring(RuleConstant.V_O_REQ_H.length() + 1), String.valueOf(val));
		} else if (targetKey.startsWith(RuleConstant.V_O_REQ_Q)) {
			commonInfo.getQueryString().put(targetKey.substring(RuleConstant.V_O_REQ_Q.length() + 1), String.valueOf(val));
		} else if (targetKey.startsWith(RuleConstant.V_O_REQ_B)) {
			ruleConversion.setJsonValue(commonInfo.getBodyJson(), targetKey.substring(RuleConstant.V_O_REQ_B.length() + 1), val);
		}
	}

	/**
	 * get RequestParser config
	 * 
	 * @param requestUri
	 * @return
	 */
	public Map<String, Object> getPreRequestParserConfig(ComInfoDto commonInfo) throws Exception {
		Map<String, Object> confMap = null;

//		String ymlName = commonInfo.getService() + ".yml";
		String ymlName = commonInfo.getService();
		confMap = (Map<String, Object>) processingConfig.getProcessingInfo().get(ymlName);

//		if (confMap == null) {
//			arrayException = new ArrayList<String>();
//			arrayException.add("PreRequestParser");
//			arrayException.add(ymlName);
//			throw new BizException("config_file_not_found", arrayException);
//		}

		return confMap;
	}
}
