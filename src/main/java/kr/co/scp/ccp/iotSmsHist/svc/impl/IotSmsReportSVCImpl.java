package kr.co.scp.ccp.iotSmsHist.svc.impl;

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
import kr.co.scp.ccp.iotSmsHist.dao.IotSmsTranHistDAO;
import kr.co.scp.ccp.iotSmsHist.dto.TbIotSmsTranHistDTO;
import kr.co.scp.ccp.iotSmsHist.svc.IotSmsReportSVC;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;

@Primary
@Service
public class IotSmsReportSVCImpl implements IotSmsReportSVC {

	@Autowired
	private IotSmsTranHistDAO iotSmsTranHistDAO;

	@Override
	public HashMap<String, Object> retrieveIotSmsReportList(TbIotSmsTranHistDTO tbIotSmsTranHistDTO)
			throws BizException {
		PageDTO pageDTO = new PageDTO();

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		tbIotSmsTranHistDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		tbIotSmsTranHistDTO.setRoleCdId(AuthUtils.getUser().getRoleCdId());

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSmsTranHistDAO.retrieveIotSmsReportCount");

		int count = 0;
		try {
			count = iotSmsTranHistDAO.retrieveIotSmsReportCount(tbIotSmsTranHistDTO);
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
		tbIotSmsTranHistDTO.setEndPage(pageDTO.getRowEnd());

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSmsTranHistDAO.retrieveIotSmsReportList");
		List<TbIotSmsTranHistDTO> retrieveSmsReportList = null;
		try {
			retrieveSmsReportList = iotSmsTranHistDAO.retrieveIotSmsReportList(tbIotSmsTranHistDTO);
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
		resultMap.put("smsReportList", retrieveSmsReportList);

		return resultMap;
	}

	@Override
	public XSSFWorkbook excelCreate(TbIotSmsTranHistDTO tbIotSmsTranHistDTO) {

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSmsTranHistDAO.retrieveIotSmsReportList");
		List<TbIotSmsTranHistDTO> retrieveSmsReportList = null;
		tbIotSmsTranHistDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		tbIotSmsTranHistDTO.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		try {
			retrieveSmsReportList = iotSmsTranHistDAO.retrieveIotSmsReportList(tbIotSmsTranHistDTO);
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
		title.put("기간", "trSendDate");
		title.put("고객사 ID", "custLoginId");
		title.put("고객사명", "custNm");
		title.put("메시지 유형", "trEtc1Nm");
		title.put("발송 건수", "sendCnt");
		title.put("성공 건수", "successCnt");
		title.put("실패 건수", "failCnt");
		ExcelUtils.createExcelFile(wb, retrieveSmsReportList, title);
		return wb;
	}

}
