package kr.co.scp.ccp.iotDev.ctl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import kr.co.scp.ccp.iotDev.dto.TbIotDevCtlDTO;
import kr.co.scp.ccp.iotDev.dto.TbIotDevDTO;
import kr.co.scp.ccp.iotDev.svc.IotDevCtlSVC;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/online/iotdev")
public class IotDevCtlCTL {


	@Autowired
	private ResponseComUtil responseComUtil;

	@Autowired
	private IotDevCtlSVC iotDevCtlSVC;


	/**
	 * 장비모델 중복 조회
	 * @param request
	 * @param tbIotDevCtlDTO
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 * @throws IOException
	 */
	@RequestMapping(value = "/retrieveIotDup", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotDup(HttpServletRequest request, @RequestBody TbIotDevCtlDTO tbIotDevCtl1DTO) throws BizException, IOException {



			log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
			int result = iotDevCtlSVC.retrieveIotDup(tbIotDevCtl1DTO);
			return responseComUtil.setResponse200ok(result);
	}



			/**
			 * 장비모델 중복 조회
			 * @param request
			 * @param tbIotDevDto
			 * @return List<tbIotDevDto>
			 * @throws BizException
			 * @throws IOException
			 */
			@RequestMapping(value = "/retrieveIotMdlDup", method = RequestMethod.POST)
			public ComResponseDto<?> retrieveIotMdlDup(HttpServletRequest request, @RequestBody TbIotDevCtlDTO tbIotDevCtl1DTO) throws BizException, IOException {



					log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
					int result = iotDevCtlSVC.retrieveIotMdlDup(tbIotDevCtl1DTO);
					return responseComUtil.setResponse200ok(result);


//		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
//		String xmlurl= "mapper/TbIotDevCtl.xml";

//		Reader reader = Resources.getResourceAsReader(xmlurl);

//		SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);

//		SqlSession sql = sqlMapper.openSession();

//		ArrayList<?> list = (ArrayList<?>)sql.selectList("TbiotDevCtl.retrieveIotDup");

//		iotDevCtlDto.cdCnt = (int)list.get(0);

//		return responseComUtil.setResponse200ok();
	}



/**
 * 장비 복사
 * @param request
 * @param tbIotDevDto
 * @return List<tbIotDevDto>
 * @throws BizException
 */
	@RequestMapping(value = "/insertIotDevCopy", method = RequestMethod.POST)
	public ComResponseDto<?> insertIotDevCopy(HttpServletRequest request, @RequestBody TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());

		iotDevCtlSVC.insertIotDevCopy(tbIotDevCtlDTO);

		return responseComUtil.setResponse200ok();
	}



	/**
	 * 서비스 유형 가져오기
	 * @param request
	 * @param tbIotDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 * @throws IOException
	 */

	@RequestMapping(value = "/retrieveIotSvcbyHd", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotSvcbyHd(HttpServletRequest request,	@RequestBody TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
			log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
			List<TbIotDevCtlDTO> devCtlListMap = iotDevCtlSVC.retrieveIotSvcbyHd(tbIotDevCtlDTO);

			return responseComUtil.setResponse200ok(devCtlListMap);

	}


	/**
	 * 서비스 유형별 장비 유형 가져오기
	 * @param request
	 * @param tbIotDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 * @throws IOException
	 */

	@RequestMapping(value = "/retrieveIotClsbyHd", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotClsbyHd(HttpServletRequest request,	@RequestBody TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
			log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
			List<TbIotDevCtlDTO> devCtlListMap = iotDevCtlSVC.retrieveIotClsbyHd(tbIotDevCtlDTO);

			return responseComUtil.setResponse200ok(devCtlListMap);

	}

	/**
	 * 장비 유형별 서비스 가져오기
	 * @param request
	 * @param tbIotDevDTO
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 * @throws IOException
	 */

	@RequestMapping(value = "/retrieveIotHdbyCls", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotHdbyCls(HttpServletRequest request,	@RequestBody TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotDevCtlDTO> devCtlListMap = iotDevCtlSVC.retrieveIotHdbyCls(tbIotDevCtlDTO);

		return responseComUtil.setResponse200ok(devCtlListMap);

	}

	/**
	 * 모델 밸류 중복 조회
	 * @param request
	 * @param tbIotDevCtlDTO
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 * @throws IOException
	 */
	@RequestMapping(value = "/retrieveIotDevMdlValCnt", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotDevMdlValCnt(HttpServletRequest request, @RequestBody TbIotDevCtlDTO tbIotDevCtlDTO) throws BizException, IOException {

			log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
			int result = iotDevCtlSVC.retrieveIotDevMdlValCnt(tbIotDevCtlDTO);
			return responseComUtil.setResponse200ok(result);
	}
}
