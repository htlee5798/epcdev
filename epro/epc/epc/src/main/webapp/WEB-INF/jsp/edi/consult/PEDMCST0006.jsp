<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	
	function doSearch()
	{
		var form = document.forms[0];
		if(!form.venderSearch.value.trim())
		{
			alert("사업자번호 또는 업체명을 입력하세요!");	
			form.venderSearch.focus();
			return;
		}
		form.action="<c:url value="/edi/consult/PEDMCST0006select.do"/>";
		form.submit();
		
	}
	
	
	function doSetDatas(flage)
	{
		var form = document.forms[0];
		
		if(!form.bmanNo.value.trim())
		{
			alert("조회된  업체정보가 없습니다.");	
			form.venderSearch.focus();
			return;
		}
		
		if(flage =='1')
		{
			if(!confirm("해당업체의 패스워드를 초기화  하시겠습니까?"))  return;
			form.action="<c:url value="/edi/consult/PEDMCST0006UpdaePass.do"/>";
		}
		else
		{
			if(!confirm("해당업체의 입점상담 초기화를 하시겠습니까?"))  return;
			form.action="<c:url value="/edi/consult/PEDMCST0002UpdateCons.do"/>";
		}
		
		form.submit();
		form.action="";
	}
	
	
	   
 	function doKeyCheck()
	{

		if( event.keyCode == 13 ) doSearch();
		
	}
	
	function initPage(){
		
	<c:if test="${not empty resultMessage }">
		alert('${resultMessage}');
	</c:if>
	}
</script>


</head>

