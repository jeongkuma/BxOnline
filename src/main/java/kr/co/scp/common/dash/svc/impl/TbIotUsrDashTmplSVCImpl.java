package kr.co.scp.common.dash.svc.impl;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.common.api.dao.TbIotParamDAO;
import kr.co.scp.common.api.dto.TbIotParamDTO;
import kr.co.scp.common.dash.dao.TbIotUsrDashTmplDAO;
import kr.co.scp.common.dash.dto.TbIotUsrDashTmplDTO;
import kr.co.scp.common.dash.svc.TbIotUsrDashTmplSVC;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

@Primary
@Service
public class TbIotUsrDashTmplSVCImpl implements TbIotUsrDashTmplSVC {

	@Autowired
	private TbIotUsrDashTmplDAO tbIotUsrDashTmplDao;

	@Autowired
	private TbIotParamDAO tbIotParamDao;

	@Override
	public List<TbIotUsrDashTmplDTO> retrieveTbIotUsrDashTmplList(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto)
			throws BizException {
		if (null == tbIotUsrDashTmplDto.getOrgSeq() || "".equals(tbIotUsrDashTmplDto.getOrgSeq())) {
			String OrgSeq = AuthUtils.getUser().getOrgSeq();
			tbIotUsrDashTmplDto.setOrgSeq(OrgSeq);
		}

		List<TbIotUsrDashTmplDTO> returntbIotUsrDashTmplDto = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotUsrDashTmplDao.retrieveTbIotUsrDashTmplList");
		try {
			returntbIotUsrDashTmplDto = tbIotUsrDashTmplDao.retrieveTbIotUsrDashTmplList(tbIotUsrDashTmplDto);
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
		return returntbIotUsrDashTmplDto;
	}

	@Override
	@Transactional
	public void deleteTbIotUsrDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotUsrDashTmplDao.deleteTbIotUsrDashTmpl");
		try {
			tbIotUsrDashTmplDao.deleteTbIotUsrDashTmpl(tbIotUsrDashTmplDto);
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
	@Transactional
	public void insertTbIotUsrDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotUsrDashTmplDao.insertTbIotUsrDashTmpl");
		try {
			tbIotUsrDashTmplDao.insertTbIotUsrDashTmpl(tbIotUsrDashTmplDto);
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
	public HashMap<String, Object> retrieveTbIotDashTmplList(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto)
			throws BizException {
		ComInfoDto temp = null;
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageDTO pageDto = new PageDTO();
		tbIotUsrDashTmplDto.setCustSeq(AuthUtils.getUser().getCustSeq());
		tbIotUsrDashTmplDto.setUserRole(AuthUtils.getUser().getRoleCdId());
		tbIotUsrDashTmplDto.setSvcCd(AuthUtils.getUser().getSvcCd());
		List<TbIotUsrDashTmplDTO> dashTmplList = null;
		int totalCount = 0;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotUsrDashTmplDao.retrieveTbIotDashTmplCnt");
		try {
			totalCount = tbIotUsrDashTmplDao.retrieveTbIotDashTmplCnt(tbIotUsrDashTmplDto);
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

		pageDto.pageCalculate(totalCount, tbIotUsrDashTmplDto.getDisplayRowCount(),
				tbIotUsrDashTmplDto.getCurrentPage());
		tbIotUsrDashTmplDto.setCurrentPage(pageDto.getRowStart());

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotUsrDashTmplDao.retrieveTbIotDashTmplList");
		try {
			dashTmplList = tbIotUsrDashTmplDao.retrieveTbIotDashTmplList(tbIotUsrDashTmplDto);
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

		for (int i = 0; i < dashTmplList.size(); i++) {
			TbIotParamDTO tbIotParamDto = new TbIotParamDTO();
			tbIotParamDto.setApiSeq(dashTmplList.get(i).getApiSeq());

			List<TbIotParamDTO> paramList = null;
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"tbIotParamDao.retrieveTbIotParamList");
			try {
				paramList = tbIotParamDao.retrieveTbIotParamList(tbIotParamDto);
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
			dashTmplList.get(i).setTbIotParamDTOList(paramList);
		}

		returnMap.put("dataList", dashTmplList);
		returnMap.put("page", pageDto);
		return returnMap;
	}

	@Override
	@Transactional
	public void insertTbIotDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException {
		// 로그인 이후 변경
		//tbIotUsrDashTmplDto.setOrgSeq(Integer.parseInt(AuthUtils.getUser().getOrgSeq()));
		tbIotUsrDashTmplDto.setCustSeq(AuthUtils.getUser().getCustSeq());
		tbIotUsrDashTmplDto.setRegUserId(AuthUtils.getUser().getUserId());

		String reqMsg = tbIotUsrDashTmplDto.getReqMsg();
		String rspMsg = tbIotUsrDashTmplDto.getRspMsg();


		try {
			tbIotUsrDashTmplDto.setReqMsg(URLDecoder.decode(reqMsg, "UTF-8"));
			tbIotUsrDashTmplDto.setRspMsg(URLDecoder.decode(rspMsg, "UTF-8"));
		} catch (Exception e) {
			throw new BizException("URLDecode Error!");
		}
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotUsrDashTmplDao.insertTbIotDashTmpl");
		try {
			tbIotUsrDashTmplDao.insertTbIotDashTmpl(tbIotUsrDashTmplDto);

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
	@Transactional
	public void updateTbIotDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException {
		String reqMsg = tbIotUsrDashTmplDto.getReqMsg();
		String rspMsg = tbIotUsrDashTmplDto.getRspMsg();
		tbIotUsrDashTmplDto.setModUserId(AuthUtils.getUser().getUserId());

		try {
			tbIotUsrDashTmplDto.setReqMsg(URLDecoder.decode(reqMsg, "UTF-8"));
			tbIotUsrDashTmplDto.setRspMsg(URLDecoder.decode(rspMsg, "UTF-8"));
		} catch (Exception e) {
			throw new BizException("URLDecode Error!");
		}
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotUsrDashTmplDao.updateTbIotDashTmpl");
		try {
			tbIotUsrDashTmplDao.updateTbIotDashTmpl(tbIotUsrDashTmplDto);
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
	@Transactional
	public void saveTbIotUsrDashTmpl(TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException {
		ComInfoDto temp = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotUsrDashTmplDao.deleteTbIotUsrDashTmplAll");
		try {
			tbIotUsrDashTmplDao.deleteTbIotUsrDashTmplAll(tbIotUsrDashTmplDto);
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

		List<TbIotUsrDashTmplDTO> TbIotUsrDashTmplDtoList = tbIotUsrDashTmplDto.getTbItoUsrDashTmplDtoList();

		for (TbIotUsrDashTmplDTO tbIotUsrDashTmpl : TbIotUsrDashTmplDtoList) {
			tbIotUsrDashTmpl.setRegUserId(AuthUtils.getUser().getUserId());
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"tbIotUsrDashTmplDao.insertTbIotUsrDashTmpl");
			try {
				tbIotUsrDashTmplDao.insertTbIotUsrDashTmpl(tbIotUsrDashTmpl);
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

}
