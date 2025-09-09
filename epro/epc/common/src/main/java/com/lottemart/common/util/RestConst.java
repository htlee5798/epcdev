package com.lottemart.common.util;

public class RestConst {
    public static final String SAMPLE_SERVICE_JOB_ID = "send.do";

    public static final String SAMPLE_TEST_SERVER = "http://10.52.55.125:8101/test/";
    public static final String SAMPLE_SERVICE_USER_ID = "jeus";
    public static final String SAMPLE_SERVICE_USER_PWD = "lm@je-05";
    public static final int READ_TIME_OUT = 30000;
    public static final int PD_API_TIME_OUT = 100000;

    public static final String SFTP_DOWNLOAD_HOST = "124.243.43.26";
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
    
    /* 클레임 */
    public static final String CLAIM_SEND_POSSIBLE_URL = "/claim/sendPossible/check";
    public static final String CLAIM_CS_API_0004_URL = "/v1/openapi/customer/v1/getSellerInquiryList";
    public static final String CLAIM_CS_API_0005_URL = "/v1/openapi/customer/v1/getSellerInquiryDetail";
    
    /* 상품 */
    public static final String PD_API_0001 = "/products/registration";
    public static final String PD_API_0002 = "/products/modification";
    public static final String PD_API_0003 = "/products/store/stock";
    public static final String PD_API_0004 = "/products/store/price";
    public static final String PD_API_0005 = "/products/status";
    public static final String PD_API_0006 = "/products/list";
    public static final String PD_API_0007 = "/products/detail/simple";
    public static final String PD_API_0009 = "/products/plan/registration/";
    public static final String PD_API_0010 = "/products/plan/modification/";
    public static final String PD_API_0011 = "/products/plan/detail/";
    public static final String PD_API_0012 = "/products/qna/list";
    public static final String PD_API_0013 = "/products/qna/registration/";
    public static final String PD_API_0014 = "/products/qna/review";
    public static final String PD_API_0019 = "/products/collaborate/registration/";
    public static final String PD_API_0020 = "/products/collaborate/detail/";
    public static final String PD_API_0021 = "/products/collaborate/modification/";
    public static final String PD_API_0026 = "/products/store/management";
    public static final String PD_API_0027 = "/products/reserve/registration"; // 사전예약상품 -마트전용 예약 상품등록 (2024.02.27 추가)
    public static final String PD_API_0028 = "/products/epcitem/status";
    public static final String PD_API_0031 = "/products/promotionalTxt/registration";
    public static final String PD_API_0032 = "/products/promotionalTxt/info";
    public static final String PD_API_0033 = "/products/preSale/Registration";
    public static final String PD_API_0035 = "/products/preSale/Modification";
    public static final String PD_API_0036 = "/products/sellerNotice/registration";
    public static final String PD_API_0038 = "/products/sellerNotice/modification";
    public static final String PD_API_0039 = "/products/reserve/list";	// 사전예약상품 -마트 전용 상품 예약 조회 (2024.02.27 추가)
    public static final String PD_API_0040 = "/products/scatchanged/list";
    public static final String PD_API_0097 = "/products/list/sellQtyList";
    public static final String PD_API_0098 = "/products/list/categoryList";
    public static final String PD_API_0099 = "/products/list/brandList";
    
    /* 배송 */
    public static final String DL_API_0004 = "/v1/openapi/delivery/v1/DeliveryAppointmentInform";
	public static final String DL_API_0005 = "/v1/openapi/delivery/v1/DeliveryOrdersSearch";
	public static final String DL_API_0006 = "/v1/openapi/delivery/v1/ReplaceProductInform";
	public static final String DL_API_0007 = "/v1/openapi/delivery/v1/DeliveryProgressStateInform";
    public static final String DL_API_0008 = "/v1/openapi/delivery/v1/IfCompleteInform";
    public static final String DL_API_0009 = "/v1/openapi/delivery/v1/RetrievalExceptionInform";
    public static final String DL_API_0010 = "/v1/openapi/delivery/v1/DeliveryCoolingBagRetrieveInform";
    public static final String DL_API_0011 = "/v1/openapi/delivery/v1/DeliveryRegionInfoInterface";
    public static final String DL_API_0012 = "/v1/openapi/delivery/v1/DeliveryRegionZipcodeInterface";
	public static final String DL_API_0013 = "/v1/openapi/delivery/v1/CubeDeliverySequenceInterface";
	public static final String DL_API_0014 = "/v1/openapi/delivery/v1/CubeDeliverySequenceExceptionInterface";
	public static final String DL_API_0015 = "/v1/openapi/delivery/v1/DeliveryRegionZipcodeInterface";
	public static final String DL_API_0016 = "/v1/openapi/delivery/v1/DeliveryFailInform";
	public static final String SV_API_0001 = "/v1/openapi/saving/v1/api/OpenApiService/getUserBalance";
	public static final String OP_API_0011 = "/v1/openapi/order/v1/abusingCustomerSendSms";
	public static final String DL_API_0021 = "/v1/openapi/delivery/v1/longTermUntreated";
	
	//릴레이배송 RDL_API_0001
	public static final String RELAY_DELIVERY_API_ID_ORDERSEND = "RDL_API_0001";
	public static final String RDL_API_0001 = "/relay/delivery/sendDeliveryInfo";

	/* 배송(spp) */
	public static final String SPP_API_0001  = "/iib-smartpick/v1/Crspk5000";
	public static final String SPP_API_0002  = "/iib-smartpick/v1/PickStatus";
	public static final String SPP_API_0003  = "/iib-smartpick/v1/Crspk5300";
	public static final String SPP_API_0005  = "/iib-smartpick/v1/Crspk5700";
	public static final String SPP_API_0006  = "/iib-smartpick/v1/Crspk5510";
	public static final String SPP_API_0007  = "/iib-smartpick/v1/Crspk5520";
	public static final String SPP_API_0008  = "/iib-smartpick/v1/Crspk5530";
	public static final String SPP_API_0010  = "/iib-smartpick/v1/Crspk6000";
	public static final String SPP_API_0011  = "/iib-smartpick/v1/ReverseReturnStatus";
	public static final String SPP_API_0012  = "/iib-smartpick/v1/Crspk6100";
	
	/* TMS */
    public static final String TMS_API_ORDER_00001  = "/tmsapi/v1/order/insertOrder";

    public static final String ECOLIFE = "/products/certInfoCodeCheck";

}
