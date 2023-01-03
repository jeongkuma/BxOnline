package kr.co.auiot.common.spring.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.common.ResultDescDto;
import kr.co.abacus.common.exception.config.ExceptionInfoConfig;
import kr.co.abacus.common.util.DateUtils;
import kr.co.abacus.common.util.StringUtils;
import kr.co.auiot.common.dto.common.ResultDto;
import kr.co.auiot.common.log.OmsFileWriter;
import kr.co.auiot.common.spring.OmsConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTAuthRedirectFilter extends GenericFilterBean {

	@Autowired
	private ExceptionInfoConfig exceptionInfoConfig;

	@Autowired
	private OmsConfig omsConfig = null;

	@Autowired
	private OmsFileWriter omsFileWriter = null;
	
	@Autowired
	private Environment env;
	
	private final String YML_DEFAULT_PATH = "log.oms.";


//    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
//    private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
//
//    private int customSessionExpiredErrorCode = 901;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		boolean isContainResponse = false;

		try {
			chain.doFilter(req, resp);

			log.debug("Chain processed normally");
		} catch (IOException ex) {
			ComInfoDto commonInfo = new ComInfoDto(request);

			log.debug("Chain processed IOException");
			Map<String, Object> exceptionInfo = exceptionInfoConfig.getExceptionInfo("IOException");

			if (exceptionInfo == null) {
				exceptionInfo = exceptionInfoConfig.getExceptionInfo("notdefine");
			}

			ResultDescDto resultDesc = new ResultDescDto();
			resultDesc.setCode(StringUtils.objectIfNullToEmpty(exceptionInfo.get("code")));
			resultDesc.setDesc(StringUtils.objectIfNullToEmpty(exceptionInfo.get("desc")));
			resultDesc.setStatus(StringUtils.objectIfNullToEmpty(exceptionInfo.get("status")));
			resultDesc.setType(StringUtils.objectIfNullToEmpty(exceptionInfo.get("type")));
			ResultDto resultDto = new ResultDto();
			resultDto.setResult(resultDesc);

			ObjectMapper mapper = new ObjectMapper();

		

			String contentType = "application/json";
			response.setContentType(contentType);
			
			if (StringUtils.isEmpty(resultDesc.getStatus())) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			} else {
				try {
					response.setStatus(Integer.parseInt(resultDesc.getStatus()));
				} catch (Exception e) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				}
			}
			
			String writerLogString = "";
			if (omsFileWriter.isOmsIsAsync()) {
				omsFileWriter.asyncWrite(omsConfig.getOmsBasicData(), commonInfo);
			} else {
				writerLogString = omsFileWriter.write(omsConfig.getOmsBasicData(), commonInfo);
			}
			
			
			if (isContainResponse) {
				try {
					resultDto.setOms(writerLogString);
				} catch (Exception e) {
				}
			}
			
			String jsonObject = mapper.writeValueAsString(resultDto);
			log.debug("Chain processed jsonObject:: " + jsonObject);
			
			PrintWriter out = response.getWriter();
			out.print(jsonObject);
			out.flush();
			out.close();

			return;
		} catch (Exception ex) {
			log.debug("Chain processed Exception:: " + ex.fillInStackTrace());
			isContainResponse = env.getProperty(YML_DEFAULT_PATH + "isContainResponse", Boolean.class);
			
			log.debug(ex.getMessage());
			
			String msg = ex.getMessage();
			if (msg.equals("Access is denied") || msg.equals("Unauthorized.") || msg.equals("Access denied")) {   //  
				msg = "AccessDenied";
			} else {
				msg = "notdefine";
			}

			ComInfoDto commonInfo = new ComInfoDto(request);

			Map<String, Object> exceptionInfo = exceptionInfoConfig.getExceptionInfo(msg);

			if (exceptionInfo == null) {
				exceptionInfo = exceptionInfoConfig.getExceptionInfo("notdefine");
			}

			ResultDescDto resultDesc = new ResultDescDto();
			resultDesc.setCode(StringUtils.objectIfNullToEmpty(exceptionInfo.get("code")));
			resultDesc.setDesc(StringUtils.objectIfNullToEmpty(exceptionInfo.get("desc")));
			resultDesc.setStatus(StringUtils.objectIfNullToEmpty(exceptionInfo.get("status")));
			resultDesc.setType(StringUtils.objectIfNullToEmpty(exceptionInfo.get("type")));
			ResultDto resultDto = new ResultDto();
			resultDto.setResult(resultDesc);

			ObjectMapper mapper = new ObjectMapper();

			

			String contentType = "application/json";
			response.setContentType(contentType);
			// response.setStatus((int) exceptionInfo.get("code"));
			if (msg.equals("AccessDenied")) {
				//response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
				response.setHeader("WWW-Authenticate", "BASIC Authenticate");
			} else {
				if (StringUtils.isEmpty(resultDesc.getStatus())) {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				} else {
					try {
						response.setStatus(Integer.parseInt(resultDesc.getStatus()));
					} catch (Exception e) {
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					}
				}
				
			}
			
			
			
			commonInfo.setResultDescDto(resultDesc);
			String resDatetime = DateUtils.timeStamp(ComConstant.CALL_LOG_DATE_FORMAT);
			commonInfo.setResDatetime(resDatetime);
			String writerLogString = "";
			if (omsFileWriter.isOmsIsAsync()) {
				omsFileWriter.asyncWrite(omsConfig.getOmsBasicData(), commonInfo);
			} else {
				writerLogString = omsFileWriter.write(omsConfig.getOmsBasicData(), commonInfo);
			}
			
			
			if (isContainResponse) {
				try {
					resultDto.setOms(writerLogString);
				} catch (Exception e) {
				}
			}
			
			String jsonObject = mapper.writeValueAsString(resultDto);
			log.debug("Chain processed jsonObject:: " + jsonObject);
			
			PrintWriter out = response.getWriter();
			out.print(jsonObject);
			out.flush();
			out.close();

			return;
		}
	}

}
