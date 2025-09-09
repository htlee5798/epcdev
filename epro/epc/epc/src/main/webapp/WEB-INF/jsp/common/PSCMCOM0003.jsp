<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.*"%>
<%@ taglib 	prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="ui" uri="http://lcnjf.lcn.co.kr/taglib/paging"%>
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



</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
	<form name="vendorPopUp" id="vendorPopUp" method="post">

		<div id="popup">
		
			<div id="p_title1">
				<h1>택배사 코드정보</h1>
				<span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
			</div>

			<br/>	
		
			<div class="popup_contents">
				
				<!-- 	2검색내역 -->
				<div class="wrap_con">
					<div class="bbs_search3">
					
						<ul class="tit">
							<li class="tit">조회내역</li>
						</ul>						
					
						<table class="bbs_search3" cellpadding="0" cellspacing="0" border="0" width="100%">
						<colgroup>
							<col style="width:50%" />
							<col style="width:50%" />
						</colgroup>
							<tr>
								<th>택배사명</th>
								<th>택배사코드</th>
							</tr>
			<c:choose>								
				<c:when test="${not empty list}">							
					<c:forEach var="list" items="${list}">
							<tr>
								<td align="center"><c:out value="${list.CD_NM}" /></td>
								<td align="center"><c:out value="${list.MINOR_CD}" /></td>
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
				</div>
			</div>
		</div>
	</form>

</body>


</html>