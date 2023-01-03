package kr.co.auiot.common.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import kr.co.abacus.common.spring.WebBaseConfig;

@Configuration
//@EnableWebMvc
public class WebConfig extends WebBaseConfig {

	@Autowired
	private CommonInterceptor commonInterceptor = null;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
		registry.addInterceptor(commonInterceptor).addPathPatterns("/**");
	}

}
