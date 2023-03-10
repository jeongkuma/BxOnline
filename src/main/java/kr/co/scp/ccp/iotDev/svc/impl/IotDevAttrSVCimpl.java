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

        devdto.setDevClsCd("??????????????????");
        devdto.setDevClsCdNm("???????????????");
        devdto.setDevMdlCd("??????????????????");
        devdto.setDevMdlNm("???????????????");
        devdto.setDevAttbCdId("??????????????????");
        devdto.setDevAttbCdNm("???????????????");
        devdto.setInputType("??????????????????");
        devdto.setDevAttbType("????????????");
        devdto.setParamKey("???????????????????????????");
        devdto.setColNmCd("???????????????????????????");
        devdto.setConNmCd("?????????????????????");
        devdto.setStaAvgNmCd("???????????????????????????");
        devdto.setStaSumNmCd("???????????????????????????");
        devdto.setDetNmCd("?????????????????????");
        devdto.setMapYn("??????????????????Y,N");
        devdto.setUnit("??????");
        devdto.setOrderNo("??????");

		tmpList.add(devdto);
        title.put("?????? ?????? ??????","devClsCd");
        title.put("?????? ?????? ???","devClsCdNm");
        title.put("?????? ?????? ??????","devMdlCd");
        title.put("?????? ?????? ???","devMdlNm");
        title.put("?????? ?????? ??????","devAttbCdId");
        title.put("?????? ?????? ???","devAttbCdNm");
        title.put("?????? ?????? ??????","inputType");
        title.put("?????? ??????","devAttbType");
        title.put("?????? ?????? ???????????? ???","paramKey");
        title.put("?????? ?????? ????????? ??????","colNmCd");
        title.put("?????? ?????? ?????? ???","conNmCd");
        title.put("?????? ?????? ?????? ?????????","staAvgNmCd");
        title.put("?????? ?????? ?????? ?????????","staSumNmCd");
        title.put("???????????? ?????????","detNmCd");
        title.put("?????? ?????? ??????","mapYn");
        title.put("??????","unit");
        title.put(" ??????","orderNo");

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





		// ???????????? ?????????????????? ????????? ?????????????????? ????????? ????????? ??????.
		// ?????? ?????? ??? ???????????? ????????? ????????????.
		// ?????? ?????? ??? ???????????? ?????? ????????? ???????????? ???????????? ????????? ?????????.
		// -> ContentSeq ?????? 1??? ????????????.
	      ComInfoDto temp = new ComInfoDto();
		final String FIXEDCONTSEQ = "1";
		fileService.saveFiles(request, FileBoardTypeDTO.DEV, FIXEDCONTSEQ);
		// Board Type ??????
		TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
		tbIoTBrdFileDTO.setContentSeq(FIXEDCONTSEQ);
		tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.DEV);

		// ???????????? ???????????? ????????? ?????????
		List<String> excelPropList = this.makeExcelPropsList();
		// ????????? ?????? ????????? ??????
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
		// ????????? ????????? 1??? ????????? ???
		if (savedFileList.size() > 0) {

			List<TbIotDevAttrDTO> devDtoList = new ArrayList<TbIotDevAttrDTO>();

			for (Iterator iterator = savedFileList.iterator(); iterator.hasNext();) {
				// exception ????????? ?????? ??????


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
