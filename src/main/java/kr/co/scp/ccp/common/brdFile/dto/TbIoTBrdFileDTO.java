package kr.co.scp.ccp.common.brdFile.dto;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIoTBrdFileDTO {
	@JsonIgnore
	private String dymmy;
	private String contentSeq;
	private String contentType;
	private String fileListSeq;
	private String filePath;
	private String fileName;
	private String orgFileName;
	private long fileSize;
	private int fileOrder;
	private String fileSizeLabel;
	private String regUsrId;
	private String modUsrId;

}
