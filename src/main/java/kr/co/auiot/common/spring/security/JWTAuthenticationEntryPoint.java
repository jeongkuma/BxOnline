package kr.co.auiot.common.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		 
		 response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized.");
		 
		 //response.reset();
		 
		
			
		//throw new BizException("org.springframework.security.core.AuthenticationException");
		// TODO Auto-generated method stub
		
	}

}
