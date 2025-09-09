/**
 * lotteMart app - intro
 * @version 20130514 (_khv)
*/

$(function(){
	var _screenW = $(window).width(),
		_screenH = $(window).height(),
		_content = $('#intro').find('div.m-intro'),
		_dw = $('#intro').find('div.m-intro-a4'),
		_mobile = $('#intro').find('div.m-intro-a3'),
		_week = $('#intro').find('div.m-intro-a5'),
		_hot = $('#intro').find('div.m-intro-a2'),
		_mainBanner = $('#intro').find('div.m-intro-a1'),
		_riMenu = $('.menu').find('ul.list2');
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	/* 높이 조절 */
	//$('.search').text(_screenW+"/"+_screenH)
	
	if( _screenH > 930 ){
		var h = ((_screenH * 0.47) - (_screenH * 0.64))/2 + 70;
	
		$('div.m-menu').css( 'position', 'absolute' );

		_content.css( 'height', (_screenH - 192) + 'px' );
		_dw.css({ 'top': (_screenH * 0.64) + 'px', 'height':(_screenH * 0.18) + 'px' });
		_dw.find('a').css({ 'display':'inline-block', 'vertical-align':h+'px' });

		_mobile.css({ 'top': (_screenH * 0.47) + 'px' });
		_mobile.find('a').css({ 'display':'inline-block', 'vertical-align':h+'px' });

		_week.css({ 'top': (_screenH * 0.47) + 'px', 'height':(_screenH - 192)*0.45 + 'px' });
		_week.find('a').css({ 'display':'inline-block', 'vertical-align':h+'px' });

		_hot.css({ 'height': (_screenH - 192)*0.595 + 'px' });
		_hot.find('a').css({ 'display':'inline-block', 'vertical-align':h+'px' });

		_mainBanner.css({ 'top': (_screenH * 0.02) + 'px' });
		_mainBanner.find('a').css({ 'display':'inline-block', 'vertical-align':h+'px' });

		_riMenu.css({ 'height' : (_screenH - 700) + 'px' });
	} else {
		$('.m-ri').find('.last').css( 'border', '' );
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	$("#touchSlider").touchwipe({
		wipeLeft: function() { rightMove(); },
		wipeRight: function() { leftMove(); },
		min_move_x: 20,
		min_move_y: 20,
		preventDefaultEvents: true
	});


	$('.m-ri').find('.last').css( 'border-bottom', 'none' )

	var _wrap = $('.m-intro-a1').find('ul.pr'),
		_bullet = $('.m-intro-a1').find('ul.turn'),
		_length = mainBannerStr.length;
	
	var i;
	for ( i = 0; i < _length; i++ ) {
		_wrap.append( '<li><a href="' + mainBannerStr[i].link + '"><img src="' + mainBannerStr[i].img + '" alt=""></a></li>' );
		_bullet.append( '<li><a href="#">' + i + '</a></li>' );
	}

	_wrap.find('li').hide().eq(0).show();
	_bullet.find('li').eq(0).addClass('on');
	
	var _target =_wrap.find('li'),
		_current = 0,
		_before,
		_open = false,
		_rollingTime = 2500;
	
	var _timer = setInterval( rightMove, _rollingTime );

	function rightMove(){
		var _width = _target.width();

		_before = _current;
		if( _current == _length-1 ){
			_current = 0;
		} else {
			_current++;
		}
		
		moveRight( _width );
	}
	function leftMove() {
		var _width = _target.width();

		_before = _current;
		if( _current == 0 ){
			_current = _length-1;
		} else {
			_current--;
		}
		
		moveLeft( _width );
	}
	
	_bullet.find('li').on( 'click', function(){
		var idx = $(this).index(),
			_width = _target.width();

		_before = _current;
		_current = idx;
		clearInterval( _timer );
		moveRight( _width );
	});

	function beforeCom(){
		_bullet.find('li').removeClass('on').eq( _current ).addClass('on');	
		$(this).hide();
		clearInterval( _timer );
		if( !_open ){
			_timer = setInterval( rightMove, _rollingTime );
		}
	}

	function moveRight( $wid ){
		clearInterval( _timer );
		_target.eq( _current ).show().css({ 'left' : $wid + 'px' });
		
		TweenMax.to( _target.eq( _before ), 0.5, { left : -$wid + 'px' } );
		TweenMax.to( _target.eq( _current ), 0.5, { left : '0px', onComplete : beforeCom } );
	}

	function moveLeft( $wid ){
		clearInterval( _timer );
		_target.eq( _current ).show().css({ 'left' : -$wid + 'px' });
		
		TweenMax.to( _target.eq( _before ), 0.5, { left : $wid + 'px' } );
		TweenMax.to( _target.eq( _current ), 0.5, { left : '0px', onComplete : beforeCom } );
	}
	
	$('.m-menu').find('.btn4').on( 'click', function(){
		clearInterval( _timer );
		if( $(this).hasClass('open') ){
			_open = false;
			_timer = setInterval( rightMove, _rollingTime );
			$(this).removeClass('open');
			
			TweenMax.to( $('.m-le'), 0.5, { left : '0px' } );
			TweenMax.to( $('.m-menu'), 0.5, { left : '0px' } );
			TweenMax.to( $('.m-ri'), 0.5, { right : '-420px', onComplete : rightComplete } );
			//$('.m-le').animate({ 'left':'0px' });
			//$('.m-menu').animate({ 'left':'0px' });
			//$('.m-ri').animate({ 'right':'-420px' }, { complete : rightComplete });
		} else {
			_open = true;
			$(this).addClass('open');
			$('.m-ri').show();

			TweenMax.to( $('.m-le'), 0.5, { left : '-420px' } );
			TweenMax.to( $('.m-menu'), 0.5, { left : '-420px' } );
			TweenMax.to( $('.m-ri'), 0.5, { right : '0' } );
			//$('.m-le').animate({ 'left':'-420px' });
			//$('.m-menu').animate({ 'left':'-420px' });
			//$('.m-ri').animate({ 'right':'0px' });
		}
	});

	function rightComplete(){
		$('.m-ri').hide();
	}
	
});