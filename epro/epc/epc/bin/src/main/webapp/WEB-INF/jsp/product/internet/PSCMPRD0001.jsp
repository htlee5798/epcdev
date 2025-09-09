<%--
- Author(s): lth
- Created Date: 2016. 04. 27
- Version : 1.0
- Description : 인터넷상품관리 (for IBSheet 멀티행)

--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="java.util.Map"%>
<%@page import="com.nhncorp.lucy.security.xss.XssPreventer"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- 신규 공통 css 및 js 파일 INCLUDE -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ page import="lcn.module.common.util.DateUtil" %>
<% 
    String startDate = DateUtil.formatDate(DateUtil.addMonth(DateUtil.getToday(),-1),"-");
    String endDate   = DateUtil.formatDate(DateUtil.getToday(),"-"); 

    // Top에서 넘겨온값
    String gubun = request.getAttribute("gubun")!=null ? (String)request.getAttribute("gubun") : "";
	String asKeywordSelect = request.getAttribute("asKeywordSelect")!=null ? (String)request.getAttribute("asKeywordSelect") : "";
	String asKeywordValue = request.getAttribute("asKeywordValue")!=null ? (String)request.getAttribute("asKeywordValue") : "";
	asKeywordValue = XssPreventer.escape(asKeywordValue);
%>
<!--product/internet/PSCMPRD0001 -->
<script type="text/javascript">

