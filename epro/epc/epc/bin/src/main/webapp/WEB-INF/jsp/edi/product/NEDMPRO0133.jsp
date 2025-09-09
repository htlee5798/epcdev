<%-- Page Name : NEDMPRO0133.jsp
Description : 중량정보 등록팝업 --%>
<%@include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>중량정보 등록</title>
</head>

<body>
<div id="popup">
	<form name="wgtInfoForm" id="wgtInfoForm" method="POST">
		<input type="hidden" id="wgtSeq" name="wgtSeq" value="<c:out value='${wgtInfo.SEQ}'/>" />
		<input type="hidden" id="prodCd" name="prodCd" value="<c:out value='${wgtInfo.PROD_CD}'/>" />
		<input type="hidden" id="srcmkCd" name="srcmkCd" value="<c:out value='${wgtInfo.SRCMK_CD}'/>" />
		<input type="hidden" id="wg" name="wg" value="<c:out value='${wgtInfo.NEW_G_WGT}'/>" />
		<input type="hidden" id="pclWg" name="pclWg" value="<c:out value='${wgtInfo.NEW_N_WGT}'/>" />
		<input type="hidden" id="pclWgUnit" name="pclWgUnit" value="<c:out value='${wgtInfo.NEW_WGT_UNIT}'/>" />
		<input type="hidden" id="regId" name="regId" value="<c:out value='${param.venCd}'/>" />
		<input type="hidden" id="applyDy" name="applyDy" value="<c:out value='${wgtInfo.APPLY_DY}'/>" />
        <input type="hidden" id="wgtResDt" name="wgtResDt" value="<c:out value='${wgtResInfo.RET_DT}'/>" />
        <input type="hidden" id="wgtResType" name="wgtResType" value="<c:out value='${wgtResInfo.RET_TYPE}'/>" />

		<div id="p_title1">
			<h1>상품 중량 등록</h1>
			<span class="logo"><img src="/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
		</div>

		<br>

		<div class="popup_contents">
			<div class="bbs_search">
				<ul class="tit">
					<li class="tit">상품 중량 등록</li>
					<li class="btn">
						<a href="javascript:submitWeight();" class="btn"><span>
									<spring:message code="button.common.save" />
								</span></a>
						<a href="javascript:window.close();" class="btn"><span>
									<spring:message code="button.common.close" />
								</span></a>
					</li>
				</ul>
			</div>

			<div class="wrap_con">
<%--				<c:if test="${not empty wgtResInfo }">--%>
<%--					<span style="color: #adb5bd;">--%>
<%--						&nbsp;최근 중량변경 요청 시간/상태값 :--%>
<%--						<c:out value='${wgtResInfo.RET_DT}'/> / <c:out value='${wgtResInfo.RET_TYPE}'/>--%>
<%--					</span>--%>
<%--				</c:if>--%>
				<div class="bbs_list">
					<ul class="tit">
						<li class="tit">중량 등록</li>
					</ul>

					<table cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td>
								<table class="bbs_list" cellpadding="0" cellspacing="0" border="0" id="testTable1">
									<colgroup>
										<col />
										<col />
										<col />
									</colgroup>

									<tr>
										<th>총중량</th>
										<th>순중량</th>
										<th>단위</th>
									</tr>
									<tr>
										<td align="center">
											<input type="text" id="reqGWgt" name="reqGWgt" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" value="<c:out value='${wgtInfo.NEW_G_WGT}'/>" maxlength="4" />
										</td>
										<td align="center">
											<input type="text" id="reqNWgt" name="reqNWgt" onKeyup="this.value=this.value.replace(/[^-\.0-9]/g,'');" value="<c:out value='${wgtInfo.NEW_N_WGT}'/>" maxlength="4" />
										</td>
										<td>
											<html:codeTag objId="reqWgtUnit" objName="reqWgtUnit"
														  parentCode="PRD41" comType="SELECT" formName="form"
														  selectParam="KG" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>

		</div>
		<br />

	</form>

</div>

