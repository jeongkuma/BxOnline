package kr.co.scp.ccp.iagEvent.svc.impl;

import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.util.DateUtils;
import kr.co.abacus.common.util.StringUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.ccp.iagEvent.dto.IagEventCctvDTO;
import kr.co.scp.ccp.iagEvent.dto.TbIotEntrDevMapDTO;
import kr.co.scp.ccp.iagEvent.dto.TbIotIagLogDTO;
import kr.co.scp.ccp.iagEvent.dto.TbIotIagSvcDevDTO;
import kr.co.scp.ccp.iagEvent.svc.IagEntrDevMSVC;
import kr.co.scp.ccp.iagEvent.svc.IagEventCctvSVC;
import kr.co.scp.ccp.iagEvent.svc.IagLogSVC;
import kr.co.scp.ccp.iagEvent.svc.IagSvcDevSVC;
import kr.co.scp.ccp.iotSelDevice.dto.TbIotEntrDevMDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
iagEventCTL에다가 CCTV iag연동용 iagEvent2를 추가.
iagEvent를 참조하여 동일한 Flow로 처리하면 되는데
연동파라미터가 iagEvent와 다르기 때문에 이에 맞게 처리해야 함.

* IAG연동시 처리할 주요내용
1. iag연동로그 생성 (TB_IOT_IAG_LOG INSERT)
2. entity-id 생성
3. 산업IoT 가입처리
   - TB_IOT_ENTR_DEV_M
   - 산업IoT 디바이스 매핑정보 생성 (TB_IOT_ENTR_DEV_MAP)
