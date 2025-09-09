<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <meta http-equiv="Content-Type" content="text/html; charset=euc-kr"> -->
<meta http-equiv="Cache-Control" content="No-Cache">
<meta http-equiv="Pragma" content="No-Cache">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 9"> 
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>

<%
	  response.setHeader("Content-Disposition", "attachment;filename=PSCMSTA000101.xls;") ;
	  response.setContentType("application/vnd.ms-excel;charset=UTF-8") ;
	  response.setHeader("Content-Transfer-Encoding", "binary;");
	  response.setHeader("Pragma", "no-cache;");
	  response.setHeader("Expires", "-1;");	  
%>
</head>


<body>

<div id="content_wrap">

	<div class="content_scroll">
	
		<div id="wrap_menu">
		
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="1">
					<colgroup>
						<col width="9%">
						<col width="9%">
						<col width="9%">
						<col width="9%">
						<col width="9%">
						<col width="9%">
						<col width="9%">
						<col width="9%">
					</colgroup>
					<tr>
						<th class="fst" bgcolor="cccccc" >주문일자</th>
						<th bgcolor="cccccc" >주문번호</th>
						<th bgcolor="cccccc" >원주문번호</th>
						<th bgcolor="cccccc" >매출상태</th>
						<th bgcolor="cccccc" >인터넷상품명</th>
						<th bgcolor="cccccc" >주문금액</th>
						<th bgcolor="cccccc" >고객명</th>
						<th bgcolor="cccccc" >배송료</th>
					</tr>
					<c:forEach var="result" items="${list}" varStatus="status">
					<tr class="r1">
						<td class="fst" style='mso-number-format:"\@";'>
							<fmt:parseDate value="${result.ORD_DY}" var="dateFmt" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" />
						</td>
						<td style='mso-number-format:"\@";'>${result.ORDER_ID}</td>
						<td style='mso-number-format:"\@";'>${result.UP_ORDER_ID}</td>
						<td style='mso-number-format:"\@";'>${result.SALE_STS_NM}</td>
						<td style='mso-number-format:"\@";'>${result.PROD_NM}</td>
						<td style='mso-number-format:"\@";text-align: right'><fmt:formatNumber value="${result.ORD_AMT}" pattern="#,###,###,###.##" /></td>
						<td style='mso-number-format:"\@";'>${result.CUST_NM}</td>
						<c:if test="${orderId != result.ORDER_ID || deliveryId != result.DELIVERY_ID}">
						<td style='mso-number-format:"\@";text-align: right'><fmt:formatNumber value="${result.DELIVERY_AMT}" pattern="#,###,###,###.##" /></td>
						</c:if>
					</tr>
					<c:set value="${result.ORDER_ID}" var="orderId" />
					<c:set value="${result.DELIVERY_ID}" var="deliveryId" />
					</c:forEach>
					<c:if test="${empty list}">
					<tr class="r1">
						<td  style="text-align: center" colspan="8"><spring:message code="msg.common.info.nodata"/></td>
					</tr>
					</c:if>
					</table>
				</div>
		
			</div>
			<!-- 2검색내역 // -->
		
		</div>

	</div>

</div>

<!--	@ BODY WRAP  END  	// -->
</body>
</html>