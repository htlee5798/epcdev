(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.callBack_$fnDeleteItem = factory();
	}
}( window, function() {
	var _callBack_$fnDeleteItem = function( response ) {

		var jsonData = eval( "(" + response + ")" );
		var param = jsonData[0];

		if(param.ERR_NO != "0"){
			alert(param.ERR_MSG);
			return;
		}//if

		if(param.ERR_NO == "0"){
			alert("삭제 되었습니다.");
			if(param.periDeliYn == "Y"){
				location.replace(_LMAppUrlM+"/mobile/peribasket/basketList.do");
			}else{
				location.replace(_LMAppUrlM+"/mobile/basket/PMWMMAR0003.do");
			}
		}//if
	};
	
	return _callBack_$fnDeleteItem;
}));