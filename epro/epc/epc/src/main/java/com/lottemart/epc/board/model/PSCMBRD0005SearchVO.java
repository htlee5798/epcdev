package com.lottemart.epc.board.model;


import java.io.Serializable;

/**
 * @Class Name : PSCMBRD0005SearchVO.java
 * @Description :
 * @Modification Information
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 * 2011. 10. 20. 오후 02:02:02 mjChoi
 * 
 * @Copyright (C) 2000 ~ 2011 롯데정보통신(주) All right reserved.
 * </pre>
 */
public class PSCMBRD0005SearchVO implements Serializable 
{
	private static final long serialVersionUID = -1935660382410961754L;

	private String majorCd = "";
	
    public String getMajorCd() {
		return majorCd;
	}

	public void setMajorCd(String majorCd) {
		this.majorCd = majorCd;
	}
	
	/** 민원대분류코드(상태) */
    private String minorCd = "";
    
    /** 검색사용여부 */
    private String searchUseYn = "";
    
    /** startBoardRegDt */
    private String startBoardRegDt = "";
    
    /** endBoardRegDt */
    private String endBoardRegDt = "";
    
    /** boardDivnCd */
    private String boardDivnCd = "";
    
    /** title */
    private String title = "";    
    
    /** memberNm */
    private String memberNm = "";    
    
    private String rowNum = "";
    private String boardSeq = "";
    private int viewCnt = 0;
    private int commCnt = 0;
    private String regId = "";
    private String regDate = "";
    private String scrpKindCd = "";
    private String totalCount = "";
    
    
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

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

	public String getBoardSeq() {
		return boardSeq;
	}

	public void setBoardSeq(String boardSeq) {
		this.boardSeq = boardSeq;
	}

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public int getCommCnt() {
		return commCnt;
	}

	public void setCommCnt(int commCnt) {
		this.commCnt = commCnt;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getScrpKindCd() {
		return scrpKindCd;
	}

	public void setScrpKindCd(String scrpKindCd) {
		this.scrpKindCd = scrpKindCd;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

}
