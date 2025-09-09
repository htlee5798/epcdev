
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"   uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<c:if test="${not empty orderList }">
						<input type="hidden" name="firstList" value="exist"/>
							<colgroup>
								<col style="width:12%" />
								<col style="width:19%" />
								<col style="width:7%" />
								<col style="width:12%" />
								<col style="width:12%" />
								<col style="width:12%"  />
								<col style="width:14%" />
								<col style="width:12%"  />
							</colgroup>
							
							<c:set var="tmp"  value="empty" />
							<c:forEach items="${orderList}" var="list" varStatus="index" >
								<c:if test="${list.ordSlipNo != tmp }">
									<tr>
										<th><spring:message code='epc.ord.ordDy'/></th>
										<th colspan="2"><spring:message code='epc.ord.ordSlipNo'/></th>
										<th><spring:message code='epc.ord.ordQty'/></th>
										<th><spring:message code='epc.ord.buyPrc'/></th>
										<th colspan="2"><spring:message code='epc.ord.ableDeleveryTime'/></th>
										<th><spring:message code='epc.ord.userHit'/></th>
									</tr>
									<tr  class="r1">
										<input type="hidden" id="ord_no" name="ord_no" value="<c:out value="${list.ordSlipNo }" />">
										<input type="hidden" id="ven" name="ven" value="<c:out value="${list.venCd }" />">
										<td align="center"><c:out value="${list.ordDy }" /></td>
										<td colspan="2" align="center"><c:out value="${list.ordSlipNo  }" /></td>
										<td align="right"><fmt:formatNumber value="${list.totQty }" type="number" currencySymbol="" /></td>
										<td align="right"><fmt:formatNumber value="${list.totPrc }" type="number" currencySymbol="" /></td>
										<td colspan="2" align="center" class="splyTm" data-ord-slip-no = "<c:out value="${list.ordSlipNo }" />"><input type="text" size="3" value="<c:out value="${list.hour }" />" id="hour" name="hour" maxlength="2" onkeyup="javascript:keyValid(this);"><spring:message code='epc.ord.hour'/><input type="text" size="3" value="<c:out value="${list.min }" />" id="min" name="min" maxlength="2" onkeyup="javascript:keyValid(this);"><spring:message code='epc.ord.min'/></td>
										<td align=center><c:out value="${list.userHit }" /></td>
								</tr>
								</c:if>
								<c:if test="${list.ordSlipNo != tmp }">
								<tr>
									<th><spring:message code='epc.ord.prodCd'/></th>
									<th><spring:message code='epc.ord.prodNm'/></th>
									<th><spring:message code='epc.ord.prodStd'/></th>
									<th><spring:message code='epc.ord.ordIpsu'/></th>
									<th><spring:message code='epc.ord.srcmkCd'/></th>
									<th><spring:message code='epc.ord.ordQty'/></th>
									<th><spring:message code='epc.ord.buyPrc'/></th>
									<th><spring:message code='epc.ord.splyAbleQty'/></th>
								</tr>
								</c:if>
								<tr  class="r1">
									<input type="hidden" id="prod_no" name="prod_no" value="<c:out value="${list.prodCd }" />">
									<td align=center><c:out value="${list.prodCd }" /></td>
									<td align="left">&nbsp; <c:out value="${list.prodNm }" /></td>
									<td align=center><c:out value="${list.ordUnit }" /></td>
									<td align="right"><fmt:formatNumber value="${list.ordIpsu }" type="number" currencySymbol="" /></td>
									<td align=center><c:out value="${list.ordSlipNo }" /></td>
									<input type="hidden" name="ordqty" value="<c:out value="${list.ordQty }" />"/>
									<td align="right"><fmt:formatNumber value="${list.ordQty }" type="number" currencySymbol="" /></td>
									<td align="right"><fmt:formatNumber value="${list.buyPrc }" type="number" currencySymbol="" /></td>
									<td align="center"><input type="text" size="5" value="<c:out value="${list.splyAbleQty }" />" id="sply_qty" name="sply_qty" maxlength="9" readonly="readonly" onkeyup="javascript:keyValid(this);"></td>
								</tr>
								<c:set var="tmp" value="${list.ordSlipNo }" />
							</c:forEach>
						</c:if>
						
						<c:if test="${empty orderList }">
							<input type="hidden" name="firstList" value="none"/>
							<tr><td colspan="8" align=center><spring:message code='epc.ord.emptySearchResult'/></td></tr>
						</c:if>