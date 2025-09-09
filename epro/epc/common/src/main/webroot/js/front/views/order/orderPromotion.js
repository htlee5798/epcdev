//"use strict"; 

var promotion = {
	// 결제쿠폰 목록 조회
	load: function(successCallback) {
		var params = {
			scpTarget: $("[name=isScpTarget]").val(),
			preOrderId: $("[name=preOrderId]").val()
		};
		
		$.post("/order/api/promotionList.do", params, "json")
		.done(function(data) {
			if (data != null) {
				var $table = $("#lpop_payDc").find("#payDcCouponTable");			// 쿠폰및할인혜택적용 > 결제쿠폰 > 결제쿠폰목록 선택 팝업
				var $deliTable = $("#lpop_deliveryDc").find("#deliDcCouponTable");	// 쿠폰및할인혜택적용 > 배송비쿠폰 > 배송비쿠폰목록 선택 팝업
				var $list = $("#payDcCouponDesc");									// 쿠폰및할인혜택적용 > 결제쿠폰 > 금액란 하단 쿠폰상세설명
				var $deliList = $("#deliDcCouponDesc");								// 쿠폰및할인혜택적용 > 배송비쿠폰 > 금액란 하단 쿠폰상세설명
				
				var payDcQty = 0;
				// 배송비쿠폰 처리
				if (data.promotion03 != null) {
					var $template = $.templates("#deliDcCouponTemplate");
					$.each(data.promotion03, function(i, cpn) {
						promotion.deliCouponTableAdd(cpn, $deliTable, $template);
						promotion.deliCouponListAdd(cpn, $deliList, $template);
					});
					payDcQty += data.promotion03.length;
				}
				
				// 결제쿠폰 처리
				if (data.promotion04 != null) {
					var $template = $.templates("#payDcCouponTemplate");
					/*
					$.each(data.promotion04, function(i, cpn) {
						promotion.couponTableAdd(cpn, $table, $template);
						promotion.couponListAdd(cpn, $list, $template);
					});
					payDcQty += data.promotion04.length;
					*/
					// 2018.05.30 수정 - 고정가쿠폰이 아닐 경우에 출력하도록 변경 (2018.09.04 고객상품할인(08), 상품DM(05) 미출력 추가)
					$.each(data.promotion04, function(i, cpn) {
						if( cpn.COUPON_TYPE_CD != CONST.get("COUPON_TYPE_CD_PAY_FIX") 
								&& cpn.COUPON_TYPE_CD != CONST.get("COUPON_TYPE_CD_CNT_FIX") 
								&& cpn.COUPON_TYPE_CD != CONST.get("COUPON_TYPE_CD_MEMBERPROD")
								&& cpn.COUPON_TYPE_CD != CONST.get("COUPON_TYPE_CD_PRODDM")
								){
							promotion.couponTableAdd(cpn, $table, $template);
							promotion.couponListAdd(cpn, $list, $template);
							payDcQty++;
						}else{
							$("tr[data-pay-coupon='"+cpn.REP_COUPON_ID+"']").hide();	// 2018.09.13 미출력 쿠폰 숨김처리
						}
					});
				}
				if (data.promotion04_scp != null) {
					var $template = $.templates("#payDcScpCouponTemplate");
					$.each(data.promotion04_scp, function(i, cpn) {
						promotion.couponTableAdd(cpn, $table, $template);
						promotion.couponListAdd(cpn, $list, $template);
					});
					payDcQty += data.promotion04_scp.length;
				} 
				calc.countCoupon();
			}
			layerPopRadio();
			$("input[name=couponChecked2]").click(function(event){
				fnApplyCouponSummary("2");
			});
			if (typeof(successCallback) != "undefined") {
				successCallback();
			}
		})
		.fail(function(xhr, status) {
		});
	},
	couponTableAdd: function(cpn, $table, $template) {
		var ordCpn = $("tr[data-pay-coupon='" + cpn.REP_COUPON_ID + "']", $table);
		if (ordCpn.length < 1) {
			var objVal = "";
			var objArr = [];
			if (cpn.OBJECT != null) {
				$.each(cpn.OBJECT, function(k, obj) {
					objArr.push(obj.OBJECT_ID);
				});
				objVal = objArr.join(",");
			}
			var html = $template.render({item: cpn, OBJ_VAL: objVal, addType: 'tr'}, {currency: util.currency});
			$table.append(html);
		}
		ordCpn.show();
	},
	deliCouponTableAdd : function(cpn, $table, $template) {
		var ordCpn = $("tr[data-deli-coupon='" + cpn.REP_COUPON_ID + "']", $table);
		if (ordCpn.length < 1) {
			var objVal = "";
			var objArr = [];
			if (cpn.OBJECT != null) {
				$.each(cpn.OBJECT, function(k, obj) {
					objArr.push(obj.OBJECT_ID);
				});
				objVal = objArr.join(",");
			}
			var html = $template.render({item: cpn, OBJ_VAL: objVal, addType: 'tr'}, {currency: util.currency});
			$("#deliDcCouponTable").append(html);
		}
	},
	couponListAdd: function(cpn, $table, $template) {
		var ordCpn = $("li[id='payDc_" + cpn.COUPON_ID + "']", $table);
		if (ordCpn.length < 1) {
			var objVal = "";
			var objArr = [];
			if (cpn.OBJECT != null) {
				$.each(cpn.OBJECT, function(k, obj) {
					objArr.push(obj.OBJECT_ID);
				});
				objVal = objArr.join(",");
			}
			var html = $template.render({item: cpn, OBJ_VAL: objVal, addType: 'li'}, {currency: util.currency});
			$table.append(html);
			$.each($('input:radio[name="couponChecked2"]'), function(i, couponObj) {
		    	if ($.trim($(couponObj).attr('REP_COUPON_ID')) != "0" && $.trim($(couponObj).attr('REP_COUPON_ID'))==cpn.REP_COUPON_ID ) {
		    		$(couponObj).attr("checked" , true);
		    		$(couponObj).parent().addClass("active");
		    	}else{
		    		$(couponObj).attr("checked" , false);
		    		$(couponObj).parent().removeClass("active");
		    	}
		    });
		}
	},
	deliCouponListAdd: function(cpn, $table, $template) {
		var ordCpn = $("li[id='payDc_" + cpn.COUPON_ID + "']", $table);
		if (ordCpn.length < 1) {
			var objVal = "";
			var objArr = [];
			if (cpn.OBJECT != null) {
				$.each(cpn.OBJECT, function(k, obj) {
					objArr.push(obj.OBJECT_ID);
				});
				objVal = objArr.join(",");
			}
			var html = $template.render({item: cpn, OBJ_VAL: objVal, addType: 'li'}, {currency: util.currency});
			$table.append(html);
/* 2018.09.14 주석처리 - 쿠폰 자동선택은 다른함수에서 처리 
			$.each($('input:radio[name="couponChecked3"]'), function(i, couponObj) {
		    	if ($.trim($(couponObj).attr('REP_COUPON_ID')) != "0" && $.trim($(couponObj).attr('REP_COUPON_ID'))==cpn.REP_COUPON_ID ) {
		    		$(couponObj).attr("checked" , true);
		    		$(couponObj).parent().addClass("active");
		    	}else{
		    		$(couponObj).attr("checked" , false);
		    		$(couponObj).parent().removeClass("active");
		    	}
		    });
*/
		}
	}
};

var lastCheckedCoupon2 = null;	// 사용자가 마지막에 선택한 결제쿠폰
var lastCheckedCoupon3 = null;	// 사용자가 마지막에 선택한 배송비쿠폰
var _securitiesPayChecked = true;
/**
 * 프로모션 재계산
 */
