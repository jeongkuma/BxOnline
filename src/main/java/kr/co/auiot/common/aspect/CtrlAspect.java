package kr.co.auiot.common.aspect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import kr.co.auiot.common.config.AuthApiConfig;
import kr.co.auiot.common.log.OmsFileWriter;
import kr.co.auiot.common.spring.OmsConfig;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.req.ComRequestDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.DateUtils;
import kr.co.abacus.common.util.HttpUtils;
import kr.co.abacus.common.util.LinkedHashMap;
import kr.co.abacus.common.util.StringUtils;
import kr.co.abacus.common.validation.ValidationComponent;
import kr.co.auiot.common.log.CallLogFileWriter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class CtrlAspect {
	@Autowired
	private CallLogFileWriter callLogFileWriter = null;

	@Autowired
	private OmsFileWriter omsFileWriter = null;

	@Autowired
	private Environment env = null;

	@PostConstruct
	private void init() {
		this.calllogIsAsync = env.getProperty("log.call.isAsync", Boolean.class);
	}

	private boolean calllogIsAsync = true;

	@Autowired
	private ObjectMapper objectMapper = null;

	@Autowired
	private OmsConfig omsConfig = null;

	@Autowired
	private ValidationComponent validate = null;

	@Autowired
	private AuthApiConfig authApiConfig = null;

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	void CtrlPointCut() {
	}

	@Around("CtrlPointCut()")
	public Object Before(ProceedingJoinPoint joinPoint) throws Throwable {

		String reqDatetime = DateUtils.timeStamp(ComConstant.CALL_LOG_DATE_FORMAT);

		Object[] args = joinPoint.getArgs();
		int argsLength = args.length;

		boolean listYn = false;
		HttpServletRequest request = null;
		for (int i = 0; i < argsLength; i++) {
			Object obj = args[i];
			if (args[i] instanceof List) {
				listYn = true;
			}
			if (obj instanceof HttpServletRequest) {
				request = (HttpServletRequest) obj;
			}
		}

		ComRequestDto comRequestDto = null;
//		HttpServletRequest request = (HttpServletRequest) args[0];
		ServletRequestAttributes currentRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();

		ComInfoDto commonInfo = new ComInfoDto();
		ReqContextComponent.setComInfoDto(commonInfo);
		ReqContextComponent.setComRequestDto(comRequestDto);
		ReqContextComponent.setOmsLog(omsConfig.getOmsBasicData());
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String buffer;
			while ((buffer = input.readLine()) != null) {
				buffer = buffer.trim();
				builder.append(buffer);
			}
			String requestBody = builder.toString();
			commonInfo.setBodyString(requestBody);

			if (request.getContentType().startsWith("multipart/form-data")) {
				commonInfo.setBodyString(request.getParameter("jsonData"));
			}

		} catch (IOException e) {
			throw new BizException("org.springframework.http.converter.HttpMessageNotReadableException");
		} finally {
			if (input != null) {
				input.close();
			}
		}

		Map<String, String> tempHeaderInfo = new LinkedHashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		String logKey = "";
		String vname = "";
		String service = "";
		String channel = "";
		String mid = "";
		String callType = "";
		String auth = "";
		String xlang = "";
		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			String value = request.getHeader(key);
			tempHeaderInfo.put(key.toLowerCase(), value);
			if (ComConstant.X_LOG_KEY.equals(key.toUpperCase())) {
				logKey = value;
			} else if (ComConstant.THINGS_NAME.equals(key.toUpperCase())) {
				vname = value;
			} else if (ComConstant.SERVICE.equals(key.toUpperCase())) {
				service = value;
			} else if (ComConstant.X_LANG_SET.equals(key.toUpperCase())) {
				currentRequestAttributes.getRequest().getSession().setAttribute(ComConstant.X_LANG_SET, value);
				xlang = value;
			} else if (ComConstant.X_CHANNEL.equals(key.toUpperCase())) {
				channel = value;
			} else if (ComConstant.X_MID.equals(key.toUpperCase())) {
				mid = value;
			} else if (ComConstant.X_CALLTYPE.equals(key.toUpperCase())) {
				callType = value;
			} else if (ComConstant.X_AUTH_TOKEN.equals(key.toUpperCase())) {
				auth = value;
			}
		}

		// Default vname (IAG)
		if(StringUtils.isEmpty(vname)) {
			vname = "ONLINE";
		}

		String requestUri = request.getRequestURI();
		commonInfo.setRequestUri(requestUri);
		String clientIp = request.getRemoteAddr();
		commonInfo.setAuth(auth);
		commonInfo.setXlang(xlang);
		commonInfo.setClientIp(clientIp);
		commonInfo.setReqDatetime(reqDatetime);
		commonInfo.setLogKey(logKey);
		commonInfo.setVname(vname);
		commonInfo.setService(service);
		commonInfo.setChannel(channel);
		commonInfo.setMid(mid);
		commonInfo.setCallType(callType);
		commonInfo.setQueryString(HttpUtils.getQueryString(request));
		commonInfo.setHeader(tempHeaderInfo);
		commonInfo.setParsedHeader(ObjectUtils.clone(commonInfo.getHeader()));
		commonInfo.setParsedQueryString(ObjectUtils.clone(commonInfo.getQueryString()));
		if (StringUtils.isNotEmpty(commonInfo.getBodyString())) {
			commonInfo.setBodyJson(objectMapper.readValue(commonInfo.getBodyString(), JsonNode.class));
			commonInfo.setParsedBodyJson(objectMapper.readValue(commonInfo.getBodyString(), JsonNode.class));
		}

		if (!authApiConfig.getautoApiConfig(requestUri)) {
			throw new BizException("auth_pai_chk");
		}


		try {
			validate.vaildate(commonInfo);
		}catch(Exception e ) {
//			e.printStackTrace();
			throw e;
		}

