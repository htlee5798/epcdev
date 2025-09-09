package com.lottemart.epc.common.util;

import lcn.module.common.namo.MimeDecodeException;
import lcn.module.common.namo.NamoMime;
import lcn.module.framework.property.ConfigurationService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class EPCUtil {

	/**
	 * @description : 현재 날짜 취득
	 * @Method Name : getCurrentDate
	 * @param dateType : yyyy-MM-dd, yyyyMMddHHmmss
	 * @return
	 * @param
	 * @return 현재 날짜 취득
	 * @exception Exception
	 */
	public String getCurrentDate(String dateType) {

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(dateType);

		return sdf.format(cal.getTime());
	}

	/**
	 * @description : 현재 날짜에 더한 날짜 취득
	 * @Method Name : getCurrentAddDate
	 * @param dateType : yyyy-MM-dd, yyyyMMddHHmmss
	 * @param addDayNum : ex) 1 : 현재날짜에 하루를 더함
	 * @return 현재 날짜에 더한 날짜 취득
	 * @exception Exception
	 */
	public String getCurrentAddDate(String dateType, int addDayNum) {

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
		cal.add(Calendar.DATE, addDayNum);
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(dateType);

		return sdf.format(cal.getTime());
	}

	/**
	 * @description :  원하는 달 마지막일  취득
	 * @Method Name : getNextEndDate
	 * @param dateType : yyyy-MM-dd, yyyyMMddHHmmss
	 * @param addMonNum : ex) 1 : 현재 달에 한달을 더함
	 * @return 원하는 달 마지막일
	 * @exception Exception
	 */
	@SuppressWarnings("static-access")
	public String getNextEndDate(String dateType, int addMonNum) {

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
		cal.add(Calendar.MONTH, addMonNum);
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(dateType);
		cal.set(cal.DATE, cal.getActualMaximum(cal.DAY_OF_MONTH));

		return sdf.format(cal.getTime());
	}

	/**
	 * @description : int배열 정렬 기능
	 * @Method Name : getIntegerArrSort
	 * @param intArr
	 * @param sortMth asc:오름차순  desc:내림차순
	 * @return
	 * @exception Exception
	 */
	public int[] getIntArrSort(int[] intArr, String sortMth) {

		int tmpInt = 0;

		if (sortMth.equals("asc")) {
			for (int i = 0; i < intArr.length - 1; i++) {
				for (int j = 0; j < intArr.length - i - 1; j++) {
					if (intArr[j] > intArr[j + 1]) {
						tmpInt = intArr[j];
						intArr[j] = intArr[j + 1];
						intArr[j + 1] = tmpInt;
					}
				}
			}
		} else if (sortMth.equals("desc")) {
			for (int i = 0; i < intArr.length - 1; i++) {
				for (int j = 0; j < intArr.length - i - 1; j++) {
					if (intArr[j] < intArr[j + 1]) {
						tmpInt = intArr[j];
						intArr[j] = intArr[j + 1];
						intArr[j + 1] = tmpInt;
					}
				}
			}
		}

		return intArr;
	}

	/**
	 * @description : String배열 내 Max, Min 취득
	 * @Method Name : getMaxMinMap
	 * @param strArr
	 * @param mode : int, String
	 * @return
	 * @param strArr : 대상 배열, mode : String, int
	 * @return
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, String> getMaxMinMap(String[] strArr, String mode) {

		String tmpStr = null;
		Map<String, String> retMap = new HashMap();

		if ("int".equals(mode)) {
			for (int i = 0; i < strArr.length - 1; i++) {

				for (int j = 0; j < strArr.length - i - 1; j++) {

					if (Integer.parseInt(strArr[j]) > Integer.parseInt(strArr[j + 1])) {
						tmpStr = strArr[j];
						strArr[j] = strArr[j + 1];
						strArr[j + 1] = tmpStr;
					}
				}
			}

		} else if ("String".equals(mode)) {
			for (int i = 0; i < strArr.length - 1; i++) {

				for (int j = 0; j < strArr.length - i - 1; j++) {

					if (strArr[j].compareTo(strArr[j + 1]) > 0) {
						tmpStr = strArr[j];
						strArr[j] = strArr[j + 1];
						strArr[j + 1] = tmpStr;
					}
				}
			}

		}

		retMap.put("min", strArr[0]);
		retMap.put("max", strArr[strArr.length - 1]);

		return retMap;
	}

	/**
	 * @description : 배열 더하기
	 * @Method Name : getCombineArr
	 * @param strArr1
	 * @param strArr2
	 * @return
	 * @param
	 * @return combArr
	 * @exception Exception
	 */
	public String[] getCombineArr(String[] strArr1, String[] strArr2) {
		String combArr[] = new String[strArr1.length + strArr2.length];

		System.arraycopy(strArr1, 0, combArr, 0, strArr1.length);
		System.arraycopy(strArr2, 0, combArr, strArr1.length, strArr2.length);

		return combArr;
	}

	/**
	 * @description : utf-8변경
	 * @Method Name : getStrUtf8
	 * @param str
	 * @return
	 * @param
	 * @return
	 * @throws UnsupportedEncodingException
	 * @exception Exception
	 */
	public String getIso8859ToUtf8(String str) throws UnsupportedEncodingException {
		return new String(str.getBytes("ISO_8859-1"), "UTF-8");
	}

	/**
		문자열중 지정한 문자열을 찾아서 새로운 문자열로 바꾸는 함수
		origianl	대상 문자열
		oldstr		찾을 문자열
		newstr		바꿀 문자열
		return		바뀐 결과
	*/
	public String replace(String original, String oldstr, String newstr) {
		String convert = "";
		int pos = 0;
		int begin = 0;
		pos = original.indexOf(oldstr);

		if (pos == -1)
			return original;

		while (pos != -1) {
			convert = convert + original.substring(begin, pos) + newstr;
			begin = pos + oldstr.length();
			pos = original.indexOf(oldstr, begin);
		}
		convert = convert + original.substring(begin);

		return convert;
	}

	/**
		내용중 HTML 툭수기호인 문자를 HTML 특수기호 형식으로 변환합니다.
		htmlstr		바꿀 대상인 문자열
		return		바뀐 결과
		PHP의 htmlspecialchars와 유사한 결과를 반환합니다.
	*/
	public String convertHtmlchars(String htmlstr) {
		String convert = "";
		convert = replace(htmlstr, "<", "&lt;");
		convert = replace(convert, ">", "&gt;");
		convert = replace(convert, "\"", "&quot;");
		convert = replace(convert, "&nbsp;", "&amp;nbsp;");
		return convert;
	}

	/**
	 * Comma : 숫자 String
	 *
	 * @param s <code>java.lang.String</code>
	 * @return str <code>java.lang.String</code>
	 */
	public String CommaString(String s) {
		String str = "";
		String tmpS = s;
		tmpS = strNull(tmpS);
		if ("".equals(tmpS)) {
			str = tmpS;
		} else {
			if (isNumber(tmpS)) {
				int i = Integer.parseInt(tmpS);
				DecimalFormat fmt1 = new DecimalFormat("#,###,###,###,###,###");
				str = fmt1.format(i);
			} else {
				str = tmpS;
			}
		}
		return str;
	}

	/**
	 * strNull :  NULL값이면 빈문자열로 변경
	 *
	 * @param str <code>java.lang.String</code> : 문자열
	 * @return str <code>java.lang.String</code> : NULL이면 ""를 아니면 기존 문자열 반환
	 */
	public String strNull(String str) {
		String tmpStr = str;

		if (str == null) {
			tmpStr = "";
		} else {
			tmpStr = str.trim();
		}

		return tmpStr;
	}

	/**
	 * replaceStr : 스트링 값이 문자인지 숫자인지 판별
	 *
	 * @param str <code>java.lang.String</code> : 문자열 원본
	 */

	public boolean isNumber(String str) {
		boolean check = true;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				check = false;
				break;
			}
		}
		return check;
	}

	/**
	 * @description : 나모에디터 마임바디 처리
	 * @Method Name : getMimeMsgBody
	 * @param content
	 * @return
	 * @throws IOException
	 * @throws MimeDecodeException
	 * @exception Exception
	 */
	public String getMimeMsgBody(ConfigurationService config, String content, HttpServletRequest request) throws MimeDecodeException, IOException {

		String uploadPath = "";

		uploadPath = config.getString("board.image.mime.save.path") + "BOS/NAMO/" + System.currentTimeMillis();

		//String serverURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath() + "/common/namo/namoFileDownload.do";
		String serverURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/common/namo/namoFileDownload.do";

		NamoMime mime = new NamoMime();
		File dirPath = new File(uploadPath);

		if (!dirPath.exists()) {
			// 업로드할 디렉토리 생성
			dirPath.mkdirs();
		}

		mime.setSaveURL(serverURL + "?path=" + uploadPath + "&filename=");
		mime.setSavePath(uploadPath);
		mime.decode(content); // MIME 디코딩
		mime.saveFile(); // 포함한 파일 저장하기
		String retStr = replace(mime.getBodyContent(), "'", "\\'");

		return retStr;
	}

	/**
	 * @description : 나모에디터 마임바디 처리 (상품 상세정보 처리)
	 * @Method Name : getMimeMsgBody
	 * @param content
	 * @return
	 * @throws IOException
	 * @throws MimeDecodeException
	 * @exception Exception
	 */
	public String getMimeMsgBody(ConfigurationService config, String content, HttpServletRequest request, String uploadDir, String serverURLS) throws MimeDecodeException, IOException {

		String uploadPath = uploadDir;//config.getString("board.image.mime.save.path") + "BOS/NAMO/" + System.currentTimeMillis();

		String serverURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "" + request.getContextPath() + "/common/namo/namoFileDownload.do";
		NamoMime mime = new NamoMime();
		File dirPath = new File(uploadPath);

		if (!dirPath.exists()) {
			// 업로드할 디렉토리 생성
			dirPath.mkdirs();
		}

		mime.setSaveURL(serverURL + "?path=" + serverURLS + "&filename=");
		mime.setSavePath(uploadPath);
		mime.decode(content); // MIME 디코딩
		mime.saveFile(); // 포함한 파일 저장하기
		String retStr = replace(mime.getBodyContent(), "'", "\\'");

		return retStr;
	}

	/**
	 * @see getNomoContet
	 * @Locaton    : com.lottemart.bb.common.util
	 * @MethodName  : getNamoContet
	 * @author     : jhkim
	 * @Description : 나모 변환 내용 취득
	 * @param content
	 * @return
	 */
	public String getNamoContet(String content) {
		String tmpContent = content;
		if (tmpContent != null && tmpContent.indexOf("<META name=GENERATOR content=ActiveSquare></HEAD>") >= 0) {
			tmpContent = tmpContent.substring(tmpContent.indexOf("<BODY"));
			tmpContent = "<p" + tmpContent.substring("<BODY".length(), tmpContent.lastIndexOf("</BODY>")) + "</p>";
		}
		return tmpContent;
	}

	/******************** SRM 추가 ********************/

	/**
	 * SHA-256 암호화
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String EncryptSHA256(String str) throws Exception {
		String result_sha = "";
		MessageDigest sh = MessageDigest.getInstance("SHA-256"); //알고리즘 정의
		sh.update(str.getBytes());
		byte bytedata[] = sh.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytedata.length; i++) {
			sb.append(Integer.toString((bytedata[i] & 0xff) + 0x100, 16).substring(1));
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
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ' ')
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
				if (validNumber.charAt(j) == tempChar) {
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

	public static String phone(String src) {
		if (src == null) {
			return "";
		}
		if (src.length() == 8) {
			return src.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
		} else if (src.length() == 12) {
			return src.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
		}
		return src.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
	}

	/************************************************************/

}
