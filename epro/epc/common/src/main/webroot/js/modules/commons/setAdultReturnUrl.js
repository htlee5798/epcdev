(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		root.setAdultReturnUrl = factory( jQuery );
	}
}( window, function( $ ) {
	function _setAdultReturnUrl() {
		var urlAdult = location.href;
	
		return location.href = $.utils.config( 'LMAppUrl' ) + "/product/loginBlank.do?urlAdult="+ encodeURIComponent(urlAdult);
	}
	
	return _setAdultReturnUrl;
}));