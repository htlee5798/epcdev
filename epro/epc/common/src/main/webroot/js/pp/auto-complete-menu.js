(function(root, factory) {
    "use strict";
    if (typeof define === 'function' && define.amd) {
        define(['jquery'], factory);
    } else if (typeof exports !== 'undefined') {
        module.exports = factory(require('jquery'))
    } else {
        root.AutoComplete = factory(root.jQuery);
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

    var AutoComplete = function (modules, options) {
        var _this = this;
        var _xhr = null;
        var $el = null;
        var $form = null;
        var _timer = null;

        this.el = '';
        this.status = '';
        this.apis = {
            url : '/dc/common/menu/search.do',
            dataType: 'json'
        };
        this.datas = [];
        this.limit = 2;

        init();

        function init() {
            for(var prop in options) {
                _this[prop] = options[prop];
            }

            if(_this.el === '') { return; }

            $el = $(_this.el);
            $form = $el.closest('form');

            if($form.length === 0) {return;}

            $el.on('focus', function(e) {
                var q = e.target.value;

                moveCursorToEnd(e.target);
                if (q.length >= _this.limit) {
                    _triggerEvent('onOpen');
                } else {
                    _triggerEvent('onClose');
                }
                })
                .on('keyup', function (e) {
                    var q = e.target.value;

                    clearTimeout(_timer);

                    _timer = setTimeout(function () {
                        if(e.keyCode === 40) {
                            $el
                                .next()
                                .find('li')
                                .eq(0)
                                .find(':radio, :checkbox, a')
                                .focus();
                        } else {
                            if (q.length >= _this.limit) {
                                if (_xhr !== null) {
                                    _xhr.abort();
                                    _xhr = null;
                                    _triggerEvent('onAbort');
                                }
                                // 검색어 체크
								if (checkPattern(q)) {
									_get(q);
								} else {
									alert("검색어는 한글, 영문자, 숫자만 가능합니다.");
								}
                            } else {
                                _triggerEvent('onClose');
                            }
                        }
                    }, 300);
                });
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
                    , workerType : _this.el
                },
                success: function (responseData) {
                    _this.datas = responseData.data ? responseData.data : [];
                    _triggerEvent('onComplete');
                }
            });
        }
        
       /**
		 * 검색어 규칙 체크 (한/영 문자열, 숫자 허용)
		 *  @returns 허용 여부 {Boolean}
		 */
		function checkPattern(keyword) {
			const pattern = /^[a-zA-Zㄱ-ㅎ가-힣0-9 ]*$/g;
			if (pattern.test(keyword)) { return true; }
			else { return false; }
		}
    };

    return AutoComplete;

}));