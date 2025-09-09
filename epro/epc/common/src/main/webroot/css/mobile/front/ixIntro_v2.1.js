/**
 * lotteMart app - intro
 * @version 20130514 (_khv)
*/

$(function(){
	var _screenW = $(window).width(),
		_screenH = $(window).height() - ($('div.m-header').outerHeight() + $('div.m-menu').outerHeight() + 86),
		_content = $('#intro').find('div.m-intro'),
		_dw = $('#intro').find('div.m-intro-a4'),
		_mobile = $('#intro').find('div.m-intro-a3'),
		_week = $('#intro').find('div.m-intro-a5'),
		_hot = $('#intro').find('div.m-intro-a2'),
		_mainBanner = $('#intro').find('div.m-intro-a1'),
		_mainBannerRoll = $('#intro').find('#pr-product-list'),
		_mainBannerRollImg = $('#pr-product-list ul.pr li:first-child img'),
		_f1 = $('#f1'),
		_f2 = $('#f2'),
		_f2_l = $('#f2-l'),
		_riMenu = $('.m-ri').find('ul.list2');

	/////////////////////////////////////////////////////////////////////////////////////////////
	/* 높이 조절 */
	//$('.search').text(_screenW+"/"+_screenH)
	/*
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
	*/

	_content.css( 'height', _screenH + 'px' );
	_f1.css( 'height', _screenH*0.628 + 'px' );
	_f2.css( 'height', _screenH*0.372 + 'px' );
	_f2_l.css( 'width', (_screenW*0.6593)-3 + 'px' );

	_mainBanner.css({ 'width':(_screenW*0.6593)-3 + 'px' });
	//_hot.css({ 'height': _screenH*0.6132 + 'px', 'width':_screenW*0.3407 + 'px' });
	_hot.css({ 'width':_screenW*0.3407 + 'px' });
	_mobile.css({ 'height':(_screenH*0.3868-3)/2 + 'px', 'width':(_screenW*0.6593)-3 + 'px' });
	_dw.css({ 'height':(_screenH*0.3868-3)/2 + 'px', 'width':(_screenW*0.6593)-3 + 'px' });
	_week.css({ 'height':_screenH*0.3868-3 + 'px', 'width':_screenW*0.3407 + 'px' });

	$('.m-ri').css({ 'height' : $(window).height() + 'px' });

	_mainBanner.find('img').css({ 'width':(_screenW*0.6593)-3 + 'px', 'height':'auto' });

	_hot.find('img').css({ 'height':'auto', 'width':_screenW*0.3407 + 'px' });
	_mobile.find('img').css({ 'height':'auto', 'width':(_screenW*0.6593)-3 + 'px' });
	_dw.find('img').css({ 'height':'auto', 'width':(_screenW*0.6593)-3 + 'px' });
	_week.find('img').css({ 'height':'auto', 'width':_screenW*0.3407 + 'px' });

	if ( _f1.height() > _mainBannerRoll.height() ) {
		$(_mainBannerRollImg).load(function(){
			$('ul.pr', _mainBannerRoll).css( 'height' , _mainBannerRollImg.height() );
			_mainBannerRoll.css( {'height' : _mainBannerRollImg.height(), 'margin-top' : (_f1.height()-_mainBannerRoll.height())/2 + 'px' });

			// cycle reset
			$('#pr-product-list').cycle('destroy');
			imgCycle("#pr-product-list", 3000, "#nav-flyer");
		});
	}

	$('img', _hot).load(function(){
		if ( _f1.height() > $(this).height() ) {
			_hot.find('img').css( 'margin-top' , (_f1.height()-$(this).height())/2 + 'px' );
		}
	});
	$('img', _mobile).load(function(){
		if ( _mobile.height() > $(this).height() ) {
			_mobile.find('img').css( 'margin-top' , (_mobile.height()-$(this).height())/2 + 'px' );
		}
	});
	$('img', _dw).load(function(){
		if ( _dw.height() > $(this).height() ) {
			_dw.find('img').css( 'margin-top' , (_dw.height()-$(this).height())/2 + 'px' );
		}
	});
	$('img', _week).load(function(){
		if ( _week.height() > $(this).height() ) {
			_week.find('img').css( 'margin-top' , (_week.height()-$(this).height())/2 + 'px' );
		}
	});
	//_hot.css({ 'height': _screenH*0.6132 + 'px' });

	/////////////////////////////////////////////////////////////////////////////////////////////

	$("#touchSlider").touchwipe({
		wipeLeft: function() { rightMove(); },
		wipeRight: function() { leftMove(); },
		min_move_x: 20,
		min_move_y: 20,
		preventDefaultEvents: true
	});

	var _wrap = $('.m-intro-a1').find('ul.pr'),
		_bullet = $('.m-intro-a1').find('ul.turn');

	var _target =_wrap.find('li'),
		_current = 0,
		_before,
		_open = false,
		_rollingTime = 2500;

	var _speed = 0.3;

	function rightMove(){
		var _width = _target.width();

		_before = _current;
	}
	function leftMove() {
		var _width = _target.width();

		_before = _current;
	}

	$('.m-menu').find('.btn4').bind( 'click', function(){
		if( $(this).hasClass('open') ){
			_open = false;
			$(this).removeClass('open');

			TweenMax.to( $('.m-le'), _speed, { left : '0px' } );
			TweenMax.to( $('.m-menu'), _speed, { left : '0px' } );
			TweenMax.to( $('.m-ri'), _speed, { right : '-420px', onComplete : rightComplete } );
			//$('.m-le').animate({ 'left':'0px' });
			//$('.m-menu').animate({ 'left':'0px' });
			//$('.m-ri').animate({ 'right':'-420px' }, { complete : rightComplete });
		} else {
			_open = true;
			$(this).addClass('open');
			$('.m-ri').show();

			TweenMax.to( $('.m-le'), _speed, { left : '-420px' } );
			TweenMax.to( $('.m-menu'), _speed, { left : '-420px' } );
			TweenMax.to( $('.m-ri'), _speed, { right : '0' } );
			//$('.m-le').animate({ 'left':'-420px' });
			//$('.m-menu').animate({ 'left':'-420px' });
			//$('.m-ri').animate({ 'right':'0px' });
		}
	});

	function rightComplete(){
		$('.m-ri').hide();
	}

});