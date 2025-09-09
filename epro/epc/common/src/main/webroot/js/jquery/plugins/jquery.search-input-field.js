(function( $, window, document, undefined ) {
	'use strict';
	
	$.fn.searchInputField = function( options ) {
		var defaults = {
			keywords : [],
			searchLinkName : '#search_link_nm',
			searchLinkUrl : '#search_link_url',
			searchTerm : '#searchTerm'
		},
		config = $.extend( true, {}, defaults, options || {} );
		
		if( config.keywords.length === 0 ) {
			return this;
		}
		
		var con = randomRange( 0, config.keywords.length - 1 );
		
		var $searchLinkName = this.find( config.searchLinkName ),
			$searchLinkUrl = this.find( config.searchLinkUrl ),
			$searchTerm = this.find( config.searchTerm );
		
		var keyword = config.keywords[ con ];
		
		$searchLinkName.val( keyword.LINK_NM || '' );
		$searchLinkUrl.val( keyword.LINK_IMG_PATH || '' );
		$searchTerm
			.attr( 'placeholder', keyword.LINK_NM || '' )
			.val( keyword.LINK_NM || '' );
		
		return this;
		

		function randomRange( n1, n2 ) {
			return Math.floor( (Math.random() * (n2 - n1 + 1)) + n1 );
		}
	};
	
})( jQuery, window, document );