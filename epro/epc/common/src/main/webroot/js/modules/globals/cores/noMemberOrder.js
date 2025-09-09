/**
 * 
 */
define(function() {
	function _noMemberOrder( returnUrl ) {
		var url = "/login/noMemLoginSeed.do?type=order&returnURL=" + encodeURIComponent(returnurl);
		var popup = window.open(url, "ssoLoginPop", "toolbar=no,location=no,directories=no,status=no,scrollbars=yes,resizable=yes, menubar=no,width=800px,height=700px,dependent=yes");
		
		if (popup != null) {
			popup.focus();
		}
	}
	
	return _noMemberOrder;
});