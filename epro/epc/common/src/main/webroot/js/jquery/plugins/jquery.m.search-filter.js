$.fn.searchFilter = function (options) {
    var promise = null;
    var SearchFilter = function (opts) {
    	var defaults;
    	if(opts.categoryId && opts.categoryId.substring(0,4) === 'C005'){		//명절몰
        	defaults = {
	            target: '#page-wrapper',
	            activeClass: 'globalSearchLayer',
	            templateName: 'mobileSearchFilter',
	            gtmNumberForButtonReset: options.gtmNumberForButtonReset,
	            gtmNumberForButtonClose: options.gtmNumberForButtonClose,
	            deliveryList: [{
	                gtmNumber: options.gtmNumber,
	                title: 'DELIVERY',
	                name: '명절매장배송',
	                type: 'deliveryView',
	                value: '06[!@!]00'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'DELIVERY',
	                name: '명절택배배송',
	                type: 'deliveryView',
	                value: '06[!@!]01,06[!@!]05'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'DELIVERY',
	                name: '명절업체배송',
	                type: 'deliveryView',
	                value: '06[!@!]03'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'DELIVERY',
	                name: '명절냉장배송',
	                type: 'deliveryView',
	                value: '06[!@!]02'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'DELIVERY',
	                name: '명절냉동택배',
	                type: 'deliveryView',
	                value: '06[!@!]04'
	            }],
	            benefitList: [{
	                gtmNumber: options.gtmNumber,
	                title: 'BENEFIT',
	                name: '덤증정',
	                type: 'benefitChkList',
	                value: '06'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'BENEFIT',
	                name: '1+1',
	                type: 'benefitChkList',
	                value: '04'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'BENEFIT',
	                name: '카드할인',
	                type: 'benefitChkList',
	                value: '15'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'BENEFIT',
	                name: 'L.POINT 혜택',
	                type: 'benefitChkList',
	                value: '13'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'BENEFIT',
	                name: '상품권증정',
	                type: 'benefitChkList',
	                value: '09'
	            }, {
	                gtmNumber: options.gtmNumber,
	                title: 'BENEFIT',
	                name: '즉시할인',
	                type: 'benefitChkList',
	                value: '16'
	            }],
	            searchObject: {},
	            callback: null
	        };
    	} else {
        	defaults = {
    	            target: '#page-wrapper',
    	            activeClass: 'globalSearchLayer',
    	            templateName: 'mobileSearchFilter',
    	            gtmNumberForButtonReset: options.gtmNumberForButtonReset,
    	            gtmNumberForButtonClose: options.gtmNumberForButtonClose,
    	            deliveryList: [{
    	                gtmNumber: options.gtmNumber,
    	                title: 'DELIVERY',
    	                name: '매장배송',
    	                type: 'deliveryView',
    	                value: '01,01[!@!]03'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'DELIVERY',
    	                name: '택배배송',
    	                type: 'deliveryView',
    	                value: '02'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'DELIVERY',
    	                name: '업체택배',
    	                type: 'deliveryView',
    	                value: '04'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'DELIVERY',
    	                name: '바로배송',
    	                type: 'deliveryView',
    	                value: 'express'
    	            }],
    	            benefitList: [{
    	                gtmNumber: options.gtmNumber,
    	                title: 'BENEFIT',
    	                name: '증정',
    	                type: 'benefitChkList',
    	                value: '03,04'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'BENEFIT',
    	                name: '1+1',
    	                type: 'benefitChkList',
    	                value: '02,05,07,09'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'BENEFIT',
    	                name: 'L.POINT 혜택',
    	                type: 'benefitChkList',
    	                value: '01'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'BENEFIT',
    	                name: '카드할인',
    	                type: 'benefitChkList',
    	                value: '10'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'BENEFIT',
    	                name: '살수록 더 싸게',
    	                type: 'benefitChkList',
    	                value: '06'
    	            }, {
    	                gtmNumber: options.gtmNumber,
    	                title: 'BENEFIT',
    	                name: '즉시할인',
    	                type: 'benefitChkList',
    	                value: '08,11,12'
    	            }],
    	            searchObject: {},
    	            callback: null
    	        };
    	}
    	
        var _this = this;

        var params = location.search.replace('?', '').split('&');

        for(var i = 0, len = params.length; i < len; i++) {
            var param = params[i].split('=');
            defaults.searchObject[param[0]] = param[1];
        }

        this.$layer = null;

        this.config = $.extend(true, defaults, options);

        this.render();
    };

    SearchFilter.prototype.reset = function () {
        this.$layer
            .find(':checkbox:checked')
            .prop('checked', false);
    };

    SearchFilter.prototype.search = function () {
        var obj = {};
        var _this = this;
        this.$layer
            .find(':checkbox')
            .each(function () {
                var $el = $(this);
                var name = $el.attr('name');
                var value = $el.is(':checked') ? $el.val() : '';

                if(obj[name]) {
                    obj[name].push(value)
                } else {
                    obj[name] = [value];
                }
            });

        $.each(obj, function (i, v) {
            _this.config.searchObject[i] = v.filter(function (v) {
                return v !== '';
            }).join(',');
        });

        if(_this.config.callback !== null) {
            _this.config.callback(_this.config.searchObject);
        } else {
            location.search = decodeURIComponent($.param(_this.config.searchObject));
        }

    };

    SearchFilter.prototype.toggle = function () {
        if(!this.$layer) {
            return;
        }
        if($(this.config.target).hasClass(this.config.activeClass)) {
            schemeLoader.loadScheme({key: 'showBar'});
        } else {
            schemeLoader.loadScheme({key: 'hideBar'});
        }
        $(this.config.target).toggleClass(this.config.activeClass);
    };

    SearchFilter.prototype.setOptions = function (obj) {
        $.extend(true, this.config, obj);
        this.render();
    };

    SearchFilter.prototype.render = function () {
        var _this = this;

        if(this.$layer !== null) {
            this.$layer.remove();
        }

        this.$layer = $($.render[this.config.templateName](this.config))
            .appendTo($(this.config.target));

        this.$layer
            .find(':checkbox')
            .each(function (i, v) {
                var $v = $(v),
                    value = $v.val(),
                    type = $v.attr('name');

                if(_this.config.searchObject.hasOwnProperty(type)
                && _this.config.searchObject[type].indexOf(value) !== -1) {
                    $v.prop('checked', true);
                }
            });

        this.$layer
            .promise()
            .then(function () {
                _this.$layer
                    .on('click', '[data-id="btnreset"]', function () {
                        _this.reset();
                        return false;
                    })
                    .on('click', '[data-id="btnclose"]', function () {
                        _this.toggle();
                        return false;
                    })
                    .on('click', '[data-id="btnaccordion"]', function () {
                        $(this)
                            .toggleClass('active')
                            .siblings()
                            .removeClass('active');
                        return false;
                    })
                    .on('click', '[data-id="btnsearch"]', function () {
                        if(window.Promise) {
                            promise = new Promise(function (resolve) {
                                _this.toggle();

                                setTimeout(function () {
                                    resolve();
                                }, 300);
                            });
                            promise.then(function () {
                                _this.search();
                            });
                        } else {
                            _this.toggle();
                            _this.search();
                        }
                        return false;
                    })
                    .on('webkitTransitionEnd transitionend', function () {
                        if ($(_this.config.target).hasClass(_this.config.activeClass)) {
                            _this
                                .$layer
                                .next()
                                .fadeIn(200);

                            $('html, body').addClass('mask');
                        } else {
                            _this
                                .$layer
                                .next()
                                .fadeOut(200);

                            $('html, body').removeClass('mask');
                        }
                    })
                    .next()
                    .on('click', function () {
                        _this.toggle();
                        return false;
                    });
            });
    };

    return this.each(function (i, v) {
        var $el = $(v);

        if(!$el.data('searchFilter')) {
            $el.data('searchFilter', new SearchFilter(options));
        }

        $el.on('click', function () {

            $(this).data('searchFilter').toggle();
            return false;
        });
    });
};