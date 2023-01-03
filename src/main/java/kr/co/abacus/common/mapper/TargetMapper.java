package kr.co.abacus.common.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.abacus.common.api.dao.RuleDAO;
import kr.co.abacus.common.api.dto.RuleDTO;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.mapper.config.TargetMapperInfoConfig;
import kr.co.abacus.common.rule.RuleConstant;
import kr.co.abacus.common.rule.RuleConversion;
import kr.co.abacus.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SuppressWarnings({ "unchecked" })
@Slf4j
@Component
public class TargetMapper {

	@Autowired
	private Environment env;

	@Autowired
	private TargetMapperInfoConfig targetMapperConfig;

	@Autowired
	private RuleConversion ruleConversion;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private RuleDAO ruleDAO;

	private ArrayList<String> arrayException;

	/**
	 * info
	 */
	public void init() {
		targetMapperConfig.init();
	}

	public void doMap(ComInfoDto commonInfo) throws Exception {
		commonInfo.setTargetMapper(new LinkedHashMap<String, Object>());
		Map<String, Object> ymlMap = getTargetMapperConfig(commonInfo);
		if (ymlMap == null) {
			return;
		}

		List<Map<String, Object>> ymlTarget = (List<Map<String, Object>>) ymlMap.get(RuleConstant.K_TARGET);

		if (ymlTarget != null) {
			for (Map<String, Object> map : ymlTarget) {
				try {
					parseSection(map, RuleConstant.K_TARGET, commonInfo);
				} catch (Exception e) {
					log.debug("=== doMap :: " + map.toString());
				}
			}
		}
//		log.info("targetMapper :{}",
//				mapper.writerWithDefaultPrettyPrinter().writeValueAsString(commonInfo.getTargetMapper()));
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

		if (section.equals(RuleConstant.K_TARGET)) {
			commonInfo.getTargetMapper().put(targetKey, val);
		}
	}

	/**
	 * get RequestParser config
	 * 
	 * @param requestUri
	 * @return
	 */
	@SuppressWarnings("null")
	@Transactional
	public Map<String, Object> getParserInfo(RuleDTO ruleDTO) throws Exception {
//		List<RuleDTO> rulelist = ruleDAO.getRuleList(ruleDTO);

		List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();

		try {
			String dbMapperChk = env.getProperty("myconfig.dbMapperChk");

			if (dbMapperChk == null || dbMapperChk.equals("Y")) {
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
		List<Map<String, Object>> targetList = new ArrayList<Map<String, Object>>();

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
			targetList.add(temp);
		}

		confMap.put("TARGET", targetList);

		return confMap;
	}

	/**
	 * get TargetMapper config
	 * 
	 * @param requestUri
	 * @return
	 */
	public Map<String, Object> getTargetMapperConfig(ComInfoDto commonInfo) throws Exception {
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
//		ymlName = commonInfo.getVname() + "." + ymlName;
		if (commonInfo.getVname() != null && commonInfo.getVname().length() > 0)
			ymlName = commonInfo.getVname() + "." + ymlName;
		else if (commonInfo.getHeader().get("COL_TYPE") != null
//				&& commonInfo.getHeader().get("CMD_TYPE").length() > 0
				&& commonInfo.getHeader().get("COL_TYPE").length() > 0)
			ymlName = commonInfo.getHeader().get("DEV_SEQ") + "." + commonInfo.getHeader().get("CMD_TYPE").toUpperCase() + "." + commonInfo.getHeader().get("COL_TYPE") + "." + ymlName;

		confMap = (Map<String, Object>) targetMapperConfig.getTargetMapperInfo().get(ymlName);

		if (confMap == null) {
			RuleDTO ruleDTO = new RuleDTO();
			String entr_seq = commonInfo.getHeader().get("ENTR_DEV_SEQ");
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

			Map<String, Object> temp = targetMapperConfig.getTargetMapperInfo();
			temp.put(ymlName, confMap);
			targetMapperConfig.setTargetMapperInfo(temp);
		}
		return confMap;
	}
}
