package kr.co.scp.ccp.iotCust.svc.impl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.DecimalFormatUtils;
import kr.co.abacus.common.util.FileUtils;
import kr.co.auiot.common.dto.common.FileBoardTypeDTO;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.EncryptUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.ccp.common.brdFile.dao.BrdFileListDAO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileDTO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.common.brdFile.util.FileServiceUtil;
import kr.co.scp.ccp.iotCust.dao.IotCustDAO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustCombineDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustSDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustUDTO;
import kr.co.scp.ccp.iotCust.svc.IotCustSVC;
import kr.co.scp.ccp.iotEDev.dao.IotEDevDAO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgDTO;
import kr.co.scp.ccp.iotOrg.svc.IotOrgSvc;
import kr.co.scp.ccp.iotuser.dto.TbIotUsrDTO;
import kr.co.scp.ccp.iotuser.svc.IotUsrSVC;
import kr.co.scp.common.iotCmCd.dao.IotCmCdDAO;
import kr.co.scp.common.iotSvc.dto.TbIotSvcDto;
import kr.co.scp.common.iotSvc.svc.TbIotSvcSvc;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Primary
@Slf4j
@Service
public class IotCustSVCImpl implements IotCustSVC {

	@Autowired
	private IotCustDAO iotCustDAO;

	@Autowired
	private BrdFileListDAO brdFileListDAO;

	@Autowired
	private FileServiceUtil fileServiceUtil;

	@Autowired
	IotCmCdDAO iotCmCdDao;

	@Autowired
	IotEDevDAO eDevDao;

	@Autowired
	IotUsrSVC iotUsrSvc;

	@Autowired
	IotOrgSvc iotOrgSvc;

	@Autowired
	TbIotSvcSvc iotSvcSvc;

	@Value("${file.upload-dir-cust}")
	private String FILE_UPLOAD_PATH;

	@Value("${file.max-size}")
	private int FILE_MAX_SIZE;

