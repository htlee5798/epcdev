<%@ page pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>

<%--
	Page Name 	: SRMJON0010.jsp
	Description : 개인정보 수집 동의
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

<script>

	$(document).ready(function() {

		/* 약관 전체동의 */
		$("input[type='checkbox']").live("change", function() {
			var cnt = 0;
			if ($(this).attr("class") == "agreeCheck") {
				$(".agreeCheck").each(function(index) {
					if ($(".agreeCheck").eq(index).attr("checked") == "checked") {
						cnt++;
						if ($(".agreeCheck").length == cnt && $("#optionalAgreeCheck").is(':checked')) {
							$(".checkAll").attr("checked", true);
						}
						$(".notAgreeCheck").eq(index).attr("checked", false);
					} else {
						$(".checkAll").attr("checked", false);
					}
				});

			} else if ($(this).attr("class") == "notAgreeCheck") {
				$(".notAgreeCheck").each(function(index) {
					if ($(".notAgreeCheck").eq(index).attr("checked") == "checked") {
						$(".agreeCheck").eq(index).attr("checked", false);
						$(".checkAll").attr("checked", false);
					}
				});
			} else if ($(this).attr("class") == "checkAll") {
				if ($(".checkAll").attr("checked") == "checked") {
					$(".notAgreeCheck").attr("checked", false);
					$(".agreeCheck").attr("checked", true);
					$("#optionalNotAgreeCheck").attr("checked", false);
					$("#optionalAgreeCheck").attr("checked", true);
				} else {
					$(".agreeCheck").attr("checked", false);
				}

			} else if ($(this).attr("id") == "optionalAgreeCheck") {
			    if ($("#optionalAgreeCheck").attr("checked") == "checked") {
			        $("#optionalNotAgreeCheck").attr("checked", false);
			    	$(".agreeCheck").each(function(index) {
				        if ($(".agreeCheck").eq(index).attr("checked") == "checked") {
							cnt++;
							if ($(".agreeCheck").length == cnt) {
								$(".checkAll").attr("checked", true);
							}
							
						}
			    	})
			    }
			    else {
			        $(".checkAll").attr("checked", false);
				}
			} else if ($(this).attr("id") == "optionalNotAgreeCheck") {
			  if ($("#optionalNotAgreeCheck").attr("checked") == "checked") {
			      $("#optionalAgreeCheck").attr("checked", false);
			  }
			      $(".checkAll").attr("checked", false);
			}
		});

	});

	/* 약관동의 */
	function fnAgree() {

		var agreeLength = 0;								// 체크된 체크박스 길이
		var notagreeindex = -1;								// 체크안된 첫 체크박스 index
		var target = "";									// 체크안된 약관 이름
        var agreeType = ""; 

	    if (!$('#optionalAgreeCheck').is(':checked') && !$('#optionalNotAgreeCheck').is(':checked')) {
		    alert("선택항목 동의 여부 선택해주세요");
		    $("#optionalAgreeCheck").focus();
		    return ;
		}

	    // 선택 항목 동의 여부에 따른 동의 유형 값 분류_NBMSHIP
	    if ($('#optionalAgreeCheck').is(':checked')) agreeType = "1";
	    else agreeType = "2";
	    
		for (var i=0; i < $(".agreeCheck").length; i++) {
			if($(".agreeCheck").eq(i).is(":checked")) {
				agreeLength++;
			} else {
				if (notagreeindex == -1) {
					notagreeindex = i;
					target = $(".agreeCheck").eq(i).val();
				}
			}
		}

		
		if ($(".agreeCheck").length != agreeLength) {
			alert("<spring:message code='msg.srm.alert.notAgree' arguments='" + target + "' />");<%--{0} 의 약관에 동의 해주세요. --%>
			$(".agreeCheck").eq(notagreeindex).focus();	// 체크안된 약관 포커스

		} else {

			/* 약관동의 시 정보동의 INSERT */
			var dataInfo = {};

			dataInfo["agreeFileVer"] = $("#agreeForm input[name='agreeFileVer']").val();	// 약관동의 파일이름
			dataInfo["agreeType"] = agreeType;
			
			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'text',
				async : false,
				url : '<c:url value="/edi/srm/insertCounselInfo.json"/>',
				data : JSON.stringify(dataInfo),
				success : function(msg) {

					if (msg == "success") {
						var url = "";
						$("#hiddenForm input[name='reqSeq']").val("<c:out value='${param.reqSeq}'/>");
						url = "<c:url value='/edi/srm/SRMJON0040.do'/>";	// 임시저장일 경우 기본정보 입력 화면
						//국내업체
						if ("<c:out value='${srmLoginSession.shipperType}'/>" == "1") {
							if ("<c:out value='${param.reqSeq}'/>" != "") {
								$("#hiddenForm input[name='reqSeq']").val("<c:out value='${param.reqSeq}'/>");
								url = "<c:url value='/edi/srm/SRMJON0040.do'/>";	// 임시저장일 경우 기본정보 입력 화면

							} else {
								url = "<c:url value='/edi/srm/SRMJON0030.do'/>";	// 신규신청일 경우 자가진단 화면
							}
						} else {
							//해외업체
							$("#hiddenForm input[name='reqSeq']").val("<c:out value='${param.reqSeq}'/>");
							url = "<c:url value='/edi/srm/SRMJON0044.do'/>";
						}

						$("#hiddenForm").attr("action", url);
						$("#hiddenForm").submit();
					}
				}
			});
		}
	}

	/* 약관동의하지 않음 */
	function fnUnagree() {
		location.href = "<c:url value="/edi/srm/SRMJON002001.do" />";
	}
