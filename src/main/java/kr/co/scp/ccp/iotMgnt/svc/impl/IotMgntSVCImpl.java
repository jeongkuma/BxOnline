package kr.co.scp.ccp.iotMgnt.svc.impl;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.FileUtils;
import kr.co.abacus.common.util.JsonUtils;
import kr.co.scp.ccp.common.brdFile.dao.BrdFileListDAO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileDTO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.common.brdFile.util.FileServiceUtil;
import kr.co.scp.ccp.iotMgnt.dao.IotMgntDAO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntApiRuleDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntColRuleDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntRoleMapDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevAtbDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevAtbSetDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevDetSetDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevMDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSvcDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntValRuleDTO;
import kr.co.scp.ccp.iotMgnt.svc.IotMgntSVC;
import kr.co.scp.ccp.iotRoleMap.dto.TbIotRoleMapDTO;
import kr.co.auiot.common.dto.common.FileBoardTypeDTO;
import kr.co.scp.common.iotSvc.dto.TbIotSvcDto;
import kr.co.scp.common.rule.dto.TbIotDevApiRuleParamDTO;
import kr.co.scp.common.rule.dto.TbIotDevColRuleDTO;
import kr.co.scp.common.rule.dto.TbIotValRuleDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IotMgntSVCImpl implements IotMgntSVC {

	@Autowired
	IotMgntDAO tbIotMgntDAO;

	@Autowired
	private BrdFileListDAO brdFileListDAO;

	@Autowired
	FileServiceUtil fileService;

	@Value("${file.upload-dir}")
	private String FILE_UPLOAD_PATH;

	@Value("${file.max-size}")
	private int FILE_MAX_SIZE;
	
	
	@Override
	public HashMap<String, Object> retriveService(IotMgntDTO iotMgntDTO) throws BizException {
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		
			// ??????
			List<TbIotSvcDto> retriveServiceList = new ArrayList<TbIotSvcDto>();
			ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"tbIotMgntDAO.retriveService");
			try {
				retriveServiceList = tbIotMgntDAO.retriveService(iotMgntDTO);
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

			returnMap.put("dataList", retriveServiceList);
		
		return returnMap;
	}
	
	
	public XSSFWorkbook retriveServiceList(IotMgntDTO iotMgntDTO) throws BizException {

		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();
		List<IotMgntSvcDTO> svcDtoList = retriveServiceListQuery(iotMgntDTO);


		title.put("????????? ??????", "svcSeq");
		title.put("????????? ??????", "svcCd");
		title.put("?????? ?????? ??????", "devClsCd");
		title.put("????????? ??????", "svcLevel");
		title.put("????????? ?????? ??????", "svcOrder");
		title.put("?????? ????????? ??????", "upSvcSeq");
		ExcelUtils.createExcelFile(wb, svcDtoList, title);

		return wb;
	}

	@Override
	public XSSFWorkbook retriveServiceAuthList(IotMgntDTO iotMgntDTO) throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();
		List<TbIotRoleMapDTO> svcRoleList = retriveServiceAuthListQuery(iotMgntDTO);


		title.put("?????? ??????", "roleSeq");
		title.put("?????? ??????ID", "roleCdId");
		title.put("???????????????", "svcCd");
		title.put("?????? ???????????? ??????", "menuProgSeq");
		title.put("?????? ???????????? ??????", "menuProgGubun");
		ExcelUtils.createExcelFile(wb, svcRoleList, title);

		return wb;
	}

	@Override
	public XSSFWorkbook retriveDevList(IotMgntDTO iotMgntDTO) throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();
		List<IotMgntSDevMDTO> list = retriveDevListQuery(iotMgntDTO);

		title.put("?????????????????????", "svcDevSeq");
		title.put("???????????????", "svcCd");
		title.put("??????????????????", "devClsCd");
		title.put("??????????????????", "devClsCdNm");
		title.put("??????????????????", "devMdlCd");
		title.put("??????????????????", "devMdlNm");
		title.put("????????????", "statusCd");
		title.put("??????????????????", "parentDevSeq");
		title.put("???????????????", "vendorNm");
		title.put("?????????????????????", "normalIconFile");
		title.put("????????????????????????1", "abnormalIconFile");
		title.put("????????????????????????2", "abnormalIconFile2");
		title.put("???????????????", "devMdlVal");
		title.put("????????????", "devSeq");

		ExcelUtils.createExcelFile(wb, list, title);

		return wb;
	}

	@Override
	public XSSFWorkbook retriveDevAtbList(IotMgntDTO iotMgntDTO) throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();

		List<IotMgntSDevAtbDTO> list = retriveDevAtbListQuery(iotMgntDTO);


		title.put("?????????????????????" ,"svcDevSeq");
		title.put("???????????????????????????" ,"svcDevAttbSeq");
		title.put("???????????????","svcCd");
		title.put("??????????????????", "devClsCd");
//		title.put("???????????????", "devClsCdNm");
		title.put("??????????????????", "devMdlCd");
//		title.put("???????????????", "devMdlNm");
		title.put("??????????????????ID", "devAttbCdId");
//		title.put("?????????????????????", "devAttbCdNm");
		title.put("??????????????????", "inputType");
		title.put("????????????", "statusCd");
		title.put("????????????", "devAttbType");
		title.put("???????????????????????????", "paramKey");
		title.put("???????????????????????????", "colNmCd");
		title.put("?????????????????????", "conNmCd");
		title.put("??????AVG???????????????", "staAvgNmCd");
		title.put("??????SUM???????????????", "staSumNmCd");
		title.put("?????????????????????", "detNmCd");
		title.put("??????????????????", "mapYn");
		title.put("??????", "unit");
		title.put("??????", "orderNo");
		title.put("????????????", "devSeq");

		ExcelUtils.createExcelFile(wb, list, title);

		return wb;
	}

	@Override
	public XSSFWorkbook retriveDevAtbSetList(IotMgntDTO iotMgntDTO) throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();
		List<IotMgntSDevAtbSetDTO> list = retriveDevAtbSetListQuery(iotMgntDTO);


		title.put("?????????????????????SET??????", "svcDevAttbSetSeq");
		title.put("???????????????????????????", "svcDevAttbSeq");
		title.put("???????????????", "svcCd");
//		title.put("????????????SET??????", "devAttbSetSeq");
//		title.put("??????????????????", "devAttbSeq");
//		title.put("??????????????????ID", "devAttbCdId");
		title.put("????????????SET??????ID", "devAttbSetCdId");
//		title.put("????????????SET?????????", "devAttbSetCdNm");
		title.put("????????????", "statusCd");
		title.put("????????????SET???", "devAttbSetVal");
		title.put("????????????SET??????", "setParentCdId");
		ExcelUtils.createExcelFile(wb, list, title);

		return wb;
	}

	@Override
	public XSSFWorkbook retriveDevDetSetList(IotMgntDTO iotMgntDTO) throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();
		List<IotMgntSDevDetSetDTO> list = retriveDevDetSetListQuery(iotMgntDTO);



		title.put("??????????????????SET??????", "svcDevDetSetSeq");
		title.put("??????????????????", "svcDevAttbSeq");
		title.put("???????????????", "svcCd");
		title.put("????????????SET??????ID", "devDetSetCdId");
