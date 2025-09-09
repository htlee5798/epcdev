(function( $, window, document ) {
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
	 * <h5>wish</h3>
	 * <p>상품 찜하기</p>
	 * 
	 * <h5>Require Options</h5>
	 * <ul>
	 * 	<li style="list-style-type:none"><strong>1. prodCd - 상품ID</strong></li>
	 * 	<li style="list-style-type:none"><strong>2. categoryId - 카테고리ID</strong></li>
	 * 	<li style="list-style-type:none"><strong>3. prodAreaIdx </strong></li>
	 * </ul>
	 * @class wish
	 * @memberOf jQuery.fn
	 * @param{Object} options - 모듈 초기 설정값
	 * @example <caption>JS - init</caption>
	 * <script type="text/javascript">
	 * 	$( '.zzim button' ).on( 'click', function( e ) {
	 * 		$( this ).wish();
	 * 		return false;
	 * 	});
	 * </script>
	 * 
	 */
	$.fn.wish = function( options ) {
		var $this = this,
			_defaults = {
				prodCd : '',
				categoryId : '',
				prodAreaIdx : '',
				overseaYn : 'N',
				add : function () {},
				remove : function () {}
			},
			loc = location.href,
			config = $.extend( true, _defaults , options || $this.data() );
		
		var overseaYn ="N";

		if(overseaYn === 'Y') {
            alert(view_messages.wish.fail);
            return;
		}
		
		if( global.isLogin( loc ) ) {
			if( !global.isMember() ) {
				alert( view_messages.member.no_member);
				return;
			}
			
			var activeClass = $this.hasClass( 'active' ) ? 'cancel' : 'access';
			
			if( activeClass === 'cancel' ) {
				global.deleteWish({
					prodCd : config.prodCd,
					categoryId : config.categoryId,
					forgnDelyplYn : config.overseaYn
				}, function( resData ) {
					config.remove(resData);
					$this.removeClass( 'active' );
				});
			} else {
				global.addWish({
					prodCd : config.prodCd,
					categoryId : config.categoryId,
					forgnDelyplYn : config.overseaYn
				}, function( resData ) {
                    config.add(resData);
					$this.addClass( 'active' );
				});
			}
		}

		return this;
	};
})( jQuery, window, document );