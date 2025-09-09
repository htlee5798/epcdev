(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.callAppScheme = factory();
	}
}( window, function() {
	var _callAppScheme = function() {
		var appActionID = "appExecuteFrame";
		var actionFrame = document.createElement("IFRAME");

		actionFrame.id = appActionID;
		actionFrame.name = actionFrame.id;
		actionFrame.width = 0;
		actionFrame.height = 0;
		actionFrame.src = scheme;

		if(isAppVal == true){
			window.location.href = scheme ;
		}
	};
	
	return _callAppScheme;
}));