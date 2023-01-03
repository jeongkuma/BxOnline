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

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import kr.co.scp.ccp.iotDev.dao.IotDevAttrSetDAO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevAttrSetDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevAttrSetSVC;
import kr.co.auiot.common.dto.common.FileBoardTypeDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.auiot.common.util.WebCommUtil;
import lombok.extern.slf4j.Slf4j;

@Primary
@Slf4j
@Service
public class IotDevAttrSetSVCimpl implements IotDevAttrSetSVC {

	@Autowired
	private ValidationComponent validationComponent;

	static ObjectMapper oMapper = new ObjectMapper();

	@Autowired
	private BrdFileListDAO brdFileListDAO;

	private TbIotDevAttrSetDTO tbIotDevAttrSetDTO;

	@Autowired
	private IotDevAttrSetSVC iotDevAttrSetSVC;

	@Autowired
	FileServiceUtil fileService;

	@Value("${file.upload-dir}")
	private String FILE_UPLOAD_PATH;

	@Value("${file.max-size}")
	private int FILE_MAX_SIZE;
	@Autowired
	private IotDevAttrSetDAO iotDevAttrSetDAO;

	@Override
	public List<TbIotDevAttrSetDTO> retrieveIotDevAttrSet(TbIotDevAttrSetDTO tbIotDevAttrSetDTO) throws BizException {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevAttrSetDAO.retrieveIotDevAttrSet");
		 List<TbIotDevAttrSetDTO>  result = null;
		try {

			result  = iotDevAttrSetDAO.retrieveIotDevAttrSet(tbIotDevAttrSetDTO);
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
	return result;
}


	public XSSFWorkbook createIotPasteDevAttrSetTemp() throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();
		TbIotDevAttrSetDTO devdto = new TbIotDevAttrSetDTO();
		List<TbIotDevAttrSetDTO> tmpList = new ArrayList<TbIotDevAttrSetDTO>();

		devdto.setSetParentCdId("부모코드 ID");
		devdto.setDevAttbSetCdId("장비속성SET 코드ID");
		devdto.setDevAttbSetCdNm("장비속성SET 코드명");
		devdto.setDevAttbSetVal("장비속성SET 값");

		tmpList.add(devdto);

		title.put("부모코드 ID", "setParentCdId");
		title.put("장비 속성SET 코드 ID", "devAttbSetCdId");
		title.put("장비 속성SET 코드명", "devAttbSetCdNm");
		title.put("장비 속성SET 값", "devAttbSetVal");

		ExcelUtils.createExcelFile(wb, tmpList, title);

		return wb;
	}

	@Override
	@Transactional
	@SuppressWarnings({ "unchecked", "rawtypes" })

	public void createIotDevAttrSetAll(HttpServletRequest request) throws BizException {



	       Map<String, String[]> map = request.getParameterMap();
	       Map<String, Object> mapper = new HashMap<String, Object>();
	      String json = map.get("jsonData")[0];
		  mapper = JsonUtils.fromJson(json, Map.class);
	      String devAttbSeq = mapper.get("devAttbSeq").toString();


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


			List<TbIotDevAttrSetDTO> devDtoList = new ArrayList<TbIotDevAttrSetDTO>();

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
					devDtoList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, TbIotDevAttrSetDTO.class);
					for (Iterator iterator2 = devDtoList.iterator(); iterator2.hasNext();) {

						TbIotDevAttrSetDTO tbIotDevAttrSetDTO = trimObj((TbIotDevAttrSetDTO) iterator2.next());
						tbIotDevAttrSetDTO.setDevAttbSeq(devAttbSeq);
						String custId =  AuthUtils.getUser().getCustId();
						tbIotDevAttrSetDTO.setRegUsrId(custId);
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevAttrSetDAO.insertIotDevAttrSetAll");
						try {

							int rs1 = 0;
							int rs2 = 0;


						    rs1  = iotDevAttrSetDAO.retrieveIotDevSetAllCdId(tbIotDevAttrSetDTO);

						    rs2 =  iotDevAttrSetDAO.retrieveIotDevSetAllCdNm(tbIotDevAttrSetDTO);



                     		if (rs1 == 0) {
								throw new BizException("uploadException");
							}

                      		if (rs2 == 0) {
                     			throw new BizException("uploadException");
                							}



						iotDevAttrSetDAO.insertIotDevAttrSetAll(trimObj(tbIotDevAttrSetDTO));
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

	private TbIotDevAttrSetDTO convertCDTO(TbIotDevAttrSetDTO inputDto) {
		TbIotDevAttrSetDTO outputDto = new TbIotDevAttrSetDTO();
        outputDto.setDevAttbSeq(inputDto.getDevAttbSeq());
		outputDto.setSetParentCdId(inputDto.getSetParentCdId());
		outputDto.setDevAttbSetCdId(inputDto.getDevAttbSetCdId());
		outputDto.setDevAttbSetCdNm(inputDto.getDevAttbSetCdNm());
		outputDto.setDevAttbSetVal(inputDto.getDevAttbSetVal());




		return outputDto;
	}

	private List<String> makeExcelPropsList() {
		List<String> excelPropList = new ArrayList<String>();
		excelPropList.add("devAttbSeq");
		excelPropList.add("setParentCdId");
		excelPropList.add("devAttbSetCdId");
		excelPropList.add("devAttbSetCdNm");
		excelPropList.add("devAttbSetVal");


		return excelPropList;
	}

	private void deleteFile() {
		final String FIXEDCONTSEQ = "1";
		TbIoTBrdFileListDTO dto = new TbIoTBrdFileListDTO();
		dto.setContentSeq(FIXEDCONTSEQ);
		dto.setContentType(FileBoardTypeDTO.DEV);
		brdFileListDAO.deleteTbIoTBrdFileList(dto);
	}

	private TbIotDevAttrSetDTO trimObj(TbIotDevAttrSetDTO dto) {
		dto.setDevAttbSeq(dto.getDevAttbSeq());
		dto.setSetParentCdId(dto.getSetParentCdId());
		dto.setDevAttbSetCdId(dto.getDevAttbSetCdId());
		dto.setDevAttbSetCdNm(dto.getDevAttbSetCdNm());
		dto.setDevAttbSetVal(dto.getDevAttbSetVal());


		return dto;
	}


	@Override
	public void insertIotDevAttrSetAll(TbIotDevAttrSetDTO tbIotDevAttrSetDTO) throws BizException {
		// TODO Auto-generated method stub

	}


	@Override
	public Integer retrieveIotDevSetAllCdId(TbIotDevAttrSetDTO tbIotDevAttrSetDTO) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer retrieveIotDevSetAllCdNm(TbIotDevAttrSetDTO tbIotDevAttrSetDTO) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Integer retrieveIotDevPSetAllCdNm(TbIotDevAttrSetDTO tbIotDevAttrSetDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
