(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.topBnrCall = factory(jQuery);
	}
}( window, function($) {
	var _topBnrCall = function(cateCd,bnrId) {
		var elm = $('#' + bnrId);
		if(elm.length < 1) {
			return;
		}

		$.ajax({
	        timeout: 1000,
	        data		: {'cateCd' : cateCd},
	        url: "/mobile/ajax/getMobileTopBnr.do",
	        dataType: "Json",
	        //async : false ,
	        success: function(data) {
	        	var bnrInfo = "";
	        	var target = "";

	        	if (data!=  null) {

	        		elm.empty();

	    			if(data.LINK_METH_CD == "02"){
	    				target = 'target="_new"';
	    			}

	    			bnrInfo = '<a href="'+ data.LINK_URL +'" class="banner" '+ target +'><img src="'+ data.IMG_BNR +'" alt="'+ data.ALT+'"></a>';
	    			elm.html(bnrInfo);

	    		}else{ // 등록된 배너가 없을때는 안보이도록 처리 (앱에서 여백이 생기는 현상 있음)
	    			elm.css("display","none");
				}

	        },
	        error: function(xhr) {

	        }
		});
	};
	
	return _topBnrCall;
}));