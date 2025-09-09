var layerPopupFunc = function( objId, act, iframeSrc ) {
	var element = jQuery('#' + objId);
	var zIndex = 2001;
	var className = jQuery( element ).attr('class');

	jQuery(window).bind('resize', function(){
		layerLocInit();
	});

	jQuery(window).bind('scroll', function(){
		layerLocInit();
	});

	var layerInfo = {
		winWidth	: 0,
		winHeight	: 0,
		objWidth	: 0,
		objHeight	: 0,
		layerX	: 0,
		layerY	: 0
	};

	var layerCalc = function() {
		var winWidth = parseInt( jQuery(window).width(), 10 ); // window width
		var winHeight = parseInt( jQuery(window).height(), 10 ); // window height
		var objWidth = parseInt( jQuery(element).find('>div:eq(0)').width(), 10 ); // 팝업레이어 width
		var objHeight = parseInt( jQuery(element).find('>div:eq(0)').height(), 10 ); // 팝업레이어 height

		var winScroll = parseInt( jQuery(window).scrollTop(), 10 );
		var backHeight = winHeight + winScroll;

		if( jQuery(element).find('>div:eq(1)').length ) {
			var tmpHeight = objHeight + winScroll;
			jQuery(element).find('>div:eq(1)').css('height', backHeight + 'px');
		}

		if( objWidth == 0) {
			objWidth = parseInt( jQuery(element).width(), 10 );
		}

		if( objHeight == 0) {
			objHeight = parseInt( jQuery(element).height(), 10 );
		}

		jQuery('body').css('min-height', objHeight + 'px');

		var layerX = Math.ceil( ( winWidth - objWidth ) / 2 );
		var layerY = Math.ceil( ( winHeight - objHeight ) / 2 );
		layerY = layerY < 0 ? 0 : layerY;

		layerInfo = {
			winWidth	: winWidth,
			winHeight	: winHeight,
			objWidth	: objWidth,
			objHeight	: objHeight,
			layerX		: layerX,
			layerY		: layerY,
			backHeight	: backHeight
		};
	};

	var layerLocInit = function() {
		if( jQuery(element).css('display') == 'block' ) {
			layerCalc();

			jQuery(element).css({
				'position'	: 'fixed',
				'top'		: '0px',
				'left'		: '0px',
				'width'		: layerInfo.winWidth + 'px',
				'height'	: layerInfo.winHeight + 'px'
			});

			jQuery(element).find('>div:eq(0)').css({
				'position'			: 'relative',
				'left'				: layerInfo.layerX + 'px',
				'top'				: layerInfo.layerY + 'px'
			});
		}
	};

	this.init = function() {
		layerCalc();

		jQuery(element).css({
			'position'	: 'absolute',
			'top'		: '0px',
			'left'		: '0px',
			'width'		: layerInfo.winWidth + 'px',
			'height'	: layerInfo.winHeight + 'px'
		});

		var tmpIndex = 0;
		var thisIndex = 0;
		jQuery('.' + className).each(function() {
			if( jQuery(this).css('display') == 'block' ) {
				tmpIndex = jQuery(this).find('>div:eq(0)').css('z-index');
				if( tmpIndex != 'auto' && thisIndex < tmpIndex ) {
					thisIndex = parseInt( tmpIndex, 10 );
				}
			}
		});
		zIndex = parseInt( thisIndex, 10 ) + 2;
		tmpIndex = null; thisIndex = null;

		jQuery(element).find('>div:eq(0)').css({
			'position'			: 'fixed',
			'left'				: layerInfo.layerX + 'px',
			'top'				: layerInfo.layerY + 'px',
			'width'				: layerInfo.objWidth + 'px',
			'height'			: layerInfo.objHeight + 'px',
			'text-align'		: 'center',
			'vertical-align'	: 'middle',
			'border'			: '2px solid black',
			'z-index'			: zIndex
		});
		//alert( zIndex );

		// iframe로 레이어 팝업 구성시
		if( jQuery(element).find('>div:eq(0)').find('>iframe').length ) {
			var iframeObj = jQuery(element).find('>div:eq(0)').find('>iframe');
			jQuery( iframeObj ).css({
				'width'		: layerInfo.objWidth + 'px',
				'height'	: layerInfo.objHeight + 'px'
			});

			if( iframeSrc != '' ) {
				jQuery(iframeObj).attr('src', iframeSrc);
			}
		}

		zIndex = zIndex - 1;
		//alert( zIndex );

		if( !jQuery(element).find('>div:eq(1)').length ) {
			jQuery(element).append( "<div></div>" );

			jQuery(element).find('>div:eq(1)').css({
				'position'		: 'absolute',
				'top'			: '0px',
				'left'			: '0px',
				'width'			: '100%',
				'height'		: layerInfo.backHeight + 'px',
				'background'	: 'URL(/guide/images_layer_test/layer_back_5c5c5c_40x100.png) repeat-x scroll 50% 50% #5C5C5C',
				'opacity'		: '0.7',
				'z-index'		: zIndex
			});
		} else {
			jQuery(element).find('>div:eq(1)').css({
				'height'		: layerInfo.backHeight + 'px',
				'z-index'		: zIndex
			});
		}

		jQuery(element).show();
	};
}

