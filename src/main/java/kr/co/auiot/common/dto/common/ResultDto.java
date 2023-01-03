package kr.co.auiot.common.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import kr.co.abacus.common.dto.common.ResultBaseDto;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ResultDto extends ResultBaseDto {

	@JsonInclude(Include.NON_NULL)
	private String oms = null;

	public String getOms() {
		return oms;
	}

	public void setOms(String oms) {
		this.oms = oms;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
