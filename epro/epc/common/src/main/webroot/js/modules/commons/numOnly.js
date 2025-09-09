(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.num_only = factory();
	}
}( window, function() {
	var _num_only = function( fl ) {
		var t = fl.value,
			i = 0,
			len = t.length;
		
		for(; i < len; i++) {			
			if (t.charAt(i)<'0' || t.charAt(i)>'9') {
				alert("숫자만 입력해주세요.");
				fl.value="";
				fl.focus();
				return false;
			}
		} 
	};
	
	return _num_only;
}));