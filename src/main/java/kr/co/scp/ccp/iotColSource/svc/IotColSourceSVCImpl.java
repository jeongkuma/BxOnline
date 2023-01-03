package kr.co.scp.ccp.iotColSource.svc;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.scp.ccp.iotColSource.dao.IotColSourceDAO;
import kr.co.scp.ccp.iotColSource.dto.TbIotColSourceDTO;
import lombok.extern.slf4j.Slf4j;


@Primary
@Slf4j
@Service
public class IotColSourceSVCImpl implements IotColSourceSVC {

	@Autowired
	private IotColSourceDAO iotColSourceDAO;

	
	/**
	 *	수집원문조회
	 */
	@Override
	public HashMap<String, Object> retrieveIotColSourceList(TbIotColSourceDTO tbIotColSourceDTO) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Integer count = 0;
		
		tbIotColSourceDTO.setCustSeq(((AuthUtils.getUser().getCustSeq())));
		
		count = iotColSourceDAO.retrieveIotColSourceCount(tbIotColSourceDTO);
		
		PageDTO pageDTO = new PageDTO();
		pageDTO.pageCalculate(count, tbIotColSourceDTO.getDisplayRowCount(), tbIotColSourceDTO.getCurrentPage());
		tbIotColSourceDTO.setStartPage(pageDTO.getRowStart());
		
		List<TbIotColSourceDTO> retrieveIotColSourceList = null;
		retrieveIotColSourceList = iotColSourceDAO.retrieveIotColSourceList(tbIotColSourceDTO);
		
		resultMap.put("page", pageDTO);
		resultMap.put("colSourceList", retrieveIotColSourceList);

		return resultMap;
	}
	
	
	/**
	 * 수집원문 헤더정보, 바디정보 조회
	 */
	@Override
	public HashMap<String, Object> retrieveIotColSourceDetail(TbIotColSourceDTO tbIotColSourceDTO) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		tbIotColSourceDTO = iotColSourceDAO.retrieveIotColSourceDetail(tbIotColSourceDTO);
		resultMap.put("colSourceDetail", tbIotColSourceDTO);
		
		return resultMap;
	}
	

}
