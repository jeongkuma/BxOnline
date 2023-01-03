package kr.co.abacus.common.spring;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class AcfwMessageSource implements MessageSourceAware {
	private MessageSource messageSource;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, Locale.getDefault());
	}

	public String getMessage(String code) {
		return messageSource.getMessage(code, null, Locale.getDefault());
	}
}
