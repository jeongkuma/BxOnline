/**
 *
 */
package kr.co.scp.ccp.iotEDev.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 고객 가입장비속성
 * @author pkjean
 *
 */
@Getter @Setter @ToString
public class EDevAttbDTO {
	@JsonIgnore
	private String dymmy;
	private String entrDevSeq;
	private String entrDevAttbSeq;
	private String sDevAttbSeq;

	private String cDevSeq;
	private String cDevAttbSeq;

	private String devAttbCdId;
	private String devAttbCdNm;

	private String devClsCd;
	private String devClsCdNM;

	private String devMdlCd;
	private String devMdlNm;

	private String eDevCurValSeq;
	private String inputType;
	private String inputValue;
	private String paramKey;

	private String devVal;
	private String stateCd;

	private List<EDevAttbSetDTO> entrDevAttbSets;
}
