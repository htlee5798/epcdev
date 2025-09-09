//(function( $, window, document, undefined ) {
//	'use strict';
//	
	$.fn.slide = function( options ) {
		console.log( 'slide' );
		var defaults = {
			initialSlide : 1,
			slideToRow : 1,
			slideToShow : 'auto',
			time : 3000,
			wrap : '.slide-wrap',
			navigation : '.slide-nav',
			slide : '.slide',
			nextButton : '.btn-next',
			prevButton : '.btn-prev',
			pauseButton : '.btn-pause',
			playButton : '.btn-play',
			variableWidth : false,
			auto : false,
			hover : true,
			infinite : true,
			effect : ''
		},
		config = $.extend( true, {}, defaults, options || {} );
		
		return this.each(function() {
			
		});
		//TODO play slide
		function play(){
			
		}
		//TODO pause slide
		function pause() {
			
		}
		//TODO next slide
		function next() {
			
		}
		//TODO prev slide
		function prev() {
			
		}
		//TODO move to slide
		function move( index ) {
			
		}
	};
//})( jQuery, window, document );