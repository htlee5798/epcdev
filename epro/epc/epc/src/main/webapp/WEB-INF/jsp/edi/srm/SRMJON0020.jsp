<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>

<%--
	Page Name 	: SRMJON0020.jsp
	Description : 입점상당신청 로그인
	Modification Information

	수정일      			수정자           		수정내용
	-----------    	------------    ------------------
	2016.07.06  	SHIN SE JIN		 최초 생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title><spring:message code='text.srm.field.VendorConsult2' /></title><%--입점상담 신청하기 --%>

<style type="text/css">
.btn_search {
	padding: 8px;
	margin-top: 7px;
    background: #888;
    border-radius: 5px;
    color: #fff;
    font-size: 19px;
}

</style>

<script>
	var g_login = false;

	/* 화면 초기화 */
	$(document).ready(function() {
		if (document.location.protocol == 'http:') {
			if ('<c:out value="${serverType}" />' == "prd") {
				document.location.href = document.location.href.replace('http:', 'https:');
			}
		}

		$("#divPwd").hide();

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
		g_login = false;

		// 기존값 설정값 초기화
		$("select[name='country']").val("");
		$("input[name='sellerNameLoc'], input[name='irsNo1'], input[name='irsNo2'], input[name='irsNo3'], select[name='country'], input[name='irsNo4']").attr("disabled", false);
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

	// 허용문자체크 메시지
	function setPermitMsg(target) {
		var permitMsg = "<spring:message code='text.srm.alert.permitCheck' arguments='"+target+"'/>";
		return permitMsg;
	}

	// Byte체크 메시지
	function setByteMsg(target, size) {
		var byteMsg = "<spring:message code='text.srm.alert.byteCheck' arguments='"+target+","+size+"'/>";
		return byteMsg;
	}

	/* 로그인 */
	function fnLoginOk() {

		var target = "<spring:message code='text.srm.field.sellerNameLoc' />";	// 사업자명
		if (!$.trim($("#MyForm input[name=sellerNameLoc]").val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");<%--사업자명을(를) 입력하세요. --%>
			$("#MyForm input[name=sellerNameLoc]").focus();
			return;
		}

		if (!cal_3byte($("#MyForm input[name=sellerNameLoc]").val(), '105', setPermitMsg(target), setByteMsg(target, '105'))) {
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

		} else {		// Global

			target = "<spring:message code='text.srm.field.country' />";	// 국가
			if (!$("#MyForm select[name='country']").val()) {
				alert("<spring:message code='msg.srm.alert.select' arguments='" + target + "' />");<%--국가을(를) 선택하세요. --%>
				$("#MyForm select[name='country']").focus();
				return;
			}

			if (!$("#MyForm input[name='irsNo4']").val()) {
				alert("<spring:message code='msg.srm.alert.reqIrsNo' />");<%--사업자번호를 확인해주세요. --%>
				$("#MyForm input[name='irsNo4']").focus();
				return;
			}

			target = "<spring:message code='text.srm.field.irsNo' />";	// 사업자등록번호
			if (!cal_3byte($("#MyForm input[name='irsNo4']").val(), '11', setPermitMsg(target), setByteMsg(target, '11'))) {
				$("#MyForm input[name=irsNo4]").focus();
				return;
			}

			$("#MyForm input[name='irsNo']").val($("#MyForm input[name='irsNo4']").val());

		}

		//----- Argument 설정 ------------------------------
		var dataInfo = {};

		dataInfo["houseCode"] 		= $("#MyForm input[name='houseCode']").val();				// 하우스코드
		dataInfo["shipperType"] 	= $("#MyForm input[name='shipperType']:checked").val();		// 해외업체구분
		dataInfo["sellerNameLoc"] 	= $("#MyForm input[name=sellerNameLoc]").val();				// 사업자명
		dataInfo["irsNo"] 			= $("#MyForm input[name=irsNo]").val();						// 사업자등록번호

		// 국가 설정(국가선택값이 없으면 기본적으로 한국(KR) 값으로 설정)
		var country = $("#MyForm select[name='country']").val();
		if (country == "" || country == null) {
			country = "KR";
		}

		dataInfo["country"] 		= country;	// 국가

		// 입점상담신청 내역이 존재할 경우 비밀번호 가져가기
		if (g_login) {
			var tempPw = $("#MyForm input[name='tempPw']").val();

			target = "<spring:message code='text.srm.field.tempPw' />";	// 비밀번호
			if (tempPw.length <= 0) {
				alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");<%--비밀번호을(를) 입력하세요. --%>
				$("#MyForm input[name='tempPw']").focus();
				return;
			}

			dataInfo["tempPw"]	= tempPw;
			fnLoginReOk(dataInfo);
			return;
		}

		//----- Argument 설정 ------------------------------

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/srm/SRMJONLogin.json"/>',
			data : JSON.stringify(dataInfo),
			success : function(data) {
				if (data.msg == "EXIST") {
					g_login = true;

					alert("<spring:message code='msg.srm.alert.exists' />");<%--상담신청내역이 존재합니다. 비밀번호 입력 후 로그인하시기 바랍니다. --%>
					$("input[name='sellerNameLoc'], input[name='irsNo1'], input[name='irsNo2'], input[name='irsNo3'], select[name='country'], input[name='irsNo4']").attr("disabled", "disabled");
					$("#divPwd").show();
					$("#MyForm input[name='tempPw']").focus();

				} else if(data.msg == "NOT_EXIST") {
					/* 기존입점상당신청 내역이 없을 경우 약관동의 페이지로 이동  */
					location.href = "<c:url value='/edi/srm/SRMJON0010.do' />";
				} else if (data.msg == "NOT_NAME") {	// 사업자명이 다른경우
					alert("<spring:message code='msg.srm.alert.notMatchInfo2' />");<%-- 최초 입점 신청 時 등록한 사업자명, 사업자등록번호를 입력하세요. --%>
					return;
				}
			}
		});
	}

	/* 입점상담 로그인(비밀번호 포함) */
	function fnLoginReOk(dataInfo) {
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/srm/SRMJONReLogin.json"/>',
			data : JSON.stringify(dataInfo),
			success : function(data) {
				if (data.msg == "EXIST") {
					/* 기존입점상당신청 내역이 존재 할 경우 안내창  */
					location.href = "<c:url value='/edi/srm/SRMJON002001.do'/>";
				} else if(data.msg == "NOT_PASSWD") {	// 비밀번호가 다른경우
					alert("<spring:message code='msg.srm.alert.notMatchInfo3' />");<%-- 비밀번호를 분실한 경우, 매뉴얼 다운로드 하시어 임시 비밀번호 발급 바랍니다. --%>

				} else if (data.msg == "PASSCHECK_OUT") {	// 실패 횟수 초과인 경우
					alert("<spring:message code='msg.srm.alert.passcheckOut' />");<%--해당 계정은 로그인 실패 횟수 초과로 로그인이 불가합니다. --%>
				} else if (data.msg == "TEMP_PW") {	// 임시비밀번호 발급한 후 첫 로그인 인경우
					changeTempPwPopup();
				} else if (data.msg == "PW_CHG_OVER90") {	// 비밀번호 변경 90일 이상 지났을 때
                    alert("비밀번호 변경 90일이 지났습니다.\n비밀번호 변경 후 이용해주세요.")
					changePwOver90Popup();
				} else if (data.msg == "NOT_ACCESS_BEFORE90") {	// 최근 90일이내 접근이력 없는지
					alert("휴먼계정입니다. 비밀번호 변경 후 이용해주세요.");
					location.href = "<c:url value='/edi/srm/SRMSPW0010.do'/>";
				}
			}
		});
	}

	/* 사업자번호 첫째칸검사 */
	function next_BizNo2(Len, BizNo2, a) {
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

	/* 입점상담 메뉴얼 다운로드 */
	function manualDown(fileNm) {
		$("#manualDownForm input[name='manulName']").val(fileNm);
		$('#manualDownForm').attr("action", '<c:url value="/edi/srm/manulDown.do"/>');
		$("#manualDownForm").submit();
	}
	
	function searchCompanyPopup() {
		popupWindow("<c:url value='/edi/srm/searchCompanyPopup.do'/>",800,450);
	}

	function changeTempPwPopup() {
		popupWindow("<c:url value='/edi/srm/tempPasswdChangPopup.do'/>",800,520);
	}

	function changePwOver90Popup() {
		popupWindow("<c:url value='/edi/srm/pwChangeOver90Popup.do'/>",800,520);
	}
	
	//팝업 윈도우
	function popupWindow(url, width, height){
		var cw=width;
		var ch=height;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open("","popup","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
		$("#MyForm").attr("action", url);
		$("#MyForm").attr("target", "popup");
		$("#MyForm").submit();
		$("#MyForm").attr("target", "_self");
	}
	
	function helpWin(state) {
		if(state) document.getElementById("ViewLayer").style.display = "";
		else document.getElementById("ViewLayer").style.display = "none";
	}


</script>

</head>


<body>
	<form name="MyForm" id="MyForm" method="post">
		<input type="hidden" id="houseCode" name="houseCode" value="000" />
		<input type="hidden" id="irsNo" name="irsNo" />


		<!--Container-->
		<div id="container">
			<!-- Sub Wrap -->
			<div class="inner sub_wrap">
				<!-- 서브상단 -->
				<div class="sub_top">
					<h2 class="tit_page"><spring:message code="header.menu.text2" /></h2>	<%-- 입점상담 신청 --%>

					<p class="page_path">HOME <span><spring:message code="header.menu.text2" /></span></p>
				</div><!-- END 서브상단 -->

				<h3 class="tit_star"><spring:message code='text.srm.field.VendorConsult2' /></h3>	<%--입점상담 신청하기 --%>

				<!-- 알림 -->
				<div class="noti_box">
					<ul class="noti_list float-l">
						<li class="txt_l">
							<spring:message code="text.srm.field.srmjon0020Notice1" /><br>	<%-- 신규입점을 진행하기 위해서는 <em>신용평가정보가 필요</em>하오니, 원활한 입점신청을 위해 미리 준비해주세요. --%>
							<spring:message code="text.srm.field.srmstp0010Notice2" />
						</li>
						<li><spring:message code="text.srm.field.srmjon0020Notice2"/></li>	<%-- 지원구매로 입점상담 진행 시 입점상담 절차안내 ‘지원구매’ 안내를 확인하세요. --%>
						<!-- <li><a href="#" onClick="goConsult();"><spring:message code="text.srm.field.srmjon0020Notice3"/></a></li> -->	<%-- [구 입점상담결과 확인] --%>
						<li><spring:message code="text.srm.field.srmjon0020Notice6"/></li>	<%-- 임대매장(푸드코트, 안경점 등) 입점 상담은 tenant@lottemart.com 로 메일 문의바랍니다. --%>
					</ul>

					<p class="float-r">
						<a href="#" class="btn_redround" onclick="manualDown('1');"><spring:message code='text.srm.field.step.tab6'/> <img src="/images/epc/edi/srm/sub/icon_download.png" alt="<spring:message code='text.srm.field.step.tab6'/>"></a>
						<a href="#" class="btn_redround ml5" onclick="manualDown('4');"><spring:message code='text.srm.field.step.tab7'/> <img src="/images/epc/edi/srm/sub/icon_download.png" alt="<spring:message code='text.srm.field.step.tab7'/>"></a>
					</p>
				</div><!-- END 알림 -->


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
								
								<!--
								<input type="radio" id="shipperType" name="shipperType" style="border-style:none;" value="1" onchange="fnGbnChanged();" checked=""> 
								국내(Domestic) &nbsp;
								<input type="radio" id="shipperType" name="shipperType" style="border-style:none;" value="2" onchange="fnGbnChanged();"> 해외(Global)
								 &nbsp;
								-->
							</dd>
							</dd>
							<dt><label for="sellerNameLoc"><spring:message code='text.srm.field.sellerNameLoc' /></label></dt>	<%--사업자명 --%>
							<dd>
								<input type="text" class="field_full" name="sellerNameLoc" id="sellerNameLoc">
							</dd>
							<dt><label for="irsNo1"><spring:message code='text.srm.field.irsNo' /></label></dt>	<%--사업자등록번호 --%>
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

							<div id="divPwd">
								<dt><label for="tempPw"><spring:message code='text.srm.field.tempPw1' /></label></dt>	<%-- 비밀번호 --%>
								<dd><input type="password" class="field_full" name="tempPw" id="tempPw" ></dd>
							</div>
							
							<dd style="text-align: left;">
								<button type="button" class="plain btn_search" style="" onclick="searchCompanyPopup();">사업자명 찾기</button>
								<button type="button" class="plain btn_search" style="" onclick="helpWin(true);">비밀번호 초기화</button>
							</dd>
							
							

						</dl><!-- END 입력폼 -->

						<!-- 확인 -->
						<button type="button" class="plain btn_ok" onclick="fnLoginOk();"><spring:message code='button.srm.ok' /></button>	<%-- 확인 --%>
						<!-- END 확인 -->

					</div><!-- 버튼포함 박스 -->
				</div><!-- END 회색박스 -->

			</div><!-- END Sub Wrap -->
		</div><!--END Container-->

	</form>
	
	
	<!-- [비밀번호 초기화 ] '비밀번호 초기화' 클릭시 display '닫기' 클릭시 non display -->	
	<div id="ViewLayer" style="display:none; position:absolute;  width:550px; height:450px; right: 410px; top: 340px; z-index:4; overflow: " style="filter:alpha(opacity=100)">
	<table cellspacing=1 cellpadding=1 border=0 bgcolor=959595 width=100%>
		<tr>
			<td bgcolor=ffffff>
								
				<table cellspacing=0 cellpadding=0 border=0 width=100%>
					<tr height=30>
						<td>&nbsp; &nbsp;<img src=/images/epc/popup/bull_01.gif border=0>&nbsp;<b>비밀번호 초기화</b></td>
						<td width=50><a href="#" class="btn" onclick="helpWin(false);"><span><spring:message code="button.common.close"/></span></a></td>
					</tr>
					<tr><td height=2 bgcolor="f4383f" colspan=2></td></tr>
				</table>
				
				<table cellspacing=0 cellpadding=0 border=0 width=100%>
					<tr>
						<td colspan=2>&nbsp; &nbsp;비밀번호 초기화를 원하시는 경우 <br/> &nbsp; &nbsp;아래 양식 및 내용을 srm@lottemart.com 으로 보내주시면 초기화된 비밀번호가 발송됩니다.  </td>
					</tr>
					<tr>

						<td> <B><B/>
				        </td>
					</tr>
					<tr><td colspan=2 height=10></td></tr>
					<tr>
						<br/><td colspan=2><B>&nbsp; &nbsp;[메일 제목]</B></td>
					</tr>
					<tr>
						<td> &nbsp; &nbsp;비밀번호 초기화 요청합니다.</td>
					</tr>
					<tr><td colspan=2 height=10></td></tr>
					<tr>
						<td colspan=2><B>&nbsp; &nbsp;[메일 내용]</B></td>
					</tr>
					<tr><td>&nbsp; &nbsp;1. 사업자명 : (띄어쓰기까지 동일 해야합니다)</td></tr>
					<tr><td>&nbsp; &nbsp;2. 사업자등록번호 : "-" 바 없이 숫자만 기입해주세요.</td></tr>
					<tr><td>&nbsp; &nbsp;3. 담당자이메일 : </td></tr>
					<tr><td><br/>&nbsp; &nbsp;※ 제목 및 내용을 꼭 포함하여 보내주세요.<td></tr>
				</table>					
			</td>
		</tr>
	</table>		
	</div>
</body>
</html>