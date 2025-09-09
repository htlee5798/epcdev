(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.callBack_$fnAddWishListBasket = factory();
	}
}( window, function() {
	var _callBack_$fnAddWishListBasket = function( response ) {
		var jsonData = eval( "(" + response + ")" );
		var param = jsonData[0]; if(param.ERR_NO != "0"){alert(param.ERR_MSG); return;}

	  	if(param.ERR_NO != "0"){
	  		alert(param.ERR_MSG);
	  		return;
	  	}//if

	  	if(param.ERR_NO == "0"){
		  	// 선택하신 상품이 찜바구니에 등록되었습니다. \\n 지금 확인하시겠습니까?
		  	var bResult = confirm(msg_mymart_confirm_insert_nowcheck);
	
		  	if(bResult == true){
		  		location.replace(_LMAppUrlM+"/mobile/mypage/PMWMMAR0032.do");
		  	}else{
		  		return;
		  	}//if
	  	}//if
	};
	
	return _callBack_$fnAddWishListBasket;
}));