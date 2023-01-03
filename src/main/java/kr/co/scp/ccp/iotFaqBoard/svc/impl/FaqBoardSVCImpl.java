package kr.co.scp.ccp.iotFaqBoard.svc.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.DateUtils;
import kr.co.abacus.common.util.DecimalFormatUtils;
import kr.co.abacus.common.util.FileUtils;
import kr.co.scp.ccp.common.brdFile.dao.BrdFileListDAO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileDTO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.common.brdFile.util.FileServiceUtil;
import kr.co.scp.ccp.iotFaqBoard.dao.FaqBoardDAO;
import kr.co.scp.ccp.iotFaqBoard.dto.TbIoTFaqBrdCategoryDTO;
import kr.co.scp.ccp.iotFaqBoard.dto.TbIoTFaqBrdDTO;
import kr.co.scp.ccp.iotFaqBoard.dto.TbIoTFaqBrdDetailDTO;
import kr.co.scp.ccp.iotFaqBoard.dto.TbIoTFaqBrdListDTO;
import kr.co.scp.ccp.iotFaqBoard.dto.TbIoTFaqBrdUserDTO;
import kr.co.scp.ccp.iotFaqBoard.svc.FaqBoardSVC;
import kr.co.auiot.common.dto.common.FileBoardTypeDTO;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;

//@Slf4j
@Service
public class FaqBoardSVCImpl implements FaqBoardSVC {

	TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();

	@Autowired
	private FaqBoardDAO faqBoardDAO;

	@Autowired
	private BrdFileListDAO faqBrdFileListDAO;

	@Autowired
	private FileServiceUtil fileServiceUtil;

	@Value("${file.upload-dir-faq}")
	private String FILE_UPLOAD_PATH;

	@Value("${file.max-size}")
	private int FILE_MAX_SIZE;

