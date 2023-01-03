package kr.co.abacus.common.validation.config;

import kr.co.abacus.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ValidationInfoConfig {
	private Map<String, Object> validationInfo = new HashMap<String, Object>();

	@Autowired
	private Environment env = null;

	@PostConstruct
	private void initValidationInfoConfig() throws IOException {

		String basePathMapping = env.getProperty("myconfig.validation");
		String resourceUri = "";

		if (StringUtils.objectIfNullToEmpty(basePathMapping).length() < 1) {
			log.info("==== ValidationInfoConfig :: Error ==> Not ValidationInfoConfig");
		} else {

			Resource[] resources = null;
			resources = new PathMatchingResourcePatternResolver().getResources(basePathMapping + "/*/*");
			for (Resource yamlResource : resources) {
				try {
					resourceUri = StringUtils.getRuleFileKeyName(yamlResource.getURI());
					if (resourceUri.contains(".yml")) {
						resourceUri = resourceUri.substring(0, resourceUri.lastIndexOf(".yml"));
					}

					YamlMapFactoryBean yaml = new YamlMapFactoryBean();
					yaml.setResources(yamlResource);
					yaml.setSingleton(true);

					validationInfo.put(resourceUri, yaml.getObject());
				} catch (Exception e) {
					// log.debug(basePathMapping + " does not exist");
					log.debug("★★★★★★★★★★★★★★★★ ERROR resoures URI :: " + resourceUri + " ★★★★★★★★★★★★★★★★");
					log.debug("Exception message :: " + e.getMessage());
					continue;
				}
			}
		}
		log.debug("=== validationInfo:: " + validationInfo.toString());
	}

	public Map<String, Object> getValidationInfo() {
		return validationInfo;
	}

	public void setValidationInfo(Map<String, Object> validationInfo) {
		this.validationInfo = validationInfo;
	}

	public void setValidationInfoKeyAdd(String yml, Map<String, Object> validationInfo) {
		this.validationInfo.put(yml, validationInfo);
	}

	public void init() {
		validationInfo.clear();
		try {
			initValidationInfoConfig();
		} catch (Exception e) {
		}
	}

}
