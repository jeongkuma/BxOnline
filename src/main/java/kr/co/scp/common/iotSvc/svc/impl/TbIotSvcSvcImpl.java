package kr.co.scp.common.iotSvc.svc.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
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

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.DecimalFormatUtils;
import kr.co.scp.ccp.common.brdFile.dao.BrdFileListDAO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileDTO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.common.brdFile.util.FileServiceUtil;
import kr.co.scp.ccp.common.dto.TbIotTreeDTO;
import kr.co.scp.ccp.common.dto.TbIotTreeDataDTO;
import kr.co.scp.ccp.common.dto.TbIotTreeLiAttrDTO;
import kr.co.scp.ccp.common.dto.TbIotTreeStatusDTO;
import kr.co.scp.ccp.common.utils.FileUtil;
import kr.co.scp.ccp.iotDev.dao.IotDevDAO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDTO;
import kr.co.auiot.common.dto.common.FileBoardTypeDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdCDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdUDTO;
import kr.co.scp.common.iotCmCd.svc.IotCmCdSVC;
import kr.co.scp.common.iotSvc.dao.TbIotSvcDao;
import kr.co.scp.common.iotSvc.dto.TbIotDevClsImgDto;
import kr.co.scp.common.iotSvc.dto.TbIotSvcCombinedDto;
import kr.co.scp.common.iotSvc.dto.TbIotSvcDto;
import kr.co.scp.common.iotSvc.dto.TbIotSvcMDto;
import kr.co.scp.common.iotSvc.svc.TbIotSvcSvc;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;

@Primary
@Service
public class TbIotSvcSvcImpl implements TbIotSvcSvc {

	@Autowired
	TbIotSvcDao svcDao;

	@Autowired
	IotDevDAO iotDevDAO;

	@Autowired
	BrdFileListDAO brdFileListDAO;

	@Autowired
	private FileServiceUtil fileServiceUtil;

	@Autowired
	IotCmCdSVC cmcdSvc;

	@Value("${file.upload-dir-map}")
	private String FILE_UPLOAD_PATH;

	@Value("${file.get-map-Img}")
	private String FILE_UPLOAD_PATH_IMG;

	@Value("${file.max-size}")
	private int FILE_MAX_SIZE;

