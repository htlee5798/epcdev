package com.lottemart.epc.restApi;

public class RestConst {
    public static final String SAMPLE_SERVICE_JOB_ID = "send.do";

    public static final int READ_TIME_OUT = 10000;

    public static final String SFTP_TEST_FOLDER_PATH = "/nas_data/temp/megazone/hdh/0924";
    public static final int SFTP_DOWNLOAD_PORT = 22;

    public static final String REQUEST_METHOD_GET = "GET";
    public static final String REQUEST_METHOD_POST = "POST";

    public static final String CONTEXT_TYPE_JSON = "application/json";
    public static final String CONTEXT_TYPE_XFORM = "application/x-www-form-urlencoded";
    public static final String PATH_DIVISON_WORD = "/";

    public static final String RESPONSE_CODE_SUCCESS = "0000";

    public static final int SERVICE_READ_TIME = 50000;

    public static final String HEAD_OFFICE_CD = "17";           // 본점 코드
    public static final String AFFILIATE_TRADE_NO = "미정";      // 상위거래처번호
    public static final String KOREA_NATION_DIALLING_CODE = "82";

    public static final String API_RESULT_CD_0000 = "0000";
    public static final String API_RESULT_CD_1001 = "1001";
    public static final String API_RESULT_CD_2001 = "2001";
    public static final String API_RESULT_CD_9999 = "9999";

    public static final String API_RESULT_MSG_0000 = "정상 처리 되었습니다.";
    public static final String API_RESULT_MSG_1001 = "인증키가 유효하지 않습니다.";
    public static final String API_RESULT_MSG_2001 = "파라미터가 부족 합니다.";
    public static final String API_RESULT_MSG_9999 = "시스템 오류가 발생 했습니다.";
    public static final String AFFILIATE_CD = "LM";        // 계열사 코드

    public static final String SETTLE_TR_TYPE_ALL = "ALL";
    public static final String SETTLE_API_PRODUCT = "SettleProduct";
    public static final String SETTLE_API_DISCOUNT = "SettleDiscount";
    public static final String SETTLE_API_DELIVERY = "SettleDelivery";


    /* 거래처 */
    public static final String API_URL_TRADER_UPDATE = "/trader/modifyEnterprise";
    public static final String API_URL_TRADER_LOWRANK_TRANSFER = "/trader/transferLowrankTrader";
    public static final String API_URL_TRADER_LOWRANK_ADD = "/trader/addLowrankTrader";
    public static final String API_URL_TRADER_LOWRANK_MODIFY = "/trader/modifyLowrankTrader";
    public static final String API_URL_TRADER_ADDRESS_LIST = "/trader/findAddressInfoListByCondition";
    public static final String API_URL_TRADER_ADDRESS_ADD = "/trader/addAddressInfo";
    public static final String API_URL_TRADER_ADDRESS_MODIFY = "/trader/modifyAddressInfo";
    public static final String API_URL_TRADER_ADDRESS_REMOVE = "/trader/removeAddressInfo";
    
    /* 상품 */
    public static final String PD_API_0001 = "/products/registration";
    public static final String PD_API_0002 = "/products/modification";
    public static final String PD_API_0003 = "/products/store/stock";
    public static final String PD_API_0004 = "/products/store/price";
    public static final String PD_API_0005 = "/products/status";
    public static final String PD_API_0006 = "/products/list";
    public static final String PD_API_0009 = "/products/plan/registration/";
    public static final String PD_API_0010 = "/products/plan/modification/";
    public static final String PD_API_0011 = "/products/plan/detail/";
    public static final String PD_API_0012 = "/products/qna/list";
    public static final String PD_API_0013 = "/products/qna/registration/";
    public static final String PD_API_0014 = "/products/qna/review";
    public static final String PD_API_0019 = "/products/collaborate/registration/";
    public static final String PD_API_0021 = "/products/collaborate/modification/";
    public static final String PD_API_0020 = "/products/collaborate/detail/";
    public static final String PD_API_0026 = "/products/store/management";
    public static final String PD_API_0097 = "/products/list/sellQtyList";
    public static final String PD_API_0098 = "/products/list/categoryList";
    public static final String PD_API_0099 = "/products/list/brandList";
    
    /* 배송 */
	public static final String DL_API_0005 = "/v1/openapi/delivery/v1/DeliveryOrdersSearch";
	public static final String DL_API_0006 = "/v1/openapi/delivery/v1/ReplaceProductInform";
	public static final String DL_API_0007 = "/v1/openapi/delivery/v1/DeliveryProgressStateInform";
    public static final String DL_API_0008 = "/v1/openapi/delivery/v1/IfCompleteInform";
    public static final String DL_API_0009 = "/v1/openapi/delivery/v1/RetrievalExceptionInform";
    public static final String DL_API_0011 = "/v1/openapi/delivery/v1/DeliveryRegionInfoInterface";
    public static final String DL_API_0012 = "/v1/openapi/delivery/v1/DeliveryRegionZipcodeInterface";
	public static final String DL_API_0013 = "/v1/openapi/delivery/v1/CubeDeliverySequenceInterface";
	public static final String DL_API_0014 = "/v1/openapi/delivery/v1/CubeDeliverySequenceExceptionInterface";
	public static final String DL_API_0015 = "/v1/openapi/delivery/v1/DeliveryRegionZipcodeInterface";

	/* 배송(spp) */
	public static final String SPP_API_0001  = "/smartpick/o/Crspk5000";
	public static final String SPP_API_0002  = "/smartpick/o/PickStatus";
	public static final String SPP_API_0003  = "/smartpick/o/Crspk5300";
	public static final String SPP_API_0005  = "/smartpick/o/Crspk5700";
	public static final String SPP_API_0006  = "/smartpick/o/Crspk5510";
	public static final String SPP_API_0007  = "/smartpick/o/Crspk5520";
	public static final String SPP_API_0008  = "/smartpick/o/Crspk5530";
	public static final String SPP_API_0010  = "/smartpick/o/Crspk6000";
	public static final String SPP_API_0011  = "/smartpick/o/ReverseReturnStatus";
	public static final String SPP_API_0012  = "/smartpick/o/Crspk6100";
	public static final String SPP_API_0015  = "/smartpick/o/Strpk4250";
	public static final String SPP_API_0016  = "/smartpick/o/Strpk4420";
	public static final String SPP_API_0019  = "/smartpick/o/Strpk4210";
	
}
