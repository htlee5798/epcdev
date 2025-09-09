/*
 * Utils.java
 *
 * Version 1.0
 *
 * Created on Mar 30, 2010
 *
 * Copyright(c) 2004 ~ 2010 ShinSeaGae I&C
 * All rights reserved.
 *
 */
package com.lottemart.epc.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.lang.RuntimeException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import com.lcnjf.util.StringUtils;


public class Utils {

	private static final String MONEY_PATTERN = "#,##0"; // 금액 포맷.
    private static final String CONTAINER_NAME = "container.name"; // 컨테이너명을 얻기위한 시스템변수명.

    /**
     * 생성자.
     */
    private Utils() {

    }

    /**
     * 패턴 형식으로 숫자를 포맷하여 문자열을 얻는다.<br>
     * java.text.NumberFormat을 사용하고 있으니 보다 상세한 내용은
     * <a href="http://java.sun.com/javase/6/docs/api/java/text/NumberFormat.html">java.text.NumberFormat.format(long)</a>을
     * 참조하세요.
     *
     * @param value
     * @param pattern
     * @return
     */
    public static String formatNumber(long value, String pattern) {
        NumberFormat numberFormat = new DecimalFormat(pattern);
        return numberFormat.format(value);
    }

    /**
     * 패턴 형식으로 숫자를 포맷하여 문자열을 얻는다.<br>
     * java.text.NumberFormat을 사용하고 있으니 보다 상세한 내용은
     * <a href="http://java.sun.com/javase/6/docs/api/java/text/NumberFormat.html">java.text.NumberFormat.format(double)</a>을
     * 참조하세요.
     *
     * @param value
     * @param pattern
     * @return
     */
    public static String formatNumber(double value, String pattern) {
        NumberFormat numberFormat = new DecimalFormat(pattern);
        return numberFormat.format(value);
    }

    /**
     * 패턴 형식으로 숫자를 포맷하여 문자열을 얻는다.<br>
     * java.text.NumberFormat을 사용하고 있으니 보다 상세한 내용은
     * <a href="http://java.sun.com/javase/6/docs/api/java/text/NumberFormat.html">java.text.NumberFormat.format(Object)</a>을
     * 참조하세요.
     *
     * @param value
     * @param pattern
     * @return
     */
    public static String formatNumber(Number value, String pattern) {
        NumberFormat numberFormat = new DecimalFormat(pattern);
        return numberFormat.format(value);
    }

    /**
     * 주어진 숫자를 금액 포맷하여 문자열을 얻는다.
     *
     * @param value
     * @return
     */
    public static String formatMoney(long value) {
    	return formatNumber(value, MONEY_PATTERN);
    }

    /**
     * 주어진 숫자를 금액 포맷하여 문자열을 얻는다.
     *
     * @param value
     * @return
     */
    public static String formatMoney(int value) {
    	return formatNumber(value, MONEY_PATTERN);
    }

    /**
     * 주어진 숫자를 금액 포맷하여 문자열을 얻는다.
     *
     * @param value
     * @return
     */
    public static String formatMoney(Number value) {
    	return formatNumber(value, MONEY_PATTERN);
    }

