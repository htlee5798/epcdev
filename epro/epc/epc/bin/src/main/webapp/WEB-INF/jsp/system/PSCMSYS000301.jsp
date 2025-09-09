<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib 	prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOTTE MART Back Office System</title>

<!-- CSS URL -->
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" ></link>

<!-- JS URL -->
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js" ></script>

<script type="text/javascript" >

/** ********************************************************
 * 수정 처리 함수
 ******************************************************** */
function insert(){
	var form = document.adminForm;

	var url = '<c:url value="/system/insertPartnerPopup.do"/>';
	
	form.repTelNo.value = form.repTelNo1.value + "-" + form.repTelNo2.value + "-" + form.repTelNo3.value;
	form.zipCd.value  = form.zipCd1.value + form.zipCd2.value;

	if(form.vendorNm.value == ""){
		alert("협력사명을 입력해주세요.");
		form.vendorNm.focus();
		return;
	}
	
	if(form.repTelNo.value.length > 2 &&  form.repTelNo.value.length < 11){
		alert("전화번호를 제대로 입력하세요.");
		return;
	}
	
	if(form.zipCd.value.length > 0 &&  form.zipCd.value.length < 6){
		alert("우편번호를 제대로 입력하세요.");
		return;
	}
	
	if (!confirm("협력사를 저장하시겠습니까?")) {
		return;
	}

 	form.action = url;

  	form.submit();
}

function onlyNumber() {
    if ((event.keyCode < 48) || (event.keyCode > 57)) {
    	event.returnValue = false;
    }
}
</script>

</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="adminForm" method="post">
 <div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>협력사 등록</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>시스템관리</li>
			<li class="last">협력사 등록</li>
		</ul>
     </div>
	 <!--  @process  //-->
	 
	 	 <div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">협력사 등록</li>
				<li class="btn">
					<a href="#" class="btn" onclick="insert();"><span><spring:message code="button.common.create"/></span></a>
					<a href="#" class="btn" onclick="self.close();"><span><spring:message code="button.common.close"/></span></a>
				</li>
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
			<colgroup>
				<col style="width:20%" />
				<col style="width:30%" />
				<col style="width:20%" />
				<col style="width:30%" />
			</colgroup>
			<tr>
				<th class="fst"><span class="star" style="color: red;" >*</span>협력사명</th>
				<td>
					<input type="text" name="vendorNm" id="vendorNm" class="input" maxlength="30" />
				</td>
				<th class="fst">도서산간배송비여부</th>
				<td class="left">
					<select name="islndDeliAmtYn" id="islndDeliAmtYn" class="select" style="width:40%;" >
						<option value="Y">Y</option>
						<option value="N">N</option>
					</select>
				</td>
			</tr>
			<tr>
				<th class="fst">전화번호</th>
				<td class="left">
					<input type="text" name="repTelNo1" id="repTelNo1" class="input" maxlength="4" style="width:25%;ime-mode:disabled" onKeyPress="onlyNumber();" />
					-
					<input type="text" name="repTelNo2" id="repTelNo2" class="input" maxlength="4" style="width:25%;ime-mode:disabled" onKeyPress="onlyNumber();" />
					-
					<input type="text" name="repTelNo3" id="repTelNo3" class="input" maxlength="4" style="width:25%;ime-mode:disabled" onKeyPress="onlyNumber();" />
					<input type="hidden" name="repTelNo" />
				</td>
				<th class="fst">우편번호</th>
				<td>
					<input type="text" name="zipCd1" id="zipCd1" class="input" maxlength="3" style="width:30%;ime-mode:disabled" onKeyPress="onlyNumber();" />
					-
					<input type="text" name="zipCd2" id="zipCd2" class="input" maxlength="3" style="width:30%;ime-mode:disabled" onKeyPress="onlyNumber();" />
					<input type="hidden" name="zipCd" />
				</td>
			</tr>
			<tr>
				<th class="fst">주소</th>
				<td colspan="3">
					<input type="text" name="addr_1_nm" id="addr_1_nm" class="input" maxlength="100"/>
				</td>
			</tr>
			<tr>
				<th class="fst">상세주소</th>
				<td colspan="3">
					<input type="text" name="addr_2_nm" id="addr_2_nm" class="input" maxlength="100"/>
				</td>
			</tr>
			</table>
		</div>
		<!--  @작성양식  2//-->
	</div>
 </div>
</form>
</body>
</html>