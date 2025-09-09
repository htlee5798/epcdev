<%--
 /**
  * @Class Name  : PSCMSBT0002.jsp
  * @Description : 매출공제조회  
  * @Modification Information
  * @
  * @  수정일             수정자                   수정내용
  * @ -------  --------    ---------------------------
  * @2015.11.25            skc			신규작성
  * @
  *  @author
  *  @since
  *  @version
  *  @see
  *
  */
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
<%@page import="lcn.module.common.util.DateUtil"%>
<% 
	String curYear  = DateUtil.getCurrentYearAsString();
	String curMonth = DateUtil.getCurrentMonthAsString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- PSCMSBT0002 -->
<html>
<head>
<%@ include file="/common/scm/scmCommon.jsp" %>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<script type="text/javascript">
$(document).ready(function(){
	setHeader();
	setHeaderDetail();

	//input enter 막기
	$("*").keypress(function(e){
		if(e.keyCode==13) return false;
	});

	$('#search').click(function() {
		doSearch();
	});
	// EXCEL버튼 클릭
	$('#excel').click(function() {
		downExcel();
	});

	// EXCEL버튼 클릭
	$('#excelDetail').click(function() {
		downExcelDetail();
	});
});

/** *****************************************************
 *  IBSheet 헤더 부분 초기화
 ****************************************************** */
