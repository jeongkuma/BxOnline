package kr.co.scp.ccp.iotRoleMap.svc;

import java.util.HashMap;
import java.util.List;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.common.dto.TbIotTreeDTO;
import kr.co.scp.ccp.iotRoleMap.dto.ProgListDTO;
import kr.co.scp.ccp.iotRoleMap.dto.TbIotRoleMapDTO;
import org.springframework.context.annotation.Primary;


public interface RoleMapSVC {

	public HashMap<String, Object> retrieveRoleMapList(TbIotRoleMapDTO roleMapDTO) throws BizException;

	public List<TbIotTreeDTO> retrieveMenuList(TbIotRoleMapDTO roleMapDTO) throws BizException;

	public List<ProgListDTO> retrieveProgList(TbIotRoleMapDTO roleMapDTO) throws BizException;

	public TbIotRoleMapDTO retrieveRoleMapDetail(TbIotRoleMapDTO roleMapDTO) throws BizException;

	public void insertProgRoleAuth(TbIotRoleMapDTO roleMapDTO) throws BizException;

	public void insertMenuRoleAuth(TbIotRoleMapDTO roleMapDTO) throws BizException;

	public void deleteRoleMap(TbIotRoleMapDTO roleMapDTO) throws BizException;
}
