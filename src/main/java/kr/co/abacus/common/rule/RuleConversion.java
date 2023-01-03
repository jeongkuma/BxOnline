package kr.co.abacus.common.rule;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.rule.annotation.Rule;
import kr.co.abacus.common.scriptEngine.NashornEngine;
import kr.co.abacus.common.util.JsonUtils;
import kr.co.abacus.common.util.ReflectionUtils;
import kr.co.abacus.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "rawtypes", "unused" })
@Slf4j
@Component
public class RuleConversion {


	@Autowired
	private NashornEngine nashornEngine;

	@Autowired
	private ObjectMapper mapper;

	private ArrayList<String> arrayException;

	final private String JSON_POINTER_SEPARATOR = String.valueOf(JsonPointer.SEPARATOR);

	/**
	 * convert value
	 *
	 * @param type
	 * @param filename
	 * @param functionName
	 * @param returnType
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Object convertValue(String type, String filename, String functionName, Object... parameter) throws Exception {
		Object val = null;

		try {
//			log.debug("===  ruleConversion.convertValue :: type :: " + type + " :: filename ||  " + filename + " :: functionName ::" + functionName);
//			log.debug("===  ruleConversion.convertValue :: parameter :: " + parameter.toString());

//			for (int i = 0 ; i < parameter.length; i++) {
//				Object tobj = parameter[i];
//				log.debug("===  ruleConversion.convertValue :: tobj :: " + tobj.toString());
//
//			}
			if (type.equals(RuleConstant.V_JAVASCRIPT)) {
				val = nashornEngine.runScript(filename, functionName, parameter);
			} else if (type.equals(RuleConstant.V_JAVA)) {
				val = callMethod(filename, functionName, parameter);
			} else if (type.equals(RuleConstant.V_RAW)) {
				val = parameter[0];
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return val;
	}

	/**
	 * call mathod
	 *
	 * @param className
	 * @param methodName
	 * @param args
	 * @return
	 */
	private Object callMethod(String className, String methodName, Object... args) {
		Object val = null;
		Object obj = null;
		Class<?>[] param = null;
		Class<?> target = null;
		boolean check = false;

//		log.debug("className : " + className);
//		log.debug("methodName : " + methodName);
		try {
			target = Class.forName(className);
			obj = new Object();
			Annotation[] annotations = target.getAnnotations();

			for (Annotation annotation : annotations) {
				if (annotation instanceof Rule) {
					check = true;
				}
			}

			if (check == false) {
				throw new ClassNotFoundException(className);
			}

			if (args != null) {
				param = new Class<?>[args.length];
				for (int i = 0; i < args.length; i++) {
					param[i] = args[i].getClass();
				}
			}
			Method method = ReflectionUtils.findMethod(target, methodName, param);

			if (method == null) {
				throw new NoSuchMethodException(methodName);
			}

			val = ReflectionUtils.invokeMethod(method, obj, args);
		} catch (IllegalArgumentException e) {
			throw new BizException(e, "java.lang.IllegalArgumentException");
		} catch (NoSuchMethodException e) {
			throw new BizException(e, "java.lang.NoSuchMethodException");
		} catch (ClassNotFoundException e) {
			throw new BizException(e, "java.lang.ClassNotFoundException");
		}

		return val;
	}

	/**
	 * set arguments
	 *
	 * @param arg
	 * @return
	 */
	public String[] setArgs(String arg) {
		String[] args = null;
		String[] rtnArgs = null;
		List<String> list = null;
		String params = "";
		boolean chk = true;

		args = arg.replaceAll(StringUtils.SPACE, StringUtils.EMPTY).split(RuleConstant.COMMA);

		if (arg.lastIndexOf(RuleConstant.V_R_MTL_K) > 0) {
			list = new ArrayList<String>();

			for (String param : args) {
				if (chk) {
					list.add(param.toString());
					if (param.toString().startsWith(RuleConstant.V_R_MTL_K)) {
						chk = false;
					}
				} else {
					params = params + RuleConstant.COMMA + param;
				}
			}
			if (params.length() > 0) {
				list.add(params.substring(1));
			}
			rtnArgs = new String[list.size()];
			list.toArray(rtnArgs);
		} else {
			rtnArgs = args;
		}

		return rtnArgs;
	}

