package kr.co.scp.ccp.iotDev.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class TbIotDevExcelDTO {
	@JsonIgnore
	private String dymmy;
	// 파라미터
	private int[] devSeq;		// 장비 순번
	private int custSeq;		// 고객 번호
	private String devSvcCdNm;	// 서비스 유형 이름


	// 장비
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String useYn;
	private String parentDevSeq;
	private String vendorNm;
	private String stateCd;
	private String regUsrId;
	private String modUsrId;
	private String normalIconFile;
	private String abnormalIconFile;
	private String abnormalIconFile2;
	public String orgDevSeq;
	public String devMdlVal;
	public String iconRegYn;

	// 장비 속성
	private String devAttbCdId;	// 장비 속성코드
	private String devAttbCdNm;	// 장비 속성코드명
	private int minVal;			// 속성 최소값
	private int maxVal;			// 속성 최대값
	private String colNmCd;		// 수집대상 컬럼명 코드
	private String conNmCd;		// 제어대상 컬럼명
	private String staNmCd;		// 통계대상 컬럼명
	private String detNmCd;		// 이상징후 컬럼명
	private String mapYn;		// 지도 표시 여부
	private String statusCd;     // 상태코드

	// 장비 속성 값
	private String devAttbSetCdId;	// 장비 속성  SET 코드 ID
	private String devAttbSetCdNm;	// 장비 속성  SET 코드명

	// 장비 이상징후
	private String devDetSetCdId;	// 이상징후  SET 코드 ID
	private String devDetSetCdNm;	// 이상징후  SET 코드명
	private String devSetCond;		// 이상징후  SET 조건
	private String iconUrl;			// 이상징후  SET 아이콘 URL

	private String langSet;
}
