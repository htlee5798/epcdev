/**
* Lottemart
*@package { topStartTip}
*@version 20131025 (jslee)
*/
if ( topSmartTip ) {

	if (window.console) console.log('[선언중북] "topSmartTip"가 이미 선언 되어있어 선언불가');

} else {

	var topSmartTip = (function () {
		var _$ssTip = $( '#smartTip' ),
			_$uxSmartTip = _$ssTip.find( '.uxSmartTip' ),
			_$ssTipBtn = _$ssTip.find( '.uxSmartTip .cont_smarttip .btn_tip' );
		var cookieTime = 24,		// 쿠키 적용 기간 ( 24시간 )
			time = 7,				// 사라지는 시간 ( 초 )
			timer;
		var cookie = ixBand( 'smartTip').getCookie();

		if ( !cookie ) {
			ixBand( 'smartTip' ).setCookie( true , cookieTime , '/' );
			openSmartTip ( 0 );
			timer = setTimeout( closeSmartTip , time * 1000 );
		}

		_$ssTipBtn.bind( 'click', function (){
			if ( $(this).hasClass( 'on' ) ) {
				closeSmartTip();
			} else {
				openSmartTip( 0 );
			}
		});

		function closeSmartTip () {
			_$ssTipBtn.removeClass( 'on' );
			_$ssTip.animate({ 'top' : '-395px' }, { duration : 400, easing : 'easeOutCubic', queue : false });
			if ( timer ) clearTimeout( timer);
		}

		function openSmartTip (h) {
			_$ssTipBtn.addClass( 'on' );
			_$ssTip.animate({ 'top' : h +'px' }, { duration : 400, easing : 'easeInOutCubic', queue : false });
		}
		return true;
	}());
}
