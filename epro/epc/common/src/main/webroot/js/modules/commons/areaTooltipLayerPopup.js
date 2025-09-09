(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.areaTooltipLayerPopup = factory( jQuery );
	}
}( window, function($) {
	var _areaTooltipLayerPopup = function(obj, elem, lTop, openArea) {
		var openEl ;
		if(openEl instanceof jQuery){
			openEl = openArea;
		}else{
			if(!openArea){
				openArea = 'body';
			}
			openEl = $(openArea);
		}
		
	    var $el = openEl.find('[data-layer='+elem+']');

	    obj.addClass('active');
	    
	    openEl.addClass('layer-popup-active').append($el).append('<div class="mask"/>');
	    $el.css('top', lTop);
	    $el.fadeIn(200);

	    openEl.find('.mask, .js-close').click(function(){
	        $el.hide();
	        obj.removeClass('active');
	        openEl.find('.mask').remove();
	        openEl.removeClass('layer-popup-active');
	    });
	};
	
	return _areaTooltipLayerPopup;
}));