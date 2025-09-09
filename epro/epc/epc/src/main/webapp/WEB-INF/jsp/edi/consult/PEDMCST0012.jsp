<%@include file="../common.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="html" uri="http://lcnjf.lcn.co.kr/taglib/edi"  %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"   uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="/css/epc/edi/style_1024.css" />
<link rel="stylesheet" href="/css/epc/edi/sample.css"/>

<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>롯데마트 입점상담</title>

<script language="JavaScript">
$(document).ready(function($){
	bmanSet();
});

function bmanSet(){

	var form = document.forms[0];
	var tmp = form.bman.value.split(";");

	for(var i=0;i<tmp.length-1;i++){
		
		form.sele_bman.options.add(new Option(tmp[i], tmp[i]));
		
	}
	
	//form.sele_bman.value = "${paramMap.sele_bman}";
}

function doSearch(){
	var form = document.forms[0];
	//alert(form.sele_bman.value);
	loadingMaskFixPos();
	
	form.action  = "<c:url value='/edi/consult/PEDMCST0012Select.do'/>";

	form.submit();		
}

function doUpdatePage(val,val2){
	var form = document.forms[0];

	form.up_ven_cd.value=val;
	form.up_bman.value=val2;

	loadingMaskFixPos();
	PEDMCST0012Select.do
	form.action  = "<c:url value='/edi/consult/PEDMCST0012SelectDetail.do'/>";	
	//form.action  = "<c:url value='/edi/consult/forwardUpdatePage.do'/>";
	form.submit();
}



</script>

</head>

<body>
<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#" >
		
		<input type="hidden" name="bman" value="${bman }"/>
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
		
		<div id="wrap_menu">
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">신상정보 조회 & 변경</li>
						<li class="btn">
							<a href="#" class="btn" onclick="javascript:doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<!--  
					<tr>
						<th><span class="star">*</span> 사업자 번호1 </th>
						<td colspan="3">
							<input type="text" name="bmanNo" />
						</td>
					</tr>
					-->
					<tr>
						<th>사업자 번호2</th>
						<td>	
							<select name="sele_bman">
								<option value="all">전체</option>
							</select>
						</td>
						
						
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
					<div style="width:100%; height:484px; overflow-y:scroll; overflow-x:scroll;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="1" id="addTable">
						<colgroup>
								<tr class="r1">
									<col style="width:100px;" />
									<col style="width:100px;" />
									<col style="width:100px;" />
									<col style="width:100px;" />
									<col style="width:100px;" />
									<col style="width:100px;" />
									<col style="width:100px;" />
									<col style="width:100px;" />
								</tr>	
							</colgroup>
						<tr class="r1">
								<th>업체코드</th>
								<th>업체명</th>
								<th>담당자</th>
								<th>E-MAIL</th>
								<th>사무실</th>
								<th>휴대폰1</th>
								<th>휴대폰2</th>
								<th>팩스</th>
						</tr>
						<c:if test="${not empty alertList }">
							<input type="hidden" name="notData" value="exist" />
							<c:forEach items="${alertList}" var="list" varStatus="index" >
								<tr class="r1">
									
								<td align="center">
									<a href="javascript:doUpdatePage('${list.VEN_CD }','${list.BMAN_NO }');" onClick="" >
								${list.VEN_CD }</a>
								</td>

								 	<td align="center">${list.VEN_NM }</td>
									<td align="center">${list.DUTY_INF }</td>
									<td align="center">${list.EMAIL }</td>
									<td align="center">${list.HP_NO3 }</td>
									<td align="center">${list.HP_NO1 }</td>
									<td>&nbsp;${list.HP_NO2 }</td>
							  	<td>&nbsp;${list.HP_NO4 }</td>			
									
									

						
						
						
												
							<!--  		<td align="center">${list.ADDR }</td>
								-->
								</tr>
							</c:forEach>
						</c:if>
						
						<c:if test="${empty alertList }">
							<input type="hidden" name="notData" value="none" />
							<tr><td colspan="6" align=center>Data가 없습니다.</td></tr>
						</c:if>
					</table>
					</div>
					
					</div>

				</div>
			</div>
			
			</form>
		
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
					<li class="last">견적문서 조회</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
	</div>

</body>
</html>