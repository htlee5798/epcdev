(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory(require('jquery'));
	} else {
		root.validateQty = factory(jQuery);
	}
}( window, function($) {
	var _validateQty = function(that) {
		var $this = $( that ),
			currentQty = $this.val() === '' ? 0 : $this.val(),
			maxQty = $this.data( 'maxQty' ) || 999,
			minQty = $this.data( 'minQty' ) || 1;
	
		if( currentQty >= maxQty ) {
			alert("상품의 최대구매수량은 " + maxQty + "개 입니다.");
	        $this.val( maxQty );
		} else if( currentQty < minQty ) {
			alert("상품의 최소구매수량은 " + minQty + "개 이상 최대 "+maxQty+ "개 까지만 구매가 가능합니다.");
	        $this.val(minQty);
		}
	};
	
	return _validateQty;
}));