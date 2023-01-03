package kr.co.scp.ccp.iotFaqBoard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.scp.ccp.iotFaqBoard.dto.TbIoTFaqBrdCategoryDTO;
import kr.co.scp.ccp.iotFaqBoard.dto.TbIoTFaqBrdDTO;
import kr.co.scp.ccp.iotFaqBoard.dto.TbIoTFaqBrdDetailDTO;
import kr.co.scp.ccp.iotFaqBoard.dto.TbIoTFaqBrdListDTO;
import kr.co.scp.ccp.iotFaqBoard.dto.TbIoTFaqBrdUserDTO;

@Mapper
public interface FaqBoardDAO {
	public List<TbIoTFaqBrdListDTO> retrieveFaqBoardList(TbIoTFaqBrdDTO FaqBoardDto);

	public List<TbIoTFaqBrdCategoryDTO> retrieveCategoryList(TbIoTFaqBrdDTO FaqBoardDto);

	public List<TbIoTFaqBrdUserDTO> retrieveFaqBoardUser(TbIoTFaqBrdDTO FaqBoardDto);

	public List<TbIoTFaqBrdUserDTO> retrieveFaqBoardInterest(TbIoTFaqBrdDTO FaqBoardDto);

	public TbIoTFaqBrdDetailDTO retrieveFaqBoardDetail(TbIoTFaqBrdDTO FaqBoardDto);

	public Integer retrieveFaqBoardCount(TbIoTFaqBrdDTO FaqBoardDto);

	public void deleteFaqBoard(TbIoTFaqBrdDTO FaqBoardDto);

	public void updateIotFaqBrd(TbIoTFaqBrdDTO FaqBoardDto);

	public void insertTbIotFaqBrd(TbIoTFaqBrdDTO FaqBoardDto);

	public String checkRegUser(TbIoTFaqBrdDTO FaqBoardDto);
}
