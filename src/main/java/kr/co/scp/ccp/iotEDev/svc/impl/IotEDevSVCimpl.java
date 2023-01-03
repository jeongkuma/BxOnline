package kr.co.scp.ccp.iotEDev.svc.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.LinkedHashMap;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.ccp.iotEDev.dao.IotEDevDAO;
import kr.co.scp.ccp.iotEDev.dto.EDevSrchResDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDetHistDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevDTO;
import kr.co.scp.ccp.iotEDev.svc.IotEDevSVC;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Primary
@Slf4j
@Service
public class IotEDevSVCimpl implements IotEDevSVC {

	@Autowired
	private IotEDevDAO iotEDevDAO;

	@Override
	public EDevSrchResDTO retrieveIotEDev(TbIotEDevDTO tbIotEDevDTO) throws BizException {
		ComInfoDto temp = null;
		List<TbIotEDevDTO> tbIotEDevDTORn = new ArrayList<TbIotEDevDTO>();
		EDevSrchResDTO eDevSrchResDTO = new EDevSrchResDTO();
		int rCnt = tbIotEDevDTO.getDisplayRowCount();
		int cPage = tbIotEDevDTO.getCurrentPage();
		int totCount = 0;
		tbIotEDevDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
		tbIotEDevDTO.setOrgNm(AuthUtils.getUser().getOrgNm());

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveIotEDevTCnt");
		try {
			totCount = iotEDevDAO.retrieveIotEDevTCnt(tbIotEDevDTO);
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

		eDevSrchResDTO.pageCalculate(totCount, tbIotEDevDTO.getDisplayRowCount(), tbIotEDevDTO.getCurrentPage());
		tbIotEDevDTO.setStartPage(eDevSrchResDTO.getRowStart());

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveIotEDev");
		try {
			tbIotEDevDTORn = iotEDevDAO.retrieveIotEDev(tbIotEDevDTO);
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
			eDevSrchResDTO.setDataList(tbIotEDevDTORn);
			eDevSrchResDTO.pageCalculate(totCount, rCnt, cPage);
		return eDevSrchResDTO;
	}

	@Override
	public List<TbIotEDevDTO> retrieveIotEDevAttb(TbIotEDevDTO tbIotEDevDTO) throws BizException {
		ComInfoDto temp = null;
		List<TbIotEDevDTO> list = new ArrayList<TbIotEDevDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveIotEDevAttb");
		try {
			list = iotEDevDAO.retrieveIotEDevAttb(tbIotEDevDTO);
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
		return list;
	}

	@Override
	public List<TbIotEDevDTO> retrieveIotEDevAddr(TbIotEDevDTO tbIotEDevDTO) throws BizException {
		ComInfoDto temp = null;
		List<TbIotEDevDTO> list = new ArrayList<TbIotEDevDTO>();

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveIotEDevAddr");
		try {
			list = iotEDevDAO.retrieveIotEDevAddr(tbIotEDevDTO);
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
		return list;
	}

	@Override
	public List<TbIotEDevDTO> retrieveIotEDevSend(TbIotEDevDTO tbIotEDevDTO) throws BizException {
		ComInfoDto temp = null;
		List<TbIotEDevDTO> list = new ArrayList<TbIotEDevDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveIotEDevSend");
		try {
			list = iotEDevDAO.retrieveIotEDevSend(tbIotEDevDTO);
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
		return list;
	}

	@Override
	public List<TbIotEDetHistDTO> retrieveTemplateList(TbIotEDetHistDTO tbIotEDetHistDTO) throws BizException {
		ComInfoDto temp = null;
		List<TbIotEDetHistDTO> list = new ArrayList<TbIotEDetHistDTO>();

		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String newLang = c.getXlang();
		tbIotEDetHistDTO.setLangSet(newLang);
		tbIotEDetHistDTO.setCharSet(newLang);
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveTemplateList");

		try {
			list = iotEDevDAO.retrieveTemplateList(tbIotEDetHistDTO);
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

		return list;
	}

	@Override
	public List<TbIotEDetHistDTO> retrieveIotDevCls() throws BizException {
		ComInfoDto temp = null;
		List<TbIotEDetHistDTO> list = new ArrayList<TbIotEDetHistDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveIotDevCls");

		try {
			list = iotEDevDAO.retrieveIotDevCls();
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

		return list;
	}

	@Override
	public List<TbIotEDetHistDTO> retrieveIotDetStatus() throws BizException {
		ComInfoDto temp = null;
		List<TbIotEDetHistDTO> list = new ArrayList<TbIotEDetHistDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveIotDetStatus");

		try {
			list = iotEDevDAO.retrieveIotDetStatus();
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

		return list;
	}

	@Override
	public List<TbIotEDetHistDTO> retrieveDevMdlList(TbIotEDetHistDTO tbIotEDetHistDTO) throws BizException {
		ComInfoDto temp = null;
		List<TbIotEDetHistDTO> list = new ArrayList<TbIotEDetHistDTO>();

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveDevMdlList");
		try {
			list = iotEDevDAO.retrieveDevMdlList(tbIotEDetHistDTO);
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

		return list;
	}

	@Override
	public XSSFWorkbook downloadExcelEntrDevDetList(TbIotEDetHistDTO tbIotEDetHistDTO) throws Exception {
		ComInfoDto temp = null;
		List<TbIotEDetHistDTO> headerList = new ArrayList<TbIotEDetHistDTO>();
		List<TbIotEDetHistDTO> retrieveDevCtrlList = new ArrayList<TbIotEDetHistDTO>();
		// excel
		XSSFWorkbook wb = new XSSFWorkbook();
		ObjectMapper mapper = new ObjectMapper();

		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String newLang = c.getXlang();
		tbIotEDetHistDTO.setLangSet(newLang);
		tbIotEDetHistDTO.setCharSet(newLang);
		tbIotEDetHistDTO.setOrgNm(AuthUtils.getUser().getOrgNm());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveTemplateList");

		try {
			if (AuthUtils.getUser() != null) {
				tbIotEDetHistDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
			}
			headerList = iotEDevDAO.retrieveTemplateList(tbIotEDetHistDTO);
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


		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveIotEntrDevDetList");

		try {
			retrieveDevCtrlList = iotEDevDAO.retrieveIotEntrDevDetList(tbIotEDetHistDTO);
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

		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

		Map<String, String> title = new LinkedHashMap<String, String>();
		String[] nameList = headerList.get(0).getColNameData().split(",");
		for (int i = 0; i < headerList.size(); i++) {
			TbIotEDetHistDTO header = headerList.get(i);
			String name = nameList[i];
			Map<String, String> map = mapper.readValue(header.getColModelData(), Map.class);
			title.put(name, map.get("name"));
		}
			ExcelUtils.createExcelFile(wb, retrieveDevCtrlList, title);
		return wb;
	}

	@Override
	public HashMap<String, Object> retrieveIotEntrDevDetList(TbIotEDetHistDTO tbIotEDetHistDTO) throws BizException {
		PageDTO pageDTO = new PageDTO();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String langSet = c.getXlang();
		tbIotEDetHistDTO.setLangSet(langSet);
		tbIotEDetHistDTO.setOrgNm(AuthUtils.getUser().getOrgNm());
		tbIotEDetHistDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		// 장애 이력 전체 갯수 조회
		int count = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveIotEntrDevDetCount");
		try {
			count = iotEDevDAO.retrieveIotEntrDevDetCount(tbIotEDetHistDTO);
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

		if (AuthUtils.getUser() != null) {
			tbIotEDetHistDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		}

		// 페이징
		pageDTO.pageCalculate(count, tbIotEDetHistDTO.getDisplayRowCount(), tbIotEDetHistDTO.getCurrentPage());
		tbIotEDetHistDTO.setStartPage(pageDTO.getRowStart());
		tbIotEDetHistDTO.setEndPage(pageDTO.getRowEnd());

		// 장애 이력 조회
		List<TbIotEDetHistDTO> tbIotEDetHistDTOn = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveIotEntrDevDetList");
		try {
			tbIotEDetHistDTOn = iotEDevDAO.retrieveIotEntrDevDetList(tbIotEDetHistDTO);
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

		resultMap.put("page", pageDTO);
		resultMap.put("boardList", tbIotEDetHistDTOn);

		return resultMap;
	}

	@Override
	public EDevSrchResDTO retrieveIotEDevDoor(TbIotEDevDTO tbIotEDevDTO) throws BizException {
		ComInfoDto temp = null;
		List<TbIotEDevDTO> tbIotEDevDTORn = new ArrayList<TbIotEDevDTO>();
		EDevSrchResDTO eDevSrchResDTO = new EDevSrchResDTO();
		int rCnt = tbIotEDevDTO.getDisplayRowCount();
		int cPage = tbIotEDevDTO.getCurrentPage();
		int totCount = 0;
		tbIotEDevDTO.setCharSet(ReqContextComponent.getComInfoDto().getXlang());
		tbIotEDevDTO.setOrgNm(AuthUtils.getUser().getOrgNm());

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveIotEDevTCntDoor");
		try {
			totCount = iotEDevDAO.retrieveIotEDevTCntDoor(tbIotEDevDTO);
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

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEDevDAO.retrieveIotEDevDoor");
		try {
			tbIotEDevDTORn = iotEDevDAO.retrieveIotEDevDoor(tbIotEDevDTO);
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
			eDevSrchResDTO.setDataList(tbIotEDevDTORn);
			eDevSrchResDTO.pageCalculate(totCount, rCnt, cPage);

		return eDevSrchResDTO;
	}

}
