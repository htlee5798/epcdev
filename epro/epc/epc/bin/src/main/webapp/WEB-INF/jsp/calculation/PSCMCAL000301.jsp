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
	  response.setHeader("Content-Disposition", "attachment;filename=PSCMSTA000401.xls;") ;
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
					<col width="5%"><!-- 순번 -->
					<col width="7%"><!-- 정산월 -->
					<col width="7%"><!-- 업체코드 -->
					<col width="10%"><!-- 도서산간구분 -->
					<col width="10%"><!-- 주문번호 -->
					<col width="10%"><!-- 원주문번호 -->
					<col width="10%"><!--  주문일자-->
					<col width="10%"><!--  주문상태-->
					<col width="10%"><!--  매출상태-->
					<col width="10%"><!--  주문금액-->
					<col width="10%"><!--  주문자-->
					<col width="10%"><!--  배송료-->
					</colgroup>
					<tr>
						<th class="fst" bgcolor="cccccc">순번</th>
						<th bgcolor="cccccc">정산월</th>
						<th bgcolor="cccccc">업체코드</th>
						<th bgcolor="cccccc">도서산간구분</th>
						<th bgcolor="cccccc">주문번호</th>
						<th bgcolor="cccccc">원주문번호</th>
						<th bgcolor="cccccc">주문일자</th>
						<th bgcolor="cccccc">주문상태</th>
						<th bgcolor="cccccc">매출상태</th>
						<th bgcolor="cccccc">주문금액</th>
						<th bgcolor="cccccc">주문자</th>
						<th bgcolor="cccccc">배송료</th>
						
					</tr>
					
			<c:choose>
				<c:when test="${not empty list}">
					<c:forEach var="result" items="${list}" varStatus="status">
					<tr class="r1">					
						<td class="fst" style='mso-number-format:"\@";'>${result.RANK_NUM}</td>
						<td style='mso-number-format:"\@";'>${result.PAY_MM}</td>	
						<td style='mso-number-format:"\@";'>${result.VEN_CD}</td>	
						<td style='mso-number-format:"\@";'>${result.ISLND_REGN_CHEK}</td>
						<td style='mso-number-format:"\@";'>${result.ORDER_ID}</td>
						<td style='mso-number-format:"\@";'>${result.UP_ORDER_ID}</td>
						<td style='mso-number-format:"\@";'>${result.ORD_DY}</td>
						<td style='mso-number-format:"\@";'>${result.ORD_STS_NM}</td>
						<td style='mso-number-format:"\@";'>${result.SALE_STS_NM}</td>
						<td style='text-align: right'><fmt:formatNumber value="${result.ORD_AMT}" pattern="#,##0" /></td>
						<td style='mso-number-format:"\@";'>${result.CUST_NM}</td>
						<td style='text-align: right'><fmt:formatNumber value="${result.DELIVERY_AMT}" pattern="#,##0" /></td>
						
					</tr>
					</c:forEach>
				</c:when>
					
				<c:otherwise>
					<tr class="r1">
						<td class="fst" colspan="12" style="text-align: center;"><spring:message code="msg.common.info.nodata"/></td>
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