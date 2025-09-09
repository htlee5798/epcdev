(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.getLength = factory();
	}
}( window, function() {
	var _getLength = function( sText ) {
		var i = 0,
			len = sText.length,
			nLength = 0;

		for (; i < len; i++) 	{
			if (sText.charCodeAt(i) > 128) {
				nLength	+= 2;				
			} else {
				nLength	++;				
			}
		}

		return nLength;
	};
	
	return _getLength;
}));