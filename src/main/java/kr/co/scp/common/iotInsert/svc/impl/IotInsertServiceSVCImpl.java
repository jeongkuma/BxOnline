package kr.co.scp.common.iotInsert.svc.impl;

import java.util.HashMap;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.iotInsert.dao.IotInsertServiceDAO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertServiceDTO;
import kr.co.scp.common.iotInsert.svc.IotInsertServiceSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IotInsertServiceSVCImpl implements IotInsertServiceSVC {

	@Autowired
	private IotInsertServiceDAO iotInsertServiceDAO;
	
	public HashMap<String, Object> useChkService(TbIotInsertServiceDTO tbIotInsertServiceDTOList) throws BizException {
		ComInfoDto temp = null;
		
		// 테스트 장비확인
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("svcCd", tbIotInsertServiceDTOList.getSvcCd().toString());
		paramMap.put("langSet",ReqContextComponent.getComInfoDto().getXlang());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.retriveSvcStatus");
		try {
			resultMap = iotInsertServiceDAO.retriveSvcStatus(paramMap);
			if (resultMap != null && !"T".equals(resultMap.get("useYn").toString()) ) {
				throw new BizException("svcUseYn");
			}
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
		
		return resultMap;
	}
	
	public HashMap<String, Object> insertServiceSVC(TbIotInsertServiceDTO tbIotInsertServiceDTOList) throws BizException {
		// TODO Auto-generated method stub
		Integer cmCnt = 0;
		Integer svcCnt = 0;
		TbIotInsertServiceDTO cmCdDTO = null;
		TbIotInsertServiceDTO svcDTO = null;
		TbIotInsertServiceDTO clsImgDTO = null;
		
		ComInfoDto temp = null;
		
		// 테스트 장비확인
//		HashMap<String, Object> resultMap = new HashMap<String, Object>();
//		HashMap<String, Object> paramMap = new HashMap<String, Object>();
//		
//		paramMap.put("svcCd", tbIotInsertServiceDTOList.getSvcCd().toString());
//		resultMap = iotInsertServiceDAO.retriveSvcStatus(paramMap);
//		if (resultMap != null && !"T".equals(resultMap.get("useYn").toString()) ) {
//			throw new BizException("svcUseYn");
//		}
		svcDTO = null;
		
		
		// 장비 이미지 할당
		for (int j = 0; j < tbIotInsertServiceDTOList.getClsImgList().size(); j++) {
			for (int k = 0; k < tbIotInsertServiceDTOList.getClsImgList().get(j).size(); k++) {
				clsImgDTO = tbIotInsertServiceDTOList.getClsImgList().get(j).get(k);
				clsImgDTO.setRegUsrId(AuthUtils.getUser().getUserId());
				if(j==0) {
					//1번만 삭제
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.deleteClsImg");
					try {
						iotInsertServiceDAO.deleteClsImg(clsImgDTO); 
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
				
				
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.insertClsImg");
				try {
					iotInsertServiceDAO.insertClsImg(clsImgDTO);
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
		}	
		
		
		
		// cm_cd 리스트
		for (int j = 0; j < tbIotInsertServiceDTOList.getCmCdList().size(); j++) {
			cmCdDTO = tbIotInsertServiceDTOList.getCmCdList().get(j);
			cmCdDTO.setRegUsrId(AuthUtils.getUser().getUserId());
			
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.deleteCmcd");
			try {
				cmCnt = iotInsertServiceDAO.deleteCmcd(cmCdDTO); //cm_cd 정보
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
			
//			if(cmCnt < 1) {
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.insertCmCd");
				try {
					cmCdDTO.setUseYn("Y");
					iotInsertServiceDAO.insertCmCd(cmCdDTO);
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
				
//			}
		}
		
		// svc_m 리스트
		for (int j = 0; j < tbIotInsertServiceDTOList.getSvcCdList().size(); j++) {
			svcDTO = tbIotInsertServiceDTOList.getSvcCdList().get(j);
			svcDTO.setRegUsrId(AuthUtils.getUser().getUserId());
			
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.deleteSvcM");
			try {
				svcCnt = iotInsertServiceDAO.deleteSvcM(svcDTO); //svc_m 정보
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
			
//			if(svcCnt < 1) {
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertServiceDAO.insertSvcM");
				try {
					iotInsertServiceDAO.insertSvcM(svcDTO);
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
				
//			}
		}
		
		
		return null;
	}


}
