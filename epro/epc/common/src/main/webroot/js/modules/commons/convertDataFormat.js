(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.convertDataFormat = factory();
	}
}( window, function() {
	var _convertDataFormat = function( secDate,gb ) {
		var year = secDate.substr(0,4);
		var month = secDate.substr(4,2);
		var day = secDate.substr(6,2);
		var rtdate = year + gb + month + gb + day;

		return rtdate;
	};
	
	return _convertDataFormat;
}));