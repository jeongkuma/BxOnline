package kr.co.abacus.common.processing.config;

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
public class ProcessingInfoConfig {
	private Map<String, Object> processingInfo = new HashMap<String, Object>();

	@Autowired
	private Environment env = null;
	
	@PostConstruct
	private void initProcessingInfoConfig() throws IOException {
		String basePathMapping = env.getProperty("myconfig.processing");
		if (StringUtils.objectIfNullToEmpty(basePathMapping).length() < 1) {
			log.info("==== ProcessingInfoConfig :: Error ==> Not ProcessingInfoConfig");
		} else {
			try {
				Resource[] resources = null;
				resources = new PathMatchingResourcePatternResolver().getResources(basePathMapping + "/*");
				for (Resource yamlResource : resources) {
					YamlMapFactoryBean yaml = new YamlMapFactoryBean();
					yaml.setResources(yamlResource);
					yaml.setSingleton(true);
					processingInfo.put(yamlResource.getFilename(), yaml.getObject());
				}
				
			} catch (Exception e) {
				log.debug(basePathMapping + " does not exist");
			}
		}

	}

	public Map<String, Object> getProcessingInfo() {
		return processingInfo;
	}

	public void setProcessingInfo(Map<String, Object> processingInfo) {
		this.processingInfo = processingInfo;
	}

}