function fnRecalcPromotion(calcDivn) {
	if (!isEnableCallPromotion) return;
	
	if(typeof fn_securitiesPay =='function' && _securitiesPayChecked ){
		_securitiesPayChecked = false;
		fn_securitiesPay(calcDivn);
		return ;
	}
	
	calc.clearDc(calcDivn);

	calc.all(true);
	if ( IS_VicJoin ) {
		return;
	}
	var $frm = $("#prodPromotionForm");
	$frm.empty();
	var inputArray = [];

    //<%-- 카드결제 --%>
	var rpCardCd = "";
	var payAllByCard = "N";
	var lPointAmt = $("#lottePoint").val();
	var point1Amt = $("#point1").val();
	var point2Amt = 0;
	var point3Amt = $("#point3").val();
	var point4Amt = $("#point4").val();
	var grpDeliYn = $("[name=hopeDeli]:checked").closest("td").find("[name=grpDeliYn]").val();
	var smtGiftAmt = 0;
	var allPointAmt = Number(lPointAmt) + Number(smtGiftAmt) + Number(point1Amt) + Number(point2Amt) + Number(point3Amt) + Number(point4Amt);
	var totalPrice = Number($('#totSellPrc').val()) + Number($('#TOTAL_ORG_DELIV_AMT').val()) + Number($('#TOTAL_ORG_DELIV2_AMT').val());
	
	if(allPointAmt <  totalPrice){
		payAllByCard = "Y";

        if($('input[name=paymentType]:checked').val() === 'card'){
            rpCardCd = $('#cardCd').val();
        }
        else if($('input[name=paymentType]:checked').val() === "kakao"){
        	rpCardCd = $('#kakaoCardCd').val();
        }
        else if($('input[name=paymentType]:checked').val() === "lpay"){
	        rpCardCd = $('#lpayCardCd').val();
        }
        else {
    		payAllByCard = "N";
        }
	}
	// 복합결제 사용시 카드할인 적용 수용
	//if (allPointAmt == 0) {
		
	//}
	//$("[name=cardCallDcViewTr1]").hide();
	//$("[name=cardCallDcViewTr2]").hide();
    //<%-- 선택한 쿠폰 --%>
    var issCpns = "";
    var input1Arr = $('[name=couponChecked1]');
    var input2Arr = $('[name=couponChecked2]:checked');
    var input3Arr = $('[name=couponChecked3]:checked');
    
    $.each(input1Arr, function(i, couponObj) {
    	if ($.trim($(couponObj).attr('REP_COUPON_ID')) != "0") {
    		if( $(couponObj).parent().hasClass("active") ){
        		inputArray.push(genDomInput("cpnCouponTypeCds", $(couponObj).attr('COUPON_TYPE_CD')));
        		inputArray.push(genDomInput("cpnRepCouponIds", $(couponObj).attr('REP_COUPON_ID')));
        		inputArray.push(genDomInput("cpnCouponIds", $(couponObj).attr('COUPON_ID')));
    		}
    	}
    });
    $.each(input2Arr, function(i, couponObj) {
    	if ($.trim($(couponObj).attr('REP_COUPON_ID')) != "0") {
    		inputArray.push(genDomInput("cpnCouponTypeCds", $(couponObj).attr('COUPON_TYPE_CD')));
    		inputArray.push(genDomInput("cpnRepCouponIds", $(couponObj).attr('REP_COUPON_ID')));
    		inputArray.push(genDomInput("cpnCouponIds", $(couponObj).attr('COUPON_ID')));
    	}
    });
    $.each(input3Arr, function(i, couponObj) {
    	if ($.trim($(couponObj).attr('REP_COUPON_ID')) != "0") {
    		inputArray.push(genDomInput("cpnCouponTypeCds", $(couponObj).attr('COUPON_TYPE_CD')));
    		inputArray.push(genDomInput("cpnRepCouponIds", $(couponObj).attr('REP_COUPON_ID')));
    		inputArray.push(genDomInput("cpnCouponIds", $(couponObj).attr('COUPON_ID')));
    	}
    });

	inputArray.push(genDomInput("isScpTarget", $("#isScpTarget").val()));

    //<%-- 배송비 --%>
    var delivAmt = Number($("#TOTAL_ORG_DELIV_AMT").val()) + Number($("#TOTAL_ORG_DELIV2_AMT").val());
    if (delivAmt > 0) {
   		inputArray.push(genDomInput("delivAmt", delivAmt));
    }

    //<%-- 직원할인 여부 --%>
	inputArray.push(genDomInput("empDcYn", ($(':checkbox[name=empDcYn]').is(":checked") ? "Y":"N")));

    //<%-- 전달 파라미터 --%>
	inputArray.push(genDomInput("calcDivn", calcDivn)); // 프로모션 계산 위치
	inputArray.push(genDomInput("mdCmplDcAmt", $('#cmplDc').val()));
	inputArray.push(genDomInput("cardCoCd", rpCardCd)); // 카드사 코드
	inputArray.push(genDomInput("payAllByCard", payAllByCard)); // 신용카드 완납여부
	inputArray.push(genDomInput("savuPoint", lPointAmt)); // L-Point
	inputArray.push(genDomInput("giftPoint", point4Amt)); // 상품권전환금
	inputArray.push(genDomInput("grpDeliYn", grpDeliYn)); // 합배송여부
	if ($("[name=scrDeliType]:checked").length > 0 && $.trim($("[name=scrDeliType]:checked").attr("data-delitype")) != "01") {
		var pickUpType = $.trim($("[name=scrDeliType]:checked").attr("data-ext_str_type"));
		if (pickUpType === "" || pickUpType === CONST.get('EXT_PICKUP_TP_WMS_STORE')) {
			pickUpType = "999";	// 매장픽업
		}
		inputArray.push(genDomInput("pickUpType", pickUpType)); // 픽업유형
	}
	var hopeDeliTm = $.trim($("[name=hopeDeli]:checked").val()).split("_");
	if (hopeDeliTm.length > 1) {
		inputArray.push(genDomInput("hopeDeliTm", hopeDeliTm[1].replace(":", ""))); // 배송회차
	}

	$frm.empty();
	$frm.append(inputArray);
	//if (calcDivn == "Trade") {
	//	console.log($("#prodPromotionForm").html());
	//	return;	// DEBUG mode
	//}
	
	fn$ajax_globalStart(true);
	
	var params = $frm.serialize();
	try{
		fn$ajax(_LMAppSSLUrl+"/order/getProductPromotionList.do", params, fnNmGetter().name, false);
	}catch(e){
		alert(e);
	}
	
	return;
}

// DEBUG 영 데이터
var PromotionData = {
	First: null,
	Trade: null
};

var multiDelivPromotion = [];

/**
 * 프로모션 재계산 callback
 */
