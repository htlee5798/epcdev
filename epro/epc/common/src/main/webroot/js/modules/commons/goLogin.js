(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.goLogin = factory();
	}
}( window, function() {
	var _goLogin = function( sid, returnURL ) {
		var url = (window._LMAppUrlM || $.utils.config('LMAppUrlM')) + '/mobile/PMWMMEM0001.do?sid=' + sid + '&returnurl=' + returnURL;
		
		if(window._LMLocalDomain || $.utils.config('LMLocalDomain') == 'true') {
			url += '&mode=DEV';
		}
		window.location = url;
	};
	
	return _goLogin;
}));