	@Override
	public List<TbIotTreeDTO> retrieveIotSvc(TbIotSvcDto svcDto) throws BizException {
		List<TbIotTreeDTO> returnTreeData = new ArrayList<TbIotTreeDTO>();

		// 관리화면 최상위 데이터 생성
		TbIotTreeDTO firstData = new TbIotTreeDTO();
		TbIotTreeDataDTO tmpTreeData = new TbIotTreeDataDTO();
		tmpTreeData.setParent("#");
		tmpTreeData.setSelfSeq("0");
		firstData.setId("0");
		firstData.setParent("#");
		firstData.setText("서비스 관리");
		firstData.setLi_attr(new TbIotTreeLiAttrDTO());
		firstData.setData(tmpTreeData);
		firstData.setState(new TbIotTreeStatusDTO(true));
		returnTreeData.add(firstData);
		svcDto.setLangSet(ReqContextComponent.getComInfoDto().getXlang());

		List<TbIotSvcDto> list = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "svcDao.retrieveIotSvc");
		try {
			list = svcDao.retrieveIotSvc(svcDto);
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
		TbIotCmCdDTO cmCdDto = new TbIotCmCdDTO();
//		cmCdDto.setLangSet("skip");
		TbIotCmCdUDTO cmCdUDto;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TbIotSvcDto tbIotSvcDto = (TbIotSvcDto) iterator.next();
			TbIotTreeDTO tmpTreeDto = new TbIotTreeDTO();
			TbIotTreeLiAttrDTO tmpTreeLiAttrDto = new TbIotTreeLiAttrDTO();
			TbIotTreeDataDTO tmpTreeDataDto = new TbIotTreeDataDTO();
			TbIotTreeStatusDTO tmpTreeStatusDto = new TbIotTreeStatusDTO();

			if (tbIotSvcDto.getSvcLevel().equals("1")) {
				tbIotSvcDto.setUpSvcSeq("0");
			}
			tmpTreeStatusDto.setOpened(true);
			tmpTreeDataDto.setParent(tbIotSvcDto.getUpSvcSeq());

			String text;
			if (null != tbIotSvcDto.getDevClsCd()) {
				tmpTreeDataDto.setSelfSeq(tbIotSvcDto.getDevClsCd());
				cmCdDto.setCdId(tbIotSvcDto.getDevClsCd());
				cmCdUDto = cmcdSvc.retrieveIotCmCdByCdId(cmCdDto);

				if (cmCdUDto.getUseYn().equals("Y")) {
					text = tbIotSvcDto.getSvcCdNm() + " [" + tbIotSvcDto.getDevClsCd() + "] (Y)";
				} else if (cmCdUDto.getUseYn().equals("N")) {
					text = tbIotSvcDto.getSvcCdNm() + " [" + tbIotSvcDto.getDevClsCd() + "] (N)";
				} else {
					text = tbIotSvcDto.getSvcCdNm() + " [" + tbIotSvcDto.getDevClsCd() + "] (T)";
				}
			} else {
				tmpTreeDataDto.setSelfSeq(tbIotSvcDto.getSvcCd());
				cmCdDto.setCdId(tbIotSvcDto.getSvcCd());
				cmCdUDto = cmcdSvc.retrieveIotCmCdByCdId(cmCdDto);
				if (cmCdUDto.getUseYn().equals("Y")) {
					text = tbIotSvcDto.getSvcCdNm() + " [" + tbIotSvcDto.getSvcCd() + "]  (Y)";
				} else if (cmCdUDto.getUseYn().equals("N")) {
					text = tbIotSvcDto.getSvcCdNm() + " [" + tbIotSvcDto.getSvcCd() + "]  (N)";
				} else {
					text = tbIotSvcDto.getSvcCdNm() + " [" + tbIotSvcDto.getSvcCd() + "] (T)";
				}
			}
			tmpTreeDto.setId(tbIotSvcDto.getSvcSeq());
			tmpTreeDto.setParent(tbIotSvcDto.getUpSvcSeq());
			tmpTreeDto.setLi_attr(tmpTreeLiAttrDto);
			tmpTreeDto.setText(text);
			tmpTreeDto.setData(tmpTreeDataDto);
			tmpTreeDto.setState(tmpTreeStatusDto);
			returnTreeData.add(tmpTreeDto);
			cmCdUDto = null;
		}
		return returnTreeData;
	}

	@Override
	public HashMap<String, Object> retrieveIotSvcBySeq(TbIotSvcDto svcDto) throws BizException {
		HashMap<String, Object> map = new HashMap<String, Object>();

		// 서비스 data 가져오기
		TbIotSvcDto returnDto = new TbIotSvcDto();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"svcDao.retrieveIotSvcBySeq");
		try {
			returnDto = svcDao.retrieveIotSvcBySeq(svcDto);
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
		// 파일 가져오기
		TbIoTBrdFileDTO fileDto = new TbIoTBrdFileDTO();
		fileDto.setContentSeq(returnDto.getSvcSeq());
		fileDto.setContentType(FileBoardTypeDTO.SVC);

		List<TbIoTBrdFileDTO> returnFile = null;
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"brdFileListDAO.retrieveTbIoTBrdFileList");
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

		// 장비 속성 가져오기
		if (null != returnDto.getDevClsCd()) {
			TbIotDevClsImgDto devClsDto = new TbIotDevClsImgDto();
			devClsDto.setDevClsCd(returnDto.getDevClsCd());
			List<TbIotDevClsImgDto> devImgList = new ArrayList<TbIotDevClsImgDto>();
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "svcDao.retrieveIotDevClsImg");
			try {
				devImgList = svcDao.retrieveIotDevClsImg(devClsDto);
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

			if (devImgList.size() > 0) {
				map.put("imgs", devImgList);
			}
		}

		return map;
	};

	@Override
	public void createIotSvc(HttpServletRequest request, TbIotSvcCombinedDto dto) throws BizException {
		String lgnUsrId = AuthUtils.getUser().getUserId();
		dto.setRegUsrId(lgnUsrId);
		TbIotCmCdCDTO cmcdDto = new TbIotCmCdCDTO();
		cmcdDto.setCdDesc(dto.getCdDesc());
		cmcdDto.setCdId(dto.getCdId());
		cmcdDto.setCdNm(dto.getCdNm());
		cmcdDto.setLangSet(dto.getLangSet());
		cmcdDto.setFirstCdId(dto.getFirstCdId());
		cmcdDto.setLvl(Integer.parseInt(dto.getLvl()));
		cmcdDto.setParentCdId(dto.getParentCdId());
		cmcdDto.setUseYn(dto.getUseYn());

		// 공통코드 서비스 단에서 oms로그 생성함.
		cmcdSvc.createIotCmCd(cmcdDto);

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "svcDao.createIotSvc");
		try {
			svcDao.createIotSvc(dto);
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

		if (null != dto.getDevClsCd()) {
			ArrayList<String[]> fileList = new ArrayList<String[]>();
			// 파일업로드
			fileList = FileUtil.uploadMutilpleFile(request, FILE_UPLOAD_PATH, FILE_MAX_SIZE);
			for (String[] arrStr : fileList) {
				TbIotDevClsImgDto devDto = new TbIotDevClsImgDto();
				String imgCd = null;
				// 파일순서 설정
				TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
				for (String str : arrStr) {
					if (arrStr[1].equals(dto.getDefaultIcon())) {
						imgCd = "GN00000121";
					}
					if (arrStr[1].equals(dto.getErrorIcon())) {
						imgCd = "GN00000122";
					}
					if (arrStr[1].equals(dto.getStopIcon())) {
						imgCd = "GN00000123";
					}
					if (arrStr[1].equals(dto.getGrpDefIcon())) {
						imgCd = "GN00000124";
					}
					if (arrStr[1].equals(dto.getGrpErrIcon())) {
						imgCd = "GN00000125";
					}
					if (arrStr[1].equals(dto.getGrpStpIcon())) {
						imgCd = "GN00000126";
					}
					if (arrStr[1].equals(dto.getMultiIcon())) {
						imgCd = "GN00000127";
					}
					if (arrStr[1].equals(dto.getMultiErrIcon())) {
						imgCd = "GN00000128";
					}
					devDto.setDevClsCd(dto.getDevClsCd());
					devDto.setRegUsrId(lgnUsrId);
					devDto.setWebUri(new StringBuilder()
							.append(FILE_UPLOAD_PATH_IMG)
							.append(arrStr[0].replace(FILE_UPLOAD_PATH, ""))
							.append(File.separator)
							.append(arrStr[2]).toString());
					devDto.setOrgFile(arrStr[1]);
					devDto.setServerFile(arrStr[2]);
					devDto.setIconCd(imgCd);

					tbIoTBrdFileListDTO.setContentSeq(dto.getSvcSeq());
					tbIoTBrdFileListDTO.setContentType(FileBoardTypeDTO.SVC);
					tbIoTBrdFileListDTO.setFilePath(arrStr[0].replace(FILE_UPLOAD_PATH, ""));
					tbIoTBrdFileListDTO.setOrgFileName(arrStr[1]);
					tbIoTBrdFileListDTO.setFileName(arrStr[2]);
					tbIoTBrdFileListDTO.setFileSize(Long.parseLong(arrStr[3]));
					tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());
				}
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"svcDao.createIotDevClsImg");
				try {
					svcDao.createIotDevClsImg(devDto);
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
	}

	@SuppressWarnings("rawtypes")
	@Override
	public HashMap<String, Object> retrieveDevClsList(TbIotSvcMDto tbIotSvcMDto) throws BizException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<TbIotSvcMDto> devClsList = new ArrayList<TbIotSvcMDto>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"svcDao.retrieveDevClsList");
		try {
			devClsList = svcDao.retrieveDevClsList(tbIotSvcMDto);
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

		TbIotSvcDto inputDto = new TbIotSvcDto();
		inputDto.setSvcSeq(tbIotSvcMDto.getSvcSeq());
		inputDto.setLangSet(tbIotSvcMDto.getLangSet());
		TbIotSvcDto svcDto = new TbIotSvcDto();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "svcDao.retrieveIotSvcBySeq");
		try {
			svcDto = svcDao.retrieveIotSvcBySeq(inputDto);
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

		List<TbIotSvcMDto> retrieveSvcMapDevBySvcCd = new ArrayList<TbIotSvcMDto>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "svcDao.retrieveSvcMapDevBySvcCd");
		try {
			retrieveSvcMapDevBySvcCd = svcDao.retrieveSvcMapDevBySvcCd(svcDto);
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

		for (Iterator mapDevIterator = retrieveSvcMapDevBySvcCd.iterator(); mapDevIterator.hasNext();) {
			TbIotSvcMDto mapDevDto = (TbIotSvcMDto) mapDevIterator.next();
			for (Iterator devIterator = devClsList.iterator(); devIterator.hasNext();) {
				TbIotSvcMDto devDto = (TbIotSvcMDto) devIterator.next();
				if (devDto.getDevClsCd().equals(mapDevDto.getDevClsCd())) {
					devDto.setChecked(true);
				}
			}
		}
		resultMap.put("devClsList", devClsList);
		resultMap.put("svcDto", svcDto);
		return resultMap;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void createIotSvcDevMap(TbIotSvcMDto tbIotSvcMDto) throws BizException {
		TbIotSvcDto svcDto = new TbIotSvcDto();
		svcDto.setRegUsrId(AuthUtils.getUser().getUserId());
		svcDto.setSvcCd(tbIotSvcMDto.getSvcCd());
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"svcDao.deleteSvcMapDevCls");
		try {
			svcDao.deleteSvcMapDevCls(tbIotSvcMDto);
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

		List<TbIotSvcMDto> devClsList = tbIotSvcMDto.getDevClsList();
		for (Iterator devClsListIterator = devClsList.iterator(); devClsListIterator.hasNext();) {
			TbIotSvcMDto devClsDto = (TbIotSvcMDto) devClsListIterator.next();
			svcDto.setDevClsCd(devClsDto.getDevClsCd());
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "svcDao.insertSvcMapDevCls");
			try {
				svcDao.insertSvcMapDevCls(svcDto);
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
	public void updateIotSvc(HttpServletRequest request, TbIotSvcCombinedDto dto) throws BizException {
		TbIotCmCdDTO cmCdDTO = new TbIotCmCdDTO();
		cmCdDTO.setCdDesc(dto.getCdDesc());
		cmCdDTO.setCdId(dto.getCdId());
		cmCdDTO.setCdNm(dto.getCdNm());
		cmCdDTO.setLangSet(dto.getLangSet());
		cmCdDTO.setFirstCdId(dto.getFirstCdId());
		cmCdDTO.setParentCdId(dto.getParentCdId());
		cmCdDTO.setUseYn(dto.getUseYn());
		cmCdDTO.setCdSeq(dto.getCdSeq());

		// 공통코드 업데이트
		cmcdSvc.updateIotCmCd(cmCdDTO);

		ComInfoDto temp = null;
		String lgnUsrId = AuthUtils.getUser().getUserId();
		if (null != dto.getDeleteArr()) {
			int deleteLength = dto.getDeleteArr().length;
			// file delete
			if (deleteLength > 0) {
				TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
				for (int i = 0; i < deleteLength; i++) {
					// <select id="selectFileName" parameterType="tbIoTBrdFileListDTO" resultType="tbIoTBrdFileListDTO">

					temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
							"brdFileListDAO.deleteTbIoTBrdFile");
					tbIoTBrdFileListDTO.setFileListSeq(dto.getDeleteArr()[i]);
					fileServiceUtil.deleteDbAndFile(request, FILE_UPLOAD_PATH, tbIoTBrdFileListDTO);
				}
			}
		}

		//
		if (null != dto.getDevClsCd()) {
			String imgCd = null;
			TbIotDevClsImgDto devDto = new TbIotDevClsImgDto();
			devDto.setDevClsCd(dto.getDevClsCd());
			devDto.setModUsrId(lgnUsrId);

			if (Arrays.asList(dto.getDeleteCdList()).contains("defIcon")) {
//			if (null == dto.getDefaultIcon()) {
				imgCd = "GN00000121";
				devDto.setIconCd(imgCd);
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"svcDao.deleteIotDevClsImg");
				try {
					svcDao.deleteIotDevClsImg(devDto);
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
			if (Arrays.asList(dto.getDeleteCdList()).contains("stpIcon")) {
				imgCd = "GN00000123";
				devDto.setIconCd(imgCd);
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"svcDao.deleteIotDevClsImg");
				try {
					svcDao.deleteIotDevClsImg(devDto);
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
			if (Arrays.asList(dto.getDeleteCdList()).contains("errIcon")) {
				imgCd = "GN00000122";
				devDto.setIconCd(imgCd);

				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"svcDao.deleteIotDevClsImg");
				try {
					svcDao.deleteIotDevClsImg(devDto);
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
			if (Arrays.asList(dto.getDeleteCdList()).contains("grpDefIcon")) {
				imgCd = "GN00000124";
				devDto.setIconCd(imgCd);

				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"svcDao.deleteIotDevClsImg");
				try {
					svcDao.deleteIotDevClsImg(devDto);
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
			if (Arrays.asList(dto.getDeleteCdList()).contains("grpErrIcon")) {
				imgCd = "GN00000125";
				devDto.setIconCd(imgCd);

				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"svcDao.deleteIotDevClsImg");
				try {
					svcDao.deleteIotDevClsImg(devDto);
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
			if (Arrays.asList(dto.getDeleteCdList()).contains("grpStpIcon")) {
				imgCd = "GN00000126";
				devDto.setIconCd(imgCd);

				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"svcDao.deleteIotDevClsImg");
				try {
					svcDao.deleteIotDevClsImg(devDto);
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
			if (Arrays.asList(dto.getDeleteCdList()).contains("multiIcon")) {
				imgCd = "GN00000127";
				devDto.setIconCd(imgCd);

				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"svcDao.deleteIotDevClsImg");
				try {
					svcDao.deleteIotDevClsImg(devDto);
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
			if (Arrays.asList(dto.getDeleteCdList()).contains("multiErrIcon")) {
				imgCd = "GN00000128";
				devDto.setIconCd(imgCd);

				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"svcDao.deleteIotDevClsImg");
				try {
					svcDao.deleteIotDevClsImg(devDto);
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

		if (null != dto.getDevClsCd()) {
			ArrayList<String[]> fileList = new ArrayList<String[]>();
			// 파일업로드
			fileList = FileUtil.uploadMutilpleFile(request, FILE_UPLOAD_PATH, FILE_MAX_SIZE);
			for (Iterator iterator = fileList.iterator(); iterator.hasNext();) {
				String[] strings = (String[]) iterator.next();
				TbIotDevClsImgDto devDto = new TbIotDevClsImgDto();
				String imgCd = null;
				// 파일순서 설정
				TbIoTBrdFileListDTO tbIoTBrdFileListDTO = new TbIoTBrdFileListDTO();
				for (String str : strings) {
					if (strings[1].equals(dto.getDefaultIcon())) {
						imgCd = "GN00000121";
					} else if (strings[1].equals(dto.getErrorIcon())) {
						imgCd = "GN00000122";
					} else if (strings[1].equals(dto.getStopIcon())) {
						imgCd = "GN00000123";
					} else if (strings[1].equals(dto.getGrpDefIcon())) {
						imgCd = "GN00000124";
					} else if (strings[1].equals(dto.getGrpErrIcon())) {
						imgCd = "GN00000125";
					} else if (strings[1].equals(dto.getGrpStpIcon())) {
						imgCd = "GN00000126";
					} else if (strings[1].equals(dto.getMultiIcon())) {
						imgCd = "GN00000127";
					} else if (strings[1].equals(dto.getMultiErrIcon())) {
						imgCd = "GN00000128";
					}

					devDto.setDevClsCd(dto.getDevClsCd());
					devDto.setRegUsrId(lgnUsrId);
					devDto.setWebUri(new StringBuilder()
							.append(FILE_UPLOAD_PATH_IMG)
							.append(strings[0].replace(FILE_UPLOAD_PATH, ""))
							.append(File.separator)
							.append(strings[2]).toString());
					devDto.setOrgFile(strings[1]);
					devDto.setServerFile(strings[2]);
					devDto.setIconCd(imgCd);

					tbIoTBrdFileListDTO.setContentSeq(dto.getSvcSeq());
					tbIoTBrdFileListDTO.setContentType(FileBoardTypeDTO.SVC);
					tbIoTBrdFileListDTO.setFilePath(strings[0].replace(FILE_UPLOAD_PATH, ""));
					tbIoTBrdFileListDTO.setOrgFileName(strings[1]);
					tbIoTBrdFileListDTO.setFileName(strings[2]);
					tbIoTBrdFileListDTO.setFileSize(Long.parseLong(strings[3]));
					tbIoTBrdFileListDTO.setRegUsrId(AuthUtils.getUser().getUserId());
				}
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"svcDao.createIotDevClsImg");
				try {
					svcDao.createIotDevClsImg(devDto);
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

			// 마스트 영역에 이름 UPDATE
			if (null != cmCdDTO.getCdId() && null != cmCdDTO.getCdNm()) {
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,	"iotDevDAO.updateDlsCdNm");

				TbIotDevDTO tbIotDevDTO = new TbIotDevDTO();
				tbIotDevDTO.setDevClsCd(cmCdDTO.getCdId());
				tbIotDevDTO.setDevClsCdNm(cmCdDTO.getCdNm());
				tbIotDevDTO.setModUserId(AuthUtils.getUser().getUserId());

				try {
					iotDevDAO.updateDlsCdNm(tbIotDevDTO);
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
	public TbIotSvcDto retrieveIotSvcOnly() throws BizException {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"svcDao.retrieveIotSvcOnly");
		TbIotSvcDto returnDto = null;
		TbIotSvcDto paramDto = new TbIotSvcDto();
		paramDto.setSvcCd(AuthUtils.getUser().getSvcCd());
		paramDto.setLangSet(ReqContextComponent.getComInfoDto().getXlang());
		try {
			returnDto = svcDao.retrieveIotSvcOnly(paramDto);
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
	public HashMap<String, Object> getServiceDevCls(TbIotSvcMDto tbIotSvcMDto) throws BizException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String newLang = c.getXlang();
		List<TbIotSvcMDto> devClsList = new ArrayList<TbIotSvcMDto>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"svcDao.retrieveDevClsList");
//		tbIotSvcMDto.setCharSet(newLang);
		tbIotSvcMDto.setLangSet(newLang);
		try {
			devClsList = svcDao.getServiceDevCls(tbIotSvcMDto);
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
		resultMap.put("devClsList", devClsList);
		return resultMap;
	}

	@Override
	public List<HashMap<String, Object>> retrieveIotCmCdForSvc() throws BizException {
		List<HashMap<String, Object>> resultMap = new ArrayList<HashMap<String, Object>>();
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		TbIotSvcMDto tbIotSvcMDto = new TbIotSvcMDto();
		String newLang = c.getXlang();

		if (newLang == null || newLang.length() == 0 ) newLang = "ko";


		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 	"svcDao.retrieveIotCmCdForSvc");

		tbIotSvcMDto.setLangSet(newLang);
		try {
			resultMap = svcDao.retrieveIotCmCdForSvc(tbIotSvcMDto);
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

		return resultMap;
	}

	@Override
	public List<HashMap<String, Object>> retrieveIotCmCdForDevDlsCd(TbIotSvcMDto tbIotSvcMDto) throws BizException {
		List<HashMap<String, Object>> resultMap = new ArrayList<HashMap<String, Object>>();
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String newLang = c.getXlang();
		if (newLang == null || newLang.length() == 0 ) newLang = "ko";
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 	"svcDao.retrieveIotCmCdForDevDlsCd");

		tbIotSvcMDto.setLangSet(newLang);
		try {
			resultMap = svcDao.retrieveIotCmCdForDevDlsCd(tbIotSvcMDto);
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

		return resultMap;
	}
}
