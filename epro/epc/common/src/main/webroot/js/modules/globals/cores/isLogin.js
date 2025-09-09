(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery', 'login'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( requrie('jquery'), require( 'login' ) );
	} else {
		root.global = root.global || {};
		root.global.isLogin = factory(jQuery, root.global.login);
	}
}( window, function($, login) {
	var _isLogin = function(redirectUrl, successFunc, failFunc) {
		var param = {
			redirectUrl: redirectUrl
		};
		var result = true;

		$.api.set({
			apiName : 'isLogin',
			async : false,
			data : {
				redirectUrl : redirectUrl
			},
			successCallback : function( data ) {
				result = data.isLogin === "Y";
				if (result) {
					if (typeof(successFunc) != "undefined") {
						successFunc();
					}
				}
			}
		});
		
		if (!result) {
			if (typeof(failFunc) != "undefined") {
				failFunc();
			}
			else {
				login($.utils.config( 'SID_NM_MMARTMALL' ), redirectUrl );
			}
		}
		return result;
	};
	
	return _isLogin;
}));