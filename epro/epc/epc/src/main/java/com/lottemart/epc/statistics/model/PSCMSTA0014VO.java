package com.lottemart.epc.statistics.model;

public class PSCMSTA0014VO {
	
	private String startDate = "";
	
	private String endDate = "";
	
	private String martStrNo = "";

	private String superStrNo = "";
	
	private String pickupStatus = "";
	
	private String deliStatus = "";
	
	private String ordNo = "";

	private String ordDivn = "";

	private String pickUpTime = "";

	private String extStrCd = "";

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

    private String rowsPerPage = "10";

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

	public String getsuperStrNo() {
		return superStrNo;
	}

	public void setsuperStrNo(String superStrNo) {
		this.superStrNo = superStrNo;
	}

	public String getMartStrNo() {
		return martStrNo;
	}

	public void setMartStrNo(String martStrNo) {
		this.martStrNo = martStrNo;
	}

	public String getSuperStrNo() {
		return superStrNo;
	}

	public void setSuperStrNo(String superStrNo) {
		this.superStrNo = superStrNo;
	}

	public String getPickupStatus() {
		return pickupStatus;
	}

	public void setPickupStatus(String pickupStatus) {
		this.pickupStatus = pickupStatus;
	}

	public String getDeliStatus() {
		return deliStatus;
	}

	public void setDeliStatus(String deliStatus) {
		this.deliStatus = deliStatus;
	}

	public String getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(String rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public String getOrdNo() {
		return ordNo;
	}

	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}

	public String getOrdDivn() {
		return ordDivn;
	}

	public void setOrdDivn(String ordDivn) {
		this.ordDivn = ordDivn;
	}

	public String getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(String pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public String getExtStrCd() {
		return extStrCd;
	}

	public void setExtStrCd(String extStrCd) {
		this.extStrCd = extStrCd;
	}
	
}
