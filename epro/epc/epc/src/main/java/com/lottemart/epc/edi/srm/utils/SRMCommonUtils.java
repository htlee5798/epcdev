package com.lottemart.epc.edi.srm.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.diquest.ir.util.common.StringUtil;
import com.lottemart.epc.edi.comm.model.PagingVO;

public class SRMCommonUtils {

	private static final Logger logger = LoggerFactory.getLogger(SRMCommonUtils.class);

	/**
	 * SHA-256 암호화
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String EncryptSHA256(String str) throws Exception{
		String result_sha = "";
		MessageDigest sh = MessageDigest.getInstance("SHA-256"); //알고리즘 정의
		sh.update(str.getBytes());
		byte bytedata[] = sh.digest();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < bytedata.length; i++) {
			sb.append(Integer.toString((bytedata[i]&0xff) + 0x100, 16).substring(1));
		}
		result_sha = sb.toString();

		return result_sha;
	}

	/**
	 * 공백문자체크
	 * @param str
	 * @return
	 */
	public static boolean isSpaceCheck(String str) {
	    for(int i = 0 ; i < str.length() ; i++) {
	        if(str.charAt(i) == ' ')
	        	return false;
	    }

	    return true;
	}

	/**
	 * 중복된 3자 이상의 문자 또는 숫자 사용불가
	 * @param str
	 * @return
	 */
	public static boolean isDuplicate3Character(String str) {
		int p = str.length();
		byte[] b = str.getBytes();
		for (int i = 0; i < ((p * 2) / 3); i++) {
			int b1 = b[i + 1];
			int b2 = b[i + 2];

			if ((b[i] == b1) && (b[i] == b2)) {
				return false;
			} else {
				continue;
			}
		}

		return true;
	}

	/**
	 * 허용된 문자열인지 체크
	 * @param str
	 * @return
	 */
	public static boolean isPermitPasswordCharCheck(String str) {
		String permitChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()";

		int isChk = 0;
		char tempChar;
		for (int i = 0; i < str.length(); i++) {
			tempChar = str.charAt(i);

			if (permitChar.indexOf(tempChar) < 0) {
				isChk++;
			}
		}

		// 정의된 이외의 문자열이 있을경우
		if (isChk > 0) {
			return false;
		}

		return true;
	}

	/**
	 * 문자, 숫자, 특수문자 혼용
	 * @param str
	 * @return
	 */
	public static boolean isPasswordCharCheck(String str) {
		boolean isNumber = false;
		boolean isAlphaBat = false;
		boolean isSpecialChar = false;

		String validNumber = "0123456789";
		String validAlphaBat = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String validSpecialChar = "!@#$%^&*()";

		int isChk = 0;
		char tempChar;
		for (int i = 0; i < str.length(); i++) {
			tempChar = str.charAt(i);

			// 숫자포함여부 체크
			for (int j = 0; j < validNumber.length(); j++) {
				if(validNumber.charAt(j) == tempChar){
					isNumber = true;
				}
			}

			// 알파벳 포함여부 체크
			for (int j = 0; j < validAlphaBat.length(); j++) {
				if (validAlphaBat.charAt(j) == tempChar) {
					isAlphaBat = true;
				}
			}

			// 특수문자 포함여부 체크
			for (int j = 0; j < validSpecialChar.length(); j++) {
				if (validSpecialChar.charAt(j) == tempChar) {
					isSpecialChar = true;
				}
			}
		}

		if (isNumber) isChk++;
		if (isAlphaBat) isChk++;
		if (isSpecialChar) isChk++;

		// 숫자,영문자 및 특수문자 3종 혼합하여 8자리 이상 또는 2종 이상 혼합시 10자리 이상(최소 8자리,최대15자리)
		if (isChk < 3) {
			if (isChk == 2) {
				if (str.length() < 10) {
					// 숫자, 영문자 및 특수문자 3종 혼합하여 8자리 이상\n또는 2종 이상 혼합시 10자리 이상\n최소 8자리, 최대15자리로 구성하세요.;
					return false;
				}
			} else {
				// 숫자, 영문자 및 특수문자 3종 혼합하여 8자리 이상\n또는 2종 이상 혼합시 10자리 이상\n최소 8자리, 최대15자리로 구성하세요.;
				return false;
			}
		}

		return true;
	}

