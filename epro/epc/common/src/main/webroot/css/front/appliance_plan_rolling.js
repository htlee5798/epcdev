/**
 * front_plan_rolling
 * @package	{front_plan_rolling}
 * @version 20130718 (jslee)
*/

if ( appliance_plan_rolling ) throw new Error('[ixError] "appliance_plan_rolling"가 이미 존재하여 충돌이 발생!');

var appliance_plan_rolling = ( function () {
	var _$list = $( 'ul.rolling_list' ),
		_$bannerList = _$list.find( 'li div.planImg' ),
		_$rollingBtn = _$list.find( 'li > div.btn' ),
		_$rollingBtn2 = $( '.rolling_contents' ).find( 'ul.rolling_btn li a' ),
		_$nextBtn = $( 'div.uxPlanRolling' ).find( 'div#arrow_plan .next' ),
		_$prevBtn = $( 'div.uxPlanRolling' ).find( 'div#arrow_plan .prev' );

	var	_tlBanner = _$bannerList.length,
		_crtBanner = 0,
		_oldBanner = 0,
		_width = 570,
		_intervalTime = 4000,
		_intervalId = 0,
		_isAutoRolling = true,
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
		_$nextBtn.hide();
		_$prevBtn.hide();

		if ( _tlBanner != 0){
			arrangeBanner();
			addBtnEvent();
			if ( _isAutoRolling ){
				autoPlay();
			}
		}
	}

	function arrangeBanner () {
		_$bannerList.each( function ( index ) {
			if ( index > 0){
				$( this ).hide();
			}
		});
	}

	function addBtnEvent () {
		_$rollingBtn.bind( 'click mouseenter mouseout', btnMouseHandler );
		_$rollingBtn2.bind( 'click mouseenter mouseout', btnMouseHandler2 );
		_$nextBtn.bind( 'click mouseenter mouseleave focusin focusout' , nextBtnMouseHandler);
		_$prevBtn.bind( 'click mouseenter mouseleave focusin focusout' , prevBtnMouseHandler);
		_$bannerList.bind( 'mouseenter mouseleave focusin focusout', bannerMouseHandler );
	}

	function btnMouseHandler (e) {
		var idx =  $( this ).parent().index();

		if ( e.type == 'click' ){
			if ( idx == _crtBanner ) return;
			moveStep( idx - _crtBanner );
			if ( _isAutoRolling ){
				clearInterval(_intervalId);
			}
		} else if ( e.type == 'mouseenter'  ){
			if ( idx == _crtBanner ) return;
			moveStep( idx - _crtBanner );

			if ( _isAutoRolling ){
				clearInterval(_intervalId);
			}
		} else if ( e.type == 'mouseout' ) {
			if ( _isAutoRolling ){
				autoPlay();
			}
		}
	}

	function btnMouseHandler2 (e) {
		var idx =  $( this ).parent().index();

		if ( e.type == 'click' ){
			if ( idx == _crtBanner ) return;
			moveStep( idx - _crtBanner );
			if ( _isAutoRolling ){
				clearInterval(_intervalId);
			}
		} else if ( e.type == 'mouseenter'  ){
			if ( idx == _crtBanner ) return;
			moveStep( idx - _crtBanner );

			if ( _isAutoRolling ){
				clearInterval(_intervalId);
			}
		} else if ( e.type == 'mouseout' ) {
			if ( _isAutoRolling ){
				autoPlay();
			}
		}
	}

	function bannerMouseHandler (e) {
		if ( e.type == 'mouseenter' || e.type == 'focusin' ){
			showArrowBtn();
		} else if ( e.type == 'mouseleave' || e.type == 'focusout' ) {
			_outTimer = setTimeout( hideArrowBtn, 100 );
		}
	}

	function nextBtnMouseHandler (e) {
		if ( _outTimer) clearTimeout( _outTimer );
		if ( e.type == 'click' ){
			moveStep( -1 );
		} else if ( e.type == 'mouseenter' || e.type == 'focusin' ){
			showArrowBtn()
		} else if ( e.type == 'mouseleave' || e.type == 'focusout' ) {
			_outTimer = setTimeout( hideArrowBtn, 100 );
		}
	}

	function prevBtnMouseHandler (e) {
		if ( _outTimer) clearTimeout( _outTimer );
		if ( e.type == 'click' ){
			moveStep( 1 );
		} else if ( e.type == 'mouseenter' || e.type == 'focusin' ){
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

		if ( isIE ) {
			$oldImg.hide();
			$crtImg.show();
		}else{
			$oldImg.fadeOut();
			$crtImg.fadeIn();
		}

		_$rollingBtn.eq( _oldBanner ).find( 'a' ).removeClass( 'on' );
		_$rollingBtn.eq( _crtBanner ).find( 'a' ).addClass( 'on' );

		var imgPath = _$rollingBtn2.eq( _oldBanner ).find( 'on' );
		_$rollingBtn2.eq( _oldBanner ).removeClass('on');

		var crtimgPath = _$rollingBtn2.eq( _crtBanner ).find( 'a' );
		_$rollingBtn2.eq( _crtBanner ).addClass('on');

	}

	function nextBanenr () {
		moveStep(1);
	}

	function autoPlay () {
		clearInterval(_intervalId);
		_intervalId = setInterval( nextBanenr , _intervalTime );
	}

}());