	@Override
	public HashMap<String, Object> retrieveIotCust(TbIotCustDTO tbIotCustDTO) throws BizException {
		ComInfoDto temp = null;
		String custSeq = AuthUtils.getUser().getCustSeq();
		String svcCd = AuthUtils.getUser().getSvcCd();
		tbIotCustDTO.setCustSeq(custSeq);
		tbIotCustDTO.setSvcCd(svcCd);
		tbIotCustDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
//		PaginationDto pageDto = new PaginationDto();
		String totalCnt = "";
//		int totalCnt = 0;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCustDAO.retrieveIotCustCount");
		try {
			totalCnt = iotCustDAO.retrieveIotCustCount(tbIotCustDTO);
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
		PageDTO pageDto = new PageDTO();
		pageDto.pageCalculate(Integer.valueOf(totalCnt), tbIotCustDTO.getDisplayRowCount(), tbIotCustDTO.getCurrentPage());
//		pageDto.pageCalculate(new BigInteger(totalCnt), tbIotCustDTO.getDisplayRowCount(), tbIotCustDTO.getCurrentPage());
		tbIotCustDTO.setCurrentPage(pageDto.getRowStart());
		tbIotCustDTO.setEndPage(pageDto.getRowEnd());

		List<TbIotCustDTO> list = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCustDAO.retrieveIotCust");
		try {
			list = iotCustDAO.retrieveIotCust(tbIotCustDTO);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		}  catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

		tbIotCustDTO.setCurrentPage(pageDto.getRowStart());
		returnMap.put("dataList", list);
		returnMap.put("page", pageDto);
		return returnMap;
	}

	@Override
	public HashMap<String, Object> retrieveIotCustAll(TbIotCustDTO tbIotCustDTO) throws BizException {
		ComInfoDto temp = null;
		tbIotCustDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		String totalCnt = "";
		List<TbIotCustDTO> list = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCustDAO.retrieveIotCust");
		try {
//			list = iotCustDAO.retrieveIotCustAll(tbIotCustDTO);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		}  catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
		returnMap.put("data", list);
		return returnMap;
	}

	@Override
	public TbIotEDevDTO retrieveCustInfoByCtn(TbIotCustDTO tbIotCustDTO) throws BizException {
		TbIotEDevDTO list = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"eDevDao.retrieveCustInfoByCtn");
		try {
			list = eDevDao.retrieveCustInfoByCtn(tbIotCustDTO);
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

	@SuppressWarnings("rawtypes")
	@Override
	public HashMap<String, Object> retrieveIotCustBySeq(TbIotCustUDTO tbIotCustDTO) throws BizException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		TbIotCustUDTO returnDto = null;
		ComInfoDto temp = null;
		tbIotCustDTO.setLangSet(ReqContextComponent.getComInfoDto().getXlang());

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCustDAO.retrieveIotCustBySeq");
		try {
			returnDto = iotCustDAO.retrieveIotCustBySeq(tbIotCustDTO);
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
		// 관리자명 가져오기
		TbIotUsrDTO tbIotUsrDTO = new TbIotUsrDTO();
		tbIotUsrDTO.setCustSeq(returnDto.getCustSeq());
		tbIotUsrDTO.setRoleCdId("GN00000035");
		List<String> usrNmList = iotUsrSvc.retrieveIotUsrAdminRole(tbIotUsrDTO);
		StringBuilder nmSb = new StringBuilder();
		for (Iterator iterator = usrNmList.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			nmSb.append(string);
			if (iterator.hasNext()) {
				nmSb.append(", ");
			}
		}
		returnDto.setUsrNm(nmSb.toString());
		map.put("data", returnDto);

		TbIoTBrdFileDTO fileDto = new TbIoTBrdFileDTO();
		fileDto.setContentSeq(returnDto.getCustSeq());
		fileDto.setContentType(FileBoardTypeDTO.CUST);
		List<TbIoTBrdFileDTO> returnFile = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCustDAO.retrieveIotCustBySeq");
		try {
			returnFile = brdFileListDAO.retrieveTbIoTBrdFileList(fileDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		}  catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

		if (returnFile.size() > 0) {
			for (Iterator iterator = returnFile.iterator(); iterator.hasNext();) {
				TbIoTBrdFileDTO tbIoTBrdFileListDTO = (TbIoTBrdFileDTO) iterator.next();
				double fileSize = tbIoTBrdFileListDTO.getFileSize();
				String strFileSize = DecimalFormatUtils.convertToKByte("#.##", fileSize);
				tbIoTBrdFileListDTO.setFileSizeLabel(strFileSize);
			}
			map.put("file", returnFile);
		}

		return map;
	}

	@Override
	@Transactional
	public void createIoTCust(TbIotCustCombineDTO tbIotCustCDTO, HttpServletRequest request) throws BizException {
		userAuthCheck();
		ComInfoDto temp = null;
		String loginId = AuthUtils.getUser().getUserId();
		tbIotCustCDTO.setRegUsrId(loginId);

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCustDAO.createIoTCust");
		try {
			iotCustDAO.createIoTCust(tbIotCustCDTO);
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
		// 조직 등록
		TbIotOrgDTO orgDto = new TbIotOrgDTO();
		orgDto.setCustSeq(String.valueOf(tbIotCustCDTO.getCustSeq()));
		orgDto.setOrgNm(tbIotCustCDTO.getCustNm());
		orgDto.setOrgLvl("1");
		orgDto.setUseYn("Y");
		orgDto.setOrgPath("/" + tbIotCustCDTO.getCustNm());
		orgDto.setUpOrgSeq("0");
		orgDto.setRegUsrId(loginId);
		iotOrgSvc.createIotOrg(orgDto);

		// 유저 등록
		TbIotUsrDTO usrDto = new TbIotUsrDTO();
		StringBuilder sb = new StringBuilder();
		sb.append(tbIotCustCDTO.getUsrLoginId())
				.append(tbIotCustCDTO.getUsrPhoneNo().substring(tbIotCustCDTO.getUsrPhoneNo().length() - 4
						, tbIotCustCDTO.getUsrPhoneNo().length()))
				.append("!!");

		usrDto.setCustSeq(String.valueOf(tbIotCustCDTO.getCustSeq()));
		usrDto.setUsrLoginId(tbIotCustCDTO.getUsrLoginId());
		usrDto.setUsrPwd(EncryptUtils.encryptThisString(sb.toString()));
		usrDto.setUsrNm(tbIotCustCDTO.getUsrNm());
		usrDto.setSmsRcvNo(tbIotCustCDTO.getSmsRcvNo());
		usrDto.setRoleCdId(tbIotCustCDTO.getRoleCdId());
		usrDto.setRoleCdNm(tbIotCustCDTO.getRoleCdNm());
		usrDto.setUsrEmail(tbIotCustCDTO.getUsrEmail());
		usrDto.setOrgSeq(orgDto.getOrgSeq());
		usrDto.setUsrPhoneNo(tbIotCustCDTO.getUsrPhoneNo());
		usrDto.setRegUsrId(loginId);
		iotUsrSvc.createIotUsr(usrDto);

		ArrayList<String[]> fileList = new ArrayList<String[]>();
		// 파일업로드
		fileList = FileUtils.uploadMutilpleFile(request, FILE_UPLOAD_PATH, FILE_MAX_SIZE);
		for (String[] arrStr : fileList) {
			// 파일순서 설정
			TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
			for (String str : arrStr) {
				tbIoTBrdFileListDTO.setContentSeq(tbIotCustCDTO.getCustSeq());
				// 고객사 관리 게시판 번호 (contentType ): 5번
				tbIoTBrdFileListDTO.setContentType(FileBoardTypeDTO.CUST);
				tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(FILE_UPLOAD_PATH, ""));
				tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
				tbIoTBrdFileListDTO.setFileName(arrStr[2]);
				tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
				tbIoTBrdFileListDTO.setRegUsrId(loginId);
			}
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"brdFileListDAO.insertTbIoTBrdFileListDTO");
			try {
				brdFileListDAO.insertTbIoTBrdFileListDTO(tbIoTBrdFileListDTO);
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
	@Transactional
	public void updateIotCust(HttpServletRequest request, TbIotCustDTO tbIotCustDTO) throws BizException {
		userAuthCheck();
		ComInfoDto temp = null;
		tbIotCustDTO.setModUsrId(AuthUtils.getUser().getUserId());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCustDAO.updateIotCust");
		try {
			iotCustDAO.updateIotCust(tbIotCustDTO);
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

		ArrayList<String[]> fileList = new ArrayList<String[]>();
		// 파일업로드
		fileList = FileUtils.uploadMutilpleFile(request, FILE_UPLOAD_PATH, FILE_MAX_SIZE);

		for (String[] arrStr : fileList) {

			TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
			for (String str : arrStr) {
				tbIoTBrdFileListDTO.setContentSeq(tbIotCustDTO.getCustSeq());
				// 고객사 관리 게시판 번호 (contentType ): 5번
				tbIoTBrdFileListDTO.setContentType(FileBoardTypeDTO.CUST);
				tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(FILE_UPLOAD_PATH, ""));
				tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
				tbIoTBrdFileListDTO.setFileName(arrStr[2]);
				tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
				tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());
			}
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"brdFileListDAO.insertTbIoTBrdFileListDTO");
			try {
				brdFileListDAO.insertTbIoTBrdFileListDTO(tbIoTBrdFileListDTO);
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

		int deleteLength = tbIotCustDTO.getDeleteSeq().length;
		if (deleteLength > 0) {
			TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
			for (int i = 0; i < deleteLength; i++) {
//				int seq = Integer.parseInt(tbIotCustDTO.getDeleteSeq()[i]);
				tbIoTBrdFileListDTO.setFileListSeq(tbIotCustDTO.getDeleteSeq()[i]);
				fileServiceUtil.deleteDbAndFile(request, FILE_UPLOAD_PATH, tbIoTBrdFileListDTO);
//				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//						"brdFileListDAO.deleteTbIoTBrdFile");
//				try {
//					brdFileListDAO.deleteTbIoTBrdFile(tbIoTBrdFileListDTO);
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
	}

	@Override
	@Transactional
	public void deleteIotCust(TbIotCustDTO tbIotCustDTO) throws BizException {
		userAuthCheck();
		tbIotCustDTO.setModUsrId(AuthUtils.getUser().getUserId());
		ComInfoDto temp = null;
		temp = null;

		String[] deleteSeq = tbIotCustDTO.getDeleteSeq();
		int delLength = deleteSeq.length;

		for (int i = 0; i < delLength; i++) {
			if (deleteSeq.length > 0) {
//				int custSeq = Integer.parseInt(deleteSeq[i]);
				tbIotCustDTO.setCustSeq(deleteSeq[i]);

				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCustDAO.deleteIotCust");
				try {
					iotCustDAO.deleteIotCust(tbIotCustDTO);
					if(tbIotCustDTO.getUseYn().equals("N")) {
						this.updateCustMember(tbIotCustDTO.getCustSeq());
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

				TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
				tbIoTBrdFileListDTO.setContentType(FileBoardTypeDTO.CUST);
				tbIoTBrdFileListDTO.setContentSeq(deleteSeq[i]);
				if(tbIotCustDTO.getUseYn().equals("N")) {
					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
							"brdFileListDAO.deleteTbIoTBrdFileList");
					try {
						brdFileListDAO.deleteTbIoTBrdFileList(tbIoTBrdFileListDTO);
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
	}
	@Override
	@Transactional
	public void rejoinIotCust(TbIotCustDTO tbIotCustDTO) throws BizException {
		userAuthCheck();
		tbIotCustDTO.setModUsrId(AuthUtils.getUser().getUserId());
		ComInfoDto temp = null;
		temp = null;

		String[] deleteSeq = tbIotCustDTO.getDeleteSeq();
		int delLength = deleteSeq.length;

		for (int i = 0; i < delLength; i++) {
			if (deleteSeq.length > 0) {
				tbIotCustDTO.setCustSeq(deleteSeq[i]);

				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "iotCustDAO.deleteIotCust");
				try {
					iotCustDAO.deleteIotCust(tbIotCustDTO);
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

	@Override
	public String retrieveDuplicatedCustId(TbIotCustDTO tbIotCustDTO) throws BizException {

		String i = "";
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCustDAO.retrieveDuplicatedCustId");

		try {
			i = iotCustDAO.retrieveDuplicatedCustId(tbIotCustDTO);
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

		return i;
	}

	@Override
	public String retrieveDuplicatedCustNm(TbIotCustDTO tbIotCustDTO) throws BizException {

		String i = "";
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotCustDAO.retrieveDuplicatedCustNm");
		try {
			i = iotCustDAO.retrieveDuplicatedCustNm(tbIotCustDTO);
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
		return i;
	}

	@Override
	public TbIoTBrdFileListDTO getFile(TbIoTBrdFileListDTO tbIoTBrdFileDTO) throws BizException {
		TbIoTBrdFileListDTO list = new TbIoTBrdFileListDTO();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"brdFileListDAO.selectFileName");
		try {
			list = brdFileListDAO.selectFileName(tbIoTBrdFileDTO);
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
	public TbIotSvcDto retrieveIotCustSvc() throws BizException {
        // 서비스 호출은 OMS 제외 대상으로 소스 수정
		return iotSvcSvc.retrieveIotSvcOnly();
	}

	@Override
	public List<TbIotCustSDTO> retrieveIotCustSelect(TbIotCustSDTO tbIotCustSDTO) throws BizException {
		String svcCd = null;
		if (null == tbIotCustSDTO.getSvcCd()) {
			svcCd = AuthUtils.getUser().getSvcCd();
		} else {
			svcCd = tbIotCustSDTO.getSvcCd();
		}
		List<TbIotCustSDTO> returnList = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSvcSvc.retrieveIotSvcOnly");
		try {
			returnList = iotCustDAO.retrieveIotCustSelect(svcCd);
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
		return returnList;
	}

	public void updateCustMember(String custSeq) {
		List<TbIotUsrDTO> usrList = iotUsrSvc.retrieveUsrListByCust(custSeq);
		for (Iterator iterator = usrList.iterator(); iterator.hasNext();) {
			TbIotUsrDTO tbIotUsrDTO = (TbIotUsrDTO) iterator.next();
			tbIotUsrDTO.setUseYn("N");
			iotUsrSvc.updateIotUsr(tbIotUsrDTO);
		}
	}

	public void userAuthCheck() {
		String[] adminRoleArr = { "GN00000033", "GN00000034", "GN00000039"};

		if (!ArrayUtils.contains(adminRoleArr, AuthUtils.getUser().getRoleCdId())) {
			throw new BizException("userNotFound");
		}
	}
}
