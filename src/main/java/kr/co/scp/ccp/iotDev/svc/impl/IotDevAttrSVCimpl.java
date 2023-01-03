 package kr.co.scp.ccp.iotDev.svc.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.co.abacus.common.component.ReqContextComponent;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.FileUtils;
import kr.co.abacus.common.util.JsonUtils;
import kr.co.abacus.common.validation.ValidationComponent;
import kr.co.scp.ccp.common.brdFile.dao.BrdFileListDAO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileDTO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.common.brdFile.util.FileServiceUtil;
import kr.co.scp.ccp.iotDev.dao.IotDevAttrDAO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevAttrSVC;
import kr.co.auiot.common.dto.common.FileBoardTypeDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.auiot.common.util.WebCommUtil;
import lombok.extern.slf4j.Slf4j;



@Primary
@Slf4j
@Service
public class IotDevAttrSVCimpl implements IotDevAttrSVC {


	@Autowired
	private ValidationComponent validationComponent;

	static ObjectMapper oMapper = new ObjectMapper();

	@Autowired
	private BrdFileListDAO brdFileListDAO;


	private TbIotDevAttrDTO tbIotDevAttrDTO;


	@Autowired
	private IotDevAttrSVC iotDevAttrSVC;

	@Autowired
	FileServiceUtil fileService;

	@Value("${file.upload-dir}")
	private String FILE_UPLOAD_PATH;

	@Value("${file.max-size}")
	private int FILE_MAX_SIZE;
	@Autowired
	private IotDevAttrDAO iotDevAttrDAO;


	@Override
	public List<TbIotDevAttrDTO> retrieveIotDevAttr(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException {
		ComInfoDto temp = null;
		 List<TbIotDevAttrDTO> list = null;
		tbIotDevAttrDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		 try {
			 temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevAttrDAO.retrieveIotDevAttr");
			 list = iotDevAttrDAO.retrieveIotDevAttr(tbIotDevAttrDTO);
		 }   catch (MyBatisSystemException e) {
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


	public XSSFWorkbook createIotPasteDevAttrTemp() throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();
		TbIotDevAttrDTO devdto = new TbIotDevAttrDTO();
		List<TbIotDevAttrDTO> tmpList = new ArrayList<TbIotDevAttrDTO>();

        devdto.setDevClsCd("장비유형코드");
        devdto.setDevClsCdNm("장비유형명");
        devdto.setDevMdlCd("장비모델코드");
        devdto.setDevMdlNm("장비모델명");
        devdto.setDevAttbCdId("장비속성코드");
        devdto.setDevAttbCdNm("장비속성명");
        devdto.setInputType("속성입력타입");
        devdto.setDevAttbType("속성타입");
        devdto.setParamKey("속성전문파라미터키");
        devdto.setColNmCd("수집대상컬럼명코드");
        devdto.setConNmCd("제어대상컬럼명");
        devdto.setStaAvgNmCd("통계평균대상컬렴명");
        devdto.setStaSumNmCd("통계합계대상컬럼명");
        devdto.setDetNmCd("이상징후컬럼명");
        devdto.setMapYn("지도표시여부Y,N");
        devdto.setUnit("단위");
        devdto.setOrderNo("순서");

		tmpList.add(devdto);
        title.put("장비 유형 코드","devClsCd");
        title.put("장비 유형 명","devClsCdNm");
        title.put("장비 모델 코드","devMdlCd");
        title.put("장비 모델 명","devMdlNm");
        title.put("장비 속성 코드","devAttbCdId");
        title.put("장비 속성 명","devAttbCdNm");
        title.put("속성 입력 타입","inputType");
        title.put("속성 타입","devAttbType");
        title.put("속성 전문 파라미터 키","paramKey");
        title.put("수집 대상 컬럼명 코드","colNmCd");
        title.put("제어 대상 컬럼 명","conNmCd");
        title.put("통계 평균 대상 컬렴명","staAvgNmCd");
        title.put("통계 합계 대상 컬럼명","staSumNmCd");
        title.put("이상징후 컬럼명","detNmCd");
        title.put("지도 표시 여부","mapYn");
        title.put("단위","unit");
        title.put(" 순서","orderNo");

		ExcelUtils.createExcelFile(wb, tmpList, title);

		return wb;
	}
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })

