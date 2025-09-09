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
 * @Class Name : PSCMSTA0004SearchVO
 * @Description : 아시아나정산관리 VO 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 18. 오후 5:24:29 yskim
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class PSCMSTA0008SearchVO implements Serializable {

	private static final long serialVersionUID = -7940379769232592863L;

	/** 조회월 */
    private String searchMonth = "";

	/** 조회시작일 */
    private String startDate = "";

	/** 조회종료일 */
    private String endDate = "";
    
    /** 검색 조회시작일 */
    private String fromDate = "";    
   
	/** 검색 조회종료일 */
    private String toDate = "";   
    
    
    /** 처리구분 */
    private String procGbn = "";
    
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

    
    
	public String getSearchMonth() {
		return searchMonth;
	}

	public void setSearchMonth(String searchMonth) {
		this.searchMonth = searchMonth;
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

	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	public String getProcGbn() {
		return procGbn;
	}

	public void setProcGbn(String procGbn) {
		this.procGbn = procGbn;
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

    public String toString() {
    	StringBuffer sb = new StringBuffer("\n");
    	sb.append("\nsearchMonth :: " ).append(searchMonth);
    	sb.append("\nprocGbn :: " ).append(procGbn);
    	sb.append("\nsearchUseYn :: " ).append(searchUseYn);
    	sb.append("\npageIndex :: " ).append(pageIndex);
    	sb.append("\npageUnit :: " ).append(pageUnit);
    	sb.append("\npageSize :: " ).append(pageSize);
    	sb.append("\nfirstIndex :: " ).append(firstIndex);
    	sb.append("\nlastIndex :: " ).append(lastIndex);
    	sb.append("\nrecordCountPerPage :: " ).append(recordCountPerPage);
    	return sb.toString();
    }
}
