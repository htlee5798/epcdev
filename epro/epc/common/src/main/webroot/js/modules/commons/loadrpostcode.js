(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery', 'showSpinner', 'hideSpinner' ], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ), require( 'showSpinner' ), require( 'hideSpinner' ));
	} else {
		root.loadrpostcode = factory( jQuery, root.showSpinner, root.hideSpinner );
	}
}( window, function( $, showSpinner, hideSpinner ) {
	var _loadrpostcode = function() {
		showSpinner();
		sessionStorage.setItem( 'postpopupscrollposition', $( window ).scrollTop() );
		$('#wrapZipCode').toggleClass('active');
		$.get('/mobile/load/address/search.do').done(function(html) {
			$('#postsearch').html(html);
			$('html, body').toggleClass('masking');
			hideSpinner();
		});
	};
	
	return _loadrpostcode;
}));