function setHeader() {

	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "180px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	mySheet.SetDataAutoTrim(false);

	var ibdata = {};
	// SizeMode: 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msBaseColumnMerge + msHeaderOnly  }; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1}; 
	var ascDivn = $("#asCmbSeq option:selected").val();
	if(ascDivn == "3"){
		ibdata.Cols = [
			{Header:"세금유형|세금유형",			Type:"Text",	SaveName:"TAXAT_DIVN",		Align:"Center",		Width:85,    Edit:0, ColMerge:0}
		  , {Header:"업체코드|업체코드",			Type:"Text",	SaveName:"SALE_VENDOR_ID",	Align:"Center",		Width:85,    Edit:0, ColMerge:0}
		  , {Header:"업체명|업체명",				Type:"Text",	SaveName:"SALE_VENDOR_NM",	Align:"Center",		Width:95,	 Edit:0, ColMerge:0}
		/*, {Header:"하위업체코드|하위업체코드",		Type:"Text",	SaveName:"VENDOR_ID",		Align:"Center",		Width:85,   Edit:0, ColMerge:0}
		  , {Header:"하위업체명|하위업체명",		Type:"Text",	SaveName:"VENDOR_NM",		Align:"Center",		Width:95,	 Edit:0, ColMerge:0} */
		  , {Header:"매출|공급가액",		 		Type:"AutoSum",	SaveName:"VOS",				Align:"Right",		Width:80,	 Edit:0, ColMerge:0}
		  , {Header:"매출|부가세",				Type:"AutoSum",	SaveName:"TAX_AMT",			Align:"Right",		Width:80,	 Edit:0, ColMerge:0}
		  , {Header:"매출|순매출액",				Type:"AutoSum",	SaveName:"S_ACCOUNT",		Align:"Right",		Width:70,	 Edit:0, ColMerge:0} 
		/*, {Header:"매출|주문취소금액",			Type:"AutoSum",	SaveName:"CANCEL_ORDER_AMT",		Hidden:true} */ 
		  , {Header:"매출|총매출금액",			Type:"AutoSum",	SaveName:"TOTAL_ORDER_AMT",	Align:"Right", 	Width:80,	 Edit:0, ColMerge:0}
		  , {Header:"매출|수수료금액\n(에누리 포함)",Type:"AutoSum",	SaveName:"PROFIT_AMT",		Align:"Right", 	Width:80,	 Edit:0, ColMerge:0, Format:"#,###,###,###.##"}
		  , {Header:"공제|업체에누리",			Type:"AutoSum",	SaveName:"VEN_AMT",			Align:"Right", 	Width:70,	 Edit:0, ColMerge:0}
		  , {Header:"공제|자사에누리",			Type:"AutoSum",	SaveName:"COM_AMT",			Align:"Right", 	Width:70,	 Edit:0, ColMerge:0}
		/*, {Header:"공제|카드사분담금",			Type:"AutoSum",	SaveName:"CARDCO_CARD_COM_AMT",	Align:"Right", 	Width:70,	 Edit:0, ColMerge:0}
		  , {Header:"공제|마일리지",				Type:"AutoSum",	SaveName:"MILEAGE_DIV_AMT",	Align:"Right", 	Width:70,	 Edit:0, ColMerge:0}
		  , {Header:"공제|배너광고비",			Type:"AutoSum",	SaveName:"BANN_DIV_AMT",	Align:"Right", 	Width:70,	 Edit:0, ColMerge:0} */
		  , {Header:"배송료\n(1,2회차 미포함)|배송료\n(1,2회차 미포함)",	Type:"AutoSum", SaveName:"SALE_DELIVERY_AMT",	 	 	Align:"Right", 	Width:70,	Edit:0, ColMerge:0}
		/*, {Header:"지급액|지급액",				Type:"AutoSum", SaveName:"A_PAYABLE",	Align:"Right",	Width:70,	 Edit:0, ColMerge:0} */
		       ];
	  }else{
		// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
		ibdata.Cols = [
			{Header:"세금유형|세금유형",			Type:"Text",		SaveName:"TAXAT_DIVN",			Align:"Center", 	Width:85,    Edit:0, ColMerge:0}
		  , {Header:"업체코드|업체코드",			Type:"Text",		SaveName:"SALE_VENDOR_ID",		Align:"Center", 	Width:85,    Edit:0, ColMerge:0}
		  , {Header:"업체명|업체명",				Type:"Text",		SaveName:"SALE_VENDOR_NM",		Align:"Center", 	Width:95,	 Edit:0, ColMerge:0}
		/*, {Header:"하위업체코드|하위업체코드",		Type:"Text",		SaveName:"VENDOR_ID",			Align:"Center",  	Width:85,   Edit:0, ColMerge:0}
		  , {Header:"하위업체명|하위업체명", 		Type:"Text",		SaveName:"VENDOR_NM",			Align:"Center", 	Width:95,	 Edit:0, ColMerge:0} */
		  , {Header:"매출|공급가액",		 		Type:"AutoSum", 	SaveName:"VOS",					Align:"Right", 	Width:80,	 Edit:0, ColMerge:0}
		  , {Header:"매출|부가세",				Type:"AutoSum",		SaveName:"TAX_AMT",				Align:"Right", 	Width:80,	 Edit:0, ColMerge:0}
		  , {Header:"매출|순매출액",		 		Type:"AutoSum",		SaveName:"S_ACCOUNT",			Align:"Right", 	Width:70,	 Edit:0, ColMerge:0} 
		/*, {Header:"매출|주문취소금액",			Type:"AutoSum",		SaveName:"CANCEL_ORDER_AMT",	 	 		Hidden:true} */ 
		  , {Header:"매출|총매출금액",			Type:"AutoSum",		SaveName:"TOTAL_ORDER_AMT",	 	Align:"Right", 	Width:80,	 Edit:0, ColMerge:0}
		  , {Header:"매출|수수료금액\n(에누리 포함)",Type:"AutoSum",		SaveName:"PROFIT_AMT",			Align:"Right", 	Width:80,	 Edit:0, ColMerge:0, Format:"#,###,###,###.##"}
		  , {Header:"공제|업체에누리",			Type:"AutoSum",		SaveName:"VEN_AMT",				Align:"Right", 	Width:70,	 Edit:0, ColMerge:0}
		  , {Header:"공제|자사에누리",			Type:"AutoSum",		SaveName:"COM_AMT",				Align:"Right", 	Width:70,	 Edit:0, ColMerge:0}
		/*, {Header:"공제|카드사분담금",			Type:"AutoSum",		SaveName:"CARDCO_CARD_COM_AMT",	Align:"Right", 	Width:70,	 Edit:0, ColMerge:0}
		  , {Header:"공제|마일리지",		 		Type:"AutoSum",		SaveName:"MILEAGE_DIV_AMT",		Align:"Right", 	Width:70,	 Edit:0, ColMerge:0}
		  , {Header:"공제|배너광고비",			Type:"AutoSum",		SaveName:"BANN_DIV_AMT",		Align:"Right", 	Width:70,	 Edit:0, ColMerge:0} */
		  , {Header:"배송료\n(3회차 지급)|배송료\n(3회차 지급)",Type:"AutoSum",	SaveName:"SALE_DELIVERY_AMT",	Align:"Right", 	Width:70,	 Edit:0, ColMerge:0}
		/*, {Header:"지급액|지급액",				Type:"AutoSum",		SaveName:"A_PAYABLE",			Align:"Right", 	Width:70,	 Edit:0, ColMerge:0} */ 
		];
	}
	
	IBS_InitSheet(mySheet, ibdata);
//	mySheet.SetFrozenCol(4);
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
	mySheet.SetSumValue(mySheet.LastRow(),0,"합계");
}

