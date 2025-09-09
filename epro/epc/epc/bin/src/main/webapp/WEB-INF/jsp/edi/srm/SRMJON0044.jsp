<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%>
<%--
	Page Name 	: SRMJON0044.jsp
	Description : 입점상담신청(해외)
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.11.15 		LEE HYOUNG TAK 	최초생성
--%>

<!doctype html>
<html lang="ko">l>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">

	<title><spring:message code='text.srm.field.srmjon0044.title'/></title><%--spring:message : 입점상담 신청하기--%>

	<script language="JavaScript">
		$(document).ready(function() {
			//저장인경우
			if("<c:out value="${result.message}"/>" != ""){
				if("<c:out value="${result.message}"/>" == "SUCCESS") {
					if("<c:out value="${result.status}"/>" == "T"){
						alert("<spring:message code='msg.srm.alert.saveOk' />");/*spring:message : 저장되었습니다.*/
						$("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0044.do'/>");
					} else {
						alert("<spring:message code="msg.srm.alert.vendorConsultReqConplete"/>");/*spring:message : 저장되었습니다.*/
						$("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMRST0020.do'/>");
					}

					$("#hiddenForm").submit();
				} else {
					errerMessage("<c:out value="${result.message}"/>");
				}
			}


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

			$('.numberic2').css('imeMode','disabled').keydown(function(){
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
				if( $(this).val() != null && $(this).val() != '' ) {
					addComma2($(this));
				}
			}).focus(function(){
				var tmps = $(this).val().replace(/[^0-9]/g, '');
				$(this).val(tmps);
				this.selectionStart = this.selectionEnd = this.value.length;
			});

			//숫자 콤마 SET
			addComma2($('#MyForm input[name=basicAmt]'));
			addComma2($('#MyForm input[name=salesAmt]'));
			addComma2($('#MyForm input[name=empCount]'));

			//커스텀태그 defName Set
			//선택
			var option = "<option value= \"\"><spring:message code='text.srm.field.select' /></option>";
			$(option).prependTo("#MyForm select[name=country]");
			$(option).prependTo("#MyForm select[name=channelCode]");

			//커스텀태그 값SET
			$("#MyForm input:radio[name='shipperType']:radio[value='<c:out value="${srmComp.shipperType}"/>']").attr("checked",true);
			$("#MyForm select[name=country]").val("<c:out value="${srmComp.country}"/>");
			$("#MyForm select[name=channelCode]").val("<c:out value="${srmComp.channelCode}"/>");

			if("<c:out value="${srmComp.onlineMailTarget}"/>" == ""){
				$('input:radio[name=onlineMailTarget]:input[value="MMD"]').attr("checked", true);
			} else {
				$('input:radio[name=onlineMailTarget]:input[value="<c:out value="${srmComp.onlineMailTarget}"/>"]').attr("checked", true);
			}
			
			//판매처 변경시 메일변경대상 display
			$('#channelCode').live('change', function(){
				//온라인몰인경우 메일발송대상 display
				if($(this).val() == "06"){
					$('#mailTargetTR').show();
				} else {
					$('#mailTargetTR').hide();
				}
			});
		});


		//숫자만 입력 reset
		function onlyNum(obj){
			if( $(obj).val() != null && $(obj).val() != '' ) {
				var tmps = $(obj).val().replace(/[^0-9]/g, '');
				$(obj).val(tmps);
			}
		}

		//금액 콤마(소숫점 포함)
		function addComma2(obj) {
			var tmps1;
			var tmps2;
			var tmps3;
			var tmps4;

			if ($(obj).val().indexOf(".") > -1) {

				tmps1 = $(obj).val().split(".");
				tmps2 = tmps1[0].replace(/[^0-9]/g, '');
				tmps3 = tmps2.replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
				tmps4 = tmps1[1].replace(/[^0-9]/g, '');
				$(obj).val(tmps3 + "." + tmps4);

			} else {

				tmps1 = $(obj).val().replace(/[^0-9]/g, '');
				tmps2 = tmps1.replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
				$(obj).val(tmps2);

			}
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

			target = "<spring:message code='text.srm.field.catLv1Code'/>";	// 대분류
			if (!$.trim($('#catLv1Code').val())) {
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 대분류을(를) 선택하세요. */
				$('#catLv1Code').focus();
				return false;
			}

			<%--target = "<spring:message code='text.srm.field.tempPw'/>";	// 비밀번호--%>
			<%--if (!$.trim($('#tempPw').val())) {--%>
				<%--alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 비밀번호을(를) 입력하세요. */--%>
				<%--$('#tempPw').focus();--%>
				<%--return false;--%>
			<%--}--%>

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

			target = "<spring:message code='text.srm.field.vEmail'/>";	// 이메일
			if (!$.trim($('#vEmail').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 이메일을(를) 입력하세요. */
				$('#vEmail').focus();
				return false;
			}

			//Email 검증
			var regVar = "^[a-zA-Z0-9!$&*.=^`|~#%'+\/?_{}-]+@([a-zA-Z0-9_-]+\.)+[a-zA-Z]{2,4}$";
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
			if (!$.trim($('#faxFhone').val())) {
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 주소을(를) 입력하세요. */
				$('#faxFhone').focus();
				return false;
			}

			if (!cal_3byte($('#faxFhone').val(), '31', setPermitMsg(target), setByteMsg(target, '31'))) {
				$('#faxFhone').focus();
				return false;
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

			target = "<spring:message code='text.srm.field.country'/>";	// 국가
			if (!$.trim($('#country').val())) {
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 국가을(를) 선택하세요. */
				$('#country').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.foundationDate'/>";	// 설립년도
			if(!$.trim($('#MyForm select[name=foundationDate]').val())){
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 설립년도을(를) 선택하세요. */
				$('#MyForm select[name=foundationDate]').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.basicAmt'/>";	// 자본금
			if(!$.trim($('#MyForm input[name=basicAmt]').val())){
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 자본금을(를) 입력하세요. */
				$('#MyForm input[name=basicAmt]').focus();
				return false;
			}

			if (!cal_3byte($('#MyForm input[name=basicAmt]').val().replaceAll(",",""), '10', setPermitMsg(target), setByteMsg(target, '10'))) {
				$('#MyForm input[name=basicAmt]').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.salesAmt'/>";	// 연간 매출액
			if(!$.trim($('#MyForm input[name=salesAmt]').val())){
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 연간 매출액을(를) 입력하세요. */
				$('#MyForm input[name=salesAmt]').focus();
				return false;
			}

			if (!cal_3byte($('#MyForm input[name=salesAmt]').val().replaceAll(",",""), '10', setPermitMsg(target), setByteMsg(target, '10'))) {
				$('#MyForm input[name=salesAmt]').focus();
				return false;
			}

			target = "<spring:message code='text.srm.field.empCount'/>";	// 종업원수
			if(!$.trim($('#MyForm input[name=empCount]').val())){
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 종업원수을(를) 입력하세요. */
				$('#MyForm input[name=empCount]').focus();
				return false;
			}

			if (!cal_3byte($('#MyForm input[name=empCount]').val().replaceAll(",",""), '10', setPermitMsg(target), setByteMsg(target, '10'))) {
				$('#MyForm input[name=empCount]').focus();
				return false;
			}

			<%--target = "<spring:message code='text.srm.field.onlineMailTarget'/>";	// 종업원수--%>
			<%--if(!$.trim($('#MyForm select[name=onlineMailTarget]').val()) && "<c:out value="${srmComp.channelCode}"/>" == "06"){--%>
				<%--alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 종업원수을(를) 입력하세요. */--%>
				<%--$('#MyForm input[name=onlineMailTarget]').focus();--%>
				<%--return false;--%>
			<%--}--%>


			return true;
		}

		//임시저장
		function doSave(status){

			if (!validation())return;
			if(status == "T"){
				if(!confirm("<spring:message code='msg.srm.alert.tempSave'/>")) return;/*spring:message : 저장하시겠습니까?*/
			} else if(status == "S"){
				if(!confirm("<spring:message code="msg.srm.alert.vendorConsultReq"/>")) return;/*spring:message : 입점상담신청을 완료 하시겠습니까?*/
			} else {
				return;
			}

			var saveInfo = {};
			<%--$('#MyForm').find("input, select").each(function() {--%>
				<%--if (this.type != "button" && this.type != "radio") {--%>
					<%--saveInfo[this.name] = $(this).val();--%>
				<%--}--%>
			<%--});--%>

			//해외업체구분
			saveInfo["shipperType"] = $(':radio[name="shipperType"]:checked').val();
			saveInfo["reqSeq"] = '<c:out value="${srmComp.reqSeq}"/>';
			saveInfo["sellerCode"] = '<c:out value="${srmComp.sellerCode}"/>';
			saveInfo["houseCode"] = '<c:out value="${srmComp.houseCode}"/>';
			saveInfo["channelCode"] = $('#MyForm select[name=channelCode]').val();
			saveInfo["catLv1Code"] = $('#MyForm input[name=catLv1Code]').val();
			if ('<c:out value="${srmComp.tempPw}"/>' == saveInfo["tempPw"]) {
				saveInfo["tempPw"] = "";
			}

			saveInfo["status"] = status;

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'post',
				dataType : 'json',
				async : false,
				url : '<c:url value="/edi/srm/selectGlobalHiddenCompInfoCheck.json"/>',
				data : JSON.stringify(saveInfo),
				success : function(data) {
					if (data.message == "SUCCESS") {
						$('#MyForm input[name=irsNo]').removeAttr('disabled');
						$('#MyForm input[name=shipperType]').removeAttr('disabled');
						$('#MyForm input[name=status]').val(status);

//			$('#MyForm input[name=shipperType').val($(':radio[name="shipperTypeRadio"]:checked').val(););

						$("#MyForm").attr("action", "<c:url value='/edi/srm/updateGlobalHiddenCompInfo.do'/>");
						$("#MyForm").submit();
						<%--if(status == "T"){--%>
							<%--alert("<spring:message code='msg.srm.alert.saveOk' />");/*spring:message : 저장되었습니다.*/--%>
							<%--$('#hiddenForm input[name=houseCode]').val("<c:out value='${srmComp.houseCode}'/>");--%>
							<%--$('#hiddenForm input[name=sellerCode]').val("<c:out value='${srmComp.sellerCode}'/>");--%>
							<%--$('#hiddenForm input[name=reqSeq]').val(data.reqSeq);--%>
							<%--$("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMJON0044.do'/>");--%>
							<%--$("#hiddenForm").submit();--%>
						<%--} else if(status == "S"){--%>
							<%--alert('<spring:message code="msg.srm.alert.vendorConsultReqConplete"/>');/*spring:message : 입점상담신청이 정상적으로 신청완료 되었습니다.*/--%>
							<%--$("#hiddenForm").attr("action", "<c:url value='/edi/srm/SRMRST0020.do'/>");--%>
							<%--$("#hiddenForm").submit();--%>
						<%--}--%>

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
			} else if(message == "FAIL-CAT_LV1_CODE_DEL"){
				alert("<spring:message code='msg.srm.alert.validation.catLv1Code'/>");/* spring:message : 삭제된 대분류 입니다.\n 대분류를 확인해 주세요. */
			} else if (message == "FAIL-CHANNEL_CODE") {
				//해외업체구분
				var target = "<spring:message code='text.srm.field.channelCode'/>";
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 판매처을(를) 선택하세요. */
			} else if (message == "FAIL-CAT_LV1_CODE") {
				//분류
				var target = "<spring:message code='text.srm.field.catLv1Code'/>";
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 대분류을(를) 선택하세요. */
			} else if (message == "FAIL-SHIPPER_TYPE") {
				//해외업체구분
				var target = "<spring:message code='text.srm.field.shipperType'/>";
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 해외업체구분을(를) 선택하세요. */
			} else if (message == "FAIL-TEMP_PW") {
				//비밀번호
				var target = "<spring:message code='text.srm.field.tempPw'/>";
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 비밀번호을(를) 입력하세요. */
			} else if (message == "FAIL-SELLER_NAME_LOC") {
				//상호명
				var target = "<spring:message code='text.srm.field.sellerNameLoc'/>";
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 사업자명을(를) 입력하세요. */
			} else if (message == "FAIL-SELLER_CEO_NAME") {
				//대표자명
				var target = "<spring:message code='text.srm.field.sellerCeoName'/>";
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 대표자명을(를) 입력하세요. */
			} else if (message == "FAIL-V_MAIN_NAME") {
				//담당자명
				var target = "<spring:message code='text.srm.field.vMainName'/>";
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 담당자명을(를) 입력하세요. */
			} else if (message == "FAIL-V_PHONE1") {
				//대표전화
				var target = "<spring:message code='text.srm.field.vPhone1'/>";
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 대표전화을(를) 입력하세요. */
			} else if (message == "FAIL-V_EMAIL") {
				//E-mail
				var target = "<spring:message code='text.srm.field.vEmail'/>";
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 이메일을(를) 입력하세요. */
			} else if (message == "FAIL-ADDRESS") {
				//주소
				var target = "<spring:message code='text.srm.field.address'/>";
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 주소을(를) 입력하세요. */
			} else if (message == "FAIL-FOUNDATION_DATE") {
				//설립년도
				var target = "<spring:message code='text.srm.field.foundationDate'/>";
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 설립년도을(를) 선택하세요. */
			} else if (message == "FAIL-BASIC_AMT") {
				//자본금
				var target = "<spring:message code='text.srm.field.basicAmt'/>";
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 자본금을(를) 입력하세요. */
			} else if (message == "FAIL-SALES_AMT") {
				//연간 매출액
				var target = "<spring:message code='text.srm.field.salesAmt'/>";
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 연간 매출액을(를) 입력하세요. */
			} else if (message == "FAIL-EMP_COUNT") {
				//종업원수
				var target = "<spring:message code='text.srm.field.empCount'/>";
				alert("<spring:message code='msg.srm.alert.text' arguments='"+target+"'/>");/* spring:message : 종업원수을(를) 입력하세요. */
			} else if (message == "FAIL-ONLINE_MAIL_TARGET") {
				//온라인몰 메일 발송대상
				var target = "<spring:message code='text.srm.field.onlineMailTarget'/>";
				alert("<spring:message code='msg.srm.alert.select' arguments='"+target+"'/>");/* spring:message : 온라인몰 메일 발송대상을(를) 선택하세요. */
			} else {
				alert('<spring:message code="msg.common.fail.request"/>');/*spring:message : 요청처리를 실패하였습니다.*/
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

		//첨부파일 행 추가
		function addAttachFileRow(){
			$('#addAttachFileTemplate1').tmpl().appendTo("#attachFileTbody");
		}

		//파일선택시
		function fileUpload(obj, inputName) {
			$(obj).parent().parent().find("#"+inputName).text($(obj).val());
			$(obj).parent().parent().find("#docSeq").val("");
		}

		//파일초기화
		function fileClear(obj, file, fileNm) {
			$(obj).parent().parent().find("#"+file).val("");
			$(obj).parent().parent().find("#"+fileNm).val("");
		}

		//파일삭제
		function fileDelete(obj) {
			$(obj).parent().parent().parent().remove();
			if($("#attachFileTbody tr").length == 0 ) {
				addAttachFileRow();
			};
		}

		//파일 다운로드
		function downloadFile(fileId, fileSeq) {
			$('#fileForm input[name=atchFileId]').val(fileId);
			$('#fileForm input[name=fileSn]').val(fileSeq);
			$('#fileForm').attr("action", '<c:url value="/edi/srm/FileDown.do"/>')
			$('#fileForm').submit();
		}
	</script>

<script id="addAttachFileTemplate1" type="text/x-jquery-tmpl">
	<tr>
		<td>
		</td>
		<td>
			<input type="hidden" id="docNo" name="docNo" value=""/>
			<input type="hidden" id="docSeq" name="docSeq" value=""/>
			<input type="file" id="companyIntroAttachNoFile" name="companyIntroAttachNoFile" title="" onchange="javascript:fileUpload(this, 'companyIntroAttachNoFileName');"/>
			<btn>
				<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear(this, 'companyIntroAttachNoFile','companyIntroAttachNoFileName')"/><%--spring:message : 취소--%>
				<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete(this);"/><%--spring:message : 삭제--%>
			<btn>
		</td>
	</tr>
</script>

</head>


<body>
<form id="MyForm" name="MyForm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="reqSeq" name="reqSeq" value="<c:out value="${srmComp.reqSeq}"/>"/>
	<input type="hidden" id="status" name="status"/>
	<input type="hidden" id="companyIntroAttachNo" name="companyIntroAttachNo" value="<c:out value="${srmComp.companyIntroAttachNo}"/>"/>
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
					<li><em><spring:message code="text.srm.field.srmjon0040Notice3"/></em></li>	<%-- 신용평가기관에 롯데마트 정보동의를 요청하셔야 정보확인이 가능합니다.(요청 후 익일 확인가능) --%>
					<li><em><spring:message code="text.srm.field.srmjon0040Notice4"/></em></li><%--사업자명은 사업자등록증의 명칭 그대로 작성해주세요.<br>예) 주식회사->(주) : 줄여쓰기 오류 | 롯데마트 : 롯 데 마 트 : 띄어쓰기 오류--%>
					<li><em><spring:message code="text.srm.field.srmjon0040Notice5"/></em></li><%--업태와 업종(=종목)은 사업자 등록증의 명시되어 있는 항목 중 당사와 거래할 주요 항목으로 작성해 주세요.--%>
				</ul>
			</div><!-- END 알림 -->


			<%----- 상담채널 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code="text.srm.field.srmjon0044.sub.title1"/></h3>	<%-- 입점신청--%>
				<div class="right_btns">
					<button type="button" class="btn_normal btn_gray" onclick="javascript:doSave('T');"><spring:message code='button.srm.tempSave'/></button>	<%-- 임시저장--%>
					<button type="button" id="btnAddFile" 	name="btnAddFile"	class="btn_normal"			onclick="javascript:addAttachFileRow();" ><spring:message code='button.srm.addFile'/></button>	<%--첨부파일추가 --%>
					<c:if test="${not empty srmComp.reqSeq}">
						<button type="button" class="btn_normal btn_blue" onclick="javascript:doSave('S');"><spring:message code='button.srm.vendorConsultReq'/></button>	<%-- 임시저장--%>
						<button type="button" class="btn_normal btn_red" onclick="javascript:doCancel();"><spring:message code='button.srm.vendorConsultReqCancel'/></button>	<%-- 입점상담신청 취소--%>
					</c:if>
				</div>
			</div><!-- END 상단 버튼이 있을 경우 타이틀 -->

			<!-- END 버튼이 없을 경우 타이틀 -->

			<!-- 정보 입력폼 -->
			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="20%"/>
					<col width="30%"/>
					<col width="20%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr id="mailTargetTRappend">
						<th>
							*<label for="channelCode"><spring:message code='text.srm.field.classification'/></label> :<%--spring:message : 상담신청 분류--%>
						</th>
						<td>
							<srm:codeTag comType="SELECT" objId="channelCode" objName="channelCode" formName="" parentCode="SRM053" />
						</td>
						<th>*<label for="catLv1Code"><spring:message code='text.srm.field.catLv1Code'/></label></th>
						<td>
							<input type="hidden"	id="catLv1Code" 	name="catLv1Code" 		value="<c:out value="${srmComp.catLv1Code}"/>" />
							<input type="text"		id="catLv1CodeNm" name="catLv1CodeNm"	value="<c:out value="${srmComp.catLv1CodeNm}"/>"class="input_txt_default"  disabled readonly/>

							<button type="button" class="btn_normal btn_blue ml5" onclick="javascript:doSearch();"><spring:message code='button.srm.search'/></button>	<%-- 검색 --%>
						</td>
					</tr>

					
					<tr id="mailTargetTR" <c:if test="${srmComp.channelCode ne '06'}">style="display:none;"</c:if>>
						<%--<th><label for="localFoodYn"><spring:message code='text.srm.field.localFoodYn'/></label></th>&lt;%&ndash;spring:message : 로컬푸드유무&ndash;%&gt;--%>
						<%--<td <c:if test="${srmComp.channelCode ne '06'}"> colspan="3" </c:if>>--%>
							<%--&lt;%&ndash;<input type="checkbox" id="" name=""/>&ndash;%&gt;--%>
							<%--<input type="radio" id="localFoodYn" name="localFoodYn" value="Y" <c:if test="${srmComp.localFoodYn eq 'Y'}">checked</c:if>/> Yes--%>
							<%--<input type="radio" id="localFoodYn" name="localFoodYn" value="N" <c:if test="${srmComp.localFoodYn ne 'Y'}">checked</c:if>/> No--%>
						<%--</td>--%>
						<th><label for="onlineMailTarget">* <spring:message code='text.srm.field.onlineMailTarget'/></label></th><%--spring:message : 온라인몰메일전송대상--%>
						<td colspan="3">
							<srm:codeTag comType="RADIO" objId="onlineMailTarget" objName="onlineMailTarget" formName="" parentCode="SRM086" width="100px"/>
						</td>
					</tr>
					
					<tr>
						<th><label for="shipperType">* <spring:message code='text.srm.field.shipperType'/></label></th><%--spring:message : 해외업체구분--%>
						<td colspan="3">
							<%--<input type="hidden" id="shipperType" name="shipperType"/>--%>
							<srm:codeTag comType="RADIO" objId="shipperType" objName="shipperType" formName="" parentCode="M025" attr="disabled=disabled" />
						</td>
					</tr>
					<tr>
						<th><label for="irsNo1">* <spring:message code='text.srm.field.irsNo'/></label></th><%--spring:message : 사업자 등록번호--%>
						<td>
							<input type="text" id="irsNo" name="irsNo" title="<spring:message code='text.srm.field.irsNo'/>" value="<c:out value='${srmLoginSession.irsNo}'/>" style="text-align: center;" disabled="disabled" readonly="readonly" />
						</td>
						<th><label for="tempPw">* <spring:message code='text.srm.field.tempPw'/></label></th><%--spring:message : 비밀번호--%>
						<td>
							<input type="password" id="tempPw" name="tempPw" title="<spring:message code='text.srm.field.tempPw'/>" value=""  maxlength="15" style="width:90%"/>
						</td>
					</tr>
					<tr>
						<th><label for="sellerNameLoc">* <spring:message code='text.srm.field.sellerNameLoc'/></label></th><%--spring:message : 상호명--%>
						<td colspan="3">
							<input type="text" id="sellerNameLoc" name="sellerNameLoc" title="<spring:message code='text.srm.field.sellerNameLoc'/>" value="<c:out value='${srmComp.sellerNameLoc}'/>" style="width: 32.3%;" />
						</td>
					</tr>
					<tr>
						<th><label for="sellerCeoName">* <spring:message code='text.srm.field.sellerCeoName'/></label></th><%--spring:message : 대표자명--%>
						<td>
							<input type="text" id="sellerCeoName" name="sellerCeoName" title="<spring:message code='text.srm.field.sellerCeoName'/>" value="<c:out value='${srmComp.sellerCeoName}'/>" style="width: 90%;"/>
						</td>
						<th><label for="vMainName">* <spring:message code='text.srm.field.vMainName'/></label></th><%--spring:message : 담당자명--%>
						<td>
							<input type="text" id="vMainName" name="vMainName" title="<spring:message code='text.srm.field.vMainName'/>" value="<c:out value='${srmComp.vMainName}'/>" style="width: 90%;"/>
						</td>
					</tr>
					<tr>
						<th><label for="vPhone1">* <spring:message code='text.srm.field.vPhone1'/></label></th><%--spring:message : 대표전화--%>
						<td>
							<input type="text" id="vPhone1" name="vPhone1" title="<spring:message code='text.srm.field.vPhone1'/>" value="<c:out value='${srmComp.vPhone1}'/>" class="input_txt_default numberic" style="width: 90%;"/>
						</td>
						<th><label for="vEmail">* <spring:message code='text.srm.field.vEmail'/></label></th><%--spring:message : E-Mail--%>
						<td>
							<input type="text" id="vEmail" name="vEmail" title="<spring:message code='text.srm.field.vEmail'/>" value="<c:out value='${srmComp.vEmail}'/>"  style="width:90%"/>
						</td>
					</tr>
					<tr>
						<th>* <label for="faxFhone"><spring:message code='text.srm.field.faxFhone'/></label></th><%--spring:message : FAX--%>
						<td colspan="3">
							<input type="text" id="faxFhone" name="faxFhone" title="<spring:message code='text.srm.field.faxFhone'/>" value="<c:out value='${srmComp.faxFhone}'/>" class="input_txt_default numberic" style="width: 32.3%;"/>
						</td>
					</tr>
					<tr>
						<th><label for="address1">* <spring:message code='text.srm.field.address'/></label></th><%--spring:message : 주소--%>
						<td colspan="3">
							<%--<input type="text" id="zipcode" name="zipcode" title="<spring:message code='text.srm.field.zipcode'/>" value="<c:out value='${srmComp.zipcode}'/>" style="width: 15%;" />--%>
							<input type="text" id="address1" name="address1" title="<spring:message code='text.srm.field.address'/>" value="<c:out value='${srmComp.address1}'/>" style="width: 80%;" />
						</td>
					</tr>
					<tr>
						<th>* <label for="country"><spring:message code='text.srm.field.country'/></label></th><%--spring:message : 국가--%>
						<td colspan="3">
							<srm:codeTag comType="SELECT" attr="disabled" objId="country" objName="country" formName="" parentCode="M001" width="100px" sortStd="NAME" />
						</td>
					</tr>
					<tr>
						<th><label for="foundationDate">* <spring:message code='text.srm.field.foundationDate'/></label></th><%--spring:message : 설립년도--%>
						<td>
							<c:set var="fromYear" value="1900"/>
							<c:set var="toYear" value="2100"/>

							<select id="foundationDate" name="foundationDate" style="width:100px;">
								<option value=""><spring:message code='text.srm.field.select'/></option>
								<c:forEach var="year" begin="${fromYear}" end="${toYear}" varStatus="status">
									<option value="<c:out value="${year}"/>" <c:if test="${year eq srmComp.foundationDate}"> selected</c:if>> <c:out value="${year}"/></option>
								</c:forEach>
							</select>
						</td>
						<th><label for="basicAmt">* <spring:message code='text.srm.field.basicAmt'/></label></th><%--spring:message : 자본금--%>
						<td>
							<input type="text" id="basicAmt" name="basicAmt" title="<spring:message code='text.srm.field.basicAmt'/>" value="<c:out value='${srmComp.basicAmt}'/>" class="input_txt_default numberic2" style="text-align: right;" /> <span class="ml10"><spring:message code='text.srm.field.unit'/></span><%--spring:message : (단위:만원)--%>
						</td>
					</tr>
					<tr>
						<th><label for="salesAmt">* <spring:message code='text.srm.field.salesAmt'/></label></th><%--spring:message : 연간 매출액(최근 3년)--%>
						<td>
							<input type="text" id="salesAmt" name="salesAmt" title="<spring:message code='text.srm.field.salesAmt'/>" value="<c:out value='${srmComp.salesAmt}'/>" class="input_txt_default numberic2" style="text-align: right;" /> <span class="ml10"><spring:message code='text.srm.field.unit'/></span><%--spring:message : (단위:만원)--%>
						</td>
						<th><label for="empCount">* <spring:message code='text.srm.field.empCount'/></label></th><%--spring:message : 종업원 수(정규직)--%>
						<td>
							<input type="text" id="empCount" name="empCount" title="<spring:message code='text.srm.field.empCount'/>" value="<c:out value='${srmComp.empCount}'/>" class="input_txt_default numberic" style="text-align: right;" /> <spring:message code='text.srm.field.persons'/><%--spring:message : 명--%>
						</td>
					</tr>
					<tr>
						<th><label for="plantOwnType">* <spring:message code='text.srm.field.plantOwnType'/></label></th><%--spring:message : 생산공장유무--%>
						<td colspan="3">
							<input type="radio" id="plantOwnType" name="plantOwnType" title="<spring:message code='text.srm.field.plantOwnType'/>" value="X" <c:if test="${srmComp.plantOwnType eq 'X'}">checked</c:if>/> Yes
							<input type="radio" id="plantOwnType" name="plantOwnType" title="<spring:message code='text.srm.field.plantOwnType'/>" value="" <c:if test="${srmComp.plantOwnType ne 'X'}">checked</c:if>/> No
						</td>
					</tr>
					<tr>
						<th><label for=""><spring:message code='text.srm.field.attachNo'/></label></th>	<%--첨부파일 --%>
						<td colspan="3">
							<table width="100%">
								<colgroup>
									<col width="50%"/>
									<col width="*"/>
								</colgroup>
								<tbody id="attachFileTbody">
									<c:forEach var="list" items="${srmComp.companyIntroAttachNoFileList}" varStatus="status">
										<tr>
											<td>
												<div>
													<a id="companyIntroAttachNoA" name="companyIntroAttachNoA" href="#" onclick="javascript:downloadFile('<c:out value="${list.fileId}"/>', '<c:out value="${list.fileSeq}"/>');"><c:out value="${list.fileNmae}"/></a>
												</div>
											</td>
											<td>
												<input type="file" id="companyIntroAttachNoFile" name="companyIntroAttachNoFile" title="" onchange="javascript:fileUpload(this, 'companyIntroAttachNoFileName');"/>
												<input type="hidden" id="docNo" name="docNo" value="<c:out value="${list.fileId}"/>"/>
												<input type="hidden" id="docSeq" name="docSeq" value="<c:out value="${list.fileSeq}"/>"/>
												<btn>
													<button type="button" id="" name="" class="btn_normal btn_gray" onclick="javascript:fileClear(this, 'companyIntroAttachNoFile','companyIntroAttachNoFileName')" ><spring:message code='button.srm.cancel'/></button>	<%--취소 --%>
													<button type="button" id="" name="" class="btn_normal btn_red"  onclick="javascript:fileDelete(this);" ><spring:message code='button.srm.delete'/></button>	<%--삭제 --%>
												</btn>
												<%--<div>--%>
													<%--<a id="companyIntroAttachNoA" name="companyIntroAttachNoA" href="#" onclick="javascript:downloadFile('<c:out value="${list.fileId}"/>', '<c:out value="${list.fileSeq}"/>');"><c:out value="${list.fileNmae}"/></a>--%>
												<%--</div>--%>
											</td>
										</tr>
									</c:forEach>
									<tr>
										<td>
											<div id="companyIntroAttachNoFileName" name="companyIntroAttachNoFileName">

											</div>
										</td>
										<td>
											<%--<input type="hidden" id="companyIntroAttachNoFileName" name="companyIntroAttachNoFileName" title="" class="input_txt_default" disabled="disabled" readonly="readonly"/>--%>
											<input type="file" id="companyIntroAttachNoFile" name="companyIntroAttachNoFile" title="" onchange="javascript:fileUpload(this, 'companyIntroAttachNoFileName');"/>
											<input type="hidden" id="docNo" name="docNo"/>
											<input type="hidden" id="docSeq" name="docSeq"/>
											<btn>
												<button type="button" id="" name="" class="btn_normal btn_gray" onclick="javascript:fileClear(this, 'companyIntroAttachNoFile','companyIntroAttachNoFileName')" ><spring:message code='button.srm.cancel'/></button>	<%--취소 --%>
												<button type="button" id="" name="" class="btn_normal btn_red"  onclick="javascript:fileDelete(this);" ><spring:message code='button.srm.delete'/></button>	<%--삭제 --%>
											</btn>
										</td>
									</tr>
								</tbody>
							</table>
							<%--<div style="color:red;"><spring:message code='text.srm.field.srmjon0042Notice1'/></div>&lt;%&ndash;spring:message : ※ JPG, GIF, PNG, BMP 이미지 파일로 업로드 하세요!&ndash;%&gt;--%>
						</td>
					</tr>
				</tbody>
			</table><!-- END 정보 입력폼 -->
			<%----- 상담채널 End -------------------------%>



		</div><!-- END Sub Wrap -->
	</div><!--END Container-->

</form>


<form id="hiddenForm" name="hiddenForm" method="post">
	<input type="hidden" id="houseCode" name="houseCode" value="<c:out value="${srmComp.houseCode}"/>"/>
	<input type="hidden" id="sellerCode" name="sellerCode" value="<c:out value="${srmComp.sellerCode}"/>"/>
	<input type="hidden" id="reqSeq" name="reqSeq" value="<c:out value="${srmComp.reqSeq}"/>"/>
</form>


<form id="fileForm" name="fileForm" method="post">
	<input type="hidden" id="atchFileId" name="atchFileId"/>
	<input type="hidden" id="fileSn" name="fileSn"/>
</form>

</body>
</html>