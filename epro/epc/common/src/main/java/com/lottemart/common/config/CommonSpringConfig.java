package com.lottemart.common.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.logging.log4j.core.config.Configurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiObjectFactoryBean;

import com.lottemart.extend.annontation.ServerType;
import com.lottemart.extend.sql.MyBatisFactoryPostProcessor;
import com.lottemart.extend.sql.UndefinedDataSource;
import com.lottemart.extend.util.ServerTypeCondition;

import lcn.module.framework.property.impl.ConfigurationReader;
import lcn.module.framework.property.impl.PropertyServiceImpl;


/**
 * common spring 설정
 * @author pat
 *
 */
@Configuration
public class CommonSpringConfig {
	
    @PostConstruct
	public void init() {
		String log4jConfigLocation = System.getProperty("server.type", System.getProperty("spring.profiles.active", "local"));
		
		if(!"prd".equals(log4jConfigLocation)) {
			Configurator.initialize(null, "classpath:log/" + log4jConfigLocation + "/log4j2.xml");
		}
	}		
	
	@Bean("configurationService")
	public ConfigurationReader configurationService(@Autowired Environment springEnv) {
		ConfigurationReader	rv;
		
		rv	= new ConfigurationReader();
		rv.setConfigLocation(springEnv.resolvePlaceholders("/property/${server.type:local}/config.xml"));
		rv.setReloadable("true");
		return rv;
	}
	
	/**
	 * MyBatis 추가 설정
	 * MyBatis 설정 xml은 classpath:application/mybatis/config/mybatis-config.xml 를 사용하며,
	 * Mapper XML 파일은 classpath*:application/mybatis/mapper/**&#47;*.xml 에 해당하는 모든 xml 파일이 대상입니다.
	 * @param appContext
	 * @return
	 */
	@Bean
	public static BeanFactoryPostProcessor mybatisBeanFactoryPostProcessor(@Autowired ApplicationContext springContext) {
		return new MyBatisFactoryPostProcessor(springContext
			, "classpath:application/mybatis/config/mybatis-config.xml"
			, "classpath*:application/mybatis/mapper/**/*.xml");
	}
	
	
	
	/**
	 * 개발자 PC와 개발서버를 위한 설정
	 * @author pat
	 *
	 */
	@Configuration
	@Conditional(ServerTypeCondition.class)
	@ServerType({"", "local", "dev"})
	public static class Default {

		@Bean("dbProperties")
		public PropertiesFactoryBean dbProperties(@Autowired ApplicationContext springContext, @Autowired Environment springEnv) {
			PropertiesFactoryBean	rv;
			
			rv	= new PropertiesFactoryBean();
			rv.setLocation(springContext.getResource(springEnv.resolvePlaceholders("classpath:property/${server.type:local}/db.properties")));
			return rv;
		}
		
		@Bean
		public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer(@Value("#{dbProperties}") Properties dbProperties) {
			PropertyPlaceholderConfigurer	rv;
			
			rv	= new PropertyPlaceholderConfigurer();
			rv.setProperties(dbProperties);
			return rv;
		}
		
		
		@Bean(name="propertiesService", destroyMethod = "destroy")
		public PropertyServiceImpl propertiesService() {
			PropertyServiceImpl	rv;
			Map<String, String>	propMap;
			
			propMap	= new HashMap<>();
			propMap.put("pageUnit", "10");
			propMap.put("pageSize", "10");
			propMap.put("Globals.fileStorePath", "/files/");
			propMap.put("Globals.otpIp", "10.52.26.104");
			propMap.put("Globals.otpPort", "1812");
			propMap.put("Globals.otpSecret", "10522237");
			propMap	= Collections.unmodifiableMap(propMap);
			
			rv	= new PropertyServiceImpl();
			rv.setProperties(propMap);
			
			return rv;
		}
		
		@Bean(name="dataSource", destroyMethod = "close")
		public DataSource dataSource(@Value("#{dbProperties}") Properties dbProperties)
			throws Exception {
			Properties	configProps;
			
			configProps	= new Properties();
			configProps.setProperty("driverClassName", 				dbProperties.getProperty("db.driver"));
			configProps.setProperty("url", 							dbProperties.getProperty("db.url"));
			configProps.setProperty("username", 					dbProperties.getProperty("db.user"));
			configProps.setProperty("password", 					dbProperties.getProperty("db.passwd"));
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
			configProps.setProperty("validationQuery", "SELECT 1"); 
			/* configProps.setProperty("validationQuery", "SELECT 1 from dual"); */
			
			return BasicDataSourceFactory.createDataSource(configProps);
		}
		
