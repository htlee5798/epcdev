;(function( $, window, document, undefined ) {
	'use strict';

	var $window = $(window),
		$document = $(document),
		$body = null;

	var BenefitLayer = function(element, option) {
		this.element = $(element);
		this.defaults = {
			className: '',
			styles: '',
			templateName : 'benefitLayer',
			after: null
		},
		this.$layer = null;

		$body = $( 'body' );

		$.extend(true, this.defaults, option || {});

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

			if( $body.find( '.benefit-layer' ).not( _this.$layer ).length > 0 ) {
				$body.find( '[data-benefit]' ).not( _this.element ).each(function( i, v ) {
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

			_this.$layer = $( $.render[ _this.defaults.templateName ](_this.defaults) ).appendTo($body);

			if( _this.defaults.styles !== '' ) {
				_this.$layer.css( _this.defaults.styles );
			} else {
				_this.$layer
				.css({
					display: 'block',
					top: _this.element.offset().top + _this.element.height() + 5,
					left: function() {
						var _left = Math.max(_this.element.offset().left + _this.element.outerWidth() - _this.$layer.outerWidth(true), 0);

						return _left;
					}
				});
			}
			
			_this.tabMove();

			_this.$layer
				.find('.js-close')
				.on('click.close', $.proxy(_this.close, _this))
				.on('keydown.close', $.proxy(_this.enterClose, _this));
		},
		close: function(e) {
			var _this = this;
			
			if(_this.$layer !== null) {
				_this.element.removeClass( 'active' );
				_this.$layer.remove();
				_this.$layer = null;
				$document.off('click.close');
			}
		},
		enterClose: function(e) {
			var _this = this;
			
			if(_wac.isEnterEvent(e)) {
				_this.element.removeClass( 'active' );
				_this.$layer.remove();
				$document.off('keydown.close');
				_this.element.focus();
			}
		},
		tabMove: function() {
			var _this = this,
				$closeBtn = _this.$layer.find('.js-close'),
				$nextHiddenBtn = $('<a href="javascript:;" class="blind">Next hidden button</a>');
			
			$nextHiddenBtn.appendTo(_this.$layer);
			
			function _layerClose() {
				_this.element.removeClass( 'active' );
				_this.$layer.remove();
				_this.$layer = null;
			}
			
			_this.element
				.off('keydown.focusEvent')
				.on('keydown.focusEvent', function(e) {
					if(_wac.isTabEvent(e)) {
						setTimeout(function() {
							$closeBtn.focus();
						}, 20);
					} else if(_wac.isShiftTabEvent(e) && _this.$layer !== null) {
						_layerClose();
					}
				});
			
			$closeBtn
				.off('keydown.closeEvent')
				.on('keydown.closeEvent', function(e) {
					if (_wac.isTabEvent(e) || _wac.isShiftTabEvent(e)) {
						e.preventDefault();
						
						_layerClose();
						setTimeout(function() {
							_this.element.focus();
						}, 20);
					}
				});
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
})( jQuery, window, document );