//		title.put("????????????SET?????????", "devDetSetCdNm");
		title.put("????????????SET??????", "detSetCond");
		title.put("????????????", "statusCd");
		title.put("????????????SET?????????URL", "iconUrl");
		title.put("????????????????????????", "detSetDesc");
		ExcelUtils.createExcelFile(wb, list, title);

		return wb;
	}

	@Override
	public XSSFWorkbook retriveDevRuleParseList(IotMgntDTO iotMgntDTO) throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();
		List<TbIotDevColRuleDTO> list = retriveDevRuleParseListQuery(iotMgntDTO);



		title.put("API??????", "apiSeq");
		title.put("????????????", "devSeq");
		title.put("??????????????????", "ruleMsgType");
		title.put("???????????????", "ruleColType");
		title.put("?????????????????????", "ruleDevType");
//		title.put("???????????????", "devruleSeq");
		title.put("?????????", "ruleSeq");
		title.put("????????????", "ruleRequest");
		title.put("????????????", "ruleSourcekey");
		title.put("?????????", "ruleKind");
		title.put("Byte??????", "ruleBytePosi");
		title.put("Bit??????", "ruleBitPosi");
		title.put("??????/??????", "ruleNumber");
		title.put("?????????", "rulePoint");
		title.put("????????????", "ruleTargetkey");

		ExcelUtils.createExcelFile(wb, list, title);

		return wb;
	}

	@Override
	public XSSFWorkbook retriveDevRuleValList(IotMgntDTO iotMgntDTO) throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();
		List<TbIotValRuleDTO> list = retriveDevRuleValListQuery(iotMgntDTO);


		title.put("Api??????", "apiSeq");
		title.put("????????????", "devSeq");
		title.put("?????????", "valRuleSeq");
		title.put("??????", "valRuleName");
		title.put("?????????", "ruleKind");
		title.put("????????????", "defaultYn");
		title.put("?????????", "nullYn");
		title.put("????????????", "emptyYn");
		title.put("??????", "dataSize");
		title.put("min", "dataMin");
		title.put("max", "dataMax");
		title.put("?????????", "allowRegex");
		title.put("??????????????????", "notallowRegex");
		title.put("???????????????", "dataType");

		ExcelUtils.createExcelFile(wb, list, title);

		return wb;
	}

	@Override
	public XSSFWorkbook retriveDevRuleMapList(IotMgntDTO iotMgntDTO) throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();
		List<TbIotDevColRuleDTO> list = retriveDevRuleMapListQuery(iotMgntDTO);


		title.put("API??????", "apiSeq");
		title.put("????????????", "devSeq");
		title.put("??????????????????", "ruleMsgType");
		title.put("???????????????", "ruleColType");
		title.put("?????????????????????", "ruleDevType");
