package com.lottemart.extend.annontation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.Target;

/**
 * ServerType을 조건검사 지시용 어노테이션
 * @author pat
 *
 */
@Target({TYPE, METHOD})
public @interface ServerType {
	
	/**
	 * 대상 서버타입을 지시
	 * @return
	 */
	public String[]	value();
}