/** *****************************************************
 *  IBSheet 헤더 부분 초기화
 ****************************************************** */
function setHeaderDetail() {

	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet2"), "mySheet2", "100%", "130px");
	mySheet2.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	mySheet2.SetDataAutoTrim(false);

	var ibdata = {};
	// SizeMode: 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
		{Header:"순번",    		Type:"Text", 	SaveName:"NUM",				Align:"Center", 	Width:90,    Edit:0}
	/*, {Header:"상위업체코드",		Type:"Text", 	SaveName:"SALE_VENDOR_ID",								Hidden:true}
	  , {Header:"상위업체명",	 	Type:"Text", 	SaveName:"SALE_VENDOR_NM",	Align:"Center",  	Width:90,   Edit:0}
	  , {Header:"하위업체코드",		Type:"Text", 	SaveName:"VENDOR_ID", 	 								Hidden:true}
	  , {Header:"하위업체명",		Type:"Text", 	SaveName:"VENDOR_NM",	 	Align:"Center", 	Width:90,	 Edit:0} */
	  , {Header:"업체코드",		Type:"Text", 	SaveName:"SALE_VENDOR_ID",	Align:"Center", 	Width:90,	 Edit:0}
	  , {Header:"최종주문번호",		Type:"Text", 	SaveName:"ORDER_ID", 		Align:"Center", 	Width:100,	 Edit:0}
	  , {Header:"원주문번호",		Type:"Text", 	SaveName:"FIRST_ORDER_ID",	Align:"Center", 	Width:100,	 Edit:0}
	  , {Header:"주문일자",		Type:"Date", 	SaveName:"ORDER_DT",		Align:"Center", 	Width:100,	 Edit:0, Format:"yyyy-MM-dd"}
	  , {Header:"매출일자",		Type:"Date", 	SaveName:"SALE_DT",			Align:"Center", 	Width:100,	 Edit:0, Format:"yyyy-MM-dd"}
	  , {Header:"상품코드",		Type:"Text", 	SaveName:"PROD_CD",			Align:"Center", 	Width:100,	 Edit:0}
	  , {Header:"상품코드명",		Type:"Text", 	SaveName:"PROD_NM",			Align:"Center", 	Width:90,	 Edit:0}
	  , {Header:"공급가액",		Type:"AutoSum", SaveName:"VOS",				Align:"Center", 	Width:90,	 Edit:0}
	  , {Header:"부가세",			Type:"AutoSum", SaveName:"TAX_AMT",			Align:"Center", 	Width:90,	 Edit:0}
	  , {Header:"순매출액",		Type:"AutoSum", SaveName:"S_ACCOUNT",		Align:"Center", 	Width:90,	 Edit:0}
	  , {Header:"에누리",			Type:"AutoSum", SaveName:"DC_AMT",	 		Align:"Right", 		Width:70,	 Edit:0}
	/*, {Header:"주문금액",		Type:"Int", 	SaveName:"ORDER_AMT",		Align:"Center", 	Width:90,	 Edit:0} */
	/*, {Header:"주문취소금액",		Type:"Int", 	SaveName:"CANCEL_ORDER_AMT",Align:"Center", 	Width:90,	 Edit:0} */
	  , {Header:"총매출액",		Type:"AutoSum", SaveName:"TOTAL_ORDER_AMT",	Align:"Center", 	Width:90,	 Edit:0}
	  , {Header:"수수료\n(에누리포함)",Type:"AutoSum",SaveName:"PROFIT_AMT",		Align:"Right", 		Width:70,	 Edit:0, Format:"#,###,###,###.##"}
	  , {Header:"수수료율",		 Type:"Text", 	SaveName:"PROFIT_RATE",		Align:"Right", 		Width:70,	 Edit:0}
	  , {Header:"배송료\n(3회차 지급)",Type:"AutoSum",SaveName:"DELIVERY_DIV_AMT",Align:"Right", 		Width:70,	 Edit:0}
	/*, {Header:"주문수량",		Type:"Int", 	SaveName:"ORDER_CNT",		Align:"Right", 		Width:70,	 Edit:0}
	  , {Header:"취소수량",		Type:"Int", 	SaveName:"CANCEL_CNT",		Align:"Right", 		Width:70,	 Edit:0} */
	  , {Header:"수량",		 	Type:"Int", 	SaveName:"SALE_QTY",		Align:"Right", 		Width:70,	 Edit:0}
	/*, {Header:"판매금액",		Type:"Int", 	SaveName:"SALE_AMT",									Hidden:true} */
	];

	IBS_InitSheet(mySheet2, ibdata);

	/*mySheet2.SetFrozenCol(5);**/
	mySheet2.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
	mySheet2.SetHeaderRowHeight(Ibs.HeaderHeight);
	mySheet2.SetSumValue(mySheet2.LastRow(),0,"합계");
}

