<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>롯데마트 입점상담</title>
</head>

<body>

<div id="wrap">

	<div id="con_wrap">
	

		<div class="con_area">

			

			<div class="tb_h_common_02">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<caption>상담 결과 확인</caption>
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
						<th width="50px" class="line">구분</th>
						<th width="96px" class="line">상담신청일자</th>
						<th width="120px" class="line">서류심사</th>
						<th width="120px" class="line">상담결과</th>
						<th width="120px" class="line">품평회/입점</th>
						<th width="120px" class="line">최종평가자</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty conList}">
					<c:forEach items="${conList}" var="list" varStatus="index" >
						<tr class="line_dot">
							
							<td class="t_center">현재</td>
							<td class="t_center"><p>${list.REG_DATE }</p></td>
							
							<c:choose>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD == 'M' and list.CHG_STATUS_CD != '0'}">
								     	<td class="t_center">평가중 </td>
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD == 'M' and list.CHG_STATUS_CD == '0'}">
								     	<td class="t_center">신청요망 </td>
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y'}">
								     	<td class="t_center">통과(${list.PAPE_JGM_PROC_DY })</td>
								     </c:when>
								     <c:otherwise>
						     			<c:choose>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '1' }">
								     			<td class="t_center" title="분류선택오류 : ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })
								     																					<br>분류선택오류 : ${list.PAPE_JGM_PROC_CONTENT }</td>
								     		</c:when>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '2' }">
								     			<td class="t_center" title="정보미비 : ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })
																														<br>정보미비 : ${list.PAPE_JGM_PROC_CONTENT }</td>
								     		</c:when>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '3' }">
								     			<td class="t_center" title="취급부적합상품:  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
								     																					<br>취급부적합상품: : ${list.PAPE_JGM_PROC_CONTENT }</td>
								     		</c:when>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '4' }">
								     			<td class="t_center" title="기존상품중복 : ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })
																														<br>기존상품중복 : ${list.PAPE_JGM_PROC_CONTENT }</td>
								     		</c:when>
											<c:otherwise>
												<td class="t_center" title="${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY }<br>${list.PAPE_JGM_PROC_CONTENT })</td>
											</c:otherwise>                        
								     	</c:choose>
								     </c:otherwise>
							    </c:choose>
							    
							    <c:choose>
							    	<c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'N'}">
							    		<td align="center">상담반려</td>
							    	</c:when>
							    	<c:otherwise>
							    		<c:choose>
										     <c:when test="${list.CNSL_RSLT_DIVN_CD eq 'M'and list.CHG_STATUS_CD != '0'}">
										     	<td class="t_center">평가중</td>
										     </c:when>
										     <c:when test="${list.CNSL_RSLT_DIVN_CD eq 'M'and list.CHG_STATUS_CD == '0'}">
										     	<td class="t_center">심사중</td>
										     </c:when>
										     <c:when test="${list.CNSL_RSLT_DIVN_CD eq 'Y'}">
										     	<td class="t_center">통과(${list.CNSL_PROC_DY })</td>
										     </c:when>
										     <c:otherwise>
										     	<c:choose>
										     		<c:when test="${list.CNSL_RETN_DIVN_CD eq '1' }">
											     			<td class="t_center" title="상품디자인미흡  ${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
											     		</c:when>
											     		<c:when test="${list.CNSL_RETN_DIVN_CD eq '2' }">
											     			<td class="t_center" title="상품구색미흡  ${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
											     		</c:when>
														<c:otherwise>
															<td class="t_center" title="${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })<br>${list.CNSL_PROC_CONTENT}</td>
													</c:otherwise>
										     	</c:choose>
										     </c:otherwise>
									    </c:choose>
							    	</c:otherwise>
							    </c:choose>
							    
							    <c:choose>
							    	<c:when test="${list.CNSL_RSLT_DIVN_CD eq 'N' or list.PAPE_JGM_RSLT_DIVN_CD eq 'N' }">
							    		<td align="center">품평회반려</td>
							    	</c:when>
							    	<c:otherwise>
							    		<c:choose>
										     <c:when test="${list.ENTSHP_RSLT_DIVN_CD eq 'M' and list.CHG_STATUS_CD != '0'}">
										     	<td class="t_center">평가중</td>
										     </c:when>
										       <c:when test="${list.ENTSHP_RSLT_DIVN_CD eq 'M' and list.CHG_STATUS_CD == '0'}">
										     	<td class="t_center">심사중</td>
										     </c:when>
										     <c:when test="${list.ENTSHP_RSLT_DIVN_CD eq 'Y'}">
										     	<td class="t_center">통과(${list.ENTSHP_PROC_DY })</td>
										     </c:when>
										     <c:otherwise>
										     	<td class="t_center">반려(${list.ENTSHP_PROC_DY })</td>
										     </c:otherwise>
									    </c:choose>
							    	</c:otherwise>
							    </c:choose>
							<td class="t_center">${list.LAST_UTAKMN }</td>	        
						</tr>
					</c:forEach>
				</c:if>
				
				<tr class="line_dot">
				<td colspan="6" class="t_center">아래 리스트는 과거 이력입니다.</td>
				</tr>
				
				<c:if test="${not empty conList_past}">
					<c:forEach items="${conList_past}" var="list" varStatus="index" >
						<tr class="line_dot">
							
							<td class="t_center">과거</td>
							<td class="t_center"><p>${list.REG_DATE }</p></td>
							
							<c:choose>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y'}">
								     	<td class="t_center">통과(${list.PAPE_JGM_PROC_DY })</td>
								     </c:when>
								     <c:otherwise>
						     			<c:choose>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '1' }">
								     			<td class="t_center" title="분류선택오류  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
								     		</c:when>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '2' }">
								     			<td class="t_center" title="정보미비  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
								     		</c:when>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '3' }">
								     			<td class="t_center" title="취급부적합상품  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
								     		</c:when>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '4' }">
								     			<td class="t_center" title="기존상품중복  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
								     		</c:when>
											<c:otherwise>
												<td class="t_center" title="${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
											</c:otherwise>
								     	</c:choose>
								     </c:otherwise>
							    </c:choose>
							    
							    <c:choose>
							    	<c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'N'}">
							    		<td align="center">상담반려</td>
							    	</c:when>
							    	<c:otherwise>
							    		<c:choose>
										     <c:when test="${list.CNSL_RSLT_DIVN_CD eq 'Y'}">
										     	<td class="t_center">통과(${list.CNSL_PROC_DY })</td>
										     </c:when>
										     <c:otherwise>
										     	<c:choose>
										     		<c:when test="${list.CNSL_RETN_DIVN_CD eq '1' }">
											     			<td class="t_center" title="상품디자인미흡  ${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
											     		</c:when>
											     		<c:when test="${list.CNSL_RETN_DIVN_CD eq '2' }">
											     			<td class="t_center" title="상품구색미흡  ${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
											     		</c:when>
														<c:otherwise>
															<td class="t_center" title="${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
													</c:otherwise>
										     	</c:choose>
										     </c:otherwise>
									    </c:choose>
							    	</c:otherwise>
							    </c:choose>
							    
							    <c:choose>
							    	<c:when test="${list.CNSL_RSLT_DIVN_CD eq 'N' or list.PAPE_JGM_RSLT_DIVN_CD eq 'N' }">
							    		<td align="center">품평회반려</td>
							    	</c:when>
							    	<c:otherwise>
							    		<c:choose>
										     <c:when test="${list.ENTSHP_RSLT_DIVN_CD eq 'Y'}">
										     	<td class="t_center">통과(${list.ENTSHP_PROC_DY })</td>
										     </c:when>
										     <c:otherwise>
										     	<td class="t_center">반려(${list.ENTSHP_PROC_DY })</td>
										     </c:otherwise>
									    </c:choose>
							    	</c:otherwise>
							    </c:choose>
							<td class="t_center">${list.LAST_UTAKMN }</td>    
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