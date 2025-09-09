(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery','fn$ajax', 'fnNmGetter'],factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('fn$ajax'), require('fnNmGetter') );
	} else {
		root.fnReviseBasketQty = factory(jQuery, root.fn$ajax, root.fnNmGetter);
	}
}( window, function($, fn$ajax, fnNmGetter) {
	'use strict';
	
	var _fnReviseBasketQty = function() {
		var params = $('#tForm').serialize();

	    fn$ajax(_LMAppUrlM+"/basket/reviseBasketQty.do", params, fnNmGetter().name, false);
	};
	
	return _fnReviseBasketQty;
}));