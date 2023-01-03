package kr.co.scp.common.menu.dao;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.iotRoleMap.dto.MenuListDTO;
import kr.co.scp.common.menu.dto.AuthMenuDTO;
import kr.co.scp.common.menu.dto.CopyTbIotMenuDto;
import kr.co.scp.common.menu.dto.MenuCdDTO;
import kr.co.scp.common.menu.dto.TbIotMenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TbIotMenuDAO {
	public List<TbIotMenuDTO> retrieveTbIotMenuList(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public void deleteTbIotMenu(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public void updateTbIotMenu(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public void updateTbIotChildrenMenu(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public void insertTbIotMenu(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public int duplicationCheckMenuNm(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public int duplicationCheckMenuId(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public int upMenuCheck(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public List<AuthMenuDTO> retrieveAuthMenu(AuthMenuDTO authMenuDTO) throws BizException;

	public int duplicationCheckCopy(CopyTbIotMenuDto copyTbIotMenuDto) throws BizException;

	public List<TbIotMenuDTO> retrieveMenuList(CopyTbIotMenuDto copyTbIotMenuDto) throws BizException;

	public List<MenuListDTO> retrieveMenuAllList(MenuListDTO menuListDTO) throws BizException;

	public String getUpMenuSeq(CopyTbIotMenuDto copyTbIotMenuDto) throws BizException;

	public void copyInsertTbIotMenu(TbIotMenuDTO tbMenuDto) throws BizException;

	public List<MenuCdDTO> retrieveIotSvcCdList(MenuCdDTO menuDto) throws BizException;

}
