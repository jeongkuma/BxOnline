package kr.co.scp.ccp.iotNotiBoard.svc.impl;

import java.util.ArrayList;
import java.util.Date;
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

import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.DateUtils;
import kr.co.abacus.common.util.DecimalFormatUtils;
import kr.co.abacus.common.util.FileUtils;
import kr.co.scp.ccp.common.brdFile.dao.BrdFileListDAO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.common.brdFile.util.FileServiceUtil;
import kr.co.scp.ccp.iotNotiBoard.dao.NotiBoardDAO;
import kr.co.scp.ccp.iotNotiBoard.dto.TbIoTNotiBrdDTO;
import kr.co.scp.ccp.iotNotiBoard.dto.TbIoTNotiBrdDetailDTO;
import kr.co.scp.ccp.iotNotiBoard.dto.TbIoTNotiBrdListDTO;
import kr.co.scp.ccp.iotNotiBoard.svc.NotiBoardSVC;
import kr.co.auiot.common.dto.common.FileBoardTypeDTO;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;


//@Slf4j
@Service
public class NotiBoardSVCImpl implements NotiBoardSVC {

	TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();

	@Autowired
	private NotiBoardDAO notiBoardDAO;

	@Autowired
	private BrdFileListDAO notiBrdFileListDAO;

	@Autowired
	private FileServiceUtil fileServiceUtil;

	@Value("${file.upload-dir-noti}")
	private String FILE_UPLOAD_PATH;

	@Value("${file.max-size}")
	private int FILE_MAX_SIZE;

