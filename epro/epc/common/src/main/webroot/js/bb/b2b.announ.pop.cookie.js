setCookie = function( name, value, expiredays ) { 
	var todayDate = new Date(); 
	todayDate.setDate( todayDate.getDate() + expiredays ); 
	document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";";
}

closeWin = function(cookieName) {
	if ( document.cnjform.notice.checked ) {
		setCookie(cookieName, "no" , 1);  
	} 
	top.close();
}

getCookie = function(name) { 
	var isExist = false; 
	var start, end;
	var i = 0;
	while(i <= document.cookie.length) { 
		start = i; 
		end = start + name.length; 
	 
		if(document.cookie.substring(start, end) == name) { 
			isExist = true;
			break; 
		} 
			i++; 
	} 
 
	if(isExist == true) { 
		start = end + 1; 
		end = document.cookie.indexOf(";", start); 
		if(end < start)  {
			end = document.cookie.length; 
			return document.cookie.substring(start, end); 
		} else {
			return document.cookie.substring(start, end); 
		}
	}
	
	return ""; 
}