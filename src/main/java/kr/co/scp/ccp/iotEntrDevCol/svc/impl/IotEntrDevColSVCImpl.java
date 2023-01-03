package kr.co.scp.ccp.iotEntrDevCol.svc.impl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.LinkedHashMap;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.ccp.iotEntrDevCol.dao.IotEntrDevColDAO;
import kr.co.scp.ccp.iotEntrDevCol.dto.TbIotEDevColValDTO;
import kr.co.scp.ccp.iotEntrDevCol.svc.IotEntrDevColSVC;
import kr.co.scp.common.tmpl.dao.TbIotTmplHdrJqgridDAO;
import kr.co.scp.common.tmpl.dto.TbIotJqDataResponseDTO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Primary
@Service
public class IotEntrDevColSVCImpl implements IotEntrDevColSVC {

	@Autowired
	private IotEntrDevColDAO iotEntrDevColDAO;

	@Autowired
	private TbIotTmplHdrJqgridDAO tbIotTmplHdrJqgridDao;

	@Override
	public HashMap<String, Object> retrieveEntrDevColList(TbIotEDevColValDTO tbIotEDevColValDTO) {
		ComInfoDto temp = null;
		PageDTO pageDTO = new PageDTO();

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<TbIotEDevColValDTO> entrDevColValList = new ArrayList<TbIotEDevColValDTO>();
		Integer count = 0;
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String charSet = c.getXlang();
		tbIotEDevColValDTO.setCharSet(charSet);
		tbIotEDevColValDTO.setOrgNm(AuthUtils.getUser().getOrgNm());
		try {
			tbIotEDevColValDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
			tbIotEDevColValDTO.setOrgNm(AuthUtils.getUser().getOrgNm());

			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"iotEntrDevColDAO.retrieveEntrDevColCount");
			count = iotEntrDevColDAO.retrieveEntrDevColCount(tbIotEDevColValDTO);

			pageDTO.pageCalculate(count, tbIotEDevColValDTO.getDisplayRowCount(), tbIotEDevColValDTO.getCurrentPage());
			tbIotEDevColValDTO.setStartPage(pageDTO.getRowStart());

			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"iotEntrDevColDAO.retrieveEntrDevColList");
			entrDevColValList = iotEntrDevColDAO.retrieveEntrDevColList(tbIotEDevColValDTO);

		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

		resultMap.put("page", pageDTO);
		resultMap.put("entrDevColValList", entrDevColValList);

		return resultMap;
	}

	@Override
	public XSSFWorkbook downloadEntrDevColList(TbIotEDevColValDTO tbIotEDevColValDTO) {
		ComInfoDto temp = null;
		List<TbIotEDevColValDTO> entrDevColValList = new ArrayList<TbIotEDevColValDTO>();
		List<TbIotJqDataResponseDTO> dataList = new ArrayList<TbIotJqDataResponseDTO>();
		List<String> headerList = new ArrayList<String>();
		Map<String, String> headerMap = new LinkedHashMap<String, String>();
		XSSFWorkbook wb = new XSSFWorkbook();
		String[] colNames = {};
		try {
			ComInfoDto c = ReqContextComponent.getComInfoDto();
			String charSet = c.getXlang();
			tbIotEDevColValDTO.setCharSet(charSet);
			tbIotEDevColValDTO.setOrgNm(AuthUtils.getUser().getOrgNm());

			// 동적 템플릿 가져오기
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEntrDevColDAO.retrieveEntrDevTmplHeader");
			dataList = iotEntrDevColDAO.retrieveEntrDevTmplHeader(tbIotEDevColValDTO);
//			dataList = null;
			if(dataList == null || dataList.isEmpty()) {
				throw new BizException("noTmplHeader");
			}
			// 동적 헤더명
			colNames = dataList.get(0).getColNameData().split(", ");

			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotEntrDevColDAO.retrieveDownEntrDevColVals");
			tbIotEDevColValDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
			entrDevColValList = iotEntrDevColDAO.retrieveDownEntrDevColVals(tbIotEDevColValDTO);

			for (int i = 0; i < dataList.size(); i++) {
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObj = (JSONObject) jsonParser.parse(dataList.get(i).getColModelData());

				// map에 값 담기
				headerMap.put(colNames[i], (String) jsonObj.get("name"));
			}

			ExcelUtils.createExcelFile(wb, entrDevColValList, headerMap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return wb;
	}

}
