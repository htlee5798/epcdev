/**
 * 
 */
define( [ 'modules/globals/cores/login' ], function( login ) {
	function _goWish() {
		var url = $.utils.config( 'LMAppUrl' ) + $.utils.config( 'wishListPath' );
		
		if ( $.utils.config( 'Member_yn' ) === "true" ) {
			location.href = url;
		}
		else {
			login( $.utils.config( 'SID_NM_MARTMALL' ), url );
		}
	}
	
	return _goWish;
});