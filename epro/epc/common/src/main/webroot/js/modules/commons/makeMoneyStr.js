(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		root.makeMoneyStr = factory( jQuery );
	}
}( window, function( $ ) {
	var _makeMoneyStr = function( value ) {
		if( $ && $.utils && $.utils.comma ) {
			return $.utils.comma( value );
		}
		var result = '',
			negative = false;

		// 숫자면 변경
		if (typeof(value) == 'number') {
			negative = value < 0;
			value = Math.abs(value).toString();
		} else if (typeof(value) == 'string') {
			if (isNaN(value)) return value;

			if (value.indexOf('-') == 0) {
				negative = true;
				value = value.substring(1);
			}
		} else {
			return value;
		}

		var len = value.length,
			comma = 0;

		for (;len > 0; len--, comma++) {
			if (comma != 0 && comma % 3 == 0) {
				result = ',' + result;
			}
			result = value.substring(len -1, len) + result;
		}

		return negative ? '-' + result : result;
	};
	
	return _makeMoneyStr;
}));