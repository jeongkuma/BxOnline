package kr.co.scp.common.tmpl.ctrl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.FileUtils;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustCombineDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustDTO;
import kr.co.scp.ccp.iotCust.dto.TbIotCustUDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevRegiDTO;
import kr.co.scp.common.api.dto.TbIotApiDTO;
import kr.co.scp.common.menu.dto.CopyTbIotMenuDto;
import kr.co.scp.common.prog.dto.TbIotProgDTO;
import kr.co.scp.common.tmpl.dto.CopyJqGridDto;
import kr.co.scp.common.tmpl.dto.TbIotJqDataRequestDTO;
import kr.co.scp.common.tmpl.dto.TbIotJqDataResponseDTO;
import kr.co.scp.common.tmpl.dto.TbIotTmplDTO;
import kr.co.scp.common.tmpl.dto.TbIotTmplHdrJqgridDTO;
import kr.co.scp.common.tmpl.svc.TbIotTmplSVC;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.WebCommUtil;
import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@Slf4j
@RestController
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/com/tmpl")
public class TbIotTmplCTL {

	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;

	@Autowired
	private TbIotTmplSVC tbIotTmplSvc;

	@Autowired
	private ResponseComUtil responseComUtil;

	@Autowired
	private ObjectMapper objectMapper = null;

	@Value("${file.upload-dir-dashboard}")
	private String FILE_UPLOAD_PATH;


