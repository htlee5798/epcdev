/**
 * 
 */
(function( $, window, document, undefined ) {
	'use strict';
	
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
	 * <h5>imagesLoaded</h3>
	 * <p>대상 DOMElement 안의 이미지가 로드가 완료되었을 시의 이벤트를 체크한다.</p>
	 * <p>onload 또는 onerror 이벤트를 체크</p>
	 * <p>$.Deferred() 객체를 return 하며, then(), done() 등 callback 함수를 이용할 수 있다.
	 * @class imagesLoaded
	 * @memberOf jQuery.fn
	 * @returns {Object} $.Deferred()
	 * @example <caption>JS - init</caption>
	 * <script type="text/javascript">
	 * 	$list.append(data).imagesLoaded().then(function() {
	 * 		//[ Callback ]
	 * 	});
	 * </script>
	 */
	$.fn.imagesLoaded = function () {

		var $imgs = this.find('img[src!=""]');
		if (!$imgs.length) {return $.Deferred().resolve().promise();}

		var dfds = [];  
		$imgs.each(function(){
			var dfd = $.Deferred();
			dfds.push(dfd);

			var img = new Image();
			img.onload = function(){dfd.resolve();}
			img.onerror = function(){dfd.resolve();}
			img.src = this.src;
		});

		return $.when.apply($,dfds);
	}
	
})( jQuery, window, document );

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
			loadFunction = isShow === undefined || isShow ? open : close,
			html = $.render.loadingBarForMobile();
			
		loadFunction();
		
		return this;
		
		function open() {
			if( $this.find( '.wrapLoadingBar' ).length === 0 ) {
				$this.append( html );
			}
			
			var element = $this[0].querySelector( '.wrapLoadingBar' );
			
			element.classList.add( 'pageLoading' );
			element.style.display = 'block';
			
//			$this.find( '.wrapLoadingBar' ).addClass( 'pageLoading' ).show();
		}
		
		function close() {
			var element = $this[0].querySelector( '.wrapLoadingBar' );
			
			if( element === null ) {
				return;
			}
			
			element.classList.remove( 'pageLoading' );
			element.style.display = 'none';
//			$this.find( '.wrapLoadingBar' ).removeClass( 'pageLoading' ).hide();
		}
	};
})( jQuery, window, document );
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
				overseaYn : 'N'
			},
			loc = location.href,
			config = $.extend( true, _defaults , options || $this.data() );
		
		var overseaYn ="N";
		
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
					//사용하는 기능인지
					//$toast.text( "찜하기가 취소되었습니다." ).addClass( 'active' );
					$this.removeClass( 'active' );
				});
			} else {
				global.addWish({
					prodCd : config.prodCd,
					categoryId : config.categoryId,
					forgnDelyplYn : config.overseaYn
				}, function( resData ) {
					//사용하는 기능인지
					//$toast.text( resData.message ).addClass( 'active' );
					$this.addClass( 'active' );
				});
			}
		}

		return this;
	};
})( jQuery, window, document );