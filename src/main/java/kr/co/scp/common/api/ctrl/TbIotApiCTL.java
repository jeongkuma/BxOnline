package kr.co.scp.common.api.ctrl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.common.api.dto.TbIotApiDTO;
import kr.co.scp.common.api.dto.TbIotParamDTO;
import kr.co.scp.common.api.svc.TbIotApiSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/online/com/api")
public class TbIotApiCTL {

	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;

	@Autowired
	private TbIotApiSVC tbIotApiSvc;

	@Autowired
	private ResponseComUtil responseComUtil;


	/**
	 * 화면 API 정보를 조회 한다. 화면에서 언어셋을 해더에 받아서 언어셋에 맞는 API을 돌려 준다.
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getIotApi", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotApi(HttpServletRequest request,
												@RequestBody TbIotApiDTO tbIotApiDto) throws BizException {
		log.debug("====== APIVERSION 1");
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> tbIotApiList = tbIotApiSvc.retrieveTbIotApiList(tbIotApiDto);
		return responseComUtil.setResponse200ok(tbIotApiList);
	}

	/**
	 * 화면 API 정보를 조회 한다. 화면에서 언어셋을 해더에 받아서 언어셋에 맞는 API을 돌려 준다.
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getIotDashApi", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotDashApi(HttpServletRequest request,
												@RequestBody TbIotApiDTO tbIotApiDto) throws BizException {
		log.debug("====== APIVERSION 1");
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> tbIotApiList = tbIotApiSvc.retrieveTbIotDashApiList(tbIotApiDto);
		return responseComUtil.setResponse200ok(tbIotApiList);
	}

	/**
	 * 수정 화면 API/Param 정보를 조회 한다.
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getIotApiParam", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotApiParam(HttpServletRequest request,
												@RequestBody TbIotApiDTO tbIotApiDto) throws BizException {
		log.debug("====== APIVERSION 1");
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		TbIotApiDTO tbIotApiList = tbIotApiSvc.searchTbIotApiParam(tbIotApiDto);
		return responseComUtil.setResponse200ok(tbIotApiList);
	}


	/**
	 * 화면 API명 중복확인
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/duplicationCheckIotApiNm", method = RequestMethod.POST)
	public ComResponseDto<?> duplicationCheckIotApiNm(HttpServletRequest request,
											@RequestBody TbIotApiDTO tbIotApiDto) throws BizException {
		log.debug("====== duplicationCheckIotApi === ");
		int chkCnt = 0;
		chkCnt = tbIotApiSvc.duplicationCheckApiNm(tbIotApiDto);

		TbIotApiDTO returnObj = new TbIotApiDTO();
		returnObj.setChkCnt(chkCnt);

		if(chkCnt>0) {
			throw new BizException("duplicatedApiNm");
		}else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}

		return responseComUtil.setResponse200ok(returnObj);
	}

	/**
	 * 화면 URi 중복확인
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/duplicationCheckIotApiUri", method = RequestMethod.POST)
	public ComResponseDto<?> duplicationCheckIotApiUri(HttpServletRequest request,
											@RequestBody TbIotApiDTO tbIotApiDto) throws BizException {
		log.debug("====== duplicationCheckIotApi === ");
		int chkCnt = 0;
		chkCnt = tbIotApiSvc.duplicationCheckApiUri(tbIotApiDto);

		TbIotApiDTO returnObj = new TbIotApiDTO();
		returnObj.setChkCnt(chkCnt);

		if(chkCnt>0) {
			throw new BizException("duplicatedURI");
		}else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}

		return responseComUtil.setResponse200ok(returnObj);
	}


	/**
	 * 화면 API 정보를 등록 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/insertIotApi", method = RequestMethod.POST)
	public ComResponseDto<?> insertTbIotApi(HttpServletRequest request,
											@RequestBody TbIotApiDTO tbIotApiDto) throws BizException {
		log.debug("====== insertTbIotApi === ");
		tbIotApiSvc.insertTbIotApi(tbIotApiDto);
		return responseComUtil.setResponse200ok();

	}

	/**
	 * 화면 API 정보를 수정 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/updateIotApi", method = RequestMethod.POST)
	public ComResponseDto<?> updateTbIotApi(HttpServletRequest request,
											@RequestBody TbIotApiDTO tbIotApiDto) throws BizException {
		log.debug("====== updateTbIotApi === ");
		tbIotApiSvc.updateTbIotApi(tbIotApiDto);
		return responseComUtil.setResponse200ok();
	}

	/**
	 * 화면 API 삭제 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/deleteIotApi", method = RequestMethod.POST)
	public ComResponseDto<?> deleteTbIotApi(HttpServletRequest request,
											@RequestBody TbIotApiDTO tbIotApiDto) throws BizException {
		log.debug("====== deleteTbIotApi === ");
		tbIotApiSvc.deleteTbIotApi(tbIotApiDto);
		return responseComUtil.setResponse200ok();

	}

	/**
	 * 수정 화면 API/Param 정보를 조회 한다.
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveTbIotParamList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotParamList(HttpServletRequest request,
												@RequestBody TbIotParamDTO tbIotParamDto) throws BizException {
		log.debug("====== APIVERSION 1");
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotParamDTO> tbIotApiParamList = tbIotApiSvc.retrieveTbIotParamList(tbIotParamDto);
		return responseComUtil.setResponse200ok(tbIotApiParamList);
	}

	/**
	 * 화면 API 정보를 조회 한다. 화면에서 언어셋을 해더에 받아서 언어셋에 맞는 API을 돌려 준다.
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveTbIotParamCd", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotParamCd(HttpServletRequest request,
												@RequestBody TbIotParamDTO tbIotParamDTO) throws BizException {
		log.debug("====== APIVERSION 1");
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<HashMap<String, Object>> tbIotApiList = tbIotApiSvc.retrieveTbIotParamCd(tbIotParamDTO);
		return responseComUtil.setResponse200ok(tbIotApiList);
	}
}
