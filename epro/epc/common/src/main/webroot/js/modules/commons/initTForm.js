(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.initTForm = factory();
	}
}( window, function() {
	'use strict';
	
	var _initTForm = function() {
		var inHtml = "<form name=\"tForm\" id=\"tForm\" method=\"POST\" action=\"\">";
			inHtml = inHtml + "<div id=\"divTemp\"></div>";
			inHtml = inHtml + "</form>";
		document.getElementById("tFormDiv").innerHTML =inHtml;
	};
	
	return _initTForm;
}));