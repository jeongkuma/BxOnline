package kr.co.abacus.common.dto.common;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultDescDto {
	private String code = null;
	private String desc = null;
	private String type = null;
	private String status = null;

	@Override
	public String toString() {
//		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
		return new GsonBuilder().serializeNulls().create().toJson(this); // 2020/06/02 한글 유니코드 대응 변경
	}
}
