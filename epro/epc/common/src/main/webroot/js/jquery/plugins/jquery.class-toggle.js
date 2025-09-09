;(function( $, window, document, undefined ) {
    var ClassToggle = function (obj, options) {
        this.element = $(obj);
        this.options = {current: 'active'};

        $.extend(this.options, options);
    };
    ClassToggle.prototype = {
        init: function () {
            var _this = this;

            if (_this.element.attr('data-toggle-event') == 'true') {
                return false;
            }

            _this.element.attr('data-toggle-event', 'true').on('click', function (e) {
                if (e) e.preventDefault();
                _this.set.apply(_this, [this]);
            });

            return _this;
        },
        set: function (obj) {
            var _this = this;
            _this.setTabs(obj);
            if (obj.hash) _this.setContents(obj);

            return _this;
        },
        setTabs: function (obj) {
            var _this = this,
                current = _this.options.current;
            ($(obj).hasClass(current)) ? $(obj).removeClass(current) : $(obj).addClass(current).siblings().removeClass(current);
        },
        setContents: function (obj) {
            var _this = this,
                current = _this.options.current;
            ($(obj.hash).hasClass(current)) ? $(obj.hash).removeClass(current) : $(obj.hash).addClass(current).siblings().removeClass(current);

            $('[class*=close], .mask').on('click', function (e) {
                e.preventDefault();
                $(obj).removeClass(current);
                $(obj.hash).removeClass(current);
            });
        }
    };
    $.fn.classToggle = function (options) {
        this.each(function () {
            return new ClassToggle(this, options).init();
        });
    };
})( jQuery, window, document );