<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lfn" uri="/WEB-INF/tlds/function.tld" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="epcutl" uri="/WEB-INF/tlds/epcutl.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.lottemart.epc.common.util.SecureUtil"%>
<%
	String prodCd = SecureUtil.stripXSS(request.getParameter("prodCd"));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="No-Cache" />
<meta http-equiv="Pragma" content="No-Cache" />
<!-- 공통 css 및 js 파일 -->
<c:import url="/common/commonHeadPopup.do" />

<script>

	//20180911 상품키워드 입력 기능 추가
	$(document).ready(function(){
		if("${resultMap.KEYWORD_YN}" == "N") {
			//alert("해당 상품은 상품키워드가 등록되어 있지 않습니다.\n하단의 상품키워드 탭에서 검색어 입력 후 수정 가능합니다.");
			alert("해당 상품은 상품키워드가 등록되어 있지 않습니다.\n상품키워드가 등록되어 있지 않거나 상품정보수정 승인요청 또는 반려 상태가 아닌 경우 상품 수정을 할 수 없습니다.");
		}
		
	});
	//20180911 상품키워드 입력 기능 추가

	// 상품정보 업데이트
	function doUpdate() {
		var form = document.dataForm;
		//값 트림, 크기 채크(바이트)
		$('#PROD_NM').val($.trim($('#PROD_NM').val()));
		if (!fnChkSpcCharByte(form.PROD_NM, "인터넷상품명", 300)) {
			$('#PROD_NM').focus();
			return;
		}
		$('#BRAND_NM').val($.trim($('#BRAND_NM').val()));
		if (!fnChkSpcCharByte(form.BRAND_NM, "브랜드", 100)) {
			$('#BRAND_NM').focus();
			return;
		}
		$('#MAKER_NM').val($.trim($('#MAKER_NM').val()));
		if (!fnChkSpcCharByte(form.MAKER_NM, "제조사", 100)) {
			$('#MAKER_NM').focus();
			return;
		}   
		$('#MAIN_MTRL_NM').val($.trim($('#MAIN_MTRL_NM').val()));
		if (!fnChkSpcCharByte(form.MAIN_MTRL_NM, "주재료", 400)) {
			$('#MAIN_MTRL_NM').focus();
			return;
		}
		$('#PROD_STANDARD_NM').val($.trim($('#PROD_STANDARD_NM').val()));
		if (!fnChkSpcCharByte(form.PROD_STANDARD_NM, "중량/규격", 50)) {
			$('#PROD_STANDARD_NM').focus();
			return;
		}
		$('#NFOML_VARIATION_DESC').val($.trim($('#NFOML_VARIATION_DESC').val()));
		if (!fnChkSpcCharByte(form.NFOML_VARIATION_DESC, "옵션값", 2000)) {
			$('#NFOML_VARIATION_DESC').focus();
			return;
		}
		
		//값 입력 유무 채크
		if(Common.isEmpty($.trim($('#PROD_NM').val()))){
			alert('<spring:message code="msg.common.error.required" arguments="인터넷상품명"/>');
			$('#PROD_NM').focus();
			return;
		}

		//값 상호 채크
		if ($('#PROD_DIVN_CD').val() == '02') {
			var MAX_ORD_PSBT_QTY = parseInt(removeComma($('#MAX_ORD_PSBT_QTY').val()));
			var MIN_ORD_PSBT_QTY = parseInt(removeComma($('#MIN_ORD_PSBT_QTY').val()));
			
			if(!(MIN_ORD_PSBT_QTY <= MAX_ORD_PSBT_QTY)){
				alert("최대 주문 수량은 최소 주문 수량과 같거나 커야 합니다.!");
				$('#MIN_ORD_PSBT_QTY').focus();
				return;
			}
		}

		//체험형 상품 Y일때 판매자 추천평 입력
		if($(":radio[name='exprProdYn']:checked").val() == "1" && $("#sellerRecomm").val().trim() == ""){
			alert('판매자 추천평을 작성해주세요.');
			$("#sellerRecomm").focus();
			return;
		}

//전상법 체크ECOM_YN->N DISP_YN->YECOM_YN 

//alert($(':input:radio[name=DISP_YN]:checked').val());
//alert($('#ECOM_YN').val());

//   if($(':input:radio[name=DISP_YN]:checked').val()== 'Y' && $('#ECOM_YN').val()== 'N'&& $('#ECOM_YN_APPROVE').val()== 'Y'){

	//값 상호 채크 온라인이거나 소셜일때 
		if ($('#PROD_DIVN_CD').val() == '02'||$('#PROD_DIVN_CD').val() == '03') {

			//최대주문량이 변경되었을때 행사상품은 문의 공지
			if("${resultMap.MAX_ORD_PSBT_QTY}" != $('#MAX_ORD_PSBT_QTY').val() ){
				if("${resultMap.PROMOTION_YN}" == 'Y' ){
					alert("본 상품은 행사상품이므로 해당 MD에게 문의 후 변경하시기 바랍니다.");
				}
			}
		}

//	alert($('#PROD_DIVN_CD').val());
//	alert("${resultMap.MAX_ORD_PSBT_QTY}");
//	alert($('#MAX_ORD_PSBT_QTY').val());

		if($(':input:radio[name=DISP_YN]:checked').val()== 'Y' && $('#ECOM_YN').val()== 'N'){
			alert(" 전상법 품목고시가  있어야 수정가능 가능합니다.");
			return;
		}

		if( ($("#ONLINE_PROD_TYPE_CD").val() == "03" || $("#ONLINE_PROD_TYPE_CD").val() == "13") && $("#NOCH_DELI_YN").val() == "N"){
			alert("설치 상품 배송비 정책 변경으로 인해\n현재 적용중인  배송비는 더 이상 사용할 수 없습니다.\n설치 상품은 무료배송만 선택 할 수 있습니다.\n※고정배송비 체크 후 0원을 입력하세요.");
			return;
		}
		
		if($(':input:radio[name=DISP_YN]:checked').val()== 'Y' && $('#ECOM_YN_APPROVE').val()== 'N'){
			alert(" 전상법 품목고시와 MD전상법확인이 있어야 수정가능 가능합니다.");
			return;
		}

		if($("#ONLINE_PROD_TYPE_CD").val() == "04"){
			if( $("input[name='OPTN_LOAD_SET_QTY']").val() == "" ){
				alert("세트수량 정보를 확인해주시기 바랍니다.");
				$("input[name='OPTN_LOAD_SET_QTY']").eq(1).focus();
				return;
			}
		}

		if($("#ONLINE_PROD_TYPE_CD").val() == "07"){
			$("input[name='RSERV_ORD_PSBT_START_DY']").val($("input[name='RSERV_ORD_PSBT_START_DAY']").val().replace(/-/gi,"")+$("select[name='RSERV_ORD_PSBT_START_HOUR'] option:selected").val());
			$("input[name='RSERV_ORD_PSBT_END_DY']").val($("input[name='RSERV_ORD_PSBT_END_DAY']").val().replace(/-/gi,"")+$("select[name='RSERV_ORD_PSBT_END_HOUR'] option:selected").val());
			$("input[name='RSERV_PROD_PICK_IDCT_DY']").val($("input[name='RSERV_PROD_PICK_IDCT_DAY']").val().replace(/-/gi,""));

			if($("input[name='RSERV_ORD_PSBT_START_DY']").val().length != 10){
				alert("예약 주문 가능 시간 시작시간을 확인해주시기 바랍니다.");
				$("input[name='RSERV_ORD_PSBT_START_DAY']").focus();
				return;
			}

			if($("input[name='RSERV_ORD_PSBT_END_DY']").val().length != 10){
				alert("예약 주문 가능 시간 종료시간을 확인해주시기 바랍니다.");
				$("input[name='RSERV_ORD_PSBT_END_DAY").focus();
				return;
			}

			if($("input[name='RSERV_PROD_PICK_IDCT_DY']").val().length != 8){
				alert("예약상품 출고지시일을 확인해주시기 바랍니다.");
				$("input[name='RSERV_PROD_PICK_IDCT_DAY']").focus();
				return;
			}
		}
		if($("#ONLINE_PROD_TYPE_CD").val() == "09"){
			if( $("input[name='PROD_OPTION_DESC']").val() == "" ){
				$("input[name='PROD_OPTION_DESC']").val("주문 후 5~10일 정도 소요");
			}
		}

		<%-- 위수탁 --%>
		<c:if test="${resultMap.PROD_DIVN_CD eq '02'}">
		if ( $(':input:radio[name=FEDAY_MALL_SELL_PSBT_YN]:checked').val() == "Y" && $("#FEDAY_MALL_PROD_DIVN_CD").val() != '5' ){
			alert("명절몰 판매여부가 Y일 경우 명절몰상품구분은 '명절업체배송'만 선택 가능합니다.");
			return; 
		} else if ( $(':input:radio[name=FEDAY_MALL_SELL_PSBT_YN]:checked').val() == "N" && $("#FEDAY_MALL_PROD_DIVN_CD").val() != '0' ){
			alert("명절몰 판매여부가 N일 경우 명절몰상품구분은 '미취급'만 선택 가능합니다.");
			return;
		}
		</c:if>

		<%-- 직매입 --%>
		<c:if test="${resultMap.PROD_DIVN_CD ne '02'}">
		if ( $(':input:radio[name=FEDAY_MALL_SELL_PSBT_YN]:checked').val() == "Y" && ($("#FEDAY_MALL_PROD_DIVN_CD").val() == '0' || $("#FEDAY_MALL_PROD_DIVN_CD").val() == '5') ) {
			alert("명절몰 판매여부가 Y일 경우 명절몰상품구분은 '미취급', '명절업체배송'을 제외하고 선택 가능합니다.");
			return;
		} else if ( $(':input:radio[name=FEDAY_MALL_SELL_PSBT_YN]:checked').val() == "N" && $("#FEDAY_MALL_PROD_DIVN_CD").val() != '0' ) {
			alert("명절몰 판매여부가 N일 경우 명절몰상품구분은 '미취급'만 선택 가능합니다.");
			return;
		}
		</c:if>

		if( confirm("상품정보를 저장 하시겠습니까?") ){

			//20180911 상품키워드 입력 기능 추가
			var paramInfo = {};

			//상품코드
			paramInfo["prodCd"] = "${resultMap.PROD_CD}";

			$.ajax({
				contentType : 'application/json; charset=utf-8',
				type : 'POST',
				dataType : 'JSON',
				async : false,
				url : '<c:url value="/edi/product/selectKeywordYnChk.json"/>',
				data : JSON.stringify(paramInfo),
				success : function(data) {
					if (data.msg == "SUCCESS") {
						//성공
						callAjaxByForm('#dataForm', '<c:url value="/product/updateProductInfo.do"/>', '#dataForm', 'POST');
					} else {
						//실패
						//alert("해당 상품은 상품키워드가 등록되어 있지 않습니다.\n하단의 상품키워드 탭에서 검색어 입력 후 수정 가능합니다.");
						alert("해당 상품은 상품키워드가 등록되어 있지 않습니다.\n상품키워드가 등록되어 있지 않거나 상품정보수정 승인요청 또는 반려 상태가 아닌 경우 상품 수정을 할 수 없습니다.");
					}
				}
			});
			//callAjaxByForm('#dataForm', '<c:url value="/product/updateProductInfo.do"/>', '#dataForm', 'POST');
			//20180911 상품키워드 입력 기능 추가
		}
	}

	// 상품정보 수정 요청 (ajax)
	function callAjaxByForm(form, url, target, Type) {

		var formQueryString = $('*', '#dataForm').fieldSerialize();
		$.ajax({
			type: Type,
			url: url,
			data: formQueryString,
			success: function(json) {
				try {
					if(jQuery.trim(json) == ""){//처리성공
						alert('<spring:message code="msg.common.success.update"/>');
						self.location.reload();
					}else{
						alert(jQuery.trim(json));
					}

				} catch (e) {}
			},
			error: function(e) {
				alert(e);
			}
		});
		
	}

	function radioVariationChk(str){
		if(str == 'Y'){
			if( confirm("옵션값을 사용하실수 없습니다. 사용하시겠습니까? \n저장버튼을 누른 후 반영됩니다.") ){
				radioVariationChange("Y");
			}else{
				$("input[name=VARIATION_YN]").filter("input[value=N]").attr("checked", "checked");
			}
		}else{
			radioVariationChange("N");
		}
	}

	function radioVariationChange(str){
		if(str == 'Y'){
			$("#NFOML_VARIATION_DESC").val("");
			$("#NFOML_VARIATION_DESC").attr('readonly', true);
		}else{
			$("#NFOML_VARIATION_DESC").attr('readonly', false);
		}
	}

	//원산지 팝업
	function popupLocation() {
		var targetUrl = '<c:url value="/product/selectLocationPopUpView.do"/>';
		Common.centerPopupWindow(targetUrl, 'epcLocationPopup', {width : 520, height : 800});
	}

	//원산지 검색창으로 부터 받은 원산지 정보 입력
	function setLocationInto(locationId, locationName) { // 이 펑션은 원산지 검색창에서 호출하는 펑션임
		$("#HOME_CD").val(locationId);
		$("#HOME_NM").val(locationName);
	}

	//연동사이트 팝업
	function popupSiteLink() {
		var targetUrl = '<c:url value="/product/selectProductSiteLinkForm.do"/>?prodCd=<%=prodCd%>';
		Common.centerPopupWindow(targetUrl, 'epcProductSiteLinkPopup', {width : 801, height : 290});
	}

	/**********************************************************
	 * 숫자만 입력 가능
	 ******************************************************** */
	function onlyNumber() {
		if ((event.keyCode<48) || (event.keyCode>57)) {
			event.returnValue = false;
		}
	}

	/**********************************************************
	 * 단위가격표시
	 ******************************************************** */
	function fnMoneyComma(nMoney, flag) {
		var charRight = String(nMoney);
		var charSplit = '';
		var charMoney = '';

		for (index = charRight.length - 1; index >= 0; index--) {
			charSplit = charRight.charAt(index);
			charMoney = charSplit + charMoney;

			if (index % 3 == charRight.length % 3 && index != 0) {
				charMoney=',' + charMoney;
			}
		}
		
		if ( flag == 'out' ) {
			document.write(charMoney);
		} else if ( flag == 'in' ) {
			return charMoney;
		} else {
			alert("flag error");
		}
	}
	
	/**********************************************************
	 * 입력한 숫자의 자릿수 체크 / 컴마 표시 및
	 * 표시총량 / 상품표시기준 변경에 다른 단위가격표시 변경
	 ******************************************************** */
	function doChange(chkValue, chkTitle, chklength, chkYn) {
		if (chkValue.value == null || chkValue.value == "") {
			chkValue.value = 0;
			return;
		} else if (chkValue.value == 0) {
			return;
		}

		if (removeComma(chkValue.value).length > chklength) {
			alert(chkTitle + " 항목은 " + chklength + " 자리 까지만 입력가능합니다.");
			fnFocus(chkTitle);
			return;
		}

		chkValue.value = addTxtComma( chkValue.value ); // 컴마 적용

		if ( chkYn == 'Y' ) {
			var DP_UNIT_NM	= $('#DP_UNIT_NM').val();  //표시단위코드명
			var CURR_SELL_PRC = Number($('#CURR_SELL_PRC').val());  //판매가
			var DP_TOT_QTY	= Number(removeComma($('#DP_TOT_QTY').val()));  //MD표시총량
			var DP_BASE_QTY   = Number(removeComma($('#DP_BASE_QTY').val()));  //표시기준수량(상품표시기준)
			//2진 부동소수점 연산 범위 문제로 유효숫자범위를 정해준다.
			var validNumberScope = 100000000;
			//표시단위가격:(판매가/표시총수량)*표시기준수량
			var UNIT_SALE_PRC = Math.round( ( CURR_SELL_PRC / DP_TOT_QTY * DP_BASE_QTY ) * validNumberScope ) / validNumberScope;
			var TOT_UNIT_SALE = addTxtComma(String(UNIT_SALE_PRC)) + "원" + " : " +  DP_BASE_QTY + DP_UNIT_NM + "당";
			
			$('#TOT_UNIT_SALE').text(TOT_UNIT_SALE);
   		}
	}

	function fnFocus(chkTitle) {
		if ( chkTitle == "MD표시총량" ) {
			$('#DP_TOT_QTY').val($('#DP_TOT_QTY_ORI').val());
			$('#DP_TOT_QTY').focus();
		} else if ( chkTitle == "상품표시기준" ) {
			$('#DP_BASE_QTY').val($('#DP_BASE_QTY_ORI').val());
			$('#DP_BASE_QTY').focus();
		} else if ( chkTitle == "표시총량" ) {
			$('#DP_TOTAMT').val($('#DP_TOTAMT_ORI').val());
			$('#DP_TOTAMT').focus();
		} else if ( chkTitle == "최소주문가능수량" ) {
			$('#MIN_ORD_PSBT_QTY').val($('#MIN_ORD_PSBT_QTY_ORI').val());
			$('#MIN_ORD_PSBT_QTY').focus();
		} else if ( chkTitle == "최대주문가능수량" ) {
			$('#MAX_ORD_PSBT_QTY').val($('#MAX_ORD_PSBT_QTY_ORI').val());
			$('#MAX_ORD_PSBT_QTY').focus();
		} else if ( chkTitle == "신선상품환산수량" ) {
			$('#FRSH_CONV_QTY').val($('#FRSH_CONV_QTY_ORI').val());
			$('#FRSH_CONV_QTY').focus();
		}
	}

	/** ********************************************************
	 * 입력항목의 최대값을 체크한다.
	 ******************************************************** */
	function fnChkSpcCharByte(chkValue, chkTitle, chklength) {
		var str = chkValue.value;
		var len = 0;
		var expAll  = /[~!@\#$%^&*\()<>{}\[\]\-=+_'\"]/gi;
		var expPart = /[<>]/gi;

		if (str == null || (event.keyCode == 13)) {
			return false;
		}

		if (str.search(expPart) != -1) {
			alert(chkTitle + "항목에는 특수문자 '<'와 '>'를 사용할수 없습니다!");
			return false;
		}

		for (var i = 0; i < str.length; i++) {
			var charSetValue = 'utf-8';

			if ( charSetValue == 'euc-kr' ) {
				// euc-kr : 한글 2byte용
				var c = escape(str.charAt(i));

				if (c.length == 1) {
					len++;
				} else if (c.indexOf("%u") != -1) {
					len += 2;
				} else if (c.indexOf("%") != -1) {
					len += c.length / 3;
				}
			} else if ( charSetValue == 'utf-8' ) {
				// utf-8 : 한글 3byte용
				var charCode = str.charCodeAt(i);

				if ( charCode <= 0x0007F ) {
					len += 1;
				} else if ( charCode <= 0x0007FF ) {
					len += 2;
				} else if ( charCode <= 0x00FFFF ) {
					len += 3;
				} else {
					len += 4;
				}
			}

		}

		if (len > chklength) {
			alert(chkTitle + " 항목은 " + chklength + "Byte (한글 3Byte, 영숫자 1Byte) 까지만\n" + "입력가능합니다.\n\n" + "[ 현재 : " + len + "Byte ]");
			return false;
		}

		return true;
	}

	/**********************************************************
	 * 특수문자 입력 방지 ex) onKeyPress="keyCode(event)"
	 ******************************************************** */
	function keyCode(e, chkTitle) {
		var code = (window.event) ? event.keyCode : e.which; // IE : FF - Chrome both

		if (code == 60 || code == 62) keyResult(e, chkTitle);
	}

	function keyResult(e, chkTitle) {
		alert(chkTitle + "항목은 특수문자 '<'와 '>'를 사용할수 없습니다!");
		
		if (navigator.appName != "Netscape") {
			event.returnValue = false;  //IE - Chrome both
		} else {
			e.preventDefault(); //FF - Chrome both
		}
	}

	// 상품 열람
	function PopupInfo(prodCD) {
		var prodCd	= prodCD;
		var targetUrl = "http://www.lottemart.com/product/ProductDetail.do?ProductCD=" + prodCd + "&strCd=307&approvalGbn=N&previewYN=Y";
		Common.centerPopupWindow(targetUrl, 'epcProductDetailView', {width : 970, height : 650, scrollBars : 'YES'});
	}

	function exprRadioCheck() {
		if($("input[name='exprProdYn']:checked").val() == "1"){
			$("#sellerRecomm").removeAttr("disabled");
		} else {
			$("#sellerRecomm").attr("disabled", "disabled");
		}
	}

 // 체험형 상품 여부 라디오 버튼 이벤트 제어
	$(function(){
		$("input[name='exprProdYn']:radio").click(function(){
			exprRadioCheck();
		})
	}); 

	// 판매자 추천평 바이트수 체크
	var maxMessage
	function checkByte() {
		var limitByte = 100; //최대 바이트
		var message = $("#sellerRecomm").val();
		var totalByte = 0;

		for(var i =0; i < message.length; i++) {
			var currentByte = message.charCodeAt(i);
			if(currentByte > 128) totalByte += 2;
			else totalByte++;
		}

		if(totalByte == limitByte){
			maxMessage = message;
		}

		if(totalByte <= limitByte){
			$('#messagebyte').html(totalByte);
		} else if(totalByte > limitByte){
			alert( limitByte+"바이트까지 전송가능합니다.");
			$('#messagebyte').html(limitByte);
			frm.sellerRecomm.value = maxMessage;
		}
	}
</script>

<script language="javascript" type="text/javascript">
	$(document).ready(function(){
		//input enter 막기
		$("*").keypress(function(e){
			if(e.keyCode==13) return false;
		});

		//로딩시 체험형 정보
		exprRadioCheck();
		checkByte();

		//상품상세정보 로딩후 아래쪽 탭에 초기 탭페이지 호출
		//document.form1.vendorId.value = document.dataForm.VENDOR_ID.value;
		document.form1.target = "prdDetailFrame";
		document.form1.action = '<c:url value="/product/selectProductItemForm.do"/>';//초기 탭 페이지
		document.form1.submit();

		radioVariationChange("${resultMap.VARIATION_YN}");

		$('#sitelink').click(function() {
			popupSiteLink();
		});
		$('#update').click(function() {
			doUpdate();
		});
		$('#close').click(function() {
			top.close();
		});
	});
</script>
</head>
<body>
<form name="form1" id="form1">
	<input type="hidden" id="prodCd" name="prodCd" value="<%=prodCd%>" />
	<input type="hidden" id="vendorId" name="vendorId" value="${resultMap.VENDOR_ID}" />
</form>
<form name="dataForm" id="dataForm">
	<input type="hidden" name="PROD_CD" id="PROD_CD" value="${resultMap.PROD_CD}" />
	<input type="hidden" name="VENDOR_ID" id="VENDOR_ID" value="${resultMap.VENDOR_ID}" />
	<input type="hidden" name="INSERT_STATUS" id="INSERT_STATUS" value="" />
	<input type="hidden" name="APRV_YN" id="APRV_YN" value="${resultMap.APRV_YN}" />
	<input type="hidden" name="FEDAY_MALL_PROD_DIVN_CD_ORI" id="FEDAY_MALL_PROD_DIVN_CD_ORI" value="${resultMap.FEDAY_MALL_PROD_DIVN_CD}" />
	<input type="hidden" name="AU_USE_YN" id="AU_USE_YN" value="${resultMap.AU_USE_YN}" />
	<input type="hidden" name="HOME_CD" id="HOME_CD" value="${resultMap.HOME_CD}" />
	<input type="hidden" name="PROD_DIVN_CD" id="PROD_DIVN_CD"  value="${resultMap.PROD_DIVN_CD}" /><!-- 상품구분코드 -->
	<input type="hidden" name="CURR_SELL_PRC" id="CURR_SELL_PRC" value="${resultMap.CURR_SELL_PRC}"/><!-- 판매가 -->
	<input type="hidden" name="DP_UNIT_NM" id="DP_UNIT_NM" value="${resultMap.DP_UNIT_NM}" /><!-- 표시단위코드명 -->
	<%--<input type="hidden" name="DP_TOT_QTY" id="DP_TOT_QTY" value="${resultMap.DP_TOT_QTY}" /> MD표시총량 --%>
	<input type="hidden" name="ECOM_YN" id="ECOM_YN" value="${resultMap.ECOM_YN}" /><!-- 전상법여부 -->
	<input type="hidden" name="ECOM_YN_APPROVE" id="ECOM_YN_APPROVE" value="${resultMap.ECOM_YN_APPROVE}" /><!-- 전상법여부 -->
	<input type="hidden" name="ONLINE_PROD_TYPE_CD" id="ONLINE_PROD_TYPE_CD" value="${resultMap.ONLINE_PROD_TYPE_CD}" /><!-- 온라인상품유형 -->
	<input type="hidden" name="NOCH_DELI_YN" id="NOCH_DELI_YN" value="${resultMap.NOCH_DELI_YN}" /><!-- 무료배송여부 -->
<div id="popup">
	<!--  @title  -->
	<div id="p_title1">
		<h1>상품 information</h1>
		<span class="logo"><img src="${lfn:getString('system.cdn.static.path')}/images/epc/popup/logo_pop.gif" alt="LOTTE MART" /></span>
	</div>
	<!--  @title  //-->
	<!--  @process  -->
	<div id="process1">
		<ul>
			<li>홈</li>
			<li>상품관리</li>
			<li class="last">인터넷상품관리</li>
		</ul>
	</div>
	<!--  @process  //-->
	<div class="popup_contents">
		<!--  @작성양식 2 -->
		<div class="bbs_search3">
			<ul class="tit">
				<li class="tit">상품 information</li>
				<li class="btn">
					<!--a href="#" class="btn" id="sitelink"><span>연동사이트 정보</span></a-->
					<a href="#" class="btn" id="update"  ><span><spring:message code="button.common.update"/></span></a>
					<a href="#" class="btn" id="close"   ><span><spring:message code="button.common.close" /></span></a>
				</li>
			</ul>
			<table class="bbs_grid2" cellpadding="0" cellspacing="0" border="0">
				<colgroup>
					<col width="10%">
					<col width="15%">
					<col width="10%">
					<col width="15%">
					<col width="10%">
					<col width="15%">
					<col width="10%">
					<col width="15%">
				</colgroup>
				<tr>
					<th>인터넷상품코드</th>
					<td>${resultMap.PROD_CD}</td>
					<th>판매코드</th>
					<td>${resultMap.MD_SRCMK_CD}</td>
					<th>상품코드</th>
					<td>${resultMap.MD_PROD_CD}</td>
					<th>상품구분</th>
					<td>${resultMap.PROD_DIVN_NM}
						<input type="hidden" id="prodDivVal" name="prodDivVal" value="${resultMap.PROD_DIVN_CD}"/> <!-- 온라인상품 체크용 -->
					</td>
				</tr>
				<c:if test="${resultMap.PROD_DIVN_CD eq '02'}">
				<tr>
					<th>상품유형</th>
					<td colspan="3">
						${resultMap.ONLINE_PROD_TYPE_NM}
					</td>
					 <%-- <c:if test="${resultMap.ONLINE_PROD_TYPE_CD eq '02' || resultMap.ONLINE_PROD_TYPE_CD eq '03' || resultMap.ONLINE_PROD_TYPE_CD eq '04' || resultMap.ONLINE_PROD_TYPE_CD eq '07' || resultMap.ONLINE_PROD_TYPE_CD eq '09'}"> --%>
					<th>상품유형상세</th>
					<td colspan="3">
						<c:choose>
							<c:when test="${resultMap.ONLINE_PROD_TYPE_CD eq '02' || resultMap.ONLINE_PROD_TYPE_CD eq '03'}"> <!-- 상품유형 - 주문제작,설치상품 -->
								<span>희망배송일은 주문일로부터</span>
								<select name="HOPE_DELI_PSBT_DD">
									<c:forEach begin="1" step="1" end="30"  var="ddVal">
										<option value="${ddVal}" <c:if test="${resultMap.HOPE_DELI_PSBT_DD eq ddVal}">selected="selected"</c:if>>${ddVal}일</option>
									</c:forEach>
								</select>
								<span>이후부터 가능</span>
							</c:when>
							<c:when test="${resultMap.ONLINE_PROD_TYPE_CD eq '04'}"> <!-- 상품유형 - 골라담기 -->
								<span>세트수량</span>
								<input type="text" name="OPTN_LOAD_SET_QTY" value="${ resultMap.OPTN_LOAD_SET_QTY }" />
								<span>옵션</span>
								<input type="text" name="OPTN_LOAD_CONTENT" value="${ resultMap.OPTN_LOAD_CONTENT }" />
							</c:when>
							<c:when test="${resultMap.ONLINE_PROD_TYPE_CD eq '07'}"> <!-- 상품유형 - 예약상품 -->
								<span>예약 주문 가능시간</span><br/>
								<input type="hidden" id="rservOrdPsbtStartDy" name="RSERV_ORD_PSBT_START_DY">
								<input type="text" id="rservOrdPsbtStartDay" name="RSERV_ORD_PSBT_START_DAY" style="width:65px" class="day" value="<c:out value="${ resultMap.RSERV_ORD_PSBT_START_DAY }"/>">
								<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('dataForm.rservOrdPsbtStartDay');" style="cursor:hand;" />
								<select name="RSERV_ORD_PSBT_START_HOUR">
									<c:forEach begin="0" end="23" var="rservOrdPsbtStartHour">
										<fmt:formatNumber minIntegerDigits="2" value="${rservOrdPsbtStartHour}" var="fmtRservOrdPsbtStartHour"  />
										<option value="<c:out value="${ fmtRservOrdPsbtStartHour }"/>" <c:if test="${ fmtRservOrdPsbtStartHour eq resultMap.RSERV_ORD_PSBT_START_HOUR }">selected="selected"</c:if>><fmt:formatNumber minIntegerDigits="2" value="${rservOrdPsbtStartHour}" /></option>
									</c:forEach>
								</select>
								~
								<input type="hidden" id="rservOrdPsbtEndDy" name="RSERV_ORD_PSBT_END_DY">
								<input type="text" id="rservOrdPsbtEndDay" name="RSERV_ORD_PSBT_END_DAY" style="width:65px" class="day" value="<c:out value="${ resultMap.RSERV_ORD_PSBT_END_DAY }"/>">
								<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('dataForm.rservOrdPsbtEndDay');" style="cursor:hand;" />
								<select name="RSERV_ORD_PSBT_END_HOUR">
									<c:forEach begin="0" end="23" var="rservOrdPsbtEndHour">
										<fmt:formatNumber minIntegerDigits="2" value="${rservOrdPsbtEndHour}" var="fmtRservOrdPsbtEndHour"  />
										<option value="<c:out value="${ fmtRservOrdPsbtEndHour }"/>" <c:if test="${ fmtRservOrdPsbtEndHour eq resultMap.RSERV_ORD_PSBT_END_HOUR }">selected="selected"</c:if>><fmt:formatNumber minIntegerDigits="2" value="${rservOrdPsbtEndHour}" /></option>
									</c:forEach>
								</select>
								<br/>
								<span>예약상품 출고지시일</span><br/>
								<input type="hidden" id="rservProdPickIdctDy" name="RSERV_PROD_PICK_IDCT_DY">
								<input type="text" id="rservProdPickIdctDay" name="RSERV_PROD_PICK_IDCT_DAY" style="width:65px" class="day" value="<c:out value="${ resultMap.RSERV_PROD_PICK_IDCT_DAY }"/>">
								<img src="/images/epc/layout/btn_cal.gif" class="middle" onClick="openCal('dataForm.rservProdPickIdctDay');" style="cursor:hand;" />
							</c:when>
							<c:when test="${resultMap.ONLINE_PROD_TYPE_CD eq '09'}"> <!-- 상품유형 - 구매대행상품 -->
								 배송예정일&nbsp;
								<input type="text"  name="PROD_OPTION_DESC"  id="PROD_OPTION_DESC"  style="width:70%;" maxlength="150"  value="${resultMap.PROD_OPTION_DESC}"/>
								<br/>
								<font color="blue" style="font-size:11px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  ※ 미입력시 [주문 후 5~10일 정도 소요] 문구로 일괄 적용됩니다.</font>
							</c:when>
						</c:choose>
					</td>
					<%-- </c:if> --%>
				</tr>
				</c:if>
				<tr>
					<th><span class="star">*</span><a href="javascript:PopupInfo('${resultMap.PROD_CD}');"> 인터넷상품명</a></th>
					<td><input type="text" name="PROD_NM" id="PROD_NM" value="${resultMap.PROD_NM}" onKeyPress="keyCode(event,'인터넷상품명')" onChange="fnChkSpcCharByte(this,'인터넷상품명',300)"></td>
					<th>행사상품명</th>
					<td>${resultMap.EVT_PROD_NM}</td>
					<th>MD상품명</th>
					<td>${resultMap.MD_PROD_NM}</td>
					<th>MD상품단축명</th>
					<td>${resultMap.MD_PROD_SHORT_NM}</td>
				</tr>
				<tr>
					<th><span class="star"></span>EC 상품코드</th>
					<td>${resultMap.EC_PROD_CD}</td>
					<th><span class="star"></span>EC 연동여부</th>
					<td>${resultMap.EC_LINK_YN}</td>
					<th><span class="star"></span>EC 판매상태</th>
					<td>${resultMap.EC_SL_STAT_NM}</td>
					<th><span class="star"></span>EC 전시상태</th>
					<td>${resultMap.EC_DISP_YN}</td>
				</tr>
				<tr>
					<th><span class="star"></span>EC</br>표준카테고리</th>
					<td colspan="7">${resultMap.STD_CAT_NM}</td>
				</tr>
				<tr>
					<th>협력사</th>
					<td>${resultMap.VENDOR_ID} / ${resultMap.VENDOR_NM}</td>
					<th>소분류</th>
					<td>${resultMap.CAT_NM}</td>
					<th>기본카테고리</th>
					<td colspan="3">${resultMap.CATEGORY_ID} <br/>| ${resultMap.CATEGORY_NM}</td>
				</tr>
				<tr>
				   <th>MD브랜드</th>
				   <td>${resultMap.MD_BRAND_NM}</td>
				   <th>브랜드</th>
				   <td><input type="text" name="BRAND_NM" id="BRAND_NM" value="${resultMap.BRAND_NM}" onKeyPress="keyCode(event,'브랜드')" onChange="fnChkSpcCharByte(this,'브랜드',100)"></td>
				   <th>증정품</th>
				   <td colspan="3">
					   <c:if test="${presentListBe == '1'}" >
						   <select style="width:370px">
							   <c:forEach items="${presentList}" var="code" begin="0">
								   <option value="${code.presentNm }">${code.presentNm }</option>
							   </c:forEach>
						   </select>
					   </c:if>
					</td>
				</tr>
				<tr>
				   <th>MD메이커</th>
				   <td>${resultMap.MD_MAKER_NM}</td>
				   <th>제조사</th>
				   <td><input type="text" name="MAKER_NM" id="MAKER_NM" value="${resultMap.MAKER_NM}" onKeyPress="keyCode(event,'제조사')" onChange="fnChkSpcCharByte(this,'제조사',100)"></td>
				   <th>주재료</th>
				   <td><input type="text" name="MAIN_MTRL_NM" id="MAIN_MTRL_NM" value="${resultMap.MAIN_MTRL_NM}" onKeyPress="keyCode(event,'주재료')" onChange="fnChkSpcCharByte(this,'주재료',400)"></td>
				   <th><a class="link" href="javascript:popupLocation();">원산지</a></th>
				   <td><input type="text" name="HOME_NM" id="HOME_NM" value="${resultMap.HOME_NM}" readonly></td>
			   </tr>
				<tr>
					<th><span class="star">*</span> 최소주문<br>&nbsp;&nbsp;가능수량</th>
					<td>
						<c:if test="${resultMap.PROD_DIVN_CD eq '02'}" >
							<input type="text" name="MIN_ORD_PSBT_QTY" id="MIN_ORD_PSBT_QTY" value="${resultMap.MIN_ORD_PSBT_QTY}" maxlength="6" style="ime-mode:disabled" onKeypress="onlyNumber();" onChange="doChange(this, '최소주문가능수량', '5', 'N')">
							<input type="hidden" name="MIN_ORD_PSBT_QTY_ORI" id="MIN_ORD_PSBT_QTY_ORI" value="${resultMap.MIN_ORD_PSBT_QTY}"/>
						</c:if>
						<c:if test="${resultMap.PROD_DIVN_CD != '02'}" >
							${resultMap.MIN_ORD_PSBT_QTY}
						</c:if>
					</td>
					<th><span class="star">*</span> 최대주문<br>&nbsp;&nbsp;가능수량 </th>
					
					
					<td>
						<c:if test="${resultMap.PROD_DIVN_CD eq '02'}" >
						   <input type="text" name="MAX_ORD_PSBT_QTY" id="MAX_ORD_PSBT_QTY" value="${resultMap.MAX_ORD_PSBT_QTY}" maxlength="6" style="ime-mode:disabled" onKeypress="onlyNumber();" onChange="doChange(this, '최대주문가능수량', '5', 'N')">
						   <input type="hidden" name="MAX_ORD_PSBT_QTY_ORI" id="MAX_ORD_PSBT_QTY_ORI" value="${resultMap.MAX_ORD_PSBT_QTY}"/>
						</c:if>
						<c:if test="${resultMap.PROD_DIVN_CD != '02'}" >
							${resultMap.MAX_ORD_PSBT_QTY}
						</c:if>
					</td>
					<th>중량/규격</th>
					<td><input type="text" name="PROD_STANDARD_NM" id="PROD_STANDARD_NM" value="${resultMap.PROD_STANDARD_NM}" onKeyPress="keyCode(event,'중량/규격')" onChange="fnChkSpcCharByte(this,'중량/규격',30)"></td>
					<th>생산국가</th>
					<td>
					 <select name="PRODUCT_NATION_CD" id="PRODUCT_NATION_CD" style="width:132px">
						 <option value="">-------선택-------</option>
							 <c:forEach items="${commonCodeList}" var="code" begin="0">
								 <c:if test="${code.majorCd == 'ET030'}" >
									 <option value="${code.minorCd }"<c:if test="${code.minorCd == resultMap.PRODUCT_NATION_CD}" > selected</c:if>>${code.cdNm }</option>
								 </c:if>
							 </c:forEach>
					 </select>
					</td>
				</tr>
				<tr>
					<th>원가</th>
					<td><script>fnMoneyComma('${resultMap.BUY_PRC}', 'out')</script></td>
					<th>매가</th>
					<td><script>fnMoneyComma('${resultMap.SELL_PRC}', 'out')</script></td>
					<th>판매가</th>
					<td><script>fnMoneyComma('${resultMap.CURR_SELL_PRC}', 'out')</script></td>
					<th>임직원할인율<br>(기본5%)</th>
					<td>${resultMap.STAFF_DC_RATE}</td>
				</tr>
				<tr>
					<th>전시여부</th>
					<td>
						<input type="radio" name="DISP_YN" id="DISP_YN" value="Y" ${resultMap.DISP_YN eq 'Y' ? 'checked' : ''} ${resultMap.PROD_DIVN_CD != '02' ? 'disabled' : ''}>Y
						<input type="radio" name="DISP_YN" id="DISP_YN" value="N" ${resultMap.DISP_YN eq 'Y' ? '' : 'checked'} ${resultMap.PROD_DIVN_CD != '02' ? 'disabled' : ''}>N
					</td>
					<th>옵션단품<br>연동여부</th>
					<td><input type="radio" name="VARIATION_YN" id="VARIATION_YN" value="Y" onclick ="radioVariationChk('Y');" ${resultMap.VARIATION_YN eq 'Y' ? 'checked' : ''}>Y <input type="radio" name="VARIATION_YN" id="VARIATION_YN" value="N" onclick="radioVariationChk('N');" ${resultMap.VARIATION_YN eq 'Y' ? '' : 'checked'}>N</td>
					<th><span class="star" title="옵션값 등록시 구분자로  ';' 를 필수로 입력 하셔야 구분 됩니다.!(예 : 빨강, 검정 2개 등록시 => 빨강;검정  입력)"></span>옵션값</th>
					<td><input type="text" name="NFOML_VARIATION_DESC" id="NFOML_VARIATION_DESC" value="${resultMap.NFOML_VARIATION_DESC}" onKeyPress="keyCode(event,'옵션값')" onChange="fnChkSpcCharByte(this,'옵션값',2000)"></td>
					<c:choose>
					<c:when test="${resultMap.PROD_DIVN_CD eq '02' or resultMap.PROD_DIVN_CD eq '03'}">
						<th>표시총량<br>(해외배송)</th>
						<td>${resultMap.DP_TOTAMT}
						<input type="hidden" name="DP_TOTAMT" id="DP_TOTAMT" value="${resultMap.DP_TOTAMT}"/></td>
					</c:when>
					<c:when test="${resultMap.PROD_DIVN_CD eq '01'}">
						<th>표시총량<br>(해외배송)</th>
						<td>
							<input type="text" maxlength="6" name="DP_TOTAMT" id="DP_TOTAMT" value="${resultMap.DP_TOTAMT}" style="ime-mode:disabled" onKeypress="onlyNumber();" onChange="doChange(this, '표시총량', '5', 'N')"></td>
							<input type="hidden" name="DP_TOTAMT_ORI" id="DP_TOTAMT_ORI" value="${resultMap.DP_TOTAMT}"/>
						</td>
					</c:when>
					<c:otherwise>
					</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<th>MD표시총량</th>
					<td><input type="text" maxlength="6" name="DP_TOT_QTY" id="DP_TOT_QTY" value="${resultMap.DP_TOT_QTY}" style="ime-mode:disabled" onChange="doChange(this, 'MD표시총량', '5', 'Y')">
						<input type="hidden" name="DP_TOT_QTY_ORI" id="DP_TOT_QTY_ORI" value="${resultMap.DP_TOT_QTY}"/></td>
					<th>상품표시기준</th>
					<td><input type="text" maxlength="6" name="DP_BASE_QTY" id="DP_BASE_QTY" value="${resultMap.DP_BASE_QTY}" style="ime-mode:disabled" 
								   onKeyPress="javascript:if(document.dataForm.DP_TOT_QTY.value != 0){ onlyNumber(); }else{ event.returnValue = false; }" onChange="doChange(this, '상품표시기준', '5', 'Y')" ${resultMap.DP_TOT_QTY eq '0' ? 'readOnly' : ''}>
						<input type="hidden" name="DP_BASE_QTY_ORI" id="DP_BASE_QTY_ORI" value="${resultMap.DP_BASE_QTY}"/>
					</td>
					<th>표시단위코드</th>
					<td>
					<select name="DP_UNIT_CD" id="DP_UNIT_CD" style="width:132px">
						 <option value="">-------선택-------</option>
							 <c:forEach items="${commonCodeList}" var="code" begin="0">
								 <c:if test="${code.majorCd == 'PRD17'}" >
									 <option value="${code.minorCd }"<c:if test="${code.minorCd == resultMap.DP_UNIT_CD}" > selected</c:if>>${code.cdNm }</option>
								 </c:if>
							 </c:forEach>
					 </select>
					</td>
					<th>단위가격표시</th>
					<td id="TOT_UNIT_SALE">${resultMap.TOT_UNIT_SALE}</td>
				</tr>
				<tr>
					<th>사이즈</th>
					<td>
					   가로:${resultMap.PROD_HRZN_LENG}, 
					   세로:${resultMap.PROD_HIGT}, 
					   높이:${resultMap.PROD_VTCL_LENG}
					</td>
					<th>손큰통큰구분</th>
					<td>${resultMap.BIGHAND_BIGENTP_DIVN_NM}</td>
					<th>신선상품<br>환산수량</th>
					<td>
						<input type="text" maxlength="11" name="FRSH_CONV_QTY" id="FRSH_CONV_QTY" value="${resultMap.FRSH_CONV_QTY}" onKeypress="onlyNumber();" style="ime-mode:disabled" onChange="doChange(this, '신선상품환산수량', '9', 'N')">
						<input type="hidden" name="FRSH_CONV_QTY_ORI" id="FRSH_CONV_QTY_ORI" value="${resultMap.FRSH_CONV_QTY}"/>
					</td>
					<th>상품평추천점수</th>
					<td>${resultMap.RECOMM_RECOM_PNT}</td>
				</tr>
				<tr>
					<th>통합몰판매<br>가능여부</th>
					<td>
						<input type="radio" name="CMBN_MALL_SELL_PSBT_YN" id="CMBN_MALL_SELL_PSBT_YN" value="Y" ${resultMap.CMBN_MALL_SELL_PSBT_YN eq 'Y' ? 'checked' : ''} ${resultMap.PROD_DIVN_CD != '02' ? 'disabled' : ''}>Y
						<input type="radio" name="CMBN_MALL_SELL_PSBT_YN" id="CMBN_MALL_SELL_PSBT_YN" value="N" ${resultMap.CMBN_MALL_SELL_PSBT_YN eq 'Y' ? '' : 'checked'} ${resultMap.PROD_DIVN_CD != '02' ? 'disabled' : ''}>N
					</td>
					<th>명절몰판매<br>가능여부</th>
					<td>
						<input type="radio" name="FEDAY_MALL_SELL_PSBT_YN" id="FEDAY_MALL_SELL_PSBT_YN" value="Y" ${resultMap.FEDAY_MALL_SELL_PSBT_YN eq 'Y' ? 'checked' : ''} ${resultMap.PROD_DIVN_CD != '02' ? 'disabled' : ''}>Y
						<input type="radio" name="FEDAY_MALL_SELL_PSBT_YN" id="FEDAY_MALL_SELL_PSBT_YN" value="N" ${resultMap.FEDAY_MALL_SELL_PSBT_YN eq 'Y' ? '' : 'checked'} ${resultMap.PROD_DIVN_CD != '02' ? 'disabled' : ''}>N
					</td>
					<th>명절몰상품<br>구분</th>
					<td>
					<select name="FEDAY_MALL_PROD_DIVN_CD" id="FEDAY_MALL_PROD_DIVN_CD" style="width:132px" ${resultMap.PROD_DIVN_CD != '02' ? 'disabled' : ''}>
						<option value=""></option>
						<c:forEach items="${commonCodeList}" var="code" begin="0">
							<c:if test="${code.majorCd == 'PRD25'}" >
								<option value="${code.minorCd }"<c:if test="${code.minorCd == resultMap.FEDAY_MALL_PROD_DIVN_CD}" > selected</c:if>>${code.cdNm }</option>
							</c:if>
						</c:forEach>
					</select>
					</td>
					<th>사용여부</th>
					<td>${resultMap.USE_YN}</td>
				</tr>
				<tr>
					<th>단일바구니여부</th>
					<td>
						<input type="radio" name="SNGL_BK_YN" id="SNGL_BK_YN" value="Y" ${resultMap.SNGL_BK_YN eq 'Y' ? 'checked' : ''} disabled>Y
						<input type="radio" name="SNGL_BK_YN" id="SNGL_BK_YN" value="N" ${resultMap.SNGL_BK_YN eq 'Y' ? '' : 'checked'} disabled>N
					</td>
					<%--<th>Youtube 소스</a></th>
					<td>
						 <input type="text" name="PROD_URL" id="PROD_URL" value="${resultMap.PROD_URL}"/><input type="button" onclick="youtubePopup();" value="미리보기"/>
					</td> --%>
					<th>품절여부</th>
					<td>${resultMap.SOUT_YN}</td>
					<th>전상법등록여부</a></th>
					<td>${resultMap.ECOM_YN}</td>
					 <th>MD전상법확인여부</a></th>
					<td>${resultMap.ECOM_YN_APPROVE}</td>
				</tr>
				<tr>
					<th>친환경인증여부</th>
					<td><input type="radio" id="ECO_YN" name="ECO_YN" value="Y" ${resultMap.ECO_YN eq 'Y' ? 'checked' : ''}> Y <input type="radio" id="ECO_YN" name="ECO_YN" value="N" ${resultMap.ECO_YN eq 'N' ? 'checked' : ''}> N</td>
					<th>친환경인증분류명</th>
					<td>
						<select id="ECO_NM" name="ECO_NM">
							<option value="">전체</option>
							<c:forEach items="${commonCodeList}" var="code" begin="0">
								<c:if test="${code.majorCd == 'PRD46'}">
									<option value="${code.minorCd}" <c:if test="${code.minorCd == resultMap.ECO_NM}">selected</c:if>>${code.cdNm}</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<th>차등배송비여부</th>
					<td><input type="radio" id="DLV_GA" name="DLV_GA" value="Y" ${resultMap.DLV_GA eq 'Y' ? 'checked' : '' }> Y  <input type="radio" id="DLV_GA" name="DLV_GA" value="N" ${resultMap.DLV_GA eq 'N' ? 'checked' : '' }> N</td>
					<th>별도설치비유무</th>
					<td><input type="radio" id="INS_CO" name="INS_CO" value="Y" ${resultMap.INS_CO eq 'Y' ? 'checked' : ''}> Y <input type="radio" id="INS_CO" name="INS_CO" value="N" ${resultMap.INS_CO eq 'N' ? 'checked' : ''}> N</td>
				</tr>
				<tr>
					<th>모델명</th>
					<td><input type="text" id="MODEL_NM" name="MODEL_NM" maxlength="50" value="${resultMap.MODEL_NM}"></td>
					<th>별도설치비내용</th>
					<td colspan="5"><input type="text" id="DLV_DT" name="DLV_DT"  style="width:480px"  maxlength="100" value="${resultMap.DLV_DT}"></td>
				</tr>
				<tr>
					<th>MD등록일</th>
					<td>${resultMap.MD_REG_DATE}</td>
					<th>승인여부</th>
					<td>${resultMap.APRV_YN}</td>
					<th>승인일자</th>
					<td>${resultMap.APRV_DATE}</td>
					<th>승인자</th>
					<td>${resultMap.APRV_ID}</td>
				</tr>
				<tr>
					<th>체험형 상품 여부</th>
					<td colspan = "7" style="padding-right: 5px;">
						<div style="display: inline; padding-right: 160px;">
							<input type="radio" id="exprProdYn" name="exprProdYn" value="0" <c:out value="${resultMap.TYPE_CD ne 'EXPERIENCE' ? 'checked' : '' }" />> N
							<input type="radio" id="exprProdYn" name="exprProdYn" value="1" <c:out value="${resultMap.TYPE_CD eq 'EXPERIENCE' ? 'checked' : '' }" />> Y
						</div>
						 판매자 추천평
						<input type="text" id="sellerRecomm" name="sellerRecomm" style="width:540px;" disabled="disabled" onkeyup="checkByte();" value="<c:out value='${resultMap.SELLER_RECOMM}'/>">
						<div style="text-align: right"><span id="messagebyte">0</span> / 100byte </div>
					</td>
				</tr>
				<tr>
					<th>등록자</th>
					<td>${resultMap.REG_ID}</td>
					<th>등록일</th>
					<td>${resultMap.REG_DATE}</td>
					<th>수정자</th> 
					<td>${resultMap.MOD_ID}</td>
					<th>수정일</th>
					<td>${resultMap.MOD_DATE}</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</form>

</body>
</html>