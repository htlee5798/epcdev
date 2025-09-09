/**
 * 
 */
define( [ 'jquery.apis' ], function() {
	function init() {
		$.api.get({
			apiName : 'unShoppingAddress',
			successCallback : function( resData ) {
				if( resData.defaultMemberAddrYn !== 'Y' ) {
					unRegist( resData );
				}
			}
		});
	}
	
	function unRegist( resData ) {
		alert( resData.message );
		//TODO
		$( '#delivery-change' ).trigger( 'click' );
	}
	return function() {
		init();
	};
});