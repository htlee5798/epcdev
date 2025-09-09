/**
 * 
 */
define(function() {
	function _noMemberLogin() {
		var url = "/login/noMemLoginSeed.do?returnURL=" + encodeURIComponent(returnurl) + "&SITELOC=AA005";
		var popup = window.open(url, "ssoLoginPop", "toolbar=no,location=no,directories=no,status=no,scrollbars=yes,resizable=yes, menubar=no,width=800px,height=900px,dependent=yes");
		
		if (popup != null) {
			popup.focus();
		}
	}
	
	return _noMemberLogin;
});