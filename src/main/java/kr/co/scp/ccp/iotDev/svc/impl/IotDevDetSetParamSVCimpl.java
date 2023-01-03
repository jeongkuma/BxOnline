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
import kr.co.scp.ccp.iotDev.dao.IotDevDetSetParamDAO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDetSetDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDetSetParamDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevDetSetParamSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;





@Primary
@Service
public class IotDevDetSetParamSVCimpl implements IotDevDetSetParamSVC {



	@Autowired
	private IotDevDetSetParamDAO  iotDevDetSetParamDAO;






	@Override
	public void taskIotDevDetSetParam(TbIotDevDetSetParamDTO tbIotDevAttSetParamDTO) {
		ComInfoDto temp = null;


		try {


		List<TbIotDevDetSetDTO> tbIotDevDetSetDTOit = tbIotDevAttSetParamDTO.getTbIotDevDetSetDTOList();
		for ( TbIotDevDetSetDTO tbIotDevDetSetDTOList : tbIotDevDetSetDTOit) {
			String custId =  AuthUtils.getUser().getCustId();

			tbIotDevDetSetDTOList.setRegUsrId(custId);
			if (tbIotDevDetSetDTOList.getMode().equals("I")) {
				tbIotDevDetSetDTOList.setModUserId(custId);
				tbIotDevDetSetDTOList.setSvcCd(AuthUtils.getUser().getSvcCd());
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotDevDetSetParamDAO.insertTbIotDevDetSetParam");
				iotDevDetSetParamDAO.insertTbIotDevDetSetParam(tbIotDevDetSetDTOList);
			} else if (tbIotDevDetSetDTOList.getMode().equals("U")) {
				tbIotDevDetSetDTOList.setModUserId(custId);
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotDevDetSetParamDAO.updateTbIotDevDetSetParam");
				iotDevDetSetParamDAO.updateTbIotDevDetSetParam(tbIotDevDetSetDTOList);
			}
			   else if (tbIotDevDetSetDTOList.getMode().equals("D")) {
					tbIotDevDetSetDTOList.setModUserId(custId);
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
							"iotDevDetSetParamDAO.deleteTbIotDevDetSetParam");
				   iotDevDetSetParamDAO.deleteTbIotDevDetSetParam(tbIotDevDetSetDTOList);
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
	public void insertTbIotDevDetSetParam(TbIotDevDetSetDTO tbIotDevDetSetDTO) throws BizException {
		// TODO Auto-generated method stub

	}






	@Override
	public void updateTbIotDevDetSetParam(TbIotDevDetSetDTO tbIotDevDetSetDTO) throws BizException {
		// TODO Auto-generated method stub

	}






	@Override
	public void deleteTbIotDevDetSetParam(TbIotDevDetSetDTO tbIotDevDetSetDTO) throws BizException {
		// TODO Auto-generated method stub

	}
}



