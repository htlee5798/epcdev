<%--
	Page Name 	: CommonProductFunction.jsp
	Description : 신상품등록(일반)
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	2015.11.26 		SONG MIN KYO 	최초생성
--%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript" >

	var ONOFFLINE_PRODUCT = "0";		//온오프겸용 상품
	var STANDARD_PRODUCT  =  "1";		//규격 상품

	/* 2019.12.23 전산정보팀 이상구, 신상품장려금 대상 파트너 여부 확인(X: 대상) */
	function _eventCheckNewPromoFg(val) {

		var paramInfo	=	{};

		paramInfo["selectedVendor"]	=	val;

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/NcheckNewPromoFg.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				if(data.VendorNewPromoCnt >= 1){ // 신상품장려금 적용 업체일 경우
					$("#newPromoVenFg").val('1');
				}else{
					$("#newPromoVenFg").val('0'); // 신상품장려금 적용 업체가 아닐경우
				}

			}
		});
	}
	/* 거래중지된 업체인지 체크 */
	function _eventCheckBlackListVendor(val) {
		var paramInfo	=	{};

		paramInfo["selectedVendor"]	=	val;

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/NcheckCountVendorStopTrading.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				_eventVendorCountResult(data, val);

			}
		});
	}

	/* 거래 중지여부, 거래방식, 과세구분값 적용 */
	function _eventVendorCountResult(data, val) {
		var selectedVendor 	= 	val;							//협력업체 코드
		var selectedObj 	= 	$("select[name='entpCd']");
		var errorNode 		= 	selectedObj.prev("div[name=error_msg]").length;
		var j 				= 	data.VendorStopTradingCnt;		//거래중지여부
		var tradeType 		= 	data.tradeTypeCd;				//거래유형
		var taxDivnCode 	= 	data.taxDivnCode;				//과세구분

		$("#tradeType").val(tradeType);

		var currentChecked = $("input[name=onOffDivnCd]").val().replace(/\s/gi, '');

		var resultCount = parseInt(j);

	    if( resultCount > 0 ) {
		    if(errorNode == 0) {
				var message = "거래가 중지된 협력업체 입니다.";
				generateMessage(selectedObj, message);
		    }

		} else {
		    deleteErrorMessageIfExist(selectedObj);
		}

	     //거래방식에 따라 이익율을 직접 입력할수 있음.(온오프 겸용일때만 실행 )
	     if( currentChecked == ONOFFLINE_PRODUCT ) {

	    	 deleteErrorMessageIfExist($("input[name=norProdPcost]"));
	    	 deleteErrorMessageIfExist($("input[name=prftRate]"));
	    	 $("input[name=norProdPcost]").val("");
	    	 $("input[name=prftRate]").val("");


	    	 deleteErrorMessageIfExist($("input[name=wnorProdSalePrc]"));
	    	 deleteErrorMessageIfExist($("input[name=wprftRate]"));
	    	 $("input[name=wnorProdPcost]").val("");
	    	 $("input[name=wprftRate]").val("");

	    	 _eventChangeFiledByTradeType(tradeType);
	     }

		//과세 구분 코드값 적용
		$("#taxatDivnCd").prev("div[name=error_msg]").remove();

		if (taxDivnCode != null && taxDivnCode != "") {
			if (taxDivnCode == "1") {			//면세
				$("#taxatDivnCd").val("0");
			} else if (taxDivnCode == "2") {	//과세
				$("#taxatDivnCd").val("1");
			}
		}


		//거래형태가 직매입(1), 특약2(4)일 경우 무발주매입구분 비활성화  특약1일경우 활성화
		if (tradeType != "2") {
			$(":radio[name='npurBuyPsbtDivnCd']:radio[value='']").attr("checked", true);		//무발주매입구분 미적용으로 Default
			$("input[name=npurBuyPsbtDivnCd]").attr("disabled",true);							//Radio 버튼 선택 못하게 Disable
		} else {
			$(":radio[name='npurBuyPsbtDivnCd']").removeAttr("checked");
			$("input[name=npurBuyPsbtDivnCd]").attr("disabled",false);							//Radio 버튼 선택 활성화
		}

		//$("#taxatDivnCd").val(taxDivnCode);

		//신상품입점 장려금 적용구분, 신상품 출시일자, 성과초과장려금적용구분 화성화  / 비활성화 함수 호출
		_eventSetupJang($("#newProduct select[name='prodTypeDivnCd']").val().replace(/\s/gi, ''));

	}

	//단순 메세지 표시함수
	function generateMessage(jqueryObject, message) {
		$("<div id=\"error_msg\" name=\"error_msg\" style=\"color:red;\">" +message+ "</div>").insertBefore(jqueryObject);
	}



	//온오프 겸용 상품에서만 실행됨. !!!!!!
	//거래 유형에 따라 이익률, 정상원가 입력필드 상태 변경.
	//거래 유형 2 or 4 		  : 이익률 입력 , 정상원가 산출
	//거래 유형 2 , 4가 아닌 경우  : 이익률 산출 , 정상원가 입력
	function _eventChangeFiledByTradeType(tradeType) {
		//alert(tradeType);

		//이익률 입력, 정상원가 산출 2:특약1, 4:특약2
		if(tradeType == "2" || tradeType == "4") {

	    	 //이익률 입력가능
	    	 $("input[name=prftRate]").attr("readonly", false);
	    	 $("input[name=prftRate]").addClass("required").removeClass("inputRead");
	    	 $("input[name=prftRate]").parent().prev().find("span.star").show();

	    	//이동빈추가
	    	 $("input[name=wprftRate]").attr("readonly", false);
	    	 $("input[name=wprftRate]").addClass("required").removeClass("inputRead");
	    	 $("input[name=wprftRate]").parent().prev().find("span.star").show();

	    	 //원가는 입력받은 이익률과 매가로 산출
	    	 $("input[name=norProdPcost]").attr("readonly", true);
	    	 $("input[name=norProdPcost]").removeClass("required").addClass("inputRead");
	    	 $("input[name=norProdPcost]").parent().prev().find("span.star").hide();

	    	 //이동빈추가
	    	 $("input[name=wnorProdPcost]").attr("readonly", true);
	    	 $("input[name=wnorProdPcost]").removeClass("required").addClass("inputRead");
	    	 $("input[name=wnorProdPcost]").parent().prev().find("span.star").hide();

	     } else {

		     //이익률 산출. 읽기 전용 상태
	    	 $("input[name=prftRate]").attr("readonly", true);
	    	 $("input[name=prftRate]").removeClass("required").addClass("inputRead");
	    	 $("input[name=prftRate]").parent().prev().find("span.star").hide();

	    	 //이동빈추가
	    	 $("input[name=wprftRate]").attr("readonly", true);
	    	 $("input[name=wprftRate]").removeClass("required").addClass("inputRead");
	    	 $("input[name=wprftRate]").parent().prev().find("span.star").hide();

	    	 //정상원가 사용자 입력. 매가, 원가, 면과세 구분을 가지고 이익률이 산출됨.
	    	 $("input[name=norProdPcost]").attr("readonly", false);
	    	 $("input[name=norProdPcost]").addClass("required").removeClass("inputRead");
	    	 $("input[name=norProdPcost]").parent().prev().find("span.star").show();

	    	 //이동빈추가
	    	 $("input[name=wnorProdPcost]").attr("readonly", false);
	    	 $("input[name=wnorProdPcost]").addClass("required").removeClass("inputRead");
	    	 $("input[name=wnorProdPcost]").parent().prev().find("span.star").show();
		 }
	}


	/* 팀의 대분류 조회 */
	function _eventSelectL1List(val) {
		var paramInfo	=	{};

		paramInfo["groupCode"]	=	val;

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/NselectL1List.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				var resultList	=	data.l1List;
				if (resultList.length > 0) {

					var eleHtml = [];
					for (var i = 0; i < resultList.length; i++) {
						eleHtml[i] = "<option value='"+resultList[i].l1Cd+"'>"+resultList[i].l1Nm+"</option>"+"\n";
					}

					$("#l1Cd option").not("[value='']").remove();	//콤보박스 초기화
					$("#l1Cd").append(eleHtml.join(''));
				} else {
					$("#l1Cd option").not("[value='']").remove();	//콤보박스 초기화
				}
			}
		});
	}

	/* 대분류의 소분류 조회 */
	function _eventSelectL3List(groupCode, l1Cd, val) {
		var paramInfo	=	{};

		paramInfo["groupCode"]	=	groupCode
		paramInfo["l1Cd"]		=	l1Cd;
		paramInfo["l2Cd"]		=	val;

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/NselectL3List.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				var resultList	=	data.l3List;
				if (resultList.length > 0) {

					var eleHtml = [];
					for (var i = 0; i < resultList.length; i++) {
						eleHtml[i] = "<option value='"+resultList[i].l3Cd+"'>"+resultList[i].l3Nm+"</option>"+"\n";
					}

					$("#l3Cd option").not("[value='']").remove();	//콤보박스 초기화
					$("#l3Cd").append(eleHtml.join(''));
				} else {
					$("#l3Cd option").not("[value='']").remove();	//콤보박스 초기화
				}
			}
		});
	}


	/* 대분류의 소분류 조회 */
	function _eventSelectGrpList(val) {
		var paramInfo	=	{};
		paramInfo["l3Cd"]		=	val;


		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/NselectGrpList.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				var resultList	=	data.grpL3List;
				if (resultList.length > 0) {

					var eleHtml = [];
					for (var i = 0; i < resultList.length; i++) {
						eleHtml[i] = "<option value='"+resultList[i].grpL3Cd+"'>"+resultList[i].grpL3Nm+"</option>"+"\n";
					}

					$("#grpCd option").not("[value='']").remove();	//콤보박스 초기화
					$("#grpCd").append(eleHtml.join(''));
				} else {
					$("#grpCd option").not("[value='']").remove();	//콤보박스 초기화
				}
			}
		});


	}




