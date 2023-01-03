package kr.co.abacus.common.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.GsonBuilder;
import kr.co.abacus.common.dto.common.ResultDescDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ComResponseDto<T> {
	private JsonNode reason = null;
	private JsonNode report = null;
	private ResultDescDto result = null;
	private T data = null;
	private String oms = null;

	@JsonIgnore
	private Boolean isException = false;

	@Override
	public String toString() {
		// return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
		return new GsonBuilder().serializeNulls().create().toJson(this); // 2020/06/02 한글 유니코드 대응 변경
	}

}
