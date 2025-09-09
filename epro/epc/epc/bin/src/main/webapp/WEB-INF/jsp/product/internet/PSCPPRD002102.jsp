<%--
- Author(s): lth
- Created Date: 2016. 05. 02
- Version : 1.0
- Description : 상세설명 수정리스트

--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="lcn.module.common.util.DateUtil"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>


<%
    String excelToDate  =DateUtil.formatDate(DateUtil.getToday(),"-");
%>
<%
	/**
	 * @Class Name : PSCPPRD002102.jsp
	 * @Description : 통계 > 업체상품수정관리 상세설명탭
	 * 
	 *   수정일                  수정자                                         수정내용
	 *  -------    --------    ---------------------------
	 * 2014.02.25	drkang 
	 *
	 * @author :  drkang
	 * @since : 2014.02.25
	 *  
	 * Copyright (C) 2012 by ldcc  All right reserved.
	 */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- PSCPPRD002102 -->
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<%@ include file="/common/scm/scmCommon.jsp" %>


<!-- 페이지별 JS -->
<script type="text/javascript" >

/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function(){
	//input enter 막기
	$("*").keypress(function(e){
        if(e.keyCode==13) return false;
	});

	// 조회버튼 클릭
	$('#search').click(function() {
		doSearch();
	});
	
	// 승인 클릭
	$('#aprv').click(function() {
		aprv();
	});
    
	// 반려 클릭
	$('#noAprv').click(function() {
		noAprv();
	});
	
	//달력셋팅
	$("#startDate, #endDate").attr("readonly", "readonly");

	$('#btnStartDate, #startDate').click(function() {
		openCal('form1.startDate');
	});
	$('#btnEndDate, #endDate').click(function() {
		openCal('form1.endDate');
	});

	//초기값 셋팅
	$("#prodCd").val('${prodCd}');
	$("#prodNm").val('${prodNm}');
	$("#startDate").val('${startDate}');
	$("#endDate").val('${endDate}');
	
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
	    {Header:"순번",		        	Type:"Int" ,			SaveName:"NUM", 		        Align:"Center", Width:30, Edit:0}
	  , {Header:"SEQ", 	        		Type:"Text", 		SaveName:"SEQ", 	          		Align:"Center", Width:20, Edit:0, Hidden:true}
	  , {Header:"인터넷상품코드", 	Type:"Text", 		SaveName:"PROD_CD", 	    	Align:"Center", Width:80, Edit:0}
	  , {Header:"상품명", 				Type:"Text", 		SaveName:"PROD_NM", 			Align:"Left", 	  Width:200, Edit:0, Cursor:'pointer', FontUnderline:true}
	  , {Header:"상세이미지", 			Type:"Text", 		SaveName:"PROD_DESC",  		Align:"Center",  Width:70, Edit:0, Cursor:'pointer', FontUnderline:true}
	  , {Header:"상태", 					Type:"Text", 		SaveName:"APRV_CD",  			Align:"Center", Width:60, Edit:0, Hidden:true}
	  , {Header:"상태", 					Type:"Text", 		SaveName:"APRV_NM",  		Align:"Center", Width:60, Edit:0}
	  , {Header:"확인관리자",     	Type:"Text", 		SaveName:"APRV_ADMIN", 	Align:"Center", Width:65, Edit:0}
	  , {Header:"협력업체명",        	Type:"Text", 		SaveName:"VENDOR_NM", 	Align:"Center",     Width:100, Edit:0}
	  , {Header:"메모", 	    			Type:"Text", 		SaveName:"MEMO", 	      		Align:"Left", Width:190, Edit:0}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	mySheet.FitColWidth();
	
	
}); // end of ready



/** ********************************************************
 * 일별카드별주문내역 목록 조회
 ******************************************************** */
function goPage(currentPage){

	var url = '<c:url value="/product/selectProdDescChgHist.do"/>';
	var param = new Object();
	
	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if ($('#vendorId').val() == "")
	    {
	        alert('업체선택은 필수입니다.');
	        $('#vendorId').focus();
	        return;
	    }
		</c:when>
	</c:choose>

	var startDate = $('#startDate').val().replace( /-/gi, '' );
 	var endDate  = $('#endDate').val().replace( /-/gi, '' );
 	
 	if(startDate > endDate){
 		alert('시작일자가 종료일자보다 클 수 없습니다.');
 		return;
 	}
	
	param.rowsPerPage 	= $("#rowsPerPage").val();
	param.startDate = $('#startDate').val();
	param.endDate = $('#endDate').val();
	param.mode = 'search';
	
	param.prodCd =  $("#prodCd").val();
	param.prodNm = $("#prodNm").val();
	param.vendorId = $("#vendorId").val();
	param.statusCd = $("#statusCd").val();
	
	loadIBSheetData(mySheet, url, currentPage, null, param);
	
}
/** ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function doSearch() {
	
	goPage('1');
}

/** ********************************************************
 * 상세팝업 띄우기
 ******************************************************** */
function popupDescForm() {
	var form = document.form1;
	var targetUrl = '<c:url value="/product/prodImgDescForm.do"/>?prodCd='+$("#prodCd").val();

	Common.centerPopupWindow(targetUrl, 'popupInsertForm', {
		width	: 750,
		height	: 500
	});
}


function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
	$("#vendorId").val(strVendorId);
}

function viewDesc(seq,prodCd) {
	var targetUrl = '<c:url value="/product/descDetailForm.do"/>?seq='+seq+'&prodCd='+prodCd;
	Common.centerPopupWindow(targetUrl, 'viewDesc', {width : 1100, height : 650, scrollBars : "YES"});
}

