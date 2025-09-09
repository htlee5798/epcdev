<%--
- Author(s): lth
- Created Date: 2016. 05. 16
- Version : 1.0
- Description : 배송료 정산목록

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
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag-dev.js" ></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridExtension.js" ></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridConfig.js" ></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<%@ include file="/common/scm/scmCommon.jsp" %>

<!-- calculation/PSCMCAL0003 -->





<script type="text/javascript">
/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function(){
	$('#search').click(function() {
		doSearch();
	});

	$('#excel').click(function() {
		doExcel();
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
	    {Header:"순번", 			Type:"Text", 			SaveName:"rankNum",				Align:"Center", 	Width:50, Edit:0}
	  , {Header:"정산월", 		Type:"Text", 			SaveName:"payMm",  				Align:"Center",  	Width:70, Edit:0}
	  , {Header:"도서산간구분", 	Type:"Text", 			SaveName:"islndRegnChek", 	 	Align:"Center", 	Width:80, Edit:0}
	  , {Header:"주문번호", 		Type:"Text", 			SaveName:"orderId",  			Align:"Center", 	Width:80, Edit:0}
	  , {Header:"EC주문번호", 		Type:"Text", 			SaveName:"ecOrderId",  			Align:"Center", 	Width:80, Edit:0}
	  , {Header:"주문일자",		Type:"Date", 			SaveName:"ordDy", 				Align:"Center", 	Width:80, Edit:0, Format:"yyyy-MM-dd"}
	  , {Header:"주문상태", 		Type:"Text", 			SaveName:"ordStsNm", 	    	Align:"Center", 	Width:80, Edit:0}
	  , {Header:"매출상태", 		Type:"Text", 			SaveName:"saleStsNm", 	    	Align:"Center", 	Width:80, Edit:0}
	  , {Header:"주문금액", 		Type:"Int", 			SaveName:"ordAmt", 	    		Align:"Right", 		Width:80, Edit:0, Format:"#,###,###"}
	  , {Header:"주문자", 		Type:"Text", 			SaveName:"custNm", 	    		Align:"Center", 	Width:80, Edit:0}
	  , {Header:"배송료", 		Type:"Int", 			SaveName:"deliveryAmt", 	    Align:"Right", 		Width:80, Edit:0, Format:"#,###,###"}
	];
	
	IBS_InitSheet(mySheet, ibdata);
		
	mySheet.SetWaitImageVisible(0); // 검색시 로딩 바가 안보이게 한다.
	mySheet.SetHeaderRowHeight(Ibs.HeaderHeight);
	mySheet.FitColWidth();
	
	
}); // end of ready



</script>
<script type="text/javascript">


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
	
  	if(form.searchOrderType.value == "1") {
  		if(form.orderId.value.trim() == ""){
  			alert("원주문번호를 입력해주세요..!");
  			return;
  		}
  	}
  	
  	if(form.searchOrderType.value == "2") {
  		if(form.orderId.value.trim() == ""){
  			alert("EC주문번호를 입력해주세요..!");
  			return;
  		}
  	}
  	
	var url = '<c:url value="/calculation/selectDeliverySettleCostsCalculateSearch.do"/>';
	var param = new Object();
	
	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if (form.vendorId.value == "")
	    {
	        alert('업체선택은 필수입니다.');
	        form.vendorId.focus();
	        return;
	    }
		</c:when>
	</c:choose>

	param.rowsPerPage 	= $("#rowsPerPage").val();
	param.vendorId = form.vendorId.value;
	param.searchMonth = form.searchMonth.value;
	param.searchOrderType = form.searchOrderType.value;
	param.orderId = form.orderId.value;
	
	param.mode = 'search';

	loadIBSheetData(mySheet, url, currentPage, null, param);
	
}


/** ********************************************************
 * excel
 ******************************************************** */
function doExcel(){
	
	var f = document.adminForm;
	
  	if(f.searchOrderType.value == "1") {
  		if(f.orderId.value.trim() == ""){
  			alert("원주문번호를 입력해주세요..!");
  			return;
  		}
  	}
  	
  	if(f.searchOrderType.value == "2") {
  		if(f.orderId.value.trim() == ""){
  			alert("EC주문번호를 입력해주세요..!");
  			return;
  		}
  	}
  	
	<c:choose>
		<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
		if (f.vendorId.value == "")
	    {
	        alert('업체선택은 필수입니다.');
	        f.vendorId.focus();
	        return;
	    }
		</c:when>
	</c:choose>

	if(mySheet.GetTotalRows() == 0){
		alert("데이터가 없습니다.");
		return;
	}
	
	excelDataDown(mySheet, 'PSCMCAL0003_', null);
}