//
	/* text필드 검증...상품사이즈(가로, 세로, 높이)는 온오프에서만 필수 항목 */
	function validateFormTextField(jqueryTextFieldObj) {
		var currentChecked = $("input[name=onOffDivnCd]").val();

		//상품 사이즈(가로, 세로, 높이) 이외의 필드이거나
		//상품 사이즈 필드더라도 온오프겸용상품인경우 필수항목으로 유효성체크 실행.
		if(!jqueryTextFieldObj.hasClass("size") || currentChecked == ONOFFLINE_PRODUCT) {
			//console.log("!jqueryTextFieldObj.hasClass(size) || currentChecked == ONOFFLINE_PRODUCT");
			//console.log(jqueryTextFieldObj);
			validateTextField(jqueryTextFieldObj);
		//상품 사이즈(가로, 세로, 높이))를 검증하는 경우
		} else {
			if(jqueryTextFieldObj.val() != '') {
				//console.log("jqueryTextFieldObj.val() !=");
				//console.log(jqueryTextFieldObj);
				validateTextField(jqueryTextFieldObj);
			}
		}

	}

	/* 상품유형 체인지 이벤트 */
	function _eventSetupJang(val) {
		var tradeType	=	$("input[name=tradeType]").val().replace(/\s/gi, '');	//거래형태
		var sType = $("#sType option:selected").val().replace(/\s/gi, '');

		//console.log("tradeType ==" + tradeType);

		//-----거래 형태가 직매입이면
		if (tradeType == "1") {

			//-----NB(1), SB(3), NB2(4), 기타(5) 유형만 신상품입점장려금, 신상품출시일자, 성과초과장려금적용구분 활성화
			if (val == "1" || val == "3" || val == "4" || val == "5" || val == "9") {
				setupJangbodyopen();

			//-----NB, SB, NB2, 기타 유형을 제외한 나머지 신상품입점장려금, 신상품출시일자, 성과초과장려금적용구분 비활성화
			} else {
				setupJangbodyclose();
			}

		//-----거래형태가 직매입이 아니면 신상품입점장려금적용구분, 신상품출시일자, 성과초과장려금적용구분은 사용하지 않는다.
		} else {
			setupJangbodyclose();
		}


		///////////////////////////////////////////// 폐기물, 재활용 활성화  / 비활성화 ///////////////////////////////////////////////////
		//-----상품 유형이 PB일때만 폐기물, 재활용 활성화
		if (val == "2" || (val == "1" && (sType == '3'))) {

			//-----추가정보 입력란의 TR 길이 만큼 돌면서
			for (var i = 0; i < $("#tblNewProdAdd tr").length; i++) {

				//----폐기물과 재활용 입력란을 활성화 해주고 입력란을 초기화 해준다.
				if (i > 0 && i < 5) {
					$("#tblNewProdAdd tr").eq(i).removeAttr("style");

					//입력값 초기화
					$("select[name='wasteFg']").val("");
					$("select[name='recycleFg1']").val("");
					$("select[name='recycleFg2']").val("");
					$("select[name='recycleFg3']").val("");
					$("select[name='recycleWeight1']").val("");
					$("select[name='recycleWeight2']").val("");
					$("select[name='recycleWeight3']").val("");
					$("input[name='plasticWeight']").val("");
					
					$("#wasteFg option:contains('선택')").attr("selected", "selected");
					$("#recycleFg1 option:contains('선택')").attr("selected", "selected");
					$("#recycleFg2 option:contains('선택')").attr("selected", "selected");
					$("#recycleFg3 option:contains('선택')").attr("selected", "selected");
				}
			}

		//-----상품 유형이 PB상품이 아닐경우 폐기물, 재활용 비활성화
		} else {
			//-----추가정보 입력란의 TR 길이 만큼 돌면서
			for (var i = 0; i < $("#tblNewProdAdd tr").length; i++) {

				//----폐기물과 재활용 입력란을 비활성화 해준다.
				if (i > 0 && i < 5) {
					$("#tblNewProdAdd tr").eq(i).attr("style", 	"display:none");
				}
			}
		}

	}

	/* vic여부         vic카멧전용 */
	function _eventSetupMatcdVicOnlineCd(val) {
		var matCd	=	val;	//vic 여부
		if (matCd == "2") { // vic 여부가 vic마켓 전용일때
			$(":radio[name='vicOnlineCd']:radio[value='']").attr("checked", true);			//
			$("div.matchVicOnlinoption").show();
			$("div.nomatchVicOnlinoption").hide();
		} else {
			$("div.matchVicOnlinoption").hide();
			$("div.nomatchVicOnlinoption").show();
		}
	}
	/* NB, SB, NB2, 기타 유형만 신상품입점장려금, 신상품출시일자, 성과초과장려금적용구분 활성화 */
	function setupJangbodyopen() {
		var newProdPromoFg	=	$(":radio[name=newProdPromoFg]:checked").val();		 		//신상품입점 장려금 적용구분
		var newPromoVenFg = $("#newPromoVenFg").val(); // 업체 신상품장려금 지원 파트너사 여부  ('1',지원, '0'미지원)

		//-----신상품입점 장려금 적용구분, 신상품 출시일자, 성과초과장려금적용구분 활성화 하고 입력되어 있던 값이 있으면 초기화
		//$(":radio[name='newProdPromoFg']:radio[value='']").attr("checked", true);			//신상품입점장려금적용구분 [미적용 Default]신상품입점장려금적용 미적용으로 초기화

		// 2019.12.23 전산정보팀 이상구 추가.
		// 위 기본 미적용 초기화에서, HQ_VEN 테이블의 NEW_PROMO_FG 값에 따른 초기화 방식으로 변경한다. ('X' -> 적용)
		if(newPromoVenFg == '1'){
			$(":radio[name='newProdPromoFg']:radio[value='X']").attr("checked", true);
		}else{
			$(":radio[name='newProdPromoFg']:radio[value='']").attr("checked", true);
		}

		$("#newProdStDy").val("");

		$(":radio[name='overPromoFg']:radio[value='']").attr("checked", true);				//성과초과장려금적용구분 [미적용 Default]

		$("div.jangoption").show();
		$("div.nojangoption").hide();

	}

	//-----신상품입점장려금적용구분, 신상품 출시일자, 성과초과장려금 적용구분 사용안함으로 설정
	function setupJangbodyclose() {
		$("div.jangoption").hide();
		$("div.nojangoption").show();
	}

	//유통일 관리 여부
	function setDayForWarehouse(val) {
		$("input.flowDate").val('');

		if( val == '1' || val == '2' ) {
			$("div.flowDate").show();
			$("div.noFlowDate").hide();

			//유통일 입력란 필수입력 마크 show
			$("#flowDd").parent().parent().prev().find("span.star").show();

			//입고가능일, 출고가능일 필수입력 마크 show
			$("#stgePsbtDd").parent().parent().prev().find("span.star").show();
			$("#pickPsbtDd").parent().parent().prev().find("span.star").show();

			//필수입력 class 설정
			$("#flowDd").addClass("required");
			$("#stgePsbtDd").addClass("required");
			$("#pickPsbtDd").addClass("required");

		} else {
			$("div.flowDate").hide();
			$("div.noFlowDate").show();

			//유통일, 입고가능일, 출고가능일 필수입력 마크 해제
			$("#flowDd").parent().parent().prev().find("span.star").hide();
			$("#stgePsbtDd").parent().parent().prev().find("span.star").hide();
			$("#pickPsbtDd").parent().parent().prev().find("span.star").hide();

			//필수입력 문구 해제
			deleteErrorMessageIfExist($("#flowDd"));
			deleteErrorMessageIfExist($("#stgePsbtDd"));
			deleteErrorMessageIfExist($("#pickPsbtDd"));

			//필수입력 class 해제
			$("#flowDd").removeClass("required");
			$("#stgePsbtDd").removeClass("required");
			$("#pickPsbtDd").removeClass("required");
		}
	}

	//88코드 검증하는 첫번째 함수.
	//코드값 자체가 유효한지 검증하고 (isThisValidSell88Code),
	//유효하다면 이미 등록된 코드인지(isUniqueSell88Code) 체크함.
	//등록된 코드값은 다시 등록할수 없음.
	function validateSellCode(jquerySellCodeObj) {

		if( !isThisValidSell88Code(jquerySellCodeObj.get(0)) ) {
			showSellCodeValidateMessage(jquerySellCodeObj, 'invalid');
		} else {
			deleteErrorMessageIfExist(jquerySellCodeObj);
			isUniqueSell88Code(jquerySellCodeObj);
		}
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
		} else {
			$("#error_msg").text(fullErrorMessage);
		}
	}

	//사용자가 입력한 88코드가 이미 등록된 코드값인지 체크. 등록된 코드값을 입력했다면, 상품등록 안됨.
	function isUniqueSell88Code(userInputSellObj) {
		//var isUniqueCode = true;

		var paramInfo = {};
		paramInfo["sellCd"] = userInputSellObj.val();
		//console.log(paramInfo);

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectSellCdCount.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				if (data.sellCdCnt > 0) {
					showSellCodeValidateMessage(userInputSellObj, 'duplicated');
				} else {
					deleteErrorMessageIfExist(userInputSellObj);
				}
			}
		});
	}

	/* 전상법 셀렉트 콤보 변경시 */
	function selectBoxProdTemplateDetailList(infoGrpCd) {

		if (infoGrpCd.replace(/\s/gi, '') == "") {
			$("table[name=data_List] tr[name=titleProdAdd]").remove();
			return;
		}

		var paramInfo = {}

		paramInfo["infoGrpCd"]	=	infoGrpCd;
		paramInfo["pgmId"]		=	$("#hiddenForm input[name='pgmId']").val().replace(/\s/gi, '');

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectProdAddTemplateDetailList.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				var resultData	=	data.resultList;

				var tempList 	= '';     	//리스트 html
			    var tempTitle 	= '';    	//1:n 인지 비교하기 위한체크값

			  	//기존행 삭제
			    var rowLength = $("table[name=data_List] tr[name=titleProdAdd]").length;
		      	for(var i = 0 ; 0 < rowLength-i ; i++){
		      		$("#titleProdAdd_"+(rowLength-i-1)).remove();
		      	}

		      	//html 형태로 만들기
			    for (var i = 0; i < resultData.length; i++) {
			    	tempList += prodAddTempHtml(i,	resultData[i].orgCd,	resultData[i].orgNm,	resultData[i].orgDesc,	resultData[i].val);
			    	//console.log("tempList += prodAddTempHtml(i,	resultData[i].orgCd,	resultData[i].orgNm,	resultData[i].orgDesc,	resultData[i].val)");
			    }

				$("#prodAddMasterCd").val(infoGrpCd);
				$("table[name=data_List]").append(tempList);
			}
		});
	}

	function prodAddTempHtml(num, orgCd, orgNm,orgDsc, val){
		var returnHtml = "";
		returnHtml += '<tr id="titleProdAdd_'+num+'" name="titleProdAdd">';
		returnHtml += '<th style="font-weight:normal;font-size:12px;" align="left"><span class="star">*</span>&nbsp;'+orgNm+'<br></th>';
		returnHtml += '<td><input type="hidden" name="prodAddDetailCd_'+num+'" id="prodAddDetailCd_'+num+'" value="'+orgCd+'"/><font color=blue>'+orgDsc+'</font><br><input type="text" name="prodAddDetailNm_'+num+'" id="prodAddDetailNm_'+num+'" value="'+val+'"  style="line-height:10px;width:480px;"/></td>';
		returnHtml += '<input type="hidden" name="prodAddOrgNm_'+num+'" id="prodAddOrgNm_'+num+'" value="'+orgNm+'"/>';
		returnHtml += '</tr>';
	return returnHtml;
	}

	function prodCertTempHtml(num, orgCd, orgNm,orgDsc, orgCd2, val){
		//console.log("func:prodCertTempHtml / "+"num:"+num+", orgCd:"+orgCd+", orgNm:"+orgNm+", orgDsc:"+orgDsc+", orgCd2:"+orgCd2+", val:"+val);
		var returnHtml = "";
		returnHtml += '<tr id="titleProdCert_'+num+'" name="titleProdCert">';
		returnHtml += '<th style="font-weight:normal;font-size:12px;" align="left"><span class="star">*</span>&nbsp;'+orgNm+'<br></th>';
		returnHtml += '<td><input type="hidden" name="prodCertDetailCd_'+num+'" id="prodCertDetailCd_'+num+'" value="'+orgCd+'"/><font color=blue>'+orgDsc+'</font><br>';
		/* KC인증 콤보박스 변경시 이벤트 2017.04.26 코드변경 후*/
		//console.log("orgCd2:"+orgCd2);
		//console.log("orgCd:"+orgCd);
		if (orgCd2 == 'KC001'){ //해당사항 없음
			//console.log("KC001");
			returnHtml += '<input type="text" name="prodCertDetailNm_'+num+'" id="prodCertDetailNm_'+num+'" value="해당사항없음"  style="line-height:10px;width:480px;" disabled="disabled"/>';
		}else if ((orgCd2 == 'KC002' && (orgCd == '00003'||orgCd == '00006')) || (orgCd2 == 'KC003' && (orgCd == '00003'||orgCd == '00006'))){ //공급자적합성
			//console.log("공급자적합성");
			returnHtml += '<input type="text" name="prodCertDetailNm_'+num+'" id="prodCertDetailNm_'+num+'" value="KC 공급자적합성 확인대상"  style="line-height:10px;width:480px;" readonly="readonly"/>';
		}else{
			//console.log("etc");
			returnHtml += '<input type="text" name="prodCertDetailNm_'+num+'" id="prodCertDetailNm_'+num+'" value="' + val + '"  style="line-height:10px;width:480px;"/>';
		}
		returnHtml += '</td><input type="hidden" name="prodCertOrgNm_'+num+'" id="prodCertOrgNm_'+num+'" value="'+orgNm+'"/>';
		returnHtml += '</tr>';
	return returnHtml;
	}

	function prodCertTempHtml2(num, orgCd, orgNm,orgDsc, orgCd2, val){
		var returnHtml = "";
		returnHtml += '<tr id="titleProdCert_'+num+'" name="titleProdCert">';
		returnHtml += '<th style="font-weight:normal;font-size:12px;" align="left"><span class="star">*</span>&nbsp;'+orgNm+'<br></th>';
		returnHtml += '<td><input type="hidden" name="prodCertDetailCd_'+num+'" id="prodCertDetailCd_'+num+'" value="'+orgCd+'"/><font color=blue>'+orgDsc+'</font><br>';

		/* KC인증 콤보박스 변경시 이벤트 2017.04.26 코드변경 후*/
		if (orgCd2 == 'KC001'){ //해당사항 없음
			returnHtml += '<input type="text" name="prodCertDetailNm_'+num+'" id="prodCertDetailNm_'+num+'" value="해당사항없음"  style="line-height:10px;width:480px;" disabled="disabled"/>';
		}else if ((orgCd2 == 'KC002' && orgCd == '00003') || (orgCd2 == 'KC003' && orgCd == '00003')){ //공급자적합성
				returnHtml += '<input type="text" name="prodCertDetailNm_'+num+'" id="prodCertDetailNm_'+num+'" value="KC 공급자적합성 확인대상"  style="line-height:10px;width:480px;" disabled="disabled"/>';
		}else{
			returnHtml += '<input type="text" name="prodCertDetailNm_'+num+'" id="prodCertDetailNm_'+num+'" value="'+val+'"  style="line-height:10px;width:480px;"/>';
		}
		returnHtml += '</td><input type="hidden" name="prodCertOrgNm_'+num+'" id="prodCertOrgNm_'+num+'" value="'+orgNm+'"/>';
		returnHtml += '</tr>';
	return returnHtml;
	}


	function prodCertDtlHtml(num, orgCd, orgNm,orgDsc, orgCd2){
		//console.log("func:prodCertDtlHtml / num:"+num+", orgCd:"+orgCd+", orgNm:"+orgNm+", orgDsc:"+orgDsc+", orgCd2:"+orgCd2);
		var returnHtml = "";
		returnHtml += '<tr id="titleProdCert_'+num+'" name="titleProdCert">';
		returnHtml += '<th style="font-weight:normal;font-size:12px;" align="left"><span class="star">*</span>&nbsp;'+orgNm+'<br></th>';
		returnHtml += '<td><input type="hidden" name="prodCertDtlCd_'+num+'" id="prodCertAddDtlCd_'+num+'" value="'+orgCd+'"/><font color=blue>'+orgDsc+'</font><br><input type="text" name="prodCertDtlNm_'+num+'" id="prodCertDtlNm_'+num+'" value=""  style="line-height:10px;width:480px;"/></td>';
		returnHtml += '<input type="hidden" name="prodCertDtlNm_'+num+'" id="prodCertDtlNm_'+num+'" value="'+orgNm+'"/>';
		returnHtml += '</tr>';
		return returnHtml;
	}


	/* KC인증 콤보박스 변경시 이벤트 */
	function prodCertDtlHtml2(infoGrpCd) {
		//console.log("func:prodCertDtlHtml2 / infoGrpCd"+infoGrpCd);
		//console.log("콤보박스 변경이벤트1");
		if (infoGrpCd.replace(/\s/gi, '') == "") {
			$("table[name=cert_List] tr[name=titleProdCert]").remove();
			return;
		}

		var paramInfo	=	{};

		paramInfo["infoGrpCd"]	=	infoGrpCd;
		paramInfo["pgmId"]		=	$("#hiddenForm input[name='pgmId']").val().replace(/\s/gi, '');

		//console.log("ajax:/edi/product/selectProdCertTemplateDetailList.json");
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectProdCertTemplateDetailList.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				var resultData	=	data.resultList;

				var tempList 	= '';     	//리스트 html
			    var tempTitle 	= '';    	//1:n 인지 비교하기 위한체크값

			  	//기존행 삭제
			  	fnRemoveCert_List();

				//html 형태로 만들기
			    for (var i = 0; i < resultData.length; i++) {
			    	//20170518변경(스크립트오류)
			    	//console.log("20170518변경(스크립트오류)3");
			    	tempList += prodCertTempHtml(0,	resultData[i].orgCd,	resultData[i].orgNm,	resultData[i].orgDesc,		resultData[i].orgCd2,	resultData[i].val);
			    	//tempList += prodCertTempHtml(i,	resultData[i].orgCd,	resultData[i].orgNm,	resultData[i].orgDesc,		resultData[i].orgCd2,	resultData[i].val);
			    }

				$("#prodCertMasterCd").val(infoGrpCd);
				$("table[name=cert_List]").append(tempList);
			}
		});
	}

	/* KC인증 콤보박스 변경시 이벤트 */
	function selectBoxCertTemplateDetailList(infoGrpCd) {
		//console.log("func:selectBoxCertTemplateDetailList / infoGrpCd:"+infoGrpCd);

		if (infoGrpCd.replace(/\s/gi, '') == "") {
			$("table[name=cert_List] tr[name=titleProdCert]").remove();
			return;
		}

		var paramInfo	=	{};

		paramInfo["infoGrpCd"]	=	infoGrpCd;
		paramInfo["pgmId"]		=	$("#hiddenForm input[name='pgmId']").val().replace(/\s/gi, '');

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectProdCertTemplateDetailList.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				var resultData	=	data.resultList;

				var tempList 	= '';     	//리스트 html
			    var tempTitle 	= '';    	//1:n 인지 비교하기 위한체크값

			  	//기존행 삭제
			    var rowLength = $("table[name=cert_List] tr[name=titleProdCert]").length;

		      	for(var i = 0 ; 0 < rowLength-i ; i++){
		      		$("#titleProdCert_"+(rowLength-i-1)).remove();
		      	}

		      	//html 형태로 만들기
			    for (var i = 0; i < resultData.length; i++) {
			    	tempList += prodCertTempHtml(i,	resultData[i].orgCd,	resultData[i].orgNm,	resultData[i].orgDesc,		resultData[i].orgCd2,	resultData[i].val);
			    }

				$("#prodCertMasterCd").val(infoGrpCd);
				$("table[name=cert_List]").append(tempList);

				if (infoGrpCd == 'KC001'){
					//console.log("func:selectBoxCertTemplateDetailList에서 KC001이면 productCertSelectDtlTitle hide");
					$("#productCertSelectDtlTitle").hide();
				}
			}
		});
	}


	function selectBoxCertTemplateDetailList2(infoGrpCd2, infoGrpCd3) {
		//console.log("func:selectBoxCertTemplateDetailList2 / infoGrpCd2:"+infoGrpCd2+", infoGrpCd3:"+infoGrpCd3);

		var tempList_KC001 = '';     //리스트 html
		if (infoGrpCd2.replace(/\s/gi, '') == "") {
			$("table[name=cert_List] tr[name=titleProdCert]").remove();
			return;
		}
		else if (infoGrpCd2.replace(/\s/gi, '') == "KC001"){
			tempList_KC001 += prodCertTempHtml(0,infoGrpCd2,"해당사항없음","해당사항없음", infoGrpCd2);
			$("li[id=productCertDtlSelectBox]").hide();
			$("#prodCertMasterCd").val(infoGrpCd2);
			$("table[name=cert_List]").append(tempList_KC001);
			return;
		}

		$("ul[name=productCertTemplateTitle]").show();
		$("div[name=productCertTemplateData]").show();

		//기존행 삭제
		fnRemoveCert_List();

		var paramInfo	=	{};
		//console.log($("#hiddenForm input[name='pgmId']").val().replace(/\s/gi, ''));
		paramInfo["infoGrpCd"]	=	infoGrpCd2;
		paramInfo["infoColCd"]	=	infoGrpCd3;
		paramInfo["pgmId"]		=	$("#hiddenForm input[name='pgmId']").val().replace(/\s/gi, '');

		//console.log("ajax:/edi/product/selectProdCertTemplateDetailList.json");
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectProdCertTemplateDetailList.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				var resultData	=	data.resultList;
				//console.log("func:selectBoxCertTemplateDetailList2 내 resultData");

				//console.log(resultData);

		      	  //var tempList = '';     //리스트 html
			      var tempSelect	 	= '<option value=\'\'>선택</option>'; 	//1:n 일 경우 select
			      var tempTitle = '';    //1:n 인지 비교하기 위한체크값
			      var tempSave = infoGrpCd3; //저장된값인지 체크
			      var tempTitleCount 	= 0;   									//1:n 갯수

			      //기존행 삭제
				  fnRemoveCert_List();

			      //html 형태로 만들기
			      for (var i = 0; i < resultData.length; i++) {

						if(tempTitle != resultData[i].orgCd){
							//console.log("--------------여기여기");
							if(tempSave != resultData[i].orgCd){
								//console.log("---------------------안탄다");
								tempTitle = resultData[i].orgCd;
					    	  	tempSelect += '<option name="groupCertCode_'+i+'" value="'+resultData[i].orgCd+'" >'+resultData[i].orgNm+'</option>';

					    	  	$("#prodCertMasterCd").val(resultData[i].orgCd); //테스트

					    	  	tempTitleCount++;
					    	}
							else{
								//console.log("---------------------탄다");
								tempTitle = resultData[i].orgCd;
					    	  	tempSelect += '<option name="groupCertCode_'+i+'" value="'+resultData[i].orgCd+'" selected="selected">'+resultData[i].orgNm+'</option>';

					    	  	$("#prodCertMasterCd").val(resultData[i].orgCd); //테스트

					    	  	tempTitleCount++;
							}
						}

				    	  //console.log("i:"+i);
				    	 //console.log("resultData.length:"+resultData.length);
				    	  //console.log("tempTitle:"+tempTitle);
				    	 // //console.log("resultData[i].orgCd:"+resultData[i].orgCd);
				    	 // //console.log("tempSelect:"+tempSelect);
				    	 // //console.log("tempTitleCount:"+tempTitleCount);
			      }

					$("li[id=productCertDtlSelectBox]").show();
					if (infoGrpCd2 == 'KC001'){
						//console.log("func:selectBoxCertTemplateDetailList에서 KC001이면 productCertSelectDtlTitle hide");
						$("#productCertSelectDtlTitle").hide();
					}
					else{
						$("#productCertSelectDtlTitle").show();
					}
					$("#productCertSelectDtlTitle option").remove();
					$("#productCertSelectDtlTitle").html(tempSelect);
			}
		});
	}


	/* KC인증 콤보박스 변경시 이벤트 */
	function selectBoxCertTemplateDetailList3(infoGrpCd2, infoGrpCd3) {
		//console.log("func:selectBoxCertTemplateDetailList3 / infoGrpCd2:"+infoGrpCd2+", infoGrpCd3:"+infoGrpCd3);

		var tempList_KC001 = '';     //리스트 html
		if (infoGrpCd2.replace(/\s/gi, '') == "") {
			$("table[name=cert_List] tr[name=titleProdCert]").remove();
			return;
		}

		$("ul[name=productCertTemplateTitle]").show();
		$("div[name=productCertTemplateData]").show();

		var tempList = '';     //리스트 html
		//기존행 삭제
		fnRemoveCert_List();

		var paramInfo	=	{};
		//console.log($("#hiddenForm input[name='pgmId']").val().replace(/\s/gi, ''));
		paramInfo["infoGrpCd"]	=	infoGrpCd2;
		paramInfo["infoColCd"]	=	infoGrpCd3;
		paramInfo["pgmId"]		=	$("#hiddenForm input[name='pgmId']").val().replace(/\s/gi, '');

		//console.log("ajax:/edi/product/selectProdCertTemplateDetailList.json");
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectProdCertTemplateDetailList.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				var resultData	=	data.resultList;
				//console.log("func:selectBoxCertTemplateDetailList2 내 resultData");

				//console.log(resultData);

				var tempTitle = '';    //1:n 인지 비교하기 위한체크값

			      //html 형태로 만들기
			      for (var i = 0; i < resultData.length; i++) {

			    	 // //console.log("i:"+i);
			    	 // //console.log("resultData.length:"+resultData.length);
			    	 // //console.log("infoGrpCd3:"+infoGrpCd3);
			    	 // //console.log("resultData[i].orgCd:"+resultData[i].orgCd);

			    	  if (infoGrpCd3 == resultData[i].orgCd) {
				    	  	tempList += prodCertTempHtml2(0,resultData[i].orgCd,resultData[i].orgNm,resultData[i].orgDesc, resultData[i].orgCd2, resultData[i].val);
				    	  	//console.log("tempList:"+tempList);
				    	  	break;
			    	  }

			      }
			     // //console.log("tempList:"+tempList);
				  $("#prodCertMasterCd").val(infoGrpCd2);
				  $("#prodCertDtlCd").val(infoGrpCd3);
				  $("table[name=cert_List]").append(tempList);
				 // //console.log("selectBoxCertTemplateDetailList3 실행 끝");
			}
		});
	}

	//대분류 코드를 받았을때 전자 상거래 템플릿 셋팅
	function setupLcdDivnCodeProdAdd(lcd,flag, infoGrpCd) {
		//console.log("func:setupLcdDivnCodeProdAdd / lcd:"+lcd+", flag:"+flag+", infoGrpCd:"+infoGrpCd);
		if(lcd == null || lcd == ''){
			$("ul[name=productAddTemplateTitle]").hide();
			$("div[name=productAddTemplateData]").hide();
		}else{
			$("ul[name=productAddTemplateTitle]").show();
			$("div[name=productAddTemplateData]").show();

			selectProdTemplateList(lcd,flag, infoGrpCd);
		}
	}

	//flag 1값은 온오프 등록, 2값은 온라인 등록, 전자상거래 등록
	function selectProdTemplateList(l1Cd,flag, infoGrpCd) {
		//console.log("func:selectProdTemplateList / l1Cd:"+l1Cd+", flag:"+flag+", infoGrpCd:"+infoGrpCd);
		var paramInfo	=	{};

		paramInfo["catCd"]		=	l1Cd;					//현재 테스트데이터로 진행되고 있음 테이블 컬럼 정의되면 l1Cd로 교체;
		paramInfo["flag"]		=	flag;
		paramInfo["infoGrpCd"]	=	infoGrpCd;

		//console.log("ajax:/edi/product/selectProdAddTemplateList.json");

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectProdAddTemplateList.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {

				var resultData	=	data.resultList;

				var tempList 		= '';     //리스트 html
			    var tempSelect 		= '<option value=\'\'>선택</option>';  //1:n 일 경우 select
			    var tempTitle 		= '';    //1:n 인지 비교하기 위한체크값
			    var tempTitleCount = 0;   //1:n 갯수

			      //기존행 삭제
			      var rowLength = $("table[name=data_List] tr[name=titleProdAdd]").length;
			      for(var i = 0 ; 0 < rowLength-i ; i++){
			      	$("#titleProdAdd_"+(rowLength-i-1)).remove();
			      }

			      //html 형태로 만들기
			      for (var i = 0; i < resultData.length; i++) {

			    	  if(tempTitle != resultData[i].orgCd2) {
			    		tempTitle = resultData[i].orgCd2;
			    	  	tempSelect += '<option name="groupCode_'+i+'" value="' + resultData[i].orgCd2 + '" >' + resultData[i].orgNm2+'</option>';

			    	  	$("#prodAddMasterCd").val(resultData[i].orgCd2);

			    	  	tempTitleCount++;
			    	  }

			    	  tempList += prodAddTempHtml(i,	resultData[i].orgCd,	resultData[i].orgNm);
			    	  //console.log("tempList += prodAddTempHtml(i,	resultData[i].orgCd,	resultData[i].orgNm)");
			      }

			      $("li[id=productAddSelectBox]").show();
				  $("#productAddSelectTitle option").remove();
				  $("#productAddSelectTitle").html(tempSelect);

				  //상품등록이후 등록된 전상법 코드가 있을경우 셋팅
				  if (infoGrpCd != "") {
					  $("#productAddSelectTitle").val(infoGrpCd);

					  //----전상법 코드의 상세 리스트 조회해서 셋팅
					  selectBoxProdTemplateDetailList(infoGrpCd);
				  }

			}
		});
	}

	//대분류 코드를 받았을때 KC인증마크 템플릿 셋팅
	function setupLcdDivnCodeCertAdd(lcd,flag, infoGrpCd2) {
		//console.log("func:setupLcdDivnCodeCertAdd / lcd:"+lcd+", flag:"+flag+", infoGrpCd2:"+infoGrpCd2);
		if(lcd == null || lcd == ''){
			$("li[id=productCertDtlSelectBox]").hide();
			$("ul[name=productCertTemplateTitle]").hide();
			$("div[name=productCertTemplateData]").hide();
		}else{
			$("ul[name=productCertTemplateTitle]").show();
			$("div[name=productCertTemplateData]").show();

			//console.log("selectCertTemplateList 호출!!!");
			selectCertTemplateList(lcd,flag, infoGrpCd2);
		}
	}

	//대분류 코드를 받았을때 KC인증마크 템플릿 셋팅 20170518 상세kc인증코드 추가
	function setupLcdDivnCodeCertAdd2 (lcd,flag, infoGrpCd2, infoGrpCd3) {
		//console.log("func:setupLcdDivnCodeCertAdd2 / lcd:"+lcd+", flag:"+flag+", infoGrpCd2:"+infoGrpCd2+", infoGrpCd3:"+infoGrpCd3);
		if(lcd == null || lcd == ''){
			$("li[id=productCertDtlSelectBox]").hide();
			$("ul[name=productCertTemplateTitle]").hide();
			$("div[name=productCertTemplateData]").hide();
		}else{
			$("li[id=productCertDtlSelectBox]").hide();
			$("ul[name=productCertTemplateTitle]").show();
			$("div[name=productCertTemplateData]").show();

			//console.log("selectCertTemplateList2 호출!!!");
			//console.log("selectCertTemplateList2 infoGrpCd3:"+infoGrpCd3);
			selectCertTemplateList2(lcd,flag, infoGrpCd2, infoGrpCd3);
			selectBoxCertTemplateDetailList3(infoGrpCd2, infoGrpCd3);
		}
	}


	//flag 1값은 온오프 등록, 2값은 온라인 등록, 전자상거래 등록
	function selectCertTemplateList(l1Cd,flag, infoGrpCd2) {
		//console.log("func:selectCertTemplateList / l1Cd:"+l1Cd+", flag:"+flag+", infoGrpCd2:"+infoGrpCd2);
		var paramInfo = {};

		paramInfo["catCd"]		=	l1Cd;				//현재 테스트데이터로 진행되고 있음 테이블 컬럼 정의되면 l1Cd로 교체;
		paramInfo["flag"]		=	flag;
		paramInfo["infoGrpCd"]	=	infoGrpCd2;

		//console.log("ajax:/edi/product/selectProdCertTemplateList.json");
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectProdCertTemplateList.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {

				var resultData	=	data.resultList;

				var tempList 		= '';     								//리스트 html
			    var tempSelect	 	= '<option value=\'\'>선택</option>'; 	//1:n 일 경우 select
			    var tempTitle 		= '';    								//1:n 인지 비교하기 위한체크값
			    var tempTitleCount 	= 0;   									//1:n 갯수

			   //기존행 삭제
			    fnRemoveCert_List();

			  	//html 형태로 만들기
			      for (var i = 0; i < resultData.length; i++) {

			    	  if(tempTitle != resultData[i].orgCd2){
			    		tempTitle = resultData[i].orgCd2;
			    	  	tempSelect += '<option name="groupCertCode_'+i+'" value="'+resultData[i].orgCd2+'" >'+resultData[i].orgNm2+'</option>';

			    	  	$("#prodCertMasterCd").val(resultData[i].orgCd2);

			    	  	tempTitleCount++;
			    	  }

			    	  //20170518변경(스크립트오류)
			    	 // //console.log("20170518변경(스크립트오류)1");
			    	  tempList += prodCertTempHtml(0,	resultData[i].orgCd,	resultData[i].orgNm, 	resultData[i].orgCd2);
			    	  //tempList += prodCertTempHtml(i,	resultData[i].orgCd,	resultData[i].orgNm, 	resultData[i].orgCd2);
			      }

			      $("li[id=productCertSelectBox]").show();
				  $("#productCertSelectTitle option").remove();
				  //console.log("#1");
				  $("#productCertSelectTitle").html(tempSelect);

				  //---- 상품 등록후 상세보기 페이지에서
				  if (infoGrpCd2 != "") {
					  $("#productCertSelectTitle").val(infoGrpCd2);

					  selectBoxCertTemplateDetailList(infoGrpCd2);

				  }
			}
		});
	}

	//flag 1값은 온오프 등록, 2값은 온라인 등록, 전자상거래 등록
	function selectCertTemplateList2(l1Cd,flag, infoGrpCd2, infoGrpCd3) {
		//console.log("func:selectCertTemplateList2 / l1Cd:"+l1Cd+", flag:"+flag+", infoGrpCd2:"+infoGrpCd2+", infoGrpCd3:"+infoGrpCd3);
		var paramInfo = {};

		paramInfo["catCd"]		=	l1Cd;				//현재 테스트데이터로 진행되고 있음 테이블 컬럼 정의되면 l1Cd로 교체;
		paramInfo["flag"]		=	flag;
		paramInfo["infoGrpCd"]	=	infoGrpCd2;
		paramInfo["infoColCd"]	=	infoGrpCd3;

		//console.log("ajax:/edi/product/selectProdCertTemplateList.json");
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectProdCertTemplateList.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {

				var resultData	=	data.resultList;

				var tempList 		= '';     								//리스트 html
			    var tempSelect	 	= '<option value=\'\'>선택</option>'; 	//1:n 일 경우 select
			    var tempSelectL2	= '<option value=\'\'>선택</option>'; 	//1:n 일 경우 select
			    var tempTitle 		= '';    								//1:n 인지 비교하기 위한체크값
			    var tempTitleL2 	= '';    								//1:n 인지 비교하기 위한체크값
			    var tempTitleCount 	= 0;   									//1:n 갯수

			   //기존행 삭제
			    fnRemoveCert_List();
			   // //console.log("-------------------------------------------");
			  	//html 형태로 만들기
			      for (var i = 0; i < resultData.length; i++) {
			    	 // //console.log("i:"+i);
			    	 // //console.log("tempTitle:"+tempTitle);
			    	 // //console.log("resultData[i].orgCd2:"+resultData[i].orgCd2);
			    	  if(tempTitle != resultData[i].orgCd2){

			    		 tempTitle = resultData[i].orgCd2;
			    	  	tempSelect += '<option name="groupCertCode_'+i+'" value="'+resultData[i].orgCd2+'" >'+resultData[i].orgNm2+'</option>';
				    	//console.log("tempSelect:"+tempSelect);
			    	  	$("#prodCertMasterCd").val(resultData[i].orgCd2); //테스트

			    	  }

			    	  //2017.05.18 추가 Option 값
			    	 // //console.log("tempTitleL2:"+tempTitleL2);
			    	 // //console.log("resultData[i].orgCd:"+resultData[i].orgCd);
			    	  if(tempTitleL2 != resultData[i].orgCd){
				    		tempTitleL2 = resultData[i].orgCd;
				    	  	tempSelectL2 += '<option name="groupCertCode_'+i+'" value="'+resultData[i].orgCd+'" >'+resultData[i].orgNm+'</option>';
					    	//console.log("tempSelectL2:"+tempSelectL2);

					    	$("#prodCertDtlCd").val(resultData[i].orgCd);
				      }

				      $("li[id=productCertSelectBox]").show();
				      $("li[id=productCertDtlSelectBox]").show();
					  $("#productCertSelectTitle option").remove();
					  $("#productCertSelectDtlTitle option").remove();
					  //console.log("#2");
					  //console.log(tempSelect);
					  $("#productCertSelectTitle").html(tempSelect);
					  $("#productCertSelectDtlTitle").html(tempSelectL2);

		      }

			      $("li[id=productCertSelectBox]").show();
				  $("#productCertSelectTitle option").remove();
				  //console.log("#3");
				  //console.log(tempSelect);
				  $("#productCertSelectTitle").html(tempSelect);

				  //---- 상품 등록후 상세보기 페이지에서
				  if (infoGrpCd2 != "" && infoGrpCd3 != "") {
					  //console.log("#4");
					  //console.log(infoGrpCd2);
					  $("#productCertSelectTitle").val(infoGrpCd2);
					  $("#productCertSelectDtlTitle").val(infoGrpCd3);

					 //2017.05.18 변경
					// //console.log("selectCertTemplateList에서 selectBoxCertTemplateDetailList2 호출");
					  selectBoxCertTemplateDetailList2(infoGrpCd2, infoGrpCd3);
				  }
			}
		});
	}

	function getNumberMessageWithArgu(argu) {
		return argu+"<spring:message code="msg.common.error.notNum" />".replace("{0}", "");
	}

	function getTextMessageWithArgu(argu) {
		return argu+"<spring:message code="msg.common.error.required" />".replace("{0}", "");
	}

	/* 대분류 변경시 중분류 호출 이벤트 */
	function _eventSelectL2List(groupCode, val) {
		var paramInfo = {};

		paramInfo["groupCode"]	=	groupCode;		//팀코드
		paramInfo["l1Cd"]		=	val;			//대븐류

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/NselectL2List.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				var resultList	=	data.l2List;
				if (resultList.length > 0) {

					var eleHtml = [];
					for (var i = 0; i < resultList.length; i++) {
						eleHtml[i] = "<option value='"+resultList[i].l2Cd+"'>"+resultList[i].l2Nm+"</option>"+"\n";
					}

					$("#l2Cd option").not("[value='']").remove();	//콤보박스 초기화
					$("#l2Cd").append(eleHtml.join(''));
				} else {
					$("#l2Cd option").not("[value='']").remove();	//콤보박스 초기화
				}
			}
		});
	}

	//브랜드 선택팝업
	function openBrandPopup() {
		openDetail("<c:url value='/edi/product/brand.do'/>")
	}

	//메이커 선택팝업
	function openMakerPopup() {
		openDetail("<c:url value='/edi/product/maker.do'/>")
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


	function _eventSetupJangCheck() {
		var tradeType		=	$("#newProduct input[name='tradeType']").val().replace(/\s/gi, '');				//거래형태
		var prodTypeDivnCd	=	$("#newProduct select[name='prodTypeDivnCd']").val().replace(/\s/gi, '');		//상품유형

		//-----직매입이면
		if (tradeType == "1") {
			//무조건 빈값
			//상품유형 (1 NB,3 SB,5 NB2,9 기타)
			if (prodTypeDivnCd == "1" || prodTypeDivnCd == "3"	||	prodTypeDivnCd == "5"	||	prodTypeDivnCd == "9") {

			} else {
				//무조건 빈값
				$("input:radio[name='newProdPromoFg']").removeAttr("checked");	//신상품입점장려금적용 미체크
				$("#newProduct input[name=newProdStDy]").val('');				//신상품 출시일자
				$("input:radio[name='overPromoFg']").removeAttr("checked");		//성과초과장려금 적용구분 미체크

			}
		} else {
			$("input:radio[name='newProdPromoFg']").removeAttr("checked");		//신상품입점장려금적용 미체크
			$("#newProduct input[name=newProdStDy]").val('');					//신상품 출시일자
			$("input:radio[name='overPromoFg']").removeAttr("checked");			//성과초과장려금 적용구분 미체크
		}

		var newProdPromoFg	=	$("input:radio[name='newProdPromoFg']:checked").val();
		var newProdStDy		=	$("#newProduct input[name=newProdStDy]").val().replace(/\s/gi, '');
		var overPromoFg 	= 	$("input:radio[name='overPromoFg']:checked").val();

		if (newProdPromoFg == "X") {
			if (newProdStDy == "") {
				alert("※신상품 출시일자를 넣으셔야 합니다. \n\n 신상품 출시일자는 KAN(88)코드 등록일자를 넣으셔야 합니다.");
				return false;
			}
		} else {
			$("#newProduct input[name=newProdStDy]").val("");
		}
		return true;
	}


	//전자상거래 검증
	function _eventProdAddValidationCheck() {

		var prodAddLength = $("table[name=data_List] tr[name=titleProdAdd]").length;
		var tempProdAddCd = "";
		var tempProdAddNm = "";


		if($("select[name=productAddSelectTitle]").val().replace(/\s/gi, '') == ''){
			alert("전자 상거래 Select 박스를 선택하세요.");
			$("select[name=productAddSelectTitle]").focus();
			return false;
		}

		for(var i = 0 ; i < prodAddLength; i++){
	 		if(! calByteProdjava($("#prodAddDetailNm_"+i).val(),2000,"전상법 속성 중 "+$("#prodAddOrgNm_"+i).val(),false) ) return;

	 		if('' == $("#prodAddDetailNm_"+i).val().replace(/\s/gi, '')){
				alert("전자 상거래 필드는 필수 입력값입니다!");
				$("#prodAddDetailNm_"+i).focus();
				return false;
			}


		}

		return true;
	}

    function _validKcCertChmStry() {
        const selectedKcCode = $("select[name=productCertSelectTitle]").val().replace(/\s/gi, '');
        // KC004: 생활화학제품, KC011: 살생물제품
        if (selectedKcCode !== 'KC004' && selectedKcCode !== 'KC011') return true;

        let formData = new FormData();
        formData.append('certInfoCode', $("#prodCertDetailNm_0").val());
        formData.append('certInfoType', '');

        const formDataStr = new URLSearchParams(formData).toString();

        let xhr = new XMLHttpRequest();
        xhr.open('Post', '<c:url value="/product/certInfoCheck.do"/>', false);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send(formDataStr);

        if (xhr.status === 200) {
            try {
                const responseData = JSON.parse(xhr.responseText);
                if (responseData.returnCode === '9999') {
                    alert("안전인증번호가 유효하지 않습니다. 유효한 번호를 입력해주세요.");
                    return false;
                }
                if (responseData.returnCode === '8888') {
                    alert("안전인증번호 검증 중 통신이 실패하였습니다. 관리자에게 문의해주세요.");
                    return false;
                }
            } catch (e) {
                alert("안전인증번호 검증 중 통신이 실패하였습니다. 관리자에게 문의해주세요. [E]");
                console.log(e.msg);
                return false;
            }
        } else {
            alert('요청이 올바르지 않습니다.');
            return false;
        }

        return true;
    }

	//KC 인증마크
	function _eventProdCertValidationCheck() {
		//console.log("func:_eventProdCertValidationCheck");
		var prodCertLength = $("table[name=cert_List] tr[name=titleProdCert]").length;
		var tempProdCertCd = "";
		var tempProdCertNm = "";


		if($("select[name=productCertSelectTitle]").val().replace(/\s/gi, '') == ''){
			alert("제품안전인증 Select 박스를 선택하세요.");
			$("select[name=productCertSelectTitle]").focus();
			return false;
		}


		if($($("select[name=productCertSelectTitle]").val().replace(/\s/gi, '') != 'KC001' && "select[name=productCertSelectDtlTitle]").val() == ''){
			alert("제품안전인증 상세 Select 박스를 선택하세요.\n상세박스가 보이지 않을 경우 대분류부터 다시 선택바랍니다.");
			//$("select[name=productCertSelectTitle]").focus();
			return false;
		}

		for(var i = 0 ; i <3; i++){
			if($("select[name=productCertSelectTitle]").val() != 'KC001' && '' == $("#prodCertDetailNm_"+i).val()){
				alert("제품안전인증 필드는 필수 입력값입니다!");
				$("#prodCertDetailNm_"+i).focus();
				return false;
			}
		}

        if (!_validKcCertChmStry()) return false;

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

	/* 해당년도의 계절리스트 조회 */
	function _eventSelectSesnList(sesnYearCd) {
		var paramInfo	=	{};
		paramInfo["sesnYearCd"]	=	sesnYearCd;

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/NselectSeasonList.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				var resultList	=	data.seasonList;
				if (resultList.length > 0) {

					var eleHtml = [];
					for (var i = 0; i < resultList.length; i++) {
						eleHtml[i] = "<option value='"+resultList[i].styleCd+"'>"+resultList[i].styleNm+"</option>"+"\n";
					}

					$("#sesnDivnCd option").not("[value='']").remove();	//콤보박스 초기화
					$("#sesnDivnCd").append(eleHtml.join(''));
				} else {
					$("#sesnDivnCd option").not("[value='']").remove();	//콤보박스 초기화
				}
			}
		});
	}


	/* 상품구분 변경시 이벤트 [0:신선비규격, 1:규격, 5:패션] */
	function _eventSetupFieldByProductDivnCode(val) {
		 var productDivision = val;			//[1:규격, 5:패션]
		 var currentChecked = $("input[name=onOffDivnCd]").val();

		 //규격상품을 선택한 경우,
		 if(productDivision == "1" || productDivision == "0" ) {


			//도난방지 태그 유형값을 필수(required)에서
			//선택(required class값 제거)항목으로 변경.
			if( $("#protectTagTypeCd").hasClass("required") ) {
				$("#protectTagTypeCd").removeClass("required");
				//필수항목표시(*)제거
				$("#protectTagTypeCd").parent().prev().find("span.star").hide();

				//필수입력 문구 해제
				deleteErrorMessageIfExist($("#protectTagTypeCd"));
			}

		//패션상품으로 선택한 경우,
		} else {

			//도난 방지 태그 유형 필수 항목으로 변경
			if( !$("#protectTagTypeCd").hasClass("required") ) {
				$("#protectTagTypeCd").addClass("required");
				$("#protectTagTypeCd").parent().prev().find("span.star").show();
			}

		 }

		//----- 신선비규격일경우 상품사이즈 필수 및 미필수 변환(2016.09.29 추가) ----------
		if (productDivision == "0") {
    		$("#prodHrznLeng").parent().parent().prev().find("span.star").hide();

    		//필수입력 문구 해제
			deleteErrorMessageIfExist($("#prodHrznLeng"));

			$("input[name='prodHrznLeng'], input[name='prodVtclLeng'], input[name='prodHigt']").off('blur');

    		$("input[name='prodHrznLeng']").removeClass("required number size");
    		$("input[name='prodVtclLeng']").removeClass("required number size");
    		$("input[name='prodHigt']").removeClass("required number size");
    	} else {
    		$("#prodHrznLeng").parent().parent().prev().find("span.star").show();

    		$("input[name='prodHrznLeng'], input[name='prodVtclLeng'], input[name='prodHigt']").on('blur', function(){
    			if( !$(this).attr("readonly"))
					validateFormTextField($(this));
    		});

    		$("input[name='prodHrznLeng']").addClass("required number size");
    		$("input[name='prodVtclLeng']").addClass("required number size");
    		$("input[name='prodHigt']").addClass("required number size");
    	}
		//--------------------------------------------------
	}
	/* 제품안전인증 입력항목 삭제 함수 */
	function fnRemoveCert_List(){
		//console.log("func:fnRemoveCert_List");
      //기존행 삭제
      var rowLength = $("table[name=cert_List] tr[name=titleProdCert]").length;

      for(var i=0;i<3;i++){
    	//console.log("titleProdCert_"+i+" 삭제!");
      	$("#titleProdCert_"+(i)).remove();
      }
	}

	/* [200326 EDI 상품 범주 및 속성 추가] START */
	/*
	function onlyNumber(event) { // 8 백스페이스 , 9 탭 , 37 왼쪽이동, 39 오른쪽이동, 46 delete
		var key;
		if(event.which) { // ie9 firefox chrome opera safari
			key = event.which;
		} else if(window.event) {  // ie8 and old
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

	//신규상품추가 단품 정보 row 삭제'
	function fnNewItemDelete(idx){
		if(idx > 1){
			$("#deleteNewItem"+(idx-1)).attr("style", "display:done");
		}
		$('#row' + idx).remove();
		$("#itemRow").val(parseInt($("#itemRow").val())-1);
	}


	function fnItemDelete(idx, itemCd){
		if(!confirm("["+itemCd+"] 단품 정보를 삭제하시겠습니까?")){
			return;
		}

		var paramInfo = {};
		paramInfo["itemCode"]	=	itemCd;
		paramInfo["newProductCode"]	=	"<c:out value='${newProdDetailInfo.pgmId}'/>";

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
					//
					alert("<spring:message code='msg.common.fail.delete'/>")
				}


			}
		});

	 var param = "itemCode="+itemCd+"&newProductCode=${newProdDetailInfo.pgmId}";
		$.ajax({
			//url     : '${ctx}/edi/product/PEDMPRO000305.do',
			url		: '<c:url value="/edi/product/PEDMPRO000305.do"/>',
			type 	: "POST",
			data    : param,
			error   : function(xhr,status, error){
				console.log("xhr ==" + xhr);
				console.log("status ==" + status);
				console.log("error ==" + error);

				//fnNewItemDelete(idx);
			}
		}); 
	}
	*/
	
	/* [200326 EDI 상품 범주 및 속성 추가] END */
</script>
