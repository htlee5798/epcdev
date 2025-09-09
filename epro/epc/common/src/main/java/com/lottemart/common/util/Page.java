package com.lottemart.common.util;

public class Page
{
    /**
     * 페이지 인덱스 크기
     */
    private int indexSize;
    /**
     * 첫페이지로 이동 표시
     */
    private String  firstPageMark;
    /**
     * 마지막 페이지로 이동 표시
     */
    private String  lastPageMark;
    /**
     * 이전 페이지 인덱스 이동 표시
     */
    private String  previousPageMark;
    /**
     * 다음 체이지 인덱스 이동 표시
     */
    private String  nextPageMark;
    /**
     * 페이지 번호 구분 표시
     */
    private String  pageSeparateMark;
    /**
     * 페이지 번호 스타일
     */
    private String  pageNumberStyle;
    /**
     * 현재 페이지 번호 스타일
     */
    private String  curPageNumberStyle;
    /**
     * 마우스 포인터가 올라왔을때 변화할 배경색
     */
    private String  mouseHoverColor;
    /**
     * 스타일
     */
    private String  style;

    /**
     * 마우스 over시 class 호출
     */
    private String overClassName;
    /**
     * 마우스 out시 class 호출
     */
    private String outClassName;
    
    public Page() {
	this.setBasicIndexRendere();
    }
    
    /**
     * 페이징 인덱스 HTML을 얻습니다.
     * @param renderId String 렌더러 아이디 null이면 기본 렌더러
     * @param tot int 총 row 수
     * @param curPage int 현제 페이지 번호
     * @param rowsPerPage int 페이당 row 갯수
     * @param funcName String 페이지 번호 클릭시 호출할 펑션 이름
     * @param section String Page Index 스타일을 지정한다. String Type 으로 front , back ,evnet 3가지로 구분한다.
     * @param imageHost 요청한 이미지 Host URL
     * @return
     */
    public static String getPageIndexHtml(int tot, int curPage, int rowsPerPage, String funcName) {
	Page p = new Page();
        return p.render(tot, curPage, rowsPerPage, funcName);
    }
    
    /**
     * 기본 페이지 인덱스 랜더링 표현
     */
    private void setBasicIndexRendere(){
    	this.firstPageMark      = "<img src='/images/bos/layout/btn_first.gif' align=absmiddle>";
        this.lastPageMark       = "<b><img src='/images/bos/layout/btn_end.gif' align=absmiddle></b>";
        this.previousPageMark   = "<img src='/images/bos/layout/btn_prev.gif' align=absmiddle>";
        this.nextPageMark       = "<img src='/images/bos/layout/btn_next.gif' align=absmiddle>";
        this.pageSeparateMark   = "<span style='color:#666666'>|</span>";
        this.pageNumberStyle    = "color:#b52f03;font-size:12px;padding:2px 5px;";
        this.curPageNumberStyle = "color:#b52f03;font-size:12px;font-weight:bold;padding:2px 5px;";
        this.mouseHoverColor    = "#FFFFE0";
        this.overClassName		= "taOver";
        this.outClassName		= "taOut";
        this.style              = "font-size:12px;color:gray;text-decoration:none;cursor:pointer";
        this.indexSize          = 10;
    }

