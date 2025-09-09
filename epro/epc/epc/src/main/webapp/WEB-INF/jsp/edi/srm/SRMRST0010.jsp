<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%> 
<%--
	Page Name 	: SRMRST0010.jsp
	Description : 입점상담 결과확인 로그인
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.06		AN TAE KYUNG	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><spring:message code='text.srm.field.resultConsult1' /></title><%--입점상담 결과 --%>

<script language="JavaScript">
	/* 화면 초기화 */
	$(document).ready(function() {
		if (document.location.protocol == 'http:') {
			if ('<c:out value="${serverType}" />' == "prd") {
				document.location.href = document.location.href.replace('http:', 'https:');
			}
		}

		fnGbnChanged();
		
		/* 해외 사업자번호 한글 입력 방지 (ime-mode:disabled 는 크롬에서 X)*/
		/* $("#MyForm input[name='irsNo4']").live("blur keyup", function() {			
			  $(this).val( $(this).val().replace( /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' ) );
		}); */

		//선택
		var option = "<option value= \"\"><spring:message code='text.srm.field.select' /></option>";
		$(option).prependTo("#MyForm select[name=country]");
		$("#MyForm select[name=country]").val("");
		
		/* 엔터키 입력시 로그인 */
		$("input[type='text'], input[type='password']").unbind().keydown(function(e) {	
			if	(e.keyCode == 13) {
				fnLoginOk();
			}
	   	});
		
	});
	
	/* 해외업체구분 변경 시 사업자등록번호 포맷 변경 */
	function fnGbnChanged() {
		
		// 기존값 설정값 초기화
		$("select[name='country']").val("");
		$("input[name='irsNo1'], input[name='irsNo2'], input[name='irsNo3'], input[name='irsNo4'], input[name='tempPw']").val("");
		$("#divPwd").hide();
		
		if ($("#MyForm input[name='shipperType']:checked").val() == "2") {
			$("#divG").show();
			$("#divD").hide();
			
		} else {
			$("#divG").hide();
			$("#divD").show();
		}
	}
	
	function fnLoginOk() {
		
		var target = "<spring:message code='text.srm.field.sellerNameLoc' />";	// 사업자명
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
			
			if (!gfnCheckBizNum(irsNo)) {	// 사업자번호 유효성검사
				alert('<spring:message code="msg.srm.alert.notIrsNo" />');<%--유효한 사업자 번호가 아닙니다. --%>
				return;
			}
			
			$("#MyForm input[name=irsNo]").val(irsNo);
			
		} else {			// Global
			
			target = "<spring:message code='text.srm.field.country' />";	// 국가
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
		
		target = "<spring:message code='text.srm.field.tempPw' />";
		if (!$.trim($("#MyForm input[name='tempPw']").val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");<%--비밀번호을(를) 입력하세요. --%>
			$("#MyForm input[name='tempPw']").focus();
			return;
		}
		
		//----- Argument 설정 ------------------------------
		var dataInfo = {};
		
		dataInfo["houseCode"] 		= $("#MyForm input[name='houseCode']").val();
		dataInfo["shipperType"] 	= $("#MyForm input[name='shipperType']:checked").val();
		dataInfo["sellerNameLoc"]	= $("#MyForm input[name='sellerNameLoc']").val();
		dataInfo["irsNo"] 			= $("#MyForm input[name='irsNo']").val();
		dataInfo["tempPw"] 			= $("#MyForm input[name='tempPw']").val();
		
		// 국가 설정(국가선택값이 없으면 기본적으로 한국(KR) 값으로 설정)
		var country = $("#MyForm select[name='country']").val();
		if (country == "" || country == null) {
			country = "KR";
		}
		
		dataInfo["country"] = country;	// 국가
		//console.log(dataInfo);
		//return;
		//----- Argument 설정 ------------------------------
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/srm/SRMRSTLogin.json"/>',
			data : JSON.stringify(dataInfo),
			success : function(data) {
				if (data.msg == "EXIST") {
					location.href="<c:url value='/edi/srm/SRMRST0020.do' />";
					
				} else if(data.msg == "NOT_EXIST") {		// 해당 정보가 없는 경우
					alert("<spring:message code='msg.srm.alert.notMatchInfo2' />");<%-- 최초 입점 신청 時 등록한 사업자명, 사업자등록번호를 입력하세요. --%>
					
				} else if(data.msg == "NOT_PASSWD") {		// 비밀번호가 다른경우
					alert("<spring:message code='msg.srm.alert.notMatchInfo3' />");<%-- 비밀번호를 분실한 경우, 매뉴얼 다운로드 하시어 임시 비밀번호 발급 바랍니다. --%>
					
				} else if (data.msg == "PASSCHECK_OUT") {	// 실패 횟수 초과인 경우
					alert("<spring:message code='msg.srm.alert.passcheckOut' />");<%--해당 계정은 로그인 실패 횟수 초과로 로그인이 불가합니다. --%>
				}
			}
		});
	}
	
	/* 사업자번호 첫째칸검사 */
	function next_BizNo2(Len, BizNo2, a) {
		if (Len == 3) {
			for (i = 0; i < a.value.length; i++) {
				if (a.value.charAt(i) < "0" || a.value.charAt(i) > "9") {
					alert("<spring:message code='msg.srm.alert.number'/>");<%--숫자만 입력할 수 있습니다.  --%>
					a.focus();
					a.value = "";
					return;
				}
			}
			BizNo2.focus();
		}
	}
	
	/* 사업자번호 두번째칸검사 */
	function next_BizNo3(Len, BizNo3, a) {
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
	function next_BizNo4(Len, BizNo4, a) {
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
	
	/* 국가변경 이벤트 */
	function fnCountryChange(value) {
		// 국가를 한국으로 선택 시 해외업체 구분을 한국으로 변경하도록 설정
		if (value.toUpperCase() == "KR") {
			$("input[name='shipperType']").eq(0).attr("checked", true);
			fnGbnChanged();
		}
	}
	
	/* 구 입점상담 확인 */
	function goConsult() {
		var url ='<c:url value="/epc/edi/consult/NEDMCST0310loginHttpsNewResult.do" />'; /* 팝업창 주소 */
	    Common.centerPopupWindow(url, 'ediConsult', {width : 913, height : 768,  scrollBars :"YES"});
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
				<h2 class="tit_page"><spring:message code='text.srm.field.resultConsult1' /></h2><%--입점상담 결과 --%>
				<p class="page_path"><%-- <a href="<c:url value="/edi/srm/SRMJONMain.do" />">HOME</a> --%>HOME <span><spring:message code='text.srm.field.resultConsult1' /></span></p><%--입점상담 결과 --%>
			</div><!-- END 서브상단 -->
	
			<h3 class="tit_star"><spring:message code='text.srm.field.resultConsult2' /></h3><%--입점상담 결과 확인 --%>
	
			<!-- 알림 -->
			<div class="noti_box">
				<ul class="noti_list">
					<li class="txt_l"><spring:message code='text.srm.field.srmrst0010Notice4' /></li> <%-- 상담신청 시 입력한 비밀번호를 분실한 경우 입점상담 절차 안내에서 메뉴얼을 다운로드 하시어 '비밀번호 변경 탭'에서 임시비밀번호를 신청하거나, Help Desk(srm@lottemart.com)으로 문의메일 송부바랍니다. --%>
					<!-- 
					<li><a href="#" onClick="goConsult();"><spring:message code="text.srm.field.srmrst0010Notice3"/></a></li> <%-- [구 입점상담결과 확인] --%>
					 -->
				</ul>
			</div><!-- END 알림 -->
			
	
			<!-- 회색박스 -->
			<div class="input_wrap">
				<!-- 버튼포함 박스 -->
				<div class="input_box">
					<!-- 입력폼 -->
					<dl class="input_form">
					
					<form name="MyForm"  id="MyForm" method="post">
						<input type="hidden" id="houseCode" name="houseCode" value="000">
						<input type="hidden" id="irsNo" name="irsNo">
						
						<dt><spring:message code='text.srm.field.shipperType' /></dt><%--해외업체구분 --%>
						<dd><srm:codeTag objId="shipperType" comType="RADIO" objName="shipperType" formName="" parentCode="M025" selectParam="1" event="onchange=\"fnGbnChanged();\"" /></dd>
						
						<dt><label for="sellerNameLoc"><spring:message code='text.srm.field.sellerNameLoc' /></label></dt><%--사업자명 --%>
						<dd><input type="text" name="sellerNameLoc" id="sellerNameLoc" class="field_full" ></dd>
						
						<dt><label for="irsNo1"><spring:message code='text.srm.field.irsNo' /></label></dt><%--사업자등록번호 --%>
						<dd>
							<div id="divD">
								<input type="text" class="field_num" name="irsNo1" id="irsNo1" maxlength="3" onKeyUp="next_BizNo2(this.value.length, irsNo2, this);" style="text-align: center;"> - 
								<input type="text" class="field_num" name="irsNo2" id="irsNo2" maxlength="2" onKeyUp="next_BizNo3(this.value.length, irsNo3, this);" style="text-align: center;"> - 
								<input type="text" class="field_num" name="irsNo3" id="irsNo3" maxlength="5" onKeyUp="next_BizNo4(this.value.length, irsNo3, this);" style="text-align: center;">
							</div>
						
							<div id="divG">
								<srm:codeTag objId="country" comType="SELECT" objName="country" formName="" parentCode="M001" event="onChange=\"fnCountryChange(this.value)\"" sortStd="NAME" /><%--선택 --%>
								<input type="text" name="irsNo4" id="irsNo4" style="width: 230px; text-align: center;" />
								<br>
								<span style="color: red;"><spring:message code="text.srm.field.srmjon0020Notice4"/></span><%--spring:message 사업자번호는 앞 11자리(영문,숫자기준)만 입력하세요.--%>
							</div>
						</dd>
						
						<dt><label for="tempPw"><spring:message code='text.srm.field.tempPw1' /></label></dt><%--비밀번호 --%>
						<dd><input type="password" name="tempPw" id="tempPw" class="field_full" ></dd>
						
					</dl><!-- END 입력폼 -->
					
					<!-- 확인 -->
					<button type="button" class="plain btn_ok" onClick="fnLoginOk();"><spring:message code='button.srm.ok' /></button><%--확인 --%>
					<!-- END 확인 -->
				</form>
				</div><!-- 버튼포함 박스 -->
			</div><!-- END 회색박스 -->
	
		</div><!-- END Sub Wrap -->
	</div><!--END Container-->
</body>
</html>