</script>

</head>

<body>
	<form name="agreeForm" id="agreeForm" method="post">

		<c:set var="localeLanguage" value="${sessionScope['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'].language}" />
		<%--약관동의 파일버전--%>
		<c:if test="${localeLanguage == 'ko' || localeLanguage == '' || localeLanguage == null}">
			<input type="hidden" id="agreeFileVer" name="agreeFileVer" value="agree_ko_ver1.0.html"/>
		</c:if>

		<c:if test="${localeLanguage == 'en'}">
			<input type="hidden" id="agreeFileVer" name="agreeFileVer" value="agree_en_ver1.0.html"/>
		</c:if>

		<input type="hidden" id="reqSeq" name="reqSeq">

		<!--Container-->
		<div id="container">
			<!-- Sub Wrap -->
			<div class="inner sub_wrap">
				<!-- 서브상단 -->
				<div class="sub_top">
					<h2 class="tit_page"><spring:message code="header.menu.text2" /></h2>	<%-- 입점상담 신청 --%>
					<p class="page_path">HOME <span><spring:message code="header.menu.text2" /></span> <span><spring:message code='text.srm.field.ConsultAgree' /></span></p>
				</div><!-- END 서브상단 -->



				<c:if test="${localeLanguage == 'ko' || localeLanguage == '' || localeLanguage == null}">
					<jsp:include page="agree_ko_ver1.0.html" /><!-- ko 일때 -->
				</c:if>

				<c:if test="${localeLanguage == 'en'}">
					<jsp:include page="agree_en_ver1.0.html" /><!-- en 일때 -->
				</c:if>

				<div style="padding: 10px; text-align: center;">
					<b><spring:message code='text.srm.field.srmjon0010Notice1' /></b><!-- 위 약관의 내용을 확인하였으며,약관에 대하여 전체 동의합니다. -->
					<input type="checkbox" class="checkAll" />
				</div>

				<div style="padding-top: 20px;" align="center">
					<table align="center">
						<tr>
							<td align="center">
								<btn>
									<button type="button" class="btn_normal btn_red" onClick="fnUnagree();"><spring:message code='button.srm.notAgree' /></button>	<%--동의하지 않음 --%>
									&nbsp;&nbsp;
									<button type="button" class="btn_normal btn_blue" onclick="fnAgree()"><spring:message code='button.srm.agree' /></button>		<%--동의합니다 --%>
								</btn>
							</td>
						</tr>
					</table>
				</div>


			</div><!-- END Sub Wrap -->
		</div><!--END Container-->



	</form>
<form id="hiddenForm" name="hiddenForm" method="post">
	<input type="hidden" id="agreeFileVer" name="agreeFileVer"/>
	<input type="hidden" id="reqSeq"       name="reqSeq">
</form>
</body>
</html>
