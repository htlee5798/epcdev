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
	 * <h5>expiredContent</h3>
	 * <p>페이지 섹션 캐싱시간을 체크하여 만기시간이 지난 페이지가 활성화가 되면 갱신시킨다.</p>
	 * <p>페이지의 DOM의 <code>data()</code> 속성에 모듈의 인스턴스를 생성한다.</p>
	 * <h5>Options</h5>
	 * <ul>
	 * 	<li style="list-style-type:none"><strong>1. expiredTime : 10</strong> - [ Default ] 10분</li>
	 * </ul>
	 * <h5>API</h5>
	 * <ul>
	 * 	<li style="list-style-type:none"><strong>1. reset() </strong> - 페이지 섹션 캐싱시간을 설정된 시간으로 다시 초기화한다.</li>
	 * 	<li style="list-style-type:none"><strong>2. isExpired() </strong> - 페이지 섹션 캐싱시간이 지났는지 여부를 판단해준다.</li>
	 * </ul>
	 * @class expiredContent
	 * @memberOf jQuery.fn
	 * @param{Object} options - 모듈 초기 설정값
	 * @example <caption>JS - init 1</caption>
	 * <script type="text/javascript">
	 * 	$( 'swipe-slide' ).expiredContent();
	 * </script>
	 * @example <caption>JS - init 2</caption>
	 * <script type="text/javascript">
	 * 	$( 'swipe-slide' ).expiredContent({
	 * 		expiredTime : 5
	 * 	});
	 * </script>
	 * @example <caption>JS - reset()</caption>
	 * <script type="text/javascript">
	 * 	//get instance
	 * 	var expiredContent = $( 'swipe-slide-active' ).data( 'expiredcontent' );
	 * 	expiredContent.reset();
	 * </script>
	 * @example <caption>JS - isExpired()</caption>
	 * <script type="text/javascript">
	 * 	//get instance
	 * 	var expiredContent = $( 'swipe-slide-active' ).data( 'expiredcontent' );
	 * 	//return true or false
	 * 	expiredContent.isExpired();
	 * </script>
	 * 
	 */
	if(typeof Date.prototype.addMinutes != 'function') {
	    Date.prototype.addMinutes = function(n) {
	        var dat = new Date(this.valueOf());
	        dat.setMinutes(dat.getMinutes() + n);
	        return dat;
	    };
	};
	$.fn.expiredContent = function( options ) {
		var $this = this;
		
		$this.data( 'expiredcontent', new ExpiredContent( $this, options ) );
		
		return this;
		
		
		
		function ExpiredContent ( $element, obj ) {
			var _this = this;
			
			_this.defaults = {
				expiredTime : 10
			};
			
			for( var prop in obj ) {
				_this.defaults[ prop ] = obj[ prop ];
			}
			init();
			
			this.reset = reset;
			this.isExpired = isExpired;
			
			function reset () {
				var date = new Date(),
					min = date.addMinutes( _this.defaults.expiredTime );
	
				_this.time = min.getTime();
			}
			
			function init () {
				var date = new Date(),
					min = date.addMinutes( _this.defaults.expiredTime );
				
				_this.time = min.getTime();
			}
			
			function isExpired () {
				var date = new Date(),
					time = date.getTime();
				
				
				return time > _this.time;
			}
		}
		
	};
})( jQuery, window, document );