(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['initTForm'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('initTForm') );
	} else {
		root.callBack_$fnAddBasketItem = factory(root.initTForm);
	}
}( window, function(initTForm) {
	var _callBack_$fnAddBasketItem = function( response ) {
		initTForm();
		var jsonData = eval( "(" + response + ")" );
		var param = jsonData[0];

		if(param.ERR_NO != "0"){
			alert(param.ERR_MSG);
			return;
		}//if
		if(param.ERR_NO == "0"){
			if(confirm("선택하신 상품이 장바구니에 등록되었습니다. \n 지금 확인하시겠습니까?")){
				window.location.href = _LMAppUrlM+"/mobile/mypage/PMWMMAR0003.do";
			}
		}//if
	};
	
	return _callBack_$fnAddBasketItem;
}));