package kr.co.scp.ccp.iotuser.svc.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.FileUtils;
import kr.co.abacus.common.util.StringUtils;
import kr.co.auiot.common.dto.common.FileBoardTypeDTO;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.*;
import kr.co.scp.ccp.common.brdFile.dao.BrdFileListDAO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileDTO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.common.brdFile.util.FileServiceUtil;
import kr.co.scp.ccp.iotCust.dto.TbIotCustSDTO;
import kr.co.scp.ccp.iotCust.svc.IotCustSVC;
import kr.co.scp.ccp.iotOrg.dao.IotOrgDAO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgOptDTO;
import kr.co.scp.ccp.iotOrg.svc.IotOrgSvc;
import kr.co.scp.ccp.iotuser.dao.IotUsrDAO;
import kr.co.scp.ccp.iotuser.dto.*;
import kr.co.scp.ccp.iotuser.svc.IotUsrSVC;
import kr.co.scp.ccp.login.dao.LoginDAO;
import kr.co.scp.ccp.login.dto.LoginDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;
import kr.co.scp.common.iotCmCd.svc.IotCmCdSVC;
import lombok.extern.slf4j.Slf4j;
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

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Primary
@Slf4j
@Service
public class IotUsrSVCImpl implements IotUsrSVC {

	static ObjectMapper oMapper = new ObjectMapper();

	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;

	@Autowired
	private IotUsrDAO iotUsrDAO;

	@Autowired
	private LoginDAO loginDao;

	@Autowired
	private IotOrgDAO iotOrgDAO;

	@Autowired
	private IotCmCdSVC iotCmCdSVC;

	@Autowired
	private IotCustSVC iotCustSVC;

	@Autowired
	private IotOrgSvc iotOrgSvc;

	@Autowired
	FileServiceUtil fileService;

	@Autowired
	private BrdFileListDAO brdFileListDAO;

	@Value("${file.upload-dir-usr}")
	private String FILE_UPLOAD_PATH;

	@Value("${file.max-size}")
	private int FILE_MAX_SIZE;

