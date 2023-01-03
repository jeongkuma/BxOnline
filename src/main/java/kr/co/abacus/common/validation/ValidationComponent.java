package kr.co.abacus.common.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import kr.co.abacus.common.api.dao.ValidationDAO;
import kr.co.abacus.common.api.dto.ValidationDTO;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.MessageServerConfig;
import kr.co.abacus.common.rule.RuleConstant;
import kr.co.abacus.common.validation.config.ValidationInfoConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class ValidationComponent {

	@Autowired
	private Environment env = null;

	@Autowired
	private ValidationInfoConfig validationConfig = null;

	@Autowired
	private MessageServerConfig messageServerConfig = null;

	@Autowired
	private ObjectMapper mapper = null;

	@Autowired
	private ValidationDAO validationDAO;

	private ArrayList<String> arrayException;

	private String PERMISSIVE_HEADER_CHARS;
	private String PERMISSIVE_BODY_CHARS;
	private String SERVERMSG_YN;;

	@PostConstruct
	public void setInit() {
		PERMISSIVE_HEADER_CHARS = env.getProperty("permissive.header_chars");
		PERMISSIVE_BODY_CHARS = env.getProperty("permissive.body_chars");
		SERVERMSG_YN = env.getProperty("myconfig.servermsgYn");
		if (SERVERMSG_YN == null) {
			SERVERMSG_YN = "Y";
		}
	}

	/**
	 * init
	 */
	public void init() {
		validationConfig.init();
	}

	public void vaildate(ComInfoDto commonInfo) throws Exception {

		Map<String, Object> ymlMap = getValidConfig(commonInfo);

		if (ymlMap == null) {
			/*
			 * arrayException = new ArrayList<String>(); arrayException.add("validation");
			 * arrayException.add(ymlName); throw new BizException("config_file_not_found",
			 * arrayException);
			 */
			return;
		}

		Map<String, Object> ymlHeader = (Map<String, Object>) ymlMap.get(RuleConstant.K_HTTP_HEADER);
		Map<String, Object> ymlQueryString = (Map<String, Object>) ymlMap.get(RuleConstant.K_QUERY_STRING);
		Map<String, Object> ymlBody = (Map<String, Object>) ymlMap.get(RuleConstant.K_HTTP_BODY);

		Map<String, String> dataHeder = commonInfo.getParsedHeader();
		Map<String, String> dataQueryString = commonInfo.getParsedQueryString();
		Map<String, Object> conMap = null;
		String mandatory = "";

		if (null != ymlHeader) {
			// Header check
			for (String ymlKey : ymlHeader.keySet()) {
				// log.debug("key : " + ymlKey + ", value : " + ymlHeader.get(ymlKey));
				conMap = (Map<String, Object>) ymlHeader.get(ymlKey);
				mandatory = (String) defaultIfEmpty(conMap.get(RuleConstant.K_REQUIRED_KEY), RuleConstant.V_YES);
				// log.debug("[mandatory : {}, {}]", ymlKey, mandatory);

				if (mandatory.equals(RuleConstant.V_YES)) {
					if (dataHeder.containsKey(ymlKey.toLowerCase())) {
						headerValidCheck(ymlKey.toLowerCase(), dataHeder.get(ymlKey.toLowerCase()), conMap);
					} else {
						arrayException = new ArrayList<String>();
						ymlKey = messageServerConfig.getMessage(ymlKey);
						arrayException.add(ymlKey);
						log.debug("[not_a_httpheader : {}]", ymlKey);
						throw new BizException("not_a_httpheader", arrayException);
					}
				}
			}
		}

		if (null != ymlQueryString) {
			// QueryString check
			for (String ymlKey : ymlQueryString.keySet()) {
				log.debug("key : " + ymlKey + ", value : " + ymlQueryString.get(ymlKey));
				conMap = (Map<String, Object>) ymlQueryString.get(ymlKey);
				mandatory = (String) defaultIfEmpty(conMap.get(RuleConstant.K_REQUIRED_KEY), RuleConstant.V_YES);
				log.debug("[mandatory : {}, {}]", ymlKey, mandatory);

				if (mandatory.equals(RuleConstant.V_YES)) {
					if (dataQueryString.containsKey(ymlKey)) {
						headerValidCheck(ymlKey, dataQueryString.get(ymlKey), conMap);
					} else {
						arrayException = new ArrayList<String>();
						ymlKey = messageServerConfig.getMessage(ymlKey);
						arrayException.add(ymlKey);
						log.debug("[not_a_QueryString : {}]", ymlKey);
						throw new BizException("not_a_parameter");
					}
				}
			}
		}

		if (null != ymlBody) {
			Map<String, Object> dataBody = mapper.readValue(commonInfo.getParsedBodyJson().toString(), Map.class);
			String ymlBodyJson = mapper.writeValueAsString(ymlBody);
			JsonNode ymlBodyNode = mapper.readValue(ymlBodyJson, JsonNode.class);

			Iterator<Entry<String, JsonNode>> keys = ymlBodyNode.fields();
			while (keys.hasNext()) {
				Entry<String, JsonNode> entry = keys.next();
				String ymlKey = entry.getKey();
				JsonNode ymlNode = entry.getValue();
				// log.debug("[key : {}, NodeType : {}, node : {}]", ymlKey,
				// ymlNode.getNodeType(), ymlNode);

				if (ymlNode.has(RuleConstant.K_REQUIRED_KEY)) {
					mandatory = ymlNode.get(RuleConstant.K_REQUIRED_KEY).asText(RuleConstant.V_YES);
				}

				boolean isKey = false;
				if (dataBody.containsKey(ymlKey)) {
					isKey = true;
				}

				if (mandatory.equals(RuleConstant.V_YES)) {
					if (dataBody.containsKey(ymlKey)) {
						// log.debug("[key : {}, node : {}]", ymlKey, dataBody.get(ymlKey));
						bodyValidCheck(ymlKey, dataBody.get(ymlKey), ymlNode, isKey);
					} else {
						arrayException = new ArrayList<String>();
						ymlKey = messageServerConfig.getMessage(ymlKey);
						arrayException.add(ymlKey);
						log.debug("[empty_key : {}]", ymlKey);

						throw new BizException("empty_key", arrayException);
					}
				} else {
					bodyValidCheck(ymlKey, dataBody.get(ymlKey), ymlNode, isKey);
				}
			}
		}
	}

	public void bodyValidCheck(String key, Object val, JsonNode con, boolean isKey) throws Exception {
		// log.debug("[key : {}, value : {}, con : {}, isKey : {} ]", key, val, con,
		// isKey);

		String dataType = "";
		ArrayList<String> arrayException = new ArrayList<String>();

		// key에 대응 하는 메세지를 읽어 온다.
		String msgKey = messageServerConfig.getMessage(key);

		if (con.has(RuleConstant.K_DATA_TYPE)) { // mandatory
			dataType = con.get(RuleConstant.K_DATA_TYPE).asText("");

			if (!(dataType.equals(RuleConstant.V_STRING) || dataType.equals(RuleConstant.V_NUMBER) || dataType.equals(RuleConstant.V_SHORT) || dataType.equals(RuleConstant.V_INTEGER) || dataType.equals(RuleConstant.V_LONG)
					|| dataType.equals(RuleConstant.V_FLOAT) || dataType.equals(RuleConstant.V_DOUBLE) || dataType.equals(RuleConstant.V_FLOAT) || dataType.equals(RuleConstant.V_BOOLEAN) || dataType.equals(RuleConstant.V_BIGDECIMAL)
					|| dataType.equals(RuleConstant.V_DATE) || dataType.equals(RuleConstant.V_OBJECT) || dataType.equals(RuleConstant.V_ARRAY))) {
				arrayException.add(RuleConstant.K_DATA_TYPE);
				log.debug("config_file_invalid_value:{}, {}", key, dataType);
				throw new BizException("config_file_invalid_value", arrayException);
			}
		} else {
			arrayException.add(RuleConstant.K_DATA_TYPE);
			log.debug("config_file_empty_key:{}, {}", key, dataType);
			throw new BizException("config_file_empty_key", arrayException);
		}

		if (dataType.equals(RuleConstant.V_ARRAY)) {
			if (!(val instanceof List)) {

				// arrayException.add(key);
				arrayException.add(getMessageKey(key, msgKey));
				arrayException.add(dataType);
				log.debug("abnormal_format:{}, {}", key, dataType);
				throw new BizException("abnormal_format", arrayException);
			}
		} else if (dataType.equals(RuleConstant.V_OBJECT)) {
			if (!(val instanceof Map)) {
				// arrayException.add(key);
				arrayException.add(getMessageKey(key, msgKey));
				arrayException.add(dataType);
				log.debug("abnormal_format:{}, {}", key, dataType);
				throw new BizException("abnormal_format", arrayException);
			}
		}

		Iterator<Entry<String, JsonNode>> keys = con.fields();

		String required = "";
		String comPatternSkip = "N";
		while (keys.hasNext()) {
			Entry<String, JsonNode> entry = keys.next();
			String ymlChildKey = entry.getKey();
			JsonNode ymlChildNode = entry.getValue();
			if (ymlChildKey.equals(RuleConstant.K_COM_PATTERN_CKIP)) {
				comPatternSkip = ymlChildNode.asText();
				if (ymlChildNode.has(RuleConstant.K_COM_PATTERN_CKIP)) {
					comPatternSkip = ymlChildNode.get(RuleConstant.K_COM_PATTERN_CKIP).asText();
				}
			}
		}

		log.debug("=== matchNotChar : " + comPatternSkip);

		while (keys.hasNext()) {
			Entry<String, JsonNode> entry = keys.next();
			String ymlChildKey = entry.getKey();
			JsonNode ymlChildNode = entry.getValue();

			if (ymlChildNode.has(RuleConstant.K_REQUIRED_KEY)) {
				required = ymlChildNode.get(RuleConstant.K_REQUIRED_KEY).asText(RuleConstant.V_YES);
			} else {
				required = RuleConstant.V_YES;
			}

			// log.debug("[key: {}, childKey : {}, required:{}, val:{}, nodeType : {}, node
			// : {}]", key, ymlChildKey, required, val, ymlChildNode.getNodeType(),
			// ymlChildNode);
			if (ymlChildNode.getNodeType().equals(JsonNodeType.OBJECT) || ymlChildNode.getNodeType().equals(JsonNodeType.ARRAY)) {

				if (val instanceof Map) {
					boolean isKeySub = false;
					if (((Map) val).containsKey(ymlChildKey)) {
						isKeySub = true;
					}

					if (required.equals(RuleConstant.V_YES)) {
						if (((Map) val).containsKey(ymlChildKey)) {
							// log.debug("[key : {}, childKey : {}, value : {}, node : {}]", key,
							// ymlChildKey, ((Map) val).get(ymlChildKey),ymlChildNode);
							bodyValidCheck(ymlChildKey, ((Map) val).get(ymlChildKey), ymlChildNode, isKeySub);
						} else {
							// log.debug("[key : {}, node : {}]", ymlKey, dataBody.get(ymlKey));
							arrayException = new ArrayList<String>();
							arrayException.add(ymlChildKey);
							log.debug("[empty_key : {}]", ymlChildKey);
							throw new BizException("empty_key", arrayException);
						}
					} else {
						bodyValidCheck(ymlChildKey, ((Map) val).get(ymlChildKey), ymlChildNode, isKeySub);
					}
				} else if (val instanceof List) {
					// log.debug("[#### childNode List : {}", mapper.convertValue(ymlChildNode,
					// Map.class));
					if (val == null || ((List) val).size() == 0) {
						// log.debug("[#### null value : {}, {}, {}", val, ymlChildKey, ymlChildNode);
						if (required.equals(RuleConstant.V_YES)) {
							arrayException = new ArrayList<String>();
							arrayException.add(ymlChildKey);
							log.debug("[empty_key : {}]", ymlChildKey);
							throw new BizException("empty_key", arrayException);
						} else {
							bodyValidCheck(ymlChildKey, new HashMap(), ymlChildNode, false); // ??????? true/false
						}
					} else {
						for (int i = 0; i < ((List) val).size(); i++) {
							Object childObj = ((List) val).get(i);
							// log.debug("[#### childVal List : {}, childKey : {}", childObj, ymlChildKey);

							if (childObj instanceof Map) {
								boolean isKeySub = false;
								if (((Map) childObj).containsKey(ymlChildKey)) {
									isKeySub = true;
								}
								if (required.equals(RuleConstant.V_YES)) {
									if (((Map) childObj).containsKey(ymlChildKey)) {
										bodyValidCheck(ymlChildKey, ((Map) childObj).get(ymlChildKey), ymlChildNode, isKeySub);
										// log.debug("[key : {}, childKey : {}, value : {}, node : {}]", key,
										// ymlChildKey, value, ymlChildNode );
									} else {
										// log.debug("[key : {}, node : {}]", ymlKey, dataBody.get(ymlKey));
										arrayException = new ArrayList<String>();
										arrayException.add(ymlChildKey);
										log.debug("[empty_key : {}]", ymlChildKey);
										throw new BizException("empty_key", arrayException);
									}
								} else {
									bodyValidCheck(ymlChildKey, ((Map) childObj).get(ymlChildKey), ymlChildNode, isKeySub);
								}
								// log.debug("[key : {}, childKey : {}, value : {}, node : {}]", key,
								// ymlChildKey, value, ymlChildNode );
							} else {
								bodyValidCheck(ymlChildKey, childObj, ymlChildNode, true);
								// log.debug("[key : {}, childKey : {}, value : {}, node : {}]", key,
								// ymlChildKey, value, ymlChildNode );
							}
						}
					}
				} else { //
					bodyValidCheck(ymlChildKey, val, ymlChildNode, isKey);
				}
			}
		}

		if (con.has(RuleConstant.K_NOT_NULL)) {
			// log.debug("@@@@[key : {}, value : {}, notNull : {} ]", key, value,
			// con.get(K_NOT_NULL));
			String ymlVal = con.get(RuleConstant.K_NOT_NULL).asText();
			if (isKey) {
				if (ymlVal.equals(RuleConstant.V_YES) && isEmpty(val)) {
					// arrayException.add(key);
					arrayException.add(getMessageKey(key, msgKey));
					log.debug("empty_value: {}", key);
					throw new BizException("empty_value", arrayException);
				}
			}
		}

		if (!dataType.equals(RuleConstant.V_ARRAY) && !dataType.equals(RuleConstant.V_OBJECT)) {
			if (isKey) {
				// Special Char Check ----------------------
				if (comPatternSkip.equals("N") && !String.valueOf(val).matches(PERMISSIVE_BODY_CHARS)) {
					// arrayException.add(key);
					arrayException.add(getMessageKey(key, msgKey));
					arrayException.add(String.valueOf(val));
					log.debug("special_char : {}, value : {}, {}, {} ", key, val, dataType);
					throw new BizException("special_char", arrayException);
				}

				// XSS Char Check ----------------------
				for (Pattern scriptPattern : xssPatterns) {
					if (scriptPattern.matcher(String.valueOf(val)).find()) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						arrayException.add(String.valueOf(val));
						throw new BizException("invalid_xss", arrayException);
					}
				}
			}
		}

		if (dataType.equals(RuleConstant.V_STRING) || dataType.equals(RuleConstant.V_NUMBER)) {
			if (con.has(RuleConstant.K_LENGTH)) {
				String ymlVal = con.get(RuleConstant.K_LENGTH).asText();
				if (isKey) {
					if (!isLength(val, Integer.parseInt(ymlVal))) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						arrayException.add(ymlVal);
						log.debug("wrong_length:{}", key);
						throw new BizException("wrong_length", arrayException);
					}
				}
			}
			if (con.has(RuleConstant.K_MIN_LENGTH)) {
				String ymlVal = con.get(RuleConstant.K_MIN_LENGTH).asText();
				if (isKey) {
					if (!isMinLength(val, Integer.parseInt(ymlVal))) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						arrayException.add(ymlVal);
						log.debug("too_short:{}", key);
						throw new BizException("too_short", arrayException);
					}
				}
			}
			if (con.has(RuleConstant.K_MAX_LENGTH)) {
				String ymlVal = con.get(RuleConstant.K_MAX_LENGTH).asText();
				if (isKey) {
					if (!isMaxLength(val, Integer.parseInt(ymlVal))) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						arrayException.add(ymlVal);
						log.debug("too_long:{}", key);
						throw new BizException("too_long", arrayException);
					}
				}
			}

			if (dataType.equals(RuleConstant.V_STRING)) {
				if (con.has(RuleConstant.K_NO_WHITE_SPACE)) {
					String ymlVal = con.get(RuleConstant.K_NO_WHITE_SPACE).asText();
					if (isKey) {
						if (ymlVal.equals(RuleConstant.V_YES) && isWhitespace(val)) {
							// arrayException.add(key);
							arrayException.add(getMessageKey(key, msgKey));
							log.debug("white_space:{}, value:{}", key, val);
							throw new BizException("white_space", arrayException);
						}
					}
				}
				if (con.has(RuleConstant.K_ACCEPT)) {
					if (isKey) {
						if (!isAccept(val, con.get(RuleConstant.K_ACCEPT).asText())) {
							// arrayException.add(key);
							arrayException.add(getMessageKey(key, msgKey));
							log.debug("accept:{}", key);
							throw new BizException("accept", arrayException);
						}
					}
				}
				if (con.has(RuleConstant.K_REJECT)) {
					if (isKey) {
						if (!isReject(val, con.get(RuleConstant.K_REJECT).asText())) {
							// arrayException.add(key);
							arrayException.add(getMessageKey(key, msgKey));
							log.debug("reject:{}", key);
							throw new BizException("reject", arrayException);
						}
					}
				}
			} // END STRING
		} // END STRING, NUMBER

		if (dataType.equals(RuleConstant.V_NUMBER) || dataType.equals(RuleConstant.V_SHORT) || dataType.equals(RuleConstant.V_INTEGER) || dataType.equals(RuleConstant.V_LONG) || dataType.equals(RuleConstant.V_FLOAT)
				|| dataType.equals(RuleConstant.V_DOUBLE) || dataType.equals(RuleConstant.V_BIGDECIMAL)) {

			if (isKey) {
				if (!isNumeric(val)) {
					// arrayException.add(key);
					arrayException.add(getMessageKey(key, msgKey));
					log.debug("not_a_number: {}", key);
					throw new BizException("not_a_number", arrayException);
				}
			}
			if (con.has(RuleConstant.K_MAX_VALUE)) {
				String ymlVal = con.get(RuleConstant.K_MAX_VALUE).asText();

				if (isKey) {
					if (!isMaxValue(val, ymlVal)) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						arrayException.add(ymlVal);
						log.debug("less_than:{}", key);
						throw new BizException("less_than", arrayException);
					}
				}
			}
			if (con.has(RuleConstant.K_MIN_VALUE)) {
				String ymlVal = con.get(RuleConstant.K_MIN_VALUE).asText();

				if (isKey) {
					if (!isMaxValue(val, ymlVal)) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						arrayException.add(ymlVal);
						log.debug("greater_than:{}", key);
						throw new BizException("greater_than", arrayException);
					}
				}
			}
		}

		// BOOLEAN
		if (dataType.equals(RuleConstant.V_BOOLEAN)) {
			if (isKey) {
				if (!isBoolean(val)) {
					// arrayException.add(key);
					arrayException.add(getMessageKey(key, msgKey));
					arrayException.add(RuleConstant.V_BOOLEAN);
					log.debug("abnormal_format:{}", key);
					throw new BizException("abnormal_format", arrayException);
				}
			}
		}

		// DATE
		if (dataType.equals(RuleConstant.V_DATE)) {
			if (con.has(RuleConstant.K_FORMAT)) {
				String ymlVal = con.get(RuleConstant.K_FORMAT).asText();
				if (isKey) {
					if (!isDate(String.valueOf(val), ymlVal)) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						arrayException.add(ymlVal);
						log.debug("abnormal_format:{}", key);
						throw new BizException("abnormal_format", arrayException);
					}
				}
			} else {
				arrayException.add(RuleConstant.K_FORMAT);
				log.debug("abnormal_format:{}, {}", key, RuleConstant.K_FORMAT);
				throw new BizException("abnormal_format", arrayException);
			}
		}
	}

	public void bodyValidCheck(String key, Object val, JsonNode con) throws Exception {
		// log.debug("[key : {}, value : {}, con : {} ]", key, val, con);

		String dataType = "";
		ArrayList<String> arrayException = new ArrayList<String>();

		String msgKey = messageServerConfig.getMessage(key);

		if (con.has(RuleConstant.K_DATA_TYPE)) { // mandatory
			dataType = con.get(RuleConstant.K_DATA_TYPE).asText("");

			if (!(dataType.equals(RuleConstant.V_STRING) || dataType.equals(RuleConstant.V_NUMBER) || dataType.equals(RuleConstant.V_SHORT) || dataType.equals(RuleConstant.V_INTEGER) || dataType.equals(RuleConstant.V_LONG)
					|| dataType.equals(RuleConstant.V_FLOAT) || dataType.equals(RuleConstant.V_DOUBLE) || dataType.equals(RuleConstant.V_FLOAT) || dataType.equals(RuleConstant.V_BOOLEAN) || dataType.equals(RuleConstant.V_BIGDECIMAL)
					|| dataType.equals(RuleConstant.V_DATE) || dataType.equals(RuleConstant.V_OBJECT) || dataType.equals(RuleConstant.V_ARRAY))) {
				arrayException.add(RuleConstant.K_DATA_TYPE);
				log.debug("config_file_invalid_value:{}, {}", key, dataType);
				throw new BizException("config_file_invalid_value", arrayException);
			}
		} else { //
			arrayException.add(RuleConstant.K_DATA_TYPE);
			log.debug("config_file_empty_key:{}, {}", key, dataType);
			throw new BizException("config_file_empty_key", arrayException);
		}

		if (dataType.equals(RuleConstant.V_ARRAY)) {
			if (!(val instanceof List)) {
				//// arrayException.add(key);
				arrayException.add(getMessageKey(key, msgKey));
				arrayException.add(getMessageKey(key, msgKey));
				arrayException.add(dataType);
				log.debug("abnormal_format:{}, {}", key, dataType);
				throw new BizException("abnormal_format", arrayException);
			}
		} else if (dataType.equals(RuleConstant.V_OBJECT)) {
			if (!(val instanceof Map)) {
				// arrayException.add(key);
				arrayException.add(getMessageKey(key, msgKey));
				arrayException.add(dataType);
				log.debug("abnormal_format:{}, {}", key, dataType);
				throw new BizException("abnormal_format", arrayException);
			}
		}

		Iterator<Entry<String, JsonNode>> keys = con.fields();
		String required = "";
		String comPatternSkip = "N";
		while (keys.hasNext()) {
			Entry<String, JsonNode> entry = keys.next();
			String ymlChildKey = entry.getKey();
			JsonNode ymlChildNode = entry.getValue();
			if (ymlChildKey.equals(RuleConstant.K_COM_PATTERN_CKIP)) {
				comPatternSkip = ymlChildNode.asText();
				if (ymlChildNode.has(RuleConstant.K_COM_PATTERN_CKIP)) {
					comPatternSkip = ymlChildNode.get(RuleConstant.K_COM_PATTERN_CKIP).asText();
				}
			}
		}

		log.debug("=== pattern chk : " + comPatternSkip);

		while (keys.hasNext()) {
			Entry<String, JsonNode> entry = keys.next();
			String ymlChildKey = entry.getKey();
			JsonNode ymlChildNode = entry.getValue();

			if (ymlChildNode.has(RuleConstant.K_REQUIRED_KEY)) {
				required = ymlChildNode.get(RuleConstant.K_REQUIRED_KEY).asText(RuleConstant.V_YES);
			} else {
				required = RuleConstant.V_YES;
			}

			// log.debug("[key: {}, childKey : {}, required:{}, val:{}, nodeType : {}, node
			// : {}]", key, ymlChildKey, required, val, ymlChildNode.getNodeType(),
			// ymlChildNode);

			if (ymlChildNode.getNodeType().equals(JsonNodeType.OBJECT) || ymlChildNode.getNodeType().equals(JsonNodeType.ARRAY)) {
				if (val instanceof Map) {
					if (required.equals(RuleConstant.V_YES)) {
						if (((Map) val).containsKey(ymlChildKey)) {
							// log.debug("[key : {}, childKey : {}, value : {}, node : {}]", key,
							// ymlChildKey, ((Map) val).get(ymlChildKey),ymlChildNode);
							bodyValidCheck(ymlChildKey, ((Map) val).get(ymlChildKey), ymlChildNode);
						} else {
							// log.debug("[key : {}, node : {}]", ymlKey, dataBody.get(ymlKey));
							arrayException = new ArrayList<String>();
							arrayException.add(ymlChildKey);
							log.debug("[empty_key : {}]", ymlChildKey);
							throw new BizException("empty_key", arrayException);
						}
					}
				} else if (val instanceof List) {
					// log.debug("[#### childNode List : {}", mapper.convertValue(ymlChildNode,
					// Map.class));
					if (val == null || ((List) val).size() == 0) {
						// log.debug("[#### null value : {}, {}, {}", val, ymlChildKey, ymlChildNode);
						if (required.equals(RuleConstant.V_YES)) {
							arrayException = new ArrayList<String>();
							arrayException.add(ymlChildKey);
							log.debug("[empty_key : {}]", ymlChildKey);
							throw new BizException("empty_key", arrayException);
						} else {
							bodyValidCheck(ymlChildKey, new HashMap(), ymlChildNode);
						}
					} else {
						for (int i = 0; i < ((List) val).size(); i++) {
							Object childObj = ((List) val).get(i);
							// log.debug("[#### childVal List : {}, childKey : {}", childObj, ymlChildKey);

							if (childObj instanceof Map) {
								if (required.equals(RuleConstant.V_YES)) {
									if (((Map) childObj).containsKey(ymlChildKey)) {
										bodyValidCheck(ymlChildKey, ((Map) childObj).get(ymlChildKey), ymlChildNode);
										// log.debug("[key : {}, childKey : {}, value : {}, node : {}]", key,
										// ymlChildKey, value, ymlChildNode );
									} else {
										// log.debug("[key : {}, node : {}]", ymlKey, dataBody.get(ymlKey));
										arrayException = new ArrayList<String>();
										arrayException.add(ymlChildKey);
										log.debug("[empty_key : {}]", ymlChildKey);
										throw new BizException("empty_key", arrayException);
									}
								}
								// log.debug("[key : {}, childKey : {}, value : {}, node : {}]", key,
								// ymlChildKey, value, ymlChildNode );
							} else {
								bodyValidCheck(ymlChildKey, childObj, ymlChildNode);
								// log.debug("[key : {}, childKey : {}, value : {}, node : {}]", key,
								// ymlChildKey, value, ymlChildNode );
							}
						}
					}
				} else { //
					bodyValidCheck(ymlChildKey, val, ymlChildNode);
				}
			}
		}

		if (con.has(RuleConstant.K_NOT_NULL)) {
			// log.debug("@@@@[key : {}, value : {}, notNull : {} ]", key, value,
			// con.get(K_NOT_NULL));
			String ymlVal = con.get(RuleConstant.K_NOT_NULL).asText();

			if (ymlVal.equals(RuleConstant.V_YES) && isEmpty(val)) {
				// arrayException.add(key);
				arrayException.add(getMessageKey(key, msgKey));
				log.debug("empty_value: {}", key);
				throw new BizException("empty_value", arrayException);
			}
		}

		if (!dataType.equals(RuleConstant.V_ARRAY) && !dataType.equals(RuleConstant.V_OBJECT)) {
			// Special Char Check ----------------------
			if (comPatternSkip.equals("N") && !String.valueOf(val).matches(PERMISSIVE_BODY_CHARS)) {
				// arrayException.add(key);
				arrayException.add(getMessageKey(key, msgKey));
				arrayException.add(String.valueOf(val));
				log.debug("special_char : {}, value : {}, {}", key, val, dataType);
				throw new BizException("special_char", arrayException);
			}

			// XSS Char Check ----------------------
			for (Pattern scriptPattern : xssPatterns) {
				if (scriptPattern.matcher(String.valueOf(val)).find()) {
					// arrayException.add(key);
					arrayException.add(getMessageKey(key, msgKey));
					arrayException.add(String.valueOf(val));
					throw new BizException("invalid_xss", arrayException);
				}
			}
		}

		if (dataType.equals(RuleConstant.V_STRING) || dataType.equals(RuleConstant.V_NUMBER)) {
			if (con.has(RuleConstant.K_LENGTH)) {
				String ymlVal = con.get(RuleConstant.K_LENGTH).asText();

				if (!isLength(val, Integer.parseInt(ymlVal))) {
					// arrayException.add(key);
					arrayException.add(getMessageKey(key, msgKey));
					arrayException.add(ymlVal);
					log.debug("wrong_length:{}", key);
					throw new BizException("wrong_length", arrayException);
				}
			}
			if (con.has(RuleConstant.K_MIN_LENGTH)) {
				String ymlVal = con.get(RuleConstant.K_MIN_LENGTH).asText();

				if (!isMinLength(val, Integer.parseInt(ymlVal))) {
					// arrayException.add(key);
					arrayException.add(ymlVal);
					log.debug("too_short:{}", key);
					throw new BizException("too_short", arrayException);
				}
			}
			if (con.has(RuleConstant.K_MAX_LENGTH)) {
				String ymlVal = con.get(RuleConstant.K_MAX_LENGTH).asText();

				if (!isMaxLength(val, Integer.parseInt(ymlVal))) {
					// arrayException.add(key);
					arrayException.add(getMessageKey(key, msgKey));
					arrayException.add(ymlVal);
					log.debug("too_long:{}", key);
					throw new BizException("too_long", arrayException);
				}
			}

			if (dataType.equals(RuleConstant.V_STRING)) {
				if (con.has(RuleConstant.K_NO_WHITE_SPACE)) {
					String ymlVal = con.get(RuleConstant.K_NO_WHITE_SPACE).asText();

					if (ymlVal.equals(RuleConstant.V_YES) && isWhitespace(val)) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						log.debug("white_space:{}, value:{}", key, val);
						throw new BizException("white_space", arrayException);
					}
				}
				if (con.has(RuleConstant.K_ACCEPT)) {
					if (!isAccept(val, con.get(RuleConstant.K_ACCEPT).asText())) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						log.debug("accept:{}", key);
						throw new BizException("accept", arrayException);
					}
				}
				if (con.has(RuleConstant.K_REJECT)) {
					if (!isReject(val, con.get(RuleConstant.K_REJECT).asText())) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						log.debug("reject:{}", key);
						throw new BizException("reject", arrayException);
					}
				}
			} // END STRING
		} // END STRING, NUMBER

		if (dataType.equals(RuleConstant.V_NUMBER) || dataType.equals(RuleConstant.V_SHORT) || dataType.equals(RuleConstant.V_INTEGER) || dataType.equals(RuleConstant.V_LONG) || dataType.equals(RuleConstant.V_FLOAT)
				|| dataType.equals(RuleConstant.V_DOUBLE) || dataType.equals(RuleConstant.V_BIGDECIMAL)) {
			if (!isNumeric(val)) {
				// arrayException.add(key);
				arrayException.add(getMessageKey(key, msgKey));
				log.debug("not_a_number: {}", key);
				throw new BizException("not_a_number", arrayException);
			}

			if (con.has(RuleConstant.K_MAX_VALUE)) {
				String ymlVal = con.get(RuleConstant.K_MAX_VALUE).asText();

				if (!isMaxValue(val, ymlVal)) {
					// arrayException.add(key);
					arrayException.add(getMessageKey(key, msgKey));
					arrayException.add(ymlVal);
					log.debug("less_than:{}", key);
					throw new BizException("less_than", arrayException);
				}
			}
			if (con.has(RuleConstant.K_MIN_VALUE)) {
				String ymlVal = con.get(RuleConstant.K_MIN_VALUE).asText();

				if (!isMaxValue(val, ymlVal)) {
					// arrayException.add(key);
					arrayException.add(getMessageKey(key, msgKey));
					arrayException.add(ymlVal);
					log.debug("greater_than:{}", key);
					throw new BizException("greater_than", arrayException);
				}
			}
		}

		// BOOLEAN
		if (dataType.equals(RuleConstant.V_BOOLEAN)) {
			if (!isBoolean(val)) {
				// arrayException.add(key);
				arrayException.add(getMessageKey(key, msgKey));
				arrayException.add(RuleConstant.V_BOOLEAN);
				log.debug("abnormal_format:{}", key);
				throw new BizException("abnormal_format", arrayException);
			}
		}

		// DATE
		if (dataType.equals(RuleConstant.V_DATE)) {
			if (con.has(RuleConstant.K_FORMAT)) {
				String ymlVal = con.get(RuleConstant.K_FORMAT).asText();

				if (!isDate(String.valueOf(val), ymlVal)) {
					// arrayException.add(key);
					arrayException.add(getMessageKey(key, msgKey));
					arrayException.add(ymlVal);
					log.debug("abnormal_format:{}", key);
					throw new BizException("abnormal_format", arrayException);
				}
			} else {
				arrayException.add(RuleConstant.K_FORMAT);
				log.debug("abnormal_format:{}, {}", key, RuleConstant.K_FORMAT);
				throw new BizException("abnormal_format", arrayException);
			}
		}
	}

	public void headerValidCheck(String key, Object val, Map<String, Object> con) throws Exception {
		String dataType = "";
		ArrayList<String> arrayException = new ArrayList<String>();
		// log.debug("[key : {}, value : {}, con : {}]", key, val, con);

		String msgKey = messageServerConfig.getMessage(key);

		if (con.containsKey(RuleConstant.K_DATA_TYPE)) { // mandatory
			dataType = (String) defaultIfEmpty(con.get(RuleConstant.K_DATA_TYPE), "");

			if (!(dataType.equals(RuleConstant.V_STRING) || dataType.equals(RuleConstant.V_NUMBER) || dataType.equals(RuleConstant.V_BOOLEAN) || dataType.equals(RuleConstant.V_DATE))) {
				arrayException.add(RuleConstant.K_DATA_TYPE);
				log.debug("config_file_invalid_value:{}, {}", key, dataType);
				throw new BizException("config_file_invalid_value", arrayException);
			}

			if (con.containsKey(RuleConstant.K_NOT_NULL)) {
				String ymlVal = String.valueOf(con.get(RuleConstant.K_NOT_NULL));
				if (ymlVal.equals(RuleConstant.V_YES) && isEmpty(val)) {
					// arrayException.add(key);
					arrayException.add(getMessageKey(key, msgKey));
					log.debug("empty_httpheadervalue: {}", key);
					throw new BizException("empty_httpheadervalue", arrayException);
				}
			}

			// Special Char Check ----------------------
			if (!String.valueOf(val).matches(PERMISSIVE_HEADER_CHARS)) {
				// arrayException.add(key);
				arrayException.add(getMessageKey(key, msgKey));
				arrayException.add(String.valueOf(val));
				throw new BizException("special_char", arrayException);
			}

			// XSS Char Check ----------------------
			for (Pattern scriptPattern : xssPatterns) {
				if (scriptPattern.matcher(String.valueOf(val)).find()) {
					// arrayException.add(key);
					arrayException.add(getMessageKey(key, msgKey));
					arrayException.add(String.valueOf(val));
					throw new BizException("invalid_xss", arrayException);
				}
			}

			// STRING, NUMBER
			if (dataType.equals(RuleConstant.V_STRING) || dataType.equals(RuleConstant.V_NUMBER)) {
				if (con.containsKey(RuleConstant.K_LENGTH)) {
					String ymlVal = String.valueOf(con.get(RuleConstant.K_LENGTH));

					if (!isLength(val, Integer.parseInt(ymlVal))) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						arrayException.add(ymlVal);
						log.debug("wrong_length_httpheadervalue: {}", key);
						throw new BizException("wrong_length_httpheadervalue", arrayException);
					}
				}
				if (con.containsKey(RuleConstant.K_MIN_LENGTH)) {
					String ymlVal = String.valueOf(con.get(RuleConstant.K_MIN_LENGTH));

					if (!isMinLength(val, Integer.parseInt(ymlVal))) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						arrayException.add(ymlVal);
						log.debug("too_short_httpheadervalue: {}", key);
						throw new BizException("too_short_httpheadervalue", arrayException);
					}
				}
				if (con.containsKey(RuleConstant.K_MAX_LENGTH)) {
					String ymlVal = String.valueOf(con.get(RuleConstant.K_MAX_LENGTH));

					if (!isMaxLength(val, Integer.parseInt(ymlVal))) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						arrayException.add(ymlVal);
						log.debug("too_long_httpheadervalue: {}", key);
						throw new BizException("too_long_httpheadervalue", arrayException);
					}
				}

				if (dataType.equals(RuleConstant.V_STRING)) {
					if (con.containsKey(RuleConstant.K_NO_WHITE_SPACE)) {
						String ymlVal = String.valueOf(con.get(RuleConstant.K_NO_WHITE_SPACE));
						if (ymlVal.equals(RuleConstant.V_YES) && isWhitespace(val)) {
							// arrayException.add(key);
							arrayException.add(getMessageKey(key, msgKey));
							log.debug("white_space_httpheadervalue: {}", key);
							throw new BizException("white_space_httpheadervalue", arrayException);
						}
					}
					if (con.containsKey(RuleConstant.K_ACCEPT)) {
						if (!isAccept(val, String.valueOf(con.get(RuleConstant.K_ACCEPT)))) {
							// arrayException.add(key);
							arrayException.add(getMessageKey(key, msgKey));
							log.debug("accept_httpheadervalue: {}", key);
							throw new BizException("accept_httpheadervalue", arrayException);
						}
					}
					if (con.containsKey(RuleConstant.K_REJECT)) {
						if (!isReject(val, String.valueOf(con.get(RuleConstant.K_REJECT)))) {
							// arrayException.add(key);
							arrayException.add(getMessageKey(key, msgKey));
							log.debug("reject_httpheadervalue: {}", key);
							throw new BizException("reject_httpheadervalue", arrayException);
						}
					}
				} else { // NUMBER
					if (!isNumeric(val)) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						log.debug("not_a_number_httpheadervalue: {}", key);
						throw new BizException("not_a_number_httpheadervalue", arrayException);
					}

					if (con.containsKey(RuleConstant.K_MAX_VALUE)) {
						String ymlVal = String.valueOf(con.get(RuleConstant.K_MAX_VALUE));

						if (!isMaxValue(val, ymlVal)) {
							// arrayException.add(key);
							arrayException.add(getMessageKey(key, msgKey));
							arrayException.add(ymlVal);
							log.debug("less_than_httpheadervalue", key);
							throw new BizException("less_than_httpheadervalue", arrayException);
						}
					}
					if (con.containsKey(RuleConstant.K_MIN_VALUE)) {
						String ymlVal = String.valueOf(con.get(RuleConstant.K_MIN_VALUE));

						if (!isMaxValue(val, ymlVal)) {
							// arrayException.add(key);
							arrayException.add(getMessageKey(key, msgKey));
							arrayException.add(ymlVal);
							log.debug("greater_than_httpheadervalue", key);
							throw new BizException("greater_than_httpheadervalue", arrayException);
						}
					}
				}
			}

			// BOOLEAN
			if (dataType.equals(RuleConstant.V_BOOLEAN)) {
				if (!isBoolean(val)) {
					// arrayException.add(key);
					arrayException.add(getMessageKey(key, msgKey));
					arrayException.add(RuleConstant.V_BOOLEAN);
					log.debug("abnormal_format_httpheadervalue", key);
					throw new BizException("abnormal_format_httpheadervalue", arrayException);
				}
			}

			// DATE
			if (dataType.equals(RuleConstant.V_DATE)) {
				if (con.containsKey(RuleConstant.K_FORMAT)) {
					String ymlVal = String.valueOf(con.get(RuleConstant.K_FORMAT));

					if (!isDate(String.valueOf(val), ymlVal)) {
						// arrayException.add(key);
						arrayException.add(getMessageKey(key, msgKey));
						arrayException.add(ymlVal);
						log.debug("abnormal_format_httpheadervalue", key);
						throw new BizException("abnormal_format_httpheadervalue", arrayException);
					}
				} else {
					arrayException.add(RuleConstant.K_FORMAT);
					log.debug("abnormal_format:{}, {}", key, RuleConstant.K_FORMAT);
					throw new BizException("abnormal_format", arrayException);
				}
			}
		} else {
			arrayException.add(RuleConstant.K_DATA_TYPE);
			log.debug("config_file_empty_key:{}, {}", key, dataType);
			throw new BizException("config_file_empty_key", arrayException);
		}
	}

	/**
	 * Header Casting
	 * 
	 * @param obj
	 * @param type
	 * @return
	 */
	public Object getHeaderCastingValue(Object obj, String type) {
		Object rtn;

		if (type.equals(RuleConstant.V_NUMBER) || type.equals(RuleConstant.V_DATE) || type.equals(RuleConstant.V_STRING)) {
			rtn = String.valueOf(obj);
		} else {
			rtn = obj;
		}
		return rtn;
	}

	/**
	 * Body Casting
	 * 
	 * @param obj
	 * @param type
	 * @return
	 */
	public Object getBodyCastingValue(Object obj, String type) {
		Object rtn;

		if (type.equals(RuleConstant.V_SHORT)) {
			rtn = obj;
		} else if (type.equals(RuleConstant.V_INTEGER)) {
			rtn = obj;
		} else if (type.equals(RuleConstant.V_LONG)) {
			rtn = obj;
		} else if (type.equals(RuleConstant.V_FLOAT)) {
			rtn = obj;
		} else if (type.equals(RuleConstant.V_DOUBLE)) {
			rtn = obj;
		} else if (type.equals(RuleConstant.V_BIGDECIMAL)) {
			rtn = obj;
		} else if (type.equals(RuleConstant.V_BOOLEAN)) {
			rtn = Boolean.valueOf(obj.toString());
		} else if (type.equals(RuleConstant.V_NUMBER) || type.equals(RuleConstant.V_DATE) || type.equals(RuleConstant.V_STRING)) {
			rtn = String.valueOf(obj);
		} else {
			rtn = obj;
		}
		return rtn;
	}

	/**
	 * get validation config
	 * 
	 * @param requestUri
	 * @return
	 */
	public Map<String, Object> getValidConfig(ComInfoDto commonInfo) throws Exception {
		Map<String, Object> confMap = null;
		String ymlName = commonInfo.getRequestUri();
		String dbYmlName = "";
		String contextPath = env.getProperty("server.servlet.context-path");

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
		if (commonInfo.getVname() != null && commonInfo.getVname().length() > 0)
			ymlName = commonInfo.getVname() + "." + ymlName;
		else if (commonInfo.getHeader().get("COL_TYPE") != null && commonInfo.getHeader().get("CMD_TYPE").length() > 0 && commonInfo.getHeader().get("COL_TYPE").length() > 0)
			ymlName = commonInfo.getHeader().get("DEV_SEQ") + "." + commonInfo.getHeader().get("CMD_TYPE").toUpperCase() + "." + commonInfo.getHeader().get("COL_TYPE") + "." + ymlName;

		confMap = (Map<String, Object>) validationConfig.getValidationInfo().get(ymlName);

		// rule.validation 경로에 없을시 DB 조회
		if (confMap == null) {
			log.info("============ Not ValidationInfoConfig (DB Search)============");
			ValidationDTO param = new ValidationDTO();
			String uuid = "";
			if (commonInfo.getHeader().get("DEV_UUID") == null || commonInfo.getHeader().get("DEV_UUID").isEmpty()) {
				uuid = null;
			} else
				uuid = commonInfo.getHeader().get("DEV_UUID");
			param.setUuid(uuid);
			param.setHttpUri("/" + dbYmlName);

			confMap = getParserInfo(param);

			validationConfig.setValidationInfoKeyAdd(ymlName, confMap);
		}

		return confMap;
	}

	/**
	 * get RequestParser config
	 * 
	 * @param requestUri
	 * @return
	 */
	@SuppressWarnings("null")
	@Transactional
	public Map<String, Object> getParserInfo(ValidationDTO validationDTO) throws Exception {
//		List<RuleDTO> rulelist = ruleDAO.getRuleList(ruleDTO);
		List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();

		try {
			String dbValidationChk = env.getProperty("myconfig.dbValidationChk");

			if (dbValidationChk == null || dbValidationChk.equals("Y")) {
				maplist = validationDAO.getValidationRules(validationDTO);
			} else {
				return null;
			}
		} catch (NullPointerException ex) {
			return null;
		}

		if (maplist == null)
			return null;

		Map<String, Object> confMap = new HashMap<String, Object>();
		Map<String, Object> headerList = new HashMap<String, Object>();
		Map<String, Object> bodyList = new HashMap<String, Object>();
//		Map<String, Object> queryList = new HashMap<String, Object>();

		for (int idx = 0; idx < maplist.size(); idx++) {
			Map<String, Object> map = maplist.get(idx);

			Map<String, Object> conversion = new HashMap<String, Object>();
//			String rule_kind = (String)map.get("RULE_KIND");		// HTTP_HEADER, HTTP_BODY, QUERY_STRING
			String rule_kind = (String) map.get("RULE_KIND");
			String rule_name = (String) map.get("VAL_RULE_NAME"); // Content-Type
			String dataType = (String) map.get("DATATYPE");

			conversion.put("dataType", dataType);
			conversion.put("requiredKey", map.get("DEFAULTYN"));
			conversion.put("notNull", map.get("NULLYN"));

			if (map.get("EMPTYYN") != null && !map.get("EMPTYYN").toString().isEmpty()) {
				conversion.put("noWhitespace", map.get("EMPTYYN"));
			}

			if (map.get("DATASIZE") != null && !map.get("DATASIZE").toString().isEmpty()) {
				conversion.put("length", map.get("DATASIZE"));
			}

			if (map.get("DATAMIN") != null && !map.get("DATAMIN").toString().isEmpty()) {
				if (dataType.contains("STRING"))
					conversion.put("minLength", map.get("DATAMIN"));
				else
					conversion.put("minValue", map.get("DATAMIN"));
			}

			if (map.get("DATAMAX") != null && !map.get("DATAMAX").toString().isEmpty()) {
				if (dataType.contains("STRING"))
					conversion.put("maxLength", map.get("DATAMAX"));
				else
					conversion.put("maxValue", map.get("DATAMAX"));
			}

			if (map.get("ALLOW_REGEX") != null && !map.get("ALLOW_REGEX").toString().isEmpty()) {
				conversion.put("accept", map.get("ALLOW_REGEX"));
			}

			if (map.get("NOTALLOW_REGEX") != null && !map.get("NOTALLOW_REGEX").toString().isEmpty()) {
				conversion.put("reject", map.get("NOTALLOW_REGEX"));
			}

			Object object = null;

			if (rule_kind.equals("HTTP_HEADER")) {
				if (confMap.containsKey("HTTP_HEADER")) {
					object = confMap.get("HTTP_HEADER");
					object = getValidationInfo(rule_name, conversion, object);
					confMap.put("HTTP_HEADER", object);
				} else {
					confMap.put("HTTP_HEADER", "");
					object = confMap.get("HTTP_HEADER");
					object = getValidationInfo(rule_name, conversion, object);
					confMap.put("HTTP_HEADER", object);
				}
			} else if (rule_kind.equals("HTTP_BODY")) {
				if (confMap.containsKey("HTTP_BODY")) {
					object = confMap.get("HTTP_BODY");
					object = getValidationInfo(rule_name, conversion, object);
					confMap.put("HTTP_BODY", object);
				} else {
					confMap.put("HTTP_BODY", "");
					object = confMap.get("HTTP_BODY");
					object = getValidationInfo(rule_name, conversion, object);
					confMap.put("HTTP_BODY", object);
				}
			}
		}

		if (!confMap.containsKey("HTTP_HEADER")) {
			confMap.put("HTTP_HEADER", headerList);
		}

		if (!confMap.containsKey("HTTP_BODY")) {
			confMap.put("HTTP_BODY", bodyList);
		}

		return confMap;
	}

	private Object getValidationInfo(String ruleName, Map<String, Object> conversion, Object confMap) {
		String rule_name = "";
//		Map<String, Object> temp = new HashMap<String, Object>();
//		Map<String, Object> headerList = new HashMap<String, Object>();
//		Map<String, Object> bodyList = new HashMap<String, Object>();
		Object object;

		if (ruleName != null && ruleName.length() > 0) {
			if (ruleName.indexOf(".") > -1) {
				rule_name = ruleName.substring(0, ruleName.lastIndexOf("."));
				if (confMap.getClass() == HashMap.class && ((Map<String, Object>) confMap).containsKey(rule_name)) {
					object = ((Map<String, Object>) confMap).get(rule_name);
					object = getValidationInfo(ruleName.substring(ruleName.lastIndexOf(".") + 1, ruleName.length()), conversion, object);
					((Map<String, Object>) confMap).put(rule_name, object);
				} else {
					Map<String, Object> temp = new HashMap<String, Object>();
					temp.put(rule_name, "");
					object = (temp).get(rule_name);
					object = getValidationInfo(ruleName.substring(ruleName.lastIndexOf(".") + 1, ruleName.length()), conversion, object);
					temp.put(rule_name, object);
					confMap = temp;
				}
			} else {
				rule_name = ruleName;
				if (confMap.getClass() == HashMap.class) {
					if (((Map<String, Object>) confMap).containsKey(rule_name)) {
						Map<String, Object> next = (Map<String, Object>) ((Map<String, Object>) confMap).get(rule_name);
						Iterator<Entry<String, Object>> entries = conversion.entrySet().iterator();
						while (entries.hasNext()) {
							Entry<String, Object> entry = entries.next();
							next.put(entry.getKey(), entry.getValue());
						}
						((Map<String, Object>) confMap).put(rule_name, next);
					} else {
						((Map<String, Object>) confMap).put(rule_name, conversion);
					}
				} else {
					Map<String, Object> temp = new HashMap<String, Object>();
					temp.put(rule_name, conversion);
					confMap = temp;
				}
			}
		}

		return confMap;
	}

	@SuppressWarnings("unused")
	private Map<String, Object> setValidationInfo(String rule_name, Map<String, Object> temp, Map<String, Object> validation) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (rule_name != null && rule_name.length() > 0) {
			if (rule_name.indexOf(".") > -1) {
				rule_name = rule_name.substring(0, rule_name.lastIndexOf("."));

				if (validation.containsKey(rule_name))
					result = setValidationInfo(rule_name.substring(rule_name.lastIndexOf(".") + 1, rule_name.length()), (Map<String, Object>) temp.get(rule_name), (Map<String, Object>) validation.get(rule_name));

				else {
					validation.put(rule_name, temp.get(rule_name));
					result = validation;
				}
			} else {
				if (validation.containsKey(rule_name)) {
					validation.replace(rule_name, temp.get(rule_name));
					result = validation;
				} else {
					validation.put(rule_name, temp.get(rule_name));
					result = validation;
				}
			}
		}

		return result;
	}

	/**
	 * null 또는 "" 빈문자열인지 여부
	 * 
	 * @param val
	 * @return
	 */
	public boolean isEmpty(Object val) {
		boolean rtn;
		if (val == null) {
			rtn = true;
		} else {
			rtn = StringUtils.isEmpty(String.valueOf(val));
		}
		return rtn;
	}

	/**
	 * whitespace 가 있는지 여부
	 * 
	 * @param val
	 * @return
	 */
	public boolean isWhitespace(Object val) {
		boolean rtn;
		if (isEmpty(val)) {
			rtn = true;
		} else {
			if (String.valueOf(val).indexOf(StringUtils.SPACE) == -1) {
				rtn = false;
			} else {
				rtn = true;
			}
		}
		return rtn;
	}

	/**
	 * 값이 비어있으면 default 값 리턴
	 * 
	 * @param obj
	 * @param defaultVal
	 * @return
	 */
	public Object defaultIfEmpty(Object obj, Object defaultVal) {
		Object rtn;
		if (isEmpty(obj)) {
			rtn = defaultVal;
		} else {
			rtn = obj;
		}
		return rtn;
	}

	/**
	 * 값의 길이가 일치하는지 여부
	 * 
	 * @param val
	 * @param len
	 * @return
	 */
	public boolean isLength(Object val, int len) {
		boolean rtn;
		if (isEmpty(val)) {
			rtn = false;
		} else {
			if (String.valueOf(val).length() == len) {
				rtn = true;
			} else {
				rtn = false;
			}
		}
		return rtn;
	}

	/**
	 * 값이 최소 길이보다 작은지 여부 (작으면 false)
	 * 
	 * @param val
	 * @param min
	 * @return
	 */
	public boolean isMinLength(Object val, int min) {
		boolean rtn;
		if (isEmpty(val)) {
			rtn = false;
		} else {
			if (String.valueOf(val).getBytes().length < min) {
				rtn = false;
			} else {
				rtn = true;
			}
		}
		return rtn;
	}

	/**
	 * 값이 최대 길이보다 큰지 여부 (크면 false)
	 * 
	 * @param val
	 * @param max
	 * @return
	 */
	public boolean isMaxLength(Object val, int max) {
		boolean rtn;
		if (isEmpty(val)) {
			// rtn = true;
			rtn = false;
		} else {
			if (String.valueOf(val).getBytes().length > max) {
				rtn = false;
			} else {
				rtn = true;
			}
		}
		return rtn;
	}

	/**
	 * 값이 최소값 보다 작은지 비교 (작으면 false)
	 * 
	 * @param val
	 * @param min
	 * @return
	 */
	public boolean isMinValue(Object val, Object min) {
		boolean rtn;
		if (isEmpty(val)) {
			rtn = false;
		} else {
			try {
				if (NumberUtils.createNumber(String.valueOf(val)).doubleValue() < NumberUtils.createNumber(String.valueOf(min)).doubleValue()) {
					rtn = false;
				} else {
					rtn = true;
				}
			} catch (NumberFormatException e) {
				rtn = false;
			}
		}
		return rtn;
	}

	/**
	 * 값이 최대값 보다 큰지 비교 (크면 false)
	 * 
	 * @param val
	 * @param max
	 * @return
	 */
	public boolean isMaxValue(Object val, Object max) {
		boolean rtn;
		if (isEmpty(val)) {
			rtn = false;
		} else {
			try {
				if (NumberUtils.createNumber(String.valueOf(val)).doubleValue() > NumberUtils.createNumber(String.valueOf(max)).doubleValue()) {
					rtn = false;
				} else {
					rtn = true;
				}
			} catch (NumberFormatException e) {
				rtn = false;
			}
		}
		return rtn;
	}

	/**
	 * 값이 정규식 패턴과 일치하는지 여부
	 * 
	 * @param val
	 * @param regex
	 * @return
	 */
	public boolean isAccept(Object val, String regex) {
		boolean rtn;
		if (isEmpty(val)) {
			rtn = false;
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(String.valueOf(val));
			if (m.matches()) {
				rtn = true;
			} else {
				rtn = false;
			}
		}
		return rtn;
	}

	/**
	 * 값이 정규식 패턴과 일치하지 않는지 여부
	 * 
	 * @param val
	 * @param regex
	 * @return
	 */
	public boolean isReject(Object val, String regex) {
		boolean rtn;
		if (isEmpty(val)) {
			rtn = true;
		} else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(String.valueOf(val));
			if (m.matches()) {
				rtn = false;
			} else {
				rtn = true;
			}
		}
		return rtn;
	}

	/**
	 * 숫자형인지 여부
	 * 
	 * @param val
	 * @return
	 */
	public boolean isNumeric(Object val) {
		boolean rtn;
		if (isEmpty(val)) {
			rtn = false;
		} else {
			rtn = NumberUtils.isCreatable(String.valueOf(val));
		}
		return rtn;
	}

	/**
	 * 문자열이, 날짜 포맷과 일치하는지 여부
	 * 
	 * @param val
	 * @param format
	 * @return
	 */
	public boolean isDate(String val, String format) {
		boolean rtn;
		if (isEmpty(val)) {
			rtn = false;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(format);

			try {
				String parseDate = sdf.format(sdf.parse(val));
				if (parseDate.equals(val)) {
					rtn = true;
				} else {
					rtn = false;
				}
			} catch (ParseException e) {
				rtn = false;
			}
		}
		return rtn;
	}

	/**
	 * Boolean형인지 여부
	 * 
	 * @param val
	 * @return
	 */
	public boolean isBoolean(Object val) {
		boolean rtn;
		if (isEmpty(val)) {
			rtn = true;
		} else {
			if (val instanceof Boolean) {
				rtn = true;
			} else {
				rtn = false;
			}
		}
		return rtn;
	}

	public Pattern[] xssPatterns = new Pattern[] {
			// Script fragments
			Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
			// src='...'
			Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL), Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			// lonely script tags
			Pattern.compile("</script>", Pattern.CASE_INSENSITIVE), Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			// eval(...)
			Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			// expression(...)
			Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			// javascript:...
			Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
			// vbscript:...
			Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
			// onload(...)=...
			Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			// char
			Pattern.compile("<", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL), Pattern.compile(">", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
			Pattern.compile("&", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL) };

	private String getMessageKey(String key, String msgKey) {
		String rnMsg = "";
		if (SERVERMSG_YN.equals("Y")) {
			if (StringUtils.isEmpty(msgKey)) {
				rnMsg = key;
			} else {
				rnMsg = msgKey;
			}
		} else {
			rnMsg = key;
		}

		return rnMsg;
	}
}
