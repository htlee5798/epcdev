<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" >
var uploadErrorMsg =
{
	"type" : "jpg 형식의 이미지 파일만 업로드가 가능합니다.",
	"extension" : "jpg 형식의 이미지 파일만 업로드가 가능합니다.",
	"size" : "500k 미만의 이미지 파일만 업로드 하실수 있습니다.",
	"minsize" : "300k 이상의 이미지 파일만 업로드 하실수 있습니다.",
	"productCopy" : "상품정보가 정상적으로 복사되었습니다."
 };

	var ONOFFLINE_PRODUCT = "0"; // 온오프겸용 상품
	var ONLINE_PRODUCT = "1"; // 온라인전용 상품
	var SOCIAL_PRODUCT = "2"; // 소셜 상품

	var STANDARD_PRODUCT = "1"; // 규격 상품
	var FASHION_PRODUCT = "5"; // 패션상품

	//단순 메세지 표시함수
	function generateMessage(jqueryObject, message) {
		$("<div name=\"error_msg\" style=\"color:red;\">" + message + "</div>").insertBefore(jqueryObject);
	}

	//ajax방식으로 전달된 결과값을 분석하여
	//현재 선택된 협력업체가 거래 중지여부, 거래방식, 과세구분값 적용.
	function vendorCountResult(data) {
		var message = "";
		//$("input[name=productTypeDivnCode]").val();
		var selectedVendor = $("select[name=entpCode]").val();
		var selectedObj = $("select[name=entpCode]");
		var errorNode = selectedObj.prev("div[name=error_msg]").length;
		var j = data.stopTradeVendorCount;
		var tradeType = data.tradeType;
		//jang 추가
		//alert("tradeType:"+tradeType);
		$("#tradeType").val(tradeType);
		//alert($("select[name=productTypeDivnCode]"));
		//$("#productTypeDivnCode").val($("select[name=productTypeDivnCode]").val());
		$("#saveBtn").show();
		var taxDivnCode = data.taxDivnCode;
		var currentChecked = $("input[name=onOffDivnCode]").val();

		var resultCount = parseInt(j);
		if( resultCount == 0 ) {
			message = "거래가 중지된 협력업체 입니다.";
			$("#saveBtn").hide();
			if(errorNode == 0) {
				generateMessage(selectedObj, message);
			}
		} else {
			deleteErrorMessageIfExist(selectedObj);
		}

		//거래방식에 따라 이익율을 직접 입력할수 있음.(온오프 겸용일때만 실행 )
		if( currentChecked == ONOFFLINE_PRODUCT ) {

			deleteErrorMessageIfExist($("input[name=normalProductCost]"));
			deleteErrorMessageIfExist($("input[name=profitRate]"));
			$("input[name=normalProductCost]").val("");
			$("input[name=profitRate]").val("");

			deleteErrorMessageIfExist($("input[name=vicnormalProductCost]"));
			deleteErrorMessageIfExist($("input[name=vicprofitRate]"));
			$("input[name=vicnormalProductCost]").val("");
			$("input[name=vicprofitRate]").val("");

			changeFiledByTradeType(tradeType);
		}

		//과세 구분 코드값 적용
		$("#taxatDivCode").prev("div[name=error_msg]").remove();
		$("#taxatDivCode").val(taxDivnCode);

		//온라인전용, 소셜상품을 선택한 경우,
		//해당 업체의 온라인 대표상품코드, 대표상품명, 88코드, 이익율을 조회한다.
		//결과가 없으면 해당 업체코드로는 온라인전용, 소셜상품을 입력할수 없음.

		//초기 제작시 온오프, 온라인 전용 구분 값이 한페이지에 있었을때 사용된 조건.
		//현재는 온오프겸용, 온라인 전용, 소셜 모두 각각의 페이지를 가지고 있음.
		//if( $("input[name=onOffDivnCode]:checked").length > 0 ) {

			if( currentChecked == ONLINE_PRODUCT || currentChecked == SOCIAL_PRODUCT ) {

				if($("select[name=onlineProductCode]").length > 0){
					makeOnlineRepresentProductCode(selectedVendor);
				}

				if(currentChecked == ONLINE_PRODUCT && message == ""){
					var chkFlag = false;

					if($("select[name=dealProdDivnCode]").length > 0){
						chkFlag = true;

						if($("select[name=dealProdDivnCode] option:selected").val() == "01"){

							if(tradeType == "4"){
								message = "특약2는 등록 불가합니다.";
								generateMessage(selectedObj, message);
								$("#saveBtn").hide();
							}else if(tradeType == "6"){
								message = "위수탁은 등록 불가합니다.";
								generateMessage(selectedObj, message);
								$("#saveBtn").hide();
							}else{
								deleteErrorMessageIfExist(selectedObj);
							}
						}else{
							chkFlag = false;
						}
					}

					if(tradeType != "6" && !chkFlag){
						message = "위수탁만 등록 가능합니다.";
						generateMessage(selectedObj, message);
						$("#saveBtn").hide();
					}else if(tradeType == "6" && !chkFlag){
						deleteErrorMessageIfExist(selectedObj);
					}
				}
			}
		//}

		if(message != ""){
			alert(message);
		}
	}

	//현재 선택된 협력업체가 거래 중지여부, 거래방식, 과세구분값 적용.
	function vendorCountResult1(data) {
		var message = "";
		//$("input[name=productTypeDivnCode]").val();
		var selectedVendor = $("#entpCode").val();
		var selectedObj = $("#entpCode");
		var errorNode = selectedObj.prev("div[name=error_msg]").length;

		var j = data.stopTradeVendorCount;
		var tradeType = data.tradeType;
		//jang 추가
		//alert("tradeType:"+tradeType);
		$("#tradeType").val(tradeType);

		var taxDivnCode = data.taxDivnCode;
		var currentChecked = $("input[name=onOffDivnCode]").val();

		//과세 구분 코드값 적용
		$("#taxatDivCode").val(taxDivnCode);

		var resultCount = parseInt(j);
		if( resultCount == 0 ) {
			message = "거래가 중지된 협력업체 입니다.";
			if(errorNode == 0) {
				generateMessage(selectedObj, message);
			}
		} else {
				deleteErrorMessageIfExist(selectedObj);
		}

		if( currentChecked == ONLINE_PRODUCT || currentChecked == SOCIAL_PRODUCT ) {
			var chkFlag = false;

			if($("select[name=onlineProductCode]").length > 0){
				makeOnlineRepresentProductCode1(selectedVendor);
			}

			if($("select[name=dealProdDivnCode]").length > 0){
				chkFlag = true;

				if($("select[name=dealProdDivnCode] option:selected").val() == "01"){
					if(tradeType == "4"){
						message = "특약2는 등록 불가합니다.";
						generateMessage(selectedObj, message);
					}else if(tradeType == "6"){
						message = "위수탁은 등록 불가합니다.";
						generateMessage(selectedObj, message);
					}else{
						deleteErrorMessageIfExist(selectedObj);
					}
				}else{
					chkFlag = false;
				}
			}

			if(currentChecked == ONLINE_PRODUCT && message == ""){
				if(tradeType != "6"){
					message = "위수탁만 등록 가능합니다.";
					generateMessage(selectedObj, message);
				}else if(tradeType == "6" && !chkFlag){
					deleteErrorMessageIfExist(selectedObj);
				}
			}
		}
	}

	//온라인전용 추가 - 2016.05.19
	//ajax방식으로 전달된 결과값을 분석하여
	//현재 선택된 협력업체가 거래 중지여부, 거래방식, 과세구분값 적용.
	function vendorCountResult4Online(data) {
		var selectedVendor = $("select[name=entpCode]").val();
		var selectedObj = $("select[name=entpCode]");
		var errorNode = selectedObj.prev("div[name=error_msg]").length;
		var j = data.stopTradeVendorCount;
		var tradeType = data.tradeType;

		$("#tradeType").val(tradeType);
		var taxDivnCode = data.taxDivnCode;
		var currentChecked = $("input[name=onOffDivnCode]").val();

		var resultCount = parseInt(j);
		var message = "";

		deleteErrorMessageIfExist(selectedObj);

		if( resultCount == 0 ) {
			if(errorNode == 0) {
				message = "거래가 중지된 협력업체 입니다";
				generateMessage(selectedObj, message);
			}

		} else {
			if (tradeType != '6') {
				message = "위수탁 협력업체로만 상품등록가능합니다";
				generateMessage(selectedObj, message);
			}
		}

		//거래방식에 따라 이익율을 직접 입력할수 있음.(온오프 겸용일때만 실행 )
		if( currentChecked == ONOFFLINE_PRODUCT ) {

			deleteErrorMessageIfExist($("input[name=normalProductCost]"));
			deleteErrorMessageIfExist($("input[name=profitRate]"));
			$("input[name=normalProductCost]").val("");
			$("input[name=profitRate]").val("");

			deleteErrorMessageIfExist($("input[name=vicnormalProductCost]"));
			deleteErrorMessageIfExist($("input[name=vicprofitRate]"));
			$("input[name=vicnormalProductCost]").val("");
			$("input[name=vicprofitRate]").val("");

			changeFiledByTradeType(tradeType);
		}

		//과세 구분 코드값 적용
		$("#taxatDivCode").prev("div[name=error_msg]").remove();
		$("#taxatDivCode").val(taxDivnCode);

		//온라인전용, 소셜상품을 선택한 경우,
		//해당 업체의 온라인 대표상품코드, 대표상품명, 88코드, 이익율을 조회한다.
		//결과가 없으면 해당 업체코드로는 온라인전용, 소셜상품을 입력할수 없음.

		//초기 제작시 온오프, 온라인 전용 구분 값이 한페이지에 있었을때 사용된 조건.
		//현재는 온오프겸용, 온라인 전용, 소셜 모두 각각의 페이지를 가지고 있음.

		if( currentChecked == ONLINE_PRODUCT || currentChecked == SOCIAL_PRODUCT ) {
			makeOnlineRepresentProductCode(selectedVendor);
		}

	}

	//온라인 전용 상품을 입력할 경우, 사용자가 선택한 해당 협력업체코드로
	//선택된 협력업체의 온라인 대표 상품코드(onlineProductCode), 대표상품명(onlineProductName),
	// 88코드(sellCode), 이익률(profitRate)을 조회함.
	function makeOnlineRepresentProductCode1(currentNodeValue) {

		//선택한 협력업체의 온라인 대표 상품 코드 조회
		$.getJSON("<c:out value='${ctx}'/>/edi/product/onlineRepresentProduct.do",{entpCode: currentNodeValue}, function(j){
				deleteErrorMessageIfExist($("select[name=onlineProductCode]"));
			var options = '<option value="">선택</option>';

			//온라인 대표상품 코드가 존재하는 경우,
			if( j.length > 0 ) {
				$("#onlineRepresentProductTD").hide();
				deleteErrorMessageIfExist($("select[name=onlineProductCode]"));

				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + jQuery.trim(j[i].onlineProductCode) + '">' +
						jQuery.trim(j[i].profitRate) +
						'% / '+ jQuery.trim(j[i].onlineProductCode) +
						' / '+ jQuery.trim(j[i].sellCode) +
						' / '+ jQuery.trim(j[i].onlineProductName) +
						' / '+ jQuery.trim(j[i].l4CodeName) +
						' / '+ jQuery.trim(j[i].taxatDivCode)
						 +'</option>';
				}

				$("select[name=onlineProductCode]").show();
				$("select[name=onlineProductCode] option").remove();
				$("select[name=onlineProductCode]").html(options);

			} else {
			//온라인 대표상품 코드가 존재하지 않는 경우
				generateMessage( $("select[name=onlineProductCode]"), "대표상품코드가 존재하지 않습니다.<br>담당 MD에게 확인해보시기 바랍니다.");
				$("select[name=onlineProductCode]").hide();
			}
		});
	}

	//온오프 겸용 상품에서만 실행됨. !!!!!!
	//거래 유형에 따라 이익률, 정상원가 입력필드 상태 변경.
	//거래 유형 2 or 4 : 이익률 입력 , 정상원가 산출
	//거래 유형 2 , 4가 아닌 경우  : 이익률 산출 , 정상원가 입력
	function changeFiledByTradeType(tradeType) {

		//이익률 입력, 정상원가 산출
		if(tradeType == "2" || tradeType =="4") {
			//이익률 입력가능
			$("input[name=profitRate]").attr("readonly", false);
			$("input[name=profitRate]").addClass("required").removeClass("inputRead");
			$("input[name=profitRate]").parent().prev().find("span.star").show();
			//이동빈추가
			$("input[name=vicprofitRate]").attr("readonly", false);
			$("input[name=vicprofitRate]").addClass("required").removeClass("inputRead");
			$("input[name=vicprofitRate]").parent().prev().find("span.star").show();

			//원가는 입력받은 이익률과 매가로 산출
			$("input[name=normalProductCost]").attr("readonly", true);
			$("input[name=normalProductCost]").removeClass("required").addClass("inputRead");
			$("input[name=normalProductCost]").parent().prev().find("span.star").hide();

			//이동빈추가
			$("input[name=vicnormalProductCost]").attr("readonly", true);
			$("input[name=vicnormalProductCost]").removeClass("required").addClass("inputRead");
			$("input[name=vicnormalProductCost]").parent().prev().find("span.star").hide();

		//이익률 산출, 정상원가 입력
		} else {
			//이익률 산출. 읽기 전용 상태
			$("input[name=profitRate]").attr("readonly", true);
			$("input[name=profitRate]").removeClass("required").addClass("inputRead");
			$("input[name=profitRate]").parent().prev().find("span.star").hide();

			//이동빈추가
			$("input[name=vicprofitRate]").attr("readonly", true);
			$("input[name=vicprofitRate]").removeClass("required").addClass("inputRead");
			$("input[name=vicprofitRate]").parent().prev().find("span.star").hide();

			//정상원가 사용자 입력. 매가, 원가, 면과세 구분을 가지고 이익률이 산출됨.
			$("input[name=normalProductCost]").attr("readonly", false);
			$("input[name=normalProductCost]").addClass("required").removeClass("inputRead");
			$("input[name=normalProductCost]").parent().prev().find("span.star").show();

			//이동빈추가
			$("input[name=vicnormalProductCost]").attr("readonly", false);
			$("input[name=vicnormalProductCost]").addClass("required").removeClass("inputRead");
			$("input[name=vicnormalProductCost]").parent().prev().find("span.star").show();
		}
	}

	//거래중지된 업체인지 체크. 실제 결과값은 vendorCountResult 함수에서 처리됨.
	function checkBlackListVendor() {

		var selectedVendor = $("select[name=entpCode]").val();
		/*
		var selectedObj = $("select[name=entpCode]");
		var errorNode = selectedObj.prev("div[name=error_msg]").length;
		var isThisBlackListVendor = false;
		*/
		$.getJSON("<c:out value='${ctx}'/>/edi/product/checkCountVendorStopTrading.do",{vendorCode: selectedVendor}, vendorCountResult);
	}

	//거래중지된 업체인지 체크. 실제 결과값은 vendorCountResult 함수에서 처리됨.
	function checkBlackListVendor1(selectedVendor) {
		$.getJSON("<c:out value='${ctx}'/>/edi/product/checkCountVendorStopTrading.do",{vendorCode: selectedVendor}, vendorCountResult1);
	}

	//거래중지된 업체인지 체크. 실제 결과값은 vendorCountResult4Online 함수에서 처리됨 - 2016.05.19
	function checkBlackListVendor4Online() {

		var selectedVendor = $("select[name=entpCode]").val();
		/*
		var selectedObj = $("select[name=entpCode]");
		var errorNode = selectedObj.prev("div[name=error_msg]").length;
		var isThisBlackListVendor = false;
		*/
		$.getJSON("<c:out value='${ctx}'/>/edi/product/checkCountVendorStopTrading.do",{vendorCode: selectedVendor}, vendorCountResult4Online);
	}

	//온라인 전용 상품을 입력할 경우, 사용자가 선택한 해당 협력업체코드로
	//선택된 협력업체의 온라인 대표 상품코드(onlineProductCode), 대표상품명(onlineProductName),
	// 88코드(sellCode), 이익률(profitRate)을 조회함.
	function makeOnlineRepresentProductCode(currentNodeValue) {

		//온오프 겸용을 선택한경우 아무것도 실행하지 않음.
		//if($("input[name=onOffDivnCode]:checked").val() == ONOFFLINE_PRODUCT )
		//return;

		//선택한 협력업체의 온라인 대표 상품 코드 조회
		$.getJSON("<c:out value='${ctx}'/>/edi/product/onlineRepresentProduct.do",{entpCode: currentNodeValue}, function(j){
				deleteErrorMessageIfExist($("select[name=onlineProductCode]"));
			var options = '<option value="">선택</option>';

			//온라인 대표상품 코드가 존재하는 경우,
			if( j.length > 0 ) {
				$("#onlineRepresentProductTD").hide();
				deleteErrorMessageIfExist($("select[name=onlineProductCode]"));

				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + jQuery.trim(j[i].onlineProductCode) + '">' +
						jQuery.trim(j[i].profitRate) +
						'% / '+ jQuery.trim(j[i].onlineProductCode) +
						' / '+ jQuery.trim(j[i].sellCode) +
						' / '+ jQuery.trim(j[i].onlineProductName) +
						' / '+ jQuery.trim(j[i].l4CodeName) +
						' / '+ jQuery.trim(j[i].taxatDivCode)
						 +'</option>';
				}

				$("select[name=onlineProductCode]").show();
				$("select[name=onlineProductCode] option").remove();
				$("select[name=onlineProductCode]").html(options);

			} else {
			//온라인 대표상품 코드가 존재하지 않는 경우
				generateMessage( $("select[name=onlineProductCode]"), "대표상품코드가 존재하지 않습니다.<br>담당 MD에게 확인해보시기 바랍니다.");
				$("select[name=onlineProductCode]").hide();
			}
		});
	}

	//사이즈 분류를 선택할 경우, 해당 분류의 하위 사이즈 목록을 콤보박스로 생성함. 하하하
	function sizeCategorySetup () {
		$.getJSON("<c:out value='${ctx}'/>/edi/product/sizeByCategory.do",{sizeCategoryCode: $("select[name=sizeCategory]").val()}, function(j){
			var options = '';
			for (var i = 0; i < j.length; i++) {
				if(i == 0) {
						options += '<option value=\'\'>선택</option>';
				}
				options += '<option value="' + j[i].categoryCode + '">' + j[i].categoryName + '</option>';
			}
			$("select.subsize option").remove();
			$("select.subsize").html(options);
		})
	};

	//온오프 규격상품일때만 88코드 사용자 직접입력 가능
	function check88SellCodeCondition(sellCodeObj) {
		var onOffDivnCode = $("input[name=onOffDivnCode]").val();
		var productDivnCode = $("select[name=productDivnCode]").val();

		//온오프 겸용이고 규격 상품인 경우 , 88코드 입력가능
		if( onOffDivnCode == ONOFFLINE_PRODUCT &&
				productDivnCode == STANDARD_PRODUCT ) {
			deleteErrorMessageIfExist(sellCodeObj);
		} else {
			//sellCodeObj.val('');
			sellCodeObj.blur();
			return;
		}
	}

