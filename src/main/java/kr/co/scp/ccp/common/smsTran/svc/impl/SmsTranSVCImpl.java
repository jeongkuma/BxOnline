package kr.co.scp.ccp.common.smsTran.svc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.scp.ccp.common.smsTran.dao.SmsTranDAO;
import kr.co.scp.ccp.common.smsTran.dto.TbIotSmsTranDTO;
import kr.co.scp.ccp.common.smsTran.svc.SmsTranSVC;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;

@Primary
@Slf4j
@Service
public class SmsTranSVCImpl implements SmsTranSVC {
	
	@Autowired
	private SmsTranDAO smsTranDAO;

	@Override
	public void insertTbIotSmsTran(TbIotSmsTranDTO tbIotSmsTranDTO, boolean isManualOmsLog) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_IN, "smsTranDAO.insertTbIotSmsTran");
		
	    try {
	    	smsTranDAO.insertTbIotSmsTran(tbIotSmsTranDTO);
	    } catch (BadSqlGrammarException e) {
	        OmsUtils.expInnerOms(temp, e);
	        throw e;
	    } finally {
	        OmsUtils.endInnerOms(temp);
	    }

		if(isManualOmsLog) {
			// 추후 필요 시 추가
		}
	}
}
