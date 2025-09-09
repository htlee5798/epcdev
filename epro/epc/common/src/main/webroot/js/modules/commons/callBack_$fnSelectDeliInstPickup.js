(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.callBack_$fnSelectDeliInstPickup = factory();
	}
}( window, function() {
	var _callBack_$fnSelectDeliInstPickup = function( response ) {

		var jsonData = eval( "(" + response + ")" );
		var param = jsonData[0];

		if(param.ERR_NO != "0"){
			alert(param.ERR_MSG);
			return;
		}//if

		if(param.ERR_NO == "0"){

			window.deliInstPickupList(jsonData);
		}//if
	};
	
	return _callBack_$fnSelectDeliInstPickup;
}));