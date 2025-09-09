(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['fn$ajax', 'fnNmGetter'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('fn$ajax'), require( 'fnNmGetter' ) );
	} else {
		root.fnAddBasketItem = factory(root.fn$ajax, root.fnNmGetter);
	}
}( window, function(fn$ajax, fnNmGetter) {
	'use strict';
	
	var _fnAddBasketItem = function() {
		var params = $('#tForm').serialize();
	    fn$ajax(_LMAppUrlM+"/basket/insertBasket.do", params, fnNmGetter().name, false);
	};
	
	return _fnAddBasketItem;
}));