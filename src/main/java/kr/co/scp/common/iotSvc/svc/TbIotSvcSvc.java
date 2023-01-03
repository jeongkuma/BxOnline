package kr.co.scp.common.iotSvc.svc;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.common.dto.TbIotTreeDTO;
import kr.co.scp.common.iotSvc.dto.TbIotSvcCombinedDto;
import kr.co.scp.common.iotSvc.dto.TbIotSvcDto;
import kr.co.scp.common.iotSvc.dto.TbIotSvcMDto;
import org.springframework.context.annotation.Primary;

public interface TbIotSvcSvc {

	public List<TbIotTreeDTO>  retrieveIotSvc(TbIotSvcDto svcDto) throws BizException;

	public HashMap<String, Object> retrieveIotSvcBySeq(TbIotSvcDto svcDto) throws BizException;
	
	public void createIotSvc(HttpServletRequest request, TbIotSvcCombinedDto dto) throws BizException;

	public HashMap<String, Object> retrieveDevClsList(TbIotSvcMDto tbIotSvcMDto);

	public void createIotSvcDevMap(TbIotSvcMDto tbIotSvcMDto);	
	
	public void updateIotSvc(HttpServletRequest request, TbIotSvcCombinedDto dto) throws BizException;
	
	public TbIoTBrdFileListDTO getFile(TbIoTBrdFileListDTO tbIoTBrdFileDTO) throws BizException;
	
	public TbIotSvcDto retrieveIotSvcOnly() throws BizException;

	public HashMap<String, Object> getServiceDevCls(TbIotSvcMDto tbIotSvcMDto) throws BizException;
	
	public List<HashMap<String, Object>> retrieveIotCmCdForSvc() throws BizException;
	
	public List<HashMap<String, Object>> retrieveIotCmCdForDevDlsCd(TbIotSvcMDto tbIotSvcMDto)  throws BizException;
}
