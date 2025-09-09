/***************************/
//@Author: Adrian "yEnS" Mato Gondelle
//@website: www.yensdesign.com
//@email: yensamg@gmail.com
//@license: Feel free to use it, but keep this credits please!					
/***************************/

//SETTING UP OUR POPUP
//0 means disabled; 1 means enabled;
var popupStatus = 0;

//loading popup with jQuery magic!
function loadPopup(contractKind){
	//loads popup only if it is disabled
	if(popupStatus==0 && contractKind =='popupContact'){
		$("#backgroundPopup").css({
			"opacity": "0.7"
		});
		$("#backgroundPopup").fadeIn("slow");
		$("#popupContact").fadeIn("slow");
		popupStatus = 1;
	}
	
	if(popupStatus==0 && contractKind =='popupDiv'){
		$("#backgroundPopup").css({
			"opacity": "0.7"
		});
		$("#backgroundPopup").fadeIn("slow");
		$("#popupDiv").fadeIn("slow");
		popupStatus = 1;
	}
}

//disabling popup with jQuery magic!
function disablePopup(contractKind){
	//disables popup only if it is enabled
	if(popupStatus==1 && contractKind =='popupContact'){
		$("#backgroundPopup").fadeOut("slow");
		$("#popupContact").fadeOut("slow");
		popupStatus = 0;
	}
	
	if(popupStatus==1 && contractKind =='popupDiv'){
		$("#backgroundPopup").fadeOut("slow");
		$("#popupDiv").fadeOut("slow");
		popupStatus = 0;
	}
}

//centering popup
function centerPopup(contractKind){
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = null;
	var popupWidth = null;
	
	if(contractKind =='popupContact') {
		popupHeight = $("#popupContact").height();
		popupWidth = $("#popupContact").width() ;
	} 
	
	//centering
	if(contractKind =='popupContact'){
		$("#popupContact").css({
			"position": "absolute",
			"top": windowHeight/2-popupHeight/2,
			"left": windowWidth/2-popupWidth/2
		});
	}
	
	
	if(contractKind =='popupDiv') {
		popupHeight = $("#popupDiv").height();
		popupWidth = $("#popupDiv").width();
	}
	
	if(contractKind =='popupDiv'){
		$("#popupDiv").css({
			"position": "absolute",
			"top": (windowHeight/2-popupHeight/2 + (document.documentElement.scrollTop || document.body.scrollTop)),
			"left": windowWidth/2-popupWidth/2
		});
	}
	//only need force for IE6
	
	$("#backgroundPopup").css({
		"height": windowHeight
	});
	
}


//CONTROLLING EVENTS IN jQuery
$(document).ready(function(){
				
	//CLOSING POPUP
	//Click the x event!
	$("#popupContactClose").click(function(){
		disablePopup('popupDiv');
	});

});