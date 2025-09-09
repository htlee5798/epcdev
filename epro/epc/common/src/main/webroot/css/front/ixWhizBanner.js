/**
 * lotteMart - ixWhizBanner
 * @package {ixWhizBanner}
 * @version 20121018 (2j5)
 * @modify 20121108 (_khv)
*/

if ( ixWhizBanner ) throw new Error('[ixError] "ixWhizBanner"가 이미 존재하여 충돌이 발생!');

var ixWhizBanner = (function(){
	var $B = ixBand,
		$u = $B.$utils;

	var _wrap = $( 'div#right_skyWingBanner' ),
		_con = $B( 'floatdiv' ).getElement(),
		_sections = $B( _con ).getElementsByClassName( 'section', 'li' ),
		_icons = [],
		_tabs = [],
		_scrollTween,
		_openTweens = [];

	var _docHeight = $B().documentHeight(),
		_conHeight = $B( _con ).height(),
		_tabsHeight = [370, 235, 235, 221],//각Tab 높이값(장바구니, 최근본상품, 단골상품, 쇼핑메모)
		_tabOutHeight = 31,
		_tabsNum,
		_seletIndex = -1,
		_isOpens = [];

	// ===============	Initialize	=============== //

	_tabs[0] = $B( _con ).getElementsByClassName( 'title version-1', 'strong' )[0];
	_tabs[1] = $B( _con ).getElementsByClassName( 'title version-2', 'strong' )[0];
	_tabs[2] = $B( _con ).getElementsByClassName( 'title version-3', 'strong' )[0];
	_tabs[3] = $B( _con ).getElementsByClassName( 'title version-4', 'strong' )[0];

	_tabsNum = _tabs.length;
	//_tabOutHeight = $B( _sections[0] ).height();

	var i;
	for ( i = 0; i < _tabsNum; ++i ) {
		var tab = _tabs[i];
		tab._idx = i;

		_icons[i] = $B( tab ).getElementsByTagName( 'img' )[0];
		_isOpens[i] = false;

		_openTweens[i] = new $u.TweenCore( 0.25, _tabOutHeight, _tabsHeight[i], {onTween: openTweenHandler, onSeekComplete: openTweenCompleteHandler, ease: $u.ease.quadInOut }, {tweenTarget:  _sections[i]} );
		//_openTweens[i].stop().seekTo( 0 );
		$B( tab ).addEvent( 'click', mouseHandler );
		$B( tab ).addEvent( 'keydown', keydownHandler );
	}

	$B( window ).addEvent( 'resize', resizeHandler );
	setScroll();

	// ===============	Private Methods	=============== //

	function openTweenHandler (e) {
		e.values.tweenTarget.style.height = e.currentValue + 'px';
	}

	function openTweenCompleteHandler (e) {
		_conHeight = $B( _con ).height();
	}

	function mouseHandler (e) {
		openTab( e.currentTarget._idx );
	}

	function keydownHandler (e) {
		//엔터일 경우  openTab 실행
		if(e._event.keyCode == 13)
			openTab( e.currentTarget._idx );
	}

	//탭열고닫기
	function openTab ( idx ) {
		//out tween
		if ( _seletIndex > -1 && _seletIndex != idx ) {
			_openTweens[_seletIndex].stop().seekTo( 0 );
			_icons[_seletIndex].className = 'off';
			_isOpens[_seletIndex] = false;
		}

		//close
		if ( _isOpens[idx] ) {
			_icons[idx].className = 'off';
			_isOpens[idx] = false;
		//open
		} else {
			_icons[idx].className = 'on';
			_isOpens[idx] = true;
		}

		//over tween
		_openTweens[idx].stop().toggle();
		_seletIndex = idx;
	}

	//퀵메뉴 스크롤 설정
	function setScroll () {
		//_scrollTween = new $u.TweenCore( 0.4, 0, 0, { onTween:scrollTweenHandler } );
		$B( window ).addEvent( 'scroll', scrollHandler );
	}

	function scrollHandler (e) {
		var scrollY = $B().documentScrollY();
		/*
		if ( scrollY > 262 ) {
			posY = scrollY - 262;

			if ( _docHeight < scrollY + _conHeight - 10 ) {
				posY = _docHeight - _conHeight - 280;
			}
		} else {
			posY = 0;
		}

		_scrollTween.stop().setValue( null, posY ).start();
		*/
		if ( scrollY > 236 ) {
			_wrap.css({ 'position' : 'fixed', 'top' : '10px' });
		} else {
			_wrap.css({ 'position' : '', 'top' : '' });
		}
	}

	function scrollTweenHandler(e){
		//_con.style.top = e.currentValue + 'px';
	}

	function resizeHandler (e) {
		_docHeight = $B().documentHeight();
	}

	// ===============	Public Methods	=============== //
	return {
		/**
		 * 기본으로 열리는 항목이 있을때만 설정해준다.
		 * @param	{Int}	idx		0~3
		 */
		defaultOpen: function ( idx ) {
			if ( idx > -1 && idx < _tabsNum ) openTab( idx );
		},
		update: function () {
			_docHeight = $B().documentHeight();
		}
	};

}());