<%@include file="../common.jsp"%>
<%@ include file="/common/edi/ediCommon.jsp"%>
<%@ taglib prefix="srm" uri="http://lcnjf.lcn.co.kr/taglib/srm"  %>
<%@ page contentType="text/html; charset=UTF-8"%>

<%--
	Page Name 	: SRMVEN0020.jsp
	Description : SRM 정보 > 인증정보 등록
	Modification Information
	
	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2016.07.26		SHIN SE JIN		최초생성
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title><spring:message code='text.srm.field.certiInfoAdd' /></title><%--인증정보 등록 --%>

<script language="JavaScript">
	/* 화면 초기화 */
	$(document).ready(function() {
		
		if ('<c:out value="${message}" />' == "insert") {
			alert('<spring:message code="msg.srm.alert.saveOk" />');<%--저장 되었습니다. --%>
		} else if ('<c:out value="${message}" />' == "update") {
			alert('<spring:message code="msg.srm.alert.updateOk" />');<%--수정 되었습니다. --%>
		} else if ('<c:out value="${message}" />' == "fail_invalid_ext") {
			alert('JPG, GIF, PNG, BMP 확장자 파일만 업로드 가능합니다.');
		}

		/* codeTag 전체 SET(다국어 지원을 위해서 추가) */
		var defName = "<spring:message code='text.srm.field.venCdAll' />";	// 전체
		/* 협력업체코드 */
		$("select[name='srchVenCd'] option:eq(0)").before("<option value=''>"+defName+"</option>");	// before: eq(0)전에 값 추가
		$("select[name='srchVenCd']").val(0);
		/* 인증대상 */
		$("select[name='srchCertiTarget'] option:eq(0)").before("<option value=''>"+defName+"</option>");	// before: eq(0)전에 값 추가
		$("select[name='srchCertiTarget']").val("");
		
		/* 인증정보 등록/수정 협력업체코드 SET */
		if ("<c:out value='${epcLoginSession.repVendorId}'/>" != "") {
			$("select[name='venCd']").val("<c:out value='${epcLoginSession.repVendorId}'/>");
		}
		
		/* 검색조건 인증명 Enter키 입력시 검색 */
		$('#srchCertiName').unbind().keydown(function(e) {	
			if	(e.keyCode == 13) {
				goPage('1');
			}
	   	});
		
		goPage("1");	// 인증정보 내역
	});

	/* 파트너사 인증정보 조회 */
	function goPage(pageIndex) {
		
		frmProdInfoReSet();	// 등록/수정폼 초기화
		
		var searchInfo = {};
		
		searchInfo["srchVenCd"]	= $("#searchForm select[name='srchVenCd']").val();				// 업체코드
		searchInfo["srchCertiTarget"] = $("#searchForm select[name='srchCertiTarget']").val();	// 인증대상
		searchInfo["srchCertiName"] = $("#searchForm input[name='srchCertiName']").val();		// 인증명
		
		searchInfo["houseCode"] = $("input[name='houseCode']").val();	// 하우스코드
		searchInfo["pageIndex"] = pageIndex;
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/ven/selectCertiInfoAddList.json"/>',
			data : JSON.stringify(searchInfo),
			success : function(json) {
				_setTbodyMainValue(json);
			}
		});
	}
	
	/* 파트너사 인증정보 List Set */
	function _setTbodyMainValue(json){
		var data = json.list;
		setTbodyInit("dataMainListbody");	// dataList 초기화
		if (data.length > 0) {
			for (var i = 0; i < data.length; i++) {
				data[i].rnum = i+1;
			}
			$("#dataMainListTemplate").tmpl(data).appendTo("#dataMainListbody");
			$("#paging").html(json.contents);
		} else {
			setTbodyNoResult("dataMainListbody", 9);
			$("#paging").html("");
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
	
	/* 파트너사 인증정보 저장 */
	function fnSaveConfirm() {
		
		var certiStdDate = $("input[name='certiStdDate']").val().replaceAll("-", "");	// 시작일 '-' 제거
		var certiEndDate = $("input[name='certiEndDate']").val().replaceAll("-", "");	// 종료일 '-' 제거
		
		/* ---------------값 체크--------------- */
		var target = "<spring:message code='text.srm.field.certiInfo1'/>";	// 인증정보
		if (!$.trim($("input[name='certiType']").val())) {
			alert("<spring:message code='msg.srm.alert.select' arguments='" + target + "'/>")<%--인증정보을(를) 선택하세요. --%>
			$("input[name='certiType']").focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.venCompany'/>";	// 협력업체
		if (!$.trim($("select[name='venCd']").val())) {
			alert("<spring:message code='msg.srm.alert.select' arguments='" + target + "'/>")<%--협력업체을(를) 선택하세요. --%>
			$("select[name='venCd']").focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.certiNo2'/>";	// 인증서번호
		if (!$.trim($("input[name='certiNo']").val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--인증서번호을(를) 입력하세요. --%>
			$("input[name='certiNo']").focus();
			return false;
		}
		
		if (!cal_3byte($('input[name="certiNo"]').val(), '50', setPermitMsg(target), setByteMsg(target, '50'))) {
			$('input[name="certiNo"]').focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.certiStdDate'/>";	// 인증기준일자
		if (!$.trim(certiStdDate)) {
			alert("<spring:message code='msg.srm.alert.select' arguments='" + target + "'/>")<%--인증기준일자을(를) 선택하세요. --%>
			$("input[name='certiStdDate']").focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.certiEndDate'/>";	// 인증종료일자
		if (!$.trim(certiEndDate)) {
			alert("<spring:message code='msg.srm.alert.select' arguments='" + target + "'/>")<%--인증종료일자을(를) 선택하세요. --%>
			$("input[name='certiEndDate']").focus();
			return false;
		}
		
		if ($.trim(certiStdDate) > $.trim(certiEndDate)) {
			alert("<spring:message code='msg.srm.alert.reqCertiDate' />");<%--인증종료일자가 인증기준일자 보다 이전일 수 없습니다. --%>
			$("input[name='certiEndDate']").focus();
			return false;
		}
		
		target = "<spring:message code='text.srm.field.certiOrgan'/>";	// 인증기관명
		if (!$.trim($("input[name='certiOrgan']").val())) {
			alert("<spring:message code='msg.srm.alert.text' arguments='" + target + "'/>")<%--인증기관명을(를) 입력하세요. --%>
			$("input[name='certiOrgan']").focus();
			return false;
		}
		
		if (!cal_3byte($('input[name="certiOrgan"]').val(), '100', setPermitMsg(target), setByteMsg(target, '100'))) {
			$('input[name="certiOrgan"]').focus();
			return false;
		}
		
		if ($("input[name='certiTarget']").val() == 1) {
			target = "<spring:message code='text.srm.field.prodInfo'/>";	// 상품
			if(!$.trim($("input[name='prodCd']").val())) {
				alert("<spring:message code='msg.srm.alert.select' arguments='" + target + "'/>")<%--상품을(를) 선택하세요. --%>
				$("input[name='prodCd']").focus();
				return false;
			}
		}
		
		if (!$.trim($("input[name='certiAttachNo']").val())) {
			target = "<spring:message code='text.srm.field.certiAdd'/>";	// 인증서
			if (!$.trim($("input[name='certiAttachNoFileName']").val())) {
				alert("<spring:message code='msg.srm.alert.file' arguments='" + target + "'/>")<%--인증서을(를) 첨부하세요. --%>
				$("input[name='certiAttachNoFileName']").focus();
				return false;
				
			} else {
				if(!fileCheck())return false;
			}
			
		} else {
			if ($.trim($("input[name='certiAttachNoFileName']").val())) {
				if(!fileCheck())return false;
			}
		}
		
		/* 신규일 경우 중복체크*/
		if (!$("input[name='seq']").val()) {
			if(!certiInfoCheck())return false;
		}
		
		if (!confirm("<spring:message code='msg.srm.alert.tempSave' />")) { <%--저장하시겠습니까? --%>
			return false;
		}
		
		$("input[name='certiStdDate']").val(certiStdDate);	// 시작일
		$("input[name='certiEndDate']").val(certiEndDate);	// 종료일
		$("input[name='certiStdDate'], input[name='certiEndDate'], input[name='prodCd']").removeAttr("disabled");
		$("#MyForm").attr("action", "<c:url value='/edi/ven/updateCertiInfo.do'/>");
		$("#MyForm").attr("target","_self");
		$("#MyForm").submit();
	}
	
	/* 파트너사 인증정보 삭제 */
	function fnDelConfirm() {
		var chkIdx = 0;
		var delList = new Array();
		var len = $("input[name='selChk']").length;
		for (var i = 0; i < len; i++) {
			if ($("input[name='selChk']").eq(i).is(":checked")) {
				var delDatas = {};
				delDatas["venCd"]		= $("input[name='venCd2']").eq(i).val();		// 협력업체코드
				delDatas["seq"]			= $("input[name='seq2']").eq(i).val();			// 순번
				delDatas["certiTarget"]	= $("input[name='certiTarget2']").eq(i).val();	// 인증대상
				delDatas["certiType"]	= $("input[name='certiType2']").eq(i).val();	// 인증구분
				delList.push(delDatas);
				chkIdx++;
			}
		}

		if (chkIdx == 0) {
			alert("<spring:message code='msg.srm.alert.notChecked' />");<%--선택된 인증정보가 없습니다. --%>
			return;
		}

		if (!confirm("<spring:message code='msg.srm.alert.confirmDelete' />")) { <%--삭제하시겠습니까? --%>
            return;
        }
		
		var data = {};
		
		data["houseCode"]	= $("input[name='houseCode']").val();		// 하우스코드
		data["delList"]	= delList;									// 인증대상
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'text',
			async : false,
			url : '<c:url value="/edi/ven/deleteCertiInfo.json"/>',
			data : JSON.stringify(data),
			success : function(data) {
				alert("<spring:message code='msg.srm.alert.deleteOk' />");<%--삭제 되었습니다. --%>
				
				frmProdInfoReSet();					// 인증정보 등록화면 초기화
				goPage('1');						// 인증정보 List 조회
			}
		});
	}
	
	/*인증정보 정보 중복체크 */
	function certiInfoCheck() {
		var result = true;
		
		var checkData = {};
		
		checkData["houseCode"]		= $("input[name='houseCode']").val();		// 하우스코드
		checkData["certiType"]		= $("input[name='certiType']").val();		// 인증구분
		checkData["certiNo"]		= $("input[name='certiNo']").val();			// 인증서번호
		checkData["certiTarget"]	= $("input[name='certiTarget']").val();		// 인증대상
		checkData["venCd"]			= $("select[name='venCd']").val();			// 협력업체코드
		/*인증대상이 상품일 경우 */
		if ($("input[name='certiTarget']").val() == "1") {
			checkData["prodCd"]			= $("input[name='prodCd']").val();		// 상품코드
		}
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/ven/selectCertiInfoCheck.json"/>',
			data : JSON.stringify(checkData),
			success : function(data) {
				if (data.message == "OVERLAP_INFO") {
					alert("<spring:message code='msg.srm.alert.srmven0020.overlapInfo'/>");<%--등록된 인증정보입니다. 기존 정보를 수정해 주세요. --%>
					result = false;
				} else if ("OK_INFO") {
					result = true;
				}
			}
		});
		
		return result;
		
	}
	
	/* 선택한 파트너사 인증정보 선택시 */
	function onSelectMainRow(rnum) {
		
		/* 수정모드 일시*/
		$("#tdCcertiType1").hide();																							// 인증정보 입력폼 숨기기
		$("#tdCcertiType2").show();																							// 인증정보 span 보이기
		$("#tdVenCd1").hide();																								// 협력업체코드 select 숨기기
	    $("#tdVenCd2").show();																								// 협력업체코드 span 보이기
	    $("#tdProdCd1").hide();																								// 상품 입력폼 숨기기
	    $("#tdProdCd2").show();																								// 상품정보 span 보이기
	    $("#certiAttachPathA").html("");																					// 파일 다운로드 초기화
	    $("input[name='certiAttachNoFile']").val("");																		// 첨부이름
		$("input[name='certiAttachNoFileName']").val("");																	// 첨부파일이름
	    
		/* row background-color 변경 */
	    $('#tblInfo tr').css("background-color","");																		//row background-color 초기화
	    $('#tblInfo tr:eq('+rnum+')').css("background-color","efefef");														//선택한 row background-color 색상 변경
	            
   		/* 인증대상이 회사일때 상품항목 숨기기 */
   		if ($("input[name='certiTarget2']").eq(rnum-1).val() == 0) {
   			$("#divProd").hide();
   		} else {
   			$("#divProd").show();
   		}
   		
        /* 선택한 row의 값 입력폼에 넣기 */
        $("#spanCertiName").text($("input[name='certiName2']").eq(rnum-1).val());											//인증구분 이름
        $("#spanProdCdName").text($("input[name='prodCdName2']").eq(rnum-1).val());											//상품이름
        $("#spanVenCd").text($("input[name='venCd2']").eq(rnum-1).val());													//협력업체
        
        $("select[name='venCd']").val($("input[name='venCd2']").eq(rnum-1).val());											//협력업체
   		$("input[name='seq']").val($("input[name='seq2']").eq(rnum-1).val());												//순서
   		$("input[name='certiNo']").val($("input[name='certiNo2']").eq(rnum-1).val());										//인증서번호
   		$("input[name='certiTarget']").val($("input[name='certiTarget2']").eq(rnum-1).val());								//인증대상
   		$("input[name='certiType']").val($("input[name='certiType2']").eq(rnum-1).val());									//인증구분
   		$("input[name='certiStdDate']").val($("input[name='certiStdDate2']").eq(rnum-1).val());								//인증일자시작
   		$("input[name='certiEndDate']").val($("input[name='certiEndDate2']").eq(rnum-1).val());								//인증일자끝
   		$("input[name='certiOrgan']").val($("input[name='certiOrgan2']").eq(rnum-1).val());									//인증기관
   		$("input[name='prodCd']").val($("input[name='prodCd2']").eq(rnum-1).val());											//상품코드
   		$("input[name='prodCdName']").val($("input[name='prodCdName2']").eq(rnum-1).val());									//상품이름
   		$("input[name='certiAttachNo']").val($("input[name='certiAttachNo2']").eq(rnum-1).val());							//첨부파일
   		$("#certiAttachPathA").attr("onclick","downloadFile("+$('input[name="certiAttachNo2"]').eq(rnum-1).val()+',1'+")");	//파일 다운로드 생성 
   		$("#certiAttachPathA").html($("input[name='srcFileName2']").eq(rnum-1).val());										//원본파일명
   		
	}
	
	/* 인증정보 등록화면 초기화 */
	function frmProdInfoReSet() {
		
		/* row background-color 변경 */
    	$("input[type='checkbox']").prop("checked",false);									//체크박스 초기화
    	$('#tblInfo tr').css("background-color","");										//row background-color 초기화
		
		$("#tdCcertiType1").show();															//인증정보 입력폼 보이기
		$("#tdCcertiType2").hide();															//인증정보 span 숨기기
		$("#divProd").hide();																//상품 tr 숨기기
		$("#tdVenCd1").show();																//select box 보여주기
	    $("#tdVenCd2").hide();																//text box 감추기
	    $("#tdProdCd1").show();																// 상품 입력폼 숨기기
	    $("#tdProdCd2").hide();																// 상품정보 span 보이기
	    
		$("#certiAttachPathA").html("");													//파일 다운로드 초기화
	    $("select[name='venCd']").val("<c:out value='${epcLoginSession.repVendorId }'/>");	//협력업체
		$("input[name='seq']").val("");														//순서
		$("input[name='certiNo']").val("");													//인증서번호
		$("input[name='certiTarget']").val("");												//인증대상
		$("input[name='certiType']").val("");												//인증구분
		$("input[name='certiStdDate']").val("");											//인증일자시작
		$("input[name='certiEndDate']").val("");											//인증일자끝
		$("input[name='certiOrgan']").val("");												//인증기관
		$("input[name='prodCd']").val("");													//상품코드
		$("input[name='prodCdName']").val("");												//상품이름
		$("input[name='certiAttachNo']").val("");											//기존파일
		$("input[name='certiAttachNoFile']").val("");										//첨부이름
		$("input[name='certiAttachNoFileName']").val("");									//첨부파일이름
		
	}
	
	/* 인증정보 팝업창 */
	function fnSearchCertiInfo() {
		var cw = 800;
		var ch = 330;
		
		var sw = screen.availWidth;
		var sh = screen.availHeight;
		var px = Math.round((sw-cw)/2);
		var py = Math.round((sh-ch)/2);
		
		window.open("", "popup", "left=" + px + ",top=" + py + ",width=" + cw + ",height=" + ch + ",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=no");
		
		$("#MyForm").attr("action", "<c:url value='/edi/ven/certiInfoPopup.do'/>");
		$("#MyForm").attr("target", "popup");
		$("#MyForm").submit();
	}
	
	/* 팝업창에서 선택한 인증정보 텍스트에 값 입력 */
	function setcertiInfo(certiTarget, certiType, certiNo, certiName) {
		
		if (certiTarget == 0) {		// 인증대상이 회사일때 상품항목 숨기기
			$("#divProd").hide();
		} else {
			$("#divProd").show();
		}
		
		$("input[name='certiTarget']").val(certiTarget);
		$("input[name='certiType']").val(certiType);
		$("input[name='certiNo']").val(certiNo);
		$("input[name='certiName']").val(certiName);
	}
	
	/* 상품코드명 찾기 팝업창 */
	function fnSearchCodeNm() {
		var cw = 400;
		var ch = 440;
		
		var sw = screen.availWidth;
		var sh = screen.availHeight;
		var px = Math.round((sw-cw)/2);
		var py = Math.round((sh-ch)/2);
		
		window.open("", "popup", "left=" + px + ",top=" + py + ",width=" + cw + ",height=" + ch + ",toolbar=no,menubar=no,status=no,resizable=no,scrollbars=no");
		
		$("#MyForm").attr("action", "<c:url value='/edi/ven/prodCdsearchPopup.do'/>");
		$("#MyForm").attr("target", "popup");
		$("#MyForm").submit();
	}
	
	/* 팝업창에서 선택한 상품정보 텍스트에 값 입력 */
	function setProdInfo(prodCd, prodCdNm) {
		$("input[name='prodCd']").val(prodCd);
		$("input[name='prodCdName']").val(prodCdNm);
	}
	
	/* 날짜 형식으로 바꾸기 */
	/* function date_format(num){
		return num.replace(/([0-9]{4})([0-9]{2})([0-9]{2})/,"$1-$2-$3");
	} */
	
	/* 파일선택시 */
	function fileUpload(obj, inputName) {
		$('#'+inputName).val($(obj).val());
	}
	
	/* 파일초기화 */
	function fileClear(file, inputName) {
		$('#'+inputName).val("");
		$('#'+file).val("");
	}
	
	/* 첨부파일 확장자 검사 */
	function fileCheck() {
		var file = $('input[name=certiAttachNoFile]').val();
		var fileExt = file.substring(file.lastIndexOf('.')+1);
		var fileName = file.substring(file.lastIndexOf('\\')+1);
		
		if(file != "" && !fileExtCheck("<spring:message code='edi.srm.file.image.ext'/>", fileExt)){
			var target1 = "<spring:message code='text.srm.field.certiAdd' />";
			var target2 = "<spring:message code='edi.srm.file.image.ext'/>";
			alert("<spring:message code='msg.srm.alert.validation.file' arguments='"+target1+","+target2+"'/>");/*인증서은(는) {1}파일만 업로드 할 수 있습니다.*/
			return false;
		}
		
		var target = "<spring:message code='text.srm.field.fileName' />";	// 첨부파일이름
		if (!cal_3byte(fileName, '192', setPermitMsg(target), setByteMsg(target, '192'))) {
			$('#MyForm input[name=creditAttachNoFile]').focus();
			return false;
		}
		return true;
	}
	
	/* 파일 다운로드 */
	function downloadFile(fileId, fileSeq) {
		$('#fileForm input[name=atchFileId]').val(fileId);
		$('#fileForm input[name=fileSn]').val(fileSeq);
		$('#fileForm').attr("action", '<c:url value="/edi/ven/fileDown.do"/>');
		$('#fileForm').submit();
	}
	
</script>

<!-- 파트너사 인증정보 리스트 템플릿(상품) -->
<script id="dataMainListTemplate" type="text/x-jquery-tmpl">
	<tr style="cursor:pointer;" bgcolor=ffffff onClick="onSelectMainRow('<c:out value="\${rnum}"/>')" style="cursor:pointer;">
		<td onclick="event.cancelBubble=true" style="text-align: center;"><input type="checkbox" id="selChk" name="selChk" /></td>
		<td style="text-align: center;"><c:out value="\${venCd}"/></td>
		<td style="text-align: center;"><c:out value="\${certiNo}"/></td>
		<td style="text-align: center;"><c:out value="\${certiName}"/></td>
		<td style="text-align: left;"><c:out value="\${certiOrgan}"/></td>
		<td style="text-align: center;"><c:out value="\${certiStdDate}"/></td>
		<td style="text-align: center;"><c:out value="\${certiEndDate}"/></td>
		<td style="text-align: center;"><c:out value="\${prodCd}"/></td>
		<td style="text-align: left;">
			<c:out value="\${prodCdName}"/>
			<input type="hidden" id="seq2" name="seq2" value="<c:out value="\${seq}" />" />
			<input type="hidden" id="venCd2" name="venCd2" value="<c:out value="\${venCd}" />" />	
			<input type="hidden" id="certiNo2" name="certiNo2" value="<c:out value="\${certiNo}" />" />	
			<input type="hidden" id="certiTarget2" name="certiTarget2" value="<c:out value="\${certiTarget}" />" />
			<input type="hidden" id="certiType2" name="certiType2" value="<c:out value="\${certiType}" />" />	
			<input type="hidden" id="certiName2" name="certiName2" value="<c:out value="\${certiName}" />" />
			<input type="hidden" id="certiStdDate2" name="certiStdDate2" value="<c:out value="\${certiStdDate}" />" />
			<input type="hidden" id="certiEndDate2" name="certiEndDate2" value="<c:out value="\${certiEndDate}" />" />
			<input type="hidden" id="certiOrgan2" name="certiOrgan2" value="<c:out value="\${certiOrgan}" />" />
			<input type="hidden" id="certiAttachNo2" name="certiAttachNo2" value="<c:out value="\${certiAttachNo}" />" />
			<input type="hidden" id="srcFileName2" name="srcFileName2" value="<c:out value="\${srcFileName}" />" />
			<input type="hidden" id="prodCd2" name="prodCd2" value="<c:out value="\${prodCd}" />" />
			<input type="hidden" id="prodCdName2" name="prodCdName2" value="<c:out value="\${prodCdName}" />" />
		</td>
	</tr>
</script>

</head>

<body>
	<div id="content_wrap">
		<div>
			<div id="wrap_menu">
			<!---------- 검색 Start  ------------------------------>
				<div class="wrap_con">
					<form id="searchForm" name="searchForm" method="post" action="#" onsubmit="return false">
						<div class="wrap_search">
							<div class="bbs_search">
								<ul class="tit">
									<li class="tit"><spring:message code='epc.ord.searchCondition' /></li><%--검색조건 --%>
									<li class="btn">
										<a href="#" class="btn" onclick="goPage(1);"><span><spring:message code="button.common.inquire" /></span></a>	<!-- 조회 -->
									</li>
								</ul>
								<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
									<colgroup id="tbHead1">
										<col style="width: 15%" />
										<col style="width: 30%" />
										<col style="width: 10%" />
										<col style="width: 30%" />
										<col style="width: 10%" />
										<col style="width: 30%" />
									</colgroup>
									<tr id="tbHead2">
										<th>
											<spring:message code='text.srm.field.venCd' /><%--업체코드 --%>
										</th>
										<td>
											<html:codeTag objId="srchVenCd" objName="srchVenCd" width="150px;" dataType="CP" comType="SELECT" formName="form" />
										</td>
										<th>
											<spring:message code='text.srm.field.certiTarget' /><%--인증대상 --%>
										</th>
										<td>
											<srm:codeTag objId="srchCertiTarget" comType="SELECT" objName="srchCertiTarget" formName="" parentCode="SRM034" />
										<th>
											<spring:message code='text.srm.field.certiName' /><%--인증명 --%>
										</th>
										<td>
											<input type="text" id="srchCertiName" name="srchCertiName" style="width: 90%;" />
										</td>
									</tr>
								</table>													
							</div>
						</div>
					</form>
				</div>
				<!---------- 검색 End  ------------------------------>
				<form name="MyForm"  id="MyForm" method="post" enctype="multipart/form-data">
					<input type="hidden" id="houseCode" 	name="houseCode" value="000"/>	<%--하우스코드 --%>
					<input type="hidden" id="certiTarget"	name="certiTarget"			/>	<%--인증대상 --%>
					<input type="hidden" id="certiType"		name="certiType"			/>	<%--인증구분 --%>
					<input type="hidden" id="certiNo"		name="certiNo"				/>	<%--인증서번호 --%>
					<input type="hidden" id="seq"			name="seq"					/>	<%--순번 --%>
					<!---------- 파트너사 정보 Start  ------------------------------>
					<p style="margin-top: 5px;" />
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit"><spring:message code='text.srm.field.certiInfo2' /></li><%--파트너사 인증정보 --%>
							<li class="btn">
								<a href="#" class="btn" onclick="frmProdInfoReSet();"><span><spring:message code="button.srm.new"/></span></a><%--신규 --%>
								<a href="#" class="btn" onclick="fnDelConfirm();"><span><spring:message code="button.srm.delete"/></span></a><%--삭제 --%>																	
							</li>
						</ul>
						<div style="width:100%; height:330px; overflow-x:scroll; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">
							<table class="bbs_list" cellpadding="0" cellspacing="0" border="1" style="border-color:#88a1b6; border-style: solid;" id="tblInfo" name="tblInfo">
								<colgroup>
									<col width="35px;" />
									<col width="70px;" />
									<col width="80px;" />
									<col width="140px;" />
									<col width="140px;" />
									<col width="80px;" />
									<col width="80px;" />
									<col width="80px;" />
									<col width="250px;" />
								</colgroup>
								
								<tr bgcolor="#e4e4e4">
									<th><spring:message code='text.srm.field.select' 		/></th><%--No --%>
									<th><spring:message code='text.srm.field.venCd'			/></th><%--업체코드 --%>
									<th><spring:message code='text.srm.field.certiNo2'		/></th><%--인증서번호 --%>
									<th><spring:message code='text.srm.field.certiName'		/></th><%--인증명 --%>
									<th><spring:message code='text.srm.field.certiOrgan'	/></th><%--인증기관명 --%>
									<th><spring:message code='text.srm.field.certiStdDate'	/></th><%--인증기준일자 --%>
									<th><spring:message code='text.srm.field.certiEndDate'	/></th><%--인증종료일자 --%>
									<th><spring:message code='text.srm.field.prodCd'		/></th><%--상품코드 --%>
									<th><spring:message code='text.srm.field.prodCdNm'		/></th><%--상품명 --%>
								</tr>
								
								<tbody id="dataMainListbody" />
							</table>
						</div>
					</div>
				
					<!-- Paging 영역 start --------------------------->
					<div id="pages">
						<div id="paging" class="page"></div>
					</div>
					<!-- Paging 영역 end ----------------------------->
					
					<!----- 인증 등록 Start ---------------------------->
					<p style="margin-top: 5px;" />
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit"><spring:message code='text.srm.field.certiInfoAddUpdate' /></li><%--인증정보 등록/수정 --%>
							<li class="btn">
								<a href="#" class="btn" onclick="fnSaveConfirm();"><span><spring:message code="button.srm.save"/></span></a><%--저장 --%>																	
							</li>
						</ul>
						
						<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
							<colgroup>
								<col width="15%" />
								<col width="35%" />
								<col width="15%" />
								<col width="35%" />
							</colgroup>
							
							<tr>
								<th><label>*<spring:message code='text.srm.field.srmjon0042.sub.title1' /></label></th><%--인증정보 --%>
								<td id="tdCcertiType1" colspan="3">
									<input type="text"	id="certiName" name="certiName" readonly="readonly" style="width: 40%;"  />
									<a href="#" id="btnCerti" class="btn" onclick="fnSearchCertiInfo();"><span><spring:message code='button.srm.find' /></span></a><%--Find --%>
								</td>
								<td id="tdCcertiType2" colspan="3" style="display: none;">
									<span id="spanCertiName"></span>
								</td>
							</tr>
							
							<tr>
								<th><label for="venCd">*<spring:message code='text.srm.field.venCompany' /></label></th><%--협력업체 --%>
								<td id="tdVenCd1"><html:codeTag objId="venCd" objName="venCd" width="155px;" dataType="CP" comType="SELECT" formName="text" /></td>
								<td id="tdVenCd2" style="display: none;"><span id="spanVenCd"></span></td>
							</tr>
							
							<tr>
								<th><label>*<spring:message code='text.srm.field.certiDate2' /></label></th><%--인증일자 --%>
								<td>
									<input type="text" id="certiStdDate" name="certiStdDate" readonly="readonly" disabled="disabled" style="width: 30%" />
									<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('MyForm.certiStdDate');" style="cursor:pointer;" />
									 ~ 
									<input type="text" id="certiEndDate" name="certiEndDate" readonly="readonly" disabled="disabled" style="width: 30%" />
									<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('MyForm.certiEndDate');" style="cursor:pointer;" />
								</td>
								
								<th><label for="certiOrgan">*<spring:message code='text.srm.field.certiOrgan' /></label></th><%--인증기관명 --%>
								<td><input type="text" id="certiOrgan" name="certiOrgan" style="width: 55%" /></td>
							</tr>
							
							<tr id="divProd" style="display: none;">
								<th><label>*<spring:message code='text.srm.field.prodInfo' /></label></th><%--상품 --%>
								<td id="tdProdCd1" colspan="3">
									<input type="text" id="prodCd" name="prodCd" readonly="readonly" disabled="disabled" style="width: 70px; text-align: center;" />
									<a href="#" class="btn" onclick="fnSearchCodeNm();"><span><spring:message code='button.srm.find' /></span></a><%--Find --%>
									<input type="text" id="prodCdName" name="prodCdName" readonly="readonly" disabled="disabled" style="width: 40%;"  />
								</td>
								<td id="tdProdCd2">
									<span id="spanProdCdName"></span>
								</td>
							</tr>
							
							<tr>
							<th><label><spring:message code='text.srm.field.certiAdd' /></label></th><%--인증서 --%>
								<td colspan="3">
									<input type="hidden" id="certiAttachNoFileName" name="certiAttachNoFileName" disabled="disabled" readonly="readonly" style="width:250px;" />
									<input type="file" id="certiAttachNoFile" 	name="certiAttachNoFile" onchange="fileUpload(this, 'certiAttachNoFileName');"/>
									<a href="#" class="btn" onclick="fileClear('certiAttachNoFile', 'certiAttachNoFileName');"><span><spring:message code='button.srm.cancel' /></span></a><%--취소 --%>
									<input type="hidden" id="certiAttachNo" name="certiAttachNo" />
									<a id="certiAttachPathA" name="certiAttachPathA" href="#" ></a>
									<div style="color:red;font-weight:bold;">
										<spring:message code='text.srm.field.srmjon0042Notice1' /><!-- ※ JPG, GIF, PNG, BMP 이미지 파일로 업로드 하세요! -->
									</div>
								</td>
							</tr>
							
						</table>
						<!----- 인증 등록 End ------------------------->
					</div>
				</form>
			</div>
		</div>
		<form id="fileForm" name="fileForm" method="post" action="#">
			<input type="hidden" id="atchFileId" name="atchFileId"/><!-- 첨부파일 아이디 -->
			<input type="hidden" id="fileSn" name="fileSn"/>		<!-- 파일연번 -->
		</form>
		
		<!-- footer -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="notice"></div>
				<div class="location">
					<ul>
						<li><spring:message code='table.srm.srmven0010.colum.title4' /></li><%--홈 --%>
						<li><spring:message code='table.srm.srmven0010.colum.title5' /></li><%--SRM정보 --%>
						<li><spring:message code='table.srm.srmven0010.colum.title6' /></li><%--정보관리 --%>
						<li class="last"><spring:message code='text.srm.field.certiInfoAdd' /></li><%--인증정보등록 --%>
					</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>
	</div>
	
</body>
</html>