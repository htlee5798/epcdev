<%@ page contentType="text/html; charset=UTF-8"%>
<%--
	Page Name 	: SRMJON003001.jsp
	Description : 1차 스크리닝 부적합 팝업
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.08 		LEE HYOUNG TAK 	최초생성
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<%@include file="../common.jsp" %>

	<title>message</title>
	<link href='/css/epc/edi/srm/srm.css' type="text/css" rel="stylesheet">

	<script>
	//확인
	function func_ok() {
		window.close();
	}
	</script>

</head>

<body style="width:95%;height: 95%;">
	
	<div class="notify">
		<div class="symbol span_txt">
			<span class="symbol icon-error"></span>
			<spring:message code="text.srm.field.srmjon003001Notice1"/><%--spring:message : 해당 상담 분류 "상품부문" 1차 스크리닝 평가에 부적합 합니다.--%>
		</div>
	</div>
	
	<div class="div_btn">
		<input type="button" id="" name="" value="<spring:message code="button.srm.ok"/>" class="btn_normal btn_blue" onclick="func_ok();" /><%--spring:message : 확인--%>
	</div>
</div>
</body>
</html>
 