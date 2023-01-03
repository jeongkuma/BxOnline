package kr.co.scp.ccp.iotRoleMap.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotRoleMap.dto.MenuListDTO;
import kr.co.scp.ccp.iotRoleMap.dto.ProgListDTO;
import kr.co.scp.ccp.iotRoleMap.dto.RoleCdListDTO;
import kr.co.scp.ccp.iotRoleMap.dto.SvcCdListDTO;
import kr.co.scp.ccp.iotRoleMap.dto.TbIotRoleMapDTO;

@Mapper
public interface RoleMapDAO {

	public List<TbIotRoleMapDTO> retrieveRoleMapList(TbIotRoleMapDTO roleMapDTO);

	public Integer retrieveRoleMapListCount(TbIotRoleMapDTO roleMapDTO);

	public List<MenuListDTO> retrieveMenuList(MenuListDTO menuListDTO);

	public List<ProgListDTO> retrieveProgList();

	public List<TbIotRoleMapDTO> retrieveRoleMapMenuList(TbIotRoleMapDTO roleMapDTO);

	public List<TbIotRoleMapDTO> retrieveRoleMapProgList(TbIotRoleMapDTO roleMapDTO);

	public List<RoleCdListDTO> retrieveRoleCdList(TbIotRoleMapDTO roleMapDTO);

	public List<SvcCdListDTO> retrieveSvcCdList(TbIotRoleMapDTO roleMapDTO);

	public TbIotRoleMapDTO retrieveRoleMapDetail(TbIotRoleMapDTO roleMapDTO);

	public TbIotRoleMapDTO retrieveDuplRoleMap(TbIotRoleMapDTO roleMapDTO);

	public void insertProgRoleAuth(TbIotRoleMapDTO roleMapDTO);

	public void insertMenuRoleAuth(TbIotRoleMapDTO roleMapDTO);

	public void updateRoleMap(TbIotRoleMapDTO roleMapDTO);

	public void deleteRoleMap(TbIotRoleMapDTO roleMapDTO);

	public void deleteMenuRoleAuth(TbIotRoleMapDTO roleMapDTO);

	public void deleteProgRoleAuth(TbIotRoleMapDTO roleMapDTO);
}
