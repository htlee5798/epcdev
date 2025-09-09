(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery', 'fnJsMsg'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory(require('jquery'), require('fnJsMsg'));
	} else {
		root.calculationOrderQty = factory(jQuery, root.fnJsMsg);
	}
}( window, function($, fnJsMsg) {
	var _calculationOrderQty = function(target , prodCd, gubun, areaIdx, _minQty, _maxQty) {
		var $target = $( target ),
			$form = $target.closest( 'form' ),
			$orderQty = $form.find( '[name="orderQty"]' ), 
			minQty = _minQty || parseInt( $form.find( '[name="minQty"]' ).val() , 10 ),
			maxQty = _maxQty || parseInt( $form.find( '[name="maxQty"]' ).val(), 10 ),
			tmpQty = parseInt( $orderQty.val(), 10 );
	
		if( gubun === "incre" ) {
			$orderQty.val( tmpQty + 1 );
			//현재수량 재셋팅
			tmpQty	= $orderQty.val();
		} else if( gubun === "decre" ) {
			$orderQty.val( tmpQty < minQty ? minQty : maxQty -1 );
			//현재수량 재셋팅
			tmpQty	= $orderQty.val();
		} 
	
		if( tmpQty < minQty ) {
			alert( fnJsMsg( window.view_messages.error.productOrderQty, minQty, maxQty ) );	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
			$orderQty.val( minQty );
		} else if( tmpQty > maxQty ) {
			alert( fnJsMsg( window.view_messages.error.productOrderQty, minQty, maxQty ) );	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
			$orderQty.val( maxQty );
		}
	};
	
	return _calculationOrderQty;
}));