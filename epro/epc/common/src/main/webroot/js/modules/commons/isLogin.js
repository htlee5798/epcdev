(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery', 'goLogin'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('goLogin') );
	} else {
		root.isLogin = factory( jQuery, root.goLogin );
	}
}( window, function( $, goLogin ) {
	var _isLogin = function(redirectUrl, event) {
		params = {
				'redirectUrl' : redirectUrl
		};

		var bLogin;

		var url = (window._LMAppUrlM || $.utils.config('LMAppUrlM')) +"/mobile/login/islogin.do";

		$.ajax({
	        type       : "POST" ,
	        url        : url,
	        //url        : _LMAppSSLUrl+"/member/islogin.do" ,
	        data       : params ,
	        async      : false ,
	        dataType   : "text" ,
	        timeOut    : (9 * 1000) ,
	        success    : function(response){
	         	var jsonData = eval( "(" + response + ")" );
	         	bLogin = ( jsonData.isLogin == "Y" );
	        } ,
			cache      : false ,
	        error      : callSysErr
	    });

		if ( !bLogin ) {
			goLogin("MMARTSHOP", redirectUrl);
			//return false;
		}

		return bLogin;
	};
	
	return _isLogin;
}));