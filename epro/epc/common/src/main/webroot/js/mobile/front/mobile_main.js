/*
// LOTTEMART MOBILE MAIN
// 2013 10 17 _khv
*/


var mobileMain = ( function () {

	var mainTab = getCookieTab('mainTab');
	
	if(mainTab == null || mainTab == "") {
		setCookieTab('mainTab','1');
		mainTab = 1;
	}
	
	mainTab = mainTab - 0; 
	
	var WIDTH = $( document ).width(), gnbWidh = 860;		// 상단 탑메뉴 가로 길이 ******************************!!

	var _contentData = [],	// 컨텐츠 string 배열
		_content = 5,	// 전체 컨텐츠 페이지 개수 ********************************!!
		_isImgAreaSwipe = false,	// 금주의 전단 배너 체크
		_isWide,	// top gnb 영역 비교
		_left = 0;		// top gnb left 이동값

	var _$cont = $( 'div#contentWrap' ),
		_$content = $( 'section' ),
		_$bannerCont,
		_$bannerList,
		_$bannerPg,
		_$topGnb = $( 'div#gnb_menu' ),
		_$gnb = $( 'div#gnb_menu' ).find( 'li a' );

	var _oldPage,
		_crtPage = mainTab,	// 시작 컨텐츠 페이지 // 2013-11-25 수정
		_oldBanner,
		_crtBanner = 0,
		_crtTop = 0,
		_wide = _$topGnb.find( 'li' ).length,
		_move = false;

	/////////////////////////////////////////////////////////////////////////
	
	// load content ( string )
	$.ajax ({
		type : "GET", 
		url : "/ajaxMobileMainContent.do",
		dataType : "html",
		async : false,
		success : ajaxHandler,
		error : ajaxHandler
	});
	
	_$content.eq( _crtPage ).html( contentData[_crtPage] );

	if ( _crtPage == 1 ) {				// 배너 페이지 확인 ******************************!!
		weekSet();
	}

	// swipe
	var gesture = new ixGuesture( _$cont[0], 'horizontal', { onSwipe: swipeHandler } );
	gesture.areaWidth( _$cont.width() );
	gesture.sensitivity( 0.1 );
	
	// 금주의 전단 배너 영역 다중 스와이프 체크
	$( 'div.weekBanner' ).live( 'touchstart', function () {
		_isImgAreaSwipe = true;
	});

	// top menu add
	_$topGnb.find( 'li' ).addClass( 'list' );

	var _clone = _$topGnb.find( 'li' ).clone();
	_clone.addClass( 'pre' );

	$( 'ul.gnbWrap' ).prepend( _clone );

	var _clone = _$topGnb.find( 'li' ).clone();
	_clone.addClass( 'nxt' );

	$( 'ul.gnbWrap' ).append( _clone );
	$( 'ul.gnbWrap' ).find( '.pre.nxt' ).remove();

	// 초기 top menu 설정
	setTopNavi();
	
	// resize 설정
	/*$( window ).resize( function () {
		WIDTH = $( document ).width();
		_$bannerList = $( 'div.img_area' ).find( 'li' );

		_$bannerList.css( 'width', WIDTH + 'px' );

		setTopNavi();
	});*/

	_$topGnb.find( 'li.list a' ).bind( 'click', function () {
		
		_move = false;
		
		var idx = $(this).parent().index() - _wide;

		if ( idx != _crtPage && !_move ) {
			_oldPage = _crtPage;

			if ( idx >= 0 && idx < _content ) {
				_crtPage = idx;
			} else if ( idx < 0 ) {
				_crtPage = idx + _content;
			} else if ( idx >= _content ) {
				_crtPage = idx - _content;
			}

			setTopNavi();

			if ( _oldPage < idx ) {
				setContent( 'left' );
			} else {
				setContent( 'right' );
			}
		}
		
	});

	/////////////////////////////////////////////////////////////////////////

	function ajaxHandler ( args, status ) {
		switch ( status ) {
			case 'success' :
				sendContent( args );
				break;
			case 'error' :
				alert( '데이터를 불러오지 못했습니다.' );
				break;
		}
	}

	function sendContent ( args ) {
		contentData = args.split( '<split/>' );
	}

	function setTopNavi () {
		if ( Math.abs( WIDTH ) < gnbWidh ) {
			_isWide = true;
			innerGnb();
		} else {
			_isWide = false;
			outerGnb();
		}

		_$topGnb.find( 'ul.gnbWrap li a' ).eq ( _oldPage + _wide ).removeClass( 'on' );
		_$topGnb.find( 'ul.gnbWrap li a' ).eq ( _crtPage + _wide ).addClass( 'on' );
	}
	
	function innerGnb () {
		_move = true;

		_$topGnb.find( 'ul.gnbWrap' ).css({ 'width' : gnbWidh*3 + 'px' });
		_$topGnb.find( 'li.pre' ).show();
		_$topGnb.find( 'li.nxt' ).show();

		if ( _crtPage - _oldPage == _content - 1 ) {
			var posX = _$topGnb.find( 'li' ).eq( _crtPage + _wide ).outerWidth( true )/2;

			_$topGnb.find( 'li' ).slice( 0, _crtPage + _content + 1 ).each( function () {
				_left += $(this).outerWidth( true );
			});

			_$topGnb.find( 'ul.gnbWrap' ).removeAttr( 'style' );
			_$topGnb.find( 'ul.gnbWrap' ).css({ 'width' : gnbWidh*3 + 'px', 'left' : (-_left + Math.abs( WIDTH )/2 - posX) + 'px' });
			_left = 0;
		} else if ( _crtPage - _oldPage == 1 - _content ) {
			var posX = _$topGnb.find( 'li' ).eq( _crtPage + _wide ).outerWidth( true )/2;

			_$topGnb.find( 'li' ).slice( 0, _content - 1 ).each( function () {
				_left += $(this).outerWidth( true );
			});

			_$topGnb.find( 'ul.gnbWrap' ).removeAttr( 'style' );
			_$topGnb.find( 'ul.gnbWrap' ).css({ 'width' : gnbWidh*3 + 'px', 'left' : (-_left + Math.abs( WIDTH )/2 - posX) + 'px' });
			_left = 0;
		}

		_$topGnb.find( 'li' ).slice( 0, _crtPage + _wide ).each( function () {
			_left += $(this).outerWidth( true );
		});

		var posX = _$topGnb.find( 'li' ).eq( _crtPage + _wide ).outerWidth( true )/2;

		//_$topGnb.find( 'ul.gnbWrap' ).css( 'left', -_left + Math.abs( WIDTH )/2 - posX + 'px' );

		_$topGnb.find( 'ul.gnbWrap' ).transition( 'left:' + (-_left + Math.abs( WIDTH )/2 - posX) + 'px', 'left 0.5s ease', { onTransitionEnd : function (e) {
			_move = false;
		}});
		_left = 0;
	}

	function outerGnb () {
		_$topGnb.find( 'ul.gnbWrap' ).css({ 'width' : gnbWidh + 'px' });
		_$topGnb.find( 'li.pre' ).hide();
		_$topGnb.find( 'li.nxt' ).hide();

		_$topGnb.find( 'ul.gnbWrap' ).css( 'left', '' );
	}

	function swipeHandler (e) {
		if ( _isImgAreaSwipe ) {	// 금주의 전단 배너 스와이프
			imgAreaHandler( e.swipeType );
			_isImgAreaSwipe = false;
		} else {
			switch ( e.swipeType ) {
				case 'left' :
					gesture.enable( false );
					slideRight();
					setContent( e.swipeType );
					break;
				case 'right' :
					gesture.enable( false );
					slideLeft();
					setContent( e.swipeType );
					break;
				case 'none' :
					break;
			}
			setTopNavi();
		}
	}

	function slideRight () {
		_oldPage = _crtPage;
		if ( _crtPage == _content - 1 ) {
			_crtPage = 0;
		} else {
			_crtPage++;
		}
	}

	function slideLeft () {
		_oldPage = _crtPage;
		if ( _crtPage == 0 ) {
			_crtPage = _content - 1;
		} else {
			_crtPage--;
		}
	}

	function setContent ( type ) {
		
		/*sessionStorage.clear();
		sessionStorage.setItem('mainTab', _crtPage);*/
		
		setCookieTab('mainTab',_crtPage);
		
		//_$cont.css( 'position', 'absolute' );
		_$content.css( 'position', 'absolute' );

		var _$crtContent = _$content.eq( _crtPage ),
			_$oldContent = _$content.eq( _oldPage );

		if ( type == 'right' ) {
			WIDTH = -($( document ).width());
		} else {
			WIDTH = $( document ).width();
		}
		
		_$crtContent.css({ 'left' : WIDTH + 'px' }).show();
		_$oldContent.css({ 'left' : '0px' }).show();

		// 컨텐츠가 없을 때만 넣어줌
		if ( _$crtContent.children().length == 0 ) {
			_$crtContent.html( contentData[_crtPage] );

			if ( _crtPage == 1 ) {				// 배너 페이지 확인 ******************************!!
				weekSet();
			}
		}

		_$crtContent.transition( 'left:0px', 'left 0.5s ease', { onTransitionEnd : function (e) {
			gesture.enable( true );
			_$oldContent.css( 'display' , 'none' );

			//_$cont.css( 'position', 'relative' );
			_$content.css( 'position', 'relative' );
			
		}});

		_$oldContent.transition( 'left:' + (-WIDTH) + 'px', 'left 0.5s ease' );


	}

	function weekSet () {
		_$bannerCont = $( 'div.img_area' );
		_$bannerList = _$bannerCont.find( 'li' );
		_$bannerPg = $( 'div.btn_num' ).find( 'a' );

		_$bannerList.css( 'width', Math.abs( WIDTH ) + 'px' );
		_$bannerCont.find( 'ul' ).css( 'width', Math.abs( WIDTH )*2 + 'px' );

		_$bannerList.hide().eq( _crtBanner ).show();
	}

	function imgAreaHandler ( type ) {
		switch ( type ) {
			case 'left' :
				gesture.enable( false );
				bannerRight();
				setBanner( type );
				break;
			case 'right' :
				gesture.enable( false );
				bannerLeft();
				setBanner( type );
				break;
			case 'none' :
				break;
		}
	}

	function bannerRight () {
		_oldBanner = _crtBanner;
		if ( _crtBanner == _$bannerList.length - 1 ) {
			_crtBanner = 0;
		} else {
			_crtBanner++;
		}
	}

	function bannerLeft () {
		_oldBanner = _crtBanner;
		if ( _crtBanner == 0 ) {
			_crtBanner = _$bannerList.length - 1;
		} else {
			_crtBanner--;
		}
	}

	function setBanner ( type ) {
		WIDTH = $( document ).width();

		_$bannerList = _$bannerCont.find( 'li' );
		var len = _$bannerList.length - 1;
		var $clone;

		if ( type == 'right' ) {
			_$bannerList.eq( len ).show();

			$clone = _$bannerList.eq( len ).clone();

			_$bannerList.eq( len ).remove();
			$clone.css( 'left', -WIDTH + 'px' );
			_$bannerList.eq(0).css( 'left', -WIDTH + 'px' );
			_$bannerCont.find( 'ul' ).prepend( $clone );

			_$bannerCont.find( 'ul' ).transition( 'left:' + WIDTH + 'px', 'left 0.5s ease', { onTransitionEnd : function (e) {
				$( this ).attr( 'style', '' );
				
				$clone.css({ 'left' : '0px' });
				_$bannerList.eq(0).hide().css( 'left', 0 );

				gesture.enable( true );
			}});

		} else {
			_$bannerList.eq(1).show();

			$clone = _$bannerList.eq(0).clone();

			_$bannerCont.find( 'ul' ).transition( 'left:' + (-WIDTH) + 'px', 'left 0.5s ease', { onTransitionEnd : function (e) {
				$( this ).attr( 'style', '' );

				_$bannerCont.find( 'ul' ).append( $clone );
				_$bannerList.eq(0).remove();
				$clone.hide();

				gesture.enable( true );
			}});

		}
		_$bannerCont.find( 'ul' ).css( 'width', Math.abs( WIDTH )*2 + 'px' );

		_$bannerPg.eq( _oldBanner ).removeClass( 'on' );
		_$bannerPg.eq( _crtBanner ).addClass( 'on' );
	}

})();


function setCookieTab(name, value) {
	document.cookie = name + "=" + escape (value) + "; path=/; expires=session";
}

function getCookieTab(Name) {
	var search = Name + "=";
	if (document.cookie.length > 0) {                  // 쿠키가 설정되어 있다면
		offset = document.cookie.indexOf(search);
		if (offset != -1) {                            // 쿠키가 존재하면
			offset += search.length;
			end = document.cookie.indexOf(";", offset);
			if (end == -1)	end = document.cookie.length;

			return unescape(document.cookie.substring(offset, end));
		}
	}

	return "";
}