function layerPopup(objId, act, iframeSrc) {
	if( act == 'open' ) {
		if( jQuery('#' + objId).attr('registCheck') == 'Y' ) {
			jQuery('#' + objId).show();
		} else {
			var thisLayerPopup;

			thisLayerPopup = new layerPopupFunc( objId, act, iframeSrc );
			thisLayerPopup.init();

			//thisLayerPopup = null;
		}
	} else if( act == 'close' ) {
		jQuery('#' + objId).hide();
	}
}

// 레이어 팝업 div=contents 내에 선언됐을 경우 사용
var VAR_layerWidth=0, VAR_layerHeight=0;
var VAR_fl_history_zIndex = 0;
function layerPopup2_resize( obj ) {
	var winWidth = parseInt( jQuery(window).width(), 10 );
	var winHeight = parseInt( jQuery(window).height(), 10 );

	if( jQuery('#' + obj).find('#jqLayer').length ) {
		jQuery('#' + obj).find('#jqLayer').css({
			'width' : winWidth + 'px',
			'height' : winHeight + 'px'
		});
	}

	var popLeft = (winWidth / 2) - (VAR_layerWidth / 2);
	var popTop = (winHeight / 2 ) - (VAR_layerHeight / 2);

	jQuery('#' + obj).find('>div:eq(0)').css({
		'top' : popTop + 'px',
		'left' : popLeft + 'px'
	});
}

function layerPopup2(objId, act) {
	if( act == 'open' ) {
		if( jQuery('#' + objId).length ) {
			// gnb, footer 가 팝업레이어 뚫고 나옴 방지
			if( jQuery('#contents').length ) {
				jQuery('#contents').css({
					'position' : 'relative',
					'z-index' : '200'
				});
			}

			// 최근본상품 레이어 뚫고 나옴 방지
			if( jQuery('.fl_history').length ) {
				if( VAR_fl_history_zIndex < 1 ) {
					VAR_fl_history_zIndex = jQuery('.fl_history').css('z-index');
				}
				jQuery('.fl_history').css('z-index', '1');
			}

			var thisObj = jQuery('#' + objId);

			if( jQuery(thisObj).attr('resizeEffect') != 'Y' ) {
				VAR_layerWidth = parseInt( jQuery(thisObj).width(), 10 );
				VAR_layerHeight = parseInt( jQuery(thisObj).height(), 10 );

				jQuery(window).bind('resize', function(){
					layerPopup2_resize(objId);
				});
			}

			var winWidth = parseInt( jQuery(window).width(), 10 );
			var winHeight = parseInt( jQuery(window).height(), 10 );

			var popLeft = (winWidth / 2) - (VAR_layerWidth / 2);
			var popTop = (winHeight / 2 ) - (VAR_layerHeight / 2);

			jQuery(thisObj).css({
				'position' : 'fixed',
				'top' : '0px',
				'left' : '0px',
				'z-index' : '9999'
			});

			if( jQuery(thisObj).find('#jqLayer').length ) {
				jQuery(thisObj).find('#jqLayer').show();
			} else {
				jQuery(thisObj).append('<div id="jqLayer"></div>');
				jQuery(thisObj).find('#jqLayer').css({
					'display' : 'block',
					'position' : 'absolute',
					'opacity' : '0.5',
					'width' : jQuery(document).width() + 'px',
					'height' : jQuery(document).height() + 'px',
					'background' : '#000000',
					'z-index' : '9998',
					'zoom' : '1',
					'left' : '0px',
					'top' : '0px'
				});
			}

			jQuery(thisObj).find('>div:eq(0)').css({
				'position' : 'absolute',
				'top' : popTop + 'px',
				'left' : popLeft + 'px',
				'z-index' : '9999'
			});

			jQuery(thisObj).show();
			jQuery(thisObj).attr('resizeEffect', 'Y');
		} else {
			return false;
		}
	} else if( act == 'close' ) {
		if( jQuery('#' + objId).length ) {
			// gnb, footer 가 팝업레이어 뚫고 나옴 방지
			if( jQuery('#contents').length ) {
				jQuery('#contents').css({
					'position' : 'relative',
					'z-index' : '100'
				});
			}

			// 최근본상품 레이어 뚫고 나옴 방지
			if( jQuery('.fl_history').length ) {
				if( VAR_fl_history_zIndex ) {
					jQuery('.fl_history').css('z-index', VAR_fl_history_zIndex);
				}
			}

			var thisObj = jQuery('#' + objId);
			jQuery(thisObj).hide();
		}
	}
}

function layerPopupBody(objId, act) {
	if( act == 'open' ) {
		jQuery('#jqLayer').show();
		jQuery('#' + objId).show();
	} else if( act == 'close' ) {
		jQuery('#jqLayer').hide();
		jQuery('#' + objId).hide();
	}
}