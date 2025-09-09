(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.isEmail = factory();
	}
}( window, function() {
	var _isEmail = function(obj) {
		var str = obj.value.trim();

		if(str == "") {		
			return false;
		}

		var i = str.indexOf("@");
		if(i < 0) {		
			return false;
		}

		i = str.indexOf(".");
		if(i < 0) {		
			return false;
		}

		return true;
	};
	
	return _isEmail;
}));