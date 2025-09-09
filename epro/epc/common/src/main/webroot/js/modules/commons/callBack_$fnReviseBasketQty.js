(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['initTForm'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('initTForm') );
	} else {
		root.callBack_$fnReviseBasketQty = factory(root.initTForm);
	}
}( window, function(initTForm) {
	var _callBack_$fnReviseBasketQty = function( response ) {

		// TForm 초기화
		initTForm();
		var jsonData = eval( "(" + response + ")" );
		var param = jsonData[0];

		if(param.ERR_NO != "0"){
			alert(param.ERR_MSG);
			location.reload();
			return;
		}//if

		if(param.ERR_NO == "0"){
			//정상적으로 수정되었습니다.
			alert('옵션/수량을 변경하였습니다.');
			//location.replace(_LMAppUrl+"/basket/basketList.do");
			location.reload();
		}//if
	};
	
	return _callBack_$fnReviseBasketQty;
}));