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
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Slf4j
@Configuration
@MapperScan(basePackages = "kr.co.scl*", sqlSessionFactoryRef = "mainSqlSessionFactoryScl")
public class MybatisConfigurerScl {

	@Resource(name = "mainDataSourceScl")
	private DataSource mainDataSourceScl;

	private PlatformTransactionManager ds1;
	private PlatformTransactionManager ds2;

	@Bean
	public SqlSessionFactory mainSqlSessionFactoryScl() {

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(mainDataSourceScl);

		try {

			sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("mapper/scl/*.xml"));

		} catch (Exception e) {
			log.error(e.getMessage());
			System.exit(0);
		}

		sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
		sqlSessionFactoryBean.setTypeAliases(ResourceFinder.getClassesArray("kr/co/scl/biz/*/*"));
//		sqlSessionFactoryBean.setTypeAliases(ResourceFinder.getClassesArray("kr/co/*/*/*/*/*"));

		SqlSessionFactory sqlSessionFactory = null;
		try {
			sqlSessionFactory = sqlSessionFactoryBean.getObject();
		} catch (Exception e) {
			sqlSessionFactory = null;
			log.error(e.getMessage());
			System.exit(0);
		}

		return sqlSessionFactory;
	}

	@Bean
	public SqlSessionTemplate mainSqlSessionTemplate() {
		if (mainDataSourceScl == null) {
			return null;
		}
		return new SqlSessionTemplate(mainSqlSessionFactoryScl());
	}

	@Bean(name = "txManagerScl")
	public DataSourceTransactionManager txManager() {
		if (mainDataSourceScl == null) {
			return null;
		}
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(mainDataSourceScl);
		return txManager;
	}


	@Bean(name = "chainedTransactionManager")
	public ChainedTransactionManager chainedTransactionManager(
			@Qualifier("txManager") PlatformTransactionManager ds1
			, @Qualifier("txManagerScl") PlatformTransactionManager ds2) {

		this.ds1 = ds1;
		this.ds2 = ds2;

		return new ChainedTransactionManager(ds1, ds2);
	}
}
