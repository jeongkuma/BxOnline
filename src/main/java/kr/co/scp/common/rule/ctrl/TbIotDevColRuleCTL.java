package kr.co.scp.common.rule.ctrl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdOptionDTO;
import kr.co.scp.common.rule.dto.TbIotDevColRuleDTO;
import kr.co.scp.common.rule.svc.TbIotDevColRuleSVC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(value = { "*" }, exposedHeaders = { "Content-Disposition" })
@RequestMapping(value = "/online/rule")
public class TbIotDevColRuleCTL {

	@Autowired
	private TbIotDevColRuleSVC tbIotDevColRuleSVC;

	@Autowired
	private ResponseComUtil responseComUtil;

	@RequestMapping(value = "/retrieveTbIotColValRuleInfo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotColValRuleInfo(HttpServletRequest request,
			@RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> retrieveTbIotValRuleinfoList = tbIotDevColRuleSVC
				.retrieveTbIotColValRuleInfo(tbIotDevColRuleDTO);
		return responseComUtil.setResponse200ok(retrieveTbIotValRuleinfoList);
	}

	@RequestMapping(value = "/retrieveTbIotColTcpValRuleInfo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotColTcpValRuleInfo(HttpServletRequest request,
			@RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> retrieveTbIotValRuleinfoList = tbIotDevColRuleSVC
				.retrieveTbIotColTcpValRuleInfo(tbIotDevColRuleDTO);
		return responseComUtil.setResponse200ok(retrieveTbIotValRuleinfoList);
	}

	@RequestMapping(value = "/devColRuleSettingInfo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveLibraryBoardList(HttpServletRequest request,
			@RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> iotDevColRuleSettingList = tbIotDevColRuleSVC
				.retrieveIotDevColRuleSetting(tbIotDevColRuleDTO);
		return responseComUtil.setResponse200ok(iotDevColRuleSettingList);
	}

	@RequestMapping(value = "/retrieveIotColAttb", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotColAttb(HttpServletRequest request,
			@RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());

		List<TbIotCmCdOptionDTO> rn = tbIotDevColRuleSVC.retrieveIotColAttb(tbIotDevColRuleDTO);
		return responseComUtil.setResponse200ok(rn);
	}

	@RequestMapping(value = "/retrieveIotConAttb", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotConAttb(HttpServletRequest request,
			@RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());

		List<TbIotCmCdOptionDTO> rn = tbIotDevColRuleSVC.retrieveIotConAttb(tbIotDevColRuleDTO);
		return responseComUtil.setResponse200ok(rn);
	}

	@RequestMapping(value = "/retrieveIotStaAvgAttb", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotStaAvgAttb(HttpServletRequest request,
			@RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());

		List<TbIotCmCdOptionDTO> rn = tbIotDevColRuleSVC.retrieveIotStaAvgAttb(tbIotDevColRuleDTO);
		return responseComUtil.setResponse200ok(rn);
	}

	@RequestMapping(value = "/retrieveIotStaSumAttb", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotStaSumAttb(HttpServletRequest request,
			@RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());

		List<TbIotCmCdOptionDTO> rn = tbIotDevColRuleSVC.retrieveIotStaSumAttb(tbIotDevColRuleDTO);
		return responseComUtil.setResponse200ok(rn);
	}

	@RequestMapping(value = "/retrieveIotDetNmCd", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotDetNmCd(HttpServletRequest request,
			@RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());

		List<TbIotCmCdOptionDTO> rn = tbIotDevColRuleSVC.retrieveIotDetNmCd(tbIotDevColRuleDTO);
		return responseComUtil.setResponse200ok(rn);
	}

	@RequestMapping(value = "/retrieveIotRule", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotRule(HttpServletRequest request,
			@RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());

		List<TbIotCmCdOptionDTO> rn = tbIotDevColRuleSVC.retrieveIotRule(tbIotDevColRuleDTO);
		return responseComUtil.setResponse200ok(rn);
	}

	@RequestMapping(value = "/insertTbIoTDevColRule", method = RequestMethod.POST)
	public ComResponseDto<?> insertTbIoTDevColRule(HttpServletRequest request,
			@RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		tbIotDevColRuleSVC.insertTbIoTDevColRule(tbIotDevColRuleDTO);
		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/deleteTbIotDevColRule", method = RequestMethod.POST)
	public ComResponseDto<?> deleteTbIotDevColRule(HttpServletRequest request,
			@RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		tbIotDevColRuleSVC.deleteTbIotDevColRule(tbIotDevColRuleDTO);
		return responseComUtil.setResponse200ok();
	}
	
	@RequestMapping(value = "/deviceRuleInfoCopy", method = RequestMethod.POST)
	public ComResponseDto<?> deviceRuleInfoCopy(HttpServletRequest request,
			@RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		tbIotDevColRuleSVC.copyDeviceRuleInfo(tbIotDevColRuleDTO);
		return responseComUtil.setResponse200ok();
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/retrieveIotTargetKeyParseList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotTargetKeyParseList(HttpServletRequest request,
			@RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<HashMap> iotValRuleList = tbIotDevColRuleSVC.retrieveIotTargetKeyParseList(tbIotDevColRuleDTO);

		return responseComUtil.setResponse200ok(iotValRuleList);

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/retrieveIotTargetKeyList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotTargetKeyList(HttpServletRequest request,
			@RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<HashMap> iotValRuleList = tbIotDevColRuleSVC.retrieveIotTargetKeyList(tbIotCmCdDTO);

		return responseComUtil.setResponse200ok(iotValRuleList);

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/retrieveIotTargetKeyParseHeaderList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotTargetKeyParseHeaderList(HttpServletRequest request,
			@RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<HashMap> iotValRuleList = tbIotDevColRuleSVC.retrieveIotTargetKeyParseHeaderList(tbIotCmCdDTO);

		return responseComUtil.setResponse200ok(iotValRuleList);

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/retrieveIotRequestKeyParseList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotRequestKeyParseList(HttpServletRequest request,
			@RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<HashMap> iotValRuleList = tbIotDevColRuleSVC.retrieveIotRequestKeyParseList(tbIotCmCdDTO);

		return responseComUtil.setResponse200ok(iotValRuleList);

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/retrieveIotRequestKeyValList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotRequestKeyValList(HttpServletRequest request,
			@RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<HashMap> iotValRuleList = tbIotDevColRuleSVC.retrieveIotRequestKeyValList(tbIotCmCdDTO);

		return responseComUtil.setResponse200ok(iotValRuleList);

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/retrieveIotBodyType", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotBodyType(HttpServletRequest request, @RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO)
			throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<HashMap> iotValRuleList = tbIotDevColRuleSVC.retrieveIotBodyType(tbIotDevColRuleDTO);

		return responseComUtil.setResponse200ok(iotValRuleList);

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/retrieveIotServiceList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotServiceList(HttpServletRequest request) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<HashMap> iotValRuleList = tbIotDevColRuleSVC.retrieveIotServiceList();

		return responseComUtil.setResponse200ok(iotValRuleList);

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/retriveTbIotColParseData", method = RequestMethod.POST)
	public ComResponseDto<?> retriveTbIotColParseData(HttpServletRequest request,
			@RequestBody TbIotDevColRuleDTO tbIotDevColRuleDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<HashMap> colParseDate = tbIotDevColRuleSVC.retriveTbIotColParseData(tbIotDevColRuleDTO);

		return responseComUtil.setResponse200ok(colParseDate);

	}

}
