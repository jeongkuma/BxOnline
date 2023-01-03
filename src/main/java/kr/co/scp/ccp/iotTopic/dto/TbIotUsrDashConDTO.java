package kr.co.scp.ccp.iotTopic.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotUsrDashConDTO {

	public TbIotUsrDashConDTO() {
		//
	}

	public TbIotUsrDashConDTO(String tmplGubun, String tmplCondVlu) {
		this.tmplGubun = tmplGubun;
		this.tmplCondVlu = tmplCondVlu;
	}

	@JsonIgnore
	private String dymmy;
	private String usrSeq; // 사용자번호
	private String topicId; // TOPIC아이디
	private String tmplGubun; // 구분
	private String tmplCondVlu; // 값
	private String regUsrId; // 등록자ID
	private String regDate; // 등록일시
	private String modUsrId; // 수정자ID
	private String modDttm; // 수정일시
}
