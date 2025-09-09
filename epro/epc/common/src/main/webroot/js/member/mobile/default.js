

var noMoreView = function(id,day){
	setCookie(id,"no",day);
	document.getElementById(id).style.display = "none"
}

var getCookie = function (name) {
	var dc = document.cookie;
	var prefix = name + "=";
	var begin = dc.indexOf("; " + prefix);
	if (begin == -1) {
		begin = dc.indexOf(prefix);
		if (begin != 0) { return null;}
	} else {begin += 2;}
	var end = document.cookie.indexOf(";", begin);
	if (end == -1) {end = dc.length;}
	return unescape(dc.substring(begin + prefix.length, end));
};

// 쿠키값 설정
var setCookie = function (name, value, expiredays) {
	var today = new Date();
	today.setDate( today.getDate() + expiredays );
	document.cookie = name + "=" + escape( value ) + "; expires=" + today.toGMTString() + ";";
};

/*
 * uger agent 체크, ie 아닐경우 window blur 이벤트 구분
 */
var ugeragent = navigator.userAgent;
ugeragent = ugeragent.toLowerCase();
var ie = false;
if(-1 < ugeragent.indexOf('msie')){
	ie = true;
}
var window_focus = 'focus';
if(ie == false){
	window.addEventListener('focus', function() {
		window_focus = 'focus';
	},false);
	window.addEventListener('blur', function() {
		window_focus = 'blur';
	},false);	
}

/*var ieCheck = ugeragent.replace(/\s*$/,'');

if(-1 < ugeragent.indexOf('msie 9')){
	$("span.btn_type3").each(function(index,element){
		var width = $(this).find(">button").outerWidth() * 1;
		$(this).css("width",width);
	});
	
}*/
/*
 * 함수명 : imgOverAttr
 * 설명 : 관리자에서 노멀, 오버시 이미지를 관리하는 경우 overimg, outimg 어트리뷰트를 생성하고, 그걸 받아서 롤오버 처리 해줍니다
 * 사용법 : imgOverAttr('해당 이미지 싸고 있는 엘리먼트에 부여한 아이디값','이벤트 핸들러 발생시킬 이미지 싸고 있는 엘리먼트 태그')
 */
function imgOverAttr(id,element){
	$("#" + id).find(element).bind("mouseover", function(event){
		var overimg = $(this).find("img").attr("overimg");
		//console.log(overimg)
		$(this).find("img").attr("src",overimg);
	});
	$("#" + id).find(element).bind("mouseout", function(event){
		var outimg = $(this).find("img").attr("outimg");
		//console.log(outimg)
		$(this).find("img").attr("src",outimg);
	});
}
/* 
 * 함수명 : slider_photo_v
 * 설명 : 포토갤러리의 상하  슬라이딩 처리, 이동 속도 변수로 받음
 * 사용법  : slider_list('위아래 버튼과 배너 마크업(ul)을 포함하는 영역에 부여한 아이디',이동속도));
 */
