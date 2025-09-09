<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>

<%--
	Page Name 	: SRMEVL0010.jsp
	Description : 품질경영평가 로그인
	Modification Information
	
	수정일      			수정자           		수정내용
	-----------    	------------    ------------------
	2016.07.08  	SHIN SE JIN		 최초 생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><spring:message code="text.srm.field.srmevl0010.title1" /></title><%--품질경영평가 로그인 --%>

<script>

	$(document).ready(function() {
		if (document.location.protocol == 'http:') {
			if ('<c:out value="${serverType}" />' == "prd") {
				document.location.href = document.location.href.replace('http:', 'https:');
			}
		}
		/* 엔터키 입력시 로그인 */
		$("input[type='text'], input[type='password']").unbind().keydown(function(e) {	
			if	(e.keyCode == 13) {
				fnLoginOk();
			}
	   	});
		
	});

	/* 품질경영평가 로그인 */
	function fnLoginOk() {
		
		var irsNo1 = $("input[name='irsNo1']").val();
		var irsNo2 = $("input[name='irsNo2']").val();
		var irsNo3 = $("input[name='irsNo3']").val();
		var irsNo = irsNo1 + "" + irsNo2 + "" + irsNo3;
		
		if (irsNo.length <= 0 || irsNo.length < 10) {
			alert("<spring:message code='msg.srm.alert.reqIrsNo' />");<%--사업자번호를 확인해주세요. --%>
			$("input[name='irsNo1']").focus();
			return;
		}
		
		if (!gfnCheckBizNum(irsNo)) {	// 사업자번호 유효성 체크
			alert('<spring:message code="msg.srm.alert.notIrsNo" />');<%--유효한 사업자 번호가 아닙니다. --%>
			return;
		}
		
		if (!$.trim($("input[name='tempPw']").val())) {
			var target = "<spring:message code='text.srm.field.tempPw' />";
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");<%--비밀번호을(를) 입력하세요. --%>
			$("input[name='tempPw']").focus();
			return;
		}
		
		$("input[name='irsNo']").val(irsNo);
		
		var dataInfo = {};
		
		dataInfo["houseCode"] 	= $("input[name='houseCode']").val();		//하우스코드
		dataInfo["sellerCode"] 	= $("input[name='irsNo']").val();			//사업자등록번호
		dataInfo["password"] 	= $("input[name='tempPw']").val();			//비밀번호
		
		//console.log(dataInfo);
		//return;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/evl/SRMEVLLogin.json"/>',
			data : JSON.stringify(dataInfo),
			success : function(data) {
				if (data.msg =="NOT_DATA") {
					alert("<spring:message code='msg.srm.alert.reqPwChangeCheck' />");<%--일치하는 정보가 없습니다. 정보를 확인해주세요. --%>
					
				} else if (data.msg == "OK") {
					location.href = "<c:url value='/edi/evl/SRMEVL0030.do' />";
					
				}else if (data.msg == "NOT_PASSWORD") {
					alert("<spring:message code='msg.srm.alert.reqPwChangeCheck' />");<%--일치하는 정보가 없습니다. 정보를 확인해주세요. --%>
					
				}else if (data.msg == "PASSCHECK_OUT") {
					alert("<spring:message code='msg.srm.alert.passcheckOut' />");<%--해당 계정은 로그인 실패 횟수 초과로 로그인이 불가합니다. --%>
				}
			}
		});
	}
	
	/* 사업자번호 첫째칸검사 */
	function next_BizNo1(Len, BizNo2, a) {
		if (Len == 3) {
			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.srm.alert.number'/>");<%--숫자만 입력할 수 있습니다. --%>
					a.focus();
					a.value = "";
					return;
				}
			}
			BizNo2.focus();
		}
	}
	
	/* 사업자번호 두번째칸검사 */
	function next_BizNo2(Len, BizNo3, a) {
		if (Len == 2) {
			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.srm.alert.number'/>");<%--숫자만 입력할 수 있습니다. --%>
					a.focus();
					a.value = "";
					return;
				}
			}
			BizNo3.focus();
		}
	}

	/* 사업자번호 마지막칸검사 */
	function next_BizNo3(Len, BizNo3, a) {
		if (Len == 5) {
			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.srm.alert.number'/>");<%--숫자만 입력할 수 있습니다. --%>
					a.focus();
					a.value = "";
					return;
				}
			}
			BizNo3.focus();
		}
	}
	
</script>

</head>

<body>
	<form name="MyForm" id="MyForm" method="post">
	<input type="hidden" id="houseCode" name="houseCode" value="000"> 
	<input type="hidden" id="irsNo" name="irsNo">
	
		<!--Container-->
		<div id="container">
			<!-- Sub Wrap -->
			<div class="inner sub_wrap">
				<!-- 서브상단 -->
				<div class="sub_top">
					<h2 class="tit_page"><spring:message code="text.srm.field.srmevl0010.title1" /></h2>	<%-- 품질경영평가 로그인 --%>
				</div><!-- END 서브상단 -->
	
				<!-- 알림 -->
				<div class="noti_box">
					<ul class="noti_list">
						<li class="txt_l"><spring:message code="text.srm.field.srmevl0010Notice2" /></li>	<%-- 평가자 등록은 각 평가사별 관리 담당자에게 요청 하시기 바랍니다. --%>
						<li class="txt_l"><spring:message code="text.srm.field.srmevl0010Notice3" /></li>	<%--관리 담당자가 없는 경우 Help Desk(02-2145-8000)로 문의하시기 바랍니다.--%>
					</ul>
				</div><!-- END 알림 -->
				
	
				<!-- 회색박스 -->
				<div class="input_wrap">
					<!-- 버튼포함 박스 -->
					<div class="input_box">
						<!-- 입력폼 -->
						<dl class="input_form">
							<dt><label for="irsNo1"><spring:message code='text.srm.field.irsNo' /></label></dt>	<%--사업자등록번호 --%>
							<dd>
								<input type="text" class="field_num" name="irsNo1" id="irsNo1" maxlength="3" onKeyUp="next_BizNo1(this.value.length, irsNo2, this);" style="text-align: center;"> - 
								<input type="text" class="field_num" name="irsNo2" id="irsNo2" maxlength="2" onKeyUp="next_BizNo2(this.value.length, irsNo3, this);" style="text-align: center;"> - 
								<input type="text" class="field_num" name="irsNo3" id="irsNo3" maxlength="5" onKeyUp="next_BizNo3(this.value.length, irsNo3, this);" style="text-align: center;">
							</dd>
							
							<dt><label for="tempPw"><spring:message code='text.srm.field.tempPw1' /></label></dt>	<%-- 비밀번호 --%>
							<dd><input type="password" class="field_full" name="tempPw" id="tempPw" ></dd>
							
						</dl><!-- END 입력폼 -->
						
						<!-- 확인 -->
						<button type="button" class="plain btn_ok" onclick="fnLoginOk();"><spring:message code='button.srm.ok' /></button>	<%-- 확인 --%>
						<!-- END 확인 -->
	
					</div><!-- 버튼포함 박스 -->
				</div><!-- END 회색박스 -->
	
			</div><!-- END Sub Wrap -->
		</div><!--END Container-->
	
	</form>
</body>
</html>