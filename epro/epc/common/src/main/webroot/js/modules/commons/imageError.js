(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory(require('jquery'));
	} else {
		root.imageError = factory(jQuery);
	}
}( window, function($) {
	var _imageError = function(obj, fileName) {
		if ( fileName == undefined) {
			return;
		}
		obj.src = $.utils.config( 'LMCdnV3RootUrl' ) + "/images/layout/" + fileName;
	};
	
	return _imageError;
}));