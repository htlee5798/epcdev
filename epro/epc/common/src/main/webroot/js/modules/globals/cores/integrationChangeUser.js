/**
 * 
 */
define(function() {
	function _integrationChangeUser() {
		var url;
		var popup;

		if ( location.protocol === "http:" ) {
			url = $.utils.config( 'LMMembersAppUrl' );
		}
		else {
			url = $.utils.config( 'LMMembersAppSSLUrl' );
		}
		
		window.open(url + "/imember/member/form.do?sid=" + $.utils.config( 'SID_NM_MARTMALL' ) +"&returnurl="+encodeURIComponent(returnUrl)+ (!!param ? "&" + param : ""), "MartMember","");
	}
	
	return _integrationChangeUser;
});