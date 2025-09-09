<%@ page pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMJON002005.jsp
	Description : 비밀번호 변경 팝업창
	Modification Information

	수정일      			수정자           		수정내용
	-----------    	------------    ------------------
	2024.09.11  	PARK IL YOUNG		 최초 생성
--%>

<!doctype html>
<html lang="ko">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700" rel="stylesheet">
	<link href="https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css" rel="stylesheet" type="text/css">

	<%@include file="./SRMCommon.jsp" %>

	<title><spring:message code='text.srm.field.tempPwChange1' /></title><%--비밀번호 변경 --%>

	<script language="JavaScript">
		/* 화면 초기화 */
		$(document).ready(function() {
			// 비밀번호변경/찾기 변경 이벤트
			fnPwdGbnChange();

			let houseCode  = "<c:out value="${vendorInfo.houseCode}" />";
			let irsNo      = "<c:out value="${vendorInfo.irsNo}" />";

			if (!houseCode || !irsNo) {
				alert("" +
						"재로그인후 변경 부탁드립니다.");
				window.close();
				return;
			}
		});

		/* 선택한 정보의 비밀번호 체크 */
		function func_Ok(isKeepPassword90) {
			// 비밀번호변경/찾기 구분자
			const pwdGbn = "0";

			/* 이전 비밀번호가 입력되지 않았을 경우 */
			if (pwdGbn === "0"  && isKeepPassword90 !== "Y") {
				if (!$.trim($("#MyForm input[name='tempPw1']").val())) {
					var target = "<spring:message code='text.srm.field.oldTempPw' />";
					alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");<%--이전 비밀번호을(를) 입력하세요. --%>
					$("#MyForm input[name=tempPw1]").focus();
					return;
				}

				/* 변경할 비밀번호가 입력되지 않았을 경우 */
				if (!$.trim($("#MyForm input[name='tempPw2']").val())) {
					var target = "<spring:message code='text.srm.field.tempPw1' />";
					alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");<%--비밀번호을(를) 입력하세요. --%>
					$("#MyForm input[name=tempPw2]").focus();
					return;
				}

				/* 비밀번호 확인이 입력되지 않았을 경우 */
				if (!$.trim($("#MyForm input[name='tempPw3']").val())) {
					var target = "<spring:message code='text.srm.field.tempPw2' />";
					alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");<%--비밀번호 확인을(를) 입력하세요. --%>
					$("#MyForm input[name=tempPw3]").focus();
					return;
				}

				/* 변경할 비밀번호와 비밀번호 확인 값이 다를 경우 */
				if ($.trim($("#MyForm input[name='tempPw2']").val()) != $.trim($("#MyForm input[name='tempPw3']").val())) {
					alert("<spring:message code='msg.srm.alert.reqChangeTempPw1' />");<%--입력한 비밀번호가 틀립니다. 비밀번호를 확인해 주세요. --%>
					$("#MyForm input[name='tempPw3']").focus();
					return;
				}
			}

			// if (!chkPwd(tempPw2)) {	// 비밀번호 유효성 검사
            //     return;
            // }

			var msg = "<spring:message code='text.srm.field.srmspw001001.save1' />";
			if (pwdGbn == "1") {
				msg = "<spring:message code='text.srm.field.srmspw001001.save2' />";
			}
			if (isKeepPassword90 === "Y") {
				msg = "비밀번호 그대로 유지하시겠습니까?";
			}

			if (!confirm(msg)) {
				return;
			}

			//----- Argument 설정 ------------------------------
			var searchInfo = {};
			searchInfo["houseCode"] 		= "<c:out value="${vendorInfo.houseCode}" />";					// 하우스코드
			searchInfo["sellerCode"] 		= "<c:out value="${vendorInfo.irsNo}" />";						// 사업자번호
			searchInfo["irsNo"] 			= "<c:out value="${vendorInfo.irsNo}" />";						// 사업자번호
			searchInfo["country"]			= "<c:out value="${vendorInfo.country}" />";						// 국가
			searchInfo["oldTempPw"]			= $("#MyForm input[name='tempPw1']").val();					// 이전 비밀번호
			searchInfo["tempPw"] 			= $("#MyForm input[name='tempPw2']").val();					// 변경할 비밀번호
			searchInfo["pwdGbn"] 			= "0"
			searchInfo["isKeepPassword90"] 	= isKeepPassword90;
			//----- Argument 설정 ------------------------------

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/srm/updatePasswdChange.json"/>',
				data : JSON.stringify(searchInfo),
				success : function(data) {
					if (data.msg == "success") {
						alert("비밀번호가 변경되었습니다.");<%-- 비밀번호가 변경되었습니다. --%>
						opener.location.href = "<c:url value='/edi/srm/SRMJON0020.do'/>";
						window.close();
					} else if (data.msg == "KEEP_PASSWORD_90") {
						alert("현재 비밀번호를 계속 사용합니다.");<%-- 비밀번호가 변경되었습니다. --%>
						opener.location.href = "<c:url value='/edi/srm/SRMJON0020.do'/>";
						window.close();
					} else if (data.msg == "NOT_LENGTH") {
						alert("<spring:message code='msg.srm.alert.notLength' />");<%--숫자, 영문자 및 특수문자 3종 혼합하여 8자리 이상\n또는 2종 이상 혼합시 10자리 이상\n최소 8자리, 최대15자리로 구성하세요. --%>
					} else if (data.msg == "NOT_PERMIT") {
						alert("<spring:message code='msg.srm.alert.notPermit' />");<%--비밀번호에 허용되지 않은 문자열이 포함되어 있습니다. --%>
					} else if (data.msg == "EXIST_SPACE") {
						alert("<spring:message code='msg.srm.alert.existSpace' />");<%--비밀번호에 공백이 포함되어 있습니다. --%>
					} else if (data.msg == "EXIST_DUPL") {
						alert("<spring:message code='msg.srm.alert.existDupl' />");<%--중복된 3자 이상의 문자 또는 숫자는 사용불가합니다. --%>
					} else if (data.msg == "NOT_MATCH") {
						alert("<spring:message code='msg.srm.alert.notLength' />");<%--숫자, 영문자 및 특수문자 3종 혼합하여 8자리 이상\n또는 2종 이상 혼합시 10자리 이상\n최소 8자리, 최대15자리로 구성하세요. --%>
					} else if (data.msg == "NOT_EQUALS") {
						alert("<spring:message code='msg.srm.alert.reqChangeTempPw2' />");<%--이전 비밀번호가 일치하지 않습니다. --%>
					} else if (data.msg == "OLD_PW_EQUALS") {
						<%--alert("<spring:message code='msg.srm.alert.reqChangeTempPw3' />");&lt;%&ndash;이전 비밀번호와 변경할 비밀번호와 같습니다. &ndash;%&gt;--%>
						alert("기존에 등록하신 비밀번호와 같습니다.");
					}
				}
			});
		}

		/* 비밀번호 유효성 검사 */
		function chkPwd(pw){

			var result = false;

			if(pw.search(/\s/) != -1){	// 비밀번호 공백 체크
				alert("<spring:message code='msg.srm.alert.existSpace' />");<%--비밀번호에 공백이 포함되어 있습니다. --%>
				return false;
			}

			if (pw.length < 8 || pw.length > 15) {	// 비밀번호 자릿수 체크 최소 8자리 이상
				alert("<spring:message code='msg.srm.alert.notLength' />");<%--숫자, 영문자 및 특수문자 3종 혼합하여 8자리 이상\n또는 2종 이상 혼합시 10자리 이상\n최소 8자리, 최대15자리로 구성하세요. --%>
				return false;
			}

			var patter = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()";

			var cnt1 = 0;
			var checkChar = "";
			for (var i = 0; i < pw.length; i++) {			// 허용되지 않은 문자검사
				checkChar = pw.charAt(i);

				if (patter.indexOf(checkChar) < 0) {		// 포함되어 있지 않은 문자가 있을 경우 -1 를 반환
					cnt1++;
				}
			}

			if (cnt1 > 0) {
				alert("<spring:message code='msg.srm.alert.notPermit' />");<%--비밀번호에 허용되지 않은 문자열이 포함되어 있습니다. --%>
				return false;
			}

			var num = pw.search(/^(?=.*[0-9])/g);			// 숫자
			var eng = pw.search(/^(?=.*[a-zA-Z])/g);		// 영문자
			var spe = pw.search(/^(?=.*[~`!@#$%\^&*()-])/g);// 특수문자

			var cnt2 = 0;

			if (num > -1) cnt2++;
			if (eng > -1) cnt2++;
			if (spe > -1) cnt2++;

			if (cnt2 < 3) {
				if (cnt2 == 2) {
					if (pw.length < 10 || pw.length > 15) {
						alert("<spring:message code='msg.srm.alert.notLength' />");<%--숫자, 영문자 및 특수문자 3종 혼합하여 8자리 이상\n또는 2종 이상 혼합시 10자리 이상\n최소 8자리, 최대15자리로 구성하세요. --%>
						result = false;
					}
				} else {
					alert("<spring:message code='msg.srm.alert.notLength' />");<%--숫자, 영문자 및 특수문자 3종 혼합하여 8자리 이상\n또는 2종 이상 혼합시 10자리 이상\n최소 8자리, 최대15자리로 구성하세요. --%>
					result = false;
				}
			}

			return result;
		}

		/* 비밀번호변경/찾기 변경 이벤트 */
		function fnPwdGbnChange() {
			$("#pwdChg").show();
			$("#btnOk").show();
			$("#pwdImsi").hide();
		}
	</script>
</head>

<body>
<!-- popup wrap -->
<div id="popup_wrap">
	<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

	<h2 class="tit_star"><spring:message code='text.srm.field.tempPwChange1' /></h2>	<%--비밀번호 변경 --%>

	<!-- 알림 -->
	<div class="noti_box">
		<ul class="noti_list">
			<li class="txt_l"><spring:message code='text.srm.field.tempPwChange2' /></li>	<%--숫자, 영문자, 특수문자 중 3종 혼합하여 ...만 사용 가능합니다. --%>
		</ul>
	</div><!-- END 알림 -->

	<form id="MyForm" name="MyForm">
		<table class="tbl_st1 form_style">
			<colgroup>
				<col style="width:150px;">
				<col>
			</colgroup>

			<%---------- 비밀번호 변경 ----------%>
			<tbody id="pwdChg">
			<tr>
				<th><label for="tempPw1"><spring:message code='text.srm.field.oldTempPw'/></label></th>	<%--이전 비밀번호 --%>
				<td><input type="password" id="tempPw1" name="tempPw1" style="width:97%" ></td>
			</tr>
			<tr>
				<th><label for="tempPw2"><spring:message code='text.srm.field.tempPw1'/></label></th>	<%--비밀번호 --%>
				<td><input type="password" id="tempPw2" name="tempPw2" style="width:97%"/></td>
			</tr>
			<tr>
				<th><label for="tempPw3"><spring:message code='text.srm.field.tempPw2'/></th>	<%--비밀번호 확인 --%>
				<td><input type="password" id="tempPw3" name="tempPw3" style="width:97%"/></td>
			</tr>
			</tbody>

		</table>
	</form>

	<p class="align-c mt10">
		<button type="button" class="btn_normal btn_blue" onclick="func_Ok('Y');">다음에 변경하기</button>	<%-- 임시저장--%>
		<button type="button" id="btnOk" name="btnOk" class="btn_normal" onclick="func_Ok();"><spring:message code='button.srm.ok' /></button>
	</p>	<%--확인 --%>
</div><!-- END popup wrap -->
</body>
</html>