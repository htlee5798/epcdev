(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery','fn$ajax', 'fnNmGetter'],factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('fn$ajax'), require('fnNmGetter') );
	} else {
		root.fnSelectDeliInstPickup = factory(jQuery, root.fn$ajax, root.fnNmGetter);
	}
}( window, function($, fn$ajax, fnNmGetter) {
	'use strict';
	
	var _fnSelectDeliInstPickup = function(strCd) {
		var params = {};

	    params["strCd"] = [];

	    $.each(strCd, function(i, value){
	        params["strCd"].push( value );
	    });

	    fn$ajax(_LMAppSSLUrlM+"/basket/ajaxDeliInstPickup.do", params, fnNmGetter().name, false);
	};
	
	return _fnSelectDeliInstPickup;
}));