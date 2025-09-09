(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.checkIdType = factory();
	}
}( window, function() {
	var _checkIdType = function( in_str ) {	
		var pattern = new RegExp('[^ㄱ-ㅎ가-힣a-zA-Z0-9]', 'i'); 
		if (pattern.exec(in_str) != null) { 
			//alert("한글, 영문, 숫자만 가능합니다."); 
			return false; 
		}else{
			return true;
		}
	};
	
	return _checkIdType;
}));