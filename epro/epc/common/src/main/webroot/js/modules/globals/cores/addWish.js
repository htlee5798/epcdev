(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define([
		        'jquery', 
		        '/modules/globals/corners/isLogin'
		        ], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( 
				require( 'jquery' ),
				require( '/modules/globals/corners/isLogin' ) 
		);
	} else {
		root.global = root.global || {};
		root.global.addWish = factory( jQuery, root.global.isLogin );
	}
}( window, function( $, isLogin ) {
	var _addWish = function(wishItems, successFunc, failFunc) {
		var successCallback = function(data) {
			if(window.fbq){
				fbq('track', 'AddToWishlist');
			}
			
			if (typeof(successFunc) != "undefined" && $.isFunction(successFunc)) {
				successFunc(data);
			}
		};
		
		var failCallback = function(xhr, status, error) {
			if (xhr) {
				var data = $.parseJSON(xhr.responseText);
				if (data && data.message) {
					alert(data.message);
					if ( data.redirectUrl != null && data.redirectUrl.trim().length > 0 ) {
						location.href = redirectUrl;
						return;
					}
				}
			}
		};
		
		if (typeof(failFunc) != "undefined" && $.isFunction(failFunc)) {
			failCallback = failFunc;
		}
		
		var validFailFunc = function(msg) {
			var validErrorXhr = {
				responseText: "{\"message\":\"" + msg + "\"}",
				status: "400"
			};
			failCallback(validErrorXhr, "validation error");
		};
		var defaultData = {
			categoryId: null,
			prodCd: null,
			forgnDelyplYn: "N"
		};
		
		var params = {
			categoryIds: [],
			prodCds: [],
			forgnDelyplYns: []
		};
		
		var validItem = function(item) {
			if (item.categoryId == null || $.trim(item.categoryId) === "" ) {
				// 카테고리 validation error
				validFailFunc("카테고리가 확인되지 않아 찜하기가 취소 되었습니다.");
				return false;
			}

			if (item.prodCd == null || $.trim(item.prodCd) === "" ) {
				// 상품코드 validation error
				validFailFunc("선택한 상품이 없어 찜하기가 취소 되었습니다.");
				return false;
			}
			
			return true;
		};
		if ($.isArray(wishItems)) {
			var item = null;
			var valid = true;
			
			$.each(wishItems, function(i, o) {
				item = $.extend({}, defaultData, o);
				if (!validItem(item)) {
					return (valid = false);
				}
				params.categoryIds.push(item.categoryId);
				params.prodCds.push(item.prodCd);
				params.forgnDelyplYns.push(item.forgnDelyplYn);
			});
			if (!valid) {
				return;
			}
		}
		else {
			var item = $.extend({}, defaultData, wishItems);
			if (!validItem(item)) {
				return;
			}

			params.categoryIds.push(item.categoryId);
			params.prodCds.push(item.prodCd);
			params.forgnDelyplYns.push(item.forgnDelyplYn);
		}
		
		isLogin(null, function() {
			$.api.set({
				apiName: 'wishAdds',
				traditional : true,
				data : params,
				successCallback : successCallback,
				errorCallback : failCallback
			});
		});
	};
	
	return _addWish;
}));