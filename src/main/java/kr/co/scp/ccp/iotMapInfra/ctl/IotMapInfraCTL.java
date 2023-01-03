package kr.co.scp.ccp.iotMapInfra.ctl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotMapInfra.dto.MapBaseDTO;
import kr.co.scp.ccp.iotMapInfra.svc.MapBaseSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/online/iotmapinfra")
public class IotMapInfraCTL {

	@Autowired
	private ResponseComUtil responseComUtil;

	@Autowired
	private MapBaseSVC mapBaseSVC;

	/**
	 * 아이나비 지도인프라 호출
	 * 
	 * @param request
	 * @return MapBaseDTO<?>
	 * @throws BizException
	 * @Author will
	 */
	@RequestMapping(value = "/getPoi", method = RequestMethod.POST)
	public ComResponseDto<?> getPoi(HttpServletRequest request, @RequestBody MapBaseDTO input) throws BizException {

		log.info("################### " + input.toString());

		Map result = mapBaseSVC.apiCall(input);

//		Map<String, Object> data = iotSelDeviceSVC.retrieveIotSelDevice(input);
		return responseComUtil.setResponse200ok(result);
	}

}