//		if (StringUtils.isEmpty(service)) {
//			requestParser.parse(commonInfo);
//			validate.vaildate(commonInfo);
//			targetMapper.doMap(commonInfo);
//		} else {
//			List<ComInfoDto> list = processing.process(commonInfo);
//
//			for (ComInfoDto dto : list) {
//				requestParser.parse(dto);
//				validate.vaildate(dto);
//				targetMapper.doMap(dto);
//			}
//		}
		if (StringUtils.isNotEmpty(commonInfo.getBodyString())) {
			if (listYn) {
				comRequestDto = new ComRequestDto();
			} else {
				comRequestDto = objectMapper.readValue(commonInfo.getBodyString(), ComRequestDto.class);
			}

		} else {
			comRequestDto = new ComRequestDto();
		}

		Object[] args1 = new Object[argsLength];
		for (int i = 0; i < argsLength; i++) {
			if (args[i] instanceof HttpServletRequest) {
				args1[i] = args[i];
			} else if (args[i] instanceof ComRequestDto) {
				args1[i] = comRequestDto;
			} else {
				args1[i] = args[i];
			}
		}

		Object returnObject = null;
		Boolean logPrintFlag = false;

		try {
			returnObject = joinPoint.proceed(args1);
			if (returnObject instanceof ComResponseDto) {
				ReqContextComponent.setComResponseDto((ComResponseDto<?>) returnObject);
			}
			String resDatetime = DateUtils.timeStamp(ComConstant.CALL_LOG_DATE_FORMAT);
//			ComInfoDto commonInfoDto = ReqContextComponent.getComInfoDto();
//			if (commonInfoDto == null) {
//				commonInfoDto = new ComInfoDto();
//			}
			commonInfo.setResDatetime(resDatetime);
			ComResponseDto<?> comResponseDto = ReqContextComponent.getComResponseDto();
			if (comResponseDto == null) {
				comResponseDto = new ComResponseDto<Void>();
			}

			if (logPrintFlag) {
				if (calllogIsAsync) {
					callLogFileWriter.asyncWrite(commonInfo, comResponseDto.toString());
				} else {
					callLogFileWriter.write(commonInfo, comResponseDto.toString());
				}

				LinkedHashMap<String, String> omsLog = ReqContextComponent.getOmsLog();
				if (omsFileWriter.isOmsIsAsync()) {
					omsFileWriter.asyncWrite(omsLog, commonInfo);
				} else {
					omsFileWriter.write(omsLog, commonInfo);
				}
			}


		} catch (Throwable e) {
			throw e;
		}

		return returnObject;
	}
}
