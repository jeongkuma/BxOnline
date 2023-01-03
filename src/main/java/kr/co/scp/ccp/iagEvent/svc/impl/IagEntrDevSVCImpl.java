package kr.co.scp.ccp.iagEvent.svc.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.scp.ccp.iagEvent.dao.IagEntrDevDAO;
import kr.co.scp.ccp.iagEvent.dto.TbIotIagEntrDevDTO;
import kr.co.scp.ccp.iagEvent.svc.IagEntrDevSVC;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IagEntrDevSVCImpl implements IagEntrDevSVC {

	@Autowired
	private IagEntrDevDAO iagEntrDevDAO;

	@Override
	public Integer retrieveTbIotIagEntrDev(TbIotIagEntrDevDTO tbIotIagEntrDevDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iagEntrDevDAO.retrieveTbIotIagEntrDev");

		int count;
	    try {
	        count = iagEntrDevDAO.retrieveTbIotIagEntrDev(tbIotIagEntrDevDTO);
	    } catch (BadSqlGrammarException e) {
	        OmsUtils.expInnerOms(temp, e);
	        throw e;
	    } finally {
	        OmsUtils.endInnerOms(temp);
	    }
	    
		return count;
	}

	@Override
	public List<String> retrieveTbIotIagEntrDevImg(TbIotIagEntrDevDTO tbIotIagEntrDevDTO) {
		List<String> list = null;
		
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iagEntrDevDAO.retrieveTbIotIagEntrDevImg");

	    try {
	        list = iagEntrDevDAO.retrieveTbIotIagEntrDevImg(tbIotIagEntrDevDTO);
	    } catch (BadSqlGrammarException e) {
	        OmsUtils.expInnerOms(temp, e);
	        throw e;
	    } finally {
	        OmsUtils.endInnerOms(temp);
	    }

		return list;
	}

	@Override
	public void insertTbIotIagEntrDev(TbIotIagEntrDevDTO tbIotIagEntrDevDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_IN, "iagEntrDevDAO.insertTbIotIagEntrDev");
		
	    try {
	    	iagEntrDevDAO.insertTbIotIagEntrDev(tbIotIagEntrDevDTO);
	    } catch (BadSqlGrammarException e) {
	        OmsUtils.expInnerOms(temp, e);
	        throw e;
	    } finally {
	        OmsUtils.endInnerOms(temp);
	    }
	}

	@Override
	public void updateTbIotIagEntrDev(TbIotIagEntrDevDTO tbIotIagEntrDevDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_IN, "iagEntrDevDAO.updateTbIotIagEntrDev");
		
	    try {
	    	iagEntrDevDAO.updateTbIotIagEntrDev(tbIotIagEntrDevDTO);
	    } catch (BadSqlGrammarException e) {
	        OmsUtils.expInnerOms(temp, e);
	        throw e;
	    } finally {
	        OmsUtils.endInnerOms(temp);
	    }
	}

	@Override
	public void updateTbIotIagEntrDevC17CAN(TbIotIagEntrDevDTO tbIotIagEntrDevDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_IN, "iagEntrDevDAO.updateTbIotIagEntrDevC17CAN");
		
	    try {
	    	iagEntrDevDAO.updateTbIotIagEntrDevC17CAN(tbIotIagEntrDevDTO);
	    } catch (BadSqlGrammarException e) {
	        OmsUtils.expInnerOms(temp, e);
	        throw e;
	    } finally {
	        OmsUtils.endInnerOms(temp);
	    }
	}
}
