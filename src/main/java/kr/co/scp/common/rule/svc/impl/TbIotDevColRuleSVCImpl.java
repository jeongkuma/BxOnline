package kr.co.scp.common.rule.svc.impl;

import java.util.ArrayList;
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

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.HttpUtils;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdOptionDTO;
import kr.co.scp.common.rule.dao.TbIotDevColRuleDAO;
import kr.co.scp.common.rule.dto.TbIotDevColRuleDTO;
import kr.co.scp.common.rule.dto.TbIotDevColRuleParamDTO;
import kr.co.scp.common.rule.svc.TbIotDevColRuleSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;

@Primary
@Service
public class TbIotDevColRuleSVCImpl implements TbIotDevColRuleSVC {

	@Autowired
	TbIotDevColRuleDAO tbIotDevColRuleDAO;


	/**
	 * @date    : 2019. 6. 11.
	 * @methods : retrieveIotBodyType
	 * @desc    : tcp 바디 command 타입 조회
	 * @param tbIotDevColRuleDTO
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> retrieveIotBodyType(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		tbIotDevColRuleDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
		List<HashMap> returnList = new ArrayList<HashMap>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotDevColRuleDAO.retrieveIotBodyType");
		try {
			returnList = tbIotDevColRuleDAO.retrieveIotBodyType(tbIotDevColRuleDTO);
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

	/**
	 * @date    : 2019. 6. 11.
	 * @methods : retriveTbIotColParseData
	 * @desc    : 목적키 리스트조회 (사용안함)
	 * @param tbIotDevColRuleDTO
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> retriveTbIotColParseData(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		tbIotDevColRuleDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
		List<HashMap> returnList = new ArrayList<HashMap>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retriveTbIotColParseData");
		tbIotDevColRuleDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
		try {
			returnList = tbIotDevColRuleDAO.retriveTbIotColParseData(tbIotDevColRuleDTO);
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

	/**
	 * @date    : 2019. 6. 11.
	 * @methods : retrieveTbIotColValRuleInfo
	 * @desc    : 수집룰 조회 (TCPCOLLECTOR 아닐때)
	 * @param tbIotDevColRuleDTO
	 * @return
	 * @throws BizException
	 */
	@Override
	public HashMap<String, Object> retrieveTbIotColValRuleInfo(TbIotDevColRuleDTO tbIotDevColRuleDTO)
			throws BizException {
		HashMap<String, Object> rstMap = new HashMap<String, Object>();

		List<TbIotCmCdOptionDTO> retrieveTbIotValRuleinfo = null;
		tbIotDevColRuleDTO.setRuleMsgType(tbIotDevColRuleDTO.getRuleMsgType()== null ? "1": tbIotDevColRuleDTO.getRuleMsgType());
//		if(tbIotDevColRuleDTO.getRuleMsgType()== null || tbIotDevColRuleDTO.getRuleMsgType()== null )
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotDevColRuleDAO.retrieveTbIotColValRuleInfo");
		try {
			retrieveTbIotValRuleinfo = tbIotDevColRuleDAO.retrieveTbIotColValRuleInfo(tbIotDevColRuleDTO);
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

	/**
	 * @date    : 2019. 6. 11.
	 * @methods : retrieveTbIotColTcpValRuleInfo
	 * @desc    : 수집룰 조회 (TCPCOLLECTOR 일때)
	 * @param tbIotDevColRuleDTO
	 * @return
	 * @throws BizException
	 */
	@Override
	public HashMap<String, Object> retrieveTbIotColTcpValRuleInfo(TbIotDevColRuleDTO tbIotDevColRuleDTO)
			throws BizException {
		HashMap<String, Object> rstMap = new HashMap<String, Object>();

		List<TbIotCmCdOptionDTO> retrieveTbIotValRuleinfo = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retrieveTbIotColTcpValRuleInfo");
		try {
			retrieveTbIotValRuleinfo = tbIotDevColRuleDAO.retrieveTbIotColTcpValRuleInfo(tbIotDevColRuleDTO);
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

	/**
	 * @date    : 2019. 6. 11.
	 * @methods : retrieveIotDevColRuleSetting
	 * @desc    : 모델별 룰 목록 조회
	 * @param tbIotDevColRuleDTO
	 * @return
	 * @throws BizException
	 */
	@Override
	public HashMap<String, Object> retrieveIotDevColRuleSetting(TbIotDevColRuleDTO tbIotDevColRuleDTO)
			throws BizException {
		ComInfoDto temp = null;
		PageDTO pageDTO = new PageDTO();
		HashMap<String, Object> rstMap = new HashMap<String, Object>();

		// FAQ 전체 갯수
		Integer count = 0;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retrieveIotDevColRuleSettingCnt");
		try {
			count = tbIotDevColRuleDAO.retrieveIotDevColRuleSettingCnt(tbIotDevColRuleDTO);
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

		// 페이징
		pageDTO.pageCalculate(count, tbIotDevColRuleDTO.getDisplayRowCount(), tbIotDevColRuleDTO.getCurrentPage());
		tbIotDevColRuleDTO.setStartPage(pageDTO.getRowStart());

		List<TbIotDevColRuleDTO> iotDevColRuleSetting = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retrieveIotDevColRuleSetting");
		try {
			iotDevColRuleSetting = tbIotDevColRuleDAO.retrieveIotDevColRuleSetting(tbIotDevColRuleDTO);
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

		rstMap.put("page", pageDTO);
		rstMap.put("dataList", iotDevColRuleSetting);

		return rstMap;
	}

	/**
	 * @date    : 2019. 6. 11.
	 * @methods : retrieveIotColAttb
	 * @desc    : 수집 조회
	 * @param tbIotDevColRuleDTO
	 * @return
	 * @throws BizException
	 */
	@Override
	public List<TbIotCmCdOptionDTO> retrieveIotColAttb(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		List<TbIotCmCdOptionDTO> returnList = new ArrayList<TbIotCmCdOptionDTO>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retrieveIotColAttb");
		try {
			returnList = tbIotDevColRuleDAO.retrieveIotColAttb(tbIotDevColRuleDTO);
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

	/**
	 * @date    : 2019. 6. 11.
	 * @methods : retrieveIotConAttb
	 * @desc    : 제어 조회
	 * @param tbIotDevColRuleDTO
	 * @return
	 * @throws BizException
	 */
	@Override
	public List<TbIotCmCdOptionDTO> retrieveIotConAttb(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		List<TbIotCmCdOptionDTO> returnList = new ArrayList<TbIotCmCdOptionDTO>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retrieveIotConAttb");
		try {
			returnList = tbIotDevColRuleDAO.retrieveIotConAttb(tbIotDevColRuleDTO);
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

	/**
	 * @date    : 2019. 6. 11.
	 * @methods : retrieveIotStaAvgAttb
	 * @desc    :  평균 통계
	 * @param tbIotDevColRuleDTO
	 * @return
	 * @throws BizException
	 */
	@Override
	public List<TbIotCmCdOptionDTO> retrieveIotStaAvgAttb(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		List<TbIotCmCdOptionDTO> returnList = new ArrayList<TbIotCmCdOptionDTO>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retrieveIotStaAvgAttb");
		try {
			returnList = tbIotDevColRuleDAO.retrieveIotStaAvgAttb(tbIotDevColRuleDTO);
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

	/**
	 * @date    : 2019. 6. 11.
	 * @methods : retrieveIotStaSumAttb
	 * @desc    : 합계 통계
	 * @param tbIotDevColRuleDTO
	 * @return
	 * @throws BizException
	 */
	@Override
	public List<TbIotCmCdOptionDTO> retrieveIotStaSumAttb(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		List<TbIotCmCdOptionDTO> returnList = new ArrayList<TbIotCmCdOptionDTO>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retrieveIotStaSumAttb");
		try {
			returnList = tbIotDevColRuleDAO.retrieveIotStaSumAttb(tbIotDevColRuleDTO);
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

	/**
	 * @date    : 2019. 6. 11.
	 * @methods : retrieveIotRule
	 * @desc    : 실행 파일 리스트 조회
	 * @param tbIotDevColRuleDTO
	 * @return
	 * @throws BizException
	 */
	@Override
	public List<TbIotCmCdOptionDTO> retrieveIotRule(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		tbIotDevColRuleDTO.setCharSet(HttpUtils.getLangSet());
		List<TbIotCmCdOptionDTO> returnList = new ArrayList<TbIotCmCdOptionDTO>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retrieveIotRule");
		try {
			returnList = tbIotDevColRuleDAO.retrieveIotRule(tbIotDevColRuleDTO);
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

	/**
	 * @date    : 2019. 6. 11.
	 * @methods : retrieveIotDetNmCd
	 * @desc    : 이상징후
	 * @param tbIotDevColRuleDTO
	 * @return
	 * @throws BizException
	 */
	@Override
	public List<TbIotCmCdOptionDTO> retrieveIotDetNmCd(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		List<TbIotCmCdOptionDTO> returnList = new ArrayList<TbIotCmCdOptionDTO>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retrieveIotDetNmCd");
		try {
			returnList = tbIotDevColRuleDAO.retrieveIotDetNmCd(tbIotDevColRuleDTO);
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

	/**
	 * @date    : 2019. 6. 11.
	 * @methods : insertTbIoTDevColRule
	 * @desc    : 수집룰 등록
	 * @param tbIotDevColRuleDTO
	 * @throws BizException
	 */
	@Override
	public void insertTbIoTDevColRule(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		ComInfoDto temp = new ComInfoDto();
//		String apiNm = null;
//		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//				"tbIotDevColRuleDAO.retrieveIotApiNm");
//		try {
//
//			apiNm = tbIotDevColRuleDAO.retrieveIotApiNm(tbIotDevColRuleDTO.getApiSeq());
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

		String ruleMsgType = (tbIotDevColRuleDTO.getRuleMsgType()==null || tbIotDevColRuleDTO.getRuleMsgType().equals("") )?"1":tbIotDevColRuleDTO.getRuleMsgType();

		String devMdlVal = "";

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
		tbIotDevColRuleDTO.setRuleMsgType(ruleMsgType);

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotDevColRuleDAO.deleteAllTbIotDevTcpColRule");
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

		List<TbIotDevColRuleParamDTO> list = tbIotDevColRuleDTO.getDataList();

		for (TbIotDevColRuleParamDTO tbIotParamDto : list) {
			tbIotParamDto.setRuleColType(tbIotDevColRuleDTO.getRuleColType());
			tbIotParamDto.setDevSeq(tbIotDevColRuleDTO.getDevSeq());
			tbIotParamDto.setApiSeq(tbIotDevColRuleDTO.getApiSeq());
			tbIotParamDto.setRuleDevType(tbIotDevColRuleDTO.getRuleDevType());

			//tbIotParamDto.setRuleDevType(devMdlVal);

			tbIotParamDto.setRuleMsgType(ruleMsgType);
			tbIotParamDto.setRegUsrId(AuthUtils.getUser().getUserId());

			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotDevColRuleDAO.insertTbIoTDevColRule");
			try {
				tbIotDevColRuleDAO.insertTbIoTDevColRule(tbIotParamDto);
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


	/**
	 * @Method    : deleteTbIotDevColRule
	 * @date      : 2019. 6. 11.
	 * @author    : 김현배
	 * @history   :
	 * @desc      : 수집룰 삭제
	 * @param tbIotDevColRuleDTO
	 * @throws BizException
	 */
	@Override
	public void deleteTbIotDevColRule(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		ComInfoDto temp = new ComInfoDto();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,  "tbIotDevColRuleDAO.deleteAllTbIotDevColRule");
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

		List<TbIotDevColRuleParamDTO> list = tbIotDevColRuleDTO.getDataList();
		for (TbIotDevColRuleParamDTO tbIotParamDto : list) {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotDevColRuleDAO.insertTbIoTDevColRule");
			try {
				tbIotDevColRuleDAO.insertTbIoTDevColRule(tbIotParamDto);
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

	/**
	 * @Method    : retrieveIotTargetKeyParseList
	 * @date      : 2019. 6. 11.
	 * @author    : selfi
	 * @history   :
	 * @desc      : 목적키 리스트 조회
	 * @param tbIotDevColRuleDTO
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> retrieveIotTargetKeyParseList(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
//		iotCmCdDTO.setCharSet(HttpUtils.getLangSet());
		List<HashMap> returnHashMap = new ArrayList<HashMap>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retrieveIotTargetKeyParseList");
		tbIotDevColRuleDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
		try {
			returnHashMap = tbIotDevColRuleDAO.retrieveIotTargetKeyParseList(tbIotDevColRuleDTO);
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
		return returnHashMap;
	}

	/**
	 * @Method    : retrieveIotTargetKeyList
	 * @date      : 2019. 6. 11.
	 * @author    : 김현배
	 * @history   :
	 * @desc      : 매핑룰 목적키리스트 조회
	 * @param tbIotCmCdDTO
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> retrieveIotTargetKeyList(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		tbIotCmCdDTO.setCharSet(HttpUtils.getLangSet());
		List<HashMap> returnHashMap = new ArrayList<HashMap>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retrieveIotTargetKeyList");
		try {
			returnHashMap = tbIotDevColRuleDAO.retrieveIotTargetKeyList(tbIotCmCdDTO);
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
		return returnHashMap;
	}

	/**
	 * @Method    : retrieveIotRequestKeyParseList
	 * @date      : 2019. 6. 11.
	 * @author    : 김현배
	 * @history   :
	 * @desc      : 검증룰 전문KEY 리스트 조회
	 * @param tbIotCmCdDTO
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> retrieveIotRequestKeyParseList(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		tbIotCmCdDTO.setCharSet(HttpUtils.getLangSet());
		List<HashMap> returnHashMap = new ArrayList<HashMap>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retrieveIotRequestKeyParseList");
		try {
			returnHashMap = tbIotDevColRuleDAO.retrieveIotRequestKeyParseList(tbIotCmCdDTO);
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
		return returnHashMap;
	}

	/**
	 * @Method    : retrieveIotRequestKeyValList
	 * @date      : 2019. 6. 11.
	 * @author    : 김현배
	 * @history   :
	 * @desc      : 검증룰 외 전문KEY 리스트 조회
	 * @param tbIotCmCdDTO
	 * @return
	 * @throws BizException
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List<HashMap> retrieveIotRequestKeyValList(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		tbIotCmCdDTO.setCharSet(HttpUtils.getLangSet());
		List<HashMap> returnHashMap = new ArrayList<HashMap>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retrieveIotRequestKeyValList");
		try {
			returnHashMap = tbIotDevColRuleDAO.retrieveIotRequestKeyValList(tbIotCmCdDTO);
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
		return returnHashMap;
	}

	/**
	 * @Method    : retrieveIotTargetKeyParseHeaderList
	 * @date      : 2019. 6. 11.
	 * @author    : 김현배
	 * @history   :
	 * @desc      : 파싱룰(전문) 헤더 목적키 리스트 조회
	 * @param tbIotCmCdDTO
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<HashMap> retrieveIotTargetKeyParseHeaderList(TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		tbIotCmCdDTO.setCharSet(HttpUtils.getLangSet());
		List<HashMap> returnHashMap = new ArrayList<HashMap>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotDevColRuleDAO.retrieveIotTargetKeyParseHeaderList");
		try {
			returnHashMap = tbIotDevColRuleDAO.retrieveIotTargetKeyParseHeaderList(tbIotCmCdDTO);
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
		return returnHashMap;
	}

	/**
	 * @Method    : retrieveIotServiceList
	 * @date      : 2019. 6. 11.
	 * @author    : 김현배
	 * @history   :
	 * @desc      : 서버명 리스트 조회
	 * @return
	 * @throws BizException
	 */
	@Override
	public List<HashMap> retrieveIotServiceList() throws BizException {
		List<HashMap> returnHashMap = new ArrayList<HashMap>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotDevColRuleDAO.retrieveIotServiceList");
		try {
			String lang = ReqContextComponent.getComInfoDto().getXlang();
			returnHashMap = tbIotDevColRuleDAO.retrieveIotServiceList(lang);
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
		return returnHashMap;
	}

	@Override
	public void copyDeviceRuleInfo(TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		tbIotDevColRuleDTO.setRegUserId(AuthUtils.getUser().getUserId());
		ComInfoDto temp = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "ttbIotDevColRuleDAO.deleteCopyRuleInfo");
		try {
			//등록되어있는 전문 삭제
			tbIotDevColRuleDAO.deleteCopyRuleInfo(tbIotDevColRuleDTO);
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

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotDevColRuleDAO.insertCopyRuleInfo");
		try {
			//등록되어있는 전문룰 복사
			tbIotDevColRuleDAO.insertCopyRuleInfo(tbIotDevColRuleDTO);
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
