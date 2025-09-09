/**
 * 
 */
define(function() {
	return {
		ie : /msie/i.test(browserAgent) || /trident/i.test(browserAgent) || /edge/i.test(browserAgent),
		opera: /mozilla/i.test(browserAgent) && /applewebkit/i.test(browserAgent) && /chrome/i.test(browserAgent) && /safari/i.test(browserAgent) && /opr/i.test(browserAgent),
		safari: /safari/i.test(browserAgent) && /applewebkit/i.test(browserAgent) && !/chrome/i.test(browserAgent),
		chrome: /webkit/i.test(browserAgent)  && /chrome/i.test(browserAgent) && !/edge/i.test(browserAgent),
		firefox: /mozilla/i.test(browserAgent) && /firefox/i.test(browserAgent),
		info: function() {
			var t, m = browserAgent.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];
			if (/trident/i.test(m[1])) {
				t = /\brv[ :]+(\d+)/g.exec(browserAgent) || [];
				return {
					name: "IE",
					version: t[1] || ''
				}
			}
			if ( m[1] === "chrome") {
				t = browserAgent.match(/\bOPR\/(\d+)/);
				if (t != null ) {
					return {
						name: 'Opera',
						version: t[1]
					}
				}
			}
			m = m[2] ? [m[1], m[2]]: [navigator.appName, navigator.appVersion, '-?'];
			if ( (t = browserAgent.match(/version\/(\d+)/i)) != null ) {
				m.splice(1, 1, t[1]);
			}
			
			return {
				name: m[0],
				version: m[1]
			};
		},
		version: ""
	};
});