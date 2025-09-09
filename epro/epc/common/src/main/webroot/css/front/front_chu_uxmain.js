$(document).ready( function() {

	/**
	 * front_uxmain
	 * @package	{front_uxmain}
	 * @version 20130709 (jslee)
	*/

	//uxPopularity
	if ( front_uxmain ) throw new Error('[ixError] "front_uxmain"이 이미 존재하여 충돌이 발생!');

	var front_uxmain = ( function () {
		var _$popularity = $('.prod_popularity'),
			_$tabBtns = $( '.tab_popularity li a' );

		var _crtBanner = 0;

		_$popularity.each( function ( index ) {
			if ( index != 0) $( this ).hide();
		});

		_$tabBtns.bind( 'click mouseover focusin focusout' , tabbtnMouseHandler );

		function tabbtnMouseHandler (e) {

			var idx = $( this ).parent().index();
			if ( e.type == 'click' || e.type == 'mouseover' ) {
				_$tabBtns.eq(_crtBanner).removeClass( 'on');
				_$popularity.eq(_crtBanner).hide();
				_crtBanner = idx;
				_$popularity.eq(idx).show();
				$( this ).addClass( 'on');
			}
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
			_$btns = _$specialzone.find( 'div.special_lnb ul li a' ),
			_$list = _$specialzone.find( 'div.special_prod ul li' );

		var _crtZone = 0;

		_$list.each( function ( index ){
			if ( index != 0) $( this ).hide();
		});

		_$btns.bind( 'click mouseover focusin focusout' , sZonebtnMouseHandler );


		function sZonebtnMouseHandler (e) {
			var idx = $( this ).parent().index();


			if ( e.type == 'click' || e.type == 'mouseover' ) {
				_$btns.eq(_crtZone).removeClass( 'on');
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
				$( this ).addClass( 'on');
			}
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
			_cate.addClass( 'open' ).animate({ 'height':'380px'});
			if ( _cuBtn.hasClass( 'tClose' ) ) {
				_cuBtn.removeClass( 'tClose' );
				_cucate.animate({ 'height':'0' }, 800, 'easeInCirc' );
			}
		}
	});

	_ccBtn.bind( 'click', function() {
		_cate.removeClass( 'open' ).animate({ 'height':'0' }, { duration : 800, easing : 'easeInCirc', queue : false });
		_cBtn.removeClass( 'view' );
	});

	_list.bind( 'mouseenter focusin', function () {
		if( !$(this).hasClass( 'on' ) ) {
			$(this).addClass( 'on' );
		}
	});
	_list.bind( 'mouseleave focusout', function () {
		if( $(this).hasClass( 'on' ) ) {
			$(this).removeClass( 'on' );
		}
	});

	// Smart Shopping Tip
	var _ssTip = $( '#smartTip' ),
		_uxSmartTip = _ssTip.find( '.uxSmartTip' ),
		_ssTipBtn = _ssTip.find( '.uxSmartTip .cont_smarttip a' );
		_cuBtn = $( 'div#chuseokGnbWrap' ).find( '.gnbMenu' ),
		_cucate = $( 'div#chuseokGnbWrap' ).find( 'div.viewAllWrap' );

	_ssTipBtn.bind( 'click', function (){
		if ( $(this).hasClass( 'on' ) ) {
			$(this).removeClass( 'on' );
			_ssTip.animate({ 'top' : '-515px' }, { duration : 400, easing : 'easeInCubic', queue : false });
		} else {
			$(this).addClass( 'on' );
			_ssTip.animate({ 'top': '0' }, { duration : 400, easing : 'easeInCubic', queue : false });
		}
	});

	// 2013 추석 카테고리 전체보기
	var _cViewAll = $( '#chuseokGnbWrap' ),
		_cViewWrap = _cViewAll.find( '.viewAllWrap' ),
		_cViewBtnWrap = _cViewAll.find( '.gnbMenu' );
		_cViewBtn = _cViewAll.find( '.gnbMenu li' );
		_cViewBtn2 = _cViewAll.find( '.btntOpen' );
		_cCloseBtn = _cViewAll.find( 'a.vClose' );
		_cCloseBtn2 = _cViewAll.find( 'a.btntClose' );

	_cViewBtn.bind( 'click', function (){
		if ( _cViewBtnWrap.hasClass( 'tClose' ) ) {
			_cViewBtnWrap.removeClass( 'tClose' );
			// 웹접근성 : 선형화작업과 관련 (화면에 보이지 않는 개체는 'display:none' 이 되어야함)
			_cViewWrap.animate({ 'height' : '0' }, { duration : 400, easing : 'easeInCubic', queue : false, complete:function(){ _cViewWrap.css("display", "none"); } });
		} else {
			_cViewBtnWrap.addClass( 'tClose' );
			// 웹접근성 : 선형화작업과 관련 (화면에 보이지 않는 개체는 'display:none' 이 되어야함)
			_cViewWrap.css("display", "block");
			_cViewWrap.animate({ 'height': '275px' }, { duration : 400, easing : 'easeInCubic', queue : false });
		}
	});
	_cViewBtn2.bind( 'click', function() {
		// 웹접근성 : 선형화작업과 관련 (화면에 보이지 않는 개체는 'display:none' 이 되어야함)
		_cViewWrap.css("display", "block");
		_cViewWrap.animate({ 'height' : '275px' }, { duration : 400, easing : 'easeInCubic', queue : false });
		_cViewBtnWrap.addClass( 'tClose' );
	});
	_cCloseBtn.bind( 'click', function() {
		// 웹접근성 : 선형화작업과 관련 (화면에 보이지 않는 개체는 'display:none' 이 되어야함)
		_cViewWrap.animate({ 'height' : '0' }, { duration : 400, easing : 'easeInCubic', queue : false, complete:function(){ _cViewWrap.css("display", "none"); } });
		_cViewBtnWrap.removeClass( 'tClose' );
	});
	_cCloseBtn2.bind( 'click', function() {
		// 웹접근성 : 선형화작업과 관련 (화면에 보이지 않는 개체는 'display:none' 이 되어야함)
		_cViewWrap.animate({ 'height' : '0' }, { duration : 400, easing : 'easeInCubic', queue : false, complete:function(){ _cViewWrap.css("display", "none"); } });
		_cViewBtnWrap.removeClass( 'tClose' );
	});
	// 웹접근성 : 선형화작업과 관련 (화면에 보이지 않는 개체는 'display:none' 이 되어야함)
	_cViewWrap.css("display", "none");
});