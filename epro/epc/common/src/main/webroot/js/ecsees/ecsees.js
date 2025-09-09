function sys4u_ecsees_setCookie (name, value) {
	var argv = sys4u_ecsees_setCookie.arguments;
	var argc = sys4u_ecsees_setCookie.arguments.length;
	var expires = (2 < argc) ? argv[2] : null;
	var toDate = new Date();
		toDate.setDate( toDate.getDate() + 1 );
	var path = (3 < argc) ? argv[3] : "/";						// path는 늘 기본값.
	var domain = (4 < argc) ? argv[4] : null;
	var secure = (5 < argc) ? argv[5] : false;
	document.cookie = name + "=" + escape (value)
			+ ((path == null) ? "" : ("; path=" + path))
			+ ((domain == null) ? "" : ("; domain=" + domain))
			+ ((secure == true) ? "; secure" : "");
}

function sys4u_ecsees_getCookie( name ){
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
(function() {
	function RndStr() {
	    this.str = '';
	    this.pattern = /^[a-zA-Z0-9]+$/;

	    this.setStr = function(n) {
	        if(!/^[0-9]+$/.test(n)) n = 0x10;
	        this.str = '';
	        for(var i=0; i<n-1; i++) {
	            this.rndchar();
	        }
	    };

	    this.getStr = function() {
	        return this.str;
	    };

	    this.rndchar = function() {
	        var rnd = Math.round(Math.random() * 1000);

	        if(!this.pattern.test(String.fromCharCode(rnd))) {
	            this.rndchar();
	        } else {
	            this.str += String.fromCharCode(rnd);
	        }
	    };
	}
	
	var ecssid = sys4u_ecsees_getCookie("ECSSID");
	if(typeof(ecssid) == 'undefined' || ecssid == null || (ecssid + "").trim() == '') {
		var rndstr = new RndStr();
		rndstr.setStr(32);
		ecssid = rndstr.getStr();
		sys4u_ecsees_setCookie("ECSSID", ecssid);
	}
	
	var sys4u_ecsees_host_url = document.URL;		
	var sys4u_ecsees_referrer = document.referrer;			// c
	var sys4u_ecsees_pathname = window.location.pathname;	// d
	var sys4u_ecsees_parameter = "";								// b
	var sys4u_ecsees_agent = encodeURIComponent(navigator.userAgent);						// e
	var channel_cd = sys4u_ecsees_getCookie("CHANNEL_CD");	// f
	
	var index = sys4u_ecsees_host_url.indexOf("?");
	if(index > -1) {
		sys4u_ecsees_parameter = sys4u_ecsees_host_url.substring(index + 1);
		sys4u_ecsees_parameter = encodeURIComponent(sys4u_ecsees_parameter);
	}
	
	var paramStr = "?b="+sys4u_ecsees_parameter+"&c="+sys4u_ecsees_referrer+"&d="+sys4u_ecsees_pathname+"&e="+sys4u_ecsees_agent+"&a="+ecssid+"&f="+channel_cd;
	
	var targetUrl = "http://112.220.205.115:8081/x2/log/Log-GetPageInfo";
	
	var ei = new Image();
	ei.src = targetUrl + paramStr;
})();