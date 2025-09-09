/**
 * 
 */
define(function() {
	function _b2eLogout( returnUrl, linkUrl ) {
		var lmMembersAppSSLUrl = $.utils.config( 'LMMembersAppSSLUrl' ),
			ssoLogoutPopPath = $.utils.config( 'ssoLogoutPopPath' );
			url = lmMembersAppSSLUrl + (!linkUrl ? ssoLogoutPopPath : linkUrl) + "?returnURL=" + encodeURIComponent(returnUrl) + "&SITELOC=AA005";
		location.href =  url;
	}
	return _b2eLogout;
});