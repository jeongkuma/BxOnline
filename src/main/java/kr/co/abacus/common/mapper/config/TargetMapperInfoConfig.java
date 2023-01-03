package kr.co.abacus.common.mapper.config;

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
public class TargetMapperInfoConfig {
	private Map<String, Object> targetMapperInfo = new HashMap<String, Object>();

	@Autowired
	private Environment env = null;

	@PostConstruct
	private void initTargetMapperInfoConfig() throws IOException {
		String basePathMapping = env.getProperty("myconfig.targetMapper");
		if (StringUtils.objectIfNullToEmpty(basePathMapping).length() < 1) {
			log.info("==== TargetMapperInfoConfig :: Error ==> Not TargetMapperInfoConfig");
		} else {
			try {
				Resource[] resources = null;
				resources = new PathMatchingResourcePatternResolver().getResources(basePathMapping + "/*/*");
				for (Resource yamlResource : resources) {
					YamlMapFactoryBean yaml = new YamlMapFactoryBean();
					yaml.setResources(yamlResource);
					yaml.setSingleton(true);
					targetMapperInfo.put(StringUtils.getRuleFileKeyName(yamlResource.getURI()), yaml.getObject());
				}
			} catch (Exception e) {
				log.debug(basePathMapping + " does not exist");
			}
		}

	}

	public Map<String, Object> getTargetMapperInfo() {
		return targetMapperInfo;
	}

	public void setTargetMapperInfo(Map<String, Object> targetMapperInfo) {
		this.targetMapperInfo = targetMapperInfo;
	}

	public void init() {
		targetMapperInfo.clear();
		try {
			initTargetMapperInfoConfig();
		} catch (Exception e) {
		}
	}

}
