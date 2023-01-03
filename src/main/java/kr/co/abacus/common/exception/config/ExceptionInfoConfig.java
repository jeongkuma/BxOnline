package kr.co.abacus.common.exception.config;

import com.google.common.collect.Maps;
import kr.co.abacus.common.api.dto.ExceptionDTO;
import kr.co.abacus.common.dto.common.ResultDescDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.exception.ExceptionData;
import kr.co.abacus.common.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName : ExceptionInfoConfig.java
 * @Project : hcfw
 * @Date : 2019. 2. 20.
 * @Author : ASUS
 * @History : 날자 작성자 내용 ---------- ----------- -------------------------------
 * @Description :
 */
@Slf4j
@Getter
@Setter
@Configuration
@ControllerAdvice
public class ExceptionInfoConfig extends ResponseEntityExceptionHandler {

	@Autowired
	private Environment env = null;

	private Map<String, Object> exceptionList = null;
	private ResultDescDto successResultDesc = null;
	private ResultDescDto undefinedErrorResultDesc = null;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void initExceptionInfoConfig() {
		log.debug("ExceptionInfoConfig .... ");
		YamlMapFactoryBean yaml = new YamlMapFactoryBean();
		String exceptionFile = env.getProperty("myconfig.exception");
		try {
			yaml.setResources(new ClassPathResource(exceptionFile));
			this.exceptionList = yaml.getObject();

			Map<String, Object> successInfos = (Map<String, Object>) exceptionList.get("success");
			Map<String, Object> successInfo = (Map<String, Object>) successInfos.get("200ok");

			/*
			 * { "timestamp": 1551282506390, "status": 403, "error": "Forbidden", "message":
			 * "Access Denied", "path": "/green/testErr1" }
			 */
			successResultDesc = new ResultDescDto();
			successResultDesc.setCode(StringUtils.objectIfNullToEmpty(successInfo.get("code")));
			successResultDesc.setDesc(StringUtils.objectIfNullToEmpty(successInfo.get("desc")));
			successResultDesc.setStatus(StringUtils.objectIfNullToEmpty(successInfo.get("status")));
			successResultDesc.setType(StringUtils.objectIfNullToEmpty(successInfo.get("type")));

			Map<String, Object> exceptionInfos = (Map<String, Object>) exceptionList.get("exception");
			Map<String, Object> exceptionInfo = (Map<String, Object>) exceptionInfos.get("notdefine");

			undefinedErrorResultDesc = new ResultDescDto();
			undefinedErrorResultDesc.setCode(StringUtils.objectIfNullToEmpty(exceptionInfo.get("code")));
			undefinedErrorResultDesc.setDesc(StringUtils.objectIfNullToEmpty(exceptionInfo.get("desc")));
			undefinedErrorResultDesc.setStatus(StringUtils.objectIfNullToEmpty(exceptionInfo.get("status")));
			undefinedErrorResultDesc.setType(StringUtils.objectIfNullToEmpty(exceptionInfo.get("type")));
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
		}
	}

	@SuppressWarnings({ "unchecked", "null" })
	public Map<String, Object> getExceptionInfo(String ymlKey) {
		Map<String, Object> exceptionInfos = null;
		Map<String, Object> exceptionInfo = null;

		exceptionInfos = (Map<String, Object>) exceptionList.get("exception");

		exceptionInfo = (Map<String, Object>) exceptionInfos.get(ymlKey);

		if (exceptionInfo == null) {
			exceptionInfo = getList(ymlKey, "N");
			exceptionInfos.put(ymlKey, exceptionInfo);
			exceptionList.replace("exception", exceptionInfos);
		}

		return exceptionInfo;
	}

	public Map<String, Object> getList(String ymlKey, String isSuccess) {
		ExceptionDTO exceptionDTO = new ExceptionDTO();
		exceptionDTO.setIs_success(isSuccess);
		exceptionDTO.setMsg_key(ymlKey);

		List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
		// 미들웨어에서만 사용 하는 것으로 dbParserChk는 선언 되어 있어야 함
		Map<String, Object> msg = new HashMap<String, Object>();

		for (int i=0;i<maplist.size();i++) {
			Map<String, Object> map = maplist.get(i);
//			Map<String, Object> msg = new HashMap<String, Object>();
			msg.put("CODE", map.get("MSG_CODE"));
			msg.put("DESC", map.get("MSG_DESC"));
			msg.put("TYPE", map.get("MSG_TYPE"));
			msg.put("STATUS", map.get("MSG_STATUS"));
		}
		return msg;
	}

