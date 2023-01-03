package kr.co.scp.ccp.iotColSource.ctl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotColSource.dto.TbIotColSourceDTO;
import kr.co.scp.ccp.iotColSource.svc.IotColSourceSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/iotcolsource")
public class IotColSourceCTL {
	
	@Autowired
	private IotColSourceSVC iotColSourceSVC;
	
	@Autowired
	private ResponseComUtil responseComUtil;
	

	/**
	 * 수집원문 조회
	 * @param request
	 * @param tbIotColSourceDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotColSourceList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotColSourceList(HttpServletRequest request, @RequestBody TbIotColSourceDTO tbIotColSourceDTO) throws BizException {
		
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());		
		HashMap<String, Object> TbIotColSourceList = iotColSourceSVC.retrieveIotColSourceList(tbIotColSourceDTO);
		
		return responseComUtil.setResponse200ok(TbIotColSourceList);
	}
	

	/**
	 * 수집원문 상세 조회
	 * @param request
	 * @param tbIotColSourceDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotColSourceDetail", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotColSourceDetail(HttpServletRequest request, @RequestBody TbIotColSourceDTO tbIotColSourceDTO ) throws BizException {
		
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());		
		HashMap<String, Object> TbIotColSourceList = iotColSourceSVC.retrieveIotColSourceDetail(tbIotColSourceDTO);
		
		return responseComUtil.setResponse200ok(TbIotColSourceList);
	}  
	

}
