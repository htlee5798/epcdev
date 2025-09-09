(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.callBack_$fnAddWishList = factory();
	}
}( window, function() {
	var _callBack_$fnAddWishList = function(response) {
		var jsonData = eval( "(" + response + ")" );
	  	var param = jsonData[0]; if(param.ERR_NO != "0"){alert(param.ERR_MSG); return;}

	  	if(param.ERR_NO == "0"){
	  		alert(param.SHOW_MSG);
	  	}
	};
	
	return _callBack_$fnAddWishList;
}));