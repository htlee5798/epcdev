<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%> 
<%--
	Page Name 	: SRMMNT0010.jsp
	Description : SRM 모니터링 대표자 로그인
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.06		AN TAE KYUNG	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<%@include file="./SRMCommon.jsp" %>
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700" rel="stylesheet">
	<link href="https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css" rel="stylesheet" type="text/css">
<title><spring:message code='text.srm.field.srmmnt0010.title' /></title><%--입점상담 결과 --%>

<script language="JavaScript">
	/* 화면 초기화 */
	$(document).ready(function() {
		fnGbnChanged();

		/* 해외 사업자번호 한글 입력 방지 (ime-mode:disabled 는 크롬에서 X)*/
		$("#MyForm input[name='irsNo4']").live("blur keyup", function() {
			$(this).val( $(this).val().replace( /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '' ) );
		});

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
		$("input[name='irsNo1'], input[name='irsNo2'], input[name='irsNo3'], input[name='irsNo4'], input[name='vEmail'], input[name='authCd']").val("");

		if ($("#MyForm input[name='shipperType']:checked").val() == "2") {
			$("#divG").show();
			$("#divD").hide();

		} else {
			$("#divG").hide();
			$("#divD").show();
		}
	}

	/*validation*/
	function validation(){
		/* 구분별 사업자번호 체크 */
		if ($("#MyForm input[name='shipperType']:checked").val() == "1") {	// Domestic
			var irsNo	= $("#MyForm input[name='irsNo1']").val()  + $("#MyForm input[name='irsNo2']").val() + $("#MyForm input[name='irsNo3']").val();

			if (irsNo.length < 10) {
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

			var target = "<spring:message code='text.srm.field.country' />";
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

			target = "<spring:message code='text.srm.field.irsNo' />";	// 사업자등록번호
			if (!cal_3byte($("#MyForm input[name='irsNo4']").val(), '33', setPermitMsg(target), setByteMsg(target, '33'))) {
				$("#MyForm input[name=irsNo4]").focus();
				return;
			}

			$("#MyForm input[name='irsNo']").val($.trim($("#MyForm input[name='irsNo4']").val()));
		}
		/* 이메일 체크 */
		target = "<spring:message code='text.srm.field.vEmail' />";
		if (!$.trim($('#MyForm input[name="email"]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");<%--이메일을(를) 입력하세요. --%>
			$('#MyForm input[name="email"]').focus();
			return;
		}

		var regVar = "^[a-zA-Z0-9!$&*.=^`|~#%'+\/?_{}-]+@([a-zA-Z0-9_-]+\.)+[a-zA-Z]{2,4}$"; //Email 형식 검증
		if(new RegExp(regVar).test($.trim($('#MyForm input[name="email"]').val())) == false) {
			alert("<spring:message code='msg.srm.alert.validation.vEmail'/>");<%--이메일 형식이 아닙니다. --%>
			$('input[name="email"]').focus();
			return;
		}

		target = "<spring:message code='text.srm.field.certificationNumver' />";
		 if (!$.trim($("#MyForm input[name='authCd']").val())) {
			 alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");<%--비밀번호을(를) 입력하세요. --%>
			 $("#MyForm input[name='authCd']").focus();
			 return;
		 }
		return true;
	}

	function fnLoginOk() {
		if(!validation())return;
		var irsNo = "";
		if ($("#MyForm input[name='shipperType']:checked").val() == "1") {	// Domestic
			irsNo = $("#MyForm input[name='irsNo1']").val() + $("#MyForm input[name='irsNo2']").val() + $("#MyForm input[name='irsNo3']").val();
		} else {
			irsNo = $("#MyForm input[name='irsNo4']").val();
		}
		var searchInfo = {}
		searchInfo["irsNo"] = irsNo;
		searchInfo["email"] = $("#MyForm input[name='email']").val();
		searchInfo["authCd"] = $("#MyForm input[name='authCd']").val();
		$('#MyForm input[name=btnLogin]').attr("disabled","true");
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/mnt/selectCEOSRMmoniteringLogin.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(data) {
				$('#MyForm input[name=btnLogin]').attr("disabled","false");
				if(data.message == "SUCCESS") {
					$("#loginForm input[name=irsNo]").val(irsNo);
					$("#loginForm").attr("action", "<c:url value="/edi/mnt/SRMMNT0020.do"/>");
					$("#loginForm").submit();
				} else if(data.message == "FAIL-MAX_COUNT"){
					alert("<spring:message code='msg.srm.alert.authMaxCount' />");<%--최대접속 횟수를 초과하였습니다. --%>
					return;
				} else if(data.message == "FAIL-AUTH_CD"){
					alert("<spring:message code='msg.srm.alert.authCd' />");<%--인증번호를 확인해주세요. --%>
					return;
				} else {
					//FAIL-NULL
					alert("<spring:message code='msg.srm.alert.notMatchInfo' />");<%--정보를 확인해주세요. --%>
					return;
				}

			}
		});


	}

	/* 사업자번호 검사 */
	function next_BizNo(obj) {
		for (i = 0; i < $(obj).val().length; i++) {
			if ($(obj).val().charAt(i) < "0" || $(obj).val().charAt(i) > "9") {
				alert("<spring:message code='msg.srm.alert.number'/>");<%--숫자만 입력할 수 있습니다. --%>
				$(obj).focus();
				$(obj).val("");
				return;
			}
		}
		if (($(obj).attr("id") == "irsNo1" && $(obj).val().length == 3)
			||($(obj).attr("id") == "irsNo2" && $(obj).val().length == 2)
			||($(obj).attr("id") == "irsNo3" && $(obj).val().length == 5)){
			if($(obj).attr("id") == "irsNo3" || $(obj).attr("id") == "irsNo3"){
				$(obj).focus();
			} else {
				$(obj).next().focus();
			}
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
	
</script>

</head>


<body>

<form name="MyForm"  id="MyForm" method="post">
	<input type="hidden" id="houseCode" name="houseCode" value="000">
	<input type="hidden" id="irsNo" name="irsNo">

	<!--Container-->
	<div id="container">
		<!-- Sub Wrap -->
		<div class="inner sub_wrap">
			<!-- 서브상단 -->
			<div class="sub_top">
				<h2 class="tit_page"><spring:message code='text.srm.field.srmmnt0010.menu' /></h2>	<%--SRM 모니터랑 대표자 로그인 --%>

				<%--<p class="page_path">HOME <span><spring:message code="header.menu.text2" /></span></p>--%>
			</div><!-- END 서브상단 -->

			<h3 class="tit_star"><spring:message code='text.srm.field.srmmnt0010.sub.title1' /></h3><%--SRM 모니터랑 대표자 로그인 --%>

			<!-- 알림 -->
			<%--<div class="noti_box">
				<ul class="noti_list">
					<li class="txt_l"><spring:message code="text.srm.field.srmjon0020Notice1"/></li>	&lt;%&ndash; 신규입점을 진행하기 위해서는 <em>신용평가정보가 필요</em>하오니, 원활한 입점신청을 위해 미리 준비해주세요. &ndash;%&gt;
					<li><spring:message code="text.srm.field.srmjon0020Notice2"/></li>	&lt;%&ndash; 지원구매로 입점상담 진행 시 입점상담 절차안내 ‘지원구매’ 안내를 확인하세요. &ndash;%&gt;
					<li><a href="#" onClick="goConsult();"><spring:message code="text.srm.field.srmjon0020Notice3"/></a></li>	&lt;%&ndash; [구 입점상담결과 확인] &ndash;%&gt;
				</ul>
			</div><!-- END 알림 -->--%>


			<!-- 회색박스 -->
			<div class="input_wrap">
				<!-- 버튼포함 박스 -->
				<div class="input_box">
					<!-- 입력폼 -->
					<dl class="input_form">
						<dt><spring:message code='text.srm.field.shipperType' /></dt>	<%--해외업체구분 --%>
						<dd>
							<!-- <input type="radio" id="shipperType1" name="shipperType" checked>
                            <label for="shipperType1">국내(Domestic)</label>
                            <input type="radio" id="shipperType2" name="shipperType" class="ml10">
                            <label for="shipperType2">해외(Global)</label> -->
							<srm:codeTag objId="shipperType" comType="RADIO" objName="shipperType" formName="" parentCode="M025" selectParam="1" event="onchange=\"fnGbnChanged();\"" />
						</dd>
						<dt><label for="irsNo1"><spring:message code='text.srm.field.irsNo' /></label></dt>	<%--사업자등록번호 --%>
						<dd>
							<div id="divD">
								<input type="text" class="field_num" name="irsNo1" id="irsNo1" maxlength="3" onKeyUp="next_BizNo(this);" style="text-align: center;"> -
								<input type="text" class="field_num" name="irsNo2" id="irsNo2" maxlength="2" onKeyUp="next_BizNo(this);" style="text-align: center;"> -
								<input type="text" class="field_num" name="irsNo3" id="irsNo3" maxlength="5" onKeyUp="next_BizNo(this);" style="text-align: center;">
							</div>

							<div id="divG">
								<srm:codeTag objId="country" comType="SELECT" objName="country" formName="" parentCode="M001" event="onChange=\"fnCountryChange(this.value)\"" width="103px;" sortStd="NAME"/><%--선택 --%>
								<input type="text" name="irsNo4" id="irsNo4" style="width: 230px; text-align: center;" />
							</div>
						</dd>

						<dt><label for="email"><spring:message code='text.srm.field.vEmail' /></label></dt><%--이메일 --%>
						<dd>
							<input type="text" class="field_full" id="email" name="email"/>
						</dd>
						<div id="divPwd">
							<dt><label for="authCd"><spring:message code='text.srm.field.certificationNumver' /></label></dt>	<%-- 비밀번호 --%>
							<dd><input type="password" class="field_full" name="authCd" id="authCd" ></dd>
						</div>

					</dl><!-- END 입력폼 -->

					<!-- 확인 -->
					<button type="button" class="plain btn_ok" onclick="fnLoginOk();"><spring:message code='button.srm.ok' /></button>	<%-- 확인 --%>
					<!-- END 확인 -->

				</div><!-- 버튼포함 박스 -->
			</div><!-- END 회색박스 -->

		</div><!-- END Sub Wrap -->
	</div><!--END Container-->

</form>


<form id="loginForm" name="loginForm" method="post">
	<input type="hidden" id="irsNo" name="irsNo"/>
</form>

</body>
</html>