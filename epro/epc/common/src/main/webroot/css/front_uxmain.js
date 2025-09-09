$(document).ready( function() {
	
	/**
	 * front_uxmain
	 * @package	{front_uxmain}
	 * @version 20130709 (jslee)
	*/

	//uxPopularity
	if ( front_uxmain ) throw new Error('[ixError] "front_uxmain"가 이미 존재하여 충돌이 발생!');

	var front_uxmain = ( function () {	
		var _$popularity = $('.prod_popularity'),
			_$tabBtns = $( '.tab_popularity li' );

		var _crtBanner = 0;

		_$popularity.each( function ( index ) {
			if ( index != 0) $( this ).hide();
		});

		_$tabBtns.on( 'mouseover' , tabbtnMouseHandler );
		
		function tabbtnMouseHandler () {		
			var idx = $( this ).index();
			_$tabBtns.eq(_crtBanner).find( 'a' ).removeClass( 'on');
			_$popularity.eq(_crtBanner).hide();
			_crtBanner = idx;
			_$popularity.eq(idx).show();
			$( this ).find( 'a' ).addClass( 'on');
		}
		
		
		var ua = navigator.userAgent.toLowerCase(),
		docMode = document.documentMode,
		isIE = false;

		if ( navigator.appName == 'Microsoft Internet Explorer' ) {
			var re = new RegExp( 'msie ([0-9]{1,}[\.0-9]{0,})' );
			if ( re.exec(ua) != null ) {
				if ( parseFloat( RegExp.$1 ) < 9 ) isIE = true;			
			}
		}

		// uxSpecialZone
		var _$specialzone = $( 'div.uxSpecialZone'),
			_$btns = _$specialzone.find( 'div.special_lnb ul li' ),
			_$list = _$specialzone.find( 'div.special_prod ul li' );

		var _crtZone = 0;

		_$list.each( function ( index ){
			if ( index != 0) $( this ).hide();
		});

		_$btns.on( 'mouseover' , sZonebtnMouseHandler );

		
		function sZonebtnMouseHandler () {		
			var idx = $( this ).index();
			_$btns.eq(_crtZone).find( 'a' ).removeClass( 'on');
			if ( isIE ){
				_$list.eq(_crtZone).hide();
			}else{
				_$list.eq(_crtZone).fadeOut();
			}
			_crtZone = idx;
			if ( isIE ){
				_$list.eq(idx).show();
				}else{
				_$list.eq(idx).fadeIn();
			}
			$( this ).find( 'a' ).addClass( 'on');
		}

	}());



	// category all
	var _gnb = $( '.UxGnb' ),
		_cate = $( '.category_all_wrap' ),
		_cBtn = _gnb.find( '#menu1 a.category_view' ),
		_ccBtn = _cate.find( '.category_close a' ),
		_list = _cate.find( 'ul a' );

	_cBtn.bind( 'click', function () {
		if ( $(this).hasClass( 'view' ) ) {
			$(this).removeClass( 'view' );
			_cate.removeClass( 'open' ).animate({ 'height':'0' }, { duration : 800, easing : 'easeInCirc', queue : false });
		} else {
			$(this).addClass( 'view' );
			_cate.addClass( 'open' ).animate({ 'height':'420px'});
		}
	});
	
	_ccBtn.bind( 'click', function() {
		_cate.removeClass( 'open' ).animate({ 'height':'0' }, { duration : 800, easing : 'easeInCirc', queue : false });
		_cBtn.removeClass( 'view' );
	});

	_list.on( 'mouseenter focusin', function () {
		if( !$(this).hasClass( 'on' ) ) {
			$(this).addClass( 'on' );
		}
	});
	_list.on( 'mouseleave focusout', function () {
		if( $(this).hasClass( 'on' ) ) {
			$(this).removeClass( 'on' );
		}
	});
	
	// Smart Shopping Tip
	var _ssTip = $('#smartTip'),
		_uxSmartTip = $('#smartTip').find('.uxSmartTip'),
		_ssTipBtn = $('#smartTip').find('a.btn_tip');

	_ssTipBtn.on('click', function(e){
		e.preventDefault();
		if ( _ssTipBtn.is('.on') ) {
			_ssTip.animate({'top': '-614px'}, '2000', 'easeInCubic');
			_ssTipBtn.removeClass('on');
		} else {
			_ssTip.animate({'top': '0'}, '2000', 'easeOutQuint');
			_ssTipBtn.addClass('on');
		}
	});
});