function slider_photo_v(tgid,minimum,speed){
	var id = $("#" + tgid);	
	var height = id.find("ul>li").outerHeight() * 1;
	var length = id.find("ul>li").length;
	id.find("ul").find("li").eq(0).find("a").addClass("active");
	id.find("ul").find("li").eq(0).find("a>span.round").addClass("active");
	if(length > minimum){
		id.attr("clickable","true");
		id.find(".btn_roll").click(function(){
			if(id.attr("clickable") == "true"){
				if($(this).attr("rel") == "prev"){
					id.attr("clickable","false");
					id.find("ul").find(">li:last").clone().prependTo(id.find("ul"));
					id.find("ul").css("top",-height);
					id.find("ul").animate({"top":"0"},speed,function(){
						id.find("ul").find(">li:last").remove();
						id.attr("clickable","true");
					});
				}else if($(this).attr("rel") == "next"){
					id.attr("clickable","false");
					id.find("ul").animate({"top":-height},speed,function(){
						id.find("ul").find(">li").eq(0).clone().appendTo(id.find("ul"));
						id.find("ul").find(">li").eq(0).remove();
						id.find("ul").css("top","0");
						id.attr("clickable","true");
					});
				}
			}
			return false;
		});
		id.find("ul>li>a").live('click',function(){
			if(!$(this).hasClass("active")){
				var alt = $(this).find("img").attr("alt");
				var src = $(this).find("img").attr("src");
				id.find(".main_photo img").attr("src",src);
				id.find(".main_photo p").text(alt);
				id.find("ul>li>a.active>span.round").removeClass("active");
				id.find("ul>li>a").removeClass("active");
				$(this).addClass("active");
			}
		});
		id.find("ul>li>a").live('mouseover',function(){
			$(this).find(">span.round").addClass("active");
			
		});
		id.find("ul>li>a").live('mouseout',function(){
			if(!$(this).hasClass("active")){
				$(this).find(">span.round").removeClass("active");
			}
			
		});
	}else{
		return;
	}
}

/* 
 * 함수명 : slider_list
 * 설명 : 기본  배너 등의 좌우 슬라이딩 처리, 이동 속도와 자동 롤링 처리 포함
 * 사용법  : slider_list('좌우 버튼과 배너 마크업(ul)을 포함하는 영역에 부여한 아이디',이동속도,자동롤링 여부(true,false),자동롤링 인터벌(자동롤링 여부가 false면 불필요));
 */
function slider_list(tgid,speed,autoplay,interval,vertical){
	var id = $("#" + tgid);
	var width = id.find("ul>li").outerWidth() * -1;
	if(vertical == true){
		width = id.find("ul>li").outerHeight() * -1;
	}
	var move = 0;
	var timerSL = tgid + 'time';
	id.attr("clickable","true");
	if(autoplay == true){
		timerSL = setInterval(function(){
			if(window_focus == "focus"){
				id.find('.btn_roll[rel="next"]').trigger('click');
			}else{
				return;
			}
		}, interval);
		id.bind({
			mouseenter: function() {
				clearInterval(timerSL);
			},
			mouseleave: function(){
				timerSL = setInterval(function(){
					if(window_focus == "focus"){
						id.find('.btn_roll[rel="next"]').trigger('click');
					}else{
						return;
					}
				}, interval);
			}
		});
	}
	id.find(".btn_roll").click(function(){
		if(id.attr("clickable") == "true"){
			if($(this).attr("rel") == "prev"){
				id.attr("clickable","false");
				id.find("ul").find(">li:last").clone().prependTo(id.find("ul"));
				if(vertical == true){
					id.find("ul").css("top",width);
					id.find("ul").animate({"top":"0"},speed,function(){
						id.find("ul").find(">li:last").remove();
						id.attr("clickable","true");
					});
				}else{					
					id.find("ul").css("left",width);
					id.find("ul").animate({"left":"0"},speed,function(){
						id.find("ul").find(">li:last").remove();
						id.attr("clickable","true");
					});
				}
				
			}else if($(this).attr("rel") == "next"){
				id.attr("clickable","false");
				if(vertical == true){
					id.find("ul").animate({"top":width},speed,function(){
						id.find("ul").find(">li").eq(0).clone().appendTo(id.find("ul"));
						id.find("ul").find(">li").eq(0).remove();
						id.find("ul").css("top","0");
						id.attr("clickable","true");
					});
				}else{
					id.find("ul").animate({"left":width},speed,function(){
						id.find("ul").find(">li").eq(0).clone().appendTo(id.find("ul"));
						id.find("ul").find(">li").eq(0).remove();
						id.find("ul").css("left","0");
						id.attr("clickable","true");
					});
				}
			}
		}
		return false;
	});
}

