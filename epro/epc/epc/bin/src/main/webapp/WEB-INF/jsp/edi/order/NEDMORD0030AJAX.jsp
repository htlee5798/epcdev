
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"   uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<c:if test="${not empty orderList }">
								<colgroup>
									<col style="width:12%" />
									<col style="width:14%" />
									<col style="width:12%" />
									<col style="width:12%" />
									<col style="width:12%" />
									<col style="width:12%"  />
									<col style="width:14%" />
									<col style="width:12%"  />
								</colgroup>
								
								<c:set var="tmp"  value="empty" />
								
								
								<c:forEach items="${orderList}" var="list" varStatus="index" >
									<c:if test="${list.ordSlipNo != tmp }">
										<tr class="r1">
											
											<th><spring:message code='epc.ord.ordDy'/></th>
											<th><spring:message code='epc.ord.ordSlipNo'/></th>
											<th><spring:message code='epc.ord.totsupplyNm'/></th>	<!-- 최종납품장소  -->
											<th style="display:none;"><spring:message code='epc.ord.strCd'/></th>
											<th><spring:message code='epc.ord.ctrNm'/></th>	<!-- 입고처  -->
											<th><spring:message code='epc.ord.ordQty'/></th>
											<th><spring:message code='epc.ord.buyPrc'/></th>
											<th><spring:message code='epc.ord.ctrArrDy'/></th>
											<th><spring:message code='epc.ord.splyDy'/></th>
										</tr>
										<tr class="r1">
											<td align=center>${list.ordDy }</td>
											<td align=center style="mso-number-format:'\@'">${list.ordSlipNo }</td>
											<td align=center>${list.strNm }</td>
											<td align=center style="display:none;">${list.strCd }</td>
											<td align=center>${list.ctrNm }</td>
											<td align="right"><fmt:formatNumber value="${list.totQty }" type="number" currencySymbol="" /></td>
											<td align="right"><fmt:formatNumber value="${list.totPrc }" type="number" currencySymbol="" /></td>
											<td align=center>${list.ctrArrDy }</td>
											<td align=center>${list.splyDy }</td>
										</tr>
									</c:if>
									<c:if test="${list.ordSlipNo != tmp }">
									<tr class="r1">
										<th><spring:message code='epc.ord.prodCd'/></th>
										<th><spring:message code='epc.ord.srcmkCd'/></th>
										<th colspan="2"><spring:message code='epc.ord.prodNm'/></th>
										<th><spring:message code='epc.ord.ordIpsu'/></th>
										<th><spring:message code='epc.ord.purUnitCdNm'/></th>
										<th><spring:message code='epc.ord.ordQty'/></th>
										<th><spring:message code='epc.ord.buyPrc'/></th>
									</tr>
									</c:if>
									<tr class="r1">
										<td align=center style="mso-number-format:'\@'">${list.prodCd }</td>
										<td align=center style="mso-number-format:'\@'">${list.srcmkCd }</td>
										<td colspan="2" align="left">&nbsp; ${list.prodNm }</td>
										<td align="right"><fmt:formatNumber value="${list.ordIpsu }" type="number" currencySymbol="" /></td>
										<td align=center>${list.purUnitCdNm }</td>
										<td align="right"><fmt:formatNumber value="${list.ordQty }" type="number" currencySymbol="" /></td>
										<td align="right"><fmt:formatNumber value="${list.buyPrc }" type="number" currencySymbol="" /></td>
										<td align=center style="display:none;">&nbsp;</td>
									</tr>
									<c:set var="tmp" value="${list.ordSlipNo }" />
								</c:forEach>
							</c:if>
													
							<c:if test="${empty orderList }">
								<tr><td colspan="8" align=center><spring:message code='epc.ord.emptySearchResult'/></td></tr>
							</c:if>
