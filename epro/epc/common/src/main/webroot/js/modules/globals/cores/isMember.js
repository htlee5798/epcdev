(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.global = root.global || {};
		root.global.isMember = factory(jQuery);
	}
}( window, function($) {
	var _isMember = function(successFunc, failFunc) {
		var result = true;
		
		$.api.set({
			apiName : 'isMember',
			async : false,
			successCallback : function( data ) {
				result = data.isMember === "Y";
				if (result) {
					if (typeof(successFunc) != "undefined") {
						successFunc();
					}
				}
			}
		});
		
		if (typeof(failFunc) != "undefined" && !result) {
			failFunc();
		}
		return result;
	};
	
	return _isMember;
}));