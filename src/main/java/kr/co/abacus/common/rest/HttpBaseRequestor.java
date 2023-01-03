package kr.co.abacus.common.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.abacus.common.dto.common.ResultDescDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpBaseRequestor {
	protected RestTemplate restTemplate = null;

	@Autowired
	protected ObjectMapper objectMapper = null;

	@Autowired
	protected void setErrorHandler(RestTemplate restTemplate) {
		restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
		this.restTemplate = restTemplate;
	}

	protected void setResultDescDto(ResultDescDto resultDescDto, String code, String desc, Integer status, String type) {
		resultDescDto.setCode(code);
		resultDescDto.setDesc(desc);
		resultDescDto.setStatus("" + status);
		resultDescDto.setType(type);
	}
}
