(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.fnAddDay = factory();
	}
}( window, function() {
	var _fnAddDay = function( targetDate  , dayPrefix ) {
		var newDate = new Date(),
			processTime = targetDate.getTime() + ( parseInt(dayPrefix) * 24 * 60 * 60 * 1000 );
		
		newDate.setTime(processTime);
		
		return newDate;
	};
	
	return _fnAddDay;
}));