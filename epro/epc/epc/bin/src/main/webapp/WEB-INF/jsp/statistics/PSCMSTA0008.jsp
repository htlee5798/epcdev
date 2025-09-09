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
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<c:import url="/common/commonHead.do" />

<!-- statistics/PSCMSTA0008 -->
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
	$('#startDate').click(function() {
		openCal('adminForm.startDate');
	});
	$('#endDate').click(function() {
		openCal('adminForm.endDate');
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
	
	var startDate = form.startDate.value.replace( /-/gi, '' );
	var endDate   = form.endDate.value.replace( /-/gi, '' );
	
	if(startDate>endDate){
		alert('시작일자가 종료일자보다 클수 없습니다.');
		return;
	}
	
	
	var url = '<c:url value="/statistics/selectLotteCardMallCalList.do"/>';
	form.pageIndex.value = pageIndex;
	form.action = url;
	loadingMask();
	form.submit();
}


/** ********************************************************
 * execl
 ******************************************************** */
function doExcel(){
	var f = document.adminForm;
	//alert("test");
	var url = '<c:url value="/statistics/selectLotteCardMallCalListExcel.do"/>';
	f.pageIndex.value = 1;
	f.action = url;
	f.submit();	
}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<div id="content_wrap">

<div class="content_scroll">

<form name="adminForm">
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
				<col width="10%">
				<col width="25%">
				<col width="10%">
				<col width="20%">
				<col width="10%">
				<col width="15%">
			</colgroup>
			<tr>
				<th><span class="star">*</span> 매출일자</th>
				<td>
					<input type="text" name="startDate" id="startDate" class="day" style="width:33%;" value="${searchVO.startDate}" readOnly="readonly"/><a href="javascript:openCal('adminForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
					~
					<input type="text" name="endDate" id="endDate" class="day" style="width:33%;" value="${searchVO.endDate}" readOnly="readonly" /><a href="javascript:openCal('adminForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>
				<td>
				</td>
			</tr>
			</table>
		</div>
		<!-- 1검색조건 // -->
		
	</div>
	

	<c:if test="${initFlag != 'Y'}">
	<div class="wrap_con">
		<!-- list -->
			
		<div class="bbs_list">
			<ul class="tit">
				<li class="tit">조회내역</li>
			</ul>
 
			<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col width="5%">
				<col width="15%">
				<col width="15%">
				<col width="15%">
				<col width="15%">
				<col width="15%">
				<col width="15%">
			</colgroup>
			<tr>
				<th class="fst">순번</th>
				<th> 매출일자</th>
				<th>총주문금액</th>
				<th>롯데카드결제금액</th>
				<th>쿠폰결제금액</th>
				<th>포인트결제금액</th>
				<th>예치금결제금액</th>
			</tr>
			<c:forEach var="result" items="${list}" varStatus="status">
			<tr class="r1">
				<td class="fst">${result.RANK_NUM}</td>
				<td>
					<fmt:parseDate value="${result.SALE_DY}" var="dateFmt" pattern="yyyyMMdd" />
					<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" />
				</td>
				
				<td style='text-align: right'><fmt:formatNumber value="${result.ORDER_AMT}" pattern="#,###,###,###.##" /></td>
			
        
        <td style='text-align: right'><fmt:formatNumber value="${result.CARD_DPST_AMT}" pattern="#,###,###,###.##" /></td>
		<td style='text-align: right'><fmt:formatNumber value="${result.COUPON_DPST_AMT}" pattern="#,###,###,###.##" /></td>
		<td style='text-align: right'><fmt:formatNumber value="${result.POINT_DPST_AMT}" pattern="#,###,###,###.##" /></td>
		<td style='text-align: right'><fmt:formatNumber value="${result.DEPOSIT_DPST_AMT}" pattern="#,###,###,###.##" /></td>
		
			</tr>
			</c:forEach>
			<c:if test="${empty list}">
			<tr class="r1">
				<td colspan="7"><spring:message code="msg.common.info.nodata"/></td>
			</tr>
			</c:if>
			</table>
		</div>
		<!-- 2검색내역 // -->

		<div id="paging">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="goPage" />
		</div>
	</div>
	</c:if>
	<input type="hidden" name="pageIndex" />
 
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
					<li>통계</li>
					<li class="last">롯데카드몰정산관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>