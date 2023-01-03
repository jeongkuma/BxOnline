package kr.co.scp.common.menu.svc.impl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;
import kr.co.scp.ccp.common.dto.TbIotTreeDTO;
import kr.co.scp.ccp.common.dto.TbIotTreeDataDTO;
import kr.co.scp.ccp.common.dto.TbIotTreeLiAttrDTO;
import kr.co.scp.ccp.common.dto.TbIotTreeStatusDTO;
import kr.co.scp.ccp.iotRoleMap.dao.RoleMapDAO;
import kr.co.scp.ccp.iotRoleMap.dto.MenuListDTO;
import kr.co.scp.common.menu.dao.TbIotMenuDAO;
import kr.co.scp.common.menu.dto.*;
import kr.co.scp.common.menu.svc.TbIotMenuSVC;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Primary
@Slf4j
@Service
public class TbIotMenuSVCImpl implements TbIotMenuSVC {

	@Autowired
	private TbIotMenuDAO tbIotMenuDao;

	@Autowired
	private RoleMapDAO roleMapDao;

	@Override
	public List<TbIotMenuDTO> retrieveTbIotMenuList(TbIotMenuDTO tbIotMenuDto) throws BizException {
		List<TbIotMenuDTO> returnDto = null;

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMenuDao.retrieveTbIotMenuList");
		try {
			returnDto = tbIotMenuDao.retrieveTbIotMenuList(tbIotMenuDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

		return returnDto;
	}

	@Override
	public void deleteTbIotMenu(TbIotMenuDTO tbIotMenuDto) throws BizException {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMenuDao.deleteTbIotMenu");
		try {
			tbIotMenuDao.deleteTbIotMenu(tbIotMenuDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
	}

	@Override
	public void updateTbIotMenu(TbIotMenuDTO tbIotMenuDto) throws BizException {
		tbIotMenuDto.setModUserId(AuthUtils.getUser().getUserId());
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMenuDao.updateTbIotMenu");
		try {
			tbIotMenuDao.updateTbIotMenu(tbIotMenuDto);
			if (tbIotMenuDto.getUseYn().equals("N")) {
				tbIotMenuDao.updateTbIotChildrenMenu(tbIotMenuDto);
			}
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
	}

	@Override
	public void insertTbIotMenu(TbIotMenuDTO tbIotMenuDto) throws BizException {
		tbIotMenuDto.setRegUserId(AuthUtils.getUser().getUserId());
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMenuDao.insertTbIotMenu");
		try {
			tbIotMenuDao.insertTbIotMenu(tbIotMenuDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
	}

	@Override
	public List<TbIotTreeDTO> retrieveMenuList(TbIotMenuDTO tbIotMenuDto) throws BizException {
		MenuListDTO menuListDto = new MenuListDTO();
		menuListDto.setLangSet(tbIotMenuDto.getLangSet());
		menuListDto.setSvcCd(tbIotMenuDto.getSvcCd());
		List<MenuListDTO> menuList = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMenuDao.retrieveMenuAllList");
		try {
			menuList = tbIotMenuDao.retrieveMenuAllList(menuListDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
		List<TbIotTreeDTO> returnTreeData = new ArrayList<TbIotTreeDTO>();

		// 관리화면 최상위 데이터 생성
		TbIotTreeDTO firstData = new TbIotTreeDTO();
		TbIotTreeDataDTO tmpTreeData = new TbIotTreeDataDTO();
		tmpTreeData.setParent("#");
		tmpTreeData.setSelfSeq("0");
		firstData.setId("0");
		firstData.setParent("#");
		firstData.setText("");
		firstData.setLi_attr(new TbIotTreeLiAttrDTO());
		firstData.setData(tmpTreeData);
		firstData.setState(new TbIotTreeStatusDTO(true));
		returnTreeData.add(firstData);
		for (Iterator menuIterator = menuList.iterator(); menuIterator.hasNext();) {
			MenuListDTO menuDto = (MenuListDTO) menuIterator.next();
			TbIotTreeDTO tmpTreeDto = new TbIotTreeDTO();
			TbIotTreeLiAttrDTO tmpTreeLiAttrDto = new TbIotTreeLiAttrDTO();
			TbIotTreeStatusDTO tmpTreeStatusDto = new TbIotTreeStatusDTO();
			TbIotTreeDataDTO tmpTreeDataDto = new TbIotTreeDataDTO();

			tmpTreeStatusDto.setOpened(true);
			tmpTreeDataDto.setSelfSeq(menuDto.getMenuSeq());
			tmpTreeDataDto.setParent(menuDto.getUpMenuSeq());
			tmpTreeDto.setId(menuDto.getMenuSeq());
			tmpTreeDto.setParent(menuDto.getUpMenuSeq());
			tmpTreeDto.setText(menuDto.getMenuCdNm());
			tmpTreeDto.setState(tmpTreeStatusDto);
			tmpTreeDto.setLi_attr(tmpTreeLiAttrDto);
			tmpTreeDto.setData(tmpTreeDataDto);
			returnTreeData.add(tmpTreeDto);
		}
		return returnTreeData;
	}

	@Override
	public int duplicationCheckMenuNm(TbIotMenuDTO tbIotMenuDto) throws BizException {
		// TODO Auto-generated method stub
		int chkCnt = 0;

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMenuDao.duplicationCheckMenuNm");
		try {
			chkCnt = tbIotMenuDao.duplicationCheckMenuNm(tbIotMenuDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

		return chkCnt;
	}

	@Override
	public int duplicationCheckMenuId(TbIotMenuDTO tbIotMenuDto) throws BizException {
		// TODO Auto-generated method stub
		int chkCnt = 0;

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMenuDao.duplicationCheckMenuId");
		try {
			chkCnt = tbIotMenuDao.duplicationCheckMenuId(tbIotMenuDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

		return chkCnt;
	}

	@Override
	public int upMenuCheck(TbIotMenuDTO tbIotMenuDto) throws BizException {
		// TODO Auto-generated method stub
		int returnVal = 0;

		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMenuDao.upMenuCheck");
		try {
			returnVal = tbIotMenuDao.upMenuCheck(tbIotMenuDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
		return returnVal;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<MenuJsonDTO> retrieveAuthMenu(AuthMenuDTO authMenuDTO) throws BizException {
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		authMenuDTO.setLangSet(c.getXlang());
		authMenuDTO.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		authMenuDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
		List<AuthMenuDTO> retrieveAuthMenu = null;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMenuDao.retrieveAuthMenu");
		try {
			retrieveAuthMenu = tbIotMenuDao.retrieveAuthMenu(authMenuDTO);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

		List<MenuJsonDTO> jsonMenuList = new ArrayList<MenuJsonDTO>();
		for (Iterator menuIter = retrieveAuthMenu.iterator(); menuIter.hasNext();) {
			AuthMenuDTO authMenu = (AuthMenuDTO) menuIter.next();
			MenuJsonDTO jsonMenu = new MenuJsonDTO();
			jsonMenu.setHref(authMenu.getProgUri());
			jsonMenu.setMenu_id(authMenu.getMenuCdId());
			jsonMenu.setMenulevel(authMenu.getMenuLevel());
			jsonMenu.setMenu_seq(authMenu.getMenuSeq());
			jsonMenu.setPrnt_menu_id(authMenu.getPrntMenuId());
			jsonMenu.setSort(authMenu.getMenuOrder());
			jsonMenu.setTitle(authMenu.getMenuCdNm());
			jsonMenuList.add(jsonMenu);
		}
		return jsonMenuList;

	}

	@Override
	public int duplicationCheckCopy(CopyTbIotMenuDto copyTbIotMenuDto) throws BizException {
		// TODO Auto-generated method stub
		int chkCnt = 0;
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMenuDao.duplicationCheckCopy");
		try {
			chkCnt = tbIotMenuDao.duplicationCheckCopy(copyTbIotMenuDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
		return chkCnt;
	}

	@Override
	public void copyMenuList(CopyTbIotMenuDto copyTbIotMenuDto) throws BizException {
		ComInfoDto temp = null;
		List<TbIotMenuDTO> menuList = null;

		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "tbIotMenuDao.retrieveMenuList");
		try {
			menuList = tbIotMenuDao.retrieveMenuList(copyTbIotMenuDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

		for (TbIotMenuDTO tbMenuDto : menuList) {
			if (copyTbIotMenuDto.getCopyType().equals("C")) {
				tbMenuDto.setLangSet(copyTbIotMenuDto.getLangSet());
				copyTbIotMenuDto.setSvcCd(copyTbIotMenuDto.getOrgSvcCd());
			} else {
				tbMenuDto.setSvcCd(copyTbIotMenuDto.getSvcCd());
				copyTbIotMenuDto.setLangSet(copyTbIotMenuDto.getOrgLangSet());
			}
			tbMenuDto.setRegUserId(AuthUtils.getUser().getUserId());
			if (tbMenuDto.getMenuLevel().equals("1")) {
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"tbIotMenuDao.copyInsertTbIotMenu");
				try {

					tbIotMenuDao.copyInsertTbIotMenu(tbMenuDto);
				} catch (MyBatisSystemException e) {
					OmsUtils.expInnerOms(temp, e);
					throw e;
				} catch (BadSqlGrammarException e) {
					OmsUtils.expInnerOms(temp, e);
					throw e;
				} catch (DataIntegrityViolationException e) {
					OmsUtils.expInnerOms(temp, e);
					throw e;
				} catch (UncategorizedSQLException e) {
					OmsUtils.expInnerOms(temp, e);
					throw e;
				} finally {
					OmsUtils.endInnerOms(temp);
				}
			} else {
				copyTbIotMenuDto.setOrgUpMenuSeq(tbMenuDto.getUpMenuSeq());
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"tbIotMenuDao.getUpMenuSeq");
				String upMenuSeq = "0";
				try {
					upMenuSeq = tbIotMenuDao.getUpMenuSeq(copyTbIotMenuDto);
				} catch (MyBatisSystemException e) {
					OmsUtils.expInnerOms(temp, e);
					throw e;
				} catch (BadSqlGrammarException e) {
					OmsUtils.expInnerOms(temp, e);
					throw e;
				} catch (DataIntegrityViolationException e) {
					OmsUtils.expInnerOms(temp, e);
					throw e;
				} catch (UncategorizedSQLException e) {
					OmsUtils.expInnerOms(temp, e);
					throw e;
				} finally {
					OmsUtils.endInnerOms(temp);
				}

				tbMenuDto.setUpMenuSeq(upMenuSeq);
				temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
						"tbIotMenuDao.copyInsertTbIotMenu");
				try {
					tbIotMenuDao.copyInsertTbIotMenu(tbMenuDto);
				} catch (MyBatisSystemException e) {
					OmsUtils.expInnerOms(temp, e);
					throw e;
				} catch (BadSqlGrammarException e) {
					OmsUtils.expInnerOms(temp, e);
					throw e;
				} catch (DataIntegrityViolationException e) {
					OmsUtils.expInnerOms(temp, e);
					throw e;
				} catch (UncategorizedSQLException e) {
					OmsUtils.expInnerOms(temp, e);
					throw e;
				} finally {
					OmsUtils.endInnerOms(temp);
				}
			}
		}
	}

	@Override
	public HashMap<String, Object> retrieveIotSvcCdList(MenuCdDTO menuCdDto) throws BizException {
		HashMap<String,Object> result = new HashMap<String,Object>();
		ComInfoDto temp = null;
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		List<MenuCdDTO> svcCdList = new ArrayList<MenuCdDTO>();
		menuCdDto.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		menuCdDto.setSvcCdId(AuthUtils.getUser().getSvcCd());
		menuCdDto.setLangSet(c.getXlang());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"tbIotMenuDao.retrieveIotSvcCdList");
		try {
			svcCdList = tbIotMenuDao.retrieveIotSvcCdList(menuCdDto);
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
		result.put("svcCdList", svcCdList);
		result.put("userRole", AuthUtils.getUser().getRoleCdId());
		result.put("userSvc", AuthUtils.getUser().getSvcCd());
		return result;
	}
}
