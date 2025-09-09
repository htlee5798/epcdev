package com.lottemart.epc.common.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <b>
 * JAVA기반 프로젝트 개발시 필요한 기본 필수유틸들을 정의한 클래스입니다.<br>
 * 각 프로젝트에서는 해당 클래스를 상속받아 확장하여 사용해주세요.
 * </b>
 * @author 주재민(zodiack)
 * @version 1.0
 */

@Component
public class BaseUtils {
	private static ConfigurableApplicationContext configContext;
	private static ServletContext servletContext;
	private static final Logger logger = LoggerFactory.getLogger(BaseUtils.class);
	
	@Autowired
	ConfigurableApplicationContext cCtx;

	@Autowired
	ServletContext sCtx;
	
	@PostConstruct
	public void init() throws Exception {
		configContext = this.cCtx;
		servletContext = this.sCtx;
	}
	
	/**
	 * 입력된 클래스의 인스턴스를 생성하여 리턴<br>
	 * <i>어노테이션을 사용할 수 없는 일반 POJO객체에서 특정 클래스의 인스턴스를 갖고와야할 때 유용</i>
	 * 
	 * @param cls 클래스
	 * @return 클래스 인스턴스
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> cls) {
		try {
			return (T) configContext.getBean(cls);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return null;
		}
	}
	
	/**
	 * 입력한 객체의 빈값여부를 체크하여 참이면 기본 문자값으로 대체
	 * 
	 * @param obj 대상객체
	 * @param strDefault 기본 대체문자
	 * @return 대체 결과값
	 */
	public static String strCheck(Object obj, String strDefault) {
		if(obj == null || obj.toString().length()== 0){
			return strDefault;
		}else{
			return obj.toString();
		}
	}
	
	public static String strCheck(String str, String strDefault) {
		if(str == null || str.length() == 0){
			return strDefault;
		}else{
			return str;
		}
	}
	
	/**
	 * 입력한 객체의 빈값여부를 체크하여 참이면 기본 숫자값으로 대체
	 * 
	 * @param obj 대상객체
	 * @param intDefault 기본 대체숫자
	 * @return 대체 숫자값
	 */
	public static int intCheck(Object obj, int intDefault) {
		if(obj == null || obj.toString().length() == 0){
			return intDefault;
		}else{
	      return Integer.parseInt(obj.toString());
		}
	}
	
	public static int intCheck(String str, int intDefault) {
	    if(str == null || str.length() == 0){
	    	return intDefault;
	    }else{
	    	return Integer.parseInt(str);
	    }
	}
	
	/**
	 * UUID를 이용한 난수 발생
	 * 
	 * @return 난수값
	 */
	public static String generateRandomString() {
        return UUID.randomUUID().toString();
    }
	
	/**
	 * 입력한 객체를 특정 자리수 만큼 0으로 공백을 채워 리턴
	 * 
	 * @param obj 제로필 대상객체
	 * @param numCount 제로필 자릿수
	 * @return 제로필 결과값
	 */
	public static String getZeroFill(Object obj, int numCount){ 
		String sResult = "";
		int nObj = BaseUtils.intCheck(obj, 0);
		sResult = String.format("%0" + numCount + "d", nObj);
		
		return sResult;
	}
	
	/**
	 * 입력한 정수값을 Byte 배열로 변환
	 * 
	 * @param value 정수값
	 * @return Byte 배열값
	 */
	public static byte[] intToByteArray(int value) {
		byte[] byteArray = new byte[4];
		byteArray[0] = (byte)(value >> 24);
		byteArray[1] = (byte)(value >> 16);
		byteArray[2] = (byte)(value >> 8);
		byteArray[3] = (byte)(value);
		return byteArray;
	}
	
	/**
	 * 입력한 Byte 배열값을 16진수 문자로 변환
	 * 
	 * @param bytes Byte 배열값
	 * @return 16진수 결과값
	 */
	public static String byteArrayToHexString(byte[] bytes){ 
		
		StringBuilder sb = new StringBuilder(); 
		
		for(byte b : bytes){
			String str = String.format("%02X", b&0xff);;
			int num = Integer.parseInt(str, 16);
			sb.append(num);
		}
		
		return sb.toString(); 
	}
		
	/**
	 * 입력한 카멜형 문자값을 케밥형 문자값으로 변환
	 *  
	 * <pre>
	 * {@code
	 *	사용예)
	 *	BaseUtils.convertCamelToKebab("helloKorea");
	 *
	 *	결과값)
	 *	hello-korea
	 * }
	 * </pre>
	 * @param str 카멜형 문자값
	 * @return 케밥형 문자값
	 */
	public static String convertCamelToKebab(String str) {
		StringBuffer ret = new StringBuffer();
		//ret.append("data-");
		for (int i = 0; i < str.length(); i++) {
			char sz = str.charAt(i);
			if (sz >= 'A' && sz <= 'Z')
				ret.append(("-" + sz).toLowerCase());
			else
				ret.append(sz);
		}
		return ret.toString();
	}
	
