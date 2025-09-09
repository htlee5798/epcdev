// 쿠키 체크
// 사용법 cookieCheck( 해당 checkbox 객체 id )
// name 으로 선언하고 싶으시면 코멘트 주세요!
function cookieCheck(objId) {
	var element = jQuery('#' + objId);
	if( jQuery(element).length ) {
		jQuery(element).attr('checked', true);
		jQuery(element).prev().addClass('selected_c');
	}
	return false;
}