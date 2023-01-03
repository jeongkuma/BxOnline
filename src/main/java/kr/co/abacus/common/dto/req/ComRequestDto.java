package kr.co.abacus.common.dto.req;

import com.google.gson.GsonBuilder;
import kr.co.abacus.common.dto.common.ComInfoDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComRequestDto {
	private String req_type = null;
	private Object reqParameter;
	private ComInfoDto comInfoDto;

	@Override
	public String toString() {
		// return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
		return new GsonBuilder().serializeNulls().create().toJson(this); // 2020/06/02 한글 유니코드 대응 변경
	}
}
