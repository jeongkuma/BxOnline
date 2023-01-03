package kr.co.auiot.common.exception;

import java.lang.reflect.UndeclaredThrowableException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.ulisesbocchio.jasyptspringboot.exception.DecryptionException;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.common.ResultDescDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.exception.GlobalBaseExceptionHandler;
import kr.co.abacus.common.exception.ResourceNotFoundException;
import kr.co.abacus.common.util.DateUtils;
import kr.co.abacus.common.util.LinkedHashMap;
import kr.co.abacus.common.util.StringUtils;
import kr.co.auiot.common.dto.common.ResultDto;
import kr.co.auiot.common.log.CallLogFileWriter;
import kr.co.auiot.common.log.OmsFileWriter;
import kr.co.auiot.common.spring.OmsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends GlobalBaseExceptionHandler {
	
	@Autowired
	private CallLogFileWriter callLogFileWriter = null;

	@Autowired
	private OmsFileWriter omsFileWriter = null;

	@Autowired
	private Environment env = null;
	
	@Autowired
	private OmsConfig omsConfig = null;

	

	@PostConstruct
	private void init() {
		this.calllogIsAsync = env.getProperty("log.call.isAsync", Boolean.class);
	}

	private boolean calllogIsAsync = true;
	
	
	private ResultDto setErrorMsg(ResultDescDto resultDescDto) {
		ResultDto resultDto = new ResultDto();
		resultDto.setResult(resultDescDto);
		
		log.debug("=== resourceNotFoundException :: {}" , resultDescDto);
		try {
			ServletRequestAttributes currentRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = (HttpServletRequest) currentRequestAttributes.getRequest();
			ComInfoDto commonInfo = new ComInfoDto(request);
			commonInfo.setResultDescDto(resultDescDto);
			
			
			
			
			if (omsFileWriter.isOmsIsAsync()) {
				omsFileWriter.asyncWrite(omsConfig.getOmsBasicData(), commonInfo);
			} else {
				
				String omsMsg = omsFileWriter.write(omsConfig.getOmsBasicData(), commonInfo);
				
				ResultDescDto resultDesc = new ResultDescDto();
				resultDesc.setCode(StringUtils.objectIfNullToEmpty(resultDescDto.getCode()));
				resultDesc.setDesc(StringUtils.objectIfNullToEmpty(resultDescDto.getDesc()));
				resultDesc.setStatus(StringUtils.objectIfNullToEmpty(resultDescDto.getStatus()));
				resultDesc.setType(StringUtils.objectIfNullToEmpty(resultDescDto.getType()));
	
				
				
				resultDto.setOms(omsMsg);
			}
		} catch (Exception e) {
			
		}
		return resultDto;
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
	public  @ResponseBody ResultDto HttpRequestMethodNotSupportedException(org.springframework.http.converter.HttpMessageNotReadableException ex, WebRequest requestWeb) {
		
		// ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
		// request.getDescription(false));
		ResultDescDto resultDescDto = getResultDescDto("JSONTypeERROR");
		
		ResultDto resultDto = setErrorMsg(resultDescDto); 
		
		
		return resultDto;
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(org.springframework.web.HttpMediaTypeNotSupportedException.class)
	public  @ResponseBody ResultDto HttpMediaTypeNotSupportedException(org.springframework.web.HttpMediaTypeNotSupportedException ex, WebRequest request) {
		
		// ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
		// request.getDescription(false));
		ResultDescDto resultDescDto = getResultDescDto("org.springframework.web.HttpRequestMethodNotSupportedException");
		log.debug("=== resourceNotFoundException :: {}" , resultDescDto);
		
		ResultDto resultDto = setErrorMsg(resultDescDto); 
		return resultDto;
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
	public  @ResponseBody ResultDto MissingServletRequestParameterException(org.springframework.web.bind.MissingServletRequestParameterException ex, WebRequest request) {
		
		// ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
		// request.getDescription(false));
		ResultDescDto resultDescDto = getResultDescDto("org.springframework.web.HttpRequestMethodNotSupportedException");
		log.debug("=== resourceNotFoundException :: {}" , resultDescDto);
		
		ResultDto resultDto = setErrorMsg(resultDescDto); 
		return resultDto;
	}
	
	
		
	
//	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler(org.springframework.web.util.NestedServletException.class)
//	public  @ResponseBody ResultDto NestedServletException(org.springframework.web.util.NestedServletException ex, WebRequest request) {
//		
//		// ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
//		// request.getDescription(false));
//		ResultDescDto resultDescDto = getResultDescDto("org.springframework.web.HttpRequestMethodNotSupportedException");
//		log.debug("=== resourceNotFoundException :: {}" , resultDescDto);
//		
//		ResultDto resultDto = setErrorMsg(resultDescDto); 
//		return resultDto;
//	}
	
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
	public  @ResponseBody ResultDto HttpRequestMethodNotSupportedException(org.springframework.web.HttpRequestMethodNotSupportedException ex, WebRequest request) {
		
		// ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
		// request.getDescription(false));
		ResultDescDto resultDescDto = getResultDescDto("java.io.FileNotFoundException");
		log.debug("=== resourceNotFoundException :: {}" , resultDescDto);
		ResultDto resultDto = setErrorMsg(resultDescDto); 
		
		return resultDto;
	}
	
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ResourceNotFoundException.class)
	public  @ResponseBody ResultDto resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		
		// ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
		// request.getDescription(false));
		ResultDescDto resultDescDto = getResultDescDto("java.io.FileNotFoundException");
		log.debug("=== resourceNotFoundException :: {}" , resultDescDto);
		ResultDto resultDto = setErrorMsg(resultDescDto); 
		return resultDto;
	}
	
	
	@ExceptionHandler(Exception.class)
	public @ResponseBody ResultDto handleException(Exception ex, HttpServletRequest req, HttpServletResponse response)
			throws Exception {
		
		//log.debug(ex.getCause().toString());
		ResultDto result = new ResultDto();
		ResultDescDto resultDesc = null;
		Throwable throwable = null;
		
		if (ex instanceof java.lang.reflect.UndeclaredThrowableException) {
			UndeclaredThrowableException exception = (UndeclaredThrowableException) ex;
			throwable = exception.getUndeclaredThrowable();
			if (throwable instanceof BizException) {
				BizException bizException = (BizException) throwable;
				String ymlKey = bizException.getYmlKey();
				System.out.print("YMLKEY = " + ymlKey + "/n");
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
		ComResponseDto<Void> comResponseDto = new ComResponseDto<Void>();
		comResponseDto.setResult(resultDesc);
		comResponseDto.setIsException(true);
		ReqContextComponent.setComResponseDto(comResponseDto);
		
		// todo 확인 ComInfoDto의 용도는 ? //
		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
		if (throwable != null) {
//			log.error("[LOGKEY : {}] [ERROR MESSAGE : {}] [DETAIL ERROR MESSAGE : {}]",
//					ReqContextComponent.getLogKey(), ExceptionUtils.getStackTrace(ex),
//					ExceptionUtils.getStackTrace(throwable));
			comInfoDto.setErrorMsg(ExceptionUtils.getStackTrace(throwable).toString());
		} else {
//			log.error("[LOGKEY : {}] [ERROR MESSAGE : {}]", ReqContextComponent.getLogKey(),
//					ExceptionUtils.getStackTrace(ex));
			if (comInfoDto == null) {
				comInfoDto = new ComInfoDto();
			}
			comInfoDto.setErrorMsg(ExceptionUtils.getStackTrace(ex).toString());
		}
		comInfoDto.setIsException(true);
		response.setStatus(Integer.parseInt(resultDesc.getStatus()));
		comInfoDto.setResultDescDto(resultDesc);
		
		String resDatetime = DateUtils.timeStamp(ComConstant.CALL_LOG_DATE_FORMAT);
		ComInfoDto commonInfoDto = ReqContextComponent.getComInfoDto();
		if (commonInfoDto == null) {
			commonInfoDto = new ComInfoDto();
		}
		commonInfoDto.setResDatetime(resDatetime);
//		ComResponseDto<?> comResponseDto = ReqContextComponent.getComResponseDto();
//		if (comResponseDto == null) {
//			comResponseDto = new ComResponseDto<Void>();
//		}
		if (calllogIsAsync) {
			callLogFileWriter.asyncWrite(commonInfoDto, comResponseDto.toString());
		} else {
			callLogFileWriter.write(commonInfoDto, comResponseDto.toString());
		}

		LinkedHashMap<String, String> omsLog = ReqContextComponent.getOmsLog();
		if (omsFileWriter.isOmsIsAsync()) {
			omsFileWriter.asyncWrite(omsLog, commonInfoDto);
		} else {
			omsFileWriter.write(omsLog, commonInfoDto);
		}
		ComResponseDto<?> comResponseDtoOms = ReqContextComponent.getComResponseDto();
		result.setOms(comResponseDtoOms.getOms());
		return result;
	}
	
	
	@ExceptionHandler(DecryptionException.class)
	public @ResponseBody void handleException01(Exception ex, HttpServletRequest req, HttpServletResponse response)
			throws Exception {
		log.debug("================== error");
	}
}
