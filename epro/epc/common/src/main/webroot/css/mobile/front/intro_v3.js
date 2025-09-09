$(window).load(function(){
	///////////////////////////////////////////////////////////////////////////////////
	// init

	var	_cont = $('#introWrap'),
		_top = $('div.tb'),
		_mainBanner = _cont.find('span.mainBanner'),
		_dot = _mainBanner.find('div.dotWrap'),
		_hotEvent = _cont.find('span.hotEvent'),
		_bottom = _cont.find('div.bottom'),
		_dm = _cont.find('div.dm');

	// upper 상단 여백 설정
	var _topH = _top.find('div.top').height(),
		_bannerImgH = _mainBanner.find('li').height(),
		_boxH = (_mainBanner.height() - _bannerImgH)/2;

	_mainBanner.css( 'padding-top', _topH + 'px' );
	_hotEvent.css( 'padding-top', _topH + 'px' );
	_mainBanner.find('ul.imgList li').css( 'padding-top', _boxH + 'px' );
	_hotEvent.find('div').css( 'padding-top', _boxH + 'px' );
	_dot.css( 'padding-top', _boxH + _topH + _bannerImgH - 17 + 'px' );

	// title 상단 여백 설정
	var _titleH = _top.find('div.title img').height();

	_top.find('div.title').css( 'padding-top', (_topH - _titleH)/2 + 'px' );

	// gnb 여백 설정
	var _botH = _bottom.height(),
		_imgH = _bottom.find('div.btn2 img').height();

	_bottom.find('div.btn1').css( 'padding-top', ( _botH - _imgH )/2 + 'px' );
	_bottom.find('div.btn2').css( 'padding-top', ( _botH - _imgH )/2 + 'px' );

	// lower 여백설정
	var _lowH = _dm.height(),
		_dontworry = _cont.find('div.dontworry img'),
		_mobile = _cont.find('div.mobile img'),
		_lfH = (_lowH/2 - _dontworry.height() + 4)/2;

	_dontworry.css( 'padding-top', _lfH + 'px' );
	_mobile.css( 'padding-top', _lfH + 'px' );

	///////////////////////////////////////////////////////////////////////////////////
	// main banner

	$("#touchSlider").touchwipe({
		wipeLeft: function() { rightMove(); },
		wipeRight: function() { leftMove(); },
		min_move_x: 20,
		min_move_y: 20,
		preventDefaultEvents: true
	});

	var _wrap = _cont.find('ul.imgList'),
		_bullet = _cont.find('ul.dot'),
		_length = 4;

	var _target =_wrap.find('li'),
		_current = 0,
		_before,
		_open = false,
		_rollingTime = 2500;

	var _speed = 0.3;

	var _timer = setInterval( rightMove, _rollingTime );

	_wrap.find('li').hide().eq(0).show();
	_dot.find( 'li img' ).eq( _current ).attr( 'src', '//simage.lottemart.com/lim/static_root/images/mobile/common/intro/dot_on.png' );

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

	function beforeCom(){
		_dot.find( 'li img' ).attr( 'src', '//simage.lottemart.com/lim/static_root/images/mobile/common/intro/dot_off.png' );
		_dot.find( 'li img' ).eq( _current ).attr( 'src', '//simage.lottemart.com/lim/static_root/images/mobile/common/intro/dot_on.png' );

		$(this).hide();
		clearInterval( _timer );
		if( !_open ){
			_timer = setInterval( rightMove, _rollingTime );
		}
	}

	function moveRight( $wid ){
		clearInterval( _timer );
		_target.eq( _current ).show().css({ 'left' : $wid + 'px' });

		_target.eq( _before ).animate({ 'left' : -$wid + 'px' }, { complete : beforeCom });
		_target.eq( _current ).animate({ 'left' : '0px' });
	}

	function moveLeft( $wid ){
		clearInterval( _timer );
		_target.eq( _current ).show().css({ 'left' : -$wid + 'px' });

		_target.eq( _before ).animate({ 'left' : $wid + 'px' }, { complete : beforeCom });
		_target.eq( _current ).animate({ 'left' : '0px' });
	}

	_dot.find('li').click( function(){
		var idx = $(this).index(),
			_width = _target.width();

		_before = _current;
		_current = idx;
		clearInterval( _timer );
		moveRight( _width );
	});

	///////////////////////////////////////////////////////////////////////////////////
	// right panel

	var _rightMenu = $('div.rightMenu'),
		_list = _rightMenu.find('ul.list'),
		_location = _rightMenu.find('div.location'),
		_listH = _list.find('li').height(),
		_listTextH = _list.find('a').height(),
		_titleH = _rightMenu.find('div.title').height(),
		_logo = _rightMenu.find('h1.logo'),
		_login = _rightMenu.find('div.login');

	_rightMenu.css( 'left', $(window).width() + 'px' );
	_logo.css( 'top', (_titleH - _logo.height())/2 + 'px' );
	_login.find('span').css( 'top', (_titleH - _login.find('span').height())/2 + 'px' );

	$('div.bottom').find('div.btn2').bind( 'click', function(){
		if( $(this).hasClass('open') ){
			_open = false;
			$(this).removeClass('open');

			//$('#introWrap').animate({ 'left' : '0px' }, { duration : 'fast', complete : rightComplete });
			$('#introWrap').transition({ x : '0px' });
		} else {
			_open = true;
			$(this).addClass('open');

			//$('#introWrap').animate({ 'left' : -$(window).width()*0.66 + 'px' }, 'fast');
			$('#introWrap').transition({ x : -$(window).width()*0.66 + 'px' });
		}
	});

	function rightComplete(){
		_timer = setInterval( rightMove, _rollingTime );
	}

	_list.find('a').css( 'padding', (_listH - _listTextH)/2 + 'px 0 0 5%' );
	_location.find('a').css({
		'padding' : (_listH - _listTextH)/2 + 'px 45px 0 5%' ,
		'background' : 'url("//simage.lottemart.com/lim/static_root/images/mobile/common/intro/icon_pos.gif") no-repeat 100% ' + Math.floor((_listH - _listTextH)/2 + 10) + 'px'
	});

});


// responsive image map
;(function(a){a.fn.rwdImageMaps=function(){var d=this,c=parseFloat(a.fn.jquery);var b=function(){d.each(function(){if(typeof(a(this).attr("usemap"))=="undefined"){return}var f=this,e=a(f);a("<img />").load(function(){var o,k,i="width",n="height";if(c<1.6){o=f.getAttribute(i),k=f.getAttribute(n)}else{o=e.attr(i),k=e.attr(n)}if(!o||!k){var p=new Image();p.src=e.attr("src");if(!o){o=p.width}if(!k){k=p.height}}var g=e.width()/100,l=e.height()/100,j=e.attr("usemap").replace("#",""),m="coords";a('map[name="'+j+'"]').find("area").each(function(){var s=a(this);if(!s.data(m)){s.data(m,s.attr(m))}var r=s.data(m).split(","),q=new Array(r.length);for(var h=0;h<q.length;++h){if(h%2===0){q[h]=parseInt(((r[h]/o)*100)*g)}else{q[h]=parseInt(((r[h]/k)*100)*l)}}s.attr(m,q.toString())})}).attr("src",e.attr("src"))})};a(window).resize(b).trigger("resize");return this}})(jQuery);