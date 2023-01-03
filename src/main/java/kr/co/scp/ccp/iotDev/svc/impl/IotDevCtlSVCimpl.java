package kr.co.scp.ccp.iotDev.svc.impl;

import java.util.List;

import org.apache.http.auth.AUTH;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dao.IotDevCtlDAO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevCtlDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevCtlSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;

@Primary
@Service
public class IotDevCtlSVCimpl implements IotDevCtlSVC {

	@Autowired
	private IotDevCtlDAO iotDevCtlDAO;

	@Override
	public int retrieveIotDup(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		return iotDevCtlDAO.retrieveIotDup(tbIotDevCtlDTO);
	}

	@Override
	public int retrieveIotMdlDup(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		return iotDevCtlDAO.retrieveIotMdlDup(tbIotDevCtlDTO);
	}

	@Override
	@Transactional

	public void insertIotDevCopy(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		// TODO Auto-generated method stub
		ComInfoDto temp = null;

       try {


		String custId =  AuthUtils.getUser().getCustId();
		tbIotDevCtlDTO.setModUserId(custId);
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevCtlDAO.insertIotDevSeq");
		tbIotDevCtlDTO.setRegUsrId(AuthUtils.getUser().getUserId());
		iotDevCtlDAO.insertIotDevSeq(tbIotDevCtlDTO);

		String sseq = tbIotDevCtlDTO.getOrgDevSeq(); // 166
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevCtlDAO.retrieveIotAttbSeq");
		String[] seq = iotDevCtlDAO.retrieveIotAttbSeq(tbIotDevCtlDTO);
		if (seq != null) {
			if (seq.length > 0) {
				tbIotDevCtlDTO.setDevSeq(sseq);
				for (int i = 0; i < seq.length; i++) {
					tbIotDevCtlDTO.setDevAttbSeq(seq[i]);
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevCtlDAO.insertIotDevAttrSeq");
					iotDevCtlDAO.insertIotDevAttrSeq(tbIotDevCtlDTO); //
                    String ssseq = tbIotDevCtlDTO.getOrgDevSetSeq();
                	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevCtlDAO.retrieveIotDevAttrSetSeq");
					String[] res1 = iotDevCtlDAO.retrieveIotDevAttrSetSeq(tbIotDevCtlDTO);
                	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevCtlDAO.retrieveIotDevDetSetSeq");
					String[] res2 = iotDevCtlDAO.retrieveIotDevDetSetSeq(tbIotDevCtlDTO);
					TbIotDevCtlDTO dto = new TbIotDevCtlDTO();
					dto.setModUserId(custId);
					if( res1.length > 0)
					for (int j = 0; j < res1.length; j++) {
						dto.setDevAttbSetSeq(res1[j]);
						dto.setOrgDevSetSeq(ssseq);

		               	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,"iotDevCtlDAO.insertIotDevAttrSetSeq");
						iotDevCtlDAO.insertIotDevAttrSetSeq(dto);
					}



					TbIotDevCtlDTO dto1 = new TbIotDevCtlDTO();
					dto1.setModUserId(custId);
					if( res2.length > 0)
				    for (int d = 0; d < res2.length; d++) {
				         dto1.setDevDetSetSeq(res2[d]);
						 dto1.setOrgDevSetSeq(ssseq);

			               	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
									"iotDevCtlDAO.insertIotDevDetSetSeq");
					     iotDevCtlDAO.insertIotDevDetSetSeq(dto1); }
			    	}
				}
		}
			}catch (MyBatisSystemException e) {
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
	public String[] retrieveIotAttbSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertIotDevAttrSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] retrieveIotDevAttrSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public void insertIotDevAttrSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public String[] retrieveIotDevDetSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public void insertIotDevDetSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer retrieveIotDevCtlAttbSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public void insertIotDevCtlAttbSetSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertIotDevSeq(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<TbIotDevCtlDTO> retrieveIotSvcbyHd(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		ComInfoDto temp = null;
		 List<TbIotDevCtlDTO> list = null;
		 try {
			 temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevCtlDAO.retrieveIotSvcbyHd");
			 list = iotDevCtlDAO.retrieveIotSvcbyHd(tbIotDevCtlDTO);
		 }   catch (MyBatisSystemException e) {
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
			return list;
			}

	@Override
	public List<TbIotDevCtlDTO> retrieveIotClsbyHd(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		ComInfoDto temp = null;
		 List<TbIotDevCtlDTO> list = null;
		 try {
			 temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevCtlDAO.retrieveIotClsbyHd");
			 list = iotDevCtlDAO.retrieveIotClsbyHd(tbIotDevCtlDTO);
		 }   catch (MyBatisSystemException e) {
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
			return list;
			}


	@Override
	public List<TbIotDevCtlDTO> retrieveIotHdbyCls(TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		ComInfoDto temp = null;
		 List<TbIotDevCtlDTO> list = null;
		 try {
			 temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevCtlDAO.retrieveIotHdbyCls");
			 list = iotDevCtlDAO.retrieveIotHdbyCls(tbIotDevCtlDTO);
		 }   catch (MyBatisSystemException e) {
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
			return list;
			}


	@Override
	public int retrieveIotDevMdlValCnt(TbIotDevCtlDTO tbIotDevCtl1DTO) throws BizException {
		int cnt = 0;
		ComInfoDto temp =new ComInfoDto();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevCtlDAO.retrieveIotDevMdlValCnt");
		try {
			cnt = iotDevCtlDAO.retrieveIotDevMdlValCnt(tbIotDevCtl1DTO);
			} catch (MyBatisSystemException e) {OmsUtils.expInnerOms(temp, e); throw e;} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
		return cnt;
	}
}
