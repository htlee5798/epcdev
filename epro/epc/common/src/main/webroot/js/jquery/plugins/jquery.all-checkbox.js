(function( $, window, document, undefined ) {
	'use strict';
	
	$.fn.allCheckbox = function( options ) {
		var defaults = {
			checkboxName : '', //name, class ... selector
			activeClass : 'active',
			checkedCallback : function() {},
			unCheckedCallback : function() {}
		},
		config = $.extend( true, defaults, options || {} ),
		$allCheckElement = this;
		
		this.each( function( v ) {
			
			$allCheckElement.on( 'change', function() {
				var checkedStatus = $( this ).is( ':checked' );
				
				$( config.checkboxName ).each(function( i, v ) {
					var $this = $( v ).not( ':disabled' ),
						$parent = $this.parent();
					
					if( checkedStatus ) {
						$parent.addClass( config.activeClass );
					} else {
						$parent.removeClass( config.activeClass );
					}
					
					if( $this.is( ':checkbox' ) ) {
						$this.prop( 'checked', checkedStatus );
					}
				});
			});
		});
		
		$( config.checkboxName ).on( 'change', function() {
			var $this = $( this ),
				isChecked = $this.is( ':checked' );

			if( isChecked ) {
				config.checkedCallback( $this );
			} else {
				config.unCheckedCallback( $this );
			}
			
			if( $( config.checkboxName + ':checked').length === $( config.checkboxName ).not( ':disabled' ).length ) {
				$allCheckElement.parent().addClass( config.activeClass );
				if( $allCheckElement.is( ':checkbox' ) ) {
					$allCheckElement.prop( 'checked', true );
				}
			} else {
				$allCheckElement.parent().removeClass( config.activeClass );
				if( $allCheckElement.is( ':checkbox' ) ) {
					$allCheckElement.prop( 'checked', false );
				}
			}
			
		});
	};
	
})( jQuery, window, document );