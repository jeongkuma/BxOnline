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
import kr.co.scp.ccp.iotDev.dao.IotDevAttrSetParamDAO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrSetDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrSetParamDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevAttrSetParamSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;





@Primary
@Service
public class IotDevAttrSetParamSVCimpl implements IotDevAttrSetParamSVC {



	@Autowired
	private IotDevAttrSetParamDAO  iotDevAttrSetParamDAO;






	@Override
	public void taskIotDevAttrSetParam(TbIotDevAttrSetParamDTO tbIotDevAttSetParamDTO) {
		ComInfoDto temp = null;


		try {

		List<TbIotDevAttrSetDTO> tbIotDevAttrSetDTOit = tbIotDevAttSetParamDTO.getTbIotDevAttrSetDTOList();
		for ( TbIotDevAttrSetDTO tbIotDevAttrSetDTOList : tbIotDevAttrSetDTOit) {
			String custId =  AuthUtils.getUser().getCustId();
			tbIotDevAttrSetDTOList.setSvcCd(AuthUtils.getUser().getSvcCd());
			if (tbIotDevAttrSetDTOList.getMode().equals("I")) {
				tbIotDevAttrSetDTOList.setRegUsrId(custId);
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotDevAttrSetParamDAO.insertTbIotDevSetParam");
				iotDevAttrSetParamDAO.insertTbIotDevSetParam(tbIotDevAttrSetDTOList);
			} else if (tbIotDevAttrSetDTOList.getMode().equals("U")) {
				tbIotDevAttrSetDTOList.setModUsrId(custId);

				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotDevAttrSetParamDAO.updateTbIotDevSetParam");
				iotDevAttrSetParamDAO.updateTbIotDevSetParam(tbIotDevAttrSetDTOList);
			}
			else if(tbIotDevAttrSetDTOList.getMode().equals("D")) {
				tbIotDevAttrSetDTOList.setModUsrId(custId);

				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotDevAttrSetParamDAO.deleteTbIotDevSetParam");
				iotDevAttrSetParamDAO.deleteTbIotDevSetParam(tbIotDevAttrSetDTOList);
		    }
		 }
		}  catch (MyBatisSystemException e) {
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
	public void insertIotDevAttrSet(TbIotDevAttrSetDTO tbIotDevAttrSetDTO) throws BizException {
		// TODO Auto-generated method stub

	}





	@Override
	public void updateIotDevAttrSet(TbIotDevAttrSetDTO tbIotDevAttrSetDTO) throws BizException {
		// TODO Auto-generated method stub

	}





	@Override
	public void deleteIotDevAttrSet(TbIotDevAttrSetDTO tbIotDevAttrSetDTO) throws BizException {
		// TODO Auto-generated method stub

	}
}
