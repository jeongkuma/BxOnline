package kr.co.scp.common.tmpl.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TbIotJqDataResponseDTO {
	@JsonIgnore
	private String dymmy;
	private String colNameData;
	private String colModelData;
	private List<String> colNameList;

}
