<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>LOTTE MART EPC System</title>
	<link rel="stylesheet" href="/css/style.css" />
	<script type="text/javascript" src="/js/jquery/jquery-1.6.1.js"></script>
	<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
</head>
<script type="text/javascript">
// 20190530 전산정보팀 이상구 추가.
// edi 최초 접속 시 센터입고거부상품내역 팝업창을 호출하여 보여준다. 

<%--
	window.open("/epc/edi/buy/NEDMBUY0112.do","_blank",'top=300, left=300, width=400, height=250, toolbar=no, status=yes, scrollbars=yse');
--%>
$.ajax({
	contentType : 'application/json; charset=utf-8',
	type : 'get',
	dataType : 'json',
	url : '<c:url value="/edi/product/NEDMPRO018003.do"/>',
	success : function(result) {
		if(result.countNotValidPbProdReport>0) {
			window.open("/epc/edi/product/NEDMPRO018002.do","_blank",'top=300, left=300, width=400, height=250, toolbar=no, status=yes, scrollbars=yse');
		} 
	}
})
</script>
<frameset rows="69px,*" frameborder="0" framespacing="0">
	<frame src="<c:url value="/edi/main/viewEdiTop.do"/>" name="gnbFram"  border="0" marginheight="0" marginwidth="0" scrolling="no">
	<frameset cols="190px,*" id="widthFram" name="widthFram" frameborder="0" framespacing="0">
		<frame src="<c:url value="/edi/main/viewEdiLeft.do"/>?pgm_code=${paramMap.pgm_code}&pgm_sub=${paramMap.pgm_sub}" id="lnbFram" name="lnbFram"  border="0" marginheight="0" marginwidth="0" scrolling="no">
		<c:choose>
			<c:when test="${empty paramMap.defPgmID }">
				<frame src="<c:url value=""/>" id="contentFram" name="contentFram"  border="0" marginheight="0" marginwidth="0" scrolling="yes">
			</c:when>
		<c:otherwise>
				<frame src="<c:url value="${paramMap.defPgmID }"/>" id="contentFram" name="contentFram"  border="0" marginheight="0" marginwidth="0" scrolling="yes">
		</c:otherwise>	
		</c:choose>
		</frame>
	</frameset>
</frameset>

<body>  
</body>
</html>