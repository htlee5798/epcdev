(function( $, window, document, undefined ) {
	'use strict';
	
	$.fn.toast = function( options ) {
		var defaults = {
			target : 'body',
			animationTime : 800,
			templateName : 'toastLayer',
			layerClass : '',
			contents : ''
		},
		$this = this,
		config = $.extend( true, {}, defaults, options );
		
		var toastTemplate = $.render[ config.templateName ]({
			layerClass : config.layerClass,
			contents : config.contents 
		}),
		$toastLayer = $( toastTemplate ).appendTo( config.target );

		var _top = $this.offset().top - $toastLayer.outerHeight(true) - 12,
			_left = $this.offset().left - ( $toastLayer.outerWidth(true) / 2) + ($this.outerWidth(true) / 2);

		$toastLayer.css({
			display:'block',
			top: _top,
			left: _left + $toastLayer.outerWidth( true ) >= $( config.target ).width() ? _left - ( _left + $toastLayer.outerWidth( true ) - $( config.target ).width() ) : _left
		});
		
		setTimeout(function () {
			$toastLayer.remove();
		}, config.animationTime );
		
		return this;
	};
	
})( jQuery, window, document );