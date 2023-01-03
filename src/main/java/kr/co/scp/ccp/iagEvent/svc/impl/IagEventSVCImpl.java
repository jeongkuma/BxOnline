package kr.co.scp.ccp.iagEvent.svc.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.scp.ccp.iagEvent.dto.IagEventDTO;
import kr.co.scp.ccp.iagEvent.dto.IagEventDeviceDTO;
import kr.co.scp.ccp.iagEvent.dto.TbIotIagEntrDevCurValDTO;
import kr.co.scp.ccp.iagEvent.dto.TbIotIagEntrDevDTO;
import kr.co.scp.ccp.iagEvent.dto.TbIotIagEntrDevEvtHistDTO;
import kr.co.scp.ccp.iagEvent.dto.TbIotIagLogDTO;
import kr.co.scp.ccp.iagEvent.dto.TbIotIagSvcDevDTO;
import kr.co.scp.ccp.iagEvent.svc.IagEntrDevCurValSVC;
import kr.co.scp.ccp.iagEvent.svc.IagEntrDevEvtHistSVC;
import kr.co.scp.ccp.iagEvent.svc.IagEntrDevSVC;
import kr.co.scp.ccp.iagEvent.svc.IagEventSVC;
import kr.co.scp.ccp.iagEvent.svc.IagLogSVC;
import kr.co.scp.ccp.iagEvent.svc.IagSvcDevSVC;

import lombok.extern.slf4j.Slf4j;

/*
 * 처리하는 event_code 와  STATUS_CD 매핑
 *  - 가입 (C16^NAC): R
 *  - 해지/폰개통취소 (C17^CAN): C
 *  - 일시정지 (SUS^xx): S
 *  - 일시정지해제 (RSP^xx): A
 *  - 기기변경 (C07^xx): A
 *  - 개통해지취소(RCL^xx, C16^RCL): A
 *  - USIM변경 (C17^UEX, C16^GSA): A
 *  - USIM변경취소 (C17^GSD, C16^GSA): A
 */

/*
 * 서비스 처리 로직
 * 1. insert [TB_IOT_IAG_LOG]
 * 2. event_code 분석하여 정의된 code가 아니면 처리 안함
 * 3. prodNo를 ctn으로 변환
 * 4. 수신데이터의 device[]에 대하여 반복
 * 5. device 정보가 편성되어 있는 경우만 처리
 * 6. TbIotIagEntrDevDTO 설정 
 * 7. select [TB_IOT_ENTR_DEV_M]
 *    7.1. 데이터가 없고, C16^NAC 이면,
 *         - insert [TB_IOT_ENTR_DEV_M]
 *         - select [TB_IOT_S_DEV_ATB] insert [TB_IOT_E_DEV_CUR_VAL]
 *    7.2. 데이터가 있고, C17^CAN 이면,
 *         - select [TB_IOT_ENTR_DEV_M] (DEV_IMG_PATH)
 *         - 장비 이미지파일 삭제
 *         - update [TB_IOT_ENTR_DEV_M]
 *    7.3. 데이터가 있고, C17^CAN 아니면,
 *         - update [TB_IOT_ENTR_DEV_M]
 * 8. 성공처리된 장비가 하나라도 있으면,
 *    8.1. 장비 이벤트 기록해야 하면, insert [TB_IOT_E_DEV_EVT_H]
 *    8.2. update [TB_IOT_IAG_LOG] (STATUS_CD = "C")
 */


@Slf4j
@Service
public class IagEventSVCImpl implements IagEventSVC {

	final static private String STATUS_CD_RESERVE   = "R";
	final static private String STATUS_CD_ACTIVE    = "A";
	final static private String STATUS_CD_SUSPEND   = "S";
	final static private String STATUS_CD_CLOSED    = "C";
	final static private String STATUS_CD_UNDEFINED = "U";

	@Autowired
	private IagLogSVC iagLogSVC;

	@Autowired
	private IagSvcDevSVC iagSvcDevSVC;

	@Autowired
	private IagEntrDevSVC iagEntrDevSVC;

	@Autowired
	private IagEntrDevCurValSVC iagEntrDevCurValSVC;

	@Autowired
	private IagEntrDevEvtHistSVC iagEntrDevEvtHistSVC;
	
	@Value("${file.upload-dir-devImg}")
	private String baseDevImgPath;

