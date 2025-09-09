package com.lcnjf.util;

//import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Aes256UtilTest {
	private static final Logger logger = LoggerFactory.getLogger(Aes256UtilTest.class);

	// @4UP 삭제 test unit가 아니므로 주석 처리
	//@Test
	public void test() throws Exception {
		// 임의로 정했습니다. 특별한 이슈가 없는한 이대로 사용했으면 합니다 
		String key = "LCN!mepc#SYSTEM@"; //  key는 16자 
		Aes256Util aes256 = new Aes256Util(key);
		
		//String text = "암호화되지 않은 문자입니다.";
		//String encText = aes256.aesEncode(text);
		//String decText = aes256.aesDecode(encText);
		
		//logger.debug("암호화할 문자 : " + text);
		//logger.debug("암호화된 문자 : " + encText);
		//logger.debug("복호화된 문자 : " + decText);

		String url = "http://localhost:8083/common/epcVendorLogin.do?";
		String text = "CONO=1208111436";
		String encText = aes256.aesEncode(text);
		//String reqURI = request.getRequestURI();
		String reqURI = url + encText;
		logger.debug("보낸 URL : " + reqURI);
		String sConoArrStr = reqURI.substring(reqURI.indexOf("?"), reqURI.length());
		// 복호화처리, 2016.06.15
		String decText1 = aes256.aesDecode(encText);
		String decText2 = aes256.aesDecode(sConoArrStr);
		
		logger.debug("암호화할 문자 : " + text);
		logger.debug("암호화된 문자 : " + encText);
		logger.debug("복호화된 문자1 : " + decText1);
		logger.debug("복호화된 문자2 : " + decText2);
	
	
	}
}
