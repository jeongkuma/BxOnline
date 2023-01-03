package kr.co.abacus.common.message;

import kr.co.abacus.common.api.dao.MessageDAO;
import kr.co.abacus.common.api.dto.MessageDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class MultiLabalConfig {
	private Map<String, Object> labelInfo = new HashMap<String, Object>();

	@Autowired
	private Environment env = null;
	
	@Autowired
	private MessageDAO messageDAO;
	
	@PostConstruct
	@Bean
	public void initMutilLabelConfig() throws IOException {
		log.debug("==== MultiLabalConfig Config Start ");
		String basePathMapping = env.getProperty("myconfig.label");
		if (StringUtils.objectIfNullToEmpty(basePathMapping).length() < 1) {
			log.info("==== MultiLabalConfig :: Error ==> Not MultiLabalConfig");
		} else {
			try {
				Resource[] resources = null;
				resources = new PathMatchingResourcePatternResolver().getResources(basePathMapping + "/*");
				for (Resource yamlResource : resources) {
					YamlMapFactoryBean yaml = new YamlMapFactoryBean();
					yaml.setResources(yamlResource);
					yaml.setSingleton(true);
					log.debug("==== MultiLabalConfig :: getFilename ::  " + yamlResource.getFilename());
					String filekey = yamlResource.getFilename();
					if (filekey.contains(".yml")) {
						filekey = filekey.substring(0, filekey.lastIndexOf(".yml"));
					}
					labelInfo.put(filekey, yaml.getObject());
				}
			}catch(Exception e) {
				log.debug(basePathMapping + " does not exist");
			}
		}
		
		//log.debug("==== MultiLabalConfig :: labelInfo ::  " + getLabelInfo());
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getLabelInfo() {
		String langSet = getLangSet();
		
		Map<String, Object> msg = (Map<String, Object>) labelInfo.get(langSet);
		
		return msg;
	}

	public void setLanguageMap() {
		String langSet = getLangSet();
		
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setLang_set(langSet);
		messageDTO.setLang_type("LABEL");
		
		List<Map<String, Object>> messageMap = messageDAO.getMessageList(messageDTO);
		
		for(int i=0;i<messageMap.size();i++) {
			Map<String, Object> map = messageMap.get(i);
			setLabelInfo((String)map.get("MSG_KEY"), (String)map.get("MSG_DESC"));
		}
	}
	
	private void setLabelInfo(String key, String value) {
		String langSet = getLangSet();
		
		if (labelInfo == null) {
			labelInfo = new HashMap<String, Object>();
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put(key, value);
			labelInfo.put(langSet, temp);
		} else {
			Map<String, Object> temp = (Map<String, Object>)labelInfo.get(langSet);
			
			if (temp.containsKey(key)) {
				temp.replace(key, value);
			} else {
				temp.put(key, value);
			}
			
			if (labelInfo.containsKey(langSet))
				labelInfo.replace(langSet, temp);
			else
				labelInfo.put(langSet, temp);
		}
	}
	
	private String getLangSet() {
		ServletRequestAttributes currentRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		String langSet = (String) currentRequestAttributes.getRequest().getSession().getAttribute(ComConstant.X_LANG_SET);
		
		log.debug("MultiLangConfig langSet :: " + langSet);
		
		if (langSet == null) langSet = ComConstant.DEFAULT_CHAR_SET;

		return langSet;
	}

}