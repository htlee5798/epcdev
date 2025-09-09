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
<c:import url="/common/commonHead.do" />
<!-- statistics/PSCMSTA0001 -->
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
	var url = '<c:url value="/statistics/selectWeMakePriceEdmSummaryList.do"/>';
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
	
	var startDate = f.startDate.value.replace( /-/gi, '' );
	var endDate   = f.endDate.value.replace( /-/gi, '' );
	
	if(startDate>endDate){
		alert('시작일자가 종료일자보다 클수 없습니다.');
		return;
	}	

	var url = '<c:url value="/statistics/selectWeMakePriceEdmSummaryListExcel.do"/>';
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
				<col width="23%">
				<col width="10%">
				<col width="20%">
				<col width="10%">
				<col width="17%">
			</colgroup>
			<tr>
				<th><span class="star">*</span> 주문일자</th>
				<td>
					<input type="text" name="startDate" id="startDate" class="day" readonly style="width:31%;" value="${searchVO.startDate}" /><a href="javascript:openCal('adminForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
					~
					<input type="text" name="endDate" id="endDate" class="day" readonly style="width:31%;" value="${searchVO.endDate}" /><a href="javascript:openCal('adminForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
				</td>
				<th><span class="star">*</span> 매출유형 </th>
				<td>
					<input type="hidden" name="rootAffiliateLinkNo" value="${searchVO.rootAffiliateLinkNo}" />
						<c:if test="${searchVO.rootAffiliateLinkNo eq '0156'}">
						<select name="affiliateLinkNo" class="select">
							<c:forEach items="${affiliateLinkNoList}" var="affiliateLinkNo" begin="0">
								<option value="${affiliateLinkNo.AFFILIATE_LINK_NO}"
									<c:if test="${affiliateLinkNo.AFFILIATE_LINK_NO == searchVO.affiliateLinkNo}">selected</c:if>
									>${affiliateLinkNo.AFFILIATE_LINK_NM}</option>
	 						</c:forEach>
						</select>
						</c:if>
						
						<c:if test="${searchVO.rootAffiliateLinkNo != '0156'}">
						<select name="affiliateLinkNo" class="select">
							<c:forEach items="${affiliateLinkNoList}" var="affiliateLinkNo" begin="0">
								<option value="${affiliateLinkNo.AFFILIATE_LINK_NO}"
									<c:if test="${affiliateLinkNo.AFFILIATE_LINK_NO == searchVO.affiliateLinkNo}">selected</c:if>
									>${affiliateLinkNo.AFFILIATE_LINK_NM}</option>
	 						</c:forEach>
						</select>
						</c:if>
				</td>
			
				
			</tr>
				
				<tr>
					<!-- column 1 -->
					<th>주문유입경로    </th>
					<td>
						<select  name="orderPathCd" id="orderPathCd" style="width:75%" class="select" >
							<option value="" selected>전체</option>
							<c:forEach items="${orderPathCdList}" var="code" begin="0">
								<option value="${code.MINOR_CD}"
									<c:if test="${code.MINOR_CD == searchVO.orderPathCd}">selected</c:if>>${code.CD_NM}</option>
	 						</c:forEach>
						</select>
					</td>
					
					<th>&nbsp;</th>
					<td>&nbsp;</td>
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
				<col style="width:10%" />
				<col style="width:10%" />
				<col style="width:10%" />
				<col style="width:10%" />
				<col style="width:10%" />
				<col style="width:10%" />
				<col style="width:10%" />
			  </colgroup>
			  <tr>
				<th>총주문수량</th>
				<td style='text-align: right'><fmt:formatNumber value="${stats.ORDER_CNT}" pattern="#,###,###,###.##" /></td>
				<th>취소수량</th>
				<td style='text-align: right'><fmt:formatNumber value="${stats.CANCEL_CNT}" pattern="#,###,###,###.##" /></td>
				<th>반품수량</th>
				<td style='text-align: right'><fmt:formatNumber value="${stats.REFUND_CNT}" pattern="#,###,###,###.##" /></td>
				<th>순주문수량</th>
				<td style='text-align: right'><fmt:formatNumber value="${stats.REAL_CNT}" pattern="#,###,###,###.##" /></td>
			  </tr>
			  <tr>
				<th>총주문금액</th>
				<td style='text-align: right'><fmt:formatNumber value="${stats.ORDER_AMT}" pattern="#,###,###,###.##" /></td>
				<th>취소금액</th>
				<td style='text-align: right'><fmt:formatNumber value="${stats.CANCEL_AMT}" pattern="#,###,###,###.##" /></td>
				<th>반품금액</th>
				<td style='text-align: right'><fmt:formatNumber value="${stats.REFUND_AMT}" pattern="#,###,###,###.##" /></td>
				<th>순주문금액</th>
				<td style='text-align: right'><fmt:formatNumber value="${stats.REAL_AMT}" pattern="#,###,###,###.##" /></td>
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
				<col width="9%">
				<col width="9%">
				<col width="10%">
				<col width="10%">
				<col width="9%">
				<col width="9%">
				<col width="9%">
				<col width="9%">
				<col width="9%">
				<col width="9%">
				<col width="8%">
				<col width="10%">
			</colgroup>
			<tr>
				<th class="fst">주문일자</th>
			<!-- 	<th>원주문번호</th>
			 -->
				<th>주문번호</th>
				<th>유입경로</th>
				<th>주문상태</th>
				<th>주문금액</th>
				<th>할인금액</th>
				<th>배송비</th>
				<th>결제금액</th>
				<th>포인트결제</th>
				<th>쿠폰결제</th>
				<th>배송비쿠폰결제</th>
			</tr>
			<c:forEach var="result" items="${list}" varStatus="status">
			<tr class="r1">
				<td class="fst">
					<fmt:parseDate value="${result.ORD_DY}" var="dateFmt" pattern="yyyyMMdd" />
					<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" />
				</td>
			<!--	<td>${result.FIRST_ORDER_ID}</td>
			  -->	<td>${result.ORDER_ID}</td>
				<td>${result.ORDER_PATH_NM}</td>
				
				<td>${result.STATUS_NM}</td>
				<td style='text-align: right'><fmt:formatNumber value="${result.ORDER_ITEM_AMT_SUM}" pattern="#,###,###,###.##" /></td>
				<td style='text-align: right'><fmt:formatNumber value="${result.TOTAL_DC_AMT_SUM}" pattern="#,###,###,###.##" /></td>
				<td style='text-align: right'><fmt:formatNumber value="${result.DELIVERY_AMT_SUM}" pattern="#,###,###,###.##" /></td>
				<td style='text-align: right'><fmt:formatNumber value="${result.APPLY_TOT_AMT}" pattern="#,###,###,###.##" /></td>
				<td style='text-align: right'><fmt:formatNumber value="${result.APPLY_POINT_AMT}" pattern="#,###,###,###.##" /></td>
				<td style='text-align: right'><fmt:formatNumber value="${result.APPLY_COUPON_AMT}" pattern="#,###,###,###.##" /></td>
				<td style='text-align: right'><fmt:formatNumber value="${result.APPLY_DELIV_COUPON_AMT}" pattern="#,###,###,###.##" /></td>
			</tr>
			</c:forEach>
			<c:if test="${empty list}">
			<tr class="r1">
				<td colspan="11"><spring:message code="msg.common.info.nodata"/></td>
			</tr>
			</c:if>
			</table>
		</div>
		<!-- 2검색내역 // -->

		<div id="paging">
			<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="goPage" />
		<input type="hidden" name="pageIndex" />
		</div>
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
					<li>통계</li>
					<li class="last">네이버지식쇼핑/쇼핑캐스트</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
 
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>