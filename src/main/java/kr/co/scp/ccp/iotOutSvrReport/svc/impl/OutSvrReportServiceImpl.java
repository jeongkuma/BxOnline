package kr.co.scp.ccp.iotOutSvrReport.svc.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import kong.unirest.UnirestException;
import kr.co.abacus.common.component.ReqContextComponent;
import kr.co.abacus.common.dto.common.ComInfoDto;
import kr.co.abacus.common.dto.req.ComRequestDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.abacus.common.exception.BizException;
import kr.co.abacus.common.logger.Log;
import kr.co.abacus.common.util.LinkedHashMap;
import kr.co.scp.ccp.iotOutSvrReport.dao.OutSvrReportDAO;
import kr.co.scp.ccp.iotOutSvrReport.dto.TbIotOutSvrReportDTO;
import kr.co.scp.ccp.iotOutSvrReport.svc.OutSvrReportService;
import kr.co.auiot.common.dto.common.PageDTO;
import kr.co.auiot.common.requestor.AgsRequestor;
import kr.co.auiot.common.util.AuthUtils;
import kr.co.auiot.common.util.ExcelUtils;
import kr.co.auiot.common.util.OmsUtils;

@Service
public class OutSvrReportServiceImpl implements OutSvrReportService {

    @Autowired
    private OutSvrReportDAO outSvrReportDAO;
    
    @Autowired
    AgsRequestor agsRequestor;

	@Value("${iot.syncIntrnSvr.url}")
	private String syncIntrnSvrUrl;
	
	@Value("${iot.reSend.url}")
	private String reSendUrl;
	
