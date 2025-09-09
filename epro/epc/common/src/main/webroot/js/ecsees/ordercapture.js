function sys4u_ecsees_getCookie_orderresult( name ){
	var nameOfCookie = name + "=";
	var x = 0;
	while ( x <= document.cookie.length ){
		var y = (x+nameOfCookie.length);
		if ( document.cookie.substring( x, y ) == nameOfCookie ) {
			if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
				endOfCookie = document.cookie.length;
			return unescape( document.cookie.substring( y, endOfCookie ) );
		}
		x = document.cookie.indexOf( " ", x ) + 1;
		if ( x == 0 )
			break;
	}
	return "";
}
function sys4u_ecsees_sendorder( order_id ){
	var sessionId = ''; 
	sessionId = sys4u_ecsees_getCookie_orderresult("ECSSID");	// j
	var targetUrl = "http://112.220.205.115:8081/x2/log/Order-insertV3?a="+order_id+"&j="+sessionId;
	
	var ei = new Image();
	ei.src = targetUrl;
}