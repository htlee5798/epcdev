package com.lottemart.epc.edi.comm.model;

public final class Constants {

	private Constants() {}
	
	public static final String MD_FTP_SERVER = "pog-file.ilottemart.com";	
	public static final String MD_FTP_LOGIN_ID = "FTP_EDI";	
	public static final String MD_FTP_PASSWORD = "FTP_EDI";	
	
	public static final String ONLINE_OFFLINE_PRODUCT_CD   = "0";
	public static final String ONLINE_ONLY_PRODUCT_CD   = "1";
	public static final String STANDARD_PRODUCT_CD      = "1";
	public static final String FASHION_PRODUCT_CD       = "5";
	
	public static final String DEFAULT_TEAM_CD 		    = "00255";
	public static final String DEFAULT_DETAIL_CD 		= "all";
	public static final String NO_ORDER_GROUP_CD        = "ALL09";
	public static final String PROTECT_TAG_GROUP_CD     = "ALL14";
	public static final String FLOW_DATE_GROUP_CD     	= "PRD29";
	public static final String ORD_UNIT_GROUP_CD        = "CSA01";
	public static final String PROTECT_TAG_TYPE_GROUP_CD = "PRD01";
	public static final String SEASON_GROUP_CD 			= "PRD03";
	public static final String TAX_TYPE_GROUP_CD 		= "PRD06";
	public static final String PRODUCT_PATTERN_GROUP_CD = "PRD09";
	public static final String CENTER_GROUP_CD 			= "PRD12";
	public static final String COUNTRY_GROUP_CD         = "PRD16";
	public static final String DISPLAY_UNIT_GROUP_CD    = "PRD17";
	public static final String TEMPERATURE_DIVN_CD    	= "PRD30";
	public static final String TOTAL_CHECK_GROUP_CD     = "PRD31";
	
	public static final String TEMP_GROUP_CD    		= "TMP02";
	public static final String TEAM_PARAM_CD			= "1";
	
	public static final String COMPANY_TYPE_MANFAC 		  = "jejo";
	public static final String COMPANY_TYPE_DIRECT_IMPORT = "jicsuip";
	public static final String COMPANY_TYPE_WHOLESALE     = "chongpan";
	public static final String COMPANY_TYPE_VENDOR        = "broker";
	public static final String COMPANY_TYPE_PRODUCTPLACE  = "sanji";
	public static final String COMPANY_TYPE_LEASE         = "imdae";
			
	public static final String CONSULT_VENDOR_STATUS_NEW      = "0";
	public static final String CONSULT_VENDOR_STATUS_REASSIGN = "1";
	public static final String CONSULT_VENDOR_STATUS_REJECT   = "2";
	
	public static final String DEFAULT_TEAM_CD_CON 		    = "all";
	public static final String DEFAULT_DETAIL_CD_CON 		= "all";
	
	public static final String NON_SORTER_CD = "0";
	public static final String SORTER_CD = "1";
	
}
