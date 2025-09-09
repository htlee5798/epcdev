package com.lottemart.common.util;

public enum GmarketRestConst {
	
	//1. 상품API > 1.3.상품관리 API 
	PRODUCT_REG("GM_BZ_GOODS_REG", "/gmProd/item/v1/ProdMngHub", "상품등록API"),                            // 상품등록 (신규)
    PRODUCT_MOD_ALL("GM_BZ_GOODS_UPT", "/gmProd/item/v1/ProdMngHub", "상품수정API"),                        // 상품수정
	PRODUCT_MOD_DISPLAY("GM_BZ_GOODS_DSP", "/gmProd/item/v1/ProdMngHub", "전시여부API"),			          // 전시여부
	PRODUCT_MOD_STORE("GM_BZ_BRANCH_UPT", "/gmProd/item/v1/ProdMngHub", "상품재고/가격/점포 정보변경API"), // 상품재고/가격/점포 정보변경 
	//PRODUCT_REG_OPT("GM_BZ_ORDOPT_REG", "/gmProd/item/v1/goods/orderOptionsReg", "구주문옵션API"), // 구주문옵션
	PRODUCT_REG_OPT("GM_API_1_3_8_MOD", "/gmProd/item/v1/goods/orderOptionsReg", "구주문옵션API"), // 구주문옵션
	GROUP_REG("GM_BZ_GROUP_GOODS_REG", "/gmProd/item/v1/groupGoodsReg", "기획전형상품등록API"),           // 기획전형상품등록
	GROUP_MOD("GM_BZ_GROUP_GOODS_UPT", "/gmProd/item/v1/groupGoodsUpt", "기획전형상품수정API"),           // 기획전형상품수정
	GROUP_DEL("GM_BZ_GROUP_GOODS_DEL", "/gmProd/item/v1/groupGoodsDel", "기획전형삭제API")                // 기획전형삭제
	;                           
	
    private String apiCode;
    private String apiAddress;
    private String apiNm;

    private GmarketRestConst(String apiCode, String apiAddress, String apiNm) {
        this.apiCode = apiCode;
        this.apiAddress = apiAddress;
        this.apiNm = apiNm;
    }
    
    public String getApiCode() {
    	return apiCode;
    }
     
    public String getApiAddress() {
    	return apiAddress;
    }
    
    public String getApiNm() {
    	return apiNm;
    }
	
    public final static int READ_TIME_OUT = 30000;
    public final static int PD_API_TIME_OUT = 100000;

    public final static String REQUEST_METHOD_GET = "GET";
    public final static String REQUEST_METHOD_POST = "POST";
    public final static String REQUEST_METHOD_PUT = "PUT";
    
    public final static String CONTEXT_TYPE_JSON = "application/json";
    public final static String CONTEXT_TYPE_XFORM = "application/x-www-form-urlencoded";
    public final static String API_RESULT_CD_0000 = "0000";
    public final static String API_RESULT_CD_9999 = "9999";
    

}
