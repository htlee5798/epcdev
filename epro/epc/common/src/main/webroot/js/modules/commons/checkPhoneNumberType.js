(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.checkPhoneNumberType = factory();
	}
}( window, function() {
	var _checkPhoneNumberType = function( in_str ) {
		var pattern = new RegExp('[^0-9\-]', 'i'); 
		if (pattern.exec(in_str) != null) { 
			return false; 
		}else{
			return true;
		}
	};
	
	return _checkPhoneNumberType;
}));