(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery', 'fn$ajax', 'fnNmGetter'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ), require( 'fn$ajax' ), require( 'fnNmGetter' ) );
	} else {
		root.fnAddWishListBasket = factory( jQuery, root.fn$ajax, root.fnNmGetter );
	}
}( window, function( $, fn$ajax, fnNmGetter ) {
	var _fnAddWishListBasket = function( prodCds, forgnDelyplYns, categoryIds ) {
		var params = {};
		
		params["prodCds"] = [];
		params["forgnDelyplYns"] = [];
		params["categoryIds"] = [];
		
		$.each(prodCds, function(i, value){
			params["prodCds"].push( value );
			
		});
		
		$.each(forgnDelyplYns, function(i, value){
			params["forgnDelyplYns"].push( value );
			
		});
		
		$.each(categoryIds, function(i, value){
			params["categoryIds"].push( value );
			
		});
		
		fn$ajax(_LMAppSSLUrlM+"/mymart/ajax/insertWishList.do", params, fnNmGetter().name, false);
	};
	
	return _fnAddWishListBasket;
}));