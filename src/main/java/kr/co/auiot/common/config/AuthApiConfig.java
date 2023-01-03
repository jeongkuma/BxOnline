package kr.co.auiot.common.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import kr.co.abacus.common.util.LinkedHashMap;
import kr.co.auiot.common.util.AuthUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthApiConfig {
	private Map<String, Object> autoApiConfig = new HashMap<String, Object>();

	@Autowired
	private Environment env;
	
	
	
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	private void initautoApiConfigConfig() throws IOException {
		String baseApiConfig = env.getProperty("myconfig.authApiConfig");
		
		if (baseApiConfig == null) {
			log.debug(baseApiConfig + " null ");
		} else {
			try {
				Resource[] resources = null;
				resources = new PathMatchingResourcePatternResolver().getResources(baseApiConfig + "/*");
				for (Resource yamlResource : resources) {
					YamlMapFactoryBean yaml = new YamlMapFactoryBean();
					yaml.setResources(yamlResource);
					yaml.setSingleton(true);
					log.debug("==== MultiLabalConfig :: getFilename ::  " + yamlResource.getFilename());
					String filekey = yamlResource.getFilename();
					if (filekey.contains(".yml")) {
						filekey = filekey.substring(0, filekey.lastIndexOf(".yml"));
					}
					autoApiConfig.put(filekey, yaml.getObject());
				}
				log.debug("=== autoApiConfig :: " +  autoApiConfig.toString());
			}catch(Exception e) {
				log.debug(baseApiConfig + " does not exist");
			}
		}
		
		
	}

	@SuppressWarnings("unchecked")
	public boolean getautoApiConfig(String reqUri) {
		
		boolean rbn = false;
		String contextPath = env.getProperty("server.servlet.context-path");

		
		
		String curRoleCdId = AuthUtils.getUser().getRoleCdId();
		
		if (curRoleCdId == null) {
			rbn = true;
		} else {
			if (contextPath.length() > 0) {
				if (reqUri.startsWith(contextPath)) {
					reqUri = reqUri.substring(contextPath.length() + 1);
				}
				if (reqUri.startsWith("/")) {
					reqUri = reqUri.substring(1);
				}
			}
			
			if (reqUri.indexOf(".") > -1) {
				reqUri = reqUri.substring(0, reqUri.lastIndexOf("."));
			}
			
			reqUri = reqUri.replaceAll("/", ".");
			
			if (autoApiConfig.containsKey(reqUri)) {
				Map<String, Object> ruleMap = (Map<String, Object>) autoApiConfig.get(reqUri);
				List<String> roleCdIdList = null;
				
				if (ruleMap.containsKey("ROLE_CD_ID")) {
					roleCdIdList = (List<String>) ruleMap.get("ROLE_CD_ID");
					if (roleCdIdList == null || roleCdIdList.size() == 0) {
						return true; // 선언이 되어 있어 있지 않으면 통과 
					} else {
						// 선언이 되어 있으므로 등록된 권한 이외 체크를 한다.
						// 하나라도 존재 하면 진행 
						for(String roleCdId: roleCdIdList ) {
							if ( roleCdId.equals(curRoleCdId)) {
								rbn = true;
							} 
						}
					}
				}
			} else { // 등록되어 있지 않는 API
				rbn = true;
			}
		}
		
		return rbn;
	}

	public void setautoApiConfig(Map<String, Object> autoApiConfig) {
		this.autoApiConfig = autoApiConfig;
	}



}
