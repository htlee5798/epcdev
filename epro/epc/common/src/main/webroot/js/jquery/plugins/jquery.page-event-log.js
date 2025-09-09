(function( $, window, document, undefined ) {
	'use strict';
	
	var pageDomain = {
			index : '메인',
			category: '카테고리',
			todayhot : '오늘HOT콕',
			plan : '기획전',
			best : '베스트',
			delivery : '정기배송',
			event : '이벤트&쿠폰',
			trend : '트렌드',
			recipe : '요리왕 장보고',
			product : '상품 상세',
			special : '전문관',
			mymart : '마이롯데'
		},
		urlRegex = /(?:http[s]*)\:\/\/(?:[^.]+)\.lottemart\.com\/([^/\n.]*)/,
		urlExec;
	
	(function init() {
		$(setLogLabel);
	})();
	
	function setLogLabel() {
		//if($.utils.config('ReqURL') == '') return false;
		urlExec = urlRegex.exec( location.href );
		
		var _pageTitle = ($.utils.config('pageTitle') === undefined) ? '' : $.utils.config('pageTitle');

		//set pageDomain
		if(urlExec !== null) {
            $.utils.config('pageDomain', pageDomain[urlExec[1]] || urlExec[1]);
        }
	}
	

	$.eventLog = {
		ga: function(log) {
			if( ga ) {
				ga( 'send', 'event', log );
				//console.log(log);
			}
		}
	};
	
})( jQuery, window, document );