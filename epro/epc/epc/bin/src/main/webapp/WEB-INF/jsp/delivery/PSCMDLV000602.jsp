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
	  response.setHeader("Content-Disposition", "attachment;filename=PSCMDLV000602.xls;") ;
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
						<th bgcolor="cccccc">EC주문번호</th>
						<th bgcolor="cccccc">주문순번</th>
						<th bgcolor="cccccc">배송지순번</th>
						<th bgcolor="cccccc">점포코드</th>
						<th bgcolor="cccccc">점포명</th>
						<th bgcolor="cccccc">주문일자</th>
						<th bgcolor="cccccc">주문상태</th>
						<th bgcolor="cccccc">택배사</th>
						<th bgcolor="cccccc">송장번호</th>
						<th bgcolor="cccccc">추가송장번호</th>
						<th bgcolor="cccccc">발송예정일자</th>
						<th bgcolor="cccccc">지연/불가사유</th>
						<th bgcolor="cccccc">지연/불가사유상세</th>
						<th bgcolor="cccccc">업체코드</th>
						<th bgcolor="cccccc">상품코드</th>
						<th bgcolor="cccccc">판매코드</th>
						<th bgcolor="cccccc">상품명</th>
						<th bgcolor="cccccc">상품가격</th>
						<th bgcolor="cccccc">주문수량</th>
						<th bgcolor="cccccc">배송비</th>
						<th bgcolor="cccccc">개인통관번호</th>						
						<th bgcolor="cccccc">집전화</th>
						<th bgcolor="cccccc">H.P</th>
						<th bgcolor="cccccc">받는분</th>
						<th bgcolor="cccccc">보내는분 H.P</th>
						<th bgcolor="cccccc">보내는분</th>
						<th bgcolor="cccccc">우편번호</th>
						<th bgcolor="cccccc">주소</th>
						<th bgcolor="cccccc">메모</th>
						<th bgcolor="cccccc">옵션</th>
						<th bgcolor="cccccc">규격</th>
						<th bgcolor="cccccc">발송완료일</th>
						<th bgcolor="cccccc">희망발송일</th>
						<th bgcolor="cccccc">온라인배송유형</th>
			
					</tr>					
	<c:choose>		
		<c:when test="${list != null}">		
			<c:forEach var="list" items="${list}">
					<tr>
						<td align="center" style='mso-number-format:"\@";'><c:out value="${list.ORDER_ID}"/></td>
						<td align="center" style='mso-number-format:"\@";'><c:out value="${list.EC_ORDER_ID}"/></td>
						<td align="center" style='mso-number-format:"\@";'><c:out value="${list.ORDER_ITEM_SEQ}"/></td>
						<td align="center" style='mso-number-format:"\@";'><c:out value="${list.DELIVERY_ID}"/></td>
						<td align="center" style='mso-number-format:"\@";'><c:out value="${list.STR_CD}"/></td>
						<td align="center" style='mso-number-format:"\@";'><c:out value="${list.STR_NM}"/></td>
						<td align="center"><fmt:parseDate value="${list.ORD_DY}" var="dateFmt" pattern="yyyyMMdd" />
							<fmt:formatDate value="${dateFmt}" pattern="yyyy-MM-dd" />
						</td>
						<td align="left"><c:out value="${list.ORD_STS_NM}"/></td>
						<td align="left"><c:out value="${list.HODECO_NM}"/></td>
						<td align="center" style='mso-number-format:"\@";'><c:out value="${list.HODECO_INVOICE_NO}"/></td>
						<td align="left"   style='mso-number-format:"\@";'><c:out value="${list.HODECO_ADD_INVOICE_NO}"/></td>
						<td align="center" style='mso-number-format:"\@";'><c:out value="${list.SND_PRAR_DY}"/></td>
						<td align="center" style='mso-number-format:"\@";'><c:out value="${list.DLAY_UNAVL_REASON_NM}"/></td>
						<td align="left" style='mso-number-format:"\@";'><c:out value="${list.DLAY_UNAVL_DTL_REASON}"/></td>
						<td align="center" style='mso-number-format:"\@";'><c:out value="${list.VEN_CD}"/></td>
						<td align="left" style='mso-number-format:"\@";'><c:out value="${list.PROD_CD}"/></td>
						<td align="left" style='mso-number-format:"\@";'><c:out value="${list.MD_SRCMK_CD}"/></td>
						<td align="left"><c:out value="${list.PROD_NM}"/></td>
						<td align="right"><fmt:formatNumber value="${list.TOT_SELL_AMT}" pattern="#,##0"/></td>
						<td align="right"><fmt:formatNumber value="${list.ORD_QTY}" pattern="#,##0"/></td>
						<td align="right"><fmt:formatNumber value="${list.DELIV_AMT}" pattern="#,##0"/></td>
						<td align="center"><c:out value="${list.CURS_CLRN_NUM}"/></td>
						<td align="center"><c:out value="${list.RECP_PSN_TEL_NO}"/></td>
						<td align="center"><c:out value="${list.RECP_PSN_CELL_NO}"/></td>
						<td align="left"><c:out value="${list.TO_PSN_NM}"/></td>						
						<td align="center"><c:out value="${list.CUST_CELL_NO}"/></td>
						<td align="left"><c:out value="${list.FROM_PSN_NM}"/></td>
						<td align="left"><c:out value="${list.RECP_PSN_POST_NO}"/></td>
						<td align="left"><c:out value="${list.ADDR}"/></td>
						<td align="left"><c:out value="${list.DELI_MSG}"/></td>
						<td align="left" style='mso-number-format:"\@";'><c:out value="${list.ITEM_OPTION}"/></td>
						<td align="left"><c:out value="${list.PROD_STANDARD_NM}"/></td>
						
						<td align="center"><c:out  value="${list.DELI_FNSH_DATE}"/></td>
						<td align="center"><c:out  value="${list.ENTP_DELI_PRAR_DY}"/></td>
						<td align="center" style='mso-number-format:"\@";'><c:out value="${list.ONLN_DELI_TYPE_CD}"/></td>
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