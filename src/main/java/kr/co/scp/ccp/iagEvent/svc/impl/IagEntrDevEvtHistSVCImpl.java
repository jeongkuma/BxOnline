package kr.co.scp.ccp.iagEvent.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.scp.ccp.iagEvent.dao.IagEntrDevEvtHistDAO;
import kr.co.scp.ccp.iagEvent.dto.TbIotIagEntrDevEvtHistDTO;
import kr.co.scp.ccp.iagEvent.svc.IagEntrDevEvtHistSVC;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IagEntrDevEvtHistSVCImpl implements IagEntrDevEvtHistSVC {

	@Autowired
	private IagEntrDevEvtHistDAO iagEntrDevEvtHistDAO;

	@Override
	public void insertTbIotIagEntrDevEvtHist(TbIotIagEntrDevEvtHistDTO tbIotIagEntrDevEvtHistDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_IN, "iagEntrDevEvtHistDAO.insertTbIotIagEntrDevEvtHist");

	    try {
	    	iagEntrDevEvtHistDAO.insertTbIotIagEntrDevEvtHist(tbIotIagEntrDevEvtHistDTO);
	    } catch (BadSqlGrammarException e) {
	        OmsUtils.expInnerOms(temp, e);
	        throw e;
	    } finally {
	        OmsUtils.endInnerOms(temp);
	    }
	}
}
