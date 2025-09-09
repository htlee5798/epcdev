(function(root, factory) {
    "use strict";
    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory);
    } else if (typeof exports !== 'undefined') {
        module.exports = factory(require('jquery'), require('Handlebars'))
    } else {
        root.HashTag = factory(root.jQuery, root.Handlebars);
    }
}(this, function($, Handlebars){
    "use strict";

    var HashTag = function (options) {
        var _this = this;
        this.tagData = [];
        this.el = '';
        this.templates = {
            id : '',
            html : ''
        };
        this.callback = function () {};

        init();

        function init() {
            for(var prop in options) {
                _this[prop] = options[prop];
            }

            if(_this.el !== '') {
                var $el = $(_this.el);

                $el.on('click', '.btnDelete', function () {
                    $(this)
                        .parent()
                        .remove();

                    var returnData = [];

                    $el.children().each(function() {
                        var data = this.dataset || {};
                        returnData.push(data);
                    });

                    _this.tagData = returnData;
                    _this.callback(returnData);

                    return false;
                });
            }
        }
    };

    HashTag.prototype.make = function (data) {
        var _this = this;
        var $el = $(_this.el);
        var $tag = null;
        var _template = '';

        if($el.length > 0) {
            var _template = _this.templates.id ? $(_this.templates.id).html() : _this.templates.html;

            if(_template === '') { return; }

            $tag = $(Handlebars.compile(_template)({data : data}));

            $el.html($tag);

            this.callback(data);
        }
    };
    HashTag.prototype.removeAll = function () {
        var _this = this;
        $(_this.el).empty();

        if(this.tagData.length !== 0) {
            this.tagData = [];
        }

        this.callback([]);
    };
    HashTag.prototype.add = function (data) {
        var _this = this;

        this.tagData.push(data[0]);
        this.make(_this.tagData);
    };

    return HashTag;
}));