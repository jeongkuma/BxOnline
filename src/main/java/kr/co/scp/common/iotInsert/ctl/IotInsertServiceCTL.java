package kr.co.scp.common.iotInsert.ctl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.JsonUtils;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.common.iotInsert.dto.TbIotInsertServiceDTO;
import kr.co.scp.common.iotInsert.svc.IotInsertServiceSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
//@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/com/iotInsert")
public class IotInsertServiceCTL {
	
	@Autowired
	private IotInsertServiceSVC iotInsertServiceSVC;
	
	@Autowired
	private ResponseComUtil responseComUtil;
	
	
	/**
	 * 서비스 업로드
	 * @param request
	 * @param TbIotCtrlHistDTO
	 * @return 
	 * @throws BizException
	 */
	@RequestMapping(value = "/uploadService", method = RequestMethod.POST)
	public ComResponseDto<?> insertIoTCtrlRsv(HttpServletRequest request) throws BizException {
		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
		log.debug("====== " + comInfoDto.toString());	
		TbIotInsertServiceDTO tbIotInsertServiceDTO = JsonUtils.fromJson(comInfoDto.getBodyString(), TbIotInsertServiceDTO.class);	
		HashMap<String, Object> msgList = iotInsertServiceSVC.insertServiceSVC(tbIotInsertServiceDTO);
		
		return responseComUtil.setResponse200ok(msgList);
	}
	
	/**
	 * 서비스 상태 체크
	 * @param request
	 * @param TbIotCtrlHistDTO
	 * @return 
	 * @throws BizException
	 */
	@RequestMapping(value = "/useChkService", method = RequestMethod.POST)
	public ComResponseDto<?> useChkService(HttpServletRequest request) throws BizException {
		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
		log.debug("====== " + comInfoDto.toString());	
		TbIotInsertServiceDTO tbIotInsertServiceDTO = JsonUtils.fromJson(comInfoDto.getBodyString(), TbIotInsertServiceDTO.class);		
		HashMap<String, Object> msgList = iotInsertServiceSVC.useChkService(tbIotInsertServiceDTO);
		
		return responseComUtil.setResponse200ok(msgList);
	}
}
