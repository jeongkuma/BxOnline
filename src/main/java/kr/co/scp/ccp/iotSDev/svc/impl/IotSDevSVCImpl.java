package kr.co.scp.ccp.iotSDev.svc.impl;

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

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.logger.Log;
import kr.co.scp.ccp.iotSDev.dao.IotSDevDAO;
import kr.co.scp.ccp.iotSDev.dto.TbIotSDevAtbDTO;
import kr.co.scp.ccp.iotSDev.dto.TbIotSDevAtbSetDTO;
import kr.co.scp.ccp.iotSDev.dto.TbIotSDevDTO;
import kr.co.scp.ccp.iotSDev.svc.IotSDevSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;

@Primary
@Slf4j
@Service
public class IotSDevSVCImpl implements IotSDevSVC {

	@Autowired
	private IotSDevDAO iotSDevDAO;

	@Override
	public TbIotSDevDTO retrieveSDev(TbIotSDevDTO tbIotSDevDTO) {
		List<TbIotSDevAtbDTO> atbList = new ArrayList<TbIotSDevAtbDTO>();
		ComInfoDto temp = null;
		try {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotSDevDAO.retrieveSDev");
			tbIotSDevDTO = iotSDevDAO.retrieveSDev(tbIotSDevDTO);
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"iotSDevDAO.retrieveSDevAtbList");
			atbList = iotSDevDAO.retrieveSDevAtbList(tbIotSDevDTO);
			if (atbList != null && !atbList.isEmpty()) {
				for (TbIotSDevAtbDTO tbIotSDevAtbDTO : atbList) {
					if ("SELECT".equals(tbIotSDevAtbDTO.getInputType())) {
						List<TbIotSDevAtbSetDTO> atbSetList = new ArrayList<TbIotSDevAtbSetDTO>();
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
								"iotSDevDAO.retrieveSDevAtbSetList");
						tbIotSDevAtbDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
						atbSetList = iotSDevDAO.retrieveSDevAtbSetList(tbIotSDevAtbDTO);

						if (atbSetList != null && !atbSetList.isEmpty() && atbSetList.size() > 0) {
							tbIotSDevAtbDTO.setSvcDevAtbSets(atbSetList);
						}
					}
				}
			}
			tbIotSDevDTO.setIotCustDevAtbs(atbList);
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

