(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.callBack_$fnCheckRecalculation = factory();
	}
}( window, function() {
	var _callBack_$fnCheckRecalculation = function( response ) {

		var jsonData = eval( "(" + response + ")" );
		var param = jsonData[0];

		if(param.ERR_NO != "0"){
			alert(param.ERR_MSG);
			return;
		}//if

		if(param.ERR_NO == "0"){
			window.checkRecalculation && window.checkRecalculation(jsonData);
		}//if
	};
	
	return _callBack_$fnCheckRecalculation;
}));