<%--
- Author(s): jdj
- Created Date:
- Version : 1.0
- Description : 기획전관리 - 기획전 등록/수정 팝업

--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%-- 버튼권한처리 태그 라이브러리 삭제 --%>
<%@page import="java.util.Map"%>
<%@page import="lcn.module.common.util.DateUtil"%>
<%@page import="com.lcnjf.util.StringUtil"%>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%
    String startDate = DateUtil.formatDate(DateUtil.addMonth(DateUtil.getToday(),-1),"-");
    String endDate   = DateUtil.formatDate(DateUtil.getToday(),"-");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>

<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHead.do" />

<script type="text/javascript">

$(document).ready(function() {
	var pageDiv = $("#pageDiv").val();
	var copyYn    = "${copyYn}";
// 	if(pageDiv == "update"){
// 		if( $("#dispTypeCdChk").val() == '00001'){
// 			$(".pcHidden").show();
// 			$(".moHidden").show();
// 		}else if ($("#dispTypeCdChk").val() == '00002'){
// 			$(".pcHidden").show();
// 			$(".moHidden").hide();
// 		}else if ($("#dispTypeCdChk").val() == '00003'){
// 			$(".pcHidden").hide();
// 			$(".moHidden").show();
// 		}else{
// 			$(".pcHidden").show();
// 			$(".moHidden").hide();
// 		}
// 	}

	$("#hidden1").hide();

	// 부모창 값 전달
	$("#viewMkdpSeq", parent.document).val( $("#mkdpSeq").val() );
	$("#categoryId", parent.document).val( $("#categoryId2").val() );
	$("#mkdpSeq", parent.document).val($("#mkdpSeq").val());
	$("#vendorIdView", parent.document).val($("#vendorIdView").val());
	$("#aprvStsCdChk", parent.document).val($("#aprvStsCdChk").val());
	// 기획전 기간
	$("#mkdpStartDate", parent.document).val($("#mkdpStartDate").val());
	$("#mkdpStartHh", parent.document).val($("#mkdpStartHh").val());
	$("#mkdpStartMm", parent.document).val($("#mkdpStartMm").val());
	$("#mkdpEndDate", parent.document).val($("#mkdpEndDate").val());
	$("#mkdpEndHh", parent.document).val($("#mkdpEndHh").val());
	$("#mkdpEndMm", parent.document).val($("#mkdpEndMm").val());

	$("#aprvStsCdChk", parent.document).val($("#aprvStsCdChk").val());

	// 저장, 수정 후 결과 메세지
	var saveMsg = "${saveMsg}";
	if(saveMsg != "") {
		alert(saveMsg);

		var code = "${saveCode}";
		if(code == "1") {	// 저장 성공
			//opener.doSearch();
			//window.close();
		}
	}

// 	$("#dispTypeCd").change(function() {
// 		if( $(this).val() == '00001' || $(this).val() == '00004'){
// 			$(".pcHidden").show();
// 			$(".moHidden").show();
// 		}else if ($(this).val() == '00002'){
// 			$(".pcHidden").show();
// 			$(".moHidden").hide();
// 		}else if ($(this).val() == '00003'){
// 			$(".pcHidden").hide();
// 			$(".moHidden").show();
// 		}
		// 리스트 비노출도 공통처럼 다 보이게
// 		else{
// 			$(".pcHidden").show();
// 			$(".moHidden").hide();
// 		}

// 	});


	var mkdpSeq   = "${resultMap.MKDP_SEQ}";
	var aprvStsCd = "${resultMap.APRV_STS_CD}";   //승인여부

	// 수정일 경우
	if(mkdpSeq != "") {
		$("#categoryParentId").val("${resultMap.CATEGORY_PARENT_ID}");
		selectCategoryParentIdChange("${resultMap.CATEGORY_PARENT_ID}", true);

		//협력업체
		$("#vendorId").val("${resultMap.VENDOR_ID}");
		//카테고리 분류
		$("#mkdpCatCd").val("${resultMap.MKDP_CAT_CD}");
		//전시유형
		$("#dispTypeCd").val("${resultMap.DISP_TYPE_CD}");
		//담당 MD
		$("#utakMd").val("${resultMap.UTAK_MD}");

		//기획전 카테고리 Readonly
		$("#categoryParentId option").not(":selected").attr("disabled", "disabled");
		$("#categoryChildId option").not(":selected").attr("disabled", "disabled");
		$("#vendorId option").not(":selected").attr("disabled", "disabled");
// 		$("#dispTypeCd option").not(":selected").attr("disabled", "disabled");

		//전시유형
		$("#dispTypeCd").trigger("change");

		//승인상태일경우 전시기간 Readonly
		if(aprvStsCd=="03"){
			$("#mkdpStartDate").attr("readonly", true);
			$("#mkdpStartHh").attr("readonly", true);
			$("#mkdpStartMm").attr("readonly", true);

			$("#mkdpEndDate").attr("readonly", true);
			$("#mkdpEndHh").attr("readonly", true);
			$("#mkdpEndMm").attr("readonly", true);
		}


    // 신규일 경우
	} else {
		$('input:radio[name="aprvStsCd"]:input[value="00"]').attr("checked", true);
		$('input:radio[name="dispYn"]:input[value="Y"]').attr("checked", true);
		$('input:radio[name="regMethodCd"]:input[value="01"]').attr("checked", true);
		$("#dispTypeCd option:eq(1)").attr("selected", "selected");

		//기획전 복사 버튼을 눌러 들어온 다음 기획전 등록 버튼을 눌러 신규 등록을 하는 상태에서 입력을 다한 상태에서 기획전 등록 버튼을 누른 다음 뒤로가기 해서 등록 시
		//기획전 소카테고리가 날라가는 이슈와 전시상태값이 무조건 전시상태값이 전시로 체크되는 이슈 수정
		//기획전 소카테고리가 날라가는 이슈 수정 - 기획전 대카테고리 값을 선택한 상태였으면 다시 소카테고리를 선택하도록 추가함
		if($("#categoryParentId").val() != "" && $("#categoryParentId").val() != "null") {
			selectCategoryParentIdChange($("#categoryParentId").val(), true);
		}

		//$("#categoryParentId option").not(":selected").attr("disabled", "");
		//$("#categoryChildId option").not(":selected").attr("disabled", "");
	}

// 	namoCross1.SetBodyValue( $("#content1").val() );
// 	namoCross2.SetBodyValue( $("#content2").val() );

	//에디터 1 세팅
// 	namoCross1.SetBodyValue(document.getElementById("content1").value);
	//에디터 2 세팅
// 	namoCross2.SetBodyValue(document.getElementById("content2").value);
});