//		title.put("???????????????", "devruleSeq");
		title.put("?????????", "ruleSeq");
		title.put("????????????", "ruleRequest");
		title.put("????????????", "ruleSourcekey");
		title.put("?????????", "ruleKind");
		title.put("Byte??????", "ruleBytePosi");
		title.put("Bit??????", "ruleBitPosi");
		title.put("??????/??????", "ruleNumber");
		title.put("?????????", "rulePoint");
		title.put("??? ?????????", "ruleTargetkey");
		ExcelUtils.createExcelFile(wb, list, title);

		return wb;
	}

	@Override
	public XSSFWorkbook retriveDevRuleServiceList(IotMgntDTO iotMgntDTO) throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();

		List<TbIotDevApiRuleParamDTO> list = retriveDevRuleServiceListQuery(iotMgntDTO);


		title.put("API??????", "apiSeq");
		title.put("????????????", "devSeq");
		title.put("??????API??????", "callApiSeq");
		ExcelUtils.createExcelFile(wb, list, title);

		return wb;
	}

	@Override
	public XSSFWorkbook retriveDevRuleApiList(IotMgntDTO iotMgntDTO) throws BizException {
		XSSFWorkbook wb = new XSSFWorkbook();

		Map<String, String> title = new LinkedHashMap<String, String>();
		List<TbIotValRuleDTO> list = retriveDevRuleApiListQuery(iotMgntDTO);

		title.put("Api??????", "apiSeq");
		title.put("????????????", "devSeq");
		title.put("??? ??????", "valRuleSeq");
		title.put("??????", "valRuleName");
		title.put("??? ??????", "ruleKind");
		title.put("????????????", "defaultYn");
		title.put("?????????", "nullYn");
		title.put("????????????", "emptyYn");
		title.put("??????", "dataSize");
		title.put("MIN", "dataMin");
		title.put("MAX", "dataMax");
		title.put("?????????", "allowRegex");
		title.put("??????????????????", "notallowRegex");
		title.put("???????????????", "datatp");
		ExcelUtils.createExcelFile(wb, list, title);

		return wb;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void svcUploadSvc(HttpServletRequest request) throws BizException {
		ComInfoDto temp = new ComInfoDto();

		// ???????????? ?????????????????? ????????? ?????????????????? ????????? ????????? ??????.
		// ?????? ?????? ??? ???????????? ????????? ????????????.
		// ?????? ?????? ??? ???????????? ?????? ????????? ???????????? ???????????? ????????? ?????????.
		// -> ContentSeq ?????? 1??? ????????????.
		final String FIXEDCONTSEQ = "1";
		fileService.saveFiles(request, FileBoardTypeDTO.SVC, FIXEDCONTSEQ);
		// Board Type ??????
		TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
		tbIoTBrdFileDTO.setContentSeq(FIXEDCONTSEQ);
		tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.SVC);
		// ???????????? ???????????? ????????? ?????????
		List<String> excelPropList = null;
		excelPropList = this.makeExcelPropsList_Service();

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
			List<TbIotSvcDto> svcDtoList = new ArrayList<TbIotSvcDto>();
			for (Iterator<TbIoTBrdFileDTO> iterator = savedFileList.iterator(); iterator.hasNext();) {
				// exception ????????? ?????? ??????

					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.deleleteServiceMAll");
					try {
						tbIotMgntDAO.deleleteServiceMAll();
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

				StringBuilder filePath = new StringBuilder();
				filePath.append(FILE_UPLOAD_PATH);
				TbIoTBrdFileDTO tmpBrdDto = (TbIoTBrdFileDTO) iterator.next();
				filePath.append(tmpBrdDto.getFilePath()).append(File.separator).append(tmpBrdDto.getFileName());
				InputStream xslInputStream = null;
				try {
					xslInputStream = new FileInputStream(filePath.toString());
					svcDtoList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, TbIotSvcDto.class);
					for (Iterator<TbIotSvcDto> iterator2 = svcDtoList.iterator(); iterator2.hasNext();) {
						TbIotSvcDto tbIotSvcDto = trimObj_Service((TbIotSvcDto) iterator2.next());
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.insertServiceM");
						try {
							tbIotMgntDAO.insertServiceM(tbIotSvcDto);
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
				finally {
					try {
						if (xslInputStream != null) {
							xslInputStream.close();
						}
					} catch (IOException e) {
					}
					this.deleteFile(request,tmpBrdDto.getFilePath(),tmpBrdDto.getFileName());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void svcRoleMapUploadSvc(HttpServletRequest request) throws BizException {

		ComInfoDto temp = new ComInfoDto();

		final String FIXEDCONTSEQ = "1";
		fileService.saveFiles(request, FileBoardTypeDTO.SVC, FIXEDCONTSEQ);
		// Board Type ??????
		TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
		tbIoTBrdFileDTO.setContentSeq(FIXEDCONTSEQ);
		tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.SVC);
		// ???????????? ???????????? ????????? ?????????
		List<String> excelPropList = null;
		excelPropList = this.makeExcelPropsList_RoleMap();

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
				List<IotMgntRoleMapDTO> roleMapDtoList = new ArrayList<IotMgntRoleMapDTO>();
				for (Iterator<TbIoTBrdFileDTO> iterator = savedFileList.iterator(); iterator.hasNext();) {
				// exception ????????? ?????? ??????

				StringBuilder filePath = new StringBuilder();
				filePath.append(FILE_UPLOAD_PATH);
				TbIoTBrdFileDTO tmpBrdDto = (TbIoTBrdFileDTO) iterator.next();
				filePath.append(tmpBrdDto.getFilePath()).append(File.separator).append(tmpBrdDto.getFileName());
				InputStream xslInputStream = null;
				try {
					xslInputStream = new FileInputStream(filePath.toString());
					roleMapDtoList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, IotMgntRoleMapDTO.class);

					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.deleleteRoleMapAll");
					try {
						tbIotMgntDAO.deleleteRoleMapAll();
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

					for (Iterator<IotMgntRoleMapDTO> iterator2 = roleMapDtoList.iterator(); iterator2.hasNext();) {
						IotMgntRoleMapDTO roleMapDto = trimObj_RoleMap((IotMgntRoleMapDTO) iterator2.next());
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.insertRoleMap");
						try {
							tbIotMgntDAO.insertRoleMap(roleMapDto);
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
				finally {
					try {
						if (xslInputStream != null) {
							xslInputStream.close();
						}
					} catch (IOException e) {
					}
					this.deleteFile(request,tmpBrdDto.getFilePath(),tmpBrdDto.getFileName());
				}
			}
		}
	}

	private List<String> makeExcelPropsList_RoleMap() {
		List<String> excelPropList = new ArrayList<String>();
        excelPropList.add("roleSeq");
        excelPropList.add("roleCdId");
        excelPropList.add("svcCd");
        excelPropList.add("menuProgSeq");
        excelPropList.add("menuProgGubun");
		return excelPropList;
	}

	private List<String> makeExcelPropsList_Service() {
		List<String> excelPropList = new ArrayList<String>();

        excelPropList.add("svcSeq");
        excelPropList.add("svcCd");
        excelPropList.add("devClsCd");
        excelPropList.add("svcLvl");
        excelPropList.add("svcOrder");
        excelPropList.add("upSvcSeq");

		return excelPropList;
	}

	private IotMgntRoleMapDTO trimObj_RoleMap(IotMgntRoleMapDTO dto) {
        String regUsrId = AuthUtils.getUser().getUserId();
		dto.setRoleSeq(dto.getRoleSeq());
		dto.setRoleCdId(dto.getRoleCdId());
		dto.setSvcCd(dto.getSvcCd());
		dto.setMenuProgSeq(dto.getMenuProgSeq());
		dto.setMenuProgGubun(dto.getMenuProgGubun());
		dto.setRegUsrId(regUsrId);
		return dto;
	}

	public TbIotSvcDto trimObj_Service(TbIotSvcDto dto) {
		String regUsrId = AuthUtils.getUser().getUserId();
		dto.setSvcSeq(dto.getSvcSeq());
	    dto.setSvcCd(dto.getSvcCd());
	    dto.setDevClsCd(dto.getDevClsCd());
	    dto.setSvcLvl(dto.getSvcLvl());
	    dto.setSvcOrder(dto.getSvcOrder());
	    dto.setUpSvcSeq(dto.getUpSvcSeq());
	    dto.setRegUsrId(regUsrId);
		return dto;
	}

	private void deleteFile(HttpServletRequest request, String getFilePath, String getFileName) {
		final String FIXEDCONTSEQ = "1";
		TbIoTBrdFileListDTO dto = new TbIoTBrdFileListDTO();
		dto.setContentSeq(FIXEDCONTSEQ);
		dto.setContentType(FileBoardTypeDTO.SVC);
		
		// ?????? ??????
		StringBuilder filePath = new StringBuilder();
		filePath.append(FILE_UPLOAD_PATH);
		filePath.append(getFilePath).append(File.separator);
		FileUtils.deleteFile(request, filePath.toString(), getFileName);
		
		brdFileListDAO.deleteTbIoTBrdFileList(dto);
		
		
	}

	private List<String> makeExcelPropsList(IotMgntDTO iotMgntDTO) {
		List<String> excelPropList = new ArrayList<String>();

		if (iotMgntDTO.getType().equals("D")) {
			excelPropList.add("devSeq");
			excelPropList.add("devClsCd");
			excelPropList.add("devClsCdNm");
			excelPropList.add("devMdlCd");
			excelPropList.add("devMdlNm");
			excelPropList.add("useYn");
			excelPropList.add("statusCd");
			excelPropList.add("parentDevSeq");
			excelPropList.add("vendorNm");
			excelPropList.add("normalIconFile");
			excelPropList.add("abnormalIconFile");
			excelPropList.add("abnormalIconFile2");
			excelPropList.add("iconRegYn");
		} else if (iotMgntDTO.getType().equals("DA")) {
			excelPropList.add("devAttbSeq");
			excelPropList.add("devSeq");
			excelPropList.add("devClsCd");
//			excelPropList.add("devClsCdNm");
			excelPropList.add("devMdlCd");
//			excelPropList.add("devMdlNm");
			excelPropList.add("devAttbCdId");
//			excelPropList.add("devAttbCdNm");
			excelPropList.add("inputType");
			excelPropList.add("statusCd");
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
		} else if (iotMgntDTO.getType().equals("DAS")) {
			excelPropList.add("sDevAttbSetSeq");
			excelPropList.add("sDevAttbSeq");
			excelPropList.add("svcCd");
//			excelPropList.add("devAttbCdId");
			excelPropList.add("devAttbSetCdId");
//			excelPropList.add("devAttbSetCdNm");
			excelPropList.add("statusCd");
			excelPropList.add("devAttbSetVal");
		} else if (iotMgntDTO.getType().equals("DD")) {
			excelPropList.add("devDetSetSeq");
			excelPropList.add("devAttbSeq");
			excelPropList.add("devDetSetCdId");
//			excelPropList.add("devDetSetCdNm");
			excelPropList.add("detSetCond");
			excelPropList.add("statusCd");
			excelPropList.add("iconUrl");
			excelPropList.add("detSetDesc");
		} else if (iotMgntDTO.getType().equals("P") || iotMgntDTO.getType().equals("M")  ) {
			excelPropList.add("apiSeq");
			excelPropList.add("devSeq");
			excelPropList.add("ruleMsgType");
			excelPropList.add("ruleColType");
			excelPropList.add("ruleDevType");
			excelPropList.add("ruleSeq");
			excelPropList.add("ruleRequest");
			excelPropList.add("ruleSourcekey");
			excelPropList.add("ruleKind");
			excelPropList.add("ruleBytePosi");
			excelPropList.add("ruleBitPosi");
			excelPropList.add("ruleNumber");
			excelPropList.add("rulePoint");
			excelPropList.add("ruleTargetkey");
		} else if (iotMgntDTO.getType().equals("A")) {
			excelPropList.add("apiSeq");
			excelPropList.add("devSeq");
			excelPropList.add("valRuleSeq");
			excelPropList.add("valRuleName");
			excelPropList.add("ruleMsgType");
			excelPropList.add("ruleKind");
			excelPropList.add("defaultYn");
			excelPropList.add("nullYn");
			excelPropList.add("emptyYn");
			excelPropList.add("dataSize");
			excelPropList.add("dataMin");
			excelPropList.add("dataMax");
			excelPropList.add("allowRegex");
			excelPropList.add("notallowRegex");
			excelPropList.add("datatp");
		} else if (iotMgntDTO.getType().equals("S")) {
			excelPropList.add("apiSeq");
			excelPropList.add("devSeq");
			excelPropList.add("callApiSeq");
		}

		return excelPropList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deviceUploadSvc(HttpServletRequest request) throws BizException {

		ComInfoDto temp = new ComInfoDto();

		final String FIXEDCONTSEQ = "1";
		fileService.saveFiles(request, FileBoardTypeDTO.SVC, FIXEDCONTSEQ);
		// Board Type ??????
		TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
		tbIoTBrdFileDTO.setContentSeq(FIXEDCONTSEQ);
		tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.SVC);
		// ???????????? ???????????? ????????? ?????????
		List<String> excelPropList = null;
		excelPropList = this.makeExcelPropsList_DevM();

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
				List<IotMgntSDevMDTO> devDtoList = new ArrayList<IotMgntSDevMDTO>();
				for (Iterator iterator = savedFileList.iterator(); iterator.hasNext();) {
				// exception ????????? ?????? ??????

				StringBuilder filePath = new StringBuilder();
				filePath.append(FILE_UPLOAD_PATH);
				TbIoTBrdFileDTO tmpBrdDto = (TbIoTBrdFileDTO) iterator.next();
				filePath.append(tmpBrdDto.getFilePath()).append(File.separator).append(tmpBrdDto.getFileName());
				InputStream xslInputStream = null;
				try {
					xslInputStream = new FileInputStream(filePath.toString());
					devDtoList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, IotMgntSDevMDTO.class);

					IotMgntSDevMDTO deleteDto = devDtoList.get(0);
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.deleleteDeviceMAll");
					try {
						tbIotMgntDAO.deleleteDeviceMAll(deleteDto);
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


					for (Iterator iterator2 = devDtoList.iterator(); iterator2.hasNext();) {
						System.out.println(iterator2);
						IotMgntSDevMDTO devMDto = trimObj_DevM((IotMgntSDevMDTO) iterator2.next());
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.insertDevM");
						try {
							tbIotMgntDAO.insertDevM(devMDto);
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
				finally {
					try {
						if (xslInputStream != null) {
							xslInputStream.close();
						}
					} catch (IOException e) {
					}
					this.deleteFile(request,tmpBrdDto.getFilePath(),tmpBrdDto.getFileName());
				}
			}
		}

	}

	private IotMgntSDevMDTO trimObj_DevM(IotMgntSDevMDTO dto) {
		dto.setSvcDevSeq(dto.getSvcDevSeq());
		dto.setSvcCd(dto.getSvcCd());
	    dto.setDevClsCd(dto.getDevClsCd());
		dto.setDevClsCdNm(dto.getDevClsCdNm());
		dto.setDevMdlCd(dto.getDevMdlCd());
		dto.setDevMdlNm(dto.getDevMdlNm());
		dto.setStatusCd(dto.getStatusCd());
		dto.setParentDevSeq(dto.getParentDevSeq());
		dto.setVendorNm(dto.getVendorNm());
		dto.setNormalIconFile(dto.getNormalIconFile());
		dto.setAbnormalIconFile(dto.getAbnormalIconFile());
		dto.setAbnormalIconFile2(dto.getAbnormalIconFile2());
		dto.setDevMdlVal(dto.getDevMdlVal());
		dto.setDevSeq(dto.getDevSeq());
		String regUsrId = AuthUtils.getUser().getUserId();
		dto.setRegUsrId(regUsrId);
		return dto;
	}

	private List<String> makeExcelPropsList_DevM() {
		List<String> excelPropList = new ArrayList<String>();
		excelPropList.add("svcDevSeq");
		excelPropList.add("svcCd");
		excelPropList.add("devClsCd");
		excelPropList.add("devClsCdNm");
		excelPropList.add("devMdlCd");
		excelPropList.add("devMdlNm");
		excelPropList.add("statusCd");
		excelPropList.add("parentDevSeq");
		excelPropList.add("vendorNm");
		excelPropList.add("normalIconFile");
		excelPropList.add("abnormalIconFile");
		excelPropList.add("abnormalIconFile2");
		excelPropList.add("devMdlVal");
		excelPropList.add("devSeq");

		return excelPropList;
	}

	@Override
	public void deviceAttrUploadSvc(HttpServletRequest request) throws BizException {
		String json = request.getParameter("jsonData");
		ComInfoDto temp = new ComInfoDto();

		final String FIXEDCONTSEQ = "1";
		fileService.saveFiles(request, FileBoardTypeDTO.SVC, FIXEDCONTSEQ);
		// Board Type ??????
		TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
		tbIoTBrdFileDTO.setContentSeq(FIXEDCONTSEQ);
		tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.SVC);
		// ???????????? ???????????? ????????? ?????????
		List<String> excelPropList = null;
		excelPropList = this.makeExcelPropsList_DevAttr();

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
				List<IotMgntSDevAtbDTO> devAttrDtoList = new ArrayList<IotMgntSDevAtbDTO>();
				for (Iterator iterator = savedFileList.iterator(); iterator.hasNext();) {
				// exception ????????? ?????? ??????

				StringBuilder filePath = new StringBuilder();
				filePath.append(FILE_UPLOAD_PATH);
				TbIoTBrdFileDTO tmpBrdDto = (TbIoTBrdFileDTO) iterator.next();
				filePath.append(tmpBrdDto.getFilePath()).append(File.separator).append(tmpBrdDto.getFileName());
				InputStream xslInputStream = null;
				try {
					xslInputStream = new FileInputStream(filePath.toString());
					devAttrDtoList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, IotMgntSDevAtbDTO.class);
					IotMgntSDevAtbDTO deleteDto = devAttrDtoList.get(0);
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.deleleteDeviceAttr");
					try {
						tbIotMgntDAO.deleleteDeviceMAttr(deleteDto);
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


					for (Iterator iterator2 = devAttrDtoList.iterator(); iterator2.hasNext();) {
						IotMgntSDevAtbDTO devAttrDto = trimObj_DevAttr((IotMgntSDevAtbDTO) iterator2.next());
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.insertDevAttr");
						try {
							tbIotMgntDAO.insertDevAttr(devAttrDto);
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
				finally {
					try {
						if (xslInputStream != null) {
							xslInputStream.close();
						}
					} catch (IOException e) {
					}
					this.deleteFile(request,tmpBrdDto.getFilePath(),tmpBrdDto.getFileName());
				}
			}
		}

	}

	private IotMgntSDevAtbDTO trimObj_DevAttr(IotMgntSDevAtbDTO dto) {
		// TODO Auto-generated method stub
		String regUsrId = AuthUtils.getUser().getUserId();
		dto.setRegUsrId(regUsrId);
		dto.setSvcDevAttbSeq(dto.getSvcDevAttbSeq());
		dto.setSvcDevSeq(dto.getSvcDevSeq());
		dto.setSvcCd(dto.getSvcCd());
		dto.setDevClsCd(dto.getDevClsCd());
//		dto.setDevClsCdNm(dto.getDevClsCdNm());
		dto.setDevMdlCd(dto.getDevMdlCd());
//		dto.setDevMdlNm(dto.getDevMdlNm());
		dto.setDevAttbCdId(dto.getDevAttbCdId());
//		dto.setDevAttbCdNm(dto.getDevAttbCdNm());
		dto.setInputType(dto.getInputType());
		dto.setDevAttbType(dto.getDevAttbType());
		dto.setStatusCd(dto.getStatusCd());
		dto.setParamKey(dto.getParamKey());
		dto.setColNmCd(dto.getColNmCd());
		dto.setConNmCd(dto.getConNmCd());
		dto.setStaAvgNmCd(dto.getStaAvgNmCd());
		dto.setStaSumNmCd(dto.getStaSumNmCd());
		dto.setDetNmCd(dto.getDetNmCd());
		dto.setMapYn(dto.getMapYn());
		dto.setUnit(dto.getUnit());
		dto.setOrderNo(dto.getOrderNo());
		dto.setDevSeq(dto.getDevSeq());
		dto.setRegUsrId(dto.getRegUsrId());
		return dto;
	}

	private List<String> makeExcelPropsList_DevAttr() {

		List<String> excelPropList = new ArrayList<String>();
		excelPropList.add("svcDevSeq");
		excelPropList.add("svcDevAttbSeq");
		excelPropList.add("svcCd");
		excelPropList.add("devClsCd");
//		excelPropList.add("devClsCdNm");
		excelPropList.add("devMdlCd");
//		excelPropList.add("devMdlNm");
		excelPropList.add("devAttbCdId");
//		excelPropList.add("devAttbCdNm");
		excelPropList.add("inputType");
		excelPropList.add("statusCd");
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
		excelPropList.add("devSeq");
		return excelPropList;
}

	@Override
	public void deviceAttrSetUploadSvc(HttpServletRequest request) throws BizException {
		String json = request.getParameter("jsonData");
		Map<String, Object> mapper = JsonUtils.fromJson(json, Map.class);
		ComInfoDto temp = new ComInfoDto();

		final String FIXEDCONTSEQ = "1";
		fileService.saveFiles(request, FileBoardTypeDTO.SVC, FIXEDCONTSEQ);
		// Board Type ??????
		TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
		tbIoTBrdFileDTO.setContentSeq(FIXEDCONTSEQ);
		tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.SVC);
		// ???????????? ???????????? ????????? ?????????
		List<String> excelPropList = null;
		excelPropList = this.makeExcelPropsList_DevAttrSet();

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
				List<IotMgntSDevAtbSetDTO> devAttrSetDtoList = new ArrayList<IotMgntSDevAtbSetDTO>();
				for (Iterator iterator = savedFileList.iterator(); iterator.hasNext();) {
				// exception ????????? ?????? ??????

				StringBuilder filePath = new StringBuilder();
				filePath.append(FILE_UPLOAD_PATH);
				TbIoTBrdFileDTO tmpBrdDto = (TbIoTBrdFileDTO) iterator.next();
				filePath.append(tmpBrdDto.getFilePath()).append(File.separator).append(tmpBrdDto.getFileName());
				InputStream xslInputStream = null;
				try {
					xslInputStream = new FileInputStream(filePath.toString());
					devAttrSetDtoList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, IotMgntSDevAtbSetDTO.class);
					IotMgntSDevAtbSetDTO deleteDto = devAttrSetDtoList.get(0);
					deleteDto.setDevClsCd(mapper.get("devClsCd").toString());
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.deleleteDeviceAttrSet");
					try {
						tbIotMgntDAO.deleleteDeviceMAttrSet(deleteDto);
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


					for (Iterator iterator2 = devAttrSetDtoList.iterator(); iterator2.hasNext();) {
						IotMgntSDevAtbSetDTO devAttrSetDto = trimObj_DevAttrSet((IotMgntSDevAtbSetDTO) iterator2.next());
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.insertDevAttrSet");
						try {
							tbIotMgntDAO.insertDevAttrSet(devAttrSetDto);
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
				finally {
					try {
						if (xslInputStream != null) {
							xslInputStream.close();
						}
					} catch (IOException e) {
					}
					this.deleteFile(request,tmpBrdDto.getFilePath(),tmpBrdDto.getFileName());
				}
			}
		}
	}

	private IotMgntSDevAtbSetDTO trimObj_DevAttrSet(IotMgntSDevAtbSetDTO dto) {
		String regUsrId = AuthUtils.getUser().getUserId();
		dto.setRegUsrId(regUsrId);

		dto.setSvcDevAttbSetSeq(dto.getSvcDevAttbSetSeq());
		dto.setSvcDevAttbSeq(dto.getSvcDevAttbSeq());
		dto.setSvcCd(dto.getSvcCd());
//		dto.setDevAttbCdId(dto.getDevAttbCdId());
		dto.setDevAttbSetCdId(dto.getDevAttbSetCdId());
//		dto.setDevAttbSetCdNm(dto.getDevAttbSetCdNm());
		dto.setStatusCd(dto.getStatusCd());
		dto.setDevAttbSetVal(dto.getDevAttbSetVal());
		dto.setSetParentCdId(dto.getSetParentCdId()); //		title.put("????????????SET??????", "");
		dto.setDevClsCd(dto.getDevClsCd());

		return dto;
	}

	private List<String> makeExcelPropsList_DevAttrSet() {

		List<String> excelPropList = new ArrayList<String>();
		excelPropList.add("svcDevAttbSetSeq");
		excelPropList.add("svcDevAttbSeq");
		excelPropList.add("svcCd");
//		excelPropList.add("devAttbCdId");
		excelPropList.add("devAttbSetCdId");
//		excelPropList.add("devAttbSetCdNm");
		excelPropList.add("statusCd");
		excelPropList.add("devAttbSetVal");
		excelPropList.add("setSetParentCdId");

		return excelPropList;
	}

	@Override
	public void deviceDetSetUploadSvc(HttpServletRequest request) throws BizException {
		String json = request.getParameter("jsonData");
		Map<String, Object> mapper = JsonUtils.fromJson(json, Map.class);

		ComInfoDto temp = new ComInfoDto();

		final String FIXEDCONTSEQ = "1";
		fileService.saveFiles(request, FileBoardTypeDTO.SVC, FIXEDCONTSEQ);
		// Board Type ??????
		TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
		tbIoTBrdFileDTO.setContentSeq(FIXEDCONTSEQ);
		tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.SVC);
		// ???????????? ???????????? ????????? ?????????
		List<String> excelPropList = null;
		excelPropList = this.makeExcelPropsList_DevDetSet();

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
				List<IotMgntSDevDetSetDTO> devDetSetDtoList = new ArrayList<IotMgntSDevDetSetDTO>();
				for (Iterator iterator = savedFileList.iterator(); iterator.hasNext();) {
				// exception ????????? ?????? ??????
				String devClsCd = mapper.get("devClsCd").toString();

				StringBuilder filePath = new StringBuilder();
				filePath.append(FILE_UPLOAD_PATH);
				TbIoTBrdFileDTO tmpBrdDto = (TbIoTBrdFileDTO) iterator.next();
				filePath.append(tmpBrdDto.getFilePath()).append(File.separator).append(tmpBrdDto.getFileName());
				InputStream xslInputStream = null;
				try {
					xslInputStream = new FileInputStream(filePath.toString());
					devDetSetDtoList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, IotMgntSDevDetSetDTO.class);
					IotMgntSDevDetSetDTO deleteDto = devDetSetDtoList.get(0);
					deleteDto.setDevClsCd(devClsCd);
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.deleleteDeviceDetSet");
					try {
						tbIotMgntDAO.deleleteDeviceDetSet(deleteDto);
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


					for (Iterator iterator2 = devDetSetDtoList.iterator(); iterator2.hasNext();) {
						IotMgntSDevDetSetDTO devDetSetDto = trimObj_DevDetSet((IotMgntSDevDetSetDTO) iterator2.next());
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.insertDevDetSet");
						try {
							tbIotMgntDAO.insertDevDetSet(devDetSetDto);
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
				finally {
					try {
						if (xslInputStream != null) {
							xslInputStream.close();
						}
					} catch (IOException e) {
					}
					this.deleteFile(request,tmpBrdDto.getFilePath(),tmpBrdDto.getFileName());
				}
			}
		}
	}

	private IotMgntSDevDetSetDTO trimObj_DevDetSet(IotMgntSDevDetSetDTO dto) {
		String regUsrId = AuthUtils.getUser().getUserId();
		dto.setRegUsrId(regUsrId);

		dto.setSvcDevDetSetSeq(dto.getSvcDevDetSetSeq());
		dto.setSvcDevAttbSeq(dto.getSvcDevAttbSeq());
		dto.setSvcCd(dto.getSvcCd());
		dto.setDevDetSetCdId(dto.getDevDetSetCdId());
//		dto.setDevDetSetCdNm(dto.getDevDetSetCdNm());
		dto.setDetSetCond(dto.getDetSetCond());
		dto.setStatusCd(dto.getStatusCd());
		dto.setIconUrl(dto.getIconUrl());
		dto.setDetSetDesc(dto.getDetSetDesc());
		return dto;
	}

	private List<String> makeExcelPropsList_DevDetSet() {

		List<String> excelPropList = new ArrayList<String>();
		excelPropList.add("svcDevDetSetSeq");
		excelPropList.add("svcDevAttbSeq");
		excelPropList.add("svcCd");
		excelPropList.add("devDetSetCdId");
//		excelPropList.add("devDetSetCdNm");
		excelPropList.add("detSetCond");
		excelPropList.add("statusCd");
		excelPropList.add("iconUrl");
		excelPropList.add("detSetDesc");
		return excelPropList;
	}

	// ?????????, ????????? ??????
	@SuppressWarnings("unchecked")
	@Override
	public void svcRoleRuleParseUploadSvc(HttpServletRequest request, IotMgntDTO iotMgntDTO) throws BizException {
		String json = request.getParameter("jsonData");
		ComInfoDto temp = new ComInfoDto();

		final String FIXEDCONTSEQ = "1";
		fileService.saveFiles(request, FileBoardTypeDTO.SVC, FIXEDCONTSEQ);
		// Board Type ??????
		TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
		tbIoTBrdFileDTO.setContentSeq(FIXEDCONTSEQ);
		tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.SVC);
		// ???????????? ???????????? ????????? ?????????
		List<String> excelPropList = this.makeExcelPropsList(iotMgntDTO);

		// ????????? ?????? ????????? ??????
		List<TbIoTBrdFileDTO> savedFileList = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"brdFileListDAO.retrieveTbIoTBrdFileList");
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
			List<IotMgntColRuleDTO> ruleColList = new ArrayList<IotMgntColRuleDTO>();
			for (Iterator iterator = savedFileList.iterator(); iterator.hasNext();) {
				// exception ????????? ?????? ??????

				StringBuilder filePath = new StringBuilder();
				filePath.append(FILE_UPLOAD_PATH);
				TbIoTBrdFileDTO tmpBrdDto = (TbIoTBrdFileDTO) iterator.next();
				filePath.append(tmpBrdDto.getFilePath()).append(File.separator).append(tmpBrdDto.getFileName());
				InputStream xslInputStream = null;
				try {
					xslInputStream = new FileInputStream(filePath.toString());
					ruleColList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, IotMgntColRuleDTO.class);
					if (iotMgntDTO.getType().equals("P"))
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.deleleteRuleParseAll");
					if (iotMgntDTO.getType().equals("M"))
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.deleleteRuleMapAll");
					try {
						if (iotMgntDTO.getType().equals("P"))
							tbIotMgntDAO.deleleteRuleParseAll(iotMgntDTO);
						if (iotMgntDTO.getType().equals("M"))
							tbIotMgntDAO.deleleteRuleMapAll(iotMgntDTO);
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

					for (Iterator iterator2 = ruleColList.iterator(); iterator2.hasNext();) {
						IotMgntColRuleDTO ruleColDto = trimObj_ColRule((IotMgntColRuleDTO) iterator2.next());
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.insertRuleCol");
						try {
							tbIotMgntDAO.insertRuleCol(ruleColDto);
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
				} finally {
					try {
						if (xslInputStream != null) {
							xslInputStream.close();
						}
					} catch (IOException e) {
					}
					this.deleteFile(request,tmpBrdDto.getFilePath(),tmpBrdDto.getFileName());
				}
			}
		}
	}

	// ????????? ??????
	@Override
	public void svcRoleRuleValUploadSvc(HttpServletRequest request, IotMgntDTO iotMgntDTO) throws BizException {
		String json = request.getParameter("jsonData");
		ComInfoDto temp = new ComInfoDto();

		final String FIXEDCONTSEQ = "1";
		fileService.saveFiles(request, FileBoardTypeDTO.SVC, FIXEDCONTSEQ);
		// Board Type ??????
		TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
		tbIoTBrdFileDTO.setContentSeq(FIXEDCONTSEQ);
		tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.SVC);
		// ???????????? ???????????? ????????? ?????????
		List<String> excelPropList = null;
		excelPropList = this.makeExcelPropsList(iotMgntDTO);

		// ????????? ?????? ????????? ??????
		List<TbIoTBrdFileDTO> savedFileList = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,"brdFileListDAO.retrieveTbIoTBrdFileList");
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
			List<IotMgntValRuleDTO> valRuleList = new ArrayList<IotMgntValRuleDTO>();
			for (Iterator iterator = savedFileList.iterator(); iterator.hasNext();) {
				// exception ????????? ?????? ??????

				StringBuilder filePath = new StringBuilder();
				filePath.append(FILE_UPLOAD_PATH);
				TbIoTBrdFileDTO tmpBrdDto = (TbIoTBrdFileDTO) iterator.next();
				filePath.append(tmpBrdDto.getFilePath()).append(File.separator).append(tmpBrdDto.getFileName());
				InputStream xslInputStream = null;
				try {
					xslInputStream = new FileInputStream(filePath.toString());
					valRuleList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, IotMgntValRuleDTO.class);
					if(iotMgntDTO.getGubun().equals("AV")) {
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.deleleteApiValRuleAll");
					}
					else {
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.deleleteValRuleAll");
					}
					try {
						if(iotMgntDTO.getGubun().equals("AV")) {
							tbIotMgntDAO.deleleteApiValRuleAll();
						} else {
							tbIotMgntDAO.deleleteValRuleAll(iotMgntDTO);
						}
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

					for (Iterator iterator2 = valRuleList.iterator(); iterator2.hasNext();) {
						IotMgntValRuleDTO ruleValDto = trimObj_ValRule((IotMgntValRuleDTO) iterator2.next());
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.insertValRule");
						try {
							tbIotMgntDAO.insertRuleVal(ruleValDto);
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
				} finally {
					try {
						if (xslInputStream != null) {
							xslInputStream.close();
						}
					} catch (IOException e) {
					}
					this.deleteFile(request,tmpBrdDto.getFilePath(),tmpBrdDto.getFileName());
				}
			}
		}
	}

	@Override
	public void svcRoleRuleApiSvcUploadSvc(HttpServletRequest request, IotMgntDTO iotMgntDTO) throws BizException {
		String json = request.getParameter("jsonData");
		ComInfoDto temp = new ComInfoDto();

		final String FIXEDCONTSEQ = "1";
		fileService.saveFiles(request, FileBoardTypeDTO.SVC, FIXEDCONTSEQ);
		// Board Type ??????
		TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
		tbIoTBrdFileDTO.setContentSeq(FIXEDCONTSEQ);
		tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.SVC);
		// ???????????? ???????????? ????????? ?????????
		List<String> excelPropList = this.makeExcelPropsList(iotMgntDTO);

		// ????????? ?????? ????????? ??????
		List<TbIoTBrdFileDTO> savedFileList = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"brdFileListDAO.retrieveTbIoTBrdFileList");
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
			List<IotMgntApiRuleDTO> ruleApiRuleList = new ArrayList<IotMgntApiRuleDTO>();
			for (Iterator iterator = savedFileList.iterator(); iterator.hasNext();) {
				// exception ????????? ?????? ??????

				StringBuilder filePath = new StringBuilder();
				filePath.append(FILE_UPLOAD_PATH);
				TbIoTBrdFileDTO tmpBrdDto = (TbIoTBrdFileDTO) iterator.next();
				filePath.append(tmpBrdDto.getFilePath()).append(File.separator).append(tmpBrdDto.getFileName());
				InputStream xslInputStream = null;
				try {
					xslInputStream = new FileInputStream(filePath.toString());
					ruleApiRuleList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, IotMgntApiRuleDTO.class);

					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
							"tbIotMgntDAO.deleleteRuleApiRuleAll");
					try {
						tbIotMgntDAO.deleleteRuleApiRuleAll(iotMgntDTO);
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

					for (Iterator iterator2 = ruleApiRuleList.iterator(); iterator2.hasNext();) {
						IotMgntApiRuleDTO ruleApiDto = trimObj_ApiRule((IotMgntApiRuleDTO) iterator2.next());
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,"tbIotMgntDAO.insertRuleApi");
						try {
							tbIotMgntDAO.insertRuleApi(ruleApiDto);
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
				} finally {
					try {
						if (xslInputStream != null) {
							xslInputStream.close();
						}
					} catch (IOException e) {
					}
					this.deleteFile(request,tmpBrdDto.getFilePath(),tmpBrdDto.getFileName());
				}
			}
		}
	}

	public IotMgntColRuleDTO trimObj_ColRule(IotMgntColRuleDTO dto) {
		String regUsrId = AuthUtils.getUser().getUserId();
		dto.setRegUsrId(regUsrId);
		dto.setRuleUri(dto.getRuleUri());
		dto.setApiSeq(dto.getApiSeq());
		dto.setDevSeq(dto.getDevSeq());
		dto.setRuleMsgType(dto.getRuleMsgType());
		dto.setRuleColType(dto.getRuleColType());
//		dto.setDevruleSeq(dto.getDevruleSeq());
		dto.setRuleSeq(dto.getRuleSeq());
		dto.setRuleDevType(dto.getRuleDevType());
		dto.setRuleGubun(dto.getRuleGubun());
		dto.setRuleKind(dto.getRuleKind());
		dto.setRuleBytePosi(dto.getRuleBytePosi());
		dto.setRuleBitPosi(dto.getRuleBitPosi());
		dto.setRuleNumber(dto.getRuleNumber());
		dto.setRulePoint(dto.getRulePoint());
		dto.setRuleTargetkey(dto.getRuleTargetkey());
		dto.setRuleArgs(dto.getRuleArgs());
		dto.setRuleRequest(dto.getRuleRequest());
		dto.setRuleSourcekey(dto.getRuleSourcekey());
		dto.setExeFile(dto.getExeFile());
		dto.setDevMdlCd(dto.getDevMdlCd());
		return dto;
	}

	public IotMgntValRuleDTO trimObj_ValRule(IotMgntValRuleDTO dto) {
		String regUsrId = AuthUtils.getUser().getUserId();
		dto.setRegUsrId(regUsrId);
		dto.setRuleUri(dto.getRuleUri());
		dto.setDevSeq(dto.getDevSeq());
		dto.setValRuleSeq(dto.getValRuleSeq());
		dto.setValRuleName(dto.getValRuleName());
		dto.setRuleKind(dto.getRuleKind());
		dto.setDefaultYn(dto.getDefaultYn());
		dto.setNullYn(dto.getNullYn());
		dto.setEmptyYn(dto.getEmptyYn());
		dto.setDataType(dto.getDataType());
		dto.setDataSize(dto.getDataSize());
		dto.setDataMin(dto.getDataMin());
		dto.setDataMax(dto.getDataMax());
		dto.setAllowRegex(dto.getAllowRegex());
		dto.setNotallowRegex(dto.getNotallowRegex());
		dto.setApiSeq(dto.getApiSeq());
		dto.setRuleColType(dto.getRuleColType());
		dto.setRuleSourcekey(dto.getRuleSourcekey());
		dto.setRuleRequest(dto.getRuleRequest());
		return dto;
	}

	public IotMgntApiRuleDTO trimObj_ApiRule(IotMgntApiRuleDTO dto) {
		String regUsrId = AuthUtils.getUser().getUserId();
		dto.setApiSeq(dto.getApiSeq());
		dto.setDevSeq(dto.getDevSeq());
		dto.setCallApiSeq(dto.getCallApiSeq());
		dto.setRegUsrId(regUsrId);
		return dto;
	}

	@Override
	public void apiValUploadSvc(HttpServletRequest request) throws BizException {
		String json = request.getParameter("jsonData");
		Map<String, Object> mapper = JsonUtils.fromJson(json, Map.class);
		String gubun = mapper.get("gubun").toString();
		ComInfoDto temp = new ComInfoDto();
		final String FIXEDCONTSEQ = "1";
		fileService.saveFiles(request, FileBoardTypeDTO.SVC, FIXEDCONTSEQ);
		// Board Type ??????
		TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
		tbIoTBrdFileDTO.setContentSeq(FIXEDCONTSEQ);
		tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.SVC);
		// ???????????? ???????????? ????????? ?????????

		IotMgntDTO iotMgntDTO = new IotMgntDTO();
		iotMgntDTO.setGubun(gubun);
		if(gubun.equals("AV")) {
			iotMgntDTO.setType("A");
		}
		List<String> excelPropList = this.makeExcelPropsList(iotMgntDTO);
		// ????????? ?????? ????????? ??????
		List<TbIoTBrdFileDTO> savedFileList = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"brdFileListDAO.retrieveTbIoTBrdFileList");
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
			List<IotMgntApiRuleDTO> ruleApiRuleList = new ArrayList<IotMgntApiRuleDTO>();
			for (Iterator iterator = savedFileList.iterator(); iterator.hasNext();) {
				// exception ????????? ?????? ??????

				StringBuilder filePath = new StringBuilder();
				filePath.append(FILE_UPLOAD_PATH);
				TbIoTBrdFileDTO tmpBrdDto = (TbIoTBrdFileDTO) iterator.next();
				filePath.append(tmpBrdDto.getFilePath()).append(File.separator).append(tmpBrdDto.getFileName());
				InputStream xslInputStream = null;
				try {
					xslInputStream = new FileInputStream(filePath.toString());
					ruleApiRuleList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, IotMgntApiRuleDTO.class);

					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,"tbIotMgntDAO.deleleteRuleApiRuleAll");
					try {
						tbIotMgntDAO.deleleteRuleApiRuleAll(iotMgntDTO);
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

					for (Iterator iterator2 = ruleApiRuleList.iterator(); iterator2.hasNext();) {
						IotMgntApiRuleDTO ruleApiDto = trimObj_ApiRule((IotMgntApiRuleDTO) iterator2.next());
						temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,"tbIotMgntDAO.insertRuleApi");
						try {
							tbIotMgntDAO.insertRuleApi(ruleApiDto);
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
				} finally {
					try {
						if (xslInputStream != null) {
							xslInputStream.close();
						}
					} catch (IOException e) {
					}
					this.deleteFile(request,tmpBrdDto.getFilePath(),tmpBrdDto.getFileName());
				}
			}
		}
	}

	
	@Override
	public HashMap<String, Object> retriveServiceListSvc(IotMgntDTO iotMgntDTO) throws BizException {
		ComInfoDto temp = new ComInfoDto();
		HashMap<String, Object> rnMap = new HashMap<String, Object>();
//		HashMap<String, Object> clsImgMap = new HashMap<String, Object>();
		List<ArrayList<HashMap<String, Object>>> clsImgMap = new ArrayList<ArrayList<HashMap<String, Object>>>();
		
		List<IotMgntSvcDTO>  svcDtoList = retriveServiceListQuery(iotMgntDTO);
		List<HashMap<String, Object>> cmcdList = retrieveSvcCdForSev(iotMgntDTO);
		
		for (IotMgntSvcDTO svcM : svcDtoList) {
			if (svcM.getDevClsCd() != null) {
				HashMap param = new HashMap();
				param.put("devClsCd", svcM.getDevClsCd());
				clsImgMap.add(retrieveClsImgForSev(param));
			}
		}
		
//		for (IotMgntSvcDTO svcM : svcDtoList) {
//			if (svcM.getDevClsCd() != null) {
//				HashMap param = new HashMap();
//				param.put("devClsCd", svcM.getDevClsCd());
//				clsImgMap.add(retrieveClsImgForSev(param));
//			}
//		}
		
		rnMap.put("clsImgList", clsImgMap);
		rnMap.put("svcCdList", svcDtoList);
		rnMap.put("cmCdList", cmcdList);
		
		return rnMap;
	}

	
	private List<IotMgntSvcDTO> retriveServiceListQuery(IotMgntDTO iotMgntDTO) throws BizException {
		ComInfoDto temp = new ComInfoDto();
		Map<String, String> title = new LinkedHashMap<String, String>();
		List<IotMgntSvcDTO> svcDtoList = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.retriveServiceList");
		try {
			svcDtoList = tbIotMgntDAO.retriveServiceList(iotMgntDTO);
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

		return svcDtoList;
	}
	
	private List<HashMap<String, Object>> retrieveSvcCdForSev(IotMgntDTO iotMgntDTO) throws BizException {
		ComInfoDto temp = new ComInfoDto();
		List<HashMap<String, Object>> rnlist  = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.retrieveSvcCdForSev");
		try {
			iotMgntDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
			rnlist = tbIotMgntDAO.retrieveSvcCdForSev(iotMgntDTO);
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
		return rnlist;
	}

	private ArrayList<HashMap<String, Object>> retrieveClsImgForSev(HashMap hashMap) throws BizException {
		ComInfoDto temp = new ComInfoDto();
		ArrayList<HashMap<String, Object>> rnlist  = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.retrieveSvcCdForSev");
		try {
			rnlist = tbIotMgntDAO.retrieveClsImgForSev(hashMap);
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
		return rnlist;
	}
		
	
	

	@Override
	public List<TbIotRoleMapDTO> retriveServiceAuthListQuery(IotMgntDTO iotMgntDTO) throws BizException {
		ComInfoDto temp = new ComInfoDto();
		List<TbIotRoleMapDTO> svcRoleList = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.retriveServiceAuthList");
		try {
			svcRoleList = tbIotMgntDAO.retriveServiceAuthList(iotMgntDTO);
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

		return svcRoleList;
	}



	@Override
	public List<IotMgntSDevMDTO> retriveDevListQuery(IotMgntDTO iotMgntDTO) throws BizException {
		List<IotMgntSDevMDTO> list = null;
		ComInfoDto temp = new ComInfoDto();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.retriveDevList");
		try {
			list = tbIotMgntDAO.retriveDevList(iotMgntDTO);
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
	public List<IotMgntSDevAtbDTO> retriveDevAtbListQuery(IotMgntDTO iotMgntDTO) throws BizException {
		ComInfoDto temp = new ComInfoDto();
		List<IotMgntSDevAtbDTO> list = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.retriveDevAtbList");
		try {
			list = tbIotMgntDAO.retriveDevAtbList(iotMgntDTO);
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
	public List<IotMgntSDevAtbSetDTO> retriveDevAtbSetListQuery(IotMgntDTO iotMgntDTO) throws BizException {
		ComInfoDto temp = new ComInfoDto();
		List<IotMgntSDevAtbSetDTO> list = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMgntDAO.retriveDevAtbSetList");
		try {
			list = tbIotMgntDAO.retriveDevAtbSetList(iotMgntDTO);
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
	public List<IotMgntSDevDetSetDTO> retriveDevDetSetListQuery(IotMgntDTO iotMgntDTO) throws BizException {
		ComInfoDto temp = new ComInfoDto();

		List<IotMgntSDevDetSetDTO> list = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,"tbIotMgntDAO.retriveDevDetSetList");
		try {
			list = tbIotMgntDAO.retriveDevDetSetList(iotMgntDTO);
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
	public List<TbIotDevColRuleDTO> retriveDevRuleParseListQuery(IotMgntDTO iotMgntDTO) throws BizException {
		ComInfoDto temp = new ComInfoDto();

		List<TbIotDevColRuleDTO> list = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMgntDAO.retriveDevRuleParseList");
		try {
			list = tbIotMgntDAO.retriveDevRuleParseList(iotMgntDTO);
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
	public List<TbIotValRuleDTO> retriveDevRuleValListQuery(IotMgntDTO iotMgntDTO) throws BizException {
		ComInfoDto temp = new ComInfoDto();

		List<TbIotValRuleDTO> list = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMgntDAO.retriveDevRuleValList");
		try {
			list = tbIotMgntDAO.retriveDevRuleValList(iotMgntDTO);
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
	public List<TbIotDevColRuleDTO> retriveDevRuleMapListQuery(IotMgntDTO iotMgntDTO) throws BizException {
		ComInfoDto temp = new ComInfoDto();
		List<TbIotDevColRuleDTO> list = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMgntDAO.retriveDevRuleMapList");
		try {
			list = tbIotMgntDAO.retriveDevRuleMapList(iotMgntDTO);
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
	public List<TbIotDevApiRuleParamDTO> retriveDevRuleServiceListQuery(IotMgntDTO iotMgntDTO) throws BizException {

		ComInfoDto temp = new ComInfoDto();

		List<TbIotDevApiRuleParamDTO> list = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMgntDAO.retriveDevRuleServiceList");
		try {
			list = tbIotMgntDAO.retriveDevRuleServiceList(iotMgntDTO);
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
	public List<TbIotValRuleDTO> retriveDevRuleApiListQuery(IotMgntDTO iotMgntDTO) throws BizException {
		ComInfoDto temp = new ComInfoDto();

		List<TbIotValRuleDTO> list = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.retriveDevRuleApiList");
		try {
			list = tbIotMgntDAO.retriveDevRuleApiList(iotMgntDTO);
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
	public List<IotMgntSDevMDTO> retrieveIotMdlList(IotMgntDTO iotMgntDTO) throws BizException {
		List<IotMgntSDevMDTO> list = null;
		ComInfoDto temp = new ComInfoDto();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMgntDAO.retriveDevList");
		try {
			list = tbIotMgntDAO.retrieveIotMdlList(iotMgntDTO);
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

}
