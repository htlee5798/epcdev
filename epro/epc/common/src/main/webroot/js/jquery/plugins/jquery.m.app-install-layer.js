(function ($, window, document, undefined) {
    'use strict';

    $.appInstallLayer = function (options) {
        var defaults = {
                closeListener: function () {},
                excludedAffiliates: {
                    name: ['위메프','위메프'],
                    id: ['01560001','01560002']
                }
            },
            $appLayer = null,
            config = $.extend(true, {}, defaults, options);

        init();

        function hasCookie() {
            return !!$.cookie('appInstallInduction');
        }

        function isApp() {
            return $.utils.config('onlinemallApp');
        }

        function isToysrus() {
            return /toysrus.(android|iphone).shopping/.test(window.navigator.userAgent);
        }

        function isQrcode() {
            return !!(parseInt(config.qrcode, 10) && config.dlink);
        }

        function getQueryParameters(str) {
            return (str || document.location.search)
                .replace(/(^\?)/, '')
                .split("&")
                .map(function (n) {
                    return n = n.split("="), this[n[0].toLowerCase()] = n[1], this
                }.bind({}))[0];
        }

        function notContainExcludedAffiliatesID() {
            var queryParams = getQueryParameters();
            return config.excludedAffiliates.id.indexOf(queryParams.affiliate_id) === -1;
        }

        function init() {
            if (isApp() || isToysrus()) {
                return;
            }

            if (hasCookie() && !isQrcode()) {
                config.closeListener();
                return;
            }

            if(userAgent.indexOf("lottemart-app-shopping-did")==-1) {
	            if (isQrcode()) {
	                // $($.render["appInstallInductionLayer2"](config)).appendTo("body");
	            } else {
	                if (notContainExcludedAffiliatesID()) {
	                    // $($.render["appInstallInductionLayer"]()).appendTo("body");
	                }
	            }
            }

            $appLayer = $(".wrap-appsetlayer");
            $appLayer.on("touchmove scroll", function () {
                return false;
            });
            $appLayer.find(".js-close").on("click", function () {
                appInstallLayerClose("web");
            });
            $appLayer.find(".conts a").on("click", function () {
                appInstallLayerClose("app");
            });
        }

        function bakeCookie() {
            $.cookie('appInstallInduction', 'appInstallInductionClose', {
                expires: 5,
                domain: '.lottemart.com',
                path: '/'
            });
        }

        function appInstallLayerClose(resultType) {
            if (resultType === "app" && !isQrcode()) {
                openAppInstall();

                bakeCookie();
            } else if (resultType === "web" && !isQrcode()) {
                bakeCookie();
            }

            $appLayer.fadeOut(300, function () {
                $(this).remove();
            });

            config.closeListener();
        }

        function openAppInstall() {
            var url;
            if ($.utils.isAndroid()) {
                url = $.utils.config("appMarketAndroid");
            } else if ($.utils.isIOS()) {
                url = $.utils.config("appMarketIOS");
            }
            window.open(url);
        }
    };
})(jQuery, window, document);