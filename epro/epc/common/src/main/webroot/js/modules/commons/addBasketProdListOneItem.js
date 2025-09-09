(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery', 'callAppScheme', 'fnJsMsg', 'isOnlyNumber'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('callAppScheme'), require('fnJsMsg'), require('isOnlyNumber') );
	} else {
		root.addBasketProdListOneItem = factory(jQuery, root.callAppScheme, root.fnJsMsg, root.isOnlyNumber);
	}
}( window, function($, callAppScheme, fnJsMsg, isOnlyNumber) {
	var _addBasketProdListOneItem = function(obj, p_prodCd, p_gubun, p_area_idx, _minQuantity, _maxQuantity) {
		var flag = global.isLogin(p_url);

	    if(flag) {
	        var prodIdx = "";
	        if(p_area_idx != null && p_area_idx != "" && p_area_idx != "undefined" && p_area_idx != undefined) {
	            prodIdx = "_" + p_area_idx;
	        }
	        /* 기본설정 */
	        var prodCd		 		= $("#prodCd_"+p_prodCd+prodIdx).val();
	        var categoryId	 		= $("#categoryId_"+p_prodCd+prodIdx).val();
	        var tmpOrdedQty 		= 1;
	        if(prodIdx != "") {
	            tmpOrdedQty 		= $("#orderQty_"+p_prodCd+prodIdx).val();
	        }
	        var minOrderQty 		= $("#minQty_"+p_prodCd+prodIdx).val();			//최소구매수량
	        var maxOrderQty 		= $("#maxQty_"+p_prodCd+prodIdx).val();			//현재 구매할수 있는 수량

	        var itemCd	 			= "001";								//단품코드

	        //01.수량 체크
	        if(isOnlyNumber(tmpOrdedQty) == false) {
	            alert(fnJsMsg(window.view_messages.error.orderQtyNumber));	//주문수량은 숫자만 입력 가능합니다.
	            return;
	        } else if(Number(tmpOrdedQty) < Number(minOrderQty)){
	            alert(fnJsMsg(window.view_messages.error.productOrderQty, minOrderQty, maxOrderQty));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
	            $("#orderQty_"+p_prodCd).val(minOrderQty);
	            return;
	        } else if(Number(tmpOrdedQty) > Number(maxOrderQty)){
	            alert(fnJsMsg(window.view_messages.error.productOrderQty, minOrderQty, maxOrderQty));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
	            $("#orderQty_"+p_prodCd).val(maxOrderQty);
	            return;
	        }
	        var basketItems=[];
	        basketItems.push({
	            prodCd: prodCd,				// 상품코드
	            itemCd: itemCd,				// 단품코드
	            bsketQty: Number(tmpOrdedQty),		// 주문수량
	            categoryId: categoryId,			// 카테고리ID
	            nfomlVariation: null,				// 옵션명, 골라담기의 경우 옵션명:수량
	            overseaYn: 'N',						// 해외배송여부
	            prodCouponId: null,				// 즉시할인쿠폰ID
	            oneCouponId: null,				// ONE 쿠폰ID
	            cmsCouponId: null,				// CMS 쿠폰ID
	            markCouponId: null,				// 마케팅제휴쿠폰ID
	            periDeliYn: "N"						// 정기배송여부
	        });
	        
	        if(p_gubun == "B") {
	        	window.global.addBasket(basketItems, function(data) {
	                callAppScheme("lottemartmall://basketcountupdate", $.utils.config( 'onlinemallApp' ) );
	                alert("선택하신 상품이 장바구니에 등록되었습니다.");
	            });
	        } else if(p_gubun == "D" ) {
	            window.global.addDirectBasket(basketItems, function(xhr){
	                var data = $.parseJSON(xhr.responseText);
	                if (data && data.message) {
	                    alert(data.message);
	                    return;
	                }
	            });
	        }
	    }
	};
	
	return _addBasketProdListOneItem;
}));