package kr.co.abacus.common.parser;

import kr.co.abacus.common.api.dao.RuleDAO;
import kr.co.abacus.common.api.dto.RuleDTO;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.parser.config.RequestParserInfoConfig;
import kr.co.abacus.common.rule.RuleConstant;
import kr.co.abacus.common.rule.RuleConversion;
import kr.co.abacus.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "unchecked" })
@Slf4j
@Component
public class RequestParser {

	@Autowired
	private Environment env;

	@Autowired
	private RequestParserInfoConfig requestParserConfig;

	@Autowired
	private RuleConversion ruleConversion;

//	@Autowired
//	private ObjectMapper mapper;

	@Autowired
	private RuleDAO ruleDAO;

	private ArrayList<String> arrayException;

	/**
	 * info
	 */
	public void init() {
		requestParserConfig.init();
	}

	/**
	 * parse
	 * 
	 * @param commonInfo
	 * @throws Exception
	 */
	public void parse(ComInfoDto commonInfo) throws Exception {
		parse(commonInfo, false);
	}

	public void parse(ComInfoDto commonInfo, boolean isInit) throws Exception {
		if (isInit) {
			requestParserConfig.init();
		}
		Map<String, Object> ymlMap = getRequestParserConfig(commonInfo);

		if (ymlMap == null) {
			return;
		}

		List<Map<String, Object>> ymlHeader = (List<Map<String, Object>>) ymlMap.get(RuleConstant.K_HTTP_HEADER);
		List<Map<String, Object>> ymlQueryString = (List<Map<String, Object>>) ymlMap.get(RuleConstant.K_QUERY_STRING);
		List<Map<String, Object>> ymlBody = (List<Map<String, Object>>) ymlMap.get(RuleConstant.K_HTTP_BODY);

		if (ymlHeader != null) {
			for (Map<String, Object> map : ymlHeader) {
				parseSection(map, RuleConstant.K_HTTP_HEADER, commonInfo);
			}
		}

		if (ymlQueryString != null) {
			for (Map<String, Object> map : ymlQueryString) {
				parseSection(map, RuleConstant.K_QUERY_STRING, commonInfo);
			}
		}

		if (ymlBody != null) {
			for (Map<String, Object> map : ymlBody) {
				parseSection(map, RuleConstant.K_HTTP_BODY, commonInfo);
			}
		}

	}

	/**
	 * parse section
	 * 
	 * @param ymlMap
	 * @param section
	 * @param commonInfo
	 * @throws Exception
	 */
	public void parseSection(Map<String, Object> ymlMap, String section, ComInfoDto commonInfo) throws Exception {
		Map<String, Object> conMap = null;
		String targetKey = "";
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

		if (conMap.containsKey(RuleConstant.K_TARGET_KEY)) {
			targetKey = String.valueOf(conMap.get(RuleConstant.K_TARGET_KEY));
		} else {
			arrayException = new ArrayList<String>();
			arrayException.add(RuleConstant.K_ARGS);
			log.debug("[empty_key : {}]", RuleConstant.K_ARGS);
			throw new BizException("empty_key", arrayException);
		}

		Object[] params = ruleConversion.setArgs(args, commonInfo);

		Object val = ruleConversion.convertValue(type, file, function, params);

		if (section.equals(RuleConstant.K_HTTP_HEADER)) {
			commonInfo.getParsedHeader().put(targetKey, String.valueOf(val));
		} else if (section.equals(RuleConstant.K_QUERY_STRING)) {
			commonInfo.getParsedQueryString().put(targetKey, String.valueOf(val));
		} else if (section.equals(RuleConstant.K_HTTP_BODY)) {
			ruleConversion.setJsonValue(commonInfo.getParsedBodyJson(), targetKey, val);
		}
	}