	@Override
	public void iagEvent(IagEventDTO iagEventDTO, ComInfoDto comInfoDto) {

		// 1. insert [TB_IOT_IAG_LOG]
		TbIotIagLogDTO tbIotIagLogDTO = new TbIotIagLogDTO();
		tbIotIagLogDTO.setHeaderInfo(comInfoDto.getHeader().toString());
		tbIotIagLogDTO.setBodyInfo(comInfoDto.getBodyString());
		iagLogSVC.insertTbIotIagLog(tbIotIagLogDTO);

		// 2. event_code 분석하여 정의된 code가 아니면 처리 안함
		String refineEventCode = refineEventCode(iagEventDTO.getEventCode());
		String statusCd = getStatusCd(refineEventCode);
		if(statusCd == STATUS_CD_UNDEFINED)
			return;

		// 3. prodNo를 ctn으로 변환
		String ctn = changeProdNo2Ctn(iagEventDTO.getIagEventSubsDTO().getProdNo());

		// 4. 수신데이터의 device[]에 대하여 반복
		int succDevCnt = 0;
		for(IagEventDeviceDTO iagEventDeviceDTO : iagEventDTO.getIagEventDeviceDTOList()) {

			// 5. device 정보가 편성되어 있는 경우만 처리
			List<TbIotIagSvcDevDTO> tbIotIagSvcDevDTOList = iagSvcDevSVC.retrieveTbIotIagSvcDev(iagEventDeviceDTO.getItemId());
			if(tbIotIagSvcDevDTOList.size() <= 0)
				continue;

			// 6. TbIotIagEntrDevDTO 설정 
			TbIotIagEntrDevDTO tbIotIagEntrDevDTO = new TbIotIagEntrDevDTO();
			tbIotIagEntrDevDTO.setEventCode(iagEventDTO.getEventCode());
			tbIotIagEntrDevDTO.setIagEventCode(iagEventDTO.getIagEventCode());
			tbIotIagEntrDevDTO.setTbIotIagSvcDevDTO(tbIotIagSvcDevDTOList.get(0));
			tbIotIagEntrDevDTO.setIagEventSubsDTO(iagEventDTO.getIagEventSubsDTO());
			tbIotIagEntrDevDTO.setIagEventDeviceDTO(iagEventDeviceDTO);
			tbIotIagEntrDevDTO.getTbIotIagSvcDevDTO().setStatusCd(statusCd);
			tbIotIagEntrDevDTO.setCtn(ctn);
			tbIotIagEntrDevDTO.setDevUuid(createDevUuid(ctn, iagEventDeviceDTO.getIccid(), tbIotIagSvcDevDTOList.get(0).getOm2mSvcCd()));
			tbIotIagEntrDevDTO.setBaseSerialNo(refineEventCode.equals("C07") ? tbIotIagEntrDevDTO.getIagEventDeviceDTO().getPrevManfSerialNo() : tbIotIagEntrDevDTO.getIagEventDeviceDTO().getManfSerialNo());

			// 7. select [TB_IOT_ENTR_DEV_M]
			int entrDevCnt = iagEntrDevSVC.retrieveTbIotIagEntrDev(tbIotIagEntrDevDTO);

			// 7.1. 데이터가 없고, C16^NAC 이면,
			if(entrDevCnt <= 0 && iagEventDTO.getEventCode().equals("C16^NAC")) {
				// insert [TB_IOT_ENTR_DEV_M]
				iagEntrDevSVC.insertTbIotIagEntrDev(tbIotIagEntrDevDTO);

				// select [TB_IOT_S_DEV_ATB] insert [TB_IOT_E_DEV_CUR_VAL]
				TbIotIagEntrDevCurValDTO tbIotIagEntrDevCurValDTO = new TbIotIagEntrDevCurValDTO();
				tbIotIagEntrDevCurValDTO.setEntrDevSeq(tbIotIagEntrDevDTO.getEntrDevSeq());
				tbIotIagEntrDevCurValDTO.setDevMdlCd(tbIotIagSvcDevDTOList.get(0).getDevMdlCd());
				tbIotIagEntrDevCurValDTO.setDevClsMdlCd("LGE_" + tbIotIagSvcDevDTOList.get(0).getDevClsCd());
				iagEntrDevCurValSVC.insertTbIotIagEntrDevCurVal(tbIotIagEntrDevCurValDTO);
				
				succDevCnt++;
			}
			// 7.2. 데이터가 있고, C17^CAN 이면,
			else if(entrDevCnt > 0 && iagEventDTO.getEventCode().equals("C17^CAN")) {
				// select [TB_IOT_ENTR_DEV_M] (DEV_IMG_PATH)
				List<String> devImgPathList = iagEntrDevSVC.retrieveTbIotIagEntrDevImg(tbIotIagEntrDevDTO);

				// 장비 이미지파일 삭제
				for(String devImgPath : devImgPathList) {
					if(devImgPath != null)
						deleteFile(baseDevImgPath + devImgPath);
				}

				// update [TB_IOT_ENTR_DEV_M]
				iagEntrDevSVC.updateTbIotIagEntrDevC17CAN(tbIotIagEntrDevDTO);

				succDevCnt++;
			}
			// 7.3. 데이터가 있고, C17^CAN 아니면,
			else if(entrDevCnt > 0 && !iagEventDTO.getEventCode().equals("C17^CAN")) {
				// update [TB_IOT_ENTR_DEV_M]
				iagEntrDevSVC.updateTbIotIagEntrDev(tbIotIagEntrDevDTO);

				succDevCnt++;
			}
		}

		// 8. 성공처리된 장비가 하나라도 있으면,
		if(succDevCnt > 0) {
			// 8.1. 장비 이벤트 기록해야 하면,
			if(isNeedEvtHist(refineEventCode)) {
				// insert [TB_IOT_E_DEV_EVT_H]
				TbIotIagEntrDevEvtHistDTO tbIotIagEntrDevEvtHistDTO = new TbIotIagEntrDevEvtHistDTO();
				tbIotIagEntrDevEvtHistDTO.setCtn(ctn);
				tbIotIagEntrDevEvtHistDTO.setEventCode(refineEventCode);
				tbIotIagEntrDevEvtHistDTO.setStatusCd(statusCd);
				iagEntrDevEvtHistSVC.insertTbIotIagEntrDevEvtHist(tbIotIagEntrDevEvtHistDTO);
			}
	
			// 8.2. update [TB_IOT_IAG_LOG] (STATUS_CD = "C")
			tbIotIagLogDTO.setStatusCd("C");
			iagLogSVC.updateTbIotIagLog(tbIotIagLogDTO);
		}
	}

