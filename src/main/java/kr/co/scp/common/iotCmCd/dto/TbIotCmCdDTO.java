package kr.co.scp.common.iotCmCd.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.co.auiot.common.dto.common.IoTBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Setter @Getter @ToString
public class TbIotCmCdDTO{

	@JsonIgnore
	private String dymmy;
	private String cdSeq        ;
	private String cdOrder   ;
	private String charSet   ;
	private String langSet   ;
	private String cdId      ;
	private String cdNm      ;
	private String cdType    ;
	private String useYn     ;
	private String useYnVal  ;
	private String parentCdId;
	private String parentCdNm;
	private String firstCdId ;
	private String secondCdId;
	private String thirdCdId ;
	private String paramKey  ;
	private String paramVal  ;
	private String cdDesc    ;
	private String regUsrId  ;
	private String modUsrId  ;
	private String regDttm  ;
	private String level1  ;
	private String level2  ;
	private String level3  ;


	public String getLevel1() {
		return level1;
	}

	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	public String getLevel2() {
		return level2;
	}

	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	public String getLevel3() {
		return level3;
	}

	public void setLevel3(String level3) {
		this.level3 = level3;
	}

	public String getRegDttm() {
		return regDttm;
	}

	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
	}

	// 조회 조건을 위한 변수
	private String searchCon ;
	private String searchVal ;
	private int lvl ;
	private String childrenCnt;

	// 중복체크를 위한 변수
	private String msg;

	// 페이징을 위한 변수
	private Integer displayRowCount  ;
	private Integer currentPage      ;
	private Integer endPage      ;
	private Integer totCnt;

	// 사용자명을 위한 변수
	private String custSeq ;

	public Integer getTotCnt() {
		return totCnt;
	}

	public void setTotCnt(Integer totCnt) {
		this.totCnt = totCnt;
	}

	public String getChildrenCnt() {
		return childrenCnt;
	}

	public void setChildrenCnt(String childrenCnt) {
		this.childrenCnt = childrenCnt;
	}

	public String getCdSeq() {
		return cdSeq;
	}

	public void setCdSeq(String cdSeq) {
		this.cdSeq = cdSeq;
	}

	public String getCdOrder() {
		return cdOrder;
	}

	public void setCdOrder(String cdOrder) {
		this.cdOrder = cdOrder;
	}

	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public String getLangSet() {
		return langSet;
	}

	public void setLangSet(String langSet) {
		this.langSet = langSet;
	}

	public String getCdId() {
		return cdId;
	}


	public void setCdId(String cdId) {
		this.cdId = cdId;
	}

	public String getCdNm() {
		return cdNm;
	}

	public void setCdNm(String cdNm) {
		this.cdNm = cdNm;
	}

	public String getCdType() {
		return cdType;
	}

	public void setCdType(String cdType) {
		this.cdType = cdType;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getUseYnVal() {
		return useYnVal;
	}

	public void setUseYnVal(String useYnVal) {
		this.useYnVal = useYnVal;
	}

	public String getParentCdId() {
		return parentCdId;
	}

	public void setParentCdId(String parentCdId) {
		this.parentCdId = parentCdId;
	}

	public String getParentCdNm() {
		return parentCdNm;
	}

	public void setParentCdNm(String parentCdNm) {
		this.parentCdNm = parentCdNm;
	}

	public String getFirstCdId() {
		return firstCdId;
	}

	public void setFirstCdId(String firstCdId) {
		this.firstCdId = firstCdId;
	}

	public String getSecondCdId() {
		return secondCdId;
	}

	public void setSecondCdId(String secondCdId) {
		this.secondCdId = secondCdId;
	}

	public String getThirdCdId() {
		return thirdCdId;
	}

	public void setThirdCdId(String thirdCdId) {
		this.thirdCdId = thirdCdId;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamVal() {
		return paramVal;
	}

	public void setParamVal(String paramVal) {
		this.paramVal = paramVal;
	}

	public String getCdDesc() {
		return cdDesc;
	}

	public void setCdDesc(String cdDesc) {
		this.cdDesc = cdDesc;
	}

	public String getRegUsrId() {
		return regUsrId;
	}

	public void setRegUsrId(String regUsrId) {
		this.regUsrId = regUsrId;
	}

	public String getModUsrId() {
		return modUsrId;
	}

	public void setModUsrId(String modUsrId) {
		this.modUsrId = modUsrId;
	}

	public String getSearchCon() {
		return searchCon;
	}

	public void setSearchCon(String searchCon) {
		this.searchCon = searchCon;
	}

	public String getSearchVal() {
		return searchVal;
	}

	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getDisplayRowCount() {
		return displayRowCount;
	}

	public void setDisplayRowCount(Integer displayRowCount) {
		this.displayRowCount = displayRowCount;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public String getCustSeq() {
		return custSeq;
	}

	public void setCustSeq(String custSeq) {
		this.custSeq = custSeq;
	}
	public Integer getEndPage() {
		return endPage;
	}

	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}

	@Override
	public String toString() {
		return "{cdSeq:" + cdSeq + ", cdOrder:" + cdOrder + ", charSet:" + charSet + ", cdId:" + cdId
				+ ", cdNm:" + cdNm + ", cdType:" + cdType + ", useYn:" + useYn + ", useYnVal:" + useYnVal
				+ ", parentCdId:" + parentCdId + ", parentCdNm:" + parentCdNm + ", firstCdId:" + firstCdId
				+ ", secondCdId:" + secondCdId + ", thirdCdId:" + thirdCdId + ", paramKey:" + paramKey + ", paramVal:"
				+ paramVal + ", cdDesc:" + cdDesc + ", regUsrId:" + regUsrId + ", modUsrId:" + modUsrId + ", searchCon:"
				+ searchCon + ", searchVal:" + searchVal + ", lvl:" + lvl + ", msg:" + msg + ", displayRowCount:"
				+ displayRowCount + ", currentPage:" + currentPage + ", custSeq:" + custSeq + "}";
	}


}
