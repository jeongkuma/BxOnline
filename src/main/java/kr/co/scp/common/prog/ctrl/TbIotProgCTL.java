package kr.co.scp.common.prog.ctrl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.common.api.dto.TbIotApiDTO;
import kr.co.scp.common.prog.dto.TbIotAuthProgDTO;
import kr.co.scp.common.prog.dto.TbIotProgDTO;
import kr.co.scp.common.prog.svc.TbIotProgSVC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/online/com/prog")

public class TbIotProgCTL {

	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;

	@Autowired
	private TbIotProgSVC tbIotProgSvc;

	@Autowired
	private ResponseComUtil responseComUtil;


	/**
	 * 화면 프로그램 정보를 조회 한다. 화면에서 언어셋을 해더에 받아서 언어셋에 맞는 프로그램을 돌려 준다.
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getIotProg", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotProg(HttpServletRequest request, @RequestBody TbIotProgDTO tbIotProgDto) throws BizException {
		HashMap<String, Object> tbIotProgList = tbIotProgSvc.retrieveTbIotProgList(tbIotProgDto);
		return responseComUtil.setResponse200ok(tbIotProgList);
	}

	/**
	 * 화면 프로그램 정보를 조회 한다. 화면에서 언어셋을 해더에 받아서 언어셋에 맞는 프로그램을 돌려 준다.
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getIotProgDetail", method = RequestMethod.POST)
	public ComResponseDto<?> searchTbIotProg(HttpServletRequest request,
												@RequestBody TbIotProgDTO tbIotProgDto) throws BizException {
//		log.debug("====== ProgVERSION 1");
//		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		TbIotProgDTO tbIotProgList = tbIotProgSvc.searchTbIotProgData(tbIotProgDto);
		return responseComUtil.setResponse200ok(tbIotProgList);
//		return responseComUtil.setResponse200ok();
	}



	/**
	 * 화면 프로그램 정보를 등록 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/insertIotProg", method = RequestMethod.POST)
	public ComResponseDto<?> insertTbIotProg(HttpServletRequest request,
			 								 @RequestBody TbIotProgDTO tbIotProgDto) throws BizException {
//		log.debug("====== insertTbIotProg === ");
		tbIotProgSvc.insertTbIotProg(tbIotProgDto);
		return responseComUtil.setResponse200ok();

	}

	/**
	 * 화면 프로그램 정보를 수정 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/updateIotProg", method = RequestMethod.POST)
	public ComResponseDto<?> updateTbIotProg(HttpServletRequest request,
			 								 @RequestBody TbIotProgDTO tbIotProgDto) throws BizException {
//		log.debug("====== updateTbIotProg === ");
		tbIotProgSvc.updateTbIotProg(tbIotProgDto);
		return responseComUtil.setResponse200ok();

	}

	/**
	 * 화면 프로그램 삭제 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/deleteIotProg", method = RequestMethod.POST)
	public ComResponseDto<?> deleteTbIotProg(HttpServletRequest request,
			 							   @RequestBody TbIotProgDTO tbIotProgDto) throws BizException {
//		log.debug("====== deleteTbIotProg === ");
		tbIotProgSvc.deleteTbIotProg(tbIotProgDto);
		return responseComUtil.setResponse200ok();

	}


	/**
	 * 화면 프로그램Id 중복확인
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/duplicationCheckIotProgId", method = RequestMethod.POST)
	public ComResponseDto<?> duplicationCheckIotProgId(HttpServletRequest request,
											@RequestBody TbIotProgDTO tbIotProgDto) throws BizException {
		log.debug("====== duplicationCheckIotApi === ");
		int chkCnt = 0;
//		chkCnt = tbIotProgSvc.duplicationCheck(tbIotProgDto);

		TbIotApiDTO returnObj = new TbIotApiDTO();
		returnObj.setChkCnt(chkCnt);

		if(chkCnt>0) {
			throw new BizException("duplicatedProgId");
		}else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}

		return responseComUtil.setResponse200ok(returnObj);
	}

	/**
	 * 화면 프로그램Id 자동생성
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/autoIotProgId", method = RequestMethod.POST)
	public ComResponseDto<?> autoIotProgId(HttpServletRequest request,
											@RequestBody TbIotProgDTO tbIotProgDto) throws BizException {
		log.debug("====== autoIotProgId === ");
		HashMap<String, Object> tbIotProg = tbIotProgSvc.autoIotProgId(tbIotProgDto);

		return responseComUtil.setResponse200ok(tbIotProg);
//		return responseComUtil.setResponse200ok();
	}

	/**
	 * 라우터(uipath)중복확인
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/duplicationCheckIotProgUiPath", method = RequestMethod.POST)
	public ComResponseDto<?> duplicationCheckIotProgUiPath(HttpServletRequest request,
											@RequestBody TbIotProgDTO tbIotProgDto) throws BizException {
		log.debug("====== duplicationCheckIotApi === ");
		int chkCnt = 0;
		chkCnt = tbIotProgSvc.duplicationCheck(tbIotProgDto);

		TbIotApiDTO returnObj = new TbIotApiDTO();
		returnObj.setChkCnt(chkCnt);

		if(chkCnt>0) {
			throw new BizException("duplicatedUiPath");
		}else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}

		return responseComUtil.setResponse200ok(returnObj);
	}

	/**
	 * fullpath(progpath)중복확인
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/duplicationCheckIotProgPath", method = RequestMethod.POST)
	public ComResponseDto<?> duplicationCheckIotProgPath(HttpServletRequest request,
											@RequestBody TbIotProgDTO tbIotProgDto) throws BizException {
		log.debug("====== duplicationCheckIotApi === ");
		int chkCnt = 0;
		chkCnt = tbIotProgSvc.duplicationCheck(tbIotProgDto);

		TbIotApiDTO returnObj = new TbIotApiDTO();
		returnObj.setChkCnt(chkCnt);

		if(chkCnt>0) {
			throw new BizException("duplicatedProgUri");
		}else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}

		return responseComUtil.setResponse200ok(returnObj);
	}

	/**
	 * 권한 프로그램 조회
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveAuthProgList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveAuthProgList(HttpServletRequest request, @RequestBody TbIotAuthProgDTO tbIotAuthProgDto) throws BizException {
//		List<TbIotAuthProgDTO> authProgList = tbIotProgSvc.retrieveAuthProgList(tbIotAuthProgDto);
//		return responseComUtil.setResponse200ok(authProgList);
		return responseComUtil.setResponse200ok();
	}

}
