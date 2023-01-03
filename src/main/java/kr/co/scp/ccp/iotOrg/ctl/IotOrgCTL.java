package kr.co.scp.ccp.iotOrg.ctl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.common.dto.TbIotTreeDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgOptDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgRDTO;
import kr.co.scp.ccp.iotOrg.dto.TbIotOrgUDTO;
import kr.co.scp.ccp.iotOrg.svc.IotOrgSvc;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins="*")
@RequestMapping(value = "/online/iotorg")
public class IotOrgCTL {

	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;

	@Autowired
	private ResponseComUtil responseComUtil;

	@Autowired
	private IotOrgSvc iotOrgSvc;

	/**
	 * 조직 조회 by custSeq
	 * @param request
	 * @param TbIotOrgDTO
	 * @return ComResponseDto<List<tbIotCustDto>>
	 * @throws BizException
	 * @Author joseph
	 */
	@RequestMapping(value = "/retrieveIotOrgByUsr", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveOrgByUsr(HttpServletRequest request) throws BizException {
		List<TbIotOrgOptDTO> tbIotOrgDtoList = iotOrgSvc.retrieveIotOrgByUsr();
		return responseComUtil.setResponse200ok(tbIotOrgDtoList);
	}
	

	@RequestMapping(value = "/retrieveIotCustOrg", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCustOrg(HttpServletRequest request) throws BizException {
		HashMap<String, Object> tbIotOrgDtoList = iotOrgSvc.retrieveIotCustOrg();
		return responseComUtil.setResponse200ok(tbIotOrgDtoList);
	}

	/**
	 * 조직 조회
	 * @param request
	 * @param TbIotOrgDTO
	 * @return ComResponseDto<List<TbIotTreeDTO>>
	 * @throws BizException
	 * @Author joseph
	 */
	@RequestMapping(value = "/retrieveIotOrg", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOrg(HttpServletRequest request, @RequestBody TbIotOrgDTO tbIotOrgDTO) throws BizException {
		List<TbIotTreeDTO> tbIotOrgDtoList = iotOrgSvc.retrieveIotOrg(tbIotOrgDTO);
		return responseComUtil.setResponse200ok(tbIotOrgDtoList);
	}

	/**
	 * 조직 조회 by orgSeq
	 * @param request
	 * @param TbIotOrgDTO
	 * @return ComResponseDto<tbIotCustDto>
	 * @throws BizException
	 * @Author joseph
	 */
	@RequestMapping(value = "/retrieveIotOrgBySeq", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOrgBySeq(HttpServletRequest request, @RequestBody TbIotOrgDTO tbIotOrgDTO) throws BizException {
		TbIotOrgRDTO returnDto = iotOrgSvc.retrieveIotOrgBySeq(tbIotOrgDTO);
		return responseComUtil.setResponse200ok(returnDto);
	}

	/**
	 * 조직명 중복확인
	 * @param request
	 * @param TbIotOrgDTO
	 * @return ComResponseDto<HashMap<String, String>>
	 * @throws BizException
	 * @Author joseph
	 */
	@RequestMapping(value = "/retrieveIotOrgNmChk", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotOrgNmChk(HttpServletRequest request, @RequestBody TbIotOrgDTO tbIotOrgDTO) throws BizException {
		String resultNum = iotOrgSvc.retrieveIotOrgNmChk(tbIotOrgDTO);
		HashMap<String, String> map = new HashMap<String, String>();
		if (null != resultNum) {
			throw new BizException("duplicatedOrgNm");
		} else {
			map.put("message", fileMessageSourceConfig.getMessage("ko", "availableStatus"));
		}

		return responseComUtil.setResponse200ok(map);
	}

	/**
	 * 조직 등록
	 * @param request
	 * @param TbIotOrgDTO
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @Author joseph
	 */
	@RequestMapping(value = "/createIotOrg", method = RequestMethod.POST)
	public ComResponseDto<?> createIotOrg(HttpServletRequest request, @RequestBody TbIotOrgDTO tbIotOrgDTO) throws BizException {
		iotOrgSvc.createIotOrg(tbIotOrgDTO);
		return responseComUtil.setResponse200ok();
	}

	/**
	 * 조직 수정
	 * @param request
	 * @param TbIotOrgDTO
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @Author joseph
	 */
	@RequestMapping(value = "/updateIotOrg", method = RequestMethod.POST)
	public ComResponseDto<?> updateIotOrg(HttpServletRequest request, @RequestBody TbIotOrgUDTO tbIotOrgUDTO) throws BizException {
		iotOrgSvc.updateIotOrg(tbIotOrgUDTO);
		return responseComUtil.setResponse200ok();
	}
}