//저장/수정
function fn_update() {

	var pageDiv = "";

	var aprvStsCdChk =  $("#aprvStsCdChk").val();
	var copyYn = $("#copyYn").val();
	var msg = "";

	if(copyYn=="Y"){
		msg = '복사하시겠습니까?';

		pageDiv = "insertCopy";
		$("#pageDiv").val("insertCopy");

	}else{

		if($("#dispYnChk").val()=="Y" && aprvStsCdChk !="00"){
			alert("전시상태에서는 기획전을 수정할 수 없습니다.")
			return;
		}
		if($("#mkdpSeq").val() == ""){
			pageDiv = "insert";
			$("#pageDiv").val("insert");
		}else{
			pageDiv = $("#pageDiv").val();
		}
	}
	//var msg = ""; //주석 한 이유는 위에 기획전 복사할 때 msg 가 안 나옴. 그래서 소스 위로 올림

	if(!fn_validation()) return;

// 	$('#content1').val(namoCross1.GetBodyValue());
// 	$('#content2').val(namoCross2.GetBodyValue());



	if( pageDiv == "insert" ){
		if( $('#dispTypeCd').val() == "00001"){
			if( $('#tmpFile1').val() == "" || $('#tmpFile2').val() == ""){
				alert("이미지를 등록해주십시요.");
				return;
			}
		}else if( $('#dispTypeCd').val() == "00002" ){
			if( $('#tmpFile1').val() == "" ){
				alert("이미지를 등록해주십시요.");
				return;
			}
		}else if( $('#dispTypeCd').val() == "00003" ){
			if( $('#tmpFile2').val() == "" ){
				alert("이미지를 등록해주십시요.");
				return;
			}
		}else{	//리시트 비노출은 소개이미지 체크 안함

		}

		if($('#tmpFile3').val() == ""){
			alert("배너 이미지를 등록해주십시요.");
			return;
		}
		if($('#tmpFile5').val() == ""){
			alert("배너 이미지를 등록해주십시요.");
			return;
		}
		if($('#tmpFile4').val() == ""){
			alert("배너 이미지를 등록해주십시요.");
			return;
		}
	}

	if(pageDiv == "insert") {
		msg = '<spring:message code="msg.common.confirm.regist" />';
	}else if(pageDiv == "update") {
		msg = '<spring:message code="msg.common.confirm.update" />';
	}

	if(confirm(msg)) {
 		$('#form1').attr({action:'<c:url value="/exhibition/insertExhibition.do"/>',target:''}).submit();
 	}
}


