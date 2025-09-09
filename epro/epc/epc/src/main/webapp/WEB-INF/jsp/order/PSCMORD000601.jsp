<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat"%>
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
<meta http-equiv="Cache-Control" content="No-Cache">
<meta http-equiv="Pragma" content="No-Cache">
<meta name="ProgId" content="Excel.Sheet">
<meta name="Generator" content="Microsoft Excel 9">
<%
	response.setHeader("Content-Disposition", "attachment;filename=PSCMORD000601.xls;");
	response.setContentType("application/vnd.ms-excel;charset=UTF-8");
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
							<col width="30%">
							<col width="30%">
							<col width="30%">
							<col width="30%">
						</colgroup>
						<tr>
							<th bgcolor="cccccc">점포명 </th>
							<th bgcolor="cccccc">점포코드 </th>
							<th bgcolor="cccccc">매출수량</th>
							<th bgcolor="cccccc">매출총액</th>
						</tr>
						<c:set var="total_qty" value="0" />
						<c:set var="total_amt" value="0" />
				<c:choose>
				<c:when test="${not empty saleList}">
					<c:forEach var="result" items="${saleList}" varStatus="status">
						<tr>
							<td align="center">${result.STR_NM }</td>
							<td align="center">${result.STR_CD }</td>
							<td align="right"><fmt:formatNumber value="${result.SALE_QTY }" pattern="#,##0" /></td>
							<td align="right"><fmt:formatNumber value="${result.SALE_AMT }" pattern="#,##0" /></td>
							<c:set var="total_qty"  value="${total_qty + result.SALE_QTY }" />
							<c:set var="total_amt"  value="${total_amt + result.SALE_AMT }" />
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
						<tr class="r1">
							<td colspan="9">${resultMsg }</td>
						</tr>
				</c:otherwise>
				</c:choose>
					</table>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="1" id="testTable3">
						<colgroup>
							<col style="width: 30px;">
							<col style="width: 30px;">
							<col style="width: 30px;">
							<col style="width: 30px;">
							<col />
							<col style="width:17px;" />
						</colgroup>
						<c:if test="${not empty saleList }">
						<tr>
							<th align="center" colspan="2" bgcolor="cccccc"><b>합 계</b></th>
							<th align="right" bgcolor="cccccc"><b><fmt:formatNumber value="${total_qty }" type="number" currencySymbol="" /></b></th>
							<th align="right" bgcolor="cccccc"><b><fmt:formatNumber value="${total_amt }" type="number" currencySymbol="" /></b></th>
						</tr>
						</c:if>
						<c:if test="${empty saleList }"><tr><td colspan="4">&nbsp;</td><th width="17">&nbsp;</th></tr></c:if>
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