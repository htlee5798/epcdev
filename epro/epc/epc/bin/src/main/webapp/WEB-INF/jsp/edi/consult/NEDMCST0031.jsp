<%@include file="../common.jsp" %>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script>
$(document).ready(function($){
	doSearch();
	//bmanSet();
});
/* function bmanSet(){
	var form = document.forms[0];

	var tmp = form.bman.value.split(";");

	for(var i=0;i<tmp.length-1;i++){
		
		form.sele_bman.options.add(new Option(tmp[i], tmp[i]));
		
	}
	
	form.sele_bman.value = "${paramMap.bmanNo}";
} */

function doSearch(){
	
	var searchInfo = {};
	
	// 조회기간(From)
	searchInfo["bmanNo"] 	= $("#searchForm select[name='bmanNo']").val();
	searchInfo["anxInfoCd"] 	= $("#searchForm select[name='anxInfoCd']").val();
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'post',
		dataType : 'json',
		async : false,
		url : '<c:url value="/edi/consult/NEDMCST0031select.json"/>',
		data : JSON.stringify(searchInfo),
		success : function(data) {
			
			//json 으로 호출된 결과값을 화면에 Setting 
			_setTbodyMasterValue(data);
		}
	});	
	
	
	/* _eventSearch() 후처리(data  객체 그리기) */
	function _setTbodyMasterValue(json) { 
		var data = json.alertList;
		var business = json.business;
		var tempData = [];
		
		for(var i = 0;i<data.length;i++){
			tempData[i] =  data[i];
			tempData[i]['business'] = business;
			
		}
			
		setTbodyInit("dataListbody");	// dataList 초기화
		
		if (data.length > 0) {
			$("#dataListTemplate").tmpl(data).appendTo("#dataListbody"); // setComma(n)
		} else { 
			setTbodyNoResult("dataListbody", 9);
		}
				
	}
		
}


function doInsertPage(){
	loadingMaskFixPos();
	$('#searchForm').attr("action", "<c:url value='/edi/consult/NEDMCST0032forwardInsertPage.do'/>");
	$('#searchForm').submit();
}

function doUpdatePage(svcSeq,bmanNo,anxInfoCd){
	$('#updateForm #svcSeq').val(svcSeq);
	$('#updateForm #bmanNo').val(bmanNo);
	$('#updateForm #anxInfoCd').val(anxInfoCd);
	loadingMaskFixPos();
	$('#updateForm').attr("action", "<c:url value='/edi/consult/NEDMCST0033forwardUpdatePage.do'/>");
	$('#updateForm').submit();
	
}
</script>
<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->
	
<script id="dataListTemplate" type="text/x-jquery-tmpl">
	
<tr class="r1">
	<td align="center"><c:out value="\${business}"/></td>
	<td align="center">
		{%if anxInfoCd == 'ALM' %}
		<spring:message code='epc.cst.codeNm1'/>
		{%else%}
		<spring:message code='epc.cst.codeNm2'/>
		{%/if%}
	</td>
	<td align="center"><c:out value="\${telnoNm}"/></td>
	<td align="center"><c:out value="\${email}"/></td>
	<td align="center"><a href="javascript:doUpdatePage('<c:out value="\${svcSeq}"/>','<c:out value="\${bmanNo}"/>','<c:out value="\${anxInfoCd}"/>');" onClick="" class="btn"><span>수정</span></a></td>
</tr>
</script>

<!-- Data List Templete -------------------------------------------------------------------------------------------------------------->

</head>

<body>
	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		<form id="searchForm" name="searchForm" method="post">
		<div id="wrap_menu">
		
			<!--	@ 검색조건	-->
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit"><spring:message code='epc.cst.alertService'/></li>
						<li class="btn">
							<a href="#" class="btn" onclick="javascript:doSearch();"><span><spring:message code='ecp.cst.button.retrive'/></span></a>
							<a href="#" class="btn" onclick="javascript:doInsertPage();"><span><spring:message code='ecp.cst.button.create'/></span></a>
						</li>
					</ul>
					<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
					<colgroup>
						<col style="width:100px;" />
						<col />
						<col style="width:100px;" />
						<col />
					</colgroup>
					<tr>
						<th><spring:message code='epc.cst.search.bmanNo'/></th>
						<td>	
							<select id="bmanNo" name="bmanNo">
								<option value="all"><spring:message code='epc.cst.header.all'/></option>
								<c:forEach var="bman" items="${paramMap.bmans }">
									<option value="${bman }">${bman }</option>
								</c:forEach>
							</select>
						</td>
						<th><spring:message code='epc.cst.search.selectAnxInfoCd'/></th>
						<td>
							<select name="anxInfoCd">
								<option value="all"><spring:message code='epc.cst.header.all'/></option>
								<option value="ALM" <c:if test="${paramMap.anxInfoCd eq 'ALM' }">selected</c:if>><spring:message code='epc.cst.codeNm1'/></option>
								<option value="PAY" <c:if test="${paramMap.anxInfoCd eq 'PAY' }">selected</c:if>><spring:message code='epc.cst.codeNm2'/></option>
							</select>&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					</table>
					
				</div>
			</div>
			
			<div class="wrap_con">
					<!-- list -->
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit"><spring:message code='epc.cst.search.result'/></li>
						</ul>
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
						<colgroup>
							<col style="width:120px;" />
							<col style="width:120px;" />
							<col style="width:150px;" />
							<col />
							<col style="width:100px;" />
							<col style="width:17px;" />
						</colgroup>
						<tr>
							<th><spring:message code='epc.cst.header.hCode00311'/></th>
							<th><spring:message code='epc.cst.header.hCode00312'/></th>
							<th><spring:message code='epc.cst.header.hCode00313'/></th>
							<th><spring:message code='epc.cst.header.hCode00314'/></th>
							<th><spring:message code='epc.cst.header.hCode00315'/></th>
							<th>&nbsp;</th>
						</tr>
						</table>
						<div style="width:100%; height:461px;overflow-x:hidden; overflow-y:scroll; overflow-x:scroll; table-layout:fixed;">
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable2">
								<colgroup>
									<col style="width:120px;" />
									<col style="width:120px;" />
									<col style="width:150px;" />
									<col />
									<col style="width:100px;" />
								</colgroup>
								<!-- Data List Body Start ------------------------------------------------------------------------------>
								<tbody id="dataListbody" />
								<!-- Data List Body End   ------------------------------------------------------------------------------>
							</table>
						</div>
					</div>
				</div>
					
		</div>
		</form>
	</div>
	<form id="updateForm" name="updateForm" method="post">
		<input id="bmanNo" type="hidden" name="bmanNo" />  
		<input id="svcSeq" type="hidden" name="svcSeq" />
		<input id="anxInfoCd" type="hidden" name="anxInfoCd" />
	</form>


	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li><spring:message code='epc.cst.home'/></li>
					<li><spring:message code='epc.cst.cola'/></li>
					<li><spring:message code='epc.cst.colaInfo'/></li>
					<li class="last"><spring:message code='epc.cst.alertService'/></li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

</body>
</html>
