package com.lottemart.extend.sql;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.Alias;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import com.lottemart.extend.annontation.MyBatisDao;

/**
 * Spring ApplicationContext에 존재하는 모든 DataSource를 사용하는 MyBatis SqlSessionFactory를 동적으로 등록합니다.
 * @author pat
 */
public class MyBatisFactoryPostProcessor
	implements BeanFactoryPostProcessor {
	
	private Logger				logger	= LoggerFactory.getLogger(this.getClass());
	
	private ApplicationContext	appContext;
	
	private String				mybatisConfigLocation;
	
	private Resource			mybatisConfigResource;
	
	private String				mybatisMapperLocations;
	
	private Resource[]			mybatisMapperResources;
	
	/**
	 * 생성자
	 * @param springContext 적용할 Spring의 Context
	 * @param mybatisConfigLocation MyBatis 설정 xml의 resource path
	 * @param mybatisMapperLocations SQL Mapper xml의 resource path목록, 와일드 카드를 사용한 ant path형식 지정 가능하며 다수의 path를 지정할 때에는 구분자 콤마 사용
	 */
	public MyBatisFactoryPostProcessor(ApplicationContext springContext, String mybatisConfigLocation, String mybatisMapperLocations) {
		this.appContext				= springContext;
		this.mybatisConfigLocation	= mybatisConfigLocation;
		this.mybatisMapperLocations	= mybatisMapperLocations;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Map<String, DataSource>				dataSourceMap;
		List<Class<?>>						typeAliasList;
		String[]							mybatisMapperLocationArray;
		List<Resource>						mybatisMapperResourceList;
		PathMatchingResourcePatternResolver	resourcesResolver;
		
		if (StringUtils.isEmpty(this.mybatisConfigLocation) || StringUtils.isEmpty(this.mybatisMapperLocations)) {
			this.logger.info("there is no mybatis config. MyBatis initialize disabled");
			return;
		}
		
		resourcesResolver	= new PathMatchingResourcePatternResolver();
		
		// 타입 알리아스 대상 클래스 목록
		typeAliasList	= this.scanTypeAlias();
		// MyBatis 설정파일 위치
		this.mybatisConfigResource	= this.appContext.getResource(this.mybatisConfigLocation);
		// MyBatis mapper 설정 파일들 위치
		mybatisMapperLocationArray	= this.mybatisMapperLocations.split(",");
		mybatisMapperResourceList	= new ArrayList<>();
		for (String mybatisMapperLocation : mybatisMapperLocationArray) {
			mybatisMapperLocation	= mybatisMapperLocation.trim();
			try {
				Collections.addAll(mybatisMapperResourceList, resourcesResolver.getResources(mybatisMapperLocation));
			} catch (IOException e) {
				throw new BeanCreationException("MyBatis mapper files(" + mybatisMapperLocation + ") resolve fail.", e);
			}
		}
		this.mybatisMapperResources	= mybatisMapperResourceList.toArray(new Resource[mybatisMapperResourceList.size()]);
		
		// 존재하는 DataSource 모두 획득 하여 SqlSessionFactory 등록
		dataSourceMap	= beanFactory.getBeansOfType(DataSource.class, false, true);
		dataSourceMap.entrySet().forEach(dsEnt -> this.registNewSqlSessionFactory(beanFactory, dsEnt, typeAliasList));
		
		// 존재하는 모든 @MyBatisDao 인터페이스들을 SqlSessionFactory와 연결
		this.registNewMapperFactory(beanFactory);
		this.logger.info("MyBatis initialized");

	}
	
	/**
	 * MyBatisDao 들을 스캔하여 지정된 DataSource와 연결된 Mapper를 등록 
	 * @param beanFactory
	 */
	private void registNewMapperFactory(ConfigurableListableBeanFactory beanFactory) {
		Reflections		reflections;
		
		reflections	= new Reflections(ClasspathHelper.forPackage("com.lottemart"), new SubTypesScanner(false), new TypeAnnotationsScanner());
		reflections.getTypesAnnotatedWith(MyBatisDao.class).forEach(dao -> {
			MyBatisDao				daoAnnontation;
			String					dsName;
			SqlSessionFactory		ssf;
			MapperFactoryBean<?>	mfb;
			String					beanName;
			
			daoAnnontation	= dao.getDeclaredAnnotation(MyBatisDao.class);
			dsName			= daoAnnontation.value();
			if (StringUtils.isEmpty(dsName)) {
				return;
			}
			try {
				ssf				= beanFactory.getBean(dsName + ".SqlSessionFactory", SqlSessionFactory.class);
			} catch (BeansException ignore) {
				return;
			}
			if (ssf == null) {
				return;
			}
			mfb				= new MapperFactoryBean<>(dao);
			mfb.setSqlSessionFactory(ssf);
			beanName		= dsName + ".SqlMapper." + dao.getName();
			beanFactory.registerSingleton(beanName, mfb);
			this.logger.info("MyBatis MapperFactoryBean {} registed. ({})", beanName, mfb);
		});
	}
	
	/**
	 * 타입 알리아스 처리 대상 모델들을 스캔
	 * @return
	 */
	private List<Class<?>>	scanTypeAlias() {
		List<Class<?>>	rv;
		ClassLoader		classLoader;
		Reflections		reflections;
		
		rv			= new ArrayList<>();
		classLoader	= Thread.currentThread().getContextClassLoader();
		reflections	= new Reflections(ClasspathHelper.forPackage("com.lottemart"), new SubTypesScanner(false));
		reflections.getAllTypes().forEach(typeName -> {
			Class<?>	type;
			String		typeNameLowercase;
			
			try {
				type				= Class.forName(typeName, false, classLoader);
			} catch (Throwable ignore) {	// 모든 오류 무시
				return;
			}
			typeNameLowercase	= typeName.toLowerCase();
			if (typeNameLowercase.endsWith("model") || typeNameLowercase.endsWith("vo") || type.isAnnotationPresent(Alias.class)) {
				rv.add(type);
			}
		});
		return rv;
	}

	/**
	 * DataSource와 연결될 Mybatis SqlSessionFactory 생성
	 * @param beanFactory
	 * @param dsEnt
	 */
	private void registNewSqlSessionFactory(ConfigurableListableBeanFactory beanFactory, Entry<String, DataSource> dsEnt, List<Class<?>> typeAliasList)
		throws BeansException {
		String					name;
		DataSource				ds;
		SqlSessionFactoryBean	ssfb;
		XMLConfigBuilder		confBuilder;
		Configuration			mybatisConfig;
		TypeAliasRegistry		typeAliasRegistry;
		String					beanName;
		
		name	= dsEnt.getKey();
		ds		= dsEnt.getValue();
		ssfb	= new SqlSessionFactoryBean();
		ssfb.setDataSource(ds);
		
		try (InputStream is = this.mybatisConfigResource.getInputStream()) {
			confBuilder		= new XMLConfigBuilder(is);
		} catch (IOException e) {
			throw new BeanCreationException("MyBatis Config file read fail.", e);
		}
		mybatisConfig		= confBuilder.parse();
		typeAliasRegistry	= mybatisConfig.getTypeAliasRegistry();
		typeAliasList.forEach(type -> typeAliasRegistry.registerAlias(type));
		ssfb.setConfiguration(mybatisConfig);
		ssfb.setMapperLocations(this.mybatisMapperResources);
		beanName	= name + ".SqlSessionFactory";
		beanFactory.registerSingleton(beanName, ssfb);
		this.logger.info("MyBatis SqlSessionFactoryBean {} registed. ({})", beanName, ssfb);
	}

}
