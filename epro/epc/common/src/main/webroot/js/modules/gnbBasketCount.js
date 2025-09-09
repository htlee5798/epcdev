define(function() {
	function _gnbBasketCount() {
		if( $.utils.config( 'Login_yn' ) === 'Y' ) {
			$.api.get({
				apiName : 'basketCount',
				successCallback : function( data ) {
					var cnt = 0,
						$gnbBasket = $( '#GNB_Basket' ),
						$em = $gnbBasket.find( '> em' );
					
					if (data) {
						cnt = data.count;
					}
	
					if ( $em.length > 0) {
						$em.text(cnt);
					}
					else {
						$gnbBasket.append("<em>" + cnt + "</em>");
					}
				}
			});
		}
	}
	
	return _gnbBasketCount;
});