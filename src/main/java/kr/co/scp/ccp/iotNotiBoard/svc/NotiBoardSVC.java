package kr.co.scp.ccp.iotNotiBoard.svc;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.iotNotiBoard.dto.TbIoTNotiBrdDTO;
import kr.co.scp.ccp.iotNotiBoard.dto.TbIoTNotiBrdDetailDTO;
import org.springframework.context.annotation.Primary;

@Primary
public interface NotiBoardSVC {

	public HashMap<String, Object> retrieveNotiBoardList(TbIoTNotiBrdDTO notiBoardDto);

	public TbIoTNotiBrdDetailDTO retrieveNotiBoardDetail(TbIoTNotiBrdDTO notiBoardDto);

	public void insertTbIoTNotiBrd (HttpServletRequest request, TbIoTNotiBrdDTO notiBoardDto);

	public void updateTbIoTNotiBrd(HttpServletRequest request, TbIoTNotiBrdDTO notiBoardDto);

	public boolean deleteMultiNotiBoard(HttpServletRequest request, TbIoTNotiBrdDTO notiBoardDto);

	public TbIoTBrdFileListDTO selectFileName(TbIoTBrdFileListDTO tbIoTBrdFileListDTO);
}
