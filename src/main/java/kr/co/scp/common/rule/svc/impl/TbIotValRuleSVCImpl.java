package kr.co.scp.common.rule.svc.impl;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.rule.dao.TbIotValRuleDAO;
import kr.co.scp.common.rule.dto.TbIotValRuleDTO;
import kr.co.scp.common.rule.dto.TbIotValRuleParamDTO;
import kr.co.scp.common.rule.svc.TbIotValRuleSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;

@Primary
@Service
public class TbIotValRuleSVCImpl implements TbIotValRuleSVC {

	@Autowired
	TbIotValRuleDAO tbIotValRuleDAO;

	@SuppressWarnings("rawtypes")
	@Override
	public HashMap<String, Object> retrieveTbIotValRuleInfoList(TbIotValRuleDTO tbIotValRuleDTO) throws BizException {
		HashMap<String, Object> rstMap = new HashMap<String, Object>();

		List iotValRuleList = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotValRuleDAO.retrieveTbIotValRuleInfoList");
		try {
			iotValRuleList = tbIotValRuleDAO.retrieveTbIotValRuleInfoList(tbIotValRuleDTO);
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
		rstMap.put("dataList", iotValRuleList);
		return rstMap;
	}

	@Override
	public void insertTbIoTValRule(TbIotValRuleDTO tbIotValRuleDTO) throws BizException {
		ComInfoDto temp = null;
		tbIotValRuleDTO.setDevSeq(tbIotValRuleDTO.getDevSeq() == null ? 0 : tbIotValRuleDTO.getDevSeq());
		String ruleMsgType = tbIotValRuleDTO.getRuleMsgType();
		tbIotValRuleDTO.setRuleMsgType((ruleMsgType == null || ruleMsgType =="" ) ? "1" : tbIotValRuleDTO.getRuleMsgType());

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotValRuleDAO.deleteAllTbIotValRule");
		try {
			tbIotValRuleDAO.deleteAllTbIotValRule(tbIotValRuleDTO);
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

		List<TbIotValRuleParamDTO> list = tbIotValRuleDTO.getDataList();
		for (TbIotValRuleParamDTO tbIotParamDto : list) {
			tbIotParamDto.setDevSeq(tbIotValRuleDTO.getDevSeq());
			tbIotParamDto.setApiSeq(tbIotValRuleDTO.getApiSeq());
			tbIotParamDto.setRuleKind(tbIotParamDto.getRuleRequest());
			tbIotParamDto.setValRuleName(tbIotParamDto.getValRuleName());
			tbIotParamDto.setRuleMsgType(tbIotValRuleDTO.getRuleMsgType());
			tbIotParamDto.setRegUsrId(AuthUtils.getUser().getUserId());
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotValRuleDAO.insertTbIoTValRule");
			try {
				tbIotValRuleDAO.insertTbIoTValRule(tbIotParamDto);
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