<script>
	$(document).ready(function () {
		let oldWgtUnit = "<c:out value='${wgtInfo.NEW_WGT_UNIT}'/>"
		$("#reqWgtUnit").val(oldWgtUnit).prop("selected", true);

        setResMsgElem();

		// 기존 단위 오카도 연동에 불필요한 단위 제거
		$("#reqWgtUnit option[value='TON']").remove();
		$("#reqWgtUnit option[value='LB']").remove();
		$("#reqWgtUnit option[value='OZ']").remove();
		$("#reqWgtUnit option[value='KT']").remove();
		$("#reqWgtUnit option[value='TO']").remove();
	});

	function validation() {

		let wgtSeq = document.getElementById("wgtSeq").value;
		if(!wgtSeq) {
			alert("중량정보 팝업창 다시 켜주시기 바랍니다.")
			self.close();
			return false;
		}

		let reqGWgt = $("#reqGWgt").val();
		if (!reqGWgt) {
			alert("총중량 입력하세요.");
			return false;
		}

		let reqNWgt = $("#reqNWgt").val();
		if (!reqNWgt) {
			alert("순중량 입력하세요.");
			return false;
		}

		let reqWgtUnit = $("#reqWgtUnit").val();
		if (!reqWgtUnit) {
			alert("중량 단위 입력하세요.");
			return false;
		}

		reqGWgt = reqGWgt * 1;
		reqNWgt = reqNWgt * 1;

		if (reqGWgt == 0 || reqNWgt == 0) {
			alert("0보다 큰 값 입력하세요.");
			return false;
		}

		if (reqGWgt <= reqNWgt) {
			alert("총중량은 순중량보다 높은 값 입력하세요.");
			return false;
		}

		if (reqWgtUnit == "KG" && (reqGWgt - reqNWgt >= 2)) {
			alert("중량 단위 KG일 경우, 총중량 순중량 차이 2 미만으로 입력하세요.");
			return false;
		}

		if (reqGWgt - reqNWgt >= 500) {
			alert("총중량 순중량 차이 500 미만으로 입력하세요.");
			return false;
		}

		return true;
	}

	/* 중량정보 정보요청 */
	function submitWeight() {
		if (!validation()) {
			return false;
		}

		if (!confirm('<spring:message code="msg.common.confirm.save" />')) {
			return false;
		}

		var paramInfo = {};

		paramInfo["proxyNm"] = "MST1530";
		paramInfo["venCd"] = document.getElementById("regId").value;

		paramInfo["prodCd"] = document.getElementById("prodCd").value;
		paramInfo["srcmkCd"] = document.getElementById("srcmkCd").value;

		paramInfo["wgtSeq"] = (document.getElementById("wgtSeq").value * 1 + 1)+ "";
		paramInfo["wg"] = document.getElementById("wg").value;
		paramInfo["pclWg"] = document.getElementById("pclWg").value;
		paramInfo["pclWgUnit"] = document.getElementById("pclWgUnit").value;
		paramInfo["applyDy"] = document.getElementById("applyDy").value;

		paramInfo["reqGWgt"] = $("#reqGWgt").val();
		paramInfo["reqNWgt"] = $("#reqNWgt").val();
		paramInfo["reqWgtUnit"] = $("#reqWgtUnit").val();

		$.ajax({
			contentType: 'application/json; charset=utf-8',
			type: 'post',
			dataType: 'json',
			async: false,
			url: '<c:url value="/edi/product/requestWgtInfoMod.json"/>',
			data: JSON.stringify(paramInfo),
			success: function (data) {

				if (data.msg == "SUCCESS") {
					alert("<spring:message code='msg.common.success.request'/>");
				} else {
					if (data.resultCode == -3) {
						alert("변경 요청했습니다.\n잠시후 변경된 중량정보 확인 부탁드립니다");
					}
					else {
						alert("<spring:message code='msg.common.fail.request'/>" + "[" + data.resultCode + "]");								//실패
					}
				}

				//-----팝업 닫고 리스트 부모페이지 리스트 갱신
				opener.doSearch();
				self.close();
        }});
    }

    function setResMsgElem() {
        let retDt = document.getElementById("wgtResDt").value;
        let retType = document.getElementById("wgtResType").value;
        let msgFrameElem = document.getElementsByClassName("wrap_con")[0];

        if ((retDt && retDt != null) &&
            (msgFrameElem && msgFrameElem != null)) {

            let retDtRefined = retDt.substr(0,4) +"-"+ retDt.substr(4,2) +"-"+ retDt.substr(6,2) +" "+ retDt.substr(8,2) +":"+ retDt.substr(10,2);
            let retTypeRefined = retType == "S" ? "변경완료" : "등록실패, 다시 등록해주세요"
            let msgElem = '<span style="color:#adb5bd;">&nbsp;최근 중량변경 요청 일자/결과 : ' + retDtRefined + ' / ' + retTypeRefined + '</span>';

            msgFrameElem.insertAdjacentHTML("beforebegin",msgElem);
        }
    }
</script>
</body>

</html>