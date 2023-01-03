package kr.co.scp.common.cse.ctrl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.JsonUtils;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.common.api.dto.TbIotApiDTO;
import kr.co.scp.common.cse.svc.TbIotCseSVC;
//import kr.co.scp.onem2m.common.svc.OneM2MService;
//import kr.co.scp.onem2m.svcsvr.dto.TbIoTOm2mSvcSvrDTO;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/online/com/cse")
public class TbIotCseCTL {

	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;

	@Autowired
	private TbIotCseSVC tbIotCseSvc;

	@Autowired
	private ResponseComUtil responseComUtil;

//	@Autowired
//	private OneM2MService om2mSvc;

	/**
	 * remoteCSE 목록을 조회한다
	 * 
	 * @param request
	 * @param tbIotCseDto
	 * @return
	 * @throws BizException
	 */
	@PostMapping("/getIotCse")
	public ComResponseDto<?> retrieveTbIotCse(HttpServletRequest request) throws BizException {
		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
		log.debug("====== " + comInfoDto.toString());
//		TbIoTOm2mSvcSvrDTO tbIotCseDTO = JsonUtils.fromJson(comInfoDto.getBodyString(), TbIoTOm2mSvcSvrDTO.class);
//		HashMap<String, Object> tbIotCseList = tbIotCseSvc.retrieveTbIotCseList(tbIotCseDTO);
//		log.debug("====== " + tbIotCseList.toString());
//		return responseComUtil.setResponse200ok(tbIotCseList);
		return responseComUtil.setResponse200ok();
	}

	@PostMapping("/getIotCseDetail")
	public ComResponseDto<?> searchTbIotCse(HttpServletRequest request) throws BizException {
		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
		log.debug("====== " + comInfoDto.toString());
//		TbIoTOm2mSvcSvrDTO tbIotCseDTO = JsonUtils.fromJson(comInfoDto.getBodyString(), TbIoTOm2mSvcSvrDTO.class);
//		TbIoTOm2mSvcSvrDTO tbIotCseList = tbIotCseSvc.retrieveTbIotCse(tbIotCseDTO);
//		return responseComUtil.setResponse200ok(tbIotCseList);
		return responseComUtil.setResponse200ok();
	}

	@PostMapping("/insertIotCse")
	public ComResponseDto<?> insertTbIotProg(HttpServletRequest request) throws BizException {
		log.debug("====== insertTbIotCse === ");
//		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
//		TbIoTOm2mSvcSvrDTO tbIotCseDTO = JsonUtils.fromJson(comInfoDto.getBodyString(), TbIoTOm2mSvcSvrDTO.class);
//		tbIotCseSvc.insertTbIotCse(tbIotCseDTO);
		return responseComUtil.setResponse200ok();

	}

	@PostMapping("/updateIotCse")
	public ComResponseDto<?> updateTbIotProg(HttpServletRequest request) throws BizException {
		log.debug("====== updateTbIotCse === ");
//		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
//		TbIoTOm2mSvcSvrDTO tbIotCseDTO = JsonUtils.fromJson(comInfoDto.getBodyString(), TbIoTOm2mSvcSvrDTO.class);
//		tbIotCseSvc.updateTbIotCse(tbIotCseDTO);
		return responseComUtil.setResponse200ok();

	}

	@PostMapping("/deleteIotCse")
	public ComResponseDto<?> deleteTbIotProg(HttpServletRequest request) throws BizException {
		log.debug("====== deleteTbIotCse === ");
//		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
//		TbIoTOm2mSvcSvrDTO tbIotCseDTO = JsonUtils.fromJson(comInfoDto.getBodyString(), TbIoTOm2mSvcSvrDTO.class);
//		tbIotCseSvc.deleteTbIotCse(tbIotCseDTO);
		return responseComUtil.setResponse200ok();

	}

	@PostMapping("/duplicationCheckIotCse")
	public ComResponseDto<?> duplicationCheckIotCse(HttpServletRequest request) throws BizException {
		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
//		TbIoTOm2mSvcSvrDTO tbIotCseDTO = JsonUtils.fromJson(comInfoDto.getBodyString(), TbIoTOm2mSvcSvrDTO.class);
//		log.debug("====== duplicationCheckIotApi ===: ", tbIotCseDTO.toString());
//		int chkCnt = 0;
//		chkCnt = tbIotCseSvc.duplicationCheck(tbIotCseDTO);
//
//		TbIotApiDTO returnObj = new TbIotApiDTO();
//		returnObj.setChkCnt(chkCnt);
//
//		if (chkCnt > 0) {
//			throw new BizException("duplicatedCse");
//			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "duplicatedCdNm"));
//		} else {
//			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
//		}

//		return responseComUtil.setResponse200ok(returnObj);
		return responseComUtil.setResponse200ok();
	}

	@PostMapping("/remoteCSE")
	public ComResponseDto<?> createRemoteCse(HttpServletRequest request) throws BizException {
		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
//		TbIoTOm2mSvcSvrDTO tbIotCseDTO = JsonUtils.fromJson(comInfoDto.getBodyString(), TbIoTOm2mSvcSvrDTO.class);
//		log.debug("====== " + tbIotCseDTO.toString());
//
//		om2mSvc.mefAuth(tbIotCseDTO);
//
//		om2mSvc.initRemoteCse(tbIotCseDTO.getSvcCd());

		return responseComUtil.setResponse200ok();
	}

	@DeleteMapping("/remoteCSE")
	public ComResponseDto<?> deleteRemoteCse(HttpServletRequest request, @RequestParam String csrId, @RequestParam String svcCd) throws BizException {
		log.debug("====== " + csrId);

//		om2mSvc.deleteResource("/cb-1/" + csrId, svcCd);

		return responseComUtil.setResponse200ok();
	}
}
