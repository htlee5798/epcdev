<%--
	Page Name 	: CommonProductExtendFunction.jsp
	Description : 상품채널확장
	Modification Information

	  수정일 			  수정자 			수정내용
	---------- 		---------    	-------------------------------------
	
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
		var selectedObj 	= 	$("select[name='venCd']");
		var errorNode 		= 	selectedObj.prev("div[name=error_msg]").length;
		var j 				= 	data.VendorStopTradingCnt;		//거래중지여부
		var tradeType 		= 	data.tradeTypeCd;				//거래유형
		var taxDivnCode 	= 	data.taxDivnCode;				//과세구분

		$("#tradeType").val(tradeType);
		
		var resultCount = parseInt(j);
		
		if( resultCount > 0 ) {
		    if(errorNode == 0) {
				var message = "거래가 중지된 협력업체 입니다.";
				generateMessage(selectedObj, message);
		    }

		} else {
		    deleteErrorMessageIfExist(selectedObj);
		}

		//원가, 이익률영역 초기화
		 //마트
		 deleteErrorMessageIfExist($("input[name=norProdPcost]"));
    	 deleteErrorMessageIfExist($("input[name=prftRate]"));
    	 $("input[name=norProdPcost]").val("");
    	 $("input[name=prftRate]").val("");

		 //MAXX
    	 deleteErrorMessageIfExist($("input[name=wnorProdSalePrc]"));
    	 deleteErrorMessageIfExist($("input[name=wprftRate]"));
    	 $("input[name=wnorProdPcost]").val("");
    	 $("input[name=wprftRate]").val("");
    	 
    	 //슈퍼
    	 deleteErrorMessageIfExist($("input[name=snorProdSalePrc]"));
    	 deleteErrorMessageIfExist($("input[name=sprftRate]"));
    	 $("input[name=snorProdPcost]").val("");
    	 $("input[name=sprftRate]").val("");
    	 
    	 //오카도
    	 deleteErrorMessageIfExist($("input[name=onorProdSalePrc]"));
    	 deleteErrorMessageIfExist($("input[name=oprftRate]"));
    	 $("input[name=onorProdPcost]").val("");
    	 $("input[name=oprftRate]").val("");
   	 
		//거래 유형에 따른 이익률, 정상원가 필드 상태변경
		_eventChangeFiledByTradeType(tradeType);
		
		//신상품입점 장려금 적용구분, 신상품 출시일자, 성과초과장려금적용구분 화성화  / 비활성화 함수 호출
		let prodTypFg = $.trim($("#prodTypFg").val());
		_eventSetupJang(prodTypFg);
	}

	//거래 유형에 따라 이익률, 정상원가 입력필드 상태 변경.
	function _eventChangeFiledByTradeType(tradeType) {
		
		var chanCd = "";	//채널코드

		//선택되지 않은 채널코드 필수체크관련 클래스제거
		$("#extendInfo input[name='extChanCd']").not(":checked").each(function(){
			chanCd = $.trim($(this).val());
			$("#extendChanInfo tr[data-chan="+chanCd+"]").find("input, select").removeClass("required inputRead");
		});
		
		
		//선택된 채널에 대해서 필수체크 영역 컨트롤
		$("#extendInfo input[name='extChanCd']:checked").each(function(){
			chanCd = $.trim($(this).val());
			//선택된 채널에 대한 원매가 영역별 컨트롤
			$("#extendChanInfo tr[data-chan="+chanCd+"]").find("input, select").each(function(){
				if(this.name == undefined || this.name == null || this.name == "") return;
				switch(tradeType){
					case "2":	//특약1 - 정상원가 입력불가 (이익률과 매가로 산출)
					case "4":	//특약2 - 정상원가 입력불가 (이익률과 매가로 산출)
						//이익률 필수입력
						if(this.name.endsWith("prftRate")){
							$(this).attr("readonly", false);
							$(this).addClass("required").removeClass("inputRead");
							$(this).parent().prev().find("span.star").show();
						}else if(this.name.endsWith("norProdPcost")){
						//원가입력불가 (이익률, 매가로 산출)
							$(this).attr("readonly", true);
					    	$(this).removeClass("required").addClass("inputRead");
					    	$(this).parent().parent().prev().find("span.star").hide();
						}
						break;
					case "1":	//직매입 - 이익률 입력불가 (매가, 원가, 면과세 구분을 가지고 이익률이 산출됨.)
					default:
						//이익률입력불가 (매가, 원가, 면과세 구분을 가지고 이익률이 산출됨.)
						if(this.name.endsWith("prftRate")){
							$(this).attr("readonly", true);
					    	$(this).removeClass("required").addClass("inputRead");
					    	$(this).parent().prev().find("span.star").hide();
						}else if(this.name.endsWith("norProdPcost")){
						//원가필수입력
							$(this).attr("readonly", false);
							$(this).addClass("required").removeClass("inputRead");
							$(this).parent().parent().prev().find("span.star").show();
						}
						break;
				}
				
				//원가 또는 이익률 필드가 아닐 경우, 모든 영역 필수입력
				if(!this.name.endsWith("prftRate") && !this.name.endsWith("norProdPcost")){
					$(this).addClass("required");
				}
				
			});
		});
	}
	
	/* 장려금영역 활성화/비활성화 */
	function _eventSetupJang(val) {
		var tradeType	=	$("input[name=tradeType]").val().replace(/\s/gi, '');	//거래형태
			
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
	}
	
	/* NB, SB, NB2, 기타 유형만 신상품입점장려금, 신상품출시일자, 성과초과장려금적용구분 활성화 */
	function setupJangbodyopen() {
		var newProdPromoFg	=	$(":radio[name=newProdPromoFg]:checked").val();		 		//신상품입점 장려금 적용구분
		var newPromoVenFg 	= $("#newPromoVenFg").val(); 									// 업체 신상품장려금 지원 파트너사 여부  ('1',지원, '0'미지원)
		
		//-----신상품입점 장려금 적용구분, 신상품 출시일자, 성과초과장려금적용구분 활성화 하고 입력되어 있던 값이 있으면 초기화
		//$(":radio[name='newProdPromoFg']:radio[value='']").attr("checked", true);			//신상품입점장려금적용구분 [미적용 Default]신상품입점장려금적용 미적용으로 초기화

		// 2019.12.23 전산정보팀 이상구 추가.
		// 위 기본 미적용 초기화에서, HQ_VEN 테이블의 NEW_PROMO_FG 값에 따른 초기화 방식으로 변경한다. ('X' -> 적용)
		if(newPromoVenFg == '1'){
			$(":radio[name='newProdPromoFg']:radio[value='X']").attr("checked", true);
			$(":radio[name='sNewProdPromoFg']:radio[value='X']").attr("checked", true);	//(슈퍼) 신상품입점 장려금적용구분
			fncSetsSNewProdStDy();			//(슈퍼) 신상품출시일자 기본값 셋팅
		}else{
			$(":radio[name='newProdPromoFg']:radio[value='']").attr("checked", true);
			$(":radio[name='sNewProdPromoFg']:radio[value='']").attr("checked", true); //(슈퍼) 신상품입점 장려금적용구분
			$("#sNewProdStDy").val("");		//(슈퍼) 신상품출시일자 빈값적용
		}

		$("#newProdStDy").val("");

		$(":radio[name='overPromoFg']:radio[value='']").attr("checked", true);				//성과초과장려금적용구분 [미적용 Default]
		$(":radio[name='sOverPromoFg']:radio[value='']").attr("checked", true);				//(슈퍼) 성과초과장려금적용구분 [미적용 Default]
		
		//장려금 영역 활성화
		$("div.jangoption").show();
		$("div.nojangoption").hide();

	}

	//-----신상품입점장려금적용구분, 신상품 출시일자, 성과초과장려금 적용구분 사용안함으로 설정
	function setupJangbodyclose() {
		$("div.jangoption").hide();
		$("div.nojangoption").show();
	}
	
	//장려금 체크
	function _eventSetupJangCheck(evntGbn) {
		var tradeType		=	$("#hiddenForm #tradeType").val().replace(/\s/gi, '');				//거래형태
		var prodTypeDivnCd	=	$("#hiddenForm #prodTypFg").val().replace(/\s/gi, '');				//상품유형
		var isSuper = $.trim($("#hiddenForm input[name='extChanCd']").val()) == "KR04"?true:false;	//채널 슈퍼인지 확인

		/********** 1) 거래형태, 상품유형 별 체크 >> 장려금대상 해당되지 않는 경우 빈값으로 초기화 ****************/
		//-----직매입이면
		if (tradeType == "1") {
			//무조건 빈값
			//상품유형 (1 NB,3 SB,5 NB2,9 기타)
			if (prodTypeDivnCd == "1" || prodTypeDivnCd == "3"	||	prodTypeDivnCd == "5"	||	prodTypeDivnCd == "9") {

			} else {
				//무조건 빈값
				$("input:radio[name='newProdPromoFg']").removeAttr("checked");	//신상품입점장려금적용 미체크
				$("#extendInfo input[name=newProdStDy]").val('');				//신상품 출시일자
				$("input:radio[name='overPromoFg']").removeAttr("checked");		//성과초과장려금 적용구분 미체크
				
				$("input:radio[name='sNewProdPromoFg']").removeAttr("checked");	//(슈퍼) 신상품입점장려금적용 미체크
				$("#extendInfo input[name=sNewProdStDy]").val('');				//(슈퍼) 신상품 출시일자
				$("input:radio[name='sOverPromoFg']").removeAttr("checked");	//(슈퍼) 성과초과장려금 적용구분 미체크

			}
		} else {
			$("input:radio[name='newProdPromoFg']").removeAttr("checked");		//신상품입점장려금적용 미체크
			$("#extendInfo input[name=newProdStDy]").val('');					//신상품 출시일자
			$("input:radio[name='overPromoFg']").removeAttr("checked");			//성과초과장려금 적용구분 미체크
			
			$("input:radio[name='sNewProdPromoFg']").removeAttr("checked");		//(슈퍼) 신상품입점장려금적용 미체크
			$("#extendInfo input[name=sNewProdStDy]").val('');					//(슈퍼) 신상품 출시일자
			$("input:radio[name='sOverPromoFg']").removeAttr("checked");		//(슈퍼) 성과초과장려금 적용구분 미체크
		}
		
		/********** 2) 채널 = 슈퍼 선택여부 체크 >> 슈퍼 미선택 시, 슈퍼 장려금 빈값으로 초기화 ****************/
		//(슈퍼) 신상품 장려금은 슈퍼가 아니면 무조건 빈값
		if(!isSuper){
			$("input:radio[name='sNewProdPromoFg']").removeAttr("checked");		//(슈퍼) 신상품입점장려금적용 미체크
			$("#extendInfo input[name=sNewProdStDy]").val('');					//(슈퍼) 신상품 출시일자
			$("input:radio[name='sOverPromoFg']").removeAttr("checked");		//(슈퍼) 성과초과장려금 적용구분 미체크
		}
		  
		/********** 3) 신상품장려금 적용여부에 따른 >> 출시일자 validation ****************/
		//신상품 장려금
		var newProdPromoFg	=	$("input:radio[name='newProdPromoFg']:checked").val();
		var newProdStDy		=	$("#extendInfo input[name=newProdStDy]").val().replace(/\s/gi, '');
		var overPromoFg 	= 	$("input:radio[name='overPromoFg']:checked").val();
		
		//(슈퍼) 신상품 장려금
		var sNewProdPromoFg	=	$("input:radio[name='sNewProdPromoFg']:checked").val();
		var sNewProdStDy	=	$("#extendInfo input[name=sNewProdStDy]").val().replace(/\s/gi, '');
		var sOverPromoFg 	= 	$("input:radio[name='sOverPromoFg']:checked").val();
		
		//신상품장려금 적용시, 출시일자 필수
		if (newProdPromoFg == "X") {
			if (newProdStDy == "") {
				alert("※신상품 출시일자를 넣으셔야 합니다. \n\n 신상품 출시일자는 KAN(88)코드 등록일자를 넣으셔야 합니다.");
				$("#extendInfo input[name=newProdStDy]").focus();
				return false;
			}
		} else {
			$("#extendInfo input[name=newProdStDy]").val("");
		}
		
		//(슈퍼) 신상품장려금 적용 시, (슈퍼) 출시일자 필수
		if(isSuper && sNewProdPromoFg == "X"){
			//(슈퍼) 신상품 출시일 셋팅
			fncSetsSNewProdStDy(evntGbn);
			return true;
// 			if (sNewProdStDy == "") {
// 				alert("※(슈퍼) 신상품 출시일자를 넣으셔야 합니다. \n\n※(슈퍼) 신상품 출시일자는 KAN(88)코드 등록일자를 넣으셔야 합니다.");
// 				$("#extendInfo input[name=sNewProdStDy]").focus();
// 				return false;
// 			}
		}else{
			$("#extendInfo input[name=sNewProdStDy]").val("");
		}
		
		return true;
	}
	

	//단순 메세지 표시함수
	function generateMessage(jqueryObject, message) {
		$("<div id=\"error_msg\" name=\"error_msg\" style=\"color:red;\">" +message+ "</div>").insertBefore(jqueryObject);
	}
	
	function getNumberMessageWithArgu(argu) {
		return argu+"<spring:message code="msg.common.error.notNum" />".replace("{0}", "");
	}

	function getTextMessageWithArgu(argu) {
		return argu+"<spring:message code="msg.common.error.required" />".replace("{0}", "");
	}
	
	/* 협력업체별 계열사 및 구매조직 체크 */
	function _eventSelVenZzorgInfo(entpCd){
		if(entpCd == null || $.trim(entpCd) == ""){
			//모든채널 비활성화
			_fncDeactiveAllChans();
			return;
		}
		
		var paramInfo	=	{};
		paramInfo["venCd"] = $.trim(entpCd);	//업체코드

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'post',
			dataType : 'json',
			async : false,
			url : '<c:url value="/edi/product/selectVendorZzorgInfo.json"/>',
			data : JSON.stringify(paramInfo),
			success : function(data) {
				//협력사별 구매조직(채널) 셋팅 
				_fncCtrlVenZzorgInfo(data);
			}
		});
	}
	
	/* 협력사 코드별 선택가능한 구매조직(채널) 선택  */
	function _fncCtrlVenZzorgInfo(json){
		var venPurDepts = json.venPurDepts;		//구매조직
		
		let extChanErrObj = $("input[name='extChanCd']").first();
		//현재 에러메세지 삭제
		deleteErrorMessageIfExist(extChanErrObj);
		
		//구매조직 존재 시
		if(venPurDepts != null && venPurDepts.length > 0){
			const allChanCds = $("input[name='extChanCd']").not(".fc-dis").map(function(){return $(this).val();}).get();	//모든 채널
			const activeChans = getMatchedArr(allChanCds, venPurDepts);			//파트너사가 선택 가능한 채널
			const deactiveChans = getUnMatchedArr(allChanCds, venPurDepts);		//온오프 채널 중 파트너사가 선택 불가한 채널
			
			//해당하는 채널별 영역 활성화
			$.each(activeChans, function(i,val){
				//채널 활성화
				$("input[name='extChanCd'][value='"+$.trim(val)+"']").prop("disabled", false).removeClass("fc-dis");
				//채널관련 상품정보 영역 show
				$("#extendInfo tr[data-chan="+$.trim(val)+"]").show();
			});
			
			//해당하지 않는 채널별 영역 비활성화
			$.each(deactiveChans, function(i,val){
				//채널 비활성화
				$("input[name='extChanCd'][value='"+$.trim(val)+"']").prop("checked", false).prop("disabled", true).addClass("fc-dis");
				//채널별 원매가 정보 영역 비활성화
				fncSetPriceAreaByChan($.trim(val), false);
				//채널관련 상품정보 영역 hide
				$("#extendInfo tr[data-chan="+$.trim(val)+"]").hide();
			});
			
			//현재 선택된 채널이 없을 경우, Default 채널 체크 ---------------------------------------------
			var chkedLen = $("input[name='extChanCd']:checked").not(":disabled").length;
			if(chkedLen == 0 && activeChans.length > 0){
				//마트가 선택 가능한 채널일 경우,
				if(activeChans.includes("KR02")){
					//마트, 오카도(CFC) 선택
					$("input[name='extChanCd'][value='KR02']").not(":disabled").prop("checked", true);
					$("input[name='extChanCd'][value='KR09']").not(":disabled").prop("checked", true);
					
					//마트, 오카도(CFC) 원매가 영역 활성화
					fncSetPriceAreaByChan("KR02", true);
					fncSetPriceAreaByChan("KR09", true);
				}else if(activeChans.includes("KR04")){
				//마트 선택 불가이면서, 슈퍼 선택가능할 경우,
					//슈퍼 선택
					$("input[name='extChanCd'][value='KR04']").not(":disabled").prop("checked", true);
				
					//슈퍼 원매가 영역 활성화
					fncSetPriceAreaByChan("KR04", true);
				}else{
				//(확장만 사용) 그 외 :: 활성화된 채널 중 첫 번째 채널 선택
					let defActiveChan = $("input[name='extChanCd']").not(":disabled").eq(0);
					if(defActiveChan != undefined && defActiveChan != null){
						let defActiveChanCd = $.trim(defActiveChan.val());
						//첫번째 채널 선택
						$("input[name='extChanCd'][value='"+defActiveChanCd+"']").not(":disabled").prop("checked", true);
						
						//원매가영역 활성화
						fncSetPriceAreaByChan(defActiveChanCd, true);
					}
					
				}
				
				$("input[name='extChanCd']").trigger("change");
			}

			//선택가능 채널 없을 경우
			if(activeChans.length == 0){
				generateMessage(extChanErrObj, "해당 업체코드로 확장 가능한 채널이 없습니다.");
			}
			//--------------------------------------------------------------------------------------
		}else{
			//모든 채널 비활성화
			_fncDeactiveAllChans();
		}
		
	}
	
	//모든 채널 비활성화
	function _fncDeactiveAllChans(){
		//채널별 원매가 정보 영역 비활성화
		fncSetPriceAreaByChan("ALL", false);
		//채널선택 해제
		$("input[name='extChanCd']").prop("checked", false);
		$("input[name='extChanCd']").prop("disabled", true);
	}
</script>