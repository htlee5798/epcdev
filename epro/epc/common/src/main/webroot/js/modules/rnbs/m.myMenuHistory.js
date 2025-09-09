(function( factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require( 'jquery' ) );
	} else {
		factory( jQuery );
	}
}(function( $ ) {
	'use strict';
	
	var myMenuHistory = (function() {
		return {
			get : _get,
			remove : _remove
		};

		function _get() {
			$.api.get({
				apiName : 'myHistory',
				data : {
					'histTypeCd' : '01',
					'ignoreType' : 'Y'
				},
				successCallback : function( data ) {
					_render( data );
				}
			});
		}
		
		function _render( data ) {
			var lists = $.render.mobileRnbMyHistoryForMenu( data );
			
			if( data && data.historyList.length > 0 ) {
				$( "#histmncnt" ).html( data.historyList.length );
				$( "#gnbhist2" ).html( lists );
			} else {
				$("#histmncnt").html( "0" );
				$("#GNTabItem2").html( lists );
			}
			
			$( "#gnbhist2" ).on( 'click', '.action-trash', function() {
				myMenuHistory.remove( $( this ).data( 'seq' ) );
				
				return false;
			});
		}
		
		function _remove( seq ) {
			$.api.set({
				apiName : 'myHistoryRemove',
				data : {
					'histSeq' : seq
				},
				traditaionnal : true,
				successCallback : function( data ) {
					if( data.success === true ) {
						myMenuHistory.get();
					} else {
						alert( data.message );
					}
				}
			});
		}
	})();
	
	window.myMenuHistory = myMenuHistory;
}));