
(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.logout = factory();
	}
}( window, function() {
	var _logout = function( returnURL ) {
		location.href = (window._LMMembersAppSSLUrlM || $.utils.config('LMMembersAppSSLUrlM')) +"/login/logout.do?returnURL="+ returnURL;
	};
	
	return _logout;
}));