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
	  response.setHeader("Content-Disposition", "attachment;filename=PSCMDLV000601.xls;") ;
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
						<th bgcolor="cccccc">주문일자</th>
						<th bgcolor="cccccc">상품명</th>
						<th bgcolor="cccccc">옵션</th>
						<th bgcolor="cccccc">상품가격</th>
						<th bgcolor="cccccc">주문수량</th>
						<th bgcolor="cccccc">배송비</th>
						<th bgcolor="cccccc">주문번호</th>
						<th bgcolor="cccccc">배송지순번</th>
						<th bgcolor="cccccc">협력업체ID</th>					
						<th bgcolor="cccccc">운송장번호</th>
						<th bgcolor="cccccc">추가운송장번호</th>
						<th bgcolor="cccccc">택배사코드</th>
						<th bgcolor="cccccc">온라인배송유형(일반:01 설치:02 무형:03)</th>
					</tr>
	<c:choose>		
		<c:when test="${list != null}">		
			<c:forEach var="list" items="${list}">
					<tr>
						<td>
							<fmt:parseDate value="${list.ORD_DY}" var="dateFmt" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" />						
						</td>
						<td><c:out value="${list.PROD_NM}"/></td>
						<td><c:out value="${list.ITEM_OPTION}"/></td>
						<td style='mso-number-format:"\@";'><fmt:formatNumber value="${list.TOT_SELL_AMT}" pattern="#,##0"/></td>
						<td style='mso-number-format:"\@";'><fmt:formatNumber value="${list.ORD_QTY}" pattern="#,##0"/></td>
						<td style='mso-number-format:"\@";'><fmt:formatNumber value="${list.DELIV_AMT}" pattern="#,##0"/></td>
						<td style='mso-number-format:"\@";'><c:out value="${list.ORDER_ID}"/></td>
						<td style='mso-number-format:"\@";'><c:out value="${list.DELIVERY_ID}"/></td>
						<td style='mso-number-format:"\@";'><c:out value="${list.VEN_CD}"/></td>
						<td style='mso-number-format:"\@";'></td>
						<td style='mso-number-format:"\@";'></td>
						<td style='mso-number-format:"\@";'></td>
						<td style='mso-number-format:"\@";'></td>
					</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
					<tr class="r1">
						<td colspan="5" style="text-align: center"><spring:message code="msg.common.info.nodata"/></td>
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