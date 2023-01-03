package kr.co.scp.ccp.iotTerms.svc.impl;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotTerms.dao.TbIotTermsDAO;
import kr.co.scp.ccp.iotTerms.dto.TbIotTermsDTO;
import kr.co.scp.ccp.iotTerms.svc.TbIotTermsSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;

@Primary
@Service
public class TbIotTermsSVCImpl implements TbIotTermsSVC {

	@Autowired
	private TbIotTermsDAO termsDao;

	@Override
	public HashMap<String, Object> retrieveTerms(TbIotTermsDTO termsDto) throws BizException {
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String langSet = c.getXlang();
		termsDto.setLangSet(langSet);
		HashMap<String, Object> rnMap = new HashMap<String, Object>();
		List<TbIotTermsDTO> reqTerms = null;
		List<TbIotTermsDTO> optTerms = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"termsDao.retrieveTermsReq");
		try {
			reqTerms = termsDao.retrieveTermsReq();
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

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"termsDao.retrieveTermsOpt");
		try {
			optTerms = termsDao.retrieveTermsOpt();
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
		rnMap.put("reqTerms", reqTerms);
		rnMap.put("optTerms", optTerms);
		return rnMap;
	}

	@Override
	public void createTermsAgr(TbIotTermsDTO termsDto) throws BizException {
		termsDto.setRegUsrId(termsDto.getUsrLoginId());
		ComInfoDto temp = null;
		for(int termsNo : termsDto.getTermsNoList()) {
			termsDto.setIotTermsNo(termsNo);
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"termsDao.createTermsAgr");
			try {
				termsDao.createTermsAgr(termsDto);
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