	/**
	 * 화면 Tmpl 정보를 조회 한다. 화면에서 언어셋을 해더에 받아서 언어셋에 맞는 Tmpl을 돌려 준다.
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getIotTmpl", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public ComResponseDto<?> retrieveTbIotTmpl(HttpServletRequest request,
												@RequestBody TbIotTmplDTO tbIotTmplDto) throws BizException {
		log.debug("====== TmplVERSION 1");
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> tbIotTmplList = tbIotTmplSvc.retrieveTbIotTmplList(tbIotTmplDto);
		return responseComUtil.setResponse200ok(tbIotTmplList);
	}

	/**
	 * 템플릿 코드 아이디 조회
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getIotTmplCdId", method = RequestMethod.POST)
	public ComResponseDto<?> getIotTmplCdId(HttpServletRequest request,
												@RequestBody TbIotTmplDTO tbIotTmplDto) throws BizException {
		String reTmplCdId = tbIotTmplSvc.retrieveTbIotTmplCdId(tbIotTmplDto);
		TbIotTmplDTO reIotTmplDto = new TbIotTmplDTO();
		reIotTmplDto.setTmplCdId(reTmplCdId);
		return responseComUtil.setResponse200ok(reIotTmplDto);
	}

	/**
	 * 화면 Tmpl 정보를 조회 한다. 화면에서 언어셋을 해더에 받아서 언어셋에 맞는 Tmpl을 돌려 준다.
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getIotJqgridTmpl", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public ComResponseDto<?> getIotJqGridTmpl(HttpServletRequest request,
												@RequestBody TbIotTmplDTO tbIotTmplDto) throws BizException {
		log.debug("====== TmplVERSION 1");
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> tbIotTmplList = tbIotTmplSvc.retrieveTbIotJqgridTmplList(tbIotTmplDto);
		return responseComUtil.setResponse200ok(tbIotTmplList);
	}

	/**
	 * 화면 Tmpl 정보를 조회 한다. 화면에서 언어셋을 해더에 받아서 언어셋에 맞는 Tmpl을 돌려 준다.
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getIotTmplDetail", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotTmplDetail(HttpServletRequest request, @RequestBody TbIotTmplDTO tbIotTmplDto) throws BizException {
		HashMap<String, Object> tbIotUseDTORn = tbIotTmplSvc.retrieveTbIotTmplDetail(tbIotTmplDto);
		return responseComUtil.setResponse200ok(tbIotUseDTORn);
	}

//	@RequestMapping(value = "/getIotTmplDetail", method = RequestMethod.POST, headers="X-APIVERSION=1")
//	public ComResponseDto<?> retrieveTbIotTmplDetail(HttpServletRequest request,
//												@RequestBody TbIotTmplDTO tbIotTmplDto) throws BizException {
//		log.debug("====== TmplVERSION 1");
//		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
//		TbIotTmplDTO tbIotTmplList = tbIotTmplSvc.retrieveTbIotTmplDetail(tbIotTmplDto);
//		return responseComUtil.setResponse200ok(tbIotTmplList);
//	}

	/**
	 * 로고 이미지 파일 다운로드
	 * @param request
	 * @param tbIotCustDto
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/downloadTmplImg", method = RequestMethod.POST)
	public void downloadTmplImg(HttpServletRequest request, @RequestBody TbIoTBrdFileListDTO tbIoTBrdFileDTO) {
		TbIoTBrdFileListDTO tbIoTBrdFileVaule = tbIotTmplSvc.getFile(tbIoTBrdFileDTO);
		ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletResponse response = servletContainer.getResponse();

		FileUtils.fileDownload(request, response, FILE_UPLOAD_PATH, tbIoTBrdFileVaule.getFilePath() + File.separator + tbIoTBrdFileVaule.getFileName(), tbIoTBrdFileVaule.getOrgFileName());

		//return response;
	}

	/**
	 * jqgrid 데이터 조회
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getJqgridData", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveJqgrid(HttpServletRequest request,
												@RequestBody TbIotTmplHdrJqgridDTO tbJqgridDto) throws BizException {
		log.debug("====== TmplVERSION 1");
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotTmplHdrJqgridDTO> tbIotGqgirdList = tbIotTmplSvc.retrieveJqgrid(tbJqgridDto);
		return responseComUtil.setResponse200ok(tbIotGqgirdList);
	}


	/**
	 * 복사
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/copyIotTmpl", method = RequestMethod.POST)
	public ComResponseDto<?>copyJqgrid(HttpServletRequest request,
												@RequestBody TbIotTmplDTO tbIotTmplDto) throws BizException {
		log.debug("====== TmplVERSION 1");
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		int chkCnt = 0;
		TbIotTmplHdrJqgridDTO chkDto = new TbIotTmplHdrJqgridDTO();
		chkDto.setLangSet(tbIotTmplDto.getTbIotTmplHdrJqgridList().get(0).getLangSet());
		chkDto.setTmplSeq(tbIotTmplDto.getTbIotTmplHdrJqgridList().get(0).getTmplSeq());
		chkDto.setDevClsCd(tbIotTmplDto.getTbIotTmplHdrJqgridList().get(0).getDevClsCd());
		chkCnt = tbIotTmplSvc.duplicationCharSet(chkDto);

		if(chkCnt > 0) {
			throw new BizException("duplicatedCharSet");
		}else {
			tbIotTmplSvc.copyJqgrid(tbIotTmplDto);
		}

		return responseComUtil.setResponse200ok();
	}

	/**
	 * Dashboard 설정 템플릿 목록 조회
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getIotDashboardTmpl", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public ComResponseDto<?> retrieveTbIotDashboardTmpl(HttpServletRequest request,
												@RequestBody TbIotTmplDTO tbIotTmplDto) throws BizException {
		log.debug("====== TmplVERSION 1");
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotTmplDTO> tbIotTmplList = tbIotTmplSvc.retrieveTbIotDashboardTmplList(tbIotTmplDto);
		return responseComUtil.setResponse200ok(tbIotTmplList);
	}



	/**
	 * 화면 Tmpl 정보를 등록 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/insertIotJqGridTmpl", method = RequestMethod.POST)
	public ComResponseDto<?> insertIotJqGridTmpl(HttpServletRequest request,
			 								 @RequestBody TbIotTmplDTO tbIotTmplDto) throws BizException {
		log.debug("====== insertTbIotTmpl === ");
		tbIotTmplSvc.insertIotJqGridTmpl(tbIotTmplDto);
		return responseComUtil.setResponse200ok();
	}
	@RequestMapping(value = "/insertIotTmpl", method = RequestMethod.POST)
	public ComResponseDto<?> insertTbIotTmpl(HttpServletRequest request, @RequestParam String jsonData) throws BizException {

		try {
			TbIotTmplDTO tbIotTmplDto = (TbIotTmplDTO) WebCommUtil.stringToObject(jsonData, TbIotTmplDTO.class);
			tbIotTmplSvc.insertTbIotTmpl(tbIotTmplDto, request);
		} catch (UnrecognizedPropertyException e) {
			throw new BizException("ObjectMapperError");
		} catch (IOException e) {
			throw new BizException("fileUploadError");
		}

		return responseComUtil.setResponse200ok();
	}

	/**
	 * 화면 Tmpl 정보를 수정 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/updateIotJqGridTmpl", method = RequestMethod.POST)
	public ComResponseDto<?> updateIotJqGridTmpl(HttpServletRequest request,
			 								 @RequestBody TbIotTmplDTO tbIotTmplDto) throws BizException {
		log.debug("====== updateTbIotTmpl === ");
		tbIotTmplSvc.updateIotJqGridTmpl(tbIotTmplDto);
		return responseComUtil.setResponse200ok();

	}
	@RequestMapping(value = "/updateIotTmpl", method = RequestMethod.POST)
	public ComResponseDto<?> updateIotTmpl(HttpServletRequest request, @RequestParam String jsonData) throws BizException {

		try {
			TbIotTmplDTO tbIotTmplDto = (TbIotTmplDTO) WebCommUtil.stringToObject(jsonData, TbIotTmplDTO.class);
			tbIotTmplSvc.updateTbIotTmpl(request, tbIotTmplDto);
		} catch (UnrecognizedPropertyException e) {
			throw new BizException("ObjectMapperError");
		} catch (IOException e) {
			throw new BizException("fileUploadError");
		}
		return responseComUtil.setResponse200ok();
	}

	/**
	 * 화면 Tmpl 삭제 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/deleteIotTmpl", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public ComResponseDto<?> deleteTbIotTmpl(HttpServletRequest request,
			 							   @RequestBody TbIotTmplDTO tbIotTmplDto) throws BizException {
		log.debug("====== deleteTbIotTmpl === ");
		tbIotTmplSvc.deleteTbIotTmpl(tbIotTmplDto);
		return responseComUtil.setResponse200ok();

	}

	/**
	 * JQ Grid 에 있는 Data 조회
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveJqData", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveJqData(HttpServletRequest request,
			 							   @RequestBody TbIotJqDataRequestDTO tbIotJqDataRequestDto) throws BizException {
		log.debug("====== deleteTbIotTmpl === ");
		List<TbIotJqDataResponseDTO> tbIotJqDataResponse = new ArrayList<TbIotJqDataResponseDTO>();
//		try {
			if(AuthUtils.getUser() != null ) {
				tbIotJqDataRequestDto.setCustSeq(AuthUtils.getUser().getCustSeq());
			}
			tbIotJqDataResponse =  tbIotTmplSvc.retrieveJqData(tbIotJqDataRequestDto);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		return responseComUtil.setResponse200ok(tbIotJqDataResponse);

	}

	/**
	 * 마법사 설정 화면에 왼쪽 이미지 표시
	 * tmplGubun
	 *
	 * tmplRuleImg
	 * tmplCdId
	 * tmplCdNm
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getIotTmplRule", method = RequestMethod.POST, headers="X-APIVERSION=1")
	public ComResponseDto<?> retrieveTbIotTmplRule(HttpServletRequest request,
												@RequestBody TbIotTmplDTO tbIotTmplDto) throws BizException {
		log.debug("====== TmplVERSION 1");
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotTmplDTO> tbIotTmplList = tbIotTmplSvc.retrieveTbIotTmplRule(tbIotTmplDto);
		return responseComUtil.setResponse200ok(tbIotTmplList);
	}

	/**
	 * 화면 템플릿Id 중복확인
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/duplicationCheckIotTmplId", method = RequestMethod.POST)
	public ComResponseDto<?> duplicationCheckIotTmplId(HttpServletRequest request,
												@RequestBody TbIotTmplDTO tbIotTmplDto) throws BizException {
		log.debug("====== duplicationCheckIotApi === ");
		int chkCnt = 0;
		chkCnt = tbIotTmplSvc.duplicationCheck(tbIotTmplDto);

		TbIotTmplDTO returnObj = new TbIotTmplDTO();
		returnObj.setChkCnt(chkCnt);

		if(chkCnt>0) {
			throw new BizException("duplicatedTmplId");
		}else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}

		return responseComUtil.setResponse200ok(returnObj);
	}

	/**
	 * Jqgrid 복사(장비 유형)
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/copydevClsJqGrid", method = RequestMethod.POST)
	public ComResponseDto<?> copydevClsJqGrid(HttpServletRequest request, @RequestBody CopyJqGridDto copyJqGridDto) throws BizException {
		log.debug("====== copyDevClsIotMenu === ");
		int chkCnt = 0;
		copyJqGridDto.setLangSet(copyJqGridDto.getOrgLangSet());
		chkCnt = tbIotTmplSvc.duplicationCheckCopy(copyJqGridDto);

		if(chkCnt>0) {
			throw new BizException("existInpormation");
		}else {
			tbIotTmplSvc.copyJqTmplGrid(copyJqGridDto);
		}
		return responseComUtil.setResponse200ok();
	}

	/**
	 * Jqgrid 복사(장비 유형)
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/copyCharSetJqGrid", method = RequestMethod.POST)
	public ComResponseDto<?> copyCharSetJqGrid(HttpServletRequest request, @RequestBody CopyJqGridDto copyJqGridDto) throws BizException {
		log.debug("====== copyDevClsIotMenu === ");
		int chkCnt = 0;
		copyJqGridDto.setDevClsCd(copyJqGridDto.getOrgDevClsCd());
		chkCnt = tbIotTmplSvc.duplicationCheckCopy(copyJqGridDto);

		if(chkCnt>0) {
			throw new BizException("existInpormation");
		}else {
			tbIotTmplSvc.copyJqTmplGrid(copyJqGridDto);
		}
		return responseComUtil.setResponse200ok();
	}


}
