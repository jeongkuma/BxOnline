package kr.co.scp.ccp.iotPushTopic.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PushTopicAttbDetStatusDTO {

	private String devClsCdNm;
	private String devMdlNm;
	private String devAttbCdNm;
	private int DS00000007; // 정상
	private int DS00000006; // 경미
	private int DS00000005; // 주의
	private int DS00000004; // 이상

}
