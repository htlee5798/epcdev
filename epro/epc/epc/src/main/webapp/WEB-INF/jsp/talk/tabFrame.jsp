<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- PBOMRPT0090 -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<!-- 공통 css 및 js 파일 -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="title.epc"/></title>
<link rel="stylesheet" href="/css/epc/edi/style_1024.css" />
<script type="text/javascript" src="/js/jquery/jquery-1.5.2.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.form.js"></script>
<script type="text/javascript" src="/js/jquery/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.validate.1.8.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.metadata.js"></script>
<script type="text/javascript" src="/js/jquery/jquery.custom.js"></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridTag.js" ></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridExtension.js" ></script>
<script type="text/javascript" src="/js/wisegrid/WiseGridConfig.js" ></script>
<script type="text/javascript" src="/js/epc/Ui_common.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<script type="text/javascript" src="/js/epc/paging.js" ></script>
<script type="text/javascript" src="/js/epc/validate.js" ></script>
<script type="text/javascript" src="/js/epc/member.js" ></script>
<script type="text/javascript" src="/js/common/utils.js" ></script>

<script type="text/javascript">

//jQeury 초기화
$(document).ready(function(){
	
	//default는 주문별Tab
// 	changeTab(1);
	
}); // end of ready

function changeTab(val){
	
	//----------------------------
	// 회원정보 탭
	//----------------------------
	if(val == 1){
		$("#memberTab").addClass("on");
		$("#orderTab").removeClass("on");
		
		var memberNo = $('#memberNo').val();
		
		document.dataForm.target = "tabframe";
		document.dataForm.action = '<c:url value="/talk/memberTab.do"/>?memberNo='+memberNo;
		document.dataForm.submit();
	}
	//----------------------------
	// 주문정보 탭
	//----------------------------
	else if(val ==2){
		$("#memberTab").removeClass("on");
		$("#orderTab").addClass("on");
		
		var memberNo = $('#memberNo').val();
		
		document.dataForm.target = "tabframe";
		document.dataForm.action = '<c:url value="/talk/orderTab.do"/>?memberNo='+memberNo;
		document.dataForm.submit();
	}
}
</script>

</head>

<body>

<form id="dataForm" name="dataForm">
<input type="hidden" id="memberNo" name="memberNo" value="${memberNo}"/>
<div id="content_wrap" style="height:776px">

	<!-- -------------------------------------------------------- -->
	<!--	Tab 	-->
	<!-- -------------------------------------------------------- -->
	<div class="tab" style="margin:2px 2px 2px 2px">
		<ul>
			<li id="memberTab"  	class="on"> <a href="#" onClick="changeTab(1);">회원정보</a></li>
			<li id="orderTab"  class="" > <a href="#" onClick="changeTab(2);">주문내역</a></li>
		</ul>
	</div>
	<!-- --------------------------------------------- end of Tab -->
	
	<iframe src="<c:url value="/talk/memberTab.do?memberNo=${memberNo}"/>" name="tabframe" frameborder="0" width="104%" height="100%" scrolling="NO"></iframe>
</div>
<div id="wrap_menu"></div>
</form>

</body>
</html>