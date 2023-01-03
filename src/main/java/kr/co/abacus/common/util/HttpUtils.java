package kr.co.abacus.common.util;

import kr.co.abacus.common.constant.ComConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpUtils {
	
	public static String urlConnectionPost(String urlString, String parameters) {
		
		BufferedReader in = null;
		String result = "Connection Fail";
		
		try {
			
			URL url = new URL(urlString);
			
			byte[] postDataBytes = parameters.getBytes("UTF-8");
			
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			//conn.setRequestProperty("Accept", "application/json");
			//conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			conn.setDoOutput(true);
			//conn.setDoInput(true);
			conn.getOutputStream().write(postDataBytes); // POST 호출
			
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			
			/*String inputLine;
			while((inputLine = in.readLine()) != null) { // response 출력
				log.info(inputLine);
			}*/
			
			if(in != null)
				result = "SUCCESS";
			
		} catch (Exception e) {
			log.debug(e.getMessage());
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				log.debug(e.getMessage());
			}
		}
		
		return result;
	}
	
	public static String urlConnectionJSON(String urlString, String jsonParameters) {
		
		InputStream is = null;
		String result = "Connection Fail";
		
		try {
			
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/json");
			conn.setDoOutput(true);		// OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
			conn.setDoInput(true);		// InputStream으로 서버로 부터 응답을 받겠다는 옵션.
			
			OutputStream os = conn.getOutputStream();
			os.write(jsonParameters.getBytes("UTF-8"));
			os.flush();
			
			try {
				
				is = conn.getInputStream();
				
				if(is != null)
					result = "SUCCESS";
			}
			catch (IOException e) {
				log.debug(e.getMessage());
			}
			finally {
				conn.disconnect();
			}
		}
		catch (IOException e) {
			log.debug(e.getMessage());
		}
		catch (Exception e) {
			log.debug(e.getMessage());
		}
		
		return result;
	}
	
	public static String setParameter(Map<String,Object> params) throws Exception {
		
		StringBuilder postData = new StringBuilder();
		for(Map.Entry<String,Object> param : params.entrySet()) {
			
			if(postData.length() != 0)
				postData.append('&');
			
			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}
		
		return postData.toString();
	}
	
	public static Map<String, String> getQueryString(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();

		if (parameterMap == null) {
			return null;
		}

		Map<String, String> rtnMap = new HashMap<String, String>();

		for (String key : parameterMap.keySet()) {
			rtnMap.put(key, parameterMap.get(key)[0]);
		}

		return rtnMap;
	}
	
	
	public static String getLangSet() {
		ServletRequestAttributes currentRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		String langSet = (String) currentRequestAttributes.getRequest().getSession().getAttribute(ComConstant.X_LANG_SET);
		
		if (langSet == null) langSet = ComConstant.DEFAULT_CHAR_SET;
		
		return langSet;	
		
	}
}