// 유효성 검사
function fn_validation() {

	var f = document.form1;

	if(f.vendorId.value == "" || f.vendorId.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="협력업체"/>');
		f.vendorId.focus();
		return;
	}

	if(f.categoryParentId.value == "" || f.categoryParentId.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="기획전 대카테고리"/>');
		f.categoryParentId.focus();
		return;
	}

	//소카테고리 자료가 존재시 => 필수조건 체크
	if($("#categoryChildId").children().length > 1){
		if(f.categoryChildId.value == "" || f.categoryChildId.value == "null") {
			alert('<spring:message code="msg.common.error.required" arguments="기획전 소카테고리"/>');
			f.categoryChildId.focus();
			return;
		}
	}

 	if(f.mkdpNm.value == "" || f.mkdpNm.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="기획전명"/>');
		f.mkdpNm.focus();
		return;
	}
 	if(getByte(f.mkdpNm.value) > 300 ) { // Byte 로 체크하므로 수정
		alert('<spring:message code="msg.common.error.maxlength" arguments="기획전명,100"/>');
		return;
	}
	if(f.mkdpScndTitle.value == "" || f.mkdpScndTitle.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="기획전 부제목"/>');
		f.mkdpScndTitle.focus();
		return;
	}
	if(getByte(f.mkdpScndTitle.value) > 180 ) { // Byte 로 체크하므로 수정
		alert('<spring:message code="msg.common.error.maxlength" arguments="기획전 부제목,60"/>');
		return;
	}

	// mz_storm1927 2018/12/14 작업 start - 기획전명(MO) 추가(기획전명, 전단/코너 타이틀, 혜택/할인정보, 마케팅행사정보)
	if(Common.isEmpty((f.mobileMkdpNm.value)) || f.mobileMkdpNm.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="기획전명(MO)"/>');
		f.mobileMkdpNm.focus();
		return;
	}
	if(getByte(f.mobileMkdpNm.value) > 45 ) {
		alert('<spring:message code="msg.common.error.maxlength" arguments="기획전명(MO),15"/>');
		return;
	}
	
	if(Common.isEmpty((f.mobileMkdpScndTitle.value)) || f.mobileMkdpScndTitle.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="전단/코너 타이틀1"/>');
		f.mobileMkdpScndTitle.focus();
		return;
	}
	if(getByte(f.mobileMkdpScndTitle.value) > 30 ) {
		alert('<spring:message code="msg.common.error.maxlength" arguments="전단/코너 타이틀1,10"/>');
		return;
	}
	
  	if(Common.isEmpty((f.mobileMkdpScndTitle2.value)) || f.mobileMkdpScndTitle2.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="전단/코너 타이틀2"/>');
		f.mobileMkdpScndTitle2.focus();
		return;
	}
  	
	if(getByte(f.mobileMkdpScndTitle2.value) > 30 ) {
		alert('<spring:message code="msg.common.error.maxlength" arguments="전단/코너 타이틀2,10"/>');
		return;
	}
	
  	if(Common.isEmpty((f.dcInfo.value)) || f.dcInfo.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="혜택/할인정보1"/>');
		f.dcInfo.focus();
		return;
	} 
	if(getByte(f.dcInfo.value) > 30 ) {
		alert('<spring:message code="msg.common.error.maxlength" arguments="혜택/할인정보1,10"/>');
		return;
	}
	
  	if(Common.isEmpty((f.dcInfo2.value)) || f.dcInfo2.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="혜택/할인정보2"/>');
		f.dcInfo2.focus();
		return;
	} 
	if(getByte(f.dcInfo2.value) > 30 ) {
		alert('<spring:message code="msg.common.error.maxlength" arguments="혜택/할인정보2,10"/>');
		return;
	}

	if(getByte(f.evtInfo.value) > 45 ) {
		alert('<spring:message code="msg.common.error.maxlength" arguments="마케팅행사 정보,15"/>');
		return;
	}
	// mz_storm1927 2018/12/14 작업 end
	
	//기획전 기간
	if(f.mkdpStartDate.value == "" || f.mkdpStartDate.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="기획전 시작일자"/>');
		f.mkdpStartDate.focus();
		return;
	}
	if(f.mkdpStartHh.value == "" || f.mkdpStartHh.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="기획전 시작시간"/>');
		f.mkdpStartHh.focus();
		return;
	}
	if(f.mkdpStartMm.value == "" || f.mkdpStartMm.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="기획전 시작분"/>');
		f.mkdpStartMm.focus();
		return;
	}

	//숫자여부 체크
	if(isNaN($("#mkdpStartHh").val()) == true) {
		alert('전시 시작시간은 숫자만 입력가능합니다.');
		$("#mkdpStartHh").focus();
		return false;
	}
	if(isNaN($("#mkdpStartMm").val()) == true) {
		alert('전시 시작분은 숫자만 입력가능합니다.');
		$("#mkdpStartMm").focus();
		return false;
	}

	//시간 체크
	 if(fHourCheck($("#mkdpStartHh").val())){
		alert("전시 시작시간이 잘못 입력되었습니다.");
		$("#mkdpStartHh").focus();
		return false;
	}
	if(fMinuteCheck($("#mkdpStartMm").val())){
		alert("전시 시작분이 잘못 입력되었습니다.");
		$("#mkdpStartMm").focus();
		return false;
	}

	if(f.mkdpEndDate.value == "" || f.mkdpEndDate.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="기획전 종료일자"/>');
		f.mkdpEndDate.focus();
		return;
	}
	if(f.mkdpEndHh.value == "" || f.mkdpEndHh.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="기획전 종료시간"/>');
		f.mkdpEndHh.focus();
		return;
	}
	if(f.mkdpEndMm.value == "" || f.mkdpEndMm.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="기획전 종료분"/>');
		f.mkdpEndMm.focus();
		return;
	}

	//숫자여부 체크
	if(isNaN($("#mkdpEndHh").val()) == true) {
		alert('전시 종료시간은 숫자만 입력가능합니다.');
		$("#mkdpEndHh").focus();
		return false;
	}
	if(isNaN($("#mkdpEndMm").val()) == true) {
		alert('전시 종료분은 숫자만 입력가능합니다.');
		$("#mkdpEndMm").focus();
		return false;
	}
	//시간 체크
	if(fHourCheck($("#mkdpEndHh").val())){
		alert("전시 종료시간이 잘못 입력되었습니다.");
		$("#mkdpEndHh").focus();
		return false;
	}
	if(fMinuteCheck($("#mkdpEndMm").val())){
		alert("전시 종료분이 잘못 입력되었습니다.");
		$("#mkdpEndMm").focus();
		return false;
	}

	// 시작시간 0 채우기
	if( $("#mkdpStartHh").val() < 10 && $("#mkdpStartHh").val().length  != 2){
		$("#mkdpStartHh").val("0"+$("#mkdpStartHh").val());
		if ($("#mkdpStartHh").val().length > 2){
			$("#mkdpStartHh").val("00");
		}
	}

	if( $("#mkdpStartMm").val() < 10 && $("#mkdpStartMm").val().length  != 2){
		$("#mkdpStartMm").val("0"+$("#mkdpStartMm").val());
		if ($("#mkdpStartMm").val().length > 2){
			$("#mkdpStartMm").val("00");
		}
	}

	if( $("#mkdpEndHh").val() < 10 && $("#mkdpEndHh").val().length  != 2){
		$("#mkdpEndHh").val("0"+$("#mkdpEndHh").val());
		if ($("#mkdpEndHh").val().length > 2){
			$("#mkdpEndHh").val("00");
		}
	}

	if( $("#mkdpEndMm").val() < 10 && $("#mkdpEndMm").val().length  != 2){
		$("#mkdpEndMm").val("0"+$("#mkdpEndMm").val());
		if ($("#mkdpEndMm").val().length > 2){
			$("#mkdpEndMm").val("00");
		}
	}

	//기획전 기간 입력체크 시작===
	var mkdpStartDay = $("#mkdpStartDate").val().replace(/-/gi, '') + $("#mkdpStartHh").val() + $("#mkdpStartMm").val();
	var mkdpEndDay   = $("#mkdpEndDate").val().replace(/-/gi, '')   + $("#mkdpEndHh").val()   + $("#mkdpEndMm").val();

	if(mkdpStartDay > mkdpEndDay){
		alert("기획전 종료일자가 시작일자보다 작을수 없습니다.");
		return
	}
	//기획전 기간 입력체크 종료===

	//전시유형
	if(f.dispTypeCd.value == "" || f.dispTypeCd.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="전시유형"/>');
		f.dispTypeCd.focus();
		return;
	}

	//담당MD
	if(f.utakMd.value == "" || f.utakMd.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="담당MD"/>');
		f.utakMd.focus();
		return;
	}

	//카테고리분류
	if(f.mkdpCatCd.value == "" || f.mkdpCatCd.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="카테고리 분류"/>');
		f.mkdpCatCd.focus();
		return;
	}

	//키워드
 	if(f.kywrd.value == "" || f.kywrd.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="키워드"/>');
		f.kywrd.focus();
		return;
	}
	if(getByte(f.kywrd.value) > 90 ) {
		alert('<spring:message code="msg.common.error.maxlength" arguments="키워드,90"/>');
		return;
	}
	if(f.strCd.value == "" || f.strCd.value == "null") {
		alert('<spring:message code="msg.common.error.required" arguments="점포할당 코드"/>');
		f.strNm.focus();
		return;
	}

	return true;
}

