	/* 2014-03 스마트쇼핑팁 레이어팝업 js */
	function closeTip(){
		$('.left_uxSmartTip_layerbox').fadeOut().find('.left_uxSmartTip_inner').animate({'width':0}, 200, 'easeOutCubic');
	}
	function openTip(){
		$("#uxSmartTipLayer").fadeIn().find('.left_uxSmartTip_inner').animate({'width':'100%'}, 300, 'easeInOutCubic');
	}

	$('.left_uxSmartTip a').bind('click', function() {
		openTip()
		return false;
	});

	$('.left_uxSmartTip_close, .uxSmartTip_bg').bind('click', function() {
		closeTip()
	});

	/* 전단오픈때만 Close 시키고 오픈 후 Open 시키기 : 네이버 검색광고 심사문제 발생 */
	/*
	var cookie = ixBand( 'smartTip').getCookie();

	if ( !cookie ) {
		ixBand( 'smartTip' ).setCookie( true , 24 , '/' );
		openTip()
		timer = setTimeout( closeSmartTip , 3000 );
	}

	function closeSmartTip () {
		closeTip()
		if ( timer ) clearTimeout( timer);
	}
	*/