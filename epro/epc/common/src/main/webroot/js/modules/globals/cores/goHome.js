(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.global = root.global || {};
		root.global.goHome = factory();
	}
}( window, function() {
	var _goHome = function() {
		window.location.href = "/mobile/main.do";
	};
	
	return _goHome;
}));