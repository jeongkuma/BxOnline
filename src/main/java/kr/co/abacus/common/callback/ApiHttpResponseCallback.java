package kr.co.abacus.common.callback;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.CallHistoryInfoDto;
import kr.co.abacus.common.util.DateUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApiHttpResponseCallback<T> implements ResponseExtractor<T> {
	private Integer httpStatus = null;
	private String responseDatetime = "";
	private ObjectMapper objectMapper = null;
	private Class<T> responseType = null;
	private String responseMessage = null;
	private CallHistoryInfoDto callInfoDto = null;
	private String responseBody = null;

	public ApiHttpResponseCallback(ObjectMapper objectMapper, CallHistoryInfoDto callInfoDto, Class<T> responseType) {
		this.objectMapper = objectMapper;
		this.responseType = responseType;
		this.callInfoDto = callInfoDto;
	}

	@Override
	public T extractData(ClientHttpResponse response) throws IOException {
		this.httpStatus = response.getRawStatusCode();
		this.responseDatetime = DateUtils.timeStamp(ComConstant.CALL_LOG_DATE_FORMAT);
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(response.getBody()));
			StringBuilder builder = new StringBuilder();
			String buffer;
			while ((buffer = input.readLine()) != null) {
				buffer = buffer.trim();
				builder.append(buffer);
			}

			responseBody = builder.toString();
			callInfoDto.setResponseBody(responseBody);
			T body = objectMapper.readValue(responseBody, responseType);
			responseMessage = response.getStatusText();
			callInfoDto.setResponseMessage(responseMessage);
			return body;
		} catch (IOException e) {
			throw e;
		}
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public String getResponseDatetime() {
		return responseDatetime;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public String getResponseBody() {
		return responseBody;
	}
}