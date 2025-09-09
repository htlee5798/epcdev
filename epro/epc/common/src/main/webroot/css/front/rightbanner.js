jQuery(function($){
	var eventNum=0,
		$eventWrap=$('.RecoEvent'),
		$eventLi=$('.RecoEvent ul li');

	if($eventLi.length>1){
		$eventWrap.append('<input type="button" value="이전" class="btn_prev"/><input type="button" value="다음" class="btn_next"/><em class="lctpage"></em>')
	}

	function bannerEvent(num){

		if(num=='prev'){
			eventNum--;
			if(eventNum<0){
				eventNum=$eventLi.length-1;
			}
		}else if(num=='next'){
			eventNum++;
			if(eventNum>$eventLi.length){
				eventNum=0;
			}
		}

		if(eventNum>$eventLi.length-1){
			eventNum=0;
		}

		$eventLi.hide();
		$eventLi.eq(eventNum).show();
		$('.lctpage').html(eventNum+1+"<span>/"+$eventLi.length+"</span>");
		eventBanner=setTimeout(function(){eventNum++;bannerEvent();},5000);
	}

	$eventLi.mouseenter(function(){
		 clearTimeout(eventBanner);
	});
	$eventLi.mouseleave(function(){
		 eventBanner=setTimeout(function(){eventNum++;bannerEvent();},5000);
	});

	function btnEventBanner(btnPrev,btnNext){
		$(btnPrev).click(function(){
			clearTimeout(eventBanner);
			bannerEvent('prev');
		});

		$(btnNext).click(function(){
			clearTimeout(eventBanner);
			bannerEvent('next');
		});
	}

		bannerEvent();
		btnEventBanner(
			btnPrev='.RecoEvent .btn_prev',
			btnNext='.RecoEvent .btn_next'
		);


});
