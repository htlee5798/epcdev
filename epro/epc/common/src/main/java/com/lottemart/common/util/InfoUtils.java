package com.lottemart.common.util;

import lcn.module.common.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfoUtils {
	private static final Logger logger = LoggerFactory.getLogger(InfoUtils.class);
	/*
	 * param : 이름
	 * 짝수번째 글자를 *로 치환
	 * ex) 김수한무 -> 김*한*
	 * */
	public static String getName(String value) {
		String returnName = value;
		try {
			if (StringUtils.isNotEmpty(returnName)) {
				int len = returnName.length();
				if (len < 2) {
					returnName = "*";
				} else {
					StringBuilder stringBuilder = new StringBuilder();
					for (int i = 0; i < len; i++) {
						String checkValue = returnName.substring( i, i + 1 );
						if (1 == (i % 2)) {
							checkValue = "*";
						}
						stringBuilder.append( checkValue );
					}
					returnName = stringBuilder.toString();
					stringBuilder.setLength(0);
				}

			}
		} catch (Exception e) {
			returnName = "";
			logger.debug(e.getMessage(), e);
		}
		return returnName;
	}

	/*
	 * param : 사용자 ID
	 * 끝의 3자리 *로 치환
	 * ex)  l -> *
	 * 		lo -> l*
	 * 		lot -> l**
	 * 		lottemart -> lottem***
	 * */
	public static String getId(String value) {
		String tmpValue = value;
		try {
			
			if (tmpValue == null || "".equals(tmpValue)) {
				return "";
			}
			
			int len = tmpValue.length();
			if (len < 4) {
				if (len == 1) {
					tmpValue = "*";
				} else if (len == 2) {
					tmpValue = tmpValue.substring(0, 1) +  "*";
				} else {
					tmpValue = tmpValue.substring(0, 1) +  "**";
				}
			} else {
				String star_cnt = "";
				for(int a=0; len-3 > a ; a++){
					star_cnt = star_cnt+"*";
				}
				tmpValue = tmpValue.substring(0, 3) +  star_cnt;
			}			
			  
			return tmpValue;
			
		} catch (Exception e) {
			return "";
		}
		/*try {
			
			if (value == null || "".equals(value)) {
				return "";
			}
			
			int len = value.length();
			if (len < 4) {
				if (len == 1) {
					value = "*";
				} else if (len == 2) {
					value = value.substring(0, 1) +  "*";
				} else {
					value = value.substring(0, 1) +  "**";
				}
			} else {
				value = value.substring(0, len-3) +  "***";
			}			
			
			return value;
			
		} catch (Exception e) {
			return "";
		}*/
	}
	
	/*
	 * param : 이메일주소
	 * 이메일 아이디 끝의 3자리 *로 치환
	 * ex) admin@lottemart.com -> ad***@lottemart.com
	 *     admin -> ""
	 * */
	public static String getEmail(String value) {
		String tmpValue = value;
		try {
			
			if (tmpValue == null || "".equals(tmpValue)) {
				return "";
			}
			
			String ids[] = tmpValue.split("@");
			String id	= ids[0];
			
			if (ids.length < 2) {
				return "";
			}
			
			id = getId(id);
			tmpValue = id + "@" + ids[1]; 
			
			return tmpValue;
			
		} catch (Exception e) {
			return "";
		}
	}
	
	/*
	 * param : 전화번호(휴대폰번호)
	 * 끝의 4자리 ****로 치환
	 * ex) 01012345678 -> 0101234****
	 *     010-1234-5678 -> 010-1234-5648
	 * */
	public static String getPhone(String value) {
		String tmpValue = value;
		try {
			
			if (tmpValue == null || "".equals(tmpValue)) {
				return "";
			}

			int len = tmpValue.length();
			if (len < 4) {
				return "";
			}
			
			tmpValue = tmpValue.substring(0, len-4) + "****";
			
			return tmpValue;
			
		} catch (Exception e) {
			return "";
		}
	}

	/*
	 * param : 전화번호(휴대폰번호)
	 * 가운데자리 *로 치환
	 * ex) 010 12345678 -> 010 ****5678
	 * */
	public static String getPhone2(String value) {
		String tmpValue = value;
		try {
			
			if (tmpValue == null || "".equals(tmpValue)) {
				return "";
			}

			int len = tmpValue.length();
			if (len < 4) {
				return "";
			}
			
			tmpValue = tmpValue.substring(0, 3) + "****" + value.substring(len-4, len);
			
			return tmpValue;
			
		} catch (Exception e) {
			return "";
		}
	}

	
	/*
	 * param : 주소
	 * addr1, 2 구분없이 ' ' 으로 구분하여 마지막 문자열 전체를 ****로 치환
	 * ex) 서울특별시 송파구 신천동 7-18  롯데캐슬골드 -> 서울특별시 송파구 신천동 7-18 *****
	 * */
	public static String getAddr(String value) {
		try {
			if (value == null || "".equals(value)) {
				return "";
			}
			
			String addrs[] = value.split(" ");
			int len = addrs.length;
			
			if (len < 2) {
				return "*****";
			}
			
			String returnValue = "";
			String blank = "";
			for (int i=0; i<len-1; i++) {
				if (i > 0) {
					blank = " ";
				}
				returnValue = returnValue + blank + addrs[i];				
			}
			
			return returnValue + "*****";
			
		} catch (Exception e) {
			return "";
		}
	}
	
	/*
	 * param : 카드번호
	 * 끝의 4자리 ****로 치환
	 * ex) 1234567812345678 -> 123456781234****
	 *     1234-5678-1234-5678 -> 1234-5678-1234-****
	 * */
	public static String getCard(String value) {
		String tmpValue = value;
		try {
			
			if (tmpValue == null || "".equals(tmpValue)) {
				return "";
			}

			int len = tmpValue.length();
			if (len < 4) {
				return "";
			}
			
			tmpValue = tmpValue.substring(0, len-4) + "****";
			
			return tmpValue;
			
		} catch (Exception e) {
			return "";
		}
	}
	
	static int MASKING_CNT_ACCOUNT_NO = 4;
	
	public static String getAccountNo(String value) {
		String tmpValue = value;
		try {
			
			if (tmpValue == null || "".equals(tmpValue)) {
				return "";
			}

			int len = tmpValue.length();
			if (len < MASKING_CNT_ACCOUNT_NO) {
				return "";
			}
			
			//앞3자리
			tmpValue = tmpValue.substring(0,MASKING_CNT_ACCOUNT_NO);
			
			int i;
			for(i=MASKING_CNT_ACCOUNT_NO; i<len; i ++){
				tmpValue = StringUtil.concat(tmpValue, "*");
			}
			
			logger.debug(tmpValue);
			
			return tmpValue;
			
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getTranMsg(String value) {
		try {
			
			if (value == null || "".equals(value)) {
				return "";
			}

			if(value.contains("고객님")){
				int i = value.indexOf("]");   // 6
				int j = value.indexOf("고객님");   // 10
				int len = j-i;
				if(len > 0){
					String star_cnt = "";
					for(int a=0 ; a <len-1 ; a++){
						star_cnt = star_cnt+"*";
					}
					String custNm = value.substring(i+1, j);
					
					return value.replace(custNm,  star_cnt );
				}
			}
			return value;
			
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String[] getSplitedAccountNo(String value) {
		
		String[] accountNo = {"", ""};
		
		try {
		
			if (value == null || "".equals(value)) {
				return accountNo;
			}
	
			int len = value.length();
			if (len < MASKING_CNT_ACCOUNT_NO) {
				return accountNo;
			}
			
			accountNo[0] = value.substring(0,MASKING_CNT_ACCOUNT_NO);
			accountNo[1] = value.substring(MASKING_CNT_ACCOUNT_NO);
			
			logger.debug(accountNo[0] + ":" + accountNo[1]);
			
		}catch(Exception e){
			String[] ex = {"", ""};
			return ex;
		}
		
		return accountNo;
	}
	
	/*
	 * param : 통관번호
	 * 통관번호 앞자리 4자리 제외 *로 치환
	 * ex) P000000000001 -> P000*********
	 *
	 */
	public static String getCursClrnNum(String value) {
		String tmpValue = value;
		try {
			
			if (tmpValue == null || "".equals(tmpValue)) {
				return "";
			}
			
			int len = tmpValue.length();
			if (len > 4) {
				String star_cnt = "";
				for(int a=0; len-4 > a ; a++){
					star_cnt = star_cnt+"*";
				}
				tmpValue = tmpValue.substring(0, 4) +  star_cnt;
			}		
			
			return tmpValue;
			
		} catch (Exception e) {
			return "";
		}
	}
	
	
	
}
