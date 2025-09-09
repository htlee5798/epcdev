;(function( $, window, document, undefined ) {
	'use strict';
	
	var $window = $(window)
		
	var FloatContainer = function(element, option) {
		this.$startTarget = null,
		this.$endTarget = null,
		this.element = $(element);
		this.elementPosetop;
		this.defaults = {
			className: 'enable',
			startTarget:'body',
			endTarget:'body',
			inView : false,
			emptySpace : 0
		},
		
		$.extend(true, this.defaults, option || {});
		
		this.$startTarget = $(this.defaults.startTarget);
		this.$endTarget = $(this.defaults.endTarget);
		
		this.init();
		return this;
	};
	
	FloatContainer.prototype = {
		init: function() {
			this.documentClick();
			var _this = this;
			$window.on( 'scroll', $.proxy(_this.move,_this));
			$(document).on( 'click', $.proxy(_this.documentClick,_this));
			
			
		},
		move : function(){
			var scrollTop = $window.scrollTop();
			var tp = this.$startTarget.offset().top;
			var tb = tp + this.$startTarget.height();
			
			if(this.element.hasClass(this.defaults.className)){
				this.elementPosetop = this.$endTarget.offset().top  + this.$endTarget.outerHeight(true) -  parseInt(this.$endTarget.css("padding-bottom")) + this.element.outerHeight(true);
			}else{
				this.elementPosetop = this.$endTarget.offset().top + this.$endTarget.outerHeight(true) - parseInt(this.$endTarget.css("padding-bottom"));
			}
			
			var viewHeight =  this.elementPosetop - $(window).height();
			
			if(this.defaults.inView){
				tp = tp - $(window).height() + this.defaults.emptySpace;
			} 
			
			if(scrollTop < tp || scrollTop  >  viewHeight){
				if(!this.element.hasClass(this.defaults.className)) return;
				this.element.removeClass(this.defaults.className);
			}else{
				if(this.element.hasClass(this.defaults.className)) return;
				this.element.addClass(this.defaults.className);
			}
			
			
		},
		set: function() {
			var _this = this;
			return _this;
		},
		documentClick: function() {
			var _this = this;
			setTimeout(function(){ _this.move(); }, 50);
		},
		destroy: function() {
			$window.unbind( 'scroll', this.move);
			$(document).unbind( 'click', this.documentClick);
		}
	};
	
	$.fn.floatContainer = function(option) {
		return this.each(function( i, v ) {
			var $v = $( v );
			
			if( !$v.data( 'floatContainer' ) ) {
				if(typeof option == "string"){
					return;
				}
				$v.data( 'floatContainer' , new FloatContainer( v, option) );
				$v.data( 'floatContainer' ).set();
			} else {
				if(typeof option == "string"){
					$v.data( 'floatContainer' )[option]();
				}else{
					$v.data( 'floatContainer' ).set();
				}
				
			}
		});
	};
	
})( jQuery, window, document );