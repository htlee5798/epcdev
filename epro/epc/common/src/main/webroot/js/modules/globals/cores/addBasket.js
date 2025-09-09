(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define([
		        'jquery', 
		        'goLogout',
		        'familyJoin',
		        'isLogin'
		        ], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( 
				require( 'jquery' ),
				require( 'goLogout' ),
				require( 'familyJoin'),
				require( 'isLogin' ) 
		);
	} else {
		root.global = root.global || {};
		root.global.addBasket = factory( jQuery, root.goLogout, root.global.familyJoin, root.global.isLogin );
	}
}( window, function( $, goLogout, familyJoin, isLogin ) {
	var _addBasket = function(params, successFunc, failFunc) {
		var defaultItem = {
				prodCd: "",			// 상품코드
				itemCd: "001",			// 단품코드
				bsketQty: 1,			// 주문수량
				categoryId: "",		// 카테고리ID
				nfomlVariation: "",	// 옵션명, 골라담기의 경우 옵션명:수량
				overseaYn: 'N',			// 해외배송여부
				prodCouponId: "",		// 즉시할인쿠폰ID
				oneCouponId: "",		// ONE 쿠폰ID
				cmsCouponId: "",		// CMS 쿠폰ID
				markCouponId: "",		// 마케팅제휴쿠폰ID
				periDeliYn: 'N',		// 정기배송여부
				mstProdCd: "",		// 딜상품의 대표상품코드
				saveYn: "N",			// 계속담기 여부
				ctpdItemYn: "N",		// 구성품 여부
				ctpdProdCd: "",		// 구성품 원 상품코드
				ctpdItemCd: "",		// 구성품 원 단품코드
				ordReqMsg: "",		// 구매요청사항
				hopeDeliDy: ""		// 희망배송일
			};
			var items = [];
			var basketItem = null;
			var validResult = true;
			var successCallback = function(data) {
				if(window.fbq){
					// facebook dynamic remarketing 스크립트변경 2019-02-08
	            	fbq('track', 'AddToCart', {
	            		content_ids: data.basketItem.prodCd,
	                	content_type: 'product'
	                });
				}
				
				if (typeof(successFunc) != "undefined" && $.isFunction(successFunc)) {
					successFunc(data);

					var _prodTitle = $( '.basket' ).filter( '[data-prod-cd="' + data.basketItem.prodCd[0] + '"]' ).data('prod-title');

					window.ga('send', 'event', {
						eventAction: '장바구니 담기',
						eventCategory: 'M | Common | LotteMartMall | ' + location.pathname,
						eventLabel : 'CIP | PID | ' + _prodTitle
					});
					var checkedItems = $.cookie('checkedItemTypeA');	// 일반장바구니 쿠키
					checkedItems = (typeof checkedItems =='undefined')?"":checkedItems+",";
					$.cookie('checkedItemTypeA', checkedItems + data.basketItem.bsketNo ,{path:"/",domain:'lottemart.com'})			
				}
			};
			
			if( $.utils.config( 'GuestMember_yn' ) == 'true' && $.utils.config( 'GuestMember_type' ) == "002"){
	   			if(confirm("비회원 배송 조회는 주문한 내역만 확인 가능합니다.\n장바구니는 회원가입 후 이용할 수 있습니다.\n회원 가입 후 이용하시겠습니까?")){
	   				goLogout();
	   				familyJoin();
	   				return;
				}else {
					return;
				}
	   		}
			
			var failCallback = function(xhr, status, error) {
				if (xhr) {
					var data = $.parseJSON(xhr.responseText);
					if (data && data.message) {
						if( window.msg_basket_errors_no_shipping === data.message ) {
							alert( '롯데마트몰의 상품은 고객님의 가까운\n롯데마트 매장에서 배송됩니다.\n상품을 받으실 배송지를 먼저 등록하신 후\n이용해 주세요.' );
							location.href = $.utils.config( 'LMAppUrlM' ) + '/mobile/popup/selectMemberDeliveryForm.do';
						}else if(data.error == "adult"){
							 goProductDetailMobile(
				                        params.categoryId,
				                        params.prodCd,
				                        'N',
				                        '',
				                        '',
				                        ''
				                    );
						}else {							
							alert(data.message);
						}
						if ( data.redirectUrl != null && data.redirectUrl.trim().length > 0 ) {
							location.href = redirectUrl;
							return;
						}
					}
				}
			};
			var validFailFunc = function(msg) {
				validResult = false;
				var validErrorXhr = {
					responseText: "{\"message\":\"" + msg + "\"}",
					status: "400"
				};
				
				failCallback(validErrorXhr, "validation error");
			};

			var search = location.search;
			if (search == null || $.trim(search) === "" ) {
				search = location.href.substring(location.href.indexOf("?"));
			}

			var argItems = params;
			if (!$.isArray(params)) {
				argItems = [];
				argItems.push(params);
			}
			
			$.each(argItems, function(i, item) {
				basketItem = $.extend({}, defaultItem, item);
				
				if (basketItem.prodCd == null || $.trim(basketItem.prodCd) === "" ) {
					// 상품코드 validation error
					validFailFunc("선택한 상품이 없습니다.");
					return false;
				}
				if (basketItem.itemCd == null || $.trim(basketItem.itemCd) === "" ) {
					// 단품코드 validation error
					validFailFunc("선택한 옵션 상품이 없습니다.");
					return false;
				}
				if (basketItem.bsketQty == null || parseInt(basketItem.bsketQty) <= 0 ) {
					// 상품수량 validation error
					validFailFunc("수량을 입력해 주시기 바랍니다.");
					return false;
				}
				if (basketItem.categoryId == null || $.trim(basketItem.categoryId) === "") {
					if (search !== "" ){
						search = search.substring(1);
						var p = search.split("&");
						$.each(p, function(j, o) {
							var v = o.split("=");
							if (v[0].toUpperCase() === "CATEGORYID") {
								basketItem.categoryId = v[1];
								return false;
							}
						});
					}
				}
				items.push(basketItem);
			});
			
			
			if (!validResult) {
				return;
			}
			
			if (typeof(failFunc) != "undefined" && $.isFunction(failFunc)) {
				failCallback = failFunc;
			}
			
			var dataParams = "";
			
			$.each(items, function(i, o) {
				dataParams += ("&" + $.param(o));
			});
			if (dataParams.length > 0) {
				dataParams = dataParams.substring(1);
			}
			
			isLogin(document.location.href, function() {
				$.api.set({
					apiName : 'basketAdd',
					data : dataParams,
					successCallback : successCallback,
					errorCallback : failCallback
				});
				// 장바구니 추가 시 wiselog 로깅 스크립트 호출
				try {
					if (typeof(window.n_click_logging) != "undefined") {
						$.each(items, function(kk, kObj) {
							window.n_click_logging((window._LMAppUrlM || $.utils.config('LMAppUrlM')) + '/basket/pickup.do?ProdCd=' + kObj.prodCd);
						});
					}
				}
				catch ( e ) {
					//
				}
			});
	};
	
	return _addBasket;
}));