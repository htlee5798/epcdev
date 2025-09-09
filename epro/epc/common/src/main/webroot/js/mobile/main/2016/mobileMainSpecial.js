(function (root, factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define([], factory);
    } else if (typeof module === 'object' && module.exports) {
        // Node. Does not work with strict CommonJS, but
        // only CommonJS-like environments that support module.exports,
        // like Node.
        module.exports = factory();
    } else {
        // Browser globals (root is window)
        root.returnExports = factory();
    }
}(typeof self !== 'undefined' ? self : this, function() {
    var loadingFooterClass = 'footer-loading',
        $footer = $('#footer');

    var _init = function () {
        if (window.getHistoryBack()) {
            window.setHistoryBack(false);
        }

        var defaultSet = {
                $this : '',
                $tab : $('.special-tab'),
                $activeTab : $('.special-tab.active')
            },
            _specialTabSortingFunc = {
                wrapActive : function($this){
                    $this.closest('.wrap-main-special').attr('class', 'wrap-main-special').addClass($this.data('target'));
                },
                thisActive : function(){
                    defaultSet.$this.addClass('active').siblings('button').removeClass('active');
                    _specialTabSortingFunc.wrapActive(defaultSet.$this);
                }
            };

        defaultSet.$tab.on('click', function(){
            defaultSet.$this = $(this);
            _specialTabSortingFunc.thisActive();
        });
        _specialTabSortingFunc.wrapActive(defaultSet.$activeTab);
    };
    return {
        init: function () {
            $footer.removeClass(loadingFooterClass);
            _init();
        }
    }
}));