//상품상세조회 팝업
function popupPrdInfo(no){	
	var targetUrl = '<c:url value="/product/selectProductFrame.do"/>'+'?prodCd='+no;	
		
	Common.centerPopupWindow(targetUrl, 'prdInfo', {width : 1024, height : 768, scrollBars:'YES'});
}

//셀 클릭 시...
function mySheet_OnClick(Row, Col, Value, CellX, CellY, CellW, CellH) {

	if (mySheet.ColSaveName(Col) == 'PROD_DESC') {
		viewDesc(mySheet.GetCellValue(Row, 'SEQ'),mySheet.GetCellValue(Row, 'PROD_CD'));
	}

	if (mySheet.ColSaveName(Col) == 'PROD_NM') {
		popupPrdInfo(mySheet.GetCellValue(Row, 'PROD_CD'));
	}
	
}


//데이터 읽은 직후 이벤트
function mySheet_OnSearchEnd(cd,msg) {
	
	if( mySheet.GetTotalRows() > 0 ){
		for(i = 1; i <= mySheet.GetTotalRows(); i++) {
			mySheet.SetCellBackColor(i, "MEMO", '#f0fff0');
			mySheet.SetCellFontColor(i, "PROD_NM", '#1E90FF');
			mySheet.SetCellFontColor(i, "PROD_DESC", '#1E90FF');
		}
	}
	
	mySheet.FitColWidth();
	
}

function mySheet_OnSmartResize(Width, Height) {
	//변경된 정보에 따라 컬럼들의 너비를 재조정한다.
	mySheet.FitColWidth();
}
</script>
</head>
<body>
<form name="form1" id="form1" method="post">


<div class="content_scroll">

<div id="wrap_menu">

	<!-- -------------------------------------------------------- -->
	<!-- 검색조건 -->
	<!-- -------------------------------------------------------- -->
	<div class="wrap_search">
		<div class="bbs_search">
		
			<!------------------------------------------------------------------ -->
			<!-- 	title -->
			<!------------------------------------------------------------------ -->
			<ul class="tit">
				<li class="tit"><spring:message code="text.common.title.searchCondition"/></li>
				<li class="btn">
					<a href="#" class="btn" id="search"><span><spring:message code="button.common.search"/></span></a>
				</li>
			</ul>
			<!-------------------------------------------------- end of title -- -->
			
			<!------------------------------------------------------------------ -->
			<!-- 	조건 table -->
			<!------------------------------------------------------------------ -->
			<table class="bbs_search" cellpadding="0" cellspacing="0"border="0">
				<colgroup>
					<col width="15%">
					<col width="35%">
					<col width="10%">
					<col width="15%">
					<col width="10%">
					<col width="15%">
				</colgroup>
				<tr>
					<th class="fst"><span class="star">*&nbsp</span>수정일자</th>
					<td class="text">
						<input type="text" id="startDate" name="startDate" style="width:35%;" class="inputRead" />
						<a href="#" id="btnStartDate"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>~
						<input type="text" id="endDate" name="endDate" style="width:35%;" class="inputRead" />
						<a href="#" id="btnEndDate"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
					</td>
					
					<th>상태</th>
					<td>
						<select class="select" name="statusCd" id="statusCd">
								<option value="">전체</option>
								<c:forEach items="${statusCdList}" var="statCd" begin="0">
	 								<option value="${statCd.CD }"  <c:if test="${statusCd == statCd.CD}">selected="selected"</c:if>>${statCd.NM }</option>
	 							</c:forEach>
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
										<select name="vendorId" id="vendorId" class="select">
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
					<th class="fst">인터넷상품코드</th>
					<td>
						<input type="text" name="prodCd" id="prodCd" class="input" maxlength="13">
					</td>
					<th class="fst">상품명</th>
					<td colspan="3">
						<input type="text" name="prodNm" id="prodNm" class="input" maxlength="50">
					</td>
				</tr>
			</table>
			<!----------------------------------------------- end of 조건 table -- -->
		</div> <!-- id bbs_search// -->
	</div> <!-- id wrap_search// -->
	<!-----------------------------------------------end of 검색조건 -->
	
	

	<!-- -------------------------------------------------------- -->
	<!--	검색내역 	-->
	<!-- -------------------------------------------------------- -->
	<div class="wrap_con">
		<!-- list -->
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit"><spring:message code="text.common.title.searchResult"/></li>
			</ul>

			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<div id="ibsheet1"></div><!-- IBSheet 위치 -->
				</tr>
			</table>
		</div><!-- end of class bbs_list -->
	</div>
	<!-----------------------------------------------end of 검색내역  -->
	
	<!-- -------------------------------------------------------- -->
	<!--	paging 	-->
	<!-- -------------------------------------------------------- -->
	<div id="pagingDiv" class="pagingbox2" style="width: 100%;">
	 	<script> setLMPaging("0", "0", "0","goPage","pagingDiv");</script>
	</div>
	<!-- -------------------------------------------end of paging -->
	
</div> <!-- id wrap_menu// -->
</div><!-- class content_scroll// -->

<!-- -------------------------------------------------------- -->
<!--	footer 	-->
<!-- -------------------------------------------------------- -->
<div id="footer" style="width:100%; height:17px; *zoom:1; *display:block; background-color:#F2F2F2;border-top:1px solid #cccccc;color:#666666;padding:4px 0 3px 0;">
	<div id="footbox">
		<div class="msg" id="resultMsg"></div>
		<!-- -------------------------------------- -->
		<!-- location -->
		<!-- -------------------------------------- -->
		<div class="location">
			<ul>
				<li>홈</li>
				<li>상품관리</li>
				<li class="last">상품정보수정요청</li>
			</ul>
		</div>
		<!-- -----------------------end of location -->		
	</div>
</div>
<!---------------------------------------------end of footer -->


</form>

</body>
</html>