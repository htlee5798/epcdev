(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.fnJsMsg = factory();
	}
}( window, function() {
	var _fnJsMsg = function( fl ) {
		var arg= arguments;
		 if(arg.length==0) return '';
		 if(arg.length==1) return arg[0];
		 
		 var fn = function(w, g) {
		  if(isNaN(g)) return '';
		  var idx = parseInt(g)+1;
		  if(idx >= arg.length) return '';
		  return arg[parseInt(g)+1];
		 };
		 return arg[0].replace(/\{([0-9]*)\}/g, fn);
	};
	
	return _fnJsMsg;
}));