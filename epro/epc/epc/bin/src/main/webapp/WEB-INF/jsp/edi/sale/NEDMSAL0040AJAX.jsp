
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"   uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<ul class="tit">
	<li class="tit"><spring:message	code='epc.sal.search.result' /></li>
</ul>
<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
	<colgroup>
		<col style="width:100px;" />
		<col style="width:60px;" />
		<col style="width:80px;" />
		<col />
		<col style="width:100px;" />
		<col style="width:100px;" />
		<col style="width:130px;" />
		<col style="width:17px;" />
	</colgroup>
	<tr>
		<th><spring:message	code='epc.sal.header.strNm' /></th>
		<th><spring:message	code='epc.sal.header.strCd' /></th>
		<th><spring:message	code='epc.sal.header.prodCd' /></th>
		<th><spring:message	code='epc.sal.header.prodNm' /></th>
		<th><spring:message	code='epc.sal.header.srcmkCd' /></th>
		<th><spring:message	code='epc.sal.header.saleQty' /></th>
		<th><spring:message	code='epc.sal.header.saleAmt' /></th>
		<th>&nbsp;</th>
	</tr>
</table>
<div class="datagrade_scroll_sum">
	<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
		<colgroup>
			<col style="width:100px;" />
			<col style="width:60px;" />
			<col style="width:80px;" />
			<col />
			<col style="width:100px;" />
			<col style="width:100px;" />
			<col style="width:130px;" />
		</colgroup>
		<c:if test="${not empty saleList }">
			<c:set var="tmp"  value="empty" />
			<c:set var="total_qty"  value="0" />
			<c:set var="total_amt"  value="0" />
			<c:set var="total_qty_sum"  value="0" />
			<c:set var="total_amt_sum"  value="0" />
			<c:forEach items="${saleList}" var="list" varStatus="index" >
				<c:if test="${list.strCd != tmp and index.count > 1}">
				<tr>
					<th colspan="5" class="fst" align=center>소계</th>
					<th align=right><fmt:formatNumber value="${total_qty }" type="number" currencySymbol="" /></th>
					<th align=right><fmt:formatNumber value="${total_amt }" type="number" currencySymbol="" /></th>
					<c:set var="total_qty" value="${0 }" />
					<c:set var="total_amt" value="${0 }" />
				</tr>
				</c:if>
				<tr class="r1">
					<td align="center"><c:out value="${list.strNm}" /></td>
					<td align="center"><c:out value="${list.strCd }" /></td>
					<td align="center" style="mso-number-format:'\@'"><c:out value="${list.prodCd }" /></td>
					<td align="left">&nbsp; <c:out value="${list.prodNm}" /></td>
					<td align="center" style="mso-number-format:'\@'"><c:out value="${list.srcmkCd }" /></td>
					<td align="right"><fmt:formatNumber value="${list.saleQty }" type="number" currencySymbol="" /></td>
					<td align="right"><fmt:formatNumber value="${list.saleSaleAmt }" type="number" currencySymbol="" /></td>
				</tr>
				
				<c:set var="total_qty" value="${total_qty + list.saleQty }" />
				<c:set var="total_amt" value="${total_amt + list.saleSaleAmt }" />
				<c:set var="total_qty_sum" value="${total_qty_sum + list.saleQty }" />
				<c:set var="total_amt_sum" value="${total_amt_sum + list.saleSaleAmt }" />
				
				<c:if test="${index.last }">
				<tr>
					<th colspan="5" class="fst" align=center><spring:message	code='epc.sal.header.summary' /></th>
					<th align=right><fmt:formatNumber value="${total_qty }" type="number" currencySymbol="" /></th>
					<th align=right><fmt:formatNumber value="${total_amt }" type="number" currencySymbol="" /></th>
				</tr>
				</c:if>
				
				<c:set var="tmp" value="${list.strCd }" />
			</c:forEach>
			
		</c:if>
		<c:if test="${empty saleList }">
			<tr><td colspan="7" align=center><spring:message	code='epc.ord.emptySearchResult' /></td></tr>
		</c:if>
	</table>
</div>

<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable3">
	<colgroup>
		<col style="width:100px;" />
		<col style="width:60px;" />
		<col style="width:80px;" />
		<col />
		<col style="width:100px;" />
		<col style="width:100px;" />
		<col style="width:130px;" />
		<col style="width:17px;" />
	</colgroup>
	<c:if test="${not empty saleList }">
	<tr class="r1">
		<th colspan="5" class="fst" align=center><b><spring:message	code='epc.sal.header.sum' /></b></th>
		<th align=right><b><fmt:formatNumber value="${total_qty_sum }" type="number" currencySymbol="" /></b></th>
		<th align=right><b><fmt:formatNumber value="${total_amt_sum }" type="number" currencySymbol="" /></b></th>
		<th>&nbsp;</th>
	</tr>
	</c:if>
	<c:if test="${empty saleList }"><tr><td colspan=7>&nbsp;</td><th width=17>&nbsp;</th></tr></c:if>
</table>