//시간 체크
function fHourCheck(val){
	var hourChk = false;
	if(val > 23){
		hourChk = true;
	}
	return hourChk;
}

// 분 체크
function fMinuteCheck(val){
	var minuteChk = false;
	if(val > 59){
		minuteChk = true;
	}
	return minuteChk;
}

//MD팝업
function fn_mdPopUp(md) {
	var targetUrl = '<c:url value="/exhibition/adminForm.do"/>';
	Common.centerPopupWindow(targetUrl, 'adminForm', {width : 800, height : 550,scrollBars : 'YES'});
}

// 이미지 미리보기
// function fn_showImg() {
// 	var gudnImg = $("#gudnImg").val();

// 	var targetUrl = "${pageContext.request.contextPath}/newTemplate/showImageForm.do"
//         +"?gudnImg="+ gudnImg;
// 		Common.centerPopupWindow(targetUrl, 'imageForm', {
// 			title : '이미지 미리보기 팝업',
// 			width : 600,
// 			height : 600,
// 			scrollBars : 'NO'
// 		});
// }


// 대카테고리 변경시
//selectCategoryParentIdChange(this)   categoryChildId
function selectCategoryParentIdChange(parentId, fistFlag) {

	var formQueryString = $('*', '#form1').fieldSerialize();
	var targetUrl = '<c:url value="/exhibition/selectCategoryChildIdList.do"/>';
	comboReset(document.form1.categoryChildId);   //콤보초기화

	$.ajax({
		type: 'POST',
		url: targetUrl,
		data: formQueryString,
		success: function(json) {
			try {
				//comboCall( document.form1.selSoGoods, json.soCdList, 'ALL');
				comboCall( document.form1.categoryChildId, json.exhibitionCategoryChildList, null);

				if(fistFlag==true) $("#categoryChildId").val("${resultMap.CATEGORY_CHILD_ID}");

				var mkdpSeq = "${resultMap.MKDP_SEQ}";

				// 수정일 경우
				if(mkdpSeq != "") {
					$("#categoryChildId option").not(":selected").attr("disabled", "disabled");
				}else{
					//$("#categoryChildId option").not(":selected").attr("disabled", "");
				}

			} catch (e) {}
		},
		error: function(e) {
			alert(e);
		}
	});
}



function tmpFile1Change() {
	 $("#tmpFile1Chk").val("1");
}

function tmpFile2Change() {
	$("#tmpFile2Chk").val("1");
}

function tmpFile3Change() {
	$("#tmpFile3Chk").val("1");
}

function tmpFile4Change() {
	$("#tmpFile4Chk").val("1");
}

function tmpFile5Change() {
	$("#tmpFile5Chk").val("1");
}

function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임
	$("#vendorId").val(strVendorId);
}


/** ********************************************************
 * 입력 수 제한
 ******************************************************** */
function chkword(obj, maxByte) {

    var strValue = obj.value;
    var strLen = strValue.length;
    var totalByte = 0;
    var len = 0;
    var oneChar = "";
    var str2 = "";

    for (var i = 0; i < strLen; i++) {
        oneChar = strValue.charAt(i);
        if (escape(oneChar).length > 4) {
            totalByte += 2;
        } else {
            totalByte++;
        }

        // 입력한 문자 길이보다 넘치면 잘라내기 위해 저장
        if (totalByte <= maxByte) {
            len = i + 1;
        }
    }

    // 넘어가는 글자는 자른다.
    if (totalByte > maxByte) {
        alert("한글 기준 " + maxByte/2 + "자를 초과 입력 할 수 없습니다.");
        str2 = strValue.substr(0, len);
        obj.value = str2;
        chkword(obj, 4000);
    }
}

function fn_strCdPopup() {
	if($("#copyYn").val() == "Y") {
		alert("복사후 사용하세요.");
		return;
	}
	
/* 	//수정상태일 경우 점포팝업 X
	if($("#mkdpSeq").val() != "") {
		return;
	} */
	
	//openStr("M");	
	openStr("M", "strCd");
}

//점포팝업 반환값 셋팅 
function fnSetStr(data) {
	var strCd = "", strNm = "";
	
	if(jQuery.isArray(data.strCdArr)){		
		for(var i=0; i < data.strCdArr.length; i++ ){
			strCd += data.strCdArr[i]+',';
			strNm += data.strNmArr[i]+',';
		}
		if(strCd!="") strCd = strCd.substring(0, strCd.length-1);
		if(strNm!="") strNm = strNm.substring(0, strNm.length-1);
		
	}else{		
		strCd = data.strCd;
		strNm = data.strNm;		
	}
	
	$("#strCd").val(strCd);
	$("#strNm").val(strNm);
	
	//점포코드 수정여부체크
	var oldStrCd = "${resultMap.STR_CD }";
	if(oldStrCd != strCd){
		$("#storeModifyYn").val("Y");
	}else{
		$("#storeModifyYn").val("N");
	}
	
	//console.log("storeModifyYn=>"+$("#storeModifyYn").val());
}

