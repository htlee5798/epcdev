<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib 	prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="squot">'</c:set>
<c:set var="dquot">"</c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LOTTE MART Back Office System</title>

<!-- CSS URL -->
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.staticssl.path')}/css/style_1024.css" ></link>

<!-- JS URL -->
<script type="text/javascript" src="/js/epc/Ui_common.js" ></script>
<script type="text/javascript" src="/js/epc/common.js" ></script>

<script type="text/javascript" >
/** ********************************************************
 * 조회 처리 함수
 ******************************************************** */
 function doSearch() {
	goPage('1');
}

// 협력사명,협력업체코드 텍스트 필드에서 엔터키 처리
function enterKey() {
	if (event.keyCode == 13) {
		goPage('1');
	}
};


/** ********************************************************
 * 페이징 처리 함수
 ******************************************************** */
function goPage(pageIndex){
	var form = document.vendorPopUp;

	var url = '<c:url value="/common/selectSSOPartnerFirmsPopupSSL.do"/>';
	form.pageIndex.value = pageIndex;
	form.action = url;
	form.submit();
}


// 클릭한 것을 지정된 곳에 넣기
function popupPushValue(vendorId, vendorName)
{
	opener.setVendorInto(vendorId, vendorName);
	top.close();
}

function popupPushValue2(vendorId, vendorName, cono)
{
	opener.setVendorInto(vendorId, vendorName, cono);
	top.close();
}


</script>

</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
	<form name="vendorPopUp" id="vendorPopUp" method="post">
	<input type="hidden" name="gubn" value="<c:out value='${searchVO.gubn}' />"/>
	<input type="hidden" name="loginId" value="<c:out value='${searchVO.loginId}' />"/>
	<input type="hidden" name="pwd" value="<c:out value='${searchVO.pwd}' />"/>
		<div id="popup">
		
			<div id="p_title1">
				<h1>협력사정보</h1>
				<span class="logo"><img src="${lfn:getString('system.cdn.staticssl.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
			</div>

			<br/>	
		
			<div class="popup_contents">
				<!-- 1검색조건 -->
				<div class="bbs_search3">
					<ul class="tit">
						<li class="tit">조회조건</li>
						<li class="btn">
							<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.inquire"/></span></a>
						</li>
					</ul>
					<table class="bbs_grid2" cellspacing="0" border="0">
						<colgroup>
							<col width="10%">
							<col width="20%">
							<col width="10%">
							<col width="20%">
							<col width="10%">
							<col width="30%">
						</colgroup>
						<tr>
							<th><span class="star"></span>사업자번호</th>
							<td><input type="text" id="cono" name="cono" value="<c:out value='${searchVO.cono }' />" onKeyPress="enterKey();" maxlength="20" style='ime-mode:disabled'></td>
							<th><span class="star"></span>협력사코드</th>
							<td><input type="text" id="vendorId" name="vendorId" value="<c:out value='${searchVO.vendorId }' />" onKeyPress="enterKey();" maxlength="20" style='ime-mode:disabled'></td>
							<th><span class="star"></span>협력사명</th>
							<td><input type="text" id="vendorNm" name="vendorNm" value="<c:out value='${searchVO.vendorNm }' />" onKeyPress="enterKey();" maxlength="100"></td>
						</tr>
					</table>
				</div>
				<!-- 	1검색조건 // -->
				
				<br/>
				
				<!-- 	2검색내역 -->
				<div class="wrap_con">
					<div class="bbs_search3">
					
						<ul class="tit">
							<li class="tit">조회내역</li>
						</ul>						
					
						<table class="bbs_search3" cellpadding="0" cellspacing="0" border="0" width="100%">
						<colgroup>
							<col style="width:25%" />
							<col style="width:25%" />
							<col style="width:25%" />
							<col style="width:25%" />
						</colgroup>
							<tr>
								<th>협력업체번호</th>
								<th>협력업체명</th>
								<th>사업자등록번호</th>
								<th>업체유형</th>
							</tr>
			<c:choose>								
				<c:when test="${not empty list}">							
					<c:forEach var="list" items="${list}">
						<c:set var="VENDOR_NM" value="${fn:replace(fn:replace(list.VENDOR_NM, squot, '’'), dquot, '’')}" />
							<tr>
								<td align="center"><c:out value="${list.VENDOR_ID}" /></td>
						<c:choose>
							<c:when test="${searchVO.gubn eq 'CONO' }">
							    <td align="center"><a href="javascript: popupPushValue2('<c:out value="${list.VENDOR_ID}"/>','<c:out value="${VENDOR_NM}"/>','<c:out value="${list.PARAM_CONO}"/>');"><c:out value="${list.VENDOR_NM}" /></a></td>
							</c:when>
							<c:otherwise>
								<td align="center"><a href="javascript: popupPushValue('<c:out value="${list.VENDOR_ID}"/>','<c:out value="${list.VENDOR_NM}"/>');"><c:out value="${list.VENDOR_NM}" /></a></td>
							</c:otherwise>
						</c:choose>
						
								<td align="center"><c:out value="${list.CONO}" /></td>
								<td align="center"><c:out value="${list.VENDOR_TYPE_CD}" /></td>
							</tr>
					</c:forEach>	
				</c:when>					
				<c:otherwise>	
					<tr class="r1">
						<td colspan="4"><spring:message code="msg.common.info.nodata"/></td>
					</tr>
				</c:otherwise>
			</c:choose>
						</table>
					</div>
				<!--  2검색내역 // -->
				<div id="paging">
					<ui:pagination paginationInfo = "${paginationInfo}" type="image" jsFunction="goPage" />
				</div>				
				
				<!--  3검색결과수 및 페이지 -->
				<input type="hidden" name="pageIndex" />
				<!--  3검색결과수 및 페이지 // -->
				</div>
			</div>
		</div>
	</form>

</body>


</html>