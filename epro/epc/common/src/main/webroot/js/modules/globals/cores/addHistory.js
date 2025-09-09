(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		root.global = root.global || {};
		root.global.addHistory = factory( jQuery );
	}
}( window, function( $ ) {
	var _addHistory = function(data, successFunc, failFunc) {
		var defaultData = {
				categoryId: null,
				contensNo: null,
				prodCd: null,
				histTypeCd: null
			};
			data = $.extend({}, defaultData, data);
			
			var successCallback = function(data) {
				if (typeof(successFunc) != "undefined" && $.isFunction(successFunc)) {
					successFunc(data);;
				}
			};
			
			if (typeof(failFunc) === "undefined" || !$.isFunction(failFunc)) {
				failFunc = function(xhr, status, error) {
				};
			}
			
			$.api.set({
				apiName : 'myHistoryAdd',
				data : data,
				success : successCallback,
				errorCallback : failFunc
			});
	};
	
	return _addHistory;
}));