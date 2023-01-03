package kr.co.scp.ccp.iotDev.svc.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotDev.dao.IotDevExcelDAO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevExcelDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevExcelSVC;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;


@Primary
@Service
public class IotDevExcelSVCImpl implements IotDevExcelSVC {

	@Autowired
	IotDevExcelDAO iotDevExcelDAO;

	@Override
	public XSSFWorkbook createIotPasteDev(TbIotDevExcelDTO tbIotDevExcelDTO) {
		List<TbIotDevExcelDTO> retrieveExcelList = iotDevExcelDAO.createIotPasteDev(tbIotDevExcelDTO);
		XSSFWorkbook wb = new XSSFWorkbook();
		Map<String, String> title = new LinkedHashMap<String, String>();

		title.put("고객번호", "custSeq");
		title.put("서비스", "devSvcCdNm");
		title.put("장비유형", "devClsCdNm");
		title.put("모델ID", "devMdlCd");
		title.put("모델명", "devMdlNm");
		title.put("사용 유형", "useYn");
		title.put("제조사", "vendorNm");

		ExcelUtils.createExcelFile(wb, retrieveExcelList, title);
		return wb;
	}


	@Override
	public XSSFWorkbook createIotPasteDevAtb(TbIotDevExcelDTO tbIotDevExcelDTO) {
		List<TbIotDevExcelDTO> retrieveExcelList = iotDevExcelDAO.createIotPasteDevAtb(tbIotDevExcelDTO);
		XSSFWorkbook wb = new XSSFWorkbook();
		Map<String, String> title = new LinkedHashMap<String, String>();

		title.put("고객번호", "custSeq");
		title.put("서비스", "devSvcCdNm");
		title.put("장비유형", "devClsCdNm");
		title.put("속성코드", "devAttbCdId");
		title.put("속성명", "devAttbCdNm");
		title.put("최소값", "minVal");
		title.put("최대값", "maxVal");
		title.put("수집대상 컬럼명", "colNmCd");
		title.put("제어대상 컬럼명", "conNmCd");
		title.put("통계대상 컬럼명", "staNmCd");
		title.put("이상징후대상 컬럼명", "detNmCd");
		title.put("지도 여부", "mapYn");
		ExcelUtils.createExcelFile(wb, retrieveExcelList, title);
		return wb;
	}

	@Override
	public XSSFWorkbook createIotPasteDevAtbSet(TbIotDevExcelDTO tbIotDevExcelDTO) {
		List<TbIotDevExcelDTO> retrieveExcelList = iotDevExcelDAO.createIotPasteDevAtbSet(tbIotDevExcelDTO);
		XSSFWorkbook wb = new XSSFWorkbook();
		Map<String, String> title = new LinkedHashMap<String, String>();

		title.put("고객번호", "custSeq");
		title.put("서비스", "devSvcCdNm");
		title.put("속성코드", "devAttbCdId");
		title.put("속성명", "devAttbCdNm");
		title.put("속성 Set코드", "devAttbSetCdId");
		title.put("속성 Set명", "devAttbSetCdNm");

		ExcelUtils.createExcelFile(wb, retrieveExcelList, title);
		return wb;
	}

	@Override
	public XSSFWorkbook createIotPasteDevDetSet(TbIotDevExcelDTO tbIotDevExcelDTO) {
		List<TbIotDevExcelDTO> retrieveExcelList = iotDevExcelDAO.createIotPasteDevDetSet(tbIotDevExcelDTO);
		XSSFWorkbook wb = new XSSFWorkbook();
		Map<String, String> title = new LinkedHashMap<String, String>();

		title.put("고객번호", "custSeq");
		title.put("서비스", "devSvcCdNm");
		title.put("속성코드", "devAttbCdId");
		title.put("속성명", "devAttbCdNm");
		title.put("이상징후 Set코드", "devDetSetCdId");
		title.put("이상징후 Set명", "devDetSetCdNm");
		title.put("조건", "devSetCond");
		title.put("아이콘", "iconUrl");

		ExcelUtils.createExcelFile(wb, retrieveExcelList, title);
		return wb;
	}

	@Override
	public int retrieveCustSeq(TbIotDevExcelDTO tbIotDevExcelDTO) {
		return iotDevExcelDAO.retrieveCustSeq(tbIotDevExcelDTO);
	}



@Override
public XSSFWorkbook createIotPasteDevAttr(TbIotDevExcelDTO tbIotDevExcelDTO) {
	List<TbIotDevExcelDTO> retrieveExcelList = null;
	XSSFWorkbook wb = new XSSFWorkbook();
	ComInfoDto temp = new ComInfoDto();
	Map<String, String> title = new LinkedHashMap<String, String>();

	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevExcelDAO.createIotPasteDevAttr");
	try {
		retrieveExcelList = iotDevExcelDAO.createIotPasteDevAttr(tbIotDevExcelDTO);
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

	title.put("장비유형", "devClsCdNm");
	title.put("모델명", "devMdlNm");
	title.put("사용 유형", "stateCd");
	title.put("제조사", "vendorNm");


	ExcelUtils.createExcelFile(wb, retrieveExcelList, title);
	return wb;

}



	public XSSFWorkbook createIotPasteDevTemp() throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();
		TbIotDevExcelDTO devdto = new TbIotDevExcelDTO();
		List<TbIotDevExcelDTO> tmpList = new ArrayList<TbIotDevExcelDTO>();

           devdto.setDevClsCd("장비 유형 코드");
           devdto.setDevClsCdNm("장비 유형명");
           devdto.setDevMdlCd("모델 코드");
           devdto.setDevMdlNm("모델명");
           devdto.setUseYn("사용 : Y, 미사용 : N");
           devdto.setStatusCd("A: 정상\r\n" + 	"S: 정지\r\n" +"C: 해지\r\n" + "R: 예약");
           devdto.setParentDevSeq("부모장비 없을 경우 기입 X");
           devdto.setVendorNm("제조사명");
           devdto.setNormalIconFile("정상 아이콘 파일 경로");
           devdto.setAbnormalIconFile("비정상 아이콘 파일 경로1");
           devdto.setAbnormalIconFile2("비정상 아이콘 파일 경로2");

		tmpList.add(devdto);

		title.put("장비 유형 코드","devClsCd");
		title.put("장비 유형명","devClsCdNm");
		title.put("장비 모델 코드","devMdlCd");
		title.put("장비 모델 명","devMdlNm");
		title.put("사용 여부","useYn");
		title.put("상태코드","statusCd");
		title.put("부모장비순번","parentDevSeq");
		title.put("장비 제조사 명","vendorNm");
		title.put("정상 장비 아이콘 파일","normalIconFile");
		title.put("비정상 장비 아이콘 파일","abnormalIconFile");
		title.put("비정상 장비 아이콘 파일2","abnormalIconFile2");

		ExcelUtils.createExcelFile(wb, tmpList, title);

		return wb;
	}

}
