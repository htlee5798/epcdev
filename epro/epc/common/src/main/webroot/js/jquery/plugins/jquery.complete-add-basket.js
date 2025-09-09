(function( $, window, document ) {
	'use strict';
	
	$.fn.completeAddBasket = function( options ) {
		var defaults = {
				layerClass : '.layerpop-type3',
				$el : null,
				templateName : 'toastLayer',
				contents : ''
			},
			config = $.extend( {}, defaults , options || {} ),
			$this = this,
			completeLayerHtml = $.render[ config.templateName ]({
				layerClass : 'layerpop-toast-btm layerpop-target',
				contents : config.contents
			});
		
		$this.append( completeLayerHtml )
			.promise()
			.done(function() {
				removeAllLayer();
				
				var $results = config.$el.closest( '.product-article' ),
					$completeLayer = $this.find( '.layerpop-toast-btm' ),
					offset = config.$el.offset(),
					layerWidth = $completeLayer.outerWidth( true ),
					layerHeight = $completeLayer.outerHeight( true ),
					top = offset.top - layerHeight - 12,
					left = offset.left - ( ( layerWidth / 2 ) - ( config.$el.outerWidth( true ) / 2 ) ); 
				
				$results.addClass( 'active' );
				
				$completeLayer.css({
					display : 'block',
					top : top,
					left : left
				});
				
				$this.on( 'click.completeLayer', function( e ) {
					var $t = $( e.target );
					
					if( $t.closest( config.layerClass ).length === 0 ) {
						$completeLayer.remove();
						$results.removeClass( 'active' );
						
						$this.off( 'click.completeLayer' );
					}
				});
			});
		
		function removeAllLayer() {
			$this.find( config.layerClass ).each(function() {
				$( this ).remove();
			});
			
			$this.off( 'click.layer' );
		}
		
		return this;
	};
})( jQuery, window, document );