function callBack_$fnRecalcPromotion(response) {
	var aProdCpnList = new Array();
	var delivAmt = Number($("#TOTAL_ORG_DELIV_AMT").val()) + Number($("#TOTAL_ORG_DELIV2_AMT").val());
	var jsonData = $.parseJSON(response);
	if (jsonData.Error === "Y") {
		alert("프로모션 설정중 오류가 발생하였습니다.");
		location.href = _LMAppSSLUrl+"/basket/basketList.do";
		return;
	}
	else {
		multiDelivPromotion = [];
		
		// DEBUG 를 위해 남겨둠
		if (jsonData.CalcDivn === "First") {
			PromotionData.First = $.extend({}, jsonData);
		}
		else {
			PromotionData.Trade = $.extend({}, jsonData);
		}
		
		$("#preOrderItemBody .ul-dclist").empty();
		$("#preOrderItemBody .dc-tag").hide();
		$("[id^=prodDc_]").hide();
    	var prdEvtList = jsonData.Product;
    	if (prdEvtList !=  null && prdEvtList.length > 0) {

			for (var i = 0; i < prdEvtList.length; i++) {
				if (prdEvtList[i].BOS_EVT_KIND == '2'
					&& (prdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_PRODDM")				// 05 : 상품DM
						|| prdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_MEMBERPROD")		// 08 : 고객상품할인 
						|| prdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_SCP_PROD_SAVU")	// 22 : 스마트쿠폰(상품적립)
						|| prdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_SCP_PROD_DC")		// 21 : 스마트쿠폰(상품할인)
						|| prdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_POINT")			// 25 : 적립쿠폰
						|| prdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_PAY_FIX")			// 31 : 고정가쿠폰(금액)
						|| prdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_CNT_FIX")			// 32 : 고정가쿠폰(수량)
					)) {
					// 상품쿠폰
					var tmpIdx = realCpnEvtList.length;
					realCpnEvtList[tmpIdx] = new Array();
					realCpnEvtList[tmpIdx] = {
       						 PROD_TRADE_DIVN : prdEvtList[i].PROD_TRADE_DIVN
      						,PROD_CD : prdEvtList[i].PROD_CD
      						,ITEM_CD : prdEvtList[i].ITEM_CD
      						,PRE_ORDER_ITEM_SEQ : prdEvtList[i].PRE_ORDER_ITEM_SEQ
      						,PAY_COND_FG : prdEvtList[i].PAY_COND_FG
      						,CARDCO_CD : prdEvtList[i].CARDCO_CD
      						,BOS_EVT_KIND : prdEvtList[i].BOS_EVT_KIND
      						,PROMO_CL_TYP_CD : prdEvtList[i].PROMO_CL_TYP_CD
      						,PROMO_CL_MST_NO : prdEvtList[i].PROMO_CL_MST_NO
      						,PROMO_CL_SEQ : prdEvtList[i].PROMO_CL_SEQ
      						,EVNT_APPLY_QTY : prdEvtList[i].EVNT_APPLY_QTY
      						,EVNT_APPLY_AMT : prdEvtList[i].EVNT_APPLY_AMT
      						,PROMO_KIND_CD : prdEvtList[i].PROMO_KIND_CD
	  						,PRVS_TO_NO : prdEvtList[i].PRVS_TO_NO
	  						,SCP_STR_CD : prdEvtList[i].SCP_STR_CD
	  						,DC_EVT_CONTENT : prdEvtList[i].DC_EVT_CONTENT
	  						,REP_COUPON_ID : prdEvtList[i].REP_COUPON_ID
	  						,PROD_SELL_QTY : prdEvtList[i].PROD_SELL_QTY
	  						,OFFER_TY : prdEvtList[i].OFFER_TY
	  						,MD_EVT_CD : prdEvtList[i].MD_EVT_CD
	  						,COUPON_ID : prdEvtList[i].COUPON_ID
					};

    				if (prdEvtList[i].PAY_COND_FG == '01') {
    					realAllCardDc += Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT);

    					if (prdEvtList[i].PROMO_CL_TYP_CD != CONST.get("COUPON_TYPE_CD_POINT")) {
    						//realCardDc += Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT);
    						if(BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_GIFTMALL") || BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_SPEC")){
    							multiDelivPromotion.push({
	                				type: "realItemDcCardAmt",
	                				promoNo: prdEvtList[i].PROMO_CL_MST_NO,
	                				prodCd: prdEvtList[i].PROD_CD,
	                				itemCd: prdEvtList[i].ITEM_CD,
	                				qty: Number(prdEvtList[i].EVNT_APPLY_QTY),
	                				price: Number(prdEvtList[i].EVNT_APPLY_AMT)
	                			});
    						}

        		            var realItemDcCardAmtList = $('input[name=realItemDcCardAmt]');
        		            for(var m = 0 ; m < realItemDcCardAmtList.length ; m++){
        		                // 동일 상품에 대해서 적용
        		                if($('input[name=itemProdCd]').get(m).value == prdEvtList[i].PROD_CD
        		                		&& $('input[name=itemItemCd]').get(m).value == prdEvtList[i].ITEM_CD){
    		                		if(BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_GIFTMALL") || BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_SPEC")){
    		                			realItemDcCardAmtList.get(m).value = Number(realItemDcCardAmtList.get(m).value) + Number($('input[name=itemBsketQty]').get(m).value) * Number(prdEvtList[i].EVNT_APPLY_AMT) ;
    		                		}else{
    		                			realItemDcCardAmtList.get(m).value = Number(realItemDcCardAmtList.get(m).value) + Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT);
    		                			appendProductDcItem(prdEvtList[i].PROD_CD, prdEvtList[i].PRE_ORDER_ITEM_SEQ, prdEvtList[i].PROMO_CL_MST_NO, 'C'+prdEvtList[i].PROMO_CL_TYP_CD, (Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT)), "원");
   		                				//$("[id='dc-" + prdEvtList[i].PROD_CD + "-" + prdEvtList[i].PRE_ORDER_ITEM_SEQ + "'] ul").append("<li data-check='1' data-promo='" + prdEvtList[i].PROMO_CL_MST_NO + "'>" + getPromotionName('C' + prdEvtList[i].PROMO_CL_TYP_CD) + "<em class=\"txt-red flt-right\">" +  util.currency(Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT)) + "원</em></li>");
    		                		}
        		                }
        		            }
    					}
    				}

    				// 사용된 상품쿠폰 목록 구하기
    				var idxProdCnp = aProdCpnList.length;
    				aProdCpnList[idxProdCnp] = new Array();
    				aProdCpnList[idxProdCnp] = {
    						 REP_COUPON_ID : prdEvtList[i].REP_COUPON_ID
	  						,COUPON_ID : prdEvtList[i].COUPON_ID
    						,COUPON_DC_AMT : prdEvtList[i].DC_EVT_CONTENT
	  						,MD_EVT_CD : prdEvtList[i].MD_EVT_CD
    				};
    				//console.log(aProdCpnList[idxProdCnp]);
    				// M쿠폰
    				if(prdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_SCP_PROD_DC")){
    					
    					$("#prodDc").val(Number($("#prodDc").val()) + Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT));
    					$("#scpDc").val(Number($("#scpDc").val()) + Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT));
    					$("[id='prodDc_" + prdEvtList[i].COUPON_ID + "']").show();
    					
    				// 롯데마트 쿠폰	
    				}else {
    					if (prdEvtList[i].PROMO_CL_TYP_CD != CONST.get("COUPON_TYPE_CD_SCP_PROD_SAVU")
    							&& prdEvtList[i].PROMO_CL_TYP_CD != CONST.get("COUPON_TYPE_CD_POINT")) {
    						$("#prodDc").val(Number($("#prodDc").val()) + Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT));
    						$("[id='prodDc_" + prdEvtList[i].COUPON_ID + "']").show();
    					}
    					else {
    						appendProductDcItem(prdEvtList[i].PROD_CD, prdEvtList[i].PRE_ORDER_ITEM_SEQ, prdEvtList[i].PROMO_CL_MST_NO, 'C'+prdEvtList[i].PROMO_CL_TYP_CD, (Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT)), "P");
    						//$("[id='dc-" + prdEvtList[i].PROD_CD + "-" + prdEvtList[i].PRE_ORDER_ITEM_SEQ + "'] ul").append("<li data-check='1' data-desc='포인트적립' data-promo='" + prdEvtList[i].PROMO_CL_MST_NO + "' data-apply_qty='" + prdEvtList[i].EVNT_APPLY_QTY + "' data-apply_amt='" + prdEvtList[i].EVNT_APPLY_AMT + "'>" + getPromotionName('C' + prdEvtList[i].PROMO_CL_TYP_CD) + "<em class=\"txt-red flt-right\">" +  util.currency(Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT)) + "P</em></li>");
    						$("#prodSave").val(Number($("#prodSave").val()) + Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT));
    						$("[id='prodDc_" + prdEvtList[i].COUPON_ID + "']").show();
    					}
    				}
				}
				else if (prdEvtList[i].BOS_EVT_KIND == '2'
						&& prdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_CARDPROD")) {
					// 카드상품할인 (온라인 전용)
					var tmpIdx = realCardEvtList.length;
					realCardEvtList[tmpIdx] = new Array();
					realCardEvtList[tmpIdx] = {
   						 PROD_TRADE_DIVN : prdEvtList[i].PROD_TRADE_DIVN
 						,PROD_CD : prdEvtList[i].PROD_CD
 						,ITEM_CD : prdEvtList[i].ITEM_CD
 						,PRE_ORDER_ITEM_SEQ : prdEvtList[i].PRE_ORDER_ITEM_SEQ
 						,PAY_COND_FG : prdEvtList[i].PAY_COND_FG
 						,CARDCO_CD : prdEvtList[i].CARDCO_CD
 						,BOS_EVT_KIND : prdEvtList[i].BOS_EVT_KIND
 						,PROMO_CL_TYP_CD : prdEvtList[i].PROMO_CL_TYP_CD
 						,PROMO_CL_MST_NO : prdEvtList[i].PROMO_CL_MST_NO
 						,PROMO_CL_SEQ : prdEvtList[i].PROMO_CL_SEQ
 						,EVNT_APPLY_QTY : prdEvtList[i].EVNT_APPLY_QTY
 						,EVNT_APPLY_AMT : prdEvtList[i].EVNT_APPLY_AMT
 						,PROMO_KIND_CD : prdEvtList[i].PROMO_KIND_CD
  						,PRVS_TO_NO : prdEvtList[i].PRVS_TO_NO
  						,SCP_STR_CD : prdEvtList[i].SCP_STR_CD
  						,DC_EVT_CONTENT : prdEvtList[i].DC_EVT_CONTENT
  						,REP_COUPON_ID : prdEvtList[i].REP_COUPON_ID
  						,PROD_SELL_QTY : prdEvtList[i].PROD_SELL_QTY
  						,OFFER_TY : prdEvtList[i].OFFER_TY
  						,MD_EVT_CD : prdEvtList[i].MD_EVT_CD
  						,COUPON_ID : prdEvtList[i].COUPON_ID
					};

					realAllCardDc += Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT);
					$("#cardDc").val(Number($("#cardDc").val()) + Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT));

					if(BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_GIFTMALL") || BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_SPEC")){
						multiDelivPromotion.push({
            				type: "itemDcCardAmt",
            				promoNo: prdEvtList[i].PROMO_CL_MST_NO,
            				prodCd: prdEvtList[i].PROD_CD,
            				itemCd: prdEvtList[i].ITEM_CD,
            				qty: Number(prdEvtList[i].EVNT_APPLY_QTY),
            				price: Number(prdEvtList[i].EVNT_APPLY_AMT)
            			});
					}

					// 상품별 카드 할인 금액 누적
		            var itemDcCardAmtList = $('input[name=itemDcCardAmt]');
		            for(var m = 0 ; m < itemDcCardAmtList.length ; m++){
		                // 동일 상품에 대해서 적용
		                if($('input[name=itemProdCd]').get(m).value == prdEvtList[i].PROD_CD
		                		&& $('input[name=itemItemCd]').get(m).value == prdEvtList[i].ITEM_CD){
	                		if(BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_GIFTMALL") || BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_SPEC")){
			                	itemDcCardAmtList.get(m).value = Number(itemDcCardAmtList.get(m).value) + Number($('input[name=itemBsketQty]').get(m).value) * Number(prdEvtList[i].EVNT_APPLY_AMT) ;
	                		}else{
	                			itemDcCardAmtList.get(m).value = Number(itemDcCardAmtList.get(m).value)	+ Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT) ;
	                			//카드 상품할인은 상품영역의 할인내역 layer에 할인내역이 보여지지 않고 총 할인금액의 카드할인에만 포함된다. 그래서 아래 주석 처리
	                			//다시 보여져야한다고 결정되면 주석 해제 후 상품 할인금액 계산 다시 할 수 있도록 변경되어야 함
	                			//appendProductDcItem(prdEvtList[i].PROD_CD, prdEvtList[i].PRE_ORDER_ITEM_SEQ, prdEvtList[i].PROMO_CL_MST_NO, 'C'+prdEvtList[i].PROMO_CL_TYP_CD, (Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT)), "원");
	                			//$("[id='dc-" + prdEvtList[i].PROD_CD + "-" + prdEvtList[i].PRE_ORDER_ITEM_SEQ + "'] ul").append("<li data-check='2' data-desc='카드할인' data-promo='" + prdEvtList[i].PROMO_CL_TYP_CD + "' data-apply_qty='" + prdEvtList[i].EVNT_APPLY_QTY + "' data-apply_amt='" + prdEvtList[i].EVNT_APPLY_AMT + "'>" + getPromotionName('C' + prdEvtList[i].PROMO_CL_TYP_CD) + "<em class=\"txt-red flt-right\">" +  util.currency(Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT)) + "원</em></li>");
			                }
		                }
		            }
				}
				else if (prdEvtList[i].PAY_COND_FG == '01') {
					// 카드지불 (One쿠폰, N쿠폰, 다둥이클럽 할인 쿠폰 포함)

					var tmpIdx = realCardEvtList.length;
					realCardEvtList[tmpIdx] = new Array();
					realCardEvtList[tmpIdx] = {
   						 PROD_TRADE_DIVN : prdEvtList[i].PROD_TRADE_DIVN
 						,PROD_CD : prdEvtList[i].PROD_CD
 						,ITEM_CD : prdEvtList[i].ITEM_CD
 						,PRE_ORDER_ITEM_SEQ : prdEvtList[i].PRE_ORDER_ITEM_SEQ
 						,PAY_COND_FG : prdEvtList[i].PAY_COND_FG
 						,CARDCO_CD : prdEvtList[i].CARDCO_CD
 						,BOS_EVT_KIND : prdEvtList[i].BOS_EVT_KIND
 						,PROMO_CL_TYP_CD : prdEvtList[i].PROMO_CL_TYP_CD
 						,PROMO_CL_MST_NO : prdEvtList[i].PROMO_CL_MST_NO
 						,PROMO_CL_SEQ : prdEvtList[i].PROMO_CL_SEQ
 						,EVNT_APPLY_QTY : prdEvtList[i].EVNT_APPLY_QTY
 						,EVNT_APPLY_AMT : prdEvtList[i].EVNT_APPLY_AMT
 						,PROMO_KIND_CD : prdEvtList[i].PROMO_KIND_CD
  						,PRVS_TO_NO : prdEvtList[i].PRVS_TO_NO
  						,SCP_STR_CD : prdEvtList[i].SCP_STR_CD
  						,DC_EVT_CONTENT : prdEvtList[i].DC_EVT_CONTENT
  						,REP_COUPON_ID : prdEvtList[i].REP_COUPON_ID
  						,PROD_SELL_QTY : prdEvtList[i].PROD_SELL_QTY
  						,OFFER_TY : prdEvtList[i].OFFER_TY
  						,MD_EVT_CD : prdEvtList[i].MD_EVT_CD
  						,COUPON_ID : prdEvtList[i].COUPON_ID
					};

					realAllCardDc += Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT);

					if (prdEvtList[i].PROMO_CL_TYP_CD == CONST.get("EVT_TYPE_CD_NPLUS1")
							|| prdEvtList[i].PROMO_CL_TYP_CD == CONST.get("EVT_TYPE_CD_NPLUS1ONLINE")) {
						// 덤

						var realItemExtraQtyList = $("input[name=realItemExtraQty]");
    		            for(var m = 0 ; m < realItemExtraQtyList.length ; m++){
    		                // 동일 상품에 대해서 적용
    		                if($('input[name=itemProdCd]').get(m).value == prdEvtList[i].PROD_CD
			                		&& $('input[name=itemItemCd]').get(m).value == prdEvtList[i].ITEM_CD){
       		                	if(BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_GIFTMALL")){
       		                		//realItemExtraQtyList.get(m).value = Number($('input[name=itemBsketQty]').get(m).value) + Number(prdEvtList[i].EVNT_APPLY_AMT);
       		                	}
       		                	else{
        		                	realItemExtraQtyList.get(m).value = Number(realItemExtraQtyList.get(m).value) + Number(prdEvtList[i].EVNT_APPLY_AMT);
        		                	if (BSKET_TYPE_CD != CONST.get("BASKET_TYPE_MART_SPEC")) {
        		            			appendProductDcItem(prdEvtList[i].PROD_CD, prdEvtList[i].PRE_ORDER_ITEM_SEQ, prdEvtList[i].PROMO_CL_MST_NO, 'E'+prdEvtList[i].PROMO_CL_TYP_CD, (Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT)), "개");
        		                		//$("[id='dc-" + prdEvtList[i].PROD_CD + "-" + prdEvtList[i].PRE_ORDER_ITEM_SEQ + "'] ul").append("<li data-check='3' data-desc='카드할인' data-promo='" + prdEvtList[i].PROMO_CL_TYP_CD + "' data-apply_qty='" + prdEvtList[i].EVNT_APPLY_QTY + "' data-apply_amt='" + prdEvtList[i].EVNT_APPLY_AMT + "'>" + getPromotionName('E' + prdEvtList[i].PROMO_CL_TYP_CD) + "<em class=\"txt-red flt-right\">" +  util.currency(Number(realItemExtraQtyList.get(m).value) + Number(prdEvtList[i].EVNT_APPLY_AMT)) + "개</em></li>");
        		                	}
        		                }
    		                }
    		            }
					}
					else if (prdEvtList[i].PROMO_CL_TYP_CD != CONST.get("EVT_TYPE_CD_MEMBERSPOINTSAVE")
							&& prdEvtList[i].PROMO_CL_TYP_CD != CONST.get("EVT_TYPE_CD_CHLD")) {
						if (prdEvtList[i].BOS_EVT_KIND != '2') {
							realCardDc += Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT);
						}
						if(BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_GIFTMALL") || BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_SPEC")){
							multiDelivPromotion.push({
	            				type: "realItemDcCardAmt",
                				promoNo: prdEvtList[i].PROMO_CL_MST_NO,
	            				prodCd: prdEvtList[i].PROD_CD,
	            				itemCd: prdEvtList[i].ITEM_CD,
	            				qty: Number(prdEvtList[i].EVNT_APPLY_QTY),
	            				price: Number(prdEvtList[i].EVNT_APPLY_AMT)
	            			});
						}

    		            var realItemDcCardAmtList = $('input[name=realItemDcCardAmt]');
    		            for(var m = 0 ; m < realItemDcCardAmtList.length ; m++){
    		                // 동일 상품에 대해서 적용
    		                if($('input[name=itemProdCd]').get(m).value == prdEvtList[i].PROD_CD
    		                		&& $('input[name=itemItemCd]').get(m).value == prdEvtList[i].ITEM_CD){
   	    		                if(BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_GIFTMALL") || BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_SPEC")){
	     		                	realItemDcCardAmtList.get(m).value = Number(realItemDcCardAmtList.get(m).value) + Number($('input[name=itemBsketQty]').get(m).value) * Number(prdEvtList[i].EVNT_APPLY_AMT) ;
   	    		                }
   	    		                else {
	     		                	realItemDcCardAmtList.get(m).value = Number(realItemDcCardAmtList.get(m).value) + Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT);
	     		                	//$("#dc-" + prdEvtList[i].PROD_CD + "-" + prdEvtList[i].PRE_ORDER_ITEM_SEQ + " ul").append("<li data-check='5' data-desc='카드할인'>" + getPromotionName('E' + prdEvtList[i].PROMO_CL_TYP_CD) + "<em class=\"txt-red flt-right\">" +  util.currency(Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT)) + "원</em></li>");
	 		                	}
    		                }
    		            }
					}
				}
				else {
					// 카드 제외 상품행사 (One쿠폰, N쿠폰, 다둥이클럽 할인 쿠폰, 즉시상품할인, 즉시상품할인II 포함)

					var tmpIdx = realProdEvtList.length;
    				realProdEvtList[tmpIdx] = new Array();
    				realProdEvtList[tmpIdx] = {
    						 PROD_TRADE_DIVN : prdEvtList[i].PROD_TRADE_DIVN
    						,PROD_CD : prdEvtList[i].PROD_CD
    						,ITEM_CD : prdEvtList[i].ITEM_CD
    						,PRE_ORDER_ITEM_SEQ : prdEvtList[i].PRE_ORDER_ITEM_SEQ
    						,PAY_COND_FG : prdEvtList[i].PAY_COND_FG
    						,CARDCO_CD : prdEvtList[i].CARDCO_CD
    						,BOS_EVT_KIND : prdEvtList[i].BOS_EVT_KIND
    						,PROMO_CL_TYP_CD : prdEvtList[i].PROMO_CL_TYP_CD
    						,PROMO_CL_MST_NO : prdEvtList[i].PROMO_CL_MST_NO
    						,PROMO_CL_SEQ : prdEvtList[i].PROMO_CL_SEQ
    						,EVNT_APPLY_QTY : prdEvtList[i].EVNT_APPLY_QTY
    						,EVNT_APPLY_AMT : prdEvtList[i].EVNT_APPLY_AMT
    						,PROMO_KIND_CD : prdEvtList[i].PROMO_KIND_CD
	  						,PRVS_TO_NO : prdEvtList[i].PRVS_TO_NO
	  						,SCP_STR_CD : prdEvtList[i].SCP_STR_CD
	  						,DC_EVT_CONTENT : prdEvtList[i].DC_EVT_CONTENT
	  						,REP_COUPON_ID : prdEvtList[i].REP_COUPON_ID
	  						,PROD_SELL_QTY : prdEvtList[i].PROD_SELL_QTY
	  						,OFFER_TY : prdEvtList[i].OFFER_TY
	  						,MD_EVT_CD : prdEvtList[i].MD_EVT_CD
	  						,COUPON_ID : prdEvtList[i].COUPON_ID
    				};

					if (prdEvtList[i].PROMO_CL_TYP_CD == CONST.get("EVT_TYPE_CD_NPLUS1")
						|| prdEvtList[i].PROMO_CL_TYP_CD == CONST.get("EVT_TYPE_CD_NPLUS1ONLINE")) {
						// 덤

						var realItemExtraQtyList = $("input[name=realItemExtraQty]");
    		            for(var m = 0 ; m < realItemExtraQtyList.length ; m++){
    		                // 동일 상품에 대해서 적용
    		                if($('input[name=itemProdCd]').get(m).value == prdEvtList[i].PROD_CD
    		                		&& $('input[name=itemItemCd]').get(m).value == prdEvtList[i].ITEM_CD){
    		                	if (BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_GIFTMALL") || BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_SPEC")) {
    		                		// 다중배송일 경우 동일 상품이 여러개 존재한다면 skip - 다중등록 후 새로고침할 경우 이중 설정 오류 발생
    		                		var itemCount = 0;
    		                		$('input[name=itemProdCd]').each(function(qIdx, qObj) {
    		                			if ($(qObj).val() == prdEvtList[i].PROD_CD && $('input[name=itemItemCd]').eq(qIdx).val() == prdEvtList[i].ITEM_CD) {
    		                				itemCount ++;
    		                			}
    		                		});
    		                		//console.log("realItemExtraQtyList.get(m).value : " + realItemExtraQtyList.get(m).value + "prdEvtList[i].EVNT_APPLY_AMT : "  + prdEvtList[i].EVNT_APPLY_AMT);
    		                		if (itemCount < 2) {
            		                	realItemExtraQtyList.get(m).value = Number(prdEvtList[i].EVNT_APPLY_AMT);
    		                		}
    		                	}
	     		                else{
        		                	realItemExtraQtyList.get(m).value = Number(realItemExtraQtyList.get(m).value) + Number(prdEvtList[i].EVNT_APPLY_AMT);
	        		            }
    		                }
    		            }
					}
					else if (prdEvtList[i].PROMO_CL_TYP_CD != CONST.get("EVT_TYPE_CD_MEMBERSPOINTSAVE")
							&& prdEvtList[i].PROMO_CL_TYP_CD != CONST.get("EVT_TYPE_CD_CHLD")) {
           				realProdDc += Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT) ;

						if(BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_GIFTMALL") || BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_SPEC")){
							multiDelivPromotion.push({
	            				type: "realItemDcBasicAmt",
                				promoNo: prdEvtList[i].PROMO_CL_MST_NO,
	            				prodCd: prdEvtList[i].PROD_CD,
	            				itemCd: prdEvtList[i].ITEM_CD,
	            				qty: Number(prdEvtList[i].EVNT_APPLY_QTY),
	            				price: Number(prdEvtList[i].EVNT_APPLY_AMT)
	            			});
						}

						var realItemDcBasicAmtList = $('input[name=realItemDcBasicAmt]');
       		            for(var m = 0 ; m < realItemDcBasicAmtList.length ; m++){
       		                // 동일 상품에 대해서 적용
       		                if($('input[name=itemProdCd]').get(m).value == prdEvtList[i].PROD_CD
       		                		&& $('input[name=itemItemCd]').get(m).value == prdEvtList[i].ITEM_CD){
       		                	if(BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_GIFTMALL") || BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_SPEC")){
     		                		realItemDcBasicAmtList.get(m).value = Number(realItemDcBasicAmtList.get(m).value) + Number($('input[name=itemBsketQty]').get(m).value) * Number(prdEvtList[i].EVNT_APPLY_AMT);
	       		                }
     		                	else{
	       		                	realItemDcBasicAmtList.get(m).value = Number(realItemDcBasicAmtList.get(m).value)
	       		                    	+ Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT) ;
		       		                if (prdEvtList[i].BOS_EVT_KIND != '2') {
        		            			appendProductDcItem(prdEvtList[i].PROD_CD, prdEvtList[i].PRE_ORDER_ITEM_SEQ, prdEvtList[i].PROMO_CL_MST_NO, 'E'+prdEvtList[i].PROMO_CL_TYP_CD, (Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT)), "원");
		       		                }
		       		                else {
        		            			appendProductDcItem(prdEvtList[i].PROD_CD, prdEvtList[i].PRE_ORDER_ITEM_SEQ, prdEvtList[i].PROMO_CL_MST_NO, 'C'+prdEvtList[i].PROMO_CL_TYP_CD, (Number(prdEvtList[i].EVNT_APPLY_QTY) * Number(prdEvtList[i].EVNT_APPLY_AMT)), "원");
		       		                }
	       		                }
       		                }
       		            }
					}
				}
			} // END of for (prdEvtList)
    	}

    	{ // 덤 화면 처리
    		var realItemExtraQtyList = $("input[name=realItemExtraQty]");
    		for(var m = 0 ; m < realItemExtraQtyList.length ; m++) {
    			//console.log("realItemExtraQtyList=" + realItemExtraQtyList.eq(m).val());
    			if (Number(realItemExtraQtyList.eq(m).val()) > 0) {
    				var extraQtyList = $("li[name=pExtraQty]");
    				$("li[name=pExtraQty]").eq(m).html("M+N : " + Number(realItemExtraQtyList.get(m).value) + "개 추가 증정");
    				$('td.pProdExtraQty').eq(m).html(Number($('input[name=itemBsketQty]').get(m).value) + "<p>(+" + Number(realItemExtraQtyList.get(m).value) + "개)</p>");
    				$($('td.pProdExtraQty').eq(m)).closest("tr").attr("data-EXTRA_QTY", realItemExtraQtyList.get(m).value);
    				$($("li[name=pExtraQty]").eq(m)).show();
    			}
    		}
    	}

    	var trdEvtList = jsonData.Trade;
    	if (trdEvtList != null && trdEvtList.length > 0) {
    		for (var i = 0; i < trdEvtList.length; i++) {

    			if (trdEvtList[i].BOS_EVT_KIND == '1') {
    				if (trdEvtList[i].PAY_COND_FG == '01') {
    					var tmpIdx = realCardEvtList.length;
    					realCardEvtList[tmpIdx] = new Array();
    					realCardEvtList[tmpIdx] = {
    	   						 PROD_TRADE_DIVN : trdEvtList[i].PROD_TRADE_DIVN
    	  						,PROD_CD : trdEvtList[i].PROD_CD
    	  						,ITEM_CD : trdEvtList[i].ITEM_CD
    	  						,PRE_ORDER_ITEM_SEQ : trdEvtList[i].PRE_ORDER_ITEM_SEQ
    	  						,PAY_COND_FG : trdEvtList[i].PAY_COND_FG
    	  						,CARDCO_CD : trdEvtList[i].CARDCO_CD
    	  						,BOS_EVT_KIND : trdEvtList[i].BOS_EVT_KIND
    	  						,PROMO_CL_TYP_CD : trdEvtList[i].PROMO_CL_TYP_CD
    	  						,PROMO_CL_MST_NO : trdEvtList[i].PROMO_CL_MST_NO
    	  						,PROMO_CL_SEQ : trdEvtList[i].PROMO_CL_SEQ
    	  						,EVNT_APPLY_QTY : trdEvtList[i].EVNT_APPLY_QTY
    	  						,EVNT_APPLY_AMT : trdEvtList[i].EVNT_APPLY_AMT
    	  						,PROMO_KIND_CD : trdEvtList[i].PROMO_KIND_CD
    	  						,PRVS_TO_NO : trdEvtList[i].PRVS_TO_NO
    	  						,SCP_STR_CD : trdEvtList[i].SCP_STR_CD
    	  						,DC_EVT_CONTENT : trdEvtList[i].DC_EVT_CONTENT
    	  						,REP_COUPON_ID : trdEvtList[i].REP_COUPON_ID
    	  						,PROD_SELL_QTY : trdEvtList[i].PROD_SELL_QTY
    	  						,OFFER_TY : trdEvtList[i].OFFER_TY
    	  						,MD_EVT_CD : trdEvtList[i].MD_EVT_CD
    					}

    					realAllCardDc += Number(trdEvtList[i].EVNT_APPLY_QTY) * Number(trdEvtList[i].EVNT_APPLY_AMT);
    					realCardDc += Number(trdEvtList[i].EVNT_APPLY_QTY) * Number(trdEvtList[i].EVNT_APPLY_AMT);

    					tradeCardDc += Number(trdEvtList[i].EVNT_APPLY_QTY) * Number(trdEvtList[i].EVNT_APPLY_AMT);
    				}
    				else {
    					var tmpIdx = realTradeEvtList.length;
    	    			realTradeEvtList[tmpIdx] = new Array();
    	    			realTradeEvtList[tmpIdx] = {
       						 PROD_TRADE_DIVN : trdEvtList[i].PROD_TRADE_DIVN
     						,PROD_CD : trdEvtList[i].PROD_CD
     						,ITEM_CD : trdEvtList[i].ITEM_CD
     						,PRE_ORDER_ITEM_SEQ : trdEvtList[i].PRE_ORDER_ITEM_SEQ
     						,PAY_COND_FG : trdEvtList[i].PAY_COND_FG
     						,CARDCO_CD : trdEvtList[i].CARDCO_CD
     						,BOS_EVT_KIND : trdEvtList[i].BOS_EVT_KIND
     						,PROMO_CL_TYP_CD : trdEvtList[i].PROMO_CL_TYP_CD
     						,PROMO_CL_MST_NO : trdEvtList[i].PROMO_CL_MST_NO
     						,PROMO_CL_SEQ : trdEvtList[i].PROMO_CL_SEQ
     						,EVNT_APPLY_QTY : trdEvtList[i].EVNT_APPLY_QTY
     						,EVNT_APPLY_AMT : trdEvtList[i].EVNT_APPLY_AMT
     						,PROMO_KIND_CD : trdEvtList[i].PROMO_KIND_CD
	  						,PRVS_TO_NO : trdEvtList[i].PRVS_TO_NO
	  						,SCP_STR_CD : trdEvtList[i].SCP_STR_CD
	  						,DC_EVT_CONTENT : trdEvtList[i].DC_EVT_CONTENT
	  						,REP_COUPON_ID : trdEvtList[i].REP_COUPON_ID
	  						,PROD_SELL_QTY : trdEvtList[i].PROD_SELL_QTY
	  						,OFFER_TY : trdEvtList[i].OFFER_TY
	  						,MD_EVT_CD : trdEvtList[i].MD_EVT_CD
    	    			};

    	    			if (trdEvtList[i].OFFER_TY != CONST.get("MD_OFFER_TYPE_PNTALL")
    	    					&& trdEvtList[i].OFFER_TY != CONST.get("MD_OFFER_TYPE_PBNPNT")
    	    					&& trdEvtList[i].OFFER_TY != CONST.get("MD_OFFER_TYPE_PBIPNT")
    	    					&& trdEvtList[i].OFFER_TY != CONST.get("MD_OFFER_TYPE_PNTGIFT")) {
							realTradeDc += Number(trdEvtList[i].EVNT_APPLY_QTY) * Number(trdEvtList[i].EVNT_APPLY_AMT);
    	    			}
    				}
    			}
    			else if (trdEvtList[i].BOS_EVT_KIND == '2') {
    				if (trdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_ONLINEAPY")			//온라인지불
							|| trdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_PAYDM") 		// 지불DM
							|| trdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_SCP_PAY_SAVU") // 스마트쿠폰(지불적립)
							|| trdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_SCP_PAY_DC")	// 스마트쿠폰(지불할인)
							|| trdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_POINT")) {		// 적립쿠폰
						// 지불쿠폰

						var tmpIdx = realCpnEvtList.length;
						realCpnEvtList[tmpIdx] = new Array();
						realCpnEvtList[tmpIdx] = {
	       						 PROD_TRADE_DIVN : trdEvtList[i].PROD_TRADE_DIVN
	      						,PROD_CD : trdEvtList[i].PROD_CD
	      						,ITEM_CD : trdEvtList[i].ITEM_CD
	      						,PRE_ORDER_ITEM_SEQ : trdEvtList[i].PRE_ORDER_ITEM_SEQ
	      						,PAY_COND_FG : trdEvtList[i].PAY_COND_FG
	      						,CARDCO_CD : trdEvtList[i].CARDCO_CD
	      						,BOS_EVT_KIND : trdEvtList[i].BOS_EVT_KIND
	      						,PROMO_CL_TYP_CD : trdEvtList[i].PROMO_CL_TYP_CD
	      						,PROMO_CL_MST_NO : trdEvtList[i].PROMO_CL_MST_NO
	      						,PROMO_CL_SEQ : trdEvtList[i].PROMO_CL_SEQ
	      						,EVNT_APPLY_QTY : trdEvtList[i].EVNT_APPLY_QTY
	      						,EVNT_APPLY_AMT : trdEvtList[i].EVNT_APPLY_AMT
	      						,PROMO_KIND_CD : trdEvtList[i].PROMO_KIND_CD
    	  						,PRVS_TO_NO : trdEvtList[i].PRVS_TO_NO
    	  						,SCP_STR_CD : trdEvtList[i].SCP_STR_CD
    	  						,DC_EVT_CONTENT : trdEvtList[i].DC_EVT_CONTENT
    	  						,REP_COUPON_ID : trdEvtList[i].REP_COUPON_ID
    	  						,PROD_SELL_QTY : trdEvtList[i].PROD_SELL_QTY
    	  						,OFFER_TY : trdEvtList[i].OFFER_TY
    	  						,MD_EVT_CD : trdEvtList[i].MD_EVT_CD
    	  						,COUPON_ID : trdEvtList[i].COUPON_ID
						}

	    				if (trdEvtList[i].PAY_COND_FG == '01'
	    						|| (trdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_ONLINEAPY")
	    								&& trdEvtList[i].CARDCO_CD != null
	    								&& trdEvtList[i].CARDCO_CD != ''
	    								)) {
	    					realAllCardDc += Number(trdEvtList[i].EVNT_APPLY_QTY) * Number(trdEvtList[i].EVNT_APPLY_AMT);

	    					if (trdEvtList[i].PROMO_CL_TYP_CD != CONST.get("COUPON_TYPE_CD_POINT")) {
	    						//realCardDc += Number(trdEvtList[i].EVNT_APPLY_QTY) * Number(trdEvtList[i].EVNT_APPLY_AMT);
	    					}
	    				}

						$("li.payDcItems").hide();
						if (trdEvtList[i].PROMO_CL_TYP_CD != CONST.get("COUPON_TYPE_CD_SCP_PAY_SAVU")
								&& trdEvtList[i].PROMO_CL_TYP_CD != CONST.get("COUPON_TYPE_CD_POINT")) {
							$("#payDc").val(Number($("#payDc").val()) + Number(trdEvtList[i].EVNT_APPLY_QTY) * Number(trdEvtList[i].EVNT_APPLY_AMT));
						}
						$("[id='payDc_" + trdEvtList[i].COUPON_ID + "']").show();
						$("input[name='couponChecked2'][COUPON_ID='" + trdEvtList[i].COUPON_ID + "']").propChecked(true);
						
						//카드사 지불 쿠폰 적용인 경우
						if(typeof cardDmChk != 'undefined'){
							if(trdEvtList[i].BOS_EVT_KIND == '2' && trdEvtList[i].PROMO_CL_TYP_CD == CONST.get("COUPON_TYPE_CD_ONLINEAPY") && trdEvtList[i].CARDCO_CD != ""){
								cardDmChk = true;
							} else {
								cardDmChk = false;
							}
						}
    				}
    			}

    		} // END of for(trdEvtList)
    	} else {
    		if(typeof cardDmChk != 'undefined'){
    			cardDmChk = false;
			}
    	}

    	var delivCpnList = jsonData.DelivCpn;
    	if (delivCpnList != null && delivCpnList.length > 0) {
			var tmpIdx = realCpnEvtList.length;
			realCpnEvtList[tmpIdx] = new Array();
			realCpnEvtList[tmpIdx] = {
						 PROD_TRADE_DIVN : delivCpnList[0].PROD_TRADE_DIVN
						,PROD_CD : delivCpnList[0].PROD_CD
						,ITEM_CD : delivCpnList[0].ITEM_CD
						,PRE_ORDER_ITEM_SEQ : delivCpnList[0].PRE_ORDER_ITEM_SEQ
						,PAY_COND_FG : delivCpnList[0].PAY_COND_FG
						,CARDCO_CD : delivCpnList[0].CARDCO_CD
						,BOS_EVT_KIND : delivCpnList[0].BOS_EVT_KIND
						,PROMO_CL_TYP_CD : delivCpnList[0].PROMO_CL_TYP_CD
						,PROMO_CL_MST_NO : delivCpnList[0].PROMO_CL_MST_NO
						,PROMO_CL_SEQ : delivCpnList[0].PROMO_CL_SEQ
						,EVNT_APPLY_QTY : delivCpnList[0].EVNT_APPLY_QTY
						,EVNT_APPLY_AMT : delivCpnList[0].EVNT_APPLY_AMT
						,PROMO_KIND_CD : delivCpnList[0].PROMO_KIND_CD
						,PRVS_TO_NO : delivCpnList[0].PRVS_TO_NO
						,SCP_STR_CD : delivCpnList[0].SCP_STR_CD
						,DC_EVT_CONTENT : delivCpnList[0].DC_EVT_CONTENT
						,REP_COUPON_ID : delivCpnList[0].REP_COUPON_ID
						,PROD_SELL_QTY : delivCpnList[0].PROD_SELL_QTY
						,OFFER_TY : delivCpnList[0].OFFER_TY
						,MD_EVT_CD : delivCpnList[0].MD_EVT_CD
						,COUPON_ID : delivCpnList[0].COUPON_ID
						,DC_UNIT_CD: delivCpnList[0].DC_UNIT_CD
			};

			if (delivCpnList[0].PROMO_KIND_CD == CONST.get("PROMO_KIND_CD_COUPON")) {// && delivAmt >= (Number(delivCpnList[0].EVNT_APPLY_QTY) * Number(delivCpnList[0].EVNT_APPLY_AMT)) ) {
				if(delivCpnList[0].DC_UNIT_CD == '02'){
					$("#deliveryDc").val(Number($("#TOTAL_ORG_DELIV_AMT_MART").val()) * Number(delivCpnList[0].DC_EVT_CONTENT) / 100);
				} else {
					$("#deliveryDc").val(Number($("#deliveryDc").val()) + Number(delivCpnList[0].EVNT_APPLY_QTY) * Number(delivCpnList[0].EVNT_APPLY_AMT));
				}
				$("li.deliveryDcItems").hide();
				$("[id='deliveryDc_" + delivCpnList[0].COUPON_ID + "']").show();
				$("input[name='couponChecked3'][COUPON_ID='" + delivCpnList[0].COUPON_ID + "']").propChecked(true).show();
			}
    	}

    	var staffDcList = jsonData.Staff;
    	if (staffDcList != null && staffDcList.length > 0) {
    		for (var i = 0; i < staffDcList.length; i++) {
	    		var tmpIdx = realStaffDcList.length;
	    		realStaffDcList[tmpIdx] = new Array();
	    		realStaffDcList[tmpIdx] = {
  						 PROD_TRADE_DIVN : staffDcList[i].PROD_TRADE_DIVN
      					,PROD_CD : staffDcList[i].PROD_CD
      					,ITEM_CD : staffDcList[i].ITEM_CD
      					,PRE_ORDER_ITEM_SEQ : staffDcList[i].PRE_ORDER_ITEM_SEQ
      					,PAY_COND_FG : staffDcList[i].PAY_COND_FG
      					,CARDCO_CD : staffDcList[i].CARDCO_CD
      					,BOS_EVT_KIND : staffDcList[i].BOS_EVT_KIND
      					,PROMO_CL_TYP_CD : staffDcList[i].PROMO_CL_TYP_CD
      					,PROMO_CL_MST_NO : staffDcList[i].PROMO_CL_MST_NO
      					,PROMO_CL_SEQ : staffDcList[i].PROMO_CL_SEQ
      					,EVNT_APPLY_QTY : staffDcList[i].EVNT_APPLY_QTY
      					,EVNT_APPLY_AMT : staffDcList[i].EVNT_APPLY_AMT
      					,PROMO_KIND_CD : staffDcList[i].PROMO_KIND_CD
	  						,PRVS_TO_NO : staffDcList[i].PRVS_TO_NO
	  						,SCP_STR_CD : staffDcList[i].SCP_STR_CD
	  						,DC_EVT_CONTENT : staffDcList[i].DC_EVT_CONTENT
	  						,REP_COUPON_ID : staffDcList[i].REP_COUPON_ID
	  						,PROD_SELL_QTY : staffDcList[i].PROD_SELL_QTY
	  						,OFFER_TY : staffDcList[i].OFFER_TY
	  						,MD_EVT_CD : staffDcList[i].MD_EVT_CD
				};

                $('#empDc').val(Number($('#empDc').val()) + Number(staffDcList[i].EVNT_APPLY_QTY) * Number(staffDcList[i].EVNT_APPLY_AMT));
                realProdDc += Number(staffDcList[i].EVNT_APPLY_QTY) * Number(staffDcList[i].EVNT_APPLY_AMT);

				if(BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_GIFTMALL") || BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_SPEC")){
					multiDelivPromotion.push({
        				type: "itemDcStaffAmt",
        				promoNo: staffDcList[i].PROMO_CL_MST_NO,
        				prodCd: staffDcList[i].PROD_CD,
        				itemCd: staffDcList[i].ITEM_CD,
        				qty: Number(staffDcList[i].EVNT_APPLY_QTY),
        				price: Number(staffDcList[i].EVNT_APPLY_AMT)
        			});
				}

	            var itemDcStaffAmtList = $('input[name=itemDcStaffAmt]');
	            for(var m = 0 ; m < itemDcStaffAmtList.length ; m++){
	                // 동일 상품에 대해서 적용
	                if($('input[name=itemProdCd]').get(m).value == staffDcList[i].PROD_CD
	                		&& $('input[name=itemItemCd]').get(m).value == staffDcList[i].ITEM_CD){
	                	if(BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_GIFTMALL") || BSKET_TYPE_CD == CONST.get("BASKET_TYPE_MART_SPEC")){
	                		itemDcStaffAmtList.get(m).value = Number(itemDcStaffAmtList.get(m).value) + Number($('input[name=itemBsketQty]').get(m).value) * Number(staffDcList[i].EVNT_APPLY_AMT);
		                }
	                	else{
	                		itemDcStaffAmtList.get(m).value = Number(itemDcStaffAmtList.get(m).value)
		                    	+ Number(staffDcList[i].EVNT_APPLY_QTY) * Number(staffDcList[i].EVNT_APPLY_AMT) ;
	                		$("[id='dc-" + staffDcList[i].PROD_CD + "-" + staffDcList[i].PRE_ORDER_ITEM_SEQ + "'] ul").append("<li data-check='6'>임직원할인<em class=\"txt-red flt-right\">" + util.currency(Number(staffDcList[i].EVNT_APPLY_QTY) * Number(staffDcList[i].EVNT_APPLY_AMT)) + "원</em></li>");
		                }
	                }
	            }
    		}
    	}
	}
	
	calcMultiDelivPromoion();
	
	$('#billDc').val(realTradeDc);

	var calcDivn = jsonData.CalcDivn;
	if (calcDivn == null || calcDivn != 'Trade') {

    	//<%-- 사용 가능한 결제쿠폰과 배송비쿠폰 제한하기 --%>
    	var totSellAmt4th = jsonData.TotSellAmt4th;
    	var totSellAmt4thPay = jsonData.TotSellAmt4thPay;

    	if (totSellAmt4th != null) {
			var enableDelivCpnCnt = 0;
			var enablePayDcCpnCnt = 0;

			totSellAmt4Trade = Number(totSellAmt4th);

    		$("[name=couponChecked3]").not("[REP_COUPON_ID='0']").each(function (k, obj) {
    			var $this = $(obj);
    			var radioArea = $this.closest("tr");

    			$this.propDisabled(true);
    			radioArea.hide();

    			var frAmt = Number($this.attr("USE_MIN_AMT"));
    			var toAmt = Number($this.attr("USE_MAX_AMT"));
    			var dcAmt = Number($this.attr("COUPON_DC_AMT"));

    			if (totSellAmt4Trade >= frAmt && totSellAmt4Trade <= toAmt && delivAmt >= dcAmt) {
    				$this.propDisabled(false);
    				radioArea.show();

    				enableDelivCpnCnt++;
    			}
    		});
    		calc.countCoupon("3");

    		totSellAmt4Trade = Number(totSellAmt4thPay);
    		$("[name=couponChecked2]").not("[REP_COUPON_ID='0']").each(function () {
    			var $this = $(this);
    			var radioArea = $this.closest("tr");

    			$this.propDisabled(true);
    			radioArea.hide();

    			var frAmt = Number($this.attr("USE_MIN_AMT"));
    			var toAmt = Number($this.attr("USE_MAX_AMT"));
    			if (totSellAmt4Trade >= frAmt && totSellAmt4Trade <= toAmt) {
    				$this.propDisabled(false);
    				radioArea.show();
    				enablePayDcCpnCnt++;
    			}
    			if ($this.is(":checked") && $this.is(":disabled")) {
    				$this.propChecked(false);
    			}
    		});

    		calc.countCoupon("2");

    		/* 2018.09.07 지불/배송비쿠폰노출개선 수정 이전
    		if (isFirstLoad) {
	    		// 최대 할인 쿠폰 적용
	    		for (var i = 2; i <= 3; i++) {
	    			var tempCpnArr = new Array();
					var tempAmtArr = new Array();
	
	    			$('input[name=couponChecked' + i + ']').not("[REP_COUPON_ID='0']").not(":disabled").each(function(k, obj) {
	    				var $this = $(obj);
	   					tempCpnArr.push($this);
						tempAmtArr.push($this.attr('WILL_DISCNT_AMT'));
	    			});
	
	    			if (tempCpnArr.length > 0) {
						var maxVal = Math.max.apply(null,tempAmtArr)+"";
						var maxIdx = $.inArray(maxVal,tempAmtArr);
						if(tempAmtArr.length > 0){
							$(tempCpnArr[maxIdx]).propChecked(true);
						}
	    			}
				}
    		}
    		*/
    		
    		// 2018.09.07 지불/배송비쿠폰노출개선 수정 이후
    		// 최초 로딩시 지불/배송비쿠폰에서 가장 금액이 큰 쿠폰을 선택, 이후 로딩시에는 지불쿠폰에서만 가장 큰 쿠폰을 선택
    		for (var i = 2; i <= (isFirstLoad?3:2); i++) {	// 최대 할인 쿠폰 적용
    			var tempCpnArr = new Array();
				var tempAmtArr = new Array();

    			$('input[name=couponChecked' + i + ']').not("[REP_COUPON_ID='0']").not(":disabled").each(function(k, obj) {
    				// 2018.09.07 허들금액 범위를 벗어난 지불/배송비 쿠폰에 대해서는 체크하지 않도록 처리
    				var $this = $(obj);
        			var frAmt = Number($this.attr("USE_MIN_AMT"));
        			var toAmt = Number($this.attr("USE_MAX_AMT"));
        			
        			var cardCheckYn = true; // 카드체크여부
        			if( i == 2 ){ // 지불쿠폰에 카드사가 걸려있을 경우 결제방법의 카드사정보와 비교
        				var cardCoCd = util.nvl($this.attr("cardco_cd"),"");	// 지불쿠폰의 카드사정보
        				var rpCardCoCd = "";									// 결제방법의 카드사정보
        				// 쿠폰에 카드가 걸려있는 경우
        				if( cardCoCd != "" ){
	        			    // 카드결제금액과 결제예정포인트 비교
	        				var rpCardCd = "";
	        				var lPointAmt = $("#lottePoint").val();
	        				var point1Amt = $("#point1").val();
	        				var point2Amt = 0;
	        				var point3Amt = $("#point3").val();
	        				var point4Amt = $("#point4").val();
	        				var smtGiftAmt = 0;
	        				var allPointAmt = Number(lPointAmt) + Number(smtGiftAmt) + Number(point1Amt) + Number(point2Amt) + Number(point3Amt) + Number(point4Amt);
	        				var totalPrice = Number($('#totSellPrc').val()) + Number($('#TOTAL_ORG_DELIV_AMT').val()) + Number($('#TOTAL_ORG_DELIV2_AMT').val());
	        				
	        				if(allPointAmt <  totalPrice){
	        			        if($('input[name=paymentType]:checked').val() === 'card'){
	        			        	rpCardCoCd = $('#cardCd').val();
	        			        }else if($('input[name=paymentType]:checked').val() === "kakao"){
	        			        	rpCardCoCd = $('#kakaoCardCd').val();
	        			        }else if($('input[name=paymentType]:checked').val() === "lpay"){
	        			        	rpCardCoCd = $('#lpayCardCd').val();
	        			        }
	        				}
	        				
	        				if( cardCoCd != rpCardCoCd ){
	        					cardCheckYn = false;
	        				}
        				}
        			}
        			if( totSellAmt4Trade >= frAmt && totSellAmt4Trade <= toAmt && cardCheckYn ){
	   					tempCpnArr.push($this);
						tempAmtArr.push($this.attr('WILL_DISCNT_AMT'));
        			}
    			});

    			if (tempCpnArr.length > 0) {
					var maxVal = Math.max.apply(null,tempAmtArr)+"";
					var maxIdx = $.inArray(maxVal,tempAmtArr);
					if(tempAmtArr.length > 0){
					//if(tempAmtArr.length > 0 && !$("#couponChecked"+i+"_no").prop("checked") ){	// 쿠폰적용안함여부가 체크되어있을 경우 쿠폰적용 안함
						$(tempCpnArr[maxIdx]).propChecked(true);
						$("#couponChecked"+i+"_no").propChecked(false); // 쿠폰적용 안함
					}else{
						$("#couponChecked"+i+"_no").propChecked(true);  // 쿠폰적용 안함
					}
    			}
			}
    	}

    	//<%-- MD 사은품 --%>
	    var $cmplTbody = $('#cmplTbody'); // 사은품 선택 영역
	    var cmplList = "";
	    var $cmplTrSelect = $('#cmplDcTbody'); // 할인/증정 선택형 영역
	    var cmplDcList = "";
	    realMdCmpl01Cnt = 0;
	    realMdCmplList = new Array();

	    var mdCmplList = jsonData.MdCmpl;
	    var promoStrCd = jsonData.STR_CD;
		var multiSelectCnt = 0;
	    if (mdCmplList != null && mdCmplList.length > 0) {
	    	var mdCmplMstId = "";
	    	var mdGrpng = "";
	    	var addRow = true;
			var cmplNm = "";
	    	var cmplTableView = false;
	    	var cmplDcTableView = false;
	    	$("#cmplTbody").empty();
	    	$.each(mdCmplList, function(i, mdCmpl) {
		    	// 할인/증정 일경우 skip
	    		if (mdCmpl.OFFER_TY == '4005' && mdCmpl.DSCNT_AMT != null && Number(mdCmpl.DSCNT_AMT) > 0) {
			    	return;
	    		}

	    		cmplTableView = true;
	    		if (mdCmpl.PAY_COND_FG == '01') {
	    			realAllCardDc += 1;
	    		}

    			if (mdCmpl.GIFT_TY == '02' || mdCmpl.GIFT_TY == '06' ) { // 상품권
    				cmplNm = "마일리지: " + util.currency(mdCmpl.GIFT_PRICE) + " 점";
    			}
    			else {
    				cmplNm = mdCmpl.CMPL_NM;
    			}

    			var tr = $("#cmplTbody tr:last")
	    		addRow = (mdCmplMstId != mdCmpl.CMPL_MST_ID || mdGrpng != mdCmpl.GRPNG);
	    		if (addRow) {
		    		$("#cmplTbody").append("<tr name='trCmpl_" + mdCmpl.CMPL_MST_ID + '_' + mdCmpl.GRPNG + "'></tr>");
	    			tr = $("#cmplTbody tr:last")
		    		$(tr).append("<th scope=\"row\"><span class=\"require\"><i>필수항목</i></span>"+ mdCmpl.CMPL_MST_NM + "</th>")
		    			.append("<td></td>");
	    			$(tr).find("td").append("<div class=\"select-type1 w-type80\"></div>");
	    			$(tr).find("td > div").append("<label for=\"\">" + cmplNm + "</label>")
	    				.append("<select name=\"mdCmplGoods\"></select>");
	    			realMdCmpl01Cnt++;
    			}
			    $(tr).find("select")
			    	.append('<option '
						+ ' value="' + mdCmpl.CMPL_MST_ID + util.nvl(mdCmpl.PROD_CD,'_') + util.nvl(mdCmpl.ITEM_CD,'_')
						+ '" CMPL_MST_ID="' + mdCmpl.CMPL_MST_ID
						+ '" GIFT_TY="' + mdCmpl.GIFT_TY
						+ '" PROD_CD="' + util.nvl(mdCmpl.PROD_CD,'')
						+ '" ITEM_CD="' + util.nvl(mdCmpl.ITEM_CD,'')
						+ '" GRPNG="' + util.nvl(mdCmpl.GRPNG,'') + '" >'
						+ cmplNm + '</option>');
			    mdGrpng = mdCmpl.GRPNG;
			    mdCmplMstId = mdCmpl.CMPL_MST_ID;
	    	});
    		if (cmplTableView) {
    			$("#cmplH3").show();
    			$("#cmplStr").show();
    		}
    		else {
    			$("#cmplH3").hide();
    			$("#cmplStr").hide();
    		}
    		mdGrpng = "";

    		$("#cmplDcTbody").empty();
	    	$.each(mdCmplList, function(i, mdCmpl) {
    			var cmplDcAmt = mdCmpl.DSCNT_AMT;
		    	// 할인/증정
	    		if (mdCmpl.OFFER_TY == '4005' && mdCmpl.DSCNT_AMT != null && Number(cmplDcAmt) > 0) {

		    		cmplDcTableView = true;

		    		if (mdCmpl.PAY_COND_FG == '01') {
		    			realAllCardDc += 1;
		    		}

	    			if (mdCmpl.GIFT_TY == '02' || mdCmpl.GIFT_TY == '06' ) { // 상품권
	    				cmplNm = "마일리지: " + util.currency(mdCmpl.GIFT_PRICE) + " 점";
	    			}
	    			else {
	    				cmplNm = mdCmpl.CMPL_NM;
	    			}

	    			var tr = $("<tr></tr>");
		    		$(tr).attr({
				    	name: "trCmpl_" + mdCmpl.CMPL_MST_ID + '_' + mdCmpl.GRPNG,
		    		})
		    		.append("<th scope=\"row\"><span class=\"require\"><i>필수항목</i></span>"+ mdCmpl.CMPL_MST_NM + "</th>")
		    		.append("<td></td>");
	    			$(tr).find("td").append("<ul class=\"radio-list-h\"></ul>");
	    			$(tr).find("td > ul").append('<li><span class=\"radio-data\"><input type="radio" id="' + mdCmpl.CMPL_MST_ID + '_' + mdCmpl.GRPNG + '_dc" name="cmplDc_' + mdCmpl.CMPL_MST_ID + '_' + mdCmpl.GRPNG + '" value="' + cmplDcAmt + '" checked=\"checked\" '
    				+ 'PROD_TRADE_DIVN="Trade" '
    				+ 'PROD_CD="" '
					+ 'ITEM_CD="" '
					+ 'PRE_ORDER_ITEM_SEQ="" '
					+ 'PAY_COND_FG="' + mdCmpl.PAY_COND_FG + '" '
					+ 'CARDCO_CD="' + mdCmpl.CARDCO_CD + '" '
					+ 'BOS_EVT_KIND="1" '
					+ 'PROMO_CL_TYP_CD="27" '
					+ 'PROMO_CL_MST_NO="' + mdCmpl.EVT_MST_SEQ + '" '
					+ 'PROMO_CL_SEQ="' + mdCmpl.EVT_SEQ + '" '
					+ 'EVNT_APPLY_QTY="1" '
					+ 'EVNT_APPLY_AMT="' + cmplDcAmt + '" '
					+ 'PROMO_KIND_CD="01" '
					+ 'PRVS_TO_NO="" '
					+ 'SCP_STR_CD="" '
					+ 'DC_EVT_CONTENT="' + mdCmpl.DC_EVT_CONTENT + '" '
					+ 'REP_COUPON_ID="" '
					+ 'PROD_SELL_QTY="' + util.nvl(mdCmpl.SELL_CNT,'0') + '" '
					+ 'OFFER_TY="4005" '
					+ 'MD_EVT_CD="' + mdCmpl.MD_EVT_CD + '" '
					+ 'CMPL_MST_ID="' + mdCmpl.CMPL_MST_ID + '" '
					+ 'GRPNG="' + mdCmpl.GRPNG + '" '
					+ 'onClick="javascript:changeMdCmplDc(true);" '
    				+ '/></span><label for=\"\">할인 적용</label><span class=\"txt-wbrown\">(' + util.currency(Number(cmplDcAmt)) + " 원 할인)" + "</span><li>");
	    			$(tr).find("td > ul").append('<li><span class=\"radio-data\"><input type="radio" id="' + mdCmpl.CMPL_MST_ID + '_' + mdCmpl.GRPNG + '_cmpl" name="cmplDc_' + mdCmpl.CMPL_MST_ID + '_' + mdCmpl.GRPNG + '" ' 
	    					+'value="0" '
		    				+'CMPL_MST_ID="' + mdCmpl.CMPL_MST_ID + '" '
		    				+'GIFT_TY="' + mdCmpl.GIFT_TY + '" '
		    				+'PROD_CD="' + util.nvl(mdCmpl.PROD_CD,'') + '" '
		    				+'ITEM_CD="' + util.nvl(mdCmpl.ITEM_CD,'') + '" '
		    				+'OFFER_TY="4005" '
		    				+'GRPNG="' + mdCmpl.GRPNG + '" '
		    				+'onClick="javascript:changeMdCmplDc(true);" '
		    				+"/></span><label for=\"\">증정</label><span class=\"txt-wbrown\">("
		    				+ util.currency(Number(cmplDcAmt))
		    				+ ' P)'
		    				+ '</span><li>');
		    		$("#cmplDcTbody").append($(tr));
                	$('#cmplDc').val(Number($('#cmplDc').val()) + Number(cmplDcAmt));
	    			realMdCmpl01Cnt++;
	    		}
	    	});
    		if (cmplDcTableView) {
    			$("#cmplDcH3").show();
    			$("#cmplDcStr").show();
    		}
    		else {
    			$("#cmplDcH3").hide();
    			$("#cmplDcStr").hide();
    		}
	    }
	    else {
	    	$("#cmplTbody").empty();
	    	$("#cmplDcTbody").empty();	//재선택 후 데이터가 없으면 기존에 생성된 DOM 초기화 시켜줘야 함.
	    }
	}

    changeMdCmplDc(false);

	calc.all(false);

	//<%-- BOS 사은품(재고 체크용) --%>
	var bosCmplList = jsonData.BosCmpl;
	if (bosCmplList != null && bosCmplList.length > 0) {
		for (var i = 0; i < bosCmplList.length; i++) {
    		var tmpIdx = realBosCmplList.length;
    		realBosCmplList[tmpIdx] = new Array();
    		realBosCmplList[tmpIdx] = {
    				 CMPL_MST_ID : bosCmplList[i].CMPL_MST_ID
    				,EVT_TYPE_CD : bosCmplList[i].EVT_TYPE_CD
    				,PEST_UNIT_CD : bosCmplList[i].PEST_UNIT_CD
    				,CMPL_CD : bosCmplList[i].CMPL_CD
    		};
		}
	}

	// 상품쿠폰 적용여부 설정
	$("input[name=couponChecked1]").each(function(idx) {
		var $this = $(this);
		$this.propChecked(false);
	});
	//console.log(aProdCpnList);
	if (aProdCpnList != null && aProdCpnList.length > 0) {
		for (var pdx = 0; pdx < aProdCpnList.length; pdx++) {
			var aProdCpn = aProdCpnList[pdx];

			$("input[name=couponChecked1]").each(function(idx) {
				var $this = $(this);
				if ($this.attr('REP_COUPON_ID') == aProdCpn.REP_COUPON_ID && $this.attr('COUPON_ID') == aProdCpn.COUPON_ID) {
					if( aProdCpn.PROD_CD != null && aProdCpn.PROD_CD != "" && typeof aProdCpn.PROD_CD !== "undefined" ){
						if( $this.attr('PROD_CD') == aProdCpn.PROD_CD ){
							$this.coupon1PropChecked(true);	// 2018.07.02 상품쿠폰 체크 펑션 추가
						}
					}else{
						$this.coupon1PropChecked(true);	// 2018.07.02 상품쿠폰 체크 펑션 추가
					}

					//$this.propChecked(true);
				}
			});
		}
	}
	
	$("#preOrderItemBody .dc-pop").each(function(i, obj) {
		if ($(obj).find("li").length > 0) {
			$(obj).prev("a").show();
		}
	});
	layerPopRadio();

	if ($("input[name='couponChecked1']:checked").length <= 0 ) {
		$("#couponChecked1_no").propChecked(true);	// 쿠폰적용 안함
	}
	if ($("input[name='couponChecked2']:checked").length <= 0 ) {
		$("#couponChecked2_no").propChecked(true);	// 쿠폰적용 안함
	}
	if ($("input[name='couponChecked3']:checked").length <= 0 ) {
		$("#couponChecked3_no").propChecked(true);	// 쿠폰적용 안함
	}

	if (calcDivn == null || calcDivn != 'Trade') {
/*
		if (false) {
			// 결제쿠폰 선택
			var isCheckedValid = false;
			if ( $.trim(lastCheckedCoupon2) != '' && $.trim(lastCheckedCoupon2) != '0' ) {
				$('[name=couponChecked2]').each(function(i, cpn) {
					$(cpn).propChecked(false);
				});
				var obj = $("[name=couponChecked2][REP_COUPON_ID='" + lastCheckedCoupon2 + "']").not(":disabled");
				if (obj.length > 0 && obj.closest("tr").css("display") != "none") {
					isCheckedValid = true;
					obj.propChecked(true);
				}
			}
			if (!isCheckedValid) {
				$('#couponChecked2_no').propChecked(true);
			}
			// 배송비쿠폰 선택
			isCheckedValid = false;
			if ( $.trim(lastCheckedCoupon3) != '' && $.trim(lastCheckedCoupon3) != '0' ) {
				$('[name=couponChecked3]').each(function(i, cpn) {
					$(cpn).propChecked(false);
				});
				var obj = $("[name=couponChecked3][REP_COUPON_ID='" + lastCheckedCoupon3 + "']").not(":disabled");
				if (obj.length > 0 && obj.closest("tr").css("display") != "none") {
					isCheckedValid = true;
					obj.propChecked(true);
				}
			}
			if (!isCheckedValid) {
				$('#couponChecked3_no').propChecked(true);
			}
		}
*/
		if (isFirstLoad) {
			MultiDelivery.addFirst();
			isFirstLoad = false;
			if(null != BSKET_TYPE_CD && CONST.get("BASKET_TYPE_MART_GIFTMALL") == BSKET_TYPE_CD ){
				MultiDelivery.pristine = false;
				MultiDelivery.accept();
			}
			
		}
		fnRecalcPromotion("Trade");
	}else{
		promotion.load();
	}
};