$(document).ready(function(){

	$("#search").click(function() {
		 $("#prodInVal").val("");
    	doSearch();
    });

	$("#excel").click(function() {
    	doExcel();
    });

	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "320px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);

	var ibdata = new Object();
	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
	// ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, Page:10, MergeSheet:msFixedMerge+msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	var bunryu = "대분류/중분류/소분류/세분류명";
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [[
	    {Header:"순번", 			Type:"int" ,	SaveName:"num", 		Align:"Center", Width:33,  Height:18, Edit:0,  Sort:false}
	  , {Header:"인터넷상품코드", 	Type:"Text", 	SaveName:"prodCd", 		Align:"Center", Width:100, Height:18, Edit:0, Cursor:"pointer", Color:"blue", FontUnderline:true} // 수정불가
	  , {Header:"MD상품코드", 		Type:"Text", 	SaveName:"mdProdCd", 	Align:"Center", Width:80, Edit:0} // 수정가능
	  , {Header:"MD판매코드", 		Type:"Text", 	SaveName:"mdSrcmkCd", 	Align:"Center", Width:90, Edit:0}
	  , {Header:"인터넷상품명", 		Type:"Text", 	SaveName:"prodNm",		Align:"Left",   Width:90, Edit:0, Cursor:"pointer", Color:"blue", FontUnderline:true, Ellipsis:true}
	  , {Header:bunryu, 		Type:"Text", 	SaveName:"catNm",		Align:"Left",   Width:90, Edit:0, ColSpan:8, Cursor:"pointer", Color:"blue", FontUnderline:true, Ellipsis:true}
	  , {Header:bunryu, 			Width:70}
	  , {Header:bunryu, 			Width:70}
	  , {Header:bunryu, 			Width:70}
	  , {Header:bunryu, 			Width:70}
	  , {Header:bunryu, 			Width:70}
	  , {Header:bunryu, 			Width:70}
	  , {Header:bunryu, 			Width:70}
	  , {Header:bunryu, 			Width:70}
	  , {Header:bunryu, 			Width:70}
	  , {Header:"", 			Type:"Text", 	SaveName:"tmps1",				Hidden:true}
	  ,	{Header:"",    			Type:"CheckBox",SaveName:"approvalChk",			Hidden:true}
	  , {Header:"", 			Type:"Text", 	SaveName:"mallDivnCd",			Hidden:true}
	  ], [
	    {Header:"",     		Type:"CheckBox",SaveName:"CHK", 				Align:"Center", Width:33, Edit:0,  Sort:false}
	  , {Header:"승인여부", 		Type:"Text", 	SaveName:"aprvYn", 				Align:"Center", Width:100, Height:18, Edit:0} // 수정불가
	  , {Header:"승인일자", 		Type:"Text", 	SaveName:"aprvDate", 			Align:"Center", Width:80, Edit:0} // 수정가능
	  , {Header:"등록일자", 		Type:"Text", 	SaveName:"regDate", 			Align:"Center", Width:90, Edit:0}
	  , {Header:"MD최근판매일", 	Type:"Text", 	SaveName:"mdRecentSellDy",		Align:"Center", Width:90, Edit:0, Format:"Ymd"}
	  , {Header:"상품유형구분", 		Type:"Text", 	SaveName:"onlineProdTypeNm", 	Align:"Center", Width:90, Edit:0}
	  , {Header:"품절여부", 		Type:"Text", 	SaveName:"absenceYn", 			Align:"Center", Width:70, Edit:0}
	  , {Header:"전시여부", 		Type:"Text", 	SaveName:"dispYn", 				Align:"Center", Width:70, Edit:0}
	  , {Header:"대표이미지", 	    Type:"Text", 	SaveName:"haveImg", 			Align:"Center", Width:70, Edit:0}
	  , {Header:"원가", 	        Type:"Int", 	SaveName:"buyPrc", 				Align:"Right", 	Width:70, Edit:0}
	  , {Header:"판매가", 	        Type:"Int", 	SaveName:"currSellPrc", 		Align:"Right", 	Width:70, Edit:0}
	  ,	{Header:"전상법등록", 		Type:"Text" ,	SaveName:"prodCommerce", 		Align:"Center", Width:70, Edit:0}
	  ,	{Header:"전상법승인",    	Type:"Text" ,	SaveName:"prodCommerceApprove", Align:"Center", Width:70, Edit:0}
	  ,	{Header:"이익율",    		Type:"Text" ,	SaveName:"profitRate",			Align:"Center", Width:70, Edit:0}
	  ,	{Header:"EC연동여부",    	Type:"Text" ,	SaveName:"ecLinkYn",			Align:"Center", Width:70, Edit:0}
	  , {Header:"",     		Type:"Text", 	SaveName:"tmps2", 				Hidden:true}
	  ,	{Header:"",    			Type:"Text" ,	SaveName:"prodDivnCd",			Hidden:true}
	  , {Header:"", Hidden:true}
	 ]];
	IBS_InitSheet(mySheet, ibdata);

	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);

	//엑셀 다운로드를 위한 IBSheet
	$("#ibsheet2").hide();
	createIBSheet2(document.getElementById("ibsheet2"), "mySheet2", "100%", "1px");
	mySheet2.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};
	ibdata.Cols = [
	    {Header:"No",		        	Type:"Int" ,	SaveName:"num", 		    	Align:"Center", Width:30,   Edit:0}
	  , {Header:"인터넷상품코드", 			Type:"Text", 	SaveName:"prodCd",  			Align:"Center", Width:70,   Edit:0, Cursor:"pointer", FontUnderline:true}
	  , {Header:"MD상품코드", 				Type:"Text", 	SaveName:"mdProdCd", 			Align:"Center", Width:90,	Edit:0} // 수정가능
	  , {Header:"MD판매코드", 				Type:"Text", 	SaveName:"mdSrcmkCd", 			Align:"Center", Width:90,	Edit:0}
	  , {Header:"인터넷상품명", 				Type:"Text", 	SaveName:"prodNm", 				Align:"Left", 	Width:200,  Edit:0}
	  , {Header:"대분류/중분류/소분류/세분류명", 	Type:"Text", 	SaveName:"catNm", 				Align:"Left", 	Width:300,	Edit:0}
	  , {Header:"승인여부", 	    		Type:"Text", 	SaveName:"aprvYn", 	    		Align:"Left",   Width:60,   Edit:0}
	  , {Header:"승인일자", 	    		Type:"Text", 	SaveName:"aprvDate", 	    	Align:"Left",   Width:70,   Edit:0}
	  , {Header:"등록일자", 	    		Type:"Text", 	SaveName:"regDate", 	    	Align:"Left",   Width:70,   Edit:0}
	  , {Header:"MD최근판매일", 			Type:"Text", 	SaveName:"mdRecentSellDy", 		Align:"Center", Width:80,	Edit:0, Format:"Ymd"}
	  , {Header:"품절여부", 				Type:"Text", 	SaveName:"absenceYn", 			Align:"Center", Width:70,	Edit:0}
	  , {Header:"전시여부", 				Type:"Text", 	SaveName:"dispYn", 				Align:"Center", Width:70,	Edit:0}
	  , {Header:"대표이미지", 				Type:"Text", 	SaveName:"haveImg", 			Align:"Center", Width:70,	Edit:0}
	  , {Header:"판매가", 					Type:"Int", 	SaveName:"buyPrc", 				Align:"Right", 	Width:65,	Edit:0, Format:"#,###,###"}
	  , {Header:"판매가", 					Type:"Int", 	SaveName:"currSellPrc", 		Align:"Right", 	Width:65,	Edit:0, Format:"#,###,###"}
	  , {Header:"전상법등록", 				Type:"Text", 	SaveName:"prodCommerce",		Align:"Center", Width:67,	Edit:0}
	  , {Header:"전상법승인", 				Type:"Text", 	SaveName:"prodCommerceApprove", Align:"Center", Width:67,	Edit:0}
	  ,	{Header:"이익율",    				Type:"Text" ,	SaveName:"profitRate",  		Align:"Center", Width:70,   Edit:0}
	  , {Header:"EC연동여부",    			Type:"Text" ,	SaveName:"ecLinkYn",  			Align:"Center", Width:70,   Edit:0}
	];

	IBS_InitSheet(mySheet2, ibdata);
	mySheet2.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.

	// 탑메뉴에서 검색을 누르면 처리
	if("<%=gubun%>" == "M") {
		$("#asKeywordSelect").val("<%=asKeywordSelect%>");
		$("#asKeywordValue").val("<%=asKeywordValue%>");
		doSearch();
	}

	//input enter 막기
	$("*").keypress(function(e) {
		if (e.keyCode==13) return false;
	});

	$("#excelDown").click(function() {
		exeExcelFile();
	});

	$("#excelInsert").click(function() {
		doExcelInsert();
	});

	$("#excelFormDown").click(function() { // 양식
		doBatchExcelForm('0');
	});

	$("#excelFormDownSearchResultImg").click(function() { // 대표이미지 양식(조회결과)
		doBatchExcelForm('1');
	});

	$("#excelFormDownSearchResultDetail").click(function() { // 상세이미지 양식(조회결과)
		doBatchExcelForm('2');
	});

	$("#imageBatchUpdate").click(function() { // 대표이미지 일괄수정 (팝업)
		var targetUrl = '<c:url value="/product/batchProductImageUploadForm.do"/>'+'?imgType=1&vendorId='+$("#asVendorId").val();
		Common.centerPopupWindow(targetUrl, "batchProductImageUploadForm1", {width : 1300, height : 850, scrollBars : "YES"});
	});

	$("#detailImageBatchUpdate").click(function() { // 상세이미지 일괄수정(팝업)
		var targetUrl = '<c:url value="/product/batchProductImageUploadForm.do"/>'+'?imgType=2&vendorId='+$("#asVendorId").val();
		Common.centerPopupWindow(targetUrl, "batchProductImageUploadForm2", {width : 1300, height : 850, scrollBars : "YES"});
	});

}); // end of ready

