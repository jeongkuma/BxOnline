package kr.co.scp.common.api.svc.impl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.common.api.dao.TbIotApiDAO;
import kr.co.scp.common.api.dao.TbIotParamDAO;
import kr.co.scp.common.api.dto.TbIotApiDTO;
import kr.co.scp.common.api.dto.TbIotParamDTO;
import kr.co.scp.common.api.svc.TbIotApiSVC;
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
public class TbIotApiSVCImpl implements TbIotApiSVC {

	@Autowired
	private TbIotApiDAO tbIotApiDao;

	@Autowired
	private TbIotParamDAO tbIotParamDao;

	@Override
	public HashMap<String, Object> retrieveTbIotApiList(TbIotApiDTO tbIotApiDto) throws BizException {
		ComInfoDto temp = null;
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageDTO pageDto = new PageDTO();
		int totalCount = 0;
		tbIotApiDto.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotApiDao.retrieveTbIotApiCount");
		try {
			totalCount = tbIotApiDao.retrieveTbIotApiCount(tbIotApiDto);
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

		pageDto.pageCalculate(totalCount, tbIotApiDto.getDisplayRowCount(), tbIotApiDto.getCurrentPage());
		tbIotApiDto.setCurrentPage(pageDto.getRowStart());
		List<TbIotApiDTO> apiList = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotApiDao.retrieveTbIotApiList");
		try {
			apiList = tbIotApiDao.retrieveTbIotApiList(tbIotApiDto);
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

		returnMap.put("dataList", apiList);
		returnMap.put("page", pageDto);
		return returnMap;
	}

	@Override
	public void insertTbIotApi(TbIotApiDTO tbIotApiDto) throws BizException {
		Integer apiSeq = 0;
		tbIotApiDto.setRegUserId(AuthUtils.getUser().getUserId());
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotApiDao.insertTbIotApi");
		try {
			apiSeq = tbIotApiDao.insertTbIotApi(tbIotApiDto);
		} catch (Exception e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

		if (apiSeq > 0) {
			List<TbIotParamDTO> TbIotParamDTOList = tbIotApiDto.getTbIotParamDTOList();
			for (TbIotParamDTO tbIotParamDto : TbIotParamDTOList) {
				tbIotParamDto.setApiSeq(tbIotApiDto.getApiSeq());
				tbIotParamDto.setRegUserId(AuthUtils.getUser().getUserId());
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"tbIotParamDao.insertTbIotParam");
				try {
					tbIotParamDao.insertTbIotParam(tbIotParamDto);
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
	public void updateTbIotApi(TbIotApiDTO tbIotApiDto) throws BizException {
		ComInfoDto temp = null;
		tbIotApiDto.setModUserId(AuthUtils.getUser().getUserId());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotApiDao.updateTbIotApi");
		try {
			tbIotApiDao.updateTbIotApi(tbIotApiDto);
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

		TbIotParamDTO deltbIotParamDto = new TbIotParamDTO();
		deltbIotParamDto.setApiSeq(tbIotApiDto.getApiSeq());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotParamDao.deleteTbIotParamAll");
		try {
			tbIotParamDao.deleteTbIotParamAll(deltbIotParamDto);
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

		List<TbIotParamDTO> TbIotParamDTOList = tbIotApiDto.getTbIotParamDTOList();
		for (TbIotParamDTO tbIotParamDto : TbIotParamDTOList) {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"tbIotParamDao.insertTbIotParam");
			try {
				tbIotParamDto.setRegUserId(AuthUtils.getUser().getUserId());
				tbIotParamDao.insertTbIotParam(tbIotParamDto);
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
	public void deleteTbIotApi(TbIotApiDTO tbIotApiDto) throws BizException {
		ComInfoDto temp = null;
		for (String apiSeq : tbIotApiDto.getApiSeqList()) {
			TbIotApiDTO delTbIotApiDto = new TbIotApiDTO();
			delTbIotApiDto.setApiSeq(apiSeq);
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotApiDao.deleteTbIotApi");
			try {
				tbIotApiDao.deleteTbIotApi(delTbIotApiDto);
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

			TbIotParamDTO tbIotParamDto = new TbIotParamDTO();
			tbIotParamDto.setApiSeq(apiSeq);
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"tbIotParamDao.deleteTbIotParamAll");
			try {
				tbIotParamDao.deleteTbIotParamAll(tbIotParamDto);
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
	public int duplicationCheckApiNm(TbIotApiDTO tbIotApiDto) throws BizException {
		// TODO Auto-generated method stub
		int chkCnt = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotParamDao.duplicationCheckApiNm");
		try {
			chkCnt = tbIotApiDao.duplicationCheckApiNm(tbIotApiDto);
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
	public int duplicationCheckApiUri(TbIotApiDTO tbIotApiDto) throws BizException {
		// TODO Auto-generated method stub
		int chkCnt = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotParamDao.duplicationCheckApiUri");
		try {
			chkCnt = tbIotApiDao.duplicationCheckApiUri(tbIotApiDto);
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
	public TbIotApiDTO searchTbIotApiParam(TbIotApiDTO tbIotApiDto) throws BizException {
		// TODO Auto-generated method stub
		ComInfoDto temp = null;
		tbIotApiDto.setCurrentPage(1);
		tbIotApiDto.setDisplayRowCount(1);
		TbIotApiDTO returnDto = new TbIotApiDTO();

		List<TbIotApiDTO> apiDtoList = null;
		tbIotApiDto.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotApiDao.retrieveTbIotApiList");
		try {
			tbIotApiDto.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
			apiDtoList = tbIotApiDao.retrieveTbIotApiList(tbIotApiDto);
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

		returnDto = apiDtoList.get(0);
		TbIotParamDTO tbIotParamDto = new TbIotParamDTO();
		tbIotParamDto.setApiSeq(tbIotApiDto.getApiSeq());
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

		returnDto.setTbIotParamDTOList(paramList);

		return returnDto;
	}

	@Override
	public HashMap<String, Object> retrieveTbIotDashApiList(TbIotApiDTO tbIotApiDto) throws BizException {
		ComInfoDto temp = null;
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageDTO pageDto = new PageDTO();

		List<TbIotApiDTO> apiDtoList = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotApiDao.retrieveTbIotDashApiList");
		try {
			apiDtoList = tbIotApiDao.retrieveTbIotDashApiList(tbIotApiDto);
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

		for (int i = 0; i < apiDtoList.size(); i++) {
			TbIotParamDTO tbIotParamDto = new TbIotParamDTO();
			tbIotParamDto.setApiSeq(apiDtoList.get(i).getApiSeq());

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
			apiDtoList.get(i).setTbIotParamDTOList(paramList);
		}

		int totalCount = 0;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotApiDao.retrieveTbIotApiCount");
		try {
			totalCount = tbIotApiDao.retrieveTbIotApiCount(tbIotApiDto);
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

		pageDto.pageCalculate(totalCount, tbIotApiDto.getDisplayRowCount(), tbIotApiDto.getCurrentPage());
		tbIotApiDto.setCurrentPage(pageDto.getRowStart());

		returnMap.put("dataList", apiDtoList);
		returnMap.put("page", pageDto);

		return returnMap;
	}

	@Override
	public List<TbIotParamDTO> retrieveTbIotParamList(TbIotParamDTO tbIotParamDto) throws BizException {
		List<TbIotParamDTO> paramDto = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotParamDao.retrieveTbIotParamList");
		try {
			paramDto = tbIotParamDao.retrieveTbIotParamList(tbIotParamDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
		return paramDto;
	}

	@Override
	public List<HashMap<String, Object>> retrieveTbIotParamCd(TbIotParamDTO tbIotParamDto) throws BizException {
		List<HashMap<String, Object>> returnList = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotParamDao.retrieveTbIotParamCd");
		try {
			returnList = tbIotParamDao.retrieveTbIotParamCd(tbIotParamDto);
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

}
