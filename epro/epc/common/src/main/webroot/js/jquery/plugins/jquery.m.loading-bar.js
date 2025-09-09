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
	 * <h5>loadingBar</h3>
	 * <p>서버와 통신 시( ajax ) loading 이미지 노출</p>
	 * @class loadingBar
	 * @memberOf jQuery.fn
	 * @param{Boolean} argument - 로딩 여부
	 * @example <caption>JS - loading</caption>
	 * <script type="text/javascript">
	 * 	//default - true
	 *	//show loading
	 * 	$( 'body' ).loadingBar();
	 * </script>
	 * @example <caption>JS - unloading</caption>
	 * <script type="text/javascript">
	 * 	//close loading
	 * 	$( 'body' ).loadingBar( false );
	 * </script>
	 *
	 */
	$.fn.loadingBar = function( isShow ) {
		var $this = this,
			isApp = $.utils ? ($.utils.isiOSLotteMartApp() || $.utils.isAndroidLotteMartApp()) : false,
			loadFunction = isShow === undefined || isShow ? open : close,
			html = $.render.loadingBarForMobile();

		var wrapLoadingBarElement = $this[0].querySelector( '.wrapLoadingBar' );

		loadFunction();

		return this;

		function open() {
			if(isApp) {
                if (window.LOTTEMARTDID && window.LOTTEMARTDID['isLoading']) {
                    window.LOTTEMARTDID.isLoading(true);
                } else {
                    schemeLoader.loadScheme({key: 'lodingStart'});
                }
			} else {
				if( wrapLoadingBarElement === null ) {
					$this.append( html );

					wrapLoadingBarElement = $this[0].querySelector( '.wrapLoadingBar' );
				}

				wrapLoadingBarElement.classList.add( 'pageLoading' );
				wrapLoadingBarElement.style.display = 'block';
			}
		}

		function close() {
			if(isApp) {
                if (window.LOTTEMARTDID && window.LOTTEMARTDID['isLoading']) {
                    window.LOTTEMARTDID.isLoading(false);
                } else {
                    schemeLoader.loadScheme({key: 'lodingEnd'});
                }
			} else {
				if( wrapLoadingBarElement === null ) {
					return;
				}

				wrapLoadingBarElement.classList.remove( 'pageLoading' );
				wrapLoadingBarElement.style.display = 'none';
			}
		}
	};
})( jQuery, window, document );