//2018.07.02 상품쿠폰 체크 펑션 추가
jQuery.fn.extend({
	coupon1PropChecked: function(mode) {
		if (mode) {
			//$("[name='" + $(this).attr("name") + "']:checked").prop("checked", false).removeAttr("checked");
		}
		return this.each(function(i, obj) {
			if (mode) {
				$(obj).attr("checked", "checked");
			}
			else {
				$(obj).removeAttr("checked");
			}
			$(obj).prop("checked", mode);
			if ($(obj).parent().is(".check-data") || $(obj).parent().is(".radio-data")) {
				if (mode) {
					$(obj).parent().addClass("active");
				}
				else {
					$(obj).parent().removeClass("active");
				}
			}
		});
	}
});

function appendProductDcItem( prodCd, seq, promoNo, promoType, promoPrice, unitDesc) {
	var dcPrice = promoPrice;
	
	if ($("[id='dc-" + prodCd + "-" + seq + "'] ul li[data-promo='" + promoNo + "']").length > 0 ) {
		dcPrice = $("[id='dc-" + prodCd + "-" + seq + "'] ul li[data-promo='" + promoNo + "'] em").html();
		dcPrice = Number(dcPrice.replace(",", "").replace(unitDesc, ""));
		dcPrice += (Number(promoPrice));
		$("[id='dc-" + prodCd + "-" + seq + "'] ul li[data-promo='" + promoNo + "'] em").html(util.currency(dcPrice) + unitDesc);
	}
	else {
		$("[id='dc-" + prodCd + "-" + seq + "'] ul").append("<li data-promo='" + promoNo + "' data-promo_type='" + promoType + "'>" + getPromotionName(promoType) + "<em class=\"txt-red flt-right\">" +  util.currency(Number(dcPrice)) + unitDesc + "</em></li>");
	}
}

