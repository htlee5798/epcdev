/* gnb */
var setGnb	= function (intIndex) {	
	$("#gnb").find("li").eq(intIndex).addClass("active");
	if (intIndex > 3){	
		setGnbMove("Right");
	} else {
		setGnbMove("Left");
	}
}

var setGnbMove = function (strType) {
	if (strType == "Left"){
		$("#menu1").show();
		$("#menu2").hide();
		$("nav#gnb p.first img").attr("src", "/images/mobile/btn_left.png");
		$("nav#gnb p.last img").attr("src", "/images/mobile/btn_right.png");
		return false;
	} else {
		$("#menu1").hide();
		$("#menu2").show();	
		$("nav#gnb p.first img").attr("src", "/images/mobile/btn_left.png");
		$("nav#gnb p.last img").attr("src", "/images/mobile/btn_right.png");
		return false;
	}
}
