package kr.co.abacus.common.parser.config;

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
public class RequestParserInfoConfig {
	private Map<String, Object> requestParserInfo = new HashMap<String, Object>();
	private Map<String, Object> requestParserInfoShCommand = new HashMap<String, Object>();

	@Autowired
	private Environment env;

	@PostConstruct
	private void initRequestParserInfoConfig() throws IOException {
		String basePathMapping = env.getProperty("myconfig.requestParser");
		if (StringUtils.objectIfNullToEmpty(basePathMapping).length() < 1) {
			log.info("==== RequestParserInfoConfig :: Error ==> Not RequestParserInfoConfig");
		} else {
			try {
				Resource[] resources = null;
				resources = new PathMatchingResourcePatternResolver().getResources(basePathMapping + "/*/*");
				for (Resource yamlResource : resources) {
					YamlMapFactoryBean yaml = new YamlMapFactoryBean();
					yaml.setResources(yamlResource);
					yaml.setSingleton(true);
					String filekey = StringUtils.getRuleFileKeyName(yamlResource.getURI());
					if (filekey.contains(".yml")) {
						filekey = filekey.substring(0, filekey.lastIndexOf(".yml"));
					}
					requestParserInfo.put(filekey, yaml.getObject());
				}
			} catch (Exception e) {
				log.debug(basePathMapping + " does not exist");
			}
		}
	}

	public Map<String, Object> getRequestParserInfo() {
		return requestParserInfo;
	}

	public void setRequestParserInfo(Map<String, Object> requestParserInfo) {
		this.requestParserInfo = requestParserInfo;
	}

	public Map<String, Object> getRequestParserInfoShCommand() {
		return requestParserInfoShCommand;
	}

	public void setRequestParserInfoShCommand(Map<String, Object> requestParserInfoShCommand) {
		this.requestParserInfoShCommand = requestParserInfoShCommand;
	}

	public void init() {
		requestParserInfo.clear();
		requestParserInfoShCommand.clear();
		try {
			initRequestParserInfoConfig();
		} catch (Exception e) {
		}
	}

}