	@Override
	public HashMap<String, Object> retrieveIotFaqBoardList(TbIoTFaqBrdDTO faqBoardDto) {
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String langSet = c.getXlang();
		faqBoardDto.setLangSet(langSet);
		faqBoardDto.setContentType(FileBoardTypeDTO.FAQ);
		faqBoardDto.setCustSeq(AuthUtils.getUser().getCustSeq());
		faqBoardDto.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		PageDTO pageDTO = new PageDTO();
		Integer count = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"faqBoardDAO.retrieveFaqBoardCount");
		try {
			count = faqBoardDAO.retrieveFaqBoardCount(faqBoardDto);
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

		pageDTO.pageCalculate(count, faqBoardDto.getDisplayRowCount(), faqBoardDto.getCurrentPage());
		faqBoardDto.setStartPage(pageDTO.getRowStart());
		List<TbIoTFaqBrdListDTO> retrieveFaqBoardList = new ArrayList<TbIoTFaqBrdListDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "faqBoardDAO.retrieveFaqBoardList");
		try {
			retrieveFaqBoardList = faqBoardDAO.retrieveFaqBoardList(faqBoardDto);
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

		List<TbIoTFaqBrdCategoryDTO> category = new ArrayList<TbIoTFaqBrdCategoryDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "faqBoardDAO.retrieveCategoryList");
		try {
			category = faqBoardDAO.retrieveCategoryList(faqBoardDto);
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

		for (Iterator faqIterator = retrieveFaqBoardList.iterator(); faqIterator.hasNext();) {
			TbIoTFaqBrdListDTO faqDto = (TbIoTFaqBrdListDTO) faqIterator.next();
			try {
				faqDto.setRegDttm(DateUtils.getDateByFormatToString(faqDto.getRegDttm(), "yyyyMMddkkmmss", "yyyy-MM-dd kk:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		resultMap.put("page", pageDTO);
		resultMap.put("boardList", retrieveFaqBoardList);
		resultMap.put("categoryList", category);
		return resultMap;
	}

	/**
	 * 선택한 데이터 삭제
	 */
	@Override
	@Transactional
	public boolean deleteMultiFaqBoard(HttpServletRequest request, TbIoTFaqBrdDTO faqBoardDto) {
		faqBoardDto.setContentType(FileBoardTypeDTO.FAQ);
		faqBoardDto.setModUserId(AuthUtils.getUser().getUserId());
		boolean result = false;
		for (String faqSeq : faqBoardDto.getFaqSeqList()) {
			faqBoardDto.setFaqSeq(faqSeq);
			String checkRegUser = "";
			ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"faqBoardDAO.checkRegUser");
			try {
				checkRegUser = faqBoardDAO.checkRegUser(faqBoardDto);
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
			if(checkRegUser.equals(faqBoardDto.getModUserId())) {
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"faqBrdFileListDAO.deleteFaqBoard");
				try {
					faqBoardDAO.deleteFaqBoard(faqBoardDto);
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

//				// 파일 지우기 위한 변수
//				tbIoTBrdFileListDTO.setContentSeq(faqSeq);
//				tbIoTBrdFileListDTO.setContentType(faqBoardDto.getContentType());
//
//				// 자료실 파일 삭제
//				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//						"faqBrdFileListDAO.deleteTbIoTBrdFileList");
//				try {
//					faqBrdFileListDAO.deleteTbIoTBrdFileList(tbIoTBrdFileListDTO);
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
				result = true;
			} else {
				throw new BizException("notMatchUser");
			}
		}

		for (String faqSeq : faqBoardDto.getFaqSeqList()) {
			tbIoTBrdFileListDTO.setContentSeq(faqSeq);
			tbIoTBrdFileListDTO.setContentType(faqBoardDto.getContentType());
			ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"faqBrdFileListDAO.retrieveTbIoTBrdFileListDetail");
			try {
				faqBoardDto.setTbIoTBrdFileListDTOlist(faqBrdFileListDAO.retrieveTbIoTBrdFileListDetail(tbIoTBrdFileListDTO));
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

			for (TbIoTBrdFileListDTO dto : faqBoardDto.getTbIoTBrdFileListDTOlist()) {
				tbIoTBrdFileListDTO.setFileListSeq(dto.getFileListSeq());
				fileServiceUtil.deleteDbAndFile(request, FILE_UPLOAD_PATH, tbIoTBrdFileListDTO);
			}
//			int delSize = faqBoardDto.getTbIoTBrdFileListDTOlist().size();
//			if(delSize > 0) {
//				for(int i = 0; i < delSize; i++) {
//					tbIoTBrdFileListDTO.setFileListSeq(faqBoardDto.getTbIoTBrdFileListDTOlist().get(i).getFileListSeq());
//					fileServiceUtil.deleteDbAndFile(request, FILE_UPLOAD_PATH, tbIoTBrdFileListDTO);
//				}
//			}
		}
		return result;
	}

	@Override
	public HashMap<String, Object> retrieveFaqBoardDetail(TbIoTFaqBrdDTO faqBoardDto) {
		// FAQ 마스터 조회
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String langSet = c.getXlang();
		faqBoardDto.setLangSet(langSet);
		faqBoardDto.setContentType(FileBoardTypeDTO.FAQ);
		TbIoTFaqBrdDetailDTO tbIoTFaqBrdDTO = new TbIoTFaqBrdDetailDTO();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"faqBoardDAO.retrieveFaqBoardDetail");
		try {
			tbIoTFaqBrdDTO = faqBoardDAO.retrieveFaqBoardDetail(faqBoardDto);
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

		List<TbIoTFaqBrdCategoryDTO> category = new ArrayList<TbIoTFaqBrdCategoryDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "faqBoardDAO.retrieveCategoryList");
		try {
			category = faqBoardDAO.retrieveCategoryList(faqBoardDto);
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

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		if (null != tbIoTFaqBrdDTO) {
			tbIoTBrdFileListDTO.setContentSeq(tbIoTFaqBrdDTO.getFaqSeq());
			tbIoTBrdFileListDTO.setContentType(faqBoardDto.getContentType());
			// 파일 리스트 조회
			List<TbIoTBrdFileListDTO> tbIoTLibBrdFileListDTOlist = new ArrayList<TbIoTBrdFileListDTO>();
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"faqBrdFileListDAO.retrieveTbIoTBrdFileListDetail");
			try {
				tbIoTLibBrdFileListDTOlist = faqBrdFileListDAO.retrieveTbIoTBrdFileListDetail(tbIoTBrdFileListDTO);
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

			tbIoTFaqBrdDTO.setTbIoTBrdFileListDTOlist(tbIoTLibBrdFileListDTOlist);

			// 파일 사이즈 계산
			for (int i = 0; i < tbIoTLibBrdFileListDTOlist.size(); i++) {
				double fileSize = tbIoTLibBrdFileListDTOlist.get(i).getFileSize();
				String strFileSize = DecimalFormatUtils.convertToKByte("#.##", fileSize);
				tbIoTLibBrdFileListDTOlist.get(i).setFileSizeLabel(strFileSize);
			}
		}
		resultMap.put("faqDetail", tbIoTFaqBrdDTO);
		resultMap.put("categoryList", category);

		return resultMap;
	}

	@Override
	@Transactional
	public void insertTbIotFaqBrd(HttpServletRequest request, TbIoTFaqBrdDTO faqBoardDto) {
		faqBoardDto.setContentType(FileBoardTypeDTO.FAQ);
		faqBoardDto.setRegUserId(AuthUtils.getUser().getUserId());
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"faqBoardDAO.insertTbIotFaqBrd");
		try {
			faqBoardDAO.insertTbIotFaqBrd(faqBoardDto);
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
		fileList = FileUtils.uploadMutilpleFile(request, FILE_UPLOAD_PATH, FILE_MAX_SIZE);
		for (String[] arrStr : fileList) {
			TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
			for (String str : arrStr) {
				tbIoTBrdFileListDTO.setContentSeq(faqBoardDto.getFaqSeq());
				tbIoTBrdFileListDTO.setContentType(faqBoardDto.getContentType());
				tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(FILE_UPLOAD_PATH, ""));
				tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
				tbIoTBrdFileListDTO.setFileName(arrStr[2]);
				tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
				tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());
			}
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"faqBrdFileListDAO.insertTbIoTBrdFileListDTO");
			try {
				faqBrdFileListDAO.insertTbIoTBrdFileListDTO(tbIoTBrdFileListDTO);
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
	public void updateIotFaqBrd(HttpServletRequest request, TbIoTFaqBrdDTO faqBoardDto) {
		faqBoardDto.setContentType(FileBoardTypeDTO.FAQ);
		faqBoardDto.setModUserId(AuthUtils.getUser().getUserId());
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"faqBoardDAO.updateIotFaqBrd");
		try {
			faqBoardDAO.updateIotFaqBrd(faqBoardDto);
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
		fileList = FileUtils.uploadMutilpleFile(request, FILE_UPLOAD_PATH, FILE_MAX_SIZE);

		for (String[] arrStr : fileList) {
			// 파일순서 설정
			TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
			for (String str : arrStr) {
				tbIoTBrdFileListDTO.setContentSeq(faqBoardDto.getFaqSeq());
				tbIoTBrdFileListDTO.setContentType(faqBoardDto.getContentType());
				tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(FILE_UPLOAD_PATH, ""));
				tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
				tbIoTBrdFileListDTO.setFileName(arrStr[2]);
				tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
				tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());
			}
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"faqBrdFileListDAO.insertTbIoTBrdFileListDTO");
			try {
				faqBrdFileListDAO.insertTbIoTBrdFileListDTO(tbIoTBrdFileListDTO);
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
		List<TbIoTBrdFileListDTO> tbIotFaqBrdFileListDTOList = new ArrayList<TbIoTBrdFileListDTO>();
		tbIotFaqBrdFileListDTOList = faqBoardDto.getTbIoTBrdFileListDTOlist();
		for (TbIoTBrdFileListDTO tbIotFaqBrdFileListDTO : tbIotFaqBrdFileListDTOList) {
			if (tbIotFaqBrdFileListDTO.getMode().equals("S")) {
			} else if (tbIotFaqBrdFileListDTO.getMode().equals("D")) {
				tbIoTBrdFileListDTO.setFileListSeq(tbIotFaqBrdFileListDTO.getFileListSeq());
				fileServiceUtil.deleteDbAndFile(request, FILE_UPLOAD_PATH, tbIoTBrdFileListDTO);
//				tbIoTLibBrdFileListDTO.setContentSeq(faqBoardDto.getFaqSeq());
//				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//						"faqBrdFileListDAO.deleteTbIoTBrdFile");
//				try {
//					faqBrdFileListDAO.deleteTbIoTBrdFile(tbIoTLibBrdFileListDTO);
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
	public TbIoTBrdFileListDTO selectFileName(TbIoTBrdFileListDTO tbIoTBrdFileListDTO) {
		TbIoTBrdFileListDTO tbBrdFileListDTO = new TbIoTBrdFileListDTO();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"faqBrdFileListDAO.selectFileName");
		try {
			tbBrdFileListDTO = faqBrdFileListDAO.selectFileName(tbIoTBrdFileListDTO);
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
		return tbBrdFileListDTO;
	}

	@Override
	public HashMap<String, Object> retrieveIotFaqBoardUser(TbIoTFaqBrdDTO faqBoardDto) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		PageDTO pageDTO = new PageDTO();
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String langSet = c.getXlang();
		faqBoardDto.setLangSet(langSet);
		faqBoardDto.setContentType(FileBoardTypeDTO.FAQ);
		faqBoardDto.setCustSeq(AuthUtils.getUser().getCustSeq());
		faqBoardDto.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		Integer count;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"faqBoardDAO.retrieveFaqBoardCount");
		try {
			count = faqBoardDAO.retrieveFaqBoardCount(faqBoardDto);
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

		pageDTO.pageCalculate(count, faqBoardDto.getDisplayRowCount(), faqBoardDto.getCurrentPage());
		faqBoardDto.setCurrentPage(pageDTO.getRowStart());

		faqBoardDto.setContentType(FileBoardTypeDTO.FAQ);
		List<TbIoTFaqBrdUserDTO> retrieveFaqBoardUser = new ArrayList<TbIoTFaqBrdUserDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "faqBoardDAO.retrieveFaqBoardUser");
		try {
			retrieveFaqBoardUser = faqBoardDAO.retrieveFaqBoardUser(faqBoardDto);
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

		List<TbIoTFaqBrdUserDTO> retrieveFaqBoardInterest = new ArrayList<TbIoTFaqBrdUserDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"faqBoardDAO.retrieveFaqBoardInterest");
		try {
			retrieveFaqBoardInterest = faqBoardDAO.retrieveFaqBoardInterest(faqBoardDto);
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

		List<TbIoTFaqBrdCategoryDTO> category = new ArrayList<TbIoTFaqBrdCategoryDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "faqBoardDAO.retrieveCategoryList");
		try {
			category = faqBoardDAO.retrieveCategoryList(faqBoardDto);
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

		resultMap.put("faqUserList", retrieveFaqBoardUser);
		resultMap.put("faqInterestList", retrieveFaqBoardInterest);
		resultMap.put("categoryList", category);
		resultMap.put("page", pageDTO);
		return resultMap;
	}
}
