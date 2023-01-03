package kr.co.scp.ccp.validation.ctl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.common.tmpl.dto.TbIotTmplHdrJqgridDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/validation")
public class ValidCTL {
	
	@Autowired
	private ResponseComUtil responseComUtil;
	
	@RequestMapping(value = "/validIotTmplHdrJqgrid", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveLibraryBoardList(HttpServletRequest request, @RequestBody TbIotTmplHdrJqgridDTO tbIotTmplHdrJqgridDTO) throws BizException {
		return responseComUtil.setResponse200ok();
	}
	
}
