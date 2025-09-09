(function (root, factory) {
    "use strict";

    if (typeof define === 'function' && define.amd) {
        define(['jquery'],factory);
    } else if (typeof exports !== 'undefined') {
        module.exports = factory(require('jquery'))
    } else {
        root.$.fn.viewTypeChange = factory(jQuery);
    }
}(window, function ($) {
    var _viewTypeChange = function (options) {
        "use strict";

        var Module = function (element, opts) {
            var _this = this;

            this._element = element;
            this.defaults = proxy();
            this.changeView = _changeView;

            function proxy () {
                try {
                    return new Proxy({
                        el: '#tgtGoodsTrans',
                        viewType: '',
                        isSupportBrowser : true
                    }, {
                        set: function (target, key, value, receiver) {
                            if (key === 'viewType') {
                                _changeView(value);
                                sessionStorage.setItem('VT', value);
                            }
                            return Reflect.set(target, key, value, receiver);
                        },
                        get: function (target, key, receiver) {
                            return Reflect.get(target, key, receiver);
                        }
                    });
                } catch (e) {
                    return {
                        el: '#tgtGoodsTrans',
                        viewType: '',
                        isSupportBrowser : false
                    };
                }
            }

            init();

            function init() {
                $.extend(true, _this.defaults, opts || {});
            }

            function _changeView(type) {
                switch (type) {
                    case 'L':
                        $(_this.defaults.el).attr('class', 'type-list');
                        $(element).attr('class', 'type-list');
                        break;
                    case 'G' :
                        $(_this.defaults.el).attr('class', 'type-gallery');
                        $(element).attr('class', 'type-gallery');
                        break;
                    default :
                        $(_this.defaults.el).attr('class', 'type-image');
                        $(element).attr('class', 'type-image');
                        break;
                }
            }
        };

        Module.prototype.onClick = function() {
            var _this = this;
            var $target = $(_this._element);

            if($target.hasClass('type-list')) {
                this.defaults.viewType = 'G';
            } else if($target.hasClass('type-gallery')) {
                this.defaults.viewType = 'I';
            } else {
                this.defaults.viewType = 'L';
            }

            if(!this.defaults.isSupportBrowser) {
                this.changeView(this.defaults.viewType);
            }
        };

        Module.prototype.onChange = function (viewType) {
            this.defaults.viewType = viewType;

            if(!this.defaults.isSupportBrowser) {
                this.changeView(this.defaults.viewType);
            }
        };

        return this.each(function (i, v) {
            $(v).data('viewTypeChange', new Module(v, options));
        });
    };

    return _viewTypeChange;
}));