/* 
 * 함수명 : slider_dots
 * 설명 : 배너 + dot 네비게이션의   좌우 슬라이딩 처리, 이동 속도와 자동 롤링 처리 포함
 * 사용법  : slider_list('좌우 버튼과 배너 마크업(ul)을 포함하는 영역에 부여한 아이디',이동속도,자동롤링 여부(true,false),자동롤링 인터벌(자동롤링 여부가 false면 불필요));
 */
function slider_dots(tgid,speed,autoplay,interval){
	var id = $("#" + tgid);
	var length = id.find("div>ul>li").length;
	var width = id.find("div>ul>li").outerWidth() * 1;
	if(tgid.indexOf("brMainDots") != -1){
		length = id.find(">div>div>ul>li").length;
		width = id.find(">div>div>ul>li").outerWidth() * 1;
	}
	//console.log(width)
	var count = 0;
	var go = 0;
	var timerSD = tgid + 'time';
	var dot_name = 'ico_';
	var dots = '';
	var dot_root = '/images/portal/adopt/';
	if($("body").hasClass("company")){
		dot_root = '/images/portal/company/';
	}
	if($("body").hasClass("adopt") && tgid == 'rollingVisualT'){
		dot_root = '/images/portal/adopt/';
		dot_name = 'ico_circle_'
	}
	
	
	if(length > 1){
		for(var i=0; i<=length - 1; i++){
			if(i == 0){
				dots += '<a href="#" rel="'+i+'"><img src="'+dot_root+dot_name+'on.png" alt="'+i+'"/></a>\n';
			}else{
				dots += '<a href="#" rel="'+i+'"><img src="'+dot_root+dot_name+'off.png" alt="'+i+'"/></a>\n';
			}
		}
		id.find(".slider_dot>li").html(dots);
	}
	if(autoplay == true){
		timerSD = setInterval(function(){
			if(window_focus == "focus"){
				if(count + 1 == length){
					id.find(".slider_dot>li").find(">a").eq(0).trigger("click");
				}else{
					id.find(".slider_dot>li").find(">a").eq(count + 1).trigger("click");
				}
			}else{
				return;
			}
		}, interval);
		id.bind({
			mouseenter: function() {
				clearInterval(timerSD);
			},
			mouseleave: function(){
				timerSD = setInterval(function(){
					if(window_focus == "focus"){
						if(count + 1 == length){
							id.find(".slider_dot>li").find(">a").eq(0).trigger("click");
						}else{
							id.find(".slider_dot>li").find(">a").eq(count + 1).trigger("click");
						}
					}else{
						return false;
					}
				}, interval);
			}
		});
	}
	id.find(".slider_dot>li>a").click(function(){
		go = $(this).attr("rel");
		//console.log(width)
		if(count != go){
			count = go * 1;
			id.find("div>ul").animate({"left":go * width * -1},speed);
			id.find(".slider_dot").find("img").attr("src",dot_root+dot_name+"off.png");
			$(this).find("img").attr("src",dot_root+dot_name+"on.png");
			//console.log(window_focus);
		}
		return false;
	});
}

/*
 * 함수명 : rolling_banner
 * 설명 : 좌우 이동 끝이 있는 슬라이딩 처리 
 */