	/**
	 * Object 객체를  Map으로 변환
	 * @param obj
	 * @return
	 */
	public static Map converObjectToMap(Object obj) {
		try {
			//Field[] fields = obj.getClass().getFields(); //private field는 나오지 않음.
			Field[] fields = obj.getClass().getDeclaredFields();
			Map resultMap = new HashMap();

			for (int i = 0; i <= fields.length-1; i++) {
				fields[i].setAccessible(true);
				//logger.debug(fields[i].getName() + "----->" + fields[i].get(obj));
				resultMap.put(fields[i].getName(), fields[i].get(obj));
			}

			return resultMap;
		} catch (IllegalArgumentException e) {
			logger.error("error message --> " + e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error("error message --> " + e.getMessage());
		}

		return null;
	}

	/**
	 * Map 객체를 Object로 변환(POJO 기반의 get/set 메소드가 있는 객체)
	 * @param map
	 * @param objClass
	 * @return
	 */
	public static Object convertMapToObject(Map map, Object objClass) {
		String keyAttribute = null;
		String setMethodString = "set";
		String methodString = null;

		Iterator itr = map.keySet().iterator();
		while (itr.hasNext()) {
			keyAttribute = (String) itr.next();
			methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);

			try {
				Method[] methods = objClass.getClass().getDeclaredMethods();
				for (int i = 0; i <= methods.length - 1; i++) {
					if (methodString.equals(methods[i].getName())) {
						//logger.debug(objClass + "-----> : " + map.get(keyAttribute));
						methods[i].invoke(objClass, map.get(keyAttribute));
					}
				}
			} catch (SecurityException e) {
				logger.error("error message --> " + e.getMessage());
			} catch (IllegalAccessException e) {
				logger.error("error message --> " + e.getMessage());
			} catch (IllegalArgumentException e) {
				logger.error("error message --> " + e.getMessage());
			} catch (InvocationTargetException e) {
				logger.error("error message --> " + e.getMessage());
			}
		}

		return objClass;
	}

