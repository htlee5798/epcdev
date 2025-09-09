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


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOTTE MART Back Office System</title>
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>

<!-- 신규 공통 css 및 js 파일 INCLUDE -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>

<script type="text/javascript">
var rtnProdCdList = new Array();
var rtnCullSellPrcList = new Array();

/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function(){
	$('#search').click(function() {
		doSearch();
	});
	
	
	// START of IBSheet Setting
	createIBSheet2(document.getElementById("ibsheet1"), "mySheet", "100%", "253px");
	mySheet.SetTheme(Ibs.ThemeCode, Ibs.ThemeName);
	
	var ibdata = {};
	// SizeMode: 사이즈 방식, SearchMode: 조회 방식, MergeSheet: 머지 종류 
	ibdata.Cfg = {SizeMode:sizeAuto, SearchMode:smGeneral, MergeSheet:msHeaderOnly}; // 10 row씩 Load
	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.HeaderMode = {Sort:1, ColMove:0, ColResize:1, HeaderCheck:1};

	// Sort: 헤더클릭시 정렬, ColMove: 마우스로 헤더이동, ColResize:컬럼Resize, HeaderCheck: 헤더에 CheckBox 표시
	ibdata.Cols = [
	    {Header:"구분",		        	Type:"Text" ,			SaveName:"crud", 		          		Align:"Center", Width:20, Edit:0, Hidden:true}
	  , {Header:"",     					Type:"CheckBox", 	SaveName:"selected", 	      		Align:"Center", Width:30, Edit:1, Sort:false}
	  , {Header:"인터넷상품코드", 	Type:"Text", 			SaveName:"prodCd",           		Align:"Center", Width:90, Edit:0}
	  , {Header:"판매코드", 			Type:"Text", 			SaveName:"mdProdCd",  			Align:"Center", Width:50, Edit:0}
	  , {Header:"대표판매코드", 	Type:"Text", 			SaveName:"mdSrcmkCd",  			Align:"Center", Width:30, Edit:0}
	  , {Header:"인터넷상품명",     Type:"Text", 			SaveName:"prodNm", 	            Align:"Left",     Width:160, Edit:0}
	  , {Header:"세분류 카테고리", Type:"Text", 			SaveName:"categoryNm", 	      	Align:"Center", Width:50, Edit:0}
	  , {Header:"전시여부", 	       	Type:"Text", 			SaveName:"dispYn",      				Align:"Center", Width:50, Edit:0}
	  , {Header:"품절여부", 	    	Type:"Text", 			SaveName:"absenceYn", 	        Align:"Center", Width:60, Edit:0}
	  , {Header:"변경판매가", 	    Type:"Int", 				SaveName:"cullSellPrc", 				Align:"Center", Width:60, Edit:0, Hidden:true}
	  , {Header:"상태",					Type:"Status",			SaveName:"s_status",              	Align:"Center",    Width:0,  Edit:0, Hidden:true}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	mySheet.FitColWidth();
	
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
	var form = document.popupForm;
	var url = '<c:url value="/product/selectProductPopupList.do"/>';
	var param = new Object();
	
	if (form.vendorId.value == "")
    {
        alert('업체선택은 필수입니다.');
        form.vendorId.focus();
        return;
    }
	
	var startDate = $('#startDate').val().replace( /-/gi, '' );
 	var endDate  = $('#endDate').val().replace( /-/gi, '' );
 	
 	if(startDate > endDate){
 		alert('시작일자가 종료일자보다 클 수 없습니다.');
 		return;
 	}
 	
	param.rowsPerPage 	= $("#rowsPerPage").val();
	param.startDate = form.startDate.value
	param.endDate = form.endDate.value
	param.mode = 'search';
	
	param.searchDateType =  form.searchDateType.value;
	param.categoryId =  form.categoryId.value;
	param.dispYn =  form.dispYn.value;
	param.vendorId =  form.vendorId.value;
	param.cmbnMallSellPsbtYn =  form.cmbnMallSellPsbtYn.value;
	param.searchCondition =  form.searchCondition.value;
	param.searchWord = form.searchWord.value;
	param.prodDivnCd =  form.prodDivnCd.value;

	loadIBSheetData(mySheet, url, currentPage, null, param);
}

$(document).ready(function(){
	$('#startDate').click(function() {
		openCal('popupForm.startDate');
	});
	$('#endDate').click(function() {
		openCal('popupForm.endDate');
	});
}); // end of ready

function goParentGridInsert() {
	var obj = new Object();
	var prodCdList = new Array();	
	var cullSellPrcList = new Array();
	for(i = 1; i <= mySheet.GetTotalRows(); i++) {
		if(mySheet.GetCellValue(i, "selected") == 1) {
			prodCdList.push( mySheet.GetCellValue(i, "prodCd") );
			cullSellPrcList.push( mySheet.GetCellValue(i, "cullSellPrc") );
		}
	}
	if(prodCdList.length < 1) {
		alert("선택된 로우가 없습니다.");
		return;
	}
	obj.prodCdList = prodCdList;
	obj.cullSellPrcList = cullSellPrcList;
	opener.selectProductItemList(obj);
	self.close();
}

function goExcelInsert() {
	var url = '<c:url value="/product/selectProductExcelPopupView.do"/>';
	Common.centerPopupWindow(url, 'excelView', {width : 600, height : 150});
}

function selectMiddleCategoryList() {
	var form = document.popupForm;
	var url = '<c:url value="/product/selectMiddleCategoryList.do"/>';
	form.target = "hiddenFrame";
	form.parentFormName.value = "popupForm";
	form.action = url;
	form.submit();
}
function selectSubCategoryList() {
	var form = document.popupForm;
	var url = '<c:url value="/product/selectSubCategoryList.do"/>';
	form.target = "hiddenFrame";
	form.parentFormName.value = "popupForm";
	form.action = url;
	form.submit();
}

// 2015.10.30 by kmlee 카테고리 체계 변경으로 사용하지 않는 함수임.
function selectDetailCategoryList() {
	var form = document.popupForm;
	var url = '<c:url value="/product/selectDetailCategoryList.do"/>';
	form.target = "hiddenFrame";
	form.parentFormName.value = "popupForm";
	form.action = url;
	form.submit();
}

function makeSelectEmpty(obj){
	var len = obj.options.length;
	for(i = len - 1; i >= 0; i--){
		obj.options[i] = null;
	}
}

//카테고리 팝업
function popupCategory(){
	var targetUrl = '<c:url value="/common/selectCategoryPopUpView.do"/>?categoryTypeCd=01';//01:상품
	Common.centerPopupWindow(targetUrl, 'prd', {width : 560, height : 485});
}

//카테고리 검색창으로 부터 받은 카테고리 정보 입력
function setCategoryInto(categoryId, categoryNm) { // 이 펑션은 카테고리 검색창에서 호출하는 펑션임
	var form = document.popupForm;
	form.categoryId.value = categoryId;
	form.categoryNm.value = categoryNm;
}

//카테고리 입력 정보 삭제
function deleteCategory() {
	var form = document.popupForm;
	form.categoryId.value = "";
	form.categoryNm.value = "";
}

function setProdArray(list){
	var form = document.popupForm;
	var param = new Object();
	var currentPage = 1;
	var url = '<c:url value="/product/selectProductArrayPopupList.do"/>';
	
	for(var i=0; i<list.prodCdList.length; i++){
		rtnProdCdList.push(list.prodCdList[i]);
		rtnCullSellPrcList.push(list.currSellPrcList[i]);
	}
	
	param.rowsPerPage 	= $("#rowsPerPage").val();
	param.mode = 'search';
	param.vendorId =  form.vendorId.value;
		
	param.selectedProdCd =  String(list.prodCdList);
// 	param.selectedProdCd =  prodArray.prodCdList;
	
	loadIBSheetData(mySheet, url, currentPage, null, param);
	
	
}

function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
	$("#vendorId").val(strVendorId);
}


