package kr.co.scp.ccp.initMemory.svc.impl;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;


import kong.unirest.UnirestException;
import kr.co.abacus.common.dto.req.ComRequestDto;
import kr.co.abacus.common.dto.res.ComResponseDto;
import kr.co.scp.ccp.initMemory.svc.InitMemorySVC;
import kr.co.auiot.common.requestor.AgsRequestor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class InitMemorySVCImpl implements InitMemorySVC {
	
	  
	@Autowired
	AgsRequestor agsRequestor;

	@Value("${init.initMemoryC.url}")
	private String initMemoryCUrl;
	
	@Value("${init.initMemoryA.url}")
	private String initMemoryAUrl;
	
	@Value("${init.initOuifSync.url}")
	private String initOuifSyncUrl;

	
	public void initMemoryC(ComRequestDto comRequestDto) {
	    
		ComResponseDto comResponseDto = new ComResponseDto<>();
    	
    	HttpHeaders headers = new HttpHeaders();
    	
    	String[] initMemoryCUrlArr = initMemoryCUrl.split(",");
    	
    	try {
	    		HttpEntity<Object> entity = new HttpEntity<Object>(comRequestDto, headers);
	    		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	    		
	    		for(String tempUrl : initMemoryCUrlArr) {
	    			comResponseDto = agsRequestor
	    					.agsApiCall(tempUrl, HttpMethod.POST, entity, ComRequestDto.class);
	    		}
    	
    	} catch (UnirestException e) {
//    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
//    		OmsUtils.endInnerOms(temp);
    	}
    	
    
	}

	public void initMemoryA(ComRequestDto comRequestDto) {
		ComResponseDto comResponseDto = new ComResponseDto<>();
    	
         HttpHeaders headers = new HttpHeaders();
    	
    	String[] initMemoryAUrlArr = initMemoryAUrl.split(",");
    	
    	try {
	    		HttpEntity<Object> entity = new HttpEntity<Object>(comRequestDto, headers);
	    		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	    		
	    		for(String tempUrl : initMemoryAUrlArr) {
	    			comResponseDto = agsRequestor
	    					.agsApiCall(tempUrl, HttpMethod.POST, entity, ComRequestDto.class);
	    		}
    	
    	} catch (UnirestException e) {
//    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
//    		OmsUtils.endInnerOms(temp);
    	}
    	
    
	}
	public void initOuifSync(ComRequestDto comRequestDto) {
		ComResponseDto comResponseDto = new ComResponseDto<>();
    	
         HttpHeaders headers = new HttpHeaders();
    	
    	String[] initOuifSyncUrlArr = initOuifSyncUrl.split(",");
    	
    	try {
	    		HttpEntity<Object> entity = new HttpEntity<Object>(comRequestDto, headers);
	    		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	    		
	    		for(String tempUrl : initOuifSyncUrlArr) {
	    			comResponseDto = agsRequestor
	    					.agsApiCall(tempUrl, HttpMethod.POST, entity, ComRequestDto.class);
	    		}
    	
    	} catch (UnirestException e) {
//    		OmsUtils.expInnerOms(temp, e);
    		throw e;
    	} finally {
//    		OmsUtils.endInnerOms(temp);
    	}
    	
    
	}
}