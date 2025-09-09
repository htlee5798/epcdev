(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.isNumber = factory();
	}
}( window, function() {
	var _isNumber = function( obj ) {
		var i = 0,
			len = 0,
			str	= obj.value.trim();

		if (str.length == 0) {		
			return false;
		}
		
		len = str.length;
	
		for (; i < len; i++) {
			if(!('0' <= str.charAt(i) && str.charAt(i) <= '9')) {
				return false;
			}
		}
		return true;
	};
	
	return _isNumber;
}));