	/**
	 * 특수문자를 HTML 특수문자 형태로 변환
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Object convertSpecialObjectToObject(Object obj) throws Exception {
		String keyAttribute = null;
		String setMethodString = "set";
		String methodString1 = null;
		String methodString2 = null;

		Object returnObject = obj.getClass().newInstance();

		try {
			if(obj instanceof PagingVO) {
				//상속(페이징)
				Field[] extendFields = PagingVO.class.getDeclaredFields();
				for (int i = 0; i <= extendFields.length-1; i++) {
					extendFields[i].setAccessible(true);
					//logger.debug(fields[i].getName() + "----->" + fields[i].get(obj));
					if(!extendFields[i].getName().equals("serialVersionUID")) {
						extendFields[i].set(returnObject, extendFields[i].get(obj));
					}
				}
			}

			Field[] fields = obj.getClass().getDeclaredFields();
			Map resultMap = new HashMap();
			String value = "";
			for (int i = 0; i <= fields.length-1; i++) {
				fields[i].setAccessible(true);
				//logger.debug(fields[i].getName() + "----->" + fields[i].get(obj));

				if (fields[i].get(obj) != null) {
					value = fields[i].get(obj).toString();
				} else {
					value = null;
				}

//				logger.debug(fields[i].getName() + "----->" + fields[i].getName());
//				logger.debug("fields[i].getType()" + "----->" + fields[i].getType());

				if(value != null){
					if(!fields[i].getName().equals("serialVersionUID")) {
						if(fields[i].getType().getName().equals("java.lang.String")) {
							resultMap.put(fields[i].getName(), changeSpecialString(value));
						} else if(fields[i].getType().getName().indexOf("java.util.List")== -1){
							//String 타입이 아닌것
							fields[i].set(returnObject, fields[i].get(obj));
						}
					}
				}

			}


			Iterator itr = resultMap.keySet().iterator();
			while (itr.hasNext()) {
				keyAttribute = (String) itr.next();
				methodString1 = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
				methodString2 = setMethodString + keyAttribute;

				try {
					Method[] methods = obj.getClass().getDeclaredMethods();
					for (int i = 0; i <= methods.length - 1; i++) {
						if (methodString1.equals(methods[i].getName()) || methodString2.equals(methods[i].getName())) {
							//logger.debug(obj + "-----> : " + map.get(keyAttribute));
//							logger.debug("paramType" + "-----> : " + methods[i].getParameterTypes()[0].toString());
							if(resultMap.get(keyAttribute) != null
									&& methods[i].getParameterTypes()[0].toString().equals("class java.lang.String")
									) {
								methods[i].invoke(returnObject, resultMap.get(keyAttribute));
							}
						}
					}


				} catch (SecurityException e) {
					logger.error("error message --> " + e.getMessage());
				} catch (IllegalAccessException e) {
					logger.error("error message --> " + e.getMessage());
				} catch (IllegalArgumentException e) {
					logger.error("error message --> " + e.getMessage());
				} catch (InvocationTargetException e) {
					logger.error("error message --> " + e.getMessage());
				}
			}

			return returnObject;
		} catch (IllegalArgumentException e) {
			logger.error("error message --> " + e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error("error message --> " + e.getMessage());
		}

		return returnObject;
	}

	/**
	 * HTML 특수문자 형태의 문자열을 특수문자로 변환
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Object convertNormalObjectToObject(Object obj) throws Exception {
		String keyAttribute = null;
		String setMethodString = "set";
		String methodString1 = null;
		String methodString2 = null;

		if(obj == null) return null;

		Object returnObject = obj.getClass().newInstance();

		try {
			if(obj instanceof PagingVO) {
				//상속(페이징)
				Field[] extendFields = PagingVO.class.getDeclaredFields();
				for (int i = 0; i <= extendFields.length-1; i++) {
					extendFields[i].setAccessible(true);
					//logger.debug(fields[i].getName() + "----->" + fields[i].get(obj));
					if(!extendFields[i].getName().equals("serialVersionUID")) {
						extendFields[i].set(returnObject, extendFields[i].get(obj));
					}
				}
			}

			//Field[] fields = obj.getClass().getFields(); //private field는 나오지 않음.
			Field[] fields = obj.getClass().getDeclaredFields();
			Map resultMap = new HashMap();

			String value = "";
			for (int i = 0; i <= fields.length-1; i++) {
				fields[i].setAccessible(true);
				//logger.debug(fields[i].getName() + "----->" + fields[i].get(obj));

				if (fields[i].get(obj) != null) {
					value = fields[i].get(obj).toString();
				} else {
					value = null;
				}

//				logger.debug(fields[i].getName() + "----->" + fields[i].getName());
//				logger.debug("fields[i].getType()" + "----->" + fields[i].getType().getName());

				if(value != null){
					if(!fields[i].getName().equals("serialVersionUID")) {
						if(fields[i].getType().getName().equals("java.lang.String")) {
							resultMap.put(fields[i].getName(), changeNormalString(value));
						} else if(fields[i].getType().getName().indexOf("java.util.List")== -1){
							//String 타입이 아닌것
							fields[i].set(returnObject, fields[i].get(obj));
						}
					}
				}

			}

			//return resultMap;

			Iterator itr = resultMap.keySet().iterator();
			while (itr.hasNext()) {
				keyAttribute = (String) itr.next();
				methodString1 = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
				methodString2 = setMethodString + keyAttribute;
				try {
					Method[] methods = obj.getClass().getDeclaredMethods();
					for (int i = 0; i <= methods.length - 1; i++) {
						if (methodString1.equals(methods[i].getName()) || methodString2.equals(methods[i].getName())) {
							//logger.debug(obj + "-----> : " + map.get(keyAttribute));
							if(resultMap.get(keyAttribute) != null
									&& methods[i].getParameterTypes()[0].toString().equals("class java.lang.String")) {
								methods[i].invoke(returnObject, resultMap.get(keyAttribute));
							}
						}
					}
				} catch (SecurityException e) {
					logger.error("error message --> " + e.getMessage());
				} catch (IllegalAccessException e) {
					logger.error("error message --> " + e.getMessage());
				} catch (IllegalArgumentException e) {
					logger.error("error message --> " + e.getMessage());
				} catch (InvocationTargetException e) {
					logger.error("error message --> " + e.getMessage());
				}
			}

			return returnObject;
		} catch (IllegalArgumentException e) {
			logger.error("error message --> " + e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error("error message --> " + e.getMessage());
		}

		return returnObject;
	}

	/**
	 * 문자열중 지정한 문자열을 찾아서 새로운 문자열로 바꾸는 함수
	 * @param original : 대상 문자열
	 * @param oldstr : 찾을 문자열
	 * @param newstr : 바꿀 문자열
	 * @return
	 */
	public static String replace(String original, String oldstr, String newstr) {
		String convert = "";
		int pos = 0;
		int begin = 0;
		pos = original.indexOf(oldstr);

		if (pos == -1) {
			return original;
		}

		while (pos != -1) {
			convert = convert + original.substring(begin, pos) + newstr;

			begin = pos + oldstr.length();
			pos = original.indexOf(oldstr, begin);
		}
		convert = convert + original.substring(begin);

		return convert;
	}

