/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 18. 오후 2:33:50
 * @author      : yskim 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.statistics.model;

import java.io.Serializable;

/**
 * @Class Name : PSCMSTA0001SearchVO
 * @Description : 네이버지식쇼핑/쇼핑캐스트 조회 VO 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:23:45 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class PSCMSTA0001SearchVO implements Serializable {

	private static final long serialVersionUID = 6988983300339029302L;

	/** 조회시작일 */
    private String startDate = "";
    
    /** 조회종료일 */
    private String endDate = "";
    
    /** 최상위 제휴링크번호 */
    private String rootAffiliateLinkNo = "";

    /** 제휴링크번호 */
    private String affiliateLinkNo = "";
    
    /** 제휴링크번호 */
    private String orderPathCd = "";
    
    
  

	/** 검색사용여부 */
    private String searchUseYn = "";
    
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
    private int recordCountPerPage = 10;

    
  
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

    public String getRootAffiliateLinkNo() {
		return rootAffiliateLinkNo;
	}

	public void setRootAffiliateLinkNo(String rootAffiliateLinkNo) {
		this.rootAffiliateLinkNo = rootAffiliateLinkNo;
	}

	public String getAffiliateLinkNo() {
		return affiliateLinkNo;
	}

	public void setAffiliateLinkNo(String affiliateLinkNo) {
		this.affiliateLinkNo = affiliateLinkNo;
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

    public String getSearchUseYn() {
        return searchUseYn;
    }

    public void setSearchUseYn(String searchUseYn) {
        this.searchUseYn = searchUseYn;
    }

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
    
    public String getOrderPathCd() {
  		return orderPathCd;
  	}
  	public void setOrderPathCd(String orderPathCd) {
  		this.orderPathCd = orderPathCd;
  	}
  	

    public String toString() {
    	StringBuffer sb = new StringBuffer("\n");
    	sb.append("\n startDate :: " ).append(startDate);
    	sb.append("\n endDate :: " ).append(endDate);
    	sb.append("\n affiliateLinkNo :: " ).append(affiliateLinkNo);
    	sb.append("\n orderPathCd :: " ).append(orderPathCd);
    	sb.append("\n searchUseYn :: " ).append(searchUseYn);
    	sb.append("\n pageIndex :: " ).append(pageIndex);
    	sb.append("\n pageUnit :: " ).append(pageUnit);
    	sb.append("\n pageSize :: " ).append(pageSize);
    	sb.append("\n firstIndex :: " ).append(firstIndex);
    	sb.append("\n lastIndex :: " ).append(lastIndex);
    	sb.append("\n recordCountPerPage :: " ).append(recordCountPerPage);
    	return sb.toString();
    }
}
