/**
 * 
 */
define(function() {
	function _familyChangeUser() {
		var url = "https://"+ $.utils.config( 'MEMBER_URL' ) +"/door/user/change_user_info.jsp?sid=" + $.utils.config( 'SID_NM_MARTMALL' ) + (!!param ? "&" + param : "");
		var popWin = window.open(url, "");
		popWin.focus();
	}
	return _familyChangeUser;
});