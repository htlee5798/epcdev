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
function PopupWindow(pageName) {
	var cw=1000;
	var ch=700;
	var sw=screen.availWidth;
	var sh=screen.availHeight;
	var px=Math.round((sw-cw)/2);
	var py=Math.round((sh-ch)/2);
	window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
}

$(function() {


	

	$("#teamGroupCode").change(function() {
		$.getJSON("<c:url value='/edi/consult/selectL1List.do'/>",
					{
						groupCode: $(this).val() 
					}, function(j){
		      var options = '';

		      if(j.length=='0'){
		    	  options = '<option value=all>전체</option>';
		      }else{
		    	  for (var i = 0; i < j.length; i++) {
					    if(i == 0) {
					    	options += '<option value=all>전체</option>';
						}  
				        options += '<option value="' + j[i].teamCode + '">' + j[i].teamName + '</option>';
			      }
		      }
		      

		      $("#l1GroupCode option").remove();
		      $("#l1GroupCode").html(options);
		    });
	});
	
});

function doSearch() {

	var form = document.forms[0];

	form.action  = "<c:url value='/edi/consult/PEDMCST0002select.do'/>";
	form.submit();		
}



function papeWin1(state, val) {
	
	if(state){
		 document.getElementById("papeViewLayer1").style.display = "none";
		 document.getElementById("papeViewLayer2").style.display = "none";
		 document.getElementById("cnslViewLayer1").style.display = "none";
		 document.getElementById("cnslViewLayer2").style.display = "none";
		 document.getElementById("entshpViewLayer1").style.display = "none";
		 document.getElementById("entshpViewLayer2").style.display = "none";

		 document.getElementById("papeViewLayer1").style.display = "";
		 document.getElementById("bmanNO_pape1").value=val;
	}else{
		 document.getElementById("papeViewLayer1").style.display = "none";
		 document.getElementById("bmanNO_pape1").value=val;
	}
}

function papeWin2(state,val) {
	if(state){ 
		document.getElementById("papeViewLayer1").style.display = "none";
        document.getElementById("papeViewLayer2").style.display = "none";
	    document.getElementById("cnslViewLayer1").style.display = "none";
	    document.getElementById("cnslViewLayer2").style.display = "none";
	    document.getElementById("entshpViewLayer1").style.display = "none";
	    document.getElementById("entshpViewLayer2").style.display = "none";
	    
		document.getElementById("papeViewLayer2").style.display = "";
		document.getElementById("bmanNO_pape2").value=val;
	}else{ 
		document.getElementById("papeViewLayer2").style.display = "none";
		document.getElementById("bmanNO_pape2").value=val;
	}
}
function updatePape1() {

	var form = document.forms[0];


	for( i=0 ; i<form.pape_tel.value.length; i++)
	{
		if(form.pape_tel.value.charAt(i) <"0" || form.pape_tel.value.charAt(i)>"9")
		{
			alert("<spring:message code='msg.common.error.noNum'/>");
			form.pape_tel.value="";
			form.pape_tel.focus();
			return;
		}
	}

	form.inputBmanNo.value=form.bmanNO_pape1.value;
	
	form.action  = "<c:url value='/edi/consult/PEDMCST0002updatePape1.do'/>";
	form.submit();
	
}

function updatePape2() {

	var form = document.forms[0];

	if(form.pape_detail.value.length > 200){
		alert("세부사유 입력은 200자 이내로 하세요.");
		return;
	}

	form.inputBmanNo.value=form.bmanNO_pape2.value;

	for(var i=0;i<form.papeNot.length;i++){
		if(form.papeNot[i].checked){
			form.papeNotVal.value=form.papeNot[i].value;
		}
	}

	form.action  = "<c:url value='/edi/consult/PEDMCST0002updatePape2.do'/>";
	form.submit();
	
}

function cnslWin1(state, val) {
	
	if(state){
		 document.getElementById("papeViewLayer1").style.display = "none";
         document.getElementById("papeViewLayer2").style.display = "none";
	     document.getElementById("cnslViewLayer1").style.display = "none";
	     document.getElementById("cnslViewLayer2").style.display = "none";
	     document.getElementById("entshpViewLayer1").style.display = "none";
	     document.getElementById("entshpViewLayer2").style.display = "none";
	    
		 document.getElementById("cnslViewLayer1").style.display = "";
		 document.getElementById("bmanNO_cnsl1").value=val;
	}else{
		 document.getElementById("cnslViewLayer1").style.display = "none";
		 document.getElementById("bmanNO_cnsl1").value=val;
	}
}

