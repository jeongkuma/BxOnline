package kr.co.scp.ccp.iotFaqBoard.svc;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import kr.co.scp.ccp.common.brdFile.dto.TbIoTBrdFileListDTO;
import kr.co.scp.ccp.iotFaqBoard.dto.TbIoTFaqBrdDTO;
import org.springframework.context.annotation.Primary;

@Primary
public interface FaqBoardSVC {


	public HashMap<String, Object> retrieveIotFaqBoardList(TbIoTFaqBrdDTO faqBoardDto);

	public HashMap<String, Object> retrieveFaqBoardDetail(TbIoTFaqBrdDTO faqBoardDto);

	public HashMap<String, Object> retrieveIotFaqBoardUser(TbIoTFaqBrdDTO faqBoardDto);

	public void insertTbIotFaqBrd (HttpServletRequest request, TbIoTFaqBrdDTO faqBoardDto);

	public void updateIotFaqBrd(HttpServletRequest request, TbIoTFaqBrdDTO faqBoardDto);

	public boolean deleteMultiFaqBoard(HttpServletRequest request, TbIoTFaqBrdDTO faqBoardDtoList);

	public TbIoTBrdFileListDTO selectFileName(TbIoTBrdFileListDTO tbIoTBrdFileListDTO);
}
