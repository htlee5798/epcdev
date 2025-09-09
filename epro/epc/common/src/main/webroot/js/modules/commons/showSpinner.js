(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		root.showSpinner = factory( jQuery );
	}
}( window, function( $ ) {
	var _showSpinner = function() {
		var loader = '<div id="wrapPageLoading">'
			+'<span class="ingpageloading">잠시만 기다려주세요.</span>'
			+'<script>$(\'#wrapPageLoading\').on(\'touchstart touchmove touchend scroll\', function() {return false;});</script>'
			+'</div>';

		$('body').append(loader);
	};
	
	return _showSpinner;
}));