/**********************************************************
 * 일괄업로드용 양식 다운
 **********************************************************/
function exeExcelFile(){
	var form = document.dataForm;

	if (!confirm("엑셀 양식을 다운로드 하시겠습니까?")) {
		return;
	}

	form.target = "frameForExcel";
	form.action = '<c:url value="/common/productExcelDown.do"/>';
	form.submit();
}

/**********************************************************
 * 일괄업로드
 **********************************************************/
function doExcelInsert(){
	var form = document.dataForm;
	var fName = $("#createFile").val();
	//alert(fName);

	if (fName.length < 3) {
		alert("업로드할 파일이 없습니다. 먼저 파일을 선택해 주세요.");
		return;
	}

	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if (form.vendorId.value == "") {
	        alert("업체선택은 필수입니다.");
	        form.vendorId.focus();
	        return;
	    }
		</c:when>
	</c:choose>
	loadingMask();
	form.target = "frameForExcel";
	form.action = '<c:url value="/product/selectProductUploadList.do"/>';
	form.submit();
}

function doSearch() {
	goPage("1");
}

// 2015.12.1 김남갑 수정
function goPage(currentPage) {
	var param = new Object();
	var url = '<c:url value="/product/selectProductList.do"/>';

	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if ($("#asVendorId").val() == "") {
	        alert("업체선택은 필수입니다.");
	        $("#asVendorId").focus();
	        return;
	    }
		</c:when>
	</c:choose>

	if ($("#startDt").val() > $("#endDt").val()) {
		alert("조회일자의 선택이 잘못되었습니다. 시작일이 종료일보다 큽니다.");
		return;
	}

	param.rowsPerPage = $("#rowsPerPage").val();
	param.startDt = $("#startDt").val();
	param.endDt = $("#endDt").val();
	param.mode = "search";
	if ($("#chkVal").is(":checked")) {
		$("#chkVal").val("Y");
	} else {
		$("#chkVal").val("N");
	}
	param.chkVal = $("#chkVal").val();

	param.asCmbnMallSellPsbtYn = $("#asCmbnMallSellPsbtYn").val();
	param.asProdDivnCd = $("#asProdDivnCd").val();
	param.asAprvYn = $("#asAprvYn").val();
	param.asDispYn = $("#asDispYn").val();
	param.asImgYn = $("#asImgYn").val();
	param.asDescYn = $("#asDescYn").val();
	param.asCategoryId = $("#asCategoryId").val();
	param.asDateSelect = $("#asDateSelect").val();
	param.asKeywordSelect = $("#asKeywordSelect").val();
	param.asKeywordValue = $("#asKeywordValue").val();
	param.mallDivnCd = $("#asMallDivnCd").val();
	param.asVendorId = $("#asVendorId").val(); 
	param.prodInVal = $("#prodInVal").val();
	param.parentFormName = $("#parentFormName").val();

	loadIBSheetData(mySheet, url, currentPage, null, param);
}

