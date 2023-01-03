package kr.co.scp.ccp.iotuser.ctl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.message.FileMessageSourceConfig;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotuser.dto.TbIotUsrDTO;
import kr.co.scp.ccp.iotuser.dto.TbIotUsrRDTO;
import kr.co.scp.ccp.iotuser.svc.IotUsrSVC;
import kr.co.scp.common.iotCmCd.dto.TbIotCmCdDTO;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins="*", exposedHeaders = {"Content-Disposition"})
@RequestMapping(value = "/online/iotusr")
public class IotUserCTL {
	
	@Autowired
	private FileMessageSourceConfig fileMessageSourceConfig;
	
	@Autowired
	private ResponseComUtil responseComUtil;
	
	@Autowired
	private IotUsrSVC iotUsrSVC;
	 
	/**
	 * 사용자 목록조회 
	 * @param request
	 * @param tbIotUseDTO
	 * @return ComResponseDto<HashMap<String, Object>>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/retrieveIotUsr", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotUsr(HttpServletRequest request, @RequestBody TbIotUsrDTO tbIotUseDTO) throws BizException {
		HashMap<String, Object> tbIotUseDTORn = iotUsrSVC.retrieveIotUsr(tbIotUseDTO);		
		return responseComUtil.setResponse200ok(tbIotUseDTORn);
	}
	
	/**
	 * 사용자 조회 
	 * @param request
	 * @param tbIotUseDTO
	 * @return ComResponseDto<TbIotUsrRDTO>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/retrieveIotUsrBySeq", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotUsrBySeq(HttpServletRequest request, @RequestBody TbIotUsrDTO tbIotUseDTO) throws BizException {
		TbIotUsrRDTO tbIotUseDTORn = iotUsrSVC.retrieveIotUsrBySeq(tbIotUseDTO);		
		return responseComUtil.setResponse200ok(tbIotUseDTORn);
	}
	
	
	/**
	 * 사용자 등록 
	 * @param request
	 * @param tbIotUseDTO
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/createIotUsr", method = RequestMethod.POST)
	public ComResponseDto<?> createIotUsr(HttpServletRequest request, @RequestBody TbIotUsrDTO tbIotUseDTO) throws BizException {
		iotUsrSVC.createIotUsr(tbIotUseDTO);
		return responseComUtil.setResponse200ok();
	}
	
	/**
	 * 사용자 일괄 등록 
	 * @param request
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/createIotUsrAll", method = RequestMethod.POST)
	public ComResponseDto<?> createIotUsrAll(HttpServletRequest request)  throws BizException{
		iotUsrSVC.createIotUsrAll(request);
		return responseComUtil.setResponse200ok();
	} 
	
	/**
	 * 사용자 수정 
	 * @param request
	 * @param tbIotUseDTO
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/updateIotUsr", method = RequestMethod.POST)
	public ComResponseDto<?> updateIotUsr(HttpServletRequest request, @RequestBody TbIotUsrDTO tbIotUseDTO) throws BizException {
		iotUsrSVC.updateIotUsr(tbIotUseDTO);
		return responseComUtil.setResponse200ok();
	}
	
	/**
	 * 사용자 정보 일괄변경 ( 비밀번호초기화, 휴면 해제 , 잠금 해제, 가입/탈퇴여부 일괄 변경 )
	 * @param request
	 * @param tbIotUseDTO
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/updateIotUsrInfo", method = RequestMethod.POST)
	public ComResponseDto<?> updateIotUsrInfo(HttpServletRequest request, @RequestBody TbIotUsrDTO tbIotUseDTO) throws BizException {		
		iotUsrSVC.updateIotUsrInfo(tbIotUseDTO);
		return responseComUtil.setResponse200ok();
	}
	
	/**
	 * 사용자 수정 
	 * @param request
	 * @param tbIotUseDTO
	 * @return ComResponseDto<?>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/deleteIotUsr", method = RequestMethod.POST)
	public ComResponseDto<?> deleteIotUsr(HttpServletRequest request, @RequestBody TbIotUsrDTO tbIotUseDTO) throws BizException {		
		iotUsrSVC.updateIotUsr(tbIotUseDTO);
		return responseComUtil.setResponse200ok();
	}
	
	/**
	 * 아이디 중복확인
	 * @param request
	 * @param tbIotCustDto
	 * @return ComResponseDto<TbIotUsrDTO>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/retrieveDuplicationLgnId", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveDuplicationLgnId(HttpServletRequest request, @RequestBody TbIotUsrDTO tbIotUseDTO) throws BizException {
		String result = iotUsrSVC.retrieveDuplicationLgnId(tbIotUseDTO);		
		TbIotUsrDTO returnObj = new TbIotUsrDTO();
		BigDecimal returnNum = new BigDecimal(result);
		int resultInt = returnNum.compareTo(new BigDecimal("0"));
		if(resultInt != 0) {			
			throw new BizException("duplicationLgnId");
		}else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}		
		return responseComUtil.setResponse200ok(returnObj);
	}
	

	/**
	 * 연락처 중복확인
	 * @param request
	 * @param tbIotCustDto
	 * @return ComResponseDto<TbIotUsrDTO>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/retrieveUsrPhoneDuplChk", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveUsrPhoneDuplChk(HttpServletRequest request, @RequestBody TbIotUsrDTO tbIotUseDTO) throws BizException {
		List<TbIotUsrDTO> outList = iotUsrSVC.retrieveUsrPhoneDuplChk(tbIotUseDTO);	
		TbIotUsrDTO returnObj = new TbIotUsrDTO();
		if (outList.size() > 0) {
			TbIotUsrDTO checkDto = outList.get(0);
			// 사용자 등록일 때
			if ( null == tbIotUseDTO.getUsrSeq() || tbIotUseDTO.getUsrSeq().equals("null")) {
				throw new BizException("duplicationPhoneNo");		
			} else {
				// 사용자 수정일 때
				if (checkDto.getUsrSeq().equals(tbIotUseDTO.getUsrSeq()) 
						&& tbIotUseDTO.getUsrPhoneNo().equals(checkDto.getSmsRcvNo()) ) {
					returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
				} else {
					throw new BizException("duplicationPhoneNo");				
				}				
			}
			
		} else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "availableStatus"));
		}
		return responseComUtil.setResponse200ok(returnObj);
	}
	
	
	/**
	 * 비밀번호 확인
	 * @param request
	 * @param tbIotCustDto
	 * @return ComResponseDto<TbIotUsrDTO>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/retrieveIotUsrPwChk", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotUsrPwChk(HttpServletRequest request, @RequestBody TbIotUsrDTO tbIotUseDTO) throws BizException {
		int result = iotUsrSVC.retrieveIotUsrPwChk(tbIotUseDTO);		
		TbIotUsrDTO returnObj = new TbIotUsrDTO();
		if(result > 0) {
			throw new BizException("incorrectPassword");
		}else {
			returnObj.setMsg(fileMessageSourceConfig.getMessage(ComConstant.DEFAULT_CHAR_SET, "correctPassword"));
		}		
		return responseComUtil.setResponse200ok(returnObj);
	}
	
	/**
	 * 사용자권한 조회
	 * @param request
	 * @param tbIotCustDto
	 * @return ComResponseDto<List<TbIotCmCdDTO>>
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/retrieveIotUsrRoleList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotUsrRoleList(HttpServletRequest request) throws BizException {
		List<TbIotCmCdDTO> returnList = iotUsrSVC.retrieveIotUsrRoleList();
		return responseComUtil.setResponse200ok(returnList);
	}
	
	/**
	 * 엑셀 파일다운로드
	 * 
	 * @param request
	 * @param response
	 * @param comRequestDto
	 * @return HttpServletResponse
	 * @throws BizException
	 * @author joseph
	 */
	@RequestMapping(value = "/excelExampleDown", method = RequestMethod.POST)
	public void excelDownload(HttpServletRequest request, HttpServletResponse response) {
		XSSFWorkbook wb = null;
		String today  = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		String fileName = "USER_ExcelTemplate" + "_" + today + ".xlsx";
		try(ServletOutputStream sos = response.getOutputStream()){ 
			wb = iotUsrSVC.excelCreate();
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
