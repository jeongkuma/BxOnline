package kr.co.scp.ccp.iotEntrDevHist.svc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import kr.co.abacus.common.util.LinkedHashMap;
import kr.co.scp.ccp.iotCtrlHist.dao.IotCtrlHistDAO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlDTO;
import kr.co.scp.ccp.iotCtrlHist.dto.TbIotDevMdlReqDTO;
import kr.co.scp.ccp.iotEntrDevHist.dao.IotEntrDevHistDAO;
import kr.co.scp.ccp.iotEntrDevHist.dto.TbIotEntrDevHistDTO;
import kr.co.scp.ccp.iotEntrDevHist.dto.TbIotEntrDevHistReqDTO;
import kr.co.scp.ccp.iotEntrDevHist.dto.TbIotEntrDevHistReqListDTO;
import kr.co.scp.ccp.iotEntrDevHist.svc.IotEntrDevHistSVC;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.scp.common.tmpl.dao.TbIotTmplHdrJqgridDAO;
import kr.co.scp.common.tmpl.dto.TbIotJqDataResponseDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;


@Primary
@Service
public class IotEntrDevHistSVCImpl implements IotEntrDevHistSVC {

	ComInfoDto temp = null;

	@Autowired
	private IotEntrDevHistDAO iotEntrDevHistDAO;

	@Autowired
	private IotCtrlHistDAO iotCtrlHistDAO;

	@Autowired
	private TbIotTmplHdrJqgridDAO tbIotTmplHdrJqgridDao;

