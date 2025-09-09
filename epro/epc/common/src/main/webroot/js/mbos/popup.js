var popupidCalendar;

$(document).ready(function () {
	
    // 팝업 시작
	var popuptopmargin;
	var popupleftmargin;
	var popupid;
	
	// Here we will write a function when link click under class popup				   
	$('.popup_1st').click(function() {
		
		// Here we will describe a variable popupid which gets the
		// rel attribute from the clicked link							
		popupid = $(this).attr('rel');
		popupidCalendar = $(this).prev().attr('id') ;
		// Now we need to popup the marked which belongs to the rel attribute
		// Suppose the rel attribute of click link is popuprel then here in below code
		// #popuprel will fadein
		$('#' + popupid).fadeIn();
		
		// append div with id fade into the bottom of body tag
		// and we allready styled it in our step 2 : CSS
		$('body').append('<div id="fade"></div>');
		$('#fade').css('opacity','.80').fadeIn();
		$(window).bind('touchmove', function(e){e.preventDefault()});//파업창 및 스크롤 정지
		
		// Now here we need to have our popup box in center of 
		// webpage when its fadein. so we add 10px to height and width 
		popuptopmargin = ($('#' + popupid).height() + 10) / 2;
		popupleftmargin = ($('#' + popupid).width() + 0) / 2;
		
		
		// Then using .css function style our popup box for center allignment
		$('#' + popupid).css({
		'margin-top' : -popuptopmargin,
		'margin-left' : -popupleftmargin
		});
	});
	
	// Here we will write a function when link click under class popup				   
	$('.popup_2nd').click(function() {
		
		// Here we will describe a variable popupid which gets the
		// rel attribute from the clicked link							
		popupid = $(this).attr('rel');
		
		
		// Now we need to popup the marked which belongs to the rel attribute
		// Suppose the rel attribute of click link is popuprel then here in below code
		// #popuprel will fadein
		$('#' + popupid).fadeIn();
		
		// append div with id fade into the bottom of body tag
		// and we allready styled it in our step 2 : CSS
		$('body').append('<div id="fade2"></div>');
		$('#fade2').css('opacity','.80').fadeIn();
		
		// Now here we need to have our popup box in center of 
		// webpage when its fadein. so we add 10px to height and width 
		popuptopmargin = ($('#' + popupid).height() + 10) / 2;
		popupleftmargin = ($('#' + popupid).width() + 0) / 2;
		
		
		// Then using .css function style our popup box for center allignment
		$('#' + popupid).css({
		'margin-top' : -popuptopmargin,
		'margin-left' : -popupleftmargin
		});
	});
	
	// Here we will write a function when link click under class popup				   
	$('.popup_3nd').click(function() {
		
		// Here we will describe a variable popupid which gets the
		// rel attribute from the clicked link							
		popupid = $(this).attr('rel');
		
		
		// Now we need to popup the marked which belongs to the rel attribute
		// Suppose the rel attribute of click link is popuprel then here in below code
		// #popuprel will fadein
		$('#' + popupid).fadeIn();
		
		// append div with id fade into the bottom of body tag
		// and we allready styled it in our step 2 : CSS
		$('body').append('<div id="fade3"></div>');
		$('#fade3').css('opacity','.20').fadeIn();
		
		// Now here we need to have our popup box in center of 
		// webpage when its fadein. so we add 10px to height and width 
		popuptopmargin = ($('#' + popupid).height() + 10) / 2;
		popupleftmargin = ($('#' + popupid).width() + 0) / 2;
		
		
		// Then using .css function style our popup box for center allignment
		$('#' + popupid).css({
		'margin-top' : -popuptopmargin,
		'margin-left' : -popupleftmargin
		});
	});
	
	//롱터치 팝업
	var isTapHold = false;
	$('.long-test').on('taphold', function(e){
		isTapHold = true;
		popupid = $(this).attr('rel');
		// Now we need to popup the marked which belongs to the rel attribute
		// Suppose the rel attribute of click link is popuprel then here in below code
		// #popuprel will fadein
		$('#' + popupid).fadeIn();
		
		// append div with id fade into the bottom of body tag
		// and we allready styled it in our step 2 : CSS
		$('body').append('<div id="fade"></div>');
		$('#fade').css('opacity','.80').fadeIn();
		$(window).bind('touchmove', function(e){e.preventDefault()});//파업창 및 스크롤 정지
		
		// Now here we need to have our popup box in center of 
		// webpage when its fadein. so we add 10px to height and width 
		popuptopmargin = ($('#' + popupid).height() + 10) / 2;
		popupleftmargin = ($('#' + popupid).width() + 0) / 2;
		
		
		// Then using .css function style our popup box for center allignment
		$('#' + popupid).css({
		'margin-top' : -popuptopmargin,
		'margin-left' : -popupleftmargin
		});
	});
	
	$('.long-test').on('tap', function(e){
		if(!isTapHold){
		  //TODO tap events
		}else{
		  isTapHold = false;
		}
	});
	
	//$(window).resize(function(e) {
	$(window).on("resize", function() {
        popuptopmargin = ($('#' + popupid).height() + 10) / 2;
		popupleftmargin = ($('#' + popupid).width() + 0) / 2;
		
		$('#' + popupid).css({
			'margin-top' : -popuptopmargin,
			'margin-left' : -popupleftmargin
		});
    }).resize();

	// Now define one more function which is used to fadeout the 
	// fade layer and popup window as soon as we click on fade layer
	$('.close, .btn_close').click(function() {
		
		// Add markup ids of all custom popup box here 						  
		$('#fade , #popup_loading, #popup_change, #popup_store, #popup_date, #popup_country, #popup_partner , #popup_product, #popup_parcel, #popup_delivery ').fadeOut();
		$(window).unbind('touchmove');//파업창 및 스크롤 정지해지	
		return false;
		
	});
	$('.close, .btn_close2').click(function() {
		
		// Add markup ids of all custom popup box here 						  
		$('#fade, #fade2 ,#popup_basic').fadeOut();
		$(window).unbind('touchmove');//파업창 및 스크롤 정지해지	
		return false;
		
	});
	$('.close, .btn_error_ok').click(function() {	
		// Add markup ids of all custom popup box here 						  
		$('#fade2 ,#fade3, #popup_confirm, #popup_error').fadeOut();
		return false;
		
	});
	
	// 버튼close 로 상위 div를 찾아 close 처리 
	$(".btn_close").click(function() {
		var topId = $(this).parent("div").parent("div").parent("div").attr("id");
		
		$('#fade, #'+topId).fadeOut();
		$(window).unbind('touchmove');//파업창 및 스크롤 정지해지	
		return false;
	});

	// 팝업 끝

});

function layer_popup_open(popup_id,fade_id){
	$('#' + popup_id).fadeIn();
	
	$('body').append('<div id="' + fade_id +'"></div>');
	if(fade_id=='fade'){
		$('#fade').css('opacity','.80').fadeIn();
		$(window).bind('touchmove', function(e){e.preventDefault()});//파업창 및 스크롤 정지
	}else{
		$('#' + fade_id).css('opacity','.20').fadeIn();
	}	
	
	popuptopmargin = ($('#' + popup_id).height() + 10) / 2;
	popupleftmargin = ($('#' + popup_id).width() + 0) / 2;
	
	$('#' + popup_id).css({
	'margin-top' : -popuptopmargin,
	'margin-left' : -popupleftmargin
	});
}