function rolling_banner(tgid,speed,autoplay,interval){
	var id = $("#" + tgid);
	var width = id.find("ul>li").outerWidth() * 1;
	var nums = id.find("ul li").length *1;
	var endnum = nums - 2;
	if(tgid == "rollHistory"){
		endnum = nums - 14;
	}else if(tgid == "winner"){
		endnum = nums - 5;
	}else if($(".coverflow_wrap").length > 0){
		endnum = nums - 4;
	}else if(tgid == "rollingHappy"){
		endnum = nums - 6;
	}else if(tgid == "banner_gogo"){
		endnum = nums - 1;
	}
	//console.log(endnum)
	var prev = id.find("a.prev");
	var next = id.find("a.next");
	var count = 0;
	var move = width;
	if(endnum <= 0){
		next.addClass("disable")
	}
	id.attr("clickable","true");
	
	prev.click(function(){
		if(!$(this).hasClass("disable") && id.attr("clickable") == "true"){
			id.attr("clickable","false");
			next.removeClass("disable");
			count = count - 1;
			move = count * width * -1;
			if(count == 0){
				$(this).addClass("disable");
			}
			id.find("ul").animate({"left":move},speed,function(){
				id.attr("clickable","true");
			});
		}
		return false;
	})
	next.click(function(){
		if(!$(this).hasClass("disable") && id.attr("clickable") == "true"){
			id.attr("clickable","false");
			prev.removeClass("disable");
			count = count + 1;
			move = count * width * -1;
			if(count == endnum){
				$(this).addClass("disable");
			}
			id.find("ul").animate({"left":move},speed,function(){
				id.attr("clickable","true");
			});
		}
		return false;
	})
	prev.mouseover(function(){
		if(!$(this).hasClass("disable")){
			$(this).addClass("hover");
		}
	});
	prev.mouseout(function(){
		$(this).removeClass("hover");
	});
	next.mouseover(function(){
		if(!$(this).hasClass("disable")){
			$(this).addClass("hover");
		}
	});
	next.mouseout(function(){
		$(this).removeClass("hover");
	});
}


/*
 * 함수명 : initSnb
 * 설명 : snb 활성 고정 처리
 * 사용법 : 페이지 로드시 default.js에서 1회 호출
 */
function initSnb(){
	var tg = $("#snb");
	var idx1 = 100;
	idx1 = tg.find(">li.on").index();
	//console.log(idx1)
	tg.find(">li").bind({
		mouseover: function() {
			tg.find(">li").removeClass("on");
			$(this).addClass("on");
			tg.find("ul").hide();
			$(this).find(">ul").show();
		}
	});
	tg.bind({
		mouseleave: function(){
			tg.find(">li").removeClass("on");
			tg.find("ul").hide();
			if(idx1 != 100){
				tg.find(">li").eq(idx1).addClass("on");
				tg.find(">li").eq(idx1).find(">ul").show();
			}
		}
	});
	//텝 하단 뎁쓰 메뉴 활성화 없으면 첫번째 활성화
	var index3dep = tg.find("> li.on > ul ").find("a.on").length
	if(index3dep == 0){
		 tg.find("> li.on > ul ").find("li").eq(0).find("a").addClass("on");
	}
	tg.find("li").each(function(index,element){
		if(!$(this).find(">ul").find("li").eq(0).hasClass("first")){
			$(this).find(">ul").find("li").eq(0).addClass("first")
		}
	});
	/*$("#snb > li").mouseover(function(){
	  $("#snb > li").removeClass("on");
	  $(this).addClass("on");
	  $("#snb ul").hide();
	  $("#snb ul").eq($(this).index()).show();
	 })*/
}

function initLnb(){
	var lnb = $("#lnb");
	lnb.find(">li").bind({
		mouseenter : function(){
			$(this).find("div").show();
		},
		mouseleave : function(){
			$(this).find("div").hide();
		}
		
	});
}

/*
 * 함수명 : layerToggle
 * 설명 : 레이어의 show hide 토글 
 * 사용법 : 아이디, 열기 = 1, 닫기 = 0으로 호출 ex)layerToggle('popid',1);
 */
function layerToggle(id,option){
	var id = $("#" + id);
	if(option == 0){
		id.hide();
	}else if(option == 1){
		id.show();
	}
}

function openLangSel(){
	var langSel = $("#selecLang");
	if(langSel.hasClass("select_lang_open")){
		langSel.animate({top:-94},300,function(){
			langSel.removeClass("select_lang_open");	
		});
		
	}else{
		langSel.animate({top:0},300,function(){
			langSel.addClass("select_lang_open");	
		});
	}
}
/*
 * 함수명 :initMyBranchTop
 * 설명 : 나의 지점정보 레이어 높이 조정
 */