</script>


</head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<body>
<form name="form1" id="form1" method="post"  enctype="multipart/form-data">
<div id="content_wrap">
<div class="content_scroll">
<input type="hidden" id="divCorn" name="divCorn" >
<input type="hidden" id="gudnImg" name="gudnImg" value="<c:out value="${resultMap.GUDN_IMG }"/>" >
<input type="hidden" id="pageDiv" name="pageDiv" value="<c:out value="${pageDiv }"/>"">
<input type="hidden" id="vendorIdView" name="vendorIdView" value="<c:out value="${vendorIdView }"/>"">
<input type="hidden" id="aprvStsCdChk" name="aprvStsCdChk" value="<c:out value="${aprvStsCdChk }"/>"">
<input type="hidden" id="dispTypeCdChk" name="dispTypeCdChk" value="<c:out value="${dispTypeCdChk }"/>"">
<input type="hidden" id="ContentSeq1" name="ContentSeq1" value="<c:out value="${resultMap.CONTENTS_SEQ }"/>"">
<input type="hidden" id="ContentSeq2" name="ContentSeq2" value="<c:out value="${resultMap.CONTENTS_SEQ2 }"/>"">
<input type="hidden" id="ContentSeq3" name="ContentSeq3" value="<c:out value="${resultMap.CONTENTS_SEQ3 }"/>"">
<input type="hidden" id="ContentSeq4" name="ContentSeq4" value="<c:out value="${resultMap.CONTENTS_SEQ4 }"/>"">
<input type="hidden" id="ContentSeq5" name="ContentSeq5" value="<c:out value="${resultMap.CONTENTS_SEQ5 }"/>"">
 <input type="hidden" id="dispYnChk" name="dispYnChk" value="<c:out value="${resultMap.DISP_YN }"/>"">

<input type="hidden" id="mkdpSeq" name="mkdpSeq" value="<c:out value="${resultMap.MKDP_SEQ }"/>" >
<input type="hidden" id="tmpFile1Chk" name="tmpFile1Chk" value="" >
<input type="hidden" id="tmpFile2Chk" name="tmpFile2Chk" value="" >
<input type="hidden" id="tmpFile3Chk" name="tmpFile3Chk" value="" >
<input type="hidden" id="tmpFile4Chk" name="tmpFile4Chk" value="" >
<input type="hidden" id="tmpFile5Chk" name="tmpFile5Chk" value="" >
<input type="hidden" id="categoryId2" name="categoryId2" value="${resultMap.CATEGORY_CHILD_ID }" >
<input type="hidden" id="mkdpSeq2"    name="mkdpSeq2"    value="${resultMap.MKDP_SEQ }" >
<input type="hidden" id="copyYn"  name="copyYn"  value="<c:out value="${copyYn }"/>" >

<!-- 점포코드(멀티) -->
<input type="hidden" id="strCd"   name="strCd"   value="<c:out value="${resultMap.STR_CD }"/>" >

<!-- 점포코드 수정시 Y로 변경 -->
<input type="hidden" id="storeModifyYn"  name="storeModifyYn"  value="N"/>

	  <!-- 전시코너 대상정보 시작 -->
	  <ul>
			<div class="wrap_search">
				<!-- 01 : search -->
				<div class="bbs_search">
					<ul class="tit">
						<li class="tit">기획전 기본정보</li>
						<li class="btn">
							협력업체코드
		                        <c:choose>
									<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
										<input type="text" name="vendorId" id="vendorId" readonly="readonly" value="<c:if test="${not empty epcLoginVO.repVendorId}">${epcLoginVO.repVendorId}</c:if>" style="width:40%;"/>
										<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
									</c:when>
									<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
										<select name="vendorId" id="vendorId" class="select">
											<option value="">전체</option>
										<c:forEach items="${epcLoginVO.vendorId}" var="venArr" begin="0">
					                        <option value="${venArr}" <c:if test="${not empty epcLoginVO.repVendorId && venArr eq epcLoginVO.repVendorId }">selected</c:if>>${venArr}</option>
										</c:forEach>
										</select>
									</c:when>
								</c:choose>
