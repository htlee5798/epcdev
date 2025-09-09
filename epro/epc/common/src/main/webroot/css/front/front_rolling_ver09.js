/**
 * front_rolling
 * @package	{front_rolling}
 * @version 20130718 (jslee)
*/

if ( front_main_rolling ) throw new Error('[ixError] "front_main_rolling"가 이미 존재하여 충돌이 발생!');

var front_main_rolling = ( function ( $list , isAuto , w , type) {

	var _$list = $list,
		_$bannerList = $list.find( '.rolling_list' ).children(),
		_$rollingBtn = $list.find( '.rolling_btn ul li a' ),
		_$nextBtn = $list.find( 'div.arrow a.next'),
		_$prevBtn = $list.find( 'div.arrow a.prev');

	var	_tlBanner = _$bannerList.length,
		_crtBanner = 0,
		_oldBanner = 0,
		_width = w,
		_intervalTime = 3000,
		_intervalId = 0,
		_isAutoRolling = isAuto,
		_type = type,
		_outTimer;

	var ua = navigator.userAgent.toLowerCase(),
		docMode = document.documentMode,
		isIE = false;

	if ( navigator.appName == 'Microsoft Internet Explorer' ) {
		var re = new RegExp( 'msie ([0-9]{1,}[\.0-9]{0,})' );
		if ( re.exec(ua) != null ) {
			if ( parseFloat( RegExp.$1 ) < 9 ) isIE = true;
		}
	}

	init();

	function init () {
		clearInterval(_intervalId);

		if ( _tlBanner != 0){
			arrangeBanner();
			addBtnEvent();
			if ( _isAutoRolling ){
				autoPlay();
			}
		}
		_$nextBtn.hide();
		_$prevBtn.hide();
	}

	function arrangeBanner () {
		_$bannerList.each( function ( index ) {
			if ( $( this ).index() > 0){
				$( this ).hide();
			}
			if ( !_type ) {
				$( this ).css( 'left' , _width * index + 'px');
			}
		});
	}

	function addBtnEvent () {
		_$rollingBtn.bind( 'click mouseenter mouseout focusin focusout', btnMouseHandler );
		_$nextBtn.bind( 'click mouseenter mouseleave focusin focusout' , nextBtnMouseHandler);
		_$prevBtn.bind( 'click mouseenter mouseleave focusin focusout' , prevBtnMouseHandler);
		_$bannerList.bind( 'mouseenter mouseleave focusin focusout', bannerMouseHandler );
	}

	function btnMouseHandler (e) {
		var idx =  $( this ).parent().index();

		if ( e.type == 'click' ){
			if ( idx == _crtBanner ) return;
			moveStep( idx - _crtBanner );
		}else if ( e.type == 'mouseenter'|| e.type == 'focusin' ) {
			if ( _isAutoRolling ){
				clearInterval(_intervalId);
			}
		}else if ( e.type == 'mouseout' || e.type == 'focusout' ) {
			if ( _isAutoRolling ){
				autoPlay();
			}
		}
	};

	function bannerMouseHandler (e) {
		if ( e.type == 'mouseenter' || e.type == 'focusin' ) {
			showArrowBtn();
		} else if ( e.type == 'mouseleave' || e.type == 'focusout' ) {
			_outTimer = setTimeout( hideArrowBtn, 100 );
		}
	}

	function nextBtnMouseHandler (e) {
		if ( _outTimer) clearTimeout( _outTimer );
		if ( e.type == 'click' ){
			moveStep( -1 );
		} else if ( e.type == 'mouseenter' || e.type == 'focusin' ) {
			showArrowBtn()
		} else if ( e.type == 'mouseleave' || e.type == 'focusout' ) {
			_outTimer = setTimeout( hideArrowBtn, 100 );
		}
	}

	function prevBtnMouseHandler (e) {
		if ( _outTimer) clearTimeout( _outTimer );
		if ( e.type == 'click' ){
			moveStep( 1 );
		} else if ( e.type == 'mouseenter' || e.type == 'focusin' ) {
			showArrowBtn()
		} else if ( e.type == 'mouseleave' || e.type == 'focusout' ) {
			_outTimer = setTimeout( hideArrowBtn, 100 );
		}
	}


	function showArrowBtn () {
		if ( _outTimer) clearTimeout( _outTimer );
		_$nextBtn.show();
		_$prevBtn.show();
		if ( _isAutoRolling ){
			clearInterval(_intervalId);
		}
	}

	function hideArrowBtn () {
		if ( _outTimer) clearTimeout( _outTimer );
		_$nextBtn.hide();
		_$prevBtn.hide();

		if ( _isAutoRolling ){
			autoPlay();
		}
	}

	function moveStep ( step ){
		_oldBanner = _crtBanner;
		_crtBanner += step;

		if ( _crtBanner == _tlBanner ){
			_crtBanner = 0;
		}else if ( _crtBanner < 0 ) {
			_crtBanner = _tlBanner - 1;
		}
		var $oldImg = _$bannerList.eq( _oldBanner ),
			$crtImg = _$bannerList.eq( _crtBanner );

		if ( _type ){
			if ( isIE ) {
				$oldImg.hide();
				$crtImg.show();
			}else{
				$oldImg.fadeOut();
				$crtImg.fadeIn();
			}
		}else{
			if ( step > 0 ){
				$oldImg.animate( {left: (_width*-1)+'px'}, { duration : 'fast', done : function () { $(this).hide(); } });
				$crtImg.css( 'left', _width + 'px' );
			}else{
				$oldImg.animate( {left: _width + 'px'}, { duration : 'fast', done : function () { $(this).hide(); } });
				$crtImg.css( 'left', (_width*-1)+'px' );
			}
			$crtImg.show().animate( {left: '0px'}, 'fast');
		}

		var imgPath = _$rollingBtn.eq( _oldBanner ).find( 'on' );
		_$rollingBtn.eq( _oldBanner ).removeClass('on');

		var crtimgPath = _$rollingBtn.eq( _crtBanner ).find( 'a' );
		_$rollingBtn.eq( _crtBanner ).addClass('on');;
	}

	function nextBanenr () {
		moveStep(1);
	}


	function autoPlay () {
		clearInterval(_intervalId);
		_intervalId = setInterval( nextBanenr , _intervalTime );
	}

});

var rolling2 = new front_main_rolling( $( 'div.uxDontWorry')  , true, 317 ,true);
var rolling1 = new front_main_rolling( $( 'div.uxRecipe' ) , true, 317 ,true );
var rolling3 = new front_main_rolling( $( 'div.uxPB')  , false, 237 ,false );
var rolling4 = new front_main_rolling( $( 'div.uxMiddleBanner')  , true, 950 ,true );
var rolling5 = new front_main_rolling( $( 'div.uxCoupon')  , true, 632 ,true );
