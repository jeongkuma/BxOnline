package kr.co.scp.ccp.iotMgnt.ctl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.util.JsonUtils;
import kr.co.abacus.common.util.LinkedHashMap;
import kr.co.abacus.common.util.ResponseComUtil;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevAtbDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevAtbSetDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevDetSetDTO;
import kr.co.scp.ccp.iotMgnt.dto.IotMgntSDevMDTO;
import kr.co.scp.ccp.iotMgnt.svc.IotMgntSVC;
import kr.co.scp.ccp.iotRoleMap.dto.TbIotRoleMapDTO;
import kr.co.scp.common.rule.dto.TbIotDevApiRuleParamDTO;
import kr.co.scp.common.rule.dto.TbIotDevColRuleDTO;
import kr.co.scp.common.rule.dto.TbIotValRuleDTO;
import kr.co.auiot.common.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(value = "*", exposedHeaders = { "Content-Disposition" })
@RequestMapping(value = "/online/iotMgnt")
public class IotMgntCTL {

	@Autowired
	private IotMgntSVC iotMgntSVC;

	@SuppressWarnings("unused")
	@Autowired
	private ResponseComUtil responseComUtil;
	
	
	
	/**
	 * 서비스 목록
	 *
	 * @param request
	 * @param IotMgntDTO
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/retrieveIotByService", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveTbIotCtrlAttbList(HttpServletRequest request, @RequestBody IotMgntDTO iotMgntDTO) throws BizException {
		
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());		
		HashMap<String, Object> retrieveIotByService = iotMgntSVC.retriveService(iotMgntDTO);
		
		return responseComUtil.setResponse200ok(retrieveIotByService);
	}
	
	/**
	 * 엑셀다운
	 *
	 * @param request
	 * @param IotMgntDTO
	 * @return
	 * @throws BizException
	 */
	@SuppressWarnings("resource")
	@RequestMapping(value = "/retriveAssignSvc", method = RequestMethod.POST)
	public void retriveAssignSvc(HttpServletRequest request, HttpServletResponse response,
			@RequestBody IotMgntDTO iotMgntDTO) throws BizException {
		XSSFWorkbook wb = null;
		String fileName = "";
		try {
			log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
			if (iotMgntDTO.getGubun().equals("S")) {
				// 서비스
				fileName = "서비스.xlsx";
				wb = iotMgntSVC.retriveServiceList(iotMgntDTO);
			} else if (iotMgntDTO.getGubun().equals("SA")) {
				// 서비스 권한
				fileName = "서비스 권한.xlsx";
				wb = iotMgntSVC.retriveServiceAuthList(iotMgntDTO);
			} else if (iotMgntDTO.getGubun().equals("SD")) {
				// 장비속성
				if(iotMgntDTO.getType().equals("D")) {
					fileName = "장비.xlsx";
					wb = iotMgntSVC.retriveDevList(iotMgntDTO);
				}
				if(iotMgntDTO.getType().equals("DA")) {
					fileName = "장비속성.xlsx";
					wb = iotMgntSVC.retriveDevAtbList(iotMgntDTO);
				}
				if(iotMgntDTO.getType().equals("DAS")) {
					fileName = "장비속성세트.xlsx";
					wb = iotMgntSVC.retriveDevAtbSetList(iotMgntDTO);
				}
				if(iotMgntDTO.getType().equals("DD")) {
					fileName = "장비이상징후.xlsx";
					wb = iotMgntSVC.retriveDevDetSetList(iotMgntDTO);
				}
				if(iotMgntDTO.getType().equals("P")) {
					fileName = "파싱룰.xlsx";
					wb = iotMgntSVC.retriveDevRuleParseList(iotMgntDTO);
				}
				if(iotMgntDTO.getType().equals("A")) {
					fileName = "검증룰.xlsx";
					wb = iotMgntSVC.retriveDevRuleValList(iotMgntDTO);
				}
				if(iotMgntDTO.getType().equals("M")) {
					fileName = "매핑룰.xlsx";
					wb = iotMgntSVC.retriveDevRuleMapList(iotMgntDTO);
				}
				if(iotMgntDTO.getType().equals("S")) {
					fileName = "서비스호출.xlsx";
					wb = iotMgntSVC.retriveDevRuleServiceList(iotMgntDTO);
				}
			}
			if(iotMgntDTO.getGubun().equals("AV")) {
				fileName = "API검증.xlsx";
				wb = iotMgntSVC.retriveDevRuleApiList(iotMgntDTO);
			}
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
	 * 엑셀업로드
	 *
	 * @param request
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value = "/svcUploadSvc", method = RequestMethod.POST)
	public ComResponseDto<?> svcUploadSvc(HttpServletRequest request, @RequestParam String jsonData )  throws BizException{

		IotMgntDTO iotMgntDto = JsonUtils.fromJson(jsonData, IotMgntDTO.class);
		if(iotMgntDto.getGubun().equals("S")) {
			// 서비스 UPLOAD
			iotMgntSVC.svcUploadSvc(request);
		} else if(iotMgntDto.getGubun().equals("SA")) {
			//서비스 권한 UPLOAD
			iotMgntSVC.svcRoleMapUploadSvc(request);
		} else if(iotMgntDto.getGubun().equals("AV")) {
			//API 검증 UPLOAD
			iotMgntDto.setType("A");
			iotMgntSVC.svcRoleRuleValUploadSvc(request,iotMgntDto);
		}
		if(iotMgntDto.getGubun().equals("SD")) {
			//룰 업로드
			if(iotMgntDto.getType().equals("D"))
				iotMgntSVC.deviceUploadSvc(request);
			if(iotMgntDto.getType().equals("DA"))
				iotMgntSVC.deviceAttrUploadSvc(request);
			if(iotMgntDto.getType().equals("DAS"))
				iotMgntSVC.deviceAttrSetUploadSvc(request);
			if(iotMgntDto.getType().equals("DD"))
				iotMgntSVC.deviceDetSetUploadSvc(request);
			if(iotMgntDto.getType().equals("P"))
				iotMgntSVC.svcRoleRuleParseUploadSvc(request, iotMgntDto);
			if(iotMgntDto.getType().equals("A"))
				iotMgntSVC.svcRoleRuleValUploadSvc(request, iotMgntDto);
			if(iotMgntDto.getType().equals("M"))
				iotMgntSVC.svcRoleRuleParseUploadSvc(request ,iotMgntDto);
			if(iotMgntDto.getType().equals("S"))
				iotMgntSVC.apiValUploadSvc(request);
		}

		return responseComUtil.setResponse200ok();
	}
	
	
	/**
	 * 엑셀다운
	 *
	 * @param request
	 * @param TbIotCtrlHistDTO
	 * @return
	 * @throws BizException
	 */
	
	@RequestMapping(value = "/retriveAssignSvcJson", method = RequestMethod.POST)
	public ComResponseDto<?> retriveAssignSvcJson(HttpServletRequest request, HttpServletResponse response,
			@RequestBody IotMgntDTO iotMgntDTO) throws BizException {
		LinkedHashMap<String, Object> rnMAP = new LinkedHashMap<String, Object>() ;
		HashMap<String, Object> tbIotSvcMap = null;
		List<TbIotRoleMapDTO> tbIotRoleMapDTO = null;
		List<IotMgntSDevMDTO> iotMgntSDevMDTO = null;
		List<IotMgntSDevAtbDTO> iotMgntSDevAtbDTO = null;
		List<IotMgntSDevAtbSetDTO> iotMgntSDevAtbSetDTO = null;
		List<IotMgntSDevDetSetDTO> iotMgntSDevDetSetDTO = null;
		List<TbIotDevColRuleDTO> tbIotDevColRuleDTO = null;
		List<TbIotDevColRuleDTO> tbIotDevColRuleDTO2 = null;
		List<TbIotValRuleDTO> tbIotValRuleDTO = null;
		List<TbIotDevApiRuleParamDTO> tbIotDevApiRuleParamDTO = null;
		
		log.debug("====== " + ReqContextComponent.getComInfoDto().toString());
		if (iotMgntDTO.getGubun().equals("S")) {
			// 서비스
			tbIotSvcMap = iotMgntSVC.retriveServiceListSvc(iotMgntDTO);
			return responseComUtil.setResponse200ok(tbIotSvcMap);
		} else if (iotMgntDTO.getGubun().equals("SA")) {
			// 서비스 권한
			tbIotRoleMapDTO = iotMgntSVC.retriveServiceAuthListQuery(iotMgntDTO);
			return responseComUtil.setResponse200ok(tbIotRoleMapDTO);
		} else if (iotMgntDTO.getGubun().equals("SD")) {
			// 장비속성 전체 
			if(iotMgntDTO.getType().equals("SDA")) {
				iotMgntSDevMDTO = iotMgntSVC.retriveDevListQuery(iotMgntDTO);
				iotMgntSDevAtbDTO = iotMgntSVC.retriveDevAtbListQuery(iotMgntDTO);
				iotMgntSDevAtbSetDTO = iotMgntSVC.retriveDevAtbSetListQuery(iotMgntDTO);
				iotMgntSDevDetSetDTO = iotMgntSVC.retriveDevDetSetListQuery(iotMgntDTO);
				
				rnMAP.put("devM", iotMgntSDevMDTO);
				rnMAP.put("devAttb", iotMgntSDevAtbDTO);
				rnMAP.put("devAttbSet", iotMgntSDevAtbSetDTO);
				rnMAP.put("devDetSet", iotMgntSDevDetSetDTO);
				
				return responseComUtil.setResponse200ok(rnMAP);
			} if (iotMgntDTO.getType().equals("SDR")) {
				tbIotDevColRuleDTO = iotMgntSVC.retriveDevRuleParseListQuery(iotMgntDTO);
				tbIotValRuleDTO = iotMgntSVC.retriveDevRuleValListQuery(iotMgntDTO);
				tbIotDevColRuleDTO2 = iotMgntSVC.retriveDevRuleMapListQuery(iotMgntDTO);
				tbIotDevApiRuleParamDTO = iotMgntSVC.retriveDevRuleServiceListQuery(iotMgntDTO);
				
				rnMAP.put("parse", tbIotDevColRuleDTO);
				rnMAP.put("valid", tbIotValRuleDTO);
				rnMAP.put("mappig", tbIotDevColRuleDTO2);
				rnMAP.put("ruleservice", tbIotDevApiRuleParamDTO);
				
				return responseComUtil.setResponse200ok(rnMAP);
			} else if (iotMgntDTO.getType().equals("D")) {
				iotMgntSDevMDTO = iotMgntSVC.retriveDevListQuery(iotMgntDTO);
				return responseComUtil.setResponse200ok(iotMgntSDevMDTO);
			} else if (iotMgntDTO.getType().equals("DA")) {
				iotMgntSDevAtbDTO = iotMgntSVC.retriveDevAtbListQuery(iotMgntDTO);
				return responseComUtil.setResponse200ok(iotMgntSDevAtbDTO);
			} else if (iotMgntDTO.getType().equals("DAS")) {
				iotMgntSDevAtbSetDTO = iotMgntSVC.retriveDevAtbSetListQuery(iotMgntDTO);
				return responseComUtil.setResponse200ok(iotMgntSDevAtbSetDTO);
			} else if (iotMgntDTO.getType().equals("DD")) {
				iotMgntSDevDetSetDTO = iotMgntSVC.retriveDevDetSetListQuery(iotMgntDTO);
				return responseComUtil.setResponse200ok(iotMgntSDevDetSetDTO);
			} else if (iotMgntDTO.getType().equals("P")) {
				tbIotDevColRuleDTO = iotMgntSVC.retriveDevRuleParseListQuery(iotMgntDTO);
				return responseComUtil.setResponse200ok(tbIotDevColRuleDTO);
			} else if (iotMgntDTO.getType().equals("A")) {
				tbIotValRuleDTO = iotMgntSVC.retriveDevRuleValListQuery(iotMgntDTO);
				return responseComUtil.setResponse200ok(tbIotValRuleDTO);
			} else if (iotMgntDTO.getType().equals("M")) {
				tbIotDevColRuleDTO = iotMgntSVC.retriveDevRuleMapListQuery(iotMgntDTO);
				return responseComUtil.setResponse200ok(tbIotDevColRuleDTO);
			} else if (iotMgntDTO.getType().equals("S")) {
				tbIotDevApiRuleParamDTO = iotMgntSVC.retriveDevRuleServiceListQuery(iotMgntDTO);
				return responseComUtil.setResponse200ok(tbIotDevApiRuleParamDTO);
			}
		} else if(iotMgntDTO.getGubun().equals("AV")) {
			tbIotValRuleDTO = iotMgntSVC.retriveDevRuleApiListQuery(iotMgntDTO);
			return responseComUtil.setResponse200ok(tbIotValRuleDTO);
		}
		
		
		return responseComUtil.setResponse200ok();
	}
	
	@RequestMapping(value = "/retrieveIotMdlList", method = RequestMethod.POST)
	public ComResponseDto<?> retrieveIotMdlList(HttpServletRequest request, HttpServletResponse response,
			@RequestBody IotMgntDTO iotMgntDTO) throws BizException {
		LinkedHashMap<String, Object> rnMAP = new LinkedHashMap<String, Object>() ;
		List<IotMgntSDevMDTO> iotMgntSDevMDTO = null;
 
		iotMgntSDevMDTO = iotMgntSVC.retrieveIotMdlList(iotMgntDTO);
		return responseComUtil.setResponse200ok(iotMgntSDevMDTO);
	
	}
}