function initMyBranchTop(){
	var main = "false";
	if($("body").hasClass("index")){
		main = "true"
		//console.log(main)
	}
	var top = 146;
	var plus = 72; //서브 상단 브랜드 바로가기 32 + 서브 나의 지점찾기 버튼 위쪽 여백 
	if(main == "true"){
		plus = 0;
	}
	var height = ($("#quick").find(">ul.section1").outerHeight() * 1) + ($("#quick").find(">ul.section2").outerHeight() * 1);
	top = height + plus
	$("#brnchLayer").css("top",top);
}

/*
 * 함수명 inputFB
 * 설명 : 홈페 포커스 시 클래스를 추가해줌
 * 사용법 : inputFB('해당 폼 아이디','추가할 클래스')
 */
function inputFB(id,focusclass){
	var tgform = $("#" + id);
	var placeholder = $("#" + id).val(); //기본 노출 값
	//console.log(placeholder);
	tgform.bind({
		focus: function() {
			var thisVal = $(this).val();
			if(thisVal == placeholder){
				$(this).val("");
				$(this).addClass(focusclass);
			}
		},
		blur: function(){
			var thisVal = $(this).val().replace( /(\s*)/g, "" );
			if(thisVal == ""){
				$(this).val(placeholder);
				$(this).removeClass(focusclass);
			}
		}
	});

}

/*
 * 함수명 : rollingSPop
 * 설명 : 인기검색어 롤링 처리
 */
function rollingSPop(id){
	var count = 0;
	var speed = 300;
	var listPop = $("#" + id);
	var height = listPop.find(">li").outerHeight() * -1;
	var timerPop = id + 'time';
	var interval = 2500;
	timerPop = setInterval(function(){
		if(window_focus == "focus"){
			if(listPop.hasClass("closed")){
				listPop.css("top","0");
				count = 0;
			}else{
				count = count + 1;
				listPop.animate({top:count*height},speed);
			}
		}else{
			return;
		}
	}, interval);
	listPop.bind({
		mouseenter: function() {
			clearInterval(timerPop);
			listPop.css("top","0");
			count = 0;
		},
		mouseleave: function(){
			timerPop = setInterval(function(){
				if(window_focus == "focus"){
					if(listPop.hasClass("closed")){
						listPop.css("top","0");
						count = 0;
					}else{
						count = count + 1;
						listPop.animate({top:count*height},speed);
					}
				}else{
					return;
				}
			}, interval);
		}
	});

}

/*
 * 함수명 : imgOverView
 * 설명 : 지점 소개 페이지의 매장이미지 보기 처리. 기존 css로 처리되 부분 수정
 * 사용법 : imgOverView('li 싸고있는 엘리먼트의 id');
 */
function imgOverView(id){
	$("#" + id).find("li").eq(0).addClass("active");
	$("#" + id).find("li > a").bind({
		focus: function() {
			$("#" + id).find("li").removeClass("active");
			$(this).parent("li").addClass("active");
		},
		mouseover: function() {
			$("#" + id).find("li").removeClass("active");
			$(this).parent("li").addClass("active");
		}
	});
}

