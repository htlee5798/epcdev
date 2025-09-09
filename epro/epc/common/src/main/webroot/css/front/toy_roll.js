// 메인페이지 content slide 기능
var prevContentIdx=0, mainContentCnt = 0, mainRollObj = null;
function slideMainContent( obj, rollSpeed, setDealy ) {
	if( jQuery(obj).length ) {
		var nowIdx = 0;
		var nowObj, prevObj;

		var contentWidth = 0;
		var slideResult;

		if( !mainContentCnt ) {
			mainContentCnt = parseInt( jQuery(obj).find('>ul>li').length, 10 );
		}

		if( jQuery(obj).find('>ul>li').length ) {
			jQuery(obj).find('>ul>li').each(function() {
				if( jQuery(this).find('img').length ) {
					jQuery(this).find('img').css('cursor', 'pointer');
				}
			});
		}

		jQuery('#rolling_scene').find('>div').hide();
		jQuery('#rolling_scene').find('>div:eq(0)').show();

		var autoRoll = function() {
			//alert( idx );
			var tmpIdx = prevContentIdx + 1;
			if( tmpIdx > mainContentCnt - 1 ) {
				tmpIdx = 0;
			}
			jQuery(obj).find('>ul>li:eq(' + tmpIdx + ')').trigger('click');
			mainRollObj = setTimeout(function() { autoRoll(); }, setDealy);
		};

		jQuery(obj).find('>ul>li').bind('click', function() {
			nowObj = jQuery('#rolling_scene').find('>div:eq(' + jQuery(this).index() + ')');
			prevObj = jQuery('#rolling_scene').find('>div:eq(' + prevContentIdx + ')');

			if( !jQuery(nowObj).length ) { return false; }
			if( !jQuery(prevObj).length ) { return false; }

			if( prevContentIdx != jQuery(this).index() ) {
				if( jQuery( prevObj ).css('left') != '0px' ) {
					return false;
				}

				jQuery(obj).find('>ul>li').each(function() {
					jQuery(this).removeClass('on');
				});

				jQuery(this).addClass('on');

				contentWidth = jQuery( prevObj ).width();

				if( prevContentIdx < jQuery(this).index() ) {
					jQuery( prevObj ).animate({left:'-'+contentWidth, top:0}, rollSpeed);
					jQuery( nowObj ).css({'left':contentWidth + 'px','display':'block'});
				} else {
					jQuery( prevObj ).animate({left:contentWidth, top:0}, rollSpeed);
					jQuery( nowObj ).css({'left':'-'+contentWidth + 'px','display':'block'});
				}

				jQuery( nowObj ).animate({left:0, top:0}, rollSpeed);

				prevContentIdx = jQuery(this).index();
			}
		});

		nowIdx = null; nowObj = null; prevObj = null;
		contentWidth = null; slideResult = null;

		if( jQuery(obj).find('.prev').length ) {
			if( jQuery(obj).find('.prev').find('img').length ) {
				jQuery(obj).find('.prev').find('img').css('cursor', 'pointer');
			}

			jQuery(obj).find('.prev').bind('click', function() {
				var tmpIdx = prevContentIdx - 1;
				if( tmpIdx < 0 ) {
					tmpIdx = mainContentCnt - 1;
				}
				jQuery(obj).find('>ul>li:eq(' + tmpIdx + ')').trigger('click');
			});
		}

		if( jQuery(obj).find('.next').length ) {
			if( jQuery(obj).find('.next').find('img').length ) {
				jQuery(obj).find('.next').find('img').css('cursor', 'pointer');
			}

			jQuery(obj).find('.next').bind('click', function() {
				var tmpIdx = prevContentIdx + 1;
				if( tmpIdx > mainContentCnt - 1 ) {
					tmpIdx = 0;
				}
				jQuery(obj).find('>ul>li:eq(' + tmpIdx + ')').trigger('click');
			});
		}

		jQuery(obj).parent().bind('mouseover', function() {
			clearTimeout( mainRollObj );
		});

		jQuery(obj).parent().bind('mouseleave', function() {
			mainRollObj = setTimeout(function() { autoRoll(); }, setDealy);
		});

		mainRollObj = setTimeout(function() { autoRoll(); }, setDealy);
	}
}

// 메인페이지 content btn on, off
function mainContentBtn( obj, targetId ) {
	if( jQuery( obj ).attr('effect') != 'Y' ) {
		jQuery( obj ).bind('mouseover', function(event) {
			jQuery('#' + targetId).show();
		});

		jQuery( obj ).bind('mouseleave', function(event) {
			if( event.relatedTarget.nodeName != 'IMG' ) {
				jQuery('#' + targetId).hide();
			} else {
				if( jQuery('#' + targetId).find('>img').get(0) != event.relatedTarget ) {
					jQuery('#' + targetId).hide();
				}
			}
		});

		jQuery('#' + targetId).show();
	}
}

try {
  document.execCommand("BackgroundImageCache", false, true);
} catch(err) {}
