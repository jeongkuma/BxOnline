package kr.co.scp.ccp.iotDev.svc.impl;

import java.util.List;

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
import kr.co.scp.ccp.iotDev.dao.IotDevAttrParamDAO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrParamDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevAttrParamSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;

@Primary
@Service
public class IotDevAttrParamSVCimpl implements IotDevAttrParamSVC {

	@Autowired
	private IotDevAttrParamDAO iotDevAttrParamDAO;

	@Override
	public void taskIotDevAttrParam(TbIotDevAttrParamDTO tbIotDevAttrParamDTO) {

		// String SSeq = tbIotDevDTO.getOrgDevSeq();
		// tbIotDevAttrDTO.setDevSeq(SSeq);
		ComInfoDto temp = null;
		List<TbIotDevAttrDTO> tbIotDevAttrDTOit = tbIotDevAttrParamDTO.getTbIotDevAttrDTOList();
		for (TbIotDevAttrDTO tbIotDevAttrDTOList : tbIotDevAttrDTOit) {
			String custId = AuthUtils.getUser().getCustId();
			tbIotDevAttrDTOList.setSvcCd(AuthUtils.getUser().getSvcCd());

			try {
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevAttrParamDAO.insertTbIotDevParam(tbIotDevAttrDTOList");
				if (tbIotDevAttrDTOList.getMode().equals("I")) {
					tbIotDevAttrDTOList.setRegUsrId(custId);
					iotDevAttrParamDAO.insertTbIotDevParam(tbIotDevAttrDTOList);
				} else if (tbIotDevAttrDTOList.getMode().equals("U")) {
					tbIotDevAttrDTOList.setModUsrId(custId);
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevAttrParamDAO.updateTbIotDevParam");
					iotDevAttrParamDAO.updateTbIotDevParam(tbIotDevAttrDTOList);
				} else if (tbIotDevAttrDTOList.getMode().equals("D")) {
					tbIotDevAttrDTOList.setModUsrId(custId);
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevAttrParamDAO.retrieveIotAttbSetCnt");
					int cnt1 = iotDevAttrParamDAO.retrieveIotAttbSetCnt(tbIotDevAttrDTOList);
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevAttrParamDAO.retrieveIotDetSetCnt");
					int cnt2 = iotDevAttrParamDAO.retrieveIotDetSetCnt(tbIotDevAttrDTOList);
					if (cnt1 > 0) {
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 	"iotDevAttrParamDAO.deleteTbIotDevAttbSetParam");
						iotDevAttrParamDAO.deleteTbIotDevAttbSetParam(tbIotDevAttrDTOList);
					}
					if (cnt2 > 0) {
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevAttrParamDAO.deleteTbIotDevDetSetParam");
						iotDevAttrParamDAO.deleteTbIotDevDetSetParam(tbIotDevAttrDTOList);
					}
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevAttrParamDAO.deleteTbIotDevParam");
					iotDevAttrParamDAO.deleteTbIotDevParam(tbIotDevAttrDTOList);
				}
			}

			catch (MyBatisSystemException e) {
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
	public void insertIotDevAttr(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateIotDevAttr(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteIotDevAttr(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public int retrieveIotAttbSetCnt(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int retrieveIotDetSetCnt(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteTbIotDevAttbSetParam(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTbIotDevDetSetParam(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException {
		// TODO Auto-generated method stub

	}
}