	/**
	 * set arguments
	 *
	 * @param argKeys
	 * @param commonInfo
	 * @return
	 * @throws Exception
	 */
	public Object[] setArgs(String[] argKeys, ComInfoDto commonInfo) throws Exception {
		Object[] rtn = null;
		String argKey = "";
		JsonNode argNode = null;

		if (argKeys != null) {
			rtn = new Object[argKeys.length];
			for (int i = 0; i < argKeys.length; i++) {
				if (argKeys[i].startsWith(RuleConstant.V_R_MTL_K)) {
					rtn[i] = argKeys[i].substring(RuleConstant.V_O_REQ_H.length() + 1);
				} else if (argKeys[i].indexOf(RuleConstant.COMMA) > 0) {
					rtn[i] = argKeys[i].split(RuleConstant.COMMA);
				} else if (argKeys[i].startsWith(RuleConstant.V_O_REQ_H)) {
					argKey = argKeys[i].toLowerCase().substring(RuleConstant.V_O_REQ_H.length() + 1);
					if (commonInfo.getHeader().containsKey(argKey)) {
						rtn[i] = commonInfo.getHeader().get(argKey);
					} else {
						arrayException = new ArrayList<String>();
						arrayException.add(argKeys[i]);
						log.debug("[empty_key : {}]", argKeys[i]);
						throw new BizException("empty_key", arrayException);
					}
				} else if (argKeys[i].startsWith(RuleConstant.V_O_REQ_Q)) {
					argKey = argKeys[i].substring(RuleConstant.V_O_REQ_Q.length() + 1);
					if (commonInfo.getQueryString().containsKey(argKey)) {
						rtn[i] = commonInfo.getQueryString().get(argKey);
					} else {
						arrayException = new ArrayList<String>();
						arrayException.add(argKeys[i]);
						log.debug("[empty_key : {}]", argKeys[i]);
						throw new BizException("empty_key", arrayException);
					}
				} else if (argKeys[i].startsWith(RuleConstant.V_O_REQ_B)) {
					argKey = argKeys[i].substring(RuleConstant.V_O_REQ_B.length() + 1);

					if(argKey.startsWith("$")) {
						argNode = JsonUtils.getFindPathValues(argKey, commonInfo.getBodyJson());
					} else {
						argNode = JsonUtils.getFindValue(argKey, commonInfo.getBodyJson());
					}

					if (!argNode.isMissingNode()) {
						if (argNode.isTextual()) {
							rtn[i] = argNode.asText();
						} else {
							rtn[i] = argNode.toString();
						}
					} else {
						arrayException = new ArrayList<String>();
						arrayException.add(argKeys[i]);
						log.debug("[empty_key : {}]", argKeys[i]);
						throw new BizException("empty_key", arrayException);
					}
				} else if (argKeys[i].startsWith(RuleConstant.V_P_REQ_H)) {
					argKey = argKeys[i].toLowerCase().substring(RuleConstant.V_P_REQ_H.length() + 1);
					if (commonInfo.getParsedHeader().containsKey(argKey)) {
						rtn[i] = commonInfo.getParsedHeader().get(argKey);
					} else {
						arrayException = new ArrayList<String>();
						arrayException.add(argKeys[i]);
						log.debug("[empty_key : {}]", argKeys[i]);
						throw new BizException("empty_key", arrayException);
					}
				} else if (argKeys[i].startsWith(RuleConstant.V_P_REQ_Q)) {
					argKey = argKeys[i].substring(RuleConstant.V_P_REQ_Q.length() + 1);
					if (commonInfo.getParsedQueryString().containsKey(argKey)) {
						rtn[i] = commonInfo.getParsedQueryString().get(argKey);
					} else {
						arrayException = new ArrayList<String>();
						arrayException.add(argKeys[i]);
						log.debug("[empty_key : {}]", argKeys[i]);
						throw new BizException("empty_key", arrayException);
					}
				} else if (argKeys[i].startsWith(RuleConstant.V_P_REQ_B)) {
					argKey = argKeys[i].substring(RuleConstant.V_P_REQ_B.length() + 1);

					if(argKey.startsWith("$")) {
						argNode = JsonUtils.getFindPathValues(argKey, commonInfo.getBodyJson());
					}else {
						argNode = JsonUtils.getFindValue(argKey, commonInfo.getBodyJson());
					}

					if (!argNode.isMissingNode()) {
						if (argNode.isTextual()) {
							rtn[i] = argNode.asText();
						} else {
							rtn[i] = argNode.toString();
						}
					} else {
						arrayException = new ArrayList<String>();
						arrayException.add(argKeys[i]);
						log.debug("[empty_key : {}]", argKeys[i]);
						throw new BizException("empty_key", arrayException);
					}
				} else {
					rtn[i] = argKeys[i];
				}
			}
		}

		return rtn;
	}

