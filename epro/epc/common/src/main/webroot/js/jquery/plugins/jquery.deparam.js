(function($){
	
	$.deparam = $.deparam || function(queryString){
		if(queryString === undefined) {
			queryString = window.location.search;
		}

		var parameters = {};
		if(queryString != null && queryString != '') {
			if(queryString.indexOf('?') == 0) {
				queryString =  queryString.slice(1);
			}
			
			var keyValuePairs = queryString.split('&');
			for(var i = 0; i < keyValuePairs.length; i++) {
				var keyValuePair = keyValuePairs[i].split('=');
				
				parameters[keyValuePair[0]] = keyValuePair[1];
			}
		}
		
		return parameters;
	};
	
})(jQuery);