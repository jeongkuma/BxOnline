package kr.co.auiot.common.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/*
Embedded Servlet Container Config Bean

* 힙메모리, GC 설정은 boot 기동시 jvm 파라미터로 전달하도록 한다.

// 힙메모리
-server
-DServiceName
-Xms
-Xmx
-XX:NewSize
-XX:MaxNewSize
-XX:SurvivorRatio=8
-XX:MetaspaceSize
-XX:MaxMetaspaceSize (java1.7 : -XX:PermSize -XX:MaxPermSize)

// GC 설정 
-XX:+PrintGCDetails
-XX:+PrintGCTimeStamps
-XX:+PrintHeapAtGC
-XX:+PrintGCDateStamps

*/
@Configuration
public class ContainerConfig {

	@Autowired
	private Environment env = null;

//	@Autowired
//	DBCheckFileWriter dbCheckFileWriter;
//
//	@PostConstruct
//	public void dbCheckFileWriterInit() throws Exception {
////		String serviceMode = env.getProperty("mode");
//		String dbCheckInstanceName = env.getProperty("log.instanceName");
//		String dbCheckInstanceCode = env.getProperty("log.instanceCode");
//		String dbCheckRolling = env.getProperty("log.pool.rolling");
//		String dbCheckPath = env.getProperty("log.pool.filePath");
//		String writerTime = env.getProperty("log.pool.writerTime");
//		
//		// HangCheck
//		dbCheckFileWriter.init(dbCheckPath, dbCheckInstanceName, dbCheckInstanceCode, Integer.valueOf(dbCheckRolling),
//				Integer.valueOf(writerTime));
//	}

//	@PreDestroy
//	public void destory() throws Exception {
//		dbCheckFileWriter.close();
//	}

//	@Bean
//	@SuppressWarnings("rawtypes")
//	public TomcatServletWebServerFactory containerFactory() throws LifecycleException {
//
//		TomcatServletWebServerFactory tomcatFactory = new TomcatServletWebServerFactory() {
//
//			@Override
//			protected TomcatWebServer getTomcatWebServer(org.apache.catalina.startup.Tomcat tomcat) {
//				// host 설정
//				Host host = tomcat.getHost();
//				host.setName(env.getProperty("tomcat.host.name"));
//				// host.setAppBase("/svc/mgmtSvc/was/app/mgmtSvcWebApp"); // 불필요
//				// host.setAutoDeploy(false); // 불필요
//
//				return super.getTomcatWebServer(tomcat);
//			}
//
////			@Override
////			protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
////				tomcat.enableNaming();
////
////				// host 설정
////				Host host = tomcat.getHost();
////				host.setName(env.getProperty("tomcat.host.name"));
////				//host.setAppBase("/svc/mgmtSvc/was/app/mgmtSvcWebApp");	// 불필요
////				//host.setAutoDeploy(false);								// 불필요		
////
////				return super.getTomcatEmbeddedServletContainer(tomcat);
////			}
//
//			// 4. GlobalNamingResources.Resource 설정
//			@SuppressWarnings("unused")
//			@Override
//			protected void postProcessContext(org.apache.catalina.Context context) {
//				ContextResource resource = new ContextResource();
//				// 연결정보
//				/*
//				 * // oracle resource.setName("jdbc/smartir");
//				 * resource.setProperty("driverClassName", "oracle.jdbc.driver.OracleDriver");
//				 * resource.setProperty("url", "jdbc:oracle:thin:@localhost:1521:orcl");
//				 * resource.setProperty("username", "system"); resource.setProperty("password",
//				 * "Passw0rd");
//				 */
//
////				resource.setName("jdbc/smartir");
////				resource.setProperty("driverClassName", "org.h2.Driver");
////				resource.setProperty("url", "jdbc:h2:mem:test");		// local in memory로 테스트
////				resource.setProperty("username", "sa");					// local in memory 테스트 이기 때문에 username/password 의미없음. 
////				resource.setProperty("password", "null");				// local in memory 테스트 이기 때문에 username/password 의미없음.
//
//				// 옵션정보
////				resource.setAuth("Container");
////				resource.setType("javax.sql.DataSource");
//				// resource.setProperty("factory",
//				// "aip2.tomcat.jdbc.factory.CompEncryptedDataSourceFactory"); /* 체크필요!! */
////				resource.setProperty("factory", "org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory"); 	/* 체크필요!! */
////				resource.setProperty("initialsize", "20");												 	/* 체크필요!! initialSize */ 
////				resource.setProperty("loginTimeout", "10");												 	/* 체크필요!! loginTimeout */
////				resource.setProperty("maxTotal", "100");												 	/* 체크필요!! */
////				resource.setProperty("maxIdel", "100");												 	 	/* 체크필요!! */
////				resource.setProperty("maxWaitMillis", "30000");												/* 체크필요!! maxWait */
////				resource.setProperty("minIdle", "20");												 	 	/* 체크필요!! minIdle */
////				resource.setProperty("validationQuery", "select 1");										/* 체크필요!! validationQuery */
////				resource.setProperty("validationQueryTimeout", "60");										/* 체크필요!! validationQueryTimeout */
////				resource.setProperty("testOnBorrow", "true");										 		/* 체크필요!! testOnBorrow */
////				resource.setProperty("timeBetweenEvictionRunsMillis", "60000");						 		/* 체크필요!! timeBetweenEvictionRunsMillis */
////				resource.setProperty("testWhileIdle", "true");										 		/* 체크필요!! testWhileIdle */
////				resource.setProperty("poolPreparedStatements", "true");								 		/* 체크필요!! poolPreparedStatements */
////				resource.setProperty("maxOpenPreparedStatements", "50");							 		/* 체크필요!! maxOpenPreparedStatements */
////				resource.setProperty("removeAbandonedOrMaintanance", "true");							 	/* 체크필요!! removeAbandoned */
////				resource.setProperty("removeAbandonedTimeout", "60");							 		 	/* 체크필요!! removeAbandonedTimeout */
////				resource.setProperty("logAbandoned", "true");							 		 			/* 체크필요!! logAbandoned */
//				// resource.setProperty("maxActive", ""); <missing> /* 체크필요!! maxActive */
//				// (현 운영중인 서버에도 설정이 없는데..)
////				context.getNamingResources().addResource(resource);
//			}
//		};
//
//		// 1.스레드풀 설정
//		StandardThreadExecutor executor = new StandardThreadExecutor();
//		executor.setNamePrefix(env.getProperty("tomcat.executor.namePrefix"));
//		executor.setName(env.getProperty("tomcat.executor.name"));
//		executor.setMaxThreads(env.getProperty("tomcat.executor.maxThreads", Integer.class));
//		executor.setMinSpareThreads(env.getProperty("tomcat.executor.minSpareThreads", Integer.class));
//
//		// --------------------------------------------------------------------
//		// 2. HTTP1.1설정
//		tomcatFactory.addConnectorCustomizers(connector -> {
//			if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {
//				AbstractHttp11Protocol http11Protocol = ((AbstractHttp11Protocol) connector.getProtocolHandler());
//				http11Protocol.setExecutor(executor); // excutor
//				http11Protocol.setPort(env.getProperty("tomcat.httpProtocol.port", Integer.class)); // port
//				// protocal <missing>
//				http11Protocol.setServer(env.getProperty("tomcat.httpProtocol.server")); // server
//				http11Protocol
//						.setMaxSavePostSize(env.getProperty("tomcat.httpProtocol.maxSavePostSize", Integer.class)); // maxPostSize
//				http11Protocol.setMaxKeepAliveRequests(
//						env.getProperty("tomcat.httpProtocol.maxKeepAliveRequests", Integer.class)); // maxKeepAliveRequest
//				http11Protocol
//						.setConnectionTimeout(env.getProperty("tomcat.httpProtocol.connectionTimeout", Integer.class));
//				// enalbeLookups="false" <missing>
//				// maxParameterCount=100 <missing>
//				http11Protocol.setAcceptCount(env.getProperty("tomcat.httpProtocol.acceptCount", Integer.class)); // acceptCount
//				// redirectPort="8443" <missing>
//			}
//		});
//
//		// --------------------------------------------------------------------
//		// 3. Http11Nio 커넥터 등록(SSL)
//		/*
//		 * Connector http11NioConnector = new
//		 * Connector("org.apache.coyote.http11.Http11NioProtocol");
//		 * http11NioConnector.setScheme("https"); http11NioConnector.setPort(8443);
//		 * //http11NioConnector.setRedirectPort(8443);
//		 * //http11NioConnector.setSecure(false);
//		 * tomcatFactory.addAdditionalTomcatConnectors(http11NioConnector);
//		 * 
//		 * // 2. 4.1 등록된 Http11Nio 커넥터 추가 설정
//		 * tomcatFactory.addConnectorCustomizers(connector -> { Http11NioProtocol
//		 * http11NioProtocol = (Http11NioProtocol) connector.getProtocolHandler();
//		 * //http11NioProtocol.setPort(8443); // port
//		 * http11NioProtocol.setMaxThreads(150); // maxThreads
//		 * http11NioProtocol.setSSLEnabled(true); // SSLEnabled
//		 * http11NioProtocol.setConnectionTimeout(3600000);
//		 * 
//		 * try { File certificateKeyFile = new
//		 * ClassPathResource("conf/ssl/localhost-key.pem").getFile(); File
//		 * certificateFile = new
//		 * ClassPathResource("conf/ssl/localhost-cert.pem").getFile(); File
//		 * certificateChinFile = new ClassPathResource("conf/ssl/cacert.pem").getFile();
//		 * // cacert.pem 설정부분 누락 !!! 추가해야 하나?? !!!
//		 * http11NioProtocol.setKeystoreFile(certificateKeyFile.getAbsolutePath());
//		 * http11NioProtocol.setTruststoreFile(certificateFile.getAbsolutePath()); }
//		 * catch (IOException ex) { throw new
//		 * IllegalStateException("can't access keystore: [" + "keystore" +
//		 * "] or truststore: [" + "keystore" + "]", ex); } });
//		 */
//
//		// --------------------------------------------------------------------
//		// 4. AJP 커넥터 등록
//		Connector ajpConnector = new Connector("AJP/1.3");
//		ajpConnector.setPort(env.getProperty("tomcat.ajp.port", Integer.class));
//		ajpConnector.setSecure(env.getProperty("tomcat.ajp.secure", Boolean.class));
//		ajpConnector.setAllowTrace(env.getProperty("tomcat.ajp.allowTrace", Boolean.class));
//		ajpConnector.setScheme(env.getProperty("tomcat.ajp.scheme"));
//		ajpConnector.setMaxPostSize(env.getProperty("tomcat.ajp.maxPostSize", Integer.class));
//		ajpConnector.setMaxParameterCount(env.getProperty("tomcat.ajp.maxParameterCount", Integer.class));
//		ajpConnector.setEnableLookups(env.getProperty("tomcat.ajp.enableLookups", Boolean.class));
//		ajpConnector.setRedirectPort(env.getProperty("tomcat.ajp.redirectPort", Integer.class));
//		tomcatFactory.addAdditionalTomcatConnectors(ajpConnector);
//
//		// 4.1 등록된 AJP 커넥터 추가 설정
//		tomcatFactory.addConnectorCustomizers(connector -> {
//			if (connector.getProtocolHandler() instanceof AbstractAjpProtocol) {
//
//				AbstractAjpProtocol ajpProtocol = ((AbstractAjpProtocol) connector.getProtocolHandler());
//				ajpProtocol.setExecutor(executor); // excutor
//				// server="Server" <missing>
//				ajpProtocol.setConnectionTimeout(env.getProperty("tomcat.ajp.connectionTimeout", Integer.class));
//				// ajpProtocol.setBacklog(env.getProperty("tomcat.ajp.backlog", Integer.class));
//				// // backlog
//				ajpProtocol.setAcceptCount(env.getProperty("tomcat.ajp.backlog", Integer.class)); // 2019.01.03
//
//			}
//		});
//
//		// -------------------------------------------------------------------
//		// 5. Context 설정
//		tomcatFactory.addContextCustomizers((context) -> {
//			// context.setPath(mgmt);
//			// context.setDocBase("");
//			// context.setReloadable(false);
//
//			// Log 설정
//			/*
//			 * 1) LOG는 accesslog, gclog, HeapDump, catalina.out, catalina.log가 생성되어야 합니다. 2)
//			 * accesslog, gclog, catalina.out은 일별생성이 기본이나, 필요시 시간/분 단위로 생성도 가능합니다. 3)
//			 * accesslog는 하기와 같은 패턴으로 설정합니다. - pattern%h %l %u %t %r %s %b %{Referer}i
//			 * %{User-Agent}i" %D 4) HeapDump는 OutOfMemory발생시 자동 생성되어야 하고, 생성되는 파일명에
//			 * 연/월/일/시까지 포함해서 생성합니다. 5) gclog는 WAS 재기동시 자동으로 백업되도록 설정합니다. 6) AP Log는 WAS
//			 * Log에 기록하지 말고 별도의 다른파일에 생성되어야 합니다. 7) synchronized이슈를 고려해서 Debug와 같은 과도한 로그를
//			 * 남기지 않도록 권고합니다.
//			 */
//
//			// accesslog
//			AccessLogValve accessLogValve = new AccessLogValve();
//			accessLogValve.setEnabled(env.getProperty("tomcat.accesslog.enabled", Boolean.class));
//			accessLogValve.setDirectory(env.getProperty("tomcat.accesslog.directory"));
//			accessLogValve.setPrefix(env.getProperty("tomcat.accesslog.prefix"));
//			accessLogValve.setSuffix(env.getProperty("tomcat.accesslog.suffix"));
//			accessLogValve.setPattern(env.getProperty("tomcat.accesslog.pattern"));
//			accessLogValve.setFileDateFormat(env.getProperty("tomcat.accesslog.fileDateFormat"));
//			// resolveHosts=false <가이드에는 없으나, 기존 운영설정에는 있었음>
//
//			context.getPipeline().addValve(accessLogValve);
//
//		});
//		executor.start();
//
//		return tomcatFactory;
//
//	}

//	@Bean
//	public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
//		JndiObjectFactoryBean bean = new JndiObjectFactoryBean(); // create JNDI data source
//		bean.setJndiName("java:/comp/env/jdbc/smartir"); // jndiDataSource is name of JNDI data source 
//		bean.setProxyInterface(DataSource.class);
//		bean.setLookupOnStartup(false);
//		bean.afterPropertiesSet();
//		return (DataSource) bean.getObject();
//	}
}