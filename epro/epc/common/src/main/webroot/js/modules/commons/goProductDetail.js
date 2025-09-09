(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.goProductDetail = factory(jQuery);
	}
}( window, function($) {
	var _goProductDetail = function( cateId,prodCd,popupYn,socialSeq,smartOfferClickUrl ) {
		var dpCode = "";
		if(typeof(smartOfferClickUrl) != "undefined" && smartOfferClickUrl != "") {
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
		window.location.href = $.utils.config( 'LMAppUrlM' ) + "/mobile/cate/PMWMCAT0004_New.do?CategoryID="+cateId+"&ProductCD="+prodCd+"&socialSeq="+socialSeq + (dpCode != "" ? dpCode:"");
	};
	
	return _goProductDetail;
}));