package com.lottemart.epc.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;

import com.lottemart.extend.annontation.ServerType;
import com.lottemart.extend.util.ServerTypeCondition;

/**
 * ECP Spring 설정
 * @author pat
 * @4UP 생성
 */
@Configuration
public class EPCSpringConfig {
	
	/**
	 * 개발자 PC와 개발서버를 위한 설정
	 * @author pat
	 *
	 */
	@Configuration
	@Conditional(ServerTypeCondition.class)
	@ServerType({"", "local", "dev"})
	public static class Default {
		
		@Bean(name="dataSource_srm", destroyMethod = "close")
		public DataSource dataSourceSrm(@Value("#{dbProperties}") Properties dbProperties)
			throws Exception {
			Properties	configProps;
			
			configProps	= new Properties();
			configProps.setProperty("driverClassName", 				dbProperties.getProperty("srmdb.driver"));
			configProps.setProperty("url", 							dbProperties.getProperty("srmdb.url"));
			configProps.setProperty("username", 					dbProperties.getProperty("srmdb.user"));
			configProps.setProperty("password", 					dbProperties.getProperty("srmdb.passwd"));
			configProps.setProperty("maxActive", 					"20");
			configProps.setProperty("maxIdle", 						"4");
			configProps.setProperty("minIdle", 						"2");
			configProps.setProperty("maxWait", 						"-1");
			configProps.setProperty("initialSize", 					"20");
			configProps.setProperty("poolPreparedStatements", 		"true");
			configProps.setProperty("maxOpenPreparedStatements", 	"100");
			configProps.setProperty("testOnBorrow", 				"true");
			configProps.setProperty("testOnReturn", 				"false");
			configProps.setProperty("testWhileIdle", 				"false");
			configProps.setProperty("validationQuery", 				"SELECT 1");
			
			return BasicDataSourceFactory.createDataSource(configProps);
		}		
		
		/*
		@Bean(name="dataSource_neo", destroyMethod = "close")
		public DataSource dataSourceNeo(@Value("#{dbProperties}") Properties dbProperties)
			throws Exception {
			Properties	configProps;
			
			configProps	= new Properties();
			configProps.setProperty("driverClassName", 				dbProperties.getProperty("emsdb.driver"));
			configProps.setProperty("url", 							dbProperties.getProperty("emsdb.url"));
			configProps.setProperty("username", 					dbProperties.getProperty("emsdb.user"));
			configProps.setProperty("password", 					dbProperties.getProperty("emsdb.passwd"));
			configProps.setProperty("maxActive", 					"20");
			configProps.setProperty("maxIdle", 						"4");
			configProps.setProperty("minIdle", 						"2");
			configProps.setProperty("maxWait", 						"-1");
			configProps.setProperty("initialSize", 					"20");
			configProps.setProperty("poolPreparedStatements", 		"true");
			configProps.setProperty("maxOpenPreparedStatements", 	"100");
			configProps.setProperty("testOnBorrow", 				"true");
			configProps.setProperty("testOnReturn", 				"false");
			configProps.setProperty("testWhileIdle", 				"false");
			configProps.setProperty("validationQuery", 				"SELECT 1 from dual");
			
			return BasicDataSourceFactory.createDataSource(configProps);
		}		
		*/
	}
	
	/**
	 * 운영 서버를 위한 설정
	 * @author pat
	 *
	 */
	@Configuration
	@Conditional(ServerTypeCondition.class)
	@ServerType({"stg", "prd"})
	public static class Product {

		private boolean JNDI_LOOKUP_ON_START	= false;
		
		@Bean(name="dataSource_srm")
		public JndiObjectFactoryBean dataSourceSrm()
			throws Exception {
			JndiObjectFactoryBean	rv;
			
			rv	= new JndiObjectFactoryBean();
			rv.setJndiName("java:comp/env/jdbc/srm");
			rv.setProxyInterface(DataSource.class);
			rv.setExpectedType(DataSource.class);
			rv.setLookupOnStartup(JNDI_LOOKUP_ON_START);
			return rv;
		}	
		/*
		@Bean(name="dataSource_neo")
		public JndiObjectFactoryBean dataSourceNeo()
			throws Exception {
			JndiObjectFactoryBean	rv;
			
			rv	= new JndiObjectFactoryBean();
			rv.setJndiName("java:comp/env/jdbc/neo");
			rv.setProxyInterface(DataSource.class);
			rv.setExpectedType(DataSource.class);
			rv.setLookupOnStartup(JNDI_LOOKUP_ON_START);
			return rv;
		}
		*/	
	}

}