		return tbIotSDevDTO;
	}

	@Override
	public List<TbIotSDevDTO> retrieveSDevList(TbIotSDevDTO tbIotSDevDTO) {
		List<TbIotSDevDTO> iotSDevDTOs = new ArrayList<TbIotSDevDTO>();
		ComInfoDto temp = null;
		try {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotSDevDAO.retrieveSDevList");
			iotSDevDTOs = iotSDevDAO.retrieveSDevList(tbIotSDevDTO);
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
		return iotSDevDTOs;
	}

	@Override
	public List<TbIotSDevAtbDTO> retrieveSDevAttb(TbIotSDevDTO tbIotSDevDTO) {
		List<TbIotSDevAtbDTO> atbList = new ArrayList<TbIotSDevAtbDTO>();
		ComInfoDto temp = null;
		try {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"iotSDevDAO.retrieveSDevAtbList");
			atbList = iotSDevDAO.retrieveSDevAtbList(tbIotSDevDTO);
			if (atbList != null && !atbList.isEmpty()) {
				for (TbIotSDevAtbDTO tbIotSDevAtbDTO : atbList) {
					if ("SELECT".equals(tbIotSDevAtbDTO.getInputType())) {
						List<TbIotSDevAtbSetDTO> atbSetList = new ArrayList<TbIotSDevAtbSetDTO>();
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
								"iotSDevDAO.retrieveSDevAtbSetList");
						tbIotSDevAtbDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
						atbSetList = iotSDevDAO.retrieveSDevAtbSetList(tbIotSDevAtbDTO);

						if (atbSetList != null && !atbSetList.isEmpty() && atbSetList.size() > 0) {
							tbIotSDevAtbDTO.setSvcDevAtbSets(atbSetList);
						}
					}
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

		return atbList;
	}

	@Override
	public List<TbIotSDevAtbDTO> retrieveSDevJoinAttbs(TbIotSDevDTO tbIotSDevDTO) {
		List<TbIotSDevAtbDTO> atbList = new ArrayList<TbIotSDevAtbDTO>();
		ComInfoDto temp = null;
		tbIotSDevDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		try {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotSDevDAO.retrieveSDevJoinAttbs");
			atbList = iotSDevDAO.retrieveSDevJoinAttbs(tbIotSDevDTO);
			if (atbList != null && !atbList.isEmpty()) {
				for (TbIotSDevAtbDTO tbIotSDevAtbDTO : atbList) {
					if ("SELECT".equals(tbIotSDevAtbDTO.getInputType())) {
						List<TbIotSDevAtbSetDTO> atbSetList = new ArrayList<TbIotSDevAtbSetDTO>();
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
								"iotSDevDAO.retrieveSDevAtbSetList");
						tbIotSDevAtbDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
						atbSetList = iotSDevDAO.retrieveSDevAtbSetList(tbIotSDevAtbDTO);

						if (atbSetList != null && !atbSetList.isEmpty() && atbSetList.size() > 0) {
							tbIotSDevAtbDTO.setSvcDevAtbSets(atbSetList);
						}
					}
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

		return atbList;
	}

	@Override
	public List<TbIotSDevDTO> retrieveSvcDevClsList(TbIotSDevDTO tbIotSDevDTO) {
		List<TbIotSDevDTO> iotSDevDTOs = new ArrayList<TbIotSDevDTO>();
		ComInfoDto temp = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSDevDAO.retrieveSvcDevClsList");
		try {
//			log.debug("SVC_CD : " + AuthUtils.getUser().getSvcCd());
//			tbIotSDevDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
			iotSDevDTOs = iotSDevDAO.retrieveSvcDevClsList(tbIotSDevDTO);
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

		return iotSDevDTOs;
	}

	@Override
	public List<TbIotSDevDTO> retrieveSvcDevMdlList(TbIotSDevDTO tbIotSDevDTO) {
		List<TbIotSDevDTO> iotSDevDTOs = new ArrayList<TbIotSDevDTO>();
		ComInfoDto temp = null;
		try {
			log.debug("SVC_CD : " + AuthUtils.getUser().getSvcCd());
			tbIotSDevDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"iotSDevDAO.retrieveSvcDevMdlList");
			iotSDevDTOs = iotSDevDAO.retrieveSvcDevMdlList(tbIotSDevDTO);
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

		return iotSDevDTOs;
	}

	@Override
	public HashMap<String, Object> retrieveSDevSndColPeriodList(TbIotSDevAtbDTO tbIotSDevDto) {
		ComInfoDto temp = null;
		HashMap<String, Object> returnMap = new HashMap<String, Object>();

		List<TbIotSDevAtbSetDTO> sndHList = null;
		List<TbIotSDevAtbSetDTO> sndMList = null;
		List<TbIotSDevAtbSetDTO> sndSList = null;
		List<TbIotSDevAtbSetDTO> colHList = null;
		List<TbIotSDevAtbSetDTO> colMList = null;
		List<TbIotSDevAtbSetDTO> colSList = null;

		tbIotSDevDto.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotSDevDAO.retrieveSDevSndColPeriodList");
		try {
			//통신주기 초
			tbIotSDevDto.setDevAttbCdId("DA00000072");
			tbIotSDevDto.setDevSetAttbCdId("DS00000008");
			sndSList = iotSDevDAO.retrieveSDevSndColPeriodList(tbIotSDevDto);
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

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotSDevDAO.retrieveSDevSndColPeriodList");
		try {
			//통신주기 분
			tbIotSDevDto.setDevAttbCdId("DA00000072");
			tbIotSDevDto.setDevSetAttbCdId("DS00000016");
			sndMList = iotSDevDAO.retrieveSDevSndColPeriodList(tbIotSDevDto);
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

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotSDevDAO.retrieveSDevSndColPeriodList");
		try {
			//통신주기 시
			tbIotSDevDto.setDevAttbCdId("DA00000072");
			tbIotSDevDto.setDevSetAttbCdId("DS00000023");
			sndHList = iotSDevDAO.retrieveSDevSndColPeriodList(tbIotSDevDto);
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

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotSDevDAO.retrieveSDevSndColPeriodList");
		try {
			//계측주기 초
			tbIotSDevDto.setDevAttbCdId("DA00000071");
			tbIotSDevDto.setDevSetAttbCdId("DS00000008");
			colSList = iotSDevDAO.retrieveSDevSndColPeriodList(tbIotSDevDto);
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

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotSDevDAO.retrieveSDevSndColPeriodList");
		try {
			//계측주기 분
			tbIotSDevDto.setDevAttbCdId("DA00000071");
			tbIotSDevDto.setDevSetAttbCdId("DS00000016");
			colMList = iotSDevDAO.retrieveSDevSndColPeriodList(tbIotSDevDto);
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

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotSDevDAO.retrieveSDevSndColPeriodList");
		try {
			//계측주기 시
			tbIotSDevDto.setDevAttbCdId("DA00000071");
			tbIotSDevDto.setDevSetAttbCdId("DS00000023");
			colHList = iotSDevDAO.retrieveSDevSndColPeriodList(tbIotSDevDto);
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

		returnMap.put("sndHPeriodList", sndHList);
		returnMap.put("sndMPeriodList", sndMList);
		returnMap.put("sndSPeriodList", sndSList);
		returnMap.put("colHPeriodList", colHList);
		returnMap.put("colMPeriodList", colMList);
		returnMap.put("colSPeriodList", colSList);
		return returnMap;
	}

	@Override
	public List<TbIotSDevAtbDTO> retrieveSDevColAttb(TbIotSDevDTO tbIotSDevDTO) {
		List<TbIotSDevAtbDTO> colAttbList = new ArrayList<TbIotSDevAtbDTO>();
		ComInfoDto temp = null;
		tbIotSDevDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
		tbIotSDevDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		try {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotSDevDAO.retrieveSDevColAttbList");
			colAttbList = iotSDevDAO.retrieveSDevColAttbList(tbIotSDevDTO);
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

		return colAttbList;
	}

}
