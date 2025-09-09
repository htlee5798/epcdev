(function( root, factory ) {
	'use strict';

	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.ban_close = factory();
	}
}( window, function() {
	var _ban_close = function() {
		var d = new Date();
		d.setDate(d.getDate() + 1); //1일 뒤 이 시간
		var expires = "expires="+d.toGMTString();
		document.cookie = "viewban=Y;" + expires;
		if (document.querySelector('#settingTopBanner')) document.querySelector('#settingTopBanner').style.display = 'none';
	};

	return _ban_close;
}));