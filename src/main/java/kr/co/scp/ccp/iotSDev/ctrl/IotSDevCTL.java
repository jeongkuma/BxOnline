package kr.co.scp.ccp.iotSDev.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kr.co.scp.ccp.iotSDev.dto.TbIotSDevAtbDTO;
import kr.co.scp.ccp.iotSDev.dto.TbIotSDevDTO;
import kr.co.scp.ccp.iotSDev.svc.IotSDevSVC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;

import kr.co.auiot.common.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 고객장비 Controller
 * @author pkjean
 *
 */
@Slf4j
@RestController
@CrossOrigin(origins="*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/iotsdev")
public class IotSDevCTL {

	@Autowired
	private ResponseComUtil responseComUtil;

	@Autowired
	private IotSDevSVC iotSDevSVC;

	/**
	 * 서비스별장비 목록 조회
	 * @param request
	 * @param tbIotSDevDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveSDevList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveSDevList(HttpServletRequest request, @RequestBody TbIotSDevDTO tbIotSDevDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotSDevDTO> tbIotSDevDTORn = iotSDevSVC.retrieveSDevList(tbIotSDevDTO);

		return responseComUtil.setResponse200ok(tbIotSDevDTORn);
	}


	/**
	 * 서비스별장비 정보 조회
	 * @param request
	 * @param tbIotSDevDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveSDev", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveSDev(HttpServletRequest request, @RequestBody TbIotSDevDTO tbIotSDevDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		TbIotSDevDTO tbIotSDevDTORn = iotSDevSVC.retrieveSDev(tbIotSDevDTO);

		return responseComUtil.setResponse200ok(tbIotSDevDTORn);
	}


	/**
	 * 서비스별 장비 속성 조회
	 * @param request
	 * @param tbIotSDevDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveSDevAttb", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveSDevAttb(HttpServletRequest request, @RequestBody TbIotSDevDTO tbIotSDevDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotSDevAtbDTO> tbIotEDevDTORn = new ArrayList<TbIotSDevAtbDTO>();
		try {
			tbIotEDevDTORn = iotSDevSVC.retrieveSDevAttb(tbIotSDevDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseComUtil.setResponse200ok(tbIotEDevDTORn);
	}

	/**
	 * 서비스별 장비 가입속성 조회
	 * @param request
	 * @param tbIotSDevDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveSDevJoinAttbs", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveSDevJoinAttbs(HttpServletRequest request, @RequestBody TbIotSDevDTO tbIotSDevDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotSDevAtbDTO> tbIotEDevDTORn = new ArrayList<TbIotSDevAtbDTO>();
		try {
			tbIotEDevDTORn = iotSDevSVC.retrieveSDevJoinAttbs(tbIotSDevDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseComUtil.setResponse200ok(tbIotEDevDTORn);
	}

	/**
	 * 서비스별 장비 가입속성 조회
	 * @param request
	 * @param tbIoTsDevAtbDto
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveSDevSndColPeriodList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveSDevSndColPeriodList(HttpServletRequest request, @RequestBody TbIotSDevAtbDTO tbIoTsDevAtbDto) throws BizException {

		HashMap<String, Object> sDevAtbSetList = iotSDevSVC.retrieveSDevSndColPeriodList(tbIoTsDevAtbDto);
		return responseComUtil.setResponse200ok(sDevAtbSetList);

	}

	/**
	 * 서비스별 장비 유형 조회
	 * @param request
	 * @param tbIotSDevDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveSvcDevClsList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveSvcDevClsList(HttpServletRequest request, @RequestBody TbIotSDevDTO tbIotSDevDTO) throws BizException {
//		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotSDevDTO> tbIotSDevDTORn = new ArrayList<TbIotSDevDTO>();
		try {


			if("SD00000003".equals(tbIotSDevDTO.getSvcCd()) || "ALL".equals(tbIotSDevDTO.getSvcCd()) || "all".equals(tbIotSDevDTO.getSvcCd())) {
				tbIotSDevDTO.setSvcCd(null);
			} else if( AuthUtils.getUser().getSvcCd() != null && !AuthUtils.getUser().getSvcCd().isEmpty()  && ("".equals(tbIotSDevDTO.getSvcCd()) || null == tbIotSDevDTO.getSvcCd())) {
				tbIotSDevDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
			}

			if (!"GN00000038".equals(AuthUtils.getUser().getRoleCdId())){
				tbIotSDevDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
			}

			tbIotSDevDTORn = iotSDevSVC.retrieveSvcDevClsList(tbIotSDevDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseComUtil.setResponse200ok(tbIotSDevDTORn);
	}

	/**
	 * 서비스별 장비유형별 모델 조회
	 * @param request
	 * @param tbIotSDevDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveSvcDevMdlList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveSvcDevMdlList(HttpServletRequest request, @RequestBody TbIotSDevDTO tbIotSDevDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotSDevDTO> tbIotSDevDTORn = new ArrayList<TbIotSDevDTO>();
		try {
			if( AuthUtils.getUser().getSvcCd() != null && !AuthUtils.getUser().getSvcCd().isEmpty() ) {
				tbIotSDevDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
			}

			tbIotSDevDTORn = iotSDevSVC.retrieveSvcDevMdlList(tbIotSDevDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseComUtil.setResponse200ok(tbIotSDevDTORn);
	}


	/**
	 * 서비스별 장비 속성_수집
	 * @param request
	 * @param tbIotSDevDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveSDevColAttb", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveSDevColAttb(HttpServletRequest request, @RequestBody TbIotSDevDTO tbIotSDevDTO) throws BizException {

		List<TbIotSDevAtbDTO> tbIotEDevDTORn = new ArrayList<TbIotSDevAtbDTO>();
		try {
			tbIotEDevDTORn = iotSDevSVC.retrieveSDevColAttb(tbIotSDevDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return responseComUtil.setResponse200ok(tbIotEDevDTORn);
	}


}
