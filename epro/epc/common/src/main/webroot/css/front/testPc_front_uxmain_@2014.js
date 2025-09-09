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

		// 설날 가격대별
		var _$popularGiftSet = $( 'div.popularGiftSet' ),
			_$btnsp = _$popularGiftSet.find( 'div.popularGiftSetCont ul li a.tab' ),
			_$listp = _$popularGiftSet.find( 'div.giftSetsList' );

		var _crtPopular = 0;

		_$listp.each( function ( index ){
			if ( index != 0) $( this ).hide();
		});

		_$btnsp.bind( 'click mouseover focusin focusout' , popularbtnMouseHandler );


		function popularbtnMouseHandler (e) {
			var idx = $( this ).parent().index();

			if ( e.type == 'click' || e.type == 'mouseover' ) {
				_$btnsp.eq(_crtPopular).removeClass( 'on');
				if ( isIE ){
					_$listp.eq(_crtPopular).hide();
				}else{
					_$listp.eq(_crtPopular).fadeOut();
				}
				_crtPopular = idx;
				if ( isIE ){
					_$listp.eq(idx).show();
				}else{
					_$listp.eq(idx).fadeIn();
				}
				$( this ).addClass( 'on');
			}
		}

		// 설날 카테고리별
		var _$categoryGiftSet = $('.categoryGiftSet'),
			_$ctabBtns = $( '.categoryGiftSetCont li a.tab' ),
			_$listCategory = _$categoryGiftSet.find( 'div.giftSetsList' );

		var _crtCategory = 0;

		_$listCategory.each( function ( index ) {
			if ( index != 0) $( this ).hide();
		});

		_$ctabBtns.bind( 'click mouseover focusin focusout' , ctabbtnMouseHandler );

		function ctabbtnMouseHandler (e) {
			var idx = $( this ).parent().index();

			if ( e.type == 'click' || e.type == 'mouseover' ) {
				_$ctabBtns.eq(_crtCategory).removeClass( 'on');
				_$listCategory.eq(_crtCategory).hide();
				_crtCategory = idx;
				_$listCategory.eq(idx).show();
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

		// 설날 타겟별
		var _$customizeGiftSet = $('.customizeGiftSet'),
			_$cutabBtns = $( '.customizeGiftSetCont li a.tab' ),
			_$listCustomize = _$customizeGiftSet.find( 'div.giftSetsList' );

		var _crtCustomize = 0;

		_$listCustomize.each( function ( index ) {
			if ( index != 0) $( this ).hide();
		});

		_$cutabBtns.bind( 'click mouseover focusin focusout' , cutabbtnMouseHandler );

		function cutabbtnMouseHandler (e) {
			var idx = $( this ).parent().index();

			if ( e.type == 'click' || e.type == 'mouseover' ) {
				_$cutabBtns.eq(_crtCustomize).removeClass( 'on');
				_$listCustomize.eq(_crtCustomize).hide();
				_crtCustomize = idx;
				_$listCustomize.eq(idx).show();
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

	}());

	/* 201403 foot 탭메뉴 온오버 js */
	$('.main_foot_top_board h5 a').bind('focus mouseenter',function(){
		var boardItem = $('.main_foot_top_board h5 a'),
			boardItemView = boardItem.index(this);
		$('.main_foot_top_board h5 a').closest('h5').removeClass('on');
		$('.main_foot_top_board ul').hide();

		$('.main_foot_top_board h5 a').eq(boardItemView).closest('h5').addClass('on');
		$('.main_foot_top_board ul').eq(boardItemView).show();
	});
	$('.main_foot_top_board h5 a').eq(0).trigger("mouseenter")

	/* 201403 스마트쇼핑팁 레이어팝업 js */
	$('.left_uxSmartTip a').bind('click', function() {
		var target = $(this).attr('href');
		$(target).fadeIn().find('.left_uxSmartTip_inner').animate({'width':'100%'}, 300, 'easeInOutCubic');
		return false;
	});
	$('.left_uxSmartTip_close, .uxSmartTip_bg').bind('click', function() {
		$('.left_uxSmartTip_layerbox').fadeOut().find('.left_uxSmartTip_inner').animate({'width':0}, 200, 'easeOutCubic');
	});

	/* 201404 RNB 사이드 메뉴 js */
	$('.rnb_bottom_btn a').bind('click', function() {
		$('body, html').animate({scrollTop:0}, '500', 'swing');
		return false;
	});

	$('.rnb_big_box .rnb_mid_inbox02').bind('click', function() {
		$('.rnb_big_box .layer_shopingCart').addClass('active');
	});
	$('html').bind('click', function(e) {
		if ($(e.target).parents('.rnb_big_box').length <= 0 || $(e.target).is('.btn_close')) {
			$('.rnb_big_box .layer_shopingCart').removeClass('active');
		}
	});
	// 검색UI 카테고리 - 더보기 스크립트
	$('.search-result-category .category button').toggle(
		function () {
			var more_height =  $('.category-group').height();
			$('.category .category-group').css({"height":"auto"});
			$(this).removeClass();
			$(this).addClass('on');
			$('.category button').html('닫기 -');
			},
		function () {
			var more_height =  $('.category-group').height();
			$('.category .category-group').css({"height":"95px"});
			$(this).removeClass();
			$(this).addClass('');
			$('.category button').html('더보기 +');
		}
	);
	// 검색UI FAQ - 아코디언 스크립트
	$(".list_faq_search dl dt a").bind("click focus", function(){
		$(".list_faq_search dt a").removeClass("on");
		$(".list_faq_search dd").removeClass("on");
		$(this).addClass("on");
		$(this).parent().next().addClass("on");
	});

});

$(window).scroll(function() {
	if ($(this).scrollTop() > 150) {
		$('.rnb_big_box').css({'position':'fixed', 'top':'10px'});
	} else {
		$('.rnb_big_box').css({'position':'absolute', 'top':'0'});
	}
})