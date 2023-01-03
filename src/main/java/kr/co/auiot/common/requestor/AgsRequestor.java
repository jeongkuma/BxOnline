package kr.co.auiot.common.requestor;

import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import kr.co.abacus.common.callback.ApiHttpRequestCallback;
import kr.co.abacus.common.callback.ApiHttpResponseCallback;
import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.CallHistoryInfoDto;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.common.ResultDescDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.rest.HttpBaseRequestor;
import kr.co.abacus.common.util.DateUtils;

@Component
public class AgsRequestor extends HttpBaseRequestor {

	public <T> ComResponseDto<T> agsApiCall(String url, HttpMethod method, HttpEntity<?> requestEntity, Class<T> paramterType) {
		CallHistoryInfoDto agsCallInfoDto = new CallHistoryInfoDto(ComConstant.AGS_CALL_SAS_HISTORY_TYPE);
		String reqDatetime = DateUtils.timeStamp(ComConstant.CALL_LOG_DATE_FORMAT);
		agsCallInfoDto.setReqDatetime(reqDatetime);
		ComResponseDto<T> resultDto = new ComResponseDto<T>();

		ApiHttpRequestCallback requestCallback = new ApiHttpRequestCallback(restTemplate, requestEntity);
		ApiHttpResponseCallback<JsonNode> responseExtractor = new ApiHttpResponseCallback<JsonNode>(objectMapper, agsCallInfoDto, JsonNode.class);
		Integer httpStatus = null;
		String responseMessage = null;

		try {
			agsCallInfoDto.setUrl(url);
			agsCallInfoDto.setRequestBody(requestEntity.getBody());
			agsCallInfoDto.setRequestHeaders(requestEntity.getHeaders().toSingleValueMap());

			JsonNode parameter = restTemplate.execute(url, method, requestCallback, responseExtractor);
			JsonNode tempReport = parameter.get("report");
			if (tempReport != null) {
				resultDto.setReport(tempReport);
			}
			JsonNode tempReason = parameter.get("reason");
			if (tempReason != null) {
				resultDto.setReason(tempReason);
			}
			JsonNode tempResultDesc = parameter.get("resultDto");
			if (tempResultDesc != null) {
				ResultDescDto resultDescDto = objectMapper.treeToValue(tempResultDesc, ResultDescDto.class);
				resultDto.setResult(resultDescDto);
			}
			T param = objectMapper.treeToValue(parameter, paramterType);
			resultDto.setData(param);

			httpStatus = responseExtractor.getHttpStatus();
			responseMessage = responseExtractor.getResponseMessage();
			String responseBodyString = responseExtractor.getResponseBody();

			ResultDescDto resultDescDto = new ResultDescDto();

			if (httpStatus == 200) {
				setResultDescDto(resultDescDto, "200", responseMessage, 200, "");
			} else {
				if (!StringUtils.isEmpty(responseBodyString)) {
					JsonNode report = resultDto.getReport();
					if (report != null) {
						String desc = responseMessage;
						String code = "52000600";
						JsonNode reason = report.get("reason");
						if (reason != null) {
							JsonNode msg = reason.get("errMsg");
							if (msg != null) {
								desc = msg.asText();
							}
							JsonNode jsonCode = reason.get("code");
							if (jsonCode != null) {
								code = jsonCode.asText();
							}
						}
						setResultDescDto(resultDescDto, code, desc, httpStatus, "API-" + httpStatus);
					} else {
						if (resultDto.getReason() == null) {
							setResultDescDto(resultDescDto, "52000600", responseMessage, httpStatus, "API-" + httpStatus);
						} else {
							String code = "52000600";
							if (httpStatus == 800) {
								code = "40000800";
							} else if (httpStatus == 801) {
								code = "40000801";
							}
							JsonNode reason = resultDto.getReason();
							setResultDescDto(resultDescDto, code, reason.asText(), httpStatus, "API-" + httpStatus);
						}
					}
					throw new BizException(resultDescDto);
				} else {
					throw new BizException("52000600", "api error", "API-900", "900");
				}
			}
			resultDto.setResult(resultDescDto);
			agsCallInfoDto.setHttpStatus(httpStatus);
		} catch (RestClientException e) {
			agsCallInfoDto.setIsException(true);
			Throwable cause = e.getRootCause();
			if (cause instanceof SocketTimeoutException) {
				throw new BizException(cause, "52000620", "Read timed out", "API-TIMEOUT", "598");
			} else if (cause instanceof ConnectException) {
				throw new BizException(cause, "52000620", "Connection timed out", "API-TIMEOUT", "598");
			} else if (cause instanceof FileNotFoundException) {
				throw new BizException(cause, "52000620", "File Not Found", "API-TIMEOUT", "598");
			} else {
				throw new BizException(cause, "52000620", "API REQUEST ERROR", "API-REQ-ERR", "598");
			}
		} catch (JsonProcessingException e) {
			throw new BizException(e, "org.springframework.http.converter.HttpMessageNotReadableException");
		} finally {
//			ComRequestDto comRequestDto = (ComRequestDto) requestEntity.getBody();
			agsCallInfoDto.setResDatetime(responseExtractor.getResponseDatetime());
			agsCallInfoDto.setResultCode("API-" + httpStatus);
			addAgsCallInfoToCommonReqDto(agsCallInfoDto);
		}
		return resultDto;
	}

	private void addAgsCallInfoToCommonReqDto(CallHistoryInfoDto agsCallInfoDto) {
		ComInfoDto commonInfo = ReqContextComponent.getComInfoDto();
		if (commonInfo == null) {
			commonInfo = new ComInfoDto();
		}
		List<CallHistoryInfoDto> agsCallInfoDtos = commonInfo.getCallHistoryInfoList();
		agsCallInfoDtos.add(agsCallInfoDto);
	}

	public HttpHeaders getDefaultAgsHeaderInfo(String headerList) {
		String[] defaultHeaders = headerList.split(",");
		Map<String, String> requestHeaders = ReqContextComponent.getComInfoDto().getHeader();
		HttpHeaders headers = new HttpHeaders();

		Iterator<String> keys = requestHeaders.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			String value = requestHeaders.get(key);
			key = key.toLowerCase();

			for (int i = 0; i < defaultHeaders.length; i++) {
				String tempDefaultheaderKey = defaultHeaders[i].toLowerCase();

				if (tempDefaultheaderKey.equals(key)) {
					headers.add(key, value);
					break;
				}
			}
		}
		return headers;
	}

}
