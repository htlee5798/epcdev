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

<!-- statistics/PSCMSTA0009 -->
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
	
	var url = '<c:url value="/statistics/selectLotteCardMallObjectCalList.do"/>';
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

	var url = '<c:url value="/statistics/selectLotteCardMallObjectCalListExcel.do"/>';
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
				<th>회원번호</th>
					<td class="text"><input type="text" name="aMemberNo" value="<c:out value="${searchVO.aMemberNo}" />" onKeyPress="if ((event.keyCode&lt;48)||(event.keyCode&gt;57)) event.returnValue=false;" style="width:70%;" maxlength="20"/></td>											
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			
			<c:set var="initFlag" value="${empty searchVO.searchUseYn ? 'Y' : 'N'}"/>
<%-- 			<c:if test="${initFlag != 'Y'}"> --%>


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
						    <col width="3%"><!-- 순번 -->							
							<col width="8%"><!-- 주문일자 -->
							<col width="10%"><!--  주문번호-->
							<col width="8%"><!--  총주문금액-->						
							<col width="9%"><!--  카드승인번호-->
							<col width="8%"><!--  결제금액-->
							<col width="8%"><!--  쿠폰결제금액-->
							<col width="7%"><!--  포인트결제금액-->
							<col width="7%"><!--  예치금결제금액-->
							<col width="8%"><!--  승인일자-->
							<col width="6%"><!--  승인시각-->
							<col width="8%"><!--  승인기관-->
							<col width="10%"><!--  가맹점코드-->
					</colgroup>
					<tr>
					<th class="fst">순번</th>
						<th>주문일자</th>
						<th>주문번호  </th>
						<th>총주문<br>금액</th>						
						<th>카드승인번호</th>
						<th>결제<br>금액</th>
						<th>쿠폰결제<br></>금액</th>
						<th>포인트결제<br>금액</th>
						<th>예치금결제<br>금액</th>
						<th>승인일자</th>
						<th>승인시각</th>
						<th>승인기관</th>
						<th>가맹점코드</th>
					</tr>
					
			<c:choose>
				<c:when test="${not empty list}">
					<c:forEach var="result" items="${list}" varStatus="status">
					<tr class="r1">
				<td class="fst">${result.RANK_NUM}</td>
				<td>
					<fmt:parseDate value="${result.ORD_DY}" var="dateFmt" pattern="yyyyMMdd" />
					<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" />
				</td>
						<td>${result.ORDER_ID}</td>					
						<td style='text-align: right'><fmt:formatNumber value="${result.ORDER_AMT}" pattern="#,###,###,###.##" /></td>
						<td>${result.APPRO_NO}</td>
						<td style='text-align: right'><fmt:formatNumber value="${result.DPST_AMT}" pattern="#,###,###,###.##" /></td>
						<td style='text-align: right'><fmt:formatNumber value="${result.COUPON_DPST_AMT}" pattern="#,###,###,###.##" /></td>
						<td style='text-align: right'><fmt:formatNumber value="${result.POINT_DPST_AMT}" pattern="#,###,###,###.##" /></td>
						<td style='text-align: right'><fmt:formatNumber value="${result.DEPOSIT_DPST_AMT}" pattern="#,###,###,###.##" /></td>
					    <td>${result.APRV_DPST_FNSH_DY}</td>
						<td>${result.APRV_DPST_FNSH_TM}</td>
						<td>${result.APPRO_ORG}</td>
						<td>${result.TRAN_ID}</td>
					</tr>
					</c:forEach>
				</c:when>
					
				<c:otherwise>
					<tr class="r1">
						<td class="fst" colspan="14"><spring:message code="msg.common.info.nodata"/></td>
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
					<li class="last">롯데카드몰정산대상관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->	
	
</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>