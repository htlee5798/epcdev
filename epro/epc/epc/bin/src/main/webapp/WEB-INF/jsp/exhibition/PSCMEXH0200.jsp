<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- PSCMEXH0200 -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script type="text/javascript">

//jQeury 초기화
$(document).ready(function(){
	
	//default는 주문별Tab
// 	changeTab(1);
	// 기획전 관리에서 검색을 누르면 처리
	if( $('#gubun').val() == "U") {
		
		$('#categoryId').val($('#mdiCategoryId').val());
		$('#mkdpSeq').val($('#mdiMkdpSeq').val());
		$('#mdiMkdpSeq').val($('#mdiMkdpSeq').val());
		$('#viewMkdpSeq').val($('#mdiMkdpSeq').val());
		$('#vendorIdView').val($('#mdiVendorId').val());
		$('#copyYn').val($('#copyYnn').val());
		
		changeTab1("1");
	}else{
		document.dataForm.action = '<c:url value="/exhibition/showExhibitionForm.do"/>';
		document.dataForm.target = "tabframe";
		document.dataForm.submit();
	}
	
	
}); // end of ready

function changeTab1(val){

	var xUrl = "";
	var pagegbn = "";
	
	if(val == 1){
		$("#AddTab").addClass("on");  // ${pageContext.request.contextPath}/exhibition/showExhibitionForm.do?pageDiv=update
		xUrl = '<c:url value="/exhibition/showExhibitionForm.do"/>';
		pagegbn = "update";
	}else{
		$("#AddTab").removeClass("on");
	}

$("#pageDiv").val(pagegbn);
$("#dispYn").val();
$('#copyYnn').val();

document.dataForm.action = xUrl;
document.dataForm.target = "tabframe";
document.dataForm.submit();

}

