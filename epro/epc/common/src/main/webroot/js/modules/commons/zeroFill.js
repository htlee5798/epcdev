(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.zeroFill = factory();
	}
}( window, function(val, len, z) {
	var _zeroFill = function() {
		z = z || '0';
		val = val + '';
		return val.length >= len ? val : new Array(len - val.length + 1).join(z) + val;
	};
	
	return _zeroFill;
}));