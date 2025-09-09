(function( $, window, document, undefined ) {
	'use strict';

    $.utils.shareHandler = new function() {
        var config = {
            isMobile: $.utils.isMobile(),
            appkey: _KAKAO_APP_KEY_, //TODO 변경필요
            title: getMeta('og:title'),
            image: getMeta('og:image'),
            url: getMeta('og:url'),
            kakaotalk: {
                id: 'kakao-link',
                width: '640',
                height: '460',
                text: ''
            }
        },
        services = {
            facebook: function facebook(url) {
            	url = url || config.url;
                var params = {u: setDomain(url)},
                      path = 'http://facebook.com/sharer.php',
                      openUrl = path + getParamString(params);

                window.open(openUrl, 'share_facebook', 'directories=no,location=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizable=no,width=420,height=370');
            },
            twitter: function twitter(url, title) {
            	url = url || config.url;
                title = title || getMeta('og:title');

                var params = { text: title, url: setDomain(url) },
                    path = 'https://twitter.com/intent/tweet',
                    openUrl = path + '?' + $.param(params),
                    settngs = 'directories=no,location=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizable=no,width=640, height=440';

                window.open(openUrl, 'share_twitter', settngs);
            },
            googleplus : function googleplus(url) {
            	url = url || config.url;

            	var params = {
                    url: setDomain(url),
                    hl: 'ko'
              },
              path = 'https://plus.google.com/share',
              openUrl = path + '?' + $.param(params);

            	var popup = null,
                	settngs = 'directories=no,location=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizable=no,width=500,height=370';

            	if (popup == null) { popup = window.open('', 'share_google', settngs); }

            	popup.location.href = openUrl;
            },
            kakaostory: function kakaostory(url, title) {
            	url = url || config.url;

            	var  path = 'https://story.kakao.com/share?url=';
            	var openUrl = path  + encodeURIComponent(url);


                if( $.utils.config( 'onlinemallApp') ) {
                    if(window.LOTTEMART && window.LOTTEMART.openPopup) {
                        window.LOTTEMART.openPopup(openUrl);
                    } else {
                        schemeLoader.loadScheme({
                            key: 'openPopup',
                            url: openUrl,
                            type: "share_kakaostory",
                            callback: schemeCallback
                        });
                    }
               } else {
                   window.open(openUrl, 'share_kakaostory', 'directories=no,location=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizable=no,width=420,height=370');
               }

            },
            kakaotalk: function kakaotalk(url, title) {
            	if( window.Kakao ) {
	            	url = url || config.url;
	                title = title || config.title;
	                Kakao.Link && Kakao.Link.sendDefault({
                        objectType : 'feed',
                        content : {
                            title : title,
                            imageUrl : config.image,
                            link : {
                                mobileWebUrl : setDomain(url),
                                webUrl: setDomain(url)
                            }
                        },
	                    fail: function () {
	                        alert('지원하지 않는 플랫폼입니다.');
	                    },
	                    buttons: [
	                   /* {
                           title : '웹으로 보기',
                            link : {
                                mobileWebUrl : setDomain(url),
                                webUrl: setDomain(url)
                            }
                        },*/
                        {
	                    	title: '앱으로 보기',
                            link : {
                                iosExecParams: setDomain(url),
                                androidExecParams: setDomain(url)
                            }
	                    }]
	                });
            	}
            },
            band: function band(url) {
            	url = url || config.url;

                var  path = 'http://band.us/plugin/share';
                var openUrl = path + "?body=" + encodeURI(config.title) + encodeURI("\n") + encodeURI(url) + "&route=" + document.location.origin;

                if( $.utils.config( 'onlinemallApp') ) {
                    if(window.LOTTEMART && window.LOTTEMART.openPopup) {
                        window.LOTTEMART.openPopup(openUrl);
                    } else {
                        schemeLoader.loadScheme({
                            key:'openPopup',
                            url:openUrl ,
                            type:"share_band" ,
                            callback:schemeCallback
                        });
                    }

               } else {
                   window.open(openUrl, 'share_band', 'directories=no,location=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizable=no,width=420,height=370');
               }
            },
            line: function line(url) {
            	url = url || config.url;

            	var  path = 'http://line.me/R/msg/text/?';
            	var openUrl = path  + encodeURI(config.title) + encodeURI("\n") + encodeURI(url);

                if( $.utils.config( 'onlinemallApp') ) {
                    if(window.LOTTEMART && window.LOTTEMART.openPopup) {
                        window.LOTTEMART.openPopup(openUrl);
                    } else {
                        schemeLoader.loadScheme({
                            key: 'openPopup',
                            url: openUrl,
                            type: "share_line",
                            callback: schemeCallback
                        });
                    }
               } else {
                   window.open(openUrl, 'share_line', 'directories=no,location=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizable=no,width=420,height=370');
               }
            },
            sms: function sms(url) {
            	url = url || config.url;

            	if (location.pathname.indexOf("/mobile/") == 0) {
            		setDomain(url);
            	}
            	var openUrl = encodeURI(url);

            	var _ua = navigator.userAgent.toUpperCase();

            	if(/LOTTEMART-APP-SHOPPING/gi.test(_ua)){
            		schemeLoader.loadScheme({key:'sendSMS', url:openUrl});
            	}else{
            		if(/android/gi.test(_ua)){
            			window.open("sms:?body=" + openUrl);
            		}
            	}
            }
        };

        (function init() {
             if (window.Kakao && window.Kakao.Auth === undefined) {
                 Kakao.init(config.appkey);
             }
         })();
        function schemeCallback(returnObj){
        	if(returnObj.result) return;

        	window.open(returnObj.url, returnObj.type , 'directories=no,location=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizable=no,width=420,height=370');

        };
        function getMeta(name) {
            var metaTag = $('meta'),
                rtn = '',
                $this = null;

            metaTag.each(function() {
                $this = $(this);

                if($this.attr('property') !== undefined && $this.attr('property') === name) {
                    rtn = $this.attr('content');
                } else if($this.attr('name') !== undefined && $this.attr('name') === name) {
                    rtn = $this.attr('content');
                }

            });

            if(!rtn){
                	if(name == 'og:title'){
                		rtn = $("title").text() ? $("title").text() :  "롯데마트몰 - easy & slow life";
                	}else if(name =='og:image'){
                		rtn = '//simage.lottemart.com/images/mobile/contentimg/ico/ico-a_72_72.png';
                	}else if(name =='og:url'){
                		rtn = location.href;
                	}
            }

            return rtn;
        }

        function setDomain(path) {
        	if (location.pathname.indexOf("/mobile/") == 0) {
        		path = $.utils.config( 'LMAppSSLUrlM' ) + location.pathname + location.search;
        	}else {
        		 var domainPath = $.utils.config( 'LMAppSSLUrl' ) || location.origin;

                 if(path && path.indexOf('http') != 0) {
                     path = domainPath + path;
                 }
        	}

            return path;
        }

        function shortenUrl(url, callback) {
        	//TODO shortenUrl 만들기
//            $.getProductShortenUrl(function(url) {
//
//                var productId = $.getProductId ? $.getProductId() : '';
//
//                callback && callback(url);
//                if(!productId.isEmpty()) {
//                    utils.api.addShortenUrl({device: 'mobile', id: productId, url: url});
//                }
//            });

        	callback && callback(url);
        }

        function clickHandler(e) {

            config.title = getMeta('og:title');
            config.image = getMeta('og:image');
            config.url = getMeta('og:url');

            var $btn = $(this),
                service = $btn.data('service'), // twitter, facebook....
                url = $btn.data('url'),
                title = $btn.data('title'),
                func = services[service];

            if(typeof func == 'function') {
                func(url, title);
            }
            e.preventDefault();
            return false;
        };

        function getParamString(params) {
            var strParams = '';
            $.each(params, function(i, v) {
                if(v != '') {
                    strParams += i + '=' + encodeURIComponent(v) + '&';
                }
            });

            return '?' + strParams;
        }

        this.bind = function($t) {
            if(!$t || !$t.length || !$t.data('service')) {
                return;
            }
        	$t.on('click', clickHandler);
        };

        this.unbind = function($t) {
            if(!$t || !$t.length || !$t.data('service')) {
                return;
            }

            $t.off('click', clickHandler);
        };
    };
})( jQuery, window, document );