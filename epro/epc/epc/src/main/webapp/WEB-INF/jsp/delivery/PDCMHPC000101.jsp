<%--
- Author(s): mg098
- Created Date: 2019. 04. 11
- Version : 1.0
- Description : 해피콜 실적 집계
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>

<%
DecimalFormat df = new DecimalFormat("00");
Calendar currentCalendar = Calendar.getInstance();

currentCalendar.add(currentCalendar.DATE, -30);
String strYear31   = Integer.toString(currentCalendar.get(Calendar.YEAR));
String strMonth31  = df.format(currentCalendar.get(Calendar.MONTH) + 1);
String strDay31   = df.format(currentCalendar.get(Calendar.DATE));
String strDate31 = strYear31 + strMonth31 + strDay31;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<head>

<style type="text/css">
.idtit{ float:right; padding:6px 0 0 13px; color:#F2F2F2; font-weight:bold;}
</style>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- delivery/PDCMHPC000101 -->

<script type="text/javascript">

$(document).ready(function(){
	
	if(!$("#loginSession").val()){
		alert($("#loginSession").val());
	}

	if($("#loginSession").val()=='false'){
		alert('<spring:message code="msg.login.necessary"/>');
		//메인 화면으로 이동
		var targetUrl = '<c:url value="/main/intro.do"/>';
		self.window.open('','_self'); 
		self.window.open(targetUrl,'_top');
		return;
	}
	
	//입력폼에서 엔터 시 조회처리
	$("input").keypress( function( e){
		if (e.which == 13) {
			doSearch();
		}
	});
	
	//선택항목에서 엔터 시 조회처리
	$("select").keypress( function( e){
		if (e.which == 13) {
			doSearch();
		}
	});
	
	//검색
    $('#search').click(function() {
        doSearch();
    });
	
	var loginId = "${loginId}";

	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "900px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibdata = {};
	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"", 	        		Type:"CheckBox", 	SaveName:"chk",				Align:"Center", MinWidth:40, Sort:false, Hidden:false}
	  , {Header:"해피콜아이디|해피콜아이디",	Type:"Text", 		SaveName:"HPCL_ID",		 	Align:"Center", MinWidth:100, Edit:0}
	  , {Header:"냉장|할당 건수", 			Type:"AutoSum", 	SaveName:"HPCL_ASMT_COLD",	Align:"Center", MinWidth:100, Edit:0, Format:"#,##0.##"}
	  , {Header:"냉장|확정 건수",	 		Type:"AutoSum", 	SaveName:"HPCL_COMP_COLD",	Align:"Center", MinWidth:100, Edit:0, Format:"#,##0.##"}
	  , {Header:"냉동|할당 건수",			Type:"AutoSum", 	SaveName:"HPCL_ASMT_ICE",	Align:"Center", MinWidth:100, Edit:0, Format:"#,##0.##"}
	  , {Header:"냉동|확정 건수",			Type:"AutoSum", 	SaveName:"HPCL_COMP_ICE",	Align:"Center", MinWidth:100, Edit:0, Format:"#,##0.##"}
	  , {Header:"etc|할당 건수",			Type:"AutoSum", 	SaveName:"HPCL_ASMT_ETC",	Align:"Center", MinWidth:100, Edit:0, Format:"#,##0.##", Hidden:true}
	  , {Header:"etc|확정 건수",			Type:"AutoSum", 	SaveName:"HPCL_COMP_ETC",	Align:"Center", MinWidth:100, Edit:0, Format:"#,##0.##", Hidden:true}
	]; 
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	//mySheet.SetFrozenCol(4);
	
	mySheet.SetSumValue(mySheet.LastRow(),1,"합계");
	mySheet.SetCellAlign(mySheet.LastRow(),0,"Center");
	
	if (loginId != "hpc1" && loginId != "hpc2" && loginId != "hpc3" && loginId != "hpc4" && loginId != "hpc5") {
		mySheet.SetColHidden("chk", 1);
	}

}); // end of ready


	/** ********************************************************
	 * 조회 처리 함수
	 ******************************************************** */
	function doSearch() {
		goPage('1');
	}

	/**********************************************************
	 * 페이지 이동 시
	 ******************************************************** */
	function goPage(currentPage){

		var form = document.hpclForm;		
		var url = '<c:url value="/delivery/selectHappyCallCount.do"/>';
		
		loadIBSheetData(mySheet, url, currentPage, '#hpclForm', null);
		
	}

	
	/*
	 * 로딩중 표시
	 */ 
	function loadingMask2(){
		var childTop    = (document.body.clientHeight) / 2.5;
		var childLeft   = (document.body.clientWidth)  / 3;

		var loadingDiv = $('<span id="loadingLayer" style="position:absolute; left:'+childLeft+'px; top:'+childTop+'px; width:100%; height:100%; z-index:10001"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></span>').appendTo($("body"));
		loadingDiv.show();
		var loadingDivBg = $('<iframe id="loadingLayerBg" frameborder="0" style="filter:alpha(opacity=0);position:absolute; left:0px; top:0px; width:100%; height:100%; z-index:10000"><img id="ajax_load_type_img" src="/js/jquery/res/loading.gif"></iframe>').appendTo($("body"));
		loadingDivBg.show();
	}
	
	/**
	 * 페이지 인덱스 렌더링
	 */
	function getRenderHtml(totalCnt, fromPageNo, toPageNo, lastPageNo, curPage, previousPage, nextPage, funcName, rowsPerPage){
		var htmlString = "";
		
		/* 20180129 이재하 담당 요청으로 홍진옥 단위 수정 */
		var pageRow = '50';
		var nextRowTerm = '100';
		var nextRowTerm2 = '200';
		var nextRowTerm3 = '300';
		var nextRowTerm4 = '400';
		var nextRowTerm5 = '500';
		var nextRowTerm6 = '1000';

		htmlString += "<p class=\"total\">[총건수 <span>";
		if(totalCnt == 0){
			htmlString += "0 </span> ]</p>";
		}else{
			htmlString += totalCnt + "</span> ]</p>";
		}
		
		htmlString += "<div class=\"paging1\">";
		
	    // 첫 페이지
	    if (curPage == 1) {
	    	htmlString += "<span class=\"fst\" ></span>";
	    } else {
	    	htmlString += "<span class=\"fst\" onclick=\"javascript:" + funcName + "(1);\" ><a href=\"#\"><img src=\"/images/epc/layout/btn_first.gif\" alt=\"첫페이지\" class=\"middle\" /></a></span>";
	    }
	    
	    // 이전 페이지 출력
	    if(previousPage !='1'){
	    	htmlString += "<span class=\"pre\" onclick=\"javascript:" + funcName + "('" + previousPage + "');\" ><a href=\"#\"><img src=\"/images/epc/layout/btn_prev.gif\" alt=\"이전페이지\" class=\"middle\" /></a></span>";
	    }else{
	    	htmlString += "<span class=\"pre\" ></span>";
	    }
	    

	    // 페이지 인덱스
	    for (var i=fromPageNo; i<=toPageNo; i++) {
	    	var css = "";
	    	if(i > fromPageNo){
	    		css = "bar";
	    	}
	    	
	    	if (i == curPage){
	    		htmlString += "<span class=\""+css+" bar2\" ><a href=\"#\">" + i + "</a></span>";
	    	}else{
	    		htmlString += "<span class=\""+css+"\" onclick=\"javascript:" + funcName + "('" + i + "');\" ><a href=\"#\">" + i + "</a></span>";
	    	}
	    }

	    // 이후 페이지 출력
	    if (curPage == nextPage) {
	        htmlString += "<span class=\"nxt\" ></span>";
	    } else {
	    	htmlString += "<span class=\"nxt\" onclick=\"javascript:" + funcName + "('" + nextPage + "');\" ><a href=\"#\"><img src=\"/images/epc/layout/btn_next.gif\" alt=\"다음페이지\" class=\"middle\" /></a></span>";
	    }

	    // 마지막 페이지 출력
	    if (curPage == lastPageNo) {
	        htmlString += "<span class=\"end\" ></span>";
	    } else {
	    	htmlString += "<span class=\"end\" onclick=\"javascript:" + funcName + "('" + lastPageNo + "');\" ><a href=\"#\"><img src=\"/images/epc/layout/btn_end.gif\" alt=\"끝페이지\" class=\"middle\" /></a></span>";
	    }
	    
	    htmlString += "</div>";
	    htmlString += "<p class=\"listCnt\"><span>";
	    htmlString += "<select name=\"rowsPerPage\" id=\"rowsPerPage\" onChange=\"javascript:doSearch();\" class=\"select\" align=\"right\">";
        htmlString += "	<option value=\""+pageRow+"\" "+(rowsPerPage == pageRow ? 'selected':'')+">"+pageRow+"건</option>";
	    htmlString += "	<option value=\""+nextRowTerm+"\" "+(rowsPerPage == nextRowTerm ? 'selected':'')+">"+nextRowTerm+"건</option>";
	    htmlString += "	<option value=\""+nextRowTerm2+"\" "+(rowsPerPage == nextRowTerm2 ? 'selected':'')+">"+nextRowTerm2+"건</option>";
	    htmlString += "	<option value=\""+nextRowTerm3+"\" "+(rowsPerPage == nextRowTerm3 ? 'selected':'')+">"+nextRowTerm3+"건</option>";
	    htmlString += "	<option value=\""+nextRowTerm4+"\" "+(rowsPerPage == nextRowTerm4 ? 'selected':'')+">"+nextRowTerm4+"건</option>";
	    htmlString += "	<option value=\""+nextRowTerm5+"\" "+(rowsPerPage == nextRowTerm5 ? 'selected':'')+">"+nextRowTerm5+"건</option>";
	    htmlString += "	<option value=\""+nextRowTerm6+"\" "+(rowsPerPage == nextRowTerm6 ? 'selected':'')+">"+nextRowTerm6+"건</option>";
	    htmlString += "</select>";
	    htmlString += "</span></p>";

	    return htmlString;
	}

	
	//데이터 읽은 직후 이벤트
	function mySheet_OnSearchEnd(cd,msg) {
		
		if(RETURN_IBS_OBJ.mode == "NOSESSION"){	//세션이 종료되었으면
			alert("로그인이 필요합니다.");
			//메인 화면으로 이동
			var targetUrl = '<c:url value="/main/intro.do"/>';
			window.open(targetUrl,'_top');  
			return;
		}
	}
		 
 
	
	
	
