package kr.co.scp.common.rule.svc.impl;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.HttpUtils;
import kr.co.scp.common.rule.dao.TbIotRuleDAO;
import kr.co.scp.common.rule.dto.TbIotRuleDTO;
import kr.co.scp.common.rule.dto.TbIotRuleParamDTO;
import kr.co.scp.common.rule.svc.TbIotRuleSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;

@Primary
@Service
public class TbIotRuleSVCImpl implements TbIotRuleSVC {

	@Autowired
	TbIotRuleDAO tbIotRuleDAO;

	@Override
	public List<TbIotRuleDTO> retrieveTbIotRuleInfoList(TbIotRuleDTO tbIotRuleDTO) throws BizException {
		List<TbIotRuleDTO> iotRuleDTOs = new ArrayList<TbIotRuleDTO>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotRuleDAO.retrieveTbIotValRuleInfoList");
		try {
			tbIotRuleDTO.setRuleName("");
			iotRuleDTOs = tbIotRuleDAO.retrieveTbIotRuleInfoList(tbIotRuleDTO);
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
		return iotRuleDTOs;
	}

	@Override
	public void insertTbIotRule(TbIotRuleDTO tbIotRuleDTO) throws BizException {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotRuleDAO.insertTbIoTRule");
		List<TbIotRuleParamDTO> list = tbIotRuleDTO.getDataList();
		for (TbIotRuleParamDTO tbIotParamDto : list) {
			tbIotParamDto.setCharSet(HttpUtils.getLangSet());
			tbIotParamDto.setRegUserId(AuthUtils.getUser().getUserId());

			try {

				tbIotRuleDAO.insertTbIoTRule(tbIotParamDto);

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
	public void deleteTbIotRule(TbIotRuleDTO tbIotRuleDTO) throws BizException {
		// TODO Auto-generated method stub

	}

}
