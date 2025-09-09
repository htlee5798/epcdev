<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.lottemart.epc.common.model.EpcLoginVO"%>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
	EpcLoginVO epcLoginVO = (EpcLoginVO)request.getAttribute("epcLoginVO");
	boolean isLogin = epcLoginVO == null ? false : true;
%>
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
function update(){
	var form = document.adminForm;

	var url = '<c:url value="/product/updateSchedulePopup.do"/>';

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

/** ********************************************************
 * 삭제 처리 함수
 ******************************************************** */
function remove(){
	var form = document.adminForm;

	var url = '<c:url value="/product/deleteSchedulePopup.do"/>';

	if (!confirm("촬영스케쥴정보를 삭제하시겠습니까?")) {
		return;
	}

 	form.action = url;
  	form.submit();
	//window.close();
  	//refresh();
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

/** ********************************************************
 * 검색조건을 초기화 시킨다음 리스트를 불러온다.
 ******************************************************** */
function refresh(){
  	opener.doSearch();
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
</script>

</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="adminForm" method="post">
 <div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>상품이미지촬영스케쥴 상세</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>상품관리</li>
			<li class="last">상품이미지촬영스케쥴 상세</li>
		</ul>
     </div>
	 <!--  @process  //-->
	 
	 	 <div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">상품이미지촬영스케쥴 상세</li>
				<li class="btn">
					<%if(isLogin){%>
						<c:forEach items="${epcLoginVO.vendorId}" var="venArr">
						<c:if test="${venArr == pscmprd0011VO.vendorId}">
						<a href="#" class="btn" onclick="remove();"><span><spring:message code="button.common.delete"/></span></a>
						<a href="#" class="btn" onclick="update();"><span><spring:message code="button.common.save"/></span></a>
						</c:if>
						</c:forEach>
					<%}%>
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
				<th class="fst">업체명</th>
				<td>
					<input type="hidden" name="scdlSeqs" value="${pscmprd0011VO.scdlSeqs}" />
					<input type="hidden" name="vendorId" value="${pscmprd0011VO.vendorId}" />
					${pscmprd0011VO.vendorNm}
				</td>
				<th class="fst">휴대전화</th>
				<td class="left">
					<select name="cellNo1" class="select" style="width:15%;" >
						<option value="010" <c:if test="${fn:trim(fn:substring(pscmprd0011VO.cellNo,0,4)) == '010'}">selected</c:if>>010</option>
						<option value="011" <c:if test="${fn:trim(fn:substring(pscmprd0011VO.cellNo,0,4)) == '011'}">selected</c:if>>011</option>
						<option value="016" <c:if test="${fn:trim(fn:substring(pscmprd0011VO.cellNo,0,4)) == '016'}">selected</c:if>>016</option>
						<option value="017" <c:if test="${fn:trim(fn:substring(pscmprd0011VO.cellNo,0,4)) == '017'}">selected</c:if>>017</option>
						<option value="018" <c:if test="${fn:trim(fn:substring(pscmprd0011VO.cellNo,0,4)) == '018'}">selected</c:if>>018</option>
						<option value="019" <c:if test="${fn:trim(fn:substring(pscmprd0011VO.cellNo,0,4)) == '019'}">selected</c:if>>019</option>
					</select>
					-
					<input type="text" name="cellNo2" class="input" value="${fn:trim(fn:substring(pscmprd0011VO.cellNo,4,8))}" maxlength="4" style="width:10%;ime-mode:disabled" onKeyPress="onlyNumber();" />
					-
					<input type="text" name="cellNo3" class="input" value="${fn:trim(fn:substring(pscmprd0011VO.cellNo,8,12))}" maxlength="4" style="width:10%;ime-mode:disabled" onKeyPress="onlyNumber();" />
					<input type="hidden" name="cellNo" />
				</td>
			</tr>
			<tr>
				<th class="fst">촬영기간</th>
				<td colspan="3">
					<input type="text" name="rservStartDy" id="rservStartDy" readonly class="day" onclick="openCal('adminForm.rservStartDy')" style="width:10%;" value="${pscmprd0011VO.rservStartDy}" /><a href="javascript:openCal('adminForm.rservStartDy')"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" /></a>
					<select name="rservStartHour" class="select">
						<option value="09" <c:if test="${pscmprd0011VO.rservStartHour == '09'}">selected</c:if>>09</option>
						<option value="10" <c:if test="${pscmprd0011VO.rservStartHour == '10'}">selected</c:if>>10</option>
						<option value="11" <c:if test="${pscmprd0011VO.rservStartHour == '11'}">selected</c:if>>11</option>
						<option value="12" <c:if test="${pscmprd0011VO.rservStartHour == '12'}">selected</c:if>>12</option>
						<option value="13" <c:if test="${pscmprd0011VO.rservStartHour == '13'}">selected</c:if>>13</option>
						<option value="14" <c:if test="${pscmprd0011VO.rservStartHour == '14'}">selected</c:if>>14</option>
						<option value="15" <c:if test="${pscmprd0011VO.rservStartHour == '15'}">selected</c:if>>15</option>
						<option value="16" <c:if test="${pscmprd0011VO.rservStartHour == '16'}">selected</c:if>>16</option>
						<option value="17" <c:if test="${pscmprd0011VO.rservStartHour == '17'}">selected</c:if>>17</option>
					</select>
					<select name="rservStartMin" class="select">
						<option value="00" <c:if test="${pscmprd0011VO.rservStartMin == '00'}">selected</c:if>>00</option>
						<option value="10" <c:if test="${pscmprd0011VO.rservStartMin == '10'}">selected</c:if>>10</option>
						<option value="20" <c:if test="${pscmprd0011VO.rservStartMin == '20'}">selected</c:if>>20</option>
						<option value="30" <c:if test="${pscmprd0011VO.rservStartMin == '30'}">selected</c:if>>30</option>
						<option value="40" <c:if test="${pscmprd0011VO.rservStartMin == '40'}">selected</c:if>>40</option>
						<option value="50" <c:if test="${pscmprd0011VO.rservStartMin == '50'}">selected</c:if>>50</option>
					</select>
					~
					<select name="rservEndHour" class="select">
						<option value="09" <c:if test="${pscmprd0011VO.rservEndHour == '09'}">selected</c:if>>09</option>
						<option value="10" <c:if test="${pscmprd0011VO.rservEndHour == '10'}">selected</c:if>>10</option>
						<option value="11" <c:if test="${pscmprd0011VO.rservEndHour == '11'}">selected</c:if>>11</option>
						<option value="12" <c:if test="${pscmprd0011VO.rservEndHour == '12'}">selected</c:if>>12</option>
						<option value="13" <c:if test="${pscmprd0011VO.rservEndHour == '13'}">selected</c:if>>13</option>
						<option value="14" <c:if test="${pscmprd0011VO.rservEndHour == '14'}">selected</c:if>>14</option>
						<option value="15" <c:if test="${pscmprd0011VO.rservEndHour == '15'}">selected</c:if>>15</option>
						<option value="16" <c:if test="${pscmprd0011VO.rservEndHour == '16'}">selected</c:if>>16</option>
						<option value="17" <c:if test="${pscmprd0011VO.rservEndHour == '17'}">selected</c:if>>17</option>
					</select>
					<select name="rservEndMin" class="select">
						<option value="00" <c:if test="${pscmprd0011VO.rservEndMin == '00'}">selected</c:if>>00</option>
						<option value="10" <c:if test="${pscmprd0011VO.rservEndMin == '10'}">selected</c:if>>10</option>
						<option value="20" <c:if test="${pscmprd0011VO.rservEndMin == '20'}">selected</c:if>>20</option>
						<option value="30" <c:if test="${pscmprd0011VO.rservEndMin == '30'}">selected</c:if>>30</option>
						<option value="40" <c:if test="${pscmprd0011VO.rservEndMin == '40'}">selected</c:if>>40</option>
						<option value="50" <c:if test="${pscmprd0011VO.rservEndMin == '50'}">selected</c:if>>50</option>
					</select>
				</td>
			</tr>
			<tr>
				<th class="fst">내용</th>
				<td colspan="3">
					<textarea rows="10" cols="100" name="scdlMemo" maxlength="200" onkeyup="return memoLimit(this);">${pscmprd0011VO.scdlMemo}</textarea>
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