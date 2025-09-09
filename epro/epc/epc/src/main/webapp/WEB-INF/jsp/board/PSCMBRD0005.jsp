<%--
- Author(s): lth
- Created Date: 2016. 05. 11
- Version : 1.0
- Description : 업체문의관리

--%>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"         %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"   %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"               %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://lcnjf.lcn.co.kr/taglib/paging"     %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"      %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"         %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- board/PSCMBRD0005 -->


<script type="text/javascript">

/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function(){
	
    $('#search').click(function() {
        doSearch();
    });
    $('#create').click(function() {
    	goBoardPopup();
    });
    
    
 // START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "400px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibdata = {};
	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"순번", 				Type:"Text", 			SaveName:"rowNum",		Align:"Center", 	Width:50,  Edit:0}
	  , {Header:"게시글번호", 	Type:"Text", 			SaveName:"boardSeq",  	Align:"Center",  	Width:100, Edit:0}
	  , {Header:"제목", 				Type:"Text", 			SaveName:"title",  				Align:"Left", 		Width:250, Edit:0, Cursor:'pointer', FontUnderline:true}
	  , {Header:"조회수", 			Type:"Int", 				SaveName:"viewCnt",  		Align:"Right", 		Width:80, Edit:0, Format:"#,###,###"}
	  , {Header:"코멘트",     		Type:"Int", 				SaveName:"commCnt", 		Align:"Right", 		Width:80, Edit:0, Format:"#,###,###"}
	  , {Header:"작성자명",       Type:"Text", 			SaveName:"regId", 			Align:"Center", 	Width:100,   Edit:0}
	  , {Header:"작성일자", 		Type:"Text", 			SaveName:"regDate", 	    Align:"Center", 	Width:130, Edit:0}
	  , {Header:" ", 					Type:"Text", 			SaveName:"scrpKindCd", 	Align:"Center", 	Width:130, Edit:0, Hidden:true}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	mySheet.FitColWidth();
    
}); // end of ready


	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() 
	{
		var form = document.adminForm;
		//조회기간 체크
	    if(!doDateCheck()) 
	    {
	        return false;
	    }
		
		//특수문자체크
		if(!fnChkSpcCharByte()) 
		{
			return false;
		}
		goPage('1');
	}
	
	/** ********************************************************
	 * 페이징 처리 함수
	 ******************************************************** */
	function goPage(currentPage){
		
		var form = document.adminForm;
		var url = '<c:url value="/board/selectSuggestionSearch.do"/>';
		var param = new Object();
		
		param.rowsPerPage 	= $("#rowsPerPage").val();
		param.startDate = form.startDate.value;
		param.endDate = form.endDate.value;
		param.searchWord = form.searchWord.value;
		param.mode = 'search';
		
		loadIBSheetData(mySheet, url, currentPage, null, param);
	}
	
	/** ********************************************************
	 * 조회기간 체크
	 ******************************************************** */
	function doDateCheck()
	{
	    var form = document.adminForm;
	
	    if (form.startDate.value.replace( /-/gi, '' ) > form.endDate.value.replace( /-/gi, '' ))
	    {
	        alert ("시작일은 종료일보다 작게 입력되어야 합니다.");
	        return false;
	    }
	    
	    return true;
	}
	
	/** ********************************************************
	 * 등록 팝업링크
	 ******************************************************** */
	 function goBoardPopup()
	{
	    var url ='<c:url value="/board/insertSuggestionPopupForm.do"/>';
		Common.centerPopupWindow(url, 'epcSuggestionPopup', {width : 800, height : 600});
	}
	
	/** ********************************************************
	 * 상세보기 팝업링크
	 ******************************************************** */
	function goBoardDetailPopup(board_seq)
	{
		var board = board_seq;
	    var url ='<c:url value="/board/selectSuggestionDetailPopup.do"/>?boardSeq='+board; /* 팝업창 주소 */
		Common.centerPopupWindow(url, 'epcSuggestionDetailPopup', {width : 800, height : 670, scrollBars : 'YES'});
	}
	
    /** ********************************************************
	 * 특수문자 입력여부 및 입력항목의 최대값을 체크한다.
	 ******************************************************** */
	function fnChkSpcCharByte()
	{
        var str = document.adminForm.searchWord.value;
        var len = 0;
        var exp = /[~!@\#$%^&*\()<>{}\[\]\-=+_'\"]/gi;

        if (str.search(exp) != -1)
        {
            alert("검색항목에는 특수문자를 사용할수 없습니다!");
            return false;
        }

        return true;
	}
	
	/**********************************************************
	 * 특수문자 입력 방지 ex) onKeyPress="keyCode(event)"
	 ******************************************************** */
	function keyCode(e)
    {
        var code = (window.event) ? event.keyCode : e.which; // IE : FF - Chrome both
        
        if (code >  32 && code <  48) keyResult(e);
        if (code >  57 && code <  65) keyResult(e);
        if (code >  90 && code <  97) keyResult(e);
        if (code > 122 && code < 127) keyResult(e);
    }
    function keyResult(e)
    {
        alert("검색항목에는 특수문자를 사용할수 없습니다!");
        
        if (navigator.appName != "Netscape") {
            event.returnValue = false;  //IE - Chrome both
        }
        else {
            e.preventDefault(); //FF - Chrome both
        }
    }
    
    
  	//셀 클릭 시...
    function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
  		if(Row == 0) return;
  			
    	if (mySheet.ColSaveName(Col) == 'title') {
    		goBoardDetailPopup(mySheet.GetCellValue(Row, 'boardSeq'));
    	}	
    }

    //데이터 읽은 직후 이벤트
    function mySheet_OnSearchEnd(cd,msg) {
     	for(i = 1; i <= mySheet.GetTotalRows(); i++) {
     		var title = mySheet.GetCellValue(i, 'title').replace(/&nbsp;/gi, ""); 
     		mySheet.SetCellValue(i, 'title', title);
			var scrpKindCd = mySheet.GetCellValue(i, 'scrpKindCd');
			if(scrpKindCd == "01"){
				mySheet.SetCellFontColor(i, "title", '#FF0000');
			}else{
				mySheet.SetCellFontColor(i, "title", '#0000FF');	
			}
     	}
     	
     	mySheet.FitColWidth();
    }


     function mySheet_OnResize(Width, Height) {
     	//변경된 정보에 따라 컬럼들의 너비를 재조정한다.
     	mySheet.FitColWidth();
     }
     
