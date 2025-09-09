"use strict";


//실행
function goMyMart(){
	var form = $('#myMart').get(0);
	form.action = '/mymart/managePeriSchd.do';
	form.submit();
}

//실행
function goHome(){
	var form = $('#myMart').get(0);
	form.action = '/index.do';
	form.submit();
}


$(function() {
});
