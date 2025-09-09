/**
 * front_plan_rolling
 * @package	{front_plan_rolling}
 * @version 20130709 (jslee)
*/

if ( front_plan_rolling ) throw new Error('[ixError] "front_plan_rolling"가 이미 존재하여 충돌이 발생!');

var front_plan_rolling = ( function () {	
	var _$list = $( 'div#planImg' ),
		_$bannerList = _$list.find( 'ul li' ),
		_$rollingBtn = $( 'ul#planTab' ).find( 'li.btn' ),
		_$nextBtn = $( '.uxPlanRolling' ).find( '#arrow_plan .next' ),
		_$prevBtn = $( '.uxPlanRolling' ).find( '#arrow_plan .prev' );

	var	_tlBanner = _$bannerList.length,
		_crtBanner = 0,
		_oldBanner = 0,		
		_width = 713,
		_intervalTime = 3000,
		_intervalId = 0,
		_isAutoRolling = true;

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
			if ( index > 0){
				$( this ).hide().fadeOut();
			}
		});	
	}

	function addBtnEvent () {
		_$rollingBtn.on( 'mouseover mouseout', btnMouseHandler );
		_$nextBtn.on( 'click mouseover mouseout' , nextBtnMouseHandler);
		_$prevBtn.on( 'click mouseover mouseout' , prevBtnMouseHandler);
		_$list.on( 'mouseover mouseout', banenrMouseHandler );
	}

	function banenrMouseHandler (e) {
		
		var idx =  $( this ).index();
		if ( e.type == 'mouseover' ){
			_$nextBtn.show();
			_$prevBtn.show();	

			if ( _isAutoRolling ){
				clearInterval(_intervalId);
			}
		}else if ( e.type == 'mouseout' ) {
			_$nextBtn.hide();
			_$prevBtn.hide();	
			if ( _isAutoRolling ){
				autoPlay();
			}
		}
	}

	function btnMouseHandler (e) {	
		var idx =  $( this ).index();
		
		if ( e.type == 'mouseover' ){
			//console.log ('over');
			if ( idx == _crtBanner ) return;
			moveStep( idx - _crtBanner );

			if ( _isAutoRolling ){
				clearInterval(_intervalId);
			}			
		}else if ( e.type == 'mouseout' ) {
			if ( _isAutoRolling ){
				
				autoPlay();
			}
		}
	};
	
	function nextBtnMouseHandler (e) {
		
		if ( e.type == 'click' ){
			moveStep( -1 );
		}else if ( e.type == 'mouseover' ){
			_$nextBtn.show();
			_$prevBtn.show();	

			if ( _isAutoRolling ){
				clearInterval(_intervalId);
			}
		}else if ( e.type == 'mouseout' ) {
			if ( _isAutoRolling ){
				autoPlay();
			}
		}

	}
	
	function prevBtnMouseHandler (e) {
		
		if ( e.type == 'click' ){		
			moveStep( 1 );
		}else if ( e.type == 'mouseover' ){
			_$nextBtn.show();
			_$prevBtn.show();	
			if ( _isAutoRolling ){
				clearInterval(_intervalId);
			}
		}else if ( e.type == 'mouseout' ) {
			if ( _isAutoRolling ){
				autoPlay();
			}
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
			$oldImg.stop().fadeOut();
			$crtImg.stop().fadeIn();
		}
		
		_$rollingBtn.eq( _oldBanner ).find( 'a' ).removeClass( 'on' );
		_$rollingBtn.eq( _crtBanner ).find( 'a' ).addClass( 'on' );	
	}

	function nextBanenr () {	
		moveStep(1);		
	}

	function autoPlay () {
		clearInterval(_intervalId);
		_intervalId = setInterval( nextBanenr , _intervalTime );
	}
	
}());