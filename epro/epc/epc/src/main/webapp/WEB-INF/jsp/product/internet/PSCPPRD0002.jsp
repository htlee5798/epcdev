<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>
<%
     String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Cache-Control"   content="No-Cache"     >
<meta http-equiv="Pragma"          content="No-Cache"     >
<title>상품정보프레임</title>
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />
<script>
$(document).ready(function(){
	var H1 = 0;
	var H2 = 0;
	$("iframe[name='prdInfoFrame']").load( function(){
		if( $("iframe[name='prdInfoFrame']").attr("src") != ""){
			$("iframe[name='prdInfoFrame']").css("height", "300"  );

			H1= $("iframe[name='prdInfoFrame']").contents().height();

			$("iframe[name='prdInfoFrame']").css("height", H1  );
		}
	});
	$("iframe[name='prdDetailFrame']").load( function(){
		$("iframe[name='prdDetailFrame']").css("height",  "300" );
		
		H2= $("iframe[name='prdDetailFrame']").contents().height();

		$("iframe[name='prdDetailFrame']").css("height",  H2 );
		$("#mainDivBox").css("height",  Number(H1)+Number(H2)+Number(120) );
	});

});

function testTop() {
	alert("WOW Firends!!!");
}
</script>
</head>
<!-- 
<frameset rows="370, *" border="0">
	<frame src="<c:url value="/product/selectProductInfo.do"/>?prodCd=<%=prodCd %>" name="prdInfoFrame" target="_parent">
	<frame src="" name="prdDetailFrame" target="_parent">
</frameset>
 -->
<body>
<div id="mainDivBox">
	<ul>
		<li><iframe style="width:1000px; border:0;" src="<c:url value="/product/selectProductInfo.do"/>?prodCd=<%=prodCd %>" name="prdInfoFrame" id="prdInfoFrame" target="_parent" scrolling="no"></iframe></li>
	</ul>
	<ul>
		<li><iframe style="width:1000px; border:0;" src="" name="prdDetailFrame" id="prdDetailFrame" target="_parent" scrolling="no"></iframe></li>
	</ul>
</div>
</body>

</html>