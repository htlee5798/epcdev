(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define([
		        'jquery', 
		        'goLogout',
		        'setAdultReturnUrl',
		        'isLogin'
		        ], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( 
				require( 'jquery' ),
				require( 'goLogout' ),
				require( 'setAdultReturnUrl' ),
				require( 'isLogin' ) 
		);
	} else {
		root.global = root.global || {};
		root.global.addDirectBasket = factory( jQuery, root.goLogout, root.setAdultReturnUrl, root.global.isLogin );
	}
}( window, function( $, goLogout, setAdultReturnUrl, isLogin ) {
    var genDomInput = function( elemName, elemValue ) {
        var input = document.createElement("input");

        input.setAttribute("type", "hidden");
        input.setAttribute("name", elemName);
        input.setAttribute("id", elemName);
        input.setAttribute("value", elemValue);

        return input;
    };
	var _addDirectBasket = function(params, failFunc) {
		var defaultItem = {
				bsketNo: "",		// 장바구니번호(비워둘것)
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
			
			var failCallback = function(xhr, status, error) {
				if (xhr) {
					var data = $.parseJSON(xhr.responseText);
					if (data && data.message) {
						if( window.msg_basket_errors_no_shipping === data.message ) {
							if( confirm( '롯데마트몰의 상품은 고객님의 가까운\n롯데마트 매장에서 배송됩니다.\n상품을 받으실 배송지를 먼저 등록하신 후\n이용해 주세요.' ) ) {
								location.href = $.utils.config( 'LMAppUrlM' ) + '/mobile/popup/selectMemberDeliveryForm.do';
								
							}
						} else {
							alert(data.message);
							if ( data.redirectUrl != null && data.redirectUrl.trim().length > 0 ) {
								location.href = redirectUrl;
								return;
							}
						}
					}
				}
			};
			
			if (typeof(failFunc) != "undefined" && $.isFunction(failFunc)) {
				failCallback = failFunc;
			}
			
			var validFailFunc = function(msg) {
				validResult = false;
				var validErrorXhr = {
					responseText: "{\"message\":\"" + msg.replace(/\n/g, '\\n') + "\"}",
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
					validFailFunc("선택한 상품이 없습니다.");
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
			
			var dataParams = "";
			
			$.each(items, function(i, o) {
				dataParams += ("&" + $.param(o));
			});
			if (dataParams.length > 0) {
				dataParams = dataParams.substring(1);
			}
			
			isLogin(null, function() {
				$.api.set({
					apiName : 'basketAddDirect',
					data : dataParams,
					successCallback : function( data ) {
						var param = data[0];

						if(param.ERR_NO != "0"){
							if(param.ERR_NO =="8"){
								setAdultReturnUrl();
								return;
							}else{
								//alert(param.ERR_MSG);
								var msg = param.ERR_MSG.replace(/\n/g, '\\n');
								validFailFunc(msg);
								return;
							}
						}//if
						
						var findItem = function(list, item) {
							var objArr = $.grep(list, function(o) {
								return o.PROD_CD == item.prodCd && o.ITEM_CD == item.itemCd;
							});
							if (objArr != null && objArr.length > 0) {
								return objArr[0];
							}
							return null;
						};

						if(param.ERR_NO == "0"){
							if ($("#tForm input[name=prodCd]").length < 1 ) {
								var basketInfo = null;
								$.each(items, function(i, item) {
									basketInfo = findItem(param.basketList, item);
									if (basketInfo != null) {
										$('#divTemp').append(genDomInput("bsketNo", basketInfo.BSKET_NO));
										$('#divTemp').append(genDomInput("prodCd", item.prodCd));
										$('#divTemp').append(genDomInput("itemCd", item.itemCd));
										$('#divTemp').append(genDomInput("bsketQty", item.bsketQty));
										$('#divTemp').append(genDomInput("categoryId", item.categoryId));
										$('#divTemp').append(genDomInput("nfomlVariation", item.nfomlVariation));
										$('#divTemp').append(genDomInput("overseaYn", item.overseaYn));
										$('#divTemp').append(genDomInput("prodCouponId", item.prodCouponId));
										$('#divTemp').append(genDomInput("oneCouponId", item.oneCouponId));
										$('#divTemp').append(genDomInput("cmsCouponId", item.cmsCouponId));
										$('#divTemp').append(genDomInput("markCouponId", item.markCouponId));
										$('#divTemp').append(genDomInput("periDeliYn", item.periDeliYn));
										$('#divTemp').append(genDomInput("mstProdCd", item.mstProdCd));
										$('#divTemp').append(genDomInput("saveYn", item.saveYn));
										$('#divTemp').append(genDomInput("ctpdItemYn", item.ctpdItemYn));
										$('#divTemp').append(genDomInput("ctpdProdCd", item.ctpdProdCd));
										$('#divTemp').append(genDomInput("ctpdItemCd", item.ctpdItemCd));
										$('#divTemp').append(genDomInput("ordReqMsg", item.ordReqMsg));
										$('#divTemp').append(genDomInput("hopeDeliDy", item.hopeDeliDy));
									}
								});
							}
							//$('#divTemp').append(genDomInput("bsketNo", param.bsketNo));
							$('#divTemp').append(genDomInput("basketType", param.basketType));
							$('#divTemp').append(genDomInput("deliTypeCd", param.deliTypeCd));
							$('#divTemp').append(genDomInput("bsketDivnCd", "02"));

							$('#tForm').attr('action', $.utils.config( 'LMAppUrlM' )+'/mobile/basket/insertPreOrder.do').submit();
						}//if
					},
					errorCallback : failCallback
				});
			});
	};
	
	return _addDirectBasket;
}));