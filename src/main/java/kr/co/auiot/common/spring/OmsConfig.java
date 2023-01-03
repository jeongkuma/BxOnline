package kr.co.auiot.common.spring;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import kr.co.abacus.common.util.LinkedHashMap;
import kr.co.abacus.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OmsConfig {
	private Map<String, Object> omsInfos = new HashMap<String, Object>();
	private LinkedHashMap<String, String> omsBasicDataset = new LinkedHashMap<String, String>();

	@Autowired
	private Environment env = null;

	@SuppressWarnings("unchecked")
	@PostConstruct
	
	private void initOmsInfoConfig() throws IOException {
		String basePathMapping = env.getProperty("myconfig.omsMappingPath");
		
		if (StringUtils.objectIfNullToEmpty(basePathMapping).length() < 1) {
			log.info("==== OmsConfig :: Error ==> Not OmsConfig");
		} else {
			try {
				Resource[] resources = new PathMatchingResourcePatternResolver().getResources(basePathMapping + "/*");
				for (Resource yamlResource : resources) {
					String nodefile = yamlResource.getFilename();
					YamlMapFactoryBean yaml = new YamlMapFactoryBean();
					yaml.setResources(yamlResource);
					yaml.setSingleton(true);
					if (nodefile.lastIndexOf(".") > 0) {
						nodefile = nodefile.substring(0, nodefile.lastIndexOf("."));
					}
					Map<String, Object> basicData = yaml.getObject();
					Map<String, Object> vnameOmsData = new LinkedHashMap<String, Object>();
					if (!"omsDataset".equals(nodefile)) {
						Map<String, Object> vnames = (Map<String, Object>) basicData.get("vnames");
						Set<String> setList = vnames.keySet();
						for (String vname : setList) {
							Map<String, String> value = (Map<String, String>) vnames.get(vname);
							vnameOmsData.put(vname, value);
						}
						omsInfos.put(nodefile, vnameOmsData);
						
					} else { // BASE PROCESS (classpath:/oms/omsDataset.yml)
						YamlMapFactoryBean datasetYaml = new YamlMapFactoryBean();
						datasetYaml.setResources(yamlResource);
						datasetYaml.setSingleton(true);
						Map<String, Object> datasetBasicData = datasetYaml.getObject();
						Map<String, Object> omssetting = (Map<String, Object>) datasetBasicData.get("omssetting");
						List<String> omsDataset = (List  <String>) omssetting.get("omsDataSet");
						for (int i = 0; i < omsDataset.size(); i++) {
							omsBasicDataset.put(omsDataset.get(i), "");
						}
					}
				}
				
			}catch(Exception e) {
				e.printStackTrace();
				log.debug(basePathMapping + " does not exist");
			}
		}

	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getOmsConfigData(String apiUrl, String vname) {
		try {
			Map<String, Object> omsInfoVnameList = (Map<String, Object>) omsInfos.get(apiUrl);
			return (Map<String, Object>) omsInfoVnameList.get(vname);
		} catch (Exception e) {
			return new HashMap<String, Object>();
		}
	}

	@SuppressWarnings("unchecked")
	public LinkedHashMap<String, String> getOmsBasicData() {
		return (LinkedHashMap<String, String>) omsBasicDataset.clone();
	}
}
