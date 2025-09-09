<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code='text.consult.field.lotteVendorConsult'/></title>
</head>

<body>

<div id="wrap">
	<div id="con_wrap">
		<div class="con_area">
			<div class="tb_h_common_02">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption><spring:message code='text.consult.field.consultResult'/></caption>
				<colgroup>
					<col width="68px">
					<col width="96px">
					<col width="120px">
					<col width="120px">
					<col width="120px">
					<col width="120px">
				</colgroup>
				<thead>
					<tr>
						<th width="50px" class="line"><spring:message code='text.consult.header.gubun'/></th>
						<th width="96px" class="line"><spring:message code='text.consult.header.consultRegDate'/></th>
						<th width="120px" class="line"><spring:message code='text.consult.header.papeJgmRsltDivnCd'/></th>
						<th width="120px" class="line"><spring:message code='text.consult.header.cnslRsltDivnCd'/></th>
						<th width="120px" class="line"><spring:message code='text.consult.header.entshpRsltDivnCd'/></th>
						<th width="120px" class="line"><spring:message code='text.consult.header.lastUtakmn'/></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty conList}">
					<c:forEach items="${conList}" var="list" varStatus="index" >
						<tr class="line_dot">
							
							<td class="t_center"><spring:message code='text.consult.body.now'/></td>
							<td class="t_center"><p><c:out value='${list.regDate }'/></p></td>
							
							<c:choose>
								     <c:when test="${list.papeJgmRsltDivnCd == 'M' and list.chgStatusCd != '0'}">
								     	<td class="t_center"><spring:message code='text.consult.body.papeJgmRsltDivnCdMNE0'/></td>
								     </c:when>
								     <c:when test="${list.papeJgmRsltDivnCd == 'M' and list.chgStatusCd == '0'}">
								     	<td class="t_center"><spring:message code='text.consult.body.papeJgmRsltDivnCdMEQ0'/> </td>
								     </c:when>
								     <c:when test="${list.papeJgmRsltDivnCd eq 'Y'}">
								     	<td class="t_center"><spring:message code='text.consult.body.papeJgmRsltDivnCdY'/>(<c:out value='${list.papeJgmProcDy }'/>)</td>
								     </c:when>
								     <c:otherwise>
						     			<c:choose>
								     		<c:when test="${list.papeJgmRetnDivnCd eq '1' }">
								     			<td class="t_center" title="<spring:message code='text.consult.body.papeJgmRetnDivnCd1'/> : <c:out value='${list.papeJgmProcContent }'/>"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.papeJgmProcDy }'/>)
								     																					<br><spring:message code='text.consult.body.papeJgmRetnDivnCd1'/> : <c:out value='${list.papeJgmProcContent }'/></td>
								     		</c:when>
								     		<c:when test="${list.papeJgmRetnDivnCd eq '2' }">
								     			<td class="t_center" title="<spring:message code='text.consult.body.papeJgmRetnDivnCd2'/> : <c:out value='${list.papeJgmProcContent }'/>"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.papeJgmProcDy }'/>)
																														<br><spring:message code='text.consult.body.papeJgmRetnDivnCd2'/> : <c:out value='${list.papeJgmProcContent }'/></td>
								     		</c:when>
								     		<c:when test="${list.papeJgmRetnDivnCd eq '3' }">
								     			<td class="t_center" title="<spring:message code='text.consult.body.papeJgmRetnDivnCd3'/>:  <c:out value='${list.papeJgmProcContent }'/>"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.papeJgmProcDy }'/>)
								     																					<br><spring:message code='text.consult.body.papeJgmRetnDivnCd3'/>: : <c:out value='${list.papeJgmProcContent }'/></td>
								     		</c:when>
								     		<c:when test="${list.papeJgmRetnDivnCd eq '4' }">
								     			<td class="t_center" title="<spring:message code='text.consult.body.papeJgmRetnDivnCd4'/> : <c:out value='${list.papeJgmProcContent }'/>"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.papeJgmProcDy }'/>)
																														<br><spring:message code='text.consult.body.papeJgmRetnDivnCd4'/> : <c:out value='${list.papeJgmProcContent }'/></td>
								     		</c:when>
											<c:otherwise>
												<td class="t_center" title="${list.papeJgmProcContent }"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.papeJgmProcDy }'/><br><c:out value='${list.papeJgmProcContent }'/>)</td>
											</c:otherwise>                        
								     	</c:choose>
								     </c:otherwise>
							    </c:choose>
							    <c:choose>
							    	<c:when test="${list.papeJgmRsltDivnCd eq 'N'}">
							    		<td align="center"><spring:message code='text.consult.body.papeJgmRsltDivnCdN'/></td>
							    	</c:when>
							    	<c:otherwise>
							    		<c:choose>
										     <c:when test="${list.cnslRsltDivnCd eq 'M'and list.chgStatusCd != '0'}">
										     	<td class="t_center"><spring:message code='text.consult.body.cnslRsltDivnCdMNE0'/></td>
										     </c:when>
										     <c:when test="${list.cnslRsltDivnCd eq 'M'and list.chgStatusCd == '0'}">
										     	<td class="t_center"><spring:message code='text.consult.body.cnslRsltDivnCdMEQ0'/></td>
										     </c:when>
										     <c:when test="${list.cnslRsltDivnCd eq 'Y'}">
										     	<td class="t_center"><spring:message code='text.consult.body.cnslRsltDivnCdY'/>(<c:out value='${list.cnslProcDy }'/>)</td>
										     </c:when>
										     <c:otherwise>
										     	<c:choose>
										     		<c:when test="${list.cnslRetnDivnCd eq '1' }">
											     			<td class="t_center" title="<spring:message code='text.consult.body.cnslRetnDivnCd1'/>  <c:out value='${list.cnslProcContent }'/>"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.cnslProcDy }'/>)</td>
											     		</c:when>
											     		<c:when test="${list.cnslRetnDivnCd eq '2' }">
											     			<td class="t_center" title="<spring:message code='text.consult.body.cnslRetnDivnCd2'/>  <c:out value='${list.cnslProcContent }'/>"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.cnslProcDy }'/>)</td>
											     		</c:when>
														<c:otherwise>
															<td class="t_center" title="${list.cnslProcContent }"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.cnslProcDy }'/>)<br><c:out value='${list.cnslProcContent}'/></td>
													</c:otherwise>
										     	</c:choose>
										     </c:otherwise>
									    </c:choose>
							    	</c:otherwise>
							    </c:choose>
							    <c:choose>
							    	<c:when test="${list.cnslRsltDivnCd eq 'N' or list.papeJgmRsltDivnCd eq 'N' }">
							    		<td align="center"><spring:message code='text.consult.body.cnslRsltDivnCdN'/></td>
							    	</c:when>
							    	<c:otherwise>
							    		<c:choose>
										     <c:when test="${list.entshpRsltDivnCd eq 'M' and list.chgStatusCd != '0'}">
										     	<td class="t_center"><spring:message code='text.consult.body.entshpRsltDivnCdMNE0'/></td>
										     </c:when>
										       <c:when test="${list.entshpRsltDivnCd eq 'M' and list.chgStatusCd == '0'}">
										     	<td class="t_center"><spring:message code='text.consult.body.entshpRsltDivnCdMEQ0'/></td>
										     </c:when>
										     <c:when test="${list.entshpRsltDivnCd eq 'Y'}">
										     	<td class="t_center"><spring:message code='text.consult.body.entshpRsltDivnCdY'/>(<c:out value='${list.entshpProcDy }'/>)</td>
										     </c:when>
										     <c:otherwise>
										     	<td class="t_center"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.entshpProcDy }'/>)</td>
										     </c:otherwise>
									    </c:choose>
							    	</c:otherwise>
							    </c:choose>
							<td class="t_center"><c:out value='${list.lastUtakmn }'/></td>	        
						</tr>
					</c:forEach>
				</c:if>
				<tr class="line_dot">
				<td colspan="6" class="t_center"><spring:message code='text.consult.field.consultResultPast'/></td>
				</tr>
				<c:if test="${not empty conList_past}">
					<c:forEach items="${conList_past}" var="list" varStatus="index" >
						<tr class="line_dot">
							<td class="t_center">과거</td>
							<td class="t_center"><p><c:out value='${list.regDate }'/></p></td>
							<c:choose>
								     <c:when test="${list.papeJgmRsltDivnCd eq 'Y'}">
								     	<td class="t_center"><spring:message code='text.consult.body.papeJgmRsltDivnCdY'/>(<c:out value='${list.papeJgmProcDy }'/>)</td>
								     </c:when>
								     <c:otherwise>
						     			<c:choose>
								     		<c:when test="${list.papeJgmRetnDivnCd eq '1' }">
								     			<td class="t_center" title="<spring:message code='text.consult.body.papeJgmRetnDivnCd1'/>  <c:out value='${list.papeJgmProcContent }'/>"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.papeJgmProcDy }'/>)</td>
								     		</c:when>
								     		<c:when test="${list.papeJgmRetnDivnCd eq '2' }">
								     			<td class="t_center" title="<spring:message code='text.consult.body.papeJgmRetnDivnCd2'/>  <c:out value='${list.papeJgmProcContent }'/>"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.papeJgmProcDy }'/>)</td>
								     		</c:when>
								     		<c:when test="${list.papeJgmRetnDivnCd eq '3' }">
								     			<td class="t_center" title="<spring:message code='text.consult.body.papeJgmRetnDivnCd3'/>  <c:out value='${list.papeJgmProcContent }'/>"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.papeJgmProcDy }'/>)</td>
								     		</c:when>
								     		<c:when test="${list.papeJgmRetnDivnCd eq '4' }">
								     			<td class="t_center" title="<spring:message code='text.consult.body.papeJgmRetnDivnCd4'/>  <c:out value='${list.papeJgmProcContent }'/>"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.papeJgmProcDy }'/>)</td>
								     		</c:when>
											<c:otherwise>
												<td class="t_center" title="<c:out value='${list.papeJgmProcContent }'/>"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.papeJgmProcDy }'/>)</td>
											</c:otherwise>
								     	</c:choose>
								     </c:otherwise>
							    </c:choose>
							    <c:choose>
							    	<c:when test="${list.papeJgmRsltDivnCd eq 'N'}">
							    		<td align="center"><spring:message code='text.consult.body.papeJgmRsltDivnCdN'/></td>
							    	</c:when>
							    	<c:otherwise>
							    		<c:choose>
										     <c:when test="${list.cnslRsltDivnCd eq 'Y'}">
										     	<td class="t_center"><spring:message code='text.consult.body.cnslRsltDivnCdY'/>(<c:out value='${list.cnslProcDy }'/>)</td>
										     </c:when>
										     <c:otherwise>
										     	<c:choose>
										     		<c:when test="${list.cnslRetnDivnCd eq '1' }">
											     			<td class="t_center" title="<spring:message code='text.consult.body.cnslRetnDivnCd1'/>  <c:out value='${list.cnslProcContent }'/>"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.cnslProcDy }'/>)</td>
											     		</c:when>
											     		<c:when test="${list.cnslRetnDivnCd eq '2' }">
											     			<td class="t_center" title="<spring:message code='text.consult.body.cnslRetnDivnCd2'/>  <c:out value='${list.cnslProcContent }'/>"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.cnslProcDy }'/>)</td>
											     		</c:when>
														<c:otherwise>
															<td class="t_center" title="<c:out value='${list.cnslProcContent }'/>"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.cnslProcDy }'/>)</td>
													</c:otherwise>
										     	</c:choose>
										     </c:otherwise>
									    </c:choose>
							    	</c:otherwise>
							    </c:choose>
							    <c:choose>
							    	<c:when test="${list.cnslRsltDivnCd eq 'N' or list.papeJgmRsltDivnCd eq 'N' }">
							    		<td align="center"><spring:message code='text.consult.body.cnslRsltDivnCdN'/></td>
							    	</c:when>
							    	<c:otherwise>
							    		<c:choose>
										     <c:when test="${list.entshpRsltDivnCd eq 'Y'}">
										     	<td class="t_center"><spring:message code='text.consult.body.entshpRsltDivnCdY'/>(<c:out value='${list.entshpProcDy }'/>)</td>
										     </c:when>
										     <c:otherwise>
										     	<td class="t_center"><spring:message code='text.consult.body.rtn'/>(<c:out value='${list.entshpProcDy }'/>)</td>
										     </c:otherwise>
									    </c:choose>
							    	</c:otherwise>
							    </c:choose>
							<td class="t_center"><c:out value='${list.lastUtakmn }'/></td>    
						</tr>
					</c:forEach>
				</c:if>
				</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" language="javascript" src="/js/front/front_input.js"></script>

</body>
</html>