/**
 * front_main_rolling
 * @package	{front_main_rolling}
 * @version 20130709 (jslee)
*/

if ( front_main_rolling ) throw new Error('[ixError] "front_main_rolling"가 이미 존재하여 충돌이 발생!');

var front_main_rolling = ( function ( $list , isAuto , w , type) {	

	var _$list = $list,
		_$bannerList = $list.find( '.rolling_list' ).children(),
		_$rollingBtn = $list.find( '.rolling_btn ul li' ),
		_$nextBtn = $list.find( '.arrow a.next'),
		_$prevBtn = $list.find( '.arrow a.prev');

	var	_tlBanner = _$bannerList.length,
		_crtBanner = 0,
		_oldBanner = 0,		
		_width = w,
		_intervalTime = 3000,
		_intervalId = 0,
		_isAutoRolling = isAuto,
		_type = type;

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
			if ( _type ){
				if ( $( this ).index() > 0){
					$( this ).hide().fadeOut();
				}
			}else{
				$( this ).css( 'left' , _width * index + 'px');
			}
		});	
	}

	function addBtnEvent () {
		_$rollingBtn.on( 'click mouseover mouseout', btnMouseHandler );		
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
		
		if ( e.type == 'click' ){

			if ( idx == _crtBanner ) return;
			moveStep( idx - _crtBanner );
			
		}else if ( e.type == 'mouseover' ){
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
		if ( _type ){
			if ( isIE ) {
				$oldImg.hide();
				$crtImg.show();
			}else{
				$oldImg.stop().fadeOut();
				$crtImg.stop().fadeIn();
			}
		}else{		
			if ( step > 0 ){
				$oldImg.animate( {left: (_width*-1)+'px'}, 'fast');			
				$crtImg.css( 'left', _width + 'px' );
			}else{
				$oldImg.animate( {left: _width + 'px'}, 'fast');
				$crtImg.css( 'left', (_width*-1)+'px' );
			}
			$crtImg.animate( {left: '0px'}, 'fast');		
		}

		var imgPath = _$rollingBtn.eq( _oldBanner ).find( 'img' ).attr( 'src' );		
		_$rollingBtn.eq( _oldBanner ).find( 'img' ).attr( 'src' , imgPath.replace( '_on.', '_off.' ) );

		var crtimgPath = _$rollingBtn.eq( _crtBanner ).find( 'img' ).attr( 'src' );	
		_$rollingBtn.eq( _crtBanner ).find( 'img' ).attr( 'src' , crtimgPath.replace( '_off.', '_on.' ) );
	}

	function nextBanenr () {	
		moveStep(1);		
	}


	function autoPlay () {
		clearInterval(_intervalId);
		_intervalId = setInterval( nextBanenr , _intervalTime );
	}

});

var rolling1 = new front_main_rolling( $( 'div.uxRecipe' ) , true, 237 ,true );
var rolling2 = new front_main_rolling( $( 'div.uxDontWorry')  , true, 237 ,true);
var rolling3 = new front_main_rolling( $( 'div.uxPB')  , false, 241 ,false );
