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
	  response.setHeader("Content-Disposition", "attachment;filename=PSCMSTA001101.xls;") ;
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
						<col width="10%">
						<col width="9%">
						<col width="9%">
						<col width="9%">
						<col width="9%">
						<col width="9%">
						<col width="9%">
						<col width="8%">
						<col width="10%">
					</colgroup>
					<tr>
						<th class="fst" bgcolor="cccccc" >주문일자</th>
						<th bgcolor="cccccc" >최초주문번호</th>
						<th bgcolor="cccccc" >주문번호</th>
						<th bgcolor="cccccc" >유입경로</th>
						<th bgcolor="cccccc" >주문상태</th>
						<th bgcolor="cccccc" >주문금액</th>
						<th bgcolor="cccccc" >할인금액</th>
						<th bgcolor="cccccc" >배송비</th>
						<th bgcolor="cccccc" >결제금액</th>
						<th bgcolor="cccccc" >매출확정일자</th>
						<th bgcolor="cccccc" >쿠폰결제</th>
						<th bgcolor="cccccc" >배송비쿠폰결제</th>
					</tr>
					<c:forEach var="result" items="${list}" varStatus="status">
					<tr class="r1">
						<td class="fst" style='text-align: center'>
							<fmt:parseDate value="${result.ORD_DY}" var="dateFmt" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" />
						</td>
						<td style='mso-number-format:"\@";'>${result.FIRST_ORDER_ID}</td>
						<td style='mso-number-format:"\@";'>${result.ORDER_ID}</td>
						<td style='mso-number-format:"\@";'>${result.ORDER_PATH_NM}</td>
						<td style='mso-number-format:"\@";'>${result.STATUS_NM}</td>
						<td style='text-align: right'><fmt:formatNumber value="${result.ORDER_ITEM_AMT_SUM}" pattern="#,###,###,###.##" /></td>
						<td style='text-align: right'><fmt:formatNumber value="${result.TOTAL_DC_AMT_SUM}" pattern="#,###,###,###.##" /></td>
						<td style='text-align: right'><fmt:formatNumber value="${result.DELIVERY_AMT_SUM}" pattern="#,###,###,###.##" /></td>
						<td style='text-align: right'><fmt:formatNumber value="${result.APPLY_TOT_AMT}" pattern="#,###,###,###.##" /></td>
						<td style='text-align: center'>
							<fmt:parseDate 	value="${result.LST_SALE_CFM_DATE}" var="ltDateFmt" pattern="yyyyMMdd" />
							<fmt:formatDate value="${ltDateFmt}" pattern="yyyy-MM-dd" />
						</td>
						<td style='text-align: right'><fmt:formatNumber value="${result.APPLY_COUPON_AMT}" pattern="#,###,###,###.##" /></td>
						<td style='text-align: right'><fmt:formatNumber value="${result.APPLY_DELIV_COUPON_AMT}" pattern="#,###,###,###.##" /></td>
					</tr>
					</c:forEach>
					<c:if test="${empty list}">
					<tr class="r1">
						<td  style="text-align: center" colspan="12"><spring:message code="msg.common.info.nodata"/></td>
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