package kr.co.auiot.common.spring.security;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("serial")
public class InvalidAuthenticationException extends AuthenticationException {

	public InvalidAuthenticationException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}
	
	public InvalidAuthenticationException(String msg, Throwable t) {
		super(msg, t);
		// TODO Auto-generated constructor stub
	}
}

