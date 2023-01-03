package kr.co.scp.common.iotInsert.ctl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.JsonUtils;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.common.iotInsert.dto.TbIotInsertDevListDTO;
import kr.co.scp.common.iotInsert.svc.IotInsertSVC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@CrossOrigin(value = "*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/com/iotInsert")
public class IotInsertCTL {
	
	@Autowired
	private IotInsertSVC iotInsertSVC;
	
	@Autowired
	private ResponseComUtil responseComUtil;


	/**
	 * 장비 Insert
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/taskDevM", method = RequestMethod.POST)
	public ComResponseDto<?> taskDevM(HttpServletRequest request) throws BizException {
		ComInfoDto comInfoDto = ReqContextComponent.getComInfoDto();
		log.debug("====== " + comInfoDto.toString());	
		TbIotInsertDevListDTO tbIotInsertDevListDTO = JsonUtils.fromJson(comInfoDto.getBodyString(), TbIotInsertDevListDTO.class);	
//		iotInsertSVC.taskDevM(tbIotInsertDevListDTO);

		return responseComUtil.setResponse200ok();
	}
	
	
	
}
