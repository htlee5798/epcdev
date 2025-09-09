(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery','fn$ajax', 'fnNmGetter'],factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('fn$ajax'), require('fnNmGetter') );
	} else {
		root.fnDirectOrderItem = factory(jQuery, root.fn$ajax, root.fnNmGetter);
	}
}( window, function($, fn$ajax, fnNmGetter) {
	'use strict';
	
	var _fnDirectOrderItem = function() {
		var params = $('#tForm').serialize();

	    fn$ajax(_LMAppSSLUrlM+"/basket/insertDirectBasket.do", params, fnNmGetter().name, false);
	};
	
	return _fnDirectOrderItem;
}));