	@Override
	public List<TbIotDevMdlDTO> retrieveDevClsList(TbIotEntrDevHistReqDTO tbIotEntrDevHistReqDTO) {

		List<TbIotDevMdlDTO> clsList = new ArrayList<TbIotDevMdlDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEntrDevHistDAO.retrieveDevClsList");
		try {
			tbIotEntrDevHistReqDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
			clsList = iotEntrDevHistDAO.retrieveDevClsList(tbIotEntrDevHistReqDTO);
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
		return clsList;
	}

	@Override
	public List<TbIotDevMdlDTO> retrieveDevMdlList(TbIotDevMdlReqDTO tbIotDevMdlReqDTO) {

		List<TbIotDevMdlDTO> list = new ArrayList<TbIotDevMdlDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCtrlHistDAO.retrieveDevMdlList");
		try {
			tbIotDevMdlReqDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
			list = iotCtrlHistDAO.retrieveDevMdlList(tbIotDevMdlReqDTO);
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
	public HashMap<String, Object> retrieveEntrDevHistList(TbIotEntrDevHistReqListDTO tbIotEntrDevHistReqListDTO) {
		PageDTO pageDTO = new PageDTO();

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		Integer count = 0;
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String langSet = c.getXlang();
		tbIotEntrDevHistReqListDTO.setLangSet(langSet);
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEntrDevHistDAO.retrieveEntrDevHistCount");
		try {
			tbIotEntrDevHistReqListDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
			count = iotEntrDevHistDAO.retrieveEntrDevHistCount(tbIotEntrDevHistReqListDTO);
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

		pageDTO.pageCalculate(count, tbIotEntrDevHistReqListDTO.getDisplayRowCount(), tbIotEntrDevHistReqListDTO.getCurrentPage());

		tbIotEntrDevHistReqListDTO.setStartPage(pageDTO.getRowStart());

		List<TbIotEntrDevHistDTO> entrDevHistDTOList = new ArrayList<TbIotEntrDevHistDTO>();

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEntrDevHistDAO.retrieveEntrDevHistList");
		try {
			tbIotEntrDevHistReqListDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
			entrDevHistDTOList = iotEntrDevHistDAO.retrieveEntrDevHistList(tbIotEntrDevHistReqListDTO);
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
		resultMap.put("entrDevHistList", entrDevHistDTOList);

		return resultMap;
	}

	@Override
	public void csvCreate(TbIotEntrDevHistReqListDTO tbIotEntrDevHistReqListDTO, HttpServletResponse response) throws BizException {

		// 헤더를 조회
		List<TbIotEntrDevHistDTO> entrDevHistDTOList = new ArrayList<TbIotEntrDevHistDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEntrDevHistDAO.retrieveEntrDevHistNotPage");
		String today  = new java.text.SimpleDateFormat("yyyy_MM_dd").format(new java.util.Date());
		String csvFileName = "entrDevHist" + "_" + today + ".csv";
		try {
			tbIotEntrDevHistReqListDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
			tbIotEntrDevHistReqListDTO.setOrgNm(AuthUtils.getUser().getOrgNm());
			entrDevHistDTOList = iotEntrDevHistDAO.retrieveEntrDevHistNotPage(tbIotEntrDevHistReqListDTO);
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

		List<TbIotJqDataResponseDTO> dataList = new ArrayList<TbIotJqDataResponseDTO>();


		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String newLang = c.getXlang();
		tbIotEntrDevHistReqListDTO.setLangSet(newLang);

		// 동적 템플릿 가져오기
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotTmplHdrJqgridDao.retrieveJqDataExcel");
		try {
			dataList = tbIotTmplHdrJqgridDao.retrieveJqDataExcel(tbIotEntrDevHistReqListDTO);
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

		// 동적 헤더명
		String colName[] = dataList.get(0).getColNameData().split(", ");

		// 엑셀 다운로드
		Map<String, String> headerMap = new LinkedHashMap<String, String>();

		for (int i = 0; i < dataList.size(); i++) {
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObj;
			try {
				jsonObj = (JSONObject) jsonParser.parse(dataList.get(i).getColModelData());
			} catch (ParseException e) {
				throw new BizException("com.fasterxml.jackson.core.JsonParseException");
			}
			// map에 값 담기
			headerMap.put((String) jsonObj.get("name"), colName[i]);
		}

//		CSVUtils.makeCSVFile(response, entrDevHistDTOList, csvFileName, headerMap);

	}
	@Override
	public XSSFWorkbook excelCreate(TbIotEntrDevHistReqListDTO tbIotEntrDevHistReqListDTO) {

		// 헤더를 조회
		List<TbIotEntrDevHistDTO> entrDevHistDTOList = new ArrayList<TbIotEntrDevHistDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotEntrDevHistDAO.retrieveEntrDevHistNotPage");
		try {
			tbIotEntrDevHistReqListDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
			entrDevHistDTOList = iotEntrDevHistDAO.retrieveEntrDevHistNotPage(tbIotEntrDevHistReqListDTO);
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

		List<TbIotJqDataResponseDTO> dataList = new ArrayList<TbIotJqDataResponseDTO>();
		XSSFWorkbook wb = new XSSFWorkbook();

		try {
			ComInfoDto c = ReqContextComponent.getComInfoDto();
			String newLang = c.getXlang();
			tbIotEntrDevHistReqListDTO.setLangSet(newLang);

			// 동적 템플릿 가져오기
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"tbIotTmplHdrJqgridDao.retrieveJqDataExcel");
			try {
				dataList = tbIotTmplHdrJqgridDao.retrieveJqDataExcel(tbIotEntrDevHistReqListDTO);
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

			// 동적 헤더명
			String colName[] = dataList.get(0).getColNameData().split(", ");

			// 엑셀 다운로드
			Map<String, String> title = new LinkedHashMap<String, String>();
			Map<String, String> title2 = new LinkedHashMap<String, String>();

			for (int i = 0; i < dataList.size(); i++) {
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObj = (JSONObject) jsonParser.parse(dataList.get(i).getColModelData());

				// map에 값 담기
				title.put(colName[i], (String) jsonObj.get("name"));
			}

			ExcelUtils.createExcelFile(wb, entrDevHistDTOList, title);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return wb;
	}

}
