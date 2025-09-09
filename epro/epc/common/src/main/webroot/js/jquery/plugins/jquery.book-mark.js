(function( $, window, document, undefined ) {
	'use strict';
	
	$.fn.bookMark = function( options ) {
		var defaults = {
			activeClass : 'active',
			statusClass : 'bookmark-on',
			wrapperClass : '.mart-bookmark-wrap',
			layerClass : '.layerpop-bookmark',
			imgCdnSrc : ''
		},
		config = $.extend( true, {}, defaults, options || {} );
		
		var $layer = null;
		
		return this.each( function( i , v ) {
			var $this = $( v ),
				$wrapper = $( v ).closest( config.wrapperClass );
			
			$wrapper.on( 'mouseenter', function() {
				$( this ).addClass( 'active' );
				
				if( $layer !== null ) {
					$layer.show();
					
					setPosition();
				} else {
					render( $this, $wrapper );
				}
			}).on( 'mouseleave', function() {
				$( this ).removeClass( 'active' );
				
				if( $layer !== null ) {
					setPosition();
					
					$layer.hide();
				}
			});
		});
		
		function render( $el, $wrapper ) {
			if( $el.hasClass( config.statusClass ) ) {
				var layerHtml = $.render.bookmarkLayer({
					contents : '<p class="txt">바로가기 ON 고객님<br>지금 바로혜택 받아가세요!</p>'+
							'<strong class="txt-point">1% 마일리지<br>적립 혜택</strong>'
				});
			} else {
				var layerHtml = $.render.bookmarkLayer({
					contents : '<p class="txt">바로가기 아직 안하셨나요?</p>'+
							'<strong class="txt-point">바로가기 ON 하고 <br>혜택 받으세요!</strong>'
				});
			}
			
			$wrapper.append( layerHtml );
			
			$layer = $wrapper.find( config.layerClass );
			
			$layer.css({
				display : 'block',
				top : $wrapper.outerHeight( true ),
				left : $wrapper.outerWidth( true ) / 2 - $layer.outerWidth( true ) / 2
			});
			
			setPosition();
		}
		
		function setPosition() {
			$layer.css({
				left : $layer.offset().left <= 0 ? 0 : $layer.closest( config.wrapperClass ).outerWidth( true ) / 2 - $layer.outerWidth( true ) / 2
			});
		}
	};
})( jQuery, window, document );