<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>LOTTE MART Back Office System</title>
	<link rel="stylesheet" href="${lfn:getString('system.cdn.static.path')}/css/style_1024.css" />
	<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
	<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
</head>
<script>
function doAction(code)
{
	var form = document.forms[0];
	var url = '<c:url value="/main/viewScmLeft.do"/>';

	
	parent.lnbFram.lnbopen();
	
	
	form.menuGb.value=code;
	form.target="lnbFram";
	form.action=url;
	form.submit();
}

function doLogout()
{
	var form = document.forms[0];
	var url = '<c:url value="/common/epcLogout.do"/>';
	form.target="_parent";
	form.action=url;
	form.submit();
}

function doEdi()
{
	var url = '<c:url value="/edi/main/viewEdiMain.do"/>';
	parent.location.href=url;
}

function doEdiOld()
{
	var url = '<c:url value="/edi/main/viewEdiMainOld.do"/>';
	parent.location.href=url;
}

function doSrm()
{
	var url = '<c:url value="/edi/main/viewSrmMain.do"/>';
	parent.location.href=url;
}

function goHome()
{
	var url = '<c:url value="/main/vendorIntro.do"/>';
	parent.location.href=url;
}

function doProdSearch()
{ 
	var url = '<c:url value="/product/selectProduct.do"/>'+'?gubun=M&asKeywordSelect='+$("#asKeywordSelect").val()+'&asKeywordValue='+$("#asKeywordValue").val();
	var menuNm = '인터넷상품관리';
	window.parent.frames["contentFram"].clickTabMenu2( url, menuNm, 'gnbSearch');
}

/** ********************************************************
 * 특수문자 입력여부 및 입력항목의 최대값을 체크한다.
 ******************************************************** */
function fnChkSpcCharByte()
{
    var str = document.topFrm.asKeywordValue.value;
    var len = 0;
    var exp = /[~!@\#$%^&*\()<>{}\[\]\-=+_'\"]/gi;

    if (str.search(exp) != -1)
    {
        alert("검색항목에는 특수문자를 사용할수 없습니다!");
        return false;
    }

    return true;
}

</script>	
<body>
	<form name="topFrm">
	<input type="hidden" name="menuGb">
	<!-- header -->
	<div id="header">
		<div class="header_box">
			<h1 class="hlogo">
			
		<!-- 20181218 EPC 상단 로고 변경 요청  -->
		<!--
		<c:choose>
			<c:when test="${epcLoginVO.happyGubn || epcLoginVO.allianceGubn}">
					<a href="#"><img width="136" height="25" src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/logo_scm.gif" alt="LOTTE MART Back Office System" /></a>
			</c:when>
			<c:otherwise>		
					<a href="#" onClick="goHome();"><img width="136" height="25" src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/logo_scm.gif" alt="LOTTE MART Back Office System" /></a>
			</c:otherwise>
		</c:choose>
		-->
		
		<c:choose>
			<c:when test="${epcLoginVO.happyGubn || epcLoginVO.allianceGubn}">
					<a href="#"><img width="136" height="25" src="/images/epc/layout/logo_scm.gif" alt="LOTTE MART Back Office System" /></a>
			</c:when>
			<c:otherwise>		
					<a href="#" onClick="goHome();"><img width="136" height="25" src="/images/epc/layout/logo_scm.gif" alt="LOTTE MART Back Office System" /></a>
			</c:otherwise>
		</c:choose>		
		
			</h1>
			<!-- 00 : util -->
			<div class="hutil">
				<ul>
		<c:choose>
			<c:when test="${epcLoginVO.happyGubn || epcLoginVO.allianceGubn}">
					<li class="on"><a href="#">SCM</a></li>
			</c:when>
			<c:otherwise>		
					<li class="on"><a href="#">SCM</a></li>
					<li><a href="#" onClick="doEdi();">EDI</a></li>
					<li><a href="#" onClick="doSrm();">SRM</a></li>	
			</c:otherwise>				
		</c:choose>
				</ul>
			</div>
			<!-- 00 : util // -->
			<div class="hname">
<c:choose>			
	<c:when test="${not empty epcLoginVO.adminId}">
		<c:choose>
			<c:when test="${epcLoginVO.happyGubn || epcLoginVO.allianceGubn}">
				<strong>${epcLoginVO.adminId}(${epcLoginVO.adminNm})</strong>님이 로그인 하셨습니다.
			</c:when>
			<c:otherwise>		
				<strong>${epcLoginVO.adminId}(${epcLoginVO.loginNm})</strong>님이 로그인 하셨습니다.
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
				<strong>${epcLoginVO.loginNm}</strong>님이 로그인 하셨습니다.
	</c:otherwise>
</c:choose>				
				<a href="#"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/header_btn_logout.gif" onClick="doLogout();" alt="로그아웃" /></a>
			</div>
			<!-- 01 : menu -->
			<div class="hmenu">
				<ul>
		<c:choose>
			<c:when test="${epcLoginVO.happyGubn}">
					<li><a href="#" onClick="doAction('2');">배송관리</a></li>
			</c:when>
			<c:when test="${epcLoginVO.allianceGubn}">
					<li><a href="#" onClick="doAction('7');">통계</a></li>
			</c:when>			
			<c:otherwise>		
					<li><a href="#" onClick="doAction('1');">상품관리</a></li>
					<li><a href="#" onClick="doAction('8');">기획전관리</a></li>
					<li><a href="#" onClick="doAction('2');">배송관리</a></li>
					<li><a href="#" onClick="doAction('3');">게시판관리</a></li>
					<li><a href="#" onClick="doAction('4');">정산관리</a></li>
					<li><a href="#" onClick="doAction('5');">주문관리</a></li>
					<li><a href="#" onClick="doAction('6');">시스템관리</a></li>
<c:if test="${not empty epcLoginVO.adminId}">
					<li><a href="#" onClick="doAction('7');">통계</a></li>
</c:if>
					<span style="float: right; margin-right: 8px; margin-top:5px;">
		                <select name="asKeywordSelect" id="asKeywordSelect" style="width:110px;">
							<option value="01">인터넷상품코드</option>
							<option value="05">기획전번호</option>
					    </select>
					    <input type="text" name="asKeywordValue" id='asKeywordValue' maxlength="100" size="28" style="vertical-align:middle;" onChange="fnChkSpcCharByte()"/> 
                        <a href="javascript:doProdSearch();" class="btn" id="search"><span><spring:message code="button.common.search"/></span></a>
		            </span>
			</c:otherwise>
		</c:choose>				
				

				</ul>
			</div>
			<!-- 01 : menu // -->
		</div>
	</div>
	
	</form>

</body>
</html>