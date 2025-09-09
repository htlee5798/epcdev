(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( ['jquery'], factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require('jquery') );
	} else {
		root.fnSetDay = factory(jQuery);
	}
}( window, function($) {
	var _fnSetDay = function( fromTo ) {
		var $yyObj = $("#select"+fromTo+"Year");
		var $mmObj = $("#select"+fromTo+"Month");
		var $ddObj = $("#select"+fromTo+"Day");
		
		var yy = $("#select"+fromTo+"Year").val();
		var mm = $("#select"+fromTo+"Month").val();
		var dd = $("#select"+fromTo+"Day").val();
		
		var dayMax = 31;
		
		if( mm == "04" || mm == "06" || mm == "09" || mm == "11" ) {
		    dayMax = 30;
		}else if( mm == "02" ) {
		    if ((yy % 4) == 0) {
		        if ((yy % 100) == 0 && (yy % 400) != 0) { 
		            dayMax = 28; 
		        } else { 
		            dayMax = 29; 
		        }
	        } else { 
	            dayMax = 28; 
	        }
		}
		
		$ddObj.empty();
				
		for( var i=0; i<dayMax; i++) {
			if( i < 9 ) {
				$ddObj.append("<option value='"+ "0"+(i+1) +"'>"+ "0"+(i+1) +"</option>");
			} else {
				$ddObj.append("<option value='"+ (i+1) +"'>"+ (i+1) +"</option>");
			}
		}
		
		$("#select"+fromTo+"Day option[value='"+ dd +"']").attr("selected", "selected");
	};
	
	return _fnSetDay;
}));