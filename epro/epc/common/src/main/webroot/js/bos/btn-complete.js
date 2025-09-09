(function(root, factory) {
    "use strict";
    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory);
    } else if (typeof exports !== 'undefined') {
        module.exports = factory(require('jquery'))
    } else {
        root.BtnComplete = factory(root.jQuery);
    }
}(this, function($){
    "use strict";

    function moveCursorToEnd(el) {
        var $el = $(el);

        if (el.setSelectionRange) {
            var len = $el.val().length * 2;
            setTimeout(function() {
                el.setSelectionRange(len, len);
            }, 1);
        } else {
            $el.val($el.val());
        }

        $el.scrollTop(999999);
    }

    var BtnComplete = function (modules, options) {
        var _this = this;
        var _xhr = null;
        var $el = null;
        var $form = null;
        var _timer = null;

        this.el = '';
        this.status = '';
        this.apis = {
            url : '/bos/common/admin/search.do',
            dataType: 'json'
        };
        this.datas = [];

        init();

        function init() {
            for(var prop in options) {
                _this[prop] = options[prop];
            }

            if(_this.el === '') { return; }

            $el = $(_this.el);
            $form = $el.closest('form');

            if($form.length === 0) {return;}

            _get($el.val());
        }
        
        function _triggerEvent(status) {
            for(var i = 0, len = modules.length; i < len; i++) {
                if(modules[i].onEvent) {
                    modules[i].onEvent(status, _this);
                }
            }
        }

        function _get(keyword) {
            _xhr = $.ajax({
                url: _this.apis.url,
                dataType: _this.apis.dataType,
                beforeSend: function () {
                    _triggerEvent('onLoading');
                },
                data: {
                    q: keyword
                },
                success: function (responseData) {
                    _this.datas = responseData.data ? responseData.data : [];
                    _triggerEvent('onComplete');
                }
            });
        }
    };

    return BtnComplete;
}));