package kr.co.scp.ccp.iotCtrl.ctl;

import java.util.ArrayList;
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
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotCtrl.dto.TbIotCtrlDTO;
import kr.co.scp.ccp.iotCtrl.svc.IotCtrlSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/iotctr")
public class IotCtrlCTL {

	@Autowired
	private IotCtrlSVC iotCtrlSVC;

	@Autowired
	private ResponseComUtil responseComUtil;

	/**
	 * 제어항목별 속성셋 조회
	 * @param request
	 * @param tbIotCtrlDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveTbIotCtrlAttbList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotCtrlAttbList(HttpServletRequest request, @RequestBody TbIotCtrlDTO tbIotCtrlDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> TbIotCtrlList = iotCtrlSVC.retrieveTbIotCtrlAttbList(tbIotCtrlDTO);

		return responseComUtil.setResponse200ok(TbIotCtrlList);
	}

	/**
	 * 제어항목 및 예약 조회
	 * @param request
	 * @param tbIotCtrlDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveTbIotCtrlRsvList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotCtrlRsvList(HttpServletRequest request, @RequestBody TbIotCtrlDTO tbIotCtrlDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> TbIotCtrlList = iotCtrlSVC.retrieveTbIotCtrlRsvList(tbIotCtrlDTO);

		return responseComUtil.setResponse200ok(TbIotCtrlList);
	}

	/**
	 * 장비목록 조회
	 * @param request
	 * @param tbIotCtrlDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotCtrlList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCtrlList(HttpServletRequest request, @RequestBody TbIotCtrlDTO tbIotCtrlDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> TbIotCtrlList = iotCtrlSVC.retrieveIotCtrlList(tbIotCtrlDTO);

		return responseComUtil.setResponse200ok(TbIotCtrlList);
	}
	/**
	 * 제어예약 삭제
	 * @param request
	 * @param tbIotCtrlDTO
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/deleteIotCtrlList", method = RequestMethod.POST)
	public ComResponseDto<?> deleteIotCtrlList(HttpServletRequest request, @RequestBody TbIotCtrlDTO tbIotCtrlDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> msgList = iotCtrlSVC.deleteIotCtrlList(tbIotCtrlDTO);

		return responseComUtil.setResponse200ok(msgList);
	}

	/**
	 * 제어예약 등록/수정
	 * @param request
	 * @param tbIotCtrlDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/insertIoTCtrlRsv", method = RequestMethod.POST)
	public ComResponseDto<?> insertIoTCtrlRsv(HttpServletRequest request, @RequestBody TbIotCtrlDTO tbIotCtrlDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> msgList = iotCtrlSVC.insertIoTCtrlRsv(request, tbIotCtrlDTO);
		List<TbIotCtrlDTO> instantList = (List<TbIotCtrlDTO>) msgList.remove("instantList");
		List<HashMap<String, Object>> msg = new ArrayList<HashMap<String, Object>>();
		if (instantList != null) {
			for(TbIotCtrlDTO param : instantList) {
				HashMap<String, Object> callMsg = iotCtrlSVC.callImmApi(request, param, null);
				Object resMsg = callMsg.get("msg");
				if(resMsg == null) {
					//fail
				}
				msg.add(callMsg);
			}
//			HashMap<String, Object> callMsg = iotCtrlSVC.callImmApi(request, null, instantList);
//			Object resMsg = callMsg.get("msg");
//			if (resMsg == null) {
//				//fail
//			}
//			msg.add(callMsg);
		}
		msgList.put("immMsg", msg);
		return responseComUtil.setResponse200ok(msgList);
	}
}