	/**
	 * 입력한 카멜형 문자값을 언더스코프형 문자값으로 변환
	 * 
	 * <pre>
	 * {@code
	 *	사용예)
	 *	BaseUtils.convertCamelToUnder("helloKorea");
	 *
	 *	결과값)
	 *	hello_korea
	 * }
	 * </pre>
	 * @param str 카멜형 문자값
	 * @return 언더스코프형 문자값
	 */
	public static String convertCamelToUnder(String str) {
		StringBuffer ret = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {
			char sz = str.charAt(i);
			if (sz >= 'A' && sz <= 'Z')
				ret.append(("_" + sz).toLowerCase());
			else
				ret.append(sz);
		}
		return ret.toString();
	}
	
	/**
	 * 현재 날짜를 입력한 날짜 포맷으로 가공
	 * 
	 * <pre>
	 * {@code
	 *	사용예)
	 *	BaseUtils.toDate("yyyy-MM-dd HH:mm:ss");
	 *
	 *	결과값)
	 *	2023-02-15 15:03:08
	 * }
	 * </pre>
	 * @param pattern 날짜 포맷 (yyyy-MM-dd HH:mm:ss)
	 * @return 변환된 현재 날짜값
	 */
	public static String toDate(String pattern) {
		java.util.Date now = new java.util.Date();
		
		return new SimpleDateFormat(pattern).format(now);
	}
	
	/**
	 * 특정 날짜를 입력한 날짜 포맷으로 가공
	 * 
	 * <pre>
	 * {@code
	 *	사용예)
	 *	BaseUtils.toDate(new Date(), "yyyy-MM-dd");
	 *
	 *	결과값)
	 *	2023-02-15
	 * }
	 * </pre>
	 * @param date 특정 날짜
	 * @param pattern 날짜 형식(yyyy-MM-dd HH:mm:ss)
	 * @return 변환된 특정 날짜값
	 */
	public static String toDate(java.util.Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static String toDate(Object obj, String pattern) {
	    java.util.Date newnow = (java.util.Date) obj;
	    return new SimpleDateFormat(pattern).format(newnow);
	}
	
	/**
	 * 영문 요일 표기를 한글로 대체
	 * 
	 * @param yoil 영문요일값 (MON/TUE/WED/THU/FRI/SAT/SUN/HOL)
	 * @return 한글 요일값
	 */
	public static String convertDayToKor(String yoil) {
		String resultVal = yoil;
		
		resultVal = resultVal.replaceAll("MON", "월");
		resultVal = resultVal.replaceAll("TUE", "화");
		resultVal = resultVal.replaceAll("WED", "수");
		resultVal = resultVal.replaceAll("THU", "목");
		resultVal = resultVal.replaceAll("FRI", "금");
		resultVal = resultVal.replaceAll("SAT", "토");
		resultVal = resultVal.replaceAll("SUN", "일");
		resultVal = resultVal.replaceAll("HOL", "휴일");
		
		return resultVal;
	}
	
	/**
	 * 입력한 초값을 시분초 형태로 가공
	 * 
	 * <pre>
	 * {@code
	 *	사용예)
	 *	BaseUtils.timeConvert(2610, "HMS", ":");
	 *
	 *	결과값)
	 *	00:43:30
	 * }
	 * </pre>
	 * @param sec 입력값 (단위:초)
	 * @param type 유형 (HMS:시분초, MS:분초)
	 * @param divideStr 구분자
	 * @return 시분초 값
	 */
	public static String timeConvert(int sec, String type, String divideStr){
		String returnValue = "";
		String playHours = "";
		String playMinutes = "";
		String playSeconds = "";
		int playHour;
		int playMinute;
		int playSecond;
				 
		playHour = sec / 3600;
		playMinute = sec % 3600 / 60;
		playSecond = sec % 3600 % 60;		
		
		if(playHour < 10){
			playHours = "0" + playHour;
		}else{
			playHours = String.valueOf(playHour);
		}
		if(playMinute < 10){
			playMinutes = "0" + playMinute;
		}else{
			playMinutes = String.valueOf(playMinute);
		}
		if(playSecond < 10){
			playSeconds = "0" + playSecond;
		}else{
			playSeconds = String.valueOf(playSecond);
		}
		
		if(type.toUpperCase() == "HMS"){
			returnValue = playHours + divideStr + playMinutes + divideStr + playSeconds;
		}else if(type.toUpperCase() == "MS"){
			returnValue = playMinutes + divideStr + playSeconds;
		}else{
			returnValue = String.valueOf(sec);
		}
		
		return returnValue;
	}
	
	/**
	 * 특정 포맷형태로 입력한 기준시각에 유형별 가감값을 더한 시각을 문자형태로 출력
	 * 
	 * <pre>
	 * {@code
	 *	사용예)
	 *	BaseUtils.addDateTime(
	 * 		"yyyy-MM-dd HH:mm:ss", 
	 * 		"HOUR", 
	 * 		"2023-01-01 14:03:01", 
	 * 		12
	 *	);
	 *
	 *	결과값)
	 *	2023-01-02 02:03:01
	 * }
	 * </pre>
	 * @param dateFormat 날짜포맷 (yyyy-MM-dd HH:mm:ss)
	 * @param sType 유형 (MONTH:월 / DAY:일 / HOUR:시 / MIN:분) 
	 * @param sNowDateTime 기준일시
	 * @param gap 가감시간값
	 * @return 가감시각값
	 */
	public static String addDateTime(String dateFormat, String sType, String sNowDateTime, int gap) {
		String calcedDateTime = null;
		  
		SimpleDateFormat sdformat = new SimpleDateFormat(dateFormat); 
		Date nowday = sdformat.parse(sNowDateTime, new ParsePosition(0));
		 
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowday);
		  
		switch(sType.toUpperCase()) {
			case "MONTH": 
				cal.add(Calendar.MONTH, gap);
			break;
			case "DAY": 
				cal.add(Calendar.DATE, gap);
			break;
			case "HOUR": 
				cal.add(Calendar.HOUR, gap);
			break;
			case "MIN": 
				cal.add(Calendar.MINUTE, gap);
			break;
			default: 
				cal.add(Calendar.MINUTE, gap);
			break;
	    }
		  
		calcedDateTime = sdformat.format(cal.getTime());  

		return calcedDateTime;
	}
		
