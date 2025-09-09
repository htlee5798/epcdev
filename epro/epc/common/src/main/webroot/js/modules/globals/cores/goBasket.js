(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define([
		        'jquery', 
		        'goLogout',
		        '/modules/globals/corners/familyJoin',
		        '/modules/globals/corners/isLogin'
		        ], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( 
				require( 'jquery' ),
				require( 'goLogout' ),
				require( '/modules/globals/corners/familyJoin'),
				require( '/modules/globals/corners/isLogin' ) 
		);
	} else {
		root.global = root.global || {};
		root.global.goBasket = factory( jQuery, root.goLogout, root.global.familyJoin, root.global.isLogin );
	}
}( window, function( $, goLogout, familyJoin, isLogin ) {
	var _goBasket = function() {
		if( $.utils.config( 'GuestMember_yn' ) == 'true' &&  $.utils.config( 'GuestMember_type' ) == "002"){
   			if(confirm("비회원 배송 조회는 주문한 내역만 확인 가능합니다.\n장바구니는 회원가입 후 이용할 수 있습니다.\n회원 가입 후 이용하시겠습니까?")){
   				goLogout();
   				familyJoin();
   				return;
			}else {
				return;
			}
   		}
		
		var url = $.utils.config( 'LMAppUrlM' ) + "/mobile/mypage/PMWMMAR0003.do";
		if ( isLogin( location.href ) ) {
			$.api.get({
    			apiName : 'defaultDeliInfo',
    			successCallback : function( resData ) {
    				if( resData.defaultMemberAddrYn === 'Y' ) {
    					location.href = url;
    				}  else {
    					alert( resData.message );
    					location.href = $.utils.config( 'LMAppUrlM' ) + '/mobile/popup/selectMemberDeliveryForm.do';
    				}	
    			}
    		});
		}
	};
	
	return _goBasket;
}));