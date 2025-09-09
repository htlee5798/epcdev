(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.isArray = factory();
	}
}( window, function() {
	var _isArray = function( obj ) {
		if(obj == null){
			return 0;
		}else {
			//alert(obj.type);
			if(obj.type == 'select-one'){
				return 1;
			}else if(obj.type == 'select-multiple'){
				return 1;
			}else{
				if(obj.length > 1){
					return obj.length;
				}else {
					return 1;
				}
			}
		}
	};
	
	return _isArray;
}));