(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery', 'schemeLoader'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('schemeLoader') );
	} else {
		root.lnbClose = factory(jQuery, root.schemeLoader);
	}
}( window, function($, schemeLoader) {
	var _lnbClose = function() {
		var wrapper = $('html, body'),
			page = $('#page-wrapper'),
			tgtCls = 'globalCategory',
			top = sessionStorage.getItem( 'openLnbTop' );
		
		page.removeClass(tgtCls);
		wrapper.removeClass('masking');
		
		$('.trigger-category-submenu').removeClass('trigger-category-submenu');
		$('li.active').removeClass('active');
		
		$('.wrap-inner-scroll').scrollTop(0);
		
		$( 'html, body' ).scrollTop( top ? top : 0 );
		
		setTimeout(function() {
	        schemeLoader.loadScheme({key: 'lnbClosed'});
	        schemeLoader.loadScheme({key: 'showBar'});
		}, 300);
	};
	
	return _lnbClose;
}));