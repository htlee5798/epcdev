<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>롯데마트 입점상담</title>

<script language="JavaScript">

function PopupWindow(pageName) {
	var cw=400;
	var ch=300;
	var sw=screen.availWidth;
	var sh=screen.availHeight;
	var px=Math.round((sw-cw)/2);
	var py=Math.round((sh-ch)/2);
	window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
}

function doUpdate(){
 	$('#searchForm').attr("action","<c:url value='/edi/consult/NEDMCST0220UpdateDetailForward.do'/>")
	$('#searchForm').submit();
}

function fileDownlord(val3){
	var form =  document.forms[0];

	form.pid_file.value=val3; 

	form.action  = "<c:url value='/edi/consult/NEDMCST0220FileDownload.do'/>";
	form.submit();
	
}
</script>

</head>

<body>
<form id = "searchForm" name="searchForm" method="post" action="#" >
<input type="hidden" name="pid" value="${estListTop.pid }" />
<input type="hidden" name="pid_file" />
<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		
		
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
		
		<div id="wrap_menu">
			<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.cst.retrive'/></li>
						<li class="btn">
							<a href="#" class="btn" onclick="javascript:doUpdate();"><span><spring:message code="button.common.update"/></span></a>
							<a href="#" class="btn" onclick="javascript:history.back();"><span><spring:message code="button.consult.back"/></span></a>
						</li>
					</ul>
				</div>
			</div>
			<!--	 검색내역 	-->
			<div class="wrap_con">
				<!-- list -->
				<div class="bbs_list">
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col style="width:100px;" />
							<col style="width:300px;" />
							<col style="width:100px;" />
							<col />
						</colgroup>
							<input type="hidden" name="e_text" value="${estListTop.docNm }"/>
							<tr>
								<th><spring:message code='epc.cst.header.hCode11'/> </th>
								<td align="left" >&nbsp;&nbsp;${estListTop.pid}</td>
								<th><spring:message code='epc.cst.header.hCode14'/></th>
								
								<td align="left" >&nbsp;&nbsp;<a href="javascript:fileDownlord('${estListTop.pid }');">${estListTop.fileNm }</a></td>
							</tr>
							<tr>
								<th><spring:message code='epc.cst.header.hCode13'/></th>
								<td align="left" >&nbsp;&nbsp;${estListTop.docNm}</td>
								<th><spring:message code='epc.cst.header.hCode12'/></th>
								<td align="left" >&nbsp;&nbsp;${estListTop.venCd}</td>
							</tr>
					</table>
					
					<ul class="tit">
						<li class="tit"><spring:message code='epc.cst.header.prodCdList'/></li>
						
					</ul>
					<div style="width:100%; height:458px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll;  table-layout:fixed;white-space:nowrap;">
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="addTable">
							<colgroup>
								<col style="width:90px;" />
								<col style="width:80px;" />
								<col />
								<col style="width:70px;" />
								<col style="width:70px;" />
								<col style="width:90px;" />
								<col style="width:70px;" />
								<col style="width:90px;" />
								<col style="width:60px;" />
							</colgroup>
							<tr>
								<th><spring:message code='epc.cst.header.hCode1'/></th>
								<th><spring:message code='epc.cst.header.prodCd'/></th>
								<th><spring:message code='epc.cst.header.prodNm'/></th>
								<th><spring:message code='epc.cst.header.hCode3'/></th>
								<th><spring:message code='epc.cst.header.hCode4'/></th>
								<th><spring:message code='epc.cst.header.hCode5'/></th>
								<th><spring:message code='epc.cst.header.hCode6'/></th>
								<th><spring:message code='epc.cst.header.hCode7'/></th>
								<th><spring:message code='epc.cst.header.hCode8'/></th>
							</tr>
							<c:choose>
								<c:when test="${not empty estListBottom }">
									<c:forEach var="list" items="${estListBottom }">
										<tr class="r1">
											<td align="center">${list.esPc}</td>
											<td align="center">${list.esProdCd}</td>
											<td align="left">&nbsp;&nbsp;${list.esProdNm }</td>
											<td align="center">${list.esStandard }</td>
											<td align="center">${list.esPackType }</td>
											<td align="center">${list.esGrade }</td>
											<td align="center">${list.esPrice }</td>
											<td align="center">${list.esOrgin }</td>
											<td align="center">${list.esDetail }</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr><td colspan="9" align=center><spring:message code='epc.cst.emptySearchResult'/></td></tr>
								</c:otherwise>
							</c:choose>
						</table>
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
						<li><spring:message code='epc.cst.home'/></li>
						<li><spring:message code='epc.cst.cola'/></li>
						<li><spring:message code='epc.cst.retrive'/></li>
						<li class="last"><spring:message code='epc.cst.retriveDetail'/></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->	
		
		</div>
	
</form>
</body>

</html>