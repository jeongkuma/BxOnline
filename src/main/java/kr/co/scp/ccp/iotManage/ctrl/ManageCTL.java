package kr.co.scp.ccp.iotManage.ctrl;

import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotCust.dto.TbIotCustUDTO;
import kr.co.scp.ccp.iotManage.dto.TbIotOutSvrEDTO;
import kr.co.scp.ccp.iotManage.svc.OutSvrInfoService;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/online/iotmanage")
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
public class ManageCTL {

	@Autowired
	private OutSvrInfoService outSvrInfoService;
	
	
	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;

	@Autowired
	private ResponseComUtil responseComUtil;

	@RequestMapping(value = "/outsvrinfomlist", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutSvrInfoMList(HttpServletRequest request, @RequestBody TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException {
		HashMap<String, Object> exSvrInfoList = outSvrInfoService.retrieveIotOutSvrInfoMList(tbIotOutSvrEDTO);
		return responseComUtil.setResponse200ok(exSvrInfoList);
	}

	@RequestMapping(value = "/outsvrm", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutSvrM(HttpServletRequest request, @RequestBody TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException {
		TbIotOutSvrEDTO result = outSvrInfoService.retrieveIotOutSvrM(tbIotOutSvrEDTO);
		return responseComUtil.setResponse200ok(result);
	}
	@RequestMapping(value = "/outsvrinfo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOutSvrInfo(HttpServletRequest request, @RequestBody TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException {
		TbIotOutSvrEDTO result = outSvrInfoService.retrieveIotOutSvrInfo(tbIotOutSvrEDTO);
		return responseComUtil.setResponse200ok(result);
	}

	@RequestMapping(value = "/outsvrinfolist", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotExSvrInfoList(HttpServletRequest request, @RequestBody TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException {
		HashMap<String, Object> exSvrInfoList = outSvrInfoService.retrieveIotOutSvrInfoList(tbIotOutSvrEDTO);
		return responseComUtil.setResponse200ok(exSvrInfoList);
	}

	@RequestMapping(value = "/createoutsvrm", method = RequestMethod.POST)
	@ResponseBody
	public ComResponseDto<?> insertTbIoTOutSvrM(HttpServletRequest request, @RequestBody TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException {
		outSvrInfoService.insertTbIoTOutSvrM(tbIotOutSvrEDTO);
		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/createoutsvrinfo", method = RequestMethod.POST)
	@ResponseBody
	public ComResponseDto<?> insertTbIoTOutSvrInfo(HttpServletRequest request, @RequestBody TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException {
		outSvrInfoService.insertTbIoTOutSvrInfo(tbIotOutSvrEDTO);
		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/updateoutsvrm", method = RequestMethod.POST)
	@ResponseBody
	public ComResponseDto<?> updateTbIoTOutSvrM(HttpServletRequest request, @RequestBody TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException {
		outSvrInfoService.updateTbIoTOutSvrM(tbIotOutSvrEDTO);
		return responseComUtil.setResponse200ok();
	}


	@RequestMapping(value = "/updateoutsvrinfo", method = RequestMethod.POST)
	@ResponseBody
	public ComResponseDto<?> updateTbIoTOutSvrInfo(HttpServletRequest request, @RequestBody TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException {
		outSvrInfoService.updateTbIoTOutSvrInfo(tbIotOutSvrEDTO);
		return responseComUtil.setResponse200ok();
	}
	
	/**
	 * 서버명 중복조회 
	 * @param request
	 * @param tbIotOutSvrEDTO
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author lee.h.s
	 */
	@RequestMapping(value = "/retrieveDuplicatedSvrNm", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveDuplicatedSvrNm(HttpServletRequest request, @RequestBody TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException {
		
		int result = outSvrInfoService.retrieveDuplicatedSvrNm(tbIotOutSvrEDTO);
		TbIotCmCdDTO returnObj = new TbIotCmCdDTO();
		if(result>0) {			
			throw new BizException("duplicatedSvrNm");
		} else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}		
		
		return responseComUtil.setResponse200ok(returnObj);
	}
	/**
	 * 고객사 중복조회 
	 * @param request
	 * @param tbIotOutSvrEDTO
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author lee.h.s
	 */
	@RequestMapping(value = "/retrieveDuplicatedCust", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveDuplicatedCust(HttpServletRequest request, @RequestBody TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException {
		
		int result = outSvrInfoService.retrieveDuplicatedCust(tbIotOutSvrEDTO);
		TbIotCmCdDTO returnObj = new TbIotCmCdDTO();
		if(result>0) {			
			throw new BizException("duplicatedCust");
		} else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}		
		
		return responseComUtil.setResponse200ok(returnObj);
	}
	
	/**
	 * make token
	 * @param request
	 * @param tbIotOutSvrEDTO
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author lee.h.s
	 */
	@RequestMapping(value = "/makeissue", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveMakeToken(HttpServletRequest request, @RequestBody TbIotOutSvrEDTO tbIotOutSvrEDTO) throws BizException {
		
		int result = outSvrInfoService.retrieveMakeToken(tbIotOutSvrEDTO);
		
		TbIotOutSvrEDTO returnObj = new TbIotOutSvrEDTO();
		if(result>0) {
		String certKey = UUID.randomUUID().toString().replaceAll("-", "");
		returnObj.setCertKey(certKey);
		}
		return responseComUtil.setResponse200ok(returnObj);
	}
	/**
     * 고객사 단일 조회
     * @param request
     * @param tbIotCustDto
     * @return List<tbIotCustDto>
     * @throws BizException
     * @author joseph
     */
@RequestMapping(value = "/retrieveIotCustBySeq", method = RequestMethod.POST)
public ComResponseDto<?> retrieveIotCustBySeq(HttpServletRequest request, @RequestBody TbIotCustUDTO tbIotCustDTO) throws BizException {
	HashMap<String, Object> tbIotUseDTORn = outSvrInfoService.retrieveIotCustBySeq(tbIotCustDTO);
	return responseComUtil.setResponse200ok(tbIotUseDTORn);
}
}
