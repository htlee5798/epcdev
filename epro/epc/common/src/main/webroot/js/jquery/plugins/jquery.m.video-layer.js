;(function( $, window, document, undefined ) {
	'use strict';

	var $body = $('body');
	var enScrollTop = 0;
	var beScrollTop = 0;
	var scrollThreshold = 150;

	$.templates('mobileVideoLayerInner',
	'<div class="wrap-movie-kok-inner" style="top:{{:posTop}}">'+
		'{{if ~isYoutubleUrl(videoUrl)}}' +
			'<iframe id="kokVideo" style="height:{{:videoHeight}}" frameborder="0" allowfullscreen src="{{:videoUrl}}&amp;enablejsapi=1&amp;origin=http:%2F%2Fm.lottemart.com" title="동영상"></iframe>' +
		'{{else}}' +
			'<video id="kokVideo" style="height:{{:videoHeight}}" src="{{:videoUrl}}" controls="controls"></video>' +
		'{{/if}}' +

		'<button name="btnClose" type="button" class="close">닫기</button>' +
	'</div>'
	);

	function movieLayerRemove(tDelay) {
		var $tMovieLayer = $body.find('.wrap-movie-kok-inner');
		var $prodWrap = $('.product-wrap');
		$tMovieLayer.fadeOut(tDelay);
		if ($prodWrap.length > 0) $prodWrap.removeClass('video-layer');
		setTimeout(function() {
			$tMovieLayer.remove();
		}, tDelay + 99);
	}

	$(window).on('scroll', function() {
		enScrollTop = window.scrollY;
		if (Math.abs(enScrollTop - beScrollTop) < scrollThreshold) return false;
		movieLayerRemove(400);
		beScrollTop = enScrollTop;
	});

	var VideoLayer = function($el, options) {
		var $elWrap = $el.closest('.product-wrap');
		var _flagElWrap = ($elWrap.length > 0);

		this.config = {
			videoUrl : $el.data('video'),
			templateId : 'mobileVideoLayerInner',
			posTop : (_flagElWrap) ? $elWrap.offset().top + 'px' : 0,
			videoHeight : (_flagElWrap) ? $elWrap.height() : '20rem'
		};

		$.extend(this.config, options);
		if (_flagElWrap) $elWrap.addClass('video-layer');

		this.init();
		return this;
	};

	VideoLayer.prototype = {
		init: function() {
			this.render();
			this.bindEvent();
		},

		render : function() {
			var layer = $.render[this.config.templateId](this.config);

			this.$layer = $(layer).appendTo($body);
		},

		bindEvent : function() {
			// 닫기
			this.$layer.on('click', '[name=btnClose]', function(){
				movieLayerRemove(400);
			});
		}
	};

	$.fn.videoLayer = function(options) {
		return new VideoLayer($(this), options);
	};
})(jQuery, window, document);