    /* 고객사리스트 */
    public HashMap<String, Object> custNmList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	List<TbIotOutSvrReportDTO> custNmList = new ArrayList<TbIotOutSvrReportDTO>();
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.custNmList");
    	try {
    		custNmList = outSvrReportDAO.custNmList(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("custNmList", custNmList);
    	
    	return resultMap;
    }
    
    /* 서비스명리스트 */
    public HashMap<String, Object> svcNmList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	List<TbIotOutSvrReportDTO> svcNmList = new ArrayList<TbIotOutSvrReportDTO>();
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.svcNmList");
    	try {
    		svcNmList = outSvrReportDAO.svcNmList(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("svcNmList", svcNmList);
    	
    	return resultMap;
    }
    
    /* 장비유형리스트 */
    public HashMap<String, Object> devClsList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	List<TbIotOutSvrReportDTO> devClsList = new ArrayList<TbIotOutSvrReportDTO>();
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.devClsList");
    	try {
    		devClsList = outSvrReportDAO.devClsList(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("devClsList", devClsList);
    	
    	return resultMap;
    }
    
    /* 장비모델리스트 */
    public HashMap<String, Object> devMdlList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	List<TbIotOutSvrReportDTO> devMdlList = new ArrayList<TbIotOutSvrReportDTO>();
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.devMdlList");
    	try {
    		devMdlList = outSvrReportDAO.devMdlList(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("devMdlList", devMdlList);
    	
    	return resultMap;
    }
    
    /* 가입장비리스트 */
    public HashMap<String, Object> entrDevList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	List<TbIotOutSvrReportDTO> entrDevList = new ArrayList<TbIotOutSvrReportDTO>();
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.entrDevList");
    	try {
    		entrDevList = outSvrReportDAO.entrDevList(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("entrDevList", entrDevList);
    	
    	return resultMap;
    }
    
    /* 외부서버리스트 */
    public HashMap<String, Object> outSvrList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	List<TbIotOutSvrReportDTO> outSvrList = new ArrayList<TbIotOutSvrReportDTO>();
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.outSvrList");
    	try {
    		outSvrList = outSvrReportDAO.outSvrList(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("outSvrList", outSvrList);
    	
    	return resultMap;
    }
    
    /* 외부서버송수신현황-재전송 */
    public String outSvrReSend(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	
    	ComResponseDto comResponseDto = new ComResponseDto<>();

    	HttpHeaders headers = new HttpHeaders();
    	ObjectMapper objectMapper = new ObjectMapper();
    	Map<String, Object> params = new HashMap<>();
    	
    	params.put("outMsgHistSeq", tbIotOutSvrReportDTO.getOutMsgHistSeq());
    	params.put("firstDttm", tbIotOutSvrReportDTO.getFirstDttm());
		
//    	comRequestDto.setReqParameter(params);
    	
//    	String[] reSendUrlArr = reSendUrl.split(",");
    	
    	try {
	    		HttpEntity<Object> entity = new HttpEntity<Object>(params, headers);
	    		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	    		
//	    		for(String tempUrl : reSendUrlArr) {
	    			comResponseDto = agsRequestor
	    					.agsApiCall(reSendUrl, HttpMethod.POST, entity, params.getClass());
//	    		}
    	
    	} catch (BizException e) {
    		return "N";
    	} finally {
//    		OmsUtils.endInnerOms(temp);
    	}
    	return "Y";
    	
    }
    
    /* 외부서버송수신이력조회 */
    public HashMap<String, Object> retrieveIotOutSvrRtReportList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        PageDTO pageDTO = new PageDTO();
        Integer count = 0;
        ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                "OutSvrReportDAO.retrieveIotOutSvrRtReportCount");
        try {
            count = outSvrReportDAO.retrieveIotOutSvrRtReportCount(tbIotOutSvrReportDTO);
        } catch (MyBatisSystemException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (BadSqlGrammarException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (DataIntegrityViolationException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (UncategorizedSQLException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } finally {
            OmsUtils.endInnerOms(temp);
        }

        pageDTO.pageCalculate(count, tbIotOutSvrReportDTO.getDisplayRowCount(), tbIotOutSvrReportDTO.getCurrentPage());
        tbIotOutSvrReportDTO.setStartPage(pageDTO.getRowStart());
        List<TbIotOutSvrReportDTO> retrieveIotOutSvrRtReportList = new ArrayList<TbIotOutSvrReportDTO>();
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
        		"OutSvrReportDAO.retrieveIotOutSvrRtReportList");
        try {
        	retrieveIotOutSvrRtReportList = outSvrReportDAO.retrieveIotOutSvrRtReportList(tbIotOutSvrReportDTO);
        } catch (MyBatisSystemException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (BadSqlGrammarException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (DataIntegrityViolationException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (UncategorizedSQLException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } finally {
            OmsUtils.endInnerOms(temp);
        }

        resultMap.put("page", pageDTO);
        resultMap.put("outSvrRtReportList", retrieveIotOutSvrRtReportList);
        
        return resultMap;
    }
    
    /* 엑셀 다운로드 */
	@Override
	public XSSFWorkbook excelDownload(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) throws Exception {
		ComInfoDto temp = null;
		Map<String, String> headerList = new LinkedHashMap<String, String>();
		List<TbIotOutSvrReportDTO> retrieveOutSvrList = new ArrayList<TbIotOutSvrReportDTO>();
		List<Map> list = new ArrayList<Map>();
		Map params = new HashMap();
		List<Map> custList = null;
		
		// excel
		XSSFWorkbook wb = new XSSFWorkbook();
		ObjectMapper mapper = new ObjectMapper();
		
		String excelFlag = tbIotOutSvrReportDTO.getExcelFlag();

		ComInfoDto c = ReqContextComponent.getComInfoDto();
		String newLang = c.getXlang();
		tbIotOutSvrReportDTO.setLangSet(newLang);
		tbIotOutSvrReportDTO.setCharSet(newLang);
		tbIotOutSvrReportDTO.setOrgNm(AuthUtils.getUser().getOrgNm());
		temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "outSvrReportDAO.retrieveTemplateList");

		try {
			/*
			 * if (AuthUtils.getUser() != null) {
			 * tbIotOutSvrReportDTO.setCustSeq(AuthUtils.getUser().getCustSeq()); }
			 */

			//외부서버 송수신 현황
			if(excelFlag.equals("outSvrRt")) {
				headerList = getOutSvrRtExcelDownHeaders();
			//외부서버 송수신 현황 상세내역
			} else if(excelFlag.equals("outSvrRtDtl")) {
				headerList = getOutSvrRtDtlExcelDownHeaders();
			//모델별 송수신 현황
			} else if(excelFlag.equals("outSvrRtMdl")) {
				headerList = getOutSvrRtMdlExcelDownHeaders();
			//가동률
			} else if(excelFlag.equals("outSvrRtOprtn")) {
				headerList = getOutSvrRtOprtnExcelDownHeaders(tbIotOutSvrReportDTO);
			//통신현황
			} else if(excelFlag.equals("cmnctStts")) {
				headerList = getCmnctSttsExcelDownHeaders();
			}
			
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}
		
		//외부서버 송수신 현황
		if(excelFlag.equals("outSvrRt")) {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "outSvrReportDAO.retrieveIotOutSvrRtReportList");
		//외부서버 송수신 현황 상세내역
		} else if(excelFlag.equals("outSvrRtDtl")) {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "outSvrReportDAO.retrieveIotOutSvrRtReportDtlList");
		//모델별 송수신 현황
		} else if(excelFlag.equals("outSvrRtMdl")) {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "outSvrReportDAO.retrieveIotOutSvrRtMdlReportList");
		//가동률
		} else if(excelFlag.equals("outSvrRtOprtn")) {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "outSvrReportDAO.retrieveIotOutSvrRtOprtnRate");
		//통신현황
		} else if(excelFlag.equals("cmnctStts")) {
			temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, "outSvrReportDAO.retrieveIotCmnctSttsList");
		}

		try {
			
			//외부서버 송수신 현황
			if(excelFlag.equals("outSvrRt")) {
				 
				retrieveOutSvrList = outSvrReportDAO.retrieveIotOutSvrRtReportList(tbIotOutSvrReportDTO);

				for(int i = 0; i < retrieveOutSvrList.size(); i++) {
					
					if(retrieveOutSvrList.get(i).getTranCd().equals("GN00000236")) {
						 tbIotOutSvrReportDTO.setOutSvrCd("GN00000242"); } 
					 else if(retrieveOutSvrList.get(i).getTranCd().equals("GN00000237")) {
						 tbIotOutSvrReportDTO.setOutSvrCd("GN00000244"); }
					
					tbIotOutSvrReportDTO.setOutMsgHistSeq(retrieveOutSvrList.get(i).getOutMsgHistSeq());
					tbIotOutSvrReportDTO.setFirstDttm(retrieveOutSvrList.get(i).getFirstDttm());
					
					tbIotOutSvrReportDTO = outSvrReportDAO.retrieveIotOutSvrRtReportDtlList(tbIotOutSvrReportDTO);
					
					if(tbIotOutSvrReportDTO != null) {
						retrieveOutSvrList.get(i).setReqMsg(tbIotOutSvrReportDTO.getReqMsg());
						retrieveOutSvrList.get(i).setReqDttm(tbIotOutSvrReportDTO.getReqDttm());
						retrieveOutSvrList.get(i).setStatus(tbIotOutSvrReportDTO.getStatus());
						retrieveOutSvrList.get(i).setResDttm(tbIotOutSvrReportDTO.getResDttm());
						retrieveOutSvrList.get(i).setResMsg(tbIotOutSvrReportDTO.getResMsg());
					} else {
						tbIotOutSvrReportDTO = new TbIotOutSvrReportDTO();
					}
				}
				
			//외부서버 송수신 현황 상세내역
			} else if(excelFlag.equals("outSvrRtDtl")) {
				retrieveOutSvrList = outSvrReportDAO.retrieveIotOutSvrRtReportListPop(tbIotOutSvrReportDTO);
				
				for(int i = 0; i < retrieveOutSvrList.size(); i++) {
					
					tbIotOutSvrReportDTO.setOutMsgHSubSeq(retrieveOutSvrList.get(i).getOutMsgHSubSeq());
					tbIotOutSvrReportDTO.setFirstDttm(retrieveOutSvrList.get(i).getFirstDttm());					
					
					tbIotOutSvrReportDTO = outSvrReportDAO.retrieveIotOutSvrRtReportListPopDtl(tbIotOutSvrReportDTO);
					
					if(tbIotOutSvrReportDTO != null) {
						retrieveOutSvrList.get(i).setReqMsg(tbIotOutSvrReportDTO.getReqMsg());
						retrieveOutSvrList.get(i).setReqDttm(tbIotOutSvrReportDTO.getReqDttm());
						retrieveOutSvrList.get(i).setStatus(tbIotOutSvrReportDTO.getStatus());
						retrieveOutSvrList.get(i).setResDttm(tbIotOutSvrReportDTO.getResDttm());
						retrieveOutSvrList.get(i).setResMsg(tbIotOutSvrReportDTO.getResMsg());
					} else {
						tbIotOutSvrReportDTO = new TbIotOutSvrReportDTO();
					}
				}
				
			//모델별 송수신 현황
			} else if(excelFlag.equals("outSvrRtMdl")) {
				retrieveOutSvrList = outSvrReportDAO.retrieveIotOutSvrRtMdlReportList(tbIotOutSvrReportDTO);
			//가동률
			} else if(excelFlag.equals("outSvrRtOprtn")) {
		 		custList = outSvrReportDAO.retrieveTbIotColSvr(tbIotOutSvrReportDTO);
		 		params.put("custList", custList);
		    	params.put("searchStartDttm", tbIotOutSvrReportDTO.getSearchStartDttm());
		    	params.put("searchEndDttm", tbIotOutSvrReportDTO.getSearchEndDttm());
		 		list = outSvrReportDAO.retrieveIotOutSvrRtOprtnRate(params);
			//통신현황
			} else if(excelFlag.equals("cmnctStts")) {
				retrieveOutSvrList = outSvrReportDAO.retrieveIotCmnctSttsList(tbIotOutSvrReportDTO);
			}
		} catch (MyBatisSystemException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (BadSqlGrammarException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (DataIntegrityViolationException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} catch (UncategorizedSQLException e) {
			OmsUtils.expInnerOms(temp, e);
			throw e;
		} finally {
			OmsUtils.endInnerOms(temp);
		}

//		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

		/*
		 * Map<String, String> title = new LinkedHashMap<String, String>(); String[]
		 * nameList = headerList.get(0).getColNameData().split(","); for (int i = 0; i <
		 * headerList.size(); i++) { TbIotOutSvrReportDTO header = headerList.get(i);
		 * String name = nameList[i]; Map<String, String> map =
		 * mapper.readValue(header.getColModelData(), new TypeReference<Map<String,
		 * String>>() {}); title.put(name, map.get("name")); }
		 */
		
		if(excelFlag.equals("outSvrRtOprtn")) {
			ExcelUtils.createExcelFile(wb, list, headerList);
		}else {
			ExcelUtils.createExcelFile(wb, retrieveOutSvrList, headerList);
		}
			
		return wb;
	}
	
	public Map<String, String> getOutSvrRtExcelDownHeaders() {
		Map<String, String> headerList = new LinkedHashMap<String, String>();
		
		headerList.put("외부서버명", "serverNm");
		headerList.put("Entity ID", "devUuid");
		headerList.put("송수신구분", "tranNm");
		headerList.put("송신/수신시간", "regDttm");
		headerList.put("상태", "successYn");
		headerList.put("요청시간", "reqDttm");
		headerList.put("요청전문", "reqMsg");
		headerList.put("응답시간", "resDttm");
		headerList.put("응답상태", "status");
		headerList.put("응답전문", "resMsg");

		return headerList;
	}
	
	public Map<String, String> getOutSvrRtDtlExcelDownHeaders() {
		Map<String, String> headerList = new LinkedHashMap<String, String>();
		
		headerList.put("서버명", "serverNm");
		headerList.put("시간", "reqDttm");
		headerList.put("상태", "status");
		headerList.put("요청시간", "reqDttm");
		headerList.put("요청전문", "reqMsg");
		headerList.put("응답시간", "resDttm");
		headerList.put("응답상태", "status");
		headerList.put("응답전문", "resMsg");
		
		return headerList;
	}
	
	public Map<String, String> getOutSvrRtMdlExcelDownHeaders() {
		Map<String, String> headerList = new LinkedHashMap<String, String>();
		
		headerList.put("고객사명", "custNm");
		headerList.put("서비스명", "svcNm");
		headerList.put("장비유형", "devClsNm");
		headerList.put("장비모델", "devMdlCd");
		headerList.put("외부서버명", "serverNm");
		headerList.put("일자", "outStatDay");
		headerList.put("송신(전체/성공/실패)", "sendRst");
		headerList.put("수신(전체/성공/실패)", "receiveRst");
		
		return headerList;
	}
	
	public Map<String, String> getOutSvrRtOprtnExcelDownHeaders(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
		Map<String, String> headerList2 = new LinkedHashMap<String, String>();
		List<Map> colList = outSvrReportDAO.retrieveTbIotColSvr(tbIotOutSvrReportDTO);
		
		headerList2.put("일자", "OR_DAY");
		headerList2.put("종합 가동률/수량", "TOT");
		
		String custNm = "";
		String devClsCd = "";
		String devClsNm = "";
		String devMdlNm = "";
		
		for(Map<String, String> idx : colList) {
			custNm = idx.get("CUST_NM");
			devClsCd = idx.get("DEV_CLS_CD");
			devClsNm = StringUtils.defaultString(idx.get("DEV_CLS_NM"));
			devMdlNm = StringUtils.defaultString(idx.get("DEV_MDL_NM"));
			
			if(" ".equals(devClsCd)) {
				headerList2.put(custNm, custNm);
			} else {
				Log.info(devClsNm + ":" + devMdlNm);
				headerList2.put(custNm + "_" + devClsNm + "_" + devMdlNm, custNm + "_" + devClsNm + "_" + devMdlNm);
			}
		}
		return headerList2;
	}
	
	public static LinkedHashMap<String, String> sortMapByValue(Map<String, String> map) {
	    List<Map.Entry<String, String>> entries = new LinkedList<>(map.entrySet());
	    Collections.sort(entries, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));

	    LinkedHashMap<String, String> result = new LinkedHashMap<>();
	    for (Map.Entry<String, String> entry : entries) {
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	}
	
	public Map<String, String> getCmnctSttsExcelDownHeaders() {
		Map<String, String> headerList = new LinkedHashMap<String, String>();
		
		headerList.put("일자", "outStatDay");
		headerList.put("고객사", "custNm");
		headerList.put("장비유형", "devClsNm");
		headerList.put("장비모델", "devMdlNm");
		headerList.put("식별번호(MAC ADDR)", "ctn");
		headerList.put("Entity ID", "devUuid");
		headerList.put("송신(전체/성공/실패)", "sendRst");
		headerList.put("수신(전체/성공/실패)", "receiveRst");
		headerList.put("송신+수신(전체/성공/실패)", "sendReceiveRst");

		return headerList;
	}
	
	
    /* 외부서버송수신현황-전문상세내역 */
    public TbIotOutSvrReportDTO retrieveIotOutSvrRtReportDtlList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	TbIotOutSvrReportDTO result = new TbIotOutSvrReportDTO();
    	
    	//송신일 때는 'GN00000242'(외부연동), 수신일 때는 'GN00000244'(제휴)
		 if(tbIotOutSvrReportDTO.getTranCd().equals("GN00000236")) {
			 tbIotOutSvrReportDTO.setOutSvrCd("GN00000242"); } 
		 else if(tbIotOutSvrReportDTO.getTranCd().equals("GN00000237")) {
			 tbIotOutSvrReportDTO.setOutSvrCd("GN00000244"); }
		 
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.retrieveIotOutSvrRtReportDtlList");
    	try {
    		
    		result = outSvrReportDAO.retrieveIotOutSvrRtReportDtlList(tbIotOutSvrReportDTO);
    		
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	
    	return result;
    }
    
    
    /* 외부서버송수신이력조회 - 팝업*/
    public HashMap<String, Object> retrieveIotOutSvrRtReportListPop(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	PageDTO pageDTO = new PageDTO();
    	Integer count = 0;
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
    			"OutSvrReportDAO.retrieveIotOutSvrRtReportPopCount");
    	try {
    		count = outSvrReportDAO.retrieveIotOutSvrRtReportPopCount(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	pageDTO.pageCalculate(count, tbIotOutSvrReportDTO.getDisplayRowCount(), tbIotOutSvrReportDTO.getCurrentPage());
    	tbIotOutSvrReportDTO.setStartPage(pageDTO.getRowStart());
    	List<TbIotOutSvrReportDTO> retrieveIotOutSvrRtReportListPop = new ArrayList<TbIotOutSvrReportDTO>();
    	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.retrieveIotOutSvrRtReportListPop");
    	try {
    		retrieveIotOutSvrRtReportListPop = outSvrReportDAO.retrieveIotOutSvrRtReportListPop(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("page", pageDTO);
    	resultMap.put("outSvrRtReportListPop", retrieveIotOutSvrRtReportListPop);
    	
    	return resultMap;
    }
    
    
    /* 외부서버송수신이력조회 - 팝업 상세*/
    public TbIotOutSvrReportDTO retrieveIotOutSvrRtReportListPopDtl(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	TbIotOutSvrReportDTO result = new TbIotOutSvrReportDTO();
    	
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.retrieveIotOutSvrRtReportListPopDtl");
    	try {
    		result = outSvrReportDAO.retrieveIotOutSvrRtReportListPopDtl(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	return result;
    }

    /* 모델별송수신현황 */
    public HashMap<String, Object> retrieveIotOutSvrRtMdlReportList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        PageDTO pageDTO = new PageDTO();
        Integer count = 0;
        ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                "OutSvrReportDAO.retrieveIotOutSvrRtMdlReportCount");
        try {
            count = outSvrReportDAO.retrieveIotOutSvrRtMdlReportCount(tbIotOutSvrReportDTO);
        } catch (MyBatisSystemException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (BadSqlGrammarException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (DataIntegrityViolationException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (UncategorizedSQLException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } finally {
            OmsUtils.endInnerOms(temp);
        }

        pageDTO.pageCalculate(count, tbIotOutSvrReportDTO.getDisplayRowCount(), tbIotOutSvrReportDTO.getCurrentPage());
        tbIotOutSvrReportDTO.setStartPage(pageDTO.getRowStart());
        List<TbIotOutSvrReportDTO> retrieveIotOutSvrRtMdlReportList = new ArrayList<TbIotOutSvrReportDTO>();
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
        		"OutSvrReportDAO.retrieveIotOutSvrRtMdlReportList");
        try {
        	retrieveIotOutSvrRtMdlReportList = outSvrReportDAO.retrieveIotOutSvrRtMdlReportList(tbIotOutSvrReportDTO);
        } catch (MyBatisSystemException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (BadSqlGrammarException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (DataIntegrityViolationException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (UncategorizedSQLException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } finally {
            OmsUtils.endInnerOms(temp);
        }

        resultMap.put("page", pageDTO);
        resultMap.put("outSvrRtMdlReportList", retrieveIotOutSvrRtMdlReportList);
        
        return resultMap;
    }
    
    /* 가동률 */
    public HashMap<String, Object> retrieveIotOutSvrRtOprtnRate(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	PageDTO pageDTO = new PageDTO();
    	Integer count = 0;
		
    	//고객사목록
		List<Map> custList = null;
		Map params = new HashMap();
		
		ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.retrieveTbIotColSvr");
		
    	try {
    		custList = outSvrReportDAO.retrieveTbIotColSvr(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	params.put("custList", custList);
    	params.put("custSeq", tbIotOutSvrReportDTO.getCustSeq());
    	params.put("searchStartDttm", tbIotOutSvrReportDTO.getSearchStartDttm());
    	params.put("searchEndDttm", tbIotOutSvrReportDTO.getSearchEndDttm());
    	
    	 temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
    			"OutSvrReportDAO.retrieveIotOutSvrRtOprtnRateCount");
    	try {
    		count = outSvrReportDAO.retrieveIotOutSvrRtOprtnRateCount(params);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}

    	List<Map> list = new ArrayList<Map>();
    	
    	pageDTO.pageCalculate(count, tbIotOutSvrReportDTO.getDisplayRowCount(), tbIotOutSvrReportDTO.getCurrentPage());
    	tbIotOutSvrReportDTO.setStartPage(pageDTO.getRowStart());
    	
    	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.retrieveIotOutSvrRtOprtnRate");
    	try {
    		list = outSvrReportDAO.retrieveIotOutSvrRtOprtnRate(params);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("page", pageDTO);
    	resultMap.put("outSvrRtOprtnRate", list);

    	return resultMap;
    }
    
    /* 통신현황 - 고객사리스트 */
    public HashMap<String, Object> cmnctSttsCustNmList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	List<TbIotOutSvrReportDTO> cmnctSttsCustNmList = new ArrayList<TbIotOutSvrReportDTO>();
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.cmnctSttsCustNmList");
    	try {
    		cmnctSttsCustNmList = outSvrReportDAO.cmnctSttsCustNmList(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("cmnctSttsCustNmList", cmnctSttsCustNmList);
    	
    	return resultMap;
    }
    
    /* 통신현황 - 서비스명리스트 */
    public HashMap<String, Object> cmnctSttsSvcNmList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	List<TbIotOutSvrReportDTO> cmnctSttsSvcNmList = new ArrayList<TbIotOutSvrReportDTO>();
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.cmnctSttsSvcNmList");
    	try {
    		cmnctSttsSvcNmList = outSvrReportDAO.cmnctSttsSvcNmList(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("cmnctSttsSvcNmList", cmnctSttsSvcNmList);
    	
    	return resultMap;
    }
    
    /* 통신현황 - 외부서버명리스트 */
    public HashMap<String, Object> cmnctSttsOutSvrList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	List<TbIotOutSvrReportDTO> cmnctSttsOutSvrList = new ArrayList<TbIotOutSvrReportDTO>();
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.cmnctSttsOutSvrList");
    	try {
    		cmnctSttsOutSvrList = outSvrReportDAO.cmnctSttsOutSvrList(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("cmnctSttsOutSvrList", cmnctSttsOutSvrList);
    	
    	return resultMap;
    }
    
    /* 통신현황 - 장비유형리스트 */
    public HashMap<String, Object> cmnctSttsDevClsList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	List<TbIotOutSvrReportDTO> cmnctSttsDevClsList = new ArrayList<TbIotOutSvrReportDTO>();
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.cmnctSttsDevClsList");
    	try {
    		cmnctSttsDevClsList = outSvrReportDAO.cmnctSttsDevClsList(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("cmnctSttsDevClsList", cmnctSttsDevClsList);
    	
    	return resultMap;
    }
    
    /* 통신현황 - 장비모델리스트 */
    public HashMap<String, Object> cmnctSttsDevMdlList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	List<TbIotOutSvrReportDTO> cmnctSttsDevMdlList = new ArrayList<TbIotOutSvrReportDTO>();
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.cmnctSttsDevMdlList");
    	try {
    		cmnctSttsDevMdlList = outSvrReportDAO.cmnctSttsDevMdlList(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("cmnctSttsDevMdlList", cmnctSttsDevMdlList);
    	
    	return resultMap;
    }
    
    
    /* 통신현황조회 */
    public HashMap<String, Object> retrieveIotCmnctSttsList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        PageDTO pageDTO = new PageDTO();
        Integer count = 0;
        ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                "OutSvrReportDAO.retrieveIotCmnctSttsListCount");
        try {
            count = outSvrReportDAO.retrieveIotCmnctSttsListCount(tbIotOutSvrReportDTO);
        } catch (MyBatisSystemException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (BadSqlGrammarException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (DataIntegrityViolationException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (UncategorizedSQLException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } finally {
            OmsUtils.endInnerOms(temp);
        }

        pageDTO.pageCalculate(count, tbIotOutSvrReportDTO.getDisplayRowCount(), tbIotOutSvrReportDTO.getCurrentPage());
        tbIotOutSvrReportDTO.setStartPage(pageDTO.getRowStart());
        List<TbIotOutSvrReportDTO> retrieveIotCmnctSttsList = new ArrayList<TbIotOutSvrReportDTO>();
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
        		"OutSvrReportDAO.retrieveIotCmnctSttsList");
        try {
        	retrieveIotCmnctSttsList = outSvrReportDAO.retrieveIotCmnctSttsList(tbIotOutSvrReportDTO);
        } catch (MyBatisSystemException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (BadSqlGrammarException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (DataIntegrityViolationException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (UncategorizedSQLException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } finally {
            OmsUtils.endInnerOms(temp);
        }

        resultMap.put("page", pageDTO);
        resultMap.put("cmnctSttsList", retrieveIotCmnctSttsList);
        
        return resultMap;
    }
    
    
    /* 통신현황상세조회 */
    public HashMap<String, Object> retrieveIotCmnctSttsPopList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	PageDTO pageDTO = new PageDTO();
    	Integer count = 0;
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
    			"outSvrReportDAO.retrieveIotCmnctSttsPopListCount");
    	try {
    		count = outSvrReportDAO.retrieveIotCmnctSttsPopListCount(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	pageDTO.pageCalculate(count, tbIotOutSvrReportDTO.getDisplayRowCount(), tbIotOutSvrReportDTO.getCurrentPage());
    	tbIotOutSvrReportDTO.setStartPage(pageDTO.getRowStart());
    	List<TbIotOutSvrReportDTO> retrieveIotCmnctSttsPopList = new ArrayList<TbIotOutSvrReportDTO>();
    	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.retrieveIotCmnctSttsPopList");
    	try {
    		retrieveIotCmnctSttsPopList = outSvrReportDAO.retrieveIotCmnctSttsPopList(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("page", pageDTO);
    	resultMap.put("cmnctSttsPopList", retrieveIotCmnctSttsPopList);
    	
    	return resultMap;
    }

    /* 내부서버연동조회 */
    public HashMap<String, Object> retrieveIotIntrnSvrCnctnList(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        PageDTO pageDTO = new PageDTO();
        Integer count = 0;
        ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT,
                "OutSvrReportDAO.retrieveIotIntrnSvrCnctnListCount");
        try {
            count = outSvrReportDAO.retrieveIotIntrnSvrCnctnListCount(tbIotOutSvrReportDTO);
        } catch (MyBatisSystemException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (BadSqlGrammarException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (DataIntegrityViolationException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (UncategorizedSQLException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } finally {
            OmsUtils.endInnerOms(temp);
        }

        pageDTO.pageCalculate(count, tbIotOutSvrReportDTO.getDisplayRowCount(), tbIotOutSvrReportDTO.getCurrentPage());
        tbIotOutSvrReportDTO.setStartPage(pageDTO.getRowStart());
        List<TbIotOutSvrReportDTO> retrieveIotIntrnSvrCnctnList = new ArrayList<TbIotOutSvrReportDTO>();
        temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
        		"OutSvrReportDAO.retrieveIotIntrnSvrCnctnList");
        try {
        	retrieveIotIntrnSvrCnctnList = outSvrReportDAO.retrieveIotIntrnSvrCnctnList(tbIotOutSvrReportDTO);
        } catch (MyBatisSystemException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (BadSqlGrammarException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (DataIntegrityViolationException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } catch (UncategorizedSQLException e) {
            OmsUtils.expInnerOms(temp, e);
            throw e;
        } finally {
            OmsUtils.endInnerOms(temp);
        }

        resultMap.put("page", pageDTO);
        resultMap.put("intrnSvrCnctnList", retrieveIotIntrnSvrCnctnList);
        
        return resultMap;
    }
    
    /* 내부서버연동등록 */
    public HashMap<String, Object> insertIotIntrnSvrCnctn(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	tbIotOutSvrReportDTO.setRegUserId(AuthUtils.getUser().getUserId());  
    	Integer count = 0;
    	
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.insertIotIntrnSvrCnctn");
    	
    	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.duplicationChkIntrnSvrCnctn");
    	
    	try {
    		
    		count = outSvrReportDAO.duplicationChkIntrnSvrCnctn(tbIotOutSvrReportDTO);

    		if(count == 0) {
    			outSvrReportDAO.insertIotIntrnSvrCnctn(tbIotOutSvrReportDTO);
    			resultMap.put("result", "success");
    		}else {
    			resultMap.put("result", "fail");
    		}
    		
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	return resultMap;
    }
    
    /* 내부서버연동수정-조회 */
    public HashMap<String, Object> selectOneIotIntrnSvrCnctn(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	List<TbIotOutSvrReportDTO> selectOneList = new ArrayList<TbIotOutSvrReportDTO>();
    	
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.selectOneIotIntrnSvrCnctn");
    	
    	try {
    		selectOneList = outSvrReportDAO.selectOneIotIntrnSvrCnctn(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	resultMap.put("selectOneList", selectOneList);
    	
    	return resultMap;
    }

    /* 내부서버연동수정-수정 */
    public HashMap<String, Object> updateIotIntrnSvrCnctn(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	
    	HashMap<String, Object> resultMap = new HashMap<String, Object>();
    	tbIotOutSvrReportDTO.setModUserId(AuthUtils.getUser().getUserId());
    	Integer count = 0;
    	
    	String selectOneCustSeq = tbIotOutSvrReportDTO.getSelectOneCustSeq();
    	String selectOneSvcCd = tbIotOutSvrReportDTO.getSelectOneSvcCd();
    	String selectOneDevClsCd = tbIotOutSvrReportDTO.getSelectOneDevClsCd();
    	String selectOneDevMdlCd = tbIotOutSvrReportDTO.getSelectOneDevMdlCd();
    			
    	String custSeq = tbIotOutSvrReportDTO.getCustSeq();
    	String svcCd = tbIotOutSvrReportDTO.getSvcCd();
    	String devClsCd = tbIotOutSvrReportDTO.getDevClsCd();
    	String devMdlCd = tbIotOutSvrReportDTO.getDevMdlCd();
    	
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.updateIotIntrnSvrCnctn");

    	temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.duplicationChkIntrnSvrCnctn");
    	
    	try {
    		
    		if(selectOneCustSeq.equals(custSeq) && selectOneSvcCd.equals(svcCd)
    				&& selectOneDevClsCd.equals(devClsCd) && selectOneDevMdlCd.equals(devMdlCd)) {
    			
    			outSvrReportDAO.updateIotIntrnSvrCnctn(tbIotOutSvrReportDTO);
    			resultMap.put("result", "success");
    			
    		} else {
    			
    			count = outSvrReportDAO.duplicationChkIntrnSvrCnctn(tbIotOutSvrReportDTO);

    			if(count == 0) {
    				outSvrReportDAO.updateIotIntrnSvrCnctn(tbIotOutSvrReportDTO);
    				resultMap.put("result", "success");
    			}else {
    				resultMap.put("result", "fail");
    			}
    		}
    		

    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    	
    	return resultMap;
    }
    
    /* 내부서버연동삭제 */
    public void deleteIotIntrnSvrCnctn(TbIotOutSvrReportDTO tbIotOutSvrReportDTO) {
    	
    	ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, 
    			"OutSvrReportDAO.deleteIotIntrnSvrCnctn");
    	try {
    		outSvrReportDAO.deleteIotIntrnSvrCnctn(tbIotOutSvrReportDTO);
    	} catch (MyBatisSystemException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (BadSqlGrammarException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (DataIntegrityViolationException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} catch (UncategorizedSQLException e) {
    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		OmsUtils.endInnerOms(temp);
    	}
    }
    

    /* 내부서버연동-동기화 */
    public void syncIntrnSvrCnctn(ComRequestDto comRequestDto) {
    	
    	//ComInfoDto temp = OmsUtils.addInnerOms(OmsUtils.CHANNEL_DB, OmsUtils.CHANNEL_TYPE_OUT, syncIntrnSvrUri);
    	ComResponseDto comResponseDto = new ComResponseDto<>();
    	Map<String, Object> params = new HashMap<>();
    	HttpHeaders headers = new HttpHeaders();
    	
    	try {
	    		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	    		HttpEntity<Object> entity = new HttpEntity<Object>(comRequestDto, headers);
	    		comResponseDto = agsRequestor
	    						.agsApiCall(syncIntrnSvrUrl, HttpMethod.POST, entity, params.getClass());
	    		
    	} catch (UnirestException e) {
    		//OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
    		//OmsUtils.endInnerOms(temp);
    	}
    	
    }
    
}
