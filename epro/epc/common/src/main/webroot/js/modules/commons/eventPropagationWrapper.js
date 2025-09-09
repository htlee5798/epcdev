(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.eventPropagationWrapper = factory();
	}
}( window, function() {
	var _eventPropagationWrapper = function(e, func) {
		if (e && e.preventDefault != null && typeof(e.preventDefault) === "function") {
			e.preventDefault();
		}
		func();
		if (e && e.stopPropagation != null && typeof(e.stopPropagation) === "function") {
			e.stopPropagation();
		}
	};
	
	return _eventPropagationWrapper;
}));