//	//온라인전용일때 상품일때만 88코드 사용자 직접입력 가능
//	function check88SellCodeConditiononline(sellCodeObj) {

//		var onOffDivnCode = $("input[name=onOffDivnCode]").val();
//		var productDivnCode = $("select[name=productDivnCode]").val();

//		 //온오프 겸용이고 규격 상품인 경우 , 88코드 입력가능
//	  // alert("onOffDivnCode:"+onOffDivnCode);
//	  // alert("productDivnCode:"+productDivnCode);

//		if( onOffDivnCode == ONLINE_PRODUCT || onOffDivnCode == SOCIAL_PRODUCT ) {
//		 //	alert("성공");
//			//	 alert(sellCodeObj);
//		deleteErrorMessageIfExist(sellCodeObj);
//	//  alert("성공");
//	  //	sellCodeObj.blur();
//	 //		return;
//		} else {
//	  //	alert("실패");
//		}
//	}

	//입력페이지에서만 실행됨.
	//온오프겸용, 온라인 전용(소셜 포함)을 선택할 경우, 각 case별 기타 필드 성격, 상태 변경
	function setOnOffDivnCode() {
			//var currentChecked = $("input[name=onOffDivnCode]:checked").val();

			//입력 페이지에서는 선택에따라 필드값이 바뀌면서 초기화가 필요함.
			//수정페이지에서는 온오프 구분, 규격/패션 구분값이 고정되 있어서 setOnOffDivnCodeModify()
			//만 단독 실행됨.
			//온라인전용, 소셜상품 관련 필드 상태 초기화.
			$("select[name=onlineProductCode] option").remove();
			$("select[name=onlineProductCode]").html("<option value=''>선택</option>");
			$("#entpCode").val('');
			$("input[name=onlineProductName]").val("");
			$("input[name=sellCode]").val("");
			deleteErrorMessageIfExist($("input[name=sellCode]"));
			deleteErrorMessageIfExist($("div[name=productSize]"));

			//온오프 구분시 실행되는 각 구분 값 별 해당 필드 상태, 값 초기화 함수.
			//수정페이지에서는 아래 함수만 실행되고 여타 필드에 대한 초기화는 하지 않음.
			setOnOffDivnCodeModify();
	}

	//온오프 구분 선택시 관련 필드들 초기화.
	function setOnOffDivnCodeModify() {
		var currentChecked = $("input[name=onOffDivnCode]:checked").val();

		//온라인 전용, 소셜상품일 경우,
		if( currentChecked == ONLINE_PRODUCT ||
			currentChecked == SOCIAL_PRODUCT ) {

			//온오프 겸용 필드에 대해서는 disabled처리.
			$(".onOffField").attr("disabled", true);

			//필수 항목표시 제거
			$(".onOffField").parent().prev().find("span.star").hide();
			$(".onOffField").parent("div").parent().prev().find("span.star").hide();

			//온라인 대표 상품코드, 상품명 필드 활성화(enabled))
			$("input[name=onlineProductName]").attr("disabled", false);
			$("select[name=onlineProductCode]").attr("disabled", false);

			//온라인 대표 상품 코드, 상품명 필드에 필수 항목임을 나타내는 required class값 부여
			$("input[name=onlineProductName]").addClass("required");
			$("input[name=onlineProductName]").parent().prev().find("span.star").show();
			$("select[name=onlineProductCode]").addClass("required");
			$("select[name=onlineProductCode]").parent().prev().find("span.star").show();

			// 온라인 전용 일경우, 88코드 필수 입력. 온라인 대표 상품명 조회시 88코드도 생성됨.
			$("input[name=sellCode]").parent().prev().find("span.star").show();
			$("input[name=sellCode]").addClass("required");

			//블필요한 온오프겸용 항목들의 에러메세지가 있을 경우 삭제
			$(".onOffField").prev("div[name=error_msg]").remove();
			$("input[name=noPublishedBuyPossibleDivnCode]").parent().prev("div[name=error_msg]").remove();
			$("input[name=newProductFirstPublishedDivnCode]").parent().prev("div[name=error_msg]").remove();
			$("input[name=mixYn]").parent().prev("div[name=error_msg]").remove();

			//온라인 대표 상품코드 콤보박스 표시
			$("select[name=onlineProductCode]").show();

			//상품사이즈 선택항목 처리
			$("div[name=productSize] input").removeClass("required").addClass("requiredIf");
			$("div[name=productSize]").parent().prev().find("span.star").hide();
		} else {
			//온오프 겸용일 경우,

			//온오프 겸용 필드 활성화, 필수항목(*) 기호 표시
			$(".onOffField").attr("disabled", false);
			$(".onOffField").parent().prev().find("span.star").show();
			$(".onOffField").parent("div").parent().prev().find("span.star").show();

			//88코드 선택항목으로 변경. 필수항목(*) 기호 제거
			//필수 항목임을 나타내는 required 클래스 값 제거.
			$("input[name=sellCode]").removeClass("required");
			$("input[name=sellCode]").parent().prev().find("span.star").hide();
			deleteErrorMessageIfExist($("input[name=sellCode]"));

			//온라인 대표 상품명, 코드 비활성화.
			$("input[name=onlineProductName]").attr("disabled", true);
			$("input[name=onlineProductName]").parent().prev().find("span.star").hide();
			deleteErrorMessageIfExist($("input[name=onlineProductName]"));
			$("select[name=onlineProductCode]").attr("disabled", true);
			$("select[name=onlineProductCode]").parent().prev().find("span.star").hide();
			deleteErrorMessageIfExist($("select[name=onlineProductCode]"));

			//필수 항목에서 선택항목으로 변경됨.
			$("input[name=onlineProductName]").removeClass("required");
			$("select[name=onlineProductCode]").removeClass("required");

			//상품사이즈 필수항목 처리
			$("div[name=productSize] input").removeClass("requiredIf").addClass("required");
			$("div[name=productSize]").parent().prev().find("span.star").show();
		}
	}
	//공통팝업함수. 브랜드, 메이커 조회함수에서 사용됨.
	function openDetail(openUrl)
	{
		var WindowWidth = 400;
		var WindowHeight = 366;
		var WindowLeft = (screen.width - WindowWidth)/2;
		var WindowTop= (screen.height - WindowHeight)/2;

		NewWin = window.open(openUrl, 'popup', "titlebar=no, resizable=1, width="+WindowWidth+", height="+WindowHeight+", top="+WindowTop+", left="+WindowLeft);
		NewWin.focus();
	}

	//브랜드 선택팝업
	function openBrandPopup() {
		//window.open("<c:out value='${ctx}'/>/edi/product/brand.do");
		openDetail("<c:out value='${ctx}'/>/edi/product/brand.do");
	}

	//메이커 선택팝업
	function openMakerPopup() {
		//window.open("${ctx}/edi/product/maker.do");
		openDetail("<c:out value='${ctx}'/>/edi/product/maker.do");
	}

	//이익율 검증함수. 마이너스나 소수점 허용
	function validateProfitRate() {
		var profitRateValue = $("input[name=profitRate]").val();
		var minusPattern = /^\-?[0-9]+(.[0-9]+)?$/;
		return minusPattern.test(profitRateValue) ? true : false;
	}

	//패션상품 등록시 색상, 사이즈 추가하는 함수. 최대 5개까지 추가
	function addColorSize() {

		var imageRowLength = $("table[name=color_size] tr[name=colorSizeRow]").length;
		var newRow = imageRowLength+1;

		//var newImageField = "front_"+newRow;
		if( imageRowLength == 10 ) {
			alert("색상은 9개까지 추가가 가능합니다.");
			return;
		}

		var imageRow = "<tr id=colorSizeRow_"+newRow+" name=colorSizeRow>"+
							$("table[name=color_size] tr[name=colorSizeRow]:last").html()+
						"</tr>";

						var ttt = imageRow.replace(/\(\d\)/, "("+newRow+")");
		var submitRow = "<tr id=sellCodeRow_"+newRow+" name=sellCodeRow>"+
							$("table[name=color_size] tr[name=sellCodeRow]:last").html()+
						"</tr>";

		$("table[name=color_size]").append(ttt);
		$("table[name=color_size]").append(submitRow);

		$("table[name=color_size] tr[name=colorSizeRow]:last").find("select").val('');
		$("table[name=color_size] tr[name=sellCodeRow]:last").find("input.subSellCode").val('');
	}

	function getNumberMessageWithArgu(argu) {
		return argu+"<spring:message code="msg.common.error.notNum" />".replace("{0}", "");
	}

	function getTextMessageWithArgu(argu) {
		return argu+"<spring:message code="msg.common.error.required" />".replace("{0}", "");
	}

	//색상, 사이즈 행 삭제
	function delColorSize(rowNum){
		if( rowNum > 1) {
			$("#colorSizeRow_"+rowNum).remove();
			$("#sellCodeRow_"+rowNum).remove();

			//$("#pogImage_"+rowNum).remove();
			//$("#pogImageUpload_"+rowNum).remove();
		} else {
			alert("첫번째 행은 삭제할 수는 없습니다.");
		}
	}

	//사용자가 입력한 88코드가 이미 등록된 코드값인지 체크. 등록된 코드값을 입력했다면, 상품등록 안됨.
	function isUniqueSell88Code(userInputSellObj) {
		//var isUniqueCode = true;

		$.getJSON("<c:out value='${ctx}'/>/edi/product/checkUserSellCode.do",{sellCode: userInputSellObj.val()}, function(j){
			if( j > 0 ) {
				showSellCodeValidateMessage(userInputSellObj, 'duplicated');
			} else {
				deleteErrorMessageIfExist(userInputSellObj);
			}
		});
		//return isUniqueCode;
	}

	//88코드 검증에 대한 에러메세지 생성 함수
	function showSellCodeValidateMessage(currentObject, messageType) {
		var targetObject = selectTargetObject(currentObject);


		var errorNode = targetObject.prev("div[name=error_msg]").length;
		var fullErrorMessage = "<spring:message code="msg.invalid.sellCode" />";

		if( messageType == 'duplicated') {
			fullErrorMessage = "<spring:message code="msg.duplicated.sellCode" />";
		}

		if( errorNode == 0 ) {
			 generateMessage(targetObject, fullErrorMessage);
		}
	}

	//88코드 검증하는 첫번째 함수.
	//코드값 자체가 유효한지 검증하고 (isThisValidSell88Code),
	//유효하다면 이미 등록된 코드인지(isUniqueSell88Code) 체크함.
	//등록된 코드값은 다시 등록할수 없음.
	function validateSellCode(jquerySellCodeObj) {

		if( !isThisValidSell88Code(jquerySellCodeObj.get(0)) ) {
		//	alert("123");
			showSellCodeValidateMessage(jquerySellCodeObj, 'invalid');
			//return false;
		} else {
		//	alert("345");
			deleteErrorMessageIfExist(jquerySellCodeObj);
			//return true;
			isUniqueSell88Code(jquerySellCodeObj);
		}
	}

	//팀코드값( $("#teamGroupCode").val() ) 을 가지고
	//해당 팀의 대분류 목록을 조회.
	function selectL1List() {
		$.getJSON("<c:out value='${ctx}'/>/edi/product/selectL1List.do",
			{
				groupCode: $("#teamGroupCode").val()
			}, function(j){
				var options = '';
				for (var i = 0; i < j.length; i++) {
					if(i == 0) {
						options += '<option value=all>전체</option>';
					}
					options += '<option value="' + j[i].teamCode + '">' + j[i].teamName + '</option>';
				}
		
				$("#l1GroupCode option").remove();
				$("#l1GroupCode").html(options);
		});
	}

	//팀( $("#teamGroupCode").val() ), 대분류 값($("#l1GroupCode").val()  )을 가지고
	//선택된 대분류의 하위 소분류 목록 조회
	function selectL4List() {
		$.getJSON("<c:out value='${ctx}'/>/edi/product/selectL4List.do",
			{
				groupCode: $("#teamGroupCode").val(),
				detailCode: $("#l1GroupCode").val()
			}, function(j){
				var options = '';
				for (var i = 0; i < j.length; i++) {
					if(i == 0) {
							options += "<option value=''>전체</option>";
						}
					options += '<option value="' + j[i].categoryCode + '">' + j[i].categoryName + '</option>';
				}
				$("#l4GroupCode option").remove();
				$("#l4GroupCode").html(options);
		})
	}

	//온라인 전시 카테고리 값을 가지고
	//TCA_CATEGORY_MAPPING, TCA_MD_CATEGORY에 하위 목록 조회
	function selectL4ListForOnline(onlineDisplayCategory) {
		$.getJSON("<c:out value='${ctx}'/>/edi/product/selectL4ListForOnline.do",
			{
				detailCode: onlineDisplayCategory
			}, function(j){
				var options = "<option value=''>선택</option>";
				for (var i = 0; i < j.length; i++) {
					options += '<option value="' + j[i].categoryCode + '">' + j[i].categoryName +'</option>';
				}
		
				$("#l4GroupCode option").remove();
				$("#l4GroupCode").html(options);
				if(j.length < 1){
					alert('카테리고에 맵핑된 소분류 데이터가 없습니다. \n담당 MD에게 문의하세요.');
				}
		})
	}

	//상품구분 값(규격:1, 패션:5)에 따라 계절 리스트 재 구성
	//상품구분 값(규격:1, 패션:5)에 따라 계절 리스트 재 구성
	function selectSeasonList(productDivnCode) {
		$.getJSON("<c:out value='${ctx}'/>/edi/product/selectSeasonList.do",
			{
				productDivnCode: $("select[name=productDivnCode]").val()
			}, function(j){
				var options = '';
					for (var i = 0; i < j.length; i++) {
							if(i == 0) {
									//options += '<option value=\'\'>선택</option>';
								}
						options += '<option value="' + j[i].categoryCode + '">' + j[i].categoryName + '</option>';
					}
				$("li[id=seasonAddSelectBox]").show();
				$("#seasonAddSelectBoxtitle").show();
				$("#seasonDivnCode option").remove();
				$("#seasonDivnCode").html(options);
			}
		)
	}

	//규격, 패션 상품구분을 선택했을때 실행되는 함수
	function setupJang() {
		var productTypeDivnCode = $("select[name=productTypeDivnCode]").val();
		var tradeType = $("input[name=tradeType]").val();
		if(tradeType == '1'){
			if(productTypeDivnCode == '1' || productTypeDivnCode == '3'|| productTypeDivnCode == '5'|| productTypeDivnCode == '9'){
				setupJangbodyopen();
			}else{
				setupJangbodyclose();
			}
		}else{
			setupJangbodyclose();
		}
	}

	function setupJangstart(tradeType,productTypeDivnCode) {
		var productTypeDivnCode = productTypeDivnCode;
		var tradeType = tradeType;
		if(tradeType == '1'){
			if(productTypeDivnCode == '1' || productTypeDivnCode == '3'|| productTypeDivnCode == '5'|| productTypeDivnCode == '9'){
				setupJangbodyopen();
			}else{
				setupJangbodyclose();
			}
		}else{
			setupJangbodyclose();
		}
	}

	function setupJangbodyopen() {
		if ($("input[name=newProdPromoFg]:checked").val()=="1")
		{
			$("div.jangoptionnewProdStDy").show(); //신상품 출시일자 없음으로 쇼
			$("div.nojangoptionnewProdStDy").hide();
		}
		else
		{
			$("div.jangoptionnewProdStDy").hide(); //신상품 출시일자 없음으로 쇼
			$("div.nojangoptionnewProdStDy").show();
		}
		$("div.jangoption").show();
		//$("div.nojangoptionnewProdStDy").show(); //신상품 출시일자 없음으로 쇼
		//$("div.jangoptionnewProdStDy").hide(); //신상품 출시일자 닫음으로 쇼
		$("div.nojangoption").hide();
	}

	function setupJangbodyclose() {
		//alert("setupJangbodyclose 닫아라");
		$("div.jangoption").hide();
		//$("div.nojangoptionnewProdStDy").show(); //신상품 출시일자 없음으로 쇼
		//$("div.jangoptionnewProdStDy").hide();   //신상품 출시일자 닫음으로 쇼
		$("div.nojangoption").show();
		//$("input[name=newProdStDy]").val('');
	}

	//규격, 패션 상품구분을 선택했을때 실행되는 함수
	function setupFieldByProductDivnCode() {
		var standardProduct = 1;
		var productDivision = $("select[name=productDivnCode]").val();
		var currentChecked = $("input[name=onOffDivnCode]").val();
		 //규격상품을 선택한 경우,
		if(productDivision == standardProduct) {
			//색상, 사이즈 테이블을 숨기고
			if(currentChecked =='0')
			{
				$("ul[name=colorSizeTitle]").hide();
				$("div[name=colorSizeData]").hide();
			}
			//도난방지 태그 유형값을 필수(required)에서
			//선택(required class값 제거)항목으로 변경.
			if( $("#protectTagTypeCode").hasClass("required") ) {
				$("#protectTagTypeCode").removeClass("required");
				//필수항목표시(*)제거
				$("#protectTagTypeCode").parent().prev().find("span.star").hide();
			}
			//온도구분 항목 필수 항목으로 변경.
			$("#temperatureDivnCode").addClass("required");
			//필수 항목 기호(*)표시
			$("#temperatureDivnCode").parent().prev().find("span.star").show();

		} else {
			//패션상품으로 선택한 경우,

			//(온오프겸용일때만[onOffDivnCode == '0'] 컬러사이즈 사용)색상, 사이즈 테이블 표시
			if(currentChecked =='0')
			{
				$("ul[name=colorSizeTitle]").show();
				$("div[name=colorSizeData]").show();
			}
			//도난 방지 태그 유형 필수 항목으로 변경
			if( !$("#protectTagTypeCode").hasClass("required") ) {
				$("#protectTagTypeCode").addClass("required");
				$("#protectTagTypeCode").parent().prev().find("span.star").show();
			}
			//온도구분 필드 선택항목으로 변경
			$("#temperatureDivnCode").removeClass("required");
			$("#temperatureDivnCode").parent().prev().find("span.star").hide();
		}

		 //productDivision 1규격 2패션
		//alert("currentChecked:"+currentChecked);
		if(productDivision =='5')
		{
			selectSeasonList(productDivision);
		}
		else if( currentChecked=='0' && productDivision =='1' ) // 온오프이면서 규격일때만처리
		{
			$("li[id=seasonAddSelectBox]").hide();
			$("#seasonAddSelectBoxtitle").hide();
			$("#seasonDivnCode option").remove();
		}
		else if( currentChecked=='1' && productDivision =='1' ) // 온전이면서 규격일때만처리
		{
			selectSeasonList(productDivision);
		}
	}

	//온라인 대표 상품 콤보박스 변경한 경우,
	//온라인 대표 상품명, 이익율, 88코드 설정.
	function selectOnlineRepresentProductCode() {
		var selectedOnlineProductCode = $("select[name=onlineProductCode]").val();
		var onlineProductName = '';
		var profitRate = '';
		var sellCode = '';
		if( selectedOnlineProductCode != '') {
			var onlineProductData = $("select[name=onlineProductCode] option[value="+selectedOnlineProductCode+"]").text();
			onlineProductName = onlineProductData.split("/")[3];
			profitRate = onlineProductData.split("/")[0].replace(/%/, '');
			profitRate = jQuery.trim(profitRate);
			sellCode  = jQuery.trim(onlineProductData.split("/")[2]);
			taxatDivCode = jQuery.trim(onlineProductData.split("/")[5]);
		}

		deleteErrorMessageIfExist($("input[name=onlineProductName]"));
		$("input[name=onlineProductName]").val(jQuery.trim(onlineProductName));
		$("input[name=profitRate]").val(profitRate);
		$("input[name=sellCode]").val(sellCode);
		//validateSellCode($("input[name=sellCode]"));
	}

	//온라인 대표 상품명, 이익율, 88코드 설정.
	function selectOnlineRepresentProductCode1() {
		var selectedOnlineProductCode = $("select[name=onlineProductCode]").val();
		var onlineProductName = '';
		var profitRate = '';
		var sellCode = '';
		if( selectedOnlineProductCode != '') {
			var onlineProductData = $("select[name=onlineProductCode] option[value="+selectedOnlineProductCode+"]").text();
			onlineProductName = onlineProductData.split("/")[3];
			profitRate = onlineProductData.split("/")[0].replace(/%/, '');
			profitRate = jQuery.trim(profitRate);
			sellCode  = jQuery.trim(onlineProductData.split("/")[2]);
			taxatDivCode = jQuery.trim(onlineProductData.split("/")[5]);
		}

		deleteErrorMessageIfExist($("input[name=onlineProductName]"));
		$("input[name=onlineProductName]").val(onlineProductName);
		$("input[name=profitRate]").val(profitRate);
		$("input[name=sellCode]").val(sellCode);
		//validateSellCode($("input[name=sellCode]"));
	}

	//온라인 대표 상품명, 이익율, 88코드 설정.
	function selectOnlineRepresentProductCode2() {
		var selectedOnlineProductCode = $("select[name=onlineProductCode]").val();
		var onlineProductName = '';
		var profitRate = '';
		var sellCode = '';
		var taxatDivCode = '';
		if( selectedOnlineProductCode != '') {
			var onlineProductData = $("select[name=onlineProductCode] option[value="+selectedOnlineProductCode+"]").text();
			onlineProductName = onlineProductData.split("/")[3];
			profitRate = onlineProductData.split("/")[0].replace(/%/, '');
			profitRate = jQuery.trim(profitRate);
			sellCode  = jQuery.trim(onlineProductData.split("/")[2]);
			taxatDivCode = jQuery.trim(onlineProductData.split("/")[onlineProductData.split("/").length-1]);

			$("#taxatDivCode").find("option").each(function(){
				if(this.text == taxatDivCode){
					$(this).attr("selected","selected");
				}
			});
			$("#taxatDivCode").attr("disabled",true);
		}else{
			$("#taxatDivCode").val("");
			$("#taxatDivCode").attr("disabled",false);
		}

		deleteErrorMessageIfExist($("input[name=onlineProductName]"));
		$("input[name=onlineProductName]").val(jQuery.trim(onlineProductName));
		$("input[name=profitRate]").val(profitRate);
		$("input[name=sellCode]").val(sellCode);

		//validateSellCode($("input[name=sellCode]"));
	}

	//사이즈 값 선택시 동일한 색상/사이즈 값이 선택됐는지 체크
	function checkSameColorSizePair(currentSizeObj) {
		var currentSelectedSize = currentSizeObj.val();
		var colorValueInRow = findColorValue(currentSizeObj);
		var sizeValueCount = 0;

		$("select.subsize").each(function() {
			if( currentSelectedSize == $(this).val() ) {
				var tmpColorValue = findColorValue($(this));
				if( colorValueInRow == tmpColorValue ) {
					sizeValueCount++;
				}
			}
		});

		if( sizeValueCount > 1 ) {
			if( currentSizeObj.val() != '' ) {
				generateMessage(currentSizeObj, '동일한 색상의 동일한 사이즈는 선택하실수 없습니다.');
			} else {
				deleteErrorMessageIfExist(currentSizeObj);
			}
		} else {
			deleteErrorMessageIfExist(currentSizeObj);
		}

	}

	//패션상품 등록시 각 사이즈별 88코드 값이 중복됐는지 체크
	function checkDupSubSellCode(currentSubSellCode) {
		var sameValueCount = 0;

		$("select.subSellCode").each(function() {
			if( currentSubSellCode.val() == $(this).val() ) {
				sameValueCount++;
			}
		});

		if( sameValueCount > 1 ) {
			if( currentSubSellCode.val() != '') {
				generateMessage(currentSubSellCode, '동일한 88코드는 선택할수 없습니다.');
			} else {
				deleteErrorMessageIfExist(currentSubSellCode);
			}
		} else {
			deleteErrorMessageIfExist(currentSubSellCode);
		}

	}

	//사이즈값이 변경된 경우, 변경된 사이즈값이 있는 행에 색상값 조회.
	function findColorValue(currentSelectedSubSize) {
		return currentSelectedSubSize.parent().parent().find("select[name=colorCode]").val();
	}

	//코리안넷에서 바코드 등록시 사용됨.
	function setSorterFlag() {
		var widthB = parseInt($("input[name=width]").val());
		var lengthB = parseInt($("input[name=length]").val());
		var heightB = parseInt($("input[name=height]").val());
		var wgB	 = parseInt($("input[name=wg]").val());
		var entpCode = $("#entpCode").val();

		var innerIpsu = $("input[name=innerIpsu]").val();
		var pltLayerQty = $("input[name=pltLayerQty]").val();
		var pltHeightQty = $("input[name=pltHeightQty]").val();
		var totalBoxCount = innerIpsu * pltLayerQty * pltHeightQty;

		if($("input[name=conveyFg]:checked").val() == "0")	//소터에러사유없음 구분
		{
			if( widthB >= 225 && widthB <= 850)
			{	if ( lengthB >= 100 && lengthB <= 600 && heightB >=50 && heightB <=500)
				{	if (wgB >= 0.5 && wgB <= 30 )
					$("input[name=sorterFg]").val("1");
				}
			}
		}

		if( entpCode =="899999" || entpCode =="899915" || entpCode =="000242" ) {
			var vol_sum = widthB * lengthB * heightB;
			if(vol_sum >=7486500) $("input[name=crsdkFg]").val("1");
		}

		$("input[name=totalBoxCount]").val(totalBoxCount);
	}

	//text필드 검증...상품사이즈(가로, 세로, 높이)는 온오프에서만 필수 항목
	function validateFormTextField(jqueryTextFieldObj) {
		var currentChecked = $("input[name=onOffDivnCode]").val();
		//상품 사이즈(가로, 세로, 높이) 이외의 필드이거나
		//상품 사이즈 필드더라도 온오프겸용상품인경우 필수항목으로 유효성체크 실행.
		if(!jqueryTextFieldObj.hasClass("size") ||
				currentChecked == ONOFFLINE_PRODUCT) {
			validateTextField(jqueryTextFieldObj);

		//상품 사이즈(가로, 세로, 높이))를 검증하는 경우
		} else {

			if(jqueryTextFieldObj.val() != '') {
				validateTextField(jqueryTextFieldObj);
			}
		}
	}

	//유통일 관리 여부
	function setDayForWarehouse() {
		var flowDateValue = $("#flowDateManageCode").val();
		$("input.flowDate").val('');

		if( flowDateValue == '1' ||
				flowDateValue == '2' ) {
			$("div.flowDate").show();
			$("div.noFlowDate").hide();
		} else {
			$("div.flowDate").hide();
			$("div.noFlowDate").show();
		}
	}

	// 패션상품 컬러사이즈 등록여부 체크
	function checkColorSize(){
		var frm = document.forms[0];

		if(frm.productDivnCode.value != '5') return true;

		if(!frm.sizeCategory.value)
		{
			alert("상품속성이 패션상품일경우 [Size선택]을 설정 하신 후\n해당 Color별 사이즈속성을 선택하세요!");
			frm.sizeCategory.focus();
			return false;
		}

		var imageRowLength = $("table[name=color_size] tr[name=colorSizeRow]").length;
		if(imageRowLength > 1)
		{
			for(var i=1; imageRowLength > i; i++)
			{
				if(!frm.size1[i].value && !frm.size2[i].value && !frm.size3[i].value && !frm.size4[i].value && !frm.size5[i].value)
				{
					alert("확장하신 컬러별 사이즈를 하나이상 선택하세요!");
					if(!frm.colorCode[i].value) frm.colorCode[i].focus();
					else frm.size1[i].focus();

					return false;
				}
			}
		}
		else
		{
			if(!frm.size1.value && !frm.size2.value && !frm.size3.value && !frm.size4.value && !frm.size5.value)
			{
				alert("컬러별 사이즈를 하나이상 선택하세요!");

				if(!frm.colorCode.value) frm.colorCode.focus();
				else frm.size1.focus();

				return false;
			}
		}
		return true;
	}

	//대분류 코드를 받았을때 전자 상거래 템플릿 셋팅
	function setupLcdDivnCodeProdAdd(lcd,flag) {

		if(lcd == null || lcd == ''){
			$("ul[name=productAddTemplateTitle]").hide();
			$("div[name=productAddTemplateData]").hide();
		}else{
			$("ul[name=productAddTemplateTitle]").show();
			$("div[name=productAddTemplateData]").show();
		//	alert("132");
			selectProdTemplateList(lcd,flag);
		}
	}

	//flag 1값은 온오프 등록, 2값은 온라인 등록, 전자상거래 등록
	function selectProdTemplateList(lcd,flag) {
	//	alert("selectProdTemplateList");
		$.getJSON("<c:out value='${ctx}'/>/edi/product/selectProdAddTemplateList.do",
		{
			lCode: lcd,
			flag: flag
		}, function(j){
			var tempList = '';	 //리스트 html
			var tempSelect = '<option value=\'\'>선택</option>'; //1:n 일 경우 select
			var tempTitle = '';	//1:n 인지 비교하기 위한체크값
			var tempTitleCount = 0;  //1:n 갯수

			//기존행 삭제
			var rowLength = $("table[name=data_List] tr[name=titleProdAdd]").length;
			for(var i=0;0<rowLength-i;i++){
				$("#titleProdAdd_"+(rowLength-i-1)).remove();
			}

			//html 형태로 만들기
			for (var i = 0; i < j.length; i++) {
				if(tempTitle != j[i].teamCode){
					tempTitle = j[i].teamCode;
					tempSelect += '<option name="groupCode_'+i+'" value="'+j[i].teamCode+'" >'+j[i].teamName+'</option>';
					$("#prodAddMasterCd").val(j[i].teamCode);
					tempTitleCount++;
				}
				tempList += prodAddTempHtml(i,j[i].orgCode,j[i].orgName);
			}

			$("li[id=productAddSelectBox]").show();
			$("#productAddSelectTitle option").remove();
			$("#productAddSelectTitle").html(tempSelect);

			/*
			if(tempTitleCount <= 1){
				$("table[name=data_List]").append(tempList);
			}
			*/
			//$("#prodAddSelectCount").val(tempTitleCount);
		})
	}

	//대분류 코드를 받았을때 KC인증마크 템플릿 셋팅
	function setupLcdDivnCodeCertAdd(lcd,flag) {

		if(lcd == null || lcd == ''){
			$("li[id=productCertDtlSelectBox]").hide();
			$("ul[name=productCertTemplateTitle]").hide();
			$("div[name=productCertTemplateData]").hide();
		}else{
			$("ul[name=productCertTemplateTitle]").show();
			$("div[name=productCertTemplateData]").show();
		//	alert("132");
			selectCertTemplateList(lcd,flag);
		}
	}

	//flag 1값은 온오프 등록, 2값은 온라인 등록, 전자상거래 등록
	function selectCertTemplateList(lcd,flag) {
	//	alert("selectProdTemplateList");
		$.getJSON("<c:out value='${ctx}'/>/edi/product/selectProdCertTemplateList.do",
		{
			lCode: lcd,
			flag: flag
		}, function(j){
			var tempList = '';	 //리스트 html
			var tempSelect = '<option value=\'\'>선택</option>'; //1:n 일 경우 select
			var tempTitle = '';	//1:n 인지 비교하기 위한체크값
			var tempTitleCount = 0;  //1:n 갯수

			//기존행 삭제
			fnRemoveCert_List();

			//html 형태로 만들기
			for (var i = 0; i < j.length; i++) {

				if(tempTitle != j[i].teamCode){
					tempTitle = j[i].teamCode;
					tempSelect += '<option name="groupCertCode_'+i+'" value="'+j[i].teamCode+'" >'+j[i].teamName+'</option>';
					$("#prodCertMasterCd").val(j[i].teamCode);
					tempTitleCount++;
				}
				tempList += prodCertTempHtml(i,j[i].orgCode,j[i].orgName, j[i].teamCode);
			}

			$("li[id=productCertSelectBox]").show();
			$("#productCertSelectTitle option").remove();
			$("#productCertSelectTitle").html(tempSelect);

			/*
			if(tempTitleCount <= 1){
				$("table[name=data_List]").append(tempList);
			}
			*/
			//$("#prodAddSelectCount").val(tempTitleCount);
		})
	}

	/* KC인증 콤보박스 변경시 이벤트 2017.04.26 코드변경 후 */
	function selectCertTemplateDtlList(infoCd) {

		$.getJSON("<c:out value='${ctx}'/>/edi/product/selectProdCertTemplateDetailList.do",
		{
			infoGrpCd: infoCd
		}, function(j){
			var tempList = ''; //리스트 html
			var tempSelect = '<option value=\'\'>선택</option>';	//1:n 일 경우 select
			var tempTitle = ''; //1:n 인지 비교하기 위한체크값
			var tempTitleCount = 0;  //1:n 갯수

			//기존행 삭제
			fnRemoveCert_List();

			//html 형태로 만들기
			for (var i = 0; i < j.length; i++) {
				if(tempTitle != j[i].orgCode){
					tempTitle = j[i].orgCode;
					tempSelect += '<option name="groupCertCode_'+i+'" value="'+j[i].orgCode+'" >'+j[i].orgName+'</option>';
					$("#prodCertMasterCd").val(j[i].orgCode);
					tempTitleCount++;
				}
				tempList += prodCertDtlHtml(i,j[i].orgCode,j[i].orgName);
			}

			$("li[id=productCertDtlSelectBox]").show();
			$("#productCertSelectDtlTitle option").remove();
			$("#productCertSelectDtlTitle").html(tempSelect);
		})
	}

	/*
	function selectBoxProdTemplateList(infoCd,lcd,flag) {
		$.getJSON("<c:out value='${ctx}'/>/edi/product/selectProdAddTemplateList.do",
		{
			infoGrpCd: infoCd,
			lCode: lcd,
			flag: flag
		}, function(j){
			var tempList = '';	 //리스트 html
			var tempTitle = '';	//1:n 인지 비교하기 위한체크값

			//기존행 삭제
			var rowLength = $("table[name=data_List] tr[name=titleProdAdd]").length;
			for(var i=0;0<rowLength-i;i++){
				$("#titleProdAdd_"+(rowLength-i-1)).remove();
			}

			//html 형태로 만들기
			for (var i = 0; i < j.length; i++) {
				tempList += prodAddTempHtml(i,j[i].orgCode,j[i].orgName);
			}

			$("#prodAddMasterCd").val(infoCd);
			$("table[name=data_List]").append(tempList);
		})
	}
	*/

	function selectBoxProdTemplateDetailList(infoCd) {

		//$.getJSON("<c:out value='${ctx}'/>/edi/product/selectProdAddTemplateList.do",
		$.getJSON("<c:out value='${ctx}'/>/edi/product/selectProdAddTemplateDetailList.do",
		{
			infoGrpCd: infoCd
		}, function(j){
			var tempList = '';	 //리스트 html
			var tempTitle = '';	//1:n 인지 비교하기 위한체크값

			//기존행 삭제
			var rowLength = $("table[name=data_List] tr[name=titleProdAdd]").length;
			for(var i=0;0<rowLength-i;i++){
				$("#titleProdAdd_"+(rowLength-i-1)).remove();
			}

			//html 형태로 만들기
			for (var i = 0; i < j.length; i++) {
				tempList += prodAddTempHtml(i,j[i].orgCode,j[i].orgName,j[i].orgDesc);
			}

			$("#prodAddMasterCd").val(infoCd);
			$("table[name=data_List]").append(tempList);
		})
	}

	function selectBoxCertTemplateDetailList(infoCd) {

		$.getJSON("<c:out value='${ctx}'/>/edi/product/selectProdCertTemplateDetailList.do",
		{
			infoGrpCd: infoCd
		}, function(j){
			var tempList = '';	 //리스트 html
			var tempTitle = '';	//1:n 인지 비교하기 위한체크값

			//기존행 삭제
			fnRemoveCert_List();

			//html 형태로 만들기
			for (var i = 0; i < j.length; i++) {
				tempList += prodCertTempHtml(i,j[i].orgCode,j[i].orgName,j[i].orgDesc, j[i].teamCode);
			}

			$("#prodCertMasterCd").val(infoCd);
			$("table[name=cert_List]").append(tempList);
		})
	}

	function selectBoxCertTemplateDetailList2(infoCd, colCode) {
		$("ul[name=productCertTemplateTitle]").show();
		$("div[name=productCertTemplateData]").show();

		//기존행 삭제
		fnRemoveCert_List();

		var tempList = '';	 //리스트 html
		if (infoCd == "KC001" ) {	//KC001 : 해당사항없음
			tempList += prodCertTempHtml(0,infoCd,"해당사항없음","해당사항없음", infoCd);
			$("li[id=productCertDtlSelectBox]").hide();
			$("#prodCertMasterCd").val(infoCd);
			$("table[name=cert_List]").append(tempList);
		} else {
			$.getJSON("<c:out value='${ctx}'/>/edi/product/selectProdCertTemplateDetailList.do",
				{
					infoGrpCd: infoCd
				}, function(j){
					var tempTitle = '';	//1:n 인지 비교하기 위한체크값

					//html 형태로 만들기
					for (var i = 0; i < j.length; i++) {
						if (colCode == j[i].orgCode) {
								tempList += prodCertTempHtml(0,j[i].orgCode,j[i].orgName,j[i].orgDesc, j[i].teamCode);
								break;
						}
					}

					$("#prodCertMasterCd").val(infoCd);
					$("#prodCertDtlCd").val(colCode);
					$("table[name=cert_List]").append(tempList);
				})
		}
	}

	function prodAddTempHtml(num, orgCd, orgNm,orgDsc){
		var returnHtml = "";
		returnHtml += '<tr id="titleProdAdd_'+num+'" name="titleProdAdd">';
		returnHtml += '<th style="font-weight:normal;font-size:12px;" align="left"><span class="star">*</span>&nbsp;'+orgNm+'<br></th>';
		returnHtml += '<td><input type="hidden" name="prodAddDetailCd_'+num+'" id="prodAddDetailCd_'+num+'" value="'+orgCd+'"/><font color=blue>'+orgDsc+'</font><br><input type="text" name="prodAddDetailNm_'+num+'" id="prodAddDetailNm_'+num+'" value=""  style="line-height:10px;width:480px;"/></td>';
		returnHtml += '<input type="hidden" name="prodAddOrgNm_'+num+'" id="prodAddOrgNm_'+num+'" value="'+orgNm+'"/>';
		returnHtml += '</tr>';
		return returnHtml;
	}

	function prodCertTempHtml(num, orgCd, orgNm,orgDsc, orgCd2){
		var returnHtml = "";
		returnHtml += '<tr id="titleProdCert_'+num+'" name="titleProdCert">';
		returnHtml += '<th style="font-weight:normal;font-size:12px;" align="left"><span class="star">*</span>&nbsp;'+orgNm+'<br></th>';
		returnHtml += '<td><input type="hidden" name="prodCertDetailCd_'+num+'" id="prodCertDetailCd_'+num+'" value="'+orgCd+'"/><font color=blue>'+orgDsc+'</font><br>';
		/*
		if (orgCd2 == 'KC001'){ //해당사항 없음
			returnHtml += '<input type="text" name="prodCertDetailNm_'+num+'" id="prodCertDetailNm_'+num+'" value="해당사항없음"  style="line-height:10px;width:480px;" disabled="disabled"/>';
		}else if(orgCd2 == 'KC006'){ //어린이 용품
			returnHtml += '<input type="text" name="prodCertDetailNm_'+num+'" id="prodCertDetailNm_'+num+'" value="어린이 용품"  style="line-height:10px;width:480px;" disabled="disabled"/>';
		}else{
			returnHtml += '<input type="text" name="prodCertDetailNm_'+num+'" id="prodCertDetailNm_'+num+'" value=""  style="line-height:10px;width:480px;"/>';
		}
		*/
		/* KC인증 콤보박스 변경시 이벤트 2017.04.26 코드변경 후*/
		if (orgCd2 == 'KC001'){ //해당사항 없음
			returnHtml += '<input type="text" name="prodCertDetailNm_'+num+'" id="prodCertDetailNm_'+num+'" value="해당사항없음"  style="line-height:10px;width:480px;" disabled="disabled"/>';
		} else if ((orgCd2 == 'KC003' && orgCd == '00003') || (orgCd2 == 'KC008' && orgCd == '00003') || (orgCd2 == 'KC009' && orgCd == '00003')){ // 공급자적합성확인
			returnHtml += '<input type="text" name="prodCertDetailNm_'+num+'" id="prodCertDetailNm_'+num+'" value="공급자적합성확인" style="line-height:10px;width:480px;" disabled="disabled"/>';
		} else if ( orgCd2 == 'KC008' && orgCd == '00005' ) { // 안전기준준수
			returnHtml += '<input type="text" name="prodCertDetailNm_'+num+'" id="prodCertDetailNm_'+num+'" value="안전기준준수" style="line-height:10px;width:480px;" disabled="disabled"/>';
		} else {
			returnHtml += '<input type="text" name="prodCertDetailNm_'+num+'" id="prodCertDetailNm_'+num+'" value="" maxlength="1000" style="line-height:10px;width:480px;"/>';
		}
		returnHtml += '</td><input type="hidden" name="prodCertOrgNm_'+num+'" id="prodCertOrgNm_'+num+'" value="'+orgNm+'"/>';
		returnHtml += '</tr>';
		return returnHtml;
	}

	function prodCertDtlHtml(num, orgCd, orgNm,orgDsc, orgCd2){
		var returnHtml = "";
		returnHtml += '<tr id="titleProdCert_'+num+'" name="titleProdCert">';
		returnHtml += '<th style="font-weight:normal;font-size:12px;" align="left"><span class="star">*</span>&nbsp;'+orgNm+'<br></th>';
		returnHtml += '<td><input type="hidden" name="prodCertDtlCd_'+num+'" id="prodCertAddDtlCd_'+num+'" value="'+orgCd+'"/><font color=blue>'+orgDsc+'</font><br><input type="text" name="prodCertDtlNm_'+num+'" id="prodCertDtlNm_'+num+'" value=""  style="line-height:10px;width:480px;"/></td>';
		returnHtml += '<input type="hidden" name="prodCertDtlNm_'+num+'" id="prodCertDtlNm_'+num+'" value="'+orgNm+'"/>';
		returnHtml += '</tr>';
		return returnHtml;
	}

	//전자상거래 검증
	function prodAddValidationCheck() {

		var prodAddLength = $("table[name=data_List] tr[name=titleProdAdd]").length;
		var tempProdAddCd = "";
		var tempProdAddNm = "";

		if($("select[name=productAddSelectTitle]").val() == ''){
			alert("전자 상거래 Select 박스를 선택하세요.");
			$("select[name=productAddSelectTitle]").focus();
			return false;
		}

		for(var i=0;i<prodAddLength;i++){
		if(! calByteProdjava($("#prodAddDetailNm_"+i).val(),2000,"전상법 속성 중 "+$("#prodAddOrgNm_"+i).val(),false) ) return;
		if('' == $("#prodAddDetailNm_"+i).val()){
				alert("전자 상거래 필드는 필수 입력값입니다!");
				$("#prodAddDetailNm_"+i).focus();
				return false;
			}

		//	tempProdAddCd = tempProdAddCd + $("#prodAddDetailCd_"+i).val() + "|| ";
			tempProdAddCd = tempProdAddCd + $("#prodAddDetailCd_"+i).val() + "#//#";
		//	alert(tempProdAddCd);

		//	tempProdAddNm = tempProdAddNm + $("#prodAddDetailNm_"+i).val() + "|| ";
			tempProdAddNm = tempProdAddNm + $("#prodAddDetailNm_"+i).val() + "#//#";
		}

		$("#prodAddCd").val(tempProdAddCd);
		$("#prodAddNm").val(tempProdAddNm);

		return true;
	}

	//제품안전인증
	function prodCertValidationCheck() {

		var prodCertLength = $("table[name=cert_List] tr[name=titleProdCert]").length;
		var tempProdCertCd = "";
		var tempProdCertNm = "";

		if($("select[name=productCertSelectTitle]").val() == ''){
			alert("제품안전인증 Select 박스를 선택하세요.");
			$("select[name=productCertSelectTitle]").focus();
			return false;
		}

		/* KC인증 콤보박스 변경시 이벤트 2017.04.28 코드변경 후
		for(var i=0;i<prodCertLength;i++){
		if(! calByteProdjava($("#prodCertDetailNm_"+i).val(),2000,"KC 인증마크 속성 중 "+$("#prodCertOrgNm_"+i).val(),false) ) return;
		if('' == $("#prodCertDetailNm_"+i).val()){
				alert(KC 인증마크 필드는 필수 입력값입니다!");
				$("#prodCertDetailNm_"+i).focus();
				return false;
			}

		//	tempProdAddCd = tempProdAddCd + $("#prodAddDetailCd_"+i).val() + "|| ";
			tempProdCertCd = tempProdCertCd + $("#prodCertDetailCd_"+i).val() + "#//#";

		//	tempProdAddNm = tempProdAddNm + $("#prodAddDetailNm_"+i).val() + "|| ";
			tempProdCertNm = tempProdCertNm + $("#prodCertDetailNm_"+i).val() + "#//#";
		}
		*/

		if($("select[name=productCertSelectTitle]").val() != 'KC001' && '' == $("#prodCertDetailNm_0").val()){
			alert("제품안전인증 필드는 필수 입력값입니다!");
			$("#prodCertDetailNm_"+i).focus();
			return false;
		}

		if ($("select[name=productCertSelectTitle]").val() == 'KC001') {	//KC001 : 해당사항없음
			tempProdCertCd = "00001" + "#//#";
			tempProdCertNm = "해당사항없음" + "#//#";
		} else {
			tempProdCertCd = $("#prodCertDetailCd_0").val() + "#//#";
			tempProdCertNm = $("#prodCertDetailNm_0").val() + "#//#";
		}

		$("#prodCertCd").val(tempProdCertCd);
		$("#prodCertNm").val(tempProdCertNm);

		return true;
	}

	function setupjangCheck(){
		// tradeType (1 직매입)
		if($("input[name=tradeType]").val() == '1'){
			//무조건 빈값
			//상품유형 (1 NB,3 SB,5 NB2,9 기타)
			if($("select[name=productTypeDivnCode]").val() == '1' || $("select[name=productTypeDivnCode]").val() == '3'|| $("select[name=productTypeDivnCode]").val() == '5'|| $("select[name=productTypeDivnCode]").val() == '9'){

			}else{		//무조건 빈값
				$("input[name=newProdPromoFg]").val('');
				$("input[name=newProdStDy]").val('');
				$("input[name=overPromoFg]").val('');
			}
		}else{
			$("input[name=newProdPromoFg]").val('');
			$("input[name=newProdStDy]").val('');
			$("input[name=overPromoFg]").val('');
		}
		var newProdPromoFg='';
		var newProdStDy='';
		newProdPromoFg = $("input[name=newProdPromoFg]:checked").val();
		var newProdStDy = $("input[name=newProdStDy]").val();
		overPromoFg = $("input[name=overPromoFg]:checked").val();

		if (newProdPromoFg =='1'){
			if (newProdStDy==''){
				alert("※신상품 출시일자를 넣으셔야 합니다. \n\n 신상품 출시일자는 KAN(88)코드 등록일자를 넣으셔야 합니다.");
				return false;
			}
		} else {
			$("input[name=newProdStDy]").val('');
		}
		return true;
	};

	function calByteProdjava(Obj, VMax, ObjName, nets)
	{
		var tmpStr;
		var temp=0;
		var onechar;
		var tcount;
		tcount = 0;
		var aquery = Obj;
		tmpStr = new String(aquery);
		temp = tmpStr.length;

		for (k=0;k<temp;k++)
		{
			onechar = tmpStr.charAt(k);
			if (escape(onechar).length > 4)
			{
				tcount += 3;
			}
			else tcount ++;
		}
		if(tcount>VMax)
		{
		reserve = tcount-VMax;
			if(nets){
				alert(ObjName+" 은(는) "+VMax+"바이트 이상은 전송하실수 없습니다.\r\n 쓰신 메세지는 "+reserve+"바이트가 초과되었습니다.\r\n 초과된 부분은 자동으로 삭제됩니다.");
				netsCheckProd(Obj, VMax);
			}
			else{
				alert(ObjName+"은(는) "+VMax+"바이트 이상은 전송하실수 없습니다.\r\n 쓰신 메세지는 "+reserve+"바이트가 초과되었습니다.");
				Obj.focus();
				Obj.value = aquery;
			}
			return false;
		}
		return true;
	}

	/**********************************************************
	 * 특수문자 입력 방지 ex) onKeyPress="keyCode(event)"
	 ******************************************************** */
	function keyCode(e)
	{
		var code = (window.event) ? event.keyCode : e.which; // IE : FF - Chrome both

		if (code > 32 && code < 48) keyResult(e);
		if (code > 57 && code < 65) keyResult(e);
		if (code > 90 && code < 97) keyResult(e);
		if (code > 122 && code < 127) keyResult(e);
	}

	function keyResult(e)
	{
		alert("검색항목에는 특수문자를 사용할수 없습니다!");

		if (navigator.appName != "Netscape") {
			event.returnValue = false; //IE - Chrome both
		}
		else {
			e.preventDefault(); //FF - Chrome both
		}
	}

	//전자상거래 수정 조회 selectProdTemplateUpdateViewList

	function selectProdTemplateUpdateViewList(prodCd,lcd,infoGrpCd,flag){

		$.getJSON("<c:out value='${ctx}'/>/edi/product/selectProdAddTemplateUpdateList.do",
		{
			newProdCd: prodCd,
			lCode: lcd,
			flag: flag
		}, function(j){
			var tempSelect = '<option value=\'\'>선택</option>'; //1:n 일 경우 select
			var tempTitle = '';	//1:n 인지 비교하기 위한체크값
			var tempTitleCount = 0;  //1:n 갯수

			//html 형태로 만들기
			for (var i = 0; i < j.length; i++) {

				if(tempTitle != j[i].teamCode){
					tempTitle = j[i].teamCode;

					if(infoGrpCd == j[i].teamCode){
						tempSelect += '<option name="groupCode_'+i+'" value="'+j[i].teamCode+'" selected="selected" >'+j[i].teamName+'</option>';
					}else{
						tempSelect += '<option name="groupCode_'+i+'" value="'+j[i].teamCode+'">'+j[i].teamName+'</option>';
					}

					$("#prodAddMasterCd").val(infoGrpCd);

					tempTitleCount++;
				}
			}

			$("li[id=productAddSelectBox]").show();
			$("#productAddSelectTitle option").remove();
			$("#productAddSelectTitle").html(tempSelect);

			//$("#prodAddSelectCount").val(tempTitleCount);
		})
		selectBoxProdTemplateUpdateDetailList(prodCd,infoGrpCd);

	}

	function selectBoxProdTemplateUpdateDetailList(prodCd,infoGrpCd) {

		$.getJSON("<c:out value='${ctx}'/>/edi/product/selectProdAddTemplateUpdateDetailList.do",
		{
			newProdCd: prodCd,
			infoGrpCd: infoGrpCd
		}, function(j){
			var tempList = '';	 //리스트 html
			var tempTitle = '';	//1:n 인지 비교하기 위한체크값

			var rowLength = $("table[name=data_List] tr[name=titleProdAdd]").length;
			for(var i=0;0<rowLength-i;i++){
				$("#titleProdAdd_"+(rowLength-i-1)).remove();
			}
			//html 형태로 만들기
			for (var i = 0; i < j.length; i++) {

				tempList += prodAddTempUpdateHtml(i,j[i].orgCode,j[i].orgName,j[i].orgDesc,j[i].colVal);
			}

			$("div[name=productAddTemplateData]").show();
			$("#prodAddMasterCd").val(infoGrpCd);
			$("table[name=data_List]").append(tempList);

			}
		)
		//selectBoxProdTemplateUpdateDetailList("<c:out value='${tmpProduct.newProductCode}'/>","<c:out value='${tmpProduct.infoGrpCd}'/>");

	}

	function prodAddTempUpdateHtml(num, orgCd, orgNm,orgDsc,colVal){
		//alert("test");
		if(colVal == null){
			colVal = "";
		}

		var returnHtml = "";
		returnHtml += '<tr id="titleProdAdd_'+num+'" name="titleProdAdd">';
		returnHtml += '<th style="font-weight:normal;font-size:12px;" align="left"><span class="star">*</span>&nbsp;'+orgNm+'<br></th>';
		returnHtml += '<td><input type="hidden" name="prodAddDetailCd_'+num+'" id="prodAddDetailCd_'+num+'" value="'+orgCd+'"/><font color=blue>'+orgDsc+'</font><br><input type="text" name="prodAddDetailNm_'+num+'" id="prodAddDetailNm_'+num+'" value="'+colVal+'"  style="line-height:10px;width:480px;"/></td>';
		returnHtml += '<input type="hidden" name="prodAddOrgNm_'+num+'" id="prodAddOrgNm_'+num+'" value="'+orgNm+'"/>';
		returnHtml += '</tr>';
		//alert(returnHtml);
		return returnHtml;
	}

	//KC 인증마크 수정 조회 selectProdTemplateUpdateViewList
	function selectProdCertTemplateUpdateViewList(prodCd,lcd,infoGrpCd,flag){

		$.getJSON("<c:out value='${ctx}'/>/edi/product/selectProdCertTemplateList.do",
		{
			newProdCd: prodCd,
			lCode: lcd,
			flag: flag
		}, function(j){
			var tempSelect = '<option value=\'\'>선택</option>'; //1:n 일 경우 select
			var tempTitle = '';	//1:n 인지 비교하기 위한체크값
			var tempTitleCount = 0;  //1:n 갯수

			//html 형태로 만들기
			for (var i = 0; i < j.length; i++) {

				if(tempTitle != j[i].teamCode){
					tempTitle = j[i].teamCode;

					if(infoGrpCd == j[i].teamCode){
						tempSelect += '<option name="groupCertCode_'+i+'" value="'+j[i].teamCode+'" selected="selected" >'+j[i].teamName+'</option>';
					}else{
						tempSelect += '<option name="groupCertCode_'+i+'" value="'+j[i].teamCode+'">'+j[i].teamName+'</option>';
					}

					$("#prodCertMasterCd").val(infoGrpCd);
					tempTitleCount++;
				}
			}

			$("li[id=productCertSelectBox]").show();
			$("#productCertSelectTitle option").remove();
			$("#productCertSelectTitle").html(tempSelect);

			//$("#prodAddSelectCount").val(tempTitleCount);
		})
		selectBoxProdCertTemplateUpdateDetailList(prodCd,infoGrpCd);
	}

	function selectBoxProdCertTemplateUpdateDetailList(prodCd,infoGrpCd) {
		$.getJSON("<c:out value='${ctx}'/>/edi/product/selectProdCertTemplateUpdateDetailList.do",		
		{
			newProdCd: prodCd,
			infoGrpCd: infoGrpCd
		}, function(j){
			var tempList = '';	 //리스트 html
			var tempListSel = '';	 //리스트 html
			var tempSelect		= '<option value=\'\'>선택</option>';	//1:n 일 경우 select
			var tempTitle = '';	//1:n 인지 비교하기 위한체크값

			//기존행 삭제
			fnRemoveCert_List();

			//html 형태로 만들기
			for (var i = 0; i < j.length; i++) {
				if(tempTitle != j[i].orgCode){	//Dtl selectBox Html 생성
					tempTitle = j[i].orgCode;

					if (j[i].colVal != null && j[i].colVal != '') {	//등록된 값 표시
						$("#prodCertDtlCd").val(j[i].orgCode);
						tempSelect += '<option name="groupCertCode_'+i+'" value="'+j[i].orgCode+'" selected="selected" >'+j[i].orgName+'</option>';
						tempList = prodCertTempUpdateHtml(0,j[i].orgCode,j[i].orgName,j[i].orgDesc,j[i].colVal, j[i].teamCode);
					} else {
						tempSelect += '<option name="groupCertCode_'+i+'" value="'+j[i].orgCode+'" >'+j[i].orgName+'</option>';
					}
				}
			}

			if (infoGrpCd == "KC001" ) {	//KC001 : 해당사항없음
				tempList = prodCertTempHtml(0,infoGrpCd,"해당사항없음","해당사항없음", infoGrpCd);
				$("li[id=productCertDtlSelectBox]").hide();
			} else {
				$("li[id=productCertDtlSelectBox]").show();
				$("#productCertSelectDtlTitle option").remove();
				$("#productCertSelectDtlTitle").html(tempSelect);
			}

			$("div[name=productCertTemplateData]").show();
			$("#prodCertMasterCd").val(infoGrpCd);
			$("table[name=cert_List]").append(tempList);

		})
		//selectBoxProdTemplateUpdateDetailList("<c:out value='${tmpProduct.newProductCode}'/>","<c:out value='${tmpProduct.infoGrpCd}'/>");
	}

	function prodCertTempUpdateHtml(num, orgCd, orgNm,orgDsc,colVal, orgCd2){
		if(colVal == null){
			colVal = "";
		}

		var returnHtml = "";
		returnHtml += '<tr id="titleProdCert_'+num+'" name="titleProdCert">';
		returnHtml += '<th style="font-weight:normal;font-size:12px;" align="left"><span class="star">*</span>&nbsp;'+orgNm+'<br></th>';
		returnHtml += '<td><input type="hidden" name="prodCertDetailCd_'+num+'" id="prodCertDetailCd_'+num+'" value="'+orgCd+'"/><font color=blue>'+orgDsc+'</font><br>';
		returnHtml += '<input type="text" name="prodCertDetailNm_'+num+'" id="prodCertDetailNm_'+num+'" value="'+colVal+'" maxlength="1000" style="line-height:10px;width:480px;" ';
		if ((orgCd2 == 'KC001') || (orgCd2 == 'KC003' && orgCd == '00003') || (orgCd2 == 'KC008' && orgCd == '00003') 
			|| (orgCd2 == 'KC009' && orgCd == '00003') || (orgCd2 == 'KC008' && orgCd == '00005')){ //해당사항 없음 또는 공급자적합성
			returnHtml += 'disabled="disabled"';
		}
		returnHtml += '/></td><input type="hidden" name="prodCertOrgNm_'+num+'" id="prodCertOrgNm_'+num+'" value="'+orgNm+'"/>';
		returnHtml += '</tr>';
		return returnHtml;
	}

	function onlyNumber(event) { // 8 백스페이스 , 9 탭 , 37 왼쪽이동, 39 오른쪽이동, 46 delete
		var key;
		if(event.which) { // ie9 firefox chrome opera safari
			key = event.which;
		} else if(window.event) { // ie8 and old
			key = event.keyCode;
		}
		if(!( key==8 || key==9 || key==37 || key==39 || key==46 || (key >= 48 && key <= 57) || (key >= 96 && key <= 105) )) {
			alert("숫자만 입력해 주세요");
			if(event.preventDefault){
				event.preventDefault();
			} else {
				event.returnValue = false;
			}
		} else {
			event.returnValue = true;
		}
	}

	//단품 정보 리스트 테이블
	function fnItemAdd(type){
		if($.trim($("#itemTd").html()) == ''){
			//if(type == 'new')
				$("#itemTd").html("<table id='itemSubTable' style='width:700px'><tr><th style='width:80px'>단품코드</th><th style='width:300px'>옵션설명</th><th style='width:100px'>재고여부</th><th style='width:100px'>재고수량</th><th></th></tr></table>");
			//else
			//	$("#itemTd").html("<table id='itemSubTable' style='width:700px'><tr><th style='width:100px'>단품코드</th><th style='width:320px'>옵션설명</th><th style='width:100px'>재고여부</th><th style='width:100px'>재고수량</th></tr></table>");
		}
		var rows = $("#itemSubTable tr");
		var index = rows.length;
		var itemCd = "00"+index;
		if(index > 1){
			if($("#optnDesc"+(index-1)).val() == "" || $("#rservStkQty"+(index-1)).val() == ""){
				alert("단품 정보를 정확하게 입력하세요");
				return;
			}
			if($("#optnDesc"+(index-1)).val() != "" && $("#optnDesc"+(index-1)).val().indexOf(';') > -1){
				alert(" ';' 을 사용할수 없습니다.");
				return;
			}
			//20180125 100개 이상 등록 가능하도록 수정
			if(index > 9 && index < 100) {
				itemCd = "0"+index;
			} else if(index >= 100 && index < 1000) {
				itemCd = index;
			} else if(index >= 1000) {
				alert("1000개 이상 등록할 수 없습니다.");
				return;
			}
		}

		var newRow = "<tr id=row"+index+"><td style='text-align:center'>"+itemCd+"<input type='hidden' name='itemCd' id='itemCd"+index+"' value='"+itemCd+"'/></td>";
		newRow += "<td><input type='text' size='50' name='optnDesc' id='optnDesc"+index+"'/></td>";
		newRow += "<td><select id='stkMgrYn"+index+"' name='stkMgrYn' style='width:100px'><option value='N'>N</option><option value='Y'>Y</option></select></td>";
		newRow += "<td><input type='text' style='text-align:right' id='rservStkQty"+index+"' name='rservStkQty' onkeydown='onlyNumber(event)' /></td>";
		//신규상품등록
		//if(type == 'new'){
			if(index > 1){
				for(var i = 0; i < index; i++){
					$("#deleteNewItem"+i).attr("style", "display:none");
				}
			}
			newRow += "<td><a href='javascript:fnNewItemDelete("+index+")' id='deleteNewItem"+index+"' class='btn' ><span>삭제</span></a></td>";
		//}
		newRow += "</tr>";
		$("#itemSubTable").last().append(newRow);
		$("#itemRow").val(index);
	}

	//단품 정보 리스트 테이블(신규)
	function fnItemAddNew(type){
		if($.trim($("#itemTd").html()) == ''){
			$("#itemTd").html("<table id='itemSubTable' style='width:700px'><tr><th style='width:70px'>단품코드</th><th style='width:200px'>옵션설명</th><th style='width:60px'>재고여부</th><th style='width:100px'>재고수량</th><th style='width:100px'>가격</th><th></th></tr></table>");
		}
		var rows = $("#itemSubTable tr");
		var index = rows.length;
		var itemCd = "00"+index;
		if(index > 1){
			if($("#optnDesc"+(index-1)).val() == "" || $("#rservStkQty"+(index-1)).val() == ""){
				alert("단품 정보를 정확하게 입력하세요");
				return;
			}

			//20180427 단품정보 등록 확인 추가
			if($(":radio[name='prodPrcMgrYn']:checked").val() == "1"){
				if($("#optnAmt"+(index-1)).val() == ""){
					alert("단품 가격 정보를 정확하게 입력하세요");
					return;
				}

				if(Number($("#optnAmt"+(index-1)).val()) == 0){
					alert("단품 가격을 0원 이상 입력하세요");
					return;
				}
			}

			if($("#optnDesc"+(index-1)).val() != "" && $("#optnDesc"+(index-1)).val().indexOf(';') > -1){
				alert(" ';' 을 사용할수 없습니다.");
				return;
			}
			//20180125 100개 이상 등록 가능하도록 수정
			if(index > 9 && index < 100) {
				itemCd = "0"+index;
			} else if(index >= 100 && index < 1000) {
				itemCd = index;
			} else if(index >= 1000) {
				alert("1000개 이상 등록할 수 없습니다.");
				return;
			}
		}

		var newRow = "<tr id=row"+index+"><td style='text-align:center'>"+itemCd+"<input type='hidden' name='itemCd' id='itemCd"+index+"' value='"+itemCd+"'/></td>";
		newRow += "<td><input type='text' style='width:98%' name='optnDesc' id='optnDesc"+index+"'/></td>";
		newRow += "<td><select id='stkMgrYn"+index+"' name='stkMgrYn' style='width:98%;'><option value='N'>N</option><option value='Y'>Y</option></select></td>";
		newRow += "<td><input type='text' style='text-align:right; width:98%;' id='rservStkQty"+index+"' name='rservStkQty' onkeydown='onlyNumber(event)' /></td>";
		newRow += "<td id='optnAmtTD"+index+"'><input type='text' style='text-align:right; width:98%;' id='optnAmt"+index+"' name='optnAmt' onkeydown='onlyNumber(event)' /></td>";
		//신규상품등록
		//if(type == 'new'){
			if(index > 1){
				for(var i = 0; i < index; i++){
					$("#deleteNewItem"+i).attr("style", "display:none");
				}
			}
			newRow += "<td><a href='javascript:fnNewItemDelete("+index+")' id='deleteNewItem"+index+"' class='btn' ><span>삭제</span></a></td>";
		//}
		newRow += "</tr>";
		$("#itemSubTable").last().append(newRow);
		$("#itemRow").val(index);
	}

	//신규상품추가 단품 정보 row 삭제'
	function fnNewItemDelete(idx){
		if(idx > 1){
			if (idx-1 > 1) {
				$("#deleteNewItem"+(idx-1)).attr("style", "display:");
			}
		}
		$('#row' + idx).remove();
		$("#itemRow").val(parseInt($("#itemRow").val())-1);
	}

	/* 단품정보 삭제 */
	function fnItemDelete(idx, itemCd){
		if(!confirm("["+itemCd+"] 단품 정보를 삭제하시겠습니까?")){
			return;
		}

		var paramInfo = {};
		paramInfo["itemCode"] = itemCd;
		paramInfo["newProductCode"] = "<c:out value='${newProdDetailInfo.pgmId}'/>";

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/delOnlineItemInfo.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				if (data.msg == "SUCCESS") {
					fnNewItemDelete(idx);
				}else {
					alert("<spring:message code='msg.common.fail.delete'/>")
				}
			}
		});

	//	$.ajax({
	//		//url	 : '${ctx}/edi/product/PEDMPRO000305.do',
	//		url		: '<c:url value="/edi/product/PEDMPRO000305.do"/>',
	//		type	: "POST",
	//		data	: param,
	//		error   : function(xhr,status, error){
	//			console.log("xhr ==" + xhr);
	//			console.log("status ==" + status);
	//			console.log("error ==" + error);
	//			//fnNewItemDelete(idx);
	//		}
	//	}); 
	}

	/* 제품안전인증 입력항목 삭제 함수 */
	function fnRemoveCert_List(){
		//기존행 삭제
		var rowLength = $("table[name=cert_List] tr[name=titleProdCert]").length;
		for(var i=0;0<rowLength-i;i++){
			$("#titleProdCert_"+(rowLength-i-1)).remove();
		}
	}

</script>