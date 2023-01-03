 package kr.co.scp.ccp.iotDev.svc.impl;

 import com.fasterxml.jackson.databind.ObjectMapper;
 import kr.co.abacus.common.dto.common.ComInfoDto;
 import kr.co.abacus.common.exception.BizException;
 import kr.co.abacus.common.util.FileUtils;
 import kr.co.abacus.common.util.JsonUtils;
 import kr.co.abacus.common.validation.ValidationComponent;
 import kr.co.auiot.common.dto.common.FileBoardTypeDTO;
 import kr.co.auiot.common.util.AuthUtils;
 import kr.co.auiot.common.util.ExcelUtils;
 import kr.co.auiot.common.util.OmsUtils;
 import kr.co.auiot.common.util.WebCommUtil;
 import kr.co.scp.ccp.common.brdFile.dao.BrdFileListDAO;
 import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileDTO;
 import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
 import kr.co.scp.ccp.common.brdFile.util.FileServiceUtil;
 import kr.co.scp.ccp.iotDev.dao.IotDevDetSetDAO;
 import kr.co.scp.ccp.iotDev.dto.TbIotDevDetSetDTO;
 import kr.co.scp.ccp.iotDev.svc.IotDevDetSetSVC;
 import lombok.extern.slf4j.Slf4j;
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

 import javax.servlet.http.HttpServletRequest;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.util.*;

@Primary
@Slf4j
@Service
public class IotDevDetSetSVCimpl implements IotDevDetSetSVC {

	@Autowired
	private ValidationComponent validationComponent;

	static ObjectMapper oMapper = new ObjectMapper();

	@Autowired
	private BrdFileListDAO brdFileListDAO;


	private TbIotDevDetSetDTO tbIotDevDetSetDTO;


	@Autowired
	private IotDevDetSetSVC iotDevDetSetSVC;

	@Autowired
	FileServiceUtil fileService;

	@Value("${file.upload-dir}")
	private String FILE_UPLOAD_PATH;

	@Value("${file.max-size}")
	private int FILE_MAX_SIZE;
	@Autowired
	private IotDevDetSetDAO iotDevDetSetDAO;




	@Override
	public List<TbIotDevDetSetDTO> retrieveIotDevDetSet(TbIotDevDetSetDTO tbIotDevDetSetDTO) throws BizException {
		ComInfoDto temp = null;
		List<TbIotDevDetSetDTO> list  = null;
		try {

			temp =	  OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"iotDevDetSetDAO.retrieveIotDevDetSet");
			list =  iotDevDetSetDAO.retrieveIotDevDetSet(tbIotDevDetSetDTO);
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


	public XSSFWorkbook createIotPasteDevDetSetTemp() throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();
		TbIotDevDetSetDTO devdto = new TbIotDevDetSetDTO();
		List<TbIotDevDetSetDTO> tmpList = new ArrayList<TbIotDevDetSetDTO>();

        devdto.setDevAttbSeq("장비 속성 순번");
        devdto.setDevDetSetCdId("이상징후 SET 코드 ID");
        devdto.setDevDetSetCdNm("이상징후 SET 코드명");
        devdto.setDetSetCond("이상징후 SET 조건");
        devdto.setIconUrl("이상징후 아이콘 URL");
        devdto.setDetSetDesc("이상징후 SET 설명");


		tmpList.add(devdto);

        title.put("장비 속성 순번","devAttbSeq");
        title.put("이상징후 SET 코드 ID","devDetSetCdId");
        title.put("이상징후 SET 코드명","devDetSetCdNm");
        title.put("이상징후 SET 조건","detSetCond");
        title.put("이상징후 아이콘 URL","iconUrl");
        title.put("이상징후 SET 설명","detSetDesc");
		ExcelUtils.createExcelFile(wb, tmpList, title);

		return wb;
	}

	@Override
	@Transactional
	@SuppressWarnings({ "unchecked", "rawtypes" })

	public void createIotDevDetSetAll(HttpServletRequest request) throws BizException {

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

			List<TbIotDevDetSetDTO> devDtoList = new ArrayList<TbIotDevDetSetDTO>();

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
					devDtoList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, TbIotDevDetSetDTO.class);
					for (Iterator iterator2 = devDtoList.iterator(); iterator2.hasNext();) {

						TbIotDevDetSetDTO tbIotDevDetSetDTO = trimObj((TbIotDevDetSetDTO) iterator2.next());
						tbIotDevDetSetDTO.setDevAttbSeq(devAttbSeq);
						String custId =  AuthUtils.getUser().getCustId();
						tbIotDevDetSetDTO.setRegUsrId(custId);
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotDevDetSetDAO.insertIotDevDetSetAll");
						try {
						iotDevDetSetDAO.insertIotDevDetSetAll(trimObj(tbIotDevDetSetDTO));
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


	private TbIotDevDetSetDTO convertCDTO(TbIotDevDetSetDTO inputDto) {
		TbIotDevDetSetDTO outputDto = new TbIotDevDetSetDTO();

		 outputDto.setDevAttbSeq(inputDto.getDevAttbSeq());
         outputDto.setDevDetSetCdId(inputDto.getDevDetSetCdId());
         outputDto.setDevDetSetCdNm(inputDto.getDevDetSetCdNm());
         outputDto.setDetSetCond(inputDto.getDetSetCond());
         outputDto.setIconUrl(inputDto.getIconUrl());
         outputDto.setDetSetDesc(inputDto.getDetSetDesc());


		return outputDto;
	}

	private List<String> makeExcelPropsList() {
		List<String> excelPropList = new ArrayList<String>();
        excelPropList.add("devAttbSeq");
        excelPropList.add("devDetSetCdId");
        excelPropList.add("devDetSetCdNm");
        excelPropList.add("detSetCond");
        excelPropList.add("iconUrl");
        excelPropList.add("detSetDesc");
		return excelPropList;
	}

	private void deleteFile() {
		final String FIXEDCONTSEQ = "1";
		TbIoTBrdFileListDTO dto = new TbIoTBrdFileListDTO();
		dto.setContentSeq(FIXEDCONTSEQ);
		dto.setContentType(FileBoardTypeDTO.DEV);
		brdFileListDAO.deleteTbIoTBrdFileList(dto);
	}

	private TbIotDevDetSetDTO trimObj(TbIotDevDetSetDTO dto) {

        dto.setDevAttbSeq(dto.getDevAttbSeq());
        dto.setDevDetSetCdId(dto.getDevDetSetCdId());
        dto.setDevDetSetCdNm(dto.getDevDetSetCdNm());
        dto.setDetSetCond(dto.getDetSetCond());
        dto.setIconUrl(dto.getIconUrl());
        dto.setDetSetDesc(dto.getDetSetDesc());

		return dto;
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


@Override
public void insertIotDevDetSetAll(TbIotDevDetSetDTO tbIotDevDetSetDTO) throws BizException {
	// TODO Auto-generated method stub

}

}