	/**
	 * get RequestParser config
	 * 
	 * @param requestUri
	 * @return
	 */
	@Transactional
	public Map<String, Object> getParserInfo(RuleDTO ruleDTO) throws Exception {
//		List<RuleDTO> rulelist = ruleDAO.getRuleList(ruleDTO);
		List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
		// 미들웨어에서만 사용 하는 것으로 dbParserChk는 선언 되어 있어야 함

		try {
			String dbParserChk = env.getProperty("myconfig.dbParserChk");

			if (dbParserChk == null || dbParserChk.equals("Y")) {
				maplist = ruleDAO.getRules(ruleDTO);
			} else {
				return null;
			}
		} catch (NullPointerException ex) {
			return null;
		}

		if (maplist == null)
			return null;

		Map<String, Object> confMap = new HashMap<String, Object>();
		List<Map<String, Object>> headerList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> bodyList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> queryList = new ArrayList<Map<String, Object>>();

		for (int idx = 0; idx < maplist.size(); idx++) {
			Map<String, Object> map = maplist.get(idx);

			Map<String, Object> conversion = new HashMap<String, Object>();
			conversion.put("targetKey", map.get("RULE_TARGETKEY"));
			conversion.put("type", map.get("RULE_TYPE"));
			String filename = (String) map.get("RULE_OBJECT");
			if (filename != null && filename.indexOf(".") > -1) {
				conversion.put("file", filename.substring(0, filename.lastIndexOf(".")));
				conversion.put("function", filename.substring(filename.lastIndexOf(".") + 1, filename.length()));
			} else {
			}

			String args = "";
			if (map.get("RULE_REQUEST") != null && !map.get("RULE_REQUEST").toString().isEmpty()) {
				args = map.get("RULE_REQUEST") + "." + map.get("RULE_SOURCEKEY");
			}

			if (map.get("RULE_BYTE_POSI") != null && !map.get("RULE_BYTE_POSI").toString().isEmpty()) {
				if (args.length() > 0)
					args += "," + map.get("RULE_BYTE_POSI");
				else
					args = map.get("RULE_BYTE_POSI").toString();
			}

			if (map.get("RULE_BIT_POSI") != null && !map.get("RULE_BIT_POSI").toString().isEmpty()) {
				args += "," + map.get("RULE_BIT_POSI");
			}

			if (map.get("RULE_POINT") != null && !map.get("RULE_POINT").toString().isEmpty()) {
				args += "," + map.get("RULE_POINT");
			}

			if (map.get("RULE_NUMBER") != null && !map.get("RULE_NUMBER").toString().isEmpty()) {
				args += "," + map.get("RULE_NUMBER");
			}

			conversion.put("args", args);

			String kind = (String) map.get("RULE_KIND");

			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("conversion", conversion);
			if (kind.equals("HTTP_HEADER")) {
				headerList.add(temp);
			} else if (kind.equals("QUERY_STRING")) {
				queryList.add(temp);
			} else {
				bodyList.add(temp);
			}
		}

		confMap.put("HTTP_HEADER", headerList);
		confMap.put("QUERY_STRING", queryList);
		confMap.put("HTTP_BODY", bodyList);

		return confMap;
	}

	/**
	 * get RequestParser config
	 * 
	 * @param requestUri
	 * @return
	 */
	public Map<String, Object> getRequestParserConfig(ComInfoDto commonInfo) throws Exception {
		Map<String, Object> confMap = null;
		String ymlName = commonInfo.getRequestUri();
		String contextPath = env.getProperty("server.servlet.context-path");
		String dbYmlName;

		if (contextPath.length() > 0) {
			if (ymlName.startsWith(contextPath)) {
				ymlName = ymlName.substring(contextPath.length() + 1);
			}
			if (ymlName.startsWith("/")) {
				ymlName = ymlName.substring(1);
			}
		}

		if (ymlName.indexOf(".") > -1) {
			ymlName = ymlName.substring(0, ymlName.lastIndexOf("."));
		}
		dbYmlName = ymlName;
		ymlName = ymlName.replaceAll("/", ".");
//		ymlName = ymlName.replaceAll("/", ".") + ".yml";
		if (commonInfo.getVname() != null && commonInfo.getVname().length() > 0) {
			ymlName = commonInfo.getVname() + "." + ymlName;

		} else if (commonInfo.getHeader().get("COL_TYPE") != null && commonInfo.getHeader().get("CMD_TYPE").length() > 0 && commonInfo.getHeader().get("COL_TYPE").length() > 0) {
			ymlName = commonInfo.getHeader().get("DEV_SEQ") + "." + commonInfo.getHeader().get("CMD_TYPE").toUpperCase() + "." + commonInfo.getHeader().get("COL_TYPE") + "." + ymlName;
		}
		confMap = (Map<String, Object>) requestParserConfig.getRequestParserInfo().get(ymlName);

		if (confMap == null) {
			RuleDTO ruleDTO = new RuleDTO();
			String dev_uuid = commonInfo.getHeader().get("DEV_UUID");
			String cmd_type = commonInfo.getHeader().get("CMD_TYPE") == null ? null : commonInfo.getHeader().get("CMD_TYPE").toUpperCase();
			String col_type = commonInfo.getHeader().get("COL_TYPE");
			String dev_type = commonInfo.getHeader().get("DEV_TYPE");

			ruleDTO.setRule_uri("/" + dbYmlName);
			if (dev_uuid != null)
				ruleDTO.setDev_uuid(dev_uuid);
			if (cmd_type != null)
				ruleDTO.setRule_cmd_type(cmd_type);
			if (col_type != null)
				ruleDTO.setRule_col_type(col_type);
			if (dev_type != null)
				ruleDTO.setRule_dev_type(dev_type);

			confMap = getParserInfo(ruleDTO);

			Map<String, Object> temp = requestParserConfig.getRequestParserInfo();
			temp.put(ymlName, confMap);
			requestParserConfig.setRequestParserInfo(temp);
		}

		return confMap;
	}

