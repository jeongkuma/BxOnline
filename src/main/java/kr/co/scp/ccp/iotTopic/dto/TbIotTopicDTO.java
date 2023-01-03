package kr.co.scp.ccp.iotTopic.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TbIotTopicDTO extends IoTBaseDTO {
	public TbIotTopicDTO() {
		//
	}

	public TbIotTopicDTO(String baseTopicId, String topicId, String dayTime, String beanCd, List<TbIotUsrDashConDTO> tbIotUsrDashConList,
			String topicGubun) {
		this.baseTopicId = baseTopicId;
		this.topicId = topicId;
		this.dayTime = dayTime;
		this.beanCd = beanCd;
		this.tbIotUsrDashConList = tbIotUsrDashConList;
		this.topicGubun = topicGubun;
	}

	@JsonIgnore
	private String dymmy;
	private String topicId; // TOPIC destination
	private String topicNm; // TOOIC명
	private String dayTime; // 주기
	private String beanCd; // 실행파일
	private String rspMsg; // 응답전문
	private String topicDesc; // 설명
	private String topicGubun; // 구분
	private String regUserId; // 등록자ID
	private String regDate; // 등록일시
	private String modUsrId; // 수정자ID
	private String modDttm; // 수정일시

	/**
	 * 조건 중 '_interval_' 이란 이름의 조건은 기준(base) topic 에 설정된 주기정보를 대체한다.
	 */
	private List<TbIotUsrDashConDTO> tbIotUsrDashConList; // 조건목록

	private String baseTopicId; // hashed topic 의 경우, parent(base) topic id.

}
