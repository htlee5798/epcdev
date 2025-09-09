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

<!-- statistics/PSCMSTA0004 -->
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
	var url = '<c:url value="/statistics/selectAsianaMileageList.do"/>';
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

	var url = '<c:url value="/statistics/selectAsianaMileageListExcel.do"/>';
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
							<c:if test="${searchVO.searchMonth == yyyymm}"> selected </c:if>
							<c:if test="${empty searchVO.searchMonth}"> selected </c:if>
					<%
							out.println(">"+ formatter2.format(new Date(c.getTimeInMillis())) +"</option>");
							c.add(c.MONTH, 1);
						} while (currentYYMM.compareTo(formatter.format(new Date(c.getTimeInMillis()))) >= 0 );
				    %>	
					</select>     
				</td>
				<th> 처리구분</th>
				<td>
					<select name="procGbn" class="select">
						<option value="%" <c:if test="${searchVO.procGbn == '%'}"> selected </c:if>>전체</option>
						<option value="c" <c:if test="${searchVO.procGbn == 'c'}"> selected </c:if>>정상</option>
						<option value="e" <c:if test="${searchVO.procGbn == 'e'}"> selected </c:if>>에러</option>
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
	
	<c:set var="initFlag" value="${empty searchVO.searchMonth ? 'Y' : 'N'}"/>
	<c:if test="${initFlag != 'Y'}">
	
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
			  </colgroup>
			  <tr>
				<th>총건수</th>
				<td style='text-align: right'><fmt:formatNumber value="${sum.CNT}" pattern="#,###,###,###.##" /></td>
				<th>총적립마일리지</th>
				<td style='text-align: right'><fmt:formatNumber value="${sum.SAVU_MILE}" pattern="#,###,###,###.##" /></td>
				<th>총결제금액</th>
				<td style='text-align: right'><fmt:formatNumber value="${sum.SETL_AMT}" pattern="#,###,###,###.##" /></td>
				<th>총수수료</th>
				<td style='text-align: right'><fmt:formatNumber value="${sum.CHARGE}" pattern="#,###,###,###.##" /></td>
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
			<colgroup>
				<col width="5%">
				<col width="15%">
				<col width="17%">
				<col width="10%">
				<col width="10%">
				<col width="12%">
				<col width="10%">
				<col width="9%">
				<col width="12%">
			</colgroup>
			<tr>
				<th class="fst">순번</th>
				<th>주문일자</th>
				<th>아시아나 회원번호</th>
				<th>정산코드</th>
				<th>적립마일리지</th>
				<th>결제금액</th>
				<th>수수료</th>
				<th>처리구분</th>
				<th>수정일</th>
			</tr>
			<c:forEach var="result" items="${list}" varStatus="status">
			<tr class="r1">
				<td class="fst">${result.RANK_NUM}</td>
				<td>
					<fmt:parseDate value="${result.ORD_DY}" var="dateFmt" pattern="yyyyMMdd" />
					<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" />
				</td>
				<td>${result.ALGN_MEMBER_NO}</td>
				<td>${result.ADJ_NAME}</td>
				<td style='text-align: right'><fmt:formatNumber value="${result.SAVU_MILE}" pattern="#,###,###,###.##" /></td>
				<td style='text-align: right'><fmt:formatNumber value="${result.SETL_AMT}" pattern="#,###,###,###.##" /></td>
				<td style='text-align: right'><fmt:formatNumber value="${result.CHARGE}" pattern="#,###,###,###.##" /></td>
				<td>${result.ERROR_DIVN_NM}</td>
				<td>${result.UPD_DATE}</td>
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
					<li class="last">아시아나정산관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>