    /**
     * Implemented this
     *   com.cjhs.frameworks.ticket.utils.PageIndexRenderer method
     */
    public String render(int totalRow, int curPageNo, int rowPerPage
        , String funcName) {
        int maxPageNo;
        int fromPageNo;
        int toPageNo;
        int pPage;
        int nPage;
        int curPageNoValue = curPageNo;

        if (curPageNo < 1) {
        	curPageNoValue = 1;
        }
        if (totalRow == 0) {
            maxPageNo   = 1;
        } else {
            maxPageNo   = totalRow / rowPerPage;
            if ((totalRow % rowPerPage) != 0) {
                maxPageNo++;
            }
        }
        if (curPageNo > maxPageNo) {
        	curPageNoValue = maxPageNo;
        }
        fromPageNo  = curPageNo - this.indexSize / 2;
        if (fromPageNo < 1) {
            fromPageNo = 1;
        }
        toPageNo = fromPageNo + this.indexSize - 1;
        if (toPageNo > maxPageNo) {
            toPageNo    = maxPageNo;
            fromPageNo = maxPageNo - this.indexSize + 1;
            if (fromPageNo < 1) {
                fromPageNo = 1;
            }
        }
        pPage   = curPageNo - this.indexSize;
        if (pPage < 1) {
            pPage   = 1;
        }
        nPage   = curPageNo + this.indexSize;
        if (nPage > maxPageNo) {
            nPage   = maxPageNo;
        }
        return this.getRenderHtml(fromPageNo, toPageNo, maxPageNo, curPageNoValue
            ,pPage, nPage, funcName);
    }

     
    /**
     * 페이지 인덱스 랜더링 (모달과 오프너를 지원하기 위하여 처리)
     * @param fromPageNo
     * @param toPageNo
     * @param maxPageNo
     * @param curPageNo
     * @param pPage
     * @param nPage
     * @param jsFunctionName
     * @param gb
     * @return
     */
    private String getRenderHtml(int fromPageNo, int toPageNo, int maxPageNo
        , int curPageNo, int pPage, int nPage, String jsFunctionName) {

	        StringBuffer    sb;
	
	        sb  = new StringBuffer();
	
	        sb.append("\r\n");
	        sb.append("<!-- Page Index Start (Use Java Script Function ");
	        sb.append(jsFunctionName);
	        sb.append("() -->\r\n");
	
	        // 첫 페이지
	        sb.append("<span title=\"1페이지\" tabindex=0 style=\"");
	        sb.append(this.style);
	        if (curPageNo == 1) {
	            sb.append("\">");
	        } else {
	            sb.append("\" onclick=\"");
	            sb.append(jsFunctionName);
	            sb.append("(1)\">");
	        }
	        sb.append(this.firstPageMark);
	        sb.append("</span>&nbsp;");
	
	        // 이전 페이지 출력
	        sb.append("<span title=\"");
	        sb.append(pPage);
	        sb.append("페이지\" tabindex=0 style=\"");
	        sb.append(this.style);
	        if (curPageNo == pPage) {
	            sb.append("\">");
	        } else {
	            sb.append("\" onclick=\"");
	            sb.append(jsFunctionName);
	            sb.append("(");
	            sb.append(pPage);
	            sb.append(")\">");
	        }
	        sb.append(this.previousPageMark);
	        sb.append("</span>&nbsp;");
	
	        // 페이지 인덱스
	        for (int i=fromPageNo; i<=toPageNo; i++) {
	            if (i>fromPageNo) {
	                sb.append(this.pageSeparateMark);
	            }
	
	            sb.append("<span tabindex=0 style=\"");
	            sb.append(this.style);		
	            sb.append("\" onMouseOver=\"this.className='");
	            sb.append(this.overClassName);
	            sb.append("'\" onMouseOut=\"this.className='");
	            sb.append(this.outClassName);
	            sb.append("'\"");
	            if (i == curPageNo) {
	                sb.append("><span style=\"");
	                sb.append(this.curPageNumberStyle);
	                sb.append("\">&nbsp;");
	            } else {
	                sb.append(" onclick=\"");
	                sb.append(jsFunctionName);
	                sb.append('(');
	                sb.append(i);
	                sb.append(")\">");
	                sb.append("<span style=\"");
	                sb.append(this.pageNumberStyle);
	                sb.append("\">&nbsp;");
	            }
	            sb.append(i);
	            sb.append("&nbsp;</span></span>");
	        }
	
	        // 이후 페이지 출력
	        sb.append("&nbsp;<span title='");
	        sb.append(nPage);
	        sb.append("페이지' tabindex=0 style=\"");
	        sb.append(this.style);
	        if (curPageNo == nPage) {
	            sb.append("\">");
	        } else {
	            sb.append("\" onclick=\"");
	            sb.append(jsFunctionName);
	            sb.append("(");
	            sb.append(nPage);
	            sb.append(")\">");
	        }
	        sb.append(this.nextPageMark);
	        sb.append("</span>&nbsp;");
	
	
	        // 마지막 페이지 출력
	        sb.append("<span title='");
	        sb.append(maxPageNo);
	        sb.append("페이지' tabindex=0 style=\"");
	        sb.append(this.style);
	        if (curPageNo == maxPageNo) {
	            sb.append("\">");
	        } else {
	            sb.append("\" onclick=\"");
	            sb.append(jsFunctionName);
	            sb.append("(");
	            sb.append(maxPageNo);
	            sb.append(")\">");
	        }
	        sb.append(this.lastPageMark);
	        sb.append("</span>\r\n");
	
	        //마무리
	        sb.append("<!-- Page Index End (Use Java Script Function ");
	        sb.append(jsFunctionName);
	        sb.append("() -->\r\n");

	        return sb.toString();
    }

    public String getCurPageNumberStyle() {
        return curPageNumberStyle;
    }

    public String getFirstPageMark() {
        return firstPageMark;
    }

    public int getIndexSize() {
        return indexSize;
    }

    public String getLastPageMark() {
        return lastPageMark;
    }

    public String getMouseHoverColor() {
        return mouseHoverColor;
    }

    public String getNextPageMark() {
        return nextPageMark;
    }

    public String getPageSeparateMark() {
        return pageSeparateMark;
    }

    public String getPageNumberStyle() {
        return pageNumberStyle;
    }

    public String getPreviousPageMark() {
        return previousPageMark;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setPreviousPageMark(String previousPageMark) {
        this.previousPageMark = previousPageMark;
    }

    public void setPageSeparateMark(String pageSeparateMark) {
        this.pageSeparateMark = pageSeparateMark;
    }

    public void setPageNumberStyle(String pageNumberStyle) {
        this.pageNumberStyle = pageNumberStyle;
    }

    public void setNextPageMark(String nextPageMark) {
        this.nextPageMark = nextPageMark;
    }

    public void setMouseHoverColor(String mouseHoverColor) {
        this.mouseHoverColor = mouseHoverColor;
    }

    public void setLastPageMark(String lastPageMark) {
        this.lastPageMark = lastPageMark;
    }

    public void setIndexSize(int indexSize) {
        this.indexSize = indexSize;
    }

    public void setFirstPageMark(String firstPageMark) {
        this.firstPageMark = firstPageMark;
    }

    public void setCurPageNumberStyle(String curPageNumberStyle) {
        this.curPageNumberStyle = curPageNumberStyle;
    }
    
    public void setOverClassName(String overClassName){
    	this.overClassName = overClassName;
    }
    
    public String getOverClassName(){
    	return this.overClassName;
    }
    
    public void setOutClassName(String outClassName){
    	this.outClassName = outClassName;
    }
    public String getOutClassName(){
    	return this.outClassName;
    }
}