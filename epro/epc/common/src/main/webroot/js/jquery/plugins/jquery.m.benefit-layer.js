;(function( $, window, document, undefined ) {
	'use strict';

	var $window = $(window),
		$document = $(document)

	var BenefitLayer = function(element, option) {
		this.$body = null,
		this.element = $(element);
		this.$mask;
		this.defaults = {
			className: 'benefit-layer',
			styles: '',
			templateName : 'benefitLayer',
			after: null,
			body:'body',
			mask:false
		},
		this.$layer = null;

		$.extend(true, this.defaults, option || {});

		if(this.defaults.body instanceof jQuery){
			this.$body = this.defaults.body;
		}else{
			this.$body = $(this.defaults.body);
		}

		this.defaults.data = parseBenefitData( this.element.data( 'benefit' ) );
		this.init();
		return this;
	};

	BenefitLayer.prototype = {
		init: function() {
			var _this = this;

			$window.on( 'resize', function() {
				_this.close();
			});
		},
		set: function() {
			var _this = this;

			if( this.$body.find( '.benefit-layer' ).not( _this.$layer ).length > 0 ) {
				this.$body.find( '[data-benefit]' ).not( _this.element ).each(function( i, v ) {
					var $v = $( v );

					if( $v.data( 'benefitlayer' ) ) {
						$v.data( 'benefitlayer' ).close();
					}
				});
			}

			if( _this.$layer !== null ) {
				_this.close();

				return _this;
			}
			_this.element.addClass( 'active' );

			$document.off('click.close').on('click.close', function(e) {
				var _target = e.target;
				if(_target === _this.element[0] || $(_target).closest( _this.$layer).length > 0) {
					return;
				}
				_this.close();
			});

			if(_this.defaults.mask){
				this.$mask = $('<div class="mask"></div>');
				this.$mask.on('touchmove', $.proxy( _this.maskTouchMove, _this));
				this.$mask.on('click',  $.proxy(_this.close, _this));
				this.$mask.appendTo(this.$body);
			}

			_this.$layer = $( $.render[ _this.defaults.templateName ](_this.defaults) ).appendTo(this.$body);

			if( _this.defaults.styles !== '' ) {
				_this.$layer.css( _this.defaults.styles );
			} else {
				_this.$layer
				.css({
					display: 'block',
					top: _this.element.offset().top + _this.element.height() + 5 - this.$body.offset().top,
					left: function() {
						var _left = Math.max(_this.element.offset().left + _this.element.outerWidth() - _this.$layer.outerWidth(true), 0);

						return _left;
					}
				});
			}

			_this.$layer
				.find('.js-close')
				.on('click.close', $.proxy(_this.close, _this));

		},
		maskTouchMove: function(e) {
			this.close();
		}
		,close: function(e) {
			var _this = this;
			if( _this.$layer !== null ) {
				_this.element.removeClass( 'active' );
				_this.$layer.remove();
				_this.$layer = null;
				$document.off('click.close');
				if(_this.defaults.mask){
					_this.$mask.off('click');
					_this.$mask.remove();
					_this.$mask = null;
				}
			}
		}
	};

	/*
	 * data split : data-benefit="온라인상품할인!123원@카드상품할인!5%@"
	 * */
	function parseBenefitData(data) {
		var val = null,
			arr = [];

		data = data.split('@');

		for(var i in data) {
			if(!data[i]) {
				return arr;
			}

			val = data[i].split('!');

			arr.push({
				'title': val[0],
				'value': $.utils.comma( val[1] )
			});
		}
	}

	$.fn.benefitLayer = function(option) {
		return this.each(function( i, v ) {
			var $v = $( v );

			if( !$v.data( 'benefitlayer' ) ) {
				$v.data( 'benefitlayer' , new BenefitLayer( v, option) );
				$v.data( 'benefitlayer' ).set();
			} else {
				$v.data( 'benefitlayer' ).set();
			}
		});
	};

	$window.on('unload', function() {

		$document.find( '[data-benefit]' ).each(function( i, v ) {
			var $v = $( v );

			if( $v.data( 'benefitlayer' ) ) {
				$v.data( 'benefitlayer' ).close();
			}
		});

	});

})( jQuery, window, document );