	/**
	 * parse
	 * 
	 * @param commonInfo
	 * @throws Exception
	 */
	public String parseShCommand(ComInfoDto commonInfo) throws Exception {
		Map<String, Object> ymlMap = getRequestParserConfigShCommand(commonInfo);
		String rn = "";

		if (ymlMap == null) {
			return rn;
		}

		List<Map<String, Object>> ymlBody = (List<Map<String, Object>>) ymlMap.get(RuleConstant.K_HTTP_BODY);

		if (ymlBody != null) {
			for (Map<String, Object> map : ymlBody) {
				rn = parseSectionShCommand(map, RuleConstant.K_HTTP_BODY, commonInfo);
			}
		}

		return rn;

	}

	/**
	 * get RequestParser config
	 * 
	 * @param requestUri
	 * @return
	 */
	public Map<String, Object> getRequestParserConfigShCommand(ComInfoDto commonInfo) throws Exception {
		Map<String, Object> confMap = null;
		String ymlName = commonInfo.getRequestUri();
		String contextPath = env.getProperty("server.servlet.context-path");
		String dbYmlName;

		if (contextPath.length() > 0) {
			if (ymlName.startsWith(contextPath)) {
				ymlName = ymlName.substring(contextPath.length() + 1);
			}
			if (ymlName.startsWith("/")) {
				ymlName = ymlName.substring(1);
			}
		}

		if (ymlName.indexOf(".") > -1) {
			ymlName = ymlName.substring(0, ymlName.lastIndexOf("."));
		}
		dbYmlName = ymlName;

		ymlName = ymlName.replaceAll("/", ".");
//		ymlName = ymlName.replaceAll("/", ".") + ".yml";
		if (commonInfo.getVname() != null && commonInfo.getVname().length() > 0)
			ymlName = commonInfo.getVname() + "." + ymlName;
		else if (commonInfo.getHeader().get("COL_TYPE") != null && commonInfo.getHeader().get("COL_TYPE").length() > 0)
			ymlName = commonInfo.getHeader().get("DEV_SEQ") + "." + commonInfo.getHeader().get("COL_TYPE") + "." + ymlName;

		confMap = (Map<String, Object>) requestParserConfig.getRequestParserInfoShCommand().get(ymlName);

		if (confMap == null) {
			RuleDTO ruleDTO = new RuleDTO();
			String dev_uuid = commonInfo.getHeader().get("DEV_UUID");
			String col_type = commonInfo.getHeader().get("COL_TYPE");
			String dev_type = commonInfo.getHeader().get("DEV_TYPE");

			ruleDTO.setRule_uri("/" + dbYmlName);
			if (dev_uuid != null)
				ruleDTO.setDev_uuid(dev_uuid);
			if (col_type != null)
				ruleDTO.setRule_col_type(col_type);
			if (dev_type != null)
				ruleDTO.setRule_dev_type(dev_type);

			confMap = getParserInfoShCommand(ruleDTO);

			Map<String, Object> temp = requestParserConfig.getRequestParserInfoShCommand();
			temp.put(ymlName, confMap);
			requestParserConfig.setRequestParserInfoShCommand(temp);
		}

		return confMap;
	}

