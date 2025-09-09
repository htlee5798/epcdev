/**
 * @prjectName  : lottemart 프로젝트   
 * @since       : 2011. 10. 19. 오후 2:31:50
 * @author      : wcpark 
 * @Copyright(c) 2000 ~ 2011 lottemart
 *  All rights reserved.
 */

package com.lottemart.epc.board.model;

import java.io.Serializable;

/**
 * @Class Name : PSCMBRD0001SearchVO
 * @Description : 공지사항 목록 SearchVO 클래스
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 19. 오후 2:32:38 wcpark
 * 
 * @Copyright (C) 2000 ~ 2011 lottemart All right reserved.
 * </pre>
 */

public class PSCMBRD0001SearchVO implements Serializable {

    
	private static final long serialVersionUID = -7700442673307910066L;



private String majorCd = "";
	
    public String getMajorCd() {
		return majorCd; 
	}

	public void setMajorCd(String majorCd) {
		this.majorCd = majorCd;
	}
	
	/** Test */
	private String minorCd = "";
	
	
	
	/** 제휴링크번호 */
    private String receiptLocation = "";
    
    /** 검색사용여부 */
    private String searchUseYn = "";
    
    /** select box 접수위치 */
    private String acceptLocaCd = "";    
    
    /** startBoardRegDt */
    private String startBoardRegDt = "";
    
    /** endBoardRegDt */
    private String endBoardRegDt = "";
    
    /** 공개여부 */
    private String pblYn = "";

    /** boardDivnCd */
    private String boardDivnCd = "";
    
    /** title */
    private String title = "";    
    
    /** memberNm */
    private String memberNm = "";    
    
    /** boardSeq */
    private String boardSeq = "";    
    
    

    /** 페이지 세팅 **/
    
	/** 현재페이지 */
    private int pageIndex = 1;
    
    /** 페이지갯수 */
    private int pageUnit = 15;
    
    /** 페이지사이즈 */
    private int pageSize = 10;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;

    /** recordCountPerPage */
    private int recordCountPerPage = 10;
    

    
    public String getMemberNm() {
		return memberNm;
	}

	public void setMemberNm(String memberNm) {
		this.memberNm = memberNm;
	}


	public String getMinorCd() {
		return minorCd;
	}

	public void setMinorCd(String minorCd) {
		this.minorCd = minorCd;
	}
	
	public String getReceiptLocation() {
		return receiptLocation;
	}
	
	public void setReceiptLocation(String receiptLocation) {
		this.receiptLocation = receiptLocation;
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

    public String getStartBoardRegDt() {
		return startBoardRegDt;
	}

	public void setStartBoardRegDt(String startBoardRegDt) {
		this.startBoardRegDt = startBoardRegDt;
	}

	public String getEndBoardRegDt() {
		return endBoardRegDt;
	}

	public void setEndBoardRegDt(String endBoardRegDt) {
		this.endBoardRegDt = endBoardRegDt;
	}

	public String getpblYn() {
		return pblYn;
	}

	public void setpblYn(String pblYn) {
		this.pblYn = pblYn;
	}

	public String getBoardDivnCd() {
		return boardDivnCd;
	}

	public void setBoardDivnCd(String boardDivnCd) {
		this.boardDivnCd = boardDivnCd;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSearchUseYn() {
        return searchUseYn;
    }

    public void setSearchUseYn(String searchUseYn) {
        this.searchUseYn = searchUseYn;
    }

    public String getAcceptLocaCd() {
		return acceptLocaCd;
	}

	public void setAcceptLocaCd(String acceptLocaCd) {
		this.acceptLocaCd = acceptLocaCd;
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

	public String getBoardSeq() {
		return boardSeq;
	}

	public void setBoardSeq(String boardSeq) {
		this.boardSeq = boardSeq;
	}

}