    /**
     * 패턴 형식의 문자열을 파싱하여 Number 객체를 얻는다.<br>
     * java.text.NumberFormat을 사용하고 있으니 보다 상세한 내용은
     * <a href="http://java.sun.com/javase/6/docs/api/java/text/NumberFormat.html">java.text.NumberFormat.parse(String)</a>을
     * 참조하세요.
     *
     * @param value
     * @param pattern
     * @return
     */
    public static Number parseNumber(String value, String pattern) {
        try {
            NumberFormat numberFormat = new DecimalFormat(pattern);
            return numberFormat.parse(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 패턴 형식으로 일시를 포맷하여 문자열을 얻는다.<br>
     * java.text.DateFormat을 사용하고 있으니 보다 상세한 내용은
     * <a href="http://java.sun.com/javase/6/docs/api/java/text/DateFormat.html">java.text.DateFormat.format(java.util.Date)</a>을
     * 참조하세요.
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.KOREA);
        return dateFormat.format(date);
    }


    /**
     * 패턴 형식으로 일시를 포맷하여 문자열을 얻는다.<br>
     * java.text.DateFormat을 사용하고 있으니 보다 상세한 내용은
     * <a href="http://java.sun.com/javase/6/docs/api/java/text/DateFormat.html">java.text.DateFormat.format(java.util.Date)</a>을
     * 참조하세요.
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String formatDate(long time, String pattern) {
        return formatDate(new Date(time), pattern);
    }

    /**
     * 문자열의 일시를 패턴 형식으로 파싱하여 java.util.Date 객체를 얻는다.<br>
     * java.text.DateFormat을 사용하고 있으니 보다 상세한 내용은
     * <a href="http://java.sun.com/javase/6/docs/api/java/text/DateFormat.html">java.text.DateFormat.parse(java.lang.String)</a>을
     * 참조하세요.
     *
     * @param value
     * @param pattern
     * @return
     */
    public static Date parseDate(String value, String pattern) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(pattern, Locale.KOREA);
            return dateFormat.parse(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 문자열의 일시를 패턴 형식으로 파싱하여 java.sql.Timestamp 객체를 얻는다.<br>
     *
     * @param value
     * @param pattern
     * @return
     */
    public static Timestamp parseTimestamp(String value, String pattern) {
    	Date date = parseDate(value, pattern);
    	return new Timestamp(date.getTime());
    }

    /**
     * 현재 시간의 java.sql.Timestamp를 얻는다.
     *
     * @return
     */
    public static Timestamp getCurrentTimestamp() {
    	return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 현재 시간의 java.util.Date 를 얻는다.
     *
     * @return
     */
    public static Date getCurrentDate() {
    	return new Date(System.currentTimeMillis());
    }

    /**
     * 계산할 일자값으로 date를 얻는다.
     * 일자값이 음수이면 과거 date를 얻고, 일자값이 양수이면 미래값을 얻는다.
     *
     * @param value 계산할 일자값.
     * @return
     */
    public static Date getDate(long value) {
        long dates = value * 24L * 60L * 60L * 1000L ;
        return new Date(System.currentTimeMillis() + dates);
    }

    /**
     * 호스트 명을 얻는다.
     * @return
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    /**
     * 컨테이너 명을 얻는다.</br>
     * 컨테이너명을 얻기 위해서는 시스템변수에 설정이 필요한다.
     * ex) -Dcontainer.name=nemall-dev_container1
     *
     * @return
     */
    public static String getContainerName() {
        return System.getProperty( CONTAINER_NAME );
    }

    /**
     * 이름 마스킹 처리
     * 2자리  : 김*, 3자리 : 홍*동, 4자리 : 제**명, 그외 : 김*******...
     * @param str
     * @return
     */
    public static String getMaskingName(String str) {
    	//----- 이름이 없을경우 "" return
    	if (str.equals("")) {
    		return "";

    	//-----	이름이 있을 경우
    	} else {

    		//----- 이름이 2자일 경우
    		if (str.trim().length() == 2) {
    			return str.trim().substring(0, 1) + "*";

    		//----- 이름이 3자일 경우
    		} else if (str.trim().length() == 3) {
    			return str.trim().substring(0, 1) + "*" + str.trim().substring(str.trim().length() - 1);

    		//----- 이름이 4자일 경우
    		} else if (str.trim().length() == 4) {
    			char[] c = new char[2];
            	Arrays.fill(c, '*');

            	return str.trim().substring(0, 1) + String.valueOf(c) + str.trim().substring(str.trim().length() - 1);

            //----- 이름이 2,3,4 자리가 아닐경우
    		} else {
    			char[] c = new char[str.trim().length()];
            	Arrays.fill(c, '*');
    			return str.trim().substring(0, 1) + String.valueOf(c);
    		}
    	}
    }
    
    /**
     * 이름 마스킹 처리2(하나 건너 마스킹 처리)
     * 2자리  : 김*, 3자리 : 홍*동, 4자리 : 제*갈*, 5자리 : 광*토*왕  (max 5자리)
     * @param str
     * @return
     */
    public static String getMaskingName2(String str) {
    	//----- 이름이 없을경우 "" return
    	if (str==null || str.trim().equals("")) {
    		return "";

    	//-----	이름이 있을 경우
    	} else {

    		//----- 이름이 2자일 경우
    		if (str.trim().length() == 2) {
    			return str.trim().substring(0, 1) + "*";

    		//----- 이름이 3자일 경우
    		} else if (str.trim().length() == 3) {
    			return str.trim().substring(0, 1) + "*" + str.trim().substring(str.trim().length() - 1);

    		//----- 이름이 4자일 경우
    		} else if (str.trim().length() == 4) {
    			// String.substring(start,end) //문자열  start위치 부터 end전까지 문자열 발췌
    			//제갈공명
    			return str.trim().substring(0, 1) + "*" + str.trim().substring(2,3)+ "*";

            //----- 이름이 5자일 경우
    		} else if (str.trim().length() == 5) {
    			// String.substring(start,end) //문자열  start위치 부터 end전까지 문자열 발췌
    			//광개토대왕
    			return str.trim().substring(0, 1) + "*" + str.trim().substring(2,3)+ "*" + str.trim().substring(str.trim().length() - 1);
    			
            //----- 이름이 2,3,4,5 자리가 아닐경우. 5자리 처리 
    		} else {
            	return str.trim().substring(0, 1) + "*" + str.trim().substring(2,3)+ "*" + str.trim().substring(4,5);
    		}
    	}
    }

    /**
     * 전화번호 중간번호만 마스킹 처리
     * @param str
     * @return
     */
    public static String getMaskingTelNo(String str) {
    	//-----전화번호가 없을경우 "" return
    	if (str.trim().length() <= 0) {
    		return "";
    	} else {

    		//----- 전화번호에 '-' 이 있으면 split을 활용해서 중간번호 마스킹 처리
    		if (str.indexOf("-") > 0) {
    			String[] arrStr	=	str.split("\\-");

            	char[] c = new char[arrStr[1].length()];
            	Arrays.fill(c, '*');

            	return arrStr[0] + '-' + String.valueOf(c) + "-" + arrStr[2];

            //----- 전화번호에 '-' 이 없으면 자리수로 체크해서 마스킹
    		} else {

    			//----- 전화번호가 9자리 일 경우[ex:02-111-1111]
    			if (str.trim().length() == 9) {

    				char[] c = new char[3];
					Arrays.fill(c, '*');
					return str.trim().substring(0, 2) + "-" + String.valueOf(c) + "-" + str.trim().substring(str.trim().length() - 4);

    			//----- 전화번호가 10자리 일 경우
    			} else if (str.trim().length() == 10) {

    				//-----지역번호가 서울일 경우[ex) 0212345678]
    				if (str.trim().substring(0, 2).equals("02")) {
    					char[] c = new char[4];
    					Arrays.fill(c, '*');
    					return str.trim().substring(0, 2) + "-" + String.valueOf(c) + "-" + str.trim().substring(str.trim().length() - 4);

    				//-----휴대폰번호이거나 서울지역번호가 아닐경우 [ex) 01112345678, 0541234567]
    				} else {
    					char[] c = new char[3];
    					Arrays.fill(c, '*');
    					return str.trim().substring(0, 3) + "-" + String.valueOf(c) + "-" + str.trim().substring(str.trim().length() - 4);
    				}

    			//----- 전화번호가 11자리 일 경우
    			} else if (str.trim().length() == 11) {
    				char[] c = new char[4];
					Arrays.fill(c, '*');
					return str.trim().substring(0, 3) + "-" + String.valueOf(c) + "-" + str.trim().substring(str.trim().length() - 4);

				//----- 전화번호가 9, 10, 11자리가 아닐경우
    			} else {
    				char[] c = new char[str.trim().length()];
					Arrays.fill(c, '*');
					return str.trim().substring(0, 3) + "-" + String.valueOf(c);
    			}
    		}
    	}
    }

    /**
     * 주소 마스킹 처리
     * 주소에 공백이 있을경우 공백으로 잘라서 배열로 생성후 마스킹 처리
     * 주소에 공백이 없을경우 주소 앞부분 일정부분만 보여주고 나머지 마스킹 처리
     * @param str
     * @return
     */
    public static String getMaskingAddr(String str) {
    	String rtnVal	=	"";
    	//----- 주소가 없을경우 "" return
    	if (str.trim().equals("")) {
    		return "";

    	//----- 주소가 있을경우
    	} else {
    		String[] arrStr = str.trim().split(" ");
    		char[] c = null;

    		if (arrStr.length > 0) {
    			for (int i = 0; i < arrStr.length; i++) {
    				if (i != 0) {
    					if (!StringUtils.trimToEmpty(arrStr[i]).equals("")) {
    						c = new char[arrStr[i].trim().length() - 1];
            				Arrays.fill(c, '*');
            				//System.out.println("String.valueof(c) ==" + String.valueOf(c) + arrStr[i].trim().substring(arrStr[i].trim().length() - 1));
            				rtnVal +=  String.valueOf(c) + arrStr[i].trim().substring(arrStr[i].trim().length() - 1);
    					}
    				}
    			}

    			if (!StringUtils.trimToEmpty(arrStr[0]).equals("")) {

    				if (arrStr[0].trim().length() > 5) {
    					c = new char[arrStr[0].trim().length() - 5];
    					Arrays.fill(c, '*');
    					arrStr[0] = arrStr[0].trim().substring(0, 5) + String.valueOf(c);
    				} else {
    					arrStr[0] = arrStr[0];
    				}

    			}

    			return arrStr[0] + rtnVal;

    		} else {
    			c = new char[str.trim().length() - 5];
    			Arrays.fill(c, '*');
    			return str.trim().substring(0, 5) + String.valueOf(c);
    		}

    	}
    }
    
    
    /**
     * 주소 마스킹 처리
     * 읍,면,동 이하 마스킹 처리
     * 서울시 금천구 가산디지털2로 179 롯데센터 : 서울 금천구 ******
     * @param str
     * @return
     */
    public static String getMaskingAddr2(String str) {
    	String rtnVal	=	" ";
    	//----- 주소가 없을경우 "" return
    	if (str==null || str.trim().equals("")) {
    		return "";

    	//----- 주소가 있을경우
    	} else {
    		String[] arrStr = str.trim().split(" ");
    		if (arrStr.length > 2) {
    			return arrStr[0] + rtnVal + arrStr[1] + rtnVal + "******";
    			
			//----- 공백이 하나인 경우 그대로 return >> 서울시 송파구	
    		} else {
    			return str.trim();
    		}

    	}
    }

    
    /**
	 * 날짜 포맷 (YYYY-MM-DD)
	 * Desc : dateFormat
	 * @Method Name : com.lottemart.epc.system.controller
	 * @Description : yyyymmdd 형의 날짜를 받아서 yyyy-mm-dd 형으로 변환하여 리턴
	 * @param String (format : yyyymmdd)
	 * @return String (format : yyyy-mm-dd)
	 */
    public static String dateFormat(String date)
	{
		if (StringUtils.trimToEmpty(date).equals("") || StringUtils.trimToEmpty(date).equals("00000000")) {
			return "";
		}
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(date.trim().substring(0, 4)).append("-")
		         .append(date.trim().substring(4, 6)).append("-")
		         .append(date.trim().substring(6, 8));

		return strBuffer.toString();
	}

}



