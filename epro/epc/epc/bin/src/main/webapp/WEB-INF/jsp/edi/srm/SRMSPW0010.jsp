<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>

<%--
	Page Name 	: SRMSPW0010.jsp
	Description : 비밀번호 변경
	Modification Information
	
	수정일      			수정자           		수정내용
	-----------    	------------    ------------------
	2016.07.06  	SHIN SE JIN		 최초 생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><spring:message code='text.srm.field.tempPwChange1' /></title><%--비밀번호 변경  --%>

<script>
	/* 화면 초기화 */
	$(document).ready(function() {
		fnGbnChanged();
		
		/* 해외 사업자번호, 이메일 한글 입력 방지 (ime-mode:disabled 는 크롬에서 X)*/
		/* $("#MyForm input[name='irsNo4'], #MyForm input[name='vEmail']").live("blur keyup", function() {			
			  $(this).val( $(this).val().replace( /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' ) );
		}); */

		//선택
		var option = "<option value= \"\"><spring:message code='text.srm.field.select' /></option>";
		$(option).prependTo("#MyForm select[name=country]");
		$("#MyForm select[name=country]").val("");
		
		/* 엔터키 입력시 로그인 */
		$("input[type='text']").unbind().keydown(function(e) {	
			if	(e.keyCode == 13) {
				fnLoginOk();
			}
	   	});
		
	});
	
	/* 해외업체구분 변경 시 사업자등록번호 포맷 변경 */
	function fnGbnChanged() {
		
		// 기존값 설정값 초기화
		$("select[name='country']").val("");
		$("input[name='irsNo1'], input[name='irsNo2'], input[name='irsNo3'], input[name='irsNo4'], input[name='vEmail']").val("");
		$("#divPwd").hide();
		
		if ($("#MyForm input[name='shipperType']:checked").val() == "2") {
			$("#divG").show();
			$("#divD").hide();
			
		} else {
			$("#divG").hide();
			$("#divD").show();
		}
	}
	
	/* 로그인 */
	function fnLoginOk() {
		
		/* 사업자명이 입력되지 않았을 경우 */
		var target = "<spring:message code='text.srm.field.sellerNameLoc' />";
		if (!$.trim($("#MyForm input[name=sellerNameLoc]").val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");<%--사업자명을(를) 입력하세요. --%>
			$("#MyForm input[name=sellerNameLoc]").focus();
			return;
		}
		
		/* 구분별 사업자번호 체크 */
		if ($("#MyForm input[name='shipperType']:checked").val() == "1") {	// Domestic
			var irsNo1 	= $("#MyForm input[name='irsNo1']").val();
			var irsNo2 	= $("#MyForm input[name='irsNo2']").val();
			var irsNo3 	= $("#MyForm input[name='irsNo3']").val();
			var irsNo	= irsNo1 + "" + irsNo2 + "" + irsNo3;
			
			if (irsNo.length <= 0 || irsNo.length < 10) {
				alert("<spring:message code='msg.srm.alert.reqIrsNo' />");<%--사업자번호를 확인해주세요. --%>
				$("#MyForm input[name='irsNo1']").focus();
				return;
			}
			
			if (!gfnCheckBizNum(irsNo)) {	// 사업자번호 유효성 체크
				alert('<spring:message code="msg.srm.alert.notIrsNo" />');<%--유효한 사업자 번호가 아닙니다. --%>
				return;
			}
			
			$("#MyForm input[name=irsNo]").val(irsNo);
			
		} else {			// Global
			
			target = "<spring:message code='text.srm.field.country' />";
			if (!$("#MyForm select[name='country']").val()) {
				alert("<spring:message code='msg.srm.alert.select' arguments='" + target + "' />");<%--국가을(를) 선택하세요. --%>
				$("#MyForm select[name='country']").focus();
				return;
			}
			
			if (!$.trim($("#MyForm input[name='irsNo4']").val())) {
				alert("<spring:message code='msg.srm.alert.reqIrsNo' />");<%--사업자번호를 확인해주세요. --%>
				$("#MyForm input[name='irsNo4']").focus();
				return;
			}
			
			$("#MyForm input[name='irsNo']").val($("#MyForm input[name='irsNo4']").val());
			
		}
		
		/* 이메일 체크 */
		target = "<spring:message code='text.srm.field.sellerCeoEmail' />";
		if (!$.trim($('#MyForm input[name="vEmail"]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");<%--이메일을(를) 입력하세요. --%>
			$('#MyForm input[name="vEmail"]').focus();
			return;
		}
		
		var regVar = "^[a-zA-Z0-9!$&*.=^`|~#%'+\/?_{}-]+@([a-zA-Z0-9_-]+\.)+[a-zA-Z]{2,4}$"; //Email 형식 검증
		if(new RegExp(regVar).test($.trim($('#MyForm input[name="vEmail"]').val())) == false) {
			alert("<spring:message code='msg.srm.alert.validation.vEmail'/>");<%--이메일 형식이 아닙니다. --%>
			$('input[name="vEmail"]').focus();
			return;
		}
		
		//----- Argument 설정 ------------------------------
		var dataInfo = {};
		dataInfo["houseCode"] 		= $("#MyForm input[name='houseCode']").val();
		dataInfo["shipperType"] 	= $("#MyForm input[name='shipperType']:checked").val();
		dataInfo["sellerNameLoc"] 	= $("#MyForm input[name=sellerNameLoc]").val();
		dataInfo["irsNo"] 			= $("#MyForm input[name='irsNo']").val();
		dataInfo["vEmail"] 			= $("#MyForm input[name='vEmail']").val();
		
		// 국가 설정(국가선택값이 없으면 기본적으로 한국(KR) 값으로 설정)
		var country = $("#MyForm select[name='country']").val();
		if (country == "" || country == null) {
			$("#MyForm select[name='country']").val("KR");
		}
		
		dataInfo["country"] 		= $("#MyForm select[name='country']").val();	// 국가
		
		//console.log(dataInfo);
		//return;
		//----- Argument 설정 ------------------------------
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/srm/passwdChangeCheck.json"/>',
			data : JSON.stringify(dataInfo),
			success : function(data) {
				if (data.msg == "success") {
					$("#MyForm input[name='sellerCode']").val(data.resultVO.sellerCode);	// 업체코드
					
					PopupWindow();		// 비밀번호 변경 팝업창
				} else {
					alert("<spring:message code='msg.srm.alert.reqPwChangeCheck'/>");<%--일치하는 정보가 없습니다. 정보를 확인해주세요. --%>
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
	function next_BizNo3(Len, BizNo4, a) {
		if (Len == 5) {
			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.srm.alert.number'/>");<%--숫자만 입력할 수 있습니다. --%>
					a.focus();
					a.value = "";
					return;
				}
			}
			BizNo4.focus();
		}
	}
	
	/* 팝업창 */
 	function PopupWindow() {
		var cw = 720;
		var ch = 550;
		
		var sw = screen.availWidth;
		var sh = screen.availHeight;
		var px = Math.round((sw-cw)/2);
		var py = Math.round((sh-ch)/2);
		
		window.open("", "popup", "left=" + px + ",top=" + py + ",width=" + cw + ",height=" + ch + ",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=no");
		
		$("#MyForm").attr("action", "<c:url value='/edi/srm/passwdChangPopup.do'/>");
		$("#MyForm").attr("target", "popup");
		$("#MyForm").submit();
	}
	
 	/* 국가변경 이벤트 */
	function fnCountryChange(value) {
		// 국가를 한국으로 선택 시 해외업체 구분을 한국으로 변경하도록 설정
		if (value.toUpperCase() == "KR") {
			$("input[name='shipperType']").eq(0).attr("checked", true);
			fnGbnChanged();
		}
	}
</script>

</head>

<body>
	<!--Container-->
	<div id="container">
		<!-- Sub Wrap -->
		<div class="inner sub_wrap">
			<!-- 서브상단 -->
			<div class="sub_top">
				<h2 class="tit_page"><spring:message code='text.srm.field.tempPwChange1' /></h2>	<%--비밀번호 변경 --%>
				
				<p class="page_path"><%-- <a href="<c:url value="/edi/srm/SRMJONMain.do" />">HOME</a> --%>HOME <span><spring:message code='text.srm.field.tempPwChange1' /></span></p>
			</div><!-- END 서브상단 -->

			<h3 class="tit_star"><spring:message code='text.srm.field.tempPwChange1' /></h3>	<%--입점상담 신청하기 --%>

			<!-- 알림 -->
			<div class="noti_box">
				<ul class="noti_list">
					<li class="txt_l"><spring:message code='text.srm.field.srmspw0010Notice1' /></li>	<%--상담신청 시 입력한 비밀번호를 변경하실 수 있습니다. --%>
					<li><spring:message code='text.srm.field.srmspw0010Notice2' /></li>	<%--정보 확인이 안되는 경우 Help Desk(02-2145-8000) 로 문의 하시기 바랍니다. --%>
					<li><spring:message code='text.srm.field.srmspw0010Notice4' /></li> <%-- 비밀번호 초기화를 원하시는 경우 화면 상단의 FAQ의 관련 항목을 참고하셔서 메일로 문의하시기 바랍니다. --%>
				</ul>
			</div><!-- END 알림 -->

			<!-- 회색박스 -->
			<div class="input_wrap">
				<!-- 버튼포함 박스 -->
				<div class="input_box">
					<!-- 입력폼 -->
					<dl class="input_form">
				
					<form name="MyForm"  id="MyForm" method="post">
						<input type="hidden" id="houseCode"		name="houseCode" value="000" />
						<input type="hidden" id="sellerCode"	name="sellerCode" />
						<input type="hidden" id="irsNo"			name="irsNo" />
						
						<dt><spring:message code='text.srm.field.shipperType' /></dt>	<%--해외업체구분 --%>
						<dd>
							<!-- <input type="radio" id="shipperType1" name="shipperType" checked> 
							<label for="shipperType1">국내(Domestic)</label>
							<input type="radio" id="shipperType2" name="shipperType" class="ml10"> 
							<label for="shipperType2">해외(Global)</label> -->
							<srm:codeTag objId="shipperType" comType="RADIO" objName="shipperType" formName="" parentCode="M025" selectParam="1" event="onchange=\"fnGbnChanged();\"" />
						</dd>
						
						<dt><label for="sellerNameLoc"><spring:message code='text.srm.field.sellerNameLoc' /></label></dt>	<%--사업자명 --%>
						<dd>
							<input type="text" class="field_full" name="sellerNameLoc" id="sellerNameLoc">
						</dd>
						
						<dt><label for="irsNo1"><spring:message code='text.srm.field.irsNo' /></label></dt><%--사업자등록번호 --%>
						
						<dd>
							<div id="divD">
								<input type="text" class="field_num" name="irsNo1" id="irsNo1" maxlength="3" onKeyUp="next_BizNo1(this.value.length, irsNo2, this);" style="text-align: center;"> - 
								<input type="text" class="field_num" name="irsNo2" id="irsNo2" maxlength="2" onKeyUp="next_BizNo2(this.value.length, irsNo3, this);" style="text-align: center;"> - 
								<input type="text" class="field_num" name="irsNo3" id="irsNo3" maxlength="5" onKeyUp="next_BizNo3(this.value.length, irsNo3, this);" style="text-align: center;">
							</div>
							
							<div id="divG">
								<srm:codeTag objId="country" comType="SELECT" objName="country" formName="" parentCode="M001" event="onChange=\"fnCountryChange(this.value)\"" sortStd="NAME" /><%--선택 --%>
								<input type="text" name="irsNo4" id="irsNo4" style="width: 230px; text-align: center;" />
								<br>
								<span style="color: red;"><spring:message code="text.srm.field.srmjon0020Notice4"/></span><%--spring:message 사업자번호는 앞 11자리(영문,숫자기준)만 입력하세요.--%>
							</div>
						</dd>
						
						<dt><label for="vEmail"><spring:message code='text.srm.field.sellerCeoEmail' /></label></dt><%--이메일 --%>
						<dd><input type="text" id="vEmail" name="vEmail" class="field_full"/> </dd>
						
					</dl><!-- END 입력폼 -->
					
					<!-- 확인 -->
					<button type="button" class="plain btn_ok" onclick="fnLoginOk();"><spring:message code='button.srm.ok' /></button>	<%-- 확인 --%>
					<!-- END 확인 -->
					</form>
				</div><!-- 버튼포함 박스 -->
			</div><!-- END 회색박스 -->

		</div><!-- END Sub Wrap -->
	</div><!--END Container-->
</body>
</html>