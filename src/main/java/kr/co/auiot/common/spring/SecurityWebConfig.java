package kr.co.auiot.common.spring;

import java.util.Arrays;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import kr.co.auiot.common.dto.common.User;
import kr.co.auiot.common.dto.common.UserRepository;
import kr.co.auiot.common.spring.security.JWTAuthRedirectFilter;
import kr.co.auiot.common.spring.security.JWTAuthenticationEntryPoint;
import kr.co.auiot.common.spring.security.JWTAuthenticationFilter;
import kr.co.auiot.common.spring.security.JWTAuthenticationProvider;
import kr.co.auiot.common.spring.security.NoopAuthenticationEventPublisher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class SecurityWebConfig extends WebSecurityConfigurerAdapter {

	// @Value("${jwt.scretKey}")
	private final String secretKey = Base64.getEncoder().encodeToString("scl_industryiot".getBytes());

	@Value("${myconfig.islocal}")
	private Boolean islocal = true;

	@Bean
	public JWTAuthenticationFilter jWTAuthenticationFilter() throws  Exception {
		return new JWTAuthenticationFilter(authenticationManager());
	}

	@Bean
	public JWTAuthenticationProvider jWTAuthenticationProvider() {
		return new JWTAuthenticationProvider(secretKey, userRepository);
	}

	// @Autowired
	private UserRepository userRepository;
	private JWTAuthenticationProvider jwtProvider = new JWTAuthenticationProvider(secretKey, userRepository);

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationEventPublisher(new NoopAuthenticationEventPublisher()).authenticationProvider(jWTAuthenticationProvider());
	}

//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring()
//			.antMatchers("/favicon.ico",
//                    "/**/*.png",
//                    "/**/*.gif",
//                    "/**/*.svg",
//                    "/**/*.jpg",
//                    "/**/*.html",
//                    "/**/*.css",
//                    "/**/*.js",
//                    "/**/h2-console/**")
//		;
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		setLocalMode(http);
//		if (islocal) {
//			log.debug("==== :: islocal :: local");
//			setLocalMode(http);
//		} else {
//			log.debug("==== :: islocal :: real");
//			setRealMode(http);
//		}
	}

