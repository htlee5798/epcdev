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
        var $wrapSpecialTab = $('#wrapSpecialTab');

        getSpecialList();

        $wrapSpecialTab.on('click', 'button', function (e) {
            e.preventDefault();

            var categoryId = $(this).data('categoryId');

            $(this)
                .addClass('active')
                .siblings()
                .removeClass('active');

            $('.wrap-special-content').each(function () {
                var $el = $(this);

                if (categoryId === 'all') {
                    $el.show();
                } else if ($el.data('categoryId') === categoryId) {
                    $el.show();
                } else {
                    $el.hide();
                }
            });
        });

        function getSpecialList() {
            if(window.getHistoryBack && window.getHistoryBack()) {
                window.setHistoryBack(false);
                return;
            }

            $.api.get({
                apiName: 'getMainSpecialList',
                successCallback : successCallback
            });
        }

        function successCallback(responseData) {
            if(responseData.isSucceed) {
                var $div = document.createElement('div');
                $div.id = 'wrapSpecialMain';

                var $docFrag = document.createDocumentFragment();

                $div.innerHTML = $.render.mobileSpecialMainWrapper(
                    responseData.data
                );

                $docFrag.appendChild($div);

                document
                    .getElementById('wrapMainSpecial')
                    .appendChild($docFrag);
            }
        }
    };
    return {
        init: function () {
            $footer.removeClass(loadingFooterClass);
            _init();
        }
    }
}));