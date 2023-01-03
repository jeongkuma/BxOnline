package kr.co.scp.ccp.libraryBoard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.libraryBoard.dto.TbIoTLibBrdDTO;
import kr.co.scp.ccp.libraryBoard.dto.TbIoTLibBrdResDetailDTO;
import kr.co.scp.ccp.libraryBoard.dto.TbIoTLibBrdResListDTO;

@Mapper
public interface LibraryBoardDAO {

	public List<TbIoTLibBrdResListDTO> retrieveLibraryBoardList(TbIoTLibBrdDTO libraryBoardDto) throws BizException;

	public TbIoTLibBrdResDetailDTO retrieveLibraryBoardDetail(TbIoTLibBrdDTO libraryBoardDto) throws BizException;

	public Integer retrieveLibraryBoardCount(TbIoTLibBrdDTO libraryBoardDto) throws BizException;

	public void deleteLibraryBoard(String libSeq) throws BizException;

	public void updateTbIoTLibBrdDTO(TbIoTLibBrdDTO libraryBoardDto) throws BizException;

	public void insertTbIoTLibBrdDTO (TbIoTLibBrdDTO libraryBoardDto) throws BizException;


}
