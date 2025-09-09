(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define(factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory();
	} else {
		root.fnNmGetter = factory();
	}
}( window, function() {
	var _fnNmGetter = function( caller ) {
		var f = arguments.callee.caller,
			pat = /^function\s+([a-zA-Z0-9_]+)\s*\(/i,
			func = new Object();
		
	    if(caller) {	    	
	    	f = f.caller;
	    }
	    
	    pat.exec(f);
	    func.name = RegExp.$1;
	    
	    return func;
	};
	
	return _fnNmGetter;
}));