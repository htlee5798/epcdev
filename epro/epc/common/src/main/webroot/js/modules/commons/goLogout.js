(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery', 'jquery.cookie'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('jquery.cookie') );
	} else {
		root.goLogout = factory( jQuery, jQuery.cookie );
	}
}( window, function( $, cookie ) {
	var _goLogout = function() {
		if( $.utils.config( 'Member_yn' ) == 'true'){
			if (!confirm('로그아웃 하시겠습니까?')) {
				return;
			}
		}
		var _autoLoginCookie = cookie("autoLogin");
		if(_autoLoginCookie == "Y") {
			$.removeCookie("autoLoginId");
			$.removeCookie("autoLogin");
			$.removeCookie("atlgdate");
		}

		var outRetURL = $.utils.config( 'LMAppUrlM' ) + '/mobile/main.do';
		var _agent = "${agent}";
		if(_agent.indexOf('TOYSRUS.IPHONE.SHOPPING') > 1 || _agent.indexOf('TOYSRUS.ANDROID.SHOPPING') > 1) {
			outRetURL = "http://m.toysrus.lottemart.com/mobile/main.do";
		}

		if( $.utils.config( 'LPOINT_SERVICE_YN' ) == "Y") {
			location.replace($.utils.config('LMMembersAppSSLUrl') + "/imember/login/ssoLogoutPop.do?sid=MMARTSHOP&returnurl=" + outRetURL);
		} else {
			if(_autoLoginCookie == "Y") {
				location.replace($.utils.config('LMMembersAppSSLUrl') + "/imember/login/ssoLogoutPop.do?mart=Y&sid=MMARTSHOP&returnurl=" + outRetURL);
			} else {
				location.replace($.utils.config('LMMembersAppSSLUrl') + "/imember/login/ssoLogoutPop.do?sid=MMARTSHOP&returnurl=" + outRetURL);
			}
		}
	};
	
	return _goLogout;
}));