<body onLoad="initPage();">

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		
		<input type="hidden" name="staticTableBodyValue">
		<input type="hidden" name="name">
			
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
		<div id="wrap_menu">
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">검색조건</li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
							&nbsp;
							<a href="#" class="btn" onclick="doSetDatas('1');"><span>비밀번호 초기화</span></a>
							<a href="#" class="btn" onclick="doSetDatas('2');"><span>입점상담 초기화</span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:30%" />
						<col style="*" />
					</colgroup>
					
					<tr>
						<th>입점상담업체 조회</th>
						<td>
							<input type="Radio"  name="charType" value="1" checked >사업자번호   
							<input type="Radio"  name="charType" value="2"  <c:if test=""> checked</c:if> >업체명  &nbsp; &nbsp;
							<input type="text"   name="venderSearch" style="width:200px;" value=""  onKeyDown="doKeyCheck();">
							
						</td>
					</tr>
					
					</table>
				</div>
				<!-- 1검색조건 // -->
			</div>
			<!--	2 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">검색내역</li>
					</ul>
					<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0" >
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:17%" />
						<col style="*" />
					</colgroup>
					<tr height=30>
						<th> 사업자번호</th>
						<td>${vender.BMAN_NO}<input type="hidden" name="bmanNo" value="${vender.BMAN_NO}"></td>
						<th> 업체명</th>
						<td>${vender.HND_NM}</td>
					</tr>
					
					<tr height=30>
						<th> 비밀번호	</th>
						<td>${vender.PWD}</td>
						<th> 담당자명</th>
						<td>${vender.UTAKMN_NM}</td>
					</tr>
					<tr height=30>
						<th> 등록일자	</th>
						<td>${vender.REG_DATE}</td>
						<th> 수정일자 </th>
						<td>${vender.MOD_DATE}</td>
					</tr>
					</table>
					
					<ul class="tit">
						<li class="tit">상담이력</li>
					</ul>
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col style="width:100px;" />
							<col style="width:100px;" />
							<col style="width:200px;" />
							<col style="width:200px;" />
							<col   />
						</colgroup>
					<tr>
						<th>구분</th>
						<th>상담신청일자</th>
						<th>서류심사</th>
						<th>상담결과</th>
						<th>품평회/입점  </th>
					</tr>
					</table>
					<div style="width:100%; height:340px; overflow-x:hidden; overflow-y:scroll; table-layout:fixed;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<c:if test="${not empty conList}">
					<c:forEach items="${conList}" var="list" varStatus="index" >
						<colgroup>
							<col style="width:100px;" />
							<col style="width:100px;" />
							<col style="width:200px;" />
							<col style="width:200px;" />
							<col   />
						</colgroup>
						
						
						<tr class="r1">
							<td align=center>현재</td>
							<td align=center><p>${list.REG_DATE }</p></td>
							<c:choose>
							     <c:when test="${list.CHG_STATUS_CD eq '0'}">
							     <td colspan=3 align=center><font color="red">상담신청 작성 중</font></td>
							     	
								 </c:when>
							     <c:otherwise>
							     	<c:choose>
									     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'M'}">
									     	<td align=center>평가중</td>
									     </c:when>
									     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y'}">
									     	<td align=center>통과(${list.PAPE_JGM_PROC_DY })</td>
									     </c:when>
									     <c:otherwise>
									     	<c:choose>
									     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '1' }">
									     			<td align=center title="분류선택오류  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
									     		</c:when>
									     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '2' }">
									     			<td align=center title="정보미비  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
									     		</c:when>
									     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '3' }">
									     			<td align=center title="취급부적합상품  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
									     		</c:when>
									     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '4' }">
									     			<td align=center title="기존상품중복  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
									     		</c:when>
												<c:otherwise>
													<td align=center title="${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
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
											     <c:when test="${list.CNSL_RSLT_DIVN_CD eq 'M'}">
											     	<td align=center>평가중</td>
											     </c:when>
											     <c:when test="${list.CNSL_RSLT_DIVN_CD eq 'Y'}">
											     	<td align=center>통과(${list.CNSL_PROC_DY })</td>
											     </c:when>
											     <c:otherwise>
											     	<c:choose>
											     		<c:when test="${list.CNSL_RETN_DIVN_CD eq '1' }">
											     			<td align=center title="상품디자인미흡  ${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
											     		</c:when>
											     		<c:when test="${list.CNSL_RETN_DIVN_CD eq '2' }">
											     			<td align=center title="상품구색미흡  ${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
											     		</c:when>
														<c:otherwise>
															<td align=center title="${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
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
											     <c:when test="${list.ENTSHP_RSLT_DIVN_CD eq 'M'}">
											     	<td align=center>평가중</td>
											     </c:when>
											     <c:when test="${list.ENTSHP_RSLT_DIVN_CD eq 'Y'}">
											     	<td align=center>통과(${list.ENTSHP_PROC_DY })</td>
											     </c:when>
											     <c:otherwise>
											     	<td align=center>반려(${list.ENTSHP_PROC_DY })</td>
											     </c:otherwise>
										    </c:choose>
								    	</c:otherwise>
								    </c:choose>   
							     	
							     
							     </c:otherwise>
							</c:choose> 
							
							
						</tr>
					
					</c:forEach>
					
					
				</c:if>
				
				
				<c:if test="${not empty conList_past}">
					<c:forEach items="${conList_past}" var="list" varStatus="index" >
						<tr class="r1">
							
							<td align="center">과거</td>
							<td align="center"><p>${list.REG_DATE }</p></td>
							
							<c:choose>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y'}">
								     	<td align="center">통과(${list.PAPE_JGM_PROC_DY })</td>
								     </c:when>
								     <c:otherwise>
								      	<c:choose>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '1' }">
								     			<td align="center" title="분류선택오류  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
								     		</c:when>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '2' }">
								     			<td align="center" title="정보미비  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
								     		</c:when>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '3' }">
								     			<td align="center" title="취급부적합상품  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
								     		</c:when>
								     		<c:when test="${list.PAPE_JGM_RETN_DIVN_CD eq '4' }">
								     			<td align="center" title="기존상품중복  ${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
								     		</c:when>
											<c:otherwise>
												<td align="center" title="${list.PAPE_JGM_PROC_CONTENT }">반려(${list.PAPE_JGM_PROC_DY })</td>
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
										     	<td align="center">통과(${list.CNSL_PROC_DY })</td>
										     </c:when>
										     <c:otherwise>
										     	<c:choose>
										     		<c:when test="${list.CNSL_RETN_DIVN_CD eq '1' }">
											     			<td align="center" title="상품디자인미흡  ${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
											     		</c:when>
											     		<c:when test="${list.CNSL_RETN_DIVN_CD eq '2' }">
											     			<td align="center" title="상품구색미흡  ${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
											     		</c:when>
														<c:otherwise>
															<td align="center" title="${list.CNSL_PROC_CONTENT }">반려(${list.CNSL_PROC_DY })</td>
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
										     	<td align="center">통과(${list.ENTSHP_PROC_DY })</td>
										     </c:when>
										     <c:otherwise>
										     	<td align="center">반려(${list.ENTSHP_PROC_DY })</td>
										     </c:otherwise>
									    </c:choose>
							    	</c:otherwise>
							    </c:choose>    
							    
							
						</tr>
					</c:forEach>
				</c:if>
				</table>
				</div>
				</div>
			</div>
		</div>
	
	</div>


	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>협업</li>
					<li>협업정보</li>
					<li class="last">입점상담 Adm</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>
<input type="text" name="__blank" style="height:1px; width:1px;">
		</form>
</body>
	
</html>