	/**
	 * parse section
	 * 
	 * @param ymlMap
	 * @param section
	 * @param commonInfo
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public String parseSectionShCommand(Map<String, Object> ymlMap, String section, ComInfoDto commonInfo) throws Exception {
		Map<String, Object> conMap = null;
		String type = "";
		String file = "";
		String targetKey = "";
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

		if (conMap.containsKey(RuleConstant.K_TARGET_KEY)) {
			targetKey = String.valueOf(conMap.get(RuleConstant.K_TARGET_KEY));
		} else {
			arrayException = new ArrayList<String>();
			arrayException.add(RuleConstant.K_ARGS);
			log.debug("[empty_key : {}]", RuleConstant.K_ARGS);
			throw new BizException("empty_key", arrayException);
		}

		Object[] params = ruleConversion.setArgs(args, commonInfo);
		Object val = ruleConversion.convertValue(type, file, function, params);

		if (val == null) {
			return "";
		}

		return (String) val;
	}

	/**
	 * get RequestParser config
	 * 
	 * @param requestUri
	 * @return
	 */
	@SuppressWarnings("null")
	@Transactional
	public Map<String, Object> getParserInfoShCommand(RuleDTO ruleDTO) throws Exception {

		List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
		// 미들웨어에서만 사용 하는 것으로 dbParserChk는 선언 되어 있어야 함

		try {
			String dbParserChk = env.getProperty("myconfig.dbParserChk");

			if (dbParserChk == null || dbParserChk.equals("Y")) {
				maplist = ruleDAO.getRulesSHcommand(ruleDTO);
			} else {
				return null;
			}
		} catch (NullPointerException ex) {
			return null;
		}

		if (maplist == null)
			return null;

		Map<String, Object> confMap = new HashMap<String, Object>();
		List<Map<String, Object>> headerList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> bodyList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> queryList = new ArrayList<Map<String, Object>>();

		for (int idx = 0; idx < maplist.size(); idx++) {
			Map<String, Object> map = maplist.get(idx);

			Map<String, Object> conversion = new HashMap<String, Object>();
			conversion.put("targetKey", map.get("RULE_TARGETKEY"));
			conversion.put("type", map.get("RULE_TYPE"));
			String filename = (String) map.get("RULE_OBJECT");
			if (filename != null && filename.indexOf(".") > -1) {
				conversion.put("file", filename.substring(0, filename.lastIndexOf(".")));
				conversion.put("function", filename.substring(filename.lastIndexOf(".") + 1, filename.length()));
			} else {
			}

			String args = "";
			if (map.get("RULE_REQUEST") != null && !map.get("RULE_REQUEST").toString().isEmpty()) {
				args = map.get("RULE_REQUEST") + "." + map.get("RULE_SOURCEKEY");
			}

			if (map.get("RULE_BYTE_POSI") != null && !map.get("RULE_BYTE_POSI").toString().isEmpty()) {
				if (args.length() > 0)
					args += "," + map.get("RULE_BYTE_POSI");
				else
					args = map.get("RULE_BYTE_POSI").toString();
			}

			if (map.get("RULE_BIT_POSI") != null && !map.get("RULE_BIT_POSI").toString().isEmpty()) {
				args += "," + map.get("RULE_BIT_POSI");
			}

			if (map.get("RULE_POINT") != null && !map.get("RULE_POINT").toString().isEmpty()) {
				args += "," + map.get("RULE_POINT");
			}

			if (map.get("RULE_NUMBER") != null && !map.get("RULE_NUMBER").toString().isEmpty()) {
				args += "," + map.get("RULE_NUMBER");
			}

			conversion.put("args", args);

			String kind = (String) map.get("RULE_KIND");

			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("conversion", conversion);
			if (kind.equals("HTTP_HEADER")) {
				headerList.add(temp);
			} else if (kind.equals("QUERY_STRING")) {
				queryList.add(temp);
			} else {
				bodyList.add(temp);
			}
		}

		confMap.put("HTTP_BODY", bodyList);

		return confMap;
	}
}
