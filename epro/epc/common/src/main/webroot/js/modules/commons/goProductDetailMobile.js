(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.goProductDetailMobile = factory();
	}
}( window, function() {
	var _goProductDetailMobile = function( cateId,prodCd,popupYn,socialSeq,siteLoc,smartOfferClickUrl ) {
		var dpCode = "";
		if(typeof(smartOfferClickUrl) != "undefined" && smartOfferClickUrl != "") {
			// 스마트오퍼를 통한 상품을 클릭 시 logging 을 위해 다음을 호출하고, 결과는 받을 필요 없다. itemSetId, scnId
			try {
				var codeList = ["dpId", "itemSetId", "scnId"];
				var clickParams = smartOfferClickUrl.substring(smartOfferClickUrl.indexOf("?") + 1).split("&");
				var curParams;
				for (var i = 0 ; i < codeList.length ; i++ ) {
					curParams = $.grep(clickParams, function(obj) {
						return obj.indexOf(codeList[i] + "=") >= 0;
					});
					if (curParams != null && curParams.length > 0) {
						if (codeList[i] === "dpId") {
							dpCode += ("&" + "dp=" + curParams[0].split("=")[1]);
						}
						else {
							dpCode += ("&" + curParams[0]);
						}
					}
				}
				$.get(smartOfferClickUrl);
			}
			catch (e) {}
		}

		document.location.href = $.utils.config( 'LMAppUrlM' ) + "/mobile/cate/PMWMCAT0004_New.do?CategoryID="+cateId+"&ProductCD="+prodCd+"&socialSeq="+socialSeq +"&SITELOC=" + siteLoc+  (dpCode != "" ? dpCode:"");
	};
	
	return _goProductDetailMobile;
}));