//callback dom load
$(document).ready(function() {
	//로드시 snb 활성화 함수 호출
	initSnb();
	initLnb();
	//퀵메뉴 > 나의 지점정보관련  레이어 높이 조정
	initMyBranchTop();
	
	//화면 리사이즈시 ui 조정
	/*$(window).resize(function() {
		var docH = $("body").height() * 1;
		//console.log(docH);
	    adjustStyle($(this).width(),docH);
	}).resize();*/
	
	/*$('span[class*="btn_type"]').each(function(index,element){
		var html = $(this).html();
		html = html.replace(/button/gi, 'a')
		alert(html)
	});*/
	
	// 이메일 셀렉트 박스 change 이벤트
	$("#cboEmailDomain").change(function () {
		var thisValue = $(this).val();
		if (thisValue == "직접입력") {
			$(this).prev("input").val("");
		} else {
			$(this).prev("input").val($(this).val());
		}
	});
	
	var selectLang = $("#selecLang span").click(function(){
		openLangSel();
		return false;
	});

	$("#btnLoginQuickHandBill").click(function () {
		window.open("/bc/sso/login.po?returnurl=" + "/bc/member/PbcmMember0001/applyHandBill.do", "login", "width=587, height=503, scrollbars=no");
		return false;
	});

 /*$("#snb > li").mouseover(function(){
  $("#snb > li").removeClass("on");
  $(this).addClass("on");
  $("#snb ul").hide();
  $("#snb ul").eq($(this).index()).show();
 })*/
 
    var excTab = $("#contents").attr("exception_tab");
    if(excTab != "true"){ //일부 컨텐츠 페이지의 탭 동작 예외처리 위해 #contents에 어트리뷰트 exception_tab를 생성하고  true이면 동작 회피
    	//기본형 탭 1
    	$('ul.tabs li a').eq(0).addClass('on');
    	$('div.tab_box_all div.tabbox').hide();
    	$('div.tab_box_all div.tabbox').eq(0).show();
    	/*$('ul.tabs li a').click(function(i){
    		$(this).parent().siblings().children('a').removeClass();
    		$(this).addClass('on');
    	});*/
    	$('ul.tabs li a').live('click',function(){
    		$(this).parent().siblings().children('a').removeClass();
    		$(this).addClass('on');
    	})
    	$('ul.tabs li a').each(function(i){
    		var num = i;
    		$(this).bind('click',function(){
    			$('div.tab_box_all div.tabbox').hide();
    			$('div.tab_box_all div.tabbox').eq(num).show();
    		});
    	});
    	
    	//기본형탭 2
    	$('ul.tabs2 li a').eq(0).addClass('on');
    	$('div.tab_box_all2 div.tabbox2').hide();
    	$('div.tab_box_all2 div.tabbox2').eq(0).show();
    	$('ul.tabs2 li a').click(function(i){
    		$(this).parent().siblings().children('a').removeClass();
    		$(this).addClass('on');
    	});
    	$('ul.tabs2 li a').each(function(i){
    		var num = i;
    		$(this).bind('click',function(){
    			$('div.tab_box_all2 div.tabbox2').hide();
    			$('div.tab_box_all2 div.tabbox2').eq(num).show();
    		});
    	});
    	
    	//기본형 탭 3
    	$('ul.tabs3 li a').eq(0).addClass('on');
    	$('div.tab_box_all3 div.tabbox3').hide();
    	$('div.tab_box_all3 div.tabbox3').eq(0).show();
    	$('ul.tabs3 li a').click(function(i){
    		$(this).parent().siblings().children('a').removeClass("on");
    		$(this).addClass('on');
    		return false;
    	});
    	$('ul.tabs3 li a').each(function(i){
    		//if(!$(this).hasClass("notab")){
			var num = i;
    		$(this).bind('click',function(){
				$('div.tab_box_all3 div.tabbox3').hide();
				$('div.tab_box_all3 div.tabbox3').eq(num).show();
    		});
    		//}
    	});
    	
    	//기본형 탭 4
    	$('ul.tabs4 li a').eq(0).addClass('on');
    	$('div.tab_box_all4 div.tabbox4').hide();
    	$('div.tab_box_all4 div.tabbox4').eq(0).show();
    	$('ul.tabs4 li a').click(function(i){
    		$(this).parent().siblings().children('a').removeClass();
    		$(this).addClass('on');
    	});
    	$('ul.tabs4 li a').each(function(i){
    		var num = i;
    		$(this).bind('click',function(){
    			$('div.tab_box_all4 div.tabbox4').hide();
    			$('div.tab_box_all4 div.tabbox4').eq(num).show();
    		});
    	});
    }
	if($("body").attr("tabEx") != "true"){
		$('ul.busy li a').eq(0).addClass('on');
		$('div.tab_box_all div.tabbox').hide();
		$('div.tab_box_all div.tabbox').eq(0).show();
		$('ul.busy li a').click(function(i){
			$(this).parent().siblings().children('a').removeClass();
			$(this).addClass('on');
		});
		$('ul.busy li a').each(function(i){
			var num = i;
			$(this).bind('click',function(){
				$('div.tab_box_all div.tabbox').hide();
				$('div.tab_box_all div.tabbox').eq(num).show();
			});
		});
	}

	//지점찾기 셀렉트 토글
    $('#h3_0, #h3_1, #h3_2').hide();
    $('a[href=#h3_0], a[href=#h3_1], a[href=#h3_2], a.open_searchlist').click(function(){
        $($(this).attr('href')).toggle();
        if($(this).attr("href") == "#h3_0" || $(this).attr("href") == "#h3_1" || $(this).attr("href") == "#h3_2"){
        	$(this).parent("h3").toggleClass("opened");
        }
        if($(this).find(">img").length > 0){
        	var src = $(this).find(">img").attr("src");
        	if(src.indexOf("btn_select.gif") != -1){
        		$(this).find(">img").attr("src",$(this).find(">img").attr("src").replace("btn_select.gif","btn_select_up.gif"));
        	}else if(src.indexOf("btn_select_up.gif") != -1){
        		$(this).find(">img").attr("src",$(this).find(">img").attr("src").replace("btn_select_up.gif","btn_select.gif"));
        	}
        }
        return false;
    });

/*

	function layer_open(el){
		//$('.layer').addClass('open');
		$('.modal_popup').show();
		var temp = $('#' + el);
		if (temp.outerHeight() < $(document).height() ) temp.css('margin-top', '-'+temp.outerHeight()/2+'px');
		else temp.css('top', '0px');
		if (temp.outerWidth() < $(document).width() ) temp.css('margin-left', '-'+temp.outerWidth()/2+'px');
		else temp.css('left', '0px');
	}
	$('#layer_open').click(function(){
		layer_open('layer1');  //  열고자 하는 것의 아이디를 입력
		return false;
	});
	$('.modal_popup .bg').click(function(){
		$('.modal_popup').hide();
	});
	$('#layer_close').click(function(){
		$('.modal_popup').hide();
		return false;
	});
 */
});

