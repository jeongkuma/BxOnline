package kr.co.scp.ccp.iotEDev.ctrl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.WebCommUtil;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevDTO;
import kr.co.scp.ccp.iotEDev.dto.TbIotEDevRegiDTO;
import kr.co.scp.ccp.iotEDev.svc.IotEDevRegiSVC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "*", exposedHeaders = { "Content-Disposition" })
@RequestMapping(value = "/online/iotedevregi")
public class IotEDevRegiCTL {

	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;
	@Autowired
	private ResponseComUtil responseComUtil;

	@Autowired
	private IotEDevRegiSVC iotEDevRegiSVC;

//	@Autowired
//	private ObjectMapper objectMapper = null;

	@Value("${file.upload-dir}")
	private String FILE_UPLOAD_PATH;

	/**
	 * 식별번호(CTN)중복체크
	 *
	 * @param request
	 * @param tbIotEdevRegDTO
	 * @return result
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveCtnDup", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveCtnDup(HttpServletRequest request, @RequestBody TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
		int result = iotEDevRegiSVC.retrieveCtnDup(tbIotEDevRegiDTO);
		TbIotEDevRegiDTO returnDto = new TbIotEDevRegiDTO();
		if (result > 0) {
			throw new BizException("duplicationCtn");
		} else {
			returnDto.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "duple.use.ctn"));
		}
		return responseComUtil.setResponse200ok(returnDto);
	}

	/**
	 * 설치번호 중복체크
	 *
	 * @param request
	 * @param tbIotEdevRegDTO
	 * @return result
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveInstNoDup", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveInstNoDup(HttpServletRequest request, @RequestBody TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
		int result = iotEDevRegiSVC.retrieveInstNoDup(tbIotEDevRegiDTO);
		if (result > 0) {
			throw new BizException("duplicationInstNo");
		} else {
			tbIotEDevRegiDTO.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "duple.use.instNo"));
		}

		return responseComUtil.setResponse200ok(tbIotEDevRegiDTO);
	}

	/**
	 * UUID 중복체크
	 *
	 * @param request
	 * @param tbIotEdevRegDTO
	 * @return result
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveUuidDup", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveUuidDup(HttpServletRequest request, @RequestBody TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
		int result = iotEDevRegiSVC.retrieveUuidDup(tbIotEDevRegiDTO);
		if (result > 0) {
			throw new BizException("duplicationUuid");
		} else {
			tbIotEDevRegiDTO.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "duple.use.uuid"));
		}

		return responseComUtil.setResponse200ok(tbIotEDevRegiDTO);
	}


	/**
	 * UNAME 중복체크
	 *
	 * @param request
	 * @param tbIotEdevRegDTO
	 * @return result
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveUnameDup", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveUnameDup(HttpServletRequest request, @RequestBody TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
		int result = iotEDevRegiSVC.retrieveUnameDup(tbIotEDevRegiDTO);
		if (result > 0) {
			throw new BizException("duplicationUname");
		} else {
			tbIotEDevRegiDTO.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "duple.use.uname"));
		}

		return responseComUtil.setResponse200ok(tbIotEDevRegiDTO);
	}

	/**
	 * 가입 장비 등록
	 *
	 * @param request
	 * @param tbIotEDevRegiDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/createEntrDev", method = RequestMethod.POST)
//	@ResponseBody
	public ComResponseDto<?> createEntrDev(HttpServletRequest request, @RequestBody TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
		if (iotEDevRegiSVC.retrieveCtnDup(tbIotEDevRegiDTO) > 0) {
			throw new BizException("duplicationCtn"); // 식별번호(MAC 주소)
//		} else if (iotEDevRegiSVC.retrieveUnameDup(tbIotEDevRegiDTO) > 0) {
//			throw new BizException("duplicationUname"); // 식별명
		} else if (iotEDevRegiSVC.retrieveUuidDup(tbIotEDevRegiDTO) > 0) {
			throw new BizException("duplicationUuid"); // Entity Id
		} else {
			iotEDevRegiSVC.createEntrDev(request, tbIotEDevRegiDTO);
		}

		return responseComUtil.setResponse200ok();
	}

	@RequestMapping(value = "/retrieveEntrDevInfo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveEntrDevInfo(HttpServletRequest req, @RequestBody TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {

		tbIotEDevRegiDTO = iotEDevRegiSVC.retrieveEntrDevInfo(tbIotEDevRegiDTO);

		return responseComUtil.setResponse200ok(tbIotEDevRegiDTO);
	}

	/**
	 * 가입 장비 수정
	 *
	 * @param request
	 * @param tbIotEDevRegiDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/updateEntrDev", method = RequestMethod.POST)
	public ComResponseDto<?> updateEntrDev(HttpServletRequest request, @RequestBody TbIotEDevRegiDTO tbIotEDevRegiDTO) throws BizException {
		if (iotEDevRegiSVC.retrieveCtnDup(tbIotEDevRegiDTO) > 0) {
			throw new BizException("duplicationCtn"); // 식별번호(MAC 주소)
//		} else if (iotEDevRegiSVC.retrieveUnameDup(tbIotEDevRegiDTO) > 0) {
//			throw new BizException("duplicationUname"); // 식별명
		} else if (iotEDevRegiSVC.retrieveUuidDup(tbIotEDevRegiDTO) > 0) {
			throw new BizException("duplicationUuid"); // Entity Id
		} else {
			iotEDevRegiSVC.updateEntrDev(tbIotEDevRegiDTO);
		}

		return responseComUtil.setResponse200ok();
	}

	/**
	 * 가입장비 목록 엑셀 파일다운로드
	 *
	 * @param request
	 * @param response
	 * @param comRequestDto
	 * @return
	 */
	@RequestMapping(value = "/downloadEntrDevList", method = RequestMethod.POST)
	public void downloadEntrDevList(HttpServletRequest request, HttpServletResponse response, @RequestBody TbIotEDevDTO tbIotEDevDTO) {
		if (AuthUtils.getUser() != null && AuthUtils.getUser().getSvcCd() != null && !"".equals(AuthUtils.getUser().getSvcCd())) {
			tbIotEDevDTO.setSvcCd(AuthUtils.getUser().getSvcCd());
		}
		if (AuthUtils.getUser() != null && AuthUtils.getUser().getCustSeq() != null && !"".equals(AuthUtils.getUser().getCustSeq())) {
			tbIotEDevDTO.setCustSeq(AuthUtils.getUser().getCustSeq());
		}

		XSSFWorkbook wb = null;

		String fileName = "Device_condition_info" + "_" + new Random().nextInt(1000) + ".xlsx";

		try (ServletOutputStream sos = response.getOutputStream()) {

			wb = iotEDevRegiSVC.downloadEntrDevList(tbIotEDevDTO);
			String header = request.getHeader("User-Agent");
			fileName = ExcelUtils.urlDecodeFileName(header, fileName);
			ExcelUtils.addFileDownloadHeader(response, fileName);
			wb.write(sos);

		} catch (Exception e) {
			ExcelUtils.addErrorFileDownloadHeader(response);
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
	 * 가입장비 일괄등록
	 *
	 * @param request
	 * @param response
	 * @param comRequestDto
	 * @return
	 */
	@RequestMapping(value = "/uploadBatchEntrDev", method = RequestMethod.POST)
	public ComResponseDto<?> uploadBatchEntrDev(HttpServletRequest request, HttpServletResponse response) throws BizException {
		iotEDevRegiSVC.uploadBatchEntrDev(request);
		return responseComUtil.setResponse200ok();
	}

	/**
	 * 가입장비 일괄등록 Excel file
	 *
	 * @param request
	 * @param response
	 * @param comRequestDto
	 * @return
	 */
	@RequestMapping(value = "downSmplEDevElsx", method = RequestMethod.POST)
	public void downSmplEDevElsx(HttpServletRequest request, HttpServletResponse response, @RequestBody TbIotEDevDTO tbIotEDevDTO) {
		XSSFWorkbook wb = null;

		String fileName = "Device_registration_template.xlsx";

		try (ServletOutputStream sos = response.getOutputStream()) {

			wb = iotEDevRegiSVC.downSmplEDevElsx(tbIotEDevDTO);
			String header = request.getHeader("User-Agent");
			fileName = ExcelUtils.urlDecodeFileName(header, fileName);
			ExcelUtils.addFileDownloadHeader(response, fileName);
			wb.write(sos);

		} catch (Exception e) {
			ExcelUtils.addErrorFileDownloadHeader(response);
			throw new BizException(e, "excellDownloadError");
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}
			} catch (Exception ignore) {
			}
		}

	}


}
