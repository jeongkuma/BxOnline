package kr.co.scp.ccp.libraryBoard.svc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;

// import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.DecimalFormatUtils;
import kr.co.abacus.common.util.FileUtils;
import kr.co.scp.ccp.common.brdFile.dao.BrdFileListDAO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.common.brdFile.util.FileServiceUtil;
import kr.co.scp.ccp.libraryBoard.dao.LibraryBoardDAO;
import kr.co.scp.ccp.libraryBoard.dto.TbIoTLibBrdDTO;
import kr.co.scp.ccp.libraryBoard.dto.TbIoTLibBrdResDetailDTO;
import kr.co.scp.ccp.libraryBoard.dto.TbIoTLibBrdResListDTO;
import kr.co.scp.ccp.libraryBoard.svc.LibraryBoardSVC;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;

import java.text.ParseException;
import kr.co.abacus.common.util.DateUtils;
@Primary
@Service
public class LibraryBoardSVCImpl implements LibraryBoardSVC {

	private TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();

	@Autowired
	private LibraryBoardDAO libraryBoardDAO;

	@Autowired
	private BrdFileListDAO libBrdFileListDAO;

	@Value("${file.upload-dir-lib}")
	private String FILE_UPLOAD_PATH;

	@Value("${file.max-size}")
	private int FILE_MAX_SIZE;

	@Autowired
	private FileServiceUtil fileServiceUtil;
	