//callback document onload
$(window).load(function(){
	//최조 로드시 퀵메뉴 BG 높이 조정
	var doc_h = $("body").height() * 1;
	$("#quick_bg").height(doc_h);
});


/**우편 번호 검색 팝업 호출
 * inpZipCdId1 : 우편번호 앞 3자리를 받는 필드에 ID값
 * inpZipCdId2 : 우편번호 뒤 3자리를 받는 필드에 ID값
 * inpAddrId1 : 주소를 입력 받는 필드에 ID값
 */

var fncZipSearch = function(inpZipCdId1, inpZipCdId2, inpAddrId1, inpAddrId2){
	window.open('/common/zip/zipCdSearch.do?inpZipCdId1='+inpZipCdId1+'&inpZipCdId2='+inpZipCdId2+'&inpAddrId1='+inpAddrId1+'&inpAddrId2='+inpAddrId2, 'zipSearch', 'width=470, height=570, scrollbars=no, status=no');
};


/**나의지점 선택 관련 **/
var fncMyBrnch = function(){
	var prodObj = $("#brnchLayer");
	prodObj.html("");
	$.get("/bc/member/PbcmMember0001/myBrnch.ajax", function (retunData) {
		prodObj.html(retunData);
	});
	prodObj.show();
};

var fncBrnchClose = function(){
	$("#brnchLayer").hide();
};

