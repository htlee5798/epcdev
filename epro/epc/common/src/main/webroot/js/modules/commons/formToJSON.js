(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.formToJSON = factory(jQuery);
	}
}( window, function($) {
	var _formToJSON = function( selector ) {
		var form = {};
	    $(selector).find(':input[name]:enabled').each( function() {
	        var self = $(this);
	        var name = self.attr('name');
	        if (form[name]) {
	            form[name] = form[name] + ',' + self.val();
	        }else{
	            form[name] = self.val();
	        }
	    });

	  return form;
	};
	
	return _formToJSON;
}));