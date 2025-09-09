
<%@ page contentType="text/html; charset=UTF-8"%>
<%--
	Page Name 	: SRMJON003002.jsp
	Description : 1차 스크리닝 적합 팝업
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
		//상담등록하기
		function func_ok() {
			parent.window.opener.doPage();
			window.close();
		}
	</script>

</head>

<body style="width:95%;height: 95%;">
	
	<div class="notify">
		<div class="symbol span_txt">
			<span class="symbol icon-info"></span>
			<spring:message code="text.srm.field.srmjon003002Notice1"/> <%--spring:message : "상품부문" 1차 스크리닝 평가에 적합한 파트너사 입니다.<br>상세 입점상담 내용을 등록 해 주세요.--%>
		</div>
	</div>
	
	<div class="div_btn">
		<input type="button" id="" name="" value="<spring:message code="button.srm.ok"/>" class="btn_normal btn_blue" onclick="func_ok();" /><%--spring:message : 확인--%>
	</div>
</div>
</body>
</html>