/** 1. 협력사팝업 */
function popupVendor() {
	var targetUrl = '<c:url value="/vendorPopUp/vendorPopUpView.do"/>';
	Common.centerPopupWindow(targetUrl, 'vendorPopUp', {width : 592, height : 500});
}
/** 2. 협렵사 정보 세팅 */
function setVendorInto(strVendorId, strVendorNm, strCono) {
	$("#vendorId").val(strVendorId);
}

/** 조회 */
function doSearch() {
	goPage();
}

function goPage() {

	var f = document.dataForm;
	if (f.vendorId.value == ""){
		alert('업체선택은 필수입니다.');
		f.vendorId.focus();
		return;
	}

	// 업체별 매출공제 조회
	var url = '<c:url value="/substn/selectVendorSubStnList.do"/>';
	setHeader();
	var param = new Object();
	param.mode = "search";
	param.year = $("#asCmbYy").val();
	param.month = $("#asCmbMonth").val();
	param.accotYm = $('#asCmbYy').val() + $('#asCmbMonth').val();
	param.adjSeq = $("#asCmbSeq").val();
	param.catCd = $("#selDaeGoods").val();
	param.catNm = $("#selDaeGoods option:selected").val();
	param.vendorId = $("#vendorId").val();
	loadIBSheetData(mySheet, url, null, null, param);
	mySheet.FitColWidth(); 
}

function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
	// 업체별 매출상세 조회
	var url = '<c:url value="/substn/selectProdSubStnList.do"/>';

	var param = new Object();
	param.mode = "search";
	param.year = $("#asCmbYy").val();
	param.month = $("#asCmbMonth").val();
	param.accotYm = $('#asCmbYy').val() + $('#asCmbMonth').val();
	param.adjSeq = $("#asCmbSeq").val();
	param.catCd = $("#selDaeGoods").val();
	param.catNm = $("#selDaeGoods option:selected").val();
	param.vendorId = mySheet.GetCellValue(Row, 'SALE_VENDOR_ID');
	param.vendorNm = mySheet.GetCellValue(Row, 'SALE_VENDOR_NM');
	param.taxatDivn = mySheet.GetCellValue(Row, 'TAXAT_DIVN');
	$("#divnTaxat").val(param.taxatDivn);
	if(param.taxatDivn == "합계") {
		param.vendorId = $("#vendorId").val();
	}
	loadIBSheetData(mySheet2, url, null, null, param);
}

function Lpad(str, len) {
	str = str + "";
	while (str.length < len) {
		str = "0" + str;
	}
	return str;
}

//엑셀다운
function downExcel() {

	if (confirm('엑셀 다운로드 하시겠습니까?')) {
		/* var xlsUrl = '<c:url value="/substn/selectVendorSubStnListExcel.do"/>';
		var param = new Object();
		param.mode = "search";
		param.year = $("#asCmbYy").val();
		param.month = $("#asCmbMonth").val();
		param.accotYm = $('#asCmbYy').val() + $('#asCmbMonth').val();
		param.adjSeq = $("#asCmbSeq").val();
		param.catCd = $("#selDaeGoods").val();
		param.catNm = $("#selDaeGoods option:selected").val();
		param.vendorId = $("#vendorId").val();
		directExcelDown(mySheet, '매출공제조회', xlsUrl, null, param, null); // 전체 다운로드  */
		excelDataDown(mySheet, '매출공제조회_' + new Date().yyyymmdd(), null);
	}
}

