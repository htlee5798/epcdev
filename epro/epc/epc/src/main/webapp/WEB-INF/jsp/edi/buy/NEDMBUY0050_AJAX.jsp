<%--
	Page Name 	: NEDMBUYO0050.jsp
	Description : 매입정보 전표상세별 조회(AJAX) 
	Modification Information
	
	  수정일 			  수정자 				수정내용
	---------- 		---------    		-------------------------------------
	2015.11.17 		O YEUN KWON	 		최초생성
	
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"   uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="headTbl">
<colgroup>
	<col style="width:90px;" />
	<col style="width:150px;" />
	<col/>
	<col style="width:50px;" />
	<col style="width:60px;" />
	<col style="width:50px;" />
	<col style="width:50px;" />
	<col style="width:70px;" />
	<col style="width:70px;" />
	<col style="width:70px;" />
</colgroup>

<c:set var="tmp"  value="empty" />

<c:if test="${not empty resultList }">
	<c:forEach items="${resultList}" var="list" varStatus="index" >
		<c:if test="${list.depth == 1 }">
		<tr>
			<th><spring:message code='text.buy.field.buyKind' /></th>
			<th><spring:message code='epc.buy.header.strNm' /></th>
			<th><spring:message code='epc.buy.header.buySlipNo' /></th>
			<th><spring:message code='epc.buy.header.ordFg' /></th>
			<th><spring:message code='epc.buy.header.ordDy' /></th>
			<th><spring:message code='epc.buy.header.totQty' /></th>
			<th><spring:message code='epc.buy.header.totPrc' /></th>
			<th><spring:message code='epc.buy.header.ctrArrDy' /></th>
			<th><spring:message code='epc.buy.header.splyDy' /></th>
			<th><spring:message code='epc.buy.header.routeFg' /></th>
		</tr>
		
		<tr class="r1">
			<td align="center"><c:out value='${list.buyRtnNm }'/></td>
			<td align="center"><c:out value='${list.strNmT }'/></td>
			<td align="center" style="mso-number-format:'\@'"><c:out value='${list.buySlipNo }'/></td>
			<td align="center"><c:out value='${list.ordFg }'/></td>
			<td align="center"><c:out value='${list.ordDy }'/></td>
			<td align="right"><fmt:formatNumber value="${list.totQty }" type="number" currencySymbol="" /></td>
			<td align="right"><fmt:formatNumber value="${list.totPrc }" type="number" currencySymbol="" /></td>
			<td align="center"><c:out value='${list.ctrArrDy }'/></td>
			<td align="center"><c:out value='${list.splyDy }'/></td>
			<td align="center"><c:out value='${list.routeFg }'/></td>
		</tr>
		
		</c:if>
		<c:if test="${list.depth == 2 }">
			<c:if test="${list.buyRtnNm != tmp }">
				<tr>
					<th><spring:message code='epc.buy.header.prodCd' /></th>
					<th><spring:message code='epc.buy.header.prodNm' /></th>
					<th><spring:message code='epc.buy.header.prodStd' /></th>
					<th><spring:message code='epc.buy.header.ordIpsu' /></th>
					<th><spring:message code='epc.buy.header.danwi' /></th>
					<th><spring:message code='epc.buy.header.totQty' /></th>
					<th><spring:message code='epc.buy.header.totPrc' /></th>
					<th><spring:message code='epc.buy.header.buyBoxQty' /></th>
					<th><spring:message code='epc.buy.header.buyQty' /></th>
					<th><spring:message code='epc.buy.header.buyBuyPrc' /></th>
				</tr>
			</c:if>

		<tr class="r1">
			<td align="center" style="mso-number-format:'\@'"><c:out value='${list.prodCd }'/></td>
			<td ><c:out value='${list.prodNm }'/></td>
			<td align="center" style="mso-number-format:'\@'"><c:out value='${list.prodStd }'/></td>
			<td align="center"><c:out value='${list.ordIpsu }'/></td>
			<td align="center"><c:out value='${list.danwi }'/></td>
			<td align="right"><fmt:formatNumber value="${list.totQty }" type="number" currencySymbol="" /></td>
			<td align="right"><fmt:formatNumber value="${list.totPrc }" type="number" currencySymbol="" /></td>
			<td align="right"><fmt:formatNumber value="${list.buyBoxQty }" type="number" currencySymbol="" /></td>
			<td align="right"><fmt:formatNumber value="${list.buyQty }" type="number" currencySymbol="" /></td>
			<td align="right"><fmt:formatNumber value="${list.buyBuyPrc }" type="number" currencySymbol="" /></td>
		</tr>
		</c:if>
		<c:set var="tmp" value="${list.buyRtnNm }" />
	</c:forEach>
</c:if>
<c:if test="${empty resultList }">
	<tr><td colspan="11" align=center><spring:message code='msg.info.nodata' /></td></tr>
</c:if>
</table>

