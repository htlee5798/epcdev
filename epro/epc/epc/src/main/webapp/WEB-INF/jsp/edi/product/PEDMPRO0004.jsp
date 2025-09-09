<%@include file="../common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>

<script>
	
	function doSearch() {
		
		
		var form = document.forms[0];
		
		if(!dateValid(form)) return;

		loadingMaskFixPos();
		form.action  = '${ctx}/edi/product/PEDMPRO0005.do';
		form.submit();	
	}



	function fnOnlyNumber(){
		if(event.keyCode < 48 || event.keyCode > 57)
		event.keyCode = null;
	}


	function dateValid(form){

		var startDate = form.startDate.value;
		var endDate = form.endDate.value;
		var rangeDate = 0;
	
		if(startDate == "" || endDate == ""  ){
			alert("<spring:message code='msg.common.fail.nocalendar'/>");
			form.startDate.focus();
			return false;
		}
		
		// startDate, endDate 는 yyyy-mm-dd 형식

	    startDate = startDate.substring(0, 4) + startDate.substring(5, 7) + startDate.substring(8, 10);
	    endDate = endDate.substring(0, 4) + endDate.substring(5, 7) + endDate.substring(8, 10);

	   var intStartDate = parseInt(startDate);
	   var intEndDate = parseInt(endDate);
			
	    if (intStartDate > intEndDate) {
	        alert("<spring:message code='msg.common.fail.calendar'/>");
	        form.startDate.focus();
	        return false;
	    }
	   
		return true;
	}

	
	function excepDown() {
			
	}
	
	function helpWin(state) {
		if(state) document.getElementById("ViewLayer").style.display = "";
		else document.getElementById("ViewLayer").style.display = "none";
	}

	function openProductInfoPopup() {
		window.open("${ctx}/edi/product/PEDMPRO000401.do");
	}

	function openColorSizePopup() {
		window.open("${ctx}/edi/product/PEDMPRO000402.do");
	}
	
</script>

</head>

<body>
	<div id="content_wrap">
	<div>
		<div id="wrap_menu">
			<a href="javascript:openProductInfoPopup();">
				상품정보 등록
			</a><br/>
			<a href="javascript:openColorSizePopup();">
				색상 사이즈 등록
			</a>

		</div>
	</div>


	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>상품정보</li>
					<li>상품일괄등록</li>
					<li class="last">상품일괄등록 안내</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>


</body>
</html>
