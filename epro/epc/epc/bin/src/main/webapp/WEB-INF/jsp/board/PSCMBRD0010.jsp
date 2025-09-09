<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"         %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"   %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"               %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://lcnjf.lcn.co.kr/taglib/paging"     %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"      %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"         %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- board/PSCMBRD0010 -->

<script type="text/javascript">

	/** ********************************************************
	 * jQeury 초기화
	 ******************************************************** */
	$(document).ready(function(){

		// START of IBSheet Setting
		createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "350px");
		mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
		mySheet.SetDataAutoTrim(false);
		
		var ibdata = {};
		// SizeMode: 
		ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral}; // 10 row씩 Load
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"순번", 		 	Type:"Int", 	SaveName:"NUM", 		 	  Align:"Center", Width:28,  Edit:0}
		  , {Header:"작성일자", 	 	Type:"Text", 	SaveName:"REG_DATE", 	 	  Align:"Center", Width:80,  Edit:0}
		  , {Header:"작성자", 	     	Type:"Text", 	SaveName:"REG_NM",	 	 	  Align:"Center", Width:90,	 Edit:0}
		  , {Header:"제목",		 	 	Type:"Text", 	SaveName:"TITLE", 	 	 	  Align:"Left",   Width:200, Edit:0, Cursor:'pointer', Color:'red', Ellipsis:true}
		  , {Header:"담당자", 	 	 	Type:"Text", 	SaveName:"ACCEPT_NM", 	 	  Align:"Center", Width:90,	 Edit:0}
		  , {Header:"문의유형",		 	Type:"Text", 	SaveName:"CLM_LGRP_NM",	 	  Align:"Center", Width:70,	 Edit:0}
		  , {Header:"상태",			 	Type:"Text", 	SaveName:"BOARD_PRGS_STS_NM", Align:"Center", Width:60,	 Edit:0}
		  , {Header:"수정자",		 	Type:"Text", 	SaveName:"MOD_ID",	 		  Align:"Center", Width:90,	 Edit:0}
		  , {Header:"수정일자",		 	Type:"Text", 	SaveName:"MOD_DATE", 		  Align:"Center", Width:80,	 Edit:0}
		  
		  , {Header:"게시글번호",  	 	Type:"Text", 	SaveName:"BOARD_SEQ",		Edit:0, Hidden:true }
		  , {Header:"상위게시글번호", 	Type:"Text", 	SaveName:"UP_BOARD_SEQ", 	Edit:0, Hidden:true }
		  , {Header:"조회수", 	 		Type:"Text", 	SaveName:"VIEW_CNT",		Edit:0, Hidden:true }
		];

		
		IBS_InitSheet(mySheet, ibdata);
			
		mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
		mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
		mySheet.FitColWidth();
		
	    $('#search').click(function() {
	        doSearch();
	    });
	    $('#create').click(function() {
	    	goBoardPopup();
	    });
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
		var url = '<c:url value="/board/selectCcQnaSearch.do"/>';
		
		loadIBSheetData(mySheet, url, currentPage, '#adminForm', null);
	}
	
	//셀 클릭 시 팝업 호출
	function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		// 상세정보 함수
		if (Row < 1) return;
		
		var colNm = mySheet.GetCellProperty(1, Col, "SaveName");
		
		if(colNm == 'TITLE'){
			var board_seq	 = mySheet.GetCellValue(Row, 'BOARD_SEQ');
			var up_board_seq = mySheet.GetCellValue(Row, 'UP_BOARD_SEQ');
			var nowBoardSeq	 = mySheet.GetCellValue(Row, 'BOARD_SEQ');
			
			if(up_board_seq != ""){
				board_seq = up_board_seq;
			}
			
			goBoardDetailPopup(board_seq,nowBoardSeq);
		}
		
	}
	
	//데이터 읽은 직후 이벤트
	function mySheet_OnSearchEnd() {
		// 조회된 데이터가 승인완료인 경우는 체크 금지
	 	var rowCount = mySheet.RowCount() + 1;
		for (var i=1; i<rowCount; i++) {
			var viewCnt = mySheet.GetCellValue(i, "VIEW_CNT");
			var upBoardSeq = mySheet.GetCellValue(i, "UP_BOARD_SEQ");
			
			if (viewCnt > 0) {
			    mySheet.SetCellFontColor(i, 'TITLE', '#0000FF');
			    mySheet.SetCellFontUnderline(i, 'TITLE', 0); 
			}
			
			if(upBoardSeq != ""){
				mySheet.SetCellValue(i, "MOD_ID","");
				mySheet.SetCellValue(i, "MOD_DATE","");
			}
		}
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
	    var url ='<c:url value="/board/insertCcQnaPopupForm.do"/>';
		Common.centerPopupWindow(url, 'scmCcQnaPopup', {width : 850, height : 650, scrollBars : 'YES'});
	}
	
	/** ********************************************************
	 * 상세보기 팝업링크
	 ******************************************************** */
	function goBoardDetailPopup(board_seq,nowBoardSeq)
	{
	    var url ='<c:url value="/board/selectCcQnaDetailPopup.do"/>?boardSeq='+board_seq+'&nowBoardSeq='+nowBoardSeq; /* 팝업창 주소 */
		Common.centerPopupWindow(url, 'scmCcQnaDetailPopup', {width : 850, height : 670, scrollBars : 'YES'});
	}
	
    /** ********************************************************
	 * 특수문자 입력여부 및 입력항목의 최대값을 체크한다.
	 ******************************************************** */
	function fnChkSpcCharByte()
	{
        var str = document.adminForm.searchTitle.value;
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
    
    function mySheet_OnSmartResize(Width, Height) {
    	//변경된 정보에 따라 컬럼들의 너비를 재조정한다.
    	mySheet.FitColWidth();
    }
</script>
</head>

<body>

<div id="content_wrap">

<div class="content_scroll">

<form name="adminForm" id="adminForm">
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
                <col width="28%">
                <col width="10%">
                <col width="27%">
                <col width="12%">
                <col width="15%">
            </colgroup>
            <tr>
                <th><span class="star">*</span> 기간</th>
                <td>
                   <input type="text" name="startDate" id="startDate" class="day" readonly style="width:31%;" value="${startDate}" /><a href="javascript:openCal('adminForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
					~
					<input type="text" name="endDate" id="endDate" class="day" readonly style="width:31%;" value="${endDate}" /><a href="javascript:openCal('adminForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
                </td> 
                <th>문의유형</th>
                <td>
                    <select name="clmLgrpCd" id="clmLgrpCd" style="width:70px;">
						<option value="">전체</option>
						<c:forEach items="${codeList1}" var="code" begin="0">
							<c:if test="${code.MINOR_CD eq '200' || code.MINOR_CD eq '400' || code.MINOR_CD eq '500' || code.MINOR_CD eq '700' || code.MINOR_CD eq '950' || code.MINOR_CD eq '980'}">
							<option value="${code.MINOR_CD}"> ${code.CD_NM }</option>
							</c:if>
						</c:forEach>
					</select> 
                </td>
                <th>진행상태</th>
                <td>
                    <select name="brdPrgsStsCd" id="brdPrgsStsCd" style="width:70px;">
						<option value="">전체</option>
						<c:forEach items="${codeList3}" var="code" begin="0">
							<option value="${code.MINOR_CD}"> ${code.CD_NM }</option>
						</c:forEach>
					</select> 
                </td>
            </tr>
            <tr>
               <th>제목</th>
               <td>
               		<input type="text" name="searchTitle" value="${searchTitle}" maxlength="100" size="25" onKeyPress="keyCode(event)" onChange="fnChkSpcCharByte()" />
               </td>
               <th>작성자</th>
               <td>
	               <select name="acceptLocaCd" id="acceptLocaCd" style="width:70px;">
						<option value="">전체</option>
						<c:forEach items="${codeList4}" var="code" begin="0">
							<c:if test="${code.MINOR_CD eq '2' || code.MINOR_CD eq '7'}">
							<option value="${code.MINOR_CD}"> ${code.CD_NM }</option>
							</c:if>
						</c:forEach>
				   </select>
				   <input type="text" name="searchRegNm" id='searchRegNm' value="${searchRegNm}" maxlength="100" size="15" onKeyPress="keyCode(event)" onChange="fnChkSpcCharByte()" style="vertical-align:middle;"/> 
               </td>
				<th>협력업체코드</th>
				<td>
					<c:choose>
							<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
								<c:if test="${not empty vendorId}">
									<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="${vendorId}" style="width:15%;"/>
								</c:if>	
								<c:if test="${empty vendorId}">
									<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
								</c:if>	
								<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
							</c:when>
							<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
								<select name="vendorId" id="vendorId" class="select" " style="width:65%;">
									<option value="">전체</option>
								<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
								
									<c:if test="${not empty vendorId}">
										<option value="${venArr}" <c:if test="${venArr eq vendorId}">selected</c:if>>${venArr}</option>
									</c:if>	
									<c:if test="${empty vendorId}">
										<option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
									</c:if>	
			                        
								</c:forEach>
								</select>
							</c:when>
				</c:choose>
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
                <li class="tit">고객센터문의목록</li>
            </ul>
 
            <table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
 					<td><div id="ibsheet1"></div></td>
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