	@SuppressWarnings("unchecked")
	public void setExceptionInfo(String ymlKey) {
		Map<String, Object> exceptionInfos = (Map<String, Object>) exceptionList.get("exception");
		Map<String, Object> exceptionInfo = getList(ymlKey, "N");

		exceptionInfos.put(ymlKey, exceptionInfo);
		exceptionList.replace("exception", exceptionInfos);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getSuccessInfo(String ymlKey) {
		Map<String, Object> successInfos = null;
		Map<String, Object> successInfo = null;



		successInfos = (Map<String, Object>) exceptionList.get("success");
		successInfo = (Map<String, Object>) successInfos.get(ymlKey);

		if (successInfo == null) {
			successInfo = getList(ymlKey, "Y");
			successInfos.put(ymlKey, successInfo);
			exceptionList.replace("exception", successInfos);
		}

		return successInfo;
	}

	@SuppressWarnings("unchecked")
	public void setSuccessInfo(String ymlKey) {
		Map<String, Object> successInfos = (Map<String, Object>) exceptionList.get("success");
		Map<String, Object> successInfo = getList(ymlKey, "Y");

		successInfos.put(ymlKey, successInfo);
		exceptionList.replace("success", successInfos);
	}

	@ExceptionHandler({RuntimeException.class})
	public ResponseEntity<?> runtimeExceptionHandler(HttpServletRequest request, Exception exception) throws IOException {

		StringBuilder sb = new StringBuilder();

		Map<String, Object> exceptionMap = (Map<String, Object>) exceptionList.get("exception");

		for (StackTraceElement element : exception.getStackTrace()) {
			sb.append(element.toString());
			sb.append("\n");
		}

		Boolean exceptionBool = false;
		ExceptionData exceptionData = new ExceptionData();

		if (exception instanceof BizException) {
			exceptionBool = true;
		}

		Map<String, Object> errorMap = Maps.newHashMap();

		/** 사용자 정의 에러 인 경우 **/
		if (exceptionBool) {

			String errorCode = "";
			String message = "";

			if (exception instanceof BizException) {
				BizException bizException = (BizException) exception;

				errorCode = bizException.getYmlKey();
				message = bizException.getMessage();
			}

			if (exceptionMap.get(errorCode) != null) {
				errorMap.putAll((Map<String, Object>) exceptionMap.get(errorCode));

			} else {
				errorMap.putAll((Map<String, Object>) exceptionMap.get("ERROR_UNKNOWN"));

//				String className = exception.getClass().getName();
//				String desc = new StringBuilder().append(exceptionData.getDesc()).append("[").append(className)
//						.append("-")
//						.append(errorCode).append("]").toString();
//
//
//				exceptionData.setDesc(desc);
			}
		} else {

			String className = exception.getClass().getName();

			Boolean findFlag = false;

			if(exceptionMap.get(className) != null) {
				findFlag = true;
				errorMap = (Map<String, Object>) exceptionMap.get(className);
			}

			if (!findFlag) {
				errorMap = (Map<String, Object>) exceptionMap.get("ERROR_UNKNOWN");

				String desc = new StringBuilder().append((String) errorMap.get("DESC")).append("[").append(className)
						.append("-")
						.append(exception.getMessage()).append("]").toString();


				exceptionData.setDesc(desc);
			} else {

				errorMap = (Map<String, Object>) exceptionMap.get("ERROR_UNKNOWN");
				errorMap.put("DESC", exception.getMessage());
			}

		}


		log.error("Request: {} {} {}", request.getRequestURL(), errorMap.get("CODE"), sb.toString());
		sb.setLength(0);
		Integer status = (Integer) errorMap.get("status");

		return ResponseEntity.status(status).body(errorMap);

	}
}
