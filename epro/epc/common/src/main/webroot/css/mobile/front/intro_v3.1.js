$(function(){
	$('#bottom .mymart a').bind('click', function(){
		slideMenu();
	});

	resize();
	vai();
});

$(window).load(function(){

	resize();
	vai();

	//imgCycle2("#prWrap", 3000, "#nav-pr");

	var opts = {
		fx: 'scrollHorz',
		slideExpr:'li',
		pause: 1,
		timeout: 3500,
		pager:'#nav-pr',
		slideResize: 0,
		fit:1
	}

	$("#prWrap").cycle(opts);

	$(window).resize(function(){
		opts.width = $("#prWrap").width();
		opts.height = $("#prWrap img:first-child").height();
		$("#prWrap").cycle('destroy');
		$("#prWrap").cycle(opts);
	});
});

$(window).resize(function(){
	resize();
	vai();
});


// rolling swipe
$("#prWrap").touchwipe({
	wipeLeft:function(){
		$("#prWrap").cycle("next");
	},
	wipeRight:function(){
		$("#prWrap").cycle("prev");
	}
});


function resize() {
	var _winH =$(window).height(),
	_ct = $('#container');
	_ct.css('height',_winH - 109 - 167); // container height
}

function vai() {
	var _winH =$(window).height(),
	_roll = $('#rolling'),
	_rollH = $('#rolling').height(),
	_ev = $('#event'),
	_evH = $('#event').height(),
	_mo = $('#mobile'),
	_moH = $('#mobile').height(),
	_moW = $('#mobile').width(),
	_dw = $('#dw'),
	_dwH = $('#dw').height(),
	_dwW = $('#dw').width(),
	_plan = $('#plan'),
	_planH = $('#plan a').height();


	$('#prWrap', _roll).css('margin-top', (_rollH - $('img', _roll).height())/2 );

	$('img', _ev).css({ 'height': _evH -3 +'px' , 'width' : 'auto' /*, 'margin-top' : (_evH - $('img', _ev).height()) / 2 */});
	if ( $('img', _ev).width() > _ev.width() ) {
		$('img', _ev).css({
			'margin-left' : (-($('img', _ev).width() - _ev.width()) /2 )
		});
	}

	$('img', _mo).css({ 'height': 'auto' , 'width' : _moW -3 +'px' , 'margin-top' : (_moH - $('img', _mo).height()) / 2 });
	if ( $('img', _mo).height() > _mo.height() ) {
		$('img', _mo).css({ 'height' : _moH -3, 'width' : 'auto' });
	}

	$('img', _dw).css({ 'height': 'auto' , 'width' : _dwW -3 +'px' , 'margin-top' : (_dwH - $('img', _dw).height()) / 2 });
	if ( $('img', _dw).height() > _dw.height() ) {
		$('img', _dw).css({ 'height' : _dwH -3, 'width' : 'auto' });
	}

	$('img', _plan).css({ 'height': _planH -3 +'px' , 'width' : 'auto' /*, 'margin-top' : (_planH - $('img', _planH).height()) / 2 */});
	if ( $('img', _plan).width() > _plan.width() ) {
		$('img', _plan).css({
			'margin-left' : (-($('img', _plan).width() - _plan.width()) /2 )
		});
	}

}

function slideMenu() {
	_intro = $('#intro'),
	_sm = $('#slideMenu'),
	_btn = $('#bottom .mymart a');

	if (_sm.hasClass('close')) {
		$(_sm).removeClass('close');
		$(_sm).css('display','block');

		_sm.transition({ x: '-420px' });
		_intro.transition({ x: '-420px' });
		_btn.addClass('open');
	} else {
		$(_sm).addClass('close');
		_intro.transition({ x: '0px' });
		_sm.transition({ x: '0px' });
		_btn.removeClass('open');
	}

}
