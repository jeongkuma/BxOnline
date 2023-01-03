package kr.co.scp.common.menu.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.common.dto.TbIotTreeDTO;
import kr.co.scp.common.menu.dto.*;

import java.util.HashMap;
import java.util.List;

public interface TbIotMenuSVC {
	public List<TbIotMenuDTO> retrieveTbIotMenuList(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public void deleteTbIotMenu(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public void updateTbIotMenu(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public void insertTbIotMenu(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public List<TbIotTreeDTO> retrieveMenuList(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public int duplicationCheckMenuNm(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public int duplicationCheckMenuId(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public int upMenuCheck(TbIotMenuDTO tbIotMenuDto) throws BizException;

	public List<MenuJsonDTO> retrieveAuthMenu(AuthMenuDTO authMenuDTO) throws BizException;

	public int duplicationCheckCopy(CopyTbIotMenuDto copyTbIotMenuDto) throws BizException;

	public void copyMenuList(CopyTbIotMenuDto copyTbIotMenuDto) throws BizException;

	public HashMap<String,Object> retrieveIotSvcCdList(MenuCdDTO menuCdDto) throws BizException;


}
