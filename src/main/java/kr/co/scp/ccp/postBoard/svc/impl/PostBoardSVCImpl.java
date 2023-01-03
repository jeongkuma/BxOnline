package kr.co.scp.ccp.postBoard.svc.impl;

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

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.DecimalFormatUtils;
import kr.co.abacus.common.util.FileUtils;
import kr.co.scp.ccp.common.brdFile.dao.BrdFileListDAO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.common.brdFile.util.FileServiceUtil;
import kr.co.scp.ccp.postBoard.dao.PostBoardDAO;
import kr.co.scp.ccp.postBoard.dto.TbIoTPostBrdDTO;
import kr.co.scp.ccp.postBoard.dto.TbIoTPostBrdResDetailDTO;
import kr.co.scp.ccp.postBoard.dto.TbIoTPostBrdResListDTO;
import kr.co.scp.ccp.postBoard.svc.PostBoardSVC;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import lombok.extern.slf4j.Slf4j;
import java.text.ParseException;
import kr.co.abacus.common.util.DateUtils;

@Primary
@Slf4j
@Service
public class PostBoardSVCImpl implements PostBoardSVC {

	private TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();

	@Autowired
	private PostBoardDAO postBoardDAO;

	@Autowired
	private BrdFileListDAO postBrdFileListDAO;

	@Value("${file.upload-dir-post}")
	private String FILE_UPLOAD_PATH;

	@Value("${file.max-size}")
	private int FILE_MAX_SIZE;

	@Autowired
	private FileServiceUtil fileServiceUtil;
	
