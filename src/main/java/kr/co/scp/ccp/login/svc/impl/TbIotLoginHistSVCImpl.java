package kr.co.scp.ccp.login.svc.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.co.abacus.common.dto.common.ResultDescDto;
import lombok.extern.slf4j.Slf4j;
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
import kr.co.abacus.common.util.DateUtils;
import kr.co.abacus.common.util.LinkedHashMap;
import kr.co.scp.ccp.login.dao.LoginHistDAO;
import kr.co.scp.ccp.login.dto.TbIoTLoginHistDTO;
import kr.co.scp.ccp.login.dto.TbIoTLoginHistListDTO;
import kr.co.scp.ccp.login.svc.TbIotLoginHistSVC;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;

@Primary
@Service
@Slf4j
public class TbIotLoginHistSVCImpl implements TbIotLoginHistSVC{

	@Autowired
	private LoginHistDAO loginHistDAO;

	// 로그인 이력 조회
	@Override
	public HashMap<String, Object> retrieveTbIotLoginHist(TbIoTLoginHistDTO tbIoTLoginHistDTO) throws BizException {
		tbIoTLoginHistDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		tbIoTLoginHistDTO.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		tbIoTLoginHistDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
		PageDTO pageDTO = new PageDTO();
		HashMap<String, Object> rnMap = new HashMap<String, Object>();
		Integer count = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"loginHistDAO.retrieveLoginHistCount");
		try {
			count = loginHistDAO.retrieveLoginHistCount(tbIoTLoginHistDTO);
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
		pageDTO.pageCalculate(count, tbIoTLoginHistDTO.getDisplayRowCount(), tbIoTLoginHistDTO.getCurrentPage());
		tbIoTLoginHistDTO.setStartPage(pageDTO.getRowStart());
		List<TbIoTLoginHistListDTO> loginHistList = new ArrayList<TbIoTLoginHistListDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,	"loginHistDAO.retrieveLoginHist");
		try {
			loginHistList = loginHistDAO.retrieveLoginHist(tbIoTLoginHistDTO);
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
//		for (Iterator logIterator = loginHistList.iterator(); logIterator.hasNext();) {
//			TbIoTLoginHistListDTO logDto = (TbIoTLoginHistListDTO) logIterator.next();
//			try {
//				logDto.setHistRegDttm(DateUtils.getDateByFormatToString(logDto.getHistRegDttm(), "yyyy-MM-dd kk:mm:ss", "yyyy-MM-dd kk:mm:ss"));
//			} catch (Exception e) {
//				throw new BizException(e, new ResultDescDto());
//			}
//		}

		rnMap.put("loginHistList", loginHistList);
		rnMap.put("page", pageDTO);
		return rnMap;
	}

	@Override
	public XSSFWorkbook excelCreate(TbIoTLoginHistDTO tbIoTLoginHistDTO) throws BizException {
		tbIoTLoginHistDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		tbIoTLoginHistDTO.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		tbIoTLoginHistDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
		List<TbIoTLoginHistListDTO> retrieveDevCtrlList = new ArrayList<TbIoTLoginHistListDTO>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"loginHistDAO.retrieveIotLoginHistNotPage");
		try {
			retrieveDevCtrlList = loginHistDAO.retrieveIotLoginHistNotPage(tbIoTLoginHistDTO);
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
		title.put("접속일시", "histRegDttm");
		title.put("로그인ID", "usrLoginId");
		title.put("성공여부", "loginSuccYn");
		title.put("로그인IP", "clientIp");
		title.put("로그인OS", "usrOs");
		title.put("로그인Browser", "usrBrowser");
		title.put("로그인 실패사유", "loginFailReason");
		ExcelUtils.createExcelFile(wb, retrieveDevCtrlList, title);
		return wb;
	}
}
