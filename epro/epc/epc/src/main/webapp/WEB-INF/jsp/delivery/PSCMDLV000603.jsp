<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%
DecimalFormat df = new DecimalFormat("00");
Calendar currentCalendar = Calendar.getInstance();

currentCalendar.add(currentCalendar.DATE, -30);
String strYear31 = Integer.toString(currentCalendar.get(Calendar.YEAR));
String strMonth31 = df.format(currentCalendar.get(Calendar.MONTH) + 1);
String strDay31 = df.format(currentCalendar.get(Calendar.DATE)-1);
String strDate31 = strYear31 + strMonth31 + strDay31;
Object list = request.getAttribute("dataMap");
Object list2 = request.getAttribute("dataMapList");
%>
<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" >

var goOrderItemAdd = function() {
	var cnt = <%=list2%>;
	var counselContent = null;
	if($("#contentCheck").prop("checked") == true ){
		$("#contentCheck").val("Y");
		counselContent = $('#Content').val();
	}else {
		$("#contentCheck").val("N");
	}
	for(var i = 0; i <= cnt ; i++){
		var tail = "";
		if (i > 0) {
			tail = i;
		}
		if($("#contentCheck"+tail).prop("checked") == true ){
			$("#contentCheck"+tail).val("Y");
			counselContent = $('#Content'+tail).val();
		}else {
			$("#contentCheck"+tail).val("N");
		}
		if(cnt == 1){
			if($("#contentCheck").val() == "N" ){
				alert("결품등록 하실 상품을 체크하세요");
				return;
			}
		}else{
			if($("#contentCheck").val() == "N" && $("#contentCheck"+(i+1)).val() == "N"){
				alert("결품등록 하실 상품을 체크하세요");
				return;
			}
		}

	}
	var tdId= $('#tdId').val();
	opener.setOrderItem(counselContent, tdId);
	top.close();
}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
</head>
<body>
	<form name="dataForm" id="dataForm" method="post">
		<%-- <input type="hidden" name="recommSeq" id="recommSeq" value="${data.prodQnaSeq}"> --%>
		<input type="hidden" name="tdId"  id="tdId"  value="${param.tdId}">
		<div id="popup">
			<!--  @title  -->
			<div id="p_title1">
				<h1>결품 상세 / 등록</h1>
				<span class="logo"><img
					src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif"
					alt="LOTTE MART" /></span>
			</div>
			<!--  @title  //-->
			<!--  @process  -->
			<div id="process1">
				<ul>
					<li>홈</li>
					<li>배송관리</li>
					<li>배송리스트</li>
					<li class="last">결품 상세 / 등록</li>
				</ul>
			</div>
			<!--  @process  //-->
			<div class="popup_contents">
				<!-- 질문내용-->
				<div class="bbs_search3">
					<ul class="tit">
						<li class="tit">결품 내용</li>
						<c:if test="${TITLE==null}">
							<li class="btn">
								<a href="javascript:void(0)" class="btn" onclick="javascript:goOrderItemAdd();"><span>등록</span></a>
							</li>
						</c:if>
					</ul>
					<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
						<colgroup>
							<col width="13.5%">
							<col width="80.5%">
							<col width="5%">
						</colgroup>
						<tr>
							<th><span class="fst"></span>제목</th>
							<td colspan="3"><c:if test="${TITLE==null}">협력사(${vendorId}) 결품 처리요청(<%=strDate31 %>)</c:if><c:if test="${TITLE!=null}">${TITLE}</c:if></td>
						</tr>
						<c:set var="mode1" value="${param.mode}"/>
							<c:choose>
							<c:when test="${mode1 eq 'search'}">
							<tr>
							<th><span class="fst"></span>결품내용</th>
								<td colspan="2" >
									<input type="text" style="width:100% ; text-align:inherit;" name="Content" id="Content" wrap="hard"  value="${CONTENT}" disabled/>
								</td>
								<td colspan="1"><input type="checkbox" id="contentCheck" name="contentCheck" value="" checked="checked"> </td>
							</tr>
							</c:when>
							<c:when test="${mode1 eq 'insert'}">
								<c:forEach items="<%=list %>" var="ccc" varStatus="status">
								<tr >
								<c:if test="${status.index eq 0}">
								<th rowspan="2"><span class="fst"></span>결품내용</th>
								</c:if>
								<td colspan="2">
								<c:if test="${status.index eq 0}">
									<input type="text" style="width:100%;" name="Content" id="Content"  wrap="hard" value=" 상품코드[${ccc.PROD_CD}] 상품명[${ccc.PROD_NM}] 배송지 순번[${ccc.DELIVERY_ID}] 결품  " disabled />
								</c:if> 
								<c:if test="${status.index != 0}">
									<input type="text" style="width:100%;" name="Content" id="Content${status.index}"  wrap="hard" value=" 상품코드[${ccc.PROD_CD}] 상품명[${ccc.PROD_NM}] 배송지 순번[${ccc.DELIVERY_ID}] 결품  " disabled />
								</c:if>
								</td>
								<c:if test="${status.index eq 0}">
								<td colspan="1"><input type="checkbox" id="contentCheck" name="contentCheck" value=""></td>
								</c:if>
								<c:if test="${status.index != 0}">
								<td colspan="1"><input type="checkbox" id="contentCheck${status.index}" name="contentCheck" value=""></td>
								</c:if>
								</tr>
								</c:forEach>
							</c:when>
							</c:choose>
					</table>
				</div>
			</div>
		</div>
	</form>
	<iframe name="_if_save" src="/html/epc/blank.html" style="display: none;"></iframe>
</body>
</html>