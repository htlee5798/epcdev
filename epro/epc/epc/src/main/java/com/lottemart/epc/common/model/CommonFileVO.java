package com.lottemart.epc.common.model;

import java.io.Serializable;

/**
 * @Class Name : CommonFileVO.java
 * @Description : 게시판 공통 CommonFileVO
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 11. 11. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */
public class CommonFileVO implements Serializable
{  
	private static final long serialVersionUID = 5994269267773900622L;
	
	private String boardSeq = "";
	private String fileSeq = "";
	private String regId = "";
	private String modId = "";
	private String fileExt = "";//파일확장자
	private String fileSize = "";//사이즈
	private String filePath = "";//파일경로 storePathString
	private String fileNmae = "";//실제파일명
	private String tempFileName = "";//변환파일
	private String fileId = ""; //첨부파일 아이디
	private String fileKey = ""; //파일연번
	
	private String pgmId = "";	//연계업무번호
	
	public String getBoardSeq() {
		return boardSeq;
	}
	public void setBoardSeq(String boardSeq) {
		this.boardSeq = boardSeq;
	}
	public String getFileSeq() {
		return fileSeq;
	}
	public void setFileSeq(String fileSeq) {
		this.fileSeq = fileSeq;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileNmae() {
		return fileNmae;
	}
	public void setFileNmae(String fileNmae) {
		this.fileNmae = fileNmae;
	}
	public String getTempFileName() {
		return tempFileName;
	}
	public void setTempFileName(String tempFileName) {
		this.tempFileName = tempFileName;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileKey() {
		return fileKey;
	}
	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}

}
