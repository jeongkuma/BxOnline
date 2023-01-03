package kr.co.scp.common.menu.ctrl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.common.dto.TbIotTreeDTO;
import kr.co.scp.common.menu.dto.*;
import kr.co.scp.common.menu.svc.TbIotMenuSVC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/online/com/menu")
public class TbIotMenuCTL {

	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;

	@Autowired
	private TbIotMenuSVC tbIotMenuSvc;

	@Autowired
	private ResponseComUtil responseComUtil;


	/**
	 * 화면 Menu 정보를 조회 한다. 화면에서 언어셋을 해더에 받아서 언어셋에 맞는 Menu을 돌려 준다.
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getIotMenu", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotMenu(HttpServletRequest request,@RequestBody TbIotMenuDTO tbIotMenuDto) throws BizException {
		log.debug("====== MenuVERSION 1");
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotMenuDTO> tbIotMenuList = tbIotMenuSvc.retrieveTbIotMenuList(tbIotMenuDto);
		return responseComUtil.setResponse200ok(tbIotMenuList);
	}

	@RequestMapping(value = "/retrieveTreeMenuList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTreeMenuList(HttpServletRequest request, @RequestBody TbIotMenuDTO tbIotMenuDto) throws BizException {
		List<TbIotTreeDTO> retrieveRoleMapList = tbIotMenuSvc.retrieveMenuList(tbIotMenuDto);
		return responseComUtil.setResponse200ok(retrieveRoleMapList);
	}

	/**
	 * 메뉴 복사(서비스)
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/copySvcIotMenu", method = RequestMethod.POST)
	public ComResponseDto<?> copySvcIotMenu(HttpServletRequest request, @RequestBody CopyTbIotMenuDto copyTbIotMenuDto) throws BizException {
		log.debug("====== copyIotMenu === ");
		int chkCnt = 0;
		copyTbIotMenuDto.setLangSet(copyTbIotMenuDto.getOrgLangSet());
		chkCnt = tbIotMenuSvc.duplicationCheckCopy(copyTbIotMenuDto);

		if(chkCnt>0) {
			throw new BizException("existMenu");
		}else {
			tbIotMenuSvc.copyMenuList(copyTbIotMenuDto);
		}
		return responseComUtil.setResponse200ok();
	}

	/**
	 * 메뉴 복사(언어셋)
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/copyCharSetIotMenu", method = RequestMethod.POST)
	public ComResponseDto<?> copyCharSetIotMenu(HttpServletRequest request, @RequestBody CopyTbIotMenuDto copyTbIotMenuDto) throws BizException {
		log.debug("====== copyIotMenu === ");
		int chkCnt = 0;
		copyTbIotMenuDto.setSvcCd(copyTbIotMenuDto.getOrgSvcCd());
		chkCnt = tbIotMenuSvc.duplicationCheckCopy(copyTbIotMenuDto);
		System.out.println("chkCnt===================="+chkCnt);
		if(chkCnt>0) {
			throw new BizException("existMenu");
		}else {
			tbIotMenuSvc.copyMenuList(copyTbIotMenuDto);
		}
		return responseComUtil.setResponse200ok();
	}

	/**
	 * 메뉴명 중복체크
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/menuNmDuplicatChk", method = RequestMethod.POST)
	public ComResponseDto<?> menuNmDuplicatChk(HttpServletRequest request, @RequestBody TbIotMenuDTO tbIotMenuDto) throws BizException {
		log.debug("====== menuNmDuplicatChk === ");
		int chkCnt = 0;
		chkCnt = tbIotMenuSvc.duplicationCheckMenuNm(tbIotMenuDto);

		TbIotMenuDTO returnObj = new TbIotMenuDTO();
		returnObj.setChkCnt(chkCnt);

		if(chkCnt>0) {
			throw new BizException("duplicatedMenuNm");
		}else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}

		return responseComUtil.setResponse200ok(returnObj);
	}


	/**
	 * 메뉴Id 중복체크
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/menuIdDuplicatChk", method = RequestMethod.POST)
	public ComResponseDto<?> menuIdDuplicatChk(HttpServletRequest request, @RequestBody TbIotMenuDTO tbIotMenuDto) throws BizException {
		log.debug("====== menuNmDuplicatChk === ");
		int chkCnt = 0;
		chkCnt = tbIotMenuSvc.duplicationCheckMenuId(tbIotMenuDto);

		TbIotMenuDTO returnObj = new TbIotMenuDTO();
		returnObj.setChkCnt(chkCnt);

		if(chkCnt>0) {
			throw new BizException("duplicatedMenuId");
		}else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}

		return responseComUtil.setResponse200ok(returnObj);
	}



	/**
	 * 화면 Menu 정보를 등록 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/insertIotMenu", method = RequestMethod.POST)
	public ComResponseDto<?> insertTbIotMenu(HttpServletRequest request,
			 								 @RequestBody TbIotMenuDTO tbIotMenuDto) throws BizException {
		log.debug("====== insertTbIotMenu === ");
		tbIotMenuSvc.insertTbIotMenu(tbIotMenuDto);
		return responseComUtil.setResponse200ok();

	}



	/**
	 * 화면 Menu 정보를 수정 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/updateIotMenu", method = RequestMethod.POST)
	public ComResponseDto<?> updateTbIotMenu(HttpServletRequest request,
			 								 @RequestBody TbIotMenuDTO tbIotMenuDto) throws BizException {
		log.debug("====== updateTbIotMenu === ");
		tbIotMenuSvc.updateTbIotMenu(tbIotMenuDto);
		return responseComUtil.setResponse200ok();

	}

	/**
	 * 화면 Menu 삭제 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/deleteIotMenu", method = RequestMethod.POST)
	public ComResponseDto<?> deleteTbIotMenu(HttpServletRequest request,
			 							   @RequestBody TbIotMenuDTO tbIotMenuDto) throws BizException {
		log.debug("====== deleteTbIotMenu === ");
		int upMenuchkCnt = 0;
		upMenuchkCnt = tbIotMenuSvc.upMenuCheck(tbIotMenuDto);

		TbIotMenuDTO returnObj = new TbIotMenuDTO();
		returnObj.setChkCnt(upMenuchkCnt);

		if(upMenuchkCnt>0) {
			throw new BizException("existDownMenu");
		}else {
			tbIotMenuSvc.deleteTbIotMenu(tbIotMenuDto);
		}

		return responseComUtil.setResponse200ok(returnObj);
	}

	/**
	 * 화면 Menu 정보를 등록 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveAuthMenu", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveAuthMenu(HttpServletRequest request, @RequestBody AuthMenuDTO authMenuDTO) throws BizException {
		log.debug("====== retrieveAuthMenu === ");
		List<MenuJsonDTO> retrieveAuthMenu = tbIotMenuSvc.retrieveAuthMenu(authMenuDTO);
		return responseComUtil.setResponse200ok(retrieveAuthMenu);

	}

	/**
	 * 서비스 목록 조회.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveSvcCdList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveSvcCdList(HttpServletRequest request, @RequestBody MenuCdDTO menuCdDto) throws BizException {
		HashMap<String, Object> retrieveSvcCdList = tbIotMenuSvc.retrieveIotSvcCdList(menuCdDto);
		return responseComUtil.setResponse200ok(retrieveSvcCdList);

	}
}
