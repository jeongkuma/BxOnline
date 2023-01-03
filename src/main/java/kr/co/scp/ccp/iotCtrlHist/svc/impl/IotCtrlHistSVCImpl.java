package kr.co.scp.ccp.iotCtrlHist.svc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.LinkedHashMap;
import kr.co.scp.ccp.iotCtrl.dto.TbIotCtrlDTO;
import kr.co.scp.ccp.iotCtrlHist.dao.IotCtrlHistDAO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotCtrlHistConditionReqDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotCtrlHistDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotCtrlHistResDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlReqDTO;
import kr.co.scp.ccp.iotCtrlHist.svc.IotCtrlHistSVC;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;

@Primary
@Slf4j
@Service
public class IotCtrlHistSVCImpl implements IotCtrlHistSVC {

	@Autowired
	private IotCtrlHistDAO iotCtrlHistDAO;

	@Override
	public HashMap<String, Object> retrieveIotCtrlHist(TbIotCtrlHistDTO tbIotCtrlHistDTO) {
		ComInfoDto temp = null;
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Integer count = 0;
		tbIotCtrlHistDTO.setCustSeq(Integer.parseInt(AuthUtils.getUser().getCustSeq()));

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,"iotCtrlHistDAO.retrieveIotCtrlCount");
		try {
			count = iotCtrlHistDAO.retrieveIotCtrlCount(tbIotCtrlHistDTO);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

		PageDTO pageDTO = new PageDTO();
		pageDTO.pageCalculate(count, tbIotCtrlHistDTO.getDisplayRowCount(), tbIotCtrlHistDTO.getCurrentPage());
		tbIotCtrlHistDTO.setStartPage(pageDTO.getRowStart());

		List<TbIotCtrlHistResDTO> retrieveIotCtrlHist = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCtrlHistDAO.retrieveIotCtrlHist");
		try {
			retrieveIotCtrlHist = iotCtrlHistDAO.retrieveIotCtrlHist(tbIotCtrlHistDTO);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

		resultMap.put("page", pageDTO);
		resultMap.put("ctrlList", retrieveIotCtrlHist);

		return resultMap;
	}

//	@Override
//	public List<TbIotDevMdlDTO> retrieveDevClsList() {
//		return iotCtrlHistDAO.retrieveDevClsList();
//	}

	@Override
	public List<TbIotDevMdlDTO> retrieveDevMdlList(TbIotDevMdlReqDTO tbIotDevMdlReqDTO) {

		List<TbIotDevMdlDTO> list = null;

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSelDeviceDAO.retrieveIotDevMAtbVal");
		try {
			list = iotCtrlHistDAO.retrieveDevMdlList(tbIotDevMdlReqDTO);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
		return list;
	}

//	@Override
//	public List<TbIotDevMdlDTO> retrieveDevColList() {
//		return iotCtrlHistDAO.retrieveDevColList();
//	}
//
//
//	@Override
//	public List<TbIotDevMdlDTO> retrieveDevPrcList() {
//		return iotCtrlHistDAO.retrieveDevPrcList();
//	}

	@Override
	public XSSFWorkbook excelCreate(TbIotCtrlHistDTO tbIotCtrlHistDTO) {

		List<TbIotCtrlHistDTO> retrieveDevCtrlList = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotSelDeviceDAO.retrieveIotDevMAtbVal");
		try {
			retrieveDevCtrlList = iotCtrlHistDAO.retrieveIotCtrlHistNotPage(tbIotCtrlHistDTO); // 제어이력 조회
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

		// excel
		XSSFWorkbook wb = new XSSFWorkbook();
		Map<String, String> title = new LinkedHashMap<String, String>();
		title.put("요청일", "requestDate");
		title.put("완료일", "resDate");
		title.put("사용자명", "custNm");
		title.put("CTN(가번)", "ctn");
		title.put("제어 상태", "prcCdNm");
		title.put("장비 유형", "devClsCdNm");
		title.put("모델명", "devMdlNm");
		title.put("제어 종류", "colNm");
		ExcelUtils.createExcelFile(wb, retrieveDevCtrlList, title);
		return wb;
		// return null;
	}

	@Override
	public HashMap<String, Object> retrieveIotCtrAllcondition(
			TbIotCtrlHistConditionReqDTO tbIotCtrlHistConditionReqDTO) {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		List<TbIotDevMdlDTO> colList = iotCtrlHistDAO.retrieveDevColList(tbIotCtrlHistConditionReqDTO);
		List<TbIotDevMdlDTO> prcList = iotCtrlHistDAO.retrieveDevPrcList(tbIotCtrlHistConditionReqDTO);

		resultMap.put("colList", colList);
		resultMap.put("prcList", prcList);

		return resultMap;
	}

	public void deleteIotCtrlList(TbIotCtrlHistDTO tbIotCtrlHistDTOList) throws BizException {

		ComInfoDto temp = null;
		TbIotCtrlHistDTO tbIotCtrlHistDTO = null;

		// 가입별장비순번 루프
		for (int i = 0; i < tbIotCtrlHistDTOList.getCtrList().size(); i++) {
			tbIotCtrlHistDTO = tbIotCtrlHistDTOList.getCtrList().get(i);
			tbIotCtrlHistDTO.setModUsrId(AuthUtils.getUser().getUserId());

			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCtrlDAO.deleteIotCtlM");
			try {
				iotCtrlHistDAO.deleteIotCtlM(tbIotCtrlHistDTO); // 제어정보 예약취소
			} catch (DataIntegrityViolationException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (UncategorizedSQLException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (MyBatisSystemException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (BadSqlGrammarException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} finally {
				OmsUtils.endInnerOms(temp);
			}

			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCtrlDAO.deleteIotCtlHist");
			try {
				iotCtrlHistDAO.deleteIotCtlHist(tbIotCtrlHistDTO); // 제어이력 예약취소
			} catch (DataIntegrityViolationException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (UncategorizedSQLException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (MyBatisSystemException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (BadSqlGrammarException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} finally {
				OmsUtils.endInnerOms(temp);
			}

		}
	}

}
