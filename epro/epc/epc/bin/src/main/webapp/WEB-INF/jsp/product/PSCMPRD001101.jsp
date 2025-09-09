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
					</colgroup>
					<tr>
						<th class="fst" bgcolor="cccccc" >촬영일</th>
						<th bgcolor="cccccc" >시작시간</th>
						<th bgcolor="cccccc" >종료시간</th>
						<th bgcolor="cccccc" >업체명</th>
						<th bgcolor="cccccc" >업체코드</th>
						<th bgcolor="cccccc" >내용</th>
					</tr>
					<c:forEach var="result" items="${list}" varStatus="status">
					<tr class="r1">
						<td class="fst" style='mso-number-format:"\@";'>
							<fmt:parseDate value="${result.RSERV_START_DY}" var="dateFmt" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" />
						</td>
						<td style='mso-number-format:"\@";'>${fn:substring(result.RSERV_START_TM,0,2)}:${fn:substring(result.RSERV_START_TM,2,4)}</td>
						<td style='mso-number-format:"\@";'>${fn:substring(result.RSERV_END_TM,0,2)}:${fn:substring(result.RSERV_END_TM,2,4)}</td>
						<td style='mso-number-format:"\@";'>${result.VENDOR_NM}</td>
						<td style='mso-number-format:"\@";'>${result.VENDOR_ID}</td>
						<td style='mso-number-format:"\@";'>${result.SCDL_MEMO}</td>
					</tr>
					</c:forEach>
					<c:if test="${empty list}">
					<tr class="r1">
						<td  style="text-align: center" colspan="6"><spring:message code="msg.common.info.nodata"/></td>
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