	/**
	 * 특수문자를 특수문자코드로 변환
	 *
	 * 1. Property 파일내 치환대상 문자를 &# + 코드 + && 로 변환
	 *
	 * 2. 다음 치환문자열을 같은 방식으로 치환...
	 * 3. 계속.
	 * 4. 모든 대상문자를 치환한 이후 &&를 ;로 치환. 입니다.
	 *
	 * 즉 ;이 아닌 &&로 치환토록 한 이후 매지막에 &&를 ;로 치환합니다.
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String changeSpecialString(String data) throws Exception {
		String tmpData = data;
		if(StringUtil.isEmpty(tmpData)) return tmpData;
		tmpData = replaceString(tmpData, "&"	, "&#38&&");
		tmpData = replaceString(tmpData, "$"	, "&#36&&");
		tmpData = replaceString(tmpData, "\""	, "&#34&&");
		tmpData = replaceString(tmpData, "%"	, "&#37&&");
		tmpData = replaceString(tmpData, "'"	, "&#39&&");
		tmpData = replaceString(tmpData, "("	, "&#40&&");
		tmpData = replaceString(tmpData, ")"	, "&#41&&");
		tmpData = replaceString(tmpData, "+"	, "&#43&&");
		tmpData = replaceString(tmpData, "/"	, "&#47&&");
		tmpData = replaceString(tmpData, ";"	, "&#59&&");
		tmpData = replaceString(tmpData, "<"	, "&#60&&");
		tmpData = replaceString(tmpData, "="	, "&#61&&");
		tmpData = replaceString(tmpData, ">"	, "&#62&&");
		tmpData = replaceString(tmpData, "?"	, "&#63&&");
		tmpData = replaceString(tmpData, "|"	, "&#124&&");
		tmpData = replaceString(tmpData, "--"	, "&#45&&&#45&&");
		tmpData = replaceString(tmpData, "@"	, "&#64&&");
		tmpData = replaceString(tmpData, "\\'"	, "&#92&&&#39&&");
		tmpData = replaceString(tmpData, "\\\""	, "&#92&&&#34&&");
		tmpData = replaceString(tmpData, "\r"	, "&#10&&");
		tmpData = replaceString(tmpData, "\n"	, "&#13&&");
		tmpData = replaceString(tmpData, "\\"	, "&#92&&");
		tmpData = replaceString(tmpData, "{"	, "&#123&&");
		tmpData = replaceString(tmpData, "}"	, "&#125&&");

		tmpData = replaceString(data, "&&"	, ";");

		/*data = replace(data, "<"	, "&lt;");
		data = replace(data, ">"	, "&gt;");
		data = replace(data, "&"	, "&amp;");*/

