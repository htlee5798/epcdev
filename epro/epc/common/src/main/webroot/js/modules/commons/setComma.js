(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.setComma = factory(jQuery);
	}
}( window, function($) {
	var _setComma = function( v ) {	
		if( $.utils && $.utils.comma ) {
			$.utils.comma( v );
		}
		var num = num.toString(),
			rtn = "",
			val = "",
			j = 0,
			x = num.length,
			i = x;

		for(; i > 0; i--) {
			if(num.substring(i,i-1) != ",") {
				val = num.substring(i, i-1) + val;				
			} 
		}
		
		x = val.length;
		i = x;
		
		for(; i > 0 ; i--) {
			if(j%3 == 0 && j!=0) { 
				rtn = val.substring(i,i-1)+","+rtn; 
			} else { 
				rtn = val.substring(i,i-1)+rtn;
			}
			j++;
		}

		return rtn;
	};
	
	return _setComma;
}));