package com.lottemart.common.util;

public class XimarketRestConst
{
	public static final int READ_TIME_OUT = 30000;
	public static final int PD_API_TIME_OUT = 100000;

	public static final String REQUEST_METHOD_GET = "GET";
	public static final String REQUEST_METHOD_POST = "POST";

	public static final String CONTEXT_TYPE_JSON = "application/json";
	public static final String CONTEXT_TYPE_XFORM = "application/x-www-form-urlencoded";
	public static final String PATH_DIVISON_WORD = "/";

	public static final String RESPONSE_CODE_SUCCESS = "0000";

	public static final int SERVICE_READ_TIME = 50000;

	public static final String API_RESULT_CD = "errCd";
	public static final String API_RESULT_MSG = "errMsg";
	public static final String API_RESULT_CD_0000 = "0000";
	public static final String API_RESULT_CD_1001 = "1001";
	public static final String API_RESULT_CD_2001 = "2001";
	public static final String API_RESULT_CD_6001 = "6001";
	public static final String API_RESULT_CD_9999 = "9999";

	public static final String API_RESULT_MSG_0000 = "정상 처리 되었습니다.";
	public static final String API_RESULT_MSG_1001 = "인증키가 유효하지 않습니다.";
	public static final String API_RESULT_MSG_2001 = "파라미터가 부족 합니다.";
	public static final String API_RESULT_MSG_9999 = "시스템 오류가 발생 했습니다.";
	public static final String AFFILIATE_CD = "LM";        // 계열사 코드

	public static final String API_URL_EVT_UNLINK = "/ximarket/product/evtunlink";
	public static final String API_URL_EVT_UNLINK_DELETE = "/ximarket/product/evtunlink/delete";

	public static final String API_URL_PROD_DISPLINK = "/ximarket/product/dispynlinkyn";
	public static final String API_URL_PROD_DISPLAY_CHANGE = "/ximarket/product/displaychange";
	
}