(function(root, factory) {
    "use strict";
    if (typeof define === 'function' && define.amd) {
        define(['jquery', 'Handlebars'], factory);
    } else if (typeof exports !== 'undefined') {
        module.exports = factory(require('jquery'), require('Handlebars'))
    } else {
        root.AutoCompleteRender = factory(root.jQuery, root.Handlebars);
    }
}(this, function($, Handlebars){
    var AutoCompleteRender = function (options) {
        var _this = this;
        var $layer = null;

        var _wrap = '<div><ul><li class="layer-text">검색 결과가 없습니다.</li></ul></div>';
        this.wrapClass = 'auto-complete-layer';
        this.templates = {
            id : '',
            html : '',
            loadingHtml : '<ul><li class="layer-text">Loading...</li></ul>'
        };

        this.selectedData = [];
        this.isAuto = true;
        this.selected = function () {};
        this.submit = function () {};

        $.extend(true, _this, options);

        this.onOpen = function (obj) {
            if($layer === null) {
                $(obj.el).after(_wrap);

                $layer = $(obj.el).next();
                $layer.css('width', obj.width || $(obj.el).width());

                _bindEvent(obj);
            }

            $layer.addClass(_this.wrapClass);

            if ($(obj.el).val().length >= obj.limit) {
                $layer.addClass('active');
            }
        };
        
        this.onLoading = function (obj) {
            "use strict";

            if($layer === null) {
                $(obj.el).after('<div>'+ _this.templates.loadingHtml +'</div>');

                $layer = $(obj.el).next();
                $layer.css('width', obj.width || $(obj.el).width());
            } else {
                $layer.html(_this.templates.loadingHtml);
            }
            
            $layer.addClass(_this.wrapClass + ' active');
        };
        
        this.onClose = function() {
            if($layer === null) { return; }

            $layer
                .removeClass('active')
                .find('ul')
                .find('li')
                .removeClass('active');
        };

        this.onComplete = function (obj) {
            _render(obj);
        };

        function _render(obj) {
            var _template = _this.templates.id ? $(_this.templates.id).html() : _this.templates.html;

            if(_template === '') { return; }
            obj.datas.nameSpace = obj.el.replace(/\#|\./, '');

            if($layer === null) {
                _this.onOpen(obj);
            }

            $layer
                .addClass('active')
                .find('ul')
                .html(Handlebars.compile(_template)(obj.datas));

            _bindEvent(obj);
        }

        function _bindEvent(obj) {
            "use strict";
            $layer
                .off('click.autocomplete')
                .on('click.autocomplete', ':radio, :checkbox', function() {
                var $this = $(this),
                    $li = $this.closest('li'),
                    isRadioButton = $this.is(':radio'),
                    isSelected = $this.is(':checked'),
                    attributes = $li[0].attributes,
                    datas = {};

                if(attributes.length > 0) {
                    for(var i = 0, len = attributes.length; i < len; i++) {
                        if(/^data-*/.test(attributes[i].name)) {
                            datas[camelCase(attributes[i].name.replace('data-', ''))] = attributes[i].value;
                        }
                    }
                }

                _addSelectedData(datas, isRadioButton, isSelected, obj);

                if(_this.isAuto) {
                    _this.onClose();
                    _this.submit();
                }
                })
                .off('focus.autocomplete')
                .on('focus.autocomplete', ':radio, :checkbox', function () {
                    $(this)
                        .closest('li')
                        .addClass('active')
                        .siblings()
                        .removeClass('active');
                })
                .off('keydown.autocomplete')
                .on('keydown.autocomplete', ':radio, :checkbox', function (e){
                    var $this = $(this),
                        $li = $this.closest('li');

                    if(e.keyCode === 40) {
                        $li.next()
                            .find(':checkbox, :radio')
                            .focus();

                        return false;
                    } else if(e.keyCode === 38) {
                        $li.prev()
                            .find(':checkbox, :radio')
                            .focus();

                        return false;
                    } else if(e.keyCode === 13) {
                        $this.trigger('click');
                    }
                })
                .off('mouseenter.autocomplete')
                .on('mouseenter.autocomplete', 'li:not(.layer-text)', function () {
                    $(this)
                        .addClass('active')
                        .siblings()
                        .removeClass('active');
                });

            $('body')
                .off('click.autocomplete')
                .on('click.autocomplete', function (e) {
                if($(e.target).closest($layer).length === 0 && $(e.target).closest(obj.el).length === 0) {
                    _this.onClose();
                }
            });
        }

        function _addSelectedData(data, isRadioButton, isSelected, obj) {
            "use strict";

            if(isRadioButton) {
                _this.selectedData = [data];
            } else {
                if(_this.selectedData.length === 0) {
                    _this.selectedData.push(data);
                } else {
                    var index = 0;
                    var selectedData = _this.selectedData.filter(function(e, i) {
                        if(e.adminId === data.adminId) {
                            index = i;
                        }
                        return e.adminId === data.adminId;
                    });

                    if(selectedData.length === 0) {
                        _this.selectedData.push(data);
                    } else {
                        if(isSelected) {
                            _this.selectedData.push(data);
                        } else {
                            _this.selectedData.splice(index, 1);
                        }
                    }
                }
            }
            _this.selected(_this.selectedData);
        }

        function camelCase(input) {
            return input.toLowerCase().replace(/-(.)/g, function(match, group1) {
                return group1.toUpperCase();
            });
        }
    };

    AutoCompleteRender.prototype.onEvent = function (eventName, eventData) {
        if(this[eventName]) {
            this[eventName](eventData);
        }
    };
    
    AutoCompleteRender.prototype.onLoading = function (eventName, eventData) {
        if(this[eventName]) {
            this[eventName](eventData);
        }
    }

    AutoCompleteRender.prototype.onClear = function () {
        "use strict";
        var _this = this;
        var $layer = $(_this.wrapClass);

        _this.selectedData = [];

        if($layer.length > 0) {
            $layer
                .find(':checkbox, :radio')
                .prop('checked', false);
        }
    };

    Handlebars.registerHelper('itemId', function (options) {
        "use strict";

        var data = '',
            id = '';

        if(options.data) {
            data = Handlebars.createFrame(options.data);
            id = data.root.nameSpace + '_item_' + data.index;
        }

        return id;
    });

    return AutoCompleteRender;
}));