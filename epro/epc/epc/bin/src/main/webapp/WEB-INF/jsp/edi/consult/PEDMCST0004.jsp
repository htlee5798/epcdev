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

		/* 폼로드 */
		$(document).ready(function($) {		
			$("select[name='entp_cd']").val("<c:out value='${param.entp_cd}'/>");	// 협력업체 선택값 세팅
		});
		



	function checkAll(){
		form=document.forms[0];
		var checkboxList = form.ck;

		if(form.notData.value=="none"){
			return;
		}

		if(form.ckall.length){
			if(form.ckall.checked==true){
				for(var i=0;i<checkboxList.length;i++){
					checkboxList[i].checked=true;
				}
			}else if(form.ckall.checked==false){
				for(var i=0;i<checkboxList.length;i++){
					checkboxList[i].checked=false;
				}
			}
		}else{
			if(form.ckall.checked==true){
				for(var i=0;i<checkboxList.length;i++){
					checkboxList.checked=true;
				}
			}else if(form.ckall.checked==false){
				for(var i=0;i<checkboxList.length;i++){
					checkboxList.checked=false;
				}
			}
		}

		if(form.ckall.checked==true){
			for(var i=0;i<checkboxList.length;i++){
				checkboxList[i].checked=true;
			}
		}else if(form.ckall.checked==false){
			for(var i=0;i<checkboxList.length;i++){
				checkboxList[i].checked=false;
			}
		}
	
	}

	function forwardDetailPage(val){
		var form = document.forms[0];

		form.forwardPID.value=val;

		form.action  = "<c:url value='/edi/consult/PEDMCST0004SelectDetail.do'/>";
		form.submit();	
	}
	
	function doSearch() {

		var form = document.forms[0];
		if(dateValid(form)){
			form.action  = "<c:url value='/edi/consult/PEDMCST0004Select.do'/>";
			form.submit();		
		}	
		
	}

	function doDelete(){
		var form = document.forms[0];
		var checkboxList = form.ck;

		var tmp="";
		var tmp_ck="none";

		if(form.notData.value=="none"){
			alert("조회된 견적문서가 없습니다.");
			return;
		}

		if(checkboxList.length){
			for(var i=0;i<checkboxList.length;i++){
				if(checkboxList[i].checked==true){
					tmp_ck="exist";
				}
			}
		}else{
			
			if(checkboxList.checked==true){
				tmp_ck="exist";
			}
		}

		if(tmp_ck=="none"){
			alert("선택된 견적문서가 없습니다.");
			return;
		}

		if(confirm("선택된 견적문서를 삭제합니까?")){
			if(form.ck.length){
				for(var i=0;i<form.ck.length;i++){
					if(form.ck[i].checked){
						tmp += form.ck[i].value+";";
					}
				}
			}else{
				tmp=form.ck.value+";";
			}

			form.deleteCode.value=tmp;

			form.action  = "<c:url value='/edi/consult/PEDMCST0004Delete.do'/>";
			form.submit();	
		}
		
	}

	function dateValid(form){

		var startDate = form.startDate.value;
		var endDate = form.endDate.value;
		var rangeDate = 0;
		
		if(startDate == "" || endDate == ""  ){
			alert("<spring:message code='msg.common.fail.nocalendar'/>");
			form.startDate.focus();
			return false;
		}


		// startDate, endDate 는 yyyy-mm-dd 형식

	    startDate = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
	    endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);

	   var intStartDate = parseInt(startDate);
	   var intEndDate = parseInt(endDate);
			
		
	    if (intStartDate > intEndDate) {
	        alert("<spring:message code='msg.common.fail.calendar'/>");
	        form.startDate.focus();
	        return false;
	    }

		
	    intStartDate = new Date(startDate.substring(0, 4),startDate.substring(4,6),startDate.substring(6, 8),0,0,0); 
	    endDate = new Date(endDate.substring(0, 4),endDate.substring(4,6),endDate.substring(6, 8),0,0,0); 


	    rangeDate=Date.parse(endDate)-Date.parse(intStartDate);
	    rangeDate=Math.ceil(rangeDate/24/60/60/1000);

	    /*
		if(rangeDate>30){
			alert("<spring:message code='msg.common.fail.rangecalendar_30'/>");
			form.startDate.focus();
			return false;
		}
	    */
		return true;
		
	}
</script>

</head>

<body>
<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form name="searchForm" method="post" action="#" >
		
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
		
		<div id="wrap_menu">
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">견적서 등록</li>
						<li class="btn">
							<a href="#" class="btn" onclick="javascript:doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
							<a href="#" class="btn" onclick="javascript:doDelete();"><span><spring:message code="button.common.delete"/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					
					<colgroup>
						<col style="width:15%" />
						<col style="width:30%" />
						<col style="width:10%" />
						<col style="*" />
					</colgroup>
					<tr>
						<th><span class="star">*</span> 조회기간 </th>
						<td>
							<input type="text" class="day" id="startDate" name="startDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.startDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.startDate');" style="cursor:hand;" />
							~
							<input type="text" class="day" id="endDate" name="endDate" onKeyPress="fnOnlyNumber();" maxlength="10" readonly value="${paramMap.endDate}" style="width:80px;" /> <img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('searchForm.endDate');"  style="cursor:hand;" />
						</td>
							
						<th>협력업체 코드</th>
						<td>
							<html:codeTag objId="entp_cd" objName="entp_cd" width="150px;" selectParam="" dataType="CP" comType="SELECT" formName="form" defName="전체" />
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
					<div style="width:100%; height:484px; overflow-y:scroll; overflow-x:scroll;">
					<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="addTable">
					<input type="hidden" name="deleteCode" />
					<input type="hidden" name="forwardPID" />
					
						<colgroup>
							<col style="width:30px;" />
							<col style="width:100px;" />
							<col style="width:100px;" />
							<col style="width:150px;" />
							<col />
							<col style="width:100px;"/>
						
						</colgroup>
						<tr>
							<th><input type="checkbox" name="ckall" onclick="javascript:checkAll();"/></th>
							<th>문서번호</th>
							<th>협력업체코드</th>
							<th>문서명</th>
							<th>첨부파일명</th>
							<th>등록일자</th>
						</tr>
						<c:if test="${not empty estList }">
							<input type="hidden" name="notData" value="exist" />
							<c:forEach items="${estList}" var="list" varStatus="index" >
								<tr class="r1">
									<td align="center"><input type="checkbox" name="ck" value="${list.PID }"/></td>
									<td align="center"><a href="javascript:forwardDetailPage('${list.PID }');">${list.PID }</a></td>
									<td align="center">${list.VEN_CD }</td>
									<td>&nbsp;${list.DOC_NM }</td>
									<td>&nbsp;${list.FILE_NM }</td>
									<td align="center">${list.REG_DATE }</td>
								</tr>
							</c:forEach>
						</c:if>
						
						<c:if test="${empty estList }">
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