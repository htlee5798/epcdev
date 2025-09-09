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
	  response.setHeader("Content-Disposition", "attachment;filename=PSCMSTA001401.xls;") ;
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
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
						<col width="11%">
					</colgroup>
					<tr>
						<th class="fst" bgcolor="cccccc">순번</th>
						<th bgcolor="cccccc">주문구분</th>
						<th bgcolor="cccccc">픽업일자</th>
						<th bgcolor="cccccc">픽업시간</th>
						<th bgcolor="cccccc">주문번호</th>
						<th bgcolor="cccccc">배송상태</th>
						<th bgcolor="cccccc">라벨번호</th>
						<th bgcolor="cccccc">고객명</th>
						<th bgcolor="cccccc">구매금액</th>
						<th bgcolor="cccccc">픽업완료일시</th>
						<th bgcolor="cccccc">인수완료일시</th>
						<th bgcolor="cccccc">마트점포명</th>
						<th bgcolor="cccccc">렌터카지점</th>
						<th bgcolor="cccccc">고객요청사항</th>
					</tr>
			<c:choose>
				<c:when test="${not empty list}">
					<c:forEach var="result" items="${list}" varStatus="status">
						<tr class="r1">
							<td class="fst" style='text-align: center' >${result.NUM}</td>
			        		<td style='text-align: center'>${result.ORD_DIVN}</td>
			        		<td style='text-align: center'>${result.DELI_HOPE_DY}</td>
			        		<td style='text-align: center'>${result.PICKUP_TIME}</td>
							<td style='mso-number-format:"\@"; text-align: right'>${result.ORDER_ID}</td>
							<td style='text-align: center'>${result.DELI_STATUS_CD}</td>
							<td style='text-align: right'>${result.FIRST_BK_NO}</td>
							<td style='text-align: center'>${result.RECP_PSN_NM}</td>
							<td style='text-align: right'><fmt:formatNumber value="${result.TOT_SELL_AMT}" pattern="#,###,###,###.##" /></td>
			        		<td style='text-align: center'>${result.HAND_OVER_DATE}</td>
			        		<td style='text-align: center'>${result.TAKE_OVER_DATE}</td>
							<td style='text-align: center'>${result.STR_NM}</td>
							<td style='text-align: center'>${result.EXT_STR_NM}</td>
							<td style='text-align: center'>${result.CUST_REQ}</td>
						</tr>
					</c:forEach>
				</c:when>
					
				<c:otherwise>
					<tr class="r1">
						<td class="fst" colspan="15" style="text-align: center;"><spring:message code="msg.common.info.nodata"/></td>
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