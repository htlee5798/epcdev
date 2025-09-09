(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.lottemart = root.lottemart || {};
		root.lottemart.interiorSchemeUrl = factory();
	}
}( window || {}, function() {
	'use strict';
	
	var schemeData = {
		'hideBar' : {
			version : {
				ios : '9.9.3',
				android : '10.73'
			},
			url : 'lottemartapp://hideBar'
		},
		'showBar' : {
			version : {
				ios : '9.9.3',
				android : '10.73'
			},
			url : 'lottemartapp://showBar'
		},
		'lnbOpen' : {
			version : {
				ios : '9.9.3',
				android : '10.73'
			},
			url : 'lottemart-shopping-iphone://lnbopen'
		},
		'lnbClose' : {
			version : {
				ios : '9.9.3',
				android : '10.73'
			},
			url : 'lottemart-shopping-iphone://lnbclosed'
		}
	};
	
	function interiorSchemeUrl( name ) {//name, target version
		if( name === undefined ) {
			return;
		}
		if( $.utils.config( 'onlinemallApp' ) ) {
			var schemeObj = schemeData[ name ],
				appVersion = userAgent.split( 'app-version' )[1].replace( 'V.', '' ),
				lastestAppVersion = $.utils.isIOS() ? schemeObj.version.ios : schemeObj.version.android;
			
			if( $.utils.checkAppVersion( appVersion, lastestAppVersion ) ) {
				var iframe = _createHiddenIframe();
				
				iframe.src = schemeObj.url;
				
				document.body.appendChild( iframe );
				setTimeout(function() {
					iframe.parentNode.removeChild( iframe );
				}, 100);
			}
		}
	}
	
	function _createHiddenIframe() {
		var iframe = document.createElement( 'IFRAME' );
		
		iframe.style.display = 'none';
		return iframe;
	}
	
	return interiorSchemeUrl;
}));