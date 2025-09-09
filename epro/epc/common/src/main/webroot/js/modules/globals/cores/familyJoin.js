(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.global = root.global || {};
		root.global.familyJoin = factory(jQuery);
	}
}( window, function($) {
	var _familyJoin = function(param) {
		var url = "/imember/member/join/memberJoin.do?sid=" + $.utils.config( 'SID_NM_MARTMALL' ) + (!!param ? "&" + param : "") + "&memberJoinYN=Y";
		if ( location.protocol === "http:" ) {
			url = $.utils.config( 'LMMembersAppUrl' ) + url;
		}
		else {
			url = $.utils.config( 'LMMembersAppSSLUrl' ) + url;
		}
		
		var popWin = window.open(url, "_blank");
		popWin.focus();
	};
	
	return _familyJoin;
}));