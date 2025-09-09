(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		root.showNoImage = factory( jQuery );
	}
}( window, function( $ ) {
	var _showNoImage = function( obj, width, height ) {
		if(typeof obj.src == "undefined"){
			return "";
		}

		if ( obj.src.indexOf("noimg_prod") == -1 ) {
			var width = width || $(obj).attr("width");
			var height = height || width || $(obj).attr("height");
			
			$(obj).attr("src", ( $.utils.config( 'LMCdnV3RootUrl' ) || window._LMCdnV3RootUrl ) +"/images/layout/noimg_prod_"+ width +"x"+ height +".jpg");
		}
	};
	
	return _showNoImage;
}));