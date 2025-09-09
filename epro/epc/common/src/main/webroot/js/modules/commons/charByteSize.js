(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.charByteSize = factory();
	}
}( window, function() {
	var _charByteSize = function( ch ) {	
		if (ch == null || ch.length == 0) {
	      return 0;
	    }

	    var charCode = ch.charCodeAt(0);

	    if (charCode <= 0x00007F) {
	      return 1;
	    } else if (charCode <= 0x0007FF) {
	      return 2;
	    } else if (charCode <= 0x00FFFF) {
	      return 3;
	    } else {
	      return 4;
	    }
	};
	
	return _charByteSize;
}));