	@Override
	public HashMap<String, Object> retrieveIotUsr(TbIotUsrDTO tbIotUseDTO) throws BizException {
//		String orderCol = null;
//		if (!StringUtils.isEmpty(tbIotUseDTO.getOrderCol())) {
//			orderCol = this.checkCol(tbIotUseDTO.getOrderCol());
//			tbIotUseDTO.setOrderCol(orderCol);
//		}

		ComInfoDto temp = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		String custSeq = AuthUtils.getUser().getCustSeq();
		tbIotUseDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		tbIotUseDTO.setUsrSeq(AuthUtils.getUser().getUserSeq());
		tbIotUseDTO.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		String totalCnt = "";
//		int totalCnt = 0;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotUsrDAO.retrieveIotUsrCount");

//		List<TbIotCustSDTO> custList = null;
//		if (AuthUtils.getUser().getRoleCdId().equals("GN00000038") || AuthUtils.getUser().getRoleCdId().equals("GN00000033")
//				|| AuthUtils.getUser().getRoleCdId().equals("GN00000034")) {
//			TbIotCustSDTO tbIotCustSDTO = new TbIotCustSDTO();
//			tbIotCustSDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
//			custList = iotCustSVC.retrieveIotCustSelect(tbIotCustSDTO);
//			map.put("custList", custList);
//			if (null != tbIotUseDTO.getCustSeq() && tbIotUseDTO.getCustSeq().equals("all")) {
//				tbIotUseDTO.setCustSeq(null);
//			}
//		} else {
//			tbIotUseDTO.setCustSeq(custSeq);
//		}

		map.put("roleCdId", AuthUtils.getUser().getRoleCdId());
		tbIotUseDTO.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		tbIotUseDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		try {
			totalCnt = iotUsrDAO.retrieveIotUsrCount(tbIotUseDTO);
		} catch (MyBatisSystemException | BadSqlGrammarException | DataIntegrityViolationException | UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

//		PaginationDto pageDto = new PaginationDto();
//		pageDto.pageCalculate(new BigInteger(totalCnt), tbIotUseDTO.getDisplayRowCount(), tbIotUseDTO.getCurrentPage());
		PageDTO pageDto = new PageDTO();
		pageDto.pageCalculate(Integer.valueOf(totalCnt), tbIotUseDTO.getDisplayRowCount(), tbIotUseDTO.getCurrentPage());
		tbIotUseDTO.setCurrentPage(pageDto.getRowStart());
		tbIotUseDTO.setEndPage(pageDto.getRowEnd());


		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotUsrDAO.retrieveIotUsr");
		List<TbIotUsrDTO> returnDtoList = null;

		try {
			returnDtoList = iotUsrDAO.retrieveIotUsr(tbIotUseDTO);
		} catch (MyBatisSystemException | BadSqlGrammarException | DataIntegrityViolationException | UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
		map.put("dataList", returnDtoList);
		map.put("page", pageDto);
		return map;
	}

	@Override
	public TbIotUsrRDTO retrieveIotUsrBySeq(TbIotUsrDTO tbIotUseDTO) throws BizException {
		TbIotUsrRDTO returnDto = null;
		if (null == tbIotUseDTO.getUsrSeq()) {
			tbIotUseDTO.setUsrSeq(AuthUtils.getUser().getUserSeq());
		}
		tbIotUseDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotUsrDAO.retrieveIotUsrBySeq");
		try {
			returnDto = iotUsrDAO.retrieveIotUsrBySeq(tbIotUseDTO);
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
		return returnDto;
	}

	@Override
	public String retrieveDuplicationLgnId(TbIotUsrDTO tbIotUseDTO) throws BizException {
		String custSeq = AuthUtils.getUser().getCustSeq();
		tbIotUseDTO.setCustSeq(custSeq);
		String chkCnt = "";
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotUsrDAO.retrieveDuplicationLgnId");
		try {
			chkCnt = iotUsrDAO.retrieveDuplicationLgnId(tbIotUseDTO);
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
		return chkCnt;
	}

	@Override
	public void createIotUsr(TbIotUsrDTO tbIotUseDTO) throws BizException {
		userAuthCheck();
		StringBuilder sb = new StringBuilder();
		sb.append(tbIotUseDTO.getUsrLoginId())
				.append(tbIotUseDTO.getUsrPhoneNo().substring(tbIotUseDTO.getUsrPhoneNo().length() - 4, tbIotUseDTO.getUsrPhoneNo().length())).append("!!");
		tbIotUseDTO.setUsrPwd(EncryptUtils.encryptThisString(sb.toString()));
		// 고객사 등록할 때 custSeq를 set해서 주기 때문에 체크
		if (null == tbIotUseDTO.getCustSeq()) {
			tbIotUseDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		}

		tbIotUseDTO.setRegUsrId(AuthUtils.getUser().getUserId());
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotUsrDAO.createIotUsr");
		try {
			iotUsrDAO.createIotUsr(tbIotUseDTO);
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

	@Override
	public List<TbIotCmCdDTO> retrieveIotUsrRoleList() throws BizException {
		TbIotCmCdDTO cmcdDto = new TbIotCmCdDTO();
		// 사용자권한 공통코드값
		cmcdDto.setParentCdId("GN00000002");
		// 로그인한 사용자가 관리자일 경우
		if (AuthUtils.getUser().getRoleCdId().equals("GN00000038")) {
			cmcdDto.setLvl(1);
		} else if (AuthUtils.getUser().getRoleCdId().equals("GN00000033")) {
			cmcdDto.setLvl(2);
		} else if (AuthUtils.getUser().getRoleCdId().equals("GN00000034")) {
			cmcdDto.setLvl(3);
		} else if (AuthUtils.getUser().getRoleCdId().equals("GN00000035")) {
			cmcdDto.setLvl(4);
		} else {
			cmcdDto.setLvl(5);
		}
		List<TbIotCmCdDTO> resultList = null;

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCmCdSVC.retrieveIotByParentCmCd");
		try {
			resultList = iotCmCdSVC.retrieveIotByParentCmCd(cmcdDto);
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
		return resultList;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createIotUsrAll(HttpServletRequest request) throws BizException {
		userAuthCheck();
		ComInfoDto temp = null;
		String custSeq = AuthUtils.getUser().getCustSeq();
		String resultString;
		BigDecimal compareStd = new BigDecimal("0");

		// 사용자는 일괄등록하는 하나의 서비스에서만 파일을 업로드 한다.
		// 일괄 등록 후 업로드한 파일은 삭제한다.
		// 파일 생성 후 일괄등록 중에 에러가 발생하면 업로드한 파일을 지운다.
		String usrSeq = iotUsrDAO.retrieveIotUstSeq();
		fileService.saveFiles(request, FileBoardTypeDTO.USR, usrSeq, FILE_UPLOAD_PATH);
		TbIoTBrdFileDTO tbIoTBrdFileDTO = new TbIoTBrdFileDTO();
		tbIoTBrdFileDTO.setContentSeq(usrSeq);
		tbIoTBrdFileDTO.setContentType(FileBoardTypeDTO.USR);

		// 엑셀에서 속성으로 뽑아낼 항목들
		List<String> excelPropList = this.makeExcelPropsList();

		// 저장된 파일 리스트 조회
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
		String idChk = null;
		String fileDelPath = null;
		String fileDelNm = null;
		int updateNum = 0;
		boolean isFileExist = true;

		// 조회된 파일이 1개 이상일 때
		if (savedFileList.size() > 0) {

			List<TbIotUsrExcelDTO> usrDtoList = new ArrayList<TbIotUsrExcelDTO>();

			for (Iterator iterator = savedFileList.iterator(); iterator.hasNext();) {

				StringBuilder filePath = new StringBuilder();
				filePath.append(FILE_UPLOAD_PATH);
				TbIoTBrdFileDTO tmpBrdDto = (TbIoTBrdFileDTO) iterator.next();
				fileDelPath = FILE_UPLOAD_PATH + tmpBrdDto.getFilePath() + File.separator;
				fileDelNm = tmpBrdDto.getFileName();
				filePath.append(tmpBrdDto.getFilePath()).append(File.separator).append(tmpBrdDto.getFileName());
				String tempFilePath = filePath.toString().replaceAll("\\\\", "/");
				String filepathTemp = WebCommUtil.cleanString(tempFilePath);
				InputStream xslInputStream = null;
				try {
					xslInputStream = new FileInputStream(filepathTemp);
					try {
						usrDtoList = ExcelUtils.excelFileParsing(xslInputStream, excelPropList, TbIotUsrExcelDTO.class);
					} catch (Exception e) {
						throw new BizException(e, "excelUploadException");
					}

					if(usrDtoList.size() > 1000) {
						throw new BizException("excelUploadLimit");
					} else if (checkExcelSize(usrDtoList)<1) {
						throw new BizException("noDataToSave");
					}
					for (Iterator iterator2 = usrDtoList.iterator(); iterator2.hasNext();) {
						TbIotUsrExcelDTO tbIotUsrExcelDTO = (TbIotUsrExcelDTO) iterator2.next();
						TbIotUsrExcel2DTO checkDto = trimObj(tbIotUsrExcelDTO);
						if (null != checkDto) {
							localValidating(checkDto);

							int resultNum = 0;
							// 전화번호 중복확인
							TbIotUsrDTO tbIotUsrDTO = new TbIotUsrDTO();
							tbIotUsrDTO.setUsrPhoneNo(checkDto.getUsrPhoneNo().trim());
							tbIotUsrDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
							List<TbIotUsrDTO> outUsrList = this.retrieveUsrPhoneDuplChk(tbIotUsrDTO);
							if (outUsrList.size() > 0) {
								throw new BizException("duplicationPhoneNo");
							} else if (outUsrList.size() < 1) {
								tbIotUsrExcelDTO.setUsrPhoneNo(checkDto.getUsrPhoneNo());
							}
							// 조직명 확인
							TbIotOrgDTO tbIotOrgDTO = new TbIotOrgDTO();
							tbIotOrgDTO.setCustSeq(custSeq);
							tbIotOrgDTO.setOrgNm(tbIotUsrExcelDTO.getOrgNm().trim());
							try {
								resultString = iotOrgSvc.retrieveIotOrgNm(tbIotOrgDTO);
							} catch(NullPointerException e) {
								throw new BizException("nonExistOrgNm");
							}
							if ( null == resultString) {
								throw new BizException("nonExistOrgNm");
							} else {
								tbIotUsrExcelDTO.setOrgSeq(String.valueOf(resultNum));
							}

							// 사용자 권한명 확인
							String roleCdId = iotCmCdSVC.retrieveCdIdByCdNm(tbIotUsrExcelDTO.getRoleCdNm().trim());
							if (null == roleCdId) {
								throw new BizException("nonExistRoleNm");
							} else {
								tbIotUsrExcelDTO.setRoleCdId(roleCdId);
							}

							// 아이디 중복 확인
							TbIotUsrDTO idCheckDto = new TbIotUsrDTO();
							idCheckDto.setUsrLoginId(tbIotUsrExcelDTO.getUsrLoginId().trim());
							idChk = this.retrieveDuplicationLgnId(idCheckDto);
							BigDecimal idChkNumber = new BigDecimal(idChk);
							int chkId = idChkNumber.compareTo(new BigDecimal("0"));
							if (chkId != 0) {
								throw new BizException("duplicationLgnId");
							} else {
								this.createIotUsr(this.convertCDTO(tbIotUsrExcelDTO));
								updateNum++;
							}
						}

						if(!iterator2.hasNext()) {
							log.info("total upload user number : " + updateNum);
						}
					}
					ReqContextComponent.getComInfoDto().setMsg("User Upload Number : " + updateNum);

				} catch (FileNotFoundException e) {
					isFileExist = false;
					throw new BizException("FileNotFoundException");
				} finally {
					if (xslInputStream != null) {
						try {
							xslInputStream.close();
						} catch (IOException e) {
						}
					}
					if (isFileExist) {
						FileUtils.deleteFile(request, fileDelPath, fileDelNm);
						this.deleteFile(usrSeq);
					}
				}
			}
		}
	}

	@Override
	public void updateIotUsr(TbIotUsrDTO tbIotUsrDTO) throws BizException {
		if (!tbIotUsrDTO.getUsrSeq().equals(AuthUtils.getUser().getUserSeq())){
			userAuthCheck();
		}
		String modUsrId = AuthUtils.getUser().getUserId();
		tbIotUsrDTO.setModUsrId(modUsrId);
		boolean isPwChg = false;
		String chgUsrId = null;
		String chgCustId = null;
		ComInfoDto temp = null;

		if (null != tbIotUsrDTO.getUsrPwd()) {
			// 비밀번호 정책 맞는지 확인
			if (!pwdReg(tbIotUsrDTO.getUsrPwd())) {
				throw new BizException("notValidPwd");
			}
			// 본인이 비밀번호 변경했을 때
			if(AuthUtils.getUser().getUserSeq().equals(tbIotUsrDTO.getUsrSeq())) {

				chgUsrId = AuthUtils.getUser().getUserId();
				chgCustId = AuthUtils.getUser().getCustId();

				LoginDTO loginDTO = new LoginDTO();
				loginDTO.setUsrLoginId(AuthUtils.getUser().getUserId());
				loginDTO.setCustLoginId(AuthUtils.getUser().getCustId());
				List<String> histArr = null;
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"loginDao.retrieveUsrPwdCur");
				try {
					histArr = loginDao.retrieveUsrPwdCur(loginDTO);
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

				for (Iterator iterator = histArr.iterator(); iterator.hasNext();) {
					String pwd = (String) iterator.next();
					if (pwd.equals(EncryptUtils.encryptThisString(tbIotUsrDTO.getUsrPwd()))) {
						throw new BizException("pw_hist_error");
					}
				}
				isPwChg = true;
				tbIotUsrDTO.setUsrPwd(EncryptUtils.encryptThisString(tbIotUsrDTO.getUsrPwd()));
			} else {
				// 관리자가 사용자 비밀번호를 변경하는 경우인데
				// 현재 정책으로는 관리자는 사용자가 변경한 비밀번호 변경이력을 알 수 없기 때문에
				// 비밀번호 수정시 최근 3건과 일치하지 않아야 한다는 제약조건이 적용되지 않는다.
				// 때문에 밑에 주석처리 된 내용도 적용 보류
//				isPwChg = true;
//				chgUsrId = tbIotUsrDTO.getUsrLoginId();
				tbIotUsrDTO.setUsrPwd(EncryptUtils.encryptThisString(tbIotUsrDTO.getUsrPwd()));
//
//				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//						"loginDao.retrieveUsrPwdCur");
//				try {
//					chgCustId = iotUsrDAO.retrieveIotUsrCustInfo(tbIotUsrDTO.getUsrSeq());
//				} catch (MyBatisSystemException e) {
//					OmsUtils.expInnerOms(temp, e);
//					throw e;
//				} catch (BadSqlGrammarException e) {
//					OmsUtils.expInnerOms(temp, e);
//					throw e;
//				} catch (DataIntegrityViolationException e) {
//					OmsUtils.expInnerOms(temp, e);
//					throw e;
//				} catch (UncategorizedSQLException e) {
//					OmsUtils.expInnerOms(temp, e);
//					throw e;
//				} finally {
//					OmsUtils.endInnerOms(temp);
//				}
			}
		}

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotUsrDAO.updateIotUsr");
		try {
			iotUsrDAO.updateIotUsr(tbIotUsrDTO);
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

		if (isPwChg) {
			LoginDTO chgDTO = new LoginDTO();
			chgDTO.setUsrLoginId(AuthUtils.getUser().getUserId());
			chgDTO.setCustLoginId(AuthUtils.getUser().getCustId());
			chgDTO.setUsrPwd(tbIotUsrDTO.getUsrPwd());
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"loginDao.updateIotUsrPwdHist");
			try {
				loginDao.updateIotUsrPwdHist(chgDTO);
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

	}

	@Override
	public void updateIotUsrInfo(TbIotUsrDTO tbIotUseDTO) throws BizException {
		userAuthCheck();
		String modUsrId = AuthUtils.getUser().getUserId();
		ComInfoDto temp = null;
		tbIotUseDTO.setModUsrId(modUsrId);
		String[] usrSeq = tbIotUseDTO.getUsrSeqArr();

		// 패스워드 일괄 초기화
		if (tbIotUseDTO.getArrType().equals("pwd")) {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < usrSeq.length; i++) {

				tbIotUseDTO.setUsrSeq(usrSeq[i]);
				TbIotUsrDTO inputDto = null;
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotUsrDAO.retrieveIotUsrBySeqInner");
				try {
					inputDto = iotUsrDAO.retrieveIotUsrBySeqInner(tbIotUseDTO);
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
				sb.append(inputDto.getUsrLoginId())
						.append(inputDto.getUsrPhoneNo().substring(7, inputDto.getUsrPhoneNo().length())).append("!!");
				inputDto.setUsrPwd(EncryptUtils.encryptThisString(sb.toString()));
				inputDto.setModUsrId(modUsrId);
				inputDto.setArrType(tbIotUseDTO.getArrType());
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotUsrDAO.updateIotUsrInfo");
				try {
					iotUsrDAO.updateIotUsrInfo(inputDto);
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
		}
		// 휴면 일괄 해제
		if (tbIotUseDTO.getArrType().equals("slp")) {
			ReqContextComponent.getComInfoDto().setFuncId("FN01290");
			for (int i = 0; i < usrSeq.length; i++) {
				tbIotUseDTO.setUsrSeq(usrSeq[i]);
				TbIotUsrDTO inputDto = null;
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotUsrDAO.retrieveIotUsrBySeqInner");
				try {
					inputDto = iotUsrDAO.retrieveIotUsrBySeqInner(tbIotUseDTO);
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
				inputDto.setUsrSlpYn("N");
				inputDto.setModUsrId(modUsrId);
				inputDto.setArrType(tbIotUseDTO.getArrType());
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotUsrDAO.updateIotUsrInfo");
				try {
					iotUsrDAO.updateIotUsrInfo(inputDto);
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
		}
		// 잠김상태 일괄 해제
		if (tbIotUseDTO.getArrType().equals("lck")) {
			ReqContextComponent.getComInfoDto().setFuncId("FN01291");
			for (int i = 0; i < usrSeq.length; i++) {
				tbIotUseDTO.setUsrSeq(usrSeq[i]);
				TbIotUsrDTO inputDto = null;
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotUsrDAO.retrieveIotUsrBySeqInner");
				try {
					inputDto = iotUsrDAO.retrieveIotUsrBySeqInner(tbIotUseDTO);
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

				inputDto.setUsrLckYn("N");
				inputDto.setModUsrId(modUsrId);
				inputDto.setArrType(tbIotUseDTO.getArrType());
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotUsrDAO.updateIotUsrInfo");
				try {
					iotUsrDAO.updateIotUsrInfo(inputDto);
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
		}

		// 재가입
		if (tbIotUseDTO.getArrType().equals("rejoin")) {
			ReqContextComponent.getComInfoDto().setFuncId("FN01292");
			for (int i = 0; i < usrSeq.length; i++) {
				tbIotUseDTO.setUsrSeq(usrSeq[i]);
				TbIotUsrDTO inputDto = null;
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotUsrDAO.retrieveIotUsrBySeqInner");
				try {
					inputDto = iotUsrDAO.retrieveIotUsrBySeqInner(tbIotUseDTO);
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
				inputDto.setUseYn("Y");
				inputDto.setModUsrId(modUsrId);
				inputDto.setArrType(tbIotUseDTO.getArrType());
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotUsrDAO.updateIotUsrInfo");
				try {
					iotUsrDAO.updateIotUsrInfo(inputDto);
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
		}

		// 탈퇴
		if (tbIotUseDTO.getArrType().equals("unregi")) {
			// 권한에 따른 사용자 해지 기능 제거
//			String[] adminRoleArr = { "GN00000035", "GN00000036"};
//			if (!Arrays.asList(adminRoleArr).contains( AuthUtils.getUser().getRoleCdId())) {
//				throw new BizException("userNotFound");
//			}
			ReqContextComponent.getComInfoDto().setFuncId("FN01293");
			for (int i = 0; i < usrSeq.length; i++) {
				tbIotUseDTO.setUsrSeq(usrSeq[i]);
				TbIotUsrDTO inputDto = null;
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotUsrDAO.retrieveIotUsrBySeqInner");
				try {
					inputDto = iotUsrDAO.retrieveIotUsrBySeqInner(tbIotUseDTO);
				} catch (MyBatisSystemException e) {
					OmsUtils.expInnerOms(temp, e);
					throw e;
				} catch (BadSqlGrammarException e) {
					OmsUtils.expInnerOms(temp, e);
					throw e;
				} finally {
					OmsUtils.endInnerOms(temp);
				}
				inputDto.setUseYn("N");
				inputDto.setModUsrId(modUsrId);
				inputDto.setArrType(tbIotUseDTO.getArrType());
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"iotUsrDAO.updateIotUsrInfo");
				try {
					iotUsrDAO.updateIotUsrInfo(inputDto);
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
		}
	}

	@SuppressWarnings("rawtypes")
	public XSSFWorkbook excelCreate() throws BizException {
		TbIotOrgDTO tbIotOrgDto = new TbIotOrgDTO();
		XSSFWorkbook wb = new XSSFWorkbook();

		tbIotOrgDto.setCustSeq(AuthUtils.getUser().getCustSeq());
		List<TbIotOrgOptDTO> orgList = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotOrgDAO.retrieveIotOrgByUsr");
		try {
			orgList = iotOrgDAO.retrieveIotOrgByUsr(tbIotOrgDto);
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
		StringBuilder sb = new StringBuilder();
		sb.append(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "orgNm"));
		sb.append(": \n");

		for (Iterator iterator = orgList.iterator(); iterator.hasNext();) {
			TbIotOrgOptDTO tbIotOrgDTO = (TbIotOrgOptDTO) iterator.next();
			sb.append(tbIotOrgDTO.getOrgNm());
			if (iterator.hasNext()) {
				sb.append("\n");
			}
		}

		Map<String, String> title = new LinkedHashMap<String, String>();
		TbIotUsrCDTO usrDto = new TbIotUsrCDTO();
		List<TbIotUsrCDTO> tmpList = new ArrayList<TbIotUsrCDTO>();
		usrDto.setOrgNm(sb.toString());
		usrDto.setUsrLoginId("exampleUser");
		usrDto.setSmsRcvNo(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "telFormatTitle"));
		usrDto.setUsrEmail("test@test.com");
		usrDto.setUsrPhoneNo(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "telFormatTitle"));
		usrDto.setUsrNm(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "usrNm"));
		usrDto.setRoleCdNm("");
		tmpList.add(usrDto);

		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "id"), "usrLoginId");
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "usrNm"), "usrNm");
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "usrPhoneNo"), "usrPhoneNo");
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "orgNm"), "orgNm");
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "roleCdNm"), "roleCdNm");
		title.put(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "usrEmail"), "usrEmail");
		ExcelUtils.createExcelFile(wb, tmpList, title);

		return wb;
	}

	@Override
	public List<String> retrieveIotUsrAdminRole(TbIotUsrDTO tbIotUseDTO) throws BizException {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotUsrDAO.retrieveIotUsrAdminRole");
		List<String> usrNmList = null;
		try {
			usrNmList = iotUsrDAO.retrieveIotUsrAdminRole(tbIotUseDTO);
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

		return usrNmList;
	}

	@Override
	public int retrieveIotUsrPwChk(TbIotUsrDTO tbIotUseDTO) throws BizException {
		int returnNum = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotUsrDAO.retrieveIotUsrById");
		tbIotUseDTO.setUsrPwd(EncryptUtils.encryptThisString(tbIotUseDTO.getUsrPwd()));
		tbIotUseDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		try {
			TbIotUsrDTO returnDto = iotUsrDAO.retrieveIotUsrById(tbIotUseDTO);
			if (null != returnDto) {
				if (returnDto.getUsrPwd().equals(tbIotUseDTO.getUsrPwd())) {
					returnNum = 1;
				}
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
		return returnNum;
	}

	public List<TbIotUsrDTO> retrieveUsrListByCust(String custSeq) throws BizException {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotUsrDAO.retrieveUsrListByCust");
		List<TbIotUsrDTO> returnDto = null;
		try {
			returnDto = iotUsrDAO.retrieveUsrListByCust(custSeq);
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
		return returnDto;
	}

	@Override
	public List<TbIotUsrDTO> retrieveUsrPhoneDuplChk(TbIotUsrDTO tbIotUsrDTO) throws BizException{
		tbIotUsrDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotUsrDAO.retrieveUsrPhoneDuplChk");
		List<TbIotUsrDTO> checkList = null;
		try {
			checkList = iotUsrDAO.retrieveUsrPhoneDuplChk(tbIotUsrDTO);
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
		return checkList;
	}


	private List<String> makeExcelPropsList() {
		List<String> excelPropList = new ArrayList<String>();
		excelPropList.add("usrLoginId");
		excelPropList.add("usrNm");
		excelPropList.add("usrPhoneNo");
		excelPropList.add("orgNm");
		excelPropList.add("roleCdNm");
		excelPropList.add("usrEmail");
		return excelPropList;
	}

	private TbIotUsrDTO convertCDTO(TbIotUsrExcelDTO inputDto) {
		TbIotUsrDTO outputDto = new TbIotUsrDTO();
		outputDto.setUsrLoginId(inputDto.getUsrLoginId());
		outputDto.setUsrNm(inputDto.getUsrNm());
		outputDto.setUsrPhoneNo(inputDto.getUsrPhoneNo());
		outputDto.setSmsRcvNo(inputDto.getUsrPhoneNo());
		outputDto.setOrgSeq(inputDto.getOrgSeq());
		outputDto.setUsrEmail(inputDto.getUsrEmail());
		outputDto.setUsrLoginId(inputDto.getUsrLoginId());
		outputDto.setRoleCdNm(inputDto.getRoleCdNm());
		outputDto.setRoleCdId(inputDto.getRoleCdId());
		return outputDto;
	}

	private void deleteFile(String usrSeq) throws BizException {
		TbIoTBrdFileListDTO dto = new TbIoTBrdFileListDTO();
		dto.setContentSeq(usrSeq);
		dto.setContentType(FileBoardTypeDTO.USR);

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"brdFileListDAO.deleteTbIoTBrdFileList");
		try {
			brdFileListDAO.deleteTbIoTBrdFileList(dto);
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

/*	private JSONObject getJsonStringFromMap(Map<String, String> map) {
		JSONObject jsonObject = new JSONObject();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			jsonObject.put(key, value);
		}

		return jsonObject;
	}
*/

	private int checkExcelSize(List<TbIotUsrExcelDTO> list) {
		int totalCount = list.size();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TbIotUsrExcelDTO tbIotUsrExcelDTO = (TbIotUsrExcelDTO) iterator.next();
			Map<String, String> propsMap = oMapper.convertValue(tbIotUsrExcelDTO, Map.class);
			String tmpKey;
			boolean objChk = true;
			Object[] keys =  propsMap.keySet().toArray();
			int keyLen = keys.length;
			for (int i = 0; i < keyLen; i++) {
				try {
					tmpKey = ((String)keys[i]).trim();
					if(StringUtils.isEmpty(propsMap.get(tmpKey))) {
						objChk = false;
					} else {
						objChk = true;
						break;
					}
				} catch (NullPointerException e) {
					objChk = false;
				}
				if (keyLen-1 == i && !objChk ) {
					totalCount--;
				}
			}
		}
		return totalCount;
	}

	private TbIotUsrExcel2DTO trimObj(TbIotUsrExcelDTO dto) {
		Map<String, String> propsMap = oMapper.convertValue(dto, Map.class);
		String value;
		boolean objChk = true;
		for (String key : propsMap.keySet()) {
			try {
				value = propsMap.get(key).trim();
				if(StringUtils.isEmpty(value)) {
					objChk = false;
				} else {
					objChk = true;
					break;
				}
			} catch (NullPointerException e) {
				objChk = false;
			}

		}
		TbIotUsrExcel2DTO outDto = new TbIotUsrExcel2DTO();
		outDto.setOrgNm(dto.getOrgNm().trim());
		outDto.setRoleCdNm(dto.getRoleCdNm().trim());
		outDto.setUsrEmail(dto.getUsrEmail());
		outDto.setUsrLoginId(dto.getUsrLoginId());
		outDto.setUsrNm(dto.getUsrNm());
		outDto.setUsrPhoneNo(dto.getUsrPhoneNo().trim());
		if (!objChk) {
			return null;
		} else {
			return outDto;
		}
	}

	public boolean localValidating(TbIotUsrExcel2DTO dto) {
		String[] notNullGrp = { "usrLoginId", "usrNm", "usrPhoneNo", "roleCdNm", "orgNm" };
		String[] noSpcChar = { "usrLoginId", "usrNm", "roleCdNm", "orgNm" };
		String[] noWhiteSpaceGrp = { "usrLoginId", "usrNm", "usrPhoneNo", "roleCdNm", "orgNm" };
		String[] numberGrp = { "usrPhoneNo"};
		String[] lengthChkGrp = { "usrLoginId", "usrNm", "usrPhoneNo", "orgNm", "roleCdNm" };
		String[] emailGroup = { "usrEmail" };
		boolean statuschk = true;
		String maxLen = null;
		String minLen = null;
		String value = null;
		String message = null;
		int lengthChkNum = 0;

		List<String> exceptionList = new ArrayList<String>();
		Map<String, String> propsMap = oMapper.convertValue(dto, Map.class);
		for (String key : propsMap.keySet()) {
			message = fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, key);
			if ( null != propsMap.get(key)) {
				value = propsMap.get(key).trim();
			} else {
				value = "";
			}

			for (int i = 0; i < notNullGrp.length; i++) {
				if (notNullGrp[i].equals(key)) {
					if (isNullCheck(value)) {
						exceptionList.add(message);
						statuschk = false;
						throw new BizException("excel_empty_value", exceptionList);
					}
				}
			}

			if(key.equals("usrLoginId")) {
				if(Pattern.matches("^[ㄱ-ㅎ가-힣]*$", value)) {
					exceptionList.add(message);
					throw new BizException("accept",exceptionList);
				}
			}

			for (int i = 0; i < noSpcChar.length; i++) {
				if (!noSpcChar[i].equals(key)) {
					if (!isContainChecialChar(value)) {
					exceptionList.add(message);
					exceptionList.add(value);
					statuschk = false;
					throw new BizException("special_char", exceptionList);
					}
				}
			}
			for (int i = 0; i < noWhiteSpaceGrp.length; i++) {
				if (noWhiteSpaceGrp[i].equals(key)) {
					if (isContainWhiteSpace(value)) {
						exceptionList.add(message);
						statuschk = false;
						throw new BizException("white_space", exceptionList);
					}
				}
			}
			for (int i = 0; i < numberGrp.length; i++) {
				if (numberGrp[i].equals(key)) {
					if (!isNumber(value)) {
						exceptionList.add(message);
						statuschk = false;
						throw new BizException("invailid_tel_num", exceptionList);
					}
				}
			}
			for (int i = 0; i < lengthChkGrp.length; i++) {
				if (lengthChkGrp[i].equals(key)) {
					maxLen = "";
					minLen = "";
					if (key.equals("usrLoginId")) {
						maxLen = "20";
						minLen = "2";
						lengthChkNum = lengthChk(value, 20, 2);
					}
					if (key.equals("usrPhoneNo")) {
						maxLen = "11";
						minLen = "10";
						lengthChkNum = lengthChk(value, 11, 10);
					}
					if (key.equals("usrNm") || key.equals("orgNm")) {
						maxLen = "33";
						minLen = "2";
						lengthChkNum = lengthChk(value, 33, 2);
					}
					if (key.equals("roleCdNm")) {
						maxLen = "16";
						minLen = "1";
						lengthChkNum = lengthChk(value, 16, 1);
					}

					if (lengthChkNum == 1) {
						exceptionList.add(message);
						exceptionList.add(minLen);
						throw new BizException("too_short", exceptionList);
					} else if (lengthChkNum == 2) {
						exceptionList.add(message);
						exceptionList.add(maxLen);
						throw new BizException("too_long", exceptionList);
					}

				}
			}
			for (int i = 0; i < emailGroup.length; i++) {
				if (emailGroup[i].equals(key)) {
					if (!StringUtils.isEmpty(value)) {
						if (!isEmailPattern(value)) {
							exceptionList.add(message);
							statuschk = false;
							throw new BizException("isNotEmailPattern", exceptionList);
						}
					}
				}
			}
		}
		return statuschk;
	}

	public boolean isNullCheck(String param) {
		return StringUtils.isEmpty(param);
	}

	public static boolean isContainChecialChar(String param) {
		Pattern pattern = Pattern.compile("^[ㄱ-ㅎ가-힣a-zA-Z0-9_@.-]*$");
		Matcher match = pattern.matcher(param);
		return match.find();
	}

	public boolean isContainWhiteSpace(String param) {
		return param.indexOf(" ") > 0 ? true : false;
	}

	public boolean isEmailPattern(String param) {
		Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z]+\\.[a-z.]{2,8}$");
		Matcher match = pattern.matcher(param);
		return match.find();
	}

	public boolean isNumber(String param) {
		String regExp = "^[0-9]{10,11}$";
		return param.matches(regExp);
	}

	// 0: 정상, 1: 값 짧음, 2: 값 길이 오버
	public int lengthChk(String param, int max, int min) {
		return param.length() > max ? 2 : param.length() < min? 1 : 0 ;
	}

	public static String checkCol(String colNm) {
		// variables for converting camelCase to underbar score
		String regex = "([a-z])([A-Z]+)";
		String replacement = "$1_$2";

		String[] cdGrp = { "releCdNm" };
		String[] term = { "termsAgreeDttm", "termsAgreeYn" };
		String[] usrmGrp = { "regUsrId" };
		String largestGrp = new StringBuilder().append("USR.").append(colNm).toString()
				.replaceAll(regex, replacement).toUpperCase();


		List<String> tmpArr = null;
		tmpArr = Arrays.asList(cdGrp);
		if (tmpArr.contains(colNm)) {
			colNm = new StringBuilder().append("CD.").append(colNm).toString();
			return colNm;
		};
		tmpArr = Arrays.asList(term);
		if (tmpArr.contains(colNm)) {
			// 원래 칼럼명과 Alias로 명명한 칼럼명이 다를 경우
			if (colNm.equals("termsAgreeDttm")) {
				colNm = "TERMS_AGR_DTTM";
			} else {
				colNm = "TERMS_AGR_YN";
			}
			colNm = new StringBuilder().append("TERM.").append(colNm).toString()
					.replaceAll(regex, replacement).toUpperCase();
			return colNm;
		};
		tmpArr = Arrays.asList(usrmGrp);
		if (tmpArr.contains(colNm)) {
			colNm = new StringBuilder().append("USRM.").append(colNm).toString()
					.replaceAll(regex, replacement).toUpperCase();
			return colNm;
		};

		return largestGrp;
	}

	public void userAuthCheck() {
		String[] adminRoleArr = { "GN00000035", "GN00000036", "GN00000033", "GN00000034" };
		if (!Arrays.asList(adminRoleArr).contains( AuthUtils.getUser().getRoleCdId())) {
			throw new BizException("userNotFound");
		}
	}

	public boolean pwdReg(String newPwd) {
		Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}|" // 특문,문,숫 3개 포함 + 8자리이상
				+ "(?=.*\\d{3,})(?=.*[$@$!%*#?&])[\\d$@$!%*#?&]{10,}|" // 숫,특문 포함 8자리 이상
				+ "(?=.*[A-Za-z])(?=.*[$@$!%*#?&])[A-Za-z$@$!%*#?&]{10,}|" // 문,특문 포함 8자리 이상
				+ "(?=.*[A-Za-z])(?=.*\\d{3,})[A-Za-z\\d]{10,}$"); // 문,숫 포함 8자리 이상
		Matcher m = p.matcher(newPwd);
		if (!m.matches()) {
			return false;
		} else {
			return true;
		}
	}

}
