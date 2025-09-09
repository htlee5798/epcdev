/*
// LOTTEMART MOBILE MAIN
// 2014 03 07_MZ
*/

function tab1(tabName, tabBox, active){
	if(active==undefined){
		active=0
	}

	$(tabName).click(function(){
		goTab = $($(this).attr('href'));
		$(tabName).removeClass('on');
		$(this).addClass('on');
		$(tabBox).hide();
		$(goTab).show()
		return false;
	});
	$(tabName).eq(active).trigger('click');
};

jQuery(function($){
	var loct = location.href;
	var loctNum = loct.split("?tab=");
	tab1('.gnbWrap a',"#contentWrap > section",loctNum[1]);
});

