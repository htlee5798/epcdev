<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<c:import url="/common/commonHead.do" />

<%@ include file="/common/scm/scmCommon.jsp" %>

<script type="text/javascript">
/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function(){
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "360px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	mySheet.SetDataAutoTrim(false);
	
	var ibdata = {};
	// SizeMode: 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
		{Header:"상품코드|상품코드", 	 	    Type:"Text", 	SaveName:"PROD_CD", 	 	 Align:"Center", 	Width:90,    Edit:0}
	  , {Header:"대표상품코드|대표상품코드", 	Type:"Text", 	SaveName:"REP_PROD_CD",	 	 Align:"Center", 	Width:80,	 Edit:0}
	  , {Header:"상품명|상품명",		 		Type:"Text", 	SaveName:"PROD_NM", 	 	 Align:"Left",   	Width:200,   Edit:0}
	  , {Header:"대분류|대분류", 	 	 		Type:"Text", 	SaveName:"L1_NM", 	 	 	 Align:"Center", 	Width:90,	 Edit:0}
	  , {Header:"중분류|중분류",		 		Type:"Text", 	SaveName:"L2_NM",	 	 	 Align:"Center", 	Width:90,	 Edit:0}
	  , {Header:"일반|매출수량",				Type:"Int", 	SaveName:"LOCAL_CNT", 		 Align:"Right", 	Width:70,	 Edit:0}
	  , {Header:"일반|매출액",					Type:"Int", 	SaveName:"LOCAL_AMT", 		 Align:"Right", 	Width:70,	 Edit:0}
	  , {Header:"명절|매출수량",		    	Type:"Int", 	SaveName:"HOLI_CNT",	 	 Align:"Right", 	Width:70,	 Edit:0}
	  , {Header:"명절|매출액",		 	    	Type:"Int", 	SaveName:"HOLI_AMT",	 	 Align:"Right", 	Width:70,	 Edit:0}
	  
	];

	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.1
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	
	$('#search').click(function() {
		doSearch();
	});
	
	$('#excel').click(function() {
		doExcel();
	});		

	$('#startDate').click(function() {
		openCal('searchForm.startDate');
	});
	
	$('#endDate').click(function() {
		openCal('searchForm.endDate');
	});
	
	$('#prodFlag').change(function(){
		var val = $(this).val();
		
		$('#prodCd').val('');
	});
}); // end of ready

/** ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function doSearch() {
	form = document.searchForm;
	var startDate = form.startDate.value.replace( /-/gi, '' );
	var endDate   = form.endDate.value.replace( /-/gi, '' );
	
	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if ($("#vendorId").val() == "")
	    {
	        alert('업체선택은 필수입니다.');
	        $("#vendorId").focus();
	        return;
	    }
		</c:when>
	</c:choose>

	if(startDate>endDate){
		alert('시작일자가 종료일자보다 클수 없습니다.');
		return;
	}	
	
	form.searchUseYn.value = 'Y';
	
	goPage('1');
}

function goPage(currentPage){
	var url = '<c:url value="/order/selectProductSaleSum.do"/>';
	
	loadIBSheetData(mySheet, url, currentPage, '#searchForm', null);
}

//카테고리 팝업
function popupCategory(){
	var targetUrl = '<c:url value="/common/selectCategoryPopUpView.do"/>?categoryTypeCd=01';//01:상품
		
	Common.centerPopupWindow(targetUrl, 'prd', {width : 600, height : 550});
}

//카테고리 검색창으로 부터 받은 카테고리 정보 입력
function setCategoryInto(categoryId, categoryNm) { // 이 펑션은 카테고리 검색창에서 호출하는 펑션임
	$("#categoryId").val(categoryId);
	$("#categoryNm").val(categoryNm);	   
}

//상품정보 팝업
function popupProductList(){
	var chkVal = $('#prodFlag').val();
	
	if(chkVal != "4"){
		var targetUrl = '<c:url value="/common/viewPopupProductList.do"/>?prodFlag='+chkVal+"&vendorId="+$("#vendorId").val();//01:상품
			
		Common.centerPopupWindow(targetUrl, 'prd', {width : 750, height : 500});
	}
}

function setProdValue(cd)
{
	form = document.searchForm;
	form.prodCd.value = cd;	
}

/** ********************************************************
 * execl
 ******************************************************** */
function doExcel(){
	var f = document.searchForm;
	
	var startDate = f.startDate.value.replace( /-/gi, '' );
	var endDate   = f.endDate.value.replace( /-/gi, '' );
	
	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if ($("#vendorId").val() == "")
	    {
	        alert('업체선택은 필수입니다.');
	        $("#vendorId").focus();
	        return;
	    }
		</c:when>
	</c:choose>
	
	if(startDate>endDate){
		alert('시작일자가 종료일자보다 클수 없습니다.');
		return;
	}	

	var url = '<c:url value="/order/selectProductSaleSumExcel.do"/>';
	f.action = url;
	f.submit();	
}

function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
	$("#vendorId").val(strVendorId);
}

function popupReturn(arg){
	$("#prodCd").val(arg);
}
</script>	
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

<div class="content_scroll">

<form name="searchForm" id="searchForm">
<input type="hidden" name="searchUseYn" value=""/>

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
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="15%">
				<col width="35%">
				<col width="15%">
				<col width="35%">
			</colgroup>
			<tr> 
				<th><span class="star">*</span> 매출확정일자</th>
				<td>
					<input type="text" name="startDate" id="startDate" class="day" readonly style="width:33%;" value="${searchVO.startDate}" /><a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
					~
					<input type="text" name="endDate" id="endDate" class="day" readonly style="width:33%;" value="${searchVO.endDate}" /><a href="javascript:openCal('searchForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>
				<th>카테고리</th>
				<td>
					<input type="text" style="width:80%;ime-mode:disabled" id="categoryId" name="categoryId"  value="${searchVO.categoryId}" maxlength="16" ondblclick="javascript:popupCategory();" onKeyPress="if ((event.keyCode&lt;48)) event.returnValue=false;">
				</td>
			</tr>
			<tr>
				<th>협력업체코드 </th>
				<td>
					<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<c:if test="${not empty vendorId}">
											<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="${vendorId}" style="width:40%;"/>
										</c:if>	
										<c:if test="${empty vendorId}">
											<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="${epcLoginVO.repVendorId}" style="width:40%;"/>
										</c:if>	
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<select name="vendorId" id="vendorId" class="select">
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
				<th>상품검색</th>
				<td>
					<select name="prodFlag" id="prodFlag" style="width:104px;">
						<option value="1">인터넷상품코드</option>
						<option value="2">상품코드</option>
						<option value="3">판매코드</option>
						<option value="4">상품명</option>
					</select>
					<input type="text" style="width:40%;vertical-align:middle;" id="prodCd" name="prodCd">
					<a href="javascript:popupProductList();" ><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_search.gif" alt="" class="middle" /></a>
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
					<li>주문관리</li>
					<li class="last">상품별매출현황</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->

</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>