	public static String encodeURIComponent(String str) {
		String result = null;

		try{
			result = URLEncoder.encode(str, "UTF-8")
							 .replaceAll("\\+", "%20")
							 .replaceAll("\\%21", "!")
							 .replaceAll("\\%27", "'")
							 .replaceAll("\\%28", "(")
							 .replaceAll("\\%29", ")")
							 .replaceAll("\\%7E", "~");
		}catch(UnsupportedEncodingException e){
			result = str;
		}

		return result;
	}
	
	/**
	 * 웹서버의 리얼경로 리턴
	 * 
	 * @param dir 디렉토리 경로
	 * @return 웹서버 경로값
	 */
	public static String getRealPath(String dir) {
		return servletContext.getRealPath(dir);
	}
	
	/**
	 * 입력한 ArrayList형 객체를 구분자를 포함한 문자열로 변환
	 * 
	 * <pre>
	 * {@code
	 *	사용예)
	 *	ArrayList al = new ArrayList();
	 *	al.add("test1");
	 *	al.add("test2");
	 *	al.add("test3");
	 *	al.add("test4");
	 *	BaseUtils.convArryToStrs(al, ",");
	 *
	 *	결과값)
	 *	test1,test2,test3,test4
	 * }
	 * </pre>
	 * @param input 대상 객체
	 * @param div 구분자값
	 * @return 변환된 문자열
	 */
	@SuppressWarnings("unchecked")
	public static String convArryToStrs(Object input, String div) {
		StringBuffer sRtnVal = new StringBuffer();

		if (input != null && div != null) {
			if (input instanceof String) {
				sRtnVal.append((String) input);
				return sRtnVal.toString();
			} else {
				ArrayList<String> list = (ArrayList<String>) input;
				if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						sRtnVal.append(div);
						sRtnVal.append(list.get(i));
					}
					return sRtnVal.toString().substring(div.length(), sRtnVal.length());
				}
			}
		}

		return sRtnVal.toString();
	}
	
	/**
	 * 입력한 문자열(구분자 포함)을 List로 변환
	 *
	 * <pre>
	 * {@code
	 *	사용예)
	 *	BaseUtils.convStrsToArry("01,02,03,04", ",");
	 *
	 *	결과값)
	 *	[01,02,03,04]
	 * }
	 * </pre>
	 * @param input 구분자를 포함한 문자열
	 * @param div 구분자
	 * @return 변환된 List
	 */
	@SuppressWarnings("unchecked")
	public static List<String> convStrsToArry(Object input, String div) {
		List<String> lRtnVal = null;

		if (input != null) {
			String sInput = input.toString();
			String[] strArry = sInput.split(div);
			lRtnVal = new ArrayList<String>(Arrays.asList(strArry));
		}

		if (input != null && div != null) {
			if (input instanceof String) {
				lRtnVal = new ArrayList<String>();
				lRtnVal.add((String) input);
			} else {
				lRtnVal = (ArrayList<String>) input;
			}
		}

		return lRtnVal;
	}
}
