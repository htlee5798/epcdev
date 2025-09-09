(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(['jquery'], factory);
    } else if (typeof module === 'object' && module.exports) {
        // Node/CommonJS
        module.exports = function( root, jQuery ) {
            if ( jQuery === undefined ) {
                // require('jQuery') returns a factory that requires window to
                // build a jQuery instance, we normalize how we use modules
                // that require this pattern but the window provided is a noop
                // if it's defined (how jquery works)
                if ( typeof window !== 'undefined' ) {
                    jQuery = require('jquery');
                }
                else {
                    jQuery = require('jquery')(root);
                }
            }
            factory(jQuery);
            return jQuery;
        };
    } else {
        // Browser globals
        factory(jQuery);
    }
}(function ($) {
    $.fn.openZoomView = function () {
        var CURRENT_OS = $.utils.isiOSLotteMartApp() ? 'ios'
            : ($.utils.isAndroidLotteMartApp() ? 'android' : 'etc');

        return this.each(function() {
            var $this = $(this);
            var url = $this.attr('href');
            var title = $this.data('openZoomViewTitle')
                    ? $this.data('openZoomViewTitle') : '상품정보 확대보기';
            
            //임시조치
            // if ($.utils.isIOS()
            //     && (window.webkit
            //     && window.webkit.messageHandlers
            //     && window.webkit.messageHandlers.LOTTEMART)) {
            //
            //     window.webkit.messageHandlers.LOTTEMART.postMessage(JSON.stringify({
            //         messageName: 'showZoomViewUrl',
            //         messageInfo: {
            //             title: title,
            //             openUrl: url
            //         }
            //     }));
            //
            // } else if (window.LOTTEMART
            //     && window.LOTTEMART['showZoomViewUrl']) {
            //
            //     window.LOTTEMART.showZoomViewUrl(title, CURRENT_OS === 'ios' ? url : encodeURIComponent(url));
            // } else {
                window.location.href = url;
            // }
        });
    };
}));