(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		root.hideSpinner = factory( jQuery );
	}
}( window, function( $ ) {
	var _hideSpinner = function() {
		$('#wrapPageLoading').remove();
	};
	
	return _hideSpinner;
}));