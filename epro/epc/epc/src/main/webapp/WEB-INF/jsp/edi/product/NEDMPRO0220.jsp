
<%--
- Author(s): 
- Created Date: 2011. 10. 20
- Version : 1.0
- Description : 물류바코드등록

--%>
<%@include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>

<script>
	var newFg 		= true;	// 신규:true, 수정:false
	var targetTbl 	= "";	// 대상테이블(신규:newTbl, 수정:oldTbl)
	
	/* 초기실행 */
	$(document).ready(function() {
		$("#footer").css("margin-top", "40px");
		checkedChange('0');
		
		var ingCnt = 0;
		var errCnt = 0;
		
		<c:forEach items="${barcodeList}" var="list" varStatus="index" >
			// 심사중인 건
			<c:if test="${list.LOGI_CFM_FG == '02'}">
				ingCnt++;
			</c:if>
			
			// 체적/중량오류 건
			<c:if test="${list.LOGI_CFM_FG == '03' || list.LOGI_CFM_FG == '06'}">
				errCnt++;
			</c:if>
		</c:forEach>
		
		if (ingCnt > 0 || errCnt > 0) {
			newFg = false;
		}
		
		if (!newFg) {	// 신규추가가 아닐 경우
			targetTbl = "oldTbl";
		} else {		// 신규추가일 경우
			targetTbl = "newTbl";
		}
		
		setButtonLoad();	// 화면 Load 시 버튼 컨트롤
	});
	
	/* 검색 */
	function doSearch() {
		var vendorTypeCd = "<c:out value="${epcLoginVO.vendorTypeCd}" />";
		if (vendorTypeCd == "06") {
			//console.log($("#searchVenCd").val());
			if ($("#searchVenCd").val() == "") {
				alert("<spring:message code='msg.barcode.chkVenCd' />");
				$("#searchVenCd").focus();
				return;
			}
		}
		
		if ($("#searchSrcmkCd").val().trim() == "") {
			alert("<spring:message code='msg.common.fail.srcmkcd'/>");
			$("#searchSrcmkCd").focus();
			return false;
		}
		
		loadingMaskFixPos();
		
		$("#searchForm").attr("action", "<c:url value="/edi/product/NEDMPRO0220Search.do"/>");
		$("#searchForm").submit();
	}
	
	/* 저장 */
	function doSubmit() {
		
		var venCd = $("#venCd").val();
		if (!venCd) {
			//alert("협력업체코드 및 판매(88)/내부 코드를 입력하시고\n상품정보를 먼저 조회하세요!");
			alert("<spring:message code='msg.barcode.chkVenCd' />");
			return;
		}
		
        var targetTbl = "";
		if (!newFg) {	// 신규추가가 아닐 경우
			targetTbl = "oldTbl";
		} else {		// 신규추가일 경우
			targetTbl = "newTbl";
		}
		
		var pgmId			= $("#" + targetTbl + " input[name='pgmId']");					// 프로그램 ID
		var seq				= $("#" + targetTbl + " input[name='seq']");					// 순번
		var logiBcd			= $("#" + targetTbl + " input[name='logiBcd']");				// 물류바코드
		var variant			= $("#" + targetTbl + " input[name='variant']");				// 변형구분
		var logiBoxIpsu		= $("#" + targetTbl + " input[name='logiBoxIpsu']");			// 물류박스입수
		
		var width			= $("#" + targetTbl + " input[name='width']");					// 가로
		var length			= $("#" + targetTbl + " input[name='length']");					// 세로
		var height			= $("#" + targetTbl + " input[name='height']");					// 높이
		var wg				= $("#" + targetTbl + " input[name='wg']");						// 총중량
		var mixProdFg		= $("#" + targetTbl + " input[name='mixProdFg']:checked");		// 혼재여부
		var conveyFg		= $("#" + targetTbl + " input[name='conveyFg']:checked");		// 소터에러 구분
		var sorterFg		= $("#" + targetTbl + " input[name='sorterFg']");				// 소터구분
		
		var innerIpsu		= $("#" + targetTbl + " input[name='innerIpsu']");				// 가로박스수
		var pltLayerQty		= $("#" + targetTbl + " input[name='pltLayerQty']");			// 세로박스수
		var pltHeightQty	= $("#" + targetTbl + " input[name='pltHeightQty']");			// 높이박스수
		
		var crsdkFg			= $("#" + targetTbl + " input[name='crsdkFg']");				// 크로스독구분
		var wUseFg			= $("#" + targetTbl + " input[name='wUseFg']:checked");			// 빅마켓취급구분
		var wInnerIpsu		= $("#" + targetTbl + " input[name='wInnerIpsu']");				// 빅마켓 박스수
		
		var useFg			= $("#" + targetTbl + " input[name='useFg']");					// 사용구분
		var cfgFg			= $("#" + targetTbl + " input[name='cfgFg']");					// 신규(I), 수정(U)
		
		/* console.log("pgmId----->" + pgmId.val());
		console.log("seq----->" + seq.val());
		console.log("logiBcd----->" + logiBcd.val());
		console.log("variant----->" + variant.val());
		console.log("logiBoxIpsu----->" + logiBoxIpsu.val());
		
		console.log("width----->" + width.val());
		console.log("length----->" + length.val());
		console.log("height----->" + height.val());
		console.log("wg----->" + wg.val());
		console.log("mixProdFg----->" + mixProdFg.val());
		console.log("conveyFg----->" + conveyFg.val());
		console.log("sorterFg----->" + sorterFg.val());
		
		console.log("innerIpsu----->" + innerIpsu.val());
		console.log("pltLayerQty----->" + pltLayerQty.val());
		console.log("pltHeightQty----->" + pltHeightQty.val());
		
		console.log("crsdkFg----->" + crsdkFg.val());
		console.log("wUseFg----->" + wUseFg.val());
		console.log("wInnerIpsu----->" + wInnerIpsu.val()); */
		
		
		var venCdState = false;
		
		if (venCd =="899999" || venCd =="899915" || venCd =="000242" ) {
			venCdState= true;
		}
		
		if (logiBcd.val().trim().length < 13) {
			//alert("정상적인 물류바코드를 입력하세요!");
			alert("<spring:message code='msg.barcode.chkBarcode' />");
			logiBcd.focus();
			return;
		}
		
		if (!width.val().trim() || Number(width.val().trim()) <= 0) {
			//alert("박스체적 [가로]를 입력하세요!");
			alert("<spring:message code='msg.barcode.chkWidth' />");
			width.focus();
			return;
		}
		 
		if (!length.val().trim() || Number(length.val().trim()) <=0 ){
			//alert("박스체적 [세로]를 입력하세요!");
			alert("<spring:message code='msg.barcode.chkLength' />");
			length.focus();
			return;
		}
		if (!height.val().trim() || Number(height.val().trim()) <=0){
			//alert("박스체적 [높이]를 입력하세요!");
			alert("<spring:message code='msg.barcode.chkHeight' />");
			height.focus();
			return;
		}
		if (!wg.val().trim() || Number(wg.val().trim()) <=0 ){
			//alert("박스체적 [총중량]을 입력하세요!");
			alert("<spring:message code='msg.barcode.chkWg' />");
			wg.focus();
			return;
		}
		
		if ((wUseFg.val() == '1') && (!wInnerIpsu.val().trim() || Number(wInnerIpsu.val().trim()) <= 0)) {
			//alert("VIC 박스 개수를  입력하세요!");
			alert("<spring:message code='msg.barcode.chkVic' />");
			wInnerIpsu.focus();
			return;	
		}
		
		if (wUseFg.val() == '0') {
			wInnerIpsu.val("0");
		}
		
		// 파레트가하나라도 입력되었으면 가로,세로,높이 모두입력
		if (innerIpsu.val().trim() ||  pltLayerQty.val().trim() || pltHeightQty.val().trim()) {
		
			if (!innerIpsu.val().trim() || Number(innerIpsu.val().trim()) <=0){
				//alert("팔레트값을 입력하실경우 [가로박스수]를 입력하세요!");
				alert("<spring:message code='msg.barcode.chkInnerIpsu' />");
				innerIpsu.focus();
				return;
			}
			
			if (!pltLayerQty.val().trim() || Number(pltLayerQty.val().trim()) <= 0 ){
				//alert("팔레트값을 입력하실경우 [세로박스수]를 입력하세요!");
				alert("<spring:message code='msg.barcode.chkPltLayerQty' />");
				pltLayerQty.focus();
				return;
			}
			
			if (!pltHeightQty.val().trim() || Number(pltHeightQty.val().trim()) <= 0){
				//alert("팔레트값을 입력하실경우 [높이박스수]를 입력하세요!");
				alert("<spring:message code='msg.barcode.chkPltHeightQty' />");
				pltHeightQty.focus();
				return;
			}
		}
		
		// 업체 하드코딩
		if (venCdState) {
			var vol_sum = width.val() * height.val() * length.val();
			if (vol_sum >= 7486500) {
				crsdkFg.val("1");
			}
		}
		
		if (!confirm("<spring:message code='msg.common.confirm.save' />")) {
			return;
		}
		
		var dataInfo = {};
		dataInfo["venCd"]			= venCd;
		dataInfo["srcmkCd"]			= $("#srcmkCd").val();
		dataInfo["prodCd"]			= $("#prodCd").val();
		dataInfo["l1Cd"]			= $("#l1Cd").val();
		
		dataInfo["pgmId"] 			= pgmId.val();
		dataInfo["seq"] 			= seq.val();
		dataInfo["logiBcd"] 		= logiBcd.val();
		dataInfo["variant"] 		= variant.val();
		dataInfo["logiBoxIpsu"] 	= logiBoxIpsu.val();
		
		dataInfo["width"] 			= width.val();
		dataInfo["length"] 			= length.val();
		dataInfo["height"] 			= height.val();
		dataInfo["wg"] 				= wg.val();
		dataInfo["mixProdFg"] 		= mixProdFg.val();
		dataInfo["conveyFg"] 		= conveyFg.val();
		dataInfo["sorterFg"] 		= sorterFg.val();
		
		dataInfo["innerIpsu"] 		= innerIpsu.val();
		dataInfo["pltLayerQty"] 	= pltLayerQty.val();
		dataInfo["pltHeightQty"] 	= pltHeightQty.val();
		
		dataInfo["crsdkFg"]			= crsdkFg.val();
		dataInfo["wUseFg"] 			= wUseFg.val();
		dataInfo["wInnerIpsu"] 		= wInnerIpsu.val();
		
		dataInfo["useFg"] 			= useFg.val();
		dataInfo["cfgFg"] 			= cfgFg.val();
		dataInfo["proxyNm"]			= "MST0800";
		//console.log(dataInfo);
		
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/NEDMPRO0220SaveBarcode.json"/>',
			//url : '/edi/product/NEDMPRO0220SaveBarcode.json',
			data : JSON.stringify(dataInfo),
			success : function(data) {
				if (data.msgCd == "0") {
					alert('<spring:message code="msg.common.success.save" />');
					doSearch();
				}
			}
		});
		
		//loadingMaskFixPos();
		//form.action="<c:url value="/edi/product/NEDMPRO0220SaveBCD.do"/>";
		//form.submit();
	}
	
	/* 화면 Load 시 버튼 컨트롤 */
	function setButtonLoad() {
		var barcodeLen = '<c:out value="${fn:length(barcodeList)}" />';
		
		document.getElementById("BarcodeLayer").style.display 		= "";
		
		// 완료가 아닌 항목이 있는경우 물류바코드 추가 못하게 막음
		var ingCnt = 0;
		var errCnt = 0;
		<c:forEach items="${barcodeList}" var="list" varStatus="index" >
			// 심사중인 건
			<c:if test="${list.LOGI_CFM_FG == '02'}">
				ingCnt++;
			</c:if>
			
			// 체적/중량오류 건
			<c:if test="${list.LOGI_CFM_FG == '03' || list.LOGI_CFM_FG == '06'}">
				errCnt++;
			</c:if>
		</c:forEach>
		
		if (ingCnt > 0) {
			document.getElementById("BarcodeLayer").style.display 		= "none";
			document.getElementById("BarcodeLayerBtnSave").style.display = "none";
			document.getElementById("BarcodeLayerBtnAdd").style.display = "none";
			document.getElementById("BarcodeLayerBtnDel").style.display = "none";
		}
		
		if (errCnt > 0) {
			document.getElementById("BarcodeLayer").style.display 		= "none";
			document.getElementById("BarcodeLayerBtnAdd").style.display = "none";
			document.getElementById("BarcodeLayerBtnDel").style.display = "none";
		}
		
		if (ingCnt <= 0 && errCnt <= 0) {
			document.getElementById("BarcodeLayerBtnAdd").style.display = "none";
			document.getElementById("BarcodeLayerBtnDel").style.display = "";
		}
	}
	
	/* 추가/취소 버튼 클릭 이벤트 */
	function barcodeSet(state) {
		if (state) {	// 추가
			document.getElementById("BarcodeLayer").style.display = "";
			document.getElementById("BarcodeLayerBtnAdd").style.display = "none";
			document.getElementById("BarcodeLayerBtnDel").style.display = "";
			document.getElementById("BarcodeLayerBtnSave").style.display = "";
		} else {		// 취소
			document.getElementById("BarcodeLayer").style.display = "none";
			document.getElementById("BarcodeLayerBtnAdd").style.display = "";
			document.getElementById("BarcodeLayerBtnDel").style.display = "none";
			document.getElementById("BarcodeLayerBtnSave").style.display = "none";
		}
	}
	
	/* 혼재여부 변경 */
	function MIX_NO_READY(name) {
		alert("<spring:message code='msg.product.mixnoready.info'/>");
		$("#" + targetTbl + " input[name='mixProdFg']").eq(0).attr("checked", true);
		//document.getElementsByName(name)[0].checked = true;//
		//document.getElementById(obj.name)[0].checked = true;
	}
	
	/* 숫자여부 체크 */
	function digitcheck(obj) {
		if (!isNumberDu(obj)) {
			obj.value = "";
			alert("<spring:message code='msg.number'/>");
			obj.focus();
			return false;
		}
		
		return true;
	}
	
	/* 소터에러사유 변경 Event */
	function setRadioValue(oObject) {
		$obj = $(oObject);
		var conveyFgDom = $obj.siblings('input[name=conveyFg]').val(oObject.value);
		var $wgObj = $obj.closest('table').find('input[name=wg]'); //총중량    this(oObject)
		
		digit_sorter_check($wgObj);
		
		
		
		/* $obj = $(oObject);
		var conveyFgDom = $obj.siblings('#' + targetTbl + ' input[name=conveyFg]').val(oObject.value);
		
		var $wgObj = $obj.closest('table').find('input[name=wg]'); //총중량    this(oObject)
		digit_sorter_check($wgObj);
		
		//console.log($("#" + targetTbl + " input[name='wg']").val());
		digit_sorter_check($("#" + targetTbl + " input[name='wg']").val());
		digit_sorter_check(oObject); */
	}
	
	function digit_sorter_check(oObject) {
		$oObject = $(oObject);
		
		var $wrapTable  = $oObject.closest('table');
		
		var widthObj    = 	$wrapTable.find('input[name=width]');	//가로  
		var lengthObj 	=   $wrapTable.find('input[name=length]');	//세로 
		var heightObj 	=	$wrapTable.find('input[name=height]');	//높이
		var wgObj 		=	$wrapTable.find('input[name=wg]');	//총중량    this(oObject)
		
		var conveyFgObj =   $wrapTable.find('input[name=conveyFg]').eq(0);	//소터에러사유(없음)
		var sorterFgObj =    $wrapTable.find('input[name=sorterFg]');   //소터구분
		var sorterFgObjNm =  $wrapTable.find('#sorterFgNm');   //소터구분명
		
		var oldSorter = sorterFgObj.value;
		
		sorterFgObjNm.html("");
		
		/* var width			= $("#" + targetTbl + " input[name='width']").val();
		var length			= $("#" + targetTbl + " input[name='length']").val();
		var height			= $("#" + targetTbl + " input[name='height']").val();
		var wg				= $("#" + targetTbl + " input[name='wg']").val();
		var conveyFg		= $("#" + targetTbl + " input[name='conveyFg']:checked").val();
		var sorterFg		= $("#" + targetTbl + " input[name='sorterFg']").val();
		var sorterFgNm		= $("#" + targetTbl + " div[id='sorterFgNm']").text(); */
		
		/* console.log("width----->" + width);
		console.log("length----->" + length);
		console.log("height----->" + height);
		console.log("wg----->" + wg);
		console.log("conveyFg----->" + conveyFg);
		console.log("sorterFg----->" + sorterFg);
		console.log("sorterFgNm----->" + sorterFgNm); */
		
		//$("#" + targetTbl + " div[id='sorterFgNm']").text("");
		//sorterFgObjNm.html("");
		//console.log(oObject.id);
		if(oObject.name == wgObj.name) {	//총중량    this(oObject) 일때만
			if (!widthObj.val()) {
				alert("박스체적 가로를 먼저입력해 주세요.");
				widthObj.focus();
				return;
			}
			
			if (!lengthObj.val()) {
				alert("박스체적 세로를 입력해 주세요.");
				lengthObj.focus();
				return;
			}
			
			if (!heightObj.val()) {
				alert("박스체적 높이를 먼저입력해 주세요.");
				heightObj.focus();
				return;
			}
		}
		
		if(!widthObj.val() || !lengthObj.val() || !heightObj.val() || !wgObj.val()) {
			return;
		}
		
		var width , length , height , wg, convey_fg;
		width	= widthObj.val();
		length	= lengthObj.val();
		height	= heightObj.val();
		wg		= wgObj.val();
		
		console.log(conveyFgObj.is(':checked'));
		
		if (conveyFgObj.is(':checked')) {
			if( width >= 180 && width <= 850) {
				if ((length >= 100 && length <= 600) && (height >=50 && height <=600)) {
					if (wg >= 0.2 && wg <= 30) {
						sorterFgObj.val("1");
						alert("소터구분은 sorter입니다.");
						sorterFgObjNm.html("<font color='2f6084'><b>sorter</b></font>");
					} else {
						sorterFgObj.val("0");
						alert("총중량으로 인하여 소터구분은 non-sorter입니다.");
						sorterFgObjNm.html("<font color='2f6084'><b>non-sorter</b></font>");
					}
				} else {
					sorterFgObj.val("0");
					alert("세로 및 높이 길이로 인하여 소터구분은 non-sorter입니다.");
					sorterFgObjNm.html("<font color='2f6084'><b>non-sorter</b></font>");
				}
			} else {
				sorterFgObj.val("0");
				alert("가로 길이로 인하여 소터구분은 non-sorter입니다.");
				sorterFgObjNm.html("<font color='2f6084'><b>non-sorter</b></font>");
			}
		} else {
			sorterFgObj.val("0");
			alert("소터에러사유로 인하여 소터구분은 non-sorter입니다.");
			sorterFgObjNm.html("<font color='2f6084'><b>non-sorter</b></font>");
		}
	}
	
	/* 팔레트 */
	function digit_pallet(oObject) {
		var total_box ;
		
		$oObject = $(oObject);
		
		var oTb = $oObject.closest('table');
		
		var innerIpsuObj		= oTb.find('input[name=innerIpsu]');	//가로박스수
		var pltLayerQtyObj		= oTb.find('input[name=pltLayerQty]');	//세로박스수
		var pltHeightQtyObj		= oTb.find('input[name=pltHeightQty]');	//높이박스수
		
		var totalBoxCountNmObj	= oTb.find('#totalBoxCountNm');
		var totalBoxCountObj	= oTb.find('input[name=totalBoxCount]');
		
		/* console.log("innerIpsu----->" + innerIpsuObj.val());
		console.log("pltLayerQtyObj----->" + pltLayerQtyObj.val());
		console.log("pltHeightQtyObj----->" + pltHeightQtyObj.val());
		
		console.log("totalBoxCountNmObj----->" + totalBoxCountNmObj.text());
		console.log("totalBoxCountObj----->" + totalBoxCountObj.val()); */
		
		totalBoxCountObj.val('');
		totalBoxCountNmObj.html('');
		
		if (!digitcheck(oObject)) {
			return;
		}
		
		//입력필드가높이일때만 보이기
		if (oObject.name == pltHeightQtyObj.name) {
			if (!innerIpsuObj.val()) {
				alert("가로박스수를 먼저입력해 주세요.");
				innerIpsuObj.focus();
				return;
			}
			
			if (!pltLayerQtyObj.val()) {
				alert("세로박스수를 먼저입력해 주세요.");
				pltLayerQtyObj.focus();
				return;
			}
		}
		
		if (!innerIpsuObj.val() || !pltLayerQtyObj.val() || !pltHeightQtyObj.val()) {
			return;
		}
		
		total_box = innerIpsuObj.val() * pltLayerQtyObj.val() * pltHeightQtyObj.val();
		//alert("총박수스 : " + total_box);
		
		totalBoxCountObj.val(total_box);
		totalBoxCountNmObj.html("<font color='2f6084'><b>"+total_box+"</b></font>");
	}
	
	function checkedChange(obj){
		$(obj).siblings('input[name=wUseFg]').val(obj.value);
		/* if(str == '0'){
		//	$("input[name=wInnerIpsu]").attr("disabled", true); 
		//	$("input[name=wInnerIpsu]").removeClass("required").addClass("inputRead");
		//	$("input[name=wInnerIpsu]").val("");
		//	$("input[name=wUseFg]").val("0");
			
 		}else{
		//	$("input[name=wInnerIpsu]").attr("disabled", false); 
		//	$("input[name=wInnerIpsu]").addClass("required").removeClass("inputRead");
		//	$("input[name=wUseFg]").val("1");
			
			
		} */
	}
	
	function setVendorInto(strVendorId, strVendorNm, strCono) { // 이 펑션은 협력업체 검색창에서 호출하는 펑션임   
		$("#searchVenCd").val(strVendorId);
	}
	
