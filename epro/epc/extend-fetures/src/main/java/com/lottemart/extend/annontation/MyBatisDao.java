package com.lottemart.extend.annontation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MyBatisDao 임을 표시하는 어노테이션
 * @author pat
 *
 */
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyBatisDao {
	
	/**
	 * 대상 dataSource 이름 지정
	 * @return
	 */
	public String	value();
}