//셀 클릭 시...
function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	// 상세정보 함수
	// if (Row < 2) return;

	var prodCd = mySheet.GetCellValue(Row, "prodCd");

	if (mySheet.ColSaveName(Col) == "prodNm") {
		popupPrdInfo(prodCd);
	}
	
	// 상품링크 함수
	if (mySheet.ColSaveName(Col) == "prodCd") {
		var mallDivnCd = mySheet.GetCellValue(Row, "mallDivnCd");
		var targetUrl = "";

		targetUrl= "https://www.lotteon.com/p/product/LM"+ prodCd;
		Common.centerPopupWindow(targetUrl, "productView", {width : 970, height : 650,scrollBars : "YES"});
	}
}

//데이터 읽은 직후 이벤트
function mySheet_OnSearchEnd() {
	// 조회된 데이터가 승인완료인 경우는 체크 금지
//  	var rowCount = mySheet.RowCount()*2 + 2;
// 	for (var i=2; i<rowCount; i++) {
// 		var aprvYn = mySheet.GetCellValue(i, "aprvYn");
// 		if (aprvYn == "Y" || aprvYn == "X") { // X는 위해상품임.
// 		    // 체크박스 비활성화(row, col, false)
// 		    mySheet.SetCellEditable(i, 1, 0); 
// 		}
// 		var txt = mySheet.GetCellValue(i, "prodNm");
// 		if (txt.length > 28) mySheet.SetToolTipText(i, "prodNm", txt); 
// 	}
// 	mySheet.FitColWidth();
}