	@Override
	public HashMap<String, Object> retrievePostBoardList(TbIoTPostBrdDTO postBoardDto) throws BizException {

		ComInfoDto temp = null;
		PageDTO pageDTO = new PageDTO();

		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// 게시판 전체 갯수
		Integer count = 0;
		postBoardDto.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		postBoardDto.setCustSeq(AuthUtils.getUser().getCustSeq());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"postBoardDAO.retrievePostBoardCount");
		try {
			postBoardDto.setCustLoginId(AuthUtils.getUser().getCustId());
			count = postBoardDAO.retrievePostBoardCount(postBoardDto);
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
		pageDTO.pageCalculate(count, postBoardDto.getDisplayRowCount(), postBoardDto.getCurrentPage());
		postBoardDto.setStartPage(pageDTO.getRowStart());

		// 게시판 조회
		System.out.println(postBoardDto.getSearchEndDttm());
		List<TbIoTPostBrdResListDTO> retrievePostBoardList = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"postBoardDAO.retrievePostBoardList");
		try {
			// postBoardDto.setCustSeq(AuthUtils.getUser().getCustId());
			retrievePostBoardList = postBoardDAO.retrievePostBoardList(postBoardDto);
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
		for (Iterator postBoardIterator = retrievePostBoardList.iterator(); postBoardIterator.hasNext();) {
			TbIoTPostBrdResListDTO boardDto = (TbIoTPostBrdResListDTO) postBoardIterator.next();
			try {
				boardDto.setRegDate(DateUtils.getDateByFormatToString(boardDto.getRegDate(), "yyyyMMddkkmmss", "yyyy-MM-dd kk:mm:ss"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		resultMap.put("roleCdId", AuthUtils.getUser().getRoleCdId());
		resultMap.put("loginId", AuthUtils.getUser().getUserId());
		resultMap.put("page", pageDTO);
		resultMap.put("boardList", retrievePostBoardList);

		return resultMap;
	}

	@Override
	@Transactional
	public void deletePostBoard(TbIoTPostBrdDTO postBoardDto) throws BizException {
		ComInfoDto temp = null;
		// 게시판 마스터
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "postBoardDAO.deletePostBoard");
		try {
			postBoardDAO.deletePostBoard(postBoardDto.getPostSeq());
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

		tbIoTBrdFileListDTO.setContentType(postBoardDto.getContentType());
		tbIoTBrdFileListDTO.setContentSeq(postBoardDto.getPostSeq());

		// 게시판 파일 삭제
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"postBrdFileListDAO.deleteTbIoTBrdFileList");
		try {
			postBrdFileListDAO.deleteTbIoTBrdFileList(tbIoTBrdFileListDTO);
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
	public void deleteMultiPostBoard(HttpServletRequest request, TbIoTPostBrdDTO postBoardDto) throws BizException {
		ComInfoDto temp = null;
		for (String postSeq : postBoardDto.getPostSepList()) {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "postBoardDAO.deletePostBoard");
			try {
				postBoardDAO.deletePostBoard(postSeq);
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

//			tbIoTBrdFileListDTO.setContentSeq(postSeq);
//			tbIoTBrdFileListDTO.setContentType(postBoardDto.getContentType());
//			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//					"postBrdFileListDAO.deleteTbIoTBrdFileList");
//			try {
//				postBrdFileListDAO.deleteTbIoTBrdFileList(tbIoTBrdFileListDTO);
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
		
		for (String postSeq : postBoardDto.getPostSepList()) {
			tbIoTBrdFileListDTO.setContentSeq(postSeq);
			tbIoTBrdFileListDTO.setContentType(postBoardDto.getContentType());
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"postBrdFileListDAO.retrieveTbIoTBrdFileListDetail");
			try {
				postBoardDto.setTbIoTBrdFileListDTOlist(postBrdFileListDAO.retrieveTbIoTBrdFileListDetail(tbIoTBrdFileListDTO));
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

			for (TbIoTBrdFileListDTO dto : postBoardDto.getTbIoTBrdFileListDTOlist()) {
				tbIoTBrdFileListDTO.setFileListSeq(dto.getFileListSeq());
				fileServiceUtil.deleteDbAndFile(request, FILE_UPLOAD_PATH, tbIoTBrdFileListDTO);
			} 
		}

//		for (TbIoTPostBrdDTO postBoardDto : postBoardDtolist) {
//			deletePostBoard(postBoardDto);
//		}

	}

	@Override
	public TbIoTPostBrdResDetailDTO retrievePostBoardDetail(TbIoTPostBrdDTO postBoardDto) throws BizException {
		ComInfoDto temp = null;
		// 게시판 마스토 조회
		TbIoTPostBrdResDetailDTO tbIoTPostBrdResDetailDTO = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"postBoardDAO.retrievePostBoardDetail");
		try {
			tbIoTPostBrdResDetailDTO = postBoardDAO.retrievePostBoardDetail(postBoardDto);
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

		if (null != tbIoTPostBrdResDetailDTO) {
			tbIoTBrdFileListDTO.setContentSeq(tbIoTPostBrdResDetailDTO.getPostSeq());
			tbIoTBrdFileListDTO.setContentType(postBoardDto.getContentType());
			// 파일 리스트 조회
			List<TbIoTBrdFileListDTO> tbIoTLibBrdFileListDTOlist = null;

			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"postBrdFileListDAO.retrieveTbIoTBrdFileListDetail");
			try {
				tbIoTLibBrdFileListDTOlist = postBrdFileListDAO.retrieveTbIoTBrdFileListDetail(tbIoTBrdFileListDTO);
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
			tbIoTPostBrdResDetailDTO.setRoleCdId(AuthUtils.getUser().getRoleCdId());
			tbIoTPostBrdResDetailDTO.setLoginUserId(AuthUtils.getUser().getUserId());
			tbIoTPostBrdResDetailDTO.setTbIoTBrdFileListDTOlist(tbIoTLibBrdFileListDTOlist);

			// 파일 사이즈 계산
			for (int i = 0; i < tbIoTLibBrdFileListDTOlist.size(); i++) {
				double fileSize = tbIoTLibBrdFileListDTOlist.get(i).getFileSize();

				String strFileSize = DecimalFormatUtils.convertToKByte("#.##", fileSize);

				tbIoTLibBrdFileListDTOlist.get(i).setFileSizeLabel(strFileSize);
			}
		}
		   
		return tbIoTPostBrdResDetailDTO;
	}

	@Override
	@Transactional
	public void insertTbIoTPostBrdDTO(HttpServletRequest request, TbIoTPostBrdDTO postBoardDto) throws BizException {

		ComInfoDto temp = null;
		// 게시판 마스트 생성
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"postBoardDAO.insertTbIoTPostBrdDTO");
		try {
			postBoardDto.setRegUserId(AuthUtils.getUser().getUserId());
			postBoardDto.setCustSeq(AuthUtils.getUser().getCustSeq());
			postBoardDAO.insertTbIoTPostBrdDTO(postBoardDto);
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

		// 파일 리스트 생성
//		List<TbIoTBrdFileListDTO>  tbIoTLibBrdFileListDTOList = postBoardDto.getTbIoTBrdFileListDTOlist();
//		for (TbIoTBrdFileListDTO tbIoTLibBrdFileListDTO : tbIoTLibBrdFileListDTOList) {
//			tbIoTLibBrdFileListDTO.setContentSeq(postBoardDto.getPostSeq());
//			postBrdFileListDAO.insertTbIoTBrdFileListDTO(tbIoTLibBrdFileListDTO);

		for (String[] arrStr : fileList) {
			// 파일순서 설정
			TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
			for (String str : arrStr) {

				tbIoTBrdFileListDTO.setContentSeq(postBoardDto.getPostSeq());
				tbIoTBrdFileListDTO.setContentType(postBoardDto.getContentType());
				tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(FILE_UPLOAD_PATH, ""));
				tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
				tbIoTBrdFileListDTO.setFileName(arrStr[2]);
				tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
				tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());

			}
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"postBrdFileListDAO.insertTbIoTBrdFileListDTO");
			try {
				postBrdFileListDAO.insertTbIoTBrdFileListDTO(tbIoTBrdFileListDTO);
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
	public void updateTbIoTPostBrdDTO(HttpServletRequest request, TbIoTPostBrdDTO postBoardDto) throws BizException {
		ComInfoDto temp = null;
		// 게시판 마스터
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"postBoardDAO.updateTbIoTPostBrdDTO");
		try {
			postBoardDto.setModUserId(AuthUtils.getUser().getUserId());
			postBoardDAO.updateTbIoTPostBrdDTO(postBoardDto);
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

				tbIoTBrdFileListDTO.setContentSeq(postBoardDto.getPostSeq());
				tbIoTBrdFileListDTO.setContentType(postBoardDto.getContentType());
				tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(FILE_UPLOAD_PATH, ""));
				tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
				tbIoTBrdFileListDTO.setFileName(arrStr[2]);
				tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
				tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());

			}

			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"postBrdFileListDAO.insertTbIoTBrdFileListDTO");
			try {
				postBrdFileListDAO.insertTbIoTBrdFileListDTO(tbIoTBrdFileListDTO);
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
		List<TbIoTBrdFileListDTO> tbIoTPostBrdFileListDTOList = postBoardDto.getTbIoTPostBrdFileListDTOlist();
		for (TbIoTBrdFileListDTO tbIoTPostBrdFileListDTO : tbIoTPostBrdFileListDTOList) {
			if (tbIoTPostBrdFileListDTO.getMode().equals("S")) {
			} else if (tbIoTPostBrdFileListDTO.getMode().equals("D")) {
				tbIoTBrdFileListDTO.setFileListSeq(tbIoTPostBrdFileListDTO.getFileListSeq());
				fileServiceUtil.deleteDbAndFile(request, FILE_UPLOAD_PATH, tbIoTBrdFileListDTO);
//				tbIoTPostBrdFileListDTO.setContentSeq(postBoardDto.getPostSeq());
//				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//						"postBrdFileListDAO.deleteTbIoTBrdFile");
//				try {
//					postBrdFileListDAO.deleteTbIoTBrdFile(tbIoTPostBrdFileListDTO);
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
				"postBrdFileListDAO.selectFileName");
		try {
			returnDto = postBrdFileListDAO.selectFileName(tbIoTBrdFileListDTO);
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
