package kr.co.scp.ccp.iotPushTopic.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PushTopicDevClsStatusDTO {

	private String devClsCdNm;
	private String devMdlNm;
	private int act;
	private int sus;
	private int can;
}
