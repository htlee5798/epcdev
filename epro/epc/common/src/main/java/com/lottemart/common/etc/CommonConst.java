package com.lottemart.common.etc;

//import com.lottemart.common.login.etc.Const;

public interface CommonConst {
	/**
	 * 공통코드 관리자권한그룹 시스템구분코드 01:BOS, 02:CC, 03:PP, 04:B2B, 05:OC, 06:OLAP, 07:MBOS
	 */
	public static final String MAJOR_CD_SYSTEM_TYPE = "SM104";
	public static final String SYSTEM_TYPE_BO = "01"; // BOS
	public static final String SYSTEM_TYPE_CC = "02"; // C.C
	public static final String SYSTEM_TYPE_PP = "03"; // P.P
	public static final String SYSTEM_TYPE_B2B = "04"; // B2B
	public static final String SYSTEM_TYPE_OC = "05"; // OC
	public static final String SYSTEM_TYPE_OLAP = "06"; // OLAP
	public static final String SYSTEM_TYPE_MBOS = "07"; // MBOS

	/**
	 * 공통코드 관리메뉴 등록조회구분코드 00001:등록/저장, 00002:조회, 00003:승인  -- 승인권한 추가(2016.02)
	 */
	public static final String MAJOR_CD_REG_VIEW_DIVN_CD = "SM103";
	public static final String REG_VIEW_DIVN_CD_REG = "00001"; // 등록/저장
	public static final String REG_VIEW_DIVN_CD_VIEW = "00002"; // 조회
	public static final String REG_VIEW_DIVN_CD_APRV = "00003"; // 승인
	
	/**
	 *  공통코드 전시삭제요청사유 [01:시즌아웃, 02:발주중단재고소진, 03:기타]
	 */
	public static final String MAJOR_CD_SM102 = "SM102";
	
	/**
	 *  공통코드 관리자유형 [01:점포, 02:온라인고객센터, 03:매장고객센터, 04:몰영업담당, 05:협력업체]
	 */
	public static final String MAJOR_CD_SM032 = "SM032";
	
	/**
	 *  공통코드 사원,직위코드 [01:사장, 02:부사장, 03:상무, 04:전무, 05:이사, 06:부장, 07:차장, 08:과장, 09:계장, 10:사원, 11:기타]
	 */
	public static final String MAJOR_CD_EMP06 = "EMP06";

	
	//=======================================================================
	// 현재 서버 구분
	//public static final String THIS_SYSTEM_TYPE = Const.SYSTEM_TYPE_BO; // 현재서버는 BOS
	public static final String THIS_SYSTEM_TYPE = SYSTEM_TYPE_BO; // 현재서버는 BOS
		
	/**
	 * 메뉴루트
	 */
	public static final String ROOT_MENU_ID_BO = "root";
	public static final String ROOT_MENU_ID_CC = "root-CC";
	public static final String ROOT_MENU_ID_PP = "root-PP";
	public static final String ROOT_MENU_ID_SC = "root-SC";
	//=======================================================================
	
	// 추가 
	public static final String LC_BASE 									= "C001"; 			//기본카테고리
	public static final String LC_PLANTHEME_BRAND			= "C003"; 			//기획테마몰-브랜드몰
	public static final String LC_PLANTHEME_THEME			= "C002"; 			//기획테마몰-테마몰  
	public static final String LC_PLANTHEME_SPECIAL			= "C202"; 			//기획테마몰-전문관 (신규)
	public static final String LC_PLANTHEME_PRIORDELIV  = "C0990015"; 	//정기배송 (신규)
	public static final String LC_PLANTHEME_VIRTURE		= "C099"; 			//가상카테고리
	public static final String LC_TOY										= "C101"; 			//토이저러스
	public static final String LC_PLANTHEME_MOBILEGNB	= "C0990013"; 	//모바일GNB

	
	public static final String CATEGORY_DISP_DIVN_FOOD	    = "01"; 	// 전시구분코드 FOOD
	public static final String CATEGORY_DISP_DIVN_LIVING	= "02"; 	// 전시구분코드 LIVING
	public static final String CATEGORY_DISP_DIVN_STYLE	    = "03"; 	// 전시구분코드 STYLE
	
	
}
