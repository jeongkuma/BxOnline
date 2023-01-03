package kr.co.scp.ccp.libraryBoard.svc;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.libraryBoard.dto.TbIoTLibBrdDTO;
import kr.co.scp.ccp.libraryBoard.dto.TbIoTLibBrdResDetailDTO;

public interface LibraryBoardSVC {

	public HashMap<String, Object> retrieveLibraryBoardList(TbIoTLibBrdDTO libraryBoardDto) throws BizException;
	
	public TbIoTLibBrdResDetailDTO retrieveLibraryBoardDetail(TbIoTLibBrdDTO libraryBoardDto) throws BizException;
	
	public void insertTbIoTLibBrdDTO (HttpServletRequest request, TbIoTLibBrdDTO libraryBoardDto) throws BizException;
	
	public void updateTbIoTLibBrdDTO(HttpServletRequest request, TbIoTLibBrdDTO libraryBoardDto) throws BizException;
	
	public void deleteLibraryBoard(TbIoTLibBrdDTO libraryBoardDto) throws BizException; 
	
	public void deleteMultiLibraryBoard(HttpServletRequest request, TbIoTLibBrdDTO libraryBoardDtoList) throws BizException;

	public TbIoTBrdFileListDTO selectFileName(TbIoTBrdFileListDTO tbIoTBrdFileListDTO) throws BizException; 
	
	

}
