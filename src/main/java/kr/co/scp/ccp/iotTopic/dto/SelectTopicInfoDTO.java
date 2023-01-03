package kr.co.scp.ccp.iotTopic.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SelectTopicInfoDTO {

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

}
