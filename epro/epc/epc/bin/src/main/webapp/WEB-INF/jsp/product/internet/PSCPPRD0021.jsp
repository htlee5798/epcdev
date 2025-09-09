<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- PSCPPRD0021 -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script type="text/javascript">

//jQeury 초기화
$(document).ready(function(){
	
	//default는 주문별Tab
// 	changeTab(1);
	
}); // end of ready

function changeTab(val){
	//----------------------------
	// 집계 Tab
	//----------------------------
	paramSet();
	if(val == 1){
		$("#ImageTab").addClass("on");
		document.dataForm.action = '<c:url value="/product/selectProdImgChgHistForm.do"/>';
	}else{
		$("#ImageTab").removeClass("on");
	}
	
	if(val == 2){
		$("#DescTab").addClass("on");
		document.dataForm.action = '<c:url value="/product/selectProdDescChgHistForm.do"/>';
	}else{
		$("#DescTab").removeClass("on");
	}
	
	if(val == 3){
		$("#AddTab").addClass("on");
		document.dataForm.action = '<c:url value="/product/selectProdAddChgHistForm.do"/>';
	}else{
		$("#AddTab").removeClass("on");
	}
	
	if(val == 4){
		$("#prodTab").addClass("on");
		document.dataForm.action = '<c:url value="/product/selectProdMstChgHistForm.do"/>';
	}else{
		$("#prodTab").removeClass("on");
	}
	
	//20180911 상품키워드 입력 기능 추가
	if(val == 5){
		$("#KeywordTab").addClass("on");
		document.dataForm.action = '<c:url value="/product/selectProdKeywordChgHistForm.do"/>';
	}else{
		$("#KeywordTab").removeClass("on");
	}
	//20180911 상품키워드 입력 기능 추가
	
	document.dataForm.target = "tabframe";
	document.dataForm.submit();
	
}

function paramSet(){
	var pForm = tabframe.form1;
	$("#prodCd").val(pForm.prodCd.value);
	$("#prodNm").val(pForm.prodNm.value);
	$("#startDate").val(pForm.startDate.value);
	$("#endDate").val(pForm.endDate.value);
	$("#statusCd").val(pForm.statusCd.value);
	
}
</script>

</head>

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
			<li id="ImageTab"  	 class="on"><a href="#" onClick="changeTab(1);">대표이미지수정리스트</a></li>
			<li id="DescTab"     class="" > <a href="#" onClick="changeTab(2);">상세이미지수정리스트</a></li>
			<li id="AddTab"      class="" > <a href="#" onClick="changeTab(3);">전상법수정리스트</a></li>
			<li id="prodTab"     class="" > <a href="#" onClick="changeTab(4);">상품승인반려리스트</a></li>
			
			<!-- 20180911 상품키워드 입력 기능 추가 -->
			<li id="KeywordTab"     class="" > <a href="#" onClick="changeTab(5);">상품키워드수정리스트</a></li>
			<!-- 20180911 상품키워드 입력 기능 추가 -->
			
		</ul>
	</div>
	<!-- --------------------------------------------- end of Tab -->
	
	<iframe src="<c:url value="/product/selectProdImgChgHistForm.do"/>" name="tabframe" frameborder="0" width="100%" height="100%" scrolling="NO"></iframe>
</div>
<div id="wrap_menu"></div>
</form>

</body>
</html>