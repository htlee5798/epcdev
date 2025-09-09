/**
 * 
 */

;(function($, window, document) {
	"use strict";
	
	$.fn.infiniteScroll = function(options) {
		var infinite = function(element, options) {
			var SCROLL_EVENT = 'scroll.infiniteScroll',
				$win = $(window),
				$element = $(element),
				$winHeight = $win.height(),
				$winTop = 0,
				$eleHeight = 0,
				$eleTop = 0,
				defaults = {
					threshold: 500,
					loadingHTML: '<div class="more-bar"><span class="spinner">잠시만 기다려주세요.</span></div>',
					dataLoadEnd: function(isEmpty) {
						if(isEmpty === true) {
							$element.next('.more-bar').remove();
							return;
						}
						$element.next('.more-bar').css({display:'none'});
						$win.on(SCROLL_EVENT, scrollEvent);
					}
				};

			function init() {
				$.extend(defaults, options);
				
				$eleHeight = $element.height();
				
				$win.off(SCROLL_EVENT).on(SCROLL_EVENT, scrollEvent);
			}
			
			function scrollEvent() {
				$winTop = $win.scrollTop();
				$eleHeight = $element.height();
				$eleTop = $element.offset().top;

				if(($winTop + $winHeight + defaults.threshold) >= ($eleHeight + $eleTop)) {
					if(defaults.callback) {
						if($element.next().is('.more-bar')) {
							$element.next('.more-bar').css({display:''});
						} else {
							$element.after(defaults.loadingHTML);
						}
						$win.off(SCROLL_EVENT);
						defaults.callback(defaults);
					}
				}
			}
			
			init();
		};
		
		return this.each(function() {
			return infinite(this, options);
		});
	};
})(jQuery, window, document);