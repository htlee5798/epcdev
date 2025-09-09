(function( root, factory ) {
	'use strict';
	
	if( typeof define === 'function' && define.amd ) {
		define( factory );
	} else if( typeof exports !== 'undefined' ) {
		module.exports = factory( require() );
	} else {
		root.lottemart = root.lottemart || {};
		root.lottemart.exteriorSchemeUrl = factory();
	}
}( window || {}, function(){
	var SCHEME_FOR_TEST = 'intent://open#Intent;scheme=;end';
	var ua = navigator.userAgent,
	    isAndroid = /android/i.test(ua),
	    isIOS = /iphone|ipad|ipod/i.test(ua),
		isChrome = ua.indexOf( 'Chrome' ) !== -1 ? true : false,
		isSupportingIntent = isChrome && parseInt( ua.match(/Chrome\/(\S+)/)[ 1 ], 10 ) > 24 && !( /firefox|opr|fb_iab/i.test( ua ) );

	var loader = (function() {
		return {
		    isPrepared: false,
		    isNotSupportedFallbackURL: false,
		    initialize: function() {
		        if (isAndroid) {
		            if (isSupportingIntent) {
		                this._checkSupportingFallbackURL();
		                this.load = this._launchAppViaIntent;
		            } else {
		                this.load = this._launchAppViaIframe;
		            }
		        } else if (isIOS) {
		           this.load = this._launchAppViaIframe;
		        } else {
		            alert('run in mobile');
		        }
		    },
		    setParams: function(params) {
		        this.appURI = params.appURI;
		        this.fallbackURL = params.fallbackURL;
		    },
		    _createHiddenIframe: function() {
		        var iframe = document.createElement('IFRAME');
	
		        iframe.style.display = 'none';
		        return iframe;
		    },
		    _launchAppViaIframe: function() {
		        var iframe = this._createHiddenIframe(),
		            start = +new Date(),
		            self = this;
	
		        iframe.src = self.appURI;
		        iframe.addEventListener('load', function onload() {
		            iframe.removeEventListener('load', onload);
		            document.body.removeChild(iframe);
		        });
		        document.body.appendChild(iframe);
	
		        setTimeout(function() {
		            var now = +new Date();
		            if (now - start < 2000) {
		                top.location.href = self.fallbackURL;
		            }
		        }, 500);
		    },
		    _checkSupportingFallbackURL: function() {
		        var iframe = this._createHiddenIframe(),
		            self = this;
	
		        iframe.addEventListener('load', function onload() {
		            if (iframe.src === SCHEME_FOR_TEST) {
		                self.isNotSupportedFallbackURL = true;
		            } else {
		                iframe.removeEventListener('load', onload);
		                document.body.removeChild(iframe);
		                self.isPrepared = true;
		            }
		        });
		        iframe.src = SCHEME_FOR_TEST;
	
		        document.body.appendChild(iframe);
		        setTimeout(function() {
		            iframe.src = '';
		        }, 100);
		    },
		    _launchAppViaIntent: function() {
		        var popup,
		            self = this;
	
		        if (!this.isPrepared) {
		            setTimeout(this.load.bind(this), 100);
		            return;
		        }
	
		        if (!this.isNotSupportedFallbackURL) {
		            window.location.href = self.appURI;
		            return;
		        }
	
		        popup = window.open('');
		        if (popup) {
		            popup.addEventListener('unload', function onUnload() {
		                setTimeout(function () {
		                    if (popup.opener) {
		                        popup.opener.location.href = self.fallbackURL;
		                    }
		                }, 10);
		                popup.close();
		                popup.removeEventListener('unload', onUnload);
		            });
		            popup.location.href = this.appURI.replace(/package=.*?;/, '');
		        } else {
		            alert('팝업 허용해주세요');
		        }
		    },
		    load: function() {
		        alert('load() is not implemented!');
		    }
		};
	})();
	
	return loader;
}));