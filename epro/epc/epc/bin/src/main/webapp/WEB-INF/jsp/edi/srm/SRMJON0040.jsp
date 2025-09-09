<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>
<%--
	Page Name 	: SRMJON0040.jsp
	Description : 입점상담신청[기본정보]
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.06 		LEE HYOUNG TAK 	최초생성
--%>

<!doctype html>
<html lang="ko">l>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<title><spring:message code='text.srm.field.srmjon0040.title'/></title><%--spring:message : 입점상담 신청하기--%>

	<script language="JavaScript">
		$(document).ready(function() {
				
			//숫자만입력
			$('.numberic').css('imeMode','disabled').keydown(function(){
				var e = window.event; //윈도우의 event를 잡	는것입니다.
				//입력 허용 키
				if ( (e.keyCode >= 48 && e.keyCode <= 57)				//숫자키
						|| (e.keyCode >= 96 && e.keyCode <= 105)			//키패드 숫자키
						|| (e.keyCode >= 112 && e.keyCode <= 123)       //F1 - F12
						|| e.keyCode == 8           //Backspace
						|| e.keyCode == 9           //Tab
						|| e.keyCode == 16           //Shift
						|| e.keyCode == 17           //Ctrl
						|| e.keyCode == 18           //Alt
						|| e.keyCode == 46          //Delete
						|| e.keyCode == 35          //End
						|| e.keyCode == 36          //Home
						|| e.keyCode == 37          //왼쪽 화살표
						|| e.keyCode == 39 ){       //오른쪽 화살표
					return true;
				} else {
					return false;
				}
			}).blur(function(){
				onlyNum($(this));
			});

			//커스텀태그 defName Set
			//선택
			var option = "<option value= \"\"><spring:message code='text.srm.field.select' /></option>";
			$(option).prependTo("#MyForm select[name=companyType]");
			$(option).prependTo("#MyForm select[name=sellerType]");
			$(option).prependTo("#MyForm select[name=country]");
			$(option).prependTo("#MyForm select[name=channelCode]");
			$(option).prependTo("#MyForm select[name=cityText]");

			//커스텀태그 값SET
			$("#MyForm input:radio[name='shipperType']:radio[value='<c:out value="${srmComp.shipperType}"/>']").attr("checked",true);
			$("#MyForm select[name=companyType]").val("<c:out value="${srmComp.companyType}"/>");
			$("#MyForm select[name=sellerType]").val("<c:out value="${srmComp.sellerType}"/>");
			$("#MyForm select[name=country]").val("<c:out value="${srmComp.country}"/>");
			$("#MyForm select[name=channelCode]").val("<c:out value="${srmComp.channelCode}"/>");
			$("#MyForm select[name=cityText]").val("<c:out value="${srmComp.cityText}"/>");

			if("<c:out value="${srmComp.shipperType}"/>" == "2") {
				$("#MyForm select[name=cityText]").attr("disabled", "disabled");
			}


			if("<c:out value="${srmComp.onlineMailTarget}"/>" == ""){
				$('input:radio[name=onlineMailTarget]:input[value="MMD"]').attr("checked", true);
			} else {
				$('input:radio[name=onlineMailTarget]:input[value="<c:out value="${srmComp.onlineMailTarget}"/>"]').attr("checked", true);
			}
		});

		//숫자만 입력 reset
		function onlyNum(obj){
			if( $(obj).val() != null && $(obj).val() != '' ) {
				var tmps = $(obj).val().replace(/[^0-9]/g, '');
				$(obj).val(tmps);
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

		//validation
		function validation(){
			var target = "";

			target = "<spring:message code='text.srm.field.channelCode'/>";	// 판매처
			if (!$.trim($('#channelCode').val())) {
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 판매처을(를) 선택하세요. */
				$('#channelCode').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.shipperType'/>";	// 해외업체구분
			if (!$(':radio[name="shipperType"]:checked').val()) {
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 해외업체구분을(를) 선택하세요. */
				$('#shipperType').focus();
				return false;
			}

			if ("<c:out value='${srmComp.shipperType}'/>" == "1") {
				// 사업자번호 체크
				var irsNo1 	= $("#MyForm input[name='irsNo1']").val();
				var irsNo2 	= $("#MyForm input[name='irsNo2']").val();
				var irsNo3 	= $("#MyForm input[name='irsNo3']").val();
				var irsNo	= irsNo1 + "" + irsNo2 + "" + irsNo3;

				if (irsNo.length <= 0 || irsNo.length < 10) {
					alert("<spring:message code='msg.srm.alert.reqIrsNo' />");<%--사업자번호를 확인해주세요. --%>
					$("#MyForm input[name='irsNo1']").focus();
					return false;
				}

				if (!gfnCheckBizNum(irsNo)) {	// 사업자번호 유효성 체크
					alert('<spring:message code="msg.srm.alert.notIrsNo" />');<%--유효한 사업자 번호가 아닙니다. --%>
					return false;
				}
			}

			target = "<spring:message code='text.srm.field.catLv1Code'/>";	// 대분류
			if (!$.trim($('#catLv1Code').val())) {
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 대분류을(를) 선택하세요. */
				$('#catLv1Code').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.tempPw'/>";	// 비밀번호
			if (!$.trim($('#tempPw').val()) && '<c:out value="${srmComp.tempPw}"/>' == "") {
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 비밀번호을(를) 입력하세요. */
				$('#tempPw').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.sellerNameLoc'/>";	// 사업자명
			if (!$.trim($('#sellerNameLoc').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 사업자명을(를) 입력하세요. */
				$('#sellerNameLoc').focus();
				return false;
			}

			if (!cal_3byte($('#sellerNameLoc').val(), '105', setPermitMsg(target), setByteMsg(target, '105'))) {
				$('#sellerNameLoc').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.sellerNameEng'/>";	// 영문 사업자명
			if (!cal_3byte($('#sellerNameEng').val(), '105', setPermitMsg(target), setByteMsg(target, '105'))) {
				$('#sellerNameEng').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.companyType'/>";	// 법인구분
			if (!$.trim($('#companyType').val())) {
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 법인구분을(를) 선택하세요. */
				$('#companyType').focus();
				return false;
			}

			//법인인경우 법인번호 입력
			if($('#companyType').val() == '1'){
				target = "<spring:message code='text.srm.field.companyRegNo'/>";	// 법인구분
				if (!$.trim($('#companyRegNo').val())) {
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 법인번호을(를) 선택하세요. */
					$('#companyRegNo').focus();
					return false;
				}
			}


			target = "<spring:message code='text.srm.field.sellerCeoName'/>";	// 대표자명
			if (!$.trim($('#sellerCeoName').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 대표자명을(를) 입력하세요. */
				$('#sellerCeoName').focus();
				return false;
			}

			if (!cal_3byte($('#sellerCeoName').val(), '30', setPermitMsg(target), setByteMsg(target, '30'))) {
				$('#sellerCeoName').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.vMainName'/>";	// 담당자명
			if (!$.trim($('#vMainName').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 담당자명을(를) 입력하세요. */
				$('#vMainName').focus();
				return false;
			}

			if (!cal_3byte($('#vMainName').val(), '100', setPermitMsg(target), setByteMsg(target, '100'))) {
				$('#vMainName').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.vPhone1'/>";	// 대표전화
			if (!$.trim($('#vPhone1').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 대표전화을(를) 입력하세요. */
				$('#vPhone1').focus();
				return false;
			}

			if (!cal_3byte($('#vPhone1').val(), '20', setPermitMsg(target), setByteMsg(target, '20'))) {
				$('#vPhone1').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.vMobilePhone'/>";	// 휴대전화
			if (!$.trim($('#vMobilePhone').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 휴대전화을(를) 입력하세요. */
				$('#vMobilePhone').focus();
				return false;
			}

			if (!cal_3byte($('#vMobilePhone').val(), '16', setPermitMsg(target), setByteMsg(target, '16'))) {
				$('#vMobilePhone').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.sellerCeoEmail'/>";	// 대표자 이메일
			if (!$.trim($('#sellerCeoEmail').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 대표자 이메일을(를) 입력하세요. */
				$('#sellerCeoEmail').focus();
				return false;
			}

			//Email 검증
			var regVar = "^[a-zA-Z0-9!$&*.=^`|~#%'+\/?_{}-]+@([a-zA-Z0-9_-]+\.)+[a-zA-Z]{2,4}$";
			if(new RegExp(regVar).test($('#sellerCeoEmail').val()) == false) {
				alert("<spring:message code='msg.srm.alert.validation.vEmail'/>");/*spring:message : 이메일 형식이 아닙니다.*/
				$("#sellerCeoEmail").focus();
				return false;
			}

			if (!cal_3byte($('#sellerCeoEmail').val(), '130', setPermitMsg(target), setByteMsg(target, '130'))) {
				$('#sellerCeoEmail').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.vEmail'/>";	// 이메일
			if (!$.trim($('#vEmail').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 이메일을(를) 입력하세요. */
				$('#vEmail').focus();
				return false;
			}

			//Email 검증
			if(new RegExp(regVar).test($('#vEmail').val()) == false) {
				alert("<spring:message code='msg.srm.alert.validation.vEmail'/>");/*spring:message : 이메일 형식이 아닙니다.*/
				$("#vEmail").focus();
				return false;
			}

			if (!cal_3byte($('#vEmail').val(), '34', setPermitMsg(target), setByteMsg(target, '34'))) {
				$('#vEmail').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.faxFhone'/>";	// FAX
			if (!cal_3byte($('#faxFhone').val(), '31', setPermitMsg(target), setByteMsg(target, '31'))) {
				$('#faxFhone').focus();
				return false;
			}


			<%--target = "<spring:message code='text.srm.field.phoneNo'/>";	// 담당자전화번호--%>
			<%--if (!$.trim($('#phoneNo').val())) {--%>
			<%--alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 담당자전화번호을(를) 입력하세요. */--%>
			<%--$('#phoneNo').focus();--%>
			<%--return false;--%>
			<%--}--%>

			<%--if (!cal_3byte($('#phoneNo').val(), '20', setPermitMsg(target), setByteMsg(target, '20'))) {--%>
			<%--$('#phoneNo').focus();--%>
			<%--return false;--%>
			<%--}--%>

			if ("<c:out value='${srmComp.shipperType}'/>" == "1") {

				target = "<spring:message code='text.srm.field.address'/>";	// 주소
				if (!$.trim($('#zipcode').val()) || !$.trim($('#address1').val()) || !$.trim($('#address2').val())) {
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 주소을(를) 입력하세요. */
					$('#address2').focus();
					return false;
				}

				if (!cal_3byte($('#zipcode').val(), '10', setPermitMsg(target), setByteMsg(target, '10'))) {
					$('#zipcode').focus();
					return false;
				}

				if (!cal_3byte($('#address2').val(), '105', setPermitMsg(target), setByteMsg(target, '105'))) {
					$('#address2').focus();
					return false;
				}

			}
			if ("<c:out value='${srmComp.shipperType}'/>" == "2") {

				target = "<spring:message code='text.srm.field.address'/>";	// 주소
				if (!$.trim($('#address1').val())) {
					alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 주소을(를) 입력하세요. */
					$('#address1').focus();
					return false;
				}

				if (!cal_3byte($('#address1').val(), '105', setPermitMsg(target), setByteMsg(target, '105'))) {
					$('#address1').focus();
					return false;
				}

			}

			target = "<spring:message code='text.srm.field.industryType'/>";	// 업종
			if (!$.trim($('#industryType').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 업종을(를) 입력하세요. */
				$('#industryType').focus();
				return false;
			}

			if (!cal_3byte($('#industryType').val(), '90', setPermitMsg(target), setByteMsg(target, '90'))) {
				$('#industryType').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.businessType'/>";	// 업태
			if (!$.trim($('#businessType').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 업태을(를) 입력하세요. */
				$('#businessType').focus();
				return false;
			}

			if (!cal_3byte($('#businessType').val(), '90', setPermitMsg(target), setByteMsg(target, '90'))) {
				$('#businessType').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.sellerType'/>";	// 사업유형
			if (!$.trim($('#sellerType').val())) {
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 사업유형을(를) 선택하세요. */
				$('#sellerType').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.country'/>";	// 국가
			if (!$.trim($('#country').val())) {
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 국가을(를) 선택하세요. */
				$('#country').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.cityText'/>";
			if (!cal_3byte($('#cityText').val(), '9', setPermitMsg(target), setByteMsg(target, '9'))) {
				$('#cityText').focus();
				return false;
			}

			return true;
		}

		//탭이동
		function tab_page(page) {
			if('<c:out value="${srmComp.reqSeq}"/>' =="") {
				alert("<spring:message code="msg.srm.alert.validation.tempsave"/>");/*임시저장 후 이동 가능합니다.*/
				return;
			}
			if(!confirm("<spring:message code='msg.srm.alert.moveTab'/>")) {/*spring:message : 페이지 이동시 해당 내용이 저장됩니다. 저장하시겠습니까?*/
				switch (page){
					case '0': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0040.do'/>");break;
					case '1': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0041.do'/>");break;
					case '2': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0042.do'/>");break;
					case '3': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0043.do'/>");break;
				}
				$("#hiddenForm").submit();
				return;
			}

			if (!validation())return;

			var saveInfo = {};
			$('#MyForm').find("input, select").each(function() {
				if (this.type != "button" && this.type != "radio") {
					saveInfo[this.name] = $(this).val();
				}
			});
			//사업자번호
			if ("<c:out value='${srmComp.shipperType}'/>" == 1) { //국내
				saveInfo["irsNo"] = saveInfo["irsNo1"] + saveInfo["irsNo2"] + saveInfo["irsNo3"];
			}

			//해외업체구분
			saveInfo["shipperType"] = $(':radio[name="shipperType"]:checked').val();
			saveInfo["reqSeq"] = '<c:out value="${srmComp.reqSeq}"/>';
			saveInfo["sellerCode"] = '<c:out value="${srmComp.sellerCode}"/>';
			saveInfo["houseCode"] = '<c:out value="${srmComp.houseCode}"/>';
			<%--if ('<c:out value="${srmComp.tempPw}"/>' == saveInfo["tempPw"]) {--%>
				<%--saveInfo["tempPw"] = "";--%>
			<%--}--%>

			saveInfo["localFoodYn"] = $(':radio[name="localFoodYn"]:checked').val();
			saveInfo["onlineMailTarget"] = $(':radio[name="onlineMailTarget"]:checked').val();

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/srm/updateHiddenCompInfo.json"/>',
				data : JSON.stringify(saveInfo),
				success : function(data) {
					if (data.message == "SUCCESS") {
						$('#hiddenForm input[name=houseCode]').val("<c:out value='${srmComp.houseCode}'/>");
						$('#hiddenForm input[name=sellerCode]').val("<c:out value='${srmComp.sellerCode}'/>");
						$('#hiddenForm input[name=reqSeq]').val(data.reqSeq);
						switch (page){
							case '0': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0040.do'/>");break;
							case '1': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0041.do'/>");break;
							case '2': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0042.do'/>");break;
							case '3': $("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0043.do'/>");break;
						}
						$("#hiddenForm").submit();

					} else {
						errerMessage(data.message);
					}
				}
			});


		}

		//임시저장
		function doTempSave(){

			if (!validation())return;
			if(!confirm("<spring:message code='msg.srm.alert.tempSave'/>")) return;/*spring:message : 저장하시겠습니까?*/

			var saveInfo = {};
			$('#MyForm').find("input, select").each(function() {
				if (this.type != "button" && this.type != "radio") {
					saveInfo[this.name] = $(this).val();
				}
			});

			//사업자번호
			if ("<c:out value='${srmComp.shipperType}'/>" == 1) { //국내
				saveInfo["irsNo"] = saveInfo["irsNo1"] + saveInfo["irsNo2"] + saveInfo["irsNo3"];
			}

			//해외업체구분
			saveInfo["shipperType"] = $(':radio[name="shipperType"]:checked').val();
			saveInfo["reqSeq"] = '<c:out value="${srmComp.reqSeq}"/>';
			saveInfo["sellerCode"] = '<c:out value="${srmComp.sellerCode}"/>';
			saveInfo["houseCode"] = '<c:out value="${srmComp.houseCode}"/>';
			saveInfo["localFoodYn"] = $(':radio[name="localFoodYn"]:checked').val();
			saveInfo["onlineMailTarget"] = $(':radio[name="onlineMailTarget"]:checked').val();

			<%--if ('<c:out value="${srmComp.tempPw}"/>' == saveInfo["tempPw"]) {--%>
				<%--saveInfo["tempPw"] = "";--%>
			<%--}--%>

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/srm/updateHiddenCompInfo.json"/>',
				data : JSON.stringify(saveInfo),
				success : function(data) {
					if (data.message == "SUCCESS") {
						alert("<spring:message code='msg.srm.alert.saveOk' />");/*spring:message : 저장되었습니다.*/
						$('#hiddenForm input[name=houseCode]').val("<c:out value='${srmComp.houseCode}'/>");
						$('#hiddenForm input[name=sellerCode]').val("<c:out value='${srmComp.sellerCode}'/>");
						$('#hiddenForm input[name=reqSeq]').val(data.reqSeq);
						$("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0040.do'/>");
						$("#hiddenForm").submit();

					} else {
						errerMessage(data.message);
					}
				}
			});
		}

		function errerMessage(message){
			if (message == "NOT_LENGTH") {
				alert("<spring:message code='msg.srm.alert.notLength' />");/*spring:message : 숫자, 영문자 및 특수문자 3종 혼합하여 8자리 이상\n또는 2종 이상 혼합시 10자리 이상\n최소 8자리, 최대15자리로 구성하세요.*/

			} else if (message == "NOT_PERMIT") {
				alert("<spring:message code='msg.srm.alert.notPermit' />");/*spring:message : 비밀번호에 허용되지 않은 문자열이 포함되어 있습니다.*/

			} else if (message == "EXIST_SPACE") {
				alert("<spring:message code='msg.srm.alert.existSpace' />");/*spring:message : 비밀번호에 공백이 포함되어 있습니다.*/

			} else if (message == "EXIST_DUPL") {
				alert("<spring:message code='msg.srm.alert.existDupl' />");/*spring:message : 중복된 3자 이상의 문자 또는 숫자는 사용불가합니다.*/

			} else if (message == "NOT_MATCH") {
				alert("<spring:message code='msg.srm.alert.notLength' />");/*spring:message : 숫자, 영문자 및 특수문자 3종 혼합하여 8자리 이상\n또는 2종 이상 혼합시 10자리 이상\n최소 8자리, 최대15자리로 구성하세요.*/

			} else if (message == "FAIL-REFUSE") {
				alert('<spring:message code="msg.srm.alert.refuse"/>');/*spring:message : 연속 3회 입점거절되어 더이상 신청이 불가합니다.*/
				revertCatLv1Code();	//대분류 복구
			} else if (message == "FAIL-SUCCESS") {
				alert('<spring:message code="msg.srm.alert.errorSuccess"/>');/*spring:message : 입점승인상태인 내용이 있습니다.*/
				revertCatLv1Code();	//대분류 복구
			} else if (message == "FAIL-ING") {
				alert('<spring:message code="msg.srm.alert.errorIng"/>');/*spring:message : 입점 신청 진행중인 내용이 있습니다.*/
				revertCatLv1Code();	//대분류 복구
			} else {
				alert('<spring:message code="msg.common.fail.request"/>');/*spring:message : 요청처리를 실패하였습니다.*/
			}
		}

		//도로명  조회 함수
		function openZipCodeNew(){
			var cw=720;
			var ch=700;
			var sw=screen.availWidth;
			var sh=screen.availHeight;
			var px=Math.round((sw-cw)/2);
			var py=Math.round((sh-ch)/2);
			window.open("<c:url value='/edi/srm/selectJusoPopup.do'/>","popup","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
		}
		function receiveValue(zipCd, juso) {
			if (zipCd != "") {
				$('#MyForm input[name=zipcode]').val(zipCd);
				$('#MyForm input[name=address1]').val(juso);
			}
		}

		//입점상담신청 취소
		function doCancel() {
			if(!confirm("<spring:message code="msg.srm.alert.vendorConsultReqCancel"/>")) return;/*spring:message :입력하신 내용은 삭제됩니다. 입점상담신청을 취소 하시겠습니까?*/

			var saveInfo = {};
			saveInfo["houseCode"] = "<c:out value='${srmComp.houseCode}'/>";
			saveInfo["sellerCode"] = "<c:out value='${srmComp.sellerCode}'/>";
			saveInfo["reqSeq"] = "<c:out value='${srmComp.reqSeq}'/>";

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/srm/updateHiddenCompReqCancel.json"/>',
				data : JSON.stringify(saveInfo),
				success : function(data) {
					if (data.message == "SUCCESS") {
						alert('<spring:message code="msg.srm.alert.vendorConsultReqCancelConplete"/>');/*spring:message : 입점상담신청 취소가 정상적으로 처리 되었습니다.*/
						$("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON002001.do'/>");
						$("#hiddenForm").submit();
					}  else {
						alert('<spring:message code="msg.common.fail.request"/>');/*spring:message : 요청처리를 실패하였습니다.*/
					}
				}
			});
		}

		//대분류 코드 조회 팝업
		function doSearch(){
			var cw=680;
			var ch=550;
			var sw=screen.availWidth;
			var sh=screen.availHeight;
			var px=Math.round((sw-cw)/2);
			var py=Math.round((sh-ch)/2);
			var popup = window.open("","popup","left="+px+",top="+py+",width="+cw+",height="+ch+","+"toolbar=no,menubar=no,status=no,resizable=no,scrollbars=no");
			$("#MyForm").attr("action", "<c:url value='/edi/srm/selectCatLv1CodePopup.do'/>");
			$("#MyForm").attr("target", "popup");
			$("#MyForm").submit();
			$("#MyForm").attr("target", "_self");
		}

		//대분류 SET
		function setCatLv1Code(sgNo, sgName, scKind){
			$('#MyForm input[name=catLv1CodeNm]').val(sgName);
			$('#MyForm input[name=catLv1Code]').val(sgNo);
		}

		//대분류 복구
		function revertCatLv1Code() {
			$('#MyForm input[name=catLv1CodeNm]').val("<c:out value='${srmComp.catLv1CodeNm}'/>");
			$('#MyForm input[name=catLv1Code]').val("<c:out value='${srmComp.catLv1Code}'/>");
		}
	</script>


</head>


<body>
<form id="MyForm" name="MyForm" method="post">

	<!--Container-->
	<div id="container">
		<!-- Sub Wrap -->
		<div class="inner sub_wrap">
			<!-- 서브상단 -->
			<div class="sub_top">
				<h2 class="tit_page"><spring:message code="header.menu.text2" /></h2>	<%-- 입점상담 신청 --%>
				<p class="page_path">HOME <span><spring:message code="header.menu.text2" /></span> <span><spring:message code='text.srm.field.srmjon0040.title' /></span></p>
			</div><!-- END 서브상단 -->

			<!-- 알림 -->
			<div class="noti_box">
				<ul class="noti_list">
					<li class="txt_l"><em><spring:message code="text.srm.field.srmjon0040Notice1"/></em></li>	<%-- 정보 누락 및 불일치 시 입점 상담 등의 절차가 지연되거나 중단 혹은 취소될 수 있습니다. --%>
					<li><em><spring:message code="text.srm.field.srmjon0040Notice3"/></em></li>	<%-- 사업자명, 사업자등록번호, 비밀번호, 대표자 이메일은 로그인 시 필수항목 이므로 반드시 기억해 두시기 바랍니다. --%>
					<li><em><spring:message code="text.srm.field.srmjon0040Notice4"/></em></li><%--사업자명은 사업자등록증의 명칭 그대로 작성해주세요.<br>예) 주식회사->(주) : 줄여쓰기 오류 | 롯데마트 : 롯 데 마 트 : 띄어쓰기 오류--%>
					<li><em><spring:message code="text.srm.field.srmjon0040Notice5"/></em></li><%--업태와 업종(=종목)은 사업자 등록증의 명시되어 있는 항목 중 당사와 거래할 주요 항목으로 작성해 주세요.--%>
				</ul>
			</div><!-- END 알림 -->

			<!-- 정보 입력 유형 탭 : 선택된 li에 클래스 on을 붙입니다.-->
			<ul class="sub_tab col4">
				<li class="on" rel="tab1"><a href="#" onclick=""><spring:message code='tab.srm.srmjon0030.tab1'/></a></li>					<%-- 기본정보--%>
				<li rel="tab2"><a href="#" onClick="tab_page('1');"><spring:message code='tab.srm.srmjon0030.tab2'/></a></li>	<%-- 상세정보--%>
				<li rel="tab3"><a href="#" onClick="tab_page('2');"><spring:message code='tab.srm.srmjon0030.tab3'/></a></li>	<%-- 인증/신용평가 정보--%>
				<li rel="tab4"><a href="#" onClick="tab_page('3');"><spring:message code='tab.srm.srmjon0030.tab4'/></a></li>	<%-- 정보확인--%>
			</ul><!-- END 정보 입력 유형 탭 -->

			<%----- 상담채널 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code='text.srm.field.srmjon0040.sub.title1'/></h3>	<%-- 상담판매처--%>
				<div class="right_btns">
					<button type="button" class="btn_normal btn_blue" onclick="javascript:doTempSave();"><spring:message code='button.srm.tempSave'/></button>	<%-- 임시저장--%>

					<c:if test="${not empty srmComp.reqSeq}">
						<button type="button" class="btn_normal btn_red" onclick="javascript:doCancel();"><spring:message code='button.srm.vendorConsultReqCancel'/></button>	<%-- 입점상담신청 취소--%>
					</c:if>
				</div>
			</div><!-- END 상단 버튼이 있을 경우 타이틀 -->

			<!-- END 버튼이 없을 경우 타이틀 -->

			<!-- 정보 입력폼 -->
			<table class="tbl_st1 form_style">
				<colgroup>
					<col style="width:15%;">
					<col style="width:35%;">
					<col style="width:15%;">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th>
							*<label for="channelCode"><spring:message code='text.srm.field.classification'/></label> :<%--spring:message : 상담신청 분류--%>
						</th>
						<td>
							<srm:codeTag comType="SELECT" objId="channelCode" objName="channelCode" formName="" parentCode="SRM053" width="100px" attr="disabled='disabled'" />
						</td>
						<th>*<label for="catLv1Code"><spring:message code='text.srm.field.catLv1Code'/></label></th>
						<td>
							<input type="hidden"	id="catLv1Code" 	name="catLv1Code" 		value="<c:out value="${srmComp.catLv1Code}"/>" />
							<input type="text"		id="catLv1CodeNm" name="catLv1CodeNm"	value="<c:out value="${srmComp.catLv1CodeNm}"/>"class="input_txt_default"  disabled readonly/>

							<button type="button" class="btn_normal btn_blue ml5" onclick="javascript:doSearch();"><spring:message code='button.srm.search'/></button>	<%-- 검색 --%>
						</td>
					</tr>
					<tr>
						<th><label for="localFoodYn"><spring:message code='text.srm.field.localFoodYn'/></label></th><%--spring:message : 로컬푸드유무--%>
						<td <c:if test="${srmComp.channelCode ne '06'}"> colspan="3" </c:if>>
							<%--<input type="checkbox" id="" name=""/>--%>
							<input type="radio" id="localFoodYn" name="localFoodYn" value="Y" <c:if test="${srmComp.localFoodYn eq 'Y'}">checked</c:if>/> Yes
							<input type="radio" id="localFoodYn" name="localFoodYn" value="N" <c:if test="${srmComp.localFoodYn ne 'Y'}">checked</c:if>/> No
						</td>
						<c:if test="${srmComp.channelCode eq '06'}">
							<th><label for="onlineMailTarget">* <spring:message code='text.srm.field.onlineMailTarget'/></label></th><%--spring:message : 온라인몰메일전송대상--%>
							<td>
								<srm:codeTag comType="RADIO" objId="onlineMailTarget" objName="onlineMailTarget" formName="" parentCode="SRM086" width="100px"/>
							</td>
						</c:if>
					</tr>
				</tbody>
			</table><!-- END 정보 입력폼 -->
			<%----- 상담채널 End -------------------------%>


			<%----- 기업정보 Start -------------------------%>
			<!-- 버튼이 없을 경우 타이틀 -->
			<h3 class="tit_star"><spring:message code='text.srm.field.srmjon0040.sub.title2'/></h3>	<%-- 기업정보--%>
			<!-- END 버튼이 없을 경우 타이틀 -->

			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%"/>
					<col width="*"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<tr>
					<th><label for="shipperType">* <spring:message code='text.srm.field.shipperType'/></label></th><%--spring:message : 해외업체구분--%>
					<td colspan="3">
						<srm:codeTag comType="RADIO" objId="shipperType" objName="shipperType" formName="" parentCode="M025" attr="disabled=disabled" />
					</td>
				</tr>
				<tr>
					<th><label for="irsNo1">* <spring:message code='text.srm.field.irsNo'/></label></th><%--spring:message : 사업자 등록번호--%>
					<td>
						<c:if test="${srmComp.shipperType eq '1'}">
							<input type="text" id="irsNo1" name="irsNo1" title="<spring:message code='text.srm.field.irsNo'/>" value="<c:out value="${fn:substring(srmLoginSession.irsNo,0,3)}"/>" maxlength="3" style="width:40px; text-align: center;" disabled="disabled" readonly="readonly" />
							-
							<input type="text" id="irsNo2" name="irsNo2" title="<spring:message code='text.srm.field.irsNo'/>" value="<c:out value="${fn:substring(srmLoginSession.irsNo,3,5)}"/>" maxlength="2" style="width:40px; text-align: center;" disabled="disabled" readonly="readonly" />
							-
							<input type="text" id="irsNo3" name="irsNo3" title="<spring:message code='text.srm.field.irsNo'/>" value="<c:out value="${fn:substring(srmLoginSession.irsNo,5,10)}"/>" maxlength="5" style="width:80px; text-align: center;" disabled="disabled" readonly="readonly" />
						</c:if>
						<c:if test="${srmComp.shipperType eq '2'}">
							<input type="text" id="irsNo" name="irsNo" title="<spring:message code='text.srm.field.irsNo'/>" value="<c:out value='${srmLoginSession.irsNo}'/>" style="text-align: center;" disabled="disabled" readonly="readonly" />
						</c:if>

					</td>
					<th><label for="tempPw">* <spring:message code='text.srm.field.tempPw'/></label></th><%--spring:message : 비밀번호--%>
					<td>
						<input type="password" id="tempPw" name="tempPw" title="<spring:message code='text.srm.field.tempPw'/>" value=""  maxlength="15"/>
					</td>
				</tr>
				<tr>
					<th><label for="sellerNameLoc">* <spring:message code='text.srm.field.sellerNameLoc'/></label></th><%--spring:message : 상호명--%>
					<td>
						<input type="text" id="sellerNameLoc" name="sellerNameLoc" title="<spring:message code='text.srm.field.sellerNameLoc'/>" value="<c:out value='${srmComp.sellerNameLoc}'/>" style="width: 90%;" />
					</td>
					<th><label for="sellerNameEng"><spring:message code='text.srm.field.sellerNameEng'/></label></th><%--spring:message : 상호명--%>
					<td>
						<input type="text" id="sellerNameEng" name="sellerNameEng" title="<spring:message code='text.srm.field.sellerNameEng'/>" value="<c:out value='${srmComp.sellerNameEng}'/>" style="width: 90%;" />
					</td>
				</tr>
				<tr>

					<th><label for="companyType">* <spring:message code='text.srm.field.companyType'/></label></th><%--spring:message : 법인구분--%>
					<td>
						<srm:codeTag comType="SELECT" objId="companyType" objName="companyType" formName="" parentCode="M074" width="100px"/>
					</td>
					<th><label for="companyRegNo"> <spring:message code='text.srm.field.companyRegNo'/></label></th><%--spring:message : 법인번호--%>
					<td>
						<input type="text" id="companyRegNo" name="companyRegNo" class="numberic" value="<c:out value='${srmComp.companyRegNo}'/>" maxlength="13"/>
					</td>
				</tr>
				<tr>
					<th><label for="sellerCeoName">* <spring:message code='text.srm.field.sellerCeoName'/></label></th><%--spring:message : 대표자명--%>
					<td>
						<input type="text" id="sellerCeoName" name="sellerCeoName" title="<spring:message code='text.srm.field.sellerCeoName'/>" value="<c:out value='${srmComp.sellerCeoName}'/>" />
					</td>
					<th><label for="vMainName">* <spring:message code='text.srm.field.vMainName'/></label></th><%--spring:message : 담당자명--%>
					<td>
						<input type="text" id="vMainName" name="vMainName" title="<spring:message code='text.srm.field.vMainName'/>" value="<c:out value='${srmComp.vMainName}'/>" />
					</td>
				</tr>
				<tr>
					<th><label for="vPhone1">* <spring:message code='text.srm.field.vPhone1'/></label></th><%--spring:message : 대표전화--%>
					<td>
						<input type="text" id="vPhone1" name="vPhone1" title="<spring:message code='text.srm.field.vPhone1'/>" value="<c:out value='${srmComp.vPhone1}'/>" class="input_txt_default numberic" />
					</td>
					<th><label for="vMobilePhone">* <spring:message code='text.srm.field.vMobilePhone'/></label></th><%--spring:message : 휴대전화--%>
					<td>
						<input type="text" id="vMobilePhone" name="vMobilePhone" title="<spring:message code='text.srm.field.vMobilePhone'/>" value="<c:out value='${srmComp.vMobilePhone}'/>" class="input_txt_default numberic" />
					</td>
				</tr>
				<tr>
					<th><label for="sellerCeoEmail">* <spring:message code='text.srm.field.sellerCeoEmail'/></label></th><%--spring:message : 대표자 E-Mail--%>
					<td>
						<input type="text" id="sellerCeoEmail" name="sellerCeoEmail" title="<spring:message code='text.srm.field.sellerCeoEmail'/>" value="<c:out value='${srmComp.sellerCeoEmail}'/>"  style="width:90%"/>
					</td>
					<th><label for="vEmail">* <spring:message code='text.srm.field.vEmail'/></label></th><%--spring:message : E-Mail--%>
					<td>
						<input type="text" id="vEmail" name="vEmail" title="<spring:message code='text.srm.field.vEmail'/>" value="<c:out value='${srmComp.vEmail}'/>"  style="width:90%"/>
					</td>
				</tr>
				<tr>
					<th><label for="faxFhone"><spring:message code='text.srm.field.faxFhone'/></label></th><%--spring:message : FAX--%>
					<td colspan="3">
					    <c:if test="${agreeType eq '1'}">
						    <input type="text" id="faxFhone" name="faxFhone" title="<spring:message code='text.srm.field.faxFhone'/>" value="<c:out value='${srmComp.faxFhone}'/>" class="input_txt_default numberic" />
						</c:if>
						<c:if test="${agreeType eq '2'}">
						    <input type="text" id="faxFhone" name="faxFhone" title="<spring:message code='text.srm.field.faxFhone'/>" value="<c:out value='${srmComp.faxFhone}'/>" class="input_txt_default numberic"  disabled/>
						</c:if>
					</td>
					<%--<th><label for="phoneNo">* <spring:message code='text.srm.field.phoneNo'/></label></th>&lt;%&ndash;spring:message : 담당자연락처&ndash;%&gt;--%>
					<%--<td>--%>
					<%--<input type="text" id="phoneNo" name="phoneNo" title="<spring:message code='text.srm.field.phoneNo'/>" value="<c:out value='${srmComp.phoneNo}'/>" class="input_txt_default numberic" />--%>
					<%--</td>--%>
				</tr>
				<tr>
					<th><label for="address2">* <spring:message code='text.srm.field.address'/></label></th><%--spring:message : 주소--%>
					<td colspan="3">
						<c:if test="${srmComp.shipperType eq '1'}"> <%--국내--%>
							<input type="text" id="zipcode" name="zipcode" title="<spring:message code='text.srm.field.address'/>" value="<c:out value='${srmComp.zipcode}'/>" disabled="disabled" readonly="readonly" size="6" style="text-align: center;" />
							<button type="button" onclick="javascript:openZipCodeNew();" class="btn_normal btn_gray"><spring:message code='button.srm.search'/></button>
							<input type="text" id="address1" name="address1" title="<spring:message code='text.srm.field.address'/>" value="<c:out value='${srmComp.address1}'/>" disabled="disabled" readonly="readonly" style="width: 250px;" />
							<input type="text" id="address2" name="address2" title="<spring:message code='text.srm.field.address'/>" value="<c:out value='${srmComp.address2}'/>" style="width: 250px;" />
						</c:if>
						<c:if test="${srmComp.shipperType eq '2'}"> <%--국외--%>
							<input type="text" id="address1" name="address1" title="<spring:message code='text.srm.field.address'/>" value="<c:out value='${srmComp.address1}'/>" style="width: 90%;" />
						</c:if>
					</td>
				</tr>
				<tr>
					<th><label for="businessType">* <spring:message code='text.srm.field.businessType'/></label></th><%--spring:message : 업태--%>
					<td>
						<input type="text" id="businessType" name="businessType" title="<spring:message code='text.srm.field.businessType'/>" value="<c:out value='${srmComp.businessType}'/>" style="width: 90%;" />
					</td>
					<th><label for="industryType">* <spring:message code='text.srm.field.industryType'/></label></th><%--spring:message : 업종--%>
					<td>
						<input type="text" id="industryType" name="industryType" title="<spring:message code='text.srm.field.industryType'/>" value="<c:out value='${srmComp.industryType}'/>" style="width: 90%;" />
					</td>
				</tr>
				<tr>
					<th><label for="sellerType">* <spring:message code='text.srm.field.sellerType'/></label></th><%--spring:message : 사업유형--%>
					<td colspan="3">
						<srm:codeTag comType="SELECT" objId="sellerType" objName="sellerType" formName="" parentCode="M076" width="100px"/>
					</td>
				</tr>
				<tr>
					<th><label for="country"><spring:message code='text.srm.field.country'/></label></th><%--spring:message : 국가--%>
					<td>
						<srm:codeTag comType="SELECT" attr="disabled" objId="country" objName="country" formName="" parentCode="M001" width="100px" sortStd="NAME" />
					</td>
					<th><label for="cityText"><spring:message code='text.srm.field.cityText'/></label></th><%--spring:message : 지역--%>
					<td>
						<%--<input type="text" id="cityText" name="cityText" title="<spring:message code='text.srm.field.cityText'/>" value="<c:out value='${srmComp.cityText}'/>" />--%>
						<srm:codeTag comType="SELECT" objId="cityText" objName="cityText" formName="" parentCode="M004" width="100px"/>
					</td>
				</tr>
			</table>
			<%----- 기업정보 End -------------------------%>

		</div><!-- END Sub Wrap -->
	</div><!--END Container-->

</form>


<form id="hiddenForm" name="hiddenForm" method="post">
	<input type="hidden" id="houseCode" name="houseCode" value="<c:out value="${srmComp.houseCode}"/>"/>
	<input type="hidden" id="sellerCode" name="sellerCode" value="<c:out value="${srmComp.sellerCode}"/>"/>
	<input type="hidden" id="reqSeq" name="reqSeq" value="<c:out value="${srmComp.reqSeq}"/>"/>
</form>

<form id="tstForm" name="tstForm" method="post">
	<input type="hidden" id="tstAgreeType" name="tstAgreeType" value="<c:out value="${agreeType}"/>"/>
</form>

</body>
</html>