package kr.co.abacus.common.dto.common;

import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CallHistoryInfoDto {
	private String callHistoryType = "";
	private String url = null;
	private String reqDatetime = null;
	private HttpMethod method = null;
	private Map<String, String> requestHeaders = null;
	private Object requestBody = null;
	private String resDatetime = null;
	private Integer httpStatus = null;
	private String responseBody = null;
	private Boolean isException = false;
	private String responseMessage = null;
	private String resultCode = null;

	private String sql = null;
	private String sqlId = null;
	private List<ComSqlParameterDto> comSqlParameterDtoList = new ArrayList<ComSqlParameterDto>();

	public CallHistoryInfoDto(String callHistoryType) {
		this.callHistoryType = callHistoryType;
	}

	@Override
	public String toString() {
//		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
		return new GsonBuilder().serializeNulls().create().toJson(this); // 2020/06/02 한글 유니코드 대응 변경
	}
}
