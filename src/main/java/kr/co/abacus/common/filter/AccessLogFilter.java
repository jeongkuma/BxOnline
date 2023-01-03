package kr.co.abacus.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
@Slf4j
@Component
@Order(1)
public class AccessLogFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("AccessLogFilter Init .... ");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		log.debug("AccessLogFilter doFilter .... ");

		HttpServletRequest req = (HttpServletRequest) request;

		String remoteAddr = StringUtils.defaultString(request.getRemoteAddr(), "-");
		String url = (req.getRequestURL() == null) ? "" : req.getRequestURL().toString();
		String queryString = StringUtils.defaultIfEmpty(req.getQueryString(), "");
		String refer = StringUtils.defaultString(req.getHeader("Refer"), "-");
		String agent = StringUtils.defaultString(req.getHeader("User-Agent"), "-");
		String fullUrl = url;
		fullUrl += StringUtils.isNotEmpty(queryString) ? "?" + queryString : queryString;

		StringBuffer result = new StringBuffer();
		result.append(":").append(remoteAddr).append(":").append(fullUrl).append(":").append(refer).append(":")
				.append(agent);

		log.info("LOG FILTER" + result.toString());

		long startDate = System.currentTimeMillis();
		ResettableStreamHttpServletRequest resettableStreamHttpServletRequest = new ResettableStreamHttpServletRequest(
				req);

		chain.doFilter(resettableStreamHttpServletRequest, response);
		long endDate = System.currentTimeMillis();
		log.info("========================================\trestAPI Access Time:"
				+ (double) (endDate - startDate) / 1000 + "초\t=====================================================");

	}

	@Override
	public void destroy() {
		log.debug("AccessLogFilter destroy .... ");
	}

}
