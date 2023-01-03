package kr.co.scp.ccp.iagEvent.svc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.scp.ccp.iagEvent.dao.IagSvcDevDAO;
import kr.co.scp.ccp.iagEvent.dto.TbIotIagSvcDevDTO;
import kr.co.scp.ccp.iagEvent.svc.IagSvcDevSVC;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IagSvcDevSVCImpl implements IagSvcDevSVC {

	@Autowired
	private IagSvcDevDAO iagSvcDevDAO;

	@Override
	public List<TbIotIagSvcDevDTO> retrieveTbIotIagSvcDev(String devMdlCd) {
		List<TbIotIagSvcDevDTO> list = null;

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iagSvcDevDAO.retrieveTbIotIagSvcDevIag");

	    try {
	        list = iagSvcDevDAO.retrieveTbIotIagSvcDev(devMdlCd);
	    } catch (BadSqlGrammarException e) {
	        OmsUtils.expInnerOms(temp, e);
	        throw e;
	    } finally {
	        OmsUtils.endInnerOms(temp);
	    }

		return list;
	}
}
