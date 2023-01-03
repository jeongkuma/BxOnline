package kr.co.scp.ccp.postBoard.svc;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.postBoard.dto.TbIoTPostBrdDTO;
import kr.co.scp.ccp.postBoard.dto.TbIoTPostBrdResDetailDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface PostBoardSVC {



	public HashMap<String, Object> retrievePostBoardList(TbIoTPostBrdDTO postBoardDto) throws BizException ;
	
	public TbIoTPostBrdResDetailDTO retrievePostBoardDetail(TbIoTPostBrdDTO postBoardDto) throws BizException ;
	
	public void insertTbIoTPostBrdDTO (HttpServletRequest request, TbIoTPostBrdDTO postBoardDto) throws BizException ;
	
	public void updateTbIoTPostBrdDTO(HttpServletRequest request, TbIoTPostBrdDTO postBoardDto) throws BizException ;
	
	public void deletePostBoard(TbIoTPostBrdDTO postBoardDto) throws BizException ; 
	
	public void deleteMultiPostBoard(HttpServletRequest request, TbIoTPostBrdDTO postBoardDtoList) throws BizException ;

	public TbIoTBrdFileListDTO selectFileName(TbIoTBrdFileListDTO tbIoTBrdFileListDTO) throws BizException ; 
}