function cnslWin2(state,val) {
	if(state){ 
		document.getElementById("papeViewLayer1").style.display = "none";
        document.getElementById("papeViewLayer2").style.display = "none";
	    document.getElementById("cnslViewLayer1").style.display = "none";
	    document.getElementById("cnslViewLayer2").style.display = "none";
	    document.getElementById("entshpViewLayer1").style.display = "none";
	    document.getElementById("entshpViewLayer2").style.display = "none";
	    
		document.getElementById("cnslViewLayer2").style.display = "";
		document.getElementById("bmanNO_cnsl2").value=val;
	}else{ 
		document.getElementById("cnslViewLayer2").style.display = "none";
		document.getElementById("bmanNO_cnsl2").value=val;
	}
}

function updateCnsl1() {

	var form = document.forms[0];

	for( i=0 ; i<form.cnsl_tel.value.length; i++)
	{
		if(form.cnsl_tel.value.charAt(i) <"0" || form.cnsl_tel.value.charAt(i)>"9")
		{
			alert("<spring:message code='msg.common.error.noNum'/>");
			form.cnsl_tel.value="";
			form.cnsl_tel.focus();
			return;
		}
	}

	form.inputBmanNo.value=form.bmanNO_cnsl1.value;
	
	form.action  = "<c:url value='/edi/consult/PEDMCST0002updateCnsl1.do'/>";
	form.submit();		
	
}

function updateCnsl2() {

	var form = document.forms[0];

	if(form.cnsl_detail.value.length > 200){
		alert("세부사유 입력은 200자 이내로 하세요.");
		return;
	}

	form.inputBmanNo.value=form.bmanNO_cnsl2.value;

	for(var i=0;i<form.cnslNot.length;i++){
		if(form.cnslNot[i].checked){
			form.cnslNotVal.value=form.cnslNot[i].value;
		}
	}

	form.action  = "<c:url value='/edi/consult/PEDMCST0002updateCnsl2.do'/>";
	form.submit();
	
}

function entshpWin1(state, val) {
	
	if(state){
		 document.getElementById("papeViewLayer1").style.display = "none";
         document.getElementById("papeViewLayer2").style.display = "none";
	     document.getElementById("cnslViewLayer1").style.display = "none";
	     document.getElementById("cnslViewLayer2").style.display = "none";
	     document.getElementById("entshpViewLayer1").style.display = "none";
	     document.getElementById("entshpViewLayer2").style.display = "none";
	    
		 document.getElementById("entshpViewLayer1").style.display = "";
		 document.getElementById("bmanNO_entshp1").value=val;
	}else{
		 document.getElementById("entshpViewLayer1").style.display = "none";
		 document.getElementById("bmanNO_entshp1").value=val;
	}
}

function entshpWin2(state,val) {
	if(state){ 
		document.getElementById("papeViewLayer1").style.display = "none";
        document.getElementById("papeViewLayer2").style.display = "none";
	    document.getElementById("cnslViewLayer1").style.display = "none";
	    document.getElementById("cnslViewLayer2").style.display = "none";
	    document.getElementById("entshpViewLayer1").style.display = "none";
	    document.getElementById("entshpViewLayer2").style.display = "none";
	    
		document.getElementById("entshpViewLayer2").style.display = "";
		document.getElementById("bmanNO_entshp2").value=val;
	}else{ 
		document.getElementById("entshpViewLayer2").style.display = "none";
		document.getElementById("bmanNO_entshp2").value=val;
	}
}

function updateEntshp1() {

	var form = document.forms[0];

	for( i=0 ; i<form.entshp_tel.value.length; i++)
	{
		if(form.entshp_tel.value.charAt(i) <"0" || form.entshp_tel.value.charAt(i)>"9")
		{
			alert("<spring:message code='msg.common.error.noNum'/>");
			form.entshp_tel.value="";
			form.entshp_tel.focus();
			return;
		}
	}

	form.inputBmanNo.value=form.bmanNO_entshp1.value;

	form.action  = "<c:url value='/edi/consult/PEDMCST0002updateEntshp1.do'/>";
	form.submit();		
}

