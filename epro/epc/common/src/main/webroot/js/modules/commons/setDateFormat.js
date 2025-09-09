(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.setDateFormat = factory();
	}
}( window, function() {
	var _setDateFormat = function( date, split ) {
		date = date.trim();
		
		if (date.length < 8 || date == undefined ){
			return "";
		}
		
		return date.substring(0, 4) + split + date.substring(4, 6) + split + date.substring(6, 8);
	};
	
	return _setDateFormat;
}));