<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<c:import url="/common/commonHead.do" />

<!-- statistics/PSCMSTA0003 -->
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
 		openCal('searchForm.startDate');
 	});
 	$('#endDate').click(function() {
 		openCal('searchForm.endDate');
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
	var form = document.searchForm;
	
	var startDate = form.startDate.value.replace( /-/gi, '' );
	var endDate   = form.endDate.value.replace( /-/gi, '' );
	
	if(startDate>endDate){
		alert('시작일자가 종료일자보다 클수 없습니다.');
		return;
	}
	
	var url = '<c:url value="/statistics/selectAsianaBalanceAccountList.do"/>';
	form.searchUseYn.value = 'serach';
	form.pageIndex.value = pageIndex;
	form.action = url;
	form.submit();
}


/** ********************************************************
 * execl
 ******************************************************** */
function doExcel(){
	var f = document.searchForm;
	
	var startDate = f.startDate.value.replace( /-/gi, '' );
	var endDate   = f.endDate.value.replace( /-/gi, '' );
	
	if(startDate>endDate){
		alert('시작일자가 종료일자보다 클수 없습니다.');
		return;
	}	

	var url = '<c:url value="/statistics/selectAsianaBalanceAccountListExcel.do"/>';
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
	
	<form name="searchForm" method="post">
	
		<input type="hidden" name="searchUseYn" value="${searchVO.searchUseYn }" />
	
<!-- 		<input type="hidden" name="startDate" value="2011-03-01"/> -->
<!-- 		<input type="hidden" name="endDate" value="2011-03-01"/> -->
	
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
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="1">
					<colgroup>
						<col width="15%">
						<col width="30%">
						<col width="10%">
						<col width="20%">
						<col width="10%">
						<col width="15%">
					</colgroup>
					<tr>
						<th>
							<select name="searchGbn" class="select" style="width:70%;" >
								<option value="1" <c:if test="${searchVO.searchGbn eq '1'}">selected="selected"</c:if>>주문일</option>
								<option value="2" <c:if test="${searchVO.searchGbn eq '2'}">selected="selected"</c:if>>매출완료일</option>
							</select>
						</th>
						<td>
							<input type="text" name="startDate" id="startDate" value="<c:out value="${searchVO.startDate}" />" style="width:30%;" class="day" readOnly="readonly" /> <a href="javascript:openCal('searchForm.startDate');"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a> 
							~
							<input type="text" name="endDate" id="endDate" value="<c:out value="${searchVO.endDate}" />" style="width:30%;" class="day" readOnly="readonly" /> <a href="javascript:openCal('searchForm.endDate');"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>					
						</td>
		
						<th>주문번호</th>
						<td class='text'><input style='ime-mode:disabled' value='<c:out value="${searchVO.orderId}" />' onKeyPress="if ((event.keyCode&lt;48)||(event.keyCode&gt;57)) event.returnValue=false;" type='text' name='orderId'  style="width:70%;" maxlength="12"/></td>
						
						<th>회원명</th>
						<td class="text"><input type="text" name="custNm" value="<c:out value="${searchVO.custNm}" />" style="width:70%;" maxlength="20"/></td>
					</tr>
						<th>아시아나회원번호</th>
						<td class="text"><input type="text" name="aMemberNo" value="<c:out value="${searchVO.aMemberNo}" />" onKeyPress="if ((event.keyCode&lt;48)||(event.keyCode&gt;57)) event.returnValue=false;" style="width:70%;" maxlength="20"/></td>			
						<th></th>
						<td></td>
						<th></th>
						<td></td>								
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			
			<c:set var="initFlag" value="${empty searchVO.searchUseYn ? 'Y' : 'N'}"/>
<%-- 			<c:if test="${initFlag != 'Y'}"> --%>

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
				<col style="width:20%" />
				<col style="width:20%" />
				<col style="width:20%" />
				<col style="width:20%" />
			  </colgroup>
			  <tr>
				<th>총건수</th>
				<td style='text-align: right'><fmt:formatNumber value="${sum.CNT}" pattern="#,###,###,###.##" /></td>
				<th>총결제금액</th>
				<td style='text-align: right'><fmt:formatNumber value="${sum.DPST_AMT}" pattern="#,###,###,###.##" /></td>
				<th>총적립마일리지</th>
				<td style='text-align: right'><fmt:formatNumber value="${sum.SAVU_MILE}" pattern="#,###,###,###.##" /></td>
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
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
					</colgroup>
					<tr>
						<th class="fst">순번</th>
						<th>주문일자</th>
						<th>주문번호</th>
						<th>회원번호</th>
						<th>회원명</th>
						<th>아시아나<br/>회원번호</th>
						<th>정산코드</th>
						<th>결제금액</th>
						<th>적립마일리지</th>
					</tr>
					
			<c:choose>
				<c:when test="${not empty list}">
					<c:forEach var="result" items="${list}" varStatus="status">
					<tr class="r1">
						<td class="fst">${result.RANK_NUM}</td>
						<td>${result.ORD_DY}</td>
						<td>${result.ORDER_ID}</td>
						<td>${result.MEMBER_NO}</td>
						<td>${result.MEMBER_NM}</td>
						<td>${result.ALGN_MEMBER_NO}</td>
						<td>${result.ADJ_CD}</td>
						<td style='text-align: right'><fmt:formatNumber value="${result.DPST_AMT}" pattern="#,##0" /></td>
						<td style='text-align: right'><fmt:formatNumber value="${result.SAVU_MILE}" pattern="#,##0" /></td>
					</tr>
					</c:forEach>
				</c:when>
					
				<c:otherwise>
					<tr class="r1">
						<td class="fst" colspan="9"><spring:message code="msg.common.info.nodata"/></td>
					</tr>
				</c:otherwise>
			</c:choose>
			
					</table>
				</div>
				<!-- 2검색내역 // -->
				<div id="paging">
					<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="goPage" />
				</div>
			</div>
<%-- 			</c:if> --%>
			<input type="hidden" name="pageIndex" />
		 
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
					<li>통계</li>
					<li class="last">아시아나정산대상리스트</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->	
	
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>