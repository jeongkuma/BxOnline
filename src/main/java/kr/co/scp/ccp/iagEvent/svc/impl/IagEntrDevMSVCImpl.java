package kr.co.scp.ccp.iagEvent.svc.impl;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.scp.ccp.iagEvent.dao.IagEntrDevMDAO;
import kr.co.scp.ccp.iagEvent.dao.IagEntrDevMapDAO;
import kr.co.scp.ccp.iagEvent.dto.TbIotEntrDevMapDTO;
import kr.co.scp.ccp.iagEvent.svc.IagEntrDevMSVC;
import kr.co.scp.ccp.iotSelDevice.dto.TbIotEntrDevMDTO;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IagEntrDevMSVCImpl implements IagEntrDevMSVC {

	@Autowired
	private IagEntrDevMDAO iagEntrDevMDAO;
	
	@Autowired
	private IagEntrDevMapDAO iagEntrDevMapDAO;

	@Override
	public int insertTbIotEntrDevM(TbIotEntrDevMDTO tbIotEntrDevMDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iagEntrDevMDAO.insertTbIotEntrDevM");

	    try {
			return iagEntrDevMDAO.insertTbIotEntrDevM(tbIotEntrDevMDTO);
		} catch (MyBatisSystemException e) {
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
	public int updateTbIotEntrDevM(TbIotEntrDevMDTO tbIotEntrDevMDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iagEntrDevMDAO.updateTbIotEntrDevM");

	    try {
			return iagEntrDevMDAO.updateTbIotEntrDevM(tbIotEntrDevMDTO);
		} catch (MyBatisSystemException e) {
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
	public int deleteTbIotEntrDevM(TbIotEntrDevMDTO tbIotEntrDevMDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iagEntrDevMDAO.deleteTbIotEntrDevM");

	    try {
			return iagEntrDevMDAO.deleteTbIotEntrDevM(tbIotEntrDevMDTO);
		} catch (MyBatisSystemException e) { 
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
	public TbIotEntrDevMDTO retrieveTbIotEntrDevM(TbIotEntrDevMDTO tbIotEntrDevMDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iagEntrDevMDAO.retrieveTbIotEntrDevM");

	    try {
			return iagEntrDevMDAO.retrieveTbIotEntrDevM(tbIotEntrDevMDTO);
		} catch (MyBatisSystemException e) {
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
	public int insertTbIotEntrDevMap(TbIotEntrDevMapDTO tbIotEntrDevMapDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iagEntrDevMapDAO.insertTbIotEntrDevMap");

	    try {
			return iagEntrDevMapDAO.insertTbIotEntrDevMap(tbIotEntrDevMapDTO);
		} catch (MyBatisSystemException e) {
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
	public int updateTbIotEntrDevMap1(TbIotEntrDevMapDTO tbIotEntrDevMapDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iagEntrDevMapDAO.updateTbIotEntrDevMap1");

	    try {
			return iagEntrDevMapDAO.updateTbIotEntrDevMap1(tbIotEntrDevMapDTO);
		} catch (MyBatisSystemException e) {
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
	public int updateTbIotEntrDevMap2(TbIotEntrDevMapDTO tbIotEntrDevMapDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iagEntrDevMapDAO.updateTbIotEntrDevMap2");

	    try {
			return iagEntrDevMapDAO.updateTbIotEntrDevMap2(tbIotEntrDevMapDTO);
		} catch (MyBatisSystemException e) {
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
	public TbIotEntrDevMapDTO retrieveIotEntrDevMap(TbIotEntrDevMapDTO tbIotEntrDevMapDTO) {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iagEntrDevMapDAO.retrieveIotEntrDevMap");

	    try {
			return iagEntrDevMapDAO.retrieveIotEntrDevMap(tbIotEntrDevMapDTO);
		} catch (MyBatisSystemException e) {
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
