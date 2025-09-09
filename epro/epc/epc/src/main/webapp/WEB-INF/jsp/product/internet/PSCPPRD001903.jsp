<%--
- Author(s): jib
- Created Date: 2011. 09. 02
- Version : 1.0
- Description : 추가속성정보 입력,수정 화면
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>

<%@page import="lcn.module.common.util.DateUtil"%>
<% 
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
	String mode = SecureUtil.stripXSS(request.getParameter("mode"));
	String categoryId = SecureUtil.stripXSS(request.getParameter("categoryId"));
	String addColSeq = SecureUtil.stripXSS(request.getParameter("addColSeq"));
	String addColValSeq = SecureUtil.stripXSS(request.getParameter("addColValSeq"));
	String colVal = SecureUtil.stripXSS((String)request.getAttribute("colVal"));
	String vendorId = SecureUtil.stripXSS((String)request.getParameter("vendorId"));
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- PBOMPRD003103 -->
<head>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script>
// 추가속성정보 업데이트
function doUpdate(v){
	
	$("#mode").val(v);

	$('#colVal').val($.trim($('#colVal').val()));
	if(!byteCheck('항목값',$('#colVal').val(),400,$('#colVal'))){
		return;
	}
	//값 입력 유무 채크
	if(Common.isEmpty($.trim($('#colVal').val()))){
		alert('<spring:message code="msg.common.error.required" arguments="항목값"/>');
		$('#colVal').focus();
		return;
	}

	var ment = "";
	if($("#mode").val()=="update") {
		ment = "상품 추가속성정보 항목값을 저장 하시겠습니까?";
	} else {
		ment = "상품 추가속성정보 항목값을 신규 등록 하시겠습니까?";
	}
	if( confirm(ment) ){
		if($("#mode").val()=="update") {
			callAjaxByForm('#dataForm', '<c:url value="/product/selectProductAttributeInsert.do"/>', '#dataForm', 'POST');
		} else {
			callAjaxByForm('#dataForm', '<c:url value="/product/selectProductAttributeInsert.do"/>', '#dataForm', 'POST');
		}
	}
}

// 추가속성정보 입력 아작스 호출
function callAjaxByForm(form, url, target, Type) {

	var formQueryString = $('*', '#dataForm').fieldSerialize();
	$.ajax({
		type: Type,
		url: url,
		data: formQueryString,
		success: function(json) {
			try {
				// 권한에러 메시지 처리 조건문 
				if(jQuery.trim(json) == "accessAlertFail") {
					alert('<spring:message code="msg.common.error.noAuth"/>');
				} else {				
					if(jQuery.trim(json) == ""){//처리성공
						alert('<spring:message code="msg.common.success.insert"/>');
						opener.goPage('1');
						top.close();
					}else{
						alert(jQuery.trim(json));
					}
				}
			} catch (e) {}
		},
		error: function(e) {
			alert("저장에 실패하였습니다");
		}
	});
	
}

// 현재창 닫기
function doClose(){
	top.close();
}

$(document).ready(function(){
});

</script>

</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>

<form name="dataForm" id="dataForm">
<input type="hidden" name="prodCd" id="prodCd" value="<%=prodCd%>">
<input type="hidden" name="mode" id="mode" value="<%=mode%>">
<input type="hidden" name="categoryId" id="categoryId" value="<%=categoryId%>">
<input type="hidden" name="addColSeq" id="addColSeq" value="<%=addColSeq%>">
<input type="hidden" name="addColValSeq" id="addColValSeq" value="<%=addColValSeq%>">
<input type="hidden" name="vendorId" id="vendorId" value="<%=vendorId%>">

<div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>추가속성</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>상품관리</li>
			<li class="last">인터넷상품관리</li>			
		</ul>
     </div>
	 <!--  @process  //-->
	 <div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
				<ul class="tit">
					<li class="tit">추가속성 항목값</li>
					<li class="btn">
						<a href="javascript:doUpdate('update');" class="btn" ><span>저장</span></a>
						<a href="javascript:doClose();" class="btn" ><span>닫기</span></a>
					</li>
				</ul>
				<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0" width="100%">
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tr>
					<th><span class="star"></span>항목값</th>
					<td><textarea cols="100" rows="8" id="colVal" name="colVal"><%=colVal%></textarea><!-- input type="text" maxlength="400" id="colVal" name="colVal" value="<%=colVal%>" size="30"--></td>
				</tr>				
				</table>
		</div>				
	</div>
</div>	
</form>

</body>
</html>