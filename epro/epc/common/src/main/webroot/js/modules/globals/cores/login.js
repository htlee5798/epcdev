(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.global = root.global || {};
		root.global.login = factory(jQuery);
	}
}( window, function($) {
	var _login = function(sid, returnUrl) {
		var url;
		url = (window._LMAppSSLUrlM || $.utils.config('LMAppSSLUrlM')) + "/mobile/PMWMMEM0001.do?sid=" + sid + "&returnurl=" + encodeURIComponent(returnUrl);
	
		window.location.href=url;
	};
	
	return _login;
}));