//엑셀다운
function downExcelDetail() {

	if (mySheet2.RowCount() == 0) {
		alert("엑셀 다운로드 할 자료가 없습니다.");
		return;
	}

	/* var url = '<c:url value="/substn/selectProdSubStnListExcel.do"/>';
	var param = new Object();
	param.mode 	    = "search";
	param.year 	    = $("#asCmbYy").val();
	param.month 	= $("#asCmbMonth").val();
	param.accotYm 	= $('#asCmbYy').val() + $('#asCmbMonth').val();
	param.adjSeq 	= $("#asCmbSeq").val();
	param.catCd 	    = $("#selDaeGoods").val();
	param.catNm 	= $("#selDaeGoods option:selected").val();
	param.vendorId	=  $("#vendorId").val();
	param.vendorNm	= mySheet2.GetCellValue(1, 'SALE_VENDOR_NM');
	param.divnTaxat = $("#divnTaxat").val();
	if(param.divnTaxat =="면세"){
		taxatDivnCd = "0";
	}else if(param.divnTaxat=="과세"){
		taxatDivnCd = "1";
	}else if(param.divnTaxat=="영세"){
		taxatDivnCd = "2";
	}
	param.taxatDivnCd = taxatDivnCd;
	directExcelDown(mySheet2, '매출공제상세조회', url, null, param, null); // 전체 다운로드  */
//	mySheet2.Down2Excel({FileName:'매출공제상세조회',SheetName:'sheet-test'});
	excelDataDown(mySheet2, '매출공제상세조회_' + new Date().yyyymmdd(), null);
}
</script>
</head>
<body>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="dataForm" id="dataForm">
<div id="content_wrap">
<div class="content_scroll"> 
	<div id="wrap_menu">
		<!--	@ 검색조건	-->
		<div class="wrap_search">
			<!-- 01 : search -->
			<div class="bbs_search">