<!-- 								승인대기 반려일때 저장버튼 보임 -->
							<%-- <c:if test="${aprvStsCdChk  eq '00' || aprvStsCdChk  eq '02'}"> --%>
		                    <c:if test="${aprvStsCdChk  eq '00' || aprvStsCdChk  eq '02'||aprvStsCdChk  eq '03' }">
	                       		<!-- <a href="javascript:fn_update();" class="btn" id="search"><span>저장</span></a> -->
	                            <a href="javascript:fn_update();"class="btn"><span>${copyYn == 'Y' ? '복사' : '저장' }</span></a>
		                    </c:if>
						</li>
					</ul>

            <table class="bbs_search" cellpadding="0" cellspacing="0" border="0" width="98%">
	        <col width="15%" />
	        <!-- mz_storm1927 2018/12/14 작업으로 인해 col 추가 -->
	        <col width="10%" />
	        <col width="35%" />
	        <col width="15%" />
	        <col width=" " />
	        <tr id="hidden1" >
	        	<th>기획전 코드</th>
	          	<td id="viewMkdpSeq">${resultMap.MKDP_SEQ}</td>

	          	<th>승인여부</th>
	          	<td>
                     <c:forEach items="${applyCdList }" var="value">
                     	<label>
	                    <input name="aprvStsCd" id="aprvStsCd"  type="radio" class="choice" title="${value.CD_NM }" value="${value.MINOR_CD }" ${resultMap.APRV_STS_CD == value.MINOR_CD ? 'checked' : '' } disabled />${value.CD_NM }</label>
	                    &nbsp;
                     </c:forEach>
	          	</td>
	        </tr>
	        <tr>
	        	<th><em>*</em>기획전 카테고리</th>
	          	<td colspan="4">
	          		<select name="categoryParentId" id="categoryParentId" style="width:30%" title="기획전 대카테고리"  onChange="javascript:selectCategoryParentIdChange(this, false)">
	            		<option value="">선택</option>
                   		<c:forEach items="${exhibitionCategoryList }" var="value">
                   			<option value="${value.CATEGORY_ID }">${value.CATEGORY_NM }</option>
                   		</c:forEach>
	          		</select>
	          		<select name="categoryChildId" id="categoryChildId" style="width:60%" title="기획전 소카테고리" >
	            		<option value="">선택</option>
                   		<c:forEach items="${exhibitionCategoryChildList }" var="value">
                   			<option value="${value.code }">${value.name }</option>
                   		</c:forEach>
	          		</select>
	          	</td>
	        </tr>
	        <tr>
	        	<th><em>*</em>기획전명</th>
	          	<td colspan="4">
	          		<input type="text" name="mkdpNm" id="mkdpNm" value="${resultMap.MKDP_NM}" style="width:80%">
	          	</td>
	        </tr>
	        <tr>
	        	<th><em>*</em>기획전 부제목</th>
	          	<td colspan="4">
	          		<input type="text" name="mkdpScndTitle" id="mkdpScndTitle" value="${resultMap.MKDP_SCND_TITLE}" style="width:80%">
	          	</td>
	        </tr>
			<!-- mz_storm1927 2018/12/14 작업 start - 기획전명(MO) 추가(기획전명, 전단/코너 타이틀, 혜택/할인정보, 마케팅행사정보) -->
			<tr>
	        	<th rowspan="4">기획전명(MO)</th>
	        	<td style="background-color: #ebe5da; text-align:right;">①&nbsp;<em style="color: #C00; font-weight: bold;">*</em>기획전명 &nbsp;&nbsp;</td>
	          	<td colspan="3" style="height: 55px">
	          		<input type="text" name="mobileMkdpNm" id="mobileMkdpNm" value="<c:out value='${resultMap.MOBILE_MKDP_NM}' />" style="width:80%" onkeyup="chkword(this, 30)" placeholder="MO 기획전명을 입력하세요"/>
	          		<br/>
	          		※ 최대 15자까지만 입력하여 주시기 바랍니다.
	          	</td>
	     	</tr>
	     	<tr>
	        	<td style="background-color: #ebe5da; text-align:right;">②&nbsp;<em style="color: #C00; font-weight: bold;">*</em>전단/코너 타이틀 &nbsp;&nbsp;</td>
	          	<td colspan="3" style="width:60%; height: 55px">
	          		<input type="text" name="mobileMkdpScndTitle" id="mobileMkdpScndTitle" value="<c:out value='${resultMap.MOBILE_MKDP_SCND_TITLE}' />" style="width:40%" onkeyup="chkword(this, 20)" placeholder="전단 또는 코너 타이틀1 (최대10자) 입력하세요"/> &nbsp;          	
	          		<input type="text" name="mobileMkdpScndTitle2" id="mobileMkdpScndTitle2" value="<c:out value='${resultMap.MOBILE_MKDP_SCND_TITLE2}' />" style="width:40%" onkeyup="chkword(this, 20)" placeholder="전단 또는 코너 타이틀2 (최대10자) 입력하세요"/>	 
	          		<br/>
	          		※ 입력한 전단 또는 코너타이틀은 최대 20자까지 허용되며 해당 메시지는 두 줄로 화면에 전시 됩니다.
	          	</td>
	        </tr>
	     	<tr>
	        	<td style="background-color: #ebe5da; text-align:right;">③&nbsp;<em style="color: #C00; font-weight: bold;">*</em>혜택/할인정보 &nbsp;&nbsp;</td>
	          	<td colspan="3" style="width:60%; height: 55px">
	          		<input type="text" name="dcInfo" id="dcInfo" value="<c:out value='${resultMap.DC_INFO}' />" style="width:40%" onkeyup="chkword(this, 20)" placeholder="혜택/할인정보1 (최대10자) 입력하세요"/> &nbsp;
	          		<input type="text" name="dcInfo2" id="dcInfo2" value="<c:out value='${resultMap.DC_INFO2}' />" style="width:40%" onkeyup="chkword(this, 20)" placeholder="혜택/할인정보2 (최대10자) 입력하세요"/>	          	
	          		<br/>
	          		※ 입력한 혜택/할인정보는 최대 20자까지 허용되며 해당 메시지는 두 줄로 화면에 전시 됩니다.
	          	</td>
	        </tr>
	        <tr>
	        	<td style="background-color: #ebe5da; text-align:right;">④&nbsp;마케팅행사 정보 &nbsp;&nbsp;</td>
	          	<td colspan="3" style="width:60%; height: 55px">
	          		<input type="text" name="evtInfo" id="evtInfo" value="<c:out value='${resultMap.EVT_INFO}' />" style="width:90%" onkeyup="chkword(this, 30)" placeholder="마케팅행사 관련 광고문구를 입력하세요 (예:출석체크 매일~1천 마일리지 / 신한카드5%청구할인)"/>	          	
	          		<br/>
	          		※ 최대 15자까지만 입력하여 주시기 바랍니다.
	          	</td>
	        </tr>
			<!-- mz_storm1927 2018/12/14 작업 end -->
	        <tr>
	        	<th><em>*</em>기획전 기간</th>
	          	<td colspan="2">
				<c:if test="${resultMap.APRV_STS_CD eq '03'}">
					<input name="mkdpStartDate" type="text" id="mkdpStartDate" style="width:75px" title="년월일" value="${resultMap.VIEW_MKDP_START_YYMMDD}">
				</c:if>
				<c:if test="${resultMap.APRV_STS_CD ne '03'}">
					<input class="day" name="mkdpStartDate" type="text" id="mkdpStartDate" style="width:75px" title="년월일"
					onclick="javascript:openCal('form1.mkdpStartDate');" value="${resultMap.MKDP_START_DATE}">
					<a href="javascript:openCal('form1.mkdpStartDate');">
						<img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" />
					</a>
				</c:if>

					<input name="mkdpStartHh" type="text" id="mkdpStartHh" style="width:20px" title="시작시간" value="${resultMap.MKDP_START_HH}" maxLength="2">
					<input name="mkdpStartMm" type="text" id="mkdpStartMm" style="width:20px" title="시작분"   value="${resultMap.MKDP_START_MM}" maxLength="2">
					~

				<c:if test="${resultMap.APRV_STS_CD eq '03'}">
					<input name="mkdpEndDate" type="text" id="mkdpEndDate" style="width:75px" title="년월일" value="${resultMap.VIEW_MKDP_END_YYMMDD}">
				</c:if>
				<c:if test="${resultMap.APRV_STS_CD ne '03'}">
					<input class="day" name="mkdpEndDate" type="text" id="mkdpEndDate" style="width:75px" title="년월일"
					onclick="javascript:openCal('form1.mkdpEndDate');" value="${resultMap.MKDP_END_DATE}">
					<a href="javascript:openCal('form1.mkdpEndDate');">
						<img src="${lfn:getString('system.cdn.static.path')}/images/epc/layout/btn_cal.gif" alt="" class="middle" />
					</a>
				</c:if>

					<input name="mkdpEndHh" type="text" id="mkdpEndHh" style="width:20px" title="종료시간" value="${resultMap.MKDP_END_HH}" maxLength="2">
					<input name="mkdpEndMm" type="text" id="mkdpEndMm" style="width:20px" title="종료분" value="${resultMap.MKDP_END_MM}"   maxLength="2">
	          	</td>

	          	<th><em>*</em>전시유형</th>
	          	<td>
	          		<select name="dispTypeCd" id="dispTypeCd" style="width:140px" title="카테고리 분류" >
	            		<option value="">선택하세요</option>
                   		<c:forEach items="${dispTypeList }" var="value">
                   			<option value="${value.MINOR_CD }">${value.CD_NM }</option>
                   		</c:forEach>
	          		</select>
	          	</td>
	        </tr>

	        <tr>
	        	<th><em>*</em>담당 MD</th>
	          	<td colspan="2">
					<input name="utakMd"   type="text" id="utakMd" style="width:50px" title="담당MD 코드" value="${resultMap.UTAK_MD}" readonly>
                    <a href="javascript:fn_mdPopUp(this);"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_search.gif" alt="" class="middle" /></a>
                    <input name="utakMdNm" type="text" id="utakMdNm" style="width:40%" title="담당MD 명칭" value="${resultMap.UTAK_MD_NM}" readonly>
	          	</td>
	        	<th><em>*</em>카테고리 분류</th>
	          	<td>
	          		<select name="mkdpCatCd" id="mkdpCatCd" style="width:80%" title="카테고리 분류" >
	            		<option value="">선택하세요</option>
                   		<c:forEach items="${categoryTypeCdList }" var="value">
                   			<option value="${value.MINOR_CD }">${value.CD_NM }</option>
                   		</c:forEach>
	          		</select>
	          	</td>
	        </tr>

	        <tr>
	        	<th><em>*</em>키워드</th>
	          	<td colspan="2">
	          		<input type="text" name="kywrd" id="kywrd" value="${resultMap.KYWRD}" style="width:80%">
	          	</td>
	          	<th><em>*</em>전시상태</th>
	          	<td>
