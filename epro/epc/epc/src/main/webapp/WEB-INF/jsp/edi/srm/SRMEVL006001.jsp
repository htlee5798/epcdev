<%@ page  pageEncoding="UTF-8"%>

<%--
	Page Name 	: SRMEVL003001.jsp
	Description : 품질경영평가 업체정보/접수 팝업
	Modification Information

	수정일      			수정자           		수정내용
	-----------    	------------    ------------------
	2016.07.27  	LEE HYOUNG TAK		 최초 생성
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=1100">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,600,700" rel="stylesheet">
	<link href="https://cdn.rawgit.com/hiun/NanumSquare/master/nanumsquare.css" rel="stylesheet" type="text/css">

	<%@include file="./SRMCommon.jsp" %>
	<title><spring:message code='text.srm.field.srmevl003002.title'/></title><%--spring:message : 파트너사 정보--%>

	<script language="JavaScript">
		/* 화면 초기화 */
		$(document).ready(function() {
		});

		//validation
		function validation() {
			if (!$.trim($('#MyForm input[name=checkDate]').val())) {
				var target = "<spring:message code='text.srm.field.checkDate' />";
				alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");/*spring:message : 현장점검일을(를) 입력하세요.*/
				return false;
			}
			if (!$.trim($('#MyForm select[name=checkTime]').val())) {
				var target = "<spring:message code='text.srm.field.checkTime' />";
				alert("<spring:message code='msg.srm.alert.select' arguments='" + target + "' />");/*spring:message : 현장점검일 시간을(를) 선택하세요.*/
				return false;
			}

			return true;
		}

		//등록
		function doReceitp() {
			if(!validation()) return;
			if(!confirm("<spring:message code='msg.srm.alert.tempSave'/>")) return;/*spring:message : 저장하시겠습니까?*/
			var saveInfo = {};

			saveInfo["seq"] = "<c:out value="${vo.seq}"/>";
			saveInfo["evNo"] = "<c:out value="${vo.evNo}"/>";
			saveInfo["sgNo"] = "<c:out value="${vo.sgNo}"/>";
			saveInfo["sellerCode"] = "<c:out value="${vo.sellerCode}"/>";
			saveInfo["visitSeq"] = "<c:out value="${vo.visitSeq}"/>";
			saveInfo["evalFlag"] = "<c:out value="${vo.evalFlag}"/>";

			saveInfo["checkDate"] = $('#MyForm input[name=checkDate]').val().replaceAll("-","");
			saveInfo["checkTime"] = $('#MyForm select[name=checkTime]').val();

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/evl/updateQualityEvaluationCheckDate.json"/>',
				data : JSON.stringify(saveInfo),
				success : function(json) {
					alert("<spring:message code='msg.srm.alert.saveOk' />");/*spring:message : 저장되었습니다.*/
					parent.window.opener.goPage('1');
					func_ok();
				}
			});;
		}

		//닫기
		function func_ok() {
			window.close();
		}
	</script>

</head>


<body>