function changeTab(val){
	//----------------------------
	//  Tab
	//----------------------------
	//paramSet();
	

	var xUrl = "";
	var pagegbn = "";
	 if(val == 4){
		 if($("#mdiMkdpSeq").val() == "" ){
				if( confirm("기획전 기본정보를 등록 중입니다. 새로 등록하시겠습니까?\n확인을 누르면 새 기획전등록 화면으로 이동합니다. ")){
					document.dataForm.action = '<c:url value="/exhibition/basicExhibition.do"/>';
					document.dataForm.target = "tabframe";
					document.dataForm.submit();
					return;
				} else return;
			}
		//$("#AddTab").addClass("on");
		$('#categoryId').val($('#mdiCategoryId').val());
		$('#mkdpSeq').val($('#mdiMkdpSeq').val());
		$('#mdiMkdpSeq').val($('#mdiMkdpSeq').val());
		$('#viewMkdpSeq').val($('#mdiMkdpSeq').val());
		$('#vendorIdView').val($('#mdiVendorId').val());
		//문구변경(copyYn 값으로 Y이면 복사, 아니면 저장) - <기획전복사> 버튼을 눌러 들어온 다음 <기획전 등록> 버튼을 눌러도 계속 버튼명이 복사로 나오는 이슈 수정
		//아래에서 copyYnn 값을 copyYn 값으로 셋팅하는 부분이 있어, copyYnn 값을 빈 값으로 셋팅
		$('#copyYnn').val('');
		xUrl = '<c:url value="/exhibition/showExhibitionForm.do"/>';
	}
	if($("#viewMkdpSeq").val() == "" ){
		alert("기획전 기본정보를 먼저 등록하세요.");
		return;
	}else{
		$("#pageDiv").val("update");	
		$("#gubun").val("U");	
	} 
	
	/* if($("#copyYnn").val() == "Y") {
		alert("복사후 사용하세요.");
		return;
	}  */
	
	
    //var xUrl = "";
	//var pagegbn = "";
	
	if(val == 1){
		$("#AddTab").addClass("on");  // ${pageContext.request.contextPath}/exhibition/showExhibitionForm.do?pageDiv=update
		xUrl = '<c:url value="/exhibition/showExhibitionForm.do"/>';
		pagegbn = "update";
	}else{
		$("#AddTab").removeClass("on");
	}
	
	 
	if(val == 2){ 
		$("#DescTab").addClass("on"); 
		xUrl = '<c:url value="/exhibition/showDivnForm.do"/>';
	}else{
		$("#DescTab").removeClass("on");
	}
	
	if(val == 3){
		$("#ProdTab").addClass("on");
		xUrl = '<c:url value="/exhibition/showImageHtmlContentsForm.do"/>';
	}else{
		$("#ProdTab").removeClass("on");
	}
	
	$("#pageDiv").val(pagegbn);
	$("#dispYn").val();
	$('#copyYn').val($('#copyYnn').val());
	
	document.dataForm.action = xUrl;
	document.dataForm.target = "tabframe";
	document.dataForm.submit();
	
}

 /* function fn_showExhibition(){
   
   document.dataForm.action = '<c:url value="/exhibition/basicExhibition.do"/>';
   document.dataForm.target = "tabframe";
  document.dataForm.submit();
}  */
function paramSet(){
	var pForm = tabframe.form1;
	/*$("#prodCd").val(pForm.prodCd.value);
	$("#prodNm").val(pForm.prodNm.value);
	$("#startDate").val(pForm.startDate.value);
	$("#endDate").val(pForm.endDate.value);
	$("#statusCd").val(pForm.statusCd.value);*/
	
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
<input type="hidden" id="viewMkdpSeq" 	name="viewMkdpSeq" value=""/>
<input type="hidden" id="categoryId" name="categoryId" value="" >
<input type="hidden" id="mkdpSeq"    name="mkdpSeq"    value="" >
<input type="hidden" id="vendorIdView"    name="vendorIdView"    value="" >
<input type="hidden" id="aprvStsCdChk" name="aprvStsCdChk" value="">
<input type="hidden" id="pageDiv" name="pageDiv" value="">

<input type="hidden" id="mkdpStartDate" name="mkdpStartDate" value="">
<input type="hidden" id="mkdpStartHh" name="mkdpStartHh" value="">
<input type="hidden" id="mkdpStartMm" name="mkdpStartMm" value="">
<input type="hidden" id="mkdpEndDate" name="mkdpEndDate" value="">
<input type="hidden" id="mkdpEndHh" name="mkdpEndHh" value="">
<input type="hidden" id="mkdpEndMm" name="mkdpEndMm" value="">
<input type="hidden" id="dispYnchk" name="dispYnchk" value="">
<input type="hidden" id="copyYn" name="copyYn" value="">

<input type="hidden" id="gubun" name="gubun" value="<c:out value="${gubun }"/>">
<input type="hidden" id="mdiCategoryId" name="mdiCategoryId" value="<c:out value="${mdiCategoryId }"/>">
<input type="hidden" id="mdiMkdpSeq" name="mdiMkdpSeq" value="<c:out value="${mdiMkdpSeq }"/>">
<input type="hidden" id="mdiVendorId" name="mdiVendorId" value="<c:out value="${mdiVendorId }"/>">
<input type="hidden" id="dispYn" name="dispYn" value="<c:out value="${dispYn }"/>">
<input type="hidden" id="copyYnn" name="copyYnn" value="<c:out value="${copyYn }"/>">
<div id="content_wrap" style="height:1500px"> <!-- 기획전복사 등록 시, 수정 시 기획전 배너 쪽 화면 짤림 이슈 수정 - 기존 사이즈 776 이었음 -->

	<!-- -------------------------------------------------------- -->
	<!--	Tab 	-->
	<!-- -------------------------------------------------------- -->
	<div class="tab" style="margin:2px 2px 2px 2px">
		<ul>
<!-- 	  		<li><a href="javascript:changeTab('1');" class="step_fir_on">기획전 기본정보</a></li> -->
<!-- 	  		<li><a href="javascript:changeTab('2');" class="step_last">구분자 정보</a></li> -->
<!-- 	  		<li><a href="javascript:changeTab('3');" class="step_last">기획전 상품정보</a></li>		 -->
<!-- 	  		<li><a href="javascript:changeTab('4');" class="step_last">이미지/HTML 정보</a></li>		 -->
 			<li id="AddTab"  	 class="on"><a href="#" onClick="changeTab(1);">기획전 기본정보</a></li>
			<li id="DescTab"     class="" > <a href="#" onClick="changeTab(2);">하위상품 그룹정보</a></li>
			<li id="ProdTab"     class="" > <a href="#" onClick="changeTab(3);">기획전 상품정보</a></li>
	       	<div align="right"><a href="#" onClick="changeTab(4);" class="btn" ><span>기획전 등록</span></a></div>		
			  
<!-- 			<li id="ImageTab"   class="" > <a href="#" onClick="changeTab(4);">이미지/HTML 정보</a></li>  -->
		</ul>
	</div>
	
	<!-- --------------------------------------------- end of Tab -->
	
	<iframe  name="tabframe" frameborder="0" width="100%" height="100%" scrolling="NO"></iframe>
</div>
<div id="wrap_menu"></div>
</form>

</body>
</html>