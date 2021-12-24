package com.jaehyuk.batch.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;

@Configuration
@EnableTransactionManagement
public class DBConfig {
	
	
	/**
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix="datasource")
	public PoolProperties getPoolProperties() {
		return new PoolProperties();
	}
	
	/** DataSource 생성
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean
	public DataSource getDataSource() {
		org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource(getPoolProperties()) ;
		//return dataSource;
		//DataSource 
		Log4jdbcProxyDataSource ljpds  =  new Log4jdbcProxyDataSource(dataSource);
		Log4JdbcCustomFormatter formatter = new Log4JdbcCustomFormatter();
		
		LoggingType loggingType = LoggingType.MULTI_LINE;
		
		formatter.setLoggingType(loggingType);
		formatter.setSqlPrefix("SQL         :  ");
		ljpds.setLogFormatter(formatter);

		return (Log4jdbcProxyDataSource)ljpds;
	}

	/** SqlSessionFactory 생성
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean
	public SqlSessionFactory getSqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource); 
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"));
		sqlSessionFactory.setConfigLocation( new PathMatchingResourcePatternResolver().getResource("classpath:/mapper/config/mybatis-config.xml"));
		return (SqlSessionFactory) sqlSessionFactory.getObject();
	}
	
	
	/** sql SqlSessionTemplate 생성
	 * @param sqlSessionFactory
	 * @return
	 */
	@Bean(name="sqlSessionTemplate")
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) throws Exception {
		//return new SqlSessionTemplate( sqlSessionFactory  );
		SqlSessionTemplate temple = new SqlSessionTemplate( getSqlSessionFactory(getDataSource())  );
		//return new SqlSessionTemplate( getSqlSessionFactory(getDataSource())  );
		return temple;
	}
	
	/** transaction 호출
	 * @return
	 */
	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager manager = new DataSourceTransactionManager(getDataSource()) ;
		return manager;
	}
	
}
