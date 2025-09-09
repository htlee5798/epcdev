(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.checkSpecialType = factory();
	}
}( window, function() {
	var _checkSpecialType = function( in_str ) {	
		var m_Sp = /[$\\@\\\#%\^\&\*\(\)\[\]\+\_\{\}\'\~\=\|\<\>]/,
			m_val  = in_str;

		if(m_val.length == 0){
			
			return true;
		}

		var strLen = m_val.length,
			m_char = m_val.charAt((strLen) - 1);
		
		if(m_char.search(m_Sp) != -1) {
			
			return false;
		}
	};
	
	return _checkSpecialType;
}));