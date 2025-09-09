(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.genDomInput = factory();
	}
}( window, function( $ ) {
	function _genDomInput( elemName, elemValue ) {
		var input = document.createElement("input");
		input.setAttribute("type", "hidden");
		input.setAttribute("name", elemName);
		input.setAttribute("id", elemName);
		input.setAttribute("value", elemValue);
		return input;
	}
	
	return _genDomInput;
}));