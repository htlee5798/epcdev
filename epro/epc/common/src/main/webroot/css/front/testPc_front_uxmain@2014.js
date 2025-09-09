jQuery.easing.def = "easeInQuad";
// 2014.10 new main lnb
function lnb(){
	$('.depth_menu > li > a').bind('mouseenter focus', function(){
		$(this).parent().siblings().removeClass('active');
		$(this).parent().addClass('active');
		$(this).parent().siblings().children('div').hide();
		$('.depth_menu3').hide();
		$('.depth_menu2 > ul > li > a').removeClass('active bg');
		$(this).next().show();
	});
	$('.depth_menu2 > ul > li > a').bind('mouseenter focus', function(){
		$(this).parent().siblings().children('div').hide();
		$(this).parent().siblings().children('a').removeClass('active');
		$(this).addClass('active');
		$(this).next().show();
	});
	$('.depth_menu').bind('mouseleave blur', function(){
		$('.depth_menu > li').removeClass('active');
		setTimeout(function() {
			$('.depth_menu li > div').hide();
		}, 70);
	});
}
// 2014.10 new main 카테고리 전체보기
function category(){
	$('.section_cate_all > h2 > a').click(function(event){
		event.preventDefault();
		$(this).parent().addClass('close');
		$(this).children('i').text('닫기');
		$(this).parent().next().addClass('open').animate({height:'656px', easing:'easeInQuad'}, 300);
		$(this).parent().next().find('h3>a').bind('mouseenter focus', function(event){
			event.preventDefault();
			$(this).addClass('active');
			$(this).parent().siblings('h3').children().removeClass('active');
			$(this).parent().siblings('.cate_directory').hide();
			$(this).parent().next().show();
			var objH = $(this).parent().next().outerHeight(true) + 75;
			$(this).parent().parent().css({'height':objH});
		});
		$('.section_cate_all .category_close').bind('click', function(){
			$('.section_cate_all > h2').removeClass('close');
			$('.section_cate_all > h2 i').text('열기');
			$('.section_cate_all > .cont-wrap').removeClass('open').animate({height:'0px', easing:'easeInQuad'}, 300);
		});
	});
}
// 2014.10 new main 전문관롤링
function cate_other(){
	$('.section_cate_other > .menu').bxSlider({
		mode:'vertical',
		auto:true,
		autoStart:true,
		autoHover:true,
		controls:false,
		pager:false,
		onAfterSlide:function(){
			$('.section_cate_other .menu > li > a, .section_cate_other > .btn').click(function(event){
				event.preventDefault();
				$('.section_cate_other .menuplus').show();
				$('.section_cate_other .menuplus').mouseleave(function(){
					$(this).hide();
				});
			});
		}
	});
}
// 2014.10 new main 메인슬라이드
function main_slide(){
	$('.section_planRolling .btn a:first').addClass('active');
	var slide = function(){
		$('.section_planRolling .btn a').bind('mouseenter focus', function(){
			$(this).siblings().removeClass('active');	
			$(this).addClass('active');
			var obj = $(this).index();
			$('.section_planRolling > ul > li:animated').stop();
			$('.section_planRolling > ul > li:eq('+obj+')').siblings().hide();
			$('.section_planRolling > ul > li:eq('+obj+')').show();
		});
	}
	var auto;
	auto_cont = function(){
		auto = window.setInterval(function(){slide_next()}, 4000);
	}
	var slide_next = function(){
		$('.section_planRolling .btn a.active').next().addClass('active').prev().removeClass('active');
		var obj = $('.section_planRolling .btn a.active').index();
		$('.section_planRolling > ul > li:animated').stop();
		$('.section_planRolling > ul > li:eq('+obj+')').siblings().hide();
		$('.section_planRolling > ul > li:eq('+obj+')').show();
		if($('.section_planRolling .btn a:last-child').attr('class') == 'active'){
			setTimeout(function(){
				$('.section_planRolling > ul > li:eq(0)').show().addClass('active').siblings().hide().removeClass('active');
				$('.section_planRolling .btn a:first-child').addClass('active').siblings().removeClass('active');
			}, 4000);
		}
	}
	auto_cont();
	$('.section_planRolling').bind('mouseenter', function(){
		clearInterval(auto);
	});
	$('.section_planRolling').bind('mouseleave', function(){
		clearInterval(auto);
		auto_cont();
	});
	slide();
}
// 2014.10 new main 금주의 전단 상품
function week_pdt(){
	var btn = $('.section_weekSpecial .btn_open a');
	btn.click(function(event){
		event.preventDefault();
		$('.weekSpecial_layer').show();
		$('.weekSpecial_layer').animate({
			'right':'-1px',
			'width':'762px',
			easing:'easeInQuad'
		},200);
		$('.weekSpecial_layer .btn').show();
		$('.weekSpecial_layer .btn_close a').click(function(event){
			event.preventDefault();
			$('.weekSpecial_layer').animate({
				'right':'-3px',
				'width':'155px',
				easing:'easeInQuad'
			},200);
			$('.weekSpecial_layer .btn').hide();
			setTimeout(function(){
				$('.weekSpecial_layer').hide();
			}, 140);
		});
	});
}
// 2014.10 new main 탭
function basic_tab(){
	$('.basic_tab > ul').hide();
	$('.basic_tab > h4:first-child').next('ul').show();
	$('.basic_tab h4 a').bind('mouseenter focus', function(){
		$(this).parent().siblings('h4').removeClass('active');
		$(this).parent().addClass('active');
		$(this).parent().siblings('ul').hide();
		$(this).parent().next('ul').show();
	});
	$('.basic_tab').bind('mouseleave blur', function(){
		$('.basic_tab h4').removeClass('active');
		$('.basic_tab > h4:first-child').addClass('active');
		$(this).children('ul').hide();
		$('.basic_tab > h4:first-child').next().show();
	});
}
// 2014.10 new main hot_issue
function hot_issue(){
	$('.seciton_hotIssue ul').bxSlider({
		autoHover:true,
		controls:false
	});
}
// 2014.10 recipejang
function recipejang(){
	$('.section_recipeJang ul').bxSlider({
		mode:'fade',
		autoHover:true,
		controls:false
	});
}
// 2014.10 new main 띠배너
function band_banner(){
	$('.first_banner > div > ul').bxSlider({
		mode:'vertical',
		auto:true,
		autoStart:true,
		autoHover:true,
		controls:false,
		autoControls:true,
		autoControlsCombine:true,
		onSliderLoad:function(){
			$('.section_mid_planBanner').mouseleave(function(){
				$('.bx-start').click();
			});
		}
	});
}
function band_banner_second(){
	$('.section_mid_planBanner.second > div > ul').bxSlider({
		controls:false,
		autoControls:true,
		autoControlsCombine:true
	});
}
function major1(){
	$('.section_major .store1 > ul').bxSlider({
		pager:false
	});
}
function major2(){
	$('.section_major .store2 > ul').bxSlider({
		pager:false
	});
}
function major3(){
	$('.section_major .store3 > ul').bxSlider({
		pager:false
	});
}
function major4(){
	$('.section_major .store4 > ul').bxSlider({
		pager:false
	});
}
$(window).load(function(){
	$('div#UxHeader').css({'overflow':'visible'});
});
$(document).ready( function() {
	// 2014.10 new main
	lnb();
	category();
	main_slide();
	week_pdt();
	basic_tab();
	cate_other();
	hot_issue();
	recipejang();
	band_banner();
	band_banner_second();
	major1();
	major2();
	major3();
	major4();

	/**
	 * front_uxmain
	 * @package	{front_uxmain}
	 * @version 20130709 (jslee)
	*/
	
	//uxPopularity
	if ( front_uxmain ) throw new Error('[ixError] "front_uxmain"이 이미 존재하여 충돌이 발생!');

	var front_uxmain = ( function () {
		var _$popularity = $('.prod_popularity'),
			_$tabBtns = $( '.tab_popularity li a' );

		var _crtBanner = 0;

		_$popularity.each( function ( index ) {
			if ( index != 0) $( this ).hide();
		});

		_$tabBtns.bind( 'click mouseover focusin focusout' , tabbtnMouseHandler );

		function tabbtnMouseHandler (e) {

			var idx = $( this ).parent().index();
			if ( e.type == 'click' || e.type == 'mouseover' ) {
				_$tabBtns.eq(_crtBanner).removeClass( 'on');
				_$popularity.eq(_crtBanner).hide();
				_crtBanner = idx;
				_$popularity.eq(idx).show();
				$( this ).addClass( 'on');
			}
		}


		var ua = navigator.userAgent.toLowerCase(),
		docMode = document.documentMode,
		isIE = false;

		if ( navigator.appName == 'Microsoft Internet Explorer' ) {
			var re = new RegExp( 'msie ([0-9]{1,}[\.0-9]{0,})' );
			if ( re.exec(ua) != null ) {
				if ( parseFloat( RegExp.$1 ) < 9 ) isIE = true;
			}
		}

		// uxSpecialZone
		var _$specialzone = $( 'div.uxSpecialZone'),
			_$btns = _$specialzone.find( 'div.special_lnb ul li a' ),
			_$list = _$specialzone.find( 'div.special_prod ul li' );

		var _crtZone = 0;

		_$list.each( function ( index ){
			if ( index != 0) $( this ).hide();
		});

		_$btns.bind( 'click mouseover focusin focusout' , sZonebtnMouseHandler );


		function sZonebtnMouseHandler (e) {
			var idx = $( this ).parent().index();


			if ( e.type == 'click' || e.type == 'mouseover' ) {
				_$btns.eq(_crtZone).removeClass( 'on');
				if ( isIE ){
					_$list.eq(_crtZone).hide();
				}else{
					_$list.eq(_crtZone).fadeOut();
				}
				_crtZone = idx;
				if ( isIE ){
					_$list.eq(idx).show();
				}else{
					_$list.eq(idx).fadeIn();
				}
				$( this ).addClass( 'on');
			}
		}

		// 설날 가격대별
		var _$popularGiftSet = $( 'div.popularGiftSet' ),
			_$btnsp = _$popularGiftSet.find( 'div.popularGiftSetCont ul li a.tab' ),
			_$listp = _$popularGiftSet.find( 'div.giftSetsList' );

		var _crtPopular = 0;

		_$listp.each( function ( index ){
			if ( index != 0) $( this ).hide();
		});

		_$btnsp.bind( 'click mouseover focusin focusout' , popularbtnMouseHandler );


		function popularbtnMouseHandler (e) {
			var idx = $( this ).parent().index();

			if ( e.type == 'click' || e.type == 'mouseover' ) {
				_$btnsp.eq(_crtPopular).removeClass( 'on');
				if ( isIE ){
					_$listp.eq(_crtPopular).hide();
				}else{
					_$listp.eq(_crtPopular).fadeOut();
				}
				_crtPopular = idx;
				if ( isIE ){
					_$listp.eq(idx).show();
				}else{
					_$listp.eq(idx).fadeIn();
				}
				$( this ).addClass( 'on');
			}
		}

		// 설날 카테고리별
		var _$categoryGiftSet = $('.categoryGiftSet'),
			_$ctabBtns = $( '.categoryGiftSetCont li a.tab' ),
			_$listCategory = _$categoryGiftSet.find( 'div.giftSetsList' );

		var _crtCategory = 0;

		_$listCategory.each( function ( index ) {
			if ( index != 0) $( this ).hide();
		});

		_$ctabBtns.bind( 'click mouseover focusin focusout' , ctabbtnMouseHandler );

		function ctabbtnMouseHandler (e) {
			var idx = $( this ).parent().index();

			if ( e.type == 'click' || e.type == 'mouseover' ) {
				_$ctabBtns.eq(_crtCategory).removeClass( 'on');
				_$listCategory.eq(_crtCategory).hide();
				_crtCategory = idx;
				_$listCategory.eq(idx).show();
				$( this ).addClass( 'on');
			}
		}

		var ua = navigator.userAgent.toLowerCase(),
		docMode = document.documentMode,
		isIE = false;

		if ( navigator.appName == 'Microsoft Internet Explorer' ) {
			var re = new RegExp( 'msie ([0-9]{1,}[\.0-9]{0,})' );
			if ( re.exec(ua) != null ) {
				if ( parseFloat( RegExp.$1 ) < 9 ) isIE = true;
			}
		}

		// 설날 타겟별
		var _$customizeGiftSet = $('.customizeGiftSet'),
			_$cutabBtns = $( '.customizeGiftSetCont li a.tab' ),
			_$listCustomize = _$customizeGiftSet.find( 'div.giftSetsList' );

		var _crtCustomize = 0;

		_$listCustomize.each( function ( index ) {
			if ( index != 0) $( this ).hide();
		});

		_$cutabBtns.bind( 'click mouseover focusin focusout' , cutabbtnMouseHandler );

		function cutabbtnMouseHandler (e) {
			var idx = $( this ).parent().index();

			if ( e.type == 'click' || e.type == 'mouseover' ) {
				_$cutabBtns.eq(_crtCustomize).removeClass( 'on');
				_$listCustomize.eq(_crtCustomize).hide();
				_crtCustomize = idx;
				_$listCustomize.eq(idx).show();
				$( this ).addClass( 'on');
			}
		}

		var ua = navigator.userAgent.toLowerCase(),
		docMode = document.documentMode,
		isIE = false;

		if ( navigator.appName == 'Microsoft Internet Explorer' ) {
			var re = new RegExp( 'msie ([0-9]{1,}[\.0-9]{0,})' );
			if ( re.exec(ua) != null ) {
				if ( parseFloat( RegExp.$1 ) < 9 ) isIE = true;
			}
		}

	}());

	/* 201403 foot 탭메뉴 온오버 js */
	$('.main_foot_top_board h5 a').bind('focus mouseenter',function(){
		var boardItem = $('.main_foot_top_board h5 a'),
			boardItemView = boardItem.index(this);
		$('.main_foot_top_board h5 a').closest('h5').removeClass('on');
		$('.main_foot_top_board ul').hide();

		$('.main_foot_top_board h5 a').eq(boardItemView).closest('h5').addClass('on');
		$('.main_foot_top_board ul').eq(boardItemView).show();
	});
	$('.main_foot_top_board h5 a').eq(0).trigger("mouseenter")

	/* 201404 RNB 사이드 메뉴 js */
	$('.rnb_bottom_btn a').bind('click', function() {
		$('body, html').animate({scrollTop:0}, '500', 'swing');
		return false;
	});

	$('.rnb_big_box .rnb_mid_inbox02').bind('click', function() {
		$('.rnb_big_box .layer_shopingCart').addClass('active');
	});
	$('html').bind('click', function(e) {
		if ($(e.target).parents('.rnb_big_box').length <= 0 || $(e.target).is('.btn_close')) {
			$('.rnb_big_box .layer_shopingCart').removeClass('active');
		}
	});
	// 검색UI 카테고리 - 더보기 스크립트
	$('.search-result-category .category button').toggle(
		function () {
			var more_height =  $('.category-group').height();
			$('.category .category-group').css({"height":"auto"});
			$(this).removeClass();
			$(this).addClass('on');
			$('.category button').html('닫기 -');
			},
		function () {
			var more_height =  $('.category-group').height();
			$('.category .category-group').css({"height":"95px"});
			$(this).removeClass();
			$(this).addClass('');
			$('.category button').html('더보기 +');
		}
	);
	// 검색UI FAQ - 아코디언 스크립트
	$(".list_faq_search dl dt a").bind("click focus", function(){
		$(".list_faq_search dt a").removeClass("on");
		$(".list_faq_search dd").removeClass("on");
		$(this).addClass("on");
		$(this).parent().next().addClass("on");
	});

});

$(window).scroll(function() {
	if ($(this).scrollTop() > 150) {
		$('.rnb_big_box').css({'position':'fixed', 'top':'10px'});
	} else {
		$('.rnb_big_box').css({'position':'absolute', 'top':'0'});
	}
})

/* 이벤트 내정보 확인하기 */
function goLoginURL2(URL){
	var flag = isLogin(URL);  //isLogin은 공통함수임
	if(flag){
		goPage2(URL);  
	}
}
function goPage2(URL){
	var form = document.searchForm;
	form.action = URL;
	form.submit();
}