</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

<div class="content_scroll">

<form name="adminForm">
<div id="wrap_menu">
    <div class="wrap_search">
        <!-- 01 : search -->
        <div class="bbs_search">
            <ul class="tit">
                <li class="tit">조회조건</li>
                <li class="btn">
                    <a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
                    <a href="#" class="btn" id="create"><span><spring:message code="button.common.create" /></span></a>
                </li>
            </ul>
            <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
            <colgroup>
                <col width="10%">
                <col width="30%">
                <col width="10%">
                <col width="50%">
            </colgroup>
            <tr>
                <th><span class="star">*</span> 기간</th>
                <td>
                   <input type="text" name="startDate" id="startDate" class="day" readonly style="width:31%;" value="${startDate}" /><a href="javascript:openCal('adminForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
					~
					<input type="text" name="endDate" id="endDate" class="day" readonly style="width:31%;" value="${endDate}" /><a href="javascript:openCal('adminForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
                </td> 
                <th>제목</th>
                <td>
                    <input type="text" name="searchWord" value="${searchWord}" maxlength="100" size="61" onKeyPress="keyCode(event)" onChange="fnChkSpcCharByte()" />
                </td>
            </tr>
            </table>
            
        </div>
        <!-- 1검색조건 // -->
    </div>
    
    <!-- 2 검색내역  -->
    <div class="wrap_con">
        <!-- list -->
        <div class="bbs_list">
            <ul class="tit">
                <li class="tit">업체문의목록</li>
            </ul>
 
            <table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
<!--  					<td><script type="text/javascript">initWiseGrid("WG1", "100%", "380");</script></td> -->
 					<td><div id="ibsheet1"></div></td><!-- IBSheet 위치 -->
				</tr>
			</table>
		</div>
		<!-- 2검색내역 // -->

	</div>
	<!-- 페이징 DIV -->
	<div id="pagingDiv" class="pagingbox1" style="width: 100%;">
		<script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
	</div>		
 
</div>
</form>



</div>
	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>게시판관리</li>
					<li class="last">업체문의관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>