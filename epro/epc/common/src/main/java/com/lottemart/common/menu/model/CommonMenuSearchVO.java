package com.lottemart.common.menu.model;

import java.io.Serializable;

public class CommonMenuSearchVO implements Serializable {

	private static final long serialVersionUID = 1227948584512174220L;

	private String q;

	private Integer rowsPerPage;

	private Integer currentPage;

	private String adminId;

	private String sysDivnCd;

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public Integer getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(Integer rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getOffset() {
		if(!hasPaginationParameters()) {
			return null;
		}
		return ((currentPage - 1) * rowsPerPage) + 1;
	}

	public Integer getLimit() {
		if(!hasPaginationParameters()) {
			return null;
		}
		return this.getOffset() + rowsPerPage - 1;
	}

	public boolean hasPaginationParameters() {
		return (this.getRowsPerPage() != null && this.getCurrentPage() != null);
	}

	public String getSysDivnCd() {
		return sysDivnCd;
	}

	public void setSysDivnCd(String sysDivnCd) {
		this.sysDivnCd = sysDivnCd;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

}