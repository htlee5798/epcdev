package com.lottemart.extend.util;

import java.io.Reader;
import java.sql.Clob;
import java.util.Properties;

/**
 * 잡다한 유틸
 * @author pat
 *
 */
public class MiscUtils {
	
	/**
	 * 주어진 properties에서 특정 이름으로 시작하는 속성들만 뽑아낸 서브 프로퍼티를 반환합니다.
	 * @param props 원본 properties
	 * @param propNamePrefix 대상 속성명 접두문자
	 * @param boolean removePrefixFlag 서브 프로퍼티를 구성할떼 prefix는 제거할지 여부. true이면 제거함
	 * @return
	 */
	public static Properties extractSubProperties(Properties props, String propNamePrefix, boolean removePrefixFlag) {
		Properties	rv;
		int			prefixLen;
		
		rv			= new Properties();
		prefixLen	= propNamePrefix.length();
		props.entrySet().forEach(propEnt -> {
			String	propName;
			
			propName	= (String)propEnt.getKey();
			if (propName.startsWith(propNamePrefix)) {
				rv.put(removePrefixFlag ? propName.substring(prefixLen) : propName, propEnt.getValue());
			}
		});
		
		return rv;
	}
	
	
	/**
	 * 주어진 객체가 Clob일수도 아닐수도 있는 상황에서 String로 변환합니다.
	 * @param org
	 * @return
	 */
	public static String getGuessClob2String(Object org) {
		
		if (org == null) {
			return null;
		}
		if (org instanceof Clob) {
			Clob 			clob;
			StringBuilder	sb;
			
			clob	= (Clob)org;
			sb		= new StringBuilder();
			try (Reader rd = clob.getCharacterStream()) {
				char[] 	buf;
				int		rsize;
				
				buf	= new char[1024];
				while ((rsize = rd.read(buf)) != -1) {
					sb.append(buf, 0, rsize); 
				}
			} catch (Exception e) {
				throw new RuntimeException("Clob read fail", e);
			}
			return sb.toString();
		} else {
			return org.toString();
		}
	}

}
