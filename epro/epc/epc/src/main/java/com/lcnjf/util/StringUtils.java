package com.lcnjf.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Class Name : StringUtils.java
 * @Description : StringUtils Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @since  
 * @version 1.0
 * @see
 *
 *
 */

public class StringUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);
	
	public static String keys = "DADAPRODUCTION01038124206";
	/**
	 * 문자열을 받아서 null이면 대체할 문자열을 리턴
	 * @param String request
	 * @param String str
	 * @return rtnStr
	 * */
	public static String nvl(String request, String str) {
		if(request == null || request.trim().equals("")|| (request.trim().equals("null"))){
			return str;
		}else{
			return request;
		}
	}
	
	public static String makeSha256(String param){
		 
	    //StringBuffer md5 = new StringBuffer();
		StringBuffer sha256  = new StringBuffer();
		try 
		{
		    MessageDigest digest = MessageDigest.getInstance("SHA-256");
		    byte[] hash = digest.digest(param.getBytes(StandardCharsets.UTF_8));  //UTF-8 상수 사용
			//byte[] digest = MessageDigest.getInstance("MD5").digest(param.getBytes());
		    for (byte b : hash) {
	            sha256.append(String.format("%02x", b));
	        }

			//for (int i = 0; i < digest.length; i++) {
			   //md5.append(Integer.toString((digest[i] & 0xf0) >> 4, 16));
			   //md5.append(Integer.toString(digest[i] & 0x0f, 16));
			//}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return sha256.toString();
	}
	
	/**
	 * sun 라이브러리 직접 접근 제한 문제 변경(2016.04.07)
	 */
	public static String makeSHA256(String param){
		String str = "";
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] mdResult = md.digest(param.getBytes());
			str = new String(Base64.encodeBase64(mdResult));			
		 }catch (Exception e) {
			 logger.error(e.getMessage());
		}
		return str;
	}
	
	
	public static String getRandomKey(int length) {
		String strReturn = "";
	    char code[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'W', 'X', 'Y', 'Z', 
	    			   '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	    int cnt =0;
	    while(length > cnt){
	    	strReturn += String.valueOf(code[(int)(Math.random() * code.length)]).toString();
        	cnt++;
        }
	    
	    return strReturn;
	 }
	
	public static String getRandomNum(int length) {
		String strReturn = "";
	    char code[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	    int cnt =0;
	    while(length > cnt){
	    	strReturn += String.valueOf(code[(int)(Math.random() * code.length)]).toString();
        	cnt++;
        }
	    
	    return strReturn;
	 }
public static String getEncryptString(String planText) {
		
		
		int addNum = 100000;
        
        String enText = "";
        int uniArr[] = new int[planText.length()];
        int rndArr[] = new int[planText.length()];
        Random ran = new Random();
        int iRan;
        int iUnicode;
        int iKeyUnicode;
        int iKeyLen = keys.length();

        char cEnChar;

        for (int i = 0; i < planText.length(); i++) {
            iUnicode = (int)planText.charAt(i);
            iKeyUnicode = (int)keys.charAt(i % iKeyLen);
            iRan = ran.nextInt();
            iRan = ((iRan % 100) * 3);
            if(iRan < 1) {
                iRan = iRan * -1;
            }
            iRan += addNum;
            uniArr[i] = iUnicode + iKeyUnicode + iRan;
            rndArr[i] = iRan;
        }

        for (int i = 0; i < planText.length(); i++) {
            iUnicode = uniArr[i];
            iRan = rndArr[i];
            enText += Integer.toString(iUnicode);
            enText += Integer.toString(iRan);
        }
        return enText;
    }
	
	
	
	public static String getDecryptString(String enText) {      
		
	    int charLen = 6;
      
        
        String planText = "";
        String errText = "";
        int iKeyLen = keys.length();

        if ((enText.length() % charLen) !=0) {
            return errText;
        }

        int uniArr[] = new int[enText.length() / charLen];
        int iRan;

        char cPlanChar;

        for (int i = 0; i < uniArr.length; i++) {
            try {
                uniArr[i] = Integer.parseInt(enText.substring(i * charLen, (i * charLen) + charLen));
            } catch (Exception ex) {
            	logger.error(ex.getMessage());
                return errText;
            }
        }
        int j= 0;
        for (int i = 0; i < uniArr.length; i++) {
            cPlanChar = (char)((uniArr[i] - uniArr[++i]) - (int)keys.charAt(j++ % iKeyLen));
			planText += cPlanChar;
        }
        return planText;
    }
	
	
	
	
	public static String getWonAmt(String amt){
		String pmdAmt = amt;
		String tmpamt ="";
		pmdAmt = "000000000000" + pmdAmt;
		int j=0;
		for(int i=pmdAmt.length();i>0;i--) {
			 j++;
			 if (!pmdAmt.substring(i-1,i).equals("0")) {
			 if (j%4==2) tmpamt ="십"+tmpamt;
			 if (j%4==3) tmpamt ="백"+tmpamt;
			 if (j>1 && (j%4)==0) tmpamt ="천"+tmpamt;
			 }
			 if (j==5 && Integer.parseInt(pmdAmt.substring(pmdAmt.length()-8,pmdAmt.length()-4))>0) 		tmpamt ="만"+tmpamt;
			 if (j==9 && Integer.parseInt(pmdAmt.substring(pmdAmt.length()-12,pmdAmt.length()-8))>0) 	tmpamt ="억"+tmpamt;
			 if (j==13 && Integer.parseInt(pmdAmt.substring(pmdAmt.length()-16,pmdAmt.length()-12))>0) 	tmpamt ="조"+tmpamt;
			 if (pmdAmt.substring(i-1,i).equals("1")) tmpamt ="일"+tmpamt;
			 if (pmdAmt.substring(i-1,i).equals("2")) tmpamt ="이"+tmpamt;
			 if (pmdAmt.substring(i-1,i).equals("3")) tmpamt ="삼"+tmpamt;
			 if (pmdAmt.substring(i-1,i).equals("4")) tmpamt ="사"+tmpamt;
			 if (pmdAmt.substring(i-1,i).equals("5")) tmpamt ="오"+tmpamt;
			 if (pmdAmt.substring(i-1,i).equals("6")) tmpamt ="육"+tmpamt;
			 if (pmdAmt.substring(i-1,i).equals("7")) tmpamt ="칠"+tmpamt;
			 if (pmdAmt.substring(i-1,i).equals("8")) tmpamt ="팔"+tmpamt;
			 if (pmdAmt.substring(i-1,i).equals("9")) tmpamt ="구"+tmpamt;
		}
	 
	 	tmpamt = "금" + tmpamt + "원정";
	 	return tmpamt;
	 }
	

	/**
	 * COOKIE 생성
	 * @param HttpServletResponse
	 * @param String cookieName, String cookieValue
	 * @return
	 * @throws Exception
	 */
	public static void  AddCookie(HttpServletResponse response, String cookieName, String cookieValue)
	{
		String cookieDomain = ".etube.re.kr";
		
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setDomain(cookieDomain); 
		cookie.setPath("/"); 
		if(cookieValue == null) cookie.setMaxAge(0);
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);	

    }
	
	public static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }
	
	
}
	