function updateEntshp2() {

	var form = document.forms[0];

	for( i=0 ; i<form.entshp_tel.value.length; i++)
	{
		if(form.entshp_tel2.value.charAt(i) <"0" || form.entshp_tel2.value.charAt(i)>"9")
		{
			alert("<spring:message code='msg.common.error.noNum'/>");
			form.entshp_tel2.value="";
			form.entshp_tel2.focus();
			return;
		}
	}

	form.inputBmanNo.value=form.bmanNO_entshp2.value;

	form.action  = "<c:url value='/edi/consult/PEDMCST0002updateEntshp2.do'/>";
	form.submit();
	
}

function paging(val) {
	var form = document.forms[0];

	form.currentPage.value=val;

	alert(form.currentPage.value);

	form.action  = "<c:url value='/edi/consult/PEDMCST0002select.do'/>";
	form.submit();
}

/* pagination 페이지 링크 function */
function goPage(pageNo){

	var form = document.forms[0];
	
	form.currentPage.value = pageNo;

	form.action  = "<c:url value='/edi/consult/PEDMCST0002select.do'/>";
	form.submit();		
	
}

function detatilViewPage(bmanNo){
	PopupWindow("<c:url value='/edi/consult/detailView.do?businessNo="+bmanNo+"'/>");
}

</script>


</head>


<body>
			
