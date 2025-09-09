/**
 * 
 */
define( function() {
	function _logout( returnUrl, linkUrl ) {
		var url;
		if ( location.protocol === "http:" ) {
			url = $.utils.config( 'LMMembersAppUrl' );
		}
		else {
			url = $.utils.config( 'LMMembersAppSSLUrl' );
		}
		
		if( $.utils.config( 'LMFamilyLoginYn' ) == "Y" ) {
			url += (!linkUrl ? "/imember/login/ssoLogoutPop.do" : linkUrl) + "?sid="+ $.utils.config( 'SID_NM_MARTMALL' ) +"&kind=f&SITELOC=AA005&returnurl=" + encodeURIComponent(returnUrl) + ( $.utils.config( 'LMLocalDomain' ) === "true" ? "&mode=DEV" : "");
		} else {
			url += (!linkUrl ? "/imember/login/ssoLogoutPop.do" : linkUrl) + "?sid="+ $.utils.config( 'SID_NM_MARTMALL' ) +"&kind=m&SITELOC=AA005&returnurl=" + encodeURIComponent(returnUrl)+ "&mart=Y" + ( $.utils.config( 'LMLocalDomain' ) === "true" ? "&mode=DEV" : "");
		}
		// https에서 로그아웃시 페이지 이동
		if (location.protocol === "https:") {
			location.href = url;
		}
		else {
			$("<iframe>").attr({"src":url}).css({"width":"0px", "height":"0px"}).appendTo("body");
		}
	}
	
	return _logout
});