	/**
	 * set Json Value
	 *
	 * @param node
	 * @param targetKey
	 * @param val
	 */
	public void setJsonValue(JsonNode node, String targetKey, Object val) {
		String[] keys = targetKey.split("[.]");
		String lastKey = targetKey;
		String path = JSON_POINTER_SEPARATOR;
		JsonNode childNode = null;
		JsonNode parentNode = node.at(path);
		JsonNode setNode = null;

		for (int i = 0; i < keys.length; i++) {
			lastKey = keys[i];
			path += keys[i];
			childNode = node.at(path);

			if (parentNode.isMissingNode()) {
				setNode = node;
			} else {
				setNode = parentNode;
			}

			if (childNode.isMissingNode()) {
				if (i < keys.length - 1) {
					childNode = ((ObjectNode) setNode).putObject(keys[i]);
				}
			}

			path += JSON_POINTER_SEPARATOR;
			parentNode = childNode;
		}

		if (val instanceof Short) {
			((ObjectNode) setNode).put(lastKey, (Short) val);
		} else if (val instanceof Integer) {
			((ObjectNode) setNode).put(lastKey, (Integer) val);
		} else if (val instanceof Long) {
			((ObjectNode) setNode).put(lastKey, (Long) val);
		} else if (val instanceof Float) {
			((ObjectNode) setNode).put(lastKey, (Float) val);
		} else if (val instanceof Double) {
			((ObjectNode) setNode).put(lastKey, (Double) val);
		} else if (val instanceof BigDecimal) {
			((ObjectNode) setNode).put(lastKey, (BigDecimal) val);
		} else if (val instanceof BigInteger) {
			((ObjectNode) setNode).put(lastKey, (BigInteger) val);
		} else if (val instanceof String) {
			((ObjectNode) setNode).put(lastKey, (String) val);
		} else if (val instanceof Boolean) {
			((ObjectNode) setNode).put(lastKey, (Boolean) val);
		} else if (val instanceof JsonNode) {
			((ObjectNode) setNode).putPOJO(lastKey, val);
		} else {
			((ObjectNode) setNode).putPOJO(lastKey, mapper.convertValue(val, JsonNode.class));
		}
	}

	/**
	 * get return class type
	 *
	 * @param className
	 * @return
	 * @throws BizException
	 */
	@Deprecated
	private Class getReturnType(String className) throws BizException {
		Class returnType = null;
		String classFullName = "";

		try {
			if (className.equals(RuleConstant.V_STRING)) {
				classFullName = "java.lang.String";
			} else if (className.equals(RuleConstant.V_BOOLEAN)) {
				classFullName = "java.lang.Boolean";
			} else if (className.equals(RuleConstant.V_SHORT)) {
				classFullName = "java.lang.Short";
			} else if (className.equals(RuleConstant.V_INTEGER)) {
				classFullName = "java.lang.Integer";
			} else if (className.equals(RuleConstant.V_LONG)) {
				classFullName = "java.lang.Long";
			} else if (className.equals(RuleConstant.V_FLOAT)) {
				classFullName = "java.lang.Float";
			} else if (className.equals(RuleConstant.V_DOUBLE)) {
				classFullName = "java.lang.Double";
			} else if (className.equals(RuleConstant.V_BIGDECIMAL)) {
				classFullName = "java.math.BigDeciaml";
			} else if (className.equals(RuleConstant.V_JSONNODE)) {
				classFullName = "com.fasterxml.jackson.databind.JsonNode";
			} else {
				classFullName = className;
			}

			returnType = Class.forName(classFullName);
		} catch (ClassNotFoundException e) {
			throw new BizException(e, "java.lang.ClassNotFoundException");
		}

		return returnType;
	}
}
