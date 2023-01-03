package kr.co.scp.ccp.iotSelDevice.ctl;

import java.util.List;
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
import kr.co.scp.ccp.iotSelDevice.dto.TbIotEDevCurValDTO;
import kr.co.scp.ccp.iotSelDevice.dto.TbIotEntrDevMDTO;
import kr.co.scp.ccp.iotSelDevice.svc.IotSelDeviceSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/online/iotseldevice")
public class IotSelDeviceCTL {

	@Autowired
	private ResponseComUtil responseComUtil;

	@Autowired
	private IotSelDeviceSVC iotSelDeviceSVC;

	/**
	 * 단말 목록 조회 by custSeq
	 * 
	 * @param request
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @Author will
	 */
	@RequestMapping(value = "/getDeviceList", method = RequestMethod.POST)
	public ComResponseDto<?> getDeviceList(HttpServletRequest request, @RequestBody TbIotEntrDevMDTO input) throws BizException {
		Map<String, Object> data = iotSelDeviceSVC.retrieveIotSelDevice(input);
		return responseComUtil.setResponse200ok(data);
	}

	/**
	 * 단말 속성 목록 조회 by custSeq
	 * 
	 * @param request
	 * @param input 
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @Author will
	 */
	@RequestMapping(value = "/getDeviceAttb", method = RequestMethod.POST)
	public ComResponseDto<?> getDeviceAttb(HttpServletRequest request, @RequestBody TbIotEntrDevMDTO input) throws BizException {
		List<TbIotEDevCurValDTO> data = iotSelDeviceSVC.retrieveIotSelDeviceDetail(input);
		return responseComUtil.setResponse200ok(data);
	}

}