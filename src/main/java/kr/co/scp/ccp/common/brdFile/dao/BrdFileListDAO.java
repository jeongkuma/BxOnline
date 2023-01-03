package kr.co.scp.ccp.common.brdFile.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileDTO;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;

@Mapper
public interface BrdFileListDAO {

	public List<TbIoTBrdFileListDTO> retrieveTbIoTBrdFileListDetail(TbIoTBrdFileListDTO tbIoTBrdFileListDTO) throws BizException;

	public List<TbIoTBrdFileDTO> retrieveTbIoTBrdFileList(TbIoTBrdFileDTO tbIoTBrdFileDTO) throws BizException;

	public void deleteTbIoTBrdFileList(TbIoTBrdFileListDTO tbIoTBrdFileListDTO) throws BizException;

	public void insertTbIoTBrdFileListDTO(TbIoTBrdFileListDTO tbIoTBrdFileListDTO) throws BizException;

	public TbIoTBrdFileListDTO selectFileName(TbIoTBrdFileListDTO tbIoTBrdFileListDTO) throws BizException;

	public void deleteTbIoTBrdFile(TbIoTBrdFileListDTO tbIoTBrdFileListDTO) throws BizException;

}
