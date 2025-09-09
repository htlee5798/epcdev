(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(['jquery', 'isOnlyNumber', 'fnJsMsg'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery'), require('isOnlyNumber'), require('fnJsMsg') );
	} else {
		root.calOrderQtyProd = factory(jQuery, root.isOnlyNumber, root.fnJsMsg);
	}
}( window, function($, isOnlyNumber, fnJsMsg) {
	var _calOrderQtyProd = function(obj, p_prodCd, p_gubun, p_area_idx, _minQuantity, _maxQuantity) {
		var minOrderQty	= 0;
	    var maxOrderQty 	= 0;
	    var tmpOrderQty 	= 0;				//현재 수량
	    var prodIdx = "";

	    if(p_area_idx != null && p_area_idx != "" && p_area_idx != "undefined" && p_area_idx != undefined) {
	        prodIdx = "_" + p_area_idx;
	    }

	    minOrderQty 		= _minQuantity || $('#minQty_'+p_prodCd+prodIdx).val();
	    maxOrderQty 		= _maxQuantity || $('#maxQty_'+p_prodCd+prodIdx).val();
	    var in_orderQty = "";
	    if(p_gubun == "incre" || p_gubun=="decre") {
	        if( ($("#aList") != null && $("#aList").length > 0)  && $("#aList").hasClass("active") ) {
	            $(obj).parent().siblings().each(function(index) {
	                if(("#"+this.id) == ('#orderQty_'+p_prodCd+prodIdx)) {
	                    tmpOrderQty = $(this).val();
	                    in_orderQty = this;
	                }
	            });
	        } else {
	            $(obj).siblings().each(function(index) {
	                if(("#"+this.id) == ('#orderQty_'+p_prodCd+prodIdx)) {
	                    tmpOrderQty = $(this).val();
	                    in_orderQty = this;
	                }
	            });
	        }
	    } else {
	        tmpOrderQty = $(obj).val();
	        in_orderQty = obj;
	    }

	    //숫자인지확인
	    if(!isOnlyNumber(tmpOrderQty)) {
	        return;
	    }

	    if(p_gubun=="incre") {
	        $(in_orderQty).val(Number(tmpOrderQty)+1);

	    }else if(p_gubun=="decre") {
	        $(in_orderQty).val(Number(tmpOrderQty) < Number(minOrderQty)? Number(minOrderQty) : Number(tmpOrderQty)-1);
	    } else {
	        //수량 하드수정
	    }

	    //현재수량 재셋팅
	    tmpOrderQty	= $(in_orderQty).val();

	    if(Number(tmpOrderQty) < Number(minOrderQty)) {
	        alert(fnJsMsg(window.view_messages.error.productOrderQty, minOrderQty, maxOrderQty));
	        $(in_orderQty).val(minOrderQty);
	        return;
	    } else if(Number(tmpOrderQty) > Number(maxOrderQty)) {
	        alert(fnJsMsg(window.view_messages.error.productOrderQty, minOrderQty, maxOrderQty));
	        $(in_orderQty).val(maxOrderQty);
	        return;
	    }
	};
	
	return _calOrderQtyProd;
}));