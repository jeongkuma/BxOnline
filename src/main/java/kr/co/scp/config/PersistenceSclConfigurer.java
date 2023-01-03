package kr.co.scp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;


@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari2")
public class PersistenceSclConfigurer extends HikariConfig {

    @Bean
    public DataSource mainDataSourceScl() throws SQLException {
        return new HikariDataSource(this);
    }

}
