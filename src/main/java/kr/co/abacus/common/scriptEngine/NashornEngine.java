package kr.co.abacus.common.scriptEngine;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.abacus.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class NashornEngine {

	

	private Map<String, ScriptEngine> scriptEngineList = new HashMap<String, ScriptEngine>();

	@Autowired
	private Environment env;

	@Autowired
	private ObjectMapper objectMapper = null;

	@PostConstruct
	private void init() {
		String scriptPath = env.getProperty("script.filePath");
		try {
			setScriptEngineList("classpath:/" + scriptPath);
		} catch (Exception e) {
			log.warn(e.getMessage());
		}

//		String scriptPath = env.getProperty("script.filePath");
//		File scriptFilePath = resourceLoader.getResource("classpath:\\" + scriptPath).getFile();
//		File[] scriptFileList = scriptFilePath.listFiles();
//		for (File temp : scriptFileList) {
//			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
//			engine.eval(new FileReader(temp.getAbsolutePath()));
//
//			scriptEngineList.put(temp.getName().replaceFirst("[.][^.]+$", ""), engine);
//		}
	}

	private void setScriptEngineList(String fullScriptPath) throws IOException, ScriptException {
		Resource[] resources = null;
		resources = new PathMatchingResourcePatternResolver().getResources(fullScriptPath + "/*");
		for (Resource resource : resources) {
			if (resource.getFilename().indexOf(".") > 0) {
				ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn"); // add scriptEngineList
				engine.eval(IOUtils.toString(resource.getInputStream(), "UTF-8"));
				scriptEngineList.put(resource.getFilename().replaceFirst("[.][^.]+$", ""), engine);

			} else {
				setScriptEngineList(fullScriptPath + File.separator + resource.getFilename());

			}
		}
	}

	public Object runScript(String filename, String functionName, Class<?> returnType, Object... parameter) throws BizException {
		Object jsonNode = null;
		try {
			ScriptEngine engine = scriptEngineList.get(filename);
			Invocable invocable = (Invocable) engine;
			Object result = invocable.invokeFunction(functionName, parameter);
			jsonNode = objectMapper.convertValue(result, returnType);
		} catch (ScriptException e) {
			throw new BizException(e, "javax.script.ScriptException");
		} catch (NoSuchMethodException e) {
			throw new BizException(e, "java.lang.NoSuchMethodException");
		}

		return jsonNode;
	}

	public Object runScript(String filename, String functionName, Object... parameter) throws BizException {
		Object result = null;
		try {
			ScriptEngine engine = scriptEngineList.get(filename);
			Invocable invocable = (Invocable) engine;
			result = invocable.invokeFunction(functionName, parameter);
		} catch (ScriptException e) {
			throw new BizException(e, "javax.script.ScriptException");
		} catch (NoSuchMethodException e) {
			throw new BizException(e, "java.lang.NoSuchMethodException");
		}

		return result;
	}

	public Object runScript(String filename, String functionName, Class<?> returnType) throws BizException {
		Object jsonNode = null;
		try {
			ScriptEngine engine = scriptEngineList.get(filename);
			Invocable invocable = (Invocable) engine;
			Object result = invocable.invokeFunction(functionName);
			jsonNode = objectMapper.convertValue(result, returnType);
		} catch (ScriptException e) {
			throw new BizException(e, "javax.script.ScriptException");
		} catch (NoSuchMethodException e) {
			throw new BizException(e, "java.lang.NoSuchMethodException");
		}

		return jsonNode;
	}

	public Object runScript(String filename, String functionName) throws BizException {
		Object result = null;
		try {
			ScriptEngine engine = scriptEngineList.get(filename);
			Invocable invocable = (Invocable) engine;
			result = invocable.invokeFunction(functionName);
		} catch (ScriptException e) {
			throw new BizException(e, "javax.script.ScriptException");
		} catch (NoSuchMethodException e) {
			throw new BizException(e, "java.lang.NoSuchMethodException");
		}

		return result;
	}

	public void setScript(String name, String content) throws ScriptException {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn"); // add scriptEngineList
		engine.eval(content);
		scriptEngineList.put(name.replaceFirst("[.][^.]+$", ""), engine);
	}

	public int size() {
		return scriptEngineList.size();
	}

	public List<String> getScriptName() {
		List<String> result = new ArrayList<String>();
		for (String key : scriptEngineList.keySet()) {
			result.add(key);
		}
		return result;
	}

}
