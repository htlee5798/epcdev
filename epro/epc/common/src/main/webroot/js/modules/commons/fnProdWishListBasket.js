(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['fnAddWishListBasket'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'fnAddWishListBasket' ) );
	} else {
		root.fnProdWishListBasket = factory( root.fnAddWishListBasket );
	}
}( window, function( fnAddWishListBasket ) {
	var _fnProdWishListBasket = function( prodCd, forgnDelyplYn, categoryId ) {
		//상품코드
		  var prodCds = [prodCd];
		  //해외배송여부
		  var forgnDelyplYns = [forgnDelyplYn];
		  //카테고리ID
		  var categoryIds = [categoryId];

		  fnAddWishListBasket(prodCds, forgnDelyplYns, categoryIds);
	};
	
	return _fnProdWishListBasket;
}));