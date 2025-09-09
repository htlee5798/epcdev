(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.co = root.co || {};
		root.co.ajax = factory(jQuery);
	}
}( window, function($) {
	var _coAjax = function( url, params, fnCallback, type, syn ) {
		var fnSuccess = typeof fnCallback === 'function' ? fnCallback : fnCallback.success;
		var jsonParams = JSON.stringify(params);
		jsonParams = encodeURIComponent(jsonParams);
		if (!type) type = 'POST';
		if (!syn) syn = true;
		$.ajax({
			async: syn,
			type: type,
			cache: true,
			url: url,
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
				'X-Requested-With': 'XMLHttpRequest'
			},
			data: params,
			success: function(data) {
				if(data && fnSuccess){
					fnSuccess(data);
				}

			},
			error: function(data, status, headers) {
				if(data && fnCallback.error){
					fnCallback.error(data, status, headers);
				}
			}
		});
	};
	
	return _coAjax;
}));