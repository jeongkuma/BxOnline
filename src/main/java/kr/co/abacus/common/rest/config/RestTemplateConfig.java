package kr.co.abacus.common.rest.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

@Configuration
public class RestTemplateConfig {

	@Autowired
	private Environment env = null;

	@Autowired
	private List<HttpMessageConverter<?>> converters = null;

//	@Bean
//	public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
//
//		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
//
//		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
//				.build();
//
//		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
//
//		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
//
//		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//
//		requestFactory.setHttpClient(httpClient);
//		RestTemplate restTemplate = new RestTemplate(requestFactory);
//		return restTemplate;
//	}

	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) throws Exception {

		RestTemplate restTemplate = new RestTemplate(getHttpComponentsClientHttpRequestFactory());
//		restTemplate.setRequestFactory(getHttpComponentsClientHttpRequestFactory());
		restTemplate.setMessageConverters(converters);
		return restTemplate;
	}

//	@Bean
//	public List<HttpMessageConverter<?>> getMessageConvertor() {
//		List<HttpMessageConverter<?>> converters = new ArrayList<>();
//
//		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//		List<MediaType> jacksonMediaType = new ArrayList<>();
//		jacksonMediaType.add(MediaType.APPLICATION_JSON_UTF8);
//		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(jacksonMediaType);
//		converters.add(mappingJackson2HttpMessageConverter);
//
//		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
//		List<MediaType> stringMediaType = new ArrayList<>();
//		stringMediaType.add(MediaType.TEXT_HTML);
//		stringMediaType.add(new MediaType(MediaType.TEXT_HTML, Charset.forName("UTF-8")));
//		stringHttpMessageConverter.setSupportedMediaTypes(stringMediaType);
//		return converters;
//	}

	@Bean
	public HttpComponentsClientHttpRequestFactory getHttpComponentsClientHttpRequestFactory()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
				getHttpClientBuilder());
		httpComponentsClientHttpRequestFactory.setHttpClient(httpComponentsClientHttpRequestFactory.getHttpClient());
		httpComponentsClientHttpRequestFactory.setReadTimeout(env.getProperty("resttemplete.config.readTimeout", Integer.class, 45000));
		return httpComponentsClientHttpRequestFactory;
	}

	@Bean
	public CloseableHttpClient getHttpClientBuilder() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setDefaultRequestConfig(getRequestConfig());
		httpClientBuilder.setDefaultConnectionConfig(getConnectionConfig());
		httpClientBuilder.setDefaultSocketConfig(getSocketConfig());
		httpClientBuilder.setMaxConnTotal(env.getProperty("resttemplete.config.maxConnection", Integer.class, 200));
		httpClientBuilder.setMaxConnPerRoute(env.getProperty("resttemplete.config.maxConnectionPerRoute", Integer.class, 200));

		// Setter proxy
		String proxyHost = env.getProperty("map.proxyHost");
		String proxyPort = env.getProperty("map.proxyPort");
		if (StringUtils.isNotEmpty(proxyHost) && StringUtils.isNotEmpty(proxyPort)) {
			int proxyPortNum = Integer.parseInt(proxyPort);
//			CredentialsProvider credsProvider = new BasicCredentialsProvider();
//			credsProvider.setCredentials(new AuthScope(proxyHost, proxyPortNum),
//					new UsernamePasswordCredentials(proxyUser, proxyPassword));
//			httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
			httpClientBuilder.useSystemProperties();
			httpClientBuilder.setProxy(new HttpHost(proxyHost, proxyPortNum));
			httpClientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
		}

		// SSL 인증서 유효성 검사 사용 안 함
		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
				return true;
			}
		};
		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
		httpClientBuilder.setSSLSocketFactory(csf);
//		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		return httpClientBuilder.build();
	}

	@Bean
	public RequestConfig getRequestConfig() {
		Builder requestConfig = RequestConfig.custom();
		requestConfig.setSocketTimeout(env.getProperty("resttemplete.config.socketTimeout", Integer.class, 30000));
		requestConfig.setConnectTimeout(env.getProperty("resttemplete.config.connectTimeout", Integer.class, 30000));
		requestConfig.setConnectionRequestTimeout(env.getProperty("resttemplete.config.connectionRequestTimeout", Integer.class, 30000));
		requestConfig.setExpectContinueEnabled(false);
		return requestConfig.build();
	}

	@Bean
	public ConnectionConfig getConnectionConfig() {
		ConnectionConfig.Builder builder = ConnectionConfig.custom();
		builder.setBufferSize(env.getProperty("resttemplete.config.bufferSize", Integer.class, 8192));
		builder.setCharset(Consts.UTF_8);
		return builder.build();
	}

	@Bean
	public SocketConfig getSocketConfig() {
		SocketConfig.Builder builder = SocketConfig.custom();
		builder.setTcpNoDelay(true);
		return builder.build();
	}
}
