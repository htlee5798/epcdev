(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery', 'jquery.cookie'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('jquery.cookie') );
	} else {
		root.getReturnUrl = factory(jQuery, jQuery.cookie);
	}
}( window, function($, cookie) {
	var _getReturnUrl = function(button) {
		var returnUrl = location.origin + location.pathname + location.search;
		var returnCategoryId = cookie('__categoryId');
		
		if(returnCategoryId && location.search.indexOf('returnCategoryId') < 0) {
			returnUrl += ((location['search']) ? '&' : '?') + "returnCategoryId=" + returnCategoryId;
		}
		return returnUrl;
	};
	
	return _getReturnUrl;
}));