</script>

</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<body>
<c:set var="startDate" value='<%=strDate31%>' />

<form name="hpclForm" id="hpclForm" method="post">
<div id="content_wrap">
	<div class="content_scroll">
		<div id="wrap_menu">
		    <div class="wrap_search">   
		        <div class="bbs_search">
		            <ul class="tit">
		                <li class="tit">해피콜실적집계</li>
		                <li class="btn">
		                    <a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
		                </li>
		            </ul>
		            <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:10%" />
						<col style="width:12%" />
						<col style="width:12%" />
						<col style="width:12%" />
						<col style="width:12%" />
						<col style="width:12%" />
						<col style="width:12%" />
						<col style="width:12%" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> 접수기간</th>
						<td colspan="3">
							<input type="text" name="rtnAcceptFromDt" id="rtnAcceptFromDt" value="<c:out value="${fn:substring(startDate,0,4)}-${fn:substring(startDate,4,6)}-${fn:substring(startDate,6,8)}" />" style="width:30%;" class="day" <%-- onclick="javascript:openCal('hpclForm.rtnAcceptFromDt')"--%> readOnly />
							<a href="javascript:openCal('hpclForm.rtnAcceptFromDt')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>   
							~
							<input type="text" name="rtnAcceptToDt" id="rtnAcceptToDt" value="<c:out value="${confInfo.currentDate}" />" style="width:30%;" class="day" <%--onclick="javascript:openCal('hpclForm.rtnAcceptToDt')"--%> readOnly />
							<a href="javascript:openCal('hpclForm.rtnAcceptToDt')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
							<input type="hidden" name="loginSession" id="loginSession" value="${loginSession}"/>  
						</td>
					</tr>
					</table>
		        </div>
		    </div>
		    <div class="wrap_con">
		        <div class="bbs_list">
		            <table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
		                <tr>
		                    <td><div id="ibsheet1"></div></td><!-- IBSheet 위치 -->
		                </tr>
		            </table>
		        </div>
		    </div>
		
			<div id="pagingDiv" class="pagingbox1" style="width: 100%;">
		 		<script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
		 	</div>
		</div>
	</div>
	<div id="footer">
	    <div id="footbox">
	        <div class="msg" id="resultMsg"></div>
	        <div class="location">
	            <ul>
	                <li>홈</li>
	                <li>배송관리</li>
	                <li class="last">해피콜실적집계</li>
	            </ul>
	        </div>
	    </div>
	</div>
</div>
</form>
</body>
</html>