(function($, window, document, undefined) {
	'use strict';

	$.fn.searchRecommendKeywords = function(options) {
		var defaults = {
			keywords: [],
			slideOptions: {
				mode: 'fade',
				pager: false,
				auto: false,
				speed: 0,
				onSliderLoad: function(activeIdx) {
					var slider = this;

					_wac !== undefined
						&& _wac.bx.setAccessibility(slider, config.slideOptions.autoHover);
				},
			},
		},
			config = $.extend(true, {}, defaults, options || {}),
			slide = '<div class="wrap">',
			$frag = $(document.createDocumentFragment()),
			keyword = '',
			keywordLen = config.keywords.length;

		for (var i = 0; i < keywordLen; i++) {
			keyword = config.keywords[i];
			if (i !== 0 && i % 4 === 0) {
				slide = slide + '</div><div class="wrap">';
			}
			slide = slide + '<a href="' + keyword.LINK_IMG_PATH + '">' + keyword.LINK_NM + '</a>';
		}
		slide = slide + '</div>';
		$frag.append(slide);

		this
			.append($frag)
			.bxSlider(config.slideOptions);

		return this;
	};
})(jQuery, window, document, undefined);