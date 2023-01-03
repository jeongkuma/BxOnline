package kr.co.scp.common.iotInsert.svc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.iotInsert.dao.IotInsertServiceDAO;
import kr.co.scp.common.iotInsert.dao.IotInsertServiceRuleDAO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertServiceMappigDTO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertServiceRuleDTO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertServiceRuleServiceDTO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertServiceValidDTO;
import kr.co.scp.common.iotInsert.svc.IotInsertServiceRuleSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IotInsertServiceRuleSVCImpl implements IotInsertServiceRuleSVC {

	@Autowired
	private IotInsertServiceRuleDAO iotInsertServiceRuleDAO;
	
	@Autowired
	private IotInsertServiceDAO iotInsertServiceDAO;
 
	@SuppressWarnings("unused")
	@Override
	@Transactional
	public HashMap<String, Object> insertServiceRuleSvc(TbIotInsertServiceRuleDTO tbIotInsertServiceRuleDTOList) throws BizException {

		Integer parseCount = 0;
		Integer mappigCount = 0;
		Integer validCount = 0;
		Integer ruleserviceCount = 0;
		TbIotInsertServiceMappigDTO parseDTO = null;
		TbIotInsertServiceMappigDTO mappigDTO = null;
		TbIotInsertServiceValidDTO validDTO = null;
		TbIotInsertServiceRuleServiceDTO ruleserviceDTO = null;
		ComInfoDto temp = null;

		List<TbIotInsertServiceMappigDTO> parseDTOList = new ArrayList();
		List<TbIotInsertServiceMappigDTO> mappigDTOList = new ArrayList();
		List<TbIotInsertServiceValidDTO> validDTOList = new ArrayList();
		List<TbIotInsertServiceRuleServiceDTO> ruleserviceDTOList = new ArrayList();
		
//		HashMap<String, Object> resultMap = new HashMap<String, Object>();
//		HashMap<String, Object> paramMap = new HashMap<String, Object>();
//		
//		paramMap.put("svcCd", tbIotInsertServiceRuleDTOList.getSvcCd().toString());
//		resultMap = iotInsertServiceDAO.retriveSvcStatus(paramMap);
//		if (resultMap != null && !"T".equals(resultMap.get("useYn").toString()) ) {
//			throw new BizException("svcUseYn");
//		}
		//parseDTO = null;

		// parse 리스트
		for (int j = 0; j < tbIotInsertServiceRuleDTOList.getParse().size(); j++) {
			parseDTO = tbIotInsertServiceRuleDTOList.getParse().get(j);
			parseDTO.setRegUsrId(AuthUtils.getUser().getUserId()); 
			
			parseDTOList.add(parseDTO);
		}

		if(parseDTOList.size() > 0) {
			
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.deleteParseInfoAll");
			try {
				iotInsertServiceRuleDAO.deleteParseInfoAll(parseDTOList);
			} catch (DataIntegrityViolationException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (UncategorizedSQLException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (MyBatisSystemException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (BadSqlGrammarException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} finally {
				OmsUtils.endInnerOms(temp);
			}
			
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.insertParseInfoAll");
			try {
				iotInsertServiceRuleDAO.insertParseInfoAll(parseDTOList);
			} catch (DataIntegrityViolationException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (UncategorizedSQLException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (MyBatisSystemException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (BadSqlGrammarException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} finally {
				OmsUtils.endInnerOms(temp);
			}
		}
	
		// mappig 리스트 (parse와 같은 쿼리 사용)
		for (int j = 0; j < tbIotInsertServiceRuleDTOList.getMappig().size(); j++) {
			mappigDTO = tbIotInsertServiceRuleDTOList.getMappig().get(j);
			mappigDTO.setRegUsrId(AuthUtils.getUser().getUserId()); 
			
			mappigDTOList.add(mappigDTO);
			
		}
		if(mappigDTOList.size() > 0) {
			
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.deleteParseInfoAll");
			try {
				iotInsertServiceRuleDAO.deleteParseInfoAll(mappigDTOList);  
			} catch (DataIntegrityViolationException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (UncategorizedSQLException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (MyBatisSystemException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (BadSqlGrammarException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} finally {
				OmsUtils.endInnerOms(temp);
			}
		
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.insertParseInfoAll");
			try {
				iotInsertServiceRuleDAO.insertParseInfoAll(mappigDTOList);
			} catch (DataIntegrityViolationException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (UncategorizedSQLException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (MyBatisSystemException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (BadSqlGrammarException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} finally {
				OmsUtils.endInnerOms(temp);
			}

		}
		// valid 리스트
		for (int j = 0; j < tbIotInsertServiceRuleDTOList.getValid().size(); j++) {
			validDTO = tbIotInsertServiceRuleDTOList.getValid().get(j);
			validDTO.setRegUsrId(AuthUtils.getUser().getUserId()); 
			 
			validDTOList.add(validDTO);
		}

		if(validDTOList.size() > 0) {
			
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.deleteValidInfoAll");
			try {
				iotInsertServiceRuleDAO.deleteValidInfoAll(validDTOList);  
			} catch (DataIntegrityViolationException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (UncategorizedSQLException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (MyBatisSystemException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (BadSqlGrammarException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} finally {
				OmsUtils.endInnerOms(temp);
			}
			
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.insertValidInfoAll");
			try {
				iotInsertServiceRuleDAO.insertValidInfoAll(validDTOList);
			} catch (DataIntegrityViolationException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (UncategorizedSQLException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (MyBatisSystemException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (BadSqlGrammarException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} finally {
				OmsUtils.endInnerOms(temp);
			}
		}
		
		
		// ruleservice 리스트
		for (int j = 0; j < tbIotInsertServiceRuleDTOList.getRuleservice().size(); j++) {
			ruleserviceDTO = tbIotInsertServiceRuleDTOList.getRuleservice().get(j);
			ruleserviceDTO.setRegUsrId(AuthUtils.getUser().getUserId()); 

			ruleserviceDTOList.add(ruleserviceDTO);
			
		}

		if(ruleserviceDTOList.size() > 0) {
			
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.deleteRuleserviceInfoAll");
			try {
				iotInsertServiceRuleDAO.deleteRuleserviceInfoAll(ruleserviceDTOList);  
			} catch (DataIntegrityViolationException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (UncategorizedSQLException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (MyBatisSystemException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (BadSqlGrammarException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} finally {
				OmsUtils.endInnerOms(temp);
			}
			
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.insertRuleserviceInfoAll");
			try {
				iotInsertServiceRuleDAO.insertRuleserviceInfoAll(ruleserviceDTOList);
			} catch (DataIntegrityViolationException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (UncategorizedSQLException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (MyBatisSystemException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} catch (BadSqlGrammarException e) {
				OmsUtils.expInnerOms(temp, e);
				throw e;
			} finally {
				OmsUtils.endInnerOms(temp);
			}

		}
		return null;

	}
}