<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#">
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
		<input type="hidden" id="currentPage" name="currentPage" value="1"/>
		<input type="hidden" id="recordCountPerPage" name="recordCountPerPage"  value="10"/>
		<input type="hidden" id="pageSize" name="pageSize"  value="10"/>
		
		<input type="hidden" name="inputBmanNo" >
		
		<div id="wrap_menu">
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">상담진행중</li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:15%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> 상담신청일자 </th>
						<td>
							<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.startDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.endDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
						</td>
						<th><span class="star">*</span> 서류심사상태  </th>
						<td>
						 
							<select name="paperState">
								<option value="all" <c:if test="${paramMap.paperState eq 'all' }"> selected</c:if>>전체</option>
								<option value="M" <c:if test="${paramMap.paperState eq 'M' }"> selected</c:if>>심사대기</option>
								<option value="Y" <c:if test="${paramMap.paperState eq 'Y' }"> selected</c:if>>심사통과</option>
								<option value="N" <c:if test="${paramMap.paperState eq 'N' }"> selected</c:if>>심사반려</option>
							</select>
						</td>
					</tr>
					<tr>
						<th><span class="star">*</span> 팀</th>
						<td>
							<select id="teamGroupCode" name="teamCode" class="required">
									<option value="all">전체</option>
								<c:forEach items="${teamList}" var="team" varStatus="index" >
									<option value="${team.teamCode}" <c:if test="${paramMap.teamCode eq team.teamCode }"> selected</c:if>>${team.teamName}</option> 
								</c:forEach>
							</select>
						</td>
						<th><span class="star">*</span> 대분류  </th>
						<td >
							<select id="l1GroupCode" name="l1GroupCode" class="required">
									<c:if test="${com == 'none'}">
										<option value="all">전체</option>
									</c:if>
								<c:forEach items="${l1GroupList}" var="l1Group" varStatus="indexL1" >
									<c:if test="${indexL1.index == 0 }">
										<option value=all <c:if test="${paramMap.l1GroupCode eq 'all'}"> selected</c:if>>전체 </option>
									</c:if>
									<option value="${l1Group.teamCode}" <c:if test="${paramMap.l1GroupCode eq l1Group.teamCode }"> selected</c:if> >${l1Group.teamName} </option> 
								</c:forEach>
							</select>
						</td>
					</tr>
					
					</table>
					<table cellspacing=0 cellpadding=0 border=0>
						<tr>
						<td bgcolor=ffffff height=30>
						 <font color=red>&nbsp; &nbsp; &nbsp;* 팀별 심사권한을 임시로   제안을 두지 않았습니다.  승인처리시 관련 부서의 상담내용만 승인처리 하시기 바랍니다. </font> 
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
					<div style="width:100%; height:457px; overflow-y:scroll; overflow-x:scroll;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:30px;" />
						<col style="width:100px;" />
						<col style="width:100px;" />
						<col style="width:80px;" />
						<col />
						<col style="width:60px;" />
						<col style="width:60px;" />
						<col style="width:60px;" />
					</colgroup>
					<tr>
						<th rowspan="2">NO</th>
						<th rowspan="2">팀</th>
						<th rowspan="2">대분류</th>
						<th rowspan="2">상담신청일자</th>
						<th rowspan="2">신청업체</th>
						<th colspan="2">서류심사</th>
						<th colspan="2">상담결과</th>
						<th colspan="2">품평회/입점</th>
						
					</tr>
					<tr>
						<th>통과</th>
						<th>반려</th>
						<th>합격</th>
						<th>불합격</th>
						<th>합격</th>
						<th>불합격</th>
					</tr>
					
					<c:if test="${not empty conList }">
						
					    <c:set var="idx"  value="1" />
						<c:forEach items="${conList}" var="list" varStatus="index" >
							<tr class="r1">
								<c:choose>
									<c:when test="${param.currentPage eq 1 }">
										<td align="center">${idx }</td>
									</c:when>
									<c:otherwise>
										<td align="center">${idx + ((param.currentPage - 1) * param.recordCountPerPage)}</td>
									</c:otherwise>
								</c:choose>
								<td align="center">${list.TEAM_CLASS }</td>
								<td align="center">${list.BIG_CLASS }</td>
								<td align="center">${list.REG_DATE }</td>
								<td align="center"><a href="javascript:detatilViewPage('${list.BMAN_NO }')">${list.HND_NM }</a></td>
								<c:choose>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'M'}">
								     	<td align="center"><input type="radio" id="pape_radio" name="pape_radio" onclick="javascript:papeWin1(true,'${list.BMAN_NO }');" /></td>
										<td align="center"><input type="radio" id="pape_radio" name="pape_radio" onclick="javascript:papeWin2(true,'${list.BMAN_NO }');"/></td>
								     </c:when>
								     <c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y'}">
								     	<td align="center">${list.PAPE_JGM_PROC_DY }</td>
										<td align="center"></td>
								     </c:when>
								     <c:otherwise>
						     			<td align="center"></td>
						     			<td align="center">${list.PAPE_JGM_PROC_DY }</td>
								     </c:otherwise>
							    </c:choose>
							    
							    <c:choose>
							    	<c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'M'}">
							    		<td colspan="2" align="center">상담미확정</td>
							    	</c:when>
							    	<c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'N'}">
							    		<td colspan="2" align="center">상담반려</td>
							    	</c:when>
							    	<c:otherwise>
							    		<c:choose>
										     <c:when test="${list.CNSL_RSLT_DIVN_CD eq 'M'}">
										     	<td align="center"><input type="radio" name="cnsl_radio" onclick="javascript:cnslWin1(true,'${list.BMAN_NO }');" /></td>
												<td align="center"><input type="radio" name="cnsl_radio" onclick="javascript:cnslWin2(true,'${list.BMAN_NO }');"/></td>	
										     </c:when>
										     <c:when test="${list.CNSL_RSLT_DIVN_CD eq 'Y'}">
										     	<td align="center">${list.CNSL_PROC_DY }</td>
												<td align="center"></td>
										     </c:when>
										     <c:otherwise>
								     			<td align="center"></td>
								     			<td align="center">${list.CNSL_PROC_DY }</td>
										     </c:otherwise>
									    </c:choose>
							    	</c:otherwise>
							    </c:choose>
							    
							    <c:choose>
							    	<c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'M' and list.CNSL_RSLT_DIVN_CD eq 'M'  }">
							    		<td colspan="2" align="center">품평회미확정</td>
							    	</c:when>
							    	<c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y' and list.CNSL_RSLT_DIVN_CD eq 'M'  }">
							    		<td colspan="2" align="center">품평회미확정</td>
							    	</c:when>
							    	<c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'N'}">
							    		<td colspan="2" align="center">품평회반려</td>
							    	</c:when>
							    	<c:when test="${list.PAPE_JGM_RSLT_DIVN_CD eq 'Y' and list.CNSL_RSLT_DIVN_CD eq 'N'  }">
							    		<td colspan="2" align="center">품평회반려</td>
							    	</c:when>
							    	<c:otherwise>
							    		<c:choose>
										     <c:when test="${list.ENTSHP_RSLT_DIVN_CD eq 'M'}">
										     	<td align="center"><input type="radio" name="entshp_radio" onclick="javascript:entshpWin1(true,'${list.BMAN_NO }');" /></td>
												<td align="center"><input type="radio" name="entshp_radio" onclick="javascript:entshpWin2(true,'${list.BMAN_NO }');"/></td>	
										     </c:when>
										     <c:when test="${list.ENTSHP_RSLT_DIVN_CD eq 'Y'}">
										     	<td align="center">${list.ENTSHP_PROC_DY }</td>
												<td align="center"></td>
										     </c:when>
										     <c:otherwise>
								     			<td align="center"></td>
								     			<td align="center">${list.ENTSHP_PROC_DY }</td>
										     </c:otherwise>
									    </c:choose>
							    	</c:otherwise>
							    </c:choose>
							</tr>
						<c:set var="idx"  value="${idx + 1 }" />
						</c:forEach>
					</c:if>
					<c:if test="${empty conList }">
						<tr><td colspan="11" align=center>Data가 없습니다.</td></tr>
					</c:if>
					</table>
					</div>
					</div>
					
					<!-- paging -->
					<c:if test="${not empty conList }">
						<!--  2검색내역 // -->
						<div id="paging" align="center">
							<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="goPage" />
						</div>				
				
					</c:if>
					
					
					
					
					
					
					<div id="papeViewLayer1" style=" display:none; position:absolute;  width:500px; height:450px; left: 100; top: 100px; z-index:4; overflow: " style="filter:alpha(opacity=100)">
						<table cellspacing=1 cellpadding=5 border=0 bgcolor=959595 width=100%>
							<tr>
								<td bgcolor=ffffff>
								
									<table cellspacing=0 cellpadding=0 border=0 width=100%>
									<input type="hidden" id="bmanNO_pape1" name="bmanNO_pape1"/>
										<tr height=30>
											<td>&nbsp; &nbsp;<img src=/images/epc/popup/bull_01.gif border=0>&nbsp;<b>서류심사통과</b></td>
											<td width=50><a href="#" class="btn" onclick="javascript:updatePape1();"><span><spring:message code="button.common.create"/></span></a></td>
											<td width=50><a href="#" class="btn" onclick="javascript:papeWin1(false,'');"><span><spring:message code="button.common.close"/></span></a></td>
											
										</tr>
										<tr><td height=2 bgcolor="f4383f" colspan=3></td></tr>
										<tr><td height=10  colspan=3></td></tr>
									</table>
									<table cellspacing=0 cellpadding=0 border=0 width=100%>
										<tr>
											<td>&nbsp;&nbsp;&nbsp;1.연락처 : <input type="text" id="pape_tel" name="pape_tel"  maxlength="12"/>&nbsp;&nbsp;('-' 없이 입력해주세요.)</td>
										</tr>
									</table>
									<br>
								</td>
							</tr>
					     </table>		
					 </div>
					 
					 <div id="papeViewLayer2" style=" display:none; position:absolute;  width:600px; height:450px; left: 100; top: 100px; z-index:4; overflow: " style="filter:alpha(opacity=100)">
						<table cellspacing=1 cellpadding=1 border=0 bgcolor=959595 width=100%>
							<tr>
								<td bgcolor=ffffff>
									<table cellspacing=0 cellpadding=0 border=0 width=100%>
									<input type="hidden" id="bmanNO_pape2" name="bmanNO_pape2"/>
									<input type="hidden" id="papeNotVal" name="papeNotVal"/>
										<tr height=30>
											<td>&nbsp; &nbsp;<img src=/images/epc/popup/bull_01.gif border=0>&nbsp;<b>서류심사 반려 사유</b></td>
											<td width=50><a href="#" class="btn" onclick="javascript:updatePape2(false);"><span><spring:message code="button.common.create"/></span></a></td>
											<td width=50><a href="#" class="btn" onclick="javascript:papeWin2(false,'');"><span><spring:message code="button.common.close"/></span></a></td>
										</tr>
										<tr><td height=2 bgcolor="f4383f" colspan=3></td></tr>
										<tr><td height=10  colspan=3></td></tr>
									</table>
									<table cellspacing=0 cellpadding=0 border=0 width=100%>
										<tr>
											<td>&nbsp;&nbsp;&nbsp;
												<input type="radio" id="papeNot" name="papeNot" value="1" />&nbsp;1. 분류선택오류&nbsp;
												<input type="radio" id="papeNot" name="papeNot" value="2" />&nbsp;2. 정보미비&nbsp;
												<input type="radio" id="papeNot" name="papeNot" value="3" />&nbsp;3. 취급부적합상품&nbsp;
												<input type="radio" id="papeNot" name="papeNot" value="4" />&nbsp;4. 기존상품중복&nbsp;
												<input type="radio" id="papeNot" name="papeNot" value="5" checked />&nbsp;5. 기타&nbsp;
											</td>
										</tr>
										
										<tr>	
											<td>
												&nbsp;&nbsp;&nbsp; #반려세부사유 : <textarea rows="5" cols="100" id="pape_detail" name="pape_detail" ></textarea>
											</td>
										</tr>
									</table>
									<br>
								</td>
							</tr>
					     </table>		
					 </div>
					 
					 <div id="cnslViewLayer1" style=" display:none; position:absolute;  width:500px; height:450px; left: 100; top: 100px; z-index:4; overflow: " style="filter:alpha(opacity=100)">
						<table cellspacing=1 cellpadding=1 border=0 bgcolor=959595 width=100%>
							<tr>
								<td bgcolor=ffffff>
									<table cellspacing=0 cellpadding=0 border=0 width=100%>
									<input type="hidden" id="bmanNO_cnsl1" name="bmanNO_cnsl1"/>
										<tr height=30>
											<td>&nbsp; &nbsp;<img src=/images/epc/popup/bull_01.gif border=0>&nbsp;<b>상담결과 합격</b></td>
											<td width=50><a href="#" class="btn" onclick="javascript:updateCnsl1();"><span><spring:message code="button.common.create"/></span></a></td>
											<td width=50><a href="#" class="btn" onclick="javascript:cnslWin1(false,'');"><span><spring:message code="button.common.close"/></span></a></td>
											
										</tr>
										<tr><td height=2 bgcolor="f4383f" colspan=3></td></tr>
										<tr><td height=10  colspan=3></td></tr>
									</table>
									<table cellspacing=0 cellpadding=0 border=0 width=100%>
										<tr>
											<td>&nbsp;&nbsp;&nbsp;1.연락처 : <input type="text" id="cnsl_tel" name="cnsl_tel"  maxlength="11"/>&nbsp;&nbsp;('-' 없이 입력해주세요.)</td>
										</tr>
									</table>
									<br>
								</td>
							</tr>
					     </table>		
					 </div>
					 
					 <div id="cnslViewLayer2" style=" display:none; position:absolute;  width:600px; height:450px; left: 100; top: 100px; z-index:4; overflow: " style="filter:alpha(opacity=100)">
						<table cellspacing=1 cellpadding=1 border=0 bgcolor=959595 width=100%>
							<tr>
								<td bgcolor=ffffff>
									<table cellspacing=0 cellpadding=0 border=0 width=100%>
									<input type="hidden" id="bmanNO_cnsl2" name="bmanNO_cnsl2"/>
									<input type="hidden" id="cnslNotVal" name="cnslNotVal"/>
										<tr height=30>
											<td>&nbsp; &nbsp;<img src=/images/epc/popup/bull_01.gif border=0>&nbsp;<b>상담반려 사유</b></td>
											<td width=50><a href="#" class="btn" onclick="javascript:updateCnsl2(false);"><span><spring:message code="button.common.create"/></span></a></td>
											<td width=50><a href="#" class="btn" onclick="javascript:cnslWin2(false,'');"><span><spring:message code="button.common.close"/></span></a></td>
										</tr>
										<tr><td height=2 bgcolor="f4383f" colspan=3></td></tr>
									</table>
									<table cellspacing=0 cellpadding=0 border=0 width=100%>
										<tr>
											<td>&nbsp;&nbsp;&nbsp;
												<input type="radio" id="cnslNot" name="cnslNot" value="1" />&nbsp;1. 상품 디자인 미흡&nbsp;
												<input type="radio" id="cnslNot" name="cnslNot" value="2" />&nbsp;2. 상품구색 미흡&nbsp;
												<input type="radio" id="cnslNot" name="cnslNot" value="5" checked />&nbsp;3. 기타&nbsp;
											</td>
										</tr>
										<tr>	
											<td>
												&nbsp;&nbsp;&nbsp; #세부사유 : <textarea rows="5" cols="100" id="cnsl_detail" name="cnsl_detail"></textarea>
											</td>
										</tr>
									</table>
									<br>
								</td>
							</tr>
					     </table>		
					 </div>
					 
					 <div id="entshpViewLayer1" style=" display:none; position:absolute;  width:500px; height:450px; left: 100; top: 100px; z-index:4; overflow: " style="filter:alpha(opacity=100)">
						<table cellspacing=1 cellpadding=1 border=0 bgcolor=959595 width=100%>
							<tr>
								<td bgcolor=ffffff>
									<table cellspacing=0 cellpadding=0 border=0 width=100%>
									<input type="hidden" id="bmanNO_entshp1" name="bmanNO_entshp1"/>
										<tr height=30>
											<td>&nbsp; &nbsp;<img src=/images/epc/popup/bull_01.gif border=0>&nbsp;<b>품평회/입점 수락</b></td>
											<td width=50><a href="#" class="btn" onclick="javascript:updateEntshp1();"><span><spring:message code="button.common.create"/></span></a></td>
											<td width=50><a href="#" class="btn" onclick="javascript:entshpWin1(false,'');"><span><spring:message code="button.common.close"/></span></a></td>
										</tr>
										<tr><td height=2 bgcolor="f4383f" colspan=3></td></tr>
										<tr><td height=10  colspan=3></td></tr>
									</table>
									<table cellspacing=0 cellpadding=0 border=0 width=100%>
										<tr>
											<td>&nbsp;&nbsp;&nbsp;1.연락처 : <input type="text" id="entshp_tel" name="entshp_tel"  maxlength="11"/>&nbsp;&nbsp;('-' 없이 입력해주세요.)</td>
										</tr>
									</table>
									<br>
								</td>
							</tr>
					     </table>		
					 </div>
					 
					 <div id="entshpViewLayer2" style=" display:none; position:absolute;  width:600px; height:450px; left: 100; top: 100px; z-index:4; overflow: " style="filter:alpha(opacity=100)">
						<table cellspacing=1 cellpadding=1 border=0 bgcolor=959595 width=100%>
							<tr>
								<td bgcolor=ffffff>
									<table cellspacing=0 cellpadding=0 border=0 width=100%>
									<input type="hidden" id="bmanNO_entshp2" name="bmanNO_entshp2"/>
									<input type="hidden" id="entshpNotVal" name="entshpNotVal"/>
										<tr height=30>
											<td>&nbsp; &nbsp;<img src=/images/epc/popup/bull_01.gif border=0>&nbsp;<b>품평회/입점 거부</b></td>
											<td width=50><a href="#" class="btn" onclick="javascript:updateEntshp2(false);"><span><spring:message code="button.common.create"/></span></a></td>
											<td width=50><a href="#" class="btn" onclick="javascript:entshpWin2(false,'');"><span><spring:message code="button.common.close"/></span></a></td>
										</tr>
										<tr><td height=2 bgcolor="f4383f" colspan=2></td></tr>
										<tr><td height=10  colspan=3></td></tr>
									</table>
									<table cellspacing=0 cellpadding=0 border=0 width=100%>
										<tr>
											<td>&nbsp;&nbsp;&nbsp;1.연락처 : <input type="text" id="entshp_tel2" name="entshp_tel2" maxlength="11"/>&nbsp;&nbsp;('-' 없이 입력해주세요.)</td>
										</tr>
									</table>
									<br>
								</td>
							</tr>
					     </table>		
					 </div>

				</div>
			</div>
		</div>
		</form>
		
		
		
	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>EPC</li>
					<li>입점상담관리</li>
					<li class="last">상담업체관리</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->	
	</div>




</body>
</html>