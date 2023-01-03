package kr.co.scp.ccp.iagEvent.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.scp.ccp.iagEvent.dao.IagEntrDevCurValDAO;
import kr.co.scp.ccp.iagEvent.dto.TbIotIagEntrDevCurValDTO;
import kr.co.scp.ccp.iagEvent.svc.IagEntrDevCurValSVC;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IagEntrDevCurValSVCImpl implements IagEntrDevCurValSVC {

	@Autowired
	private IagEntrDevCurValDAO iagEntrDevCurValDAO;

	@Override
	public void insertTbIotIagEntrDevCurVal(TbIotIagEntrDevCurValDTO tbIotIagEntrDevCurValDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_IN, "iagEntrDevCurValDAO.insertTbIotIagEntrDevCurVal");
		
	    try {
	    	iagEntrDevCurValDAO.insertTbIotIagEntrDevCurVal(tbIotIagEntrDevCurValDTO);
	    } catch (BadSqlGrammarException e) {
	        OmsUtils.expInnerOms(temp, e);
	        throw e;
	    } finally {
	        OmsUtils.endInnerOms(temp);
	    }
	}
}
