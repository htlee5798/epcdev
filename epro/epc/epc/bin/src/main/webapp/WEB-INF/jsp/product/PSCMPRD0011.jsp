<%--
- Author(s): lth
- Created Date: 2016. 05. 03
- Version : 1.0
- Description : 이미지촬영 스케쥴관리

--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ page import="com.lottemart.epc.common.model.EpcLoginVO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html  xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag.js" ></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- product/PSCMPRD0011 -->

<script type="text/javascript">

var vendorList = new Array();
<%
	EpcLoginVO epcLoginVO = (EpcLoginVO)request.getAttribute("epcLoginVO");
	if(epcLoginVO != null) {
		String[] vendorList = epcLoginVO.getVendorId();
		int size = vendorList.length;
		for(int i = 0; i < size; i++) {
			out.println("vendorList["+i+"] = '" + vendorList[i] + "';");
		}
	}
%>

$(document).ready(function(){

	$('#startDate').click(function() {
		openCal('adminForm.startDate');
	});
	$('#endDate').click(function() {
		openCal('adminForm.endDate');
	});
	$('#search').click(function() {
		doSearch();
	});
	$('#excel').click(function() {
		doExcel();
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
	    {Header:"구분",		       	Type:"Text" ,			SaveName:"crud", 		        			Align:"Center", Width:30, Edit:0, Hidden:true}
	  , {Header:"", 					Type:"CheckBox", 	SaveName:"selected", 	    			Align:"Center", Width:33, Edit:1}
	  , {Header:"순번", 				Type:"Text", 			SaveName:"rankNum", 					Align:"Center", 	  Width:50, Edit:0}
	  , {Header:"스케쥴순번", 	Type:"Text", 			SaveName:"scdlSeqs",  					Align:"Center",  Width:70, Edit:0, Hidden:true}
	  , {Header:"촬영일", 			Type:"Date", 			SaveName:"rservStartDy",  				Align:"Left", Width:90, Edit:0, Format:"yyyy-MM-dd"}
	  , {Header:"시작", 				Type:"Combo", 		SaveName:"rservStartHour",  			Align:"Left", Width:40, Edit:0, ComboCode:"09|10|11|12|13|14|15|16", ComboText:"09|10|11|12|13|14|15|16"}
	  , {Header:"시간",     			Type:"Combo", 		SaveName:"rservStartMin", 			Align:"Left", Width:40, Edit:0, ComboCode:"00|10|20|30|40|50", ComboText:"00|10|20|30|40|50"}
	  , {Header:"종료",        		Type:"Combo", 		SaveName:"rservEndHour", 			Align:"Left", Width:40, Edit:0, ComboCode:"09|10|11|12|13|14|15|16|17", ComboText:"09|10|11|12|13|14|15|16|17"}
	  , {Header:"시간", 				Type:"Combo", 		SaveName:"rservEndMin", 	      		Align:"Left", Width:40, Edit:0, ComboCode:"00|10|20|30|40|50", ComboText:"00|10|20|30|40|50"}
	  , {Header:"업체명", 	    	Type:"Text", 			SaveName:"vendorNm", 	      		Align:"Center", Width:100, Edit:0, Cursor:'pointer', FontUnderline:true}
	  , {Header:"업체코드", 		Type:"Text", 			SaveName:"vendorId", 	      			Align:"Center", Width:70, Edit:0}
	  , {Header:"내용", 	    		Type:"Text", 			SaveName:"scdlMemo", 	      			Align:"Left", Width:230, Edit:0}
	  , {Header:"상태",				Type:"Status",			SaveName:"s_status",              		Align:"Center",    Width:0,  Edit:0, Hidden:true}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	mySheet.FitColWidth();
	
	
	//엑셀 다운로드를 위한 IBSheet
	$('#ibsheet2').hide();
	createIBSheet2(document.getElementById("ibsheet2"), "mySheet2", "100%", "1px");
	mySheet2.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	ibdata.Cols = [
	       	    {Header:"촬영일", 			Type:"Date", 			SaveName:"rservStartDy",  		Align:"Left", Width:90, Edit:0, Format:"yyyy-MM-dd"}
	       	  , {Header:"시작시간", 		Type:"Combo", 		SaveName:"rservStartTm",  	Align:"Left", Width:60, Edit:0, ComboCode:"09|10|11|12|13|14|15|16", ComboText:"09|10|11|12|13|14|15|16"}
	       	  , {Header:"종료시간",        Type:"Combo", 		SaveName:"rservEndTm", 		Align:"Left", Width:60, Edit:0, ComboCode:"09|10|11|12|13|14|15|16|17", ComboText:"09|10|11|12|13|14|15|16|17"}
	       	  , {Header:"업체명", 	    	Type:"Text", 			SaveName:"vendorNm", 	    Align:"Center", Width:100, Edit:0, Cursor:'pointer', FontUnderline:true}
	       	  , {Header:"업체코드", 		Type:"Text", 			SaveName:"vendorId", 	      	Align:"Center", Width:70, Edit:0}
	       	  , {Header:"내용", 	    		Type:"Text", 			SaveName:"scdlMemo", 	      	Align:"Left", Width:230, Edit:0}
	       	];
	IBS_InitSheet(mySheet2, ibdata);
	mySheet2.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	
}); // end of ready
 	


/** ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function doSearch() {
	goPage('1');
}

/** ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function goPage(currentPage){
	var form = document.adminForm;
	var url = '<c:url value="/product/selectScheduleMgrList.do"/>';
	var param = new Object();
	
	var startDate = $('#startDate').val().replace( /-/gi, '' );
 	var endDate  = $('#endDate').val().replace( /-/gi, '' );
 	
 	if(startDate > endDate){
 		alert('시작일자가 종료일자보다 클 수 없습니다.');
 		return;
 	}
	
	param.rowsPerPage 	= $("#rowsPerPage").val();
	param.startDate = form.startDate.value;
	param.endDate = form.endDate.value;
	param.mode = 'search';
	
	loadIBSheetData(mySheet, url, currentPage, null, param);
	
}

/** ********************************************************
 * 저장
 ******************************************************** */
function GridSaveRow(){
	
	//등록 가능 체크
	if(!checkInsertRows())
		return;
	
	//입력값 체크
	if(!checkValidValue()) {
		return;
	}
	
	//촬영시간 중복 체크
	if(checkDuplicate())
		return;	
	
	if(checkVendor('수정')) {
		return;
	}

	if (!confirm("촬영스케쥴정보를 저장하시겠습니까?")) {
		return;
	}

// 	gridObj.setParam('mode', 'save');
// 	var url = '<c:url value="/product/updateSchedule.do"/>';
// 	loadingMask();
// 	gridObj.DoQuery(url, 'selected');	
	mySheet.DoSave( '<c:url value="/product/updateSchedule.do"/>', {Param:'mode='+'save', Quest:0});
// 	mySheet.DoSave(sUrl, {Param:'categoryId='+$("#categoryId").val(), Quest:false, Sync:2});
// 	mySheet.DoSave( '<c:url value="/product/exlnSltYnUpdate.do"/>', {Param:"gubun="+gubun, Quest:0});
}

/** ********************************************************
 * 삭제
 ******************************************************** */
function GridDeleteRow(){
	
	if(!checkRows())
		return;

	if(checkVendor('삭제')) {
		return;
	}

	if (!confirm("촬영스케쥴정보를 삭제하시겠습니까?")) {
		return;
	}


// 	var url = '<c:url value="/product/updateSchedule.do"/>';

	mySheet.DoSave( '<c:url value="/product/updateSchedule.do"/>', {Param:'mode='+'delete', Quest:0});
}
/**********************************************************
 * 선택 로우 체크
 *********************************************************/
function checkRows()
{
	for(i = 1; i <= mySheet.GetTotalRows(); i++)
	{
		if(mySheet.GetCellValue(i, "selected") == 1)
			return true;	
	}
	
	alert("선택된 로우가 없습니다.");
	return false;
}
/**********************************************************
 * 입력 값 체크
 *********************************************************/
function checkValidValue() {

	var date = new Date();
	var yyyy = date.getFullYear().toString();
	var mm = (date.getMonth()+1).toString().length < 2 ? "0"+(date.getMonth()+1) : (date.getMonth()+1).toString();
	var dd = date.getDate().toString().length < 2 ? "0"+date.getDate() : date.getDate().toString();
	var hh = date.getHours().toString().length < 2 ? "0"+date.getHours() : date.getHours().toString();
	var mi = date.getMinutes().toString().length < 2 ? "0"+date.getMinutes() : date.getMinutes().toString();;
	
	var today = yyyy+mm+dd+hh+mi;
	
	for(i = 1; i <= mySheet.GetTotalRows(); i++) {
		if((mySheet.GetCellValue(i, "selected") == 1)&&(mySheet.GetCellValue(i, "crud") == 'I')) {
			var day = mySheet.GetCellValue(i, "rservStartDy");
			var startHour = mySheet.GetCellValue(i, "rservStartHour");
			var startMin = mySheet.GetCellValue(i, "rservStartMin");
			var endHour = mySheet.GetCellValue(i, "rservEndHour");
			var endMin = mySheet.GetCellValue(i, "rservEndMin");
			
			if(today > (day+startHour+startMin)) {
				alert("촬영시작시간을 현재 시간 이후로 설정해주세요.");
				return false;
			}
			if(startHour+startMin >= endHour+endMin) {
				alert("촬영종료시간을 촬영시작시간 이후로 설정해주세요.");
				return false;
			}
			if(mySheet.GetCellValue(i, "rservEndHour") == "17" && mySheet.GetCellValue(i, "rservEndMin") != "00") {
				alert("촬영종료시간은 17시까지만 가능합니다.");
				return false;
			}
		}
	}
	
	return true;
}

function checkVendor(gb) {

	for(i = 1; i <= mySheet.GetTotalRows(); i++) {
		if(mySheet.GetCellValue(i, "selected") == 1) {
			var isMine = false;
			for(j = 0; j < vendorList.length; j++) {
				if(mySheet.GetCellValue(i, "vendorId") == vendorList[j]) {
					isMine = true;
				}
			}
			if(!isMine) {
				alert("타 협력업체 정보는 " + gb + "할 수 없습니다.");
				return true;
			}
		}
	}
}

/**********************************************************
 * insert 로우 체크
 *********************************************************/
function checkInsertRows() {
	
	for(i = 1; i <= mySheet.GetTotalRows(); i++) {
		if((mySheet.GetCellValue(i, "selected") == 1)&&(mySheet.GetCellValue(i, "crud") == 'I'))
			return true;	
	}
	
	alert("저장 가능한 데이터가 없습니다11.");
	return false;
}

/**********************************************************
 * 시간 중복 체크
 ******************************************************** */
function checkDuplicate() {
	var tempArr = new Array();
	
	var index = 0;
	for(i = 1; i <= mySheet.GetTotalRows(); i++) {
		
		var day = mySheet.GetCellValue(i, "rservStartDy");
		var start = mySheet.GetCellValue(i, "rservStartHour") + mySheet.GetCellValue(i, "rservStartMin");
		var end = mySheet.GetCellValue(i, "rservEndHour") + mySheet.GetCellValue(i, "rservEndMin");
		var startIndex = parseInt(day + start);
		var lastIndex = parseInt(day + end);
		
		for(j = startIndex; j < lastIndex; j=j+10) {
			if(j.toString().substring(10, 12) > "50") {
				j = j + 40;
			}
			if(j != lastIndex) {
				tempArr[index] = j;
				index++;
			}
		}
	}
	
	for(i = 0; i < tempArr.length; i++) {
		for(j = i+1; j < tempArr.length; j++) {
			if(tempArr[i] == tempArr[j]) {
				alert("촬영 스케쥴이 중복됩니다.");
				return true;
			}
		}
	}
	return false;
}

/** ********************************************************
 * 등록 팝업 함수
 ******************************************************** */
function goScheduleAddPopup() {
	var targetUrl = '<c:url value="/product/insertSchedulePopupView.do"/>';
	Common.centerPopupWindow(targetUrl, 'insert', {width : 800, height : 320});
}

/** ********************************************************
 * 달력보기 함수
 ******************************************************** */
function goCalendarView() {
	var form = document.adminForm;
	var url = '<c:url value="/product/selectCalendarScheduleList.do"/>';
	form.action = url;

	loadingMask();
	form.submit();
}

/** ********************************************************
 * excel
 ******************************************************** */
function doExcel(){
	var form = document.adminForm;
	
	var startDate = form.startDate.value.replace( /-/gi, '' );
	var endDate   = form.endDate.value.replace( /-/gi, '' );
	
	if(startDate > endDate){
		alert('시작일자가 종료일자보다 클 수 없습니다.');
		return;
	}	

	var xlsUrl = '<c:url value="/product/selectScheduleMgrListExcel.do"/>';
	
	var hideCols = '';
	directExcelDown(mySheet2, 'PSCMSTA000101_' + new Date().yyyymmdd(), xlsUrl, '#adminForm', null, hideCols); // 전체 다운로드 
}


//체인지 이벤트
function mySheet_OnChange(Row, Col, Value, OldValue, RaiseFlag) { 
	
// 	if(mySheet.ColSaveName(Col) == 'rservStartHour' || mySheet.ColSaveName(Col) == 'rservStartMin' || mySheet.ColSaveName(Col) == 'rservEndHour' || mySheet.ColSaveName(Col) == 'rservEndMin'){
	if( Value != OldValue ){
		if(mySheet.GetCellValue(Row, "selected") == "1") {
			mySheet.SetCellValue(Row, "crud", "I");
			mySheet.SetCellBackColor(i, Col, '#F6E5E2');
		} else {
			mySheet.SetCellValue(Row, "crud", "");			
		}
	}
	
}


//셀 클릭 시...
function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {

	if(mySheet.ColSaveName(Col) == 'selected'){
		if(mySheet.GetCellValue(Row, "selected") == "1") {
			mySheet.SetCellValue(Row, "crud", "I");
			mySheet.SetCellBackColor(i, Col, '#F6E5E2');
		} else {
			mySheet.SetCellValue(Row, "crud", "");			
		}
	}
	
	/** ********************************************************
	 * 상세 팝업 함수
	 ******************************************************** */
	if(mySheet.ColSaveName(Col) == 'vendorNm'){
		var targetUrl = '<c:url value="/product/selectSchedulePopup.do"/>?scdlSeqs='+mySheet.GetCellValue(Row, 'scdlSeqs');
		Common.centerPopupWindow(targetUrl, 'detail', {width : 800, height : 320});
	}
	
	
	
}


//데이터 읽은 직후 이벤트
function mySheet_OnSearchEnd(cd,msg) {

	for(i = 1; i <= mySheet.GetTotalRows(); i++) {

		var date = new Date();
		var yyyy = date.getFullYear().toString();
		var mm = (date.getMonth()+1).toString().length < 2 ? "0"+(date.getMonth()+1) : (date.getMonth()+1).toString();
		var dd = date.getDate().toString().length < 2 ? "0"+date.getDate() : date.getDate().toString();
		var hh = date.getHours().toString().length < 2 ? "0"+date.getHours() : date.getHours().toString();
		var mi = date.getMinutes().toString().length < 2 ? "0"+date.getMinutes() : date.getMinutes().toString();;
		var currTime = yyyy+mm+dd+hh+mi;
		
		mySheet.SetCellFontColor(i, "vendorNm", '#1E90FF');
		
		var rservTime = mySheet.GetCellValue(i, "rservStartDy") + mySheet.GetCellValue(i, "rservStartHour") + mySheet.GetCellValue(i, "rservStartMin");
// 		alert(mySheet.GetCellValue(i, "rservStartHour")+"   =   "+mySheet.GetCellValue(i, "rservStartMin")+"   =   "+mySheet.GetCellValue(i, "rservEndHour")+"   =   "+mySheet.GetCellValue(i, "rservEndMin"));
		if(currTime < rservTime) {
			for(j = 1; j <= vendorList.length; j++) {
				if(mySheet.GetCellValue(i, "vendorId") == vendorList[j]) {
					mySheet.SetCellEditable(i, 'rservStartDy', true);
					mySheet.SetCellEditable(i, 'rservStartHour', true);
					mySheet.SetCellEditable(i, 'rservStartMin', true);
					mySheet.SetCellEditable(i, 'rservEndHour', true);
					mySheet.SetCellEditable(i, 'rservEndMin', true);
					// 수정 가능 컬럼에 표시
			        mySheet.SetCellBackColor(i, "rservStartDy",	'#f0fff0');
			        mySheet.SetCellBackColor(i, "rservStartHour",	'#f0fff0');
			        mySheet.SetCellBackColor(i, "rservStartMin",	'#f0fff0');
			        mySheet.SetCellBackColor(i, "rservEndHour",	'#f0fff0');
			        mySheet.SetCellBackColor(i, "rservEndMin",	'#f0fff0');
				}
			}
		}
	}
	mySheet.FitColWidth();
	
}


function mySheet_OnResize(Width, Height) {
	//변경된 정보에 따라 컬럼들의 너비를 재조정한다.
	mySheet.FitColWidth();
}

//저장후 자동으로 호출되는 이벤트(승인처리후 재조회 처리)
function mySheet_OnSaveEnd(code, Msg) {
	alert(code);
	alert(Msg);
	doSearch();
}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

<div class="content_scroll">
<form name="form1" id="form1" style="display:none;">
	<div id="ibsheet2" style="display:none"></div>
</form>
<form name="adminForm" id="adminForm">
<div id="wrap_menu">

	<!--	@ 검색조건	-->
	<div class="wrap_search">
		<!-- 01 : search -->
		<div class="bbs_search">
			<ul class="tit">
				<li class="tit">조회조건</li>
				<li class="btn">
					<a href="#" class="btn" id="excel"><span><spring:message code="button.common.excel" /></span></a>	
					<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
					<a href="javascript: GridDeleteRow();" class="btn"><span><spring:message code="button.common.delete"/></span></a>
					<a href="javascript: GridSaveRow();" class="btn"><span><spring:message code="button.common.save"/></span></a>
					<a href="javascript:goScheduleAddPopup();" class="btn"><span>스케쥴추가</span></a>
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="10%">
				<col width="22%">
				<col width="10%">
				<col width="20%">
				<col width="10%">
				<col width="15%">
			</colgroup> 
			<tr>
				<th><span class="star">*</span> 조회기간</th>
				<td>
					<input type="text" name="startDate" id="startDate" readonly class="day" style="width:31%;" value="${startDate}" /><a href="javascript:openCal('adminForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
					~
					<input type="text" name="endDate" id="endDate" readonly class="day" style="width:31%;" value="${endDate}" /><a href="javascript:openCal('adminForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>
				<th></th>
				<td>
				</td>
				<th></th>
				<td>
				</td>
			</tr>
			</table>
		</div>
		<!-- 1검색조건 // -->
	</div>
		
	<!--	2 검색내역 	-->
	<div class="wrap_con">
		<!-- list -->
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">조회내역</li>
				<li class="btn">
					<a href="javascript:goCalendarView();" class="btn" ><span>캘린더로 보기</span></a>
				</li>
			</ul>
 
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
<!--  					<td><script type="text/javascript">initWiseGrid("WG1", "100%", "400");</script></td> -->
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
					<li>상품관리</li>
					<li class="last">이미지촬영스케쥴관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->

</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>