</script>




<style type="text/css">
input { width:30px; }
</style>
</head>

<body>

	<div id="content_wrap" <c:if test="${not empty param.widthSize }">style=width:<c:out value='${param.widthSize }'/></c:if>>
		<div>
			<!--	@ BODY WRAP START 	-->
			<form id="searchForm" name="searchForm" method="post">
			<input type="hidden" id="entpCode" name="entpCode" value="<c:out value="${param.widthSize }" />" /> 
			
			<%-- 공통 마스터 Parameter 값 --%>
			<input type="hidden" id="venCd"  name="venCd" 	 value="<c:out value="${productData.VEN_CD}" />" /> 
			<input type="hidden" id="l1Cd"   name="l1Cd" 	 value="<c:out value="${productData.L1_CD}" />" />
			<input type="hidden" id="prodCd" name="prodCd" 	 value="<c:out value="${productData.PROD_CD}" />" />
			<input type="hidden" id="srcmkCd" name="srcmkCd" value="<c:out value="${productData.SRCMK_CD}" />" />
			
			<div id="wrap_menu">
			
				<!--	@ 검색조건	-->
				<div class="wrap_search">
					<!-- 01 : search -->
					<div class="bbs_search">
						<ul class="tit">
							<li class="tit"><spring:message code="txt.barcode.title" /></li>
							<li class="btn">
								<a href="#" class="btn" onclick="doSearch();"><span><spring:message code="button.common.search" /></span></a>
							</li>
						</ul>
						
						<table class="bbs_search" cellpadding="0" cellspacing="0" border="0">
							<colgroup>
								<col style="width:15%" />
								<col style="width:30%" />
								<col style="width:15%" />
								<col style="*" />
							</colgroup>
							
							<tr>
								<th>* 협력업체</th>
								<td>
									<c:choose>
										<c:when test="${epcLoginVO.vendorTypeCd eq '06'}">
											<c:if test="${not empty searchParam.searchVenCd}">
												<input type="text" name="searchVenCd" id="searchVenCd" value="<c:out value="${searchParam.searchVenCd}" />" style="width:40%;"/>
											</c:if>
											<c:if test="${empty searchParam.searchVenCd}">
												<input type="text" name="searchVenCd" id="searchVenCd" readonly="readonly" value="<c:out value="${epcLoginVO.repVendorId}" />" style="width:40%;"/>
											</c:if>
											<a href="#" class="btn" id="search" onclick="popupVendorList();"><span>선택</span></a>
										</c:when>
										<c:when test="${epcLoginVO.vendorTypeCd != '06'}">
														<c:if test="${not empty searchParam.searchVenCd}">
															<html:codeTag objId="searchVenCd" objName="searchVenCd" width="150px;" selectParam="<c:out value='${searchParam.searchVenCd}'/>" dataType="CP" comType="SELECT" formName="form"  />
														</c:if>
														<c:if test="${ empty searchParam.searchVenCd}">
															<html:codeTag objId="searchVenCd" objName="searchVenCd" width="150px;" selectParam="<c:out value='${epcLoginVO.repVendorId}'/>" dataType="CP" comType="SELECT" formName="form"   />
														</c:if>
										</c:when>
									</c:choose>
								</td>
								<th>* <spring:message code="txt.barcode.srcmkCd" /> </th>
								<td>
									<input type="text" id="searchSrcmkCd" name="searchSrcmkCd" value="<c:out value="${searchParam.searchSrcmkCd}" />" style="width:150px"/></a>
								</td>
							</tr>
						</table>
					</div>
					<!-- 1검색조건 // -->
				</div>
				
				
				<!-- 상품정보 Start -->
				<div class="wrap_con">
				
					<!-- list -->
					<div class="bbs_list">
						<ul class="tit">
							<li class="tit">검색내역</li>
						</ul>
						<table class="bbs_list" cellpadding="0" cellspacing="0" border="0">
							<colgroup>
								<col style="width:100px;"  />
								<col style="width:100px;"  />
								<col style="width:100px;"  />
								<col  />
								<col style="width:100px;"  />
							</colgroup>
							<tr>
								<th>등록일자</th>
								<th>판매(88)코드</th>
								<th>상품코드</th>
								<th>상품명</th>
								<th>입수</th>
							</tr>
							<c:choose>
								<c:when test="${!empty productData }">
									<tr class="r1">
										<td align="center"><c:out value="${productData.REG_DT }" /></td>
										<td align="center"><c:out value="${productData.SRCMK_CD }" /></td>
										<td align="center"><c:out value="${productData.PROD_CD }" /></td>
										<td>&nbsp;<c:out value="${productData.SHORT_NM }" /></td>
										<td align="right">
											<c:set var="ORD_IPSU" value="${fn:escapeXml(productData.ORD_IPSU)}"/>
											<fmt:formatNumber value="${ORD_IPSU}" type="number" currencySymbol=""/>&nbsp;
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<tr class="r1" name="info_row">
										<td align="center" colspan="5"><font color="red">판매(88)/내부 코드를 입력하시고 검색하시기 바랍니다.</font></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</table>
						
						<!-- <div style="width:100%; height:333px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">  -->
						
					</div>
				</div>
				<!-- 상품정보 End -->
				
				
				<!-- 물류 바코드 자료 필드 시작 -->
				<div class="wrap_con">
					<!-- list -->
					<div class="bbs_list">
					<!-- <div id="BlankLayer" style="display:none;">
						<input type="hidden" name="chgFg">
						<input type="hidden" name="pgmId">
						<input type="hidden" name="seq">
						<input type="hidden" name="crsdkFg">
						<input type="hidden" name="logiBcd">
						<input type="hidden" name="logiBoxIpsu">
						<input type="hidden" name="regDt" >
						<input type="hidden" name="useFg">
						<input type="hidden" name="width">
						<input type="hidden" name="length">
						<input type="hidden" name="height">
						<input type="hidden" name="wg">
						<input type="hidden" name="mixProdFg">
						<input type="hidden" name="conveyFg">
						<input type="hidden" name="sorterFg">
						<input type="hidden" name="innerIpsu">
						<input type="hidden" name="pltLayerQty">
						<input type="hidden" name="pltHeightQty">
						<input type="hidden" name="totalBoxCount">
						<input type="hidden" name="wUseFg">
						<input type="hidden" name="wInnerIpsu">
					</div> -->
					
					<ul class="tit" >
							<li class="btn" style="float:right">	
								<div id="BarcodeLayerBtnSave"><a href="javascript:doSubmit();" class="btn" ><span><spring:message code="button.common.save" /></span></a></div>
							</li>	
							<li class="btn" style="float:right">
								<div id="BarcodeLayerBtnAdd" style="display:none;"><a href="javascript:barcodeSet(true);" class="btn" ><span><spring:message code="button.common.add" /></span></a>&nbsp;</div>
								<div id="BarcodeLayerBtnDel" style="display:none;"><a href="javascript:barcodeSet(false);" class="btn" ><span><spring:message code="button.common.cancel" /></span></a>&nbsp;</div>
							</li>
					</ul>
					
					<table cellpadding="0" cellspacing="0" border=0 width=100%>
						<tr>
							<td>
								
								<!-- 추가 Template Start ------------------------->
								<div id="BarcodeLayer" style="display:none;">
									<table id="newTbl" name="newTbl" class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
										<input type="hidden" id="newRow" name="newRow" value="NEW" />
										<input type="hidden" id="pgmId" 	name="pgmId" 		value=""	/>
										<input type="hidden" id="seq" 		name="seq" 			value="000"	/>
										<input type="hidden" id="variant" 	name="variant" 		value="000"	/>
										
										<input type="hidden" id="crsdkFg" 	name="crsdkFg" 		value="0"	/>
										<input type="hidden" id="cfgFg" 	name="cfgFg" 		value="I"	/>
										
										<tr>
											<th colspan="6"><b>&nbsp;물&nbsp;&nbsp; 류&nbsp;&nbsp; 바&nbsp;&nbsp; 코&nbsp;&nbsp; 드&nbsp;&nbsp;  정&nbsp;&nbsp; 보&nbsp;&nbsp;</b></th>
											<th width=100>바코드 상태</th>
											<td width=100 align=center>
												&nbsp;
												<b><font color="blue">등록요망</font></b>
											</td>
										</tr>
										<tr>
											<th align="center"  width=100>물류바코드</th>
											<td align="left" width=100>
												<input type="text" id="logiBcd" name="logiBcd" value="" style="width: 100px;" maxlength="14" onblur="digitcheck(this)" />
											</td>
											<th align="center" width=100>물류박스 입수</td>
											<td align="center" width=100>
												<input type="text" id="logiBoxIpsu" name="logiBoxIpsu" class="inputReadOnly" value="1" style="width: 80px;" readOnly />
											</td>
											<th align="center"  width=80>등록날짜</th>
											<td align="center">
												<input type="text" id="regDt" name="regDt" class="inputReadOnly" value="<c:out value="${productData.REG_DT_NOW}" />" style="width: 60px;" readOnly />
											</td>
											<th align="center">사용여부</th>
											<td align="center">
												&nbsp;사용
												<input type="hidden" name="useFg" value="1">
											</td>
										</tr>
										<tr>
											<th align="center">박스체적 </th>
											<td align="left" style="padding-left:20px" colspan="5">
												<font color="red">
													※ 정확한 숫자를 입력하셔야 납품이 가능합니다. 예)30cm=300mm,1,000g=1kg 
												</font>
												<br>
												가로 : <input type="text" id="width" name="width"  maxlength="4" style="width:40px;" onblur="digit_sorter_check(this)"  value="" /> 
												세로 : <input type="text" id="length" name="length" maxlength="4" style="width:40px;" onblur="digit_sorter_check(this)"  value="" /> 
												높이 : <input type="text" id="height" name="height" maxlength="4" style="width:40px;" onblur="digit_sorter_check(this)"  value="" /> 
												mm<%-- <html:codeTag objId="sizeUnit" objName="sizeUnit" parentCode="PRD42" comType="SELECT" formName="form" selectParam="MM" attr="class=\"required\""/> --%>  
											
												총중량 : <input type="text" id="wg" name="wg"   maxlength="4" style="width:40px;" onblur="digit_sorter_check(this)"  value="" /> 
												KG<%-- <html:codeTag   objId="wgUnit" 	objName="wgUnit"		parentCode="PRD41" 	comType="SELECT" 	formName="form" selectParam="KG" attr="class=\"required\"" /> --%>
												<br>
												<table cellpadding="0" cellspacing="0" border="1" width="90%">
													<tr>
														<th align="center">기준</th>
														<th align="center">가로(mm) </th>
														<th align="center">세로(mm) </th>
														<th align="center">높이(mm) </th>
														<th align="center">중량(kg) </th>
													</tr>
													<tr>
														<th align="center">최소</th>
														<td>180</td>
														<td>100</td>
														<td>50</td>
														<td>0.20</td>
													</tr>
													<tr>
														<th align="center">최대</th>
														<td>850</td>
														<td>600</td>
														<td>600</td>
														<td>30.00</td>
													</tr>
												</table>
												
												<font color="blue">※ 박스 체적이 가로, 세로, 높이, 중량이 범위내에 있고 <br> 소터에러사유가 "없음" 일 경우에만 Sorter 사용이 가능합니다.</font>
											</td>
											<th>혼재여부</th>
											<td align="center">
												<!-- <input type="hidden" id="mixProdFg" name="mixProdFg" value="0"> -->
												<input type="radio" id="mixProdFg" name="mixProdFg" value="0" checked >비혼재<br> 
												<input type="radio" id="mixProdFg" name="mixProdFg" value="1" onclick="MIX_NO_READY('mixProdFg');" >혼재
											</td>
										</tr>
										<tr>
											<th align="center">소터에러사유</th>
											<td style="padding-left:15px" colspan="5">
												<!-- <input type="hidden" id="conveyFg" name="conveyFg" value="0"> -->
												<input type="radio" id="conveyFg" name="conveyFg" onClick="setRadioValue(this)" value="0" checked />없음 &nbsp;
												<input type="radio" id="conveyFg" name="conveyFg" onClick="setRadioValue(this)" value="1" />파손가능상품 &nbsp;
												<input type="radio" id="conveyFg" name="conveyFg" onClick="setRadioValue(this)" value="2" />비닐포장제품 &nbsp;
												<br/>
												<input type="radio" id="conveyFg" name="conveyFg" onClick="setRadioValue(this)" value="3" />식품이력제상품 &nbsp;
												<input type="radio" id="conveyFg" name="conveyFg" onClick="setRadioValue(this)" value="4" />토트박스 &nbsp;
												<input type="radio" id="conveyFg" name="conveyFg" onClick="setRadioValue(this)" value="5" />원예/화훼 &nbsp;
											</td>
											
											<th align="center">소터구분</th>
											<td align="center">
												<input type="hidden" id="sorterFg" name="sorterFg" value="0">
												<div id=sorterFgNm></div>
											</td>
										</tr>
										<tr>
											<th align="center">팔레트</th>
											<td style="padding-left:20px" colspan="5">
												가로박스수 <input type="text" id="innerIpsu" name="innerIpsu" value=""  maxlength="4" onblur="digit_pallet(this)"	style="width:50px;" />&nbsp;&nbsp;&nbsp;
												세로박스수 <input type="text" id="pltLayerQty" name="pltLayerQty" value=""  maxlength="4" onblur="digit_pallet(this)"	style="width:50px;" />&nbsp;&nbsp;&nbsp;
												높이박스수 <input type="text" id="pltHeightQty" name="pltHeightQty" value=""  maxlength="4" onblur="digit_pallet(this)"	style="width:50px;" />
											</td>
											<th align="center">총박스수</th>
											<td align="center">
												<div id=totalBoxCountNm><c:out value="${list.TOTAL_BOX}" /></div>
												<input type ="hidden" id="totalBoxCount" name="totalBoxCount" value="<c:out value="${list.TOTAL_BOX}" />"/>
											</td>
										</tr>
										<tr>
											<th align="center">VIC마켓 취급여부</th>
											<td style="padding-left:20px" >									
												<input type="radio" id="wUseFg" name="wUseFg" value="0" checked/>미취급
												<input type="radio" id="wUseFg" name="wUseFg" value="1" />취급
											<td>
											<th align="center" width=100>VIC 박스 수</td>
											
											<td>
											 <input type="text" id="wInnerIpsu" name="wInnerIpsu" maxlength="4"	style="width:50px;" />&nbsp;&nbsp;&nbsp;
											</td>
										</tr>
									</table>
									<p style="margin-bottom: 10px;" />
								</div>
								<!-- 추가 Template End ------------------------->
								
							</td>
						</tr>
						
						<!-- List Start ------------------------->
						<c:set var="row_cnt"  value="1" />
						<c:if test="${not empty barcodeList}">
							<c:forEach items="${barcodeList}" var="list" varStatus="index" >
								<tr>
									<td>
										<c:choose>
											<c:when test="${list.LOGI_CFM_FG eq '00' or list.LOGI_CFM_FG eq '01' or  list.LOGI_CFM_FG eq '02' or list.LOGI_CFM_FG eq '04' or list.LOGI_CFM_FG eq '05' or list.LOGI_CFM_FG eq '09'}">
												<!-- 심사중/완료 Start ------------------------->
												<%-- 조회 모드
													LOGI_CFM_FG_IDX =='00' or LOGI_CFM_FG_IDX =='02'  : 심사중
					  								LOGI_CFM_FG_IDX =='09'   						  : 완료
												--%>
												<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
													<tr>
														<th colspan="6"><b>&nbsp;물&nbsp;&nbsp; 류&nbsp;&nbsp; 바&nbsp;&nbsp; 코&nbsp;&nbsp; 드&nbsp;&nbsp;  정&nbsp;&nbsp; 보&nbsp;&nbsp;NO.&nbsp;<c:out value="${index.count}"/></b></th>
														<th width=100>바코드 상태</th>
														<td align=center width=100>
															&nbsp;
															<c:choose>
																<c:when test="${list.LOGI_CFM_FG eq '00' or list.LOGI_CFM_FG eq '02'}"><b><font color="blue">심사중</font></b></c:when>
																<c:when test="${list.LOGI_CFM_FG eq '01'}"><b><font color="red">상품/판매코드 비정상</font></b></c:when>
																<c:when test="${list.LOGI_CFM_FG eq '04'}"><b><font color="red">기등록코드</font></b></c:when>
																<c:when test="${list.LOGI_CFM_FG eq '05'}"><b><font color="red">물류바코드 오류</font></b></c:when>
																<c:otherwise><b><font color="2f6084">완료</font></b></c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<th align="center"  width=100>물류바코드</th>
														<td align="left" width=100><c:out value="${list.LOGI_BCD}" /></td>
														<th align="center" width=100>물류박스 입수</td>
														<td align="center" width=100><c:out value="${list.LOGI_BOX_IPSU}" /></td>
														<th align="center"  width=80>등록날짜</th>
														<td align="center"><c:out value="${list.REG_DT}" /></td>
														<th align="center">사용여부</th>
														<td align="center">
															&nbsp;
															<c:choose>
																<c:when test="${list.USE_FG eq '1'}">사용</c:when>
																<c:otherwise><font color="2f6084">사용안함</font></c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr >
														<th align="center">박스체적 </th>
														<td align="left" style="padding-left:20px" colspan="5">
															가로 : <c:out value="${list.WIDTH}" />
															세로 : <c:out value="${list.LENGTH}" />
															높이 : <c:out value="${list.HEIGHT}" />
															단위 : <c:out value="${list.SIZE_UNIT_NAME}" />
															총중량 :<c:out value="${list.WG}" />&nbsp; 단위 :<c:out value="${list.WG_UNIT_NAME}" />
														</td>
														<th>혼재여부</th>
														<td align="center">
															&nbsp;
															<c:choose>
																<c:when test="${list.MIX_PROD_FG eq '0'}">비혼재</c:when>
																<c:otherwise>혼재</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<th align="center">소터에러사유</th>
														<td style="padding-left:15px" colspan="5">
															<input type="radio" disabled="true" <c:if test="${list.CONVEY_FG eq '0'}">checked</c:if> />없음 &nbsp;
															<input type="radio" disabled="true" <c:if test="${list.CONVEY_FG eq '1'}">checked</c:if> />파손가능상품 &nbsp;
															<input type="radio" disabled="true" <c:if test="${list.CONVEY_FG eq '2'}">checked</c:if> />비닐포장제품 &nbsp;
															<input type="radio" disabled="true" <c:if test="${list.CONVEY_FG eq '3'}">checked</c:if> />식품이력제상품 &nbsp;
															<input type="radio" disabled="true" <c:if test="${list.CONVEY_FG eq '4'}">checked</c:if> />토트박스 &nbsp;
															<input type="radio" disabled="true" <c:if test="${list.CONVEY_FG eq '5'}">checked</c:if> />원예/화훼 &nbsp;
														</td>
														<th align="center">소터구분</th>
														<td align="center">
															&nbsp;
															<c:choose>
																<c:when test="${list.SORTER_FG eq '1'}">SORTER</c:when>
																<c:otherwise>non-sorter</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<th align="center">팔레트..</tj>
														<td style="padding-left:20px" colspan="5">
															가로박스수&nbsp;:&nbsp;<c:out value="${list.INNER_IPSU}" />&nbsp;&nbsp;&nbsp;
															세로박스수&nbsp;:&nbsp;<c:out value="${list.PLT_LAYER_QTY}" />&nbsp;&nbsp;&nbsp;
															높이박스수&nbsp;:&nbsp;<c:out value="${list.PLT_HEIGHT_QTY}" />
														</td>
														<th align="center">총박스수</th>
														<td align="center">
															&nbsp;
															<c:out value="${list.TOTAL_BOX}" />
														</td>
													</tr>
													<tr>
														<th align="center">VIC마켓 취급여부</th>
														<td style="padding-left:20px">
															<c:choose>
																<c:when test="${list.W_USE_FG eq '1'}">취급</c:when>
																<c:when test="${list.W_USE_FG eq '0'}">미취급</c:when>
															</c:choose>
														</td>
														<th align="center" width=100>VIC 박스 수</th>
														<td align="center">
															&nbsp;
															<c:out value="${list.W_INNER_IPSU}" />
														</td>
													</tr>
												</table>
												<!-- 심사중/완료 End ------------------------->
											</c:when>
											<c:otherwise>
												<!-- 수정요청일 경우 Start ------------------------->
												
												<!-- <form id="oldFrm" name="oldFrm"> -->
												<table id="oldTbl" name="oldTbl" class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
													<%-- <input type="text" id="pgmId" name="pgmId"   value="<c:out value="${list.PGM_ID}" />">
													<input type="text" id="seq" name="seq"     value="<c:out value="${list.SEQ}" />">
													<input type="text" id="crsdkFg" name="crsdkFg" value="<c:out value="${list.CRSDK_FG}" />">		
													<input type="text" id="chgFg" name="chgFg"   value="U"> --%>
													
													<input type="hidden" id="oldRow" name="oldRow" value="OLD" />
													<input type="hidden" id="pgmId" 	name="pgmId" 		value="<c:out value="${list.PGM_ID}" />"	/>
													<input type="hidden" id="seq" 		name="seq" 			value="<c:out value="${list.SEQ}" />"		/>
													<!-- <input type="hidden" id="variant" 	name="variant" 		value="000"	/> -->
													<!-- <input type="hidden" id="logiBcd" 	name="logiBcd" 		value="000"	/> -->
													
													<input type="hidden" id="crsdkFg" 	name="crsdkFg" 		value="<c:out value="${list.CRSDK_FG}" />"	/>
													<input type="hidden" id="cfgFg" 	name="cfgFg" 		value="U"	/>
													
													
													<tr>
														<th colspan="6">
															<b>&nbsp;물&nbsp;&nbsp; 류&nbsp;&nbsp; 바&nbsp;&nbsp; 코&nbsp;&nbsp; 드&nbsp;&nbsp;  정&nbsp;&nbsp; 보&nbsp;&nbsp;NO.&nbsp;<c:out value="${index.count}"/></b>
															<font style="font-weight: bold; color: red;">
																<c:if test="${list.LOGI_CFM_FG == '01'}">(상품/판매코드 비정상)</c:if>
																<c:if test="${list.LOGI_CFM_FG == '03'}">(체적/중량오류)</c:if>
																<c:if test="${list.LOGI_CFM_FG == '04'}">(기등록코드)</c:if>
																<c:if test="${list.LOGI_CFM_FG == '05'}">(물류바코드 오류)</c:if>
																<c:if test="${list.LOGI_CFM_FG == '06'}">(소터에러)</c:if>
															</font>
														</th>
														<th width=100>바코드 상태</th>
														<td align=center width=100>
															&nbsp;
															<c:if test="${list.LOGI_CFM_FG == '03' || list.LOGI_CFM_FG == '06'}"><b><font color="blue">수정요청</font></b></c:if>
														</td>
													</tr>
													<tr>
														<th align="center"  width=100>*물류바코드</th>
														<td align="left" width=100>
															<input type="text" id="logiBcd" name="logiBcd" value="<c:out value="${list.LOGI_BCD }" />" style="width: 100px;" onblur="digitcheck(this)" maxlength="14" />
														</td>
														<th align="center" width=100>물류박스 입수</td>
														<td align="center"  width=100>
															<input type="text" id="logiBoxIpsu" name="logiBoxIpsu" class="inputReadOnly" readOnly value="<c:out value="${list.LOGI_BOX_IPSU}" />" style="width: 80px;" />
														</td>
														<th align="center" width=80>등록날짜</th>
														<td align="center">
															<input type="text" id="regDt" name="regDt" class="inputReadOnly" readOnly value="<c:out value="${list.REG_DT}" />"  style="width: 60px;" />
														</td>
														<th align="center">사용여부</th>
														<td align="center">
															<c:choose>
																<c:when test="${list.USE_FG eq '1'}">사용</c:when>
																<c:otherwise><font color="2f6084">사용안함</font></c:otherwise>
															</c:choose>
															<input type="hidden" id="useFg" name="useFg" value="<c:out value="${list.USE_FG}" />">
														</td>
													</tr>
													<tr>
														<th align="center">박스체적 </th>
														<td align="left" style="padding-left:20px" colspan="5">
															
															<font color="red">
																※ 정확한 숫자를 입력하셔야 납품이 가능합니다.
																예)30cm=300mm,1,000g=1kg 
															</font>
															<br> 
															가로 : <input type="text" id="width" name="width"   style="width:40px;"  maxlength="4"  value="<c:out value="${list.WIDTH}" />"   onblur="digit_sorter_check(this)" /> 
															세로 : <input type="text" id="length" name="length"  style="width:40px;"  maxlength="4"  value="<c:out value="${list.LENGTH}" />"  onblur="digit_sorter_check(this)" /> 
															높이 : <input type="text" id="height" name="height"  style="width:40px;"  maxlength="4"  value="<c:out value="${list.HEIGHT}" />"  onblur="digit_sorter_check(this)" /> 
															mm<%-- <html:codeTag objId="sizeUnit" objName="sizeUnit" parentCode="PRD42" comType="SELECT" formName="form" selectParam="<c:out value='${list.SIZE_UNIT}'/>" attr="class=\"required\""/> --%>  
															총중량 : <input type="text" id="wg" name="wg"      style="width:40px;"  maxlength="4"  value="<c:out value="${list.WG}" />"      onblur="digit_sorter_check(this)"  />
															KG<%-- <html:codeTag   objId="wgUnit" 	objName="wgUnit"		parentCode="PRD41" 	comType="SELECT" 	formName="form" selectParam="<c:out value='${list.WG_UNIT}'/>" attr="class=\"required\"" /> --%>
														</td>
														<th>혼재여부</th>
														<td align="center">
															<%-- <input type=hidden id="mixProdFg" name="mixProdFg" value="<c:out value="${list.MIX_PROD_FG}" />"> --%>
															<input type="radio" id="mixProdFg" name="mixProdFg" value="0" <c:if test="${ list.MIX_PROD_FG eq '0' }"> checked	</c:if>  />비혼재<br>
															<input type="radio" id="mixProdFg" name="mixProdFg" value="1" onClick="MIX_NO_READY('mixProdFg');"  <c:if test="${ list.MIX_PROD_FG eq '1' }"> checked	</c:if> />혼재
														</td>
													</tr>
													<tr>
														<th align="center">소터에러사유</th>
														<td style="padding-left:15px" colspan="5">
															<%-- <input type="hidden" id="conveyFg" name="conveyFg" value="<c:out value="${ list.CONVEY_FG}" />"> --%>
															<input type="radio"  id="conveyFg"  name="conveyFg" value="0" onClick="setRadioValue(this)" <c:if test="${ list.CONVEY_FG eq '0' }">checked</c:if> />없음 &nbsp;
															<input type="radio"  id="conveyFg"  name="conveyFg" value="1" onClick="setRadioValue(this)" <c:if test="${ list.CONVEY_FG eq '1' }">checked</c:if> />파손가능상품 &nbsp;
															<input type="radio"  id="conveyFg"  name="conveyFg" value="2" onClick="setRadioValue(this)" <c:if test="${ list.CONVEY_FG eq '2' }">checked</c:if> />비닐포장제품 &nbsp;
															<br/>
															<input type="radio"  id="conveyFg"  name="conveyFg" value="3" onClick="setRadioValue(this)" <c:if test="${ list.CONVEY_FG eq '3' }">checked</c:if> />식품이력제상품 &nbsp;
															<input type="radio"  id="conveyFg"  name="conveyFg" value="4" onClick="setRadioValue(this)" <c:if test="${ list.CONVEY_FG eq '4' }">checked</c:if> />파손가능상품 &nbsp;
															<input type="radio"  id="conveyFg"  name="conveyFg" value="5" onClick="setRadioValue(this)" <c:if test="${ list.CONVEY_FG eq '5' }">checked</c:if> />토트박스 &nbsp;
														</td>
														<th align="center">소터구분</th>
														<td align="center">
															<input type="hidden" id="sorterFg" name="sorterFg" value="<c:out value="${list.SORTER_FG}" />">
															<c:choose>
																<c:when test="${list.SORTER_FG eq '1'}">
																	<div id=sorterFgNm><font color="2f6084">sorter</font></div>
																</c:when>
																<c:otherwise>
																	<div id=sorterFgNm><font color="2f6084">non-sorter</font></div>
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
													<tr>
														<th align="center">팔레트...</th> 
														<td style="padding-left:20px" colspan="5">
															가로박스수 <input type="text" id="innerIpsu" name="innerIpsu" 	value="<c:out value="${list.INNER_IPSU}" />" 		onblur="digit_pallet(this)" style="width:50px;" />&nbsp;&nbsp;&nbsp;
															세로박스수 <input type="text" id="pltLayerQty" name="pltLayerQty" 	value="<c:out value="${list.PLT_LAYER_QTY}" />" 	onblur="digit_pallet(this)" style="width:50px;" />&nbsp;&nbsp;&nbsp;
														        높이박스수 <input type="text" id="pltHeightQty" name="pltHeightQty" 	value="<c:out value="${list.PLT_HEIGHT_QTY}" />"  onblur="digit_pallet(this)" style="width:50px;" />
														</td>
														<th align="center">총박스수</th>
														<td align="center">
															<div id=totalBoxCountNm><c:out value="${list.TOTAL_BOX}" /></div>
															<input type ="hidden" id="totalBoxCount" name="totalBoxCount" value="<c:out value="${list.TOTAL_BOX}" />"/>
														</td>
													</tr>
													<tr>
														<th align="center">VIC마켓 취급여부</th>
														<td style="padding-left:20px" >	
															<%-- <input type="hidden" id="wUseFg" name="wUseFg" value="<c:out value="${list.W_USE_FG}" />"> --%>
															<input type="radio" id="wUseFg" name="wUseFg"  value="0"  onclick="javascript:checkedChange(this);" <c:if test="${ list.W_USE_FG eq '0' }"> checked	</c:if> />미취급 &nbsp;
															<input type="radio" id="wUseFg" name="wUseFg"  value="1"  onclick="javascript:checkedChange(this);" <c:if test="${ list.W_USE_FG eq '1' }"> checked	</c:if> />취급 &nbsp;
														<td>
														<!--  
														<input type="hidden" name="conveyFg" value="<c:out value='${ list.CONVEY_FG}' />">
														<input type="radio"  name="conveyFg_<c:out value='${index.count}' />" value="0" onClick="setRadioValue(this)" <c:if test="${ list.CONVEY_FG eq '0' }"> checked	</c:if> />없음 &nbsp;
														<input type="radio"  name="conveyFg_<c:out value='${index.count}' />" value="1" onClick="setRadioValue(this)" <c:if test="${ list.CONVEY_FG eq '1' }"> checked	</c:if> />파손가능상품 &nbsp;
														<input type="radio"  name="conveyFg_<c:out value='${index.count}' />" value="2" onClick="setRadioValue(this)" <c:if test="${ list.CONVEY_FG eq '2' }"> checked	</c:if> />비닐포장제품 &nbsp;
														-->
														<th align="center" width=100>VIC 박스 수</th>
														<td>
										 					<input type="text" id="wInnerIpsu"  name="wInnerIpsu" 	value="<c:out value="${list.W_INNER_IPSU}" />"    maxlength="4" onblur="digit_sorter_check(this)"	style="width:50px;" />&nbsp;&nbsp;&nbsp;
										 				</td>
										 			</tr>
												</table>
												<!-- </form> -->
												<!-- 수정요청일 경우 End ------------------------->
											</c:otherwise>
										</c:choose>
									</td>
								</tr>	
								<tr><td height=1 bgcolor=a3a3a3></td></tr>	
								
								<c:set var="row_cnt" value="${row_cnt+1 }" />
							</c:forEach>
						</c:if>
						
					</table>			
					
					<!-- <div style="width:100%; height:333px;overflow-x:hidden; overflow-y:scroll; table-layout:fixed;white-space:nowrap;">  -->
					
				</div>
			</div>
					
		</div>
		</form>
	</div>


	<!-- footer -->
	<div id="footer">
		<div id="footbox">
			<div class="msg" id="resultMsg">
					<c:choose>
						<c:when test="${!empty productData }">
							<div>
								<spring:message code="msg.common.success.select"/>
							</div>
						</c:when>
						<c:otherwise>
							<div>
								<spring:message code="msg.common.info.nodata"/>
							</div>
						</c:otherwise>
					</c:choose>
			</div>
			<div class="notice"></div>
			<div class="location">
				<ul>
					<li>홈</li>
					<li>상품정보</li>
					<li>신규상품관리</li>
					<li class="last">물류바코드등록</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- footer //-->
</div>

</body>
</html>
