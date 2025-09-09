(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery','fnAddDay'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ), require('fnAddDay') );
	} else {
		root.fnSetDate = factory(jQuery, root.fnAddDay);
	}
}( window, function($,fnAddDay) {
	var _fnSetDate = function( setGbn ) {
		var dt = new Date();
		var todayYYYY =  dt.getFullYear().toString();
		var todayMM =  (dt.getMonth() + 1).toString();
		var todayDD =  dt.getDate().toString();
		
		toYYYY = todayYYYY;
		toMM = lpad(todayMM, 2, "0");
		toDD = lpad(todayDD, 2, "0");
		
		if(setGbn == "today"){
			fromYYYY = todayYYYY;
			fromMM = lpad(todayMM, 2, "0");
			fromDD = lpad(todayDD, 2, "0");;
		}else if(setGbn == "3day"){
			fromDt = fnAddDay(dt, -3);
			fromYYYY = fromDt.getFullYear().toString();
			fromMM = lpad((fromDt.getMonth() + 1).toString(), 2, "0");
			fromDD = lpad(fromDt.getDate().toString(), 2, "0");;
		}else if(setGbn == "7day"){
			fromDt = fnAddDay(dt, -7);
			fromYYYY = fromDt.getFullYear().toString();
			fromMM = lpad((fromDt.getMonth() + 1).toString(), 2, "0");
			fromDD = lpad(fromDt.getDate().toString(), 2, "0");
		}else if(setGbn == "15day"){
			fromDt = fnAddDay(dt, -15);
			fromYYYY = fromDt.getFullYear().toString();
			fromMM = lpad((fromDt.getMonth() + 1).toString(), 2, "0");
			fromDD = lpad(fromDt.getDate().toString(), 2, "0");
		}else if(setGbn == "1month"){
			fromDt = fnAddDay(dt, -30);
			fromYYYY = fromDt.getFullYear().toString();
			fromMM = lpad((fromDt.getMonth() + 1).toString(), 2, "0");
			fromDD = lpad(fromDt.getDate().toString(), 2, "0");
		}else if(setGbn == "3month"){
			fromDt = fnAddDay(dt, -90);
			fromYYYY = fromDt.getFullYear().toString();
			fromMM = lpad((fromDt.getMonth() + 1).toString(), 2, "0");
			fromDD = lpad(fromDt.getDate().toString(), 2, "0");
		}
		
		$("#selectFromYear option[value='"+ fromYYYY +"']").attr("selected", "selected");
		$("#selectFromMonth option[value='"+ fromMM +"']").attr("selected", "selected");
		fnSetDay('From');
		$("#selectFromDay option[value='"+ fromDD +"']").attr("selected", "selected");
		
		$("#selectToYear option[value='"+ toYYYY +"']").attr("selected", "selected");
		$("#selectToMonth option[value='"+ toMM +"']").attr("selected", "selected");
		fnSetDay('To');
		$("#selectToDay option[value='"+ toDD +"']").attr("selected", "selected");
		
		$("#todayEnable").removeClass();
		$("#3dayEnable").removeClass();
		$("#7dayEnable").removeClass();
		$("#15dayEnable").removeClass();
		$("#1monthEnable").removeClass();
		$("#3monthEnable").removeClass();
		
		$("#"+ setGbn +"Enable").addClass("on");
		$("#setDateArrange").val(setGbn);
	};
	
	return _fnSetDate;
}));