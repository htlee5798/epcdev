<%@ page  pageEncoding="UTF-8"%>
<%@ include file="/common/edi/taglibs.jsp"%> 
<%--
	Page Name 	: SRMEVL0050.jsp
	Description : 평가총평
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.11 		LEE HYOUNG TAK 	최초생성
--%>

<!doctype html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title><spring:message code="text.srm.field.srmevl0050.title"/></title><%--spring:message : 품질경영평가--%>

<script language="JavaScript">
	$(document).ready(function() {
        //임시저장인경우
        if("<c:out value="${tempYn}"/>" == "N") {
			alert("<spring:message code='msg.srm.alert.saveOk' />");/*spring:message : 저장되었습니다.*/
            $("#MyForm").attr("action", "<c:url value='/edi/evl/SRMEVL0050.do'/>");
            $("#MyForm").submit();
            
        } else if("<c:out value="${tempYn}"/>" == "Y") {
        	alert("<spring:message code='msg.srm.alert.evalConpleteOk' />");/*spring:message : 평가가 완료 되었습니다. */
			if("<c:out value="${vo.evalFlag}"/>" == "0"){
				$("#MyForm").attr("action", "<c:url value='/edi/evl/SRMEVL0030.do'/>");
			} else {
				$("#MyForm").attr("action", "<c:url value='/edi/evl/SRMEVL0060.do'/>");
			}
            $("#MyForm").submit();
        }
        
		//숫자만 입력
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

		
		/* 참석자 체크박스 All */
		$('#attendantsAll').live("change", function(){

			if($(this).is(":checked")){
				$('#attendantsTbody tr').each(function(i){
					$(this).find("td").last().find("input").attr("checked", true);
				});
			} else {
				$('#attendantsTbody tr').each(function(i){
					$(this).find("td").last().find("input").attr("checked", false);
				});
			}
		});
		
		/* 개선조치 체크박스 All */
		$('#improvingAll').live("change", function(){

			if($(this).is(":checked")){
				$('#improvingTbody tr').each(function(i){
					$(this).find("td").last().find("input").attr("checked", true);
				});
			} else {
				$('#improvingTbody tr').each(function(i){
					$(this).find("td").last().find("input").attr("checked", false);
				});
			}
		});

		<%--if("<c:out value="${siteVisit1.evGrade}"/>" == ""){--%>
			<%--if("<c:out value="${siteVisit1.evScorePer}"/>" != "0") {--%>
				<%--if(<c:out value="${siteVisit1.evScorePer}"/> >= 95 && 100 >= <c:out value="${siteVisit1.evScorePer}"/>) {--%>
					<%--$('#evGrade').val("S");--%>
				<%--} else if(<c:out value="${siteVisit1.evScorePer}"/> >= 80 && 94 >= <c:out value="${siteVisit1.evScorePer}"/>) {--%>
					<%--$('#evGrade').val("A");--%>
				<%--} else if(<c:out value="${siteVisit1.evScorePer}"/> >= 70 && 79 >= <c:out value="${siteVisit1.evScorePer}"/>) {--%>
					<%--$('#evGrade').val("B");--%>
				<%--} else if(<c:out value="${siteVisit1.evScorePer}"/> >= 55 && 69 >= <c:out value="${siteVisit1.evScorePer}"/>) {--%>
					<%--$('#evGrade').val("C");--%>
				<%--} else if(55 > <c:out value="${siteVisit1.evScorePer}"/>) {--%>
					<%--$('#evGrade').val("D");--%>
				<%--}--%>
			<%--} else {--%>
				<%--$('#evGrade').val("D");--%>
			<%--}--%>
		<%--}--%>

	});


	//숫자만 입력 reset
	function onlyNum(obj){
		if( $(obj).val() != null && $(obj).val() != '' ) {
			var tmps = $(obj).val().replace(/[^0-9]/g, '');
			$(obj).val(tmps);
		}
	}



	//상세보기 팝업
	function doDetail(){
		var cw=950;
		var ch=650;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open("","detailPopup","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
		$("#MyForm").attr("target", "detailPopup");
		$("#MyForm").attr("action", "<c:url value='/edi/evl/selectQualityEvaluationSiteVisitDetailPopup.do'/>");
		$("#MyForm").submit();
		$("#MyForm").attr("target", "_self");
	}

	//결과보고서 팝업
	function doReport(){
		var cw=950;
		var ch=650;
		var sw=screen.availWidth;
		var sh=screen.availHeight;
		var px=Math.round((sw-cw)/2);
		var py=Math.round((sh-ch)/2);
		window.open("","reportPopup","left="+px+",top="+py+",width="+cw+",height="+ch+",toolbar=no,menubar=no,status=yes,resizable=no,scrollbars=yes");
		$("#MyForm").attr("target", "reportPopup");
		$("#MyForm").attr("action", "<c:url value='/edi/evl/selectQualityEvaluationSiteVisitReportPopup.do'/>");
		$("#MyForm").submit();
		$("#MyForm").attr("target", "_self");
	}
	
	//참석자 행 No 정리
	function attendantsRowNo(){
		$('#attendants tbody tr').each(function(i){
			$(this).find("td").eq(0).html(i+1);
		});
	}

	//참석자 행 추가
	function addAttendantsRow(){
		$('#addAttendantTemplate').tmpl().appendTo("#attendantsTbody");
		attendantsRowNo();
		onlyNum();
	}

	//참석자 행 삭제
	function delAttendantsRow(obj){
		var html = "";
		$('#attendantsTbody tr').each(function(i){
			if($(this).find("td").eq(6).find("input").is(":checked")) {
				$(this).remove();
			}
		});

		if($("#attendantsTbody tr").length == 0 ) {
			addAttendantsRow();
		}
		
		$("#attendantsAll").attr("checked",false);
		attendantsRowNo();
	}
	
	//개선조치 행 추가
	function addImprovingRow(){
		$('#addImprovingTemplate').tmpl().appendTo("#improvingTbody");
	}

	//개선조치 행 삭제
	function delImprovingRow(){
		var html = "";
		$('#improvingTbody tr').each(function(i){
			if($(this).find("td").eq(2).find("input").is(":checked")) {
				$(this).remove();
			}
		});

		if($("#improvingTbody tr").length == 0 ) {
			addImprovingRow();
		}
		
		$("#improvingAll").attr("checked", false);
	}

	//첨부파일 행 추가
	function addAttachFileRow(){
		$('#addAttachFileTemplate').tmpl().appendTo("#attachFileTbody");
	}

	//파일선택시
	function fileUpload(obj, inputName) {
		$(obj).parent().find("#"+inputName).val($(obj).val());
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
		$('#fileForm').attr("action", '<c:url value="/edi/evl/FileDown.do"/>')
		$('#fileForm').submit();
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
    function validation() {
    	
    	var regVar = "^[a-zA-Z0-9!$&*.=^`|~#%'+\/?_{}-]+@([a-zA-Z0-9_-]+\.)+[a-zA-Z]{2,4}$"; // 이메일 형식
    	
    	/*-------------------------------------------- 감사자 정보 --------------------------------------------*/
    	var target = "<spring:message code='text.srm.field.evCtrlDate' />";	// 감사일
		if(!$.trim($('#MyForm input[name=evCtrlDate]').val().replaceAll("-",""))) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");/*spring:message : 감사일을(를) 입력하세요.*/
			$('#MyForm input[name=evCtrlDate]').focus();
			return false;
		}
    	
		target = "<spring:message code='text.srm.field.evCtrlName' />";	// 심사원 성명
		if(!$.trim($('#MyForm input[name=evCtrlName]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");/*spring:message : 심사원 성명을(를) 입력하세요.*/
			$('#MyForm input[name=evCtrlName]').focus();
			return false;
		}
		
		if(!cal_3byte($('#MyForm input[name=evCtrlName]').val(), '100', setPermitMsg(target), setByteMsg(target, '100'))) {
			$('#MyForm input[name=evCtrlName]').focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.evCtrlPhone' />";	// 심사원 연락처
		if(!$.trim($('#MyForm input[name=evCtrlPhone]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");/*spring:message : 심사원 연락처을(를) 입력하세요.*/
			$('#MyForm input[name=evCtrlPhone]').focus();
			return false;
		}
		
		if(!cal_3byte($('#MyForm input[name=evCtrlPhone]').val(), '20', setPermitMsg(target), setByteMsg(target, '20'))) {
			$('#MyForm input[name=evCtrlPhone]').focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.evCtrlEmail' />";	// 심사원 이메일
		if(!$.trim($('#MyForm input[name=evCtrlEmail]').val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "' />");/*spring:message : 심사원 이메일을(를) 입력하세요.*/
			$('#MyForm input[name=evCtrlEmail]').focus();
			return false;
		}
		
		if(new RegExp(regVar).test($('#MyForm input[name=evCtrlEmail]').val()) == false) {
			alert("<spring:message code='msg.srm.alert.validation.vEmail'/>");/*spring:message : 이메일 형식이 아닙니다.*/
			$('#MyForm input[name=evCtrlEmail]').focus();
			return false;
		}
		
		if(!cal_3byte($('#MyForm input[name=evCtrlEmail]').val(), '100', setPermitMsg(target), setByteMsg(target, '100'))) {
			$('#MyForm input[name=evCtrlEmail]').focus();
			return false;
		}
		/*-------------------------------------------------------------------------------------------------*/
		
		/*---------------------------------------------- 참석자 ----------------------------------------------*/
        var phoneCheck = true;
        var emailCheck = true;
        var phoneByteCheck = true;
        var emailByteCheck = true;
		var nullCheck = true;

        var idx = 0;
		var nullName = "";
        $('#attendants tbody tr').each(function(i){

        	<%--
            if(phoneNumCheck($(this).find("input[name=evPartPhones]").val()) == false) {
            	phoneCheck = false;
                idx = i+1;
                return false;
            }
            if(new RegExp(regVar).test($(this).find("input[name=evPartEmails]").val()) == false) {
                emailCheck = false;
                idx = i+1;
                return false;
            }
            --%>
			// 참석자 성명
			if($.trim($(this).find("input[name=evPartDepts]").val())
														|| $.trim($(this).find("input[name=evPartPostions]").val())
														|| $.trim($(this).find("input[name=evPartPhones]").val())
														|| $.trim($(this).find("input[name=evPartEmails]").val())){
				if(!$.trim($(this).find("input[name=evPartNames]").val())){
					nullCheck = false;
					nullName = "<spring:message code='text.srm.field.srmevl0050.sub.title8'/>";
					idx = i+1;
					return false;
				}
			}


            target = "<spring:message code='text.srm.field.srmevl0050.sub.title8'/>";	// 참석자 성명
            if(!cal_3byte($(this).find("input[name=evPartNames]").val(), '100', setPermitMsg(target), setByteMsg(target, '100'))) {
            	phoneByteCheck = false;
            	idx = i+1;
    			return false;
    		}
            
            target = "<spring:message code='text.srm.field.srmevl0050.sub.title9'/>";	// 참석자 부서
            if(!cal_3byte($(this).find("input[name=evPartDepts]").val(), '50', setPermitMsg(target), setByteMsg(target, '50'))) {
            	phoneByteCheck = false;
            	idx = i+1;
    			return false;
    		}
            
            target = "<spring:message code='text.srm.field.srmevl0050.sub.title10'/>";	// 참석자 직위
            if(!cal_3byte($(this).find("input[name=evPartPostions]").val(), '50', setPermitMsg(target), setByteMsg(target, '50'))) {
            	phoneByteCheck = false;
            	idx = i+1;
    			return false;
    		}
            
            target = "<spring:message code='text.srm.field.srmevl0050.sub.title11'/>";	// 참석자 전화번호
            if(!cal_3byte($(this).find("input[name=evPartPhones]").val(), '20', setPermitMsg(target), setByteMsg(target, '20'))) {
            	phoneByteCheck = false;
            	idx = i+1;
    			return false;
    		}
            
            target = "<spring:message code='text.srm.field.srmevl0050.sub.title12'/>";	// 참석자 E-mail
            if(!cal_3byte($(this).find("input[name=evPartEmails]").val(), '100', setPermitMsg(target), setByteMsg(target, '100'))) {
            	emailByteCheck = false;
            	idx = i+1;
    			return false;
    		}
            
        });

		if(!nullCheck){
			alert("<spring:message code='msg.srm.alert.text' arguments='" + nullName + "'/>");
			$('#attendants tbody tr input[name=evPartNames]').eq(idx-1).focus();
			return false;
		}
        
        if(!phoneCheck) {
            alert('<spring:message code="msg.srm.alert.validation.evPartPhones" arguments="'+idx+'"/>');/*spring:message : 참석자 [{0}]번째 전화번호 양식에 맞지 않습니다.*/
            return false;
        }
        if(!emailCheck) {
            alert('<spring:message code="msg.srm.alert.validation.evPartEmails" arguments="'+idx+'"/>');/*spring:message : 참석자 [{0}]번째 이메일 양식에 맞지 않습니다.*/
            return false;
        }
        if(!phoneByteCheck)return false;
        
        if(!emailByteCheck)return false;
        /*-------------------------------------------------------------------------------------------------*/
        
        /*----------------------------------------- 중요부적합 사항 ---------------------------------------------*/
        target = "<spring:message code='text.srm.field.srmevl0050.sub.title4'/>";	// 중요부적합 사항
        if(!cal_3byte($("textarea[name='evInconRemark']").val(), '4000', setPermitMsg(target), setByteMsg(target, '4000'))) {
   			$("#evInconRemark").focus();
   			return false;
    	}
        /*-------------------------------------------------------------------------------------------------*/
        
        /*------------------------------------------- 개선조치 ------------------------------------------------*/
		idx = 0;
		var evItemType1CodeCheck = true;
		var impReqRemarksCheck = true;
		var impReqRemarksByteCheck = true;
		
		$('#improvingTbody tr').each(function(i){
			<%--
			if(!$.trim($(this).find("select[name=evItemType1Codes]").val())) {
				evItemType1CodeCheck = false;
				idx = i+1;
				return false;
			}
			if(!$.trim($(this).find("textarea[name=impReqRemarks]").val())) {
				impReqRemarksCheck = false;
				idx = i+1;
				return false;
			}
			--%>
			if($.trim($(this).find("select[name=evItemType1Codes]").val()) && !$.trim($(this).find("textarea[name=impReqRemarks]").val())) {
				impReqRemarksCheck = false;
				idx = i+1;
				return false;
			}
			target = "<spring:message code='text.srm.field.srmevl0050.sub.title5'/>";	// 개선조치
			if(!cal_3byte($(this).find("textarea[name=impReqRemarks]").val(), '1000', setPermitMsg(target), setByteMsg(target, '1000'))) {
				impReqRemarksByteCheck = false;
				idx = i+1;
	   			return false;
	    	}
			
		});
		if(!evItemType1CodeCheck) {
			alert('<spring:message code="msg.srm.alert.validation.evItemType1Codes" arguments="'+idx+'"/>');/*spring:message : 개선조치 [{0}]번째 개선조치를 선택하세요.*/
			return false;
		}
		if(!impReqRemarksCheck) {
			alert('<spring:message code="msg.srm.alert.validation.impReqRemarks" arguments="'+idx+'"/>');/*spring:message : 개선조치 [{0}]번째 특이사항 내용을 입력하세요.*/
			$('#improvingTbody tr textarea[name=impReqRemarks]').eq(idx-1).focus();
			return false;
		}
		if(!impReqRemarksByteCheck) {
			return false;
		}
		
		/*-------------------------------------------------------------------------------------------------*/
		
		/*------------------------------------------- 심사총평 -----------------------------------------------*/
		target = "<spring:message code='text.srm.field.srmevl0050.sub.title6'/>";	// 심사총평
		if(!cal_3byte($("textarea[name='evGenView']").val(), '4000', setPermitMsg(target), setByteMsg(target, '4000'))) {
   			$("#evGenView").focus();
   			return false;
    	}
        /*-------------------------------------------------------------------------------------------------*/
        
        /*--------------------------------------- 품질평가 첨부파일 ---------------------------------------------*/
		var fileCheck = true;
        
		$('#attachFileTbody tr').each(function(){
			var file = $(this).find('#attachFile').val();
			var fileExt = file.substring(file.lastIndexOf('.')+1);
			var fileName = file.substring(file.lastIndexOf('\\')+1);
			
			if(file != "" && !fileExtCheck("<spring:message code='edi.srm.file.all.ext'/>", fileExt)){
				var target1 = "<spring:message code='text.srm.field.srmevl0050.sub.title7'/>";
				var target2 = "<spring:message code='edi.srm.file.all.ext'/>";
				alert("<spring:message code='msg.srm.alert.validation.file' arguments='"+target1+","+target2+"'/>");/*품질평가 첨부파일은(는) {1}파일만 업로드 할 수 있습니다.*/
				fileCheck = false;
				return false;
			}
			
			target = "<spring:message code='text.srm.field.fileName' />";	// 첨부파일이름
			if(!cal_3byte(fileName, '192', setPermitMsg(target), setByteMsg(target, '192'))) {
				fileCheck = false;
				return false;
			}
		});

		if(!fileCheck)return false;
		
		/*-------------------------------------------------------------------------------------------------*/
        return true;
    }


	//평가완료
	function doComplete(tempYn){
        if(!validation())return;
        if(tempYn == "Y"){
            if(!confirm("<spring:message code="msg.srm.alert.evalConplete"/>"))return;/*spring:message : 평가완료 하시겠습니까?*/
        } else {
            if(!confirm("<spring:message code="msg.srm.alert.tempSave"/>"))return;/*spring:message : 저장 하시겠습니까?*/
        }


		$('#MyForm input[name=evCtrlDate]').removeAttr("disabled");
        $('#MyForm input[name=tempYn]').val(tempYn);

		$('#MyForm').attr("action", '<c:url value="/edi/evl/SRMEVLupdateQualityEvaluationComplete.do"/>')
		$('#MyForm').submit();
	}

	//평가화면 이동
	function doEval() {
		$('#MyForm').attr("action", '<c:url value="/edi/evl/SRMEVL0040.do"/>')
		$('#MyForm').submit();
	}

	//목록
	function doList() {
		if("<c:out value="${vo.evalFlag}"/>" == "0"){
			$('#MyForm').attr("action", '<c:url value="/edi/evl/SRMEVL0030.do"/>')
		} else {
			$('#MyForm').attr("action", '<c:url value="/edi/evl/SRMEVL0060.do"/>')
		}

		$('#MyForm').submit();
	}


	//전화번호 체크
	function phoneNumCheck(telNm) {
		telNm = telNm.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
		telNm = telNm.split("-");
		var objVal = null;
		var elem = null;
		var objIdx = 0;
		var isNumOk = true;
		for (var i=0; i < 3 ; i++) {
			objIdx = 0;
			for (var idx=0; idx < telNm[i].length; idx++) {
				if (/[^0-9]/.test(telNm[i].substring(idx, idx+1))) {
					objIdx = idx;
					isNumOk = false;
					break;
				}
			}

			if (i == 0) {
				if(/\d{2,4}/.test(telNm[i]) == false) {
					return false;
				}
				if(telNm[i] == "000") {
					return false;
				}
				if (isNumOk == false) {
					return false;
				}
			} else if (i == 1) {
				if(/\d{3,4}/.test(telNm[i]) == false) {
					return false;
				}
				if (isNumOk == false) {
					return false;
				}
			} else if (i == 2) {
				if(/\d{4}/.test(telNm[i]) == false) {
					return false;
				}
				if (isNumOk == false) {
					return false;
				}
			}
		}
		return true;
	}
</script>
<script id="addAttendantTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td style="text-align:center;"></td>
		<td><input type="text" class="input_txt_default" style="width:95%" id="evPartNames" name="evPartNames" title=""/></td>
		<td><input type="text" class="input_txt_default" style="width:95%" id="evPartDepts" name="evPartDepts" title=""/></td>
		<td><input type="text" class="input_txt_default" style="width:95%" id="evPartPostions" name="evPartPostions" title=""/></td>
		<td><input type="text" class="input_txt_default numberic" style="width:95%" id="evPartPhones" name="evPartPhones" title=""/></td>
		<td><input type="text" class="input_txt_default" style="width:95%" id="evPartEmails" name="evPartEmails" title=""/></td>
		<td style="text-align:center;">
			<input type="checkbox" id="attendantCheck" name="attendantCheck" title=""/>
		</td>
	</tr>
</script>

<script id="addImprovingTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td style="text-align:center;">
			<select id="evItemType1Codes" name="evItemType1Codes" style="width:90%;">
				<option value=""><spring:message code="text.srm.field.select"/></option><%--spring:message : 선택--%>
				<c:forEach var="codeList" items="${evItemType1CodeList}" varStatus="status">
					<option value="<c:out value="${codeList.evItemType1Code}"/>"><c:out value="${codeList.evItemType1CodeName}"/></option>
				</c:forEach>
			</select>
		</td>
		<td>
			<textarea type="text" class="input_textarea_default" id="impReqRemarks" name="impReqRemarks" title="" style="width:95%;height:50px;"></textarea>
		</td>
		<td style="text-align:center;">
			<input type="checkbox" id="improvingCheck" name="improvingCheck" title=""/>
		</td>
	</tr>
</script>

<script id="addAttachFileTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td>
			<input type="hidden" id="attachFileName" name="attachFileName" title="" class="input_txt_default" disabled="disabled" readonly="readonly"/>
			<input type="hidden" id="docNo" name="docNo"/>
			<input type="hidden" id="docSeq" name="docSeq"/>
			<input type="file" id="attachFile" name="attachFile" title="" onchange="javascript:fileUpload(this, 'attachFileName');"/>
			<btn>
				<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear(this, 'attachFile','attachFileName')"/><%--spring:message : 취소--%>
				<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete(this);"/><%--spring:message : 삭제--%>
			<btn>
		</td>
	</tr>
</script>

</head>


<body>
<form id="MyForm" name="MyForm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="houseCode" name="houseCode" value="<c:out value="${vo.houseCode}"/>"/>
	<input type="hidden" id="sgNo" name="sgNo" value="<c:out value="${vo.sgNo}"/>"/>
	<input type="hidden" id="evNo" name="evNo" value="<c:out value="${vo.evNo}"/>"/>
	<input type="hidden" id="vendorCode" name="vendorCode" value="<c:out value="${vo.vendorCode}"/>"/>
	<input type="hidden" id="visitSeq" name="visitSeq" value="<c:out value="${vo.visitSeq}"/>"/>
	<input type="hidden" id="seq" name="seq" value="<c:out value="${vo.seq}"/>"/>
	<input type="hidden" id="evUserId" name="evUserId" value="<c:out value="${vo.evUserId}"/>"/>
	<input type="hidden" id="evTplNo" name="evTplNo" value="<c:out value="${vo.evTplNo}"/>"/>
	<input type="hidden" id="evalFlag" name="evalFlag" value="<c:out value="${vo.evalFlag}"/>"/>
    <input type="hidden" id="tempYn" name="tempYn"/>
	<input type="hidden" id="evalNoResult" name="evalNoResult" value="<c:out value="${vo.evNo}"/>"/>
	<input type="hidden" id="statusNm" name="statusNm" value="<c:out value="${siteVisit1.statusNm}"/>"/>
	<input type="hidden" id="sellerNameLoc" name="sellerNameLoc" value="<c:out value="${siteVisit1.sellerNameLoc}"/>"/>
	<input type="hidden" id="progressCode" name="progressCode" value="<c:out value="${param.progressCode}"/>"/>
	<input type="hidden" id="activeTab" name="activeTab" value="<c:out value="${param.activeTab}" />" />

	<input type="hidden" id="attachFileNo" name="attachFileNo" value="<c:out value="${siteVisit1.attachFileNo}"/>"/>
	
	
	<!--Container-->
	<div id="container">
		<!-- Sub Wrap -->
		<div class="inner sub_wrap">
			<!-- 서브상단 -->
			<div class="sub_top">
				<c:if test="${vo.evalFlag eq '0'}">
					<h2 class="tit_page"><spring:message code="evlHeader.menu.text2" /></h2>	<%-- 입점평가--%>
					<p class="page_path"><spring:message code="evlHeader.menu.text2" /><span><spring:message code="text.srm.field.srmevl005001.title"/></span></p>
				</c:if>
				<c:if test="${vo.evalFlag eq '1'}">
					<h2 class="tit_page"><spring:message code="evlHeader.menu.text3" /></h2>	<%-- 정기평가--%>
					<p class="page_path"><spring:message code="evlHeader.menu.text3" /><span><spring:message code="text.srm.field.srmevl005001.title"/></span></p>
				</c:if>
			</div>
			<!-- END 서브상단 -->
			
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code="text.srm.field.srmevl0040.sub.title1"/></h3>	<%-- 품질평가 기본정보--%>
				<div class="right_btns">
					<button type="button" id="" name="" value="" class="btn_normal btn_black" onclick="javascript:doReport();"><spring:message code="button.srm.report"/></button>				<%--spring:message : 보고서--%>
					<c:if test="${param.progressCode ne '300'}">
					<button type="button" id="" name="" value="" class="btn_normal btn_gray" onclick="javascript:doComplete('N');"><spring:message code="button.srm.tempSave"/></button>		<%--spring:message : 임시저장--%>
					<button type="button" id="" name="" value="" class="btn_normal btn_red" onclick="javascript:doComplete('Y');"><spring:message code="button.srm.evalComplete"/></button>		<%--spring:message : 평가완료--%>
					</c:if>
					<button type="button" id="" name="" value="" class="btn_normal btn_gray" onclick="javascript:doEval();"><spring:message code="button.srm.eval"/></button>					<%--spring:message : 평가--%>
					<button type="button" id="" name="" value="" class="btn_normal btn_gray" onclick="javascript:doList();"><spring:message code="button.srm.list"/></button>					<%--spring:message : 목록--%>
				</div>
			</div>
			
			<%----- 품질평가 기본정보 Start -------------------------%>
			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="15%"/>
					<col width="30%"/>
					<col width="15%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th><spring:message code='text.srm.field.evalNoResult' /></th>	<%--spring:message : 의뢰번호--%>
						<td><c:out value="${vo.evNo}"/>-<c:out value="${vo.visitSeq}"/></td>
						<th><spring:message code='text.srm.field.status' /></th>	<%--spring:message : 진행상태--%>
						<td><c:out value="${siteVisit1.statusNm}"/></td>
					</tr>
					<tr>
						<th><spring:message code="text.srm.search.field.sellerNameLoc"/></th>	<%--spring:message : 파트너사명--%>
						<td><c:out value="${siteVisit1.sellerNameLoc}"/></td>
						<th><spring:message code='text.srm.field.mainProduct'/></th>	<%--spring:message : 주요상품--%>
						<td><c:out value="${siteVisit1.mainProduct}"/></td>
					</tr>
				</tbody>
			</table>
			<%----- 품질평가 기본정보 End -------------------------%>
			
			
			<%----- 감사자 정보 Start -------------------------%>
			<h3 class="tit_star"><spring:message code="text.srm.field.srmevl0050.sub.title2"/></h3>	<%-- 감사자 정보--%>
			
			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="15%"/>
					<col width="*"/>
					<col width="15%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th><spring:message code="text.srm.field.evTotScore"/></th><%--spring:message : 획득총점--%>
						<td>
							<input type="hidden" id="evTotScore" name="evTotScore" value="<c:out value="${siteVisit1.evTotScore}"/>"/>
							<input type="hidden" id="evScore" name="evScore" value="<c:out value="${siteVisit1.evScore}"/>"/>
							<input type="hidden" id="evScorePer" name="evScorePer" value="<c:out value="${siteVisit1.evScorePer}"/>"/>
							<span style="font-weight:bold;color:blue;"><fmt:formatNumber value="${siteVisit1.evScorePer}" pattern="#"/>%</span>
							&nbsp;
							<input type="button" id="" name="" value="<spring:message code="button.srm.detail"/>" class="btn_normal btn_blue" onclick="javascript:doDetail();" style="margin:0px;padding-top:2px;padding-bottom:2px;"/><%--상세보기--%>
						</td>
						<th><spring:message code="text.srm.field.evGrade"/></th><%--spring:message : 공급업체등급--%>
						<td>
							<select id="evGrade" name="evGrade" title="" style="width:120px;">
								<option value=""><spring:message code="text.srm.field.select"/></option><%--spring:message : 선택--%>
								<option value="A+" <c:if test="${siteVisit1.evGrade eq 'A+'}">selected</c:if>>A+<spring:message code="text.srm.field.grade"/></option>
								<option value="A" <c:if test="${siteVisit1.evGrade eq 'A'}">selected</c:if>>A<spring:message code="text.srm.field.grade"/></option>
								<option value="B" <c:if test="${siteVisit1.evGrade eq 'B'}">selected</c:if>>B<spring:message code="text.srm.field.grade"/></option>
								<option value="C" <c:if test="${siteVisit1.evGrade eq 'C'}">selected</c:if>>C<spring:message code="text.srm.field.grade"/></option>
								<option value="D" <c:if test="${siteVisit1.evGrade eq 'D'}">selected</c:if>>D<spring:message code="text.srm.field.grade"/></option>
							</select>
						</td>
					</tr>
					<tr>
						<th><spring:message code="text.srm.field.evCtrlDate"/></th><%--spring:message : 감사일--%>
						<td>
							<jsp:useBean id="toDay" class="java.util.Date" />
							<c:set var="date"><fmt:formatDate value="${toDay}" pattern="yyyy-MM-dd"/></c:set>
							<c:if test="${not empty siteVisit1.evCtrlDate}">
								<c:set var="date"><c:out value='${fn:substring(siteVisit1.evCtrlDate,0,4)}'/>-<c:out value='${fn:substring(siteVisit1.evCtrlDate,4,6)}'/>-<c:out value='${fn:substring(siteVisit1.evCtrlDate,6,8)}'/></c:set>
							</c:if>
							<input type="text" class="input_txt_default" id="evCtrlDate" name="evCtrlDate" title="" disabled="disabled" readonly="readonly" value="<c:out value="${date}"/>" style="width: 80px;">
							<button type="button" class="plain btn_cal" onClick="openCal('MyForm.evCtrlDate');"><img src="/images/epc/edi/srm/sub/icon_cal.png" alt="달력"></button>
						</td>
						<th><label for="evCtrlName"><spring:message code="text.srm.field.evCtrlName"/></label></th><%--spring:message : 심사원 성명--%>
						<td>
							<%--<c:out value='${siteVisit1.evCtrlName}'/>--%>
							<input type="text" id="evCtrlName" name="evCtrlName" class="input_txt_default" value="<c:out value="${siteVisit1.evCtrlName}"/>"/>
						</td>
					</tr>
					<tr>
						<th><label for="evCtrlPhone"><spring:message code="text.srm.field.evCtrlPhone"/></label></th><%--spring:message : 심사원 연락처--%>
						<td>
							<%--<c:out value='${siteVisit1.evCtrlPhone}'/>--%>
							<input type="text" id="evCtrlPhone" name="evCtrlPhone" class="input_txt_default numberic" value="<c:out value="${siteVisit1.evCtrlPhone}"/>"/>
						</td>

						<th><label for="evCtrlEmail"><spring:message code="text.srm.field.evCtrlEmail"/></label></th><%--spring:message : 심사원 E-mail--%>
						<td>
							<%--<c:out value='${siteVisit1.evCtrlEmail}'/>--%>
							<input type="text" id="evCtrlEmail" name="evCtrlEmail" class="input_txt_default" value="<c:out value="${siteVisit1.evCtrlEmail}"/>"/>
						</td>
					</tr>
				</tbody>
			</table>
			<%----- 감사자 정보 End -------------------------%>
			
			
			<%----- 참석자 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code="text.srm.field.srmevl0050.sub.title3"/></h3>	<%-- 참석자--%>
				<c:if test="${param.progressCode ne '300'}">
				<div class="right_btns">
					<button type="button" id="" name="" value="" class="btn_normal btn_blue" onclick="javascript:addAttendantsRow();"><spring:message code="button.srm.add"/></button><%--spring:message : 추가--%>
					<button type="button" id="" name="" value="" class="btn_normal btn_red" onclick="javascript:delAttendantsRow(this);"><spring:message code='button.srm.delete'/></button><%--spring:message : 삭제--%>
				</div>
				</c:if>
			</div>
			
			<table id="attendants" class="tbl_st1 form_style">
				<colgroup>
					<col width="7%"/>
					<col width="15%"/>
					<col width="15%"/>
					<col width="15%"/>
					<col width="20%"/>
					<col width="*"/>
					<col width="5%"/>
				</colgroup>
				<thead>
					<tr>
						<th><spring:message code="table.srm.colum.title.no"/></th><%--spring:message : No--%>
						<th><spring:message code="table.srm.srmevl0050.colum.title2"/></th><%--spring:message : 성명--%>
						<th><spring:message code="table.srm.srmevl0050.colum.title3"/></th><%--spring:message : 부서--%>
						<th><spring:message code="table.srm.srmevl0050.colum.title4"/></th><%--spring:message : 직위--%>
						<th><spring:message code="table.srm.srmevl0050.colum.title5"/></th><%--spring:message : 전화번호--%>
						<th><spring:message code="table.srm.srmevl0050.colum.title6"/></th><%--spring:message : E-mail--%>
						<th style="text-align:center;">
							<input type="checkbox" id="attendantsAll" name="attendantsAll" title=""/>
						</th>
					</tr>
				</thead>
				<tbody id="attendantsTbody">
					<c:if test="${empty siteVisit2List}">
						<tr>
							<td style="text-align:center;">1</td>
							<td><input type="text" class="txt_normal" style="width:95%" id="evPartNames" name="evPartNames" title=""/></td>
							<td><input type="text" class="txt_normal" style="width:95%" id="evPartDepts" name="evPartDepts" title=""/></td>
							<td><input type="text" class="txt_normal" style="width:95%" id="evPartPostions" name="evPartPostions" title=""/></td>
							<td><input type="text" class="txt_normal numberic" style="width:95%" id="evPartPhones" name="evPartPhones" title=""/></td>
							<td><input type="text" class="txt_normal" style="width:95%" id="evPartEmails" name="evPartEmails" title=""/></td>
							<td style="text-align:center;"><input type="checkbox" id="attendantCheck" name="attendantCheck" title=""/></td>
						</tr>
					</c:if>
					<c:if test="${not empty siteVisit2List}">
						<c:forEach var="list" items="${siteVisit2List}" varStatus="status">
							<tr>
								<td style="text-align:center;"><c:out value="${status.count}"/></td>
								<td><input type="text" class="txt_normal" style="width:95%" id="evPartNames" name="evPartNames" title="" value="<c:out value="${list.evPartName}"/>"></td>
								<td><input type="text" class="txt_normal" style="width:95%" id="evPartDepts" name="evPartDepts" title="" value="<c:out value="${list.evPartDept}"/>"/></td>
								<td><input type="text" class="txt_normal" style="width:95%" id="evPartPostions" name="evPartPostions" title="" value="<c:out value="${list.evPartPostion}"/>"/></td>
								<td><input type="text" class="txt_normal numberic" style="width:95%" id="evPartPhones" name="evPartPhones" title="" value="<c:out value="${list.evPartPhone}"/>"/></td>
								<td><input type="text" class="txt_normal" style="width:95%"  id="evPartEmails" name="evPartEmails" title="" value="<c:out value="${list.evPartEmail}"/>"/></td>
								<td style="text-align:center;"><input type="checkbox" id="attendantCheck" name="attendantCheck" title=""/></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<%----- 참석자 End -------------------------%>
			
			
			
			<%----- 중요부적합 사항 Start -------------------------%>
			<h3 class="tit_star"><spring:message code="text.srm.field.srmevl0050.sub.title4"/></h3>	<%-- 중요부적합 사항--%>
			
			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<td>
							<textarea id="evInconRemark" name="evInconRemark" class="input_textarea_default" style="width:98%;height:100px;"><c:out value="${siteVisit1.evInconRemark}"/></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<%----- 중요부적합 사항 End -------------------------%>
			
			
			
			<%----- 개선조치 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code="text.srm.field.srmevl0050.sub.title5"/></h3>	<%-- 개선조치--%>
				<c:if test="${param.progressCode ne '300'}">
				<div class="right_btns">
					<button type="button" id="" name="" value="" class="btn_normal btn_blue" onclick="javascript:addImprovingRow();"><spring:message code="button.srm.add"/></button><%--spring:message : 추가--%>
					<button type="button" id="" name="" value="" class="btn_normal btn_red" onclick="javascript:delImprovingRow(this);"><spring:message code='button.srm.delete'/></button><%--spring:message : 삭제--%>
				</div>
				</c:if>
			</div>
			
			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="30%"/>
					<col width="*"/>
					<col width="5%"/>
				</colgroup>
				<thead>
					<tr>
						<th><spring:message code="text.srm.field.impReqSubject"/></th><%--spring:message : 개선조치--%>
						<th><spring:message code="text.srm.field.impReqRemark"/></th><%--spring:message : 특이사항--%>
						<th style="text-align:center;">
							<input type="checkbox" id="improvingAll" name="improvingAll" title=""/>
						</th>
					</tr>
				</thead>
				<tbody id="improvingTbody">
					<c:if test="${empty siteVisit3List}">
						<tr>
							<td style="text-align: center;">
								<select id="evItemType1Codes" name="evItemType1Codes" style="width:90%;">
									<option value=""><spring:message code="text.srm.field.select"/></option><%--spring:message : 선택--%>
									<c:forEach var="codeList" items="${evItemType1CodeList}" varStatus="status">
										<option value="<c:out value="${codeList.evItemType1Code}"/>"><c:out value="${codeList.evItemType1CodeName}"/></option>
									</c:forEach>
								</select>
							</td>
							<td>
								<textarea type="text" class="input_textarea_default" id="impReqRemarks" name="impReqRemarks" title="" style="width:98%;height:50px;"></textarea>
							</td>
							<td style="text-align:center;">
								<input type="checkbox" id="" name="" title=""/>
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty siteVisit3List}">
						<c:forEach var="list" items="${siteVisit3List}" varStatus="status">
							<tr>
								<td style="text-align: center;">
									<select id="evItemType1Codes" name="evItemType1Codes" style="width:90%;">
										<option value=""><spring:message code="text.srm.field.select"/></option><%--spring:message : 선택--%>
										<c:forEach var="codeList" items="${evItemType1CodeList}" varStatus="status">

											<option value="<c:out value="${codeList.evItemType1Code}"/>" <c:if test="${list.evItemType1Code eq codeList.evItemType1Code}">selected</c:if>><c:out value="${codeList.evItemType1CodeName}"/></option>
										</c:forEach>
									</select>
								</td>
								<td>
									<textarea type="text" class="input_textarea_default" id="impReqRemarks" name="impReqRemarks" title="" style="width:98%;height:50px;"><c:out value="${list.impReqRemark}"/></textarea>
								</td>
								<td style="text-align:center;">
									<input type="checkbox" id="" name="" title=""/>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<%----- 중요부적합 사항 End -------------------------%>
			
			
			<%----- 심사총평 Start -------------------------%>
			<h3 class="tit_star"><spring:message code="text.srm.field.srmevl0050.sub.title6"/></h3>	<%-- 심사총평--%>
			
			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<td>
							<textarea id="evGenView" name="evGenView" class="input_textarea_default" style="width:98%;height:100px;"><c:out value="${siteVisit1.evGenView}"/></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<%----- 심사총평 End -------------------------%>
			
			
			<%----- 품질평가 첨부파일 Start -------------------------%>
			<div class="tit_btns">
				<h3 class="tit_star"><spring:message code="text.srm.field.srmevl0050.sub.title7"/></h3>	<%-- 품질평가 첨부파일--%>
				<c:if test="${param.progressCode ne '300'}">
				<div class="right_btns">
					<button type="button" id="" name="" value="" class="btn_normal btn_blue" onclick="javascript:addAttachFileRow();"><spring:message code="button.srm.add"/></button><%--spring:message : 추가--%>
				</div>
				</c:if>
			</div>
			
			<table class="tbl_st1 form_style">
				<colgroup>
					<col width="15%"/>
					<col width="*"/>
				</colgroup>
				<tbody>
					<tr>
						<th><spring:message code="text.srm.field.attachNo"/></th><%--spring:message : 첨부파일--%>
						<td>
							<table width="100%">
								<colgroup>
									<col width="15%"/>
									<col width="*"/>
								</colgroup>
								<tbody>
									<tr>
										<td rowspan="2"><spring:message code="text.srm.field.evalAttchNo"/></td><%--spring:message : 품질평가 첨부파일--%>
										<td>
											<c:if test="${param.progressCode ne '300'}">
												<table class="edit_none_board">
													<tbody id="attachFileTbody">
														<c:if test="${empty attachFileList}">
															<tr>
																<td>
																	<input type="hidden" id="attachFileName" name="attachFileName" title="" class="input_txt_default" disabled="disabled" readonly="readonly"/>
																	<input type="hidden" id="docNo" name="docNo"/>
																	<input type="hidden" id="docSeq" name="docSeq"/>
																	<input type="file" id="attachFile" name="attachFile" title="" onchange="javascript:fileUpload(this, 'attachFileName');"/>
																	<btn>
																		<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear(this, 'attachFile','attachFileName')"/><%--spring:message : 취소--%>
																		<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete(this);"/><%--spring:message : 삭제--%>
																	</btn>
																</td>
															</tr>
														</c:if>
														<c:if test="${not empty attachFileList}">
															<c:forEach var="list" items="${attachFileList}" varStatus="status">
																<tr>
																	<td>

																		<input type="hidden" id="attachFileName" name="attachFileName" title="" class="input_txt_default" disabled="disabled" readonly="readonly"/>
																		<input type="hidden" id="docNo" name="docNo" value="<c:out value="${list.fileId}"/>"/>
																		<input type="hidden" id="docSeq" name="docSeq"  value="<c:out value="${list.fileSeq}"/>"/>
																		<input type="file" id="attachFile" name="attachFile" title="" onchange="javascript:fileUpload(this, 'attachFileName');"/>
																		<btn>
																			<input type="button" id="" name="" class="btn_normal btn_gray" value="<spring:message code='button.srm.cancel'/>" onclick="javascript:fileClear(this, 'attachFile','attachFileName')"/><%--spring:message : 취소--%>
																			<input type="button" id="" name="" class="btn_normal btn_red" value="<spring:message code='button.srm.delete'/>" onclick="javascript:fileDelete(this);"/><%--spring:message : 삭제--%>
																		</btn>

																		<div>
																			<a id="" name="" href="#" onclick="javascript:downloadFile(<c:out value="${list.fileId}"/>,<c:out value="${list.fileSeq}"/>);"><c:out value="${list.fileNmae}"/></a>
																		</div>
																	</td>
																</tr>
															</c:forEach>
														</c:if>
													</tbody>
											</c:if>
											<c:if test="${param.progressCode eq '300'}">
												<c:forEach var="list" items="${attachFileList}" varStatus="status">
													<div>
														<a id="" name="" href="#" onclick="javascript:downloadFile(<c:out value="${list.fileId}"/>,<c:out value="${list.fileSeq}"/>);"><c:out value="${list.fileNmae}"/></a>
													</div>
												</c:forEach>
											</c:if>
											</table>
										</td>
									</tr>
								</tbody>
							</table>
							<div style="color:red;"><spring:message code='text.srm.field.srmjon0041Notice1'/></div><%--spring:message : 첨부문서는 100M이하, DOC, DOCX, XLS, XLSX, PPT, PPTX, PDF 파일로 올려주세요--%>
						</td>
					</tr>
				</tbody>
			</table>
			<%----- 품질평가 첨부파일 End -------------------------%>
		
		</div>
	</div>
	</form>
	
	<form id="fileForm" name="fileForm" method="post">
		<input type="hidden" id="atchFileId" name="atchFileId"/>
		<input type="hidden" id="fileSn" name="fileSn"/>
	</form>

</body>
</html>
