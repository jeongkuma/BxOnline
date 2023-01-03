package kr.co.scp.common.iotCmCd.svc.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.scp.ccp.iotuser.dao.IotUsrDAO;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.scp.common.iotCmCd.dao.IotCmCdDAO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdCDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdOptionDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdPDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdUDTO;
import kr.co.scp.common.iotCmCd.svc.IotCmCdSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;

@Primary
@Slf4j
@Service
public class IotCmCdSVCImpl implements IotCmCdSVC {

	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;

	@Autowired
	private IotCmCdDAO iotCmCdDAO;

	@Autowired
	private IotUsrDAO iotUsrDAO;

	@Override
	public HashMap<String, Object> retrieveIotCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		ComInfoDto temp = null;
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageDTO pageDto = new PageDTO();
		if (null == tbIotCmCdDTO.getLangSet()) {
			tbIotCmCdDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		}
		tbIotCmCdDTO.setCustSeq(AuthUtils.getUser().getCustSeq());

		int totalCount = 0;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCmCdDAO.retrieveIotCmCdCount");
		try {
			totalCount = iotCmCdDAO.retrieveIotCmCdCount(tbIotCmCdDTO);
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
		pageDto.pageCalculate(totalCount, tbIotCmCdDTO.getDisplayRowCount(), tbIotCmCdDTO.getCurrentPage());
		tbIotCmCdDTO.setCurrentPage(pageDto.getRowStart());
		tbIotCmCdDTO.setEndPage(pageDto.getRowEnd());
		tbIotCmCdDTO.setTotCnt(totalCount);
		List<TbIotCmCdDTO> returnList = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCmCdDAO.retrieveIotCmCd");
		try {
			returnList = iotCmCdDAO.retrieveIotCmCd(tbIotCmCdDTO);
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
		returnMap.put("dataList", makeLevelData(returnList));
		returnMap.put("page", pageDto);
		return returnMap;
	}

	@Override
	public TbIotCmCdUDTO retrieveIotCmCdById(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		TbIotCmCdUDTO returnCmCd = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveIotCmCdById");
		tbIotCmCdDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		try {
			returnCmCd = iotCmCdDAO.retrieveIotCmCdById(tbIotCmCdDTO);
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

		if (returnCmCd.getLvl() == 4) {
			returnCmCd.setParentCdNm(returnCmCd.getLevel1() + " > " + returnCmCd.getLevel2() + " > " + returnCmCd.getParentCdNm());
		} else if (returnCmCd.getLvl() == 3) {
			returnCmCd.setParentCdNm(returnCmCd.getLevel2() + " > " + returnCmCd.getParentCdNm());
		} else if (returnCmCd.getLvl() == 2) {
			returnCmCd.setParentCdNm(returnCmCd.getParentCdNm());
		} else if (returnCmCd.getLvl() == 1) {
			returnCmCd.setParentCdNm("");
		}
		return returnCmCd;
	}

	@Override
	public TbIotCmCdUDTO retrieveIotCmCdByCdId(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		tbIotCmCdDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
//		if( null == tbIotCmCdDTO.getLangSet()) {
//			tbIotCmCdDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
//		}
		TbIotCmCdUDTO returnCmCd = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveIotCmCdById");
		try {
			returnCmCd = iotCmCdDAO.retrieveIotCmCdByCdId(tbIotCmCdDTO);
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
		return returnCmCd;
	}

	@Override
	public List<TbIotCmCdPDTO> retrieveIotParentCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		if (null == tbIotCmCdDTO.getLangSet()) {
//			if (null == tbIotCmCdDTO.getCharSet()) {
//			tbIotCmCdDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
			tbIotCmCdDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		}
		List<TbIotCmCdPDTO> returnDto = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveIotParentCmCd");
		try {
			returnDto = iotCmCdDAO.retrieveIotParentCmCd(tbIotCmCdDTO);
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
	public List<TbIotCmCdDTO> retrieveIotByParentCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		tbIotCmCdDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		List<TbIotCmCdDTO> returnDto = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveIotByParentCmCd");
		tbIotCmCdDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		try {
			returnDto = iotCmCdDAO.retrieveIotByParentCmCd(tbIotCmCdDTO);
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
	public void createIotCmCd(TbIotCmCdCDTO tbIotCmCdCDTO) throws BizException {
		String regUsrId = AuthUtils.getUser().getUserId();
		tbIotCmCdCDTO.setRegUsrId(regUsrId);
		TbIotCmCdDTO langSetDto = new TbIotCmCdDTO();
		langSetDto.setParentCdId("GN00000021");
		langSetDto.setLangSet("ko");
		langSetDto.setLvl(5);
		List<TbIotCmCdPDTO> langSetList = this.retrieveIotParentCmCd(langSetDto);
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.createIotCmCd");
		try {

			for (Iterator iterator = langSetList.iterator(); iterator.hasNext();) {
				TbIotCmCdDTO nmChkDto = new TbIotCmCdDTO();
				TbIotCmCdPDTO tbIotCmCdPDTO = (TbIotCmCdPDTO) iterator.next();
				if (tbIotCmCdPDTO.getLangSet() != tbIotCmCdCDTO.getLangSet() ) {
					tbIotCmCdCDTO.setLangSet(tbIotCmCdPDTO.getCdId());
					nmChkDto.setLangSet(tbIotCmCdPDTO.getLangSet());
				} else {
					nmChkDto.setLangSet(tbIotCmCdCDTO.getLangSet());
				}
				nmChkDto.setCdId(tbIotCmCdCDTO.getCdId());
				nmChkDto.setCdNm(tbIotCmCdCDTO.getCdNm());
				nmChkDto.setParentCdId(tbIotCmCdCDTO.getParentCdId());

				int duplChkNum = this.retrieveDuplicatedCdNm(nmChkDto);
				int duplChkNm = this.retrieveDuplicatedCdId(nmChkDto);
				if (duplChkNum > 0) {
					throw new BizException("duplicatedCdNmDuringProcess");
				} else if (duplChkNm > 0) {
					throw new BizException("duplicatedCdIdDuringProcess");
				} else {
					iotCmCdDAO.createIotCmCd(tbIotCmCdCDTO);
				}
			}

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
	public void updateIotCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		String modUsrId = AuthUtils.getUser().getUserId();
		tbIotCmCdDTO.setModUsrId(modUsrId);

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.updateIotCmCd");
		try {
			iotCmCdDAO.updateIotCmCd(tbIotCmCdDTO);
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
		// 하위 코드들 삭제하는 로직
//		if (tbIotCmCdDTO.getUseYn().equals("N")) {
//			this.deleteIotCmCd(tbIotCmCdDTO);
//		}
//		TbIotCmCdDTO preInfo = iotCmCdDAO.retrieveIotCmCdById(tbIotCmCdDTO);
//		iotCmCdDAO.updateIotCmCd(tbIotCmCdDTO);
//		String preValue = preInfo.getCdId();
//		tbIotCmCdDTO.setParentCdId(preValue);
//		String changedValue = tbIotCmCdDTO.getCdId();
//
//		List<TbIotCmCdDTO> childList = iotCmCdDAO.retrieveIotByParentCmCd(tbIotCmCdDTO);
//		if(childList.size() > 0 ) {
//			for (Iterator iterator = childList.iterator(); iterator.hasNext();) {
//				TbIotCmCdDTO tmpCmCdDTO = (TbIotCmCdDTO) iterator.next();
//				tmpCmCdDTO.setParentCdId(changedValue);
//				tmpCmCdDTO.setModUsrId(tbIotCmCdDTO.getModUsrId());
//				iotCmCdDAO.updateIotParentCmId(tmpCmCdDTO);
//			}
//		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void deleteIotCmCd(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		ComInfoDto temp = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCmCdDAO.deleteIotCmCd");
		try {
			iotCmCdDAO.deleteIotCmCd(tbIotCmCdDTO);
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

		tbIotCmCdDTO.setParentCdId(tbIotCmCdDTO.getCdId());
		List<TbIotCmCdDTO> childList = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveIotByParentCmCd");
		try {
			childList = iotCmCdDAO.retrieveIotByParentCmCd(tbIotCmCdDTO);
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

		if (childList.size() > 0) {
			for (Iterator iterator = childList.iterator(); iterator.hasNext();) {
				TbIotCmCdDTO tmpCmCdDTO = (TbIotCmCdDTO) iterator.next();
				tmpCmCdDTO.setModUsrId(tbIotCmCdDTO.getModUsrId());
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCmCdDAO.deleteIotCmCd");
				try {
					iotCmCdDAO.deleteIotCmCd(tmpCmCdDTO);
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

	@Override
	public int retrieveDuplicatedCdNm(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		int chkCnt = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveDuplicatedCdNm");
		try {
			chkCnt = iotCmCdDAO.retrieveDuplicatedCdNm(tbIotCmCdDTO);
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
	public int retrieveDuplicatedCdId(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		int chkCnt = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveDuplicatedCdId");
		try {
			chkCnt = iotCmCdDAO.retrieveDuplicatedCdId(tbIotCmCdDTO);
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
	public List<TbIotCmCdDTO> retrieveIotCmCdBySubString(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		tbIotCmCdDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
//		tbIotCmCdDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
		List<TbIotCmCdDTO> returnDto = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveIotCmCdBySubString");
		try {

			tbIotCmCdDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
			returnDto = iotCmCdDAO.retrieveIotCmCdBySubString(tbIotCmCdDTO);
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
	public List<TbIotCmCdOptionDTO> retrieveIotByParentCmCdRuntime(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
//		tbIotCmCdDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
		tbIotCmCdDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		List<TbIotCmCdOptionDTO> returnDto = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveIotByParentCmCdRuntime");
		try {
			returnDto = iotCmCdDAO.retrieveIotByParentCmCdRuntime(tbIotCmCdDTO);
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

	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> retrieveIotByParentCmCdOnly(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
//		tbIotCmCdDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
		tbIotCmCdDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		List<HashMap> returnList = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveIotByParentCmCdOnly");
		try {
			returnList = iotCmCdDAO.retrieveIotByParentCmCdOnly(tbIotCmCdDTO);
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
		return returnList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> retrieveIotByParentCmCdTwo(TbIotCmCdOptionDTO tbIotCmCdOptionDTO) throws BizException {
//		tbIotCmCdOptionDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
		tbIotCmCdOptionDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		List<HashMap> returnList = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveIotByParentCmCdTwo");
		try {
			returnList = iotCmCdDAO.retrieveIotByParentCmCdTwo(tbIotCmCdOptionDTO);
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
		return returnList;
	}

	@Override
	public String retrieveCdIdByCdNm(String cdNm) throws BizException {
		String returnValue = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveCdIdByCdNm");
		TbIotCmCdDTO tbIotCmCdDTO = new TbIotCmCdDTO();
		tbIotCmCdDTO.setCdNm(cdNm);
		tbIotCmCdDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		try {
			returnValue = iotCmCdDAO.retrieveCdIdByCdNm(tbIotCmCdDTO);
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
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			OmsUtils.endInnerOms(temp);
		}
		return returnValue;
	}

	@Override
	public XSSFWorkbook excelCreate(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();
		List<TbIotCmCdDTO> list = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveIotCmCdExcel");
		try {
			list = iotCmCdDAO.retrieveIotCmCdExcel(tbIotCmCdDTO);
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

		Map<String, String> title = new LinkedHashMap<String, String>();
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "code"), "cdId");
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "codeNm"), "cdNm");
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "parentCdNm"), "parentCdNm");
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "parentCd"), "parentCdId");
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "level-1"), "level1");
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "level-2"), "level2");
//		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "level-3"), "level3");
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "useYn"), "useYn");
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "codeDesc"), "cdDesc");
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "regUsrId"), "regUsrId");
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "regDttm"), "regDttm");
		ExcelUtils.createExcelFile(wb, makeLevelData(list), title);

		return wb;
	}

	@Override
	public String retrieveIotMaxOrder(TbIotCmCdDTO dto) throws BizException {

		String newOrder = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveIotMaxOrder");

		try {
			newOrder = iotCmCdDAO.retrieveIotMaxOrder(dto);
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

		return newOrder;
	}

	private List<TbIotCmCdDTO> makeLevelData(List<TbIotCmCdDTO> list) throws BizException {

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TbIotCmCdDTO tbIotCmCdDTO = (TbIotCmCdDTO) iterator.next();
			if (tbIotCmCdDTO.getLvl() == 4) {
				tbIotCmCdDTO.setLevel3(tbIotCmCdDTO.getParentCdNm());
			} else if (tbIotCmCdDTO.getLvl() == 3) {
				tbIotCmCdDTO.setLevel1(tbIotCmCdDTO.getLevel2());
				tbIotCmCdDTO.setLevel2(tbIotCmCdDTO.getParentCdNm());
				tbIotCmCdDTO.setLevel3("");
			} else if (tbIotCmCdDTO.getLvl() == 2) {
				tbIotCmCdDTO.setLevel1(tbIotCmCdDTO.getParentCdNm());
				tbIotCmCdDTO.setLevel2("");
				tbIotCmCdDTO.setLevel3("");
			} else if (tbIotCmCdDTO.getLvl() == 1) {
				tbIotCmCdDTO.setLevel1("");
				tbIotCmCdDTO.setLevel2("");
				tbIotCmCdDTO.setLevel3("");
			}
		}
		return list;

	}

	@Override
	public List<TbIotCmCdPDTO> retrieveIotParentCmCdOrderByCdNm(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		if (null == tbIotCmCdDTO.getLangSet()) {
//			if (null == tbIotCmCdDTO.getCharSet()) {
//			tbIotCmCdDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
			tbIotCmCdDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		}
		List<TbIotCmCdPDTO> returnDto = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdDAO.retrieveIotParentCmCdOrderByCdNm");
		try {
			returnDto = iotCmCdDAO.retrieveIotParentCmCdOrderByCdNm(tbIotCmCdDTO);
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
}