<%--  	공식 확인 <br>
				지급액 =  총매출액 - 수수료( 에누리함 ) <br>
				순매출액 = 총매출액 - 에누리 <br>
				공급가액 = 순매출액 / 1.1 ( 과세 기준 ) <br>
				부가세  = (순매출액 / 1.1) *0.1 ( 과세 기준 )		 <br> --%>
				<input type="hidden" name="divnTaxat" id="divnTaxat" />
				<ul class="tit">
					<li class="tit">매출공제조회</li>
					<li class="btn">
						<a href="#" class="btn" id="excel"><span><spring:message code="button.common.excel" /></span></a>
						<a href="#" class="btn" id="search"><span><spring:message code="button.common.search"/></span></a> 
					</li>
				</ul>
				<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col width="10%">
						<col width="20%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="15%">
						<col width="10%">
						<col width="25%">
					</colgroup>
					<tr>
						<th><span class="star">* </span>회계년월</th>
						<td>
							<select name="asCmbYy" id="asCmbYy" style="width:50%"  class="select">
								<script>
									var year;
									var selYear;
									if (<%=curMonth%> == 12) {
										selYear = <%=curYear%> + 1;
									} else {
										selYear = <%=curYear%>;
									}
									for (i = 2015; i <= selYear; i++) {
										year = i;
										if (year == <%=curYear%>) {
											document.write("<option value="+year+" selected>"+year+"년</option>");
										} else {
											document.write("<option value="+year+">"+year+"년</option>");
										}
									}
								</script>
							</select>
							<select name="asCmbMonth" id="asCmbMonth" style="width:40%">
								<script>
									var month;
									for (i = 1; i <= 12; i++) {
										month = i;
										if (month == <%=curMonth%>) {
											document.write("<option value="+Lpad(month,2)+" selected>"+month+"월</option>");
										} else {
											document.write("<option value="+Lpad(month,2)+">"+month+"월</option>");
										}
									}
								</script>
							</select>
						</td>
						<th><span class="star">* </span>회차</th>
						<td>
							<select name="asCmbSeq" id="asCmbSeq" style="width:90%">
								<option value="1">1차 (1일~10일)</option>
								<option value="2">2차 (11일~20일)</option>
								<option value="3">3차 (21~월말)</option>
								<%-- <option value="4">전체 (1일~월말)</option> --%>
							</select>
						</td>
						<th><span class="star">* </span>대분류</th>
						<td>
							<select style="width:90%" class="select" name="selDaeGoods" id="selDaeGoods"><!--  style="width:150px;" -->
								<option value="">선택</option>
								<c:forEach items="${daeCdList}" var="code" begin="0">
	 								<option value="${code.code }" ${code.code == daeCd ? "selected='selected'" : "" }>${code.name }</option>
	 							</c:forEach>
							</select>
						</td>
						<th><span class="star">* </span>협력업체</th>
							<td>
								<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<select class="select" name="vendorId" id="vendorId" style="width:90%">
										<option value="" <c:if test="${param.vendorId == ''}">selected</c:if>>선택</option>
										<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
											<option value="${venArr}" <c:if test="${venArr eq param.vendorId || (not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId) }">selected</c:if>>${venArr}</option>
										</c:forEach>
										</select>
									</c:when>
								</c:choose>
							</td>
					</tr>
				</table>
			</div>
			<!-- 1검색조건 // -->
			<div>
				<br>
				<table cellpadding="0" cellspacing="0" border="0" width="100%" bgcolor="#F3F3F3">
					<tr align="left">
						<td style="text-align: left ;font-size: 10pt;font-weight:bold;" > * 지급액 = 총매출액 - 수수료금액(에누리포함) + 배송료(3회차 일괄 지급)&nbsp;<font color="red">☞ 합계금 기준으로 계산 후 소수점 반올림</font></td>
					</tr>
					<tr align="left">
						<td style="text-align: left;font-size: 10pt;font-weight:bold;">	* 수수료 = 순매출액 - 지급액&nbsp;<font color="red">☞  수수료 세금계산서는 회차별 수수료 합계금으로 월 1회 정발행</font></td>
					</tr>
					<tr align="left">
						<td style="text-align: left;font-size: 10pt;font-weight:bold; color:red;"> ※ 원 단위 단수차로 인하여 합계금 기준으로 계산하세요.</td>
					</tr>
				</table>
			</div>
		</div>
		<!--	2 검색내역 	-->
		<div class="wrap_con">
			<!-- list -->
			<div class="bbs_list">
				<ul class="tit">
					<ul class="tit"">
						<li class="tit" style="float:left;width:15%;">업체별매출내역</li>
					</ul>
				</ul>
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr><div id="ibsheet1"></div></tr>
				</table>
			</div>
		</div>
		<br/>
		<div>
			<table cellpadding="0" cellspacing="0" border="0" width="70%" bgcolor="#F3F3F3">
				<tr align="left">
					<td style="text-align: left ;font-size: 10pt;font-weight:bold;">
					※ 최종주문번호 : 원주문번호에서 타업체 주문변경으로 생성된 최종주문번호
					</td>
				</tr>
			</table>
		</div>
		<!--	2-2상세검색내역 	-->
		<div class="wrap_con">
			<!-- list -->
			<div class="bbs_list">
				<ul class="tit">
					<ul class="tit"">
						<li class="tit" style="float:left;width:70%;">업체별 매출상세내역&nbsp;<font color="lightgray">(☞ 매출공제 조회 후 상단 업체별매출내역 '합계' 클릭)</font></li>
						<li class="btn">
							<a href="#" class="btn" id="excelDetail"><span><spring:message code="button.common.excel" /></span></a>
						</li>
					</ul>
				</ul>
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr><div id="ibsheet2"></div></tr>
				</table>
			</div>
		</div>
	</div>
</div>
<!-- footer -->
<div id="footer">
	<div id="footbox">
		<div class="msg" id="resultMsg"></div>
		<!--  홈 -->
		<div class="location">
			<ul>
				<li>홈</li>
				<li>정산관리</li>
				<li class="last">매출공제조회</li>
			</ul>
		</div>
	</div>
</div>
<!-- footer //-->
	
</div>
</form>
</body>
</html>