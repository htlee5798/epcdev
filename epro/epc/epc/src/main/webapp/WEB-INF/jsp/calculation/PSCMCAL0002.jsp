<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag-dev.js" ></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>

<script type="text/javascript">
/** ********************************************************
 * jQeury 초기화
 ******************************************************** */
$(document).ready(function(){
	$('#search').click(function() {
		doSearch();
	});

	$('#startDate').click(function() {
		openCal('adminForm.startDate');
	});
	$('#endDate').click(function() {
		openCal('adminForm.endDate');
	});
	$('#excel').click(function() {
		doExcel();
	});	
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
function goPage(pageIndex){
	var form = document.adminForm;
	var url = '<c:url value="/calculation/selectDeliveryCostsCalculateList.do"/>';
	//form.pageIndex.value = pageIndex;
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

	var url = '<c:url value="/calculation/selectDeliveryCostsCalculateListExcel.do"/>';
	form.action = url;
	form.submit();
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
				<col width="25%">
				<col width="10%">
				<col width="20%">
				<col width="10%">
				<col width="15%">
			</colgroup>
			<tr>
				<th><span class="star">*</span> 기준일자</th>
				<td>
					<input type="text" name="startDate" id="startDate" class="day" readonly style="width:31%;" value="${searchVO.startDate}" /><a href="javascript:openCal('adminForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
					~
					<input type="text" name="endDate" id="endDate" class="day" readonly style="width:31%;" value="${searchVO.endDate}" /><a href="javascript:openCal('adminForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>
				<th> 협력업체코드</th>
				<td>
					<select name="vendorId" class="select">
						<option value="" <c:if test="${searchVO.searchVendorId == ''}">selected</c:if>>전체</option>
						<c:forEach items="${epcLoginVO.vendorId}" var="venArr">
						<option value="${venArr}" <c:if test="${venArr == searchVO.searchVendorId}">selected</c:if>>${venArr}</option>
						</c:forEach>
					</select>
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
				<li class="tit">합계</li>
			</ul>
			
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
			  <colgroup>
				<col style="width:16%" />
				<col style="width:16%" />
				<col style="width:16%" />
				<col style="width:16%" />
				<col style="width:16%" />
				<col style="width:16%" />
			  </colgroup> 
			  <tr>
				<th>총주문건수</th>
				<td style='text-align: right'><fmt:formatNumber value="${orderStats.ORD_CNT}" pattern="#,###,###,###.##" /></td>
				<th>총주문금액</th>
				<td style='text-align: right'><fmt:formatNumber value="${orderStats.ORD_AMT}" pattern="#,###,###,###.##" /></td>
				<th>배송비총합</th>
				<td style='text-align: right'><fmt:formatNumber value="${deliveryStats.DELIVERY_AMT}" pattern="#,###,###,###.##" /></td>
			  </tr>
			</table>
		</div>
		<br />
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">조회내역</li>
			</ul>
 
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="4%">
				<col width="8%">
				<col width="10%">
				<col width="10%">
				<col width="7%">
				<col width="27%">
				<col width="8%">
				<col width="7%">
				<col width="10%">
			</colgroup>
			<tr>
				<th class="fst">순번</th>
				<th>주문일자</th>
				<th>주문번호</th>
				<th>원주문번호</th>
				<th>매출상태</th>
				<th>인터넷상품명</th>
				<th>주문금액</th>
				<th>고객명</th>
				<th>배송료</th>
			</tr>
			<c:forEach var="result" items="${list}" varStatus="status">
			<tr class="r1">
				<td class="fst">${result.ROW_SEQ}</td>
				<td>
					<fmt:parseDate value="${result.ORD_DY}" var="dateFmt" pattern="yyyyMMdd" />
					<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" />
				</td>
				<td>${result.ORDER_ID}</td>
				<td>${result.UP_ORDER_ID}</td>
				<td>${result.SALE_STS_NM}</td>
				<td style='text-align: left'>${result.PROD_NM}</td>
				<td style='text-align: right'><fmt:formatNumber value="${result.ORD_AMT}" pattern="#,###,###,###.##" /></td>
				<td>${result.CUST_NM}</td>
				<c:if test="${orderId != result.ORDER_ID || deliveryId != result.DELIVERY_ID}">
					<td rowspan="${result.CNT}" style='text-align: right'><fmt:formatNumber value="${result.DELIVERY_AMT}" pattern="#,###,###,###.##" /></td>
				</c:if>
			</tr>
			
			<c:set value="${result.ORDER_ID}" var="orderId" />
			<c:set value="${result.DELIVERY_ID}" var="deliveryId" />
			</c:forEach>
			
			<c:if test="${empty list}">
			<tr class="r1">
				<td colspan="11"><spring:message code="msg.common.info.nodata"/></td>
			</tr>
			</c:if>
			</table>
		</div>
		<!-- 2검색내역 // -->
	</div>
	
</div>
</form>

</div>

	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg">${resultMsg}</div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>정산관리</li>
					<li class="last">택배비정산</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
 
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>