4. 연동결과 cctv 전달 (cctv쪽 api 호출)
5. iag연동로그 업데이트 (
   - 4까지 성공했으면 STATUS='C'
   - 3까지는 성공이나 4에서 실패면 STATUS='F'


* entity-id는 TB_IOT_ENTR_DEV_M의 dev_uuid 컬럼에 들어가는 값으로
  deviceEntityIdByMac 메소드호출하여 만든다.
  - NODE_TYPE nodeType : ASN
  - String mac : equip_mac
  - String deviceSerialNo : equip_sn
  - String svcCd : tbIotIagSvcDevDTOList.get(0).getOm2mSvcCd()
  - boolean isShort : true

* subs_stus_code의 값에 따라 아래와 같이 처리한다.
01:신규
   1. 신규가입처리
      - TB_IOT_ENTR_DEV_M (C)
      - TB_IOT_ENTR_DEV_MAP (C)
   2. cctv연동
      - subs_stus_code, equip_mac, equip_sn

02:변경
   1. TB_IOT_ENTR_DEV_M을 조회한다.
      - WHERE ENTR_NO = subs_no(전문) AND STATUS_CD = 'A'
   2. Not-found면
      2.1. 신규가입처리
           - TB_IOT_ENTR_DEV_M (C)
           - TB_IOT_ENTR_DEV_MAP (C)
      2.2. cctv연동처리
           - 연동항목 : subs_stus_code, equip_mac, equip_sn
   3. Exists면
      테이블의 MACHINE_NO = equip_mac(전문), MANF_SERIAL_NO = equip_sn(전문) 일치여부 체크하고
      둘다 동일하면 아무처리도 안함.
      하나라도 다르면 변경처리 (기존데이터 해지, 신규데이터 신규가입)
      3.1. 기존 데이터 해지처리
           - TB_IOT_ENTR_DEV_M (U)
             (STATUS_CD = 'C', IAG_EVENT_CODE = subs_stus_code(전문), MOD_USR_ID='IAG', MOD_DTTM=SYSDATE)
      3.2. 새로 들어온 것은 신규가입처리
           - TB_IOT_ENTR_DEV_M (C)
           - TB_IOT_ENTR_DEV_MAP (C)
      3.3. cctv연동처리
           - 연동항목 : subs_stus_code, equip_mac, equip_sn

04:정지
   1. TB_IOT_ENTR_DEV_M을 조회한다. (WHERE ENTR_NO = subs_no(전문) AND STATUS_CD = 'A')
   2. Not-found면 skip
   3. Exists면 상태코드 업데이트함.
      - TB_IOT_ENTR_DEV_M (U)
        (STATUS_CD = 'S', IAG_EVENT_CODE = subs_stus_code(전문), MOD_USR_ID='IAG', MOD_DTTM=SYSDATE)
   4. cctv연동처리
           - 연동항목 : subs_stus_code, equip_mac, equip_sn

05:해제
   1. TB_IOT_ENTR_DEV_M을 조회한다. (WHERE ENTR_NO = subs_no(전문) AND STATUS_CD = 'S')
   2. Not-found면 skip
   3. Exists면 상태코드 업데이트함.
      - TB_IOT_ENTR_DEV_M (U)
        (STATUS_CD = 'A', IAG_EVENT_CODE = subs_stus_code(전문), MOD_USR_ID='IAG', MOD_DTTM=SYSDATE)
   4. cctv연동처리
           - 연동항목 : subs_stus_code, equip_mac, equip_sn

06:해지
   1. TB_IOT_ENTR_DEV_M을 조회한다. (WHERE ENTR_NO = subs_no(전문) AND STATUS_CD IN ('A','S'))
   2. Not-found면 skip
   3. Exists면 상태코드 업데이트함.
      - TB_IOT_ENTR_DEV_M (U)
        (STATUS_CD = 'C', IAG_EVENT_CODE = subs_stus_code(전문), MOD_USR_ID='IAG', MOD_DTTM=SYSDATE)
   4. cctv연동처리
           - 연동항목 : subs_stus_code, equip_mac, equip_sn

08:삭제
   06:해지와 동일처리
 */


@Slf4j
@Service
public class IagEventCctvSVCImpl implements IagEventCctvSVC {

	@Autowired
	private IagLogSVC iagLogSVC;

	@Autowired
	private IagSvcDevSVC iagSvcDevSVC;

	@Autowired
	private IagEntrDevMSVC iagEntrDevMSVC;

	/** @author dhj **/
//	@Autowired
//	private OneM2MService oneM2MService;

	@Value("${iag.cctv.url}")
	private String cctvUrl;

	@Value("${iag.cctv.custSeq}")
	private String custSeq;

	@Value("${iag.cctv.orgSeq}")
	private String orgSeq;

	@Override
	@Transactional
	public void iagEvent(IagEventCctvDTO iagEventCctvDTO, ComInfoDto comInfoDto) {

//		iagEventCctvDTO.setEquipMac(iagEventCctvDTO.getEquipMac().replaceAll("[^a-zA-Z0-9]", "").toUpperCase());
		String serialNo = StringUtils.leftPad(iagEventCctvDTO.getEquipSn(), 20, '0');
		String logKey = null;
//		String serialNo = iagEventCctvDTO.getEquipSn();
		
		// 1. iag연동로그 생성 (TB_IOT_IAG_LOG INSERT)
		TbIotIagLogDTO tbIotIagLogDTO = new TbIotIagLogDTO();
		tbIotIagLogDTO.setHeaderInfo(comInfoDto.getHeader().toString());
		tbIotIagLogDTO.setBodyInfo(comInfoDto.getBodyString());
		iagLogSVC.insertTbIotIagLog(tbIotIagLogDTO);
		tbIotIagLogDTO.setStatusCd("C");
		
		List<TbIotIagSvcDevDTO> tbIotIagSvcDevDTOList = iagSvcDevSVC.retrieveTbIotIagSvcDev(iagEventCctvDTO.getEquipModel());
		if(tbIotIagSvcDevDTOList.size() <= 0)
			return;

		// 2. 받은 전문으로 TbIotEntrDevMDTO 생성 
		String nowDttm = DateUtils.timeStamp(14);
		TbIotEntrDevMDTO tbIotEntrDevMDTO = new TbIotEntrDevMDTO();
		tbIotEntrDevMDTO.setDevMdlCd(iagEventCctvDTO.getEquipModel()); // equip_model(전문)
		tbIotEntrDevMDTO.setHldrCustNo("M00000000000001"); // 고정값
		tbIotEntrDevMDTO.setStatusCd("A"); // 고정값
		tbIotEntrDevMDTO.setIagEventCode(iagEventCctvDTO.getSubsStusCode()); // subs_stus_code(전문)
		tbIotEntrDevMDTO.setEntrNo(iagEventCctvDTO.getSubsNo()); // subs_no(전문)
		tbIotEntrDevMDTO.setItemId(iagEventCctvDTO.getEquipModel()); // equip_model(전문)
		tbIotEntrDevMDTO.setManfSerialNo(serialNo); // equip_sn(전문)
		tbIotEntrDevMDTO.setCtn(iagEventCctvDTO.getEquipMac()); // equip_mac(전문)
		tbIotEntrDevMDTO.setDevRegiDt(nowDttm.substring(0, 8));
		tbIotEntrDevMDTO.setInstDttm(nowDttm.substring(0, 8));
		tbIotEntrDevMDTO.setCustSeq(custSeq);
		tbIotEntrDevMDTO.setOrgSeq(orgSeq);
		tbIotEntrDevMDTO.setRegUsrId("IAG");
		tbIotEntrDevMDTO.setRegDttm(nowDttm);
		tbIotEntrDevMDTO.setModUsrId("IAG");
		tbIotEntrDevMDTO.setModDttm(nowDttm);
		
		TbIotEntrDevMapDTO tbIotEntrDevMapDTO = new TbIotEntrDevMapDTO();
		tbIotEntrDevMapDTO.setDeviceId(new BigInteger(iagEventCctvDTO.getEquipSn())); // equip_sn(전문)을 integer로 치환
		tbIotEntrDevMapDTO.setDevMdlCd(iagEventCctvDTO.getEquipModel()); // equip_model(전문)
		tbIotEntrDevMapDTO.setManfSerialNo(serialNo); // equip_sn(전문)
		tbIotEntrDevMapDTO.setMacAddr(iagEventCctvDTO.getEquipMac()); // equip_mac(전문)
		tbIotEntrDevMapDTO.setRegUsrId("IAG");
		tbIotEntrDevMapDTO.setRegDttm(nowDttm);
		tbIotEntrDevMapDTO.setModUsrId("IAG");
		tbIotEntrDevMapDTO.setModDttm(nowDttm);
		
		// svcCd 구하기 
		String svcCd = tbIotIagSvcDevDTOList.get(0).getSvcCd();
		String om2mSvcCd = tbIotIagSvcDevDTOList.get(0).getOm2mSvcCd();
		String devClsCd = tbIotIagSvcDevDTOList.get(0).getDevClsCd();
		TbIotEntrDevMDTO existTbIotEntrDevMDTO = iagEntrDevMSVC.retrieveTbIotEntrDevM(tbIotEntrDevMDTO);

		// 3. subs_stus_code의 값에 따라 아래와 같이 처리한다.
		String subsStusCode = iagEventCctvDTO.getSubsStusCode();
		int cnt = 0;
		switch(subsStusCode) {
		case "01" : // 신규
		case "02" : // 변경
			
			if (existTbIotEntrDevMDTO != null) { // 존재하면 삭제 처리하고 처리한다. 
				iagEntrDevMSVC.deleteTbIotEntrDevM(tbIotEntrDevMDTO);
			}

			/* @author dhj */
			// entity-id 생성
//			String entityId = oneM2MUtil.deviceEntityIdByMac(NODE_TYPE.ASN, iagEventCctvDTO.getEquipMac(), iagEventCctvDTO.getEquipSn(), om2mSvcCd, true);
			String entityId = "";
			if (entityId == null) { // entityId가 없으면 아무처리도 안한다.
				break; 
			}
			tbIotEntrDevMDTO.setSvcCd(svcCd); // 서비스코드
			tbIotEntrDevMDTO.setDevClsCd(devClsCd); // 장비유형
			tbIotEntrDevMDTO.setDevUuid(entityId); // entity_id
			iagEntrDevMSVC.insertTbIotEntrDevM(tbIotEntrDevMDTO);
			
			existTbIotEntrDevMDTO = tbIotEntrDevMDTO; // 아래 CCTV 연동시 필요한 정보가 있어서 
			
			cnt = iagEntrDevMSVC.updateTbIotEntrDevMap1(tbIotEntrDevMapDTO);
			if (cnt == 0)  iagEntrDevMSVC.insertTbIotEntrDevMap(tbIotEntrDevMapDTO);
			break;
		case "04" : // 정지
			tbIotEntrDevMDTO.setStatusCd("S");
			existTbIotEntrDevMDTO = iagEntrDevMSVC.retrieveTbIotEntrDevM(tbIotEntrDevMDTO);
			iagEntrDevMSVC.updateTbIotEntrDevM(tbIotEntrDevMDTO);

			cnt = iagEntrDevMSVC.updateTbIotEntrDevMap2(tbIotEntrDevMapDTO);
			
			// 리소스삭제 (deleteRemoteCse) => IAG연동일때만
			/** @author dhj **/
//			oneM2MService.deleteRemoteCse(om2mSvcCd);
			break;
		case "05" : // 해제
			tbIotEntrDevMDTO.setStatusCd("A");
			existTbIotEntrDevMDTO = iagEntrDevMSVC.retrieveTbIotEntrDevM(tbIotEntrDevMDTO);
			iagEntrDevMSVC.updateTbIotEntrDevM(tbIotEntrDevMDTO);
			
			TbIotEntrDevMapDTO tbIotEntrDevMDTO2 = iagEntrDevMSVC.retrieveIotEntrDevMap(tbIotEntrDevMapDTO);
			
			if (tbIotEntrDevMDTO2 == null) {
				iagEntrDevMSVC.insertTbIotEntrDevMap(tbIotEntrDevMapDTO);
			} else {
				iagEntrDevMSVC.updateTbIotEntrDevMap2(tbIotEntrDevMapDTO);
				
				// broker_ip, broker_port에 값이 있으면 Device, Reboot 호출 => IAG연동일때만
				String ip = tbIotEntrDevMDTO2.getBrokerIp();
				String port = tbIotEntrDevMDTO2.getBrokerPort();
				if (StringUtils.isNotEmpty(ip) && StringUtils.isNotEmpty(port) && existTbIotEntrDevMDTO != null) {
					String url = "http://"+ip+":"+port+"/onem2m/collector/"+existTbIotEntrDevMDTO.getDevUuid()+"/10250/0/1";
					// Device 리부팅 호출 
					Map head = new HashMap();
					head.put("CONTENT-TYPE","application/json");
					head.put("X-APIVERSION","1");
					head.put("X-COMMANDTYPE", "Device.Reboot");              // cctv와 협의된 값임
					logKey = DateUtils.timeStamp() + StringUtils.getRandomStr(4) + StringUtils.getRandomStr(4);
					head.put("X-LOGKEY", logKey); // 로그키생성규칙 적용
					head.put("X-UUID",existTbIotEntrDevMDTO.getDevUuid()); // entity-id임
					String body = "{\"con\":\"{\\\"command\\\":\\\"Device.Reboot\\\",\\\"logKey\\\":\\\""+logKey+"\\\",\\\"data\\\":{\\\"deviceID\\\":"+tbIotEntrDevMapDTO.getDeviceId()+"}}\",\"cnf\":\"text/plain\"}";
					ComInfoDto temp = OmsUtils.addInnerOms("IAG", OmsUtils.CHANNEL_TYPE_OUT, url);
					HttpRequestWithBody req = Unirest.post(url);
					req.headers(head);
					try {
						HttpResponse<String> res = req.body(body).asString();
					} catch (UnirestException e) {
						OmsUtils.expInnerOms(temp, e);
					} finally {
						OmsUtils.endInnerOms(temp);
					}
				}
			}
			break;
		case "06" : // 해지
		case "08" : // 삭제
			tbIotEntrDevMDTO.setStatusCd("C");
			existTbIotEntrDevMDTO = iagEntrDevMSVC.retrieveTbIotEntrDevM(tbIotEntrDevMDTO);
			iagEntrDevMSVC.updateTbIotEntrDevM(tbIotEntrDevMDTO);
			
			cnt = iagEntrDevMSVC.updateTbIotEntrDevMap2(tbIotEntrDevMapDTO);
			
			// 리소스삭제 (deleteRemoteCse) => IAG연동일때만
			/** @author dhj **/
//			oneM2MService.deleteRemoteCse(om2mSvcCd);
			break;
		}
			
		if (existTbIotEntrDevMDTO != null) { // 존재하지 않는다면 연동 처리 안함.  => IAG연동일때만
			switch(subsStusCode) { // "03" 일 경우 Skip 
			case "01" : // 신규
			case "02" : // 변경
			case "04" : // 정지
			case "05" : // 해제
			case "06" : // 해지
			case "08" : // 삭제
				// 4. 연동결과 cctv 전달 (cctv쪽 api 호출)
				// - 연동항목 : subs_stus_code, equip_mac, equip_sn
				Map head = new HashMap();
				Map body = new HashMap();
				Map data = new HashMap();
				head.put("content-type","application/json");
				head.put("x-apiversion","1");
				head.put("x-devclscd",existTbIotEntrDevMDTO.getDevClsCd());            // 출입관리기 장비유형코드임
				head.put("x-logkey",DateUtils.timeStamp() + StringUtils.getRandomStr(4) + StringUtils.getRandomStr(4)); // 로그키생성규칙 적용
				head.put("x-commandtype", "prov");              // cctv와 협의된 값임
				head.put("x-lang", "ko");
				head.put("x-channel","OUT");
				head.put("x-uuid",existTbIotEntrDevMDTO.getDevUuid()); // entity-id임
				head.put("x-service","UISVC");                  // 온라인서버
				head.put("x-devmdlcd",existTbIotEntrDevMDTO.getDevMdlCd());              // 단말기모델임
				body.put("command", "prov");
				body.put("data", data);
				data.put("subs_stus_code", iagEventCctvDTO.getSubsStusCode());
				data.put("equip_mac", iagEventCctvDTO.getEquipMac());
				data.put("equip_sn", iagEventCctvDTO.getEquipSn());
				
				ComInfoDto temp = OmsUtils.addInnerOms("IAG", OmsUtils.CHANNEL_TYPE_OUT, cctvUrl);
				HttpRequestWithBody req = Unirest.post(cctvUrl);
				req.headers(head);
				try {
					HttpResponse<String> res = req.body(body).asString();
					if (res.getStatus() != 200) {
						tbIotIagLogDTO.setStatusCd("F");
					}
				} catch (UnirestException e) {
					OmsUtils.expInnerOms(temp, e);
					tbIotIagLogDTO.setStatusCd("F");
				} finally {
					OmsUtils.endInnerOms(temp);
				}
			}
		}
		
		// 5. iag연동로그 업데이트 (4까지 성공했으면 STATUS='C', 3까지는 성공이나 4에서 실패면 STATUS='F')
		iagLogSVC.updateTbIotIagLog(tbIotIagLogDTO);
	}
	
	public String getFuncId(String subsStusCode) {
		switch(subsStusCode) {
		case "01": return "FN02021";
		case "02": return "FN02022";
		case "04": return "FN02024";
		case "05": return "FN02025";
		case "06": return "FN02026";
		case "08": return "FN02028";
		}

		return "FN02021";
	}
		
}
