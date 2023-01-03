package kr.co.auiot.common.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommonInterceptor extends HandlerInterceptorAdapter {

//	@Autowired
//	private CallLogFileWriter callLogFileWriter = null;
//
//	@Autowired
//	private OmsFileWriter omsFileWriter = null;
//
//	@Autowired
//	private Environment env = null;
//
//	@PostConstruct
//	private void init() {
//		this.calllogIsAsync = env.getProperty("log.call.isAsync", Boolean.class);
//	}
//
//	private boolean calllogIsAsync = true;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// login uri 체크
		// 헤더 토큰 가져와서 
		// session 조회 (권한 가져오기) 
		// -- User 변환 
		// -- id 조회
		// uri 체크 : 있으면 true 없으면 false Exception
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
//		String resDatetime = DateUtils.timeStamp(ComConstant.CALL_LOG_DATE_FORMAT);
//		ComInfoDto commonInfoDto = ReqContextComponent.getComInfoDto();
//		if (commonInfoDto == null) {
//			commonInfoDto = new ComInfoDto();
//		}
//		commonInfoDto.setResDatetime(resDatetime);
//		ComResponseDto<?> comResponseDto = ReqContextComponent.getComResponseDto();
//		if (comResponseDto == null) {
//			comResponseDto = new ComResponseDto<Void>();
//		}
//		if (calllogIsAsync) {
//			callLogFileWriter.asyncWrite(commonInfoDto, comResponseDto.toString());
//		} else {
//			callLogFileWriter.write(commonInfoDto, comResponseDto.toString());
//		}
//
//		LinkedHashMap<String, String> omsLog = ReqContextComponent.getOmsLog();
//		if (omsFileWriter.isOmsIsAsync()) {
//			omsFileWriter.asyncWrite(omsLog, commonInfoDto);
//		} else {
//			omsFileWriter.write(omsLog, commonInfoDto);
//		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {

//		try {
//			if (exception != null) {
//				String resDatetime = DateUtils.timeStamp(ComConstant.CALL_LOG_DATE_FORMAT);
//				ComInfoDto commonInfoDto = ReqContextComponent.getComInfoDto();
//				if (commonInfoDto == null) {
//					commonInfoDto = new ComInfoDto();
//				}
//				commonInfoDto.setResDatetime(resDatetime);
//				ComResponseDto<?> comResponseDto = ReqContextComponent.getComResponseDto();
//				if (comResponseDto == null) {
//					comResponseDto = new ComResponseDto<Void>();
//				}
//				if (calllogIsAsync) {
//					callLogFileWriter.asyncWrite(commonInfoDto, comResponseDto.toString());
//				} else {
//					callLogFileWriter.write(commonInfoDto, comResponseDto.toString());
//				}
//
//				LinkedHashMap<String, String> omsLog = ReqContextComponent.getOmsLog();
//				if (omsFileWriter.isOmsIsAsync()) {
//					omsFileWriter.asyncWrite(omsLog, commonInfoDto);
//				} else {
//					omsFileWriter.write(omsLog, commonInfoDto);
//				}
//			}
//		} catch (Exception e) {
//			log.warn(e.getMessage());
//		}

	}
}
