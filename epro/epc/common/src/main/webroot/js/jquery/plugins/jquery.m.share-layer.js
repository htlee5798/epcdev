;(function ($, window, document, undefined) {
    'use strict';

    var $window = $(window),
        $document = $(document);

    var ShareLayer = function (element, option) {
        this.$body = null;
        this.element = $(element);
        this.defaults = {
            className: 'share-layer',
            styles: '',
            templateName: 'shareLayer',
            after: null,
            body: 'body'
        };
        this.$layer = null;
        this.clipboard = null;

        $.extend(true, this.defaults, option || {});

        if (this.defaults.body instanceof jQuery) {
            this.$body = this.defaults.body;
        } else {
            this.$body = $(this.defaults.body);
        }

        this.init();
        return this;
    };

    ShareLayer.prototype = {
        init: function () {
            var _this = this;

            $window.on('resize', function () {
                _this.close();
            });
        },
        set: function () {
            var _this = this;
            var _ua = navigator.userAgent.toUpperCase();

            if (this.$body.find('.shareLayer').not(_this.$layer).length > 0) {
                this.$body.find('[data-share]').not(_this.element).each(function (i, v) {
                    var $v = $(v);

                    if ($v.data('shareLayer')) {
                        $v.data('shareLayer').close();
                    }
                });
            }

            if (_this.$layer !== null) {
                _this.close();

                return _this;
            }
            _this.element.addClass('active');

            $document.off('click.close').on('click.close', function (e) {
                var _target = e.target;
                if (_target === _this.element[0] || $(_target).closest(_this.$layer).length > 0) {
                    return;
                }
                _this.close();
            });

            _this.$layer = $($.render[_this.defaults.templateName](_this.defaults)).appendTo(this.$body);
            _this.$layer.find("[data-service]").each(function () {
                $.utils.shareHandler.bind($(this));
            });

            if (Clipboard.isSupported()) {
                _this.clipboard = new Clipboard('.link-copy', {
                    text: function (trigger) {
                        //return "[" + $('meta[property="og:title"]').attr("content") + "] " + location.href;
                    	return "[" + $('meta[property="og:title"]').attr("content") + "] " + $.utils.config( 'LMAppSSLUrlM' ) + location.pathname + location.search;
                    },
                    container: _this.$layer
                });

                _this.clipboard.on('success', function () {
                    alert(view_messages.copylink.success);
                });
                _this.clipboard.on('error', function () {
                    alert(view_messages.copylink.fail);
                });
            } else {
                _this.$layer.find(".link-copy").remove();
            }

            if (schemeLoader.isSupport('sendSMS')) {
                _this.$layer.find("[data-service=sms]").css("display", "inline-block");
            } else {
                _this.$layer.find("[data-service=sms]").remove();
            }

            if ($.utils.config('onlinemallApp')) {
                var appVersion = $.utils.getAppVersion();
                var appLastVersion1 = $.utils.isAndroid() ? "10.80" : "10.14";

                var appIsLast = $.utils.checkAppVersion(appVersion, appLastVersion1);
                if (!appIsLast) {
                    _this.$layer.find("[data-service=kakaostory]").remove();
                    _this.$layer.find("[data-service=band]").remove();
                    _this.$layer.find("[data-service=line]").remove();
                }

                if ($.utils.isIOS()) {
                    var excludeTwitterVersion = $.utils.checkAppVersion(appVersion, '10.22');
                    if (!excludeTwitterVersion) {
                        _this.$layer.find("[data-service=twitter]").remove();
                    }
                }
            }else if(/TOGETHER-[A-Z]+/gi.test(_ua) && !/TOGETHER-MART/gi.test(_ua)){
            	_this.$layer.find("[data-service=band]").remove();
            	_this.$layer.find("[data-service=line]").remove();

            	if(/iphone|ipad/gi.test(_ua)){
            		_this.$layer.find("[data-service=sms]").remove();
            	}
            }


            if (_this.defaults.styles !== '') {
                _this.$layer.css(_this.defaults.styles);
            } else {
                _this.$layer
                    .css({
                        display: 'block',
                        top: _this.element.offset().top + _this.element.height() + 5 - this.$body.offset().top,
                        left: function () {
                            return Math.max(_this.element.offset().left + _this.element.outerWidth() - _this.$layer.outerWidth(true), 0);
                        }
                    });
            }

            _this.$layer
                .find('.js-close')
                .on('click.close', $.proxy(_this.close, _this));
        },
        close: function (e) {
            var _this = this;
            if (_this.$layer !== null) {
                _this.element.removeClass('active');
                _this.$layer.remove();
                _this.$layer = null;
                _this.clipboard.destroy();
                $document.off('click.close');
            }
        }
    };

    $.fn.shareLayer = function (option) {
        return this.each(function (i, v) {
            var $v = $(v);

            if (!$v.data('shareLayer')) {
                $v.data('shareLayer', new ShareLayer(v, option));
            }

            $v.data('shareLayer').set();
        });
    };

    $document.on("click", "[data-share]", function (event) {
        $(this).shareLayer();
        event.preventDefault();
    });
})(jQuery, window, document);