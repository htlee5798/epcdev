(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define([ 'charByteSize' ], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'chartByteSize' ) );
	} else {
		root.checkMaxLength = factory( root.charByteSize );
	}
}( window, function( charByteSize ) {
	var _checkMaxLength = function( str ) {	
		if (str == null || str.length == 0) {
	      return 0;
	    }
	    var size = 0,
	    	i = 0,
	    	len = str.length;

	    for (; i < len ; i++) {
	      size += charByteSize(str.charAt(i));
	    }
	    return size;
	};
	
	return _checkMaxLength;
}));