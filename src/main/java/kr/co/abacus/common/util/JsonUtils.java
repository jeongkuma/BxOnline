package kr.co.abacus.common.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import kr.co.abacus.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonUtils {

	public static final ObjectMapper mapper = new ObjectMapper();

	public static <T> T parse(JsonNode json, Class<T> clazz) {
		T result = null;
		result = mapper.convertValue(json, clazz);
		return result;
	}

	public static String objectToString(Object value) {
		String result = null;
		try {
			result = mapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new RuntimeException();
		}
		return result;
	}

	public static JsonNode objectToJsonNode(Object obj) {
		return mapper.valueToTree(obj);
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonParseException e) {
			throw new BizException(e, e.getMessage());
		} catch (JsonMappingException e) {
			throw new BizException(e, e.getMessage());
		} catch (IOException e) {
			throw new BizException(e, e.getMessage());
		}
	}

	public static JsonNode getFindValue(String searchKey, JsonNode sourceNode) throws BizException {
		String findkey = "/" + searchKey.replaceAll("\\.", "\\/");
		JsonNode soureJsonNodeTemp = sourceNode.at(findkey);
		return soureJsonNodeTemp;
	}

	public static boolean compareSourceSearchKey(String searchKey, JsonNode value, JsonNode soureJsonNode)
			throws BizException {
		boolean rn = false;
		String findkey = "/" + searchKey.replaceAll("\\.", "\\/");
		JsonNode soureJsonNodeTemp = soureJsonNode.at(findkey);
		if (soureJsonNodeTemp.asText().equals(value.asText())) {
			rn = true;
		}
		return rn;
	}

	public static boolean isValidJson(String jsonInString) {
		boolean isValid = false;
		try {
			mapper.readTree(jsonInString);
			isValid = true;
		} catch (IOException e) {
			isValid = false;
		}
		return isValid;
	}

	public static JsonNode getFindPathValue(String searchKey, JsonNode sourceNode) throws BizException {
		Configuration jacksonConfig = Configuration.builder().mappingProvider(new JacksonMappingProvider())
				.jsonProvider(new JacksonJsonProvider()).build();
		return JsonPath.using(jacksonConfig).parse(sourceNode.toString()).read(searchKey, JsonNode.class).get(0);
	}

	public static JsonNode getFindPathValues(String searchKey, JsonNode sourceNode) throws BizException {
		Configuration jacksonConfig = Configuration.builder().mappingProvider(new JacksonMappingProvider())
				.jsonProvider(new JacksonJsonProvider()).build();
		return JsonPath.using(jacksonConfig).parse(sourceNode.toString()).read(searchKey, JsonNode.class);
	}

}
