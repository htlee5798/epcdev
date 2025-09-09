;(function( $, window, document, undefined ) {
	'use strict';

	var oneSeconds = 1e3;
	
	var Timer = function($el, options) {
		var _this = this;
		
		_this.config = {
			interval : oneSeconds,
			limitSeconds : 0,
			$wrapper : null,
			callback : function(t, c) {
				_this.setFormalizedTimeText(c.remainSeconds);
			}
		};
		
		$.extend(this.config, options);
	};
	
	Timer.prototype = {
		
		start : function() {
			this.setFormalizedTimeText(this.config.remainSeconds);
			
			this._timer = this.create();				
		},
		
		create : function() {
			var _this = this
			  , _config = this.config;
			
			return setInterval(function() {
				_config.remainSeconds -= _config.interval / oneSeconds;
				
				if(_config.remainSeconds <= _config.limitSeconds) {
					_this.stop();
				}
				
				_config.callback(_this, _config);
			}, this.config.interval);
		},
		
		stop : function() {
			clearInterval(this._timer);
		},
		
		setFormalizedTimeText : function(remainSeconds) {
			var remainDate = this.convertToRemainSecondsToDate(remainSeconds);
			
			this.config.$wrapper.find('[data-format]').each(function(i, v) {
				var $v = $(v);
				
				$v.text(remainDate.format($v.data('format')));	
			});
		},
		
		convertToRemainSecondsToDate : function(remainSeconds) {
			var initDate = new Date(null);
			
			initDate.setHours(null);
			initDate.setSeconds(remainSeconds);
			
			return initDate;
		}
		
	};
	
	$.fn.setTimer = function(options) {
		if(!options.currentSeconds || !options.remainSeconds || !options.$wrapper) {
			$.utils.log('currendSeconds or remainSeconds or $wrapper is necessary');
			return;
		}
		
		return new Timer($(this), options);
	};
})(jQuery, window, document);