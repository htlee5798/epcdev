<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib 	prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOTTE MART Back Office System</title>

<!-- CSS URL -->
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>

<!-- JS URL -->
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js" ></script>

<script type="text/javascript" >
/** ********************************************************
 * 조회 처리 함수
 ******************************************************** */
function doSearch() {
	goPage();
}

// 협력사명,협력업체코드 텍스트 필드에서 엔터키 처리
function enterKey() {
	if (event.keyCode == 13) {
		goPage();
	}
};


/** ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function goPage(){
	var form = document.searchForm;

	var url = '<c:url value="/board/selectOrderIdList.do"/>';
	form.action = url;
	form.submit();
}


// 클릭한 것을 지정된 곳에 넣기
function setOrderId(vendorId,prodNm)
{
	opener.setOrderId(vendorId,prodNm);
	top.close();
}



</script>

</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
	<form name="popUp" id="searchForm" method="post">
		<div id="popup">
		
			<div id="p_title1">
				<h1>협력사정보</h1>
				<span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
			</div>

			<br/>	
		
			<div class="popup_contents">
				<!-- 1검색조건 -->
				<div class="bbs_search3">
					<ul class="tit">
						<li class="tit">조회조건</li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<table class="bbs_grid2" cellspacing="0" border="0">
						<colgroup>
							<col width="10%">
							<col width="30%">
							<col width="10%">
							<col width="20%">
							<col width="10%">
							<col width="20%">
						</colgroup>
						<tr>
							<th>주문일자</th>
							<td>
								<input type="text" name="startDate" value="${startDate}" id="startDate" class="day" readonly style="width: 30%;"/>
								<a href="javascript:openCal('searchForm.startDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/pp/layout/btn_cal.gif" alt="" class="middle" /></a> 
							  ~ <input type="text" name="endDate" value="${endDate}" id="endDate" class="day" readonly style="width: 30%;"/>
							    <a href="javascript:openCal('searchForm.endDate')"><img src="${lfn:getString('system.cdn.static.path')}/images/pp/layout/btn_cal.gif" alt="" class="middle" /></a>
							</td>
						</tr>
					</table>
				</div>
				<!-- 	1검색조건 // -->
				
				<br/>
				
				<!-- 	2검색내역 -->
				<div class="wrap_con">
					<div class="bbs_search3">
					
						<ul class="tit">
							<li class="tit">조회내역</li>
						</ul>						
					
						<table class="bbs_search3" cellpadding="0" cellspacing="0" border="0" width="100%">
						<colgroup>
							<col style="width:15%" />
							<col style="width:10%" />
							<col style="width:10%" />
							<col style="width:35%" />
							<col style="width:10%" />
							<col style="width:10%" />
							<col style="width:10%" />
						</colgroup>
							<tr>
								<th>주문번호</th>
								<th>보내는분</th>
								<th>받는분</th>
								<th>상품명</th>
								<th>배송상태</th>
								<th>수량</th>
								<th>주문일자</th>
							</tr>
							
	<c:set var="T_ORDER_ID"/>		
	<c:forEach var="list" items="${list}">
		<c:choose>					
		<c:when test="${list.ROWSPAN > 1}">
							<tr class="r1">
			<c:if test="${list.ORDER_ID != T_ORDER_ID}">					
								<td rowspan="${list.ROWSPAN}">
									<a href="javascript:setOrderId('${list.ORDER_ID}','${list.PROD_NM}')">${list.ORDER_ID}</a>
								</td>
			</c:if>				
								<td style='text-align: center'>${list.CUST_NM}</td>	
								<td style='text-align: center'>${list.RECP_PSN_NM}</td>
								<td style='text-align: left'>${list.PROD_NM}</td>
			<c:if test="${list.ORDER_ID != T_ORDER_ID}">
								<td rowspan="${list.ROWSPAN}">${list.VEN_DELI_STATUS_NM}</td>
			</c:if>				
								<td><fmt:formatNumber value="${list.ORD_QTY}" pattern="#,##0" /></td>	
								<td>
									<fmt:parseDate value="${list.ORD_DY}" var="dateFmt" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" />
								</td>
							</tr>
		</c:when>
		
		<c:otherwise>
							<tr class="r1">
								<td><a href="javascript:setOrderId('${list.ORDER_ID}','${list.PROD_NM}')">${list.ORDER_ID}</a></td>
								<td style='text-align: center'>${list.CUST_NM}</td>	
								<td style='text-align: center'>${list.RECP_PSN_NM}</td>								
								<td style='text-align: left'>${list.PROD_NM}</td>
								<td>${list.VEN_DELI_STATUS_NM}</td>
								<td><fmt:formatNumber value="${list.ORD_QTY}" pattern="#,##0" /></td>
								<td>
									<fmt:parseDate value="${list.ORD_DY}" var="dateFmt" pattern="yyyyMMdd" />
									<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" />								
								</td>
							</tr>		
		</c:otherwise>			
		</c:choose>		
		<c:set var="T_ORDER_ID" value="${list.ORDER_ID}"/>
	</c:forEach>						
	
	<c:if test="${empty list}">
							<tr class="r1">
								<td colspan="7"><spring:message code="msg.common.info.nodata"/></td>
							</tr>
	</c:if>	
			
			
						</table>
					</div>
		
				
				</div>
			</div>
		</div>
	</form>

</body>


</html>