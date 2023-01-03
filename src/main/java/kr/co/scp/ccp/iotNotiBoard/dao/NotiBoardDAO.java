package kr.co.scp.ccp.iotNotiBoard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotNotiBoard.dto.TbIoTNotiBrdDTO;
import kr.co.scp.ccp.iotNotiBoard.dto.TbIoTNotiBrdDetailDTO;
import kr.co.scp.ccp.iotNotiBoard.dto.TbIoTNotiBrdListDTO;

@Mapper
public interface NotiBoardDAO {
	public List<TbIoTNotiBrdListDTO> retrieveNotiBoardList(TbIoTNotiBrdDTO notiBoardDto);

	public TbIoTNotiBrdDetailDTO retrieveNotiBoardDetail(TbIoTNotiBrdDTO notiBoardDto);

	public Integer retrieveNotiBoardCount(TbIoTNotiBrdDTO notiBoardDto);

	public void deleteNotiBoard(TbIoTNotiBrdDTO notiBoardDto);

	public void updateTbIoTNotiBrd(TbIoTNotiBrdDTO notiBoardDto);

	public void insertTbIoTNotiBrd(TbIoTNotiBrdDTO notiBoardDto);

	public String checkRegUser(TbIoTNotiBrdDTO notiBoardDto);
}
