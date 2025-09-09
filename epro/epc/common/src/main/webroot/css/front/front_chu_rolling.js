/**
 * front_plan_rolling
 * @package	{front_plan_rolling}
 * @version 20130718 (jslee)
*/

if ( front_chu_rolling ) throw new Error('[ixError] "front_chu_rolling"가 이미 존재하여 충돌이 발생!');

var front_chu_rolling = ( function () {
	/* s:웹접근성 : 구조변경으로 인해 셀렉트 수정 및 '_$rollingBtnA' 추가 */
	/*
	var _$list = $( 'div#planPic' ),
		_$bannerList = _$list.find( 'ul li' ),
		_$rollingBtn = $( 'ul#planNav' ).find( 'li.btn' ),
		_$nextBtn = $( 'div#mainRolling' ).find( 'div#arrow_plan .next' ),
		_$prevBtn = $( 'div#mainRolling' ).find( 'div#arrow_plan .prev' );
	*/
	var _$list = $( 'ul#planNav' ),
		_$bannerList = _$list.find( 'div.pic' ),
		_$rollingBtn = $( 'ul#planNav' ).find( 'div.btn' ),
		_$rollingBtnA = $( 'ul#planNav' ).find( 'div.btn a' ),
		_$nextBtn = $( 'div#mainRolling' ).find( 'div#arrow_plan .next' ),
		_$prevBtn = $( 'div#mainRolling' ).find( 'div#arrow_plan .prev' );
	/* e:웹접근성 : 구조변경으로 인해 셀렉트 수정 및 '_$rollingBtnA' 추가 */

	var	_tlBanner = _$bannerList.length,
		_crtBanner = 0,
		_oldBanner = 0,
		_width = 713,
		_intervalTime = 3000,
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
		// 웹접근성 : '_$rollingBtnA' 이벤트 추가
		_$rollingBtnA.bind( 'click mouseenter mouseout focusin focusout', btnMouseHandler );
		_$nextBtn.bind( 'click mouseenter mouseleave focusin focusout' , nextBtnMouseHandler);
		_$prevBtn.bind( 'click mouseenter mouseleave focusin focusout' , prevBtnMouseHandler);
		_$bannerList.bind( 'mouseenter mouseleave focusin focusout', bannerMouseHandler );
	}

	function btnMouseHandler (e) {
		// 웹접근성 : 구조변경으로 인한 셀렉트 수정
		var idx =  $( this ).parent().parent().index();

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
		/* s:웹접근성 : 선형화 작업을 위해 이벤트 타입 추가 */
		} else if ( e.type == 'focusin' ) {
			if ( _isAutoRolling ){
				clearInterval(_intervalId);
			}
			if ( idx == _crtBanner ) return;
			moveStep( idx - _crtBanner );
		} else if ( e.type == 'focusout' ) {
			if ( _isAutoRolling ){
				autoPlay();
			}
		}
		/* e:웹접근성 : 선형화 작업을 위해 이벤트 타입 추가 */
	};


	function bannerMouseHandler (e) {
		if ( e.type == 'mouseenter' || e.type == 'focusin' ){
			showArrowBtn();
			/* s:웹접근성 : 오토롤링 수정 */
			if ( _isAutoRolling ){
				clearInterval(_intervalId);
			}
			/* e:웹접근성 : 오토롤링 수정 */
		} else if ( e.type == 'mouseleave' || e.type == 'focusout' ) {
			/* s:웹접근성 : 오토롤링 수정 */
			//_outTimer = setTimeout( hideArrowBtn, 100 );
			hideArrowBtn();
			if ( _isAutoRolling ){
				autoPlay();
			}
			/* e:웹접근성 : 오토롤링 수정 */
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
		
		/* s:웹접근성 : 셀렉트 수정 */
		_$rollingBtn.eq( _oldBanner ).removeClass( 'on' );
		_$rollingBtn.eq( _crtBanner ).addClass( 'on' );
		/* e:웹접근성 : 셀렉트 수정 */
	}

	function nextBanenr () {
		moveStep(1);
	}

	function autoPlay () {
		clearInterval(_intervalId);
		_intervalId = setInterval( nextBanenr , _intervalTime );
	}

}());