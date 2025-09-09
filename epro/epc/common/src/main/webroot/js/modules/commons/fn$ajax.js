(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ), require( 'callSysErr' ) );
	} else {
		root.fn$ajax = factory(jQuery, root.callSysErr );
	}
}( window, function( $ ) {
	var _fn$ajax = function( in_url, in_send_json, in_call_function, async_type, dataType ) {
		if( typeof async_type == "undefined") async_type = true;
	    
	    _com_ajax_async_mode_j$Object = async_type;

	    var call_back_ = eval("callBack_$" + in_call_function);
	    
	    if(dataType == null) {dataType = 'text';}
	    
	    $.ajax({
	        type       : "POST" ,
	        url        : in_url ,
	        data       : in_send_json ,
	        async      : true ,
	        dataType   : dataType ,
	        timeOut    : (9 * 1000) ,
	        success    : call_back_ ,
			cache      : async_type
	    }); 
	};
	
	return _fn$ajax;
}));