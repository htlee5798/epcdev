(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.isMember = factory( jQuery );
	}
}( window, function( $ ) {
	var _isMember = function() {
		params = {};

		var bMember;

		$.ajax({
	        type       : "POST" ,
	        url        : _LMAppSSLUrlM+"/member/ismember.do" ,
	        data       : params ,
	        async      : false ,
	        dataType   : "text" ,
	        timeOut    : (9 * 1000) ,
	        success    : function(response){
	         	var jsonData = eval( "(" + response + ")" );
	         	bMember = ( jsonData.isMember == 'Y' );
	        } ,
			cache      : true ,
	        error      : callSysErr
	    });

		return bMember;
	};
	
	return _isMember;
}));