	@SuppressWarnings("rawtypes")
	@Override
	public HashMap<String, Object> retrieveNotiBoardList(TbIoTNotiBrdDTO notiBoardDto) {
		PageDTO pageDTO = new PageDTO();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		ComInfoDto temp = null;
		notiBoardDto.setContentType(FileBoardTypeDTO.NOTI);
		Integer count = 0;
		notiBoardDto.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		notiBoardDto.setCustSeq(AuthUtils.getUser().getCustSeq());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"notiBoardDAO.retrieveNotiBoardCount");
		try {
			count = notiBoardDAO.retrieveNotiBoardCount(notiBoardDto);
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
		pageDTO.pageCalculate(count, notiBoardDto.getDisplayRowCount(), notiBoardDto.getCurrentPage());
		notiBoardDto.setStartPage(pageDTO.getRowStart());
		List<TbIoTNotiBrdListDTO> retrieveNotiBoardList = new ArrayList<TbIoTNotiBrdListDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"notiBoardDAO.retrieveNotiBoardList");
		try {
			retrieveNotiBoardList = notiBoardDAO.retrieveNotiBoardList(notiBoardDto);
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

		for (Iterator notiIterator = retrieveNotiBoardList.iterator(); notiIterator.hasNext();) {
			TbIoTNotiBrdListDTO notiDto = (TbIoTNotiBrdListDTO) notiIterator.next();
			try {
				notiDto.setRegDttm(DateUtils.getDateByFormatToString(notiDto.getRegDttm(), "yyyyMMddkkmmss", "yyyy-MM-dd kk:mm:ss"));
				notiDto.setStartDt(DateUtils.getDateByFormatToString(notiDto.getStartDt(), "yyyy-MM-dd kk:mm:ss", "yyyy-MM-dd"));
				notiDto.setEndDt(DateUtils.getDateByFormatToString(notiDto.getEndDt(), "yyyy-MM-dd kk:mm:ss", "yyyy-MM-dd"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		resultMap.put("page", pageDTO);
		resultMap.put("boardList", retrieveNotiBoardList);

		return resultMap;
	}

	/**
	 * 선택한 데이터 삭제
	 */
	@Override
	@Transactional
	public boolean deleteMultiNotiBoard(HttpServletRequest request, TbIoTNotiBrdDTO notiBoardDto) {
		notiBoardDto.setContentType(FileBoardTypeDTO.NOTI);
		notiBoardDto.setModUserId(AuthUtils.getUser().getUserId());
		boolean result = false;
		for (String notiSeq : notiBoardDto.getNotiSeqList()) {
			notiBoardDto.setNotiSeq(notiSeq);
			String checkRegUser = "";
			ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"notiBoardDAO.checkRegUser");
			try {
				checkRegUser = notiBoardDAO.checkRegUser(notiBoardDto);
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
			if (notiBoardDto.getModUserId().equals(checkRegUser) || AuthUtils.getUser().getRoleCdId().equals("GN00000033")) {
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"notiBoardDAO.deleteNotiBoard");
				try {
					notiBoardDAO.deleteNotiBoard(notiBoardDto);
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
//				tbIoTBrdFileListDTO.setContentSeq(notiSeq);
//				tbIoTBrdFileListDTO.setContentType(notiBoardDto.getContentType());
//
//				// 자료실 파일 삭제
//				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//						"notiBrdFileListDAO.deleteTbIoTBrdFileList");
//				try {
//					notiBrdFileListDAO.deleteTbIoTBrdFileList(tbIoTBrdFileListDTO);
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

		for (String notiSeq : notiBoardDto.getNotiSeqList()) {
			tbIoTBrdFileListDTO.setContentSeq(notiSeq);
			tbIoTBrdFileListDTO.setContentType(notiBoardDto.getContentType());
			ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"faqBrdFileListDAO.retrieveTbIoTBrdFileListDetail");
			try {
				notiBoardDto.setTbIoTBrdFileListDTOlist(notiBrdFileListDAO.retrieveTbIoTBrdFileListDetail(tbIoTBrdFileListDTO));
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

			for (TbIoTBrdFileListDTO dto : notiBoardDto.getTbIoTBrdFileListDTOlist()) {
				tbIoTBrdFileListDTO.setFileListSeq(dto.getFileListSeq());
				fileServiceUtil.deleteDbAndFile(request, FILE_UPLOAD_PATH, tbIoTBrdFileListDTO);
			}
		}
		return result;
	}

	@Override
	public TbIoTNotiBrdDetailDTO retrieveNotiBoardDetail(TbIoTNotiBrdDTO notiBoardDto) {
		notiBoardDto.setContentType(FileBoardTypeDTO.NOTI);
		TbIoTNotiBrdDetailDTO tbIoTNotiBrdDetailDTO = new TbIoTNotiBrdDetailDTO();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"notiBoardDAO.retrieveNotiBoardDetail");
		try {
			tbIoTNotiBrdDetailDTO = notiBoardDAO.retrieveNotiBoardDetail(notiBoardDto);
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

		if (null != tbIoTNotiBrdDetailDTO) {
			tbIoTBrdFileListDTO.setContentSeq(tbIoTNotiBrdDetailDTO.getNotiSeq());
			tbIoTBrdFileListDTO.setContentType(notiBoardDto.getContentType());
			// 파일 리스트 조회
			List<TbIoTBrdFileListDTO> tbIoTLibBrdFileListDTOlist = new ArrayList<TbIoTBrdFileListDTO>();
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"notiBrdFileListDAO.retrieveTbIoTBrdFileListDetail");
			try {
				tbIoTLibBrdFileListDTOlist = notiBrdFileListDAO.retrieveTbIoTBrdFileListDetail(tbIoTBrdFileListDTO);
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

			tbIoTNotiBrdDetailDTO.setTbIoTBrdFileListDTOlist(tbIoTLibBrdFileListDTOlist);
			// 파일 사이즈 계산
			for (int i = 0; i < tbIoTLibBrdFileListDTOlist.size(); i++) {
				double fileSize = tbIoTLibBrdFileListDTOlist.get(i).getFileSize();
				String strFileSize = DecimalFormatUtils.convertToKByte("#.##", fileSize);
				tbIoTLibBrdFileListDTOlist.get(i).setFileSizeLabel(strFileSize);
			}
		}

		return tbIoTNotiBrdDetailDTO;
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional
	public void insertTbIoTNotiBrd(HttpServletRequest request, TbIoTNotiBrdDTO notiBoardDto) {
		notiBoardDto.setRegUserId(AuthUtils.getUser().getUserId());
		notiBoardDto.setContentType(FileBoardTypeDTO.NOTI);

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"notiBoardDAO.insertTbIoTNotiBrd");
		try {
			notiBoardDAO.insertTbIoTNotiBrd(notiBoardDto);
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
				tbIoTBrdFileListDTO.setContentSeq(notiBoardDto.getNotiSeq());
				tbIoTBrdFileListDTO.setContentType(notiBoardDto.getContentType());
				tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(FILE_UPLOAD_PATH , ""));
				tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
				tbIoTBrdFileListDTO.setFileName(arrStr[2]);
				tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
				tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());
			}
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"notiBrdFileListDAO.insertTbIoTBrdFileListDTO");
			try {
				notiBrdFileListDAO.insertTbIoTBrdFileListDTO(tbIoTBrdFileListDTO);
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

	@SuppressWarnings("unused")
	@Override
	@Transactional
	public void updateTbIoTNotiBrd(HttpServletRequest request, TbIoTNotiBrdDTO notiBoardDto) {
		notiBoardDto.setContentType(FileBoardTypeDTO.NOTI);
		notiBoardDto.setModUserId(AuthUtils.getUser().getUserId());
		try {
			ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"notiBoardDAO.updateTbIoTNotiBrd");
			try {
				notiBoardDAO.updateTbIoTNotiBrd(notiBoardDto);
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
					tbIoTBrdFileListDTO.setContentSeq(notiBoardDto.getNotiSeq());
					tbIoTBrdFileListDTO.setContentType(notiBoardDto.getContentType());
					tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(FILE_UPLOAD_PATH , ""));
					tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
					tbIoTBrdFileListDTO.setFileName(arrStr[2]);
					tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
					tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());
				}
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"notiBrdFileListDAO.insertTbIoTBrdFileListDTO");
				try {
					notiBrdFileListDAO.insertTbIoTBrdFileListDTO(tbIoTBrdFileListDTO);
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
			List<TbIoTBrdFileListDTO> tbIotNotiBrdFileListDTOList = new ArrayList<TbIoTBrdFileListDTO>();
			tbIotNotiBrdFileListDTOList = notiBoardDto.getTbIoTBrdFileListDTOlist();
			for (TbIoTBrdFileListDTO tbIotNotiBrdFileListDTO : tbIotNotiBrdFileListDTOList) {
				if (tbIotNotiBrdFileListDTO.getMode().equals("S")) {
				} else if (tbIotNotiBrdFileListDTO.getMode().equals("D")) {
					tbIoTBrdFileListDTO.setFileListSeq(tbIotNotiBrdFileListDTO.getFileListSeq());
					fileServiceUtil.deleteDbAndFile(request, FILE_UPLOAD_PATH, tbIoTBrdFileListDTO);
//					tbIoTLibBrdFileListDTO.setContentSeq(notiBoardDto.getNotiSeq());
//					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
//							"notiBrdFileListDAO.deleteTbIoTBrdFile");
//					try {
//						notiBrdFileListDAO.deleteTbIoTBrdFile(tbIoTLibBrdFileListDTO);
//					} catch (MyBatisSystemException e) {
//						OmsUtils.expInnerOms(temp, e);
//						throw e;
//					} catch (BadSqlGrammarException e) {
//						OmsUtils.expInnerOms(temp, e);
//						throw e;
//					} catch (DataIntegrityViolationException e) {
//						OmsUtils.expInnerOms(temp, e);
//						throw e;
//					} catch (UncategorizedSQLException e) {
//						OmsUtils.expInnerOms(temp, e);
//						throw e;
//					} finally {
//						OmsUtils.endInnerOms(temp);
//					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public TbIoTBrdFileListDTO selectFileName(TbIoTBrdFileListDTO tbIoTBrdFileListDTO) {
		TbIoTBrdFileListDTO brdFileListDTO = new TbIoTBrdFileListDTO();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"iotSelDeviceDAO.retrieveIotDevMAtbVal");
		try {
			brdFileListDTO = notiBrdFileListDAO.selectFileName(tbIoTBrdFileListDTO);
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

		return brdFileListDTO;
	}

}
