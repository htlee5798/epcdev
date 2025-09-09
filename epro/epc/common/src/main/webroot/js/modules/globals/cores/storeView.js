(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.global = root.global || {};
		root.global.storeView = factory(jQuery, root.global.storeView);
	}
}( window, function($) {
	var _rtn = function(stdistMallYn) {
		if( stdistMallYn == "N" ) {
			alert("고객님의 현재 배송지는 전용센터 택배 배송지입니다.\n택배상품은 결제일 다음날부터 2~3일 이내 발송됩니다.");
		} else {
			var url;
			url = (window._LMAppSSLUrlM || $.utils.config('LMAppSSLUrlM')) + "/mobile/popup/deliverytime.do";
		
			window.location.href = url;
		}
	};
	
	return _rtn;
}));