//3자리 마다 콤마(,) 찍어주는 기능
function formatComma(num)  {
	var num = num.toString();
	var rtn = "";
	var val = "";
	var j = 0;
	x = num.length;

	for(i=x; i>0; i--) {
		if(num.substring(i,i-1) != ",") 
			val = num.substring(i, i-1) + val;
	}
	x = val.length;
	for(i=x; i>0; i--) {
		if(j%3 == 0 && j!=0) 
			rtn = val.substring(i,i-1)+","+rtn; 
		else 
			rtn = val.substring(i,i-1)+rtn;

		j++;
	}

	return rtn;
}


function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
	$("#vendorId").val(strVendorId);
}


//데이터 읽은 직후 이벤트
function mySheet_OnSearchEnd(cd,msg) {	
	$("#sumPayMm").html(RETURN_IBS_OBJ.sumPayMm );
	$("#sumDeliveryAmt").html(formatComma(RETURN_IBS_OBJ.sumDeliveryAmt));
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
				<col width="10%">
				<col width="20%">
				<col width="10%">
				<col width="20%">
				<col width="10%">
				<col width="20%">
			</colgroup>
			<tr>
				<th><span class="star">*</span> 정산년월</th>				
				<td>				
					<select name="searchMonth" class="select" >
					<% 
						java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("yyyyMM", java.util.Locale.KOREA);
						java.text.SimpleDateFormat formatter2 = new java.text.SimpleDateFormat ("yyyy년 MM월", java.util.Locale.KOREA);
						Calendar c = Calendar.getInstance();
						String currentYYMM = formatter.format(new Date(c.getTimeInMillis()));
						// 기본월 설정 2011년 2월 정산시작
						c.set(2011, 1, 1);
						do {
							out.print("<option value='"+ formatter.format(new Date(c.getTimeInMillis())) +"'");
					%>
							<c:set var="yyyymm" value="<%=formatter.format(new Date(c.getTimeInMillis()))%>" />
							<c:if test="${param.searchMonth == yyyymm}"> selected </c:if>
							<c:if test="${empty param.searchMonth}"> selected </c:if>
					<%
							out.println(">"+ formatter2.format(new Date(c.getTimeInMillis())) +"</option>");
							c.add(c.MONTH, 1);
						} while (currentYYMM.compareTo(formatter.format(new Date(c.getTimeInMillis()))) >= 0 );
				    %>	
					</select>     
				</td>
			<th> 협력업체코드</th>
				<td>
					<c:choose>
							<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
								<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
								<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
							</c:when>
							<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
								<select class="select" name="vendorId" style="width:70px">
                            	<option value="" <c:if test="${param.vendorId == ''}">selected</c:if>>전체</option>
								<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
			                        <option value="${venArr}" <c:if test="${venArr eq param.vendorId || (not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId) }">selected</c:if>>${venArr}</option>
			                    </c:forEach>
								</select>
							</c:when>
						</c:choose>
				</td>
				<th>
					<select id="searchOrderType" name="searchOrderType" class="select">
						<option value="%">선택</option>
						<option value="1">주문번호</option>
						<option value="2">EC주문번호</option>
					</select>
				</th>
				<td>
					<input type="text" id="orderId" name="orderId" value="" class="input" maxlength="30"/>
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
				<li class="tit">합계</li>
			</ul>
			
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
			  <colgroup>
				<col style="width:10%" />
				<col style="width:15%" />
				<col style="width:10%" />
				<col style="width:15%" />
				<col style="width:10%" />
				<col style="width:15%" />
				<col style="width:10%" />
				<col style="width:15%" />
				<col style="width:15%" />
				
			  </colgroup>
			  <tr>
				<th>정산년월</th>
				<td id="sumPayMm"></td>
				<th>총수수료</th>
				<td style='text-align: right' id="sumDeliveryAmt"></td>
			  </tr>
			</table>
		</div>
	</div>	
		<br />	
	<!--	2 검색내역 	-->
	<div class="wrap_con">
		<!-- list -->
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">조회내역</li>
			</ul>
 
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
				<tr>
<!--  					<td><script type="text/javascript">initWiseGrid("WG1", "100%", "300");</script></td> -->
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
					<li>정산관리</li>
					<li class="last">배송료정산목록</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>