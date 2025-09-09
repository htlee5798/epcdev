(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.callSysErr = factory();
	}
}( window, function() {
	var _callSysErr = function( XMLHttpRequest, textStatus, errorThrown ) {
		if(XMLHttpRequest.status != 200){
			
			var errMsg = "서버에서 데이터를 처리하는중 다음과 같은 오류가 발생하였습니다"+"\n"
						  +XMLHttpRequest.status + "\n" + XMLHttpRequest.statusText+"\n"
						  +"페이지를 새로고침해보시고 계속 오류가 발생하면 관리자에게 문의바랍니다";
			//alert(errMsg);
		}
	};
	
	return _callSysErr;
}));