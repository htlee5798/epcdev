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
	  response.setHeader("Content-Disposition", "attachment;filename=PSCMSTA000901.xls;") ;
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
							<col width="10%"><!-- 순번 -->
							<col width="10%"><!-- 주문일자 -->
							<col width="7%"><!--  주문번호-->
							<col width="10%"><!-- 총주문금액 -->
						
							<col width="7%"><!--  카드승인번호-->
							<col width="10%"><!--  결제금액-->
							
							<col width="10%"><!--  쿠폰결제금액-->
							<col width="10%"><!--  포인트결제금액-->
							<col width="10%"><!--  예치금결제금액-->

							<col width="8%"><!-- 승인일자 -->
							<col width="5%"><!--  승인시각-->
							<col width="10%"><!--  승인기관-->
							<col width="10%"><!--  가맹점코드-->
					</colgroup>
					<tr>
						<th class="fst" bgcolor="cccccc">순번</th>
						<th bgcolor="cccccc">주문일자</th>
						<th bgcolor="cccccc">주문번호</th>
						<th bgcolor="cccccc">총주문금액</th>
					
						<th bgcolor="cccccc">카드승인번호</th>
						<th bgcolor="cccccc">결제금액</th>
						
						<th bgcolor="cccccc">쿠폰결제금액</th>
						<th bgcolor="cccccc">포인트결제금액</th>
						<th bgcolor="cccccc">예치금결제금액</th>
						
						
						<th bgcolor="cccccc">승인일자</th>
						<th bgcolor="cccccc">승인시각</th>
						<th bgcolor="cccccc">승인기관</th>
						<th bgcolor="cccccc">가맹점코드</th>
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
						<td class="fst" colspan="12"><spring:message code="msg.common.info.nodata"/></td>
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