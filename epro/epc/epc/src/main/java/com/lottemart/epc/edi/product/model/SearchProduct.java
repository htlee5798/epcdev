package com.lottemart.epc.edi.product.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.lottemart.epc.edi.comm.model.Constants;


/**
 * @Class Name : SearchProduct
 * @Description : 신상품 등록에 사용되는 파라미터 전달용  SearchVO 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 2:32:38 kks
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class SearchProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9165984052941608622L;
	
	public SearchProduct() {}
	
	//업체코드
	private String entpCode;
	//one점포코드
	private String oneStrCd;
		


	//상품 구분 필드 1:규격 5:패션
	private String productDivnCode;
	
	//판매(88)코드 
	private String sellCode;
	
	//상품코드
	private String newProductCode; 

	//바코드 상태 값
	private String logiBarcodeStatus;
	
	//검색 시작일
	private String startDate;
	
	//검색 종료일
	private String endDate;
	
	//상품 상태값 : 
	private String productStatus;
	private String adjustStatusFlag;
	
	//로그인사용자의 협력업체코드 전체
	private String[] venCds;		
	
	//조회조건에서 선택될 협력 업체코드
	private String   venCd;			
	
	//온오프(0), 온라인전용(1), 소셜(2) 의 상품 구분 값
	private String onOffDivnCode;
	
	private String imageSeq;
	private String imageName; // 기존 PROD_IMG_ID
	
	//상품가로
	private String productWidth;
	
	//상품세로길이
	private String productHeight;
	private String productLength;
	
	//적용일, 이미지 사이즈 수정시 사용됨. 
	private String applyDay;
	//물류바코드대상여부
	private String wUseFlag; 
	
	
	/* 2015.11.26이 추가 상품분석속성관리(일괄 조회조건) */
	private String	teamCode;
	private String l1Cd;
	private String l4Cd;
	private String pageIdx;
	private String majorCd;
	private String orderGbn;
	private String srchSellCode;
	private String srchCompleteGbn;
	
	
	/* 2015.12.21 추가 상품분석속성관리(일괄) 한페이지에 게시물건수 */
	private String	recordCnt;
	
	private String sapL3Cd;					//SAP으로 매핑된 소분류 코드
	

	private String downloadFlag;
	
	public String getDownloadFlag() {
		return downloadFlag;
	}

	public void setDownloadFlag(String downloadFlag) {
		this.downloadFlag = downloadFlag;
	}
	
	
    /** 페이지 세팅 **/
    

	/** 현재페이지 */
    private int pageIndex = 1;
    
    /** 페이지갯수 */
    private int pageUnit = 10;
    
    /** 페이지사이즈 */
    private int pageSize = 10;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;

    /** recordCountPerPage */
    private int recordCountPerPage = 20;
    

	
    /** 분석속성 검색조건 상품구분 추가 2016.03.07 by song min kyo */
    private String srchProdGbn;
    private String srchGrpCd;
    
	
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public String getApplyDay() {
		return applyDay;
	}

	public void setApplyDay(String applyDay) {
		this.applyDay = applyDay;
	}

	public String getProductWidth() {
		return productWidth;
	}

	public void setProductWidth(String productWidth) {
		this.productWidth = productWidth;
	}

	public String getProductHeight() {
		return productHeight;
	}

	public void setProductHeight(String productHeight) {
		this.productHeight = productHeight;
	}

	public String getProductLength() {
		return productLength;
	}

	public void setProductLength(String productLength) {
		this.productLength = productLength;
	}

	public String getImageSeq() {
		return imageSeq;
	}

	public void setImageSeq(String imageSeq) {
		this.imageSeq = imageSeq;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getOnOffDivnCode() {
		return onOffDivnCode;
	}

	public void setOnOffDivnCode(String onOffDivnCode) {
		this.onOffDivnCode = onOffDivnCode;
	}

	public String getNewProductCode() {
		return newProductCode;
	}

	public void setNewProductCode(String newProductCode) {
		this.newProductCode = newProductCode;
	}

	public String getAdjustStatusFlag() {
		return adjustStatusFlag;
	}

	public void setAdjustStatusFlag(String adjustStatusFlag) {
		this.adjustStatusFlag = adjustStatusFlag;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLogiBarcodeStatus() {
		return logiBarcodeStatus;
	}

	public void setLogiBarcodeStatus(String logiBarcodeStatus) {
		this.logiBarcodeStatus = logiBarcodeStatus;
	}

	public String getSellCode() {
		return sellCode;
	}

	public void setSellCode(String sellCode) {
		this.sellCode = sellCode;
	}

	public String getEntpCode() {
		return entpCode;
	}

	public void setEntpCode(String entpCode) {
		this.entpCode = entpCode;
	}

	public String getProductDivnCode() {
		return StringUtils.defaultIfEmpty(productDivnCode, Constants.STANDARD_PRODUCT_CD);
	}

	public void setProductDivnCode(String productDivnCode) {
		this.productDivnCode = productDivnCode;
	}

	public String[] getVenCds() {
		return venCds;
	}

	public void setVenCds(String[] venCds) {
		this.venCds = venCds;
	}

	public String getVenCd() {
		return venCd;
	}

	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	
	
	 private String colorCd;
	 private String szCatCd;
	 private String szCd;


	public String getColorCd() {
		return colorCd;
	}

	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}

	public String getSzCatCd() {
		return szCatCd;
	}

	public void setSzCatCd(String szCatCd) {
		this.szCatCd = szCatCd;
	}

	public String getSzCd() {
		return szCd;
	}

	public void setSzCd(String szCd) {
		this.szCd = szCd;
	}
	public String getwUseFlag() {
		return wUseFlag;
	}

	public void setwUseFlag(String wUseFlag) {
		this.wUseFlag = wUseFlag;
	}

	public String getOneStrCd() {
		return oneStrCd;
	}
	public void setOneStrCd(String oneStrCd) {
		this.oneStrCd = oneStrCd;
	}

	public String getTeamCode() {
		return teamCode;
	}

	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}

	public String getL1Cd() {
		return l1Cd;
	}

	public void setL1Cd(String l1Cd) {
		this.l1Cd = l1Cd;
	}

	public String getL4Cd() {
		return l4Cd;
	}

	public void setL4Cd(String l4Cd) {
		this.l4Cd = l4Cd;
	}

	public String getSrchSellCode() {
		return srchSellCode;
	}

	public void setSrchSellCode(String srchSellCode) {
		this.srchSellCode = srchSellCode;
	}

	public String getPageIdx() {
		return pageIdx;
	}

	public void setPageIdx(String pageIdx) {
		this.pageIdx = pageIdx;
	}

	public String getMajorCd() {
		return majorCd;
	}

	public void setMajorCd(String majorCd) {
		this.majorCd = majorCd;
	}

	public String getOrderGbn() {
		return orderGbn;
	}

	public void setOrderGbn(String orderGbn) {
		this.orderGbn = orderGbn;
	}

	public String getSrchCompleteGbn() {
		return srchCompleteGbn;
	}

	public void setSrchCompleteGbn(String srchCompleteGbn) {
		this.srchCompleteGbn = srchCompleteGbn;
	}

	public String getRecordCnt() {
		return recordCnt;
	}

	public void setRecordCnt(String recordCnt) {
		this.recordCnt = recordCnt;
	}

	public String getSapL3Cd() {
		return sapL3Cd;
	}

	public void setSapL3Cd(String sapL3Cd) {
		this.sapL3Cd = sapL3Cd;
	}

	public String getSrchProdGbn() {
		return srchProdGbn;
	}

	public void setSrchProdGbn(String srchProdGbn) {
		this.srchProdGbn = srchProdGbn;
	}

	public String getSrchGrpCd() {
		return srchGrpCd;
	}

	public void setSrchGrpCd(String srchGrpCd) {
		this.srchGrpCd = srchGrpCd;
	}		
	
}