	@Override
	public HashMap<String, Object> retrieveLibraryBoardList(TbIoTLibBrdDTO libraryBoardDto) throws BizException {
		ComInfoDto temp = null;
		PageDTO pageDTO = new PageDTO();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// 자료실 전체 갯수
		Integer count = 0;

		libraryBoardDto.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		libraryBoardDto.setCustSeq(AuthUtils.getUser().getCustSeq());

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"libraryBoardDAO.retrieveLibraryBoardCount");
		try {
			count = libraryBoardDAO.retrieveLibraryBoardCount(libraryBoardDto);
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

		// 페이징
		pageDTO.pageCalculate(count, libraryBoardDto.getDisplayRowCount(), libraryBoardDto.getCurrentPage());
		libraryBoardDto.setStartPage(pageDTO.getRowStart());

		// 자료실 조회
		List<TbIoTLibBrdResListDTO> retrieveLibraryBoardList = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"libraryBoardDAO.retrieveLibraryBoardList");
		try {
			retrieveLibraryBoardList = libraryBoardDAO.retrieveLibraryBoardList(libraryBoardDto);
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
		for (Iterator boardIterator = retrieveLibraryBoardList.iterator(); boardIterator.hasNext();) {
			TbIoTLibBrdResListDTO boardDto = (TbIoTLibBrdResListDTO) boardIterator.next();
			try {
				boardDto.setRegDttm(DateUtils.getDateByFormatToString(boardDto.getRegDttm(), "yyyyMMddkkmmss", "yyyy-MM-dd kk:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		resultMap.put("roleCdId", AuthUtils.getUser().getRoleCdId());
		resultMap.put("loginId", AuthUtils.getUser().getUserId());
		resultMap.put("page", pageDTO);
		resultMap.put("boardList", retrieveLibraryBoardList);

		return resultMap;
	}

	@Override
	@Transactional
	public void deleteLibraryBoard(TbIoTLibBrdDTO libraryBoardDto) throws BizException {
		ComInfoDto temp = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"libraryBoardDAO.deleteLibraryBoard");
		try {
			// 자료실 마스터
			libraryBoardDAO.deleteLibraryBoard(libraryBoardDto.getLibSeq());
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

		// 파일 지우기 위한 변수
		tbIoTBrdFileListDTO.setContentSeq(libraryBoardDto.getLibSeq());
		tbIoTBrdFileListDTO.setContentType(libraryBoardDto.getContentType());

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"libBrdFileListDAO.deleteTbIoTBrdFileList");
		try {
			// 자료실 파일 삭제
			libBrdFileListDAO.deleteTbIoTBrdFileList(tbIoTBrdFileListDTO);
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

	/**
	 * 선택한 데이터 삭제
	 */
	@Override
	@Transactional
	public void deleteMultiLibraryBoard(HttpServletRequest request, TbIoTLibBrdDTO libraryBoardDto) throws BizException {
		ComInfoDto temp = null;
		for (String libSeq : libraryBoardDto.getLibSepList()) {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"libraryBoardDAO.deleteLibraryBoard");
			try {
				libraryBoardDAO.deleteLibraryBoard(libSeq);
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
			// 파일 지우기 위한 변수
//			tbIoTBrdFileListDTO.setContentSeq(libSeq);
//			tbIoTBrdFileListDTO.setContentType(libraryBoardDto.getContentType());
//
//			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//					"libBrdFileListDAO.deleteTbIoTBrdFileList");
//			try {
//				libBrdFileListDAO.deleteTbIoTBrdFileList(tbIoTBrdFileListDTO);
//			} catch (MyBatisSystemException e) {
//				OmsUtils.expInnerOms(temp, e);
//				throw e;
//			} catch (BadSqlGrammarException e) {
//				OmsUtils.expInnerOms(temp, e);
//				throw e;
//			} catch (DataIntegrityViolationException e) {
//				OmsUtils.expInnerOms(temp, e);
//				throw e;
//			} catch (UncategorizedSQLException e) {
//				OmsUtils.expInnerOms(temp, e);
//				throw e;
//			} finally {
//				OmsUtils.endInnerOms(temp);
//			}
		}
		
		for (String faqSeq : libraryBoardDto.getLibSepList()) {
			tbIoTBrdFileListDTO.setContentSeq(faqSeq);
			tbIoTBrdFileListDTO.setContentType(libraryBoardDto.getContentType());
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"faqBrdFileListDAO.retrieveTbIoTBrdFileListDetail");
			try {
				libraryBoardDto.setTbIoTBrdFileListDTOlist(libBrdFileListDAO.retrieveTbIoTBrdFileListDetail(tbIoTBrdFileListDTO));
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

			for (TbIoTBrdFileListDTO dto : libraryBoardDto.getTbIoTBrdFileListDTOlist()) {
				tbIoTBrdFileListDTO.setFileListSeq(dto.getFileListSeq());
				fileServiceUtil.deleteDbAndFile(request, FILE_UPLOAD_PATH, tbIoTBrdFileListDTO);
			} 
		}
	}

	@Override
	public TbIoTLibBrdResDetailDTO retrieveLibraryBoardDetail(TbIoTLibBrdDTO libraryBoardDto) throws BizException {
		ComInfoDto temp = null;
		// 자료실 마스토 조회
		TbIoTLibBrdResDetailDTO tbIoTLibBrdDTO = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"libraryBoardDAO.retrieveLibraryBoardDetail");
		try {
			tbIoTLibBrdDTO = libraryBoardDAO.retrieveLibraryBoardDetail(libraryBoardDto);
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

		if (null != tbIoTLibBrdDTO) {
			tbIoTBrdFileListDTO.setContentSeq(tbIoTLibBrdDTO.getLibSeq());
			tbIoTBrdFileListDTO.setContentType(libraryBoardDto.getContentType());
			// 파일 리스트 조회
			List<TbIoTBrdFileListDTO> tbIoTLibBrdFileListDTOlist = null;
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"libBrdFileListDAO.retrieveTbIoTBrdFileListDetail");
			try {
				tbIoTLibBrdFileListDTOlist = libBrdFileListDAO.retrieveTbIoTBrdFileListDetail(tbIoTBrdFileListDTO);
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
			tbIoTLibBrdDTO.setRoleCdId(AuthUtils.getUser().getRoleCdId());
			tbIoTLibBrdDTO.setLoginUserId(AuthUtils.getUser().getUserId());
			tbIoTLibBrdDTO.setTbIoTBrdFileListDTOlist(tbIoTLibBrdFileListDTOlist);

			// 파일 사이즈 계산
			for (int i = 0; i < tbIoTLibBrdFileListDTOlist.size(); i++) {
				double fileSize = tbIoTLibBrdFileListDTOlist.get(i).getFileSize();

				String strFileSize = DecimalFormatUtils.convertToKByte("#.##", fileSize);

				tbIoTLibBrdFileListDTOlist.get(i).setFileSizeLabel(strFileSize);
			}
		}

		return tbIoTLibBrdDTO;
	}

	@Override
	@Transactional
	public void insertTbIoTLibBrdDTO(HttpServletRequest request, TbIoTLibBrdDTO libraryBoardDto) throws BizException {
		ComInfoDto temp = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"libraryBoardDAO.insertTbIoTLibBrdDTO");
		try {
			// 자료실 마스트 생성
			libraryBoardDto.setRegUserId(AuthUtils.getUser().getUserId());
			libraryBoardDAO.insertTbIoTLibBrdDTO(libraryBoardDto);
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
			// 파일순서 설정
			TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
			for (String str : arrStr) {
				tbIoTBrdFileListDTO.setContentSeq(libraryBoardDto.getLibSeq());
				tbIoTBrdFileListDTO.setContentType(libraryBoardDto.getContentType());
				tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(FILE_UPLOAD_PATH, ""));
				tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
				tbIoTBrdFileListDTO.setFileName(arrStr[2]);
				tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
				tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());

			}
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"libBrdFileListDAO.insertTbIoTBrdFileListDTO");
			try {
				libBrdFileListDAO.insertTbIoTBrdFileListDTO(tbIoTBrdFileListDTO);
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
	public void updateTbIoTLibBrdDTO(HttpServletRequest request, TbIoTLibBrdDTO libraryBoardDto) throws BizException {
		ComInfoDto temp = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"libraryBoardDAO.updateTbIoTLibBrdDTO");
		try {
			// 자료실 마스터
			libraryBoardDto.setModUserId(AuthUtils.getUser().getUserId());
			libraryBoardDAO.updateTbIoTLibBrdDTO(libraryBoardDto);
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
		// 파일 업로드
		fileList = FileUtils.uploadMutilpleFile(request, FILE_UPLOAD_PATH, FILE_MAX_SIZE);

		// 파일 저장
		for (String[] arrStr : fileList) {
			// 파일순서 설정
			TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
			for (String str : arrStr) {

				tbIoTBrdFileListDTO.setContentSeq(libraryBoardDto.getLibSeq());
				tbIoTBrdFileListDTO.setContentType(libraryBoardDto.getContentType());
				tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(FILE_UPLOAD_PATH, ""));
				tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
				tbIoTBrdFileListDTO.setFileName(arrStr[2]);
				tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
				tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());

			}
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"libBrdFileListDAO.insertTbIoTBrdFileListDTO");
			try {
				libBrdFileListDAO.insertTbIoTBrdFileListDTO(tbIoTBrdFileListDTO);
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

		// TODO 파일목록 삭제, 생성
		// mode : d, mod: i
		List<TbIoTBrdFileListDTO> tbIoTLibBrdFileListDTOList = libraryBoardDto.getTbIoTBrdFileListDTOlist();

		for (TbIoTBrdFileListDTO tbIoTLibBrdFileListDTO : tbIoTLibBrdFileListDTOList) {
			if (tbIoTLibBrdFileListDTO.getMode().equals("S")) {
			} else if (tbIoTLibBrdFileListDTO.getMode().equals("D")) {
				tbIoTBrdFileListDTO.setFileListSeq(tbIoTLibBrdFileListDTO.getFileListSeq());
				fileServiceUtil.deleteDbAndFile(request, FILE_UPLOAD_PATH, tbIoTBrdFileListDTO);
				
//				tbIoTLibBrdFileListDTO.setContentSeq(libraryBoardDto.getLibSeq());
//				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//						"libBrdFileListDAO.deleteTbIoTBrdFile");
//				try {
//					libBrdFileListDAO.deleteTbIoTBrdFile(tbIoTLibBrdFileListDTO);
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
	public TbIoTBrdFileListDTO selectFileName(TbIoTBrdFileListDTO tbIoTBrdFileListDTO) throws BizException {
		TbIoTBrdFileListDTO returnDto = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"libBrdFileListDAO.selectFileName");
		try {
			returnDto = libBrdFileListDAO.selectFileName(tbIoTBrdFileListDTO);
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
}
