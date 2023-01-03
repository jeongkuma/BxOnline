package kr.co.scp.common.tmpl.svc.impl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.DecimalFormatUtils;
import kr.co.abacus.common.util.FileUtils;
import kr.co.auiot.common.dto.common.FileBoardTypeDTO;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.ccp.common.brdFile.dao.BrdFileListDAO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileDTO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.common.tmpl.dao.TbIotTmplDAO;
import kr.co.scp.common.tmpl.dao.TbIotTmplHdrJqgridDAO;
import kr.co.scp.common.tmpl.dto.*;
import kr.co.scp.common.tmpl.svc.TbIotTmplSVC;
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
@Service
public class TbIotTmplSVCImpl implements TbIotTmplSVC {

	@Autowired
	private BrdFileListDAO brdFileListDAO;

	@Autowired
	private TbIotTmplDAO tbIotTmplDao;

	@Autowired
	private TbIotTmplHdrJqgridDAO tbIotTmplHdrJqgridDao;

	@Value("${file.upload-dir-dashboard}")
	private String FILE_UPLOAD_PATH;

	@Value("${file.get-dashboard-img}")
	private String FILE_GET_DASHPATH;

	@Value("${file.max-size}")
	private int FILE_MAX_SIZE;

	@Override
	public HashMap<String, Object> retrieveTbIotTmplList(TbIotTmplDTO tbIotTmplDto) throws BizException {
		ComInfoDto temp = null;
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageDTO pageDto = new PageDTO();

		int totalCount = 0;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotTmplDao.retrieveTbIotTmplCount");
		try {
			totalCount = tbIotTmplDao.retrieveTbIotTmplCount(tbIotTmplDto);
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

		pageDto.pageCalculate(totalCount, tbIotTmplDto.getDisplayRowCount(), tbIotTmplDto.getCurrentPage());
		tbIotTmplDto.setCurrentPage(pageDto.getRowStart());

		List<TbIotTmplDTO> returnDto = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotTmplDao.retrieveTbIotTmplList");
		try {
			returnDto = tbIotTmplDao.retrieveTbIotTmplList(tbIotTmplDto);
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
		returnMap.put("dataList", returnDto);
		returnMap.put("page", pageDto);
		return returnMap;
	}

	@Override
	public void insertIotJqGridTmpl(TbIotTmplDTO tbIotTmplDto) throws BizException {
		ComInfoDto temp = null;
		Integer tmplSeq = 0;
		tbIotTmplDto.setRegUserId(AuthUtils.getUser().getUserId());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotTmplDao.insertTbIotTmpl");
		try {
			tmplSeq = tbIotTmplDao.insertTbIotTmpl(tbIotTmplDto);
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

		List<TbIotTmplHdrJqgridDTO> tbIotTmplHdrJqgridList = tbIotTmplDto.getTbIotTmplHdrJqgridList();
		if (tbIotTmplHdrJqgridList.size() > 0) {
			for (TbIotTmplHdrJqgridDTO tbIotTmplHdrJqgridDto : tbIotTmplHdrJqgridList) {
				tbIotTmplHdrJqgridDto.setTmplSeq(tbIotTmplDto.getTmplSeq());
				tbIotTmplHdrJqgridDto.setRegUserId(AuthUtils.getUser().getUserId());
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"tbIotTmplHdrJqgridDao.insertTbIotTmplHdrJqgrid");
				try {
					tbIotTmplHdrJqgridDao.insertTbIotTmplHdrJqgrid(tbIotTmplHdrJqgridDto);
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
	public void insertTbIotTmpl(TbIotTmplDTO tbIotTmplDto, HttpServletRequest request) throws BizException {

		ComInfoDto temp = null;
		Integer tmplSeq = 0;
		tbIotTmplDto.setRegUserId(AuthUtils.getUser().getUserId());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotTmplDao.insertTbIotTmpl");
		try {
			tmplSeq = tbIotTmplDao.insertTbIotTmpl(tbIotTmplDto);
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
		String fullFileNm = "";
		// 파일업로드
		fileList = FileUtils.uploadMutilpleFile(request, FILE_UPLOAD_PATH, FILE_MAX_SIZE);
		for (String[] arrStr : fileList) {
			// 파일순서 설정
			TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
			for (String str : arrStr) {
				tbIoTBrdFileListDTO.setContentSeq(tbIotTmplDto.getTmplSeq());
				// 고객사 관리 게시판 번호 (contentType ): 5번
				tbIoTBrdFileListDTO.setContentType(FileBoardTypeDTO.TMPL);
				tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(FILE_UPLOAD_PATH, ""));
				tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
				tbIoTBrdFileListDTO.setFileName(arrStr[2]);
				tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
				tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());
				fullFileNm = FILE_GET_DASHPATH + tbIoTBrdFileListDTO.getFilePath() + "/" + arrStr[2];

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

			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"brdFileListDAO.insertTbIoTBrdFileListDTO");
			tbIotTmplDto.setTmplRuleImg(fullFileNm);
			tbIotTmplDto.setModUserId(AuthUtils.getUser().getUserId());
			try {
				tbIotTmplDao.updateTmplFileNm(tbIotTmplDto);
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
	public void updateIotJqGridTmpl(TbIotTmplDTO tbIotTmplDto) throws BizException {
		ComInfoDto temp = null;
		tbIotTmplDto.setModUserId(AuthUtils.getUser().getUserId());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotTmplDao.updateTbIotTmpl");
		try {
			tbIotTmplDao.updateTbIotTmpl(tbIotTmplDto);
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
		List<TbIotTmplHdrJqgridDTO> tbIotTmplHdrJqgridList = tbIotTmplDto.getTbIotTmplHdrJqgridList();

		if (tbIotTmplHdrJqgridList.size() > 0) {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"tbIotTmplHdrJqgridDao.deleteTbIotTmplHdrJqgridAll");
			try {
				tbIotTmplHdrJqgridList.get(0).setTmplSeq(tbIotTmplDto.getTmplSeq());
				tbIotTmplHdrJqgridDao.deleteTbIotTmplHdrJqgridAll(tbIotTmplHdrJqgridList.get(0));
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
			for (TbIotTmplHdrJqgridDTO tbIotTmplHdrJqgridDto : tbIotTmplHdrJqgridList) {
				tbIotTmplHdrJqgridDto.setTmplSeq(tbIotTmplDto.getTmplSeq());
				tbIotTmplHdrJqgridDto.setRegUserId(AuthUtils.getUser().getUserId());
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"tbIotTmplHdrJqgridDao.insertTbIotTmplHdrJqgrid");
				try {
					tbIotTmplHdrJqgridDao.insertTbIotTmplHdrJqgrid(tbIotTmplHdrJqgridDto);
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
	public void updateTbIotTmpl(HttpServletRequest request, TbIotTmplDTO tbIotTmplDto) throws BizException {
		ComInfoDto temp = null;
		tbIotTmplDto.setModUserId(AuthUtils.getUser().getUserId());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotTmplDao.updateTbIotTmpl");
		try {
			tbIotTmplDao.updateTbIotTmpl(tbIotTmplDto);
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
		String fullFileNm = "";

		for (String[] arrStr : fileList) {

			TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
			for (String str : arrStr) {
				tbIoTBrdFileListDTO.setContentSeq(tbIotTmplDto.getTmplSeq());
				tbIoTBrdFileListDTO.setContentType(FileBoardTypeDTO.TMPL);
				tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(FILE_UPLOAD_PATH, ""));
				tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
				tbIoTBrdFileListDTO.setFileName(arrStr[2]);
				tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
				tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());
				fullFileNm = FILE_GET_DASHPATH + tbIoTBrdFileListDTO.getFilePath() + "/" + arrStr[2];
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

		int deleteLength = tbIotTmplDto.getDeleteSeq().length;
		if (deleteLength > 0) {
			TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
			for (int i = 0; i < deleteLength; i++) {
				String seq = tbIotTmplDto.getDeleteSeq()[i];
				tbIoTBrdFileListDTO.setFileListSeq(seq);
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"brdFileListDAO.deleteTbIoTBrdFile");
				try {
					brdFileListDAO.deleteTbIoTBrdFile(tbIoTBrdFileListDTO);
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

		if (!fullFileNm.equals("")) {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"brdFileListDAO.updateTmplFileNm");
			tbIotTmplDto.setTmplRuleImg(fullFileNm);
			tbIotTmplDto.setModUserId(AuthUtils.getUser().getUserId());
			try {
				tbIotTmplDao.updateTmplFileNm(tbIotTmplDto);
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
	public void deleteTbIotTmpl(TbIotTmplDTO tbIotTmplDto) throws BizException {
		ComInfoDto temp = null;
		for (String tmplSeq : tbIotTmplDto.getTmplSeqList()) {
			TbIotTmplDTO delTbIotTmplDto = new TbIotTmplDTO();
			delTbIotTmplDto.setTmplSeq(tmplSeq);
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotTmplDao.deleteTbIotTmpl");
			try {
				tbIotTmplDao.deleteTbIotTmpl(delTbIotTmplDto);
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

			TbIotTmplHdrJqgridDTO tbIotTmplHdrJqgridDto = new TbIotTmplHdrJqgridDTO();
			tbIotTmplHdrJqgridDto.setTmplSeq(tmplSeq);
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"tbIotTmplHdrJqgridDao.deleteTbIotTmplHdrJqgridAll");
			try {
				tbIotTmplHdrJqgridDao.deleteTbIotTmplHdrJqgridAll(tbIotTmplHdrJqgridDto);
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
	public List<TbIotJqDataResponseDTO> retrieveJqData(TbIotJqDataRequestDTO tbIotJqDataRequestDto)
			throws BizException {
		ComInfoDto temp = null;
		List<TbIotJqDataResponseDTO> dataList = null;
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String newLang = c.getXlang();
		tbIotJqDataRequestDto.setLangSet(newLang);
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotTmplHdrJqgridDao.retrieveJqData");
		try {
			dataList = tbIotTmplHdrJqgridDao.retrieveJqData(tbIotJqDataRequestDto);
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

		if (dataList.size() == 0) {

		}

		List<String> colPerName = new ArrayList<>();

		if (dataList.size() == 0) {
			throw new BizException("noTmplHeader");
		} else {
			String colName[] = dataList.get(0).getColNameData().split(", ");

			for (int i = 0; i < colName.length; i++) {
				colPerName.add(colName[i]);
			}

			dataList.get(0).setColNameList(colPerName);
		}

		return dataList;
	}

	@Override
	public List<TbIotTmplDTO> retrieveTbIotTmplRule(TbIotTmplDTO tbIotTmplDto) throws BizException {
		List<TbIotTmplDTO> returnDto = null;

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotTmplDao.retrieveTbIotTmplRule");
		try {
			returnDto = tbIotTmplDao.retrieveTbIotTmplRule(tbIotTmplDto);
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
	public List<TbIotTmplDTO> retrieveTbIotDashboardTmplList(TbIotTmplDTO tbIotTmplDto) throws BizException {
		// TODO Auto-generated method stubW
		List<TbIotTmplDTO> returnDto = null;

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotTmplDao.retrieveTbIotDashboardTmplList");
		try {
			returnDto = tbIotTmplDao.retrieveTbIotDashboardTmplList(tbIotTmplDto);
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
	public int duplicationCheck(TbIotTmplDTO tbIotTmplDto) throws BizException {
		// TODO Auto-generated method stub
		int chkCnt = 0;

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotTmplDao.duplicationCheck");
		try {
			chkCnt = tbIotTmplDao.duplicationCheck(tbIotTmplDto);
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

//	@Override
//	public TbIotTmplDTO retrieveTbIotTmplDetail(TbIotTmplDTO tbIotTmplDto) throws BizException {
//		TbIotTmplDTO returnDto = new TbIotTmplDTO();
//		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotTmplDao.retrieveTbIotTmplDetail");
//		try {
//			returnDto = tbIotTmplDao.retrieveTbIotTmplDetail(tbIotTmplDto);
//		} catch (MyBatisSystemException e) {OmsUtils.expInnerOms(temp, e); throw e;} catch (BadSqlGrammarException e) {
//			OmsUtils.expInnerOms(temp, e);
//			throw e;
//		} catch (DataIntegrityViolationException e) { OmsUtils.expInnerOms(temp, e); throw e; }  catch (UncategorizedSQLException e)  {
//	OmsUtils.expInnerOms(temp, e);
//	throw e;
//} finally {
//			OmsUtils.endInnerOms(temp);
//		}
//		return returnDto;s
//	}

	@Override
	public HashMap<String, Object> retrieveTbIotTmplDetail(TbIotTmplDTO tbIotTmplDto) throws BizException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		TbIotTmplDTO returnDto = new TbIotTmplDTO();
		ComInfoDto temp = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotTmplDao.retrieveTbIotTmplDetail");
		try {
			returnDto = tbIotTmplDao.retrieveTbIotTmplDetail(tbIotTmplDto);
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
		map.put("data", returnDto);

		TbIoTBrdFileDTO fileDto = new TbIoTBrdFileDTO();
		fileDto.setContentSeq(returnDto.getTmplSeq());
		fileDto.setContentType(FileBoardTypeDTO.TMPL);
		List<TbIoTBrdFileDTO> returnFile = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "brdFileListDAO.retrieveTbIoTBrdFileList");
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
		} catch (UncategorizedSQLException e) {
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
	public List<TbIotTmplHdrJqgridDTO> retrieveJqgrid(TbIotTmplHdrJqgridDTO tbJqgridDto) throws BizException {
		List<TbIotTmplHdrJqgridDTO> returnDto = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotTmplHdrJqgridDao.retrieveTbIotTmplHdrJqgridList");
		try {
			returnDto = tbIotTmplHdrJqgridDao.retrieveTbIotTmplHdrJqgridList(tbJqgridDto);
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
	public void copyJqgrid(TbIotTmplDTO tbIotTmplDto) throws BizException {
		// TODO Auto-generated method stub
		List<TbIotTmplHdrJqgridDTO> tbIotTmplHdrJqgridList = tbIotTmplDto.getTbIotTmplHdrJqgridList();
		if (tbIotTmplHdrJqgridList.size() > 0) {
			for (TbIotTmplHdrJqgridDTO tbIotTmplHdrJqgridDto : tbIotTmplHdrJqgridList) {

				tbIotTmplHdrJqgridDto.setRegUserId(AuthUtils.getUser().getUserId());
				ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"tbIotTmplHdrJqgridDao.insertTbIotTmplHdrJqgrid");
				try {
					tbIotTmplHdrJqgridDao.insertTbIotTmplHdrJqgrid(tbIotTmplHdrJqgridDto);
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
	public int duplicationCharSet(TbIotTmplHdrJqgridDTO chkDto) throws BizException {
		// TODO Auto-generated method stub
		int chkCnt = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotTmplHdrJqgridDao.duplicationCharSet");
		try {
			chkCnt = tbIotTmplHdrJqgridDao.duplicationCharSet(chkDto);
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
	public HashMap<String, Object> retrieveTbIotJqgridTmplList(TbIotTmplDTO tbIotTmplDto) throws BizException {
		ComInfoDto temp = null;
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageDTO pageDto = new PageDTO();

		int totalCount = 0;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotTmplDao.retrieveTbIotTmplJqgridCount");
		try {
			totalCount = tbIotTmplDao.retrieveTbIotTmplJqgridCount(tbIotTmplDto);
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

		pageDto.pageCalculate(totalCount, tbIotTmplDto.getDisplayRowCount(), tbIotTmplDto.getCurrentPage());
		tbIotTmplDto.setCurrentPage(pageDto.getRowStart());

		List<TbIotTmplDTO> returnDto = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotTmplDao.retrieveTbIotTmplJqgridList");
		try {
			returnDto = tbIotTmplDao.retrieveTbIotTmplJqgridList(tbIotTmplDto);
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
		returnMap.put("dataList", returnDto);
		returnMap.put("page", pageDto);
		return returnMap;
	}

	@Override
	public int duplicationCheckCopy(CopyJqGridDto copyJqGridDto) throws BizException {
		int chkCnt = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotTmplHdrJqgridDao.duplicationCheckCopy");
		try {
			chkCnt = tbIotTmplHdrJqgridDao.duplicationCheckCopy(copyJqGridDto);
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
	public void copyJqTmplGrid(CopyJqGridDto copyJqGridDto) throws BizException {
		ComInfoDto temp = null;
		List<TbIotTmplHdrJqgridDTO> jqList = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotTmplHdrJqgridDao.retrieveOrgTbIotTmplHdrJqgridList");
		try {
			jqList = tbIotTmplHdrJqgridDao.retrieveOrgTbIotTmplHdrJqgridList(copyJqGridDto);
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

		for (TbIotTmplHdrJqgridDTO tJqGridDto : jqList) {
			if (copyJqGridDto.getCopyType().equals("C")) {
				tJqGridDto.setLangSet(copyJqGridDto.getLangSet());
				tJqGridDto.setDevClsCd(copyJqGridDto.getOrgDevClsCd());
			} else {
				tJqGridDto.setDevClsCd(copyJqGridDto.getDevClsCd());
				tJqGridDto.setDevClsCdNm(copyJqGridDto.getDevClsCdNm());
				tJqGridDto.setLangSet(copyJqGridDto.getOrgLangSet());
			}
			tJqGridDto.setRegUserId(AuthUtils.getUser().getUserId());
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"tbIotMenuDao.copyInsertTbIotMenu");
			try {
				tbIotTmplHdrJqgridDao.insertTbIotTmplHdrJqgrid(tJqGridDto);
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
	public String retrieveTbIotTmplCdId(TbIotTmplDTO tbIotTmplDto) throws BizException {
		String returnValue = "";
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotTmplDao.retrieveTbIotTmplCdId");
		try {
			returnValue = tbIotTmplDao.retrieveTbIotTmplCdId(tbIotTmplDto);
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
		return returnValue;
	}
}
