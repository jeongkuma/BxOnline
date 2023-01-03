package kr.co.scp.ccp.common.brdFile.dto;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIoTBrdFileListDTO{
	@JsonIgnore
	private String dymmy;
	//	첨부파일 리스트 테이블
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

	// mode : i : insert, mode : u update, mode : d : 삭제
	private String mode;

}
