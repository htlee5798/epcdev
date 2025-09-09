
/* 쿠키 설정 */
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
/*// 쿠키 설정 */

//callback dom load
$(document).ready(function() {
						   
    var excTab = $("#contents").attr("exception_tab");
	
    if(excTab != "true"){ //일부 컨텐츠 페이지의 탭 동작 예외처리 위해 #contents에 어트리뷰트 exception_tab를 생성하고  true이면 동작 회피


		$('ul.tabs li').eq(0).addClass('on');
    	$('div.tab_box_all div.tabbox').hide();
    	$('div.tab_box_all div.tabbox').eq(0).show();
    	/*$('ul.tabs li a').click(function(i){
    		$(this).parent().siblings().children('a').removeClass();
    		$(this).addClass('on');
    	});*/
    	$('ul.tabs li').bind('click',function(){
    		$(this).parent().siblings().children('li').removeClass();
    		$(this).addClass('on');
    	})
    	$('ul.tabs li').each(function(i){
    		var num = i;
    		$(this).bind('click',function(){
    			$('div.tab_box_all div.tabbox').hide();
    			$('div.tab_box_all div.tabbox').eq(num).show();
    		});
    	});
		
		$('input.input_label1').eq(0).addClass('on');
    	$('div.tab_box_all div.tabbox2').hide();
    	$('div.tab_box_all div.tabbox2').eq(0).show();
		
    	$('input.input_label1').each(function(i){
    		var num = i;
    		$(this).bind('click',function(){
    			$('div.tab_box_all div.tabbox2').hide();
    			$('div.tab_box_all div.tabbox2').eq(num).show();
    		});
    	});
		$('label.input_label1').eq(0).addClass('on');
    	$('div.tab_box_all div.tabbox2').hide();
    	$('div.tab_box_all div.tabbox2').eq(0).show();
		
    	$('label.input_label1').each(function(i){
    		var num = i;
    		$(this).bind('click',function(){
    			$('div.tab_box_all div.tabbox2').hide();
    			$('div.tab_box_all div.tabbox2').eq(num).show();
    		});
    	});
		
		$('input.input_label2').eq(0).addClass('on');
    	$('div.tab_box_all div.tabbox3').hide();
    	$('div.tab_box_all div.tabbox3').eq(0).show();
		
    	$('input.input_label2').each(function(i){
    		var num = i;
    		$(this).bind('click',function(){
    			$('div.tab_box_all div.tabbox3').hide();
    			$('div.tab_box_all div.tabbox3').eq(num).show();
    		});
    	});
		$('label.input_label2').eq(0).addClass('on');
    	$('div.tab_box_all div.tabbox3').hide();
    	$('div.tab_box_all div.tabbox3').eq(0).show();
		
    	$('label.input_label2').each(function(i){
    		var num = i;
    		$(this).bind('click',function(){
    			$('div.tab_box_all div.tabbox3').hide();
    			$('div.tab_box_all div.tabbox3').eq(num).show();
    		});
    	});
    	
	}
	
	//이용약관 모두 동의
	$("#allCheck").click(function(){      
	
		 // name이 check_all 인 체크박스가 checked 가 되어 있다면
	
		 if ( $("#no_agree").is(":checked") ){     
	
			  // class는 box_class 인 체크박스의 속성 checked는 checked이다*/
			  $("[id=agree]").attr("checked","checked");    
		 }
		/*
		 else{      // 그렇지 않으면
			  // class는 box_class인 체크박스의 속성 checked 는 "" 공백이다
			  $("[id=no_agree]").attr("checked","");      
		 }*/
	});
	
	//아이디 전환 or 이중 회원 이용약관 모두동의
	$("#allCheck2").click(function(){      
	// class는 box_class 인 체크박스의 속성 checked는 checked이다*/
	  $("[id=agree]").attr("checked","checked");
	});
	
	
	//이용약관 정보 수신동의
	$("#all_info").click(function(){
		// name이 check_all 인 체크박스가 checked 가 되어 있다면
		if ( $("#all_info").is(":checked") ){
		  // class는 box_class 인 체크박스의 속성 checked는 checked이다
		  $(".checkBox").attr("checked",true);
		}
	});
	
	$("[id=sel_info]").click(function(){
		// name이 check_all 인 체크박스가 checked 가 되어 있다면
		if ( $("[id=sel_info]").is(":checked") ){
		  // class는 box_class 인 체크박스의 속성 checked는 checked이다
		  $(".checkBox").attr("checked",false); 
		}
	});
	
});

