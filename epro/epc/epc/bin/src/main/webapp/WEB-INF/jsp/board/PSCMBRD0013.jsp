<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jstl/core_rt"         %>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"   %>
<%@ taglib prefix="lfn"    uri="/WEB-INF/tlds/function.tld"               %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ui"     uri="http://lcnjf.lcn.co.kr/taglib/paging"     %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"      %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"         %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />
<!-- board/PSCMBRD0013 -->

<script type="text/javascript">

//jQeury 초기화
$(document).ready(function(){
	//	default는 changeTab(1);
}); 

function changeTab(val){
	//----------------------------
	// Tab 화면 전환
	//----------------------------
	if(val == 1){
		$("#freshTab").addClass("on");
		document.dataForm.action = '<c:url value="/board/selectFreshList.do"/>';
	}else{
		$("#freshTab").removeClass("on");
	}
	
	if(val == 2){
		$("#exprTab").addClass("on");
		document.dataForm.action = '<c:url value="/board/selectExprList.do"/>';
	}else{
		$("#exprTab").removeClass("on");
	}
	
	document.dataForm.target = "tabframe";
	document.dataForm.submit();
}

</script>
</head>

<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
	
<form id="dataForm" name="dataForm" method="post">
	<input type="hidden" id="prodCd" 	name="prodCd" value=""/>
	<input type="hidden" id="prodNm" 	name="prodNm" value=""/>
	<input type="hidden" id="startDate" name="startDate" value=""/>
	<input type="hidden" id="endDate" 	name="endDate" value=""/>
	<input type="hidden" id="statusCd" 	name="statusCd" value=""/>
	
	<div id="content_wrap" style="height:776px">
	
		<!-- -------------------------------------------------------- -->
		<!--	Tab 	-->
		<!-- -------------------------------------------------------- -->
		<div class="tab" style="margin:2px 2px 2px 2px">
			<ul>
				<li id="freshTab"  	 class="on"><a href="#" onClick="changeTab(1);">일반 상품평</a></li>
				<li id="exprTab"     class="" > <a href="#" onClick="changeTab(2);">체험형 상품평</a></li>
			</ul>
		</div>
		<!-- --------------------------------------------- end of Tab -->
		
		<iframe src="<c:url value='/board/selectFreshList.do'/>" name="tabframe" frameborder="0" width="100%" height="100%" scrolling="NO"></iframe>
	</div>
</form>

</body>
</html>