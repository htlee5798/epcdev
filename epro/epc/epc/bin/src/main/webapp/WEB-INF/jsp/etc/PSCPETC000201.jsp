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
function update(){
	var form = document.AdminForm;

	var url = '<c:url value="/etc/updateCodePopup.do"/>';

	if(!chkfield(form.majorCd,	form.majorCd.value,	"코드그룹ID")) return ;
	if(!chkfield(form.minorCd,	form.minorCd.value,	"MAJOR코드")) return ;

	if (!confirm("코드정보를 저장하시겠습니까?")) {
		return;
	}

 	form.action = url;
  	form.submit();
	window.close();
  	refresh();
}

function remove(){
	var form = document.AdminForm;

	var url = '<c:url value="/etc/deleteCodePopup.do"/>';

	if (!confirm("코드정보를 삭제하시겠습니까?")) {
		return;
	}

 	form.action = url;
  	form.submit();
	window.close();
  	refresh();
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

/** ********************************************************
 * 검색조건을 초기화 시킨다음 리스트를 불러온다.
 ******************************************************** */
 function refresh(){
	opener.searchForm.major_cd.value = "";
	opener.searchForm.minor_cd.value = "";
	opener.searchForm.cd_nm.value = "";
  	opener.doSearch();
}

</script>

</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="AdminForm" method="post">
  <div id="popup">
    <!--  @title  -->
     <div id="p_title1">
		<h1>코드정보상세</h1>
        <span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
     </div>
     <!--  @title  //-->
     <!--  @process  -->
     <div id="process1">
		<ul>
			<li>홈</li>
			<li>코드관리</li>
			<li class="last">코드관리</li>
		</ul>
     </div>
	 <!--  @process  //-->
	 
	 
	 <div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">코드정보</li>
				<li class="btn">
					<a href="#" class="btn" onclick="remove();"><span><spring:message code="button.common.delete"/></span></a>
					<a href="#" class="btn" onclick="update();"><span><spring:message code="button.common.update"/></span></a>
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
				<th class="fst">코드그룹ID</th>
				<td>${pscmetc0001VO.majorCd}</td>
				<th class="fst">MINOR코드</th>
				<td class="left">${pscmetc0001VO.minorCd}</td>
				<input type="hidden" name="majorCd" value="${pscmetc0001VO.majorCd}" />
				<input type="hidden" name="minorCd" value="${pscmetc0001VO.minorCd}" />
			</tr>
			<tr>
				<th class="fst">코드명</th>
				<td><input type="text" name="cdNm" value="${pscmetc0001VO.cdNm}" /></td>
				<th class="fst">설명</th>
				<td class="left"><input type="text" name="cdDesc" value="${pscmetc0001VO.cdDesc}" /></td>
			</tr>
			</table>
		</div>
		<!--  @작성양식  2//-->
	</div>
  </div>
</form>
</body>
</html>