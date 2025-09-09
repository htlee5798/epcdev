package com.lottemart.epc.calculation.model;
import java.io.Serializable;
import java.util.List;

public class PSCMCAL0003SearchVO implements Serializable {
	private static final long serialVersionUID = -6988150686011225975L;


	/** 조회월 */
    private String searchMonth = "";

	/** 조회시작일 */
    private String startDate = "";

	/** 조회종료일 */
    private String endDate = "";
    
    /** 협력사코드 */
    private List<String> vendorId;
    
    /** 선택 협력사코드 */
    private String searchVendorId;
    
    
    /** 처리구분 */
    private String procGbn = "";
    
    /** 검색사용여부 */
    private String searchUseYn = "";
    
	/** 현재페이지 */
    private int pageIndex = 1;
    
    /** 페이지갯수 */
    private int pageUnit = 30;
    
    /** 페이지사이즈 */
    private int pageSize = 10;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;

    /** recordCountPerPage */
    private int recordCountPerPage = 10;
    
    private String rankNum = "";
    private String totalCount = "";
    private String payMm = "";
    private String islndRegnChek = "";
    private String orderId = "";
    private String upOrderId = "";
    private String ordDy = "";
    private String ordStsNm = "";
    private String saleStsNm = "";
    private String ordAmt = "";
    private String custNm = "";
    private String deliveryAmt = "";
    private String ecOrderId = "";
    
	public String getSearchMonth() {
		return searchMonth;
	}
	
	
	public List<String> getVendorId() {
		return vendorId;
	}

	public void setVendorId(List<String> vendorId) {
		this.vendorId = vendorId;
	}

	public String getSearchVendorId() {
		return searchVendorId;
	}

	public void setSearchVendorId(String searchVendorId) {
		this.searchVendorId = searchVendorId;
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


	public String getRankNum() {
		return rankNum;
	}


	public void setRankNum(String rankNum) {
		this.rankNum = rankNum;
	}


	public String getTotalCount() {
		return totalCount;
	}


	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}


	public String getPayMm() {
		return payMm;
	}


	public void setPayMm(String payMm) {
		this.payMm = payMm;
	}


	public String getIslndRegnChek() {
		return islndRegnChek;
	}


	public void setIslndRegnChek(String islndRegnChek) {
		this.islndRegnChek = islndRegnChek;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getUpOrderId() {
		return upOrderId;
	}


	public void setUpOrderId(String upOrderId) {
		this.upOrderId = upOrderId;
	}


	public String getOrdDy() {
		return ordDy;
	}


	public void setOrdDy(String ordDy) {
		this.ordDy = ordDy;
	}


	public String getOrdStsNm() {
		return ordStsNm;
	}


	public void setOrdStsNm(String ordStsNm) {
		this.ordStsNm = ordStsNm;
	}


	public String getSaleStsNm() {
		return saleStsNm;
	}


	public void setSaleStsNm(String saleStsNm) {
		this.saleStsNm = saleStsNm;
	}


	public String getOrdAmt() {
		return ordAmt;
	}


	public void setOrdAmt(String ordAmt) {
		this.ordAmt = ordAmt;
	}


	public String getCustNm() {
		return custNm;
	}


	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}


	public String getDeliveryAmt() {
		return deliveryAmt;
	}


	public void setDeliveryAmt(String deliveryAmt) {
		this.deliveryAmt = deliveryAmt;
	}

	
	public String getEcOrderId() {
		return ecOrderId;
	}

	
	public void setEcOrderId(String ecOrderId) {
		this.ecOrderId = ecOrderId;
	}
	
	
}



