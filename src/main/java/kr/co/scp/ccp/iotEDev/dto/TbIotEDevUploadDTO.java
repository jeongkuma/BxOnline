package kr.co.scp.ccp.iotEDev.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 가입별 장비 등록 DTO
 * @author "pkjean"
 *
 */
@ToString
public class TbIotEDevUploadDTO{
	@JsonIgnore
	private String dymmy;
	private String ctn;
	private String svcCd;
	private String devUname;
	private String statusCd;
	private String usingNo;
	private String instAddr;
	private String instAddrDetail;
	private String instLat;
	private String instLon;
	private String devClsCd;
	private String devClsCdNm;
	private String devMdlCd;
	private String devMdlNm;
	private String devUuid;
	private String instNo;
	private String machineNo;
	private String devRegiDt;
	private String instDttm;
	private String regUserId;
	private String devBuildingNm;

	private String hldrCustNo;
	private String entrNo;
	private String aceno;

	// 삼천리 온라인 추가 필드
	private String nodeId;
	private String equiTypeNm;
	private String equiType;
	private String sequenceNum;

	public String getDymmy() {
		return dymmy;
	}
	public void setDymmy(String dymmy) {
		this.dymmy = dymmy;
	}


	public String getCtn() {
		return ctn;
	}
	public void setCtn(String ctn) {
		this.ctn = ctn;
	}
	public String getDevUname() {
		return devUname;
	}
	public void setDevUname(String devUname) {
		this.devUname = devUname;
	}
	public String getStatusCd() {
		return statusCd;
	}
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}
	public String getUsingNo() {
		return usingNo;
	}
	public void setUsingNo(String usingNo) {
		this.usingNo = usingNo;
	}
	public String getInstAddr() {
		return instAddr;
	}
	public void setInstAddr(String instAddr) {
		this.instAddr = instAddr;
	}
	public String getInstAddrDetail() {
		return instAddrDetail;
	}
	public void setInstAddrDetail(String instAddrDetail) {
		this.instAddrDetail = instAddrDetail;
	}
	public String getInstLat() {
		return instLat;
	}
	public void setInstLat(String instLat) {
		this.instLat = instLat;
	}
	public String getInstLon() {
		return instLon;
	}
	public void setInstLon(String instLon) {
		this.instLon = instLon;
	}
	public String getDevClsCd() {
		return devClsCd;
	}
	public void setDevClsCd(String devClsCd) {
		this.devClsCd = devClsCd;
	}
	public String getDevClsCdNm() {
		return devClsCdNm;
	}
	public void setDevClsCdNm(String devClsCdNm) {
		this.devClsCdNm = devClsCdNm;
	}
	public String getDevMdlCd() {
		return devMdlCd;
	}
	public void setDevMdlCd(String devMdlCd) {
		this.devMdlCd = devMdlCd;
	}
	public String getDevMdlNm() {
		return devMdlNm;
	}
	public void setDevMdlNm(String devMdlNm) {
		this.devMdlNm = devMdlNm;
	}
	public String getDevUuid() {
		return devUuid;
	}
	public void setDevUuid(String devUuid) {
		this.devUuid = devUuid;
	}
	public String getInstNo() {
		return instNo;
	}
	public void setInstNo(String instNo) {
		this.instNo = instNo;
	}
	public String getMachineNo() {
		return machineNo;
	}
	public void setMachineNo(String machineNo) {
		this.machineNo = machineNo;
	}
	public String getDevRegiDt() {
		return devRegiDt;
	}
	public void setDevRegiDt(String devRegiDt) {
		this.devRegiDt = devRegiDt;
	}
	public String getInstDttm() {
		return instDttm;
	}
	public void setInstDttm(String instDttm) {
		this.instDttm = instDttm;
	}
	public String getRegUserId() {
		return regUserId;
	}
	public void setRegUserId(String regUserId) {
		this.regUserId = regUserId;
	}
	public String getSvcCd() {
		return svcCd;
	}
	public void setSvcCd(String svcCd) {
		this.svcCd = svcCd;
	}
	public String getDevBuildingNm() {
		return devBuildingNm;
	}
	public void setDevBuildingNm(String devBuildingNm) {
		this.devBuildingNm = devBuildingNm;
	}
	public String getHldrCustNo() {
		return hldrCustNo;
	}
	public void setHldrCustNo(String hldrCustNo) {
		this.hldrCustNo = hldrCustNo;
	}
	public String getEntrNo() {
		return entrNo;
	}
	public void setEntrNo(String entrNo) {
		this.entrNo = entrNo;
	}
	public String getAceno() {
		return aceno;
	}
	public void setAceno(String aceno) {
		this.aceno = aceno;
	}
	// 삼천리 온라인 추가 필드
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getEquiType() {
		return equiType;
	}
	public void setEquiType(String equiType) {
		this.equiType = equiType;
	}
	public String getEquiTypeNm() {
		return equiTypeNm;
	}
	public void setEquiTypeNm(String equiTypeNm) {
		this.equiTypeNm = equiTypeNm;
	}
	public String getSequenceNum() {
		return sequenceNum;
	}
	public void setSequenceNum(String sequenceNum) { this.sequenceNum = sequenceNum; }

}
