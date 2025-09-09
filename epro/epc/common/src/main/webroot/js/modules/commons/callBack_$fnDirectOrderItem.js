(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery','initTForm'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('initTForm') );
	} else {
		root.callBack_$fnDirectOrderItem = factory( jQuery, root.initTForm );
	}
}( window, function( $, initTForm ) {
	var _callBack_$fnDirectOrderItem = function( response ) {
		var jsonData = eval( "(" + response + ")" );
		var param = jsonData[0];

		if(param.ERR_NO != "0"){
			alert(param.ERR_MSG);
			return;
		}//if

		if(param.ERR_NO == "0"){
			initTForm();
			var deliTypeList = param.deliTypeCd.split("[!@!]");
			$('#divTemp').append(genDomInput("bsketNo", param.bsketNo));
			$('#divTemp').append(genDomInput("basketType", param.basketType));
			$('#divTemp').append(genDomInput("deliTypeCd", param.deliTypeCd));
			$('#divTemp').append(genDomInput("bsketDivnCd", "02"));
			$('#tForm').attr('action',_LMAppUrlM+'/mobile/basket/insertPreOrder.do').submit();
		}//if
	};
	
	return _callBack_$fnDirectOrderItem;
}));