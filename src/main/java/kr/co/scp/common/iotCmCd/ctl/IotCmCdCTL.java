package kr.co.scp.common.iotCmCd.ctl;

import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.scp.common.iotCmCd.dto.*;
import kr.co.scp.common.iotCmCd.svc.IotCmCdSVC;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/iotcmcd")
public class IotCmCdCTL {

	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;

	@Autowired
	private ResponseComUtil responseComUtil;

	@Autowired
	private IotCmCdSVC iotCmCdSVC;

	/**
	 * 공통코드 조회
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return List<TbIotCmCdDTO>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/retrieveIotCmCd", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCmCd(HttpServletRequest request, @RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		HashMap<String, Object> tbIotUseDTORn = iotCmCdSVC.retrieveIotCmCd(tbIotCmCdDTO);
		return responseComUtil.setResponse200ok(tbIotUseDTORn);
	}
	/**
	 * 공통코드화면: 부모코드 조회
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return List<TbIotCmCdDTO>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/retrieveIotParentCmCd", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotParentCmCd(HttpServletRequest request, @RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		List<TbIotCmCdPDTO> tbIotUseDTORn = iotCmCdSVC.retrieveIotParentCmCd(tbIotCmCdDTO);
		return responseComUtil.setResponse200ok(tbIotUseDTORn);
	}

	/**
	 * 부모코드로 조회
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return List<TbIotCmCdDTO>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/retrieveIotByParentCmCd", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotByParentCmCd(HttpServletRequest request, @RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		List<TbIotCmCdDTO> tbIotUseDTORn = iotCmCdSVC.retrieveIotByParentCmCd(tbIotCmCdDTO);
		return responseComUtil.setResponse200ok(tbIotUseDTORn);
	}


	/**
	 * 부모코드로 조회
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return List<TbIotCmCdDTO>
	 * @throws BizException
	 * @author
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/retrieveIotByParentCmCdOnly", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotByParentCmCdOnly(HttpServletRequest request, @RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		List<HashMap> tbIotUseDTORn = iotCmCdSVC.retrieveIotByParentCmCdOnly(tbIotCmCdDTO);
		return responseComUtil.setResponse200ok(tbIotUseDTORn);
	}

	/**
	 * 부모코드로 조회
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return List<TbIotCmCdDTO>
	 * @throws BizException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/retrieveIotByParentCmCdTwo", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotByParentCmCdTwo(HttpServletRequest request, @RequestBody TbIotCmCdOptionDTO tbIotCmCdOptionDTO) throws BizException {
		List<HashMap> tbIotUseDTORn = iotCmCdSVC.retrieveIotByParentCmCdTwo(tbIotCmCdOptionDTO);
		return responseComUtil.setResponse200ok(tbIotUseDTORn);
	}

	/**
	 * 공통코드 단일조회 by seq
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return TbIotCmCdDTO
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/retrieveIotCmCdById", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCmCdById(HttpServletRequest request, @RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		TbIotCmCdUDTO tbIotCmCdUDTO = iotCmCdSVC.retrieveIotCmCdById(tbIotCmCdDTO);
		return responseComUtil.setResponse200ok(tbIotCmCdUDTO);
	}

	/**
	 * 공통코드 단일조회 by cd_id
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return TbIotCmCdDTO
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/retrieveIotCmCdByCdId", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCmCdByCdId(HttpServletRequest request, @RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		TbIotCmCdUDTO tbIotCmCdUDTO = iotCmCdSVC.retrieveIotCmCdByCdId(tbIotCmCdDTO);
		return responseComUtil.setResponse200ok(tbIotCmCdUDTO);
	}

	/**
	 * 공통코드 신규
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/createIotCmCd", method = RequestMethod.POST)
	public ComResponseDto<?> createIotCmCd(HttpServletRequest request, @RequestBody TbIotCmCdCDTO tbIotCmCdCDTO) throws BizException {
		iotCmCdSVC.createIotCmCd(tbIotCmCdCDTO);
		return responseComUtil.setResponse200ok();
	}

	/**
	 * 공통코드 수정
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/updateIotCmCd", method = RequestMethod.POST)
	public ComResponseDto<?> updateIotCmCd(HttpServletRequest request, @RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		iotCmCdSVC.updateIotCmCd(tbIotCmCdDTO);
		return responseComUtil.setResponse200ok();
	}

	/**
	 * 공통코드 삭제
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/deleteIotCmCd", method = RequestMethod.POST)
	public ComResponseDto<?> deleteIotCmCd(HttpServletRequest request, @RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		iotCmCdSVC.deleteIotCmCd(tbIotCmCdDTO);
		return responseComUtil.setResponse200ok();
	}

	/**
	 * 공통코드명 중복조회
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/retrieveDuplicatedCdNm", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveDuplicatedCdNm(HttpServletRequest request, @RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {

		int result = iotCmCdSVC.retrieveDuplicatedCdNm(tbIotCmCdDTO);
		TbIotCmCdDTO returnObj = new TbIotCmCdDTO();
		if(result>0) {
			throw new BizException("duplicatedCdNm");
		} else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}

		return responseComUtil.setResponse200ok(returnObj);
	}

//	/**
//	 * 공통코드 중복조회
//	 * @param request
//	 * @param TbIotCmCdDTO
//	 * @return ComResponseDto<?>
//	 * @throws BizException
//	 */
	@RequestMapping(value = "/retrieveDuplicatedCdId", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveDuplicatedCdId(HttpServletRequest request, @RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {

		int result = iotCmCdSVC.retrieveDuplicatedCdId(tbIotCmCdDTO);
		TbIotCmCdDTO returnObj = new TbIotCmCdDTO();
		if(result>0) {
			throw new BizException("duplicatedCdId");
		}else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}

		return responseComUtil.setResponse200ok(returnObj);
	}


	/**
	 * 공통코드 SUBSTRING
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return TbIotCmCdDTO
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotCmCdBySubString", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotCmCdBySubString(HttpServletRequest request, @RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		List<TbIotCmCdDTO> tbIotUseDTORn1 = iotCmCdSVC.retrieveIotCmCdBySubString(tbIotCmCdDTO);
		return responseComUtil.setResponse200ok(tbIotUseDTORn1);
	}


	/**
	 * 부모코드로 조회
	 * 룰 설정 파일에서 실행파일 기준으로 조회
	 * @param request
	 * @param TbIotCmCdDTO
	 *  parentCdId
	 * @return List<TbIotCmCdOptionDTO>
	 *  CD_ID(PARAM_KEY), CD_NM
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotByParentCmCdRuntime", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotByParentCmCdRuntime(HttpServletRequest request, @RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		List<TbIotCmCdOptionDTO> tbIotUseDTORn = iotCmCdSVC.retrieveIotByParentCmCdRuntime(tbIotCmCdDTO);
		return responseComUtil.setResponse200ok(tbIotUseDTORn);
	}

	/**
	 * 엑셀 다운로드
	 * @param tbIotCmCdDTO
	 * @return HttpServletResponse
	 * @throws BizException
	 */
	@RequestMapping(value = "/excelDown", method = RequestMethod.POST)
	public void excelDown(HttpServletRequest request, HttpServletResponse response, @RequestBody TbIotCmCdDTO tbIotCmCdDTO) {
		// 로그인한 사람의 정보를 받아와서 엑셀에 넣어줘야 하는 내용
		// 로그인한 사용자 정보 :
		// 2. 속한 조직의 조직명들
		XSSFWorkbook wb = null;
		String today  = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		String fileName = "Common_Code" + "_" + today + ".xlsx";
		try(ServletOutputStream sos = response.getOutputStream()){
			wb = iotCmCdSVC.excelCreate(tbIotCmCdDTO);

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

	/**
	 * 공통코드 코드아이디 채번
	 * @param request
	 * @param TbIotCmCdDTO
	 * @return TbIotCmCdDTO
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotMaxOrder", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotMaxOrder(HttpServletRequest request, @RequestBody TbIotCmCdDTO tbIotCmCdDTO) throws BizException {
		String newOrder = iotCmCdSVC.retrieveIotMaxOrder(tbIotCmCdDTO);
		TbIotCmCdDTO out = new TbIotCmCdDTO();
	    out.setCdId(newOrder);
		System.out.println("newOrder = " + newOrder);
		return responseComUtil.setResponse200ok(out);
	}

}
