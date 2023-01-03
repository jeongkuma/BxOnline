package kr.co.scp.common.rule.svc.impl;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdOptionDTO;
import kr.co.scp.common.rule.dao.TbIotDevColRuleDAO;
import kr.co.scp.common.rule.dao.TbIotDevCtlRuleDAO;
import kr.co.scp.common.rule.dto.TbIotDevColRuleDTO;
import kr.co.scp.common.rule.dto.TbIotDevCtlRuleDTO;
import kr.co.scp.common.rule.dto.TbIotDevCtlRuleParamDTO;
import kr.co.scp.common.rule.svc.TbIotDevCtlRuleSVC;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Primary
@Service
public class TbIotDevCtlRuleSVCImpl implements TbIotDevCtlRuleSVC {

	@Autowired
	TbIotDevCtlRuleDAO tbIotDevCtlRuleDAO;

	@Autowired
	TbIotDevColRuleDAO tbIotDevColRuleDAO;

	@Override
	public HashMap<String, Object> retrieveTbIotCtlValRuleInfo(TbIotDevCtlRuleDTO tbIotDevCtlRuleDTO)
			throws BizException {
		HashMap<String, Object> rstMap = new HashMap<String, Object>();

		List<TbIotCmCdOptionDTO> retrieveTbIotValRuleinfo = null;
		tbIotDevCtlRuleDTO.setRuleMsgType(tbIotDevCtlRuleDTO.getRuleMsgType()== null ? "1": tbIotDevCtlRuleDTO.getRuleMsgType());

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotDevCtlRuleDAO.retrieveTbIotCtlValRuleInfo");
		try {
			retrieveTbIotValRuleinfo = tbIotDevCtlRuleDAO.retrieveTbIotCtlValRuleInfo(tbIotDevCtlRuleDTO);
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

		rstMap.put("dataList", retrieveTbIotValRuleinfo);

		return rstMap;
	}

	@Override
	public void insertTbIoTDevCtlRule(TbIotDevCtlRuleDTO tbIotDevCtlRuleDTO) throws BizException {
		ComInfoDto temp = new ComInfoDto();
		String apiNm = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevCtlRuleDAO.retrieveIotApiNm");
		try {

			apiNm = tbIotDevColRuleDAO.retrieveIotApiNm(tbIotDevCtlRuleDTO.getApiSeq());
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

		String ruleMsgType = (tbIotDevCtlRuleDTO.getRuleMsgType()==null || tbIotDevCtlRuleDTO.getRuleMsgType().equals("") )?"1":tbIotDevCtlRuleDTO.getRuleMsgType();

		String devMdlVal = "";
		TbIotDevColRuleDTO tbIotDevColRuleDTO = new TbIotDevColRuleDTO();
		tbIotDevColRuleDTO.setDevSeq(tbIotDevCtlRuleDTO.getDevSeq());
		tbIotDevColRuleDTO.setApiSeq(tbIotDevCtlRuleDTO.getApiSeq());
		tbIotDevColRuleDTO.setRuleColType(tbIotDevCtlRuleDTO.getRuleColType());
		tbIotDevColRuleDTO.setRuleMsgType(tbIotDevCtlRuleDTO.getRuleMsgType());
//		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotDevColRuleDAO.retrieveTbIotDevMdlVal");
//		try {
//			devMdlVal = tbIotDevColRuleDAO.retrieveTbIotDevMdlVal(tbIotDevColRuleDTO);
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
		tbIotDevCtlRuleDTO.setRuleMsgType(ruleMsgType);

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotDevColRuleDAO.deleteAllTbIotDevColRule");
		try {
			tbIotDevColRuleDAO.deleteAllTbIotDevColRule(tbIotDevColRuleDTO);
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

		List<TbIotDevCtlRuleParamDTO> list = tbIotDevCtlRuleDTO.getDataList();

		for (TbIotDevCtlRuleParamDTO tbIotParamDto : list) {
			tbIotParamDto.setRuleColType(tbIotDevCtlRuleDTO.getRuleColType());
			tbIotParamDto.setDevSeq(tbIotDevCtlRuleDTO.getDevSeq());
			tbIotParamDto.setApiSeq(tbIotDevCtlRuleDTO.getApiSeq());
			tbIotParamDto.setRuleDevType(tbIotDevCtlRuleDTO.getRuleDevType());

			//tbIotParamDto.setRuleDevType(devMdlVal);

			tbIotParamDto.setRuleMsgType(ruleMsgType);
			tbIotParamDto.setRegUsrId(AuthUtils.getUser().getUserId());

			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotDevCtlRuleDAO.insertTbIoTDevCtlRule");
			try {
				tbIotDevCtlRuleDAO.insertTbIoTDevCtlRule(tbIotParamDto);
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
