package com.lottemart.epc.edi.consult.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Estimation {
	private String srchStartDate;
	private String srchEndDate;
	
	/** 협력업체코드 */
	private String searchEntpCd;
	
	
	/** 협력업체 코드 */
	private String[] venCds;
	
	/** 삭제파람 */
	private String[] pids;
	private String del_file;
	private String del_file_path;
	private String del_file_nm;
	
	private String  pid;;
	private String  venCd;
	private String  docNm;
	private String  fileSeq;
	private String  fileNm;
	private String  regDate;
	private String fileSize;
	private MultipartFile file;
	private List<EstimationSheet> estimationSheet;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getVenCd() {
		return venCd;
	}
	public void setVenCd(String venCd) {
		this.venCd = venCd;
	}
	public String getDocNm() {
		return docNm;
	}
	public void setDocNm(String docNm) {
		this.docNm = docNm;
	}
	public String getFileSeq() {
		return fileSeq;
	}
	public void setFileSeq(String fileSeq) {
		this.fileSeq = fileSeq;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public List<EstimationSheet> getEstimationSheet() {
		return estimationSheet;
	}
	public void setEstimationSheet(List<EstimationSheet> estimationSheet) {
		this.estimationSheet = estimationSheet;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getSrchEndDate() {
		return srchEndDate;
	}
	public void setSrchEndDate(String srchEndDate) {
		this.srchEndDate = srchEndDate;
	}
	
	public String[] getPids() {
		if (this.pids != null) {
			String[] ret = new String[pids.length];
			for (int i = 0; i < pids.length; i++) { 
				ret[i] = this.pids[i]; 
			}
			return ret;
		} else {
			return null;
		}
	}
	public void setPids(String[] pids) {
		if (pids != null) {
			 this.pids = new String[pids.length];			 
			 for (int i = 0; i < pids.length; ++i) {
				 this.pids[i] = pids[i];
			 }
		 } else {
			 this.pids = null;
		 }
	}
	
	
	public String[] getVenCds() {
		if (this.venCds != null) {
			String[] ret = new String[venCds.length];
			for (int i = 0; i < venCds.length; i++) { 
				ret[i] = this.venCds[i]; 
			}
			return ret;
		} else {
			return null;
		}
	}
	public void setVenCds(String[] venCds) {
		if (venCds != null) {
			 this.venCds = new String[venCds.length];			 
			 for (int i = 0; i < venCds.length; ++i) {
				 this.venCds[i] = venCds[i];
			 }
		 } else {
			 this.venCds = null;
		 }
	}
	public String getSrchStartDate() {
		return srchStartDate;
	}
	public void setSrchStartDate(String srchStartDate) {
		this.srchStartDate = srchStartDate;
	}
	public String getSearchEntpCd() {
		return searchEntpCd;
	}
	public void setSearchEntpCd(String searchEntpCd) {
		this.searchEntpCd = searchEntpCd;
	}
	public String getDel_file() {
		return del_file;
	}
	public void setDel_file(String del_file) {
		this.del_file = del_file;
	}
	public String getDel_file_path() {
		return del_file_path;
	}
	public void setDel_file_path(String del_file_path) {
		this.del_file_path = del_file_path;
	}
	public String getDel_file_nm() {
		return del_file_nm;
	}
	public void setDel_file_nm(String del_file_nm) {
		this.del_file_nm = del_file_nm;
	}
	
}
