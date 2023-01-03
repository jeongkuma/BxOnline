package kr.co.abacus.common.callback;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ApiHttpRequestCallback implements RequestCallback {
	private HttpEntity<?> requestEntity = null;
	private RestTemplate restTemplate = null;

	public ApiHttpRequestCallback(RestTemplate restTemplate, HttpEntity<?> requestEntity) {
		this.restTemplate = restTemplate;
		this.requestEntity = requestEntity;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void doWithRequest(ClientHttpRequest httpRequest) throws IOException {
		Object requestBody = this.requestEntity.getBody();
		Class<?> requestBodyClass = requestBody.getClass();
		Type requestBodyType = (this.requestEntity instanceof RequestEntity ? ((RequestEntity<?>) this.requestEntity).getType() : requestBodyClass);
		HttpHeaders httpHeaders = httpRequest.getHeaders();
		HttpHeaders requestHeaders = this.requestEntity.getHeaders();
		MediaType requestContentType = requestHeaders.getContentType();
		for (HttpMessageConverter<?> messageConverter : restTemplate.getMessageConverters()) {
			if (messageConverter instanceof GenericHttpMessageConverter) {
				GenericHttpMessageConverter<Object> genericMessageConverter = (GenericHttpMessageConverter<Object>) messageConverter;
				if (genericMessageConverter.canWrite(requestBodyType, requestBodyClass, requestContentType)) {
					if (!requestHeaders.isEmpty()) {
						for (Map.Entry<String, List<String>> entry : requestHeaders.entrySet()) {
							httpHeaders.put(entry.getKey(), new LinkedList<String>(entry.getValue()));
						}
					}
					genericMessageConverter.write(requestBody, requestBodyType, requestContentType, httpRequest);
					return;
				}
			} else if (messageConverter.canWrite(requestBodyClass, requestContentType)) {
				if (!requestHeaders.isEmpty()) {
					for (Map.Entry<String, List<String>> entry : requestHeaders.entrySet()) {
						httpHeaders.put(entry.getKey(), new LinkedList<String>(entry.getValue()));
					}
				}
				((HttpMessageConverter<Object>) messageConverter).write(requestBody, requestContentType, httpRequest);
				return;
			}
		}
	}
}
