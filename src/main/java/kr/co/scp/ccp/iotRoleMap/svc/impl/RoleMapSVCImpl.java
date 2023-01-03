package kr.co.scp.ccp.iotRoleMap.svc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.common.dto.TbIotTreeDTO;
import kr.co.scp.ccp.common.dto.TbIotTreeDataDTO;
import kr.co.scp.ccp.common.dto.TbIotTreeLiAttrDTO;
import kr.co.scp.ccp.common.dto.TbIotTreeStatusDTO;
import kr.co.scp.ccp.iotRoleMap.dao.RoleMapDAO;
import kr.co.scp.ccp.iotRoleMap.dto.MenuListDTO;
import kr.co.scp.ccp.iotRoleMap.dto.ProgListDTO;
import kr.co.scp.ccp.iotRoleMap.dto.RoleCdListDTO;
import kr.co.scp.ccp.iotRoleMap.dto.SvcCdListDTO;
import kr.co.scp.ccp.iotRoleMap.dto.TbIotRoleMapDTO;
import kr.co.scp.ccp.iotRoleMap.svc.RoleMapSVC;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.OmsUtils;

@Primary
@Service
public class RoleMapSVCImpl implements RoleMapSVC {

	@Autowired
	private RoleMapDAO roleMapDao;

	@Override
	public HashMap<String, Object> retrieveRoleMapList(TbIotRoleMapDTO roleMapDTO) throws BizException {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		PageDTO pageDTO = new PageDTO();
		Integer count = 0;
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String langSet = c.getXlang();
		roleMapDTO.setLangSet(langSet);
		roleMapDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
		roleMapDTO.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"roleMapDao.retrieveRoleMapListCount");
		try {
			count = roleMapDao.retrieveRoleMapListCount(roleMapDTO);
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
		pageDTO.pageCalculate(count, roleMapDTO.getDisplayRowCount(), roleMapDTO.getCurrentPage());
		roleMapDTO.setStartPage(pageDTO.getRowStart());
		List<TbIotRoleMapDTO> roleMapList = new ArrayList<TbIotRoleMapDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "roleMapDao.retrieveRoleMapList");
		try {
			roleMapList = roleMapDao.retrieveRoleMapList(roleMapDTO);
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

		List<RoleCdListDTO> roleCdList = new ArrayList<RoleCdListDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "roleMapDao.retrieveRoleCdList");
		try {
			roleCdList = roleMapDao.retrieveRoleCdList(roleMapDTO);
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

		List<SvcCdListDTO> svcCdList = new ArrayList<SvcCdListDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "roleMapDao.retrieveSvcCdList");
		roleMapDTO.setRoleCdId(AuthUtils.getUser().getRoleCdId());
		roleMapDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
		try {
			svcCdList = roleMapDao.retrieveSvcCdList(roleMapDTO);
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

		resultMap.put("page", pageDTO);
		resultMap.put("roleMapList", roleMapList);
		resultMap.put("roleCdList", roleCdList);
		resultMap.put("svcCdList", svcCdList);
		resultMap.put("userRole", AuthUtils.getUser().getRoleCdId());
		resultMap.put("userSvc", AuthUtils.getUser().getSvcCd());
		return resultMap;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<TbIotTreeDTO> retrieveMenuList(TbIotRoleMapDTO roleMapDTO) throws BizException {
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String newLang = c.getXlang();
		// 사용하는 메뉴 조회
		MenuListDTO menuListDto = new MenuListDTO();
		roleMapDTO.setLangSet(newLang);
		menuListDto.setLangSet(newLang);
		if (roleMapDTO.isFlag()) {
			menuListDto.setSvcCd(roleMapDTO.getSvcCd());
		} else {
			menuListDto.setSvcCd(AuthUtils.getUser().getSvcCd());
			roleMapDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
		}
		List<MenuListDTO> menuList = new ArrayList<MenuListDTO>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"roleMapDao.retrieveMenuList");
		try {
			menuList = roleMapDao.retrieveMenuList(menuListDto);
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

		List<TbIotRoleMapDTO> roleMapMenuList = new ArrayList<TbIotRoleMapDTO>();
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"roleMapDao.retrieveRoleMapMenuList");
		try {
			roleMapMenuList = roleMapDao.retrieveRoleMapMenuList(roleMapDTO);
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
		TbIotTreeStatusDTO treeStatusDto = new TbIotTreeStatusDTO();
		treeStatusDto.setSelected(true);
		treeStatusDto.setOpened(false);
		tmpTreeData.setSelfSeq("0");
		firstData.setParent("#");
		firstData.setId("0");
		firstData.setText("메뉴 공통");
		firstData.setData(tmpTreeData);
		firstData.setLi_attr(new TbIotTreeLiAttrDTO());
		firstData.setState(treeStatusDto);
		returnTreeData.add(firstData);

		for (Iterator menuIterator = menuList.iterator(); menuIterator.hasNext();) {
			MenuListDTO menuDto = (MenuListDTO) menuIterator.next();
			TbIotTreeDTO tmpTreeDto = new TbIotTreeDTO();
			TbIotTreeLiAttrDTO tmpTreeLiAttrDto = new TbIotTreeLiAttrDTO();
			TbIotTreeStatusDTO tmpTreeStatusDto = new TbIotTreeStatusDTO();
			TbIotTreeDataDTO tmpTreeDataDto = new TbIotTreeDataDTO();
			if (null == menuDto.getUpMenuSeq())
				menuDto.setUpMenuSeq("0");
			for (Iterator roleIterator = roleMapMenuList.iterator(); roleIterator.hasNext();) {
				TbIotRoleMapDTO roleDto = (TbIotRoleMapDTO) roleIterator.next();
				if (menuDto.getMenuSeq().equals(roleDto.getMenuProgSeq())) {
					tmpTreeStatusDto.setSelected(true);
					tmpTreeStatusDto.setOpened(false);
					break;
				}
			}
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

	@SuppressWarnings("rawtypes")
	@Override
	public List<ProgListDTO> retrieveProgList(TbIotRoleMapDTO roleDTO) throws BizException {
		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String newLang = c.getXlang();
		roleDTO.setLangSet(newLang);
		List<ProgListDTO> retrieveProgList = new ArrayList<ProgListDTO>();
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"roleMapDao.retrieveProgList");
		try {
			retrieveProgList = roleMapDao.retrieveProgList();
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
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"roleMapDao.retrieveRoleMapProgList");
		List<TbIotRoleMapDTO> retrieveRoleMapProgList = new ArrayList<TbIotRoleMapDTO>();
		try {
			retrieveRoleMapProgList = roleMapDao.retrieveRoleMapProgList(roleDTO);
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
		for (Iterator progRoleIterator = retrieveRoleMapProgList.iterator(); progRoleIterator.hasNext();) {
			TbIotRoleMapDTO progRole = (TbIotRoleMapDTO) progRoleIterator.next();
			for (Iterator progIterator = retrieveProgList.iterator(); progIterator.hasNext();) {
				ProgListDTO prog = (ProgListDTO) progIterator.next();
				if (progRole.getMenuProgSeq().equals(prog.getProgSeq())) {
					prog.setChecked(true);
					break;
				}
			}
		}

		return retrieveProgList;
	}

	@Override
	public TbIotRoleMapDTO retrieveRoleMapDetail(TbIotRoleMapDTO roleMapDTO) throws BizException {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void insertProgRoleAuth(TbIotRoleMapDTO progRoleList) throws BizException {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"roleMapDao.deleteMenuRoleAuth");
		try {
			roleMapDao.deleteProgRoleAuth(progRoleList);
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

		List<TbIotRoleMapDTO> insertDto = progRoleList.getInsertDto();
		for (Iterator progIterator = insertDto.iterator(); progIterator.hasNext();) {
			TbIotRoleMapDTO progRole = (TbIotRoleMapDTO) progIterator.next();
			progRole.setSvcCd(progRoleList.getSvcCd());
			progRole.setRoleCdId(progRoleList.getRoleCdId());
			progRole.setRegUserId(AuthUtils.getUser().getUserId());
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"roleMapDao.insertProgRoleAuth");
			try {
				roleMapDao.insertProgRoleAuth(progRole);
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

	@SuppressWarnings("rawtypes")
	@Override
	public void insertMenuRoleAuth(TbIotRoleMapDTO menuRoleList) throws BizException {
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
				"roleMapDao.deleteMenuRoleAuth");
		try {
			roleMapDao.deleteMenuRoleAuth(menuRoleList);
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

		List<TbIotRoleMapDTO> insertDto = menuRoleList.getInsertDto();

		for (Iterator menuIterator = insertDto.iterator(); menuIterator.hasNext();) {
			TbIotRoleMapDTO menuRole = (TbIotRoleMapDTO) menuIterator.next();
			menuRole.setRoleCdId(menuRoleList.getRoleCdId());
			menuRole.setRegUserId(AuthUtils.getUser().getUserId());
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"roleMapDao.insertMenuRoleAuth");
			try {
				roleMapDao.insertMenuRoleAuth(menuRole);
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

	@Override
	public void deleteRoleMap(TbIotRoleMapDTO roleMapDTO) {
		for (String roleSeq : roleMapDTO.getRoleSeqList()) {
			TbIotRoleMapDTO reqDTO = new TbIotRoleMapDTO();
			reqDTO.setRoleSeq(roleSeq);
			ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
					"roleMapDao.deleteRoleMap");
			try {
				roleMapDao.deleteRoleMap(reqDTO);
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
