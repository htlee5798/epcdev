<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
int time = 300; // 인증번호 입력대기시간, 기본값 300초(5분)
int min = time / 60; // 제한시간 표기용 (분)
int sec = time % 60; // 제한시간 표기용 (초), 0이면 표기안함
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${lfn:getString('system.cdn.staticssl.path')}/css/epc/epcLogin.css">
<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/js/epc/common.js"></script>
<style type="text/css">
	#sms_wrap_login {position:absolute; left:50%; top:38%; width:544px; height:450px; margin:-141px auto 0 -315px; padding:39px 47px 0 40px; background: no-repeat left top; background-color: #F6F6F6; border-radius: 10px;}
	.login_content  {overflow:hidden; height:90px; margin-bottom:20px; padding:10px 0 20px 10px; border-top:1px solid #e3e3e3; border-bottom:1px solid #e3e3e3;}
	.login_content table {width:520px;}
	.login_content td {height:30px;}
	.login_content td input[type=text] {padding:0 0 0 4px;  height:22px; border-top:1px solid #d4d4d4; border-right:1px solid #ededed; border-bottom:1px solid #ededed; border-left:1px solid #d4d4d4; vertical-align:middle;}
	.login_content td select { height:22px; border-top:1px solid #d4d4d4; border-right:1px solid #ededed; border-bottom:1px solid #ededed; border-left:1px solid #d4d4d4; vertical-align:middle;}
	.btn_cancel {height:22px; border-radius: 3px; border: 1px solid; }
 	.btn_login input {background-color: #ffffff;}
	.login_bottom {text-align:left; margin-bottom:19px }
	.btn_auth {color:#ffffff; background-color: #686868; }

	.auto-complete {position:absolute; margin:3px 0;  z-index:2;}
	.auto-complete input[type="text"] {text-overflow:ellipsis;}
	.auto-complete-layer {display:none; position:absolute; top:100%; left:0; padding-right:1px; border:1px solid #CCC; border-top:0; background:#fff; z-index:3;}
	.auto-complete-layer.active {display:block;}
	.auto-complete-layer ul {max-height:200px; padding-left:1px; overflow-y:auto;}
	.auto-complete-layer li {position:relative; overflow:hidden; cursor:default;}
	.auto-complete-layer li.active {color:#fff; background:#1e90ff;  cursor:default;}
	.auto-complete-layer li:hover {color: #000; background:#1e90ff;}
	.auto-complete-layer .layer-text {padding:10px 0 10px 7px; line-height:1.7;}
</style>
<title>2차 인증 페이지</title>
<script language="javascript">
$(document).ready(function() {

	if (document.location.protocol == "http:") {
		if ('<c:out value="${serverType}" />' == "prd") {
			document.location.href = document.location.href.replace("http:", "https:");
		}
	}

	// event
	<%--$("#userTel").change(function() {
		var apply = $(this).children("option:selected").attr("alt");
		if (apply == "Y") {
			$("#personalInfoUseApply").attr("checked", true);
		} else {
			$("#personalInfoUseApply").attr("checked", false);
		}
	});--%>

	$("#vendorId").change(function() {
		<c:forEach items="${vendorList}" var="item" begin="0">
		if('${item.VENDOR_ID}' == $("#vendorId").val()) {
			$('#vendorNm').val('${item.VENDOR_NM}');
		}
		</c:forEach>
	});

	$(document).on("click", "li", function(){
		var index = $("li").index(this);

		clearInterval(timer);
		$("#timeContent").val("");
		$("#smsAuthBtn").attr("disabled", false);
		$("#authNumber").val("");
		time = <%=time%>;

		var clickLi = $("li:eq(" + index + ")");
		var vendorSeq = clickLi.attr("data-vendorseq");
		$("#userTel").val(vendorSeq);
		var applyYn = clickLi.attr("data-apply-yn");
		if (applyYn == "Y") {
			$("#personalInfoUseApply").attr("checked", true);
			$("#applyArea").hide();
		} else {
			$("#personalInfoUseApply").attr("checked", false);
			$("#applyArea").show();
		}
		$("#userTelSearch").val(clickLi.attr("data-user-tel"));
		$("#userTelSearchDiv").removeClass("active");
		$("#searchTelList").children().remove();
		document.getElementById("authContent").innerHTML = "";
	});

	<%--$(document).keydown(function(event) {
		if (event.keyCode == '38' || event.keyCode == '40') {
		}
	});--%>

	var prevSearchCellNo = "";
	$("#userTelSearch").keyup(function(event) {

		$(this).val($(this).val().replace(/[^0-9/*]/g,"")); // TODO 숫자만 입력하는 기능 테스트 해봐야한다.
		var searchCellNo = $("#userTelSearch").val();

		var len = searchCellNo.length;
		if (len > 3) {
			if (searchCellNo != prevSearchCellNo) {
				$.ajax({
					type : "POST",
					url : "/epc/main/smsVendorUserTelSelect.do",
					data : { "vendorId" : $('#vendorId').val(), "searchCellNo" : searchCellNo },
					dataType : "json",
					success : function(data) {
						var telList = "";
						if(data.length > 0) {
							$("#searchTelList").children().remove();
							for (var i = 0; i < data.length; i++) {
								if ("" != data[i].USERTEL) {
									$("#searchTelList").append("<li name=\"telLi\" id=\"telLi\" data-vendorseq=\"" + data[i].VENDORSEQ 
											+ "\" data-apply-yn=\"" + data[i].USERINFOAPPLYYN 
											+ "\" data-user-tel=\"" + data[i].USERTEL + "\" >"
											+ data[i].USERTEL + "</li>");
								}
							}
						} else {
							$("#searchTelList").children().remove();
							$("#userTelSearchDiv").removeClass("active");
						}
						$("#userTelSearchDiv").addClass("active");
					}
				});
				$("#userTel").val("");
				$("#personalInfoUseApply").attr("checked", false);
				if ($("#smsAuthBtn").is(":disabled")) {
					$("#smsAuthBtn").attr("disabled", false);
				}
			}
		}
		if (len <= 3) {
			$("#searchTelList").children().remove();
			$("#userTelSearchDiv").removeClass("active");
		}
		prevSearchCellNo = searchCellNo;
	});

});

var time = <%=time%>;
var min = "";
var sec = "";
var timer;
var btnClickFlag = false;
function smsAuthSubmit() {

	if (time < 120) {
		time = <%=time%>;
		clearInterval(timer);
	}
	<%--var smsTel = $("#userTel option:selected").text();--%>
	if ($("#vendorId").val() == "") {
		document.getElementById("authContent").innerHTML = "업체코드를 선택해주세요.";
	} else {

		/*if ($("li").length == 0) {
		alert("검색된 휴대폰번호가 없습니다.\n인증받을 번호를 입력해주세요.");
		 $("#userTelSearch").focus();
		return;
		}*/
		if($("#userTelSearchDiv").hasClass("active")) {
			if ($("li").length == 1) {
				var clickLi = $("li:eq(0)");
				var vendorSeq = clickLi.attr("data-vendorseq");
				$("#userTel").val(vendorSeq);
				var applyYn = clickLi.attr("data-apply-yn");
				if (applyYn == "Y") {
					$("#personalInfoUseApply").attr("checked", true);
					$("#applyArea").hide();
				} else {
					$("#personalInfoUseApply").attr("checked", false);
					$("#applyArea").show();
				}
				$("#userTelSearch").val(clickLi.attr("data-user-tel"));
				$("#userTelSearchDiv").removeClass("active");
				$("#searchTelList").children().remove();
				document.getElementById("authContent").innerHTML = "";
			} else if ($("li").length > 1) {
				alert("검색 결과중 인증할 휴대폰번호를 선택해주세요.");
				return;
			}
		}

		var smsTel = $("#userTelSearch").val();

		if ($("#userTel").val() == "" || smsTel.length < 10 || smsTel.substring(0,2) != "01" ) {
			document.getElementById("authContent").innerHTML = "등록된 번호가 없거나 발송이 불가한 번호입니다. 휴대폰 번호 재확인 바랍니다.";
		} else {
			document.getElementById("authContent").innerHTML = "인증번호를 전송했습니다. <%=min%>분<%if(sec > 0) {out.print(" "+sec+" 초");}%> 이내 입력해주세요.";
			$("#smsAuthBtn").attr("disabled", true);
			smsAuthCode(1); // 인증코드 생성
			time = <%=time%>;
			clearInterval(timer);
			startTimer(); // 타이머 시작
		}
	}
}

function smsAuthCode(code) {
	if (code == 0) {
		timeFlag = "N";
	} else {
		timeFlag = "Y";
	}
	$.ajax({
		type : "POST",
		url : "/epc/main/smsAuthCodeInsert.do",
		data : {
			"vendorId" : $('#vendorId').val(),
			"timeFlag" : timeFlag,
			"userTel" : $('#userTel').val()
		},
		dataType : "json",
		success : function(data) {
			if (data == 2) {
				alert("에러가 발생했습니다. 다시 시도해주세요.");
			}
		}
	});
}

function startTimer() {
	btnClickFlag = true;
	timer = setInterval(function(){
		min = parseInt(time / 60);
		sec = time%60;
		if (sec < 10) {
			sec = "0" + sec;
		}
		$("#timeContent").val(min + ":"+sec);
		time--;
		if (time < 0) {
			clearInterval(timer);
			smsAuthCode(0);
			btnClickFlag = false;
			document.getElementById("authContent").innerHTML = "인증번호 입력 유효시간이 초과되었습니다. 다시 시도 바랍니다.";
		} else if (time < 120) {
			// 유효시간이 2분 미만일 경우 SMS 인증코드 발송 코드 활성화
			$("#smsAuthBtn").attr("disabled", false);
		}
	}, 1000);
}

function vendorOnchange(vendorId) {
	<c:forEach items="${vendorList}" var="item" begin="0">
	if(vendorId == '${item.VENDOR_ID}'){
		$("#vendorNm").val('${item.VENDOR_NM}');
	}
	</c:forEach>
	<%--vendorTelOnChange(vendorId);--%>
}

<%--function vendorTelOnChange(vendorId) {
	$.ajax({
		type : "POST",
		url : "/epc/main/smsVendorUserTelSelect.do",
		data : {"vendorId":vendorId},
		dataType : "json",
		success : function(data) {
			var options = "";
			if(data.length == 0) {
				options = "<option value=''>선택</option>";
			} else {
				for (var i = 0; i < data.length; i++) {
					if(i == 0) {
						options += '선택';
					}

					if ("" == data[i].USERTEL) {
						options += '<option value="' + data[i].VENDORSEQ + '"selected alt="' + data[i].USERINFOAPPLYYN + '">' + data[i].USERTEL + '</option>';
					} else {
						options += '<option value="' + data[i].VENDORSEQ + '" alt="' + data[i].USERINFOAPPLYYN + '">' + data[i].USERTEL + '</option>';
					}
				}
			}
			$("#userTel option").remove();
			$("#userTel").html(options);
		}
	});
}--%>

function smsAuthConfirm() {
	var authNumber = $("#authNumber").val();
	var vendorId = $("#vendorId").val();
	var userTel = $("#userTel").val();
	var infoApplyChk = $("#personalInfoUseApply").is(":checked");
	var personalInfoUseApply = "";

	if (!infoApplyChk) {
		alert("개인정보 수집 이용 동의 후 인증 번호 확인 바랍니다.");
		return false;
	} else {
		personalInfoUseApply = "Y";
	}

	if (time <= 0) {
		document.getElementById("authContent").innerHTML = "인증번호 입력 유효시간이 초과되었습니다. 다시 시도 바랍니다.";
		return false;
	} else if (!btnClickFlag) {
		document.getElementById("authContent").innerHTML = "인증번호 SMS 발송 버튼을 눌러주세요.";
		return false;
	} else if (authNumber == null || authNumber == "") {
		document.getElementById("authContent").innerHTML = "인증번호를 입력해주세요.";
		return false;
	}

	$.ajax({
		type : "POST",
		url : "/epc/main/smsCodeCheck.do",
		data : {
			"vendorId":vendorId,
			"authNumber":authNumber,
			"userTel" :userTel,
			"personalInfoUseApply":personalInfoUseApply
		},
		dataType : "json",
		success : function(data){
			if (data == 'false') {
				document.getElementById("authContent").innerHTML = "인증번호가 일치하지 않습니다. 다시 확인해주시기 바랍니다.";
			} else {
				location.href = data;
			}
		}
	});
}

<%--function numkeyCheck(event) {
	event = event || window.event;
	var keyID = (event.which) ? event.which : event.keyCode;

	if ((keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39) {
		return;
	} else {
		$("#authNumber").val("");
		alert("숫자만 입력해 주세요");
	}

	return false;
}--%>

function PopupWindow() {
	var pageName = "/epc/main/smsAuthUserInfoApplyNoti.do";
	var cw = 840;
	var ch = 340;
	var sw = screen.availWidth;
	var sh = screen.availHeight;
	var px = Math.round((sw - cw) / 2);
	var py = Math.round((sh - ch) / 2);
	window.open(pageName,"","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
}
</script>
</head>
<body>
<form id="form" name="form" method="POST">
	<input type="hidden" id="userTel" name="userTel" value="" />
	<div id="sms_wrap_login">
		<div class="login_top" style="height:28px; margin-bottom:15px;">
			<h1>■ 2단계 휴대폰 인증</h1>
			<span><img src="http://image.lottemart.com/images/login/logo_login.gif" alt="행복드림 LOTTE MART" /></span>
		</div>
		<div style="margin-bottom:6px;">
			보안강화에 따라 SMS 인증 단계가 추가되었습니다.<br />
			협력업체 아이디 및 휴대폰 번호 확인 후 SMS 인증을 진행하시기 바랍니다.
		</div>
		<dl class="login_content">
		<table>
			<tr>
				<td>협력업체 코드</td>
				<td>
					<select id="vendorId" name="vendorId" style="width:157px;">
						<option value="" selected>업체를 선택해주세요.</option>
						<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
						<option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }"></c:if>>${venArr}</option>
						</c:forEach>
					</select>
				</td>
				<td>협력업체 명</td>
				<td><input type="text" id="vendorNm" style="width:154px;" readonly /></td>
			</tr>
			<%--<tr>
				<td>휴대폰 번호</td>
				<td><select name="userTel" id="userTel" style="width:157px;"></select></td>
				<td colspan="2">
				<input type="button" id="smsAuthBtn" class="btn_auth" style="width:150px; border-radius: 2px;" onClick="smsAuthSubmit();" value="인증번호 SMS 발송" />
				</td>
			</tr>--%>
			<tr>
				<td>휴대폰 번호</td>
				<td>
					<input type="text" name="userTelSearch" id="userTelSearch" maxlength="12" style="width:157px;" autocomplete="off" placeholder="번호를 입력하세요." value="" />
					<div class="auto-complete">
						<div id="userTelSearchDiv" class="auto-complete-layer" style="width:157px;">
						<ul id="searchTelList"></ul>
						</div>
					</div>
				</td>
				<td colspan="2">
					<input type="button" id="smsAuthBtn" class="btn_auth" style="width:150px; border-radius: 2px;" onClick="smsAuthSubmit();" value="인증번호 SMS 발송" />
				</td>
			</tr>
			<tr>
				<td>인증 번호</td>
				<td><input type="text" id="authNumber" style="width:154px;" /></td>
				<td><input type="button" class="btn_auth" style="width:50px; border-radius: 2px; color:#ffffff; background-color: #686868;"  onClick="smsAuthConfirm();" value="확인" /></td>
				<td><input type="text" id="timeContent" style="border:none;border-right:0px; border-top:0px; boder-left:0px; boder-bottom:0px; color:blue; background-color:#F6F6F6;" disabled /></td>
			</tr>
		</table>
		</dl>
		<div id="authContent" style="color:blue; height:30px;"></div>
		<div class="login_bottom">
		· 사업자등록번호 하나에 여러개의 협력업체 코드가 존재할 수 있습니다.<br />&nbsp;&nbsp;담당 협력업체 코드 선택 후 인증 받을 담당자 휴대폰 번호를 확인하실 수 있습니다.<br />
		· 휴대폰 번호는 'SCM>업체정보관리'에 협력업체 코드 별 등록된 담당자 휴대폰 번호 (SMS수신 Y)를<br />&nbsp;&nbsp;대상으로 합니다.<br />
		· 등록된 휴대폰번호가 최신이 아니라면 담당MD에게 휴대폰 번호 등록/수정을 요청하시기 바랍니다.<br />
		· 인증번호 재발송은 잔여 유효시간이 2분 미만일 경우부터 가능합니다.<br /><br />
		<div style="height:16px;">
		<div id="applyArea"><input type="checkbox" id="personalInfoUseApply" name="personalInfoUseApply" value="Y"> <strong>개인정보 수집∙이용에 동의 합니다.(필수)&nbsp;&nbsp;&nbsp;<a href="#" onclick="PopupWindow();">자세히보기</a></strong></div>
		</div>
		</div>
		<div class="btn_login">
			<input type="button" class="btn_cancel" value="&nbsp;&nbsp;취소 · 로그인 페이지로 돌아가기&nbsp;&nbsp;" onClick="document.location.href='/epc/main/intro.do'"/>
		</div>
	</div>
</form>
</body>
</html>