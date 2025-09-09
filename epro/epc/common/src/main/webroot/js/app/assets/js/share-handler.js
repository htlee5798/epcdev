import $ from 'jquery';

class ShareHandler {
    constructor() {
        this.isMobile = $.utils.isMobile();
        this.appKey = $.utils.config('kakaoAppKey');
        this.title = getMeta('og:title');
        this.image = getMeta('og:image');
        this.url = getMeta('og:url');

        if (window.Kakao && window.Kakao.Auth === undefined) {
            Kakao.init(this.appKey);
        }
    }

    facebook(url = this.url) {
        let params = {
            u: setDomain(url)
        };
        let path = 'http://facebook.com/sharer.php';
        let openUrl = path + getParamString(params);

        window.open(openUrl, 'share_facebook', 'directories=no,location=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizable=no,width=420,height=370');
    }

    twitter(url = this.url, title = this.title) {
        let params = {
            text: title,
            url: setDomain(url)
        };
        let path = 'https://twitter.com/intent/tweet';
        let openUrl = path + '?' + $.param(params);
        let settngs = 'directories=no,location=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizable=no,width=640, height=440';

        window.open(openUrl, 'share_twitter', settngs);
    }

    kakaostory(url = this.url, title = this.title) {
        let path = 'https://story.kakao.com/share?url=';
        let openUrl = path + encodeURIComponent(url);


        if ($.utils.config('onlinemallApp')) {
            if (window.LOTTEMART && window.LOTTEMART.openPopup) {
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
    }

    kakaotalk(url = this.utl, title = this.title) {
        if (window.Kakao) {
            let _this = this;
            Kakao.Link && Kakao.Link.sendTalkLink({
                label: _this.title,
                image: {
                    src: _this.image,
                    width: "640",
                    height: "640"
                },
                webLink: {
                    text: "웹브라우저로 바로 확인",
                    url: setDomain(_this.url)
                },
                fail: function () {
                    alert('지원하지 않는 플랫폼입니다.');
                },
                appButton: {
                    text: '앱으로 연결',
                    execParams: {
                        iphone: {
                            UrlLink: setDomain(_this.url)
                        },
                        android: {
                            UrlLink: setDomain(_this.url)
                        }
                    }
                }
            });
        }
    }

    band(url = this.url) {
        let path = 'http://band.us/plugin/share';
        let openUrl = path + "?body=" + encodeURI(this.title) + encodeURI("\n") + encodeURI(url) + "&route=" + document.location.origin;

        if ($.utils.config('onlinemallApp')) {
            if (window.LOTTEMART && window.LOTTEMART.openPopup) {
                window.LOTTEMART.openPopup(openUrl);
            } else {
                schemeLoader.loadScheme({
                    key: 'openPopup',
                    url: openUrl,
                    type: "share_band",
                    callback: schemeCallback
                });
            }

        } else {
            window.open(openUrl, 'share_band', 'directories=no,location=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizable=no,width=420,height=370');
        }
    }

    line(url = this.url) {
        let path = 'http://line.me/R/msg/text/?';
        let openUrl = path + encodeURI(this.title) + encodeURI("\n") + encodeURI(url);

        if ($.utils.config('onlinemallApp')) {
            if (window.LOTTEMART && window.LOTTEMART.openPopup) {
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
    }

    sms(url) {
        let openUrl = encodeURI(url);
        schemeLoader.loadScheme({key: 'sendSMS', url: openUrl});
    }

    clickHandler($btn) {
        this.title = getMeta('og:title');
        this.image = getMeta('og:image');
        this.url = getMeta('og:url');

        let service = $btn.data('service');
        let url = $btn.data('url');
        let title = $btn.data('title');

        if (service) {
            this[service](url, title);
        }
    }
}

function setDomain(path) {
    let domainPath = $.utils.config('LMAppSSLUrl')
        || location.origin;

    if (path && path.indexOf('http') != 0) {
        path = domainPath + path;
    }
    return path;
}

function getMeta(name) {
    let metaTag = $('meta'),
        rtn = '',
        $this = null;

    metaTag.each(function () {
        $this = $(this);

        if ($this.attr('property') !== undefined
            && $this.attr('property') === name) {
            rtn = $this.attr('content');
        } else if ($this.attr('name') !== undefined
            && $this.attr('name') === name) {
            rtn = $this.attr('content');
        }
    });

    if (!rtn) {
        if (name == 'og:title') {
            rtn = $("title").text() ? $("title").text() : "롯데마트몰 - easy & slow life";
        } else if (name == 'og:image') {
            rtn = '//simage.lottemart.com/images/mobile/contentimg/ico/ico-a_72_72.png';
        } else if (name == 'og:url') {
            rtn = location.href;
        }
    }
    return rtn;
}

function getParamString(params) {
    let strParams = '';
    $.each(params, function (i, v) {
        if (v != '') {
            strParams += i + '=' + encodeURIComponent(v) + '&';
        }
    });

    return '?' + strParams;
}

function schemeCallback({url, type, result}) {
    if (result) return;

    window.open(url, type, 'directories=no,location=no,menubar=no,status=no,toolbar=no,scrollbars=no,resizable=no,width=420,height=370');

}

export default ShareHandler;