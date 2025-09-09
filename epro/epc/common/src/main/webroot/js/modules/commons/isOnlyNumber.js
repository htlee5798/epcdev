(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.isOnlyNumber = factory();
	}
}( window, function() {
	var _isOnlyNumber = function( v ) {		
		return /^(\s|\d)+$/.test(v);
	};
	
	return _isOnlyNumber;
}));