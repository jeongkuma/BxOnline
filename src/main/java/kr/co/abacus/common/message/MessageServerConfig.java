package kr.co.abacus.common.message;

import kr.co.abacus.common.constant.ComConstant;
import kr.co.abacus.common.util.StringUtils;
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
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class MessageServerConfig {
	private LinkedHashMap<String, Map<String, Object>> serverMsgInfo;

	@Autowired
	private Environment env = null;

	
	@PostConstruct
	@Bean
	public void initMutilServerMsgConfig() throws IOException {
		log.debug("==== MessageServerConfig Config Start ");
		String basePathMapping = env.getProperty("myconfig.servermsg");
		serverMsgInfo = new LinkedHashMap<String, Map<String, Object>>();
		
		if (StringUtils.objectIfNullToEmpty(basePathMapping).length() < 1) {
			log.info("==== MessageServerConfig :: Error ==> Not initMutilServerMsgConfig");
		} else {
			try {
				Resource[] resources = null;
				resources = new PathMatchingResourcePatternResolver().getResources(basePathMapping + "/*");
				for (Resource yamlResource : resources) {
					YamlMapFactoryBean yaml = new YamlMapFactoryBean();
					yaml.setResources(yamlResource);
					yaml.setSingleton(true);
					log.debug("==== MessageServerConfig :: getFilename ::  " + yamlResource.getFilename());
					String filekey = yamlResource.getFilename();
					if (filekey.contains(".yml")) {
						filekey = filekey.substring(0, filekey.lastIndexOf(".yml"));
					}
					serverMsgInfo.put(filekey, yaml.getObject());
				}
			}catch(Exception e) {
				e.printStackTrace();
				log.debug(basePathMapping + " does not exist");
			}
		}
		
		log.debug("==== initMutilServerMsgConfig :: serverMsgInfo ::  " + serverMsgInfo.toString());
	}
	
	
	
	
	private String getLangSet() {
		ServletRequestAttributes currentRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		String langSet = (String) currentRequestAttributes.getRequest().getSession().getAttribute(ComConstant.X_LANG_SET);
		
		log.debug("MultiLangConfig langSet :: " + langSet);
		
		if (langSet == null) langSet = ComConstant.DEFAULT_CHAR_SET;

		return langSet;
	}

	
	public String getMessage(String key) {
		String langSet = getLangSet();
		
		log.debug("MultiLangConfig getMessage :: key :: {} ", key);
		
		String msg = "";
		if (serverMsgInfo != null && !serverMsgInfo.isEmpty()) {
			if (serverMsgInfo.get(langSet).containsKey(key)) {
				msg = (String) serverMsgInfo.get(langSet).get(key);	
			} 
		}
		return msg;
	}
}
