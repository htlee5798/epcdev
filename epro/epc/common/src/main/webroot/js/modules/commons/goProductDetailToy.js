(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.goProductDetailToy = factory();
	}
}( window, function() {
	var _goProductDetailToy = function( cateId,prodCd ) {
		if( cateId == undefined || cateId == "" || prodCd == undefined || prodCd == "" ){
	        alert('잘롯된 상품정보 입니다.');
	        return false;
	    }
	    
	    document.location.href = "http://m.toysrus.lottemart.com/mobile/cate/ProductDetail.do?CategoryID="+cateId+"&ProductCD="+prodCd;
	};
	
	return _goProductDetailToy;
}));