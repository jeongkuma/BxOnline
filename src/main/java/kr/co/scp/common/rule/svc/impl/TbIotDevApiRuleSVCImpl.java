package kr.co.scp.common.rule.svc.impl;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.common.rule.dao.TbIotDevApiRuleDAO;
import kr.co.scp.common.rule.dto.TbIotDevApiRuleDTO;
import kr.co.scp.common.rule.dto.TbIotDevApiRuleParamDTO;
import kr.co.scp.common.rule.svc.TbIotDevApiRuleSVC;
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

@Primary
@Service
public class TbIotDevApiRuleSVCImpl implements TbIotDevApiRuleSVC {

	@Autowired
	TbIotDevApiRuleDAO tbIotDevApiRuleDAO;

	@Override
	public HashMap<String, Object> retrieveTbIotDevApiRule(TbIotDevApiRuleDTO tbIotDevApiRuleDTO) throws BizException {

		HashMap<String, Object> rstMap = new HashMap<String, Object>();

		@SuppressWarnings("rawtypes")
		List iotDevApiRule = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevApiRuleDAO.retrieveTbIotDevApiRule");
		try {
			iotDevApiRule = tbIotDevApiRuleDAO.retrieveTbIotDevApiRule(tbIotDevApiRuleDTO);
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
		rstMap.put("dataList", iotDevApiRule);

		return rstMap;
	}

	@Override
	public HashMap<String, Object> retrieveTbIotDevApiRuleInputList(TbIotDevApiRuleDTO tbIotDevApiRuleDTO)
			throws BizException {

		HashMap<String, Object> rstMap = new HashMap<String, Object>();

		@SuppressWarnings("rawtypes")
		List iotDevApiRule = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevApiRuleDAO.retrieveTbIotDevApiRuleInputList");
		try {
			iotDevApiRule = tbIotDevApiRuleDAO.retrieveTbIotDevApiRuleInputList(tbIotDevApiRuleDTO);
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
		rstMap.put("dataList", iotDevApiRule);

		return rstMap;
	}

	@Override
	public void insertTbIotDevApiRule(TbIotDevApiRuleDTO tbIotDevApiRuleDTO) throws BizException {
		ComInfoDto temp = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevApiRuleDAO.deleteAllTbIotDevApiRule");
		try {
			tbIotDevApiRuleDAO.deleteAllTbIotDevApiRule(tbIotDevApiRuleDTO);
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

		List<TbIotDevApiRuleParamDTO> list = tbIotDevApiRuleDTO.getDataList();
		for (TbIotDevApiRuleParamDTO tbIotParamDto : list) {
			tbIotParamDto.setDevSeq(tbIotDevApiRuleDTO.getDevSeq());
			tbIotParamDto.setApiSeq(tbIotDevApiRuleDTO.getApiSeq());
			tbIotParamDto.setRegUsrId(AuthUtils.getUser().getUserId());

			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"tbIotDevApiRuleDAO.insertTbIotDevApiRule");
			try {
				tbIotDevApiRuleDAO.insertTbIotDevApiRule(tbIotParamDto);
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
