package kr.co.scp.ccp.iotRoleMap.ctrl;

import java.util.HashMap;
import java.util.List;

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
import kr.co.scp.ccp.common.dto.TbIotTreeDTO;
import kr.co.scp.ccp.iotRoleMap.dto.ProgListDTO;
import kr.co.scp.ccp.iotRoleMap.dto.TbIotRoleMapDTO;
import kr.co.scp.ccp.iotRoleMap.svc.RoleMapSVC;

//@Slf4j
@RestController
@RequestMapping(value = "/online/iotrolemap")
@CrossOrigin(value = {"*"})
public class RoleMapCTL {

	@Autowired
	private RoleMapSVC roleMapSvc;

	@Autowired
	private ResponseComUtil responseComUtil;

	/**
	 * @Author jbs
	 * @param request
	 * @param notiBoardDto
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotRoleMapList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveRoleMapList(HttpServletRequest request, @RequestBody TbIotRoleMapDTO tbIotRoleMapDTO) throws BizException {
		HashMap<String, Object> retrieveRoleMapList = roleMapSvc.retrieveRoleMapList(tbIotRoleMapDTO);
		return responseComUtil.setResponse200ok(retrieveRoleMapList);
	}

	@RequestMapping(value = "/retrieveIotMenuList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveMenuList(HttpServletRequest request, @RequestBody TbIotRoleMapDTO tbIotRoleMapDTO) throws BizException {
		List<TbIotTreeDTO> retrieveRoleMapList = roleMapSvc.retrieveMenuList(tbIotRoleMapDTO);
		return responseComUtil.setResponse200ok(retrieveRoleMapList);
	}

	@RequestMapping(value = "/retrieveIotProgList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveProgList(HttpServletRequest request, @RequestBody TbIotRoleMapDTO tbIotRoleMapDTO) throws BizException {
		List<ProgListDTO> retrieveRoleMapList = roleMapSvc.retrieveProgList(tbIotRoleMapDTO);
		return responseComUtil.setResponse200ok(retrieveRoleMapList);
	}

	@RequestMapping(value = "/retrieveIotRoleMapDetail", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveNotiBoardDetail(HttpServletRequest request, @RequestBody TbIotRoleMapDTO tbIotRoleMapDTO) throws BizException {
		TbIotRoleMapDTO retrieveRoleMapDetail = roleMapSvc.retrieveRoleMapDetail(tbIotRoleMapDTO);
		return responseComUtil.setResponse200ok(retrieveRoleMapDetail);
	}

	@RequestMapping(value = "/createIotProgRoleAuth", method = RequestMethod.POST)
	public ComResponseDto<?> createProgRoleAuth(HttpServletRequest request, @RequestBody TbIotRoleMapDTO tbIotRoleMapDTO) throws BizException {
		roleMapSvc.insertProgRoleAuth(tbIotRoleMapDTO);
		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/createIotMenuRoleAuth", method = RequestMethod.POST)
	public ComResponseDto<?> createMenuRoleAuth(HttpServletRequest request, @RequestBody TbIotRoleMapDTO tbIotRoleMapDTO) throws BizException {
		roleMapSvc.insertMenuRoleAuth(tbIotRoleMapDTO);
		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/deleteIotRoleMap", method = RequestMethod.POST)
	public ComResponseDto<?> deleteRoleMap(HttpServletRequest request, @RequestBody TbIotRoleMapDTO tbIotRoleMapDTO) throws BizException {
		roleMapSvc.deleteRoleMap(tbIotRoleMapDTO);
		return responseComUtil.setResponse200ok();
	}
}















