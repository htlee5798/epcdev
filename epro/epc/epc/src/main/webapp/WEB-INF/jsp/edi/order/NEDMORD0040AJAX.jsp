
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"   uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<colgroup>
									<col style="width:80px;" />
									<col style="width:80px;" />
									<col style="width:80px;" />
									<col />
									<col style="width:60px;" />
									<col style="width:60px;" />
									<col style="width:50px;" />
									<col style="width:70px;" />
									<col style="width:70px;" />
									<col style="width:90px;"/>
								</colgroup>
							<c:if test="${not empty orderList }">
							
								<c:set var="tmp"  value="empty" />
								<c:set var="total_qty"  value="0" />
								<c:set var="total_prc"  value="0" />
								
							
								<c:forEach items="${orderList}" var="list" varStatus="index" >
								     <c:if test="${list.prodCd != tmp and index.count > 1}">
										<th colspan="8" class="fst" align=center><spring:message code='epc.ord.perDaySum'/></th>
										<th align=right><fmt:formatNumber value="${total_qty }" type="number" currencySymbol="" /></th>
										<th align=right><fmt:formatNumber value="${total_prc }" type="number" currencySymbol="" /></th>
										<c:set var="total_qty" value="${0 }" />
										<c:set var="total_prc" value="${0 }" />
									</c:if>
									
									<tr class="r1">
										<td align=center><c:out value ="${list.strNm }" /></td>
										<td align=center style="display:none;"><c:out value ="${list.strCd }" /></td>
										<td align=center style="mso-number-format:'\@'"><c:out value ="${list.prodCd }" /></td>
										<td align=center style="mso-number-format:'\@'"><c:out value ="${list.srcmkCd }" /></td>
										<td align="left">&nbsp;<c:out value ="${list.prodNm }" /></td>
										<td align=center><c:out value ="${list.prodStd }" /></td>
										<td align=center><c:out value ="${list.purUnitCdNm }" /></td>
										<td align="right"><fmt:formatNumber value="${list.ordIpsu }" type="number" currencySymbol="" /></td>
										<td align="right"><fmt:formatNumber value="${list.buyDan }" type="number" currencySymbol="" /></td>
										<td align="right"><fmt:formatNumber value="${list.ordQty }" type="number" currencySymbol="" /></td>
										<td align="right"><fmt:formatNumber value="${list.buyPrc }" type="number" currencySymbol="" /></td>
									</tr>
									
									
									
									<c:set var="total_qty" value="${total_qty + list.ordQty }" />
									<c:set var="total_prc" value="${total_prc + list.buyPrc }" />
									
									<c:if test="${index.last }">
										<th colspan="8" class="fst" align=center><spring:message code='epc.ord.perDaySum'/></th>
										<td align=center style="display:none;">&nbsp;</td>
										<th align=right><fmt:formatNumber value="${total_qty }" type="number" currencySymbol="" /></th>
										<th align=right><fmt:formatNumber value="${total_prc }" type="number" currencySymbol="" /></th>
										<c:set var="total_qty" value="${0 }" />
										<c:set var="total_prc" value="${0 }" />
									</c:if>
									
									
									<c:set var="tmp" value="${list.prodCd }" />
								</c:forEach>
							</c:if>
							
							<c:if test="${empty orderList }">
								<tr><td colspan="10" align=center><spring:message code='epc.ord.emptySearchResult'/></td></tr>
							</c:if>