<%-- 	          	<c:if test="${pageDiv eq 'update' }"> --%>
        		     <label>
                       <input name="dispYn" id="dispYn"  type="radio" class="choice" title="Y" value="Y" ${resultMap.DISP_YN == 'Y' ? 'checked' : '' }/>전시</label>
                     &nbsp;
                     <label>
                       <input name="dispYn" id="dispYn"  type="radio" class="choice" title="N" value="N" ${resultMap.DISP_YN == 'N' ? 'checked' : '' }/>전시안함</label>
<%-- 				</c:if>	 --%>
<%-- 	          	<c:if test="${pageDiv ne 'update' }"> --%>
<!--         		     <label> -->
<!--                        <input name="dispYn" id="dispYn"  type="radio" class="choice" title="Y" value="Y" />전시</label> -->
<!--                      &nbsp; -->
<!--                      <label> -->
<!--                        <input name="dispYn" id="dispYn"  type="radio" class="choice" title="N" value="N" />전시안함</label>	          	 -->
<%-- 				</c:if>	 --%>
	          	</td>
	        </tr>
<!-- 	        <tr> -->
<!-- 	        <th>등록구분</th> -->
<!-- 	          	<td colspan="3"> -->
<!-- 	          		<label> -->
<%--                        <input name="regMethodCd" id="regMethodCd"  type="radio" class="choice" title="01" value="01" ${resultMap.REG_METHOD_CD == '01' ? 'checked' : '' }/>파일 URL</label> --%>
<!--                      &nbsp; -->
<!--                      <label> -->
<%--                        <input name="regMethodCd" id="regMethodCd"  type="radio" class="choice" title="02" value="02" ${resultMap.REG_METHOD_CD == '02' ? 'checked' : '' }/>에디트 편집</label> --%>
<!-- 	          	</td> -->
<!-- 	        </tr> -->

			<tr class="pcHidden">
	        <th><em>*</em>점포할당</th>
	          	<td colspan="4">
                    <textarea name="strNm" id="strNm" rows="3" cols="108" readonly style="resize:none;">${resultMap.STR_NM}</textarea>
	          		<a href="javascript:fn_strCdPopup();"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/btn/bt_search.gif" alt="" class="middle" /></a>
	          	</td>
	        </tr>
	        <tr class="pcHidden">
	        <th><em>*</em>기획전소개(PC)</th>
	          	<td colspan="4">
	          		<input type="file" name="tmpFile1" id="tmpFile1" style="width:50%" onchange="tmpFile1Change()" >
	          		<br/>
	          		<c:if test="${resultMap.IMG_PATH eq null}">
					<img src='' onerror="this.style.display='none'" alt='이미지' />
					</c:if>
					<c:if test="${resultMap.IMG_PATH ne null}">
	          		<img width="100" height="100" src="${_epc_image_path}${resultMap.IMG_PATH}" alt="이미지" />
					</c:if>
					<b>Tip.</b> 기획전 상세페이지 상단영역  기획전소개내용 이미지(가로x세로 = 980x가변)
	          	</td>
	        </tr>
	        <tr class="moHidden">
	        <th><em>*</em>기획전소개(MOBILE)</th>
	          	<td colspan="4">
	          		<input type="file" name="tmpFile2" id="tmpFile2" style="width:50%" onchange="tmpFile2Change()" >
	          		<br/>
	          		<c:if test="${resultMap.IMG_PATH2 eq null}">
					<img src='' onerror="this.style.display='none'" alt='이미지' />
					</c:if>
					<c:if test="${resultMap.IMG_PATH2 ne null}">
	          		<img width="100" height="100" src="${_epc_image_path}${resultMap.IMG_PATH2}" alt="이미지" />
					</c:if>
					<b>Tip.</b> 기획전 상세페이지 상단영역  기획전소개내용 이미지(가로x세로 = 584x가변)
	          	</td>
	        </tr>
	        <tr>
	        <th><em>*</em>기획전 배너<br/>(PC)</th>
	          	<td colspan="4">
	          		<input type="file" name="tmpFile3" id="tmpFile3" style="width:50%" onchange="tmpFile3Change()" >
	          		<br/>
	          		<c:if test="${resultMap.IMG_PATH3 eq null}">
					<img src='' onerror="this.style.display='none'" alt='이미지' />
					</c:if>
					<c:if test="${resultMap.IMG_PATH3 ne null}">
	          		<img width="100" height="100" src="${_epc_image_path}${resultMap.IMG_PATH3}" alt="이미지" />
					</c:if>
					<b>Tip.</b> 기획전 목록페이지 기획전 이미지 (가로x세로 = 240x240)
	          	</td>

	        </tr>
	        <tr>
	        <th><em>*</em>기획전 배너<br/>(MOBILE)</th>
	          	<td colspan="4">
	          		<input type="file" name="tmpFile5" id="tmpFile5" style="width:50%" onchange="tmpFile5Change()" >
	          		<br/>
	          		<c:if test="${resultMap.IMG_PATH5 eq null}">
					<img src='' onerror="this.style.display='none'" alt='이미지' />
					</c:if>
					<c:if test="${resultMap.IMG_PATH5 ne null}">
	          		<img width="100" height="100" src="${_epc_image_path}${resultMap.IMG_PATH5}" alt="이미지" />
					</c:if>
					<b>Tip.</b> 기획전 목록페이지 기획전 이미지 (가로x세로 = 640x440)
	          	</td>

	        </tr>
	        <th><em>*</em>기획전 배너<br/>(MOBILE 검색결과)</th>
	          	<td colspan="4">
	          		<input type="file" name="tmpFile4" id="tmpFile4" style="width:50%" onchange="tmpFile4Change()" >
	          		<br/>
	          		<c:if test="${resultMap.IMG_PATH4 eq null}">
					<img src='' onerror="this.style.display='none'" alt='이미지' />
					</c:if>
					<c:if test="${resultMap.IMG_PATH4 ne null}">
	          		<img width="100" height="100" src="${_epc_image_path}${resultMap.IMG_PATH4}" alt="이미지" />
					</c:if>
					<b>Tip.</b> MOBILE 검색결과 페이지 노출형 기획전 이미지 (가로x세로 = 640x240)
	          	</td>

	        </tr>