//셀 클릭 시...
function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {

	if(mySheet.ColSaveName(Col) == 'selected'){
		if(mySheet.GetCellValue(Row, "selected") == "1") {
			mySheet.SetCellValue(Row, "crud", "I");
		} else {
			mySheet.SetCellValue(Row, "crud", "");			
		}
	}
}


//데이터 읽은 직후 이벤트
function mySheet_OnSearchEnd() {
		mySheet.FitColWidth();
		
		for(var i=0; i<mySheet.RowCount() + 1; i++){
			for(var j=0; j<rtnProdCdList.length; j++){
				if(mySheet.GetCellValue(i, "prodCd") == rtnProdCdList[j]){
					mySheet.SetCellValue(i, "cullSellPrc", rtnCullSellPrcList[j]);
				}
			}
		}
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

<form name="popupForm">
<input type="hidden" name="parentFormName" />
<div id="wrap_menu">

	<!--	@ 검색조건	-->
	<div class="wrap_search">
    <!--  @title  -->
     <div id="p_title1">
		<h1>상품검색</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
		<!-- 01 : search -->
		<div class="bbs_search">
			<ul class="tit">
				<li class="tit">조회조건</li>
				<li class="btn">
					<a href="#" class="btn" id="search"><span><spring:message code="button.common.inquire"/></span></a>
					<a href="#" class="btn" onclick="self.close();"><span><spring:message code="button.common.close"/></span></a>
				</li>
			</ul>
			<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="9%">
				<col width="12%">
				<col width="8%">
				<col width="22%">
				<col width="10%">
				<col width="22%">
			</colgroup>
			<tr>
				<th>상품종류</th>
				<td>
					<select name="cmbnMallSellPsbtYn" class="select">
   						<option value="">전체</option>
   						<option value="Y">근거리</option>
					</select>
				</td>
				<th>전시여부</th>
				<td>
					<select name="dispYn" class="select">
						<option value="">전체</option>
                        <option value="Y">전시</option>
						<option value="N">비전시</option>
					</select>
				</td>
				
				<th>협력업체코드</th>
				<td>
					<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<select name="vendorId" class="select">
											<option value="">전체</option>
											<c:forEach items="${epcLoginVO.vendorId}" var="venArr">
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
					<select name="searchCondition" class="select">
						<option value="01">인터넷상품코드</option>
                        <option value="02">MD상품코드</option>
                        <option value="03">대표판매코드</option>
						<option value="04">인터넷상품명</option>
					</select>
				</td>
				<th>검색어</th>
				<td>
					<input type="text" name="searchWord" class="input" maxlength="20" />
				</td>
				<th>온라인등록여부</th>
				<td>
					<select name="prodDivnCd" class="select">
						<option value="">전체</option>
						<c:forEach items="${prodDivnCdList}" var="prodDivnCdList" begin="0">
 							<option value="${prodDivnCdList.MINOR_CD}">${prodDivnCdList.CD_NM}</option>
 						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th><span class="star">*</span> 일자검색조건</th>
				<td>
					<select name="searchDateType" class="select">
   						<option value="01">온라인등록일</option>
   						<option value="02">승인일</option>
   						<option value="03">MD등록일</option>
					</select>
				</td>
				<th><span class="star">*</span> 조회일자</th>
				<td>
					<input type="text" name="startDate" id="startDate" readonly class="day" style="width:31%;" value="${startDate}" /><a href="javascript:openCal('popupForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
					~
					<input type="text" name="endDate" id="endDate" readonly class="day" style="width:31%;" value="${endDate}" /><a href="javascript:openCal('popupForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>
				<th>카테고리</th>
				<td>
					<input type="text" name="categoryId" class="day" readonly style="width:35%;" />
					<input type="text" name="categoryNm" class="day" readonly style="width:35%;" />
					<a href="javascript:popupCategory();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_search.gif" alt="" class="middle" /></a>
					<a href="javascript:deleteCategory();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_eraser.gif" alt="" class="middle" /></a>
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
					<a href="javascript:goExcelInsert();" class="btn" ><span>엑셀업로드</span></a>
					<a href="javascript:goParentGridInsert();" class="btn" ><span>추가</span></a>
				</li>
			</ul>
 
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
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
					<li class="last">상품검색</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->


</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>