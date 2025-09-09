(function( $, window, document, undefined ) {
	'use strict';
	
	$.fn.layer = function( options ) {
		var defaults = {
			target : 'body',
			templateName : 'popupLayer',
			layerClass : '',
			title : '',
			contents : '',
			position : 'center'
		},
		$this = this,
		config = $.extend( true, {}, defaults, options || {});
		
		var $target = $( config.target );
		
		var layerTemplate = $.render[ config.templateName ]({
			title : config.title,
			layerClass : config.layerClass,
			contents : config.contents 
		}),
		$layer = $( layerTemplate ).appendTo( this );
		
		var _top = 0,
			_left = 0;
		
		if( config.position === 'center' ) {
			_top = $this.height() / 2 - $layer.height() / 2;
			_left = $this.width() / 2 - $layer.width() / 2;
		}

		$layer.css({
			display:'block',
			top: _top,
			left: _left
		}).on( 'click', '.btn-ico-close', function() {
			$layer.remove();
			return false;
		});
		
		$( window ).on( 'resize', function() {
			if( $layer ) {
				$layer.remove();
				$target.off( 'click.layer' );
			}
		});
		
		$target.on( 'click.layer', function( e ) {
			var $this = $( e.target );

			if( $layer.find( $this ).length === 0 ) {
				$layer.remove();
				$target.off( 'click.layer' );
			}
		});
		
		return this;
	};
	
})( jQuery, window, document );