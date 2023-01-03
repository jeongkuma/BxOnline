package kr.co.scp.ccp.postBoard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.abacus.common.exception.BizException;
import kr.co.scp.ccp.postBoard.dto.TbIoTPostBrdDTO;
import kr.co.scp.ccp.postBoard.dto.TbIoTPostBrdResDetailDTO;
import kr.co.scp.ccp.postBoard.dto.TbIoTPostBrdResListDTO;

@Mapper
public interface PostBoardDAO {
	public List<TbIoTPostBrdResListDTO> retrievePostBoardList(TbIoTPostBrdDTO postBoardDto) throws BizException ;

	public TbIoTPostBrdResDetailDTO retrievePostBoardDetail(TbIoTPostBrdDTO postBoardDto) throws BizException ;

	public Integer retrievePostBoardCount(TbIoTPostBrdDTO postBoardDto) throws BizException ;

	public void deletePostBoard(String postSeq) throws BizException ;

	public void updateTbIoTPostBrdDTO(TbIoTPostBrdDTO postBoardDto) throws BizException ;

	public void insertTbIoTPostBrdDTO(TbIoTPostBrdDTO postBoardDto) throws BizException ;

}