//	private void setLocalMode(HttpSecurity http) throws Exception {
//		http
//        	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        		.and()
//        	.csrf().disable()
//        	.addFilterBefore(new JWTAuthenticationFilter(authenticationManager()), AbstractPreAuthenticatedProcessingFilter.class)
//        	.addFilterBefore(new BasicAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class)
//        	.authorizeRequests()
////        	.antMatchers(HttpMethod.POST, "/green/createUser").permitAll()
//        		.antMatchers(HttpMethod.POST, "/green/createUser").permitAll()
////        		.antMatchers("/green/createUser").permitAll()
//        		.antMatchers(HttpMethod.POST, "/", "/green", "/green/createUser").permitAll()
//        		.antMatchers(HttpMethod.POST, "/green/**").permitAll()
////        		.antMatchers(HttpMethod.POST, "/green/**").hasRole("ADMIN")
////        	.antMatchers("/**").hasRole("USER")
//        	;
//	}

	private void setLocalMode(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
				.addFilterBefore(jWTAuthenticationFilter(),
						AbstractPreAuthenticatedProcessingFilter.class)
				.addFilterBefore(new BasicAuthenticationFilter(authenticationManager()),
						BasicAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js", "/**/h2console/**").permitAll()
				.antMatchers("/h2console", "**/h2console/**", "/h2-console", "/h2-console/**").permitAll()
				.antMatchers(HttpMethod.POST, "/token", "/Login").permitAll()
				.antMatchers(HttpMethod.POST, "/test", "/getLabel", "/testErr").permitAll()
				.antMatchers(HttpMethod.POST, "/select", "/list", "/update", "/exp/**").permitAll()
				.antMatchers(HttpMethod.POST, "/getReDeviceinforList").permitAll()
				.antMatchers(HttpMethod.POST, "/online/**").permitAll()
				.antMatchers(HttpMethod.POST, "/sample/**").permitAll()
				.antMatchers(HttpMethod.POST, "/resources/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/**").permitAll()
				.antMatchers(HttpMethod.POST, "/iot-ws/**").permitAll()
				.antMatchers(HttpMethod.POST, "/online/label/getlabel").permitAll()
				.antMatchers(HttpMethod.POST, "/online/iotlogin/processIotLogin").permitAll()
				.antMatchers(HttpMethod.POST, "/online/iotToken/refreshToken").permitAll()
				.antMatchers(HttpMethod.POST, "/online/com/iotInsert/uploadService").permitAll()
				.antMatchers(HttpMethod.POST, "/uploadFile").permitAll().antMatchers(HttpMethod.POST, "/restCall")
				.permitAll().antMatchers(HttpMethod.GET, "/downloadFile").permitAll()
				.antMatchers(HttpMethod.POST, "/**").authenticated().and()
				.cors()
				.and().csrf().disable().headers().frameOptions().disable()
//					.and().csrf().ignoringAntMatchers("/h2console/**");
		;
		http.exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint());
		http.addFilterAfter(this.customRedirectFilter(), ExceptionTranslationFilter.class);
	}

	@Bean
	public JWTAuthRedirectFilter customRedirectFilter() {
		return new JWTAuthRedirectFilter();
	}

	private void setRealMode(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
				.addFilterBefore(jWTAuthenticationFilter(),
						AbstractPreAuthenticatedProcessingFilter.class)
				.addFilterBefore(new BasicAuthenticationFilter(authenticationManager()),
						BasicAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js", "/**/h2console/**").permitAll()
				.antMatchers(HttpMethod.POST, "/createUser").permitAll()
				.antMatchers(HttpMethod.POST, "/online/label/getlabel").permitAll()
				.antMatchers(HttpMethod.POST, "/online/iotlogin/**").permitAll()
				.antMatchers(HttpMethod.POST, "/online/iotterms/**").permitAll()
				.antMatchers(HttpMethod.POST, "/online/iag/event/**").permitAll()
				.antMatchers(HttpMethod.POST, "/Login").permitAll()
				.antMatchers(HttpMethod.POST, "/online/com/iotInsert/uploadService").permitAll()
				.antMatchers(HttpMethod.POST, "/**").authenticated().and()
				.cors().and();

		// to do
		http.exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint());
		http.addFilterAfter(this.customRedirectFilter(), ExceptionTranslationFilter.class);
	}

	public String getToken(User user) {
		return jwtProvider.getToken(user);
	}

	public String getRefreshToken(User user) {
		return jwtProvider.getRefreshToken(user);
	}

	private final long MAX_AGE_SECS = 3600;

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setExposedHeaders(Arrays.asList("Content-Disposition"));
		configuration.setMaxAge(MAX_AGE_SECS);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	public User getUser(String token) {
		User user = new User();
		Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		user.setCustId(claimsJws.getBody().get("custId", String.class));
		user.setSvcCd(claimsJws.getBody().get("svcCd", String.class));
		user.setUserId(claimsJws.getBody().get("userId", String.class));
		user.setUserName(claimsJws.getBody().get("userName", String.class));
		user.setCustSeq(claimsJws.getBody().get("custSeq", String.class));
		user.setUserSeq(claimsJws.getBody().get("userSeq", String.class));
		user.setOrgSeq(claimsJws.getBody().get("orgSeq", String.class));
		user.setOrgNm(claimsJws.getBody().get("orgNm", String.class));
		user.setRoleCdId(claimsJws.getBody().get("roleCdId", String.class));
		user.setRoleCdNm(claimsJws.getBody().get("roleCdNm", String.class));
		return user;
	}

	public User getRefreshToken(String token) {
		User user = new User();
		Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		user.setCustId(claimsJws.getBody().get("custId", String.class));
		user.setSvcCd(claimsJws.getBody().get("svcCd", String.class));
		user.setUserId(claimsJws.getBody().get("userId", String.class));
		user.setUserName(claimsJws.getBody().get("userName", String.class));
		user.setCustSeq(claimsJws.getBody().get("custSeq", String.class));
		user.setUserSeq(claimsJws.getBody().get("userSeq", String.class));
		user.setOrgSeq(claimsJws.getBody().get("orgSeq", String.class));
		user.setOrgNm(claimsJws.getBody().get("orgNm", String.class));
		user.setRoleCdId(claimsJws.getBody().get("roleCdId", String.class));
		user.setRoleCdNm(claimsJws.getBody().get("roleCdNm", String.class));
		user.setTime(claimsJws.getBody().get("time", Long.class));
		return user;
	}

}
