package kr.co.abacus.common.exception;

import kr.co.abacus.common.dto.common.ResultBaseDto;
import kr.co.abacus.common.dto.common.ResultDescDto;
import kr.co.abacus.common.exception.config.ExceptionInfoConfig;
import kr.co.abacus.common.message.MultiLangManager;
import kr.co.abacus.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.UndeclaredThrowableException;
import java.text.MessageFormat;
import java.util.Map;

//@ControllerAdvice
//@Order(Ordered.HIGHEST_PRECEDENCE)

/**
 * @FileName : GlobalExceptionHandler.java
 * @Project : hcfw
 * @Date : 2019. 2. 20.
 * @Author : ASUS
 * @History : 날자 작성자 내용 ---------- ----------- -------------------------------
 * @Description :
 */

@Slf4j
public class GlobalBaseExceptionHandler {

	@Autowired
	private ExceptionInfoConfig exceptionInfoConfig;

	@Autowired
	private MultiLangManager multiLangManager;

	
	
	
	

	@ExceptionHandler(Exception.class)
	public @ResponseBody ResultBaseDto handleException(Exception ex, HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		ResultBaseDto result = new ResultBaseDto();
		ResultDescDto resultDesc = null;
		Throwable throwable = null;
		log.debug("==== handleException ");

		if (ex instanceof UndeclaredThrowableException) {
			UndeclaredThrowableException exception = (UndeclaredThrowableException) ex;
			throwable = exception.getUndeclaredThrowable();
			if (throwable instanceof BizException) {
				BizException bizException = (BizException) throwable;
				String ymlKey = bizException.getYmlKey();
				resultDesc = getResultDescDto(ymlKey);
			}
		} else if (ex instanceof BizException) {
			BizException bizException = (BizException) ex;
			String ymlKey = bizException.getYmlKey();
			if (StringUtils.isEmpty(ymlKey)) {
				// resultDesc = getResultDescDto("notdefine");
				resultDesc = bizException.getRst();
			} else {
				if (bizException.getArrayReplace() != null) {
					resultDesc = getResultDescDto(ymlKey, bizException.getArrayReplace().toArray());
				} else {
					resultDesc = getResultDescDto(ymlKey);
				}
			}
			throwable = bizException.getThrowable();
		} else {
			String errorName = ex.getClass().getName();
			resultDesc = getResultDescDto(errorName);
		}
		result.setResult(resultDesc);

		return result;
	}

	protected ResultDescDto getResultDescDto(String ymlKey) {
		Map<String, Object> exceptionInfo = exceptionInfoConfig.getExceptionInfo(ymlKey);
		if (exceptionInfo == null) {
			return exceptionInfoConfig.getUndefinedErrorResultDesc();
		}

		String desc = getMultiLangMessage(StringUtils.objectIfNullToEmpty(exceptionInfo.get("desc")));
		log.debug("==== desc :: " + desc);
		ResultDescDto resultDesc = new ResultDescDto();
		resultDesc.setCode(StringUtils.objectIfNullToEmpty(exceptionInfo.get("code")));
		resultDesc.setDesc(desc);
		resultDesc.setStatus(StringUtils.objectIfNullToEmpty(exceptionInfo.get("status")));
		resultDesc.setType(StringUtils.objectIfNullToEmpty(exceptionInfo.get("type")));
		return resultDesc;
	}

	protected ResultDescDto getResultDescDto(String ymlKey, Object[] args) {
		Map<String, Object> exceptionInfo = exceptionInfoConfig.getExceptionInfo(ymlKey);
		if (exceptionInfo == null) {
			return exceptionInfoConfig.getUndefinedErrorResultDesc();
		}

		log.debug("==== exceptionInfo :: " + StringUtils.objectIfNullToEmpty(exceptionInfo.get("desc")));
		
		String desc = "";
		try {
			desc = getMultiLangMessage(StringUtils.objectIfNullToEmpty(exceptionInfo.get("desc")));
		} catch (Exception ex) {
			desc = "";
		}
		log.debug("==== desc :: " + desc);
		ResultDescDto resultDesc = new ResultDescDto();
		resultDesc.setCode(StringUtils.objectIfNullToEmpty(exceptionInfo.get("code")));
		resultDesc.setDesc(MessageFormat.format(desc, args));
		resultDesc.setStatus(StringUtils.objectIfNullToEmpty(exceptionInfo.get("status")));
		resultDesc.setType(StringUtils.objectIfNullToEmpty(exceptionInfo.get("type")));
		return resultDesc;
	}

	/**
	 * @Method Name : getMultiLangMessage
	 * @Date : 2019. 2. 23.
	 * @Author : ASUS
	 * @History : 날자 작성자 내용 ---------- ----------- -------------------------------
	 *          2019.02.23 애커커스 홍효상
	 * @Description : 다국어 메세지 가지고 온다.
	 * @param lang
	 * @param key
	 * @return
	 */
	private String getMultiLangMessage(String key) {

		return multiLangManager.getMessage(key);
	}
}