		@Bean(name="dataSource_user", destroyMethod = "close")
		public DataSource dataSourceUser(@Value("#{dbProperties}") Properties dbProperties)
			throws Exception {
			Properties	configProps;
			
			configProps	= new Properties();
			configProps.setProperty("driverClassName", 				dbProperties.getProperty("udb.driver"));
			configProps.setProperty("url", 							dbProperties.getProperty("udb.url"));
			configProps.setProperty("username", 					dbProperties.getProperty("udb.user"));
			configProps.setProperty("password", 					dbProperties.getProperty("udb.passwd"));
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
			 configProps.setProperty("validationQuery", "SELECT 1"); 
			/* configProps.setProperty("validationQuery", "SELECT 1 from dual"); */
			
			return BasicDataSourceFactory.createDataSource(configProps);
		}		
		
		@Bean(name="dataSource_pog", destroyMethod = "close")
		public DataSource dataSourcePog(@Value("#{dbProperties}") Properties dbProperties)
			throws Exception {
			try {
				Properties	configProps;
				
				configProps	= new Properties();
				configProps.setProperty("driverClassName", 				dbProperties.getProperty("pogdb.driver"));
				configProps.setProperty("url", 							dbProperties.getProperty("pogdb.url"));
				configProps.setProperty("username", 					dbProperties.getProperty("pogdb.user"));
				configProps.setProperty("password", 					dbProperties.getProperty("pogdb.passwd"));
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
				 configProps.setProperty("validationQuery", "SELECT 1"); 
					/* configProps.setProperty("validationQuery", "SELECT 1 from dual"); */
				
				return BasicDataSourceFactory.createDataSource(configProps);
			} catch (NullPointerException e) {
				return new UndefinedDataSource("dataSource_pog");
			}
		}
				
//      LMREQ-5223(hsu) : 미접속 Data Srource 주석 처리		
//		@Bean(name="dataSource_ems", destroyMethod = "close")
//		public DataSource dataSourceEms(@Value("#{dbProperties}") Properties dbProperties)
//			throws Exception {
//			try {
//				Properties	configProps;
//				
//				configProps	= new Properties();
//				configProps.setProperty("driverClassName", 				dbProperties.getProperty("emsdb.driver"));
//				configProps.setProperty("url", 							dbProperties.getProperty("emsdb.url"));
//				configProps.setProperty("username", 					dbProperties.getProperty("emsdb.user"));
//				configProps.setProperty("password", 					dbProperties.getProperty("emsdb.passwd"));
//				
//				return BasicDataSourceFactory.createDataSource(configProps);
//			} catch (NullPointerException e) {
//				return new UndefinedDataSource("dataSource_ems");
//			}
//		}
		
		@Bean(name="dataSource_seq", destroyMethod = "close")
		public DataSource dataSourceSeq(@Value("#{dbProperties}") Properties dbProperties)
			throws Exception {
			try {
				Properties		configProps;
				
				configProps	= new Properties();
				configProps.setProperty("driverClassName", 				dbProperties.getProperty("seqdb.driver"));
				configProps.setProperty("url", 							dbProperties.getProperty("seqdb.url"));
				configProps.setProperty("username", 					dbProperties.getProperty("seqdb.user"));
				configProps.setProperty("password", 					dbProperties.getProperty("seqdb.passwd"));
				
				return BasicDataSourceFactory.createDataSource(configProps);
			} catch (NullPointerException e) {
				return new UndefinedDataSource("dataSource_seq");
			}
				
		}
		
//      LMREQ-5223(hsu) : 미접속 Data Srource 주석 처리		
//		@Bean(name="dataSource_sms", destroyMethod = "close")
//		public DataSource dataSourceSms(@Value("#{dbProperties}") Properties dbProperties)
//			throws Exception {
//			try {
//				Properties	configProps;
//				
//				configProps	= new Properties();
//				configProps.setProperty("driverClassName", 				dbProperties.getProperty("smsdb.driver"));
//				configProps.setProperty("url", 							dbProperties.getProperty("smsdb.url"));
//				configProps.setProperty("username", 					dbProperties.getProperty("smsdb.user"));
//				configProps.setProperty("password", 					dbProperties.getProperty("smsdb.passwd"));
//				
//				return BasicDataSourceFactory.createDataSource(configProps);
//			} catch (NullPointerException e) {
//				return new UndefinedDataSource("dataSource_sms");
//			}
//				
//		}
//		
//		@Bean(name="dataSource_newsms", destroyMethod = "close")
//		public DataSource dataSourceNewsms(@Value("#{dbProperties}") Properties dbProperties)
//			throws Exception {
//			try {
//				Properties	configProps;
//				
//				configProps	= new Properties();
//				configProps.setProperty("driverClassName", 				dbProperties.getProperty("newsmsdb.driver"));
//				configProps.setProperty("url", 							dbProperties.getProperty("newsmsdb.url"));
//				configProps.setProperty("username", 					dbProperties.getProperty("newsmsdb.user"));
//				configProps.setProperty("password", 					dbProperties.getProperty("newsmsdb.passwd"));
//				
//				return BasicDataSourceFactory.createDataSource(configProps);
//			} catch (NullPointerException e) {
//				return new UndefinedDataSource("dataSource_newems");
//			}
//		}		
		
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
		