		return tmpData;
	}

	/**
	 * 특수문자코드를 특수문자로 변환
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String changeNormalString(String data) throws Exception {
		String tmpData = data;
		if(StringUtil.isEmpty(tmpData)) return tmpData;
		tmpData = replaceString(tmpData, "&#38;"		, "&"		);
		tmpData = replaceString(tmpData, "&#36;"		, "$"		);
		tmpData = replaceString(tmpData, "&#34;"		, "\""		);
		tmpData = replaceString(tmpData, "&#37;"		, "%"		);
		tmpData = replaceString(tmpData, "&#39;"		, "'"		);
		tmpData = replaceString(tmpData, "&#40;"		, "("		);
		tmpData = replaceString(tmpData, "&#41;"		, ")"		);
		tmpData = replaceString(tmpData, "&#43;"		, "+"		);
		tmpData = replaceString(tmpData, "&#47;"		, "/"		);
		tmpData = replaceString(tmpData, "&#59;"		, ";"		);
		tmpData = replaceString(tmpData, "&#60;"		, "<"		);
		tmpData = replaceString(tmpData, "&#61;"		, "="		);
		tmpData = replaceString(tmpData, "&#62;"		, ">"		);
		tmpData = replaceString(tmpData, "&#63;"		, "?"		);
		tmpData = replaceString(tmpData, "&#124;"		, "|"		);
		tmpData = replaceString(tmpData, "&#45;&#45;"	, "--"		);
		tmpData = replaceString(tmpData, "&#64;"		, "@"		);
		tmpData = replaceString(tmpData, "&#92;&#39;"	, "\\'"		);
		tmpData = replaceString(tmpData, "&#92;&#34;"	, "\\\""	);
		tmpData = replaceString(tmpData, "&#10;"		, "\r"		);
		tmpData = replaceString(tmpData, "&#13;"		, "\n"		);
		tmpData = replaceString(tmpData, "&#92;"		, "\\"		);
		tmpData = replaceString(tmpData, "&#123;"		, "{"		);
		tmpData = replaceString(tmpData, "&#125;"		, "}"		);

		/*data = data.replaceAll ("&lt;", "<");
		data = data.replaceAll ("&gt;", ">");
		data = data.replaceAll ("&amp;", "&");*/

		return tmpData;
	}

	/**
	 * 문자치환
	 * @param original
	 * @param source
	 * @param dest
	 * @return
	 * @throws Exception
	 */
	public static String replaceString(String original, String source, String dest) throws Exception {
		StringBuffer buf = new StringBuffer(original);
		String tmp = original;

		int index = 0;
		while (true) {
			index = tmp.indexOf(source, index);
			if(index == -1) {
				break;
			}
			tmp = new String(buf.replace(index, index + source.length(), dest));
			buf = new StringBuffer(tmp);
			index = index + dest.length();
		}

		return tmp;
	}

	/**
	 * 현재설정된 Locale 가져오기
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getLocale(HttpServletRequest request) throws Exception {
		String retLocale = "";
		HttpSession session = request.getSession();
		Locale locale = (Locale)session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);

		if (locale == null || locale.equals("")) {
			retLocale = Locale.KOREAN.toString();
		} else {
			retLocale = locale.toString();
		}

		return retLocale;
	}

	/**
	 * 이메일 전송을 위한 메세지 설정(HTML)
	 * @param String
	 * @return String
	 * @throws Exception
	 */
	public static String getMailContents(String contents, String lotteMartJuso, String sendMsg) throws Exception {
		return contents;
		/*
		StringBuffer mailContents = new StringBuffer();

		mailContents.append("	<html lang='ko'>																														"+"\n");

		mailContents.append("		<head>																																"+"\n");
		mailContents.append("		<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>																"+"\n");
		mailContents.append("		<meta http-equiv='X-UA-Compatible' content='IE=edge' />																				"+"\n");
		mailContents.append("		</head>																																"+"\n");

		mailContents.append("		<body style='font:13px malgun, sans-serif;'>																						"+"\n");
		mailContents.append("			<div style='width:700px; margin:0 auto; *zoom:1;'>																				"+"\n");
		mailContents.append("				<div>																														"+"\n");
		mailContents.append("					<div style='padding: 15px;'>																							"+"\n");
		mailContents.append("						<img src='http://partner.lottemart.com/images/epc/edi/consult/h1-logo-lottemart.gif'>								"+"\n");
		mailContents.append("					</div>																													"+"\n");
		mailContents.append("					<div style='border-bottom:1px solid #FF6464;'></div>																	"+"\n");
		mailContents.append("				</div>																														"+"\n");
		mailContents.append("				<p style='margin-top: 10px;'/>																								"+"\n");
		mailContents.append("				<div style='border:1px solid #CDCDCD; min-height: 200 px; padding: 10px; line-height: 20px;'>								"+"\n");
		mailContents.append(					contents																												 +"\n");
		mailContents.append("				</div>																														"+"\n");
		mailContents.append("				<p style='margin-top: 10px;'/>																								"+"\n");
		mailContents.append("				<div style='border:1px solid #CDCDCD; padding: 10px; line-height: 20px;text-align:center;'>									"+"\n");
		mailContents.append(					sendMsg																													 +"\n");
		mailContents.append("				</div>																														"+"\n");
		mailContents.append("				<p style='margin-top: 10px;'/>																								"+"\n");
		mailContents.append("				<div style='text-align: right;'>																							"+"\n");
		mailContents.append(					lotteMartJuso																											 +"\n");
		mailContents.append("				</div>																														"+"\n");
		mailContents.append("			</div>																															"+"\n");
		mailContents.append("		</body>																																"+"\n");

		mailContents.append("	</html>																																	"+"\n");

		return mailContents.toString();
		*/
	}


