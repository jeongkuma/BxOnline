package kr.co.abacus.common.encryptor;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {

//	@Value("${jasypt.encryptor.password}")
	private String cryptoPassword = "abacus";
//	@Value("${jasypt.encryptor.algorithm}")
	private String cryptoAlgorithm = "PBEWithMD5AndDES";
//	@Value("${jasypt.encryptor.pool-size}")
//	private String pool_size;
//	@Value("${jasypt.encryptor.outputtype}")
//	private String outputtype;

	@Bean("jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(cryptoPassword); // 암호화에 사용할 키 -> 중요
		config.setPoolSize("1");
		config.setAlgorithm(cryptoAlgorithm); // 사용할 알고리즘
		config.setKeyObtentionIterations("1000");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType("base64");

		encryptor.setConfig(config);

		return encryptor;
	}

	public String encrypt(String msg) {
		return this.stringEncryptor().encrypt(msg);
	}

	public String decrypt(String msg) {
		return this.stringEncryptor().decrypt(msg);
	}

}
