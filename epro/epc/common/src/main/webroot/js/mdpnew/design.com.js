// LNB 너비
$(function(){
	// calc(((100% - 6px) / 3) - 4px)
	// android 4.3 미만
	var menu = $('.contents .lnb ul');
	var menuWidth = $(menu).width();
	var autoWidth = (((menuWidth - 4) / 2) - 3) + 'px';   // 2015-12-14 수정
	$('.contents .lnb ul li').css('width', autoWidth);
});

// 셀렉트박스 디자인 UI
$(function(){
	$('.op > ul > li > a').click(function() {
		var selectListWidth = $('.op > ul > li > a').outerWidth();
		$('.op > ul > li > ul').css('width', (selectListWidth-2));
		$('.op > ul > li > a').css({'border-color':'#999'});  // 모든 셀렉박스 border 강조 지우기 
		$('.op > ul > li > ul').hide(); // 모든 셀렉박스 숨기기
		var prevIndex = $('ul.open').parent().index(); // 열려 있는 셀렉박스
		var nextIndex = $(this).parent().index();  // 다른 셀렉박스 선택
		$('.op > ul > li > ul').removeClass('open');

		// 선택된 셀렉박스 강조 & 열림
		$(this).css({'border-color':'#0762a0'});
		$(this).next('ul').show().addClass('open');
		
		if ( prevIndex == nextIndex ){
			$('.op > ul > li > a').css({'border-color':'#999'});
			$(this).next('ul').hide().removeClass('open');
		}
		
		// 셀렉 리트스 선택
		$(this).next('ul').find('> li > a').click(function(){
			var _selectVal = $(this).text();
			$(this).parent().parent().find('a').removeClass('on');
			$(this).addClass('on');
			$(this).parent().parent().prev('a').text(_selectVal).css({'border-color':'#999'});
			$(this).parent().parent().hide().removeClass('open');
		});		
	});
});

// 상단 URL Bar 없애기
$(function(){
	setTimeout(function(){ if(window.pageYOffset === 0) { window.scrollTo(0, 1); } }, 50);
});

//JQM loading 메시지 숨김
$(function(){	
	$.mobile.loadingMessage = false;
});

//이미지 체크 버튼
$(function(){
	var iCheck = $('.iCheck');
	
	$(iCheck).click(function(){
		if ( $(this).is(':checked') ){
			$(this).parent().find('>label').addClass('checked');
		} else {
			$(this).parent().find('>label').removeClass('checked');
		}
	});
});

// 탭
$(function(){
	$('.tab a').click(function(){
		$('.tab a').removeClass('on');
		$(this).addClass('on');
	});
});

// 리스트 스크롤링
$(function(){
	fn_listHeight = function(){
		var deviceHeight = $(window).height();
		var subHeaderHeight = $('header').outerHeight();
		var infoHeight = $('.info').outerHeight();
		var startHeight = $('.start').outerHeight();
		var listHeight = deviceHeight - (subHeaderHeight + infoHeight + startHeight);  // header + info + 출발보고
		$('.list').height(listHeight);
	};
	fn_listHeight();	
	
	// 버튼 On, Off
	/*$.fn.buttonAction = function(className){
		return $(this).each(function(){
			var $this = $(this),
				 $btn = $('input, button', $this)
				
			$btn.click(function(){
				if($(this).hasClass(className)){
					//$(this).removeClass(className);
				} else {
					$btn.removeClass(className);
					$(this).addClass(className);
				}
				$(this).blur();
				return;
			});
		});
	}
	$('.btn_app').buttonAction('ACTIVE');*/
});
$(window).resize(function() {
	fn_listHeight();
}); // 디바이스 높이에 맞춰 list height 값 변경