//	public Object changeQueryData(String gubun, Object vo) throws Exception {
//		Object object = vo.getClass().newInstance();
//
//		if(vo != null) {
//			Field[] fields = vo.getClass().getDeclaredFields();
//			Map resultMap = new HashMap();
//			for(int i=0; i<=fields.length-1;i++){
//				fields[i].setAccessible(true);
//				resultMap.put(fields[i].getName(), fields[i].get(vo));
//			}
//
//			String keyAttribute = null;
//			String setMethodString = "set";
//			String methodString = null;
//			Iterator itr = resultMap.keySet().iterator();
//			while(itr.hasNext()) {
//				keyAttribute = (String) itr.next();
//				methodString = setMethodString + keyAttribute.substring(0, 1).toUpperCase() + keyAttribute.substring(1);
//				Method[] methods = vo.getClass().getDeclaredMethods();
//				for (int i = 0; i <= methods.length - 1; i++) {
//					if (methodString.equals(methods[i].getName())) {
////						System.out.println("invoke : " + methodString);
//						if(resultMap.get(keyAttribute) != null
//								&& methods[i].getParameterTypes()[0].toString().indexOf("MultipartFile") == -1
//								&& methods[i].getParameterTypes()[0].toString().indexOf("ArrayList") == -1){
//							//data set
//							if(gubun.equals("get")) {
//								methods[i].invoke(object, changeNormalString(resultMap.get(keyAttribute).toString()));
//							} else if(gubun.equals("set")) {
//								methods[i].invoke(object, changeSpecialString(resultMap.get(keyAttribute).toString()));
//							}
//						}
//					}
//				}
//			}
//		} else {
//			return object;
//		}
//		return object;
//	}

	/**
	 * 숫자/영문대소문자/특수문자 연결
	 * @return
	 */
	public static char[] charString() {
		StringBuffer b = new StringBuffer();

		for (char ch = '0'; ch <= '9'; ++ch) {
			b.append(ch);
		}

		for (char ch = 'a'; ch <= 'z'; ++ch) {
			b.append(ch);
		}

		for (char ch = 'A'; ch <= 'Z'; ++ch) {
			b.append(ch);
		}

		b.append("!@#$%^&*()");

		return b.toString().toCharArray();
	}

	/**
	 * 문자열에서 Random하게 문자열 추출
	 * @param length
	 * @return
	 */
	public static String getRandomAuth(int length) {
		char[] chars = charString();

		if (length < 1) {
			throw new IllegalArgumentException("length < 1: " + length);
		}

		StringBuffer buffer = new StringBuffer();
		SecureRandom r = new SecureRandom();

		for (int i = 0; i < length; i++) {
			buffer.append(chars[r.nextInt(chars.length)]);
		}

		return buffer.toString();
	}

}
