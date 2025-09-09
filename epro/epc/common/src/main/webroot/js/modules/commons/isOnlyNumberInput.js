(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.isOnlyNumberInput = factory();
	}
}( window, function() {
	var _isOnlyNumberInput = function(e, decimal) {
		var key,
			keychar; 
	
	    if (window.event) { 
	       // IE에서 이벤트를 확인하기 위한 설정 
	        key = window.event.keyCode; 
	    } else if (e) { 
	      // FireFox에서 이벤트를 확인하기 위한 설정 
	        key = e.which; 
	    } else { 
	        return true; 
	    } 
	
	    keychar = String.fromCharCode(key);
	    
	    if ((key == null) || (key == 0) || (key == 8) || (key == 9) || (key == 13)  || (key == 27)) { 
	        return true; 
	    } else if ((("0123456789").indexOf(keychar) > -1)) { 
	        return true; 
	    } else if (decimal && (keychar == ".")) { 
	        return true; 
	    } else {
	        return false; 
	    }
	};
	
	return _isOnlyNumberInput;
}));