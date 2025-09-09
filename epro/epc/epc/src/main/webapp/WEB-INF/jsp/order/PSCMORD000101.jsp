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
	  response.setHeader("Content-Disposition", "attachment;filename=PSCMORD000101.xls;") ;
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
					<tr>
						<th bgcolor="cccccc">주문번호</th>
						<th bgcolor="cccccc">원주문번호</th>
						<th bgcolor="cccccc">점포코드</th>
						<th bgcolor="cccccc">점포명</th>
						<th bgcolor="cccccc">주문상태</th>
						<th bgcolor="cccccc">매출상태</th>
						<th bgcolor="cccccc">주문자</th>
						<th bgcolor="cccccc">주문금액</th>
						<th bgcolor="cccccc">주문원가</th>
						<th bgcolor="cccccc">할인금액</th>
						<th bgcolor="cccccc">배송료</th>
						<th bgcolor="cccccc">결제금액</th>
						<th bgcolor="cccccc">결제방법</th>
						<th bgcolor="cccccc">주문일자</th>
						<th bgcolor="cccccc">매출일자</th>
					</tr>					
	<c:choose>		
		<c:when test="${list != null}">		
			<c:forEach var="result" items="${list}">
					<tr>
						<td align="center" style='mso-number-format:"\@";'><c:out value="${result.ORDER_ID}"/></td>
						<td align="center" style='mso-number-format:"\@";'><c:out value="${result.UP_ORDER_ID}"/></td>
						<td align="center" style='mso-number-format:"\@";'><c:out value="${result.STR_CD}"/></td>
						<td align="center"><c:out value="${result.STR_NM}"/></td>
						<td align="center"><c:out value="${result.ORD_STS_NM}"/></td>
						<td align="center"><c:out value="${result.SALE_STS_NM}"/></td>
						<td align="center"><c:out value="${result.CUST_NM}"/></td>
						<td align="right"><fmt:formatNumber value="${result.ORDER_ITEM_AMT_SUM}" pattern="#,##0" /></td>
						<td align="right"><fmt:formatNumber value="${result.BUY_PRC}" pattern="#,##0" /></td>
						<td align="right"><fmt:formatNumber value="${result.DC_AMT_SUM}" pattern="#,##0" /></td>
						<td align="right"><fmt:formatNumber value="${result.DELIVERY_AMT_SUM}" pattern="#,##0" /></td>
						<td align="right"><fmt:formatNumber value="${result.APPLY_TOT_AMT}" pattern="#,##0" /></td>
						<td align="center"><c:out value="${result.SETL_TYPE_NM}"/></td>
						<td align="center"><c:out value="${result.ORD_DY}"/></td>
						<td align="center"><c:out value="${result.SALE_DY}"/></td>
					</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
					<tr class="r1">
						<td colspan="5"><spring:message code="msg.common.info.nodata"/></td>
					</tr>					
		</c:otherwise>
	</c:choose>
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