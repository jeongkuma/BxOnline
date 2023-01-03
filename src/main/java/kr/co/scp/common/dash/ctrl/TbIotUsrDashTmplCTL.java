package kr.co.scp.common.dash.ctrl;

import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.common.dash.dto.TbIotUsrDashTmplDTO;
import kr.co.scp.common.dash.svc.TbIotUsrDashTmplSVC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/online/com/usrDash")
public class TbIotUsrDashTmplCTL {
	@Autowired
	private TbIotUsrDashTmplSVC tbIotUsrDashTmplSvc;

	@Autowired
	private ResponseComUtil responseComUtil;


	/**
	 * 사용자 정의 템플릿 조회
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getIotUsrDashTmpl", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotUsrDashTmpl(HttpServletRequest request,
												@RequestBody TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException {
		List<TbIotUsrDashTmplDTO> tbIotUsrDashTmplList = tbIotUsrDashTmplSvc.retrieveTbIotUsrDashTmplList(tbIotUsrDashTmplDto);
		return responseComUtil.setResponse200ok(tbIotUsrDashTmplList);
	}



	/**
	 * 컴포넌트 설정 template-list 조회
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/getIotDashTmplList", method = RequestMethod.POST)
	public ComResponseDto<?> getIotDashTmplList(HttpServletRequest request,
										@RequestBody TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException {
		HashMap<String, Object> tbIotUsrDashTmplList = tbIotUsrDashTmplSvc.retrieveTbIotDashTmplList(tbIotUsrDashTmplDto);
		return responseComUtil.setResponse200ok(tbIotUsrDashTmplList);
	}





	/**
	 * 사용자 정의 템플릿 등록/수정  한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/saveIotUsrDashTmpl", method = RequestMethod.POST)
	public ComResponseDto<?> saveTbIotUsrDashTmpl(HttpServletRequest request,
											@RequestBody TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException {
		tbIotUsrDashTmplSvc.saveTbIotUsrDashTmpl(tbIotUsrDashTmplDto);
		return responseComUtil.setResponse200ok();
	}

	/**
	 * 사용자 정의 템플릿 등록 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/insertIotDashTmpl", method = RequestMethod.POST)
	public ComResponseDto<?> insertTbIotDashTmpl(HttpServletRequest request,
											@RequestBody TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException {
		tbIotUsrDashTmplSvc.insertTbIotDashTmpl(tbIotUsrDashTmplDto);
		return responseComUtil.setResponse200ok();

	}



	/**
	 * 사용자 정의 템플릿 수정 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/updateIotDashTmpl", method = RequestMethod.POST)
	public ComResponseDto<?> updateTbIotDashTmpl(HttpServletRequest request,
											@RequestBody TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException {
		tbIotUsrDashTmplSvc.updateTbIotDashTmpl(tbIotUsrDashTmplDto);
		return responseComUtil.setResponse200ok();

	}


	/**
	 * 사용자 정의 템플릿 삭제 한다.
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/deleteIotUsrDashTmpl", method = RequestMethod.POST)
	public ComResponseDto<?> deleteTbIotUsrDashTmpl(HttpServletRequest request,
											@RequestBody TbIotUsrDashTmplDTO tbIotUsrDashTmplDto) throws BizException {
		tbIotUsrDashTmplSvc.deleteTbIotUsrDashTmpl(tbIotUsrDashTmplDto);
		return responseComUtil.setResponse200ok();
	}

}
