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
	 * <h5>moreBar</h3>
	 * <p>리스트 더보기 시 more 이미지 노출</p>
	 * @class moreBar
	 * @memberOf jQuery.fn
	 * @param{Boolean} argument - 로딩 여부
	 * @example <caption>JS - more</caption>
	 * <script type="text/javascript">
	 * 	//default - true
	 *	//show more
	 * 	$( '.list' ).moreBar();
	 * </script>
	 * @example <caption>JS - unloading</caption>
	 * <script type="text/javascript">
	 * 	//close more
	 * 	$( '.list' ).moreBar( false );
	 * </script>
	 * 
	 */
	$.fn.moreBar = function( isShow ) {
		var $this = this,
			loadFunction = isShow === undefined || isShow ? open : close,
			html = '<div class="more-bar"><span class="spinner">잠시만 기다려주세요.</span></div>';

		loadFunction();
		
		return this;
		
		function open() {
			if( $this.find( '.more-bar' ).length === 0 ) {
				$this.append( html );
			}

			$this.find( '.more-bar' ).show();
		}
		
		function close() {
			$this.find( '.more-bar' ).hide();
		}
	};

})( jQuery, window, document );