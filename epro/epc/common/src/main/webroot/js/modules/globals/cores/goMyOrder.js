/**
 * 
 */
define( [ 
          'modules/globals/cores/login',
          'modules/globals/cores/noMemberOrder'
          ], function( login, noMemberOrder ) {
	function _goMyOrder() {
		var url = $.utils.config( 'LMAppUrl' ) + $.utils.config( 'selectMyOrderListPath' );
		
		if ( $.utils.config( 'Member_yn' ) === "true" ) {
			location.href = url;
		}
		else if ( $.utils.config( 'Login_yn' ) === "Y") {
			noMemberOrder(url);
		}
		else {
			login( $.utils.config( 'SID_NM_MARTMALL' ), url);
		}
	}
	
	return _goMyOrder;
});