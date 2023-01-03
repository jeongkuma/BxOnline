package kr.co.scp.ccp.iotSelDevice.svc.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotSelDevice.dao.IotSelDeviceDAO;
import kr.co.scp.ccp.iotSelDevice.dto.TbIotCDevMDTO;
import kr.co.scp.ccp.iotSelDevice.dto.TbIotEDevCurValDTO;
import kr.co.scp.ccp.iotSelDevice.dto.TbIotEntrDevMDTO;
import kr.co.scp.ccp.iotSelDevice.svc.IotSelDeviceSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;

@Primary
@Service
public class IotSelDeviceSVCImpl implements IotSelDeviceSVC {

	@Autowired
	IotSelDeviceDAO iotSelDeviceDAO;

	@Override
	public Map<String, Object> retrieveIotSelDevice(TbIotEntrDevMDTO input) throws BizException {
		input.setCustSeq(AuthUtils.getUser().getCustSeq());
		input.setOrgNm(AuthUtils.getUser().getOrgNm());
		List<TbIotEntrDevMDTO> list = new ArrayList<TbIotEntrDevMDTO>();
		List<TbIotCDevMDTO> devs = new ArrayList<TbIotCDevMDTO>();

		ComInfoDto db1 = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSelDeviceDAO.retrieveIotEntrDevM");
		try {
			list = iotSelDeviceDAO.retrieveIotEntrDevM(input); // 단말 목록 조회
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(db1, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(db1, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(db1, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(db1, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(db1);
		}

		ComInfoDto db2 = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSelDeviceDAO.retrieveiotCDevM");
		try {
			devs = iotSelDeviceDAO.retrieveiotCDevM(input); // 표시 단말 목록 조회
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(db2, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(db2, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(db2, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(db2, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(db2);
		}

		for (TbIotEntrDevMDTO a : list) {
			for (TbIotCDevMDTO b : devs) {
				if (a.getDevClsCd().equals(b.getDevClsCd())) {
					b.setCntTotal(b.getCntTotal() + 1);
					if ("A".equals(a.getAttbStatusCd())) {
						b.setCntTrue(b.getCntTrue() + 1);
					} else if ("W".equals(a.getAttbStatusCd())) {
						b.setCntFalse(b.getCntFalse() + 1);
					} else if ("S".equals(a.getAttbStatusCd())) {
						b.setCntStop(b.getCntStop() + 1);
					}
				}
			}
		}

		Map<String, Object> result = new HashedMap<String, Object>();
		result.put("list", list);
		result.put("view", devs);

		return result;
	}

	@Override
	public List<TbIotEDevCurValDTO> retrieveIotSelDeviceDetail(TbIotEntrDevMDTO input) throws BizException {
		input.setCustSeq(AuthUtils.getUser().getCustSeq());
		input.setOrgNm(AuthUtils.getUser().getOrgNm());

		List<TbIotEDevCurValDTO> list = null;

		ComInfoDto db1 = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSelDeviceDAO.retrieveIotDevMAtbVal");
		try {
			input.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
			input.setSvcCd(AuthUtils.getUser().getSvcCd());
			list = iotSelDeviceDAO.retrieveIotDevMAtbVal(input);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(db1, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(db1, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(db1, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(db1, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(db1);
		}

		return list;
	}

}
