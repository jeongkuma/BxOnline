package kr.co.abacus.common.util;

import kr.co.abacus.common.dto.common.ResultDescDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.config.ExceptionInfoConfig;
import kr.co.abacus.common.message.MultiLangManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
public class ResponseComUtil {

//	@SuppressWarnings("rawtypes")
//	private ComResponseDto comResponseDto = null;
//	private ResultDescDto resultDto = null;

	@Autowired
	private ExceptionInfoConfig exceptionInfoConfig = null;

	@Autowired
	private MultiLangManager multiLangManager;

//	public void initResponseComUtil() {
//		comResponseDto = new ComResponseDto<Object>();
//		resultDto = new ResultDescDto();
//	}

	public ComResponseDto<?> setResponse(String str) {
		ComResponseDto<Object> comResponseDto = new ComResponseDto<Object>();
		ResultDescDto resultDto = getSucessInfoMessage(str, "");
		comResponseDto.setResult(resultDto);
		return comResponseDto;

	}

	public ComResponseDto<?> setResponse200ok() {
		ComResponseDto<Object> comResponseDto = new ComResponseDto<Object>();
		ResultDescDto resultDto = getSucessInfoMessage("200ok", "");
		comResponseDto.setResult(resultDto);
		return comResponseDto;

	}

	public ComResponseDto<?> setResponse200ok(String str) {
		ComResponseDto<Object> comResponseDto = new ComResponseDto<Object>();
		ResultDescDto resultDto = getSucessInfoMessage("200ok", "");
		comResponseDto.setResult(resultDto);
		return comResponseDto;

	}

	public ComResponseDto<?> setResponse200ok(Object obj) {
		ComResponseDto<Object> comResponseDto = new ComResponseDto<Object>();
		ResultDescDto resultDto = getSucessInfoMessage("200ok", "");
		comResponseDto.setResult(resultDto);
		comResponseDto.setData(obj);
		return comResponseDto;

	}

	public ComResponseDto<?> setResponse200ok(Map<String, Object> parameter) {
		ComResponseDto<Object> comResponseDto = new ComResponseDto<Object>();
		ResultDescDto resultDto = getSucessInfoMessage("200ok", "");
		comResponseDto.setResult(resultDto);
		comResponseDto.setData(parameter);
		return comResponseDto;
	}

	public ComResponseDto<?> setResponse200ok(List<?> parameter) {
		ComResponseDto<Object> comResponseDto = new ComResponseDto<Object>();
		ResultDescDto resultDto = getSucessInfoMessage("200ok", "");
		comResponseDto.setResult(resultDto);
		comResponseDto.setData(parameter);
		return comResponseDto;
	}

	public ResultDescDto getSucessInfoMessage(String str, String desc) {
//		initResponseComUtil();
		ResultDescDto resultDto = new ResultDescDto();

		Map<String, Object> sucessInfo = exceptionInfoConfig.getSuccessInfo(str);
		if (sucessInfo == null) {
			resultDto = exceptionInfoConfig.getUndefinedErrorResultDesc();
		} else {
			resultDto.setCode(StringUtils.objectIfNullToEmpty(sucessInfo.get("code")));
			resultDto.setStatus(StringUtils.objectIfNullToEmpty(sucessInfo.get("status")));
			resultDto.setType(StringUtils.objectIfNullToEmpty(sucessInfo.get("type")));

			if (StringUtils.isEmpty(desc)) {
				resultDto.setDesc(multiLangManager.getMessage(StringUtils.objectIfNullToEmpty(str)));
			} else {
				resultDto.setDesc(multiLangManager.getMessage(StringUtils.objectIfNullToEmpty(sucessInfo.get("desc"))));
			}
		}
		return resultDto;

	}

	public ResultDescDto getInfoMessage(Boolean sucess, String str, String desc) {
		// initResponseComUtil();
		ResultDescDto resultDto = new ResultDescDto();

		Map<String, Object> sucessInfo = null;

		if (sucess) {
			sucessInfo = exceptionInfoConfig.getSuccessInfo(str);
		} else {
			sucessInfo = exceptionInfoConfig.getExceptionInfo(str);
		}

		if (sucessInfo == null) {
			resultDto = exceptionInfoConfig.getUndefinedErrorResultDesc();
		} else {
			resultDto.setCode(StringUtils.objectIfNullToEmpty(sucessInfo.get("code")));
			resultDto.setStatus(StringUtils.objectIfNullToEmpty(sucessInfo.get("status")));
			resultDto.setType(StringUtils.objectIfNullToEmpty(sucessInfo.get("type")));

			if (StringUtils.isEmpty(desc)) {
				resultDto.setDesc(multiLangManager.getMessage(StringUtils.objectIfNullToEmpty(str)));
			} else {
				resultDto.setDesc(multiLangManager.getMessage(StringUtils.objectIfNullToEmpty(sucessInfo.get("desc"))));
			}
		}
		return resultDto;

	}

	public ResultDescDto getInfoMessage(Boolean sucess, String str) {
//		initResponseComUtil();
		ResultDescDto resultDto = new ResultDescDto();
		Map<String, Object> sucessInfo = null;

		if (sucess) {
			sucessInfo = exceptionInfoConfig.getSuccessInfo(str);
		} else {
			sucessInfo = exceptionInfoConfig.getExceptionInfo(str);
		}

		if (sucessInfo == null) {
			resultDto = exceptionInfoConfig.getUndefinedErrorResultDesc();
		} else {
			resultDto.setCode(StringUtils.objectIfNullToEmpty(sucessInfo.get("code")));
			resultDto.setStatus(StringUtils.objectIfNullToEmpty(sucessInfo.get("status")));
			resultDto.setType(StringUtils.objectIfNullToEmpty(sucessInfo.get("type")));
			resultDto.setDesc(multiLangManager.getMessage(StringUtils.objectIfNullToEmpty(str)));
		}

		return resultDto;

	}

	public ComResponseDto<?> setResponseToken(String id, String token) {
		// initResponseComUtil();
		ComResponseDto<Object> comResponseDto = new ComResponseDto<Object>();
		ResultDescDto resultDto = new ResultDescDto();
		resultDto.setCode(id);
		resultDto.setDesc(token);

		comResponseDto.setResult(resultDto);
		return comResponseDto;
	}

}
