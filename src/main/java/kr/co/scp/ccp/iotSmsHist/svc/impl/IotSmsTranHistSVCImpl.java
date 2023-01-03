package kr.co.scp.ccp.iotSmsHist.svc.impl;

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
import kr.co.abacus.common.util.StringUtils;
import kr.co.scp.ccp.iotSmsHist.dao.IotSmsTranHistDAO;
import kr.co.scp.ccp.iotSmsHist.dto.TbIotSmsConditionDTO;
import kr.co.scp.ccp.iotSmsHist.dto.TbIotSmsConditiontReqDTO;
import kr.co.scp.ccp.iotSmsHist.dto.TbIotSmsTranHistDTO;
import kr.co.scp.ccp.iotSmsHist.svc.IotSmsTranHistSVC;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;

@Primary
@Service
public class IotSmsTranHistSVCImpl implements IotSmsTranHistSVC {

	ComInfoDto temp = null;

	@Autowired
	private IotSmsTranHistDAO iotSmsTranHistDAO;

	@Override
	public HashMap<String, Object> retrieveIotAlarmHistList(TbIotSmsTranHistDTO tbIotSmsTranHistDTO)
			throws BizException {
		PageDTO pageDTO = new PageDTO();

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		tbIotSmsTranHistDTO.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		tbIotSmsTranHistDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		Integer count = 0;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSmsTranHistDAO.retrieveIotAlarmCount");
		try {
			count = iotSmsTranHistDAO.retrieveIotAlarmCount(tbIotSmsTranHistDTO);
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

		pageDTO.pageCalculate(count, tbIotSmsTranHistDTO.getDisplayRowCount(), tbIotSmsTranHistDTO.getCurrentPage());
		tbIotSmsTranHistDTO.setStartPage(pageDTO.getRowStart());

		List<TbIotSmsTranHistDTO> retrieveIotAlarmHistList = new ArrayList<TbIotSmsTranHistDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSmsTranHistDAO.retrieveIotAlarmHistList");
		try {
			retrieveIotAlarmHistList = iotSmsTranHistDAO.retrieveIotAlarmHistList(tbIotSmsTranHistDTO);
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

		for (TbIotSmsTranHistDTO dto : retrieveIotAlarmHistList) {
			String trPhone = "";
			String trCallback = "";	
			if (!StringUtils.isEmpty(dto.getTrPhone())) {
				trPhone = dto.getTrPhone().replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-****-$3");
			} 
			if (!StringUtils.isEmpty(dto.getTrCallback())) {
				trCallback = dto.getTrCallback().replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-****-$3");
			} 

			dto.setTrPhone(trPhone);
			dto.setTrCallback(trCallback);
		}

		resultMap.put("page", pageDTO);
		resultMap.put("smsHistList", retrieveIotAlarmHistList);

		return resultMap;
	}

	@Override
	public HashMap<String, Object> retrieveIotAlarmConditionList(TbIotSmsConditiontReqDTO tbIotSmsConditiontReqDTO) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		List<TbIotSmsConditionDTO> trEtc1List = new ArrayList<TbIotSmsConditionDTO>();

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSmsTranHistDAO.retrieveTrEtc1List");
		try {
			trEtc1List = iotSmsTranHistDAO.retrieveTrEtc1List(tbIotSmsConditiontReqDTO);
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

		List<TbIotSmsConditionDTO> trEtc2List = new ArrayList<TbIotSmsConditionDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSmsTranHistDAO.retrieveTrEtc2List");
		try {
			trEtc2List = iotSmsTranHistDAO.retrieveTrEtc2List(tbIotSmsConditiontReqDTO);
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

		resultMap.put("trEtc1List", trEtc1List);
		resultMap.put("trEtc2List", trEtc2List);

		return resultMap;
	}

	@Override
	public XSSFWorkbook excelCreate(TbIotSmsTranHistDTO tbIotSmsTranHistDTO) {

		tbIotSmsTranHistDTO.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		tbIotSmsTranHistDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		List<TbIotSmsTranHistDTO> retrieveDevCtrlList = null;
		
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSmsTranHistDAO.retrieveIotSmsTranHistNotPage");
		try {
			retrieveDevCtrlList = iotSmsTranHistDAO.retrieveIotSmsTranHistNotPage(tbIotSmsTranHistDTO); // 제어이력 조회
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

		for (TbIotSmsTranHistDTO dto : retrieveDevCtrlList) {

			String trPhone = "";
			String trCallback = "";	
			if (!StringUtils.isEmpty(dto.getTrPhone())) {
				trPhone = dto.getTrPhone().replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-****-$3");
			} 
			if (!StringUtils.isEmpty(dto.getTrCallback())) {
				trCallback = dto.getTrCallback().replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-****-$3");
			} 

			dto.setTrPhone(trPhone);
			dto.setTrCallback(trCallback);
		}

		// excel
		XSSFWorkbook wb = new XSSFWorkbook();
		Map<String, String> title = new LinkedHashMap<String, String>();
		title.put("발송기간", "trSendDate");
		title.put("메세지 유형", "trEtc1Nm");
		title.put("등급", "trEtc2Nm");
		title.put("전송형태", "trMsgTypeNm");
		title.put("발송번호", "trCallback");
		title.put("수신자번호", "trPhone");
		title.put("발송메세지", "trMsg");
		ExcelUtils.createExcelFile(wb, retrieveDevCtrlList, title);
		return wb;
	}

}