<!-- 	        <tr class="pcHidden"> -->
<!-- 				<th>기획전 출력정보<br/>(PC 전용)</th> -->
<!-- 					<td colspan="3"> -->
<%-- 						<input type="hidden" name="content1" id="content1" value='<c:out value="${resultMap.INDT_HTML}" escapeXml="false"/>' /> --%>
<!-- 						<script type="text/javascript" language="javascript">
 							var namoCross1 = new NamoSE('pe_agt');
 							namoCross1.params.Width 		= "98%";
 							namoCross1.params.Height 		= "300";
 							namoCross1.params.UserLang 	= "auto";
 							namoCross1.params.ImageSavePath = "edi";
 							namoCross1.params.FullScreen = false;
 							namoCross1.params.SetFocus 	= false; // 에디터 포커스 해제
 							namoCross1.EditorStart();
						</script>

					</td> -->

<!-- 			</tr> -->
<!-- 	        <tr class="moHidden"> -->
<!-- 				<th>기획전 출력정보<br/>(모바일 전용)</th> -->
<!-- 					<td colspan="3"> -->
<%-- 						<input type="hidden" name="content2" id="content2" value='<c:out value="${resultMap.INDT_HTML2}" escapeXml="false"/>'/> --%>
<!-- 						<script type="text/javascript" language="javascript">
 							var namoCross2 = new NamoSE('pe_agt1');
 							namoCross2.params.Width 		= "98%";
 							namoCross2.params.Height 		= "300";
 							namoCross2.params.UserLang 	= "auto";
 							namoCross2.params.ImageSavePath = "edi";
 							namoCross2.params.FullScreen = false;
 							namoCross2.params.SetFocus 	= false; // 에디터 포커스 해제
 							namoCross2.EditorStart();
 						</script>

 					</td> -->
			</tr>
	      </table>
	  			</div>
	  		</div>
	  </ul>
 </div> <!-- content scroll -->
	  <!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>기획전관리</li>
					<li class="last">기획전등록</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>


</form>
</body>
</html>