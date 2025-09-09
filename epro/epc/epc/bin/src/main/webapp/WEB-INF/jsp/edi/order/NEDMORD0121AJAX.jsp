
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
								<col style="width:9%" />
								<col style="width:9%" />
								<col style="width:9%" />
								<col style="width:9%" />
								<col style="width:9%" />
								<col style="width:9%"  />
								<col style="width:9%" />
								<col style="width:9%"  />
								<col style="width:9%"  />
								<col style="width:9%"  />
							</colgroup>
							
							<tr>
								<td colspan="10">
									<table  cellpadding="0" cellspacing="1" border="0" width=100% bgcolor=efefef>
									<colgroup>
										<col style="width:25%;" />
										<col style="width:25%;" />
										<col style="width:25%;" />
										<col style="width:25%;"   />
									</colgroup>
									<tr>
										<th><spring:message code='epc.ord.prodCd'/></th>
										<th><spring:message code='epc.ord.buyPrice'/></th>
										<th id="tmp"><spring:message code='epc.ord.made'/></th>
										<th rowspan="2">
											<a href="#" class="btn" onclick="javascript:forwardValue();"><span><spring:message code="button.common.setall"/></span></a> <%-- 일괄적용 --%>
										</th>
									</tr>
									<tr >
										<td align=center><input type="text" id="pr_code" name="pr_code"></td>
										<td align=center><input type="text" id="negoprc1" name="negoprc1" onkeyup="javascript:keyValid(this);"></td>
										<td align=center><input type="text" id="origin1" name="origin1"></td>
									</tr>
									
									</table>
								</td>
							</tr>
							<tr>
								<th><spring:message code='epc.ord.prodCd'/></th>
								<th><spring:message code='epc.ord.srcmkCd'/></th>
								<th><spring:message code='epc.ord.prodNm'/></th>
								<th><spring:message code='epc.ord.prodStd'/></th>
								<th><spring:message code='epc.ord.store'/></th>
								<th><spring:message code='epc.ord.ordQty'/></th>
								<th><spring:message code='epc.ord.ableDelevery'/><br><spring:message code='epc.ord.qty'/></th>
								<th><spring:message code='epc.ord.negoBuyPrc'/></th>
								<th><spring:message code='epc.ord.negoBuyPrc2'/></th>
								<th><spring:message code='epc.ord.made'/></th>
							</tr>
							<c:forEach items="${orderList}" var="list" varStatus="index" >
								<input type="hidden" name="ordSlipNo" value="<c:out value="${list.ordSlipNo }" />"/>
								<input type="hidden" name="prodCd" value="<c:out value="${list.prodCd }" />"/>
								<input type="hidden" name="venCd" value="<c:out value="${list.venCd }" />"/>
								<tr class="r1">
									<td class="compare_prod" align=center><c:out value="${list.prodCd }" /></td>
									<td align=center><c:out value="${list.srcmkCd  }" /></td>
									<td align="left">&nbsp;<c:out value="${list.prodNm }" /></td>
									<td align=center><c:out value="${list.prodStd  }" /></td>
									<td align=center><c:out value="${list.strNm  }" /></td>
									<td align="right"><fmt:formatNumber value="${list.ordQty }" type="number" currencySymbol="" /></td>
									<td align="center"><input type="text" size="7" id="sply_qty" name="sply_qty" value="<c:out value="${list.splyAbleQty }" />"  readonly="readonly" maxlength="9" onkeyup="javascript:keyValid(this);"></td>
									<td align="right"><fmt:formatNumber value="${list.negoBuyPrc }" type="number" currencySymbol=""/></td>
									<td align="center"><input type="text" size="7" id="negoBuyPrc" name="negoBuyPrc" value="<c:out value="${list.negoBuyPrc }" />" maxlength="12" onkeyup="javascript:keyValid(this);"></td>
									<td align="center"><input type="text" size="7" id="producer" name="producer" maxlength="15" value="<c:out value="${list.producer }" />"></td>
								</tr>
							</c:forEach>
						</c:if>
						
						<c:if test="${empty orderList }">
						<input type="hidden" name="firstList" value="none"/>
							<tr><td colspan="10" align=center><spring:message code='epc.ord.emptySearchResult'/></td></tr>
						</c:if>