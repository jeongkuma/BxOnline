package kr.co.scp.common.prog.svc.impl;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.common.prog.dao.TbIotProgDAO;
import kr.co.scp.common.prog.dto.TbIotAuthProgDTO;
import kr.co.scp.common.prog.dto.TbIotProgDTO;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;


//public class TbIotProgSVCImpl {
@Primary
@Service
public class TbIotProgSVCImpl implements kr.co.scp.common.prog.svc.TbIotProgSVC{


	@Autowired
	private TbIotProgDAO tbIotProgDAO;

	@Override
	public HashMap<String, Object> retrieveTbIotProgList(TbIotProgDTO tbIotProgDto) throws BizException {
		ComInfoDto temp = null;
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageDTO pageDto = new PageDTO();

		int totalCount = 0;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotProgDAO.retrieveTbIotPorgCount");
		try {
			totalCount = tbIotProgDAO.retrieveTbIotPorgCount(tbIotProgDto);
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

		List<TbIotProgDTO> returnList = null;
		pageDto.pageCalculate(totalCount, tbIotProgDto.getDisplayRowCount(), tbIotProgDto.getCurrentPage());
		tbIotProgDto.setCurrentPage(pageDto.getRowStart());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotProgDAO.retrieveTbIotProgList");
		try {
			returnList = tbIotProgDAO.retrieveTbIotProgList(tbIotProgDto);
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

		returnMap.put("dataList", returnList);
		returnMap.put("page", pageDto);
		return returnMap;
	}

	@Override
	public void deleteTbIotProg(TbIotProgDTO tbIotProgDto) throws BizException {
		for (String progSeq : tbIotProgDto.getProgSeqList()) {
			TbIotProgDTO delProgDto = new TbIotProgDTO();
			delProgDto.setProgSeq(progSeq);
			ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"tbIotProgDAO.deleteTbIotProg");
			try {
				tbIotProgDAO.deleteTbIotProg(delProgDto);
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
		}
	}

	@Override
	public void updateTbIotProg(TbIotProgDTO tbIotProgDto) throws BizException {
		tbIotProgDto.setModUserId(AuthUtils.getUser().getUserId());
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotProgDAO.updateTbIotProg");
		try {
			tbIotProgDAO.updateTbIotProg(tbIotProgDto);
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

	}

	@Override
	public void insertTbIotProg(TbIotProgDTO tbIotProgDto) throws BizException {
		tbIotProgDto.setRegUserId(AuthUtils.getUser().getUserId());
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotProgDAO.insertTbIotProg");
		try {
			tbIotProgDAO.insertTbIotProg(tbIotProgDto);
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
	}

	@Override
	public TbIotProgDTO searchTbIotProgData(TbIotProgDTO tbIotProgDto) throws BizException {
		// TODO Auto-generated method stub
		TbIotProgDTO returnDto = null;

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotProgDAO.searchTbIotProgData");
		try {
			returnDto = tbIotProgDAO.searchTbIotProgData(tbIotProgDto);
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
		return returnDto;
	}

	@Override
	public int duplicationCheck(TbIotProgDTO tbIotProgDto) throws BizException {
		// TODO Auto-generated method stub
		int chkCnt = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotProgDAO.duplicationCheck");
		try {
			chkCnt = tbIotProgDAO.duplicationCheck(tbIotProgDto);
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
		return chkCnt;
	}

	@Override
	public HashMap<String, Object> autoIotProgId(TbIotProgDTO tbIotProgDto) throws BizException {
		// TODO Auto-generated method stub
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotProgDAO.autoIotProgId");
		try {
			returnMap = tbIotProgDAO.autoIotProgId(tbIotProgDto);
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
		return returnMap;
	}

	@Override
	public List<TbIotAuthProgDTO> retrieveAuthProgList(TbIotAuthProgDTO tbIotAuthProgDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotProgDAO.duplicationCheck");
		List<TbIotAuthProgDTO> authProgList = null;
		tbIotAuthProgDTO.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		tbIotAuthProgDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
		try {
			authProgList = tbIotProgDAO.retrieveAuthProgList(tbIotAuthProgDTO);
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
		return authProgList;
	}
}