//상품상세조회 팝업
function popupPrdInfo(no) {
    var targetUrl = '<c:url value="/product/selectProductFrame.do"/>'+'?prodCd='+no;
    Common.centerPopupWindow(targetUrl, "epcProductInfo", {width : 1024, height : 850, scrollBars : "YES"});
}

//엑셀 저장
// var goExcelCnt  = 0;
// var oriExcelCnt = 0;

function doBatchExcelForm(imgType) {

	var form = document.dataForm;
	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
	if ($("#asVendorId").val() == "") {
		alert("업체선택은 필수입니다.");
		$("#asVendorId").focus();
		return;
	}
		</c:when>
	</c:choose>

	if ($("#startDt").val() > $("#endDt").val()) {
		alert("조회일자의 선택이 잘못되었습니다. 시작일이 종료일보다 큽니다.");
		return;
	}

	$("#imgType").val(imgType);

	form.target = "frameForExcel";
	form.action = '<c:url value="/product/batchProductExcelUploadForm.do"/>';
	form.submit();
}

function doExcel() {
    goExcelCnt = 0;

    if (confirm("엑셀 다운로드 하시겠습니까?")) {
    	var form = document.dataForm;

    	if ($("#startDt").val() > $("#endDt").val()) {
    		alert("조회일자의 선택이 잘못되었습니다. 시작일이 종료일보다 큽니다.");
    		return;
    	}

    	var xlsUrl = '<c:url value="/product/selectProductExcel.do"/>';
		// var xlsUrl = '<c:url value="/product/selectProductList.do"/>';
    	var param = new Object();
    	
		// param.rowsPerPage = 65000;
    	param.startDt = $("#startDt").val();
    	param.endDt = $("#endDt").val();
    	param.mode = "excel";
    	if ($("#chkVal").is(":checked")) {
    		$("#chkVal").val("Y");
    	} else {
    		$("#chkVal").val("N");
    	}
    	param.chkVal = $("#chkVal").val();

    	param.asCmbnMallSellPsbtYn = $("#asCmbnMallSellPsbtYn").val();
    	param.asProdDivnCd = $("#asProdDivnCd").val();
    	param.asAprvYn = $("#asAprvYn").val();
    	param.asDispYn = $("#asDispYn").val();
    	param.asImgYn = $("#asImgYn").val();
    	param.asDescYn = $("#asDescYn").val();
    	param.asCategoryId = $("#asCategoryId").val();
    	param.asDateSelect = $("#asDateSelect").val();
    	param.asKeywordSelect = $("#asKeywordSelect").val();
    	param.asKeywordValue = $("#asKeywordValue").val();
    	param.mallDivnCd = $("#asMallDivnCd").val();
    	param.asVendorId = $("#asVendorId").val();
    	//alert(param.prodInVal);

    	var hideCols = "";
    	directExcelDown(mySheet2, "PSCMPRD0001_" + new Date().yyyymmdd(), xlsUrl, null, param, hideCols); // 전체 다운로드 
    }
}

//카테고리 팝업
function doPopupCategory() {
    var targetUrl = '<c:url value="/common/selectCategoryPopUpView.do"/>?categoryTypeCd=01'; //01:상품
    Common.centerPopupWindow(targetUrl, "epcCategoryPopup", {width : 560, height : 485});
}

//카테고리 검색창으로 부터 받은 카테고리 정보 입력
function setCategoryInto(asCategoryId, asCategoryNm) { // 이 펑션은 카테고리 검색창에서 호출하는 펑션임
    var form = document.dataForm;
    form.asCategoryId.value = asCategoryId;
    form.asCategoryNm.value = asCategoryNm;
}

