/**
 * 
 */
(function( $, window, document, undefined ) {
	'use strict';
	/**
	 * See (http://jquery.com/).
	 * @name jQuery
	 * @class 
	 * See the jQuery Library  (http://jquery.com/) for full details.  This just
	 * documents the function and classes that are added to jQuery by this plug-in.
	 */

	/**
	 * See (http://jquery.com/)
	 * @name fn
	 * @class 
	 * See the jQuery Library  (http://jquery.com/) for full details.  This just
	 * documents the function and classes that are added to jQuery by this plug-in.
	 * @memberOf jQuery
	 */
	
	
	/**
	 * <h5>allMenu</h3>
	 * <p>전시 상품 전체 메뉴 레이어 팝업 공통 모듈</p>
	 * 
	 * <h5>Require Options</h5>
	 * <ul>
	 * 	<li style="list-style-type:none"><strong>1. id : </strong>[ Default ] $.cookie( '__categoryId' ) </li>
	 * 	<li style="list-style-type:none"><strong>2. menuClick : </strong>[ Function ] 메뉴를 선택하였을 때의 callback 함수. 선언하지 않으면 a 태그의 link url 로 이동한다. </li>
	 * </ul>
	 * @class allMenu
	 * @memberOf jQuery.fn
	 * @param{Object} options - 모듈 초기 설정값
	 * @example <caption>JS - init 1</caption>
	 * <script type="text/javascript">
	 * 	$( 'a.icon-common-allmenu' ).on( 'click', function() {
	 * 		$( this ).allMenu({
	 * 			menuClick : function( $el ) {
	 * 				//slide를 해당 위치로 이동시킨다.
	 * 				var direction = $.MAIN_SLIDE.swipeDirection,
	 * 					href = $el.attr( 'href' ),
	 * 					menu = menuData.filter(function( v ) { return _LMAppUrlM + v.uri === href; });
	 * 
	 * 					if( menu.length === 0 ) { return; }
	 * 				defaultCategoryId = menu[ 0 ].id;
	 * 				moveToSlide();
	 * 			}
	 * 		});
	 * 		return false;
	 * 	});
	 * </script>
	 * 
	 */
	$.fn.allMenu = function( options ) {
		var defaults = {
			id : '',
			url : '/mobile/ajax/special/mobileSpecialFullReview.do',
			styles : {
				position : 'fixed',
				top : 0,
				left : 0,
				right : 0,
				bottom : 0,
				zIndex : 1000,
				backgroundColor : '#fff',
				overflow : 'auto'
			},
			menuClick : null,
			afterOpen : null,
			afterClose : null
		},
		config = $.extend( true, defaults , options || {} );
		
		if( config.id === '' ) {
			config.id = $.cookie !== undefined ? $.cookie( '__categoryId' ) : utils.cookie.get( '__categoryId' );
		}
		
		var $target = $( '#' + config.id + '_special_review_layer' );

		render();
		
		return this;
		
		function render() {
			$.get( config.url, function( resData ) {
				var html = '<div id="' + config.id + '_special_review_layer"></div>';
				
				$( 'body' ).append( html );
				
				$target = $( '#' + config.id + '_special_review_layer' ).css( config.styles ).html( resData );
				$target.find( '.btn-close' ).removeAttr( 'onclick' );
				bindEvent();
			});
		}
		
		function open() {
			$target.show();
			if( config.afterOpen ) {
				config.afterOpen();
			}
		}
		
		function close() {
			$target.remove();
			if( config.afterClose ) {
				config.afterClose();
			}
		}
		
		function bindEvent() {
			$target.on( 'click', '.btn-close', function( e ) {
				close();
				return false;
			}).on( 'click', 'a', function() {
				var $this = $( this );
				if( config.menuClick ) {
					config.menuClick( $this );
					close();
					return false;
				}
			});
		}
	};
})( jQuery, window, document );