
<%--
- Author(s): projectBOS32
- Created Date: 2016. 04. 14
- Version : 1.0
- Description : 신상품등록  [배송정보  등록페이지 ]
--%>
<%@include file="../common.jsp" %>
<%@ include file="/common/scm/scmCommon.jsp" %>
<%@ include file="./CommonProductFunction.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn"     uri="http://java.sun.com/jsp/jstl/functions"   %>
<%@ taglib prefix="fmt"    uri="http://java.sun.com/jsp/jstl/fmt"         %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Cache-Control" content="no-store"/>
<!-- HTTP 1.0 -->
<meta http-equiv="Pragma" content="no-cache"/>
<!-- Prevents caching at the Proxy Server -->
<meta http-equiv="Expires" content="0"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>임시보관함 배송정보 등록</title>

	<style type="text/css">
		/* TAB */
		.tabs {height:31px; background:#fff;}
		.tabs ul {width:100%; height:31px;}
		.tabs li {float:left; width:130px; height:29px; background:#fff; border:1px solid #ccd0d9; border-radius:2px 2px 0 0; font-size:12px; color:#666; line-height:30px; text-align:center;}
		.tabs li.on {border-bottom:#e7eaef 1px solid; color:#333; font-weight:bold;}
		.tabs li a {display:block; color:#666;}
		.tabs li.on a {color:#333; font-weight:bold;}
	</style>

	<script type="text/javascript" src="/js/epc/edi/product/validation.js"></script>
	<script type="text/javascript" src="../../namoCross/js/namo_scripteditor.js"></script>
	<%@include file="./javascript.jsp" %>
	<script type="text/javascript" >
	var fstChkFlag = false;

	$(document).ready(function() {
		
		var mode = "<c:out value='${param.mode}'/>";
		
		if (mode == "save") {
			alert("배송정보가 등록 되었습니다.");
		}

		//업체 공통 조건 사용
		/* $("#condUseChkFlag").click(function() {
			condUseChkClick($(this));
		}); */

		$("input[name='deliKindCdSel']").click(function() {
			$(":input").each(function(index){
				var type = $(this).attr("type");

				if(type == "text"){
					if($(this).attr("name") != "deliAmt06" ){
						$(this).val("");
					}
				}
			});

			// 20181004 - 제주/도서산간 추가 배송비 설정 오류
			if($(":radio[name='deliKindCdSel']:checked").val() == "") {
			 	$("#deliveryFrom input[type='text']").each(function(index){ 	
				 		$(this).attr("disabled",true);
				 		$(this).val("");
			 	});

			 	$("#psbtChkFlag").attr("checked",false);
			 	$("#psbtChkFlag").attr("disabled",true);
			 	$("#psbtChkYn").val("N");
			} else {
				
				if( ("${newProdDetailInfo.onlineProdTypeCd}" == '03' || "${newProdDetailInfo.onlineProdTypeCd}" == '13')){
					var ndeliKindCd = $("input[name='deliKindCdSel']:checked").val();
					if(ndeliKindCd == '01'){
						alert('설치 상품은 무료배송만 선택 할 수 있습니다.\n※고정배송비 체크 후 0원을 입력하세요.');
						$(":radio[name='deliKindCdSel']").attr("checked", false);
						return;
					}
				}
				
				$("#deliveryFrom input[type='text']").each(function(index){
			 		$(this).attr("disabled",false);
			 	});
				$("#psbtChkFlag").attr("checked",false);
				$("#psbtChkFlag").attr("disabled",false);
				$("#psbtChkYn").val("N");
			}
			// 20181004 - 제주/도서산간 추가 배송비 설정 오류

		});
		
		//제주/도서산간 배송비 체크
		$("#psbtChkFlag").click(function() {
			if($(this).attr("checked") == "checked"){
			 	$("#psbtChkYn").val("Y");
			}else{
				$("#psbtChkYn").val("N");
			}
		});

		//-----탭 클릭 이벤트
		$("#prodTabs li").click(function() {
			var id = this.id;

			var pgmId = $("input[name='pgmId']").val();

			if("<c:out value = '${param.mode}'/>" != "view"){
				$("#mode").val("modify");
			}

			//기본정보 탭
			if (id == "pro01") {
				$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0030OnlineDetail.do'/>");
				$("#hiddenForm").attr("method", "post").submit();
			//이미지 등록 탭
			} else if (id == "pro02") {
				if (pgmId == "") {
					alert("상품 기본정보를 먼저 등록해 주세요.");
					return;
				}

				$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0032.do'/>");
				$("#hiddenForm").attr("method", "post").submit();

			//배송정책 등록 탭
			} else if (id == "pro03") {
				if (pgmId == "") {
					alert("상품 기본정보를 먼저 등록해 주세요.");
					return;
				}

				$("#hiddenForm").attr("action", "<c:url value='/edi/product/NEDMPRO0033.do'/>");
				$("#hiddenForm").attr("method", "post").submit();
			}
		});
	
		var condUseYn = '<c:out value="${newProdDetailInfo.condUseYn}" />';

		$("#condUseYn").val(condUseYn);

		if(condUseYn == "Y"){
			$("#condUseChkFlag").attr("checked", true);
		}else{
			$("#condUseChkFlag").attr("checked", false);
		}
		
		
		//20200626 - 업체 공통조건 및 업체 배송비 관리 수정
		var arrVendorInfo = new Array();
		
		<c:forEach items="${vendorDeliInfoList}" var="vendorList" varStatus="index">
			
			var jsonArr = new Object();
			// 추후 사용해야할지도 몰라서 json 으로 데이터 관리
			jsonArr.APPLY_END_DY = "${vendorList.APPLY_END_DY}";
			jsonArr.APPLY_START_DY = "${vendorList.DELI_DIVN_CD}";
			jsonArr.DELI_DIVN_CD = "${vendorList.DELI_DIVN_CD}";
			jsonArr.DELI_BASE_MAX_AMT = "${vendorList.DELI_BASE_MAX_AMT}";
			jsonArr.DELI_BASE_MIN_AMT = "${vendorList.DELI_BASE_MIN_AMT}";
			arrVendorInfo["${index.count}" -1] = jsonArr;
			
		</c:forEach>
 		if (arrVendorInfo.length > 1) {
 			if(condUseYn == '' || condUseYn == "Y"){
				$("#condUseChkFlag").attr("checked", true);	
			}
 			 else{
				$("#condUseChkFlag").attr("checked", false);
			}
 			
 			$("#condUseChkFlag").click(function() {
 				if("${newProdDetailInfo.onlineProdTypeCd}" == '03' || "${newProdDetailInfo.onlineProdTypeCd}" == '13'){
 					alert("설치 상품은 무료배송만 선택 할 수 있습니다.\n※고정배송비 체크 후 0원을 입력하세요.");
 					$("#condUseChkFlag").attr("checked", false);
 				}
 				condUseChkClick($("#condUseChkFlag"))
 			}); 
		} else {
			
			var deliNm = "";
			
			for (var i=0; i<arrVendorInfo.length; i++)  {
 				if (arrVendorInfo[i].DELI_DIVN_CD == "20") {
 					deliNm ="배송비";
	 			} else if (arrVendorInfo[i].DELI_DIVN_CD == "10") {
	 				deliNm ="반품배송비";
	 			}
	 		}

			if(arrVendorInfo.length ==0){
				deliNm ="배송비,반품배송비";
			}
			
			var resetHtml = '배송정보 <span style="padding-top:5px; padding-left:20px;"><input type="checkbox"  id="condUseChkFlag" name="condUseChkFlag"  style="vertical-align:middle;"/>업체 공통조건 사용 불가 <font color="red">([SCM]시스템관리→업체정보관리→주문배송비관리 '+deliNm+' 미등록)</font></span>';
			$("#condUseChkLi").html(resetHtml);
				
			//업체 공통 조건 사용
		 	$("#condUseChkFlag").click(function() {
				$("#condUseChkFlag").attr("checked", false);
				vendorDeliInfoWarning(deliNm);
			}); 
			
			
		}
	
		condUseChkClick($("#condUseChkFlag")); //최조 메뉴 진입시 클릭 (시작하고나서  공용배송비 체크할거면 이쪽에 로직추가)
		

		if ('<c:out value="${newProdDetailInfo.psbtRegnCd}" />' != "") {
			_eventSetDeliveryDetailValue();
		}

		$(":input").focusout(function(){
			var nameVal = $(this).attr("name");

			if(nameVal.indexOf("minSetQty") != -1 || nameVal.indexOf("maxSetQty") != -1){
				setQtyValChk(nameVal);
			}
		});
	});

	//20180626 - 업체 공통조건 사용시 무료배송 'N' 처리(주문파트 요청건) 및 업체 배송비 관리 수정
	function vendorDeliInfoWarning(msg) {

		alert('                         업체 공통조건 사용 불가\n ([SCM]시스템관리→업체정보관리→주문배송비관리 '+msg+' 미등록)');
	}
	
	
	// 체크 박스 체크시 공용 값이 존재하면  input 박스  disable 처리 또는 오픈 처리 로직
	function condUseChkClick(arg){
		
		if(arg.attr("checked") == "checked"){
		 	$("#condUseYn").val("Y");

		 	$(":radio[name='deliKindCdSel']").attr("checked", false);
		 	$(":radio[name='deliKindCdSel']").attr("disabled",true);

		 	$("select[name='bdlDeliYn']").attr("disabled",true);
		 	$("select[name='bdlDeliYn02']").attr("disabled",true);
		 	$("select[name='bdlDeliYn03']").attr("disabled",true);

		 	$("#deliveryFrom input[type='text']").each(function(index){
		 		$(this).attr("disabled",true);
		 		$(this).val("");
		 	});

		 	$("#psbtChkFlag").attr("checked",false);
		 	$("#psbtChkFlag").attr("disabled",true);
		 	$("#psbtChkYn").val("N");
		 	
		}else{
			$("#condUseYn").val("N");

			$(":radio[name='deliKindCdSel']").attr("disabled",false);

			$("select[name='bdlDeliYn']").attr("disabled",false);
			$("select[name='bdlDeliYn02']").attr("disabled",false);
		 	$("select[name='bdlDeliYn03']").attr("disabled",false);

			$("#deliveryFrom input[type='text']").each(function(index){
		 		$(this).attr("disabled",false);
		 	});

			$("#psbtChkFlag").attr("disabled",false);
		}
	}
	
	function _eventSetDeliveryDetailValue() {
		var psbtRegnCd = '<c:out value="${newProdDetailInfo.psbtRegnCd}" />';
		var bdlDeliYn = '<c:out value="${newProdDetailInfo.bdlDeliYn}" />';
		var deliKindCd = "";
		var count = 0;
		var psbtChkFlag = false;
		var psbtChkCnt = 0;
		var addr1 = "";
		var addr2 = "";

		<c:forEach items="${newOnlineDeliveryList}" var="DeliList" varStatus="index" >

			count = "${index.count}";
			if("${DeliList.DELIVERY_KIND_CD}" == "01"){
				$("#deliCondAmt").val("${DeliList.DELI_COND_AMT}");
			}
			
			// 2020.03.03 수량별 차등, 착불 배송비 삭제			
			if("${DeliList.DELIVERY_KIND_CD}" == "01" || "${DeliList.DELIVERY_KIND_CD}" == "03"){
				deliKindCd = "${DeliList.DELIVERY_KIND_CD}";

				$("select[name='bdlDeliYn"+deliKindCd+"']").val(bdlDeliYn);
			}


			if("${DeliList.DELIVERY_KIND_CD}" != ""){
				$("#deliAmt"+"${DeliList.DELIVERY_KIND_CD}").val("${DeliList.DELIVERY_AMT}");

				if("${DeliList.DELIVERY_KIND_CD}" == "04" || "${DeliList.DELIVERY_KIND_CD}" == "05"){
					if("${DeliList.DELIVERY_AMT}" != ""){
						psbtChkCnt++;
					}
				}
			}
		</c:forEach>

		<c:forEach items="${addrMgrList}" var="addrList" varStatus="idx" >
			if("${addrList.SEQ}" == "1"){
				addr1 = "${addrList.ADDR_SEQ}";
			}

			if("${addrList.SEQ}" == "2"){
				addr2 = "${addrList.ADDR_SEQ}";
			}
		</c:forEach>

		if(count == 2){
			$("select[name='bdlDeliYn']").val(bdlDeliYn);
		}

		if(psbtChkCnt == 2){
			psbtChkFlag = true;
		}

		$("#psbtChkFlag").attr("checked", psbtChkFlag);
		if(psbtChkFlag){
			$("#psbtChkYn").val("Y");
		}else{
			$("#psbtChkYn").val("N");
		}

		//-----selectBox Value Settings....

		$("select[name='psbtRegnCd']").val(psbtRegnCd);

		if(addr1 != ""){
			$("select[name='addr1']").val(addr1);
		}

		if(addr2 != ""){
			$("select[name='addr2']").val(addr2);
		}

		//-----Radio Value Settings....
		//$(":radio[name='deliKindCdSel']:radio[value='" + deliKindCd + "']").attr("checked", true);

		//20180626 - 업체 공통조건 사용시 무료배송 'N' 처리(주문파트 요청건) 및 업체 배송비 관리 수정
		if($("#condUseYn").val() == "N") {
			if(deliKindCd == "01" || deliKindCd =="03"){
				$(":radio[name='deliKindCdSel']:radio[value='" + deliKindCd + "']").attr("checked", true);
			}
			// 20181004 - 제주/도서산간 추가 배송비 설정 오류
			if($(":radio[name='deliKindCdSel']:checked").val() == "") {
			 	$("#deliveryFrom input[type='text']").each(function(index){
			 		$(this).attr("disabled",true);
			 		$(this).val("");
			 	});

			 	$("#psbtChkFlag").attr("checked",false);
			 	$("#psbtChkFlag").attr("disabled",true);
			 	$("#psbtChkYn").val("N");
			} else {
				$("#deliveryFrom input[type='text']").each(function(index){
			 		$(this).attr("disabled",false);
			 	});

				$("#psbtChkFlag").attr("disabled",false);
			}
			// 20181004 - 제주/도서산간 추가 배송비 설정 오류
		}
		
		// 설치상품일 경우
		var deliKindCd = $(":radio[name='deliKindCdSel']:checked").val();
		var delikind03Amt = $("#deliAmt03").val();
		if( ("${newProdDetailInfo.onlineProdTypeCd}" == '03' || "${newProdDetailInfo.onlineProdTypeCd}" == '13') 
				&& !(deliKindCd == '03' && delikind03Amt == '0') ) {
			alert("설치 상품 배송비 정책 변경으로 인해\n현재 적용중인  배송비는 더 이상 사용할 수 없습니다.\n설치 상품은 무료배송만 선택 할 수 있습니다.\n※고정배송비 체크 후 0원을 입력하세요.");	
		}
	}

	/* 저장 */
	function doSave(){
		var deliKindCd = $(":radio[name='deliKindCdSel']:checked").val();

		if($("#condUseChkFlag").attr("checked") != "checked"){
			if(deliKindCd == undefined || deliKindCd == ""){
				alert("배송비 종류를 선택 하세요.\n※ 무료배송 적용을 원할 시, 고정배송비 체크 후 0원을 입력하세요.");
				return;
			}

			if(!validateNewProductInfo()){
				return;
			}
		}
		
		$("#deliKindCd").val(deliKindCd);
		
		if( ("${newProdDetailInfo.onlineProdTypeCd}" == '03' || "${newProdDetailInfo.onlineProdTypeCd}" == '13') && $("#condUseChkFlag").attr("checked") == "checked"){
			alert("설치 상품은 무료배송만 선택 할 수 있습니다.\n※고정배송비 체크 후 0원을 입력하세요.");
			return;
		}

		$("#deliveryFrom").attr("method", "post").submit();
	}

	/* 필수 체크 */
	function validateNewProductInfo(){
		var rtnVal = true;
		var deliKindCd = $(":radio[name='deliKindCdSel']:checked").val();
		var deliAmt06 = $("#deliAmt06").val();


		if($("select[name=psbtRegnCd] option:selected").val() == ""){
			alert("배송가능지역을 선택 하세요.");
			return false;
		}

		if($("select[name=addr1] option:selected").val() == ""){
			alert("출고지주소를 선택 하세요.");
			return false;
		}

		if($("select[name=addr2] option:selected").val() == ""){
			alert("반품/교환 주소를 선택 하세요.");
			return false;
		}
		
		//설치상품일경우 무료배송만 가능(고정배송비 0원)
		if( "${newProdDetailInfo.onlineProdTypeCd}" == '03' || "${newProdDetailInfo.onlineProdTypeCd}" == '13'){
			 if( ($("input[name='deliKindCdSel']:checked").val() == '03' && $("#deliAmt03").val() != '0') || $("#condUseChkFlag").attr("checked") == "checked" ){
				alert("설치 상품은 무료배송만 선택 할 수 있습니다.\n※고정배송비 체크 후 0원을 입력하세요.");
				doubleSubmitFlag = false;
				return;
			 }
		}
		
		
		if(deliKindCd == "01"){ //상품금액별 차등
			if($.trim($("#deliAmt01").val()) == ""){
				alert("상품금액별 차등 배송비를 입력 하세요.");
				rtnVal = false;
			}else{
				if($.trim($("#deliCondAmt").val()) == ""){
					alert("기준 구매금액을 입력 하세요.");
					rtnVal = false;
				}
			}
		}else if(deliKindCd == "03"){ //고정배송비
			if($.trim($("#deliAmt03").val()) == ""){
				alert("고정배송비를 입력 하세요.");
				rtnVal = false;
			}
		}

		if(rtnVal){
			if($("#psbtChkFlag").attr("checked") == "checked"){
				if($.trim($("#deliAmt04").val()) == ""){
					alert("제주 배송비를 입력 하세요.");
					$("#deliAmt04").focus();
					rtnVal = false;
				}else if($.trim($("#deliAmt05").val()) == ""){
					alert("도서산간 배송비를 입력 하세요.");
					$("#deliAmt05").focus();
					rtnVal = false;
				}
			}
			
			//반품배송비
			if( deliKindCd != "" && (deliAmt06 == undefined || deliAmt06 == "") ){
				alert("반품배송비를 입력 하세요.");
				$("#deliAmt06").focus();
				rtnVal = false;
			}
		}
		
		return rtnVal;
	}

	function setDeliAmt02(arg,flag){
		if($.trim($("#"+arg.name).val()) == ""){
			$("#minSetQty_"+flag).val("");
			$("#maxSetQty_"+flag).val("");
		}

		if(flag > 1){
			if($.trim($("#"+arg.name).val()) != "" && $.trim($("#deliAmt02_"+(flag-1)).val()) == ""){
				alert("순차적으로 등록 하세요.");
				$("#"+arg.name).val("");
				return;
			}
		}

		if($.trim($("#"+arg.name).val()) == ""){
			for(var i=flag; i<6; i++){
				$("#deliAmt02_"+i).val("");
				$("#minSetQty_"+i).val("");
				$("#maxSetQty_"+i).val("");
			}
		}
	}

	function setQtyChk(arg,flag){
		if($.trim($("#deliAmt02_"+flag).val()) == ""){
			fstChkFlag = true;
			alert("배송비 부터 입력 하세요.");
			$("#"+arg.name).val("");
			$("#deliAmt02_"+flag).focus();
			return;
		}

		fstChkFlag = false;
	}

	function setQtyValChk(name){
		if(!fstChkFlag){
			var flag = name.substring(name.length-1,name.length);

			if(flag > 1 && name.indexOf("min") != -1){
				if($.trim($("#minSetQty_"+(flag-1)).val()) == "" || $.trim($("#maxSetQty_"+(flag-1)).val()) == ""){
					alert("순차적으로 등록 하세요.");
					$("#deliAmt02_"+flag).val("");
					$("#minSetQty_"+flag).val("");
					$("#maxSetQty_"+flag).val("");
					fstChkFlag = true;
					return;
				}

				if($.trim($("#"+name).val()) != ""){
					if(Number($("#minSetQty_"+flag).val()) != Number($("#maxSetQty_"+(flag-1)).val())+1){
						$("#minSetQty_"+flag).val(Number($("#maxSetQty_"+(flag-1)).val())+1);
						return;
					}

					if(Number($("#minSetQty_"+flag).val()) <= Number($("#maxSetQty_"+(flag-1)).val())){
						alert("이전 최대값 보다 같거나 작습니다.");
						$("#minSetQty_"+flag).val("");
						$("#minSetQty_"+flag).focus();
						fstChkFlag = true;
						return;
					}
				}
			}

			if(name.indexOf("max") != -1){
				if($.trim($("#"+name).val()) != ""){
					if($.trim($("#minSetQty_"+flag).val()) == ""){
						alert("최소수량이 입력 되지 않았습니다.");
						$("#"+name).val("");
						$("#"+name).focus();
						fstChkFlag = true;
						return;
					}else{
						if(Number($("#minSetQty_"+flag).val()) >= Number($("#"+name).val())){
							alert("최대수량이 최소수량보다 같거나 작습니다.");
							$("#"+name).val("");
							$("#"+name).focus();
							fstChkFlag = true;
							return;
						}
					}
				}
			}

			if(name.indexOf("min") != -1){
				if($.trim($("#maxSetQty_"+flag).val()) != ""){
					if(Number($("#minSetQty_"+flag).val()) >= Number($("#maxSetQty_"+flag).val())){
						alert("최대수량이 최소수량보다 같거나 작습니다.");
						$("#maxSetQty_"+flag).val("");
						$("#maxSetQty_"+flag).focus();
						fstChkFlag = true;
						return;
					}
				}
			}

			fstChkFlag = false;
		}
	}
	</script>

</head>

<body>
	<div id="content_wrap">
		<div>
			<div id="wrap_menu">
				<div class="wrap_search">

					<!-- tab 구성---------------------------------------------------------------->
					<div id="prodTabs" class="tabs" style="padding-top:10px;">
						<ul>
							<li id="pro01" style="cursor: pointer;">기본정보</li>
							<li id="pro02" style="cursor: pointer;">이미지</li>
							<li id="pro03" class="on">배송정책</li>
						</ul>
					</div>
					<!-- tab 구성---------------------------------------------------------------->
					<!-- 온라인 이미지 등록 시작------------------------------------------------------------------------------------------------------------------------------------------->
					<div class="bbs_search">

						<ul class="tit">
							<li class="tit" id="condUseChkLi">
								배송정보
								<span style="padding-top:5px; padding-left:20px;"><input type="checkbox"  id="condUseChkFlag" name="condUseChkFlag"  style="vertical-align:middle;"/> 업체 공통조건 사용</span>
							</li>
							<c:if test="${param.mode != 'view'}">
							<li class="btn">
								<a href="javascript:doSave();" class="btn"><span><spring:message code="button.common.save"/></span></a>
							</li>
							</c:if>
						</ul>
					</div>


					<form name="deliveryFrom" id="deliveryFrom"	action="<c:url value='/edi/product/NEDMPRO0033Save.do'/>" method="POST" >
						<input type="hidden" name="pgmId"				    id="pgmId"				value="<c:out value='${newProdDetailInfo.pgmId }'/>" />
						<input type="hidden" name="prodDivnCd" 			id="prodDivnCd"			 	/>
						<input type="hidden" name="onOffDivnCd" 		id="onOffDivnCd"		value="<c:out value='${newProdDetailInfo.onoffDivnCd}'/>" 	/>
						<input type="hidden"	name="entpCd"				    id="entpCd"				value="<c:out value='${newProdDetailInfo.entpCd }'/>"	 />
						<input type="hidden" name="onlinProdTypeCd"		id="onlinProdTypeCd"	value="<c:out value='${newProdDetailInfo.onlineProdTypeCd }'/>"	 />
						<input type="hidden" name="uploadFieldCount" 	id="uploadFieldCount" 		/>
						<input type="hidden" name="deliKindCd"           id="deliKindCd" />
						<input type="hidden" name="condUseYn"     		id="condUseYn"  value="N"/>
						<input type="hidden" name="psbtChkYn"           id="psbtChkYn" />

						<div class="bbs_list" style="margin-top:5px">
							<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
							<colgroup>
								<col style="width:20%" />
								<col style="width:80%" />
							</colgroup>

							<tr>
								<th><span class="star">*</span> 배송가능지역</th>
								<td colspan="2">
									<html:codeTag attr="class=\"required\"" objId="psbtRegnCd" objName="psbtRegnCd" parentCode="SM338" width="150px;" comType="SELECT" formName="form" defName="선택"/>
								</td>
							</tr>
							<tr>
								<th><span class="star">*</span> 출고지 주소</th>
								<td colspan="2">
									<c:set var="zipCdVal" value=""/>
									<select name="addr1" class="required" style="width:500px;">
										<option value="" selected="selected">선택</option>
										<c:forEach items="${vendorAddrlist}" var="Addrlist" varStatus="index" >
											<c:if test="${Addrlist.ZIP_CD != ''}">
												<c:set var="zipCdVal" value="(${Addrlist.ZIP_CD})"/>
											</c:if>
											<c:if test="${Addrlist.ADDR_KIND_CD eq '01' || Addrlist.ADDR_KIND_CD eq '03'}">
												<option value="${Addrlist.ADDR_SEQ}">${Addrlist.ADDR_1_NM} ${Addrlist.ADDR_2_NM} ${zipCdVal}</option>
											</c:if>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th><span class="star">*</span> 반품/교환 주소</th>
								<td colspan="2">
									<c:set var="zipCdVal" value=""/>
									<select name="addr2" class="required" style="width:500px;">
										<option value="" selected="selected">선택</option>
										<c:forEach items="${vendorAddrlist}" var="Addrlist" varStatus="index" >
											<c:if test="${Addrlist.ZIP_CD != ''}">
												<c:set var="zipCdVal" value="(${Addrlist.ZIP_CD})"/>
											</c:if>
											<c:if test="${Addrlist.ADDR_KIND_CD eq '02' || Addrlist.ADDR_KIND_CD eq '03'}">
												<option value="${Addrlist.ADDR_SEQ}">${Addrlist.ADDR_1_NM} ${Addrlist.ADDR_2_NM} ${zipCdVal}</option>
											</c:if>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th>공통 배송비 정보</th>
								<td colspan="2" style="padding-right:5px;">
									<div class="bbs_list" >
									<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
										<colgroup>
											<col width="13%">
											<col width="21%">
											<col width="10%">
											<col width="10%">
											<col width="13%">
											<col width="10%">
											<col width="13%">
											<col width="10%">
										</colgroup>
										
										<tr>
											<th>주문배송비</th>
											<td>
												<c:forEach items="${vendorDeliInfoList}" var="vendorList" varStatus="index">
													<c:if test="${vendorList.DELI_DIVN_CD eq '10'}">
														주문금액
														${vendorList.DELI_BASE_MAX_AMT}
														원 이상 무료, 아니면
														${vendorList.DELIVERY_AMT} 원
													</c:if>
												</c:forEach>
										    </td>
											<th>반품배송비</th>
											<td>
												<c:forEach items="${vendorDeliInfoList}" var="vendorList" varStatus="index">
													<c:if test="${vendorList.DELI_DIVN_CD eq '20'}">
														${vendorList.DELIVERY_AMT} 원
													</c:if>
												</c:forEach>
											</td>	
											<th>제주 추가배송비</th>
											<td>
												<c:if test="${vendorDlvInfo.ADD_DELI_AMT1 != '0'}">
												<fmt:formatNumber value="${fn:trim(vendorDlvInfo.ADD_DELI_AMT1)}" type="number" currencySymbol="" />
												</c:if>
												<c:if test="${vendorDlvInfo.ADD_DELI_AMT1 eq '0'}">
												${vendorDlvInfo.ADD_DELI_AMT1}
												</c:if>
												<c:if test="${fn:trim(vendorDlvInfo.ADD_DELI_AMT1) != ''}">원</c:if>
											</td>
											<th>도서산간<br/>추가배송비</th>
											<td>
												<c:if test="${vendorDlvInfo.ADD_DELI_AMT2 != '0'}">
												<fmt:formatNumber value="${fn:trim(vendorDlvInfo.ADD_DELI_AMT2)}" type="number" currencySymbol="" />
												</c:if>
												<c:if test="${vendorDlvInfo.ADD_DELI_AMT2 eq '0'}">
												${vendorDlvInfo.ADD_DELI_AMT2}
												</c:if>
												<c:if test="${fn:trim(vendorDlvInfo.ADD_DELI_AMT2) != ''}">원</c:if>
											</td>
										</tr>
									</table>
									</div>
								</td>
							</tr>
							
							
							<tr>
								<th><span class="star">*</span> 배송비 설정</th>
								<td colspan="2" style="padding-right:5px;">
									<div class="bbs_list" >
									<table class="bbs_grid3" cellpadding="0" cellspacing="0" border="0">
										<colgroup>					
											<col style="width:20%" />
											<col style="width:21%" />
											<col style="*" />
											<col style="width:12%" />
										</colgroup>

										<tr>
											<th>배송비종류</th>
											<th>배송비</th>
											<th>기준</th>
											<th>묶음배송</th>
										</tr>
									    
										<tr>
											<td>
												<input type="radio" name="deliKindCdSel"  value="01"  />상품금액별 차등
											</td>
											<td>
												<input type="text" id="deliAmt01" name="deliAmt01"	 style="width:115px;" value="" onkeydown='onlyNumber(event)'/> 원
											</td>
											<td>
												구매금액 <input type="text" id="deliCondAmt" name="deliCondAmt"	 style="width:115px;" value="" onkeydown='onlyNumber(event)'/> 원 이상 시 배송비 무료
											</td>
											<td style="text-align:center; padding-right:4px;">
												불가
												<input type="hidden" id="bdlDeliYn01" name="bdlDeliYn01" value="N"/>
											</td>
										</tr>
										
										<tr>
											<td>
												<input type="radio" name="deliKindCdSel" value="03"  />고정배송비
											</td>
											<td>
												<input type="text" id="deliAmt03" name="deliAmt03"	  style="width:115px;" value="" onkeydown='onlyNumber(event)'/> 원
											</td>
											<td>
												수량/주문금액에 상관없이 고정 배송비
												<span><font color="red"> </br> * 무료배송을 원할 시, 고정배송비를 0원으로 입력해주세요.</font></span>
											</td>
											<td style="text-align:center; padding-right:4px;">
											<c:choose>
											<c:when test="${newProdDetailInfo.onlineProdTypeCd eq '03' || newProdDetailInfo.onlineProdTypeCd eq '13'}">
												불가
												<input type="hidden" id="bdlDeliYn03" name="bdlDeliYn03" value="N"/>
											</c:when>
											<c:otherwise>
												<select name="bdlDeliYn03" class="required" style="width:70px;">
													<option value="Y" selected="selected">가능</option>
													<option value="N">불가</option>
												</select>
											</c:otherwise>
											</c:choose>
											</td>
										</tr>				
										<tr>
											<td>
												<input type="checkbox"  id="psbtChkFlag" name="psbtChkFlag" />제주/도서산간<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;추가 배송비 설정
											</td>
											<td colspan="3">
												제주 <input type="text" id="deliAmt04" name="deliAmt04"	  value="" onkeydown='onlyNumber(event)'/> 원&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												도서산간 <input type="text" id="deliAmt05" name="deliAmt05"	  value="" onkeydown='onlyNumber(event)'/> 원
											</td>
										</tr>
										<!-- 20181004 - 제주/도서산간 추가 배송비 설정 오류 -->
										<tr>
											<td colspan="4">
												<span><font color="red">* 일반 배송권역은 무료배송, 제주/도서산간 지역 배송비 부과를 원할 시<br/>1. 고정배송비 체크 후 0원 입력, 2. 제주/도서산간 추가 배송비 설정 선택 후 배송비 입력하세요.</font></span>									
											</td>
										</tr>
										<!-- 20181004 - 제주/도서산간 추가 배송비 설정 오류 -->
									</table>
									</div>
								</td>
							</tr>
							<tr>
								<th><span class="star">*</span> 반품 배송비</th>
								<td>
									<input type="text" id="deliAmt06" name="deliAmt06"	  value="" onkeydown='onlyNumber(event)'/> 원									
								</td>
							</tr>
							</table>
						</div>
					</form>

				</div>
			</div>
		</div>

		<!-- footer -->
		<div id="footer">
			<div id="footbox">
				<div class="msg" id="resultMsg"></div>
				<div class="notice"></div>
				<div class="location">
					<ul>
					<li>홈</li>
					<li>상품</li>
					<li>신규상품관리</li>
					<li>온라인전용 상품등록</li>
					<li class="last">배송정책</li>
				</ul>
				</div>
			</div>
		</div>
		<!-- footer //-->
	</div>

<!-- 탭 이동을 위한 hiddenForm -->
	<form name="hiddenForm"	id="hiddenForm">
		<input type="hidden"	name="vendorTypeCd"		id="vendorTypeCd"	value="<c:out value='${epcLoginVO.vendorTypeCd}'/>" />
		<input type="hidden"	name="pgmId"				    id="pgmId"				value="<c:out value='${newProdDetailInfo.pgmId }'/>"	/>	<!-- 상품이 등록되고 나면 등록된 상품의 pgmId가 설정됨 -->
		<input type="hidden"	name="entpCd"				    id="entpCd"				value="<c:out value='${newProdDetailInfo.entpCd }'/>"	 />	<!-- 상품이 등록되고 나면 등록된 상품의 협력업체코드가 설정됨 -->
		<input type="hidden"	name="mode"					id="mode"				value="<c:out value='${param.mode}'/>"	 />	<!-- view, modify, ''-->
		<input type="hidden"	name="cfmFg"				    id="cfmFg"				value="<c:out value='${param.cfmFg}'/>" />	<!-- 상품확정구분 -->
	</form>

</body>

</html>