//카테고리 입력 정보 삭제
function doDdeleteCategory() {
    var form = document.dataForm;
    form.asCategoryId.value = "";
    form.asCategoryNm.value = "";
}

function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임
	$("#asVendorId").val(strVendorId);
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
<form name="dataForm" id="dataForm" method="post" enctype="multipart/form-data">
<iframe name="frameForExcel" style="display:none;"></iframe>
<input type="hidden" name="parentFormName" id="parentFormName" value="" />
<input type="hidden" name="prodInVal" id="prodInVal" value="" />
<input type="hidden" name="imgType" id="imgType" value="" />
    <div id="wrap_menu">
        <!--    @ 검색조건  -->
        <div class="wrap_search">
            <!-- 01 : search -->
            <div class="bbs_search">
                <ul class="tit">
                    <li class="tit">상품정보 조회</li>
                    <li class="btn">
                        <a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
                    </li>
                </ul>
                <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
	                <colgroup>
	                    <col width="12%">
	                    <col width="15%">
	                    <col width="12%">
	                    <col width="13%">
	                    <col width="11%">
	                    <col width="10%">
	                    <col width="11%">
	                    <col width="16%">
	                </colgroup>
	                <tr>
	                    <th>상품종류</th>
	                    <td>
	                        <select name="asCmbnMallSellPsbtYn" id="asCmbnMallSellPsbtYn" style="width:80px;">
	                            <option value="">전체</option>
	                            <option value="N">근거리</option>
	                        </select>
	                    </td>
	                    <th>상품구분여부</th>
	                    <td>
	                        <select name="asProdDivnCd" id="asProdDivnCd" class="select" style="width:80px;">
	                            <option value="">전체</option>
	                            <c:forEach items="${prodDivnCdList}" var="code" begin="0">
	                                <option value="${code.CODE_CD}">${code.CODE_NM}</option>
	                            </c:forEach>
	                        </select>
	                    </td>
	                    <th>승인여부</th>
	                    <td>
	                        <select name="asAprvYn" id="asAprvYn" style="width:60px;">
	                        <option value="">전체</option>
	                        <option value="Y">승인</option>
	                        <option value="N">비승인</option>
	                        </select>
	                    </td>
	                    <th>전시여부</th>
	                    <td>
	                        <select name="asDispYn" id="asDispYn" style="width:80px;">
	                        <option value="">전체</option>
	                        <option value="Y">전시</option>
	                        <option value="N">비전시</option>
	                        </select>
	                    </td>
	                </tr>
	                <tr>
	                    <th>대표이미지<br/>등록여부</th>
	                    <td>
	                        <select name="asImgYn" id="asImgYn" style="width:80px;">
	                            <option value="">전체</option>
	                            <option value="1">Yes</option>
	                            <option value="0">No</option>
	                        </select>
	                    </td>
	                    <th>상세이미지<br/>등록여부</th>
	                    <td>
	                        <select name="asDescYn" id="asDescYn" style="width:80px;">
	                            <option value="">전체</option>
	                            <option value="1">Yes</option>
	                            <option value="0">No</option>
	                        </select>
	                    </td>
		                <th>카테고리</th>
		                <td colspan="3">
		                    <input type="text" name="asCategoryId" id="asCategoryId" class="day" readonly style="width:39%;" />
		                    <input type="text" name="asCategoryNm" id="asCategoryNm" class="day" readonly style="width:39%;" />
		                    <a href="javascript:doPopupCategory();"  ><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_search.gif" alt="" class="middle" /></a>
		                    <a href="javascript:doDdeleteCategory();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_eraser.gif" alt="" class="middle" /></a>
		                </td>
	                </tr>
	                <tr>
					<th>상품일괄조회</th>
					<td colspan="7">
						<input type="file" name="createFile" id="createFile" style="width:545px;" value="파일이름"  />
						<a href="#" id="excelDown" class="btn"><span>양식</span></a>
						<a href="#" id="excelInsert" class="btn"><span>업로드</span></a>
					</td>
	                </tr>
	                <tr>
	                    <th>일자검색조건</th>
	                    <td>
	                        <select name="asDateSelect" id="asDateSelect" style="width:102px;">
	                            <option value="01">온라인등록일</option>
	                            <option value="02">승인일</option>
	                            <option value="03">MD등록일</option>
	                        </select>
	                    </td>
	                    <th><input type="checkbox" name="chkVal" id="chkVal" value="N" align="center"> 조회일자</th>
	                    <td colspan="3">
	                        <input type="text" name="startDt" id="startDt" value="<%=startDate%>" class="day" onclick="javascript:openCal('dataForm.startDt');" style="width:25%;text-align:center;" readOnly />
	                        <a href="javascript:openCal('dataForm.startDt');"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
	                        ~
	                        <input type="text" name="endDt" id="endDt" value="<%=endDate%>" class="day" onclick="javascript:openCal('dataForm.endDt');" style="width:25%;text-align:center;" readOnly />
	                        <a href="javascript:openCal('dataForm.endDt');"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
	                    </td>
	                    <th>협력업체코드</th>
	                    <td>
	                        <c:choose>
								<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
									<input type="text" name="asVendorId" id="asVendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
									<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
								</c:when>
								<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
									<select name="asVendorId" id="asVendorId" class="select">
										<option value="">전체</option>
										<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
					                        <option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
										</c:forEach>
									</select>
								</c:when>
							</c:choose>
	                    </td>
	                </tr>
	                <tr>
	                    <th>일반검색조건</th>
	                    <td>
	                        <select name="asKeywordSelect" id="asKeywordSelect" style="width:102px;">
	                        <option value="01">인터넷상품코드</option>
	                        <option value="02">MD상품코드</option>
	                        <option value="03">MD판매코드</option>
	                        <option value="04">인터넷상품명</option>
	                        </select>
	                    </td>
	                    <th>검색어</th>
	                    <td colspan="5">
	                        <input type="text" name="asKeywordValue" id="asKeywordValue" maxlength="1000" size="70" />
	                    </td>
	                </tr>
                </table>
            </div>
            <!-- 1검색조건 // -->
            <div class="bbs_search" style="margin-top:5px;">
                <ul class="tit">
                    <li class="tit">이미지 일괄 수정</li>
                    <li class="btn">
                    </li>
                </ul>
                <table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
	                <colgroup>
	                    <col width="100%">
	                </colgroup>
	                <tr>
	                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                    	<a href="#" id="excelFormDown" class="btn"><span>양식</span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                    	<a href="#" id="excelFormDownSearchResultImg" class="btn"><span>대표이미지 양식(조회결과)</span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                    	<a href="#" id="excelFormDownSearchResultDetail" class="btn"><span>상세이미지 양식(조회결과)</span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                    	<a href="#" id="imageBatchUpdate" class="btn"><span>대표이미지 일괄수정</span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                    	<a href="#" id="detailImageBatchUpdate" class="btn"><span>상세이미지 일괄수정</span></a>
	                    </td>
	                </tr>
	            </table>
            </div>
        </div>
	    <!--    2 검색내역  -->
	    <div class="wrap_con">
	        <!-- list -->
	        <div class="bbs_list">
	            <ul class="tit">
	                <li class="tit">상품목록</li>
	                <li class="btn">
	                    <a href="#" class="btn" id="excel"><span><spring:message code="button.common.excel"/></span></a>
	                </li>
	            </ul>
				<tablecellpadding="0" cellspacing="0" border="0" width="100%">
	                <tr>
	                    <div id="ibsheet1"></div>
	                </tr>
	            </table>
	        </div>
	    </div>
	    <div id="pagingDiv" class="pagingbox1" style="width: 100%;">
            <script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script> <!-- 페이징 -->
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
                    <li class="last">인터넷상품관리</li>
                </ul>
            </div>
        </div>
    </div>
    <!-- footer //-->
</div>
</body>
</html>