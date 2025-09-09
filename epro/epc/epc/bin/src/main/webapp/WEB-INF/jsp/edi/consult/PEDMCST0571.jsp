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

			

			<br>
					<div class="s_title mt20">
						<h2>상담신청 결과 확인</h2><br>
					</div>
					<div class="tb_v_common">
						<table cellpadding="0" cellspacing="0" border="0" width="100%">
							<caption>기본정보 화면</caption>
							<colgroup>
								<col width="130px">
								<col width="200px">
								<col width="150px">
								<col width="150px">
							</colgroup>
							<tbody>	
							
								<c:if test="${not empty conList}">
									<c:forEach items="${conList}" var="list" varStatus="index" >
									<tr>
									<th><p>사업자번호</p></th>								
									<td class="t_center">${list.BMAN_NO}</p></td>	
									<td colspan="2"></td>	
									</tr>
									<tr>
									<th><p>상담신청일</p></th>								
									<td class="t_center">${list.REG_DATE}</p></td>	
										<td colspan="2"></td>	
									</tr>
									<tr>
									<th width="130px"><p>검토결과</p></th>
									
								<c:choose>  		    
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y' and list.CNSL_RSLT_DIVN_CD eq 'Y' and list.ENTSHP_RSLT_DIVN_CD eq 'Y' }">
								     	<td class="t_center">통과</td>	<td colspan="2"></td>	
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y' and list.CNSL_RSLT_DIVN_CD eq 'Y' and list.ENTSHP_RSLT_DIVN_CD eq 'N' }">
								     	<td class="t_center">거절(푸ㅁ평회 반려)</td>	<td colspan="2"></td>	
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y' and list.CNSL_RSLT_DIVN_CD eq 'Y' and list.ENTSHP_RSLT_DIVN_CD eq 'M' }">
								     	<td class="t_center">심사중(상담 통과)</td>	<td colspan="2"></td>	
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y' and list.CNSL_RSLT_DIVN_CD eq 'N' and list.ENTSHP_RSLT_DIVN_CD eq 'M' }">
								     	<td class="t_center">거절(상담결과 반려)</td>	<td colspan="2"></td>	
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y' and list.CNSL_RSLT_DIVN_CD eq 'M' and list.ENTSHP_RSLT_DIVN_CD eq 'M' }">
								     	<td class="t_center">심사중(상담신청 통과)</td>	<td colspan="2"></td>	
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'N' and list.CNSL_RSLT_DIVN_CD eq 'M' and list.ENTSHP_RSLT_DIVN_CD eq 'M' }">
								     	<td class="t_center">거절(상담신청 반려)</td>	<td colspan="2"></td>	
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'M' and list.CNSL_RSLT_DIVN_CD eq 'M' and list.ENTSHP_RSLT_DIVN_CD eq 'M' }">
								     	<td class="t_center">심사중</td>	<td colspan="2"></td>	
								     </c:when>									     
							    </c:choose>			
								</tr>									
					</c:forEach>
				</c:if>
								
								<tr>
								<c:if test="${not empty conList}">
									<c:forEach items="${conList}" var="list" varStatus="index" >
									<th><p>평가자 의견</p></th>								
								
									
								<c:choose>  		    
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y' and list.CNSL_RSLT_DIVN_CD eq 'Y' and list.ENTSHP_RSLT_DIVN_CD eq 'Y' }">
								     	<td class="t_center" colspan="1">서류,상담,품평회 모두 통과(통과)</td>	<td colspan="2"></td>
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y' and list.CNSL_RSLT_DIVN_CD eq 'Y' and list.ENTSHP_RSLT_DIVN_CD eq 'N' }">
								     	<td  class="t_center" colspan="1">연락처:${list.ENTSHP_UTAKMN_TEL_NO}</td><td colspan="2"></td>
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y' and list.CNSL_RSLT_DIVN_CD eq 'Y' and list.ENTSHP_RSLT_DIVN_CD eq 'M' }">
								     	<td class="t_center" colspan="1">연락처:${list.CNSL_UTAKMN_TEL_NO}</td>	<td colspan="2"></td>	
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y' and list.CNSL_RSLT_DIVN_CD eq 'N' and list.ENTSHP_RSLT_DIVN_CD eq 'M' }">
								     	<td class="t_center" colspan="1">${list.CNSL_PROC_CONTENT}</td>		<td colspan="2"></td>
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y' and list.CNSL_RSLT_DIVN_CD eq 'M' and list.ENTSHP_RSLT_DIVN_CD eq 'M' }">
								     	<td class="t_center" colspan="1">연락처:${list.PAPE_JGM_UTAKMN_TEL_NO}</td><td colspan="2"></td>
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'N' and list.CNSL_RSLT_DIVN_CD eq 'M' and list.ENTSHP_RSLT_DIVN_CD eq 'M' }">
								     	<td class="t_left" colspan="3">${list.PAPE_JGM_PROC_CONTENT}</td>	
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'M' and list.CNSL_RSLT_DIVN_CD eq 'M' and list.ENTSHP_RSLT_DIVN_CD eq 'M' }">
								     	<td class="t_left" colspan="2">심사중입니다. 빠른시일내에 답변을 드리겠습니다.</td>	<td colspan="1"></td>
								     </c:when>									     
							    </c:choose>			
								</tr>									
					</c:forEach>
				</c:if>
							
								</tr>
								
							</tbody>
						</table>
						<br>
						<br><font color="black">* 상담신청 승인 시 ,롯데마트 담당자가 개재하신 연락처로 연락드리며 상담일자 및 장소에 대해 약속을 정하게 됩니다.</font>
						<br><br><font color="black">* 기타 문의사항은 Help Desk (02-2145-8000)로 문의 하시기 바랍니다.</font>
						
						
						
					</div>
			</div>


		</div>

		

	</div>

</div>

<script type="text/javascript" language="javascript" src="/js/front/front_input.js"></script>

</body>
</html>