var fncLogin = function(){
	window.open("/bc/sso/login.po?returnurl=" + location.href, "login", "width=587, height=503, scrollbars=no");
};


var fncMyBrnchType = function(strBrnchTypeCd){
	$("#inpMySchBrnchTypeCd").val(strBrnchTypeCd);
	$(".myBrnchType").removeClass("on");
	$("#inpMy"+strBrnchTypeCd).addClass("on");
	$("#textMyBrnchType").text($("#inpMy"+strBrnchTypeCd).text());
	if($("#inpMySchRegnCd").val() != ""){
		fncMyBrnchRegnCd($("#inpMySchRegnCd").val());
	}
	$("#Myh3_0").hide();
	fncMyBrnchStoreList($("#inpMySchRegnCd").val());
};

var fncMyBrnchRegnCd = function(strRegnCd){
	$("#inpMySchRegnCd").val(strRegnCd);
	$("#inpMySchStrCd").val("");
	$(".myRegn").removeClass("on");
	$("#inpMy"+strRegnCd).addClass("on");
	$("#Myh3_1").hide();
	if(strRegnCd != ""){
		$("#txtMyRegnNm").text($("#inpMy"+strRegnCd).text());
	}else{
		$("#txtMyRegnNm").text("시,도 선택");		
	}
	fncMyBrnchStoreList(strRegnCd);
};

/** 정렬 데이터 로드 */
var fncMyBrnchStoreList = function (strRegnCd) {
	var strBrnchTypeCd = $("#inpMySchBrnchTypeCd").val();
	$.getJSON("/bc/branch/repStoreList.json", {schRegnCd: strRegnCd, schBrnchTypeCd : strBrnchTypeCd}, function (returnJson) {
		var objData = returnJson.resultList;		
		$("#Myh3_2 li").remove();
		if (typeof objData == "object") {
			$.each(objData, function (intIndex, strValue) {
				var thisData = objData[intIndex];
				var objLi = $("<li/>");
				var objChangeButton = $('<a href="#" id="inpMy'+thisData.brnchCd+'">'+thisData.strNm+'</a>');
				objChangeButton.bind("click", function () {fncMyBrnchChangeStrCd(thisData.brnchCd, thisData.strNm);return false;});
				objLi.append(objChangeButton);
				$("#Myh3_2").append(objLi);
			});
		}
	});

	$("#txtMyStrNm").text("지점 선택");
};

var fncMyBrnchChangeStrCd = function(brnchCd, strNm){
	$("#inpMyBrnchCd").val(brnchCd);
	$("#txtMyStrNm").text(strNm);
	$("#Myh3_2").hide();
};

var fncBrnchTypeShow = function(){
	if($("#Myh3_0").is(':visible')) {
		$("#Myh3_0").hide();
	} else {
		$("#Myh3_0").show();
	}
};

var fncRegnShow = function(){
	if($("#Myh3_1").is(':visible')) {
		$("#Myh3_1").hide();
	} else {
		$("#Myh3_1").show();
	}
};

var fncStoreShow = function(){
	if($("#Myh3_2").is(':visible')) {
		$("#Myh3_2").hide();
	} else {
		$("#Myh3_2").show();
	}
};

var fncMyBrnchSubmit = function(){
	if($("#inpMyBrnchCd").val() == ""){
		alert("지점을 선택해주세요.");
		return;
	}
	$("#myfrmDefault").attr({"action": "/bc/member/PbcmMember0001/insertContents.do", "target": "iframeMyBrnch", "method": "get"}).submit();
};

var fncHandbillView = function(siteCd, brnchCd){
	iframeMyBrnch.location.href="/common/handbill/HandbillLink.do?siteCd="+siteCd+"&brnchCd="+brnchCd;
};

/**나의지점 선택 관련 **/