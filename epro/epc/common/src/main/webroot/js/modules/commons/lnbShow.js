(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery', 'schemeLoader'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('schemeLoader') );
	} else {
		root.lnbShow = factory(jQuery, root.schemeLoader);
	}
}( window, function($, schemeLoader) {
	var _lnbShow = function(button) {
		var page = $('#page-wrapper'),
			tgtCls = 'globalCategory';

		page.addClass(tgtCls);
	    schemeLoader.loadScheme({key: 'lnbOpen'});
	    schemeLoader.loadScheme({key: 'hideBar'});
	};
	
	return _lnbShow;
}));