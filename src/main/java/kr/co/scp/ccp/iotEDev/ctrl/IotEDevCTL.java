package kr.co.scp.ccp.iotEDev.ctrl;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.scp.ccp.iotEDev.dto.EDevSrchResDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDetHistDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevDTO;
import kr.co.scp.ccp.iotEDev.svc.IotEDevSVC;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@CrossOrigin(origins = "*", exposedHeaders = { "Content-Disposition" })
@RequestMapping(value = "/online/iotedev")
public class IotEDevCTL {

	@Autowired
	private ResponseComUtil responseComUtil;

	@Autowired
	private IotEDevSVC iotEDevSVC;

	/**
	 * 고객별 장비 목록 조회 (무선)
	 * 
	 * @param request
	 * @param tbIotEDevDto
	 * @return List<tbIotEDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotEDev", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotEDev(HttpServletRequest request, @RequestBody TbIotEDevDTO tbIotEDevDTO) throws BizException {
		if (AuthUtils.getUser() != null && AuthUtils.getUser().getSvcCd() != null && !"".equals(AuthUtils.getUser().getSvcCd())) {
			tbIotEDevDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
		}
		if (AuthUtils.getUser() != null && AuthUtils.getUser().getCustSeq() != null && !"".equals(AuthUtils.getUser().getCustSeq())) {
			tbIotEDevDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		}

		EDevSrchResDTO eDevSrchResDTORn = iotEDevSVC.retrieveIotEDev(tbIotEDevDTO);

		return responseComUtil.setResponse200ok(eDevSrchResDTORn);
	}

	/**
	 * 고객별 장비 속성 조회
	 * 
	 * @param request
	 * @param tbIotEDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotEDevAttb", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotEDevAttb(HttpServletRequest request, @RequestBody TbIotEDevDTO tbIotEDevDTO) throws BizException {

		List<TbIotEDevDTO> tbIotEDevDTORn1 = iotEDevSVC.retrieveIotEDevAttb(tbIotEDevDTO);

		return responseComUtil.setResponse200ok(tbIotEDevDTORn1);
	}

	/**
	 * 수신자 목록 조회
	 * 
	 * @param request
	 * @param tbIotEDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotEDevAddr", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotEDevAddr(HttpServletRequest request, @RequestBody TbIotEDevDTO tbIotEDevDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotEDevDTO> tbIotEDevDTORn2 = iotEDevSVC.retrieveIotEDevAddr(tbIotEDevDTO);

		return responseComUtil.setResponse200ok(tbIotEDevDTORn2);
	}

	/**
	 * 발송 상세 조회
	 * 
	 * @param request
	 * @param tbIotEDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotEDevSend", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotEDevSend(HttpServletRequest request, @RequestBody TbIotEDevDTO tbIotEDevDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotEDevDTO> tbIotEDevDTORn3 = iotEDevSVC.retrieveIotEDevSend(tbIotEDevDTO);

		return responseComUtil.setResponse200ok(tbIotEDevDTORn3);
	}

	/**
	 * 장비 유형별 템플릿 헤더 조회
	 * 
	 * @param request
	 * @param tbIotEDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveTemplateList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTemplateList(HttpServletRequest request, @RequestBody TbIotEDetHistDTO tbIotEDetHistDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotEDetHistDTO> tbIotEDetHistDTOn = iotEDevSVC.retrieveTemplateList(tbIotEDetHistDTO);

		return responseComUtil.setResponse200ok(tbIotEDetHistDTOn);
	}

	/**
	 * 장비 유형 목록 조회
	 * 
	 * @param request
	 * @param tbIotEDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotDevCls", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotDevCls(HttpServletRequest request) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotEDetHistDTO> tbIotEDetHistDTO = iotEDevSVC.retrieveIotDevCls();

		return responseComUtil.setResponse200ok(tbIotEDetHistDTO);
	}

	/**
	 * 장비 유형 목록 조회
	 * 
	 * @param request
	 * @param tbIotEDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotDetStatus", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotDetStatus(HttpServletRequest request) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotEDetHistDTO> tbIotEDetHistDTO = iotEDevSVC.retrieveIotDetStatus();

		return responseComUtil.setResponse200ok(tbIotEDetHistDTO);
	}

	/**
	 * 장비 유형에 맞는 장비 모델 목록 조회
	 * 
	 * @param request
	 * @param tbIotEDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveDevMdlList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveDevMdlList(HttpServletRequest request, @RequestBody TbIotEDetHistDTO tbIotEDetHistDTO) throws BizException {

		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		List<TbIotEDetHistDTO> tbIotEDetHistDTOn = iotEDevSVC.retrieveDevMdlList(tbIotEDetHistDTO);

		return responseComUtil.setResponse200ok(tbIotEDetHistDTOn);
	}

	/**
	 * 장애이력 데이터 조회
	 * 
	 * @param request
	 * @param tbIotEDevDto
	 * @return List<tbIotDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotEntrDevDetList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotEntrDevDetList(HttpServletRequest request, @RequestBody TbIotEDetHistDTO tbIotEDetHistDTO) throws BizException {
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		HashMap<String, Object> tbIotEDetHistDTOn = iotEDevSVC.retrieveIotEntrDevDetList(tbIotEDetHistDTO);

		return responseComUtil.setResponse200ok(tbIotEDetHistDTOn);
	}

	/**
	 * 엑셀 파일다운로드
	 * 
	 * @param request
	 * @param response
	 * @param comRequestDto
	 * @return
	 */
	@RequestMapping(value = "/downloadIotEntrDevDetList", method = RequestMethod.POST, headers = "X-APIVERSION=1")
	public void downloadIotEntrDevDetList(HttpServletRequest request, HttpServletResponse response, @RequestBody TbIotEDetHistDTO tbIotEDetHistDTO) {
		XSSFWorkbook wb = null;

		String fileName = "entrDevDetList" + "_" + new Random().nextInt(1000) + ".xlsx";

		try {
			wb = iotEDevSVC.downloadExcelEntrDevDetList(tbIotEDetHistDTO);
			String header = request.getHeader("User-Agent");
			fileName = ExcelUtils.urlDecodeFileName(header, fileName);
			ExcelUtils.addFileDownloadHeader(response, fileName);
			wb.write(response.getOutputStream());

		} catch (Exception e) {
			ExcelUtils.addErrorFileDownloadHeader(response);
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}
			} catch (Exception ignore) {
			}
		}
	}

	/**
	 * 고객별 장비 목록 조회 (출입관리기)
	 * 
	 * @param request
	 * @param tbIotEDevDto
	 * @return List<tbIotEDevDto>
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotEDevDoor", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotEDevDoor(HttpServletRequest request, @RequestBody TbIotEDevDTO tbIotEDevDTO) throws BizException {
		if (AuthUtils.getUser() != null && AuthUtils.getUser().getSvcCd() != null && !"".equals(AuthUtils.getUser().getSvcCd())) {
			tbIotEDevDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
		}
		if (AuthUtils.getUser() != null && AuthUtils.getUser().getCustSeq() != null && !"".equals(AuthUtils.getUser().getCustSeq())) {
			tbIotEDevDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		}

		EDevSrchResDTO eDevSrchResDTORn = iotEDevSVC.retrieveIotEDevDoor(tbIotEDevDTO);

		return responseComUtil.setResponse200ok(eDevSrchResDTORn);
	}

}