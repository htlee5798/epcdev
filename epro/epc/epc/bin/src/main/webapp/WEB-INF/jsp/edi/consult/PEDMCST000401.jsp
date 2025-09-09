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
	var cw=400;
	var ch=300;
	var sw=screen.availWidth;
	var sh=screen.availHeight;
	var px=Math.round((sw-cw)/2);
	var py=Math.round((sh-ch)/2);
	window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
}

function doUpdate(){
	var form =  document.forms[0];

	var pids = document.getElementsByName("pid");
	var e_text = document.getElementsByName("e_text");
	var pcs = document.getElementsByName("pc");
	var product_pops = document.getElementsByName("product_pop");
	var standards = document.getElementsByName("standard");
	var packs = document.getElementsByName("pack");
	var ratings = document.getElementsByName("rating");
	var estimate_prices = document.getElementsByName("estimate_price");
	var origins = document.getElementsByName("origin");
	var remarks = document.getElementsByName("remark");
	var product_ids = document.getElementsByName("product_id");
	var product_names = document.getElementsByName("product_name");
	
	var tmp="";
	var tmp2="";

	if(pcs.length){
		for(var i = 0 ; i < pcs.length; i++){
			tmp2 = pids[i].value;
			tmp += pcs[i].value + ";";
			tmp += product_ids[i].value + ";";
			tmp += product_names[i].value + ";";
			tmp += standards[i].value + ";";
			tmp += packs[i].value + ";";
			tmp += ratings[i].value + ";";
			tmp += estimate_prices[i].value + ";";
			tmp += origins[i].value + ";";
			tmp += remarks[i].value + "@";
		}
	}else{
			tmp2 = pids.value;
			tmp = pcs.value + ";";
			tmp = product_ids.value + ";";
			tmp = product_names.value + ";";
			tmp = standards.value + ";";
			tmp = packs.value + ";";
			tmp = ratings.value + ";";
			tmp = estimate_prices.value + ";";
			tmp = origins.value + ";";
			tmp = remarks.value + "@";
	}
	
	form.forward_values.value = tmp;
	form.forward_pid.value = tmp2;
	form.forward_e_text.value = form.e_text.value;

	form.action  = "<c:url value='/edi/consult/PEDMCST0004UpdateDetailForward.do'/>";
	form.submit();
	
}

function fileDownlord(val3){
	var form =  document.forms[0];

	form.pid_file.value=val3; 

	form.action  = "<c:url value='/edi/consult/PEDMCST0004FileDownload.do'/>";
	form.submit();
	
}
</script>

</head>

<body>
<form name="searchForm" method="post" action="#" >
<input type="hidden" name="forwardPID" value="${estListTop.PID }" />

<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:${param.widthSize }</c:if>  >
	<div>
		<!--	@ BODY WRAP START 	-->
		
		
		<input type="hidden" id="widthSize" name="widthSize" value="${param.widthSize }" > 
		
		<div id="wrap_menu">
			<div class="bbs_search">
					<ul class="tit">
						<li class="tit">견적문서 조회 </li>
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
					<input type="hidden" name="deleteCode" />
					<input type="hidden" name="forward_values" />
					<input type="hidden" name="forward_pid" />
					<input type="hidden" name="forward_e_text" />
					<input type="hidden" name="file_name" />
					<input type="hidden" name="file_path" />
					<input type="hidden" name="pid_file" />
					
					
						<colgroup>
							<col style="width:100px;" />
							<col style="width:300px;" />
							<col style="width:100px;" />
							<col />
						</colgroup>
							<input type="hidden" name="e_text" value="${estListTop.DOC_NM }"/>
							<tr>
								<th>문서번호 </th>
								<td align="left" >&nbsp;&nbsp;${estListTop.PID }</td>
								<th>첨부문서</th>
								
								<td align="left" >&nbsp;&nbsp;<a href="javascript:fileDownlord('${estListTop.PID }');">${estListTop.FILE_NM }</a></td>
							</tr>
							<tr>
								<th>문서명</th>
								<td align="left" >&nbsp;&nbsp;${estListTop.DOC_NM }</td>
								<th>협력업체</th>
								<td align="left" >&nbsp;&nbsp;${estListTop.VEN_CD }</td>
							</tr>
						
					
						<c:if test="${not empty estList }">
						<input type="hidden" name="firstList" value="exist"/>
							<c:forEach items="${estList}" var="list" varStatus="index" >
								<tr class="r1">
									<td align="center"><input type="checkbox" name="ck" value="${list.PID }"/></td>
									<td align="center"><a href="javascript:forwardDetailPage('${list.PID }');">${list.PID }</a></td>
									<td align="center">${list.VEN_CD }</td>
									<td align="center">${list.DOC_NM }</td>
									<td align="center">${list.FILE_NM }</td>
									<td align="center">${list.REG_DATE }</td>
								</tr>
							</c:forEach>
						</c:if>
					
					</table>
					
					<ul class="tit">
						<li class="tit">상품내역</li>
						
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
							<th>PC</th>
							<th>상품코드</th>
							<th>상품명</th>
							<th>규격</th>
							<th>포장형태</th>
							<th>등급</th>
							<th>견적가</th>
							<th>원산지</th>
							<th>비고</th>
						</tr>
						<c:if test="${not empty estListBottom }">
							<c:forEach items="${estListBottom}" var="list" varStatus="index" >
							
							<input type="hidden" name="pid" value="${list.PID }" />
							<input type="hidden" name="pc" value="${list.ES_PC }"/>
							<input type="hidden" name="product_id" value="${list.ES_PROD_CD }" />
							<input type="hidden" name="product_name" value="${list.ES_PROD_NM }" />
							<input type="hidden" name="standard" value="${list.ES_STANDARD }" />
							<input type="hidden" name="pack" value="${list.ES_PACK_TYPE }" />
							<input type="hidden" name="rating" value="${list.ES_GRADE }" />
							<input type="hidden" name="estimate_price" value="${list.ES_PRICE }" />
							<input type="hidden" name="origin" value="${list.ES_ORGIN }" />
							<input type="hidden" name="remark" value="${list.ES_DETAIL }" />
					
								<tr class="r1">
									<td align="center">${list.ES_PC }</td>
									<td align="center">${list.ES_PROD_CD }</td>
									<td align="left">&nbsp;&nbsp;${list.ES_PROD_NM }</td>
									<td align="center">${list.ES_STANDARD }</td>
									<td align="center">${list.ES_PACK_TYPE }</td>
									<td align="center">${list.ES_GRADE }</td>
									<td align="center">${list.ES_PRICE }</td>
									<td align="center">${list.ES_ORGIN }</td>
									<td align="center">${list.ES_DETAIL }</td>
								</tr>
							</c:forEach>
						</c:if>
						
						<c:if test="${empty estListBottom }">
						<input type="hidden" name="firstList" value="none"/>
							<tr><td colspan="9" align=center>Data가 없습니다.</td></tr>
						</c:if>
					
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
						<li>홈</li>
						<li>협업</li>
						<li>견적문서 조회</li>
						<li class="last">견적문서 상세조회</li>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->	
		
		</div>
	
</form>
</body>

</html>