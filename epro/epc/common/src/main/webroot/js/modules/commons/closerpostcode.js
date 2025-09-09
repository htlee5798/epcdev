(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.closerpostcode = factory(jQuery);
	}
}( window, function($) {
	var _closerpostcode = function() {
		var scrollPosition = sessionStorage.getItem( 'postpopupscrollposition' ) === null ? 0 : sessionStorage.getItem( 'postpopupscrollposition' );
		$('html, body').removeClass('masking');
		$( window ).scrollTop( scrollPosition );
		$('#wrapZipCode').removeClass('active');
		
		sessionStorage.removeItem( 'postpopupscrollposition' );
	};
	
	return _closerpostcode;
}));