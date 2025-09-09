$(document).ready( function() {

	/**
	 * appliance_tab
	 * @package	{front_uxmain}
	 * @version 20130709 (jslee)
	 * @modify 20131230 (RaiN)
	*/

	//uxPopularity
	if ( appliance_tab ) throw new Error('[ixError] "appliance_tab"이 이미 존재하여 충돌이 발생!');

	var appliance_tab = ( function () {
		//lnb
		var _$lnbWrap = $('.lnb_contents'),
			_$lnbTabs = $( '.appliance_lnb li a.tab' ),
			_$listDepth = _$lnbWrap.find( 'div.display_depth' ),
			_$list2Depth = _$listDepth.find( 'ul li a' );

		var _crtDepth = 0;

		_$listDepth.each( function ( index ) {
			if ( index != 0) $( this ).hide();
		});

		_$lnbTabs.bind( 'click mouseover focusin focusout' , lnbTabsMouseHandler );

		function lnbTabsMouseHandler (e) {
			var idx = $( this ).parent().index();

			if ( e.type == 'click' || e.type == 'mouseover' ) {
				_$lnbTabs.eq(_crtDepth).removeClass( 'on');
				_$listDepth.eq(_crtDepth).hide();
				_crtDepth = idx;
				_$listDepth.eq(idx).show();
				$( this ).addClass( 'on');
			}
		}

		_$listDepth.find('ul li').bind( 'mouseenter focusin', function () {
			if( !$(this).children( '.display_depth2' ).hasClass( 'on' ) ) {
				$(this).children( '.display_depth2' ).addClass( 'on' );
				$(this).find('a').addClass( 'on' );
			}
		});
		_$listDepth.find( 'ul li' ).bind( 'mouseleave focusout', function () {
			if( $(this).children('.display_depth2').hasClass( 'on' ) ) {
				$(this).children('.display_depth2').removeClass( 'on' );
				$(this).find('a').removeClass( 'on' );
			}
		});

		var ua = navigator.userAgent.toLowerCase(),
		docMode = document.documentMode,
		isIE = false;

		if ( navigator.appName == 'Microsoft Internet Explorer' ) {
			var re = new RegExp( 'msie ([0-9]{1,}[\.0-9]{0,})' );
			if ( re.exec(ua) != null ) {
				if ( parseFloat( RegExp.$1 ) < 9 ) isIE = true;
			}
		}

		//하이마트 베스트
		var _$tabBest = $('.tab_contents_wrap'),
			_$bestTabs = $( '.tab_contents li a.tab' ),
			_$listBest = _$tabBest.find( 'div.betsList' );

		var _crtBest = 0;

		_$listBest.each( function ( index ) {
			if ( index != 0) $( this ).hide();
		});

		_$bestTabs.bind( 'click mouseover focusin focusout' , bestTabsMouseHandler );

		function bestTabsMouseHandler (e) {
			var idx = $( this ).parent().index();

			if ( e.type == 'click' || e.type == 'mouseover' ) {
				_$bestTabs.eq(_crtBest).removeClass( 'on');
				_$listBest.eq(_crtBest).hide();
				_crtBest = idx;
				_$listBest.eq(idx).show();
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

		//브랜드존
		var _$tabBrand = $('.tab_brandzone_wrap'),
			_$brandTabs = $( '.brandzone_contents li div.tab' ),
			_$listBrand = _$tabBrand.find( 'div.imgBrand' );

		var _crtBrand = 0;

		_$listBrand.each( function ( index ) {
			if ( index != 0) $( this ).hide();
		});

		_$brandTabs.bind( 'click mouseover focusin focusout', brandTabsMouseHandler );

		function brandTabsMouseHandler (e) {
			var idx = $( this ).parent().index();

			if ( e.type == 'click' || e.type == 'mouseover' ) {
				_$brandTabs.eq(_crtBrand).removeClass( 'on');
				_$listBrand.eq(_crtBrand).hide();
				_crtBrand = idx;
				_$listBrand.eq(idx).show();
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

});