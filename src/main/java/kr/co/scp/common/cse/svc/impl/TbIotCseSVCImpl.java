package kr.co.scp.common.cse.svc.impl;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;

import kr.co.scp.common.cse.dao.TbIotCseDAO;
import kr.co.scp.common.cse.svc.TbIotCseSVC;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
//import kr.co.auiot.onem2m.svcsvr.dto.TbIoTOm2mSvcSvrDTO;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

//@Primary
//@Service
public class TbIotCseSVCImpl implements TbIotCseSVC {

//	@Autowired
//	private TbIotCseDAO tbIotCseDAO;
//
//	@Override
//	public HashMap<String, Object> retrieveTbIotCseList(TbIoTOm2mSvcSvrDTO tbIotCseDto) throws BizException {
//		ComInfoDto temp = null;
//		HashMap<String, Object> returnMap = new HashMap<String, Object>();
//		PageDTO pageDto = new PageDTO();
//
//		int totalCount = 0;
//		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//				"tbIotCseDAO.retrieveTbIotCseCount");
//		try {
//			totalCount = tbIotCseDAO.retrieveTbIotCseCount(tbIotCseDto);
//		} catch (MyBatisSystemException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (BadSqlGrammarException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (DataIntegrityViolationException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (UncategorizedSQLException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} finally {
//			OmsUtils.endInnerOms(temp);
//		}
//
//		List<TbIoTOm2mSvcSvrDTO> returnList = null;
//		pageDto.pageCalculate(totalCount, tbIotCseDto.getDisplayRowCount(), tbIotCseDto.getCurrentPage());
//		tbIotCseDto.setCurrentPage(pageDto.getRowStart());
//		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//				"tbIotCseDAO.retrieveTbIotCseList");
//		try {
//			returnList = tbIotCseDAO.retrieveTbIotCseList(tbIotCseDto);
//		} catch (MyBatisSystemException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (BadSqlGrammarException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (DataIntegrityViolationException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (UncategorizedSQLException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} finally {
//			OmsUtils.endInnerOms(temp);
//		}
//
//		returnMap.put("dataList", returnList);
//		returnMap.put("page", pageDto);
//		return returnMap;
//	}
//
//	@Override
//	public TbIoTOm2mSvcSvrDTO retrieveTbIotCse(TbIoTOm2mSvcSvrDTO tbIotCseDTO) throws BizException {
//		TbIoTOm2mSvcSvrDTO returnDto = null;
//
//		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//				"tbIotCseDAO.searchTbIotCseData");
//		try {
//			returnDto = tbIotCseDAO.retrieveTbIotCse(tbIotCseDTO);
//		} catch (MyBatisSystemException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (BadSqlGrammarException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (DataIntegrityViolationException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (UncategorizedSQLException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} finally {
//			OmsUtils.endInnerOms(temp);
//		}
//		return returnDto;
//	}
//
//	@Override
//	public void insertTbIotCse(TbIoTOm2mSvcSvrDTO tbIotCseDTO) throws BizException {
//		tbIotCseDTO.setRegUserId(AuthUtils.getUser().getUserId());
//		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotCseDAO.insertTbIotCse");
//
//		try {
//			tbIotCseDAO.insertTbIotCse(tbIotCseDTO);
//		} catch (MyBatisSystemException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (BadSqlGrammarException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (DataIntegrityViolationException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (UncategorizedSQLException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} finally {
//			OmsUtils.endInnerOms(temp);
//		}
//	}
//
//	@Override
//	public void updateTbIotCse(TbIoTOm2mSvcSvrDTO tbIotCseDTO) throws BizException {
//		tbIotCseDTO.setModUserId(AuthUtils.getUser().getUserId());
//		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//				"tbIotCseDAO.updateTbIotCse");
//		try {
//			tbIotCseDAO.updateTbIotCse(tbIotCseDTO);
//		} catch (MyBatisSystemException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (BadSqlGrammarException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (DataIntegrityViolationException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (UncategorizedSQLException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} finally {
//			OmsUtils.endInnerOms(temp);
//		}
//	}
//
//	@Override
//	public void deleteTbIotCse(TbIoTOm2mSvcSvrDTO tbIotCseDTO) throws BizException {
//		for (String svcCd : tbIotCseDTO.getCseSeqList()) {
//			ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//					"tbIotCseDAO.deleteTbIotCse");
//			try {
//				TbIoTOm2mSvcSvrDTO dto = new TbIoTOm2mSvcSvrDTO();
//				dto.setSvcCd(svcCd);
//				tbIotCseDAO.deleteTbIotCse(dto);
//			} catch (MyBatisSystemException e) {
//				OmsUtils.expInnerOms(temp, e);
//				throw e;
//			} catch (BadSqlGrammarException e) {
//				OmsUtils.expInnerOms(temp, e);
//				throw e;
//			} catch (DataIntegrityViolationException e) {
//				OmsUtils.expInnerOms(temp, e);
//				throw e;
//			} catch (UncategorizedSQLException e) {
//				OmsUtils.expInnerOms(temp, e);
//				throw e;
//			} finally {
//				OmsUtils.endInnerOms(temp);
//			}
//		}
//	}
//
//	@Override
//	public int duplicationCheck(TbIoTOm2mSvcSvrDTO tbIotCseDTO) throws BizException {
//		int chkCnt = 0;
//		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//				"tbIotCseDAO.duplicationCheck");
//		try {
//			chkCnt = tbIotCseDAO.duplicationCheck(tbIotCseDTO);
//		} catch (MyBatisSystemException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (BadSqlGrammarException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (DataIntegrityViolationException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (UncategorizedSQLException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} finally {
//			OmsUtils.endInnerOms(temp);
//		}
//		return chkCnt;
//	}
}