<form name="MyForm"  id="MyForm" method="post">
	<!-- popup wrap -->
	<div id="popup_wrap_full">
		<h1 class="popup_logo"><img src="/images/epc/edi/srm/common/logo.png" alt="Lotte Mart"></h1>

		<div class="tit_btns">
			<h2 class="tit_star"><spring:message code='text.srm.field.srmevl003002.sub.title4'/></h2>	<%-- 접수--%>
			<div class="right_btns">
				<c:if test="${srmComp.progressCode ne '300'}">
					<btn>
						<input type="button" class="btn_normal btn_blue" value="<spring:message code='button.srm.receipt'/>" onclick="javascript:doReceitp();"/><%--spring:message : 접수--%>
					</btn>
				</c:if>
			</div>
		</div>

		<table class="tbl_st1 form_style">
			<colgroup>
				<col width="25%" />
				<col width="*" />
				<col width="25%" />
				<col width="*" />
			</colgroup>
			<tbody>
			<%--<tr>--%>
			<%--<th><spring:message code='text.srm.field.catLv1Code2'/></th>&lt;%&ndash;spring:message : 분류&ndash;%&gt;--%>
			<%--<td colspan="3">--%>
			<%--<c:out value="${srmComp.catLv1CodeName}"/>--%>
			<%--</td>--%>
			<%--</tr>--%>
			<tr>
				<th><spring:message code='text.srm.field.receiptDate'/></th><%--spring:message : 요청일자--%>
				<td>
					<c:out value="${srmComp.reqDate}"/>
				</td>
				<th><spring:message code='text.srm.field.status'/></th><%--spring:message : 진행 상태--%>
				<td>
					<%--<c:out value="${srmComp.statusNm}"/>--%>
					<c:if test="${srmComp.progressCode eq '100'}">
						<spring:message code="text.srm.search.field.status.option1"/>
					</c:if>
					<c:if test="${srmComp.progressCode eq '200'}">
						<spring:message code="text.srm.search.field.status.option2"/>
					</c:if>
					<c:if test="${srmComp.progressCode eq '300'}">
						<spring:message code="text.srm.search.field.status.option3"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<th><spring:message code='text.srm.field.remark'/></th><%--spring:message : 비고--%>
				<td colspan="3">
					<div style="height: 100px; overflow-y: scroll; padding-top:5px;">
						<pre style="white-space: pre-wrap;"><c:out value="${srmComp.remark}"/></pre>
					</div>
				</td>
			</tr>
			<tr>
				<th><spring:message code='text.srm.field.checkDate'/></th><%--spring:message : 현장점검일--%>
				<td colspan="3">
					<c:if test="${srmComp.status eq 'G04'}">
						<input type="text" class="input_txt_default" id="checkDate" name="checkDate" title="<spring:message code='text.srm.field.checkDate'/>" disabled="disabled" readonly="readonly" value="" style="width: 80px;">
						<img src="/images/epc/layout/btn_cal.gif" align="top" class="middle" onClick="openCal('MyForm.checkDate');" style="cursor: pointer;" />

						<select id="checkTime" name="checkTime" title="" style="width:80px;">
							<option value=""><spring:message code="text.srm.field.select"/></option><%--spring:message : 선택--%>
							<option value="00">00:00</option>
							<option value="01">01:00</option>
							<option value="02">02:00</option>
							<option value="03">03:00</option>
							<option value="04">04:00</option>
							<option value="05">05:00</option>
							<option value="06">06:00</option>
							<option value="07">07:00</option>
							<option value="08">08:00</option>
							<option value="09">09:00</option>
							<option value="10">10:00</option>
							<option value="11">11:00</option>
							<option value="12">12:00</option>
							<option value="13">13:00</option>
							<option value="14">14:00</option>
							<option value="15">15:00</option>
							<option value="16">16:00</option>
							<option value="17">17:00</option>
							<option value="18">18:00</option>
							<option value="19">19:00</option>
							<option value="20">20:00</option>
							<option value="21">21:00</option>
							<option value="22">22:00</option>
							<option value="23">23:00</option>
						</select>
					</c:if>
					<c:if test="${srmComp.status ne 'G04'}">
						<c:out value="${srmComp.checkDate}"/> <c:out value="${srmComp.checkTime}"/>:00
					</c:if>
				</td>
			</tr>
			<c:if test="${not empty srmComp.receiptDate}">
				<tr>
					<th><spring:message code='text.srm.field.requestDate'/></th><%--spring:message : 접수일자--%>
					<td colspan="3">
						<c:out value="${srmComp.receiptDate}"/>
					</td>
				</tr>
			</c:if>
			</tbody>
		</table>
		<%----- 현장점검일 End -------------------------%>
		<%----- 기업정보 Start -------------------------%>
		<div class="tit_btns">
			<h2 class="tit_star"><spring:message code='text.srm.field.srmevl003002.sub.title2'/></h2>	<%--spring:message : 기업정보--%>
		</div>

		<table class="tbl_st1">
			<colgroup>
				<col width="25%" />
				<col width="*" />
				<col width="25%" />
				<col width="*" />
			</colgroup>
			<tbody>
			<tr>
				<th><spring:message code='text.srm.field.sellerNameLoc'/></th><%--spring:message : 사업자명--%>
				<td>
					<c:out value="${srmComp.sellerNameLoc}"/>
				</td>
				<th><spring:message code='text.srm.field.vMainName'/></th><%--spring:message : 담당자명--%>
				<td>
					<c:out value="${srmComp.vMainName}"/>
				</td>
			</tr>
			<tr>
				<th><spring:message code='text.srm.field.vPhone1'/></th><%--spring:message : 대표전화--%>
				<td>
					<c:out value="${srmComp.vPhone1}"/>
				</td>
				<th><spring:message code='text.srm.field.vEmail'/></th><%--spring:message : 이메일--%>
				<td>
					<c:out value="${srmComp.vEmail}"/>
				</td>
			</tr>
			<tr>
				<th><spring:message code='text.srm.field.address'/></th><%--spring:message : 주소--%>
				<td colspan="3">
					<c:out value="${srmComp.sellerAddress}"/>
				</td>
			</tr>
			<c:if test="${srmComp.shipperType eq '1'}">
			<tr>
				<th><spring:message code='text.srm.field.industryType'/></th><%--spring:message : 업종--%>
				<td>
					<c:out value="${srmComp.industryType}"/>
				</td>
				<th><spring:message code='text.srm.field.businessType'/></th><%--spring:message : 업태--%>
				<td>
					<c:out value="${srmComp.businessType}"/>
				</td>
			</tr>
			<tr>
				<th><spring:message code='text.srm.field.sellerType'/></th><%--spring:message : 사업유형--%>
				<td colspan="3">
					<c:out value="${srmComp.sellerTypeNm}"/>
				</td>
			</tr>
			</c:if>
			</tbody>
		</table>
		<%----- 기업정보 End -------------------------%>

		<c:if test="${srmComp.shipperType eq '1'}">
		<%----- 인증정보 Start -------------------------%>
		<div class="tit_btns">
			<h2 class="tit_star"><spring:message code='text.srm.field.srmjon0042.sub.title1'/></h2>	<%--spring:message : 인증정보--%>
		</div>

		<table class="tbl_st1">
			<colgroup>
				<col width="20%"/>
				<col width="20%"/>
				<col width="20%"/>
				<col width="20%"/>
				<col width="20%"/>
			</colgroup>
			<tr>
				<td>
					<input type="checkbox" id="zzqcFg1" name="zzqcFg1" disabled title="<spring:message code='checkbox.srm.zzqcFg1'/>" <c:if test="${srmComp.zzqcFg1 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg1'/><%--spring:message : HACCP--%>
				</td>
				<td>
					<input type="checkbox" id="zzqcFg2" name="zzqcFg2" disabled title="<spring:message code='checkbox.srm.zzqcFg2'/>" <c:if test="${srmComp.zzqcFg2 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg2'/><%--spring:message : FSSC22000--%>
				</td>
				<td>
					<input type="checkbox" id="zzqcFg3" name="zzqcFg3" disabled title="<spring:message code='checkbox.srm.zzqcFg3'/>" <c:if test="${srmComp.zzqcFg3 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg3'/><%--spring:message : ISO 22000--%>
				</td>
				<td>
					<input type="checkbox" id="zzqcFg4" name="zzqcFg4" disabled title="<spring:message code='checkbox.srm.zzqcFg4'/>" <c:if test="${srmComp.zzqcFg4 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg4'/><%--spring:message : GMP인증--%>
				</td>
				<td>
					<input type="checkbox" id="zzqcFg5" name="zzqcFg5" disabled title="<spring:message code='checkbox.srm.zzqcFg5'/>" <c:if test="${srmComp.zzqcFg5 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg5'/><%--spring:message : KS인증--%>
				</td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" id="zzqcFg6" name="zzqcFg6" disabled title="<spring:message code='checkbox.srm.zzqcFg6'/>" <c:if test="${srmComp.zzqcFg6 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg6'/><%--spring:message : 우수농산품(GAP)인증--%>
				</td>
				<td>
					<input type="checkbox" id="zzqcFg7" name="zzqcFg7" disabled title="<spring:message code='checkbox.srm.zzqcFg7'/>" <c:if test="${srmComp.zzqcFg7 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg7'/><%--spring:message : 유기농식품인증--%>
				</td>
				<td>
					<input type="checkbox" id="zzqcFg8" name="zzqcFg8" disabled title="<spring:message code='checkbox.srm.zzqcFg8'/>" <c:if test="${srmComp.zzqcFg8 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg8'/><%--spring:message : 전통식품품질인증--%>
				</td>
				<td>
					<input type="checkbox" id="zzqcFg9" name="zzqcFg9" disabled title="<spring:message code='checkbox.srm.zzqcFg9'/>" <c:if test="${srmComp.zzqcFg9 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg9'/><%--spring:message : ISO 9001--%>
				</td>
				<td>
					<input type="checkbox" id="zzqcFg10" name="zzqcFg10" disabled title="<spring:message code='checkbox.srm.zzqcFg10'/>" <c:if test="${srmComp.zzqcFg10 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg10'/><%--spring:message : 수산물품질인증--%>
				</td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" id="zzqcFg11" name="zzqcFg11" disabled title="<spring:message code='checkbox.srm.zzqcFg11'/>" <c:if test="${srmComp.zzqcFg11 eq 'X'}">checked</c:if>/> <spring:message code='checkbox.srm.zzqcFg11'/><%--spring:message : PAS 220--%>
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td colspan="5">
					<spring:message code='checkbox.srm.zzqcFg12'/> :<%--spring:message : 기타인증--%>
					<c:out value='${srmComp.zzqcFg12}'/>
				</td>
			</tr>
		</table>
		</c:if>
		<p class="align-c mt10"><input type="button" id="" name="" value="<spring:message code='button.srm.close'/>" class="btn_normal btn_blue" onclick="func_ok();" /><%--spring:message : 닫기--%></p>
		<%----- 인증정보 End -------------------------%>
	</div><!-- END popup wrap -->

</form>

</body>
</html>