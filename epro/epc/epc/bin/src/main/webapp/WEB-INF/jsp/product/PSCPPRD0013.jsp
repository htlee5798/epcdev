<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib 	prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="/common/scm/scmCommon.jsp" %>

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

	var url = '<c:url value="/product/insertSchedulePopup.do"/>';
	
	//입력값 체크
	if(!checkValidValue()) {
		return;
	}

	if (!confirm("촬영스케쥴정보를 저장하시겠습니까?")) {
		return;
	}
	
	setCellNo();

 	form.action = url;

  	form.submit();
}

/**********************************************************
 * 입력 값 체크
 *********************************************************/
function checkValidValue()
{
	var date = new Date();
	var yyyy = date.getFullYear().toString();
	var mm = (date.getMonth()+1).toString().length < 2 ? "0"+(date.getMonth()+1) : (date.getMonth()+1).toString();
	var dd = date.getDate().toString().length < 2 ? "0"+date.getDate() : date.getDate().toString();
	var hh = date.getHours().toString().length < 2 ? "0"+date.getHours() : date.getHours().toString();
	var mi = date.getMinutes().toString().length < 2 ? "0"+date.getMinutes() : date.getMinutes().toString();;
	var today = yyyy+mm+dd+hh+mi;
	
	var form = document.adminForm;
	var startTime = form.rservStartDy.value.replace(/-/gi, "") + form.rservStartHour.value + form.rservStartMin.value;
	var endTime = form.rservStartDy.value.replace(/-/gi, "") + form.rservEndHour.value + form.rservEndMin.value;
	
	if (form.vendorId.value == "")
    {
        alert('업체선택은 필수입니다.');
        form.vendorId.focus();
        return;
    }
	
	if(today > startTime) {
		alert("촬영시작시간을 현재 시간 이후로 설정해주세요.");
		return false;
	}
	if(startTime >= endTime) {
		alert("촬영종료시간을 촬영시작시간 이후로 설정해주세요.");
		return false;
	}
	if("1700" < startTime.substring(8, 12) || "1700" < endTime.substring(8, 12)) {
		alert("촬영시간은 17시까지만 가능합니다.");
		return false;
	}
	return true;
}

/** ********************************************************
 * 입력창 null check 함수
 ******************************************************** */
function chkfield(focusField,data, name)
{
	if (data == "")
	{
		alert(name + " 항목을 반드시 입력해 주십시오.");
		focusField.focus();
		return false;
	}
	return true;
}

function setCellNo() {
	var form = document.adminForm;
	var cellNo1 = spaceLpad(form.cellNo1.value);
	var cellNo2 = spaceLpad(form.cellNo2.value);
	var cellNo3 = spaceLpad(form.cellNo3.value);
	form.cellNo.value = cellNo1 + cellNo2 + cellNo3;
}

function spaceLpad(str) {
	while(str.length < 4) {
		str = str + " ";
	}
	return str;
}

function memoLimit(obj) {
	var maxlength = parseInt(obj.getAttribute("maxlength"));
	if(obj.value.length > maxlength) {
		alert("내용은 " + maxlength + "자 이내로 입력해주십시오.");
		obj.value = obj.value.substring(0, maxlength);
	}
}

function onlyNumber() {
    if ((event.keyCode < 48) || (event.keyCode > 57)) {
    	event.returnValue = false;
    }
}

function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
	$("#vendorId").val(strVendorId);
}
</script>

</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="adminForm" method="post">
 <div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>상품이미지촬영스케쥴 등록</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>상품관리</li>
			<li class="last">상품이미지촬영스케쥴 등록</li>
		</ul>
     </div>
	 <!--  @process  //-->
	 
	 	 <div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">상품이미지촬영스케쥴 등록</li>
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
				<th class="fst">협력업체코드</th>
				<td>
					<c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<select name="vendorId" id="vendorId" class="select">
										<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
					                        <option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
										</c:forEach>
										</select>
									</c:when>
						</c:choose>
				</td>
				<th class="fst">휴대전화</th>
				<td class="left">
					<select name="cellNo1" class="select" style="width:15%;" >
						<option value="010">010</option>
						<option value="011">011</option>
						<option value="010">016</option>
						<option value="017">017</option>
						<option value="018">018</option>
						<option value="019">019</option>
					</select>
					-
					<input type="text" name="cellNo2" class="input" maxlength="4" style="width:10%;ime-mode:disabled" onKeyPress="onlyNumber();" />
					-
					<input type="text" name="cellNo3" class="input" maxlength="4" style="width:10%;ime-mode:disabled" onKeyPress="onlyNumber();" />
					<input type="hidden" name="cellNo" />
				</td>
			</tr>
			<tr>
				<th class="fst">촬영기간</th>
				<td colspan="3">
					<input type="text" name="rservStartDy" id="rservStartDy" readonly class="day" onclick="openCal('adminForm.rservStartDy')" style="width:10%;" value="${today}" /><a href="javascript:openCal('adminForm.rservStartDy')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
					<select name="rservStartHour" class="select">
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
					</select>
					<select name="rservStartMin" class="select">
						<option value="00">00</option>
						<option value="10">10</option>
						<option value="20">20</option>
						<option value="30">30</option>
						<option value="40">40</option>
						<option value="50">50</option>
					</select>
					~
					<select name="rservEndHour" class="select">
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						<option value="14">14</option>
						<option value="15">15</option>
						<option value="16">16</option>
						<option value="17">17</option>
					</select>
					<select name="rservEndMin" class="select">
						<option value="00">00</option>
						<option value="10">10</option>
						<option value="20">20</option>
						<option value="30">30</option>
						<option value="40">40</option>
						<option value="50">50</option>
					</select>
				</td>
			</tr>
			<tr>
				<th class="fst">내용</th>
				<td colspan="3">
					<textarea rows="10" cols="100" name="scdlMemo" maxlength="200" onkeyup="return memoLimit(this);"></textarea>
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