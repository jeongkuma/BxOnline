package kr.co.scp.common.label.ctrl;

import java.util.Map;

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
import kr.co.scp.common.label.dto.TbIotLabelDTO;
import kr.co.scp.common.label.svc.TbIotLabelSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/online/com/label")
public class LabelCTL {

	@Autowired
	private TbIotLabelSVC tbIotLabelSvc;

	@Autowired
	private ResponseComUtil responseComUtil;

	/**
	 * 화면 레이블 정보를 조회 한다. 화면에서 언어셋을 해더에 받아서 언어셋에 맞는 레이블을 돌려 준다.
	 * 
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getLabel", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotLavel(HttpServletRequest request, 
												@RequestBody TbIotLabelDTO tbIotLabelDto) throws BizException {
		log.debug("====== APIVERSION 1");
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		Map<String, Object> labelMap = tbIotLabelSvc.retrievetbIotLabelList(tbIotLabelDto);
		return responseComUtil.setResponse200ok(labelMap);
	}
	
	
	/**
	 * 화면 레이블 정보를 조회 한다. 화면에서 언어셋을 해더에 받아서 언어셋에 맞는 레이블을 돌려 준다.
	 * 
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getLabelView", method = RequestMethod.POST)
	public ComResponseDto<?> retrievetbIotLabelView(HttpServletRequest request, 
												@RequestBody TbIotLabelDTO tbIotLabelDto) throws BizException {
		log.debug("====== APIVERSION 1");
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		Map<String, Object> labelMap = tbIotLabelSvc.retrievetbIotLabelView(tbIotLabelDto);
		log.debug("====== " + labelMap.toString());
		return responseComUtil.setResponse200ok(labelMap);
	}
	

	/**
	 * 화면 레이블 정보를 등록 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/insertLabel", method = RequestMethod.POST)
	public ComResponseDto<?> insertTbIotLavel(HttpServletRequest request, 
			 								 @RequestBody TbIotLabelDTO tbIotLabelDto) throws BizException {
		log.debug("====== insertTbIotLavel === ");
		tbIotLabelSvc.insertTbIotLabel(tbIotLabelDto);
		return responseComUtil.setResponse200ok();

	}
	
	/**
	 * 화면 레이블 정보를 수정 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/updateLabel", method = RequestMethod.POST)
	public ComResponseDto<?> updateTbIotLavel(HttpServletRequest request, 
			 								 @RequestBody TbIotLabelDTO tbIotLabelDto) throws BizException {
		log.debug("====== updateTbIotLavel === ");
		tbIotLabelSvc.updateTbIotLabel(tbIotLabelDto);
		return responseComUtil.setResponse200ok();

	}
	
	/**
	 * 화면 레이블 삭제 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/deleteLabel", method = RequestMethod.POST)
	public ComResponseDto<?> deleteTbIotLavel(HttpServletRequest request, 
			 @RequestBody TbIotLabelDTO tbIotLabelDto) throws BizException {
		log.debug("====== deleteTbIotLavel === ");
		tbIotLabelSvc.deletetbIotLabel(tbIotLabelDto);
		return responseComUtil.setResponse200ok();

	}
}
