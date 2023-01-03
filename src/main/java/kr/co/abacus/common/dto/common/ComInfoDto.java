package kr.co.abacus.common.dto.common;

import com.fasterxml.jackson.databind.JsonNode;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.CaseInsensitiveMap;
import kr.co.abacus.common.util.DateUtils;
import kr.co.abacus.common.util.HttpUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ComInfoDto {
	private String logKey = null;
	private String vname = null;
	private String service = null;
	private String cmd = null;
	private String requestUri = null;
	private String reqDatetime = null;
	private String resDatetime = null;
	private String bodyString = null;
	private String xlang = null;
	private String channel = null;
	private String mid = null;
	private String callType = null;
	private String clientIp = null;
	private String temp = null;
	private String funcId = null;
	private String msg = null;

	private String uuid = null;
	private String commandType = null;
	private String xservice = null;
	private String tcpController = "N";
	private String devMdlCd = null;
	private String devClsCd = null;

	private List<CallHistoryInfoDto> callHistoryInfoList = new LinkedList<CallHistoryInfoDto>();
	private Map<String, String> header = new CaseInsensitiveMap<String, String>();
	private Map<String, String> parsedHeader = new CaseInsensitiveMap<String, String>();
	private Map<String, String> queryString = null;
	private Map<String, String> parsedQueryString = null;
	private Map<String, Object> targetMapper = null;
	private JsonNode bodyJson = null;
	private JsonNode parsedBodyJson = null;

	private ResultDescDto resultDescDto = null;
	private String errorMsg = null;
	private Boolean isException = false;

	private String auth = null;

	private List<ComInfoDto> comInfos = new LinkedList<ComInfoDto>();
	private String callHistoryType = null;

	private String asyncConectFailCode = null;

	public ComInfoDto() {
	}

	public ComInfoDto(String callHistoryType) {
		this.callHistoryType = callHistoryType;
	}

	public String getHeader(String key) {
		return header.get(key);
	}

	public void setHeader(Map<String, String> m) {
		if (m != null) {
			this.header.putAll(m);
		}
	}

	public void setParsedHeader(Map<String, String> m) {
		if (m != null) {
			this.parsedHeader.putAll(m);
		}
	}

	public ComInfoDto(HttpServletRequest request) {
		try {
			String reqDatetime = DateUtils.timeStamp(ComConstant.CALL_LOG_DATE_FORMAT);
			try {
				BufferedReader input = new BufferedReader(new InputStreamReader(request.getInputStream()));
				StringBuilder builder = new StringBuilder();
				String buffer = null;
				while ((buffer = input.readLine()) != null) {
					buffer = buffer.trim();
					builder.append(buffer);
				}
				String requestBody = builder.toString();
				this.bodyString = requestBody;
			} catch (IOException e) {
				throw new BizException("org.springframework.http.converter.HttpMessageNotReadableException");
			}

			Map<String, String> tempHeaderInfo = new CaseInsensitiveMap<String, String>();

			Enumeration<String> headerNames = request.getHeaderNames();
			this.logKey = request.getHeader(ComConstant.X_LOG_KEY) + ":" + DateUtils.timeStamp();
			this.vname = request.getHeader(ComConstant.THINGS_NAME);
			this.channel = request.getHeader(ComConstant.X_CHANNEL);
			this.mid = request.getHeader(ComConstant.X_MID);
			this.callType = request.getHeader(ComConstant.X_CALLTYPE);
			this.auth = request.getHeader(ComConstant.X_AUTH_TOKEN);

			while (headerNames.hasMoreElements()) {
				String key = headerNames.nextElement();
				String value = request.getHeader(key);
				tempHeaderInfo.put(key, value);
			}
			String requestUri = request.getRequestURI();
			this.requestUri = requestUri;
			this.clientIp = request.getRemoteAddr();
			this.reqDatetime = reqDatetime;
			this.header = tempHeaderInfo;
			this.queryString = HttpUtils.getQueryString(request);

		} catch (Exception e) {

		}
	}

	@Override
	public String toString() {
		ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.JSON_STYLE);
		return ReflectionToStringBuilder.toStringExclude(this, "bodyByte", "callInfoDto");
	}

}