		@Bean(name="propertiesService", destroyMethod = "destroy")
		public PropertyServiceImpl propertiesServiceOnServers() {
			PropertyServiceImpl	rv;
			Map<String, String>	propMap;
			
			propMap	= new HashMap<>();
			propMap.put("pageUnit", "10");
			propMap.put("pageSize", "10");
			propMap.put("Globals.fileStorePath", "/files/");
			propMap.put("Globals.otpIp", "10.52.11.112");
			propMap.put("Globals.otpPort", "1812");
			propMap.put("Globals.otpSecret", "10522237");
			propMap	= Collections.unmodifiableMap(propMap);
			
			rv	= new PropertyServiceImpl();
			rv.setProperties(propMap);
			
			return rv;
		}
		
		@Bean(name="dataSource")
		public JndiObjectFactoryBean dataSource()
			throws Exception {
			JndiObjectFactoryBean	rv;
			
			rv	= new JndiObjectFactoryBean();
			rv.setJndiName("java:comp/env/jdbc/ec");
			rv.setProxyInterface(DataSource.class);
			rv.setExpectedType(DataSource.class);
			rv.setLookupOnStartup(JNDI_LOOKUP_ON_START);
			return rv;
		}
		
		@Bean(name="dataSource_user")
		public JndiObjectFactoryBean dataSourceUser()
			throws Exception {
			JndiObjectFactoryBean	rv;
			
			rv	= new JndiObjectFactoryBean();
			rv.setJndiName("java:comp/env/jdbc/cip");
			rv.setProxyInterface(DataSource.class);
			rv.setExpectedType(DataSource.class);
			rv.setLookupOnStartup(JNDI_LOOKUP_ON_START);
			return rv;
		}	
		
		@Bean(name="dataSource_pog")
		public JndiObjectFactoryBean dataSourcePog()
			throws Exception {
			JndiObjectFactoryBean	rv;
			
			rv	= new JndiObjectFactoryBean();
			rv.setJndiName("java:comp/env/jdbc/pog");
			rv.setProxyInterface(DataSource.class);
			rv.setExpectedType(DataSource.class);
			rv.setLookupOnStartup(JNDI_LOOKUP_ON_START);
			return rv;
		}

//      LMREQ-5223(hsu) : 미접속 Data Srource 주석 처리
//		@Bean(name="dataSource_ems")
//		public JndiObjectFactoryBean dataSourceEms()
//			throws Exception {
//			JndiObjectFactoryBean	rv;
//			
//			rv	= new JndiObjectFactoryBean();
//			rv.setJndiName("java:comp/env/jdbc/ems");
//			rv.setProxyInterface(DataSource.class);
//			rv.setExpectedType(DataSource.class);
//			rv.setLookupOnStartup(JNDI_LOOKUP_ON_START);
//			return rv;
//		}
		
		@Bean(name="dataSource_seq")
		public JndiObjectFactoryBean dataSourceSeq()
			throws Exception {
			JndiObjectFactoryBean	rv;
			
			rv	= new JndiObjectFactoryBean();
			rv.setJndiName("java:comp/env/jdbc/log");
			rv.setProxyInterface(DataSource.class);
			rv.setExpectedType(DataSource.class);
			rv.setLookupOnStartup(JNDI_LOOKUP_ON_START);
			return rv;
		}	
		
//		LMREQ-5223(hsu) : 미접속 Data Srource 주석 처리
//		@Bean(name="dataSource_sms")
//		public JndiObjectFactoryBean dataSourceSms()
//			throws Exception {
//			JndiObjectFactoryBean	rv;
//			
//			rv	= new JndiObjectFactoryBean();
//			rv.setJndiName("java:comp/env/jdbc/sms");
//			rv.setProxyInterface(DataSource.class);
//			rv.setExpectedType(DataSource.class);
//			rv.setLookupOnStartup(JNDI_LOOKUP_ON_START);
//			return rv;
//		}	
//		
//		@Bean(name="dataSource_newsms")
//		public JndiObjectFactoryBean dataSourceNewsms()
//			throws Exception {
//			JndiObjectFactoryBean	rv;
//			
//			rv	= new JndiObjectFactoryBean();
//			rv.setJndiName("java:comp/env/jdbc/newsms");
//			rv.setProxyInterface(DataSource.class);
//			rv.setExpectedType(DataSource.class);
//			rv.setLookupOnStartup(JNDI_LOOKUP_ON_START);
//			return rv;
//		}
	}
}
