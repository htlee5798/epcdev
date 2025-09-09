(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root._InfiniteScroll = factory();
	}
}( window, function() {
	var InfiniteScroll = function( options ) {
		var defaults = {
			threshold : 1000,
			scrollPosition : 0,
			setHeight: function() {
				defaults.elementPostion = (defaults.container.outerHeight(true) + defaults.container.offset().top ) * 2;
			},
			callback : function() {}
		},
		documentHeight = document.documentElement.clientHeight;

		function init() {
			if( options ) {
				for( var prop in options ) {
					defaults[ prop ] = options[ prop ];
				}
			}
		}

		this.setPosition = function( pos ) {
			defaults.scrollPosition = pos;

			var scrollTop = ( pos + documentHeight ) * 2;

			defaults.elementPostion = (defaults.container.outerHeight(true) + defaults.container.offset().top);

			if( scrollTop >= defaults.elementPostion ) {
				defaults.callback( defaults );
			}
		};

		init();
	}
	
	return InfiniteScroll;
}));