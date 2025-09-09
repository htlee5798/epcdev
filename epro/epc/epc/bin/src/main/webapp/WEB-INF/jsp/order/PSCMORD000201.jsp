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
	  response.setHeader("Content-Disposition", "attachment;filename=PSCMORD000201.xls;") ;
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
							<col width="12%">
							<col width="10%">
							<col width="26%">
							<col width="10%">
							<col width="10%">
							<col width="8%">
							<col width="8%">
							<col width="8%">
							<col width="8%">
						</colgroup>
						<tr>
							<th rowspan="2" bgcolor="cccccc">상품코드 </th>
							<th rowspan="2" bgcolor="cccccc">대표상품코드</th>
							<th rowspan="2" bgcolor="cccccc">상품명</th>
							<th rowspan="2" bgcolor="cccccc">대분류</th>
							<th rowspan="2" bgcolor="cccccc">중분류</th>
							<th colspan="2" bgcolor="cccccc">일반</th>
							<th colspan="2" bgcolor="cccccc">명절</th>
						</tr>
						<tr>
							<th bgcolor="cccccc">매출수량 </th>
							<th bgcolor="cccccc">매출액</th>
							<th bgcolor="cccccc">매출수량</th>
							<th bgcolor="cccccc">매출액</th>
						</tr>
				<c:choose>			
				<c:when test="${not empty list}">	
					<c:forEach var="result" items="${list}" varStatus="status">
						<tr class="r1">
							<td class="fst" style='mso-number-format:"\@";'><c:out value="${result.PROD_CD }"/></td>
							<td style='mso-number-format:"\@";'><c:out value="${result.REP_PROD_CD }"/></td>
							<td><c:out value="${result.PROD_NM }"/></td>
							<td><c:out value="${result.L1_NM }"/></td>
							<td><c:out value="${result.L2_NM }"/></td>
							<td style='text-align: right'><fmt:formatNumber value="${result.LOCAL_CNT }" pattern="#,##0" /></td>
							<td style='text-align: right'><fmt:formatNumber value="${result.LOCAL_AMT }" pattern="#,##0" /></td>
							<td style='text-align: right'><fmt:formatNumber value="${result.HOLI_CNT }" pattern="#,##0" /></td>
							<td style='text-align: right'><fmt:formatNumber value="${result.HOLI_AMT }" pattern="#,##0" /></td>
						</tr>
					</c:forEach>
				</c:when>					
				<c:otherwise>	
						<tr class="r1">
							<td colspan="9"><spring:message code="msg.common.info.nodata"/></td>
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