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
$(function(){
	$("#btnTop").click(function(){
		$("html, body").stop().animate({scrollTop : 0}, 500);
	});
	$("#btnTop").keypress(function (e){
		if (e.keyCode == 13) {
			$("html, body").stop().animate({scrollTop : 0}, 500);
		}
	});
	if($("#h_loading").length == 0){
		var tmpHtml = "";
		tmpHtml  = "<div id='h_loading' style='display:none;'>"; 
		tmpHtml += "	<img id='ajax_load_type_img' src='/js/jquery/res/loading.gif' style='position:relative; left:120px;top:30px;'>";
		tmpHtml += "</div>";
		$(tmpHtml).prependTo("body");
	}

	$(tmpHtml)
	.ajaxStart(function() {
		$("#h_loading").dialog({
			modal: true,
			open:function(){
				$(this).parents(".ui-dialog").css({background: "transparent", borderWidth: "0px"})
				.find(".ui-dialog-titlebar").remove();
				$(".ui-widget-overlay").css({opacity:0.0});
			}
		});
	})
	.ajaxStop(function() {
		$("#h_loading").dialog("close");
	});

	$("form")
	.submit(function(event) {
		$("#h_loading").dialog({
			modal: true,
			open:function(){
				$(this).parents(".ui-dialog").css({background: "transparent", borderWidth: "0px"})
				.find(".ui-dialog-titlebar").remove();
				$(".ui-widget-overlay").css({opacity:0.0});
			}
		});
//		event.preventDefault();
	});
	$("input.numberOnly").keyup(function(){
		$(this).val($(this).val().replace(/[^\d]/g, ''));
	});
	$("input.enableEmail").keyup(function(){
		$(this).val($(this).val().replace(/[^\w_.-]/g, ''));
	});

});