// 다중 배송 상품에 대한 프로모션 금액 계산
function calcMultiDelivPromoion() {
	var promoNoList = [];	// 프로모션ID 목록
	
	var pos;
	$.each(multiDelivPromotion, function(idx, promo) {
		var pos = promoNoList.indexOf(promo.promoNo);
		if ( typeof pos == "undefined" || pos < 0) {
			promoNoList.push(promo.promoNo);
		}
	});// each multiDelivPromotion
	
	// 프로모션 ID 별 금액 분할
	$.each(promoNoList, function(i, promoNo) {
		var promoList = $.grep(multiDelivPromotion, function(promo) {
			return promo.promoNo === promoNo;
		});
		
		if (promoList != null && promoList.length > 0) {
			$("[name=itemBsketQty]").removeAttr("data-REST");
			
			$.each(promoList, function(j, promoParam) {
				if (Number(promoParam.price) <= 0) {
					return;
				}
				var promoItem = $("[name=" + promoParam.type + "]");
				var bsketQty = 0;
				var temp = $.grep(MultiDelivery.prodList, function(prod) {
					return prod.PROD_CD == promoParam.prodCd && prod.ITEM_CD == promoParam.itemCd;
				});
				// 장바구니 수량
				if (temp != null && temp.length > 0) {
					bsketQty = Number(temp[0].BSKET_QTY) + Number(temp[0].EXTRA_QTY);
				}
				else {
					return;
				}
				var restQty = Number(promoParam.qty);
				$.each(promoItem, function(idx, item) {
					var promoBsketQty = Number($("[name=itemBsketQty]").eq(idx).attr("data-REST"));
					if ($.trim($("[name=itemBsketQty]").eq(idx).attr("data-REST")) == "") {
						promoBsketQty = Number($("[name=itemBsketQty]").eq(idx).val());
					}
					if (promoBsketQty < 1 || restQty < 1) {
						return;
					}
					if (promoBsketQty < restQty ) {
						//$(item).val(Number($(item).val()) + (promoBsketQty * Number(promoParam.price)));
						restQty = restQty - promoBsketQty;
						$("[name=itemBsketQty]").eq(idx).attr("data-REST", 0);
					}
					else {
						//$(item).val((Number($(item).val()) + (restQty * Number(promoParam.price))));
						$("[name=itemBsketQty]").eq(idx).attr("data-REST", promoBsketQty - restQty);
						restQty = 0;
					}
				});// each promoItem
			});// each promoList
		}
	});// each promoNoList
}

// 이벤트/ 쿠폰 > M스탬프 행사 레이어 하위 레이어팝업
function toggleLayerPop(src, dataName, e) {
	if (e == null && window.event != null) {
		e = window.event;
	}
	var parentObj = $(src).parent();
	if ($('.dc-pop', parentObj).css("display") != "none") {
		$(src).removeClass("active");
		$('.dc-pop').hide();
		return;
	}
	$(src).addClass("active");
	$('.dc-pop').hide();
	var topValue = $(src).outerHeight(true);
	$('.dc-pop', parentObj).css({top:(topValue + 6) + 'px'});
	$('.dc-pop', parentObj).show();
	$('.dc-pop', parentObj).find('.btn-ico-close').on('click', function ( e ) {
		$(src).removeClass("active");
		$('.dc-pop').hide();
		return false;
	});
	if (e != null) {
		if(typeof(e.preventDefault) != "undefined") {
			e.preventDefault();
		}
		else {
			e.returnValue = false;
		}
	}
}