	public void createIotDevAttrAll(HttpServletRequest request) throws BizException {

	       Map<String, String[]> map = request.getParameterMap();
	       Map<String, Object> mapper = new HashMap<String, Object>();
	      String json = map.get("jsonData")[0];
		  mapper = JsonUtils.fromJson(json, Map.class);
	      String devSeq = mapper.get("devSeq").toString();





		// 사용자는 일괄등록하는 하나의 서비스에서만 파일을 업로드 한다.
		// 일괄 등록 후 업로드한 파일은 삭제한다.
		// 파일 생성 후 일괄등록 중에 에러가 발생하면 업로드한 파일을 지운다.
		// -> ContentSeq 값은 1로 고정한다.
	      ComInfoDto temp = new ComInfoDto();
		final String FIXEDCONTSEQ = "1";
		fileService.saveFiles(request, FileBoardTypeDTO.DEV, FIXEDCONTSEQ);
		// Board Type 설정
		TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
		tbIoTBrdFileDTO.setContentSeq(FIXEDCONTSEQ);
		tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.DEV);

		// 엑셀에서 속성으로 뽑아낼 항목들
		List<String> excelPropList = this.makeExcelPropsList();
		// 저장된 파일 리스트 조회
		List<TbIoTBrdFileDTO> savedFileList = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "brdFileListDAO.retrieveTbIoTBrdFileList");
		try {
		savedFileList = brdFileListDAO.retrieveTbIoTBrdFileList(tbIoTBrdFileDTO);
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
		// 조회된 파일이 1개 이상일 때
		if (savedFileList.size() > 0) {

			List<TbIotDevAttrDTO> devDtoList = new ArrayList<TbIotDevAttrDTO>();

			for (Iterator iterator = savedFileList.iterator(); iterator.hasNext();) {
				// exception 구분을 위한 변수


				StringBuilder filePath = new StringBuilder();
				filePath.append(FILE_UPLOAD_PATH);
				TbIoTBrdFileDTO tmpBrdDto = (TbIoTBrdFileDTO) iterator.next();
				filePath.append(tmpBrdDto.getFilePath()).append(File.separator).append(tmpBrdDto.getFileName());


				String fileDPath = null;
				String fileDNm = null;

				fileDPath = FILE_UPLOAD_PATH + tmpBrdDto.getFilePath() + File.separator;
				fileDNm = tmpBrdDto.getFileName();

				InputStream xslInputStream = null;
				try {
					String filepathTemp = WebCommUtil.cleanString(filePath.toString());
					xslInputStream = new FileInputStream(filepathTemp);
					devDtoList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, TbIotDevAttrDTO.class);
					for (Iterator iterator2 = devDtoList.iterator(); iterator2.hasNext();) {

						TbIotDevAttrDTO tbIotDevAttrDTO1 = trimObj((TbIotDevAttrDTO) iterator2.next());
						tbIotDevAttrDTO1.setDevSeq(devSeq);
						String custId =  AuthUtils.getUser().getCustId();
						tbIotDevAttrDTO1.setRegUsrId(custId);
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevAttrDAO.insertIotDevAttrAll");
						try {

						iotDevAttrDAO.insertIotDevAttrAll(tbIotDevAttrDTO1);
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
				} catch (Exception e) {
					e.printStackTrace();
					throw new BizException("excelUploadException");
				}
//				catch (Exception e) {
//					 e.printStackTrace();
//					if (null != exceptionNm) {
//						if (exceptionNm.equals("lgnId")) {
//							throw new BizException("duplicationLgnId");
//						} else if (exceptionNm.equals("roleNm")) {
//							throw new BizException("nonExistRoleNm");
//						} else if (exceptionNm.equals("orgNm")) {
//							throw new BizException("nonExistOrgNm");
//						}
//						log.error("Error has occured during validating excelFile");
//					}else {
//						log.error("Error has occured during parsing excelFile");
////						throw new BizException(e,"excelUploadException");
//					}
//				}
				finally {
					try {
						if (xslInputStream != null) {
							xslInputStream.close();
						}
					} catch (IOException e) {
					}
					this.deleteFile();
					FileUtils.deleteFile(request, fileDPath, fileDNm);
				}

			}
		}
	}



	private List<String> makeExcelPropsList() {
		List<String> excelPropList = new ArrayList<String>();

        excelPropList.add("devClsCd");
        excelPropList.add("devClsCdNm");
        excelPropList.add("devMdlCd");
        excelPropList.add("devMdlNm");
        excelPropList.add("devAttbCdId");
        excelPropList.add("devAttbCdNm");
        excelPropList.add("inputType");
        excelPropList.add("devAttbType");
        excelPropList.add("paramKey");
        excelPropList.add("colNmCd");
        excelPropList.add("conNmCd");
        excelPropList.add("staAvgNmCd");
        excelPropList.add("staSumNmCd");
        excelPropList.add("detNmCd");
        excelPropList.add("mapYn");
        excelPropList.add("unit");
        excelPropList.add("orderNo");

		return excelPropList;
	}
	private TbIotDevAttrDTO convertCDTO(TbIotDevAttrDTO inputDto) {
		TbIotDevAttrDTO outputDto = new TbIotDevAttrDTO();


		outputDto.setDevSeq(inputDto.getDevSeq());
        outputDto.setDevClsCd(inputDto.getDevClsCd());
        outputDto.setDevClsCdNm(inputDto.getDevClsCdNm());
        outputDto.setDevMdlCd(inputDto.getDevMdlCd());
        outputDto.setDevMdlNm(inputDto.getDevMdlNm());
        outputDto.setDevAttbCdId(inputDto.getDevAttbCdId());
        outputDto.setDevAttbCdNm(inputDto.getDevAttbCdNm());
        outputDto.setInputType(inputDto.getInputType());
        outputDto.setDevAttbType(inputDto.getDevAttbType());
        outputDto.setParamKey(inputDto.getParamKey());
        outputDto.setColNmCd(inputDto.getColNmCd());
        outputDto.setConNmCd(inputDto.getConNmCd());
        outputDto.setStaAvgNmCd(inputDto.getStaAvgNmCd());
        outputDto.setStaSumNmCd(inputDto.getStaSumNmCd());
        outputDto.setDetNmCd(inputDto.getDetNmCd());
        outputDto.setMapYn(inputDto.getMapYn());
        outputDto.setUnit(inputDto.getUnit());
        outputDto.setOrderNo(inputDto.getOrderNo());
		return outputDto;
	}



	private void deleteFile() {
		final String FIXEDCONTSEQ = "1";
		TbIoTBrdFileListDTO dto = new TbIoTBrdFileListDTO();
		dto.setContentSeq(FIXEDCONTSEQ);
		dto.setContentType(FileBoardTypeDTO.DEV);

		brdFileListDAO.deleteTbIoTBrdFileList(dto);

	}



private JSONObject getJsonStringFromMap( Map<String, String> map )
{
    JSONObject jsonObject = new JSONObject();
    for( Map.Entry<String, String> entry : map.entrySet() ) {
        String key = entry.getKey();
        Object value = entry.getValue();
        jsonObject.put(key, value);
    }
	return jsonObject;

}

public TbIotDevAttrDTO trimObj(TbIotDevAttrDTO dto) {



    dto.setDevClsCd(dto.getDevClsCd());
    dto.setDevClsCdNm(dto.getDevClsCdNm());
    dto.setDevMdlCd(dto.getDevMdlCd());
    dto.setDevMdlNm(dto.getDevMdlNm());
    dto.setDevAttbCdId(dto.getDevAttbCdId());
    dto.setDevAttbCdNm(dto.getDevAttbCdNm());
    dto.setInputType(dto.getInputType());
    dto.setDevAttbType(dto.getDevAttbType());
    dto.setParamKey(dto.getParamKey());
    dto.setColNmCd(dto.getColNmCd());
    dto.setConNmCd(dto.getConNmCd());
    dto.setStaAvgNmCd(dto.getStaAvgNmCd());
    dto.setStaSumNmCd(dto.getStaSumNmCd());
    dto.setDetNmCd(dto.getDetNmCd());
    dto.setMapYn(dto.getMapYn());
    dto.setUnit(dto.getUnit());
    dto.setOrderNo(dto.getOrderNo());

	return dto;
}

@Override
public void insertIotDevAttrAll(TbIotDevAttrDTO tbIotDevAttrDTO) throws BizException {
	// TODO Auto-generated method stub

}
}
