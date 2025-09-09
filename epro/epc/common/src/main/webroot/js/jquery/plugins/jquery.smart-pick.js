(function( $, window, document, undefined ) {
	'use strict';
	
	$.fn.smartPick = function( options ) {
		var defaults = {
			isLogin : '',
			martCode : '',
			cookieName : 'smartPickPopup',
			layerClass : '.smartPick-layer',
			completeLayerClass : '.pickup-complete-layer',
			closeClass : '.ico-layer-close',
			activeClass : 'active',
			checkedTodayClass : '#today_close',
			closeCallback : function() {},
			completeCallbakc : function() {}
		},
		config = $.extend( true, {}, defaults, options || {} );;
		
		var timer = null,
			$this = this,
			$smartPickLayer = $this.find( config.layerClass );
		
		if( $.cookie( config.cookieName ) !== 'Y' ) {
			$smartPickLayer.addClass( config.activeClass );
			timer = setTimeout(function() {
				$smartPickLayer.removeClass( config.activeClass );
			}, 5000 );
		}
		$this.find( '.banner-link' ).removeAttr( 'onclick' );
		
		$this.on( 'mouseenter', function() {
			if( $.cookie( config.cookieName ) === 'Y' ) {
				return false;
			}
			clearTimeout( timer );
			$smartPickLayer
				.addClass( config.activeClass )
				.find( config.checkedTodayClass )
				.prop( 'checked', false );
		}).on( 'mouseleave', function() {
			if( $.cookie( config.cookieName ) === 'Y' ) {
				return false;
			}
			$smartPickLayer.removeClass( config.activeClass );
		}).on( 'click', '.banner-link', function( e ) {
			if( config.isLogin === 'Y' ) {
				var winl = (screen.width - 665 ) / 2;
				var winprops = 'height=665px,width=665px,left='+ winl +',scrollbars="yes"';
				var win = window.open($.utils.config( 'LMAppUrl' ) + "/mymart/popup/smartPickUp.do", 'smartPickPopup', winprops);
				
				if (parseInt(navigator.appVersion) >= 4) { win.window.focus(); }
				
			} else {
				global.login( "MARTSHOP" , location.href );
			}
			
			return false;
		});
		//TODO 
		$smartPickLayer.on( 'click', '> a', function() {
			if( config.isLogin === 'Y' ) {
				var win = window.open( 
					'/mymart/popup/smartPickUp.do',
					'smartPickPopup',
					'height=665px,width=665px,scrollbars=yes');
				
				if( parseInt( navigator.appVersion ) >= 4 ) {
					win.window.focus();
				}
			} else {
				global.login( config.martCode, location.href);
			}
			return false;
		}).on( 'click', config.closeClass , function() {
			if( $smartPickLayer.find( config.checkedTodayClass ).is( ':checked') ) {
				
				$.cookie( config.cookieName, 'Y', { 
					expires : 1,
					path : '/'
				});
				
				$smartPickLayer.removeClass( config.activeClass );
			}
			return false;
		});
		
		$( config.completeLayerClass )
			.find( '.pickup-inner' )
			.on( 'click', 'button', function() {
				clearTimeout( timer );
				$smartPickLayer.removeClass( config.activeClass );
				
				location.reload();
				return false;
			});
		
		return this;
	};
	
})( jQuery, window, document );