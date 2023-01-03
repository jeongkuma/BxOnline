package kr.co.scp.config;


import kr.co.scp.config.helper.ResourceFinder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Slf4j
@Configuration
@MapperScan(basePackages = "kr.co*", sqlSessionFactoryRef = "mainSqlSessionFactory")
public class MybatisConfigurer {

	@Resource(name = "mainDataSource")
	private DataSource mainDataSource;

	@Bean
	public SqlSessionFactory mainSqlSessionFactory() {
		if (mainDataSource == null) {
			return null;
		}
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(mainDataSource);

		try {
			sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/base/*.xml"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			System.exit(0);
		}

		sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
		sqlSessionFactoryBean.setTypeAliases(ResourceFinder.getClassesArray("kr/co/*/*/*/*/*$1"));

		SqlSessionFactory sqlSessionFactory = null;
		try {
			sqlSessionFactory = sqlSessionFactoryBean.getObject();
		} catch (Exception e) {
			sqlSessionFactory = null;
			e.printStackTrace();
			log.error(e.getMessage());
			System.exit(0);
		}

		return sqlSessionFactory;
	}

	@Bean
	@Qualifier("mainSqlSessionTemplate")
	public SqlSessionTemplate mainSqlSessionTemplate() {
		if (mainDataSource == null) {
			return null;
		}
		return new SqlSessionTemplate(mainSqlSessionFactory());
	}

	@Bean(name = "txManager")
	public DataSourceTransactionManager txManager() {
		if (mainDataSource == null) {
			return null;
		}
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(mainDataSource);
		return txManager;
	}
}
