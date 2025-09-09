/**
 * 
 */
define(function() {
	function _init( obj ) {
		var returnValue = false;
		
		if( obj.prodTypeCd !== 'undefined'
			&& ( obj.prodTypeCd === onlineProdMake || obj.prodTypeCd === onlineProdInst || obj.prodTypeCd === onlineProdReserve ) ) {
			//주문제작상품, 설치상품, 예약상품은 상품상세페이지로 이동
			alert( view_messages.confirm.goProductDetail );		//해당 상품은 상품 상세에서 장바구니에 담으실 수 있습니다.
			goProductDetail( obj.categoryId, obj.prodCd, 'N' );
			
			returnValue = true;
		}
		
		return returnValue;
	}	
	return _init;
});