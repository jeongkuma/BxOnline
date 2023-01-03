package kr.co.abacus.common.component;

import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.req.ComRequestDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.util.LinkedHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class ReqContextComponent {

	public static Object getSessionAttribute(String key) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_SESSION);
	}

	public static void setSessionAttribute(String key, Object value) {
		RequestContextHolder.currentRequestAttributes().setAttribute(key, value, RequestAttributes.SCOPE_SESSION);
	}

	public static void removeSessionAttribute(String key) {
		RequestContextHolder.currentRequestAttributes().removeAttribute(key, RequestAttributes.SCOPE_SESSION);
	}

	public static Object getAttribute(String key) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_REQUEST);
	}

	public static void setAttribute(String key, Object value) {
		RequestContextHolder.currentRequestAttributes().setAttribute(key, value, RequestAttributes.SCOPE_REQUEST);
	}

	public static void removeAttribute(String key, Object value) {
		RequestContextHolder.currentRequestAttributes().removeAttribute(key, RequestAttributes.SCOPE_REQUEST);
	}

	public static void setComRequestDto(ComRequestDto comRequestDto) {
		setAttribute(ComConstant.COM_REQUEST_DTO, comRequestDto);
	}

	public static ComRequestDto getComRequestDto() {
		Object temp = getAttribute(ComConstant.COM_REQUEST_DTO);
		if (temp == null) {
			return new ComRequestDto();
		}
		return (ComRequestDto) temp;
	}

	public static void setComResponseDto(ComResponseDto<?> comResponseDto) {
		setAttribute(ComConstant.COM_RESPONSE_DTO, comResponseDto);
	}

	public static ComResponseDto<?> getComResponseDto() {
		Object temp = getAttribute(ComConstant.COM_RESPONSE_DTO);
		if (temp == null) {
			return new ComResponseDto<Void>();
		}
		return (ComResponseDto<?>) temp;
	}

	public static String getLogKey() {
		ComInfoDto comInfoDto = getComInfoDto();
		return comInfoDto.getLogKey();
	}

	public static void setOmsLog(LinkedHashMap<String, String> omsLog) {
		setAttribute(ComConstant.COM_OMS_LOG, omsLog);
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getOmsLog() {
		return (LinkedHashMap<String, String>) getAttribute(ComConstant.COM_OMS_LOG);
	}

	public static void setComInfoDto(ComInfoDto comInfoDto) {
		setAttribute(ComConstant.COM_INFO, comInfoDto);
	}

	public static ComInfoDto getComInfoDto() {
		return (ComInfoDto) getAttribute(ComConstant.COM_INFO);
	}
}
