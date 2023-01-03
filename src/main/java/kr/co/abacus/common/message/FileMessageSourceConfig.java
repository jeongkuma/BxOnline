package kr.co.abacus.common.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class FileMessageSourceConfig {
	

	@Autowired
	private MessageProperty messageProperty;
	
	private Map<String, Object> targetMapperInfo = new HashMap<String, Object>();
	
	@PostConstruct
	private void fileMessageSourceConfig() throws IOException {
		List<?> message  = messageProperty.getMessageList();
		for(Object object : message) {
		    String ymlfile = (String) object;
		    log.debug("=== :: initFileMessageSourceConfig :: element :: {}", ymlfile);
		    
		    YamlMapFactoryBean yaml = new YamlMapFactoryBean();
			yaml.setResources(new ClassPathResource(ymlfile));
			yaml.setSingleton(true);
			int findPosition =   ymlfile.lastIndexOf('/');
			String name = ymlfile.substring(findPosition+1);
			int findPosition1 =   name.indexOf('.');
			name = name.substring(0, findPosition1);
			Map<String, Object> ymlObj = yaml.getObject();
			
			targetMapperInfo.put(name, ymlObj);
			
		}


	}
	
	
	@SuppressWarnings("unchecked")
	public String getMessage(String msgName, String msgKey) {
		Map<String, Object>  targetObject = (Map<String, Object>) targetMapperInfo.get(msgName);
		return (String) targetObject.get(msgKey);
	}
	

}
