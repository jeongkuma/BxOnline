package kr.co.scp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;


@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari1")
public class PersistenceConfigurer extends HikariConfig {

    @Bean
    public DataSource mainDataSource() throws SQLException {
        return new HikariDataSource(this);
    }

}

//
//@Configuration
//@ConfigurationProperties("spring.datasource.hikari")
//public class PersistenceConfigurer {
//
//    @Bean(destroyMethod="postDeregister")
//    public DataSource mainDataSource() {
//        return DataSourceBuilder.create().type(HikariDataSource.class).build();
//    }
////	@Autowired
////	private DbmsConfig dbmsConfig;
////
////    @Bean(destroyMethod="postDeregister")
////    public DataSource mainDataSource() {
////        BasicDataSource dataSource = new BasicDataSource();
////        dataSource.setDriverClassName(dbmsConfig.getMain().getDriver());
////        dataSource.setUrl(dbmsConfig.getMain().getUrl());
////        dataSource.setUsername(dbmsConfig.getMain().getUserName());
////        dataSource.setPassword(dbmsConfig.getMain().getPassword());
////        dataSource.setValidationQuery(dbmsConfig.getMain().getValidateQuery());
////        dataSource.setDefaultAutoCommit(false);
////        dataSource.setInitialSize(dbmsConfig.getMain().getMinConnect());
////        dataSource.setMaxTotal(dbmsConfig.getMain().getMaxConnect());
////        dataSource.setMinIdle(dbmsConfig.getMain().getMinConnect());
////        dataSource.setMaxIdle(dbmsConfig.getMain().getMaxConnect());
////        dataSource.setMaxWaitMillis(dbmsConfig.getMain().getWait());
////        return dataSource;
////    }
//}
