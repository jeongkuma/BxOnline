package kr.co.scp.ccp.iagEvent.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.scp.ccp.iagEvent.dao.IagLogDAO;
import kr.co.scp.ccp.iagEvent.dto.TbIotIagLogDTO;
import kr.co.scp.ccp.iagEvent.svc.IagLogSVC;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IagLogSVCImpl implements IagLogSVC {

	@Autowired
	private IagLogDAO iagLogDAO;

	@Override
	public void insertTbIotIagLog(TbIotIagLogDTO tbIotIagLogDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_IN, "iagLogDAO.insertTbIotIagLog");

	    try {
	    	iagLogDAO.insertTbIotIagLog(tbIotIagLogDTO);
	    } catch (BadSqlGrammarException e) {
	        OmsUtils.expInnerOms(temp, e);
	        throw e;
	    } finally {
	        OmsUtils.endInnerOms(temp);
	    }
	}

	@Override
	public void updateTbIotIagLog(TbIotIagLogDTO tbIotIagLogDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_IN, "iagLogDAO.updateTbIotIagLog");

	    try {
	    	iagLogDAO.updateTbIotIagLog(tbIotIagLogDTO);
	    } catch (BadSqlGrammarException e) {
	        OmsUtils.expInnerOms(temp, e);
	        throw e;
	    } finally {
	        OmsUtils.endInnerOms(temp);
	    }
	}

}
