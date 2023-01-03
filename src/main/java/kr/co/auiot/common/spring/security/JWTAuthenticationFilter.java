package kr.co.auiot.common.spring.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import kr.co.abacus.common.constant.ComConstant;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final AuthenticationManager authenticationManager; 
	
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager; 
	}

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		
//	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authToken = request.getHeader(ComConstant.X_AUTH_TOKEN);
		
		if (authToken == null) { 
			filterChain.doFilter(request, response); 
			return; 
		}

		try {
			Authentication authentication = authenticationManager.authenticate(new JWTAuthenticationToken(authToken));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			// This is needed to deliver input to controller
			// Invalid token or black token is not set authntication, so access denied happens
		}

		filterChain.doFilter(request, response);
	}
}
