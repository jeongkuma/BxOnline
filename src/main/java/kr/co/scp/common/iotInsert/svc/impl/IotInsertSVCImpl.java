package kr.co.scp.common.iotInsert.svc.impl;

import java.util.HashMap;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.common.iotInsert.dao.IotInsertDAO;
import kr.co.scp.common.iotInsert.dao.IotInsertServiceDAO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertDevAttbDTO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertDevAttbSetDTO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertDevDetSetDTO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertDevListDTO;
import kr.co.scp.common.iotInsert.dto.TbIotInsertDevMDTO;
import kr.co.scp.common.iotInsert.svc.IotInsertSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IotInsertSVCImpl implements IotInsertSVC {

	@Autowired
	private IotInsertDAO iotInsertDAO;
	@Autowired
	private IotInsertServiceDAO iotInsertServiceDAO;

	@Transactional
	public void taskDevM(TbIotInsertDevListDTO tbIotInsertDevListDTO) {


//		HashMap<String, Object> resultMap = new HashMap<String, Object>();
//		HashMap<String, Object> paramMap = new HashMap<String, Object>();
//		String svcCd1 = tbIotInsertDevListDTO.getSvcCd();
//		
//		paramMap.put("svcCd",  svcCd1);
//		resultMap = iotInsertServiceDAO.retriveSvcStatus(paramMap);
//		if (resultMap != null && !"T".equals(resultMap.get("useYn").toString()) ) {
//			throw new BizException("svcUseYn");
//		}
		

		ComInfoDto temp = null;

//		List<TbIotInsertDevMDTO> tbIotInsertDevMDTOIt = tbIotInsertDevListDTO.getDevMList();
		String custId = AuthUtils.getUser().getCustId();
		Integer svcCnt = 0;
		
		// dev_m 루프
		for (TbIotInsertDevMDTO devMList : tbIotInsertDevListDTO.getDevM()) {
			// dev_m 테이블이 존재시 업데이트
			
			// dev_m 테이블이 미존재시 인서트
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertDAO.deleteIotSDevCnt");
			try {
				iotInsertDAO.deleteIotSDevCnt(devMList); //svc_m 정보
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
			
//			if ( svcCnt < 1) {
				devMList.setRegUsrId(custId);
				
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertDAO.insertSDevM");
				try {
					iotInsertDAO.insertSDevM(devMList);
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
			svcCnt = 0;
		
			// dev_attb 루프
			for (TbIotInsertDevAttbDTO devAttb : tbIotInsertDevListDTO.getDevAttb()) {
				if (!devAttb.getSvcDevSeq().equals(devMList.getSvcDevSeq())) continue;
				
				// dev_attb 테이블이 존재시 업데이트
				
				// dev_attb 테이블이 미존재시 인서트
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertDAO.deleteIotSDevAttbCnt");
				try {
					iotInsertDAO.deleteIotSDevAttbCnt(devAttb);
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
				
//				if ( svcCnt < 1) {
					devAttb.setRegUsrId(custId);
					
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertDAO.insertSDevAttb");
					try {
						iotInsertDAO.insertSDevAttb(devAttb);
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
					
//				}
				svcCnt = 0;
				
				// dev_attb_set 루프
				for (TbIotInsertDevAttbSetDTO devAttbSet : tbIotInsertDevListDTO.getDevAttbSet()) {
					if (!devAttb.getSvcDevAttbSeq().equals(devAttbSet.getSvcDevAttbSeq())) continue;
					
					// dev_attb 테이블이 존재시 업데이트
					
					// dev_attb 테이블이 미존재시 인서트
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertDAO.deleteIotSDevAttbSetCnt");
					try {
						iotInsertDAO.deleteIotSDevAttbSetCnt(devAttbSet);
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
					
//					if ( svcCnt < 1) {
						devAttbSet.setRegUsrId(custId);
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertDAO.insertSDevAttbSet");
						try {
							iotInsertDAO.insertSDevAttbSet(devAttbSet);
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
						
//					}
					svcCnt = 0;
				}
				
				// dev_det_set 루프
				for (TbIotInsertDevDetSetDTO devDetSet : tbIotInsertDevListDTO.getDevDetSet()) {
					if (!devAttb.getSvcDevAttbSeq().equals(devDetSet.getSvcDevAttbSeq())) continue;
					
					// dev_det_set 테이블이 존재시 업데이트
					
					// dev_det_set 테이블이 미존재시 인서트
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertDAO.deleteIotSDevDetSetCnt");
					try {
						iotInsertDAO.deleteIotSDevDetSetCnt(devDetSet);
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
					
//					if ( svcCnt < 1) {
						devDetSet.setRegUsrId(custId);
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotInsertDAO.insertSDevDetSet");
						try {
							iotInsertDAO.insertSDevDetSet(devDetSet);
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
						
//					}
					svcCnt = 0;
				}
				/**/
			}

		}
		
		
	}



}
	