	public String getFuncId(String eventCode) {
		switch(eventCode) {
		case "C16^NAC": return "FN02002";
		case "C17^UEX": return "FN02006";
		case "C17^GSD": return "FN02007";
		case "C16^GSA": return "FN02008";
		case "C17^CAN": return "FN02009";
		case "C16^RCL": return "FN02011";
		}

		if(eventCode.length() >= 3)	{
			switch(eventCode.substring(0, 3)) {
			case "SUS": return "FN02003"; 
			case "RSP": return "FN02004";
			case "C07": return "FN02005";
			case "RCL": return "FN02010";
			}
		}

		return "FN02001";
	}

	private String refineEventCode(String eventCode) {
		switch(eventCode) {
		case "C16^GSA":
		case "C16^NAC":
		case "C16^RCL":
		case "C17^CAN":
		case "C17^GSD":
		case "C17^UEX":
			return eventCode;
		}

		if(eventCode.length() >= 3)	{
			switch(eventCode.substring(0, 3)) {
			case "C07":
			case "RCL":
			case "RSP":
			case "SUS":
				return eventCode.substring(0, 3);
			}
		}

		return eventCode;
	}

	private String getStatusCd(String refineEventCode) {
		switch(refineEventCode) {
		case "C16^NAC":
			return STATUS_CD_RESERVE;
		case "C17^CAN":
			return STATUS_CD_CLOSED;
		case "SUS":
			return STATUS_CD_SUSPEND;
		case "C07":
		case "RCL":
		case "RSP":
		case "C16^GSA":
		case "C16^RCL":
		case "C17^GSD":
		case "C17^UEX":
			return STATUS_CD_ACTIVE;
		}

		return STATUS_CD_UNDEFINED;
	}

	private boolean isNeedEvtHist(String refineEventCode) {
		switch(refineEventCode) {
		case "SUS":
		case "RSP":
		case "RCL":
		case "C16^RCL":
		case "C17^CAN":
			return true;
		}

		return false;
	}

	private String changeProdNo2Ctn(String prodNo) {
		String ctn = prodNo;

		if((prodNo.length() > 10) && (prodNo.charAt(3) == '0'))
			ctn = prodNo.substring(0, 3) + prodNo.substring(4);

		return ctn;
	}

	private String createDevUuid(String ctn, String iccId, String svcCd) {
		iccId = iccId.length() >= 20 ? iccId.substring(0, 19) : iccId;

		/** @author dhj **/
//		String entityId = oneM2MUtil.deviceEntityIdByCtn(NODE_TYPE.ASN, ctn, iccId, svcCd, true);

//		return (entityId != null ? entityId : "");
		return "";
	}

	private void deleteFile(String filename) {
//		File file = new File(filename);
//
//		if(file.exists())
//			file.delete();
	}

}
