package kr.co.abacus.common.message;

import kr.co.abacus.common.api.dao.MessageDAO;
import kr.co.abacus.common.api.dto.MessageDTO;
import kr.co.abacus.common.constant.ComConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * @FileName    : MessageConfig.java  
 * @Project     : hiwayGreen
 * @Date        : 2019. 2. 23.
 * @Author      : ASUS
 * @History     :
 *    날자            작성자             내용
 *  ---------- ----------- -------------------------------
 *  2012.02.04 애버커스           최초작성
 * @Description :  
 *  다국어 처리를 위한 메세지를 was가 구동 되는 시점에 Bean 생성 
 *  메세제 파일 위치 : /config/message/multiLang.yml 
 */
@Slf4j
@Configuration
public class MultiLangConfig {
	
	/**
	 * @Desctiption : application.yml에 선언 되어 있으며 메세지 정의 
	 *  위치 : /config/message/multiLang.yml
	 */
	@Autowired
	private Environment env;

	@Autowired
	private MessageDAO messageDAO;
	
	/**
	 * @Desctiption : 나라별 메세지를 담고 있는 변수
	 */
	private LinkedHashMap<String, Map<String, Object>> multiLangMap ;
	
	
	/**
	  * @Method Name : initMultiLanguageConfig
	  * @Date        : 2019. 2. 23.
	  * @Author      : ASUS
	  * @History     :  
	  *    날자            작성자             내용
	  *  ---------- ----------- -------------------------------
	  *  2012.02.04 애버커스           최초작성
	  * @Description : 메세지(yml)파일을 읽어서  
	  * @Input Data  :
	  * @Output Data :
	  */

	@PostConstruct
	@Bean
	public void initMultiLanguageConfig() throws IOException {
		log.debug("==== MultiLanguageConfig Config Starting .... ");
		multiLangMap = new LinkedHashMap<String, Map<String, Object>>();
		
		String basePathMapping = env.getProperty("myconfig.multiLang");
		
		try {
			Resource[] resources = null;
			resources = new PathMatchingResourcePatternResolver().getResources(basePathMapping + "/*");
			for (Resource yamlResource : resources) {
				YamlMapFactoryBean yaml = new YamlMapFactoryBean();
				yaml.setResources(yamlResource);
				yaml.setSingleton(true);
				String filekey = yamlResource.getFilename();
				if (filekey.contains(".yml")) {
					filekey = filekey.substring(0, filekey.lastIndexOf(".yml"));
				}
				multiLangMap.put(filekey, yaml.getObject());
			}
		}catch(Exception e) {
			log.debug(basePathMapping + " does not exist");
		}
	}
	
	public String getMessage( String key) {
		String langSet = getLangSet();
		
		log.debug("MultiLangConfig getMessage :: key :: {} ", key);
		
		String msg = "";
		if (multiLangMap == null) {
			setLanguageMap();
		}
			
		if (multiLangMap.get(langSet).containsKey(key)) {
			msg = (String) multiLangMap.get(langSet).get(key);	
		} 
		
		return msg;
	}
	
	public void setLanguageMap() {
		String langSet = getLangSet();
		
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setLang_set(langSet);
		messageDTO.setLang_type("MESSAGE");
		
		List<Map<String, Object>> messageMap = messageDAO.getMessageList(messageDTO);
		
		for(int i=0;i<messageMap.size();i++) {
			Map<String, Object> map = messageMap.get(i);
			setLanguageInfo((String)map.get("MSG_KEY"), (String)map.get("MSG_DESC"));
		}
	}
	
	private void setLanguageInfo(String key, String value) {
		String langSet = getLangSet();
		
		if (multiLangMap == null) {
			multiLangMap = new LinkedHashMap<String, Map<String, Object>>();
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put(key, value);
			multiLangMap.put(langSet, temp);
		} else {
			Map<String, Object> temp = (Map<String, Object>)multiLangMap.get(langSet);
			
			if (temp.containsKey(key)) {
				temp.replace(key, value);
			} else {
				temp.put(key, value);
			}
			
			if (multiLangMap.containsKey(langSet))
				multiLangMap.replace(langSet, temp);
			else
				multiLangMap.put(langSet, temp);
		}
	}
	
//	private void getMessageByDB(String key) {
//		String langSet = getLangSet();
//		
//		MessageDTO messageDTO = new MessageDTO();
//		messageDTO.setLang_set(langSet);
//		messageDTO.setLang_type("MESSAGE");
//		messageDTO.setMsg_key(key);
//		
//		List<Map<String, Object>> messageMap = messageDAO.getMessageList(messageDTO);
//		
//		Map<String, Object> msg = new HashMap<String, Object>();
//		for(int i=0;i<messageMap.size();i++) {
//			Map<String, Object> map = messageMap.get(i);
//			if (multiLangMap == null) {
//				msg.put((String)map.get("MSG_KEY"), map.get("MSG_DESC"));
//				multiLangMap = new LinkedHashMap<String, Map<String, Object>>();
//				multiLangMap.put(langSet, msg);
//			} else {
//				Map<String, Object> temp = multiLangMap.get(langSet);
//				temp.replace((String)map.get("MSG_KEY"), map.get("MSG_DESC"));
//				multiLangMap.replace(langSet, temp);
//			}
//		}
//	}
	
	private String getLangSet() {
		ServletRequestAttributes currentRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		String langSet = (String) currentRequestAttributes.getRequest().getSession().getAttribute(ComConstant.X_LANG_SET);
		
		log.debug("MultiLangConfig langSet :: " + langSet);
		
		if (langSet == null || langSet.isEmpty()) langSet = ComConstant.DEFAULT_CHAR_SET;

		return langSet;
	}
}
