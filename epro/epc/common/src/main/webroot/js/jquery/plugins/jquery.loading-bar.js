(function( $, window, document, undefined ) {
	'use strict';
	
	$.fn.loadingBar = function( status ) {
		var template = $.render.loadingBarForPC(),
			$loading = this.find( '#wrapLoadingBar' ),
			$this = this;
		
		if( status === undefined ) {
			status = true;
		}
		
		if( status ) {
			loading();
		} else {
			unLoading();
		}
		
		return this;
		
		function loading() {
			if( $loading.length > 0 ) {
				$loading.show();
			} else {
				$this.append( template );
			}
 		}
		
		function unLoading() {
			$loading.hide();
		}
	};
	
})( jQuery, window, document );