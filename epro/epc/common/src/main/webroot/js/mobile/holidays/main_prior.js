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
    var hash = '';
    var $container = '';
    var $stickyTabMenu = null;
    var $bestPanelContainer = null;
    var $holiMainInfotab = null;
    var $pricePanelContainer = null;
    var $specialPanelContainer = null;
    var $bigPanelContainer = null;
    var $recommendPanelContainer = null;
    var $middleBaneBannerSection = null;
    var $otherPrice1 = null;
    var stickyElementTop = 0;
    var stickyElementEnd = 0;
    var holiMainPosition = {};
    var loadingFooterClass = 'footer-loading';
    var $footer = $('#footer');

    var _init = function (obj) {
        hash = obj.hash;

        $container = $('[data-pageid="'+ hash + '"]');
        $stickyTabMenu = $container.find('.holi-tab-sticky-wrap');
        $bestPanelContainer = $container.find( '#bestPanelContainer' );
        $holiMainInfotab = $container.find( '#holiMainInfotab' );
        $pricePanelContainer = $container.find('#pricePanelContainer');
        $specialPanelContainer = $container.find('#specialPanelContainer');
        $bigPanelContainer = $container.find('#bigPanelContainer');
        $recommendPanelContainer = $container.find('#recommendPanelContainer');
        $middleBaneBannerSection = $container.find('#middleBaneBannerSection');
        $otherPrice1 = $container.find('#holiMdBestContainer');

        stickyElementTop = 0;
        stickyElementEnd = 0;
        holiMainPosition = {};

        if(window.getHistoryBack && window.getHistoryBack()) {
            window.setHistoryBack(false);
		} else {
        	 if($('[name=hotFlag]').length == 0){
             	if($('[name=categoryByPriceTab].active').data('categoryId') != ''){//가격대별 선물 세트
             		showProductsByPrice($('[name=categoryByPriceTab].active'));
             	}
             	if($('[name=bestCategoryTab].active').data('categoryId') != ''){ //카테고리별 선물세트
             		showProductsByCategory($('[name=bestCategoryTab].active'));
             	}
             	if($('[name=categoryBySpecialTab].active').data('categoryId') != ''){ //설 특별혜택 선물세트
             		showProductsBySpecial($('[name=categoryBySpecialTab].active'));
             	}
             	if($('[name=categoryByBigTab].active').data('categoryId') != ''){ //대량구매 추천세트
             		showProductsByBig($('[name=categoryByBigTab].active'));
             	}
             	if($('[name=otherPriceBnr].active').data('categoryId') != ''){ //MD 추천상품
             		showotherPrice($('[name=otherPriceBnr].active'));
             	}
             	if($('[name=recommend].active').data('categoryId') != ''){	//개인화 명절추천상품
             		showRecommendProducts($('[name=recommend].active'));        		
             	} 
             }
        }

        setEvent();
        setEventForGA();
    };

    function setEvent(){
        $container
            .find('[name=categoryByPriceTab]')
            .off('click')
            .on('click', function(e){
                e.preventDefault();

                var $t = $(this);

                showProductsByPrice($t);

                sendEventToGAForHolidays({
                    eventAction : '가격대별 상품 보기',
                    eventLabel : '#가격대별=' + $t.text().trim()
                });

                cateTabScrTop();
            });

        $container.find('[name=bestCategoryTab]').off('click').on('click', function(){
            var $t = $(this);

            showProductsByCategory($t);
            cateTabScrTop();
        });

        $(window).on('scroll.main', function() {
            setStickyElementPosition();
        });

        setStickyClickEvent();

    }

    function setStickyElementPosition() {
        var position = getPosition($holiMainInfotab);
        var	isChange = stickyElementTop !== position.top
            || stickyElementEnd !== position.end;


        if( isChange ) {
            stickyElementTop = position.top;
            stickyElementEnd = position.end;

            setHoliMainPosition();
        }
    }

	function setHoliMainPosition() {
		var nDataIndex = $holiMainInfotab.find("a[name=infotab].active").data("index") ;
		var $this = $holiMainInfotab.find('[name=infoPanel]').eq(nDataIndex),
            position = getPosition($this);

        holiMainPosition = {
            top : position.top,
            end : position.end
        };
    }

    function getPosition($criterion){
        var top = $criterion.offset().top;

        return {
            top : top,
            end : top + $criterion.outerHeight( true )
        };
    }

    function setStickyClickEvent(){
        $('a[name=infotab]').on('click', function(e) {
            e.preventDefault();

            var index = $(this).data("index");

            $(this)
                .addClass('active')
                .siblings()
                .removeClass('active');

            $holiMainInfotab
                .find('[name="infoPanel"]')
                .eq(index)
                .addClass('active')
                .siblings(':not(.holi-price-tab)')
                .removeClass('active');

            $(window).scrollTop(0);
        });
    }

    function showProductsByPrice($tab) {

        var data = {
                sortType : 'DISPLAY_SEQ_ASC',
                ROW_SIZE : 20
            },
            templateParams = {
                categoryNameHtml : '가격대별 선물세트',
                successCallback : function(
                    productListHtml,
                    params
                ){
                    var _params = $.extend({
                            productListHtml : productListHtml,
                            classId : 'byprice-article'
                        }, params),
                        html = $.render.mobileHolidaysProductListPanel(_params);

                    $pricePanelContainer.append(html);
                    $pricePanelContainer.find('.prditem-groups').removeClass('type-list');
                },
                afterRender : function(params){
                    $.ui.helper.activeProductsWithTab(
                        $tab,
                        params.getContainer()
                    );
                }
            };

        $.ui.helper.showProductsByTab(
            extendData(data, $tab),
            extendTemplateParams(
                templateParams,
                $tab,
                $pricePanelContainer
            )
        );
    }

    function showProductsByCategory($tab) {
        var data = {
                sortType : 'DISPLAY_SEQ_ASC',
                ROW_SIZE : 20,
                CATEGORY_ID : $tab ? $tab.data('categoryId') : ''
            },
            templateParams = {
                categoryNameHtml : '카테고리별 선물세트',
                successCallback : function(
                    productListHtml,
                    params
                ){
                    var _params = $.extend({
                            productListHtml : productListHtml,
                            classId : 'category-article'},
                        params
                        ),
                        html = $.render.mobileHolidaysProductListPanel(_params);

                    $bestPanelContainer.append(html);
                    $bestPanelContainer.find('.prditem-groups').removeClass('type-list');
                },
                afterRender : function(params){
                    $.ui.helper.activeProductsWithTab($tab, params.getContainer());
                }
            };

        $.ui.helper.showProductsByTab(
            extendData(data, $tab),
            extendTemplateParams(templateParams, $tab, $bestPanelContainer)
        );
    }

	function showProductsBySpecial($tab) {
		var data = {sortType : 'DISPLAY_SEQ_ASC', ROW_SIZE : 2}
		, templateParams = {
			categoryNameHtml : '설 특별혜택 선물세트'
			, successCallback : function(productListHtml, params){
				var _params = $.extend({productListHtml : productListHtml, classId : 'byprice-article'}, params)
				, html = $.render.mobileHolidaysProductListPanel(_params);

				$specialPanelContainer.append(html);
				$specialPanelContainer.find('.prditem-groups').removeClass('type-list');
			}
			,afterRender : function(params){
				$.ui.helper.activeProductsWithTab($tab, params.getContainer());
			}
		};

		$.ui.helper.showProductsByTab(
			extendData(data, $tab)
			, extendTemplateParams(templateParams, $tab, $specialPanelContainer)
		);
	}

	function showProductsByBig($tab) {
		var data = {sortType : 'DISPLAY_SEQ_ASC', ROW_SIZE : 2, CATEGORY_ID : $tab ? $tab.data('categoryId') : ''}
		, templateParams = {
			categoryNameHtml : '대량구매 추천세트'
			, successCallback : function(productListHtml ,params){
				var _params = $.extend({ productListHtml : productListHtml, classId : 'category-article'}, params)
				, html = $.render.mobileHolidaysProductListPanel(_params);

				$bigPanelContainer.append(html);
				$bigPanelContainer.find('.prditem-groups').removeClass('type-list');
			}
			, afterRender : function(params){
				$.ui.helper.activeProductsWithTab($tab, params.getContainer());
			}
		};

		$.ui.helper.showProductsByTab(
			extendData(data, $tab),
			extendTemplateParams(templateParams, $tab, $bigPanelContainer)
		);
	}


    function showotherPrice($tab) {
        var data = {
        		apiName : 'getCategoryProductListPage',
                imageSize : 500,
                CATEGORY_ID : $tab ? $tab.data('categoryId') : '',
                ROW_SIZE : $tab.data('rowSize'),
                returnPagePath : $tab.data('pagePath'),
                sortType : 'DISPLAY_SEQ_ASC'
            },
            templateParams = {
                hasMoreButton : false,
                successCallback : function(productListHtml, params){
                    var _params = $.extend({productListHtml : productListHtml}, params),
                        html = $.render.mobileHolidaysBannerProductListPanel(_params);
                    $otherPrice1.append(html);
                    $otherPrice1.append("<input type='hidden' name='hotFlag'>");
                },
                afterRender : function(params){
                    $.ui.helper.activeProductsWithTab($tab, params.getContainer());
                }
            };

        $.ui.helper.showProductsByTab(data, extendTemplateParams(templateParams, $tab, $otherPrice1));
    }

    function extendData(data, $tab) {
        var _data = {
            CATEGORY_ID : $tab ? $tab.data('categoryId') : '',
            ROW_SIZE : 10,
            returnPagePath : 'mobile/holidays/includes/productList'
        };

        return $.extend(_data, data);
    }

    function extendTemplateParams(templateParams, $tab, _$container) {
        var _templateParams = {
            hasMoreButton : true,
            categoryNameHtml : $tab.html(),
            siteLocation : $tab.data('siteLocation'),
            categoryId : $tab.data('categoryId'),
            categoryListUrl : '/mobile/cate/productList.do',
            getContainer : function(){
                return _$container.find('[name=productPanel][data-category-id=' + $tab.data('categoryId') + ']');
            }
        };

        return $.extend(_templateParams, templateParams);
    }

    function setEventForGA(){
        $container.on( 'click', '.holi-top-banner a', function() {
            var $this = $( this );

            sendEventToGAForHolidays({
                eventAction : '명절선물탭 배너',
                eventLabel : '#컨텐츠=' + $this.find( 'img' ).attr( 'alt' )
            });
        });

        $middleBaneBannerSection.on( 'click', 'a', function(){
            var $this = $( this );

            sendEventToGAForHolidays({
                eventAction : '안내/가이드 배너',
                eventLabel : '#컨텐츠=' + $this.find( 'img' ).attr( 'alt' )
            });
        });

        $container.on( 'click', '.basket', function() {
            var $panelContainer = $(this).closest('[id$=PanelContainer]');

            sendEventToGAForProduct('addBasket', $panelContainer, $(this));
        }).on( 'click' , '[data-id="productImageGA"]', function() {
            var $panelContainer = $(this).closest('[id$=PanelContainer]');

            sendEventToGAForProduct('goProductDetail', $panelContainer, $(this));
        }).on( 'click', '.holi-btn-list-bottom', function() {
            var $panelContainer = $(this).closest('[id$=PanelContainer]');

            sendEventToGAForProduct('getMoreProduct', $panelContainer, $(this));
        });
    }

    function sendEventToGAForHolidays(data){
        var defaults = {
            eventCategory : '#몰구분=Mart #기기=M #분류=상품탐색PLP(메인GNB) #페이지명=명절몰 #URL=/corners.do?returnCategoryId=C099001300080021',
            eventAction : data.eventAction,
            eventLabel : data.eventLabel
        };

        if(data.eventValue !== undefined){
            $.extend(defaults, data);
        }

        ga( 'send', 'event', defaults);
    }

    function sendEventToGAForProduct(eventType, $container, $btn){
        var $els, addtionalLabelTitle;

        if($container.is($bestPanelContainer)){
            addtionalLabelTitle = '#전시카테고리=';
            $els = getBestPanelElements($btn);
        } else {
            addtionalLabelTitle = '#가격대=';
            $els = getPricePanelElements($btn);
        };

        sendEventToGAForHolidays(
            createGAData(eventType, $els, addtionalLabelTitle)
        );
    }

    function getBestPanelElements($btn){
        var elements = {
            $this : $btn,
            $article : $btn.closest( 'article' ),
            $i : $btn.find( 'i' )
        };

        return $.extend(elements, { $label : elements.$article.find( 'h3' ).find( 'b' ) });
    }

    function getPricePanelElements($btn){
        var elements = {
            $this : $btn,
            $i : $btn.find( 'i' ),
            $tab : $container.find('[name=categoryByPriceTab].active'),
        };

        return $.extend(elements, { $label : elements.$tab });
    }

    function createGAData(eventType, $els, addtionalLabelTitle){
        var eventTypes = {
            getMoreProduct : {
                eventAction : '더보기',
                eventLabel : $els.$label.text().trim()
            },
            addBasket : {
                eventAction : '장바구니 담기',
                eventLabel : '#Seq=' + $els.$i.data( 'order' ) + ' #상품명=' + $els.$this.data( 'prodTitle' ),
                eventValue : parseInt( $els.$i.data( 'price' ), 10 )
            },
            goProductDetail : {
                eventAction : '상품 상세 탐색',
                eventLabel : '#Seq=' + $els.$this.data( 'order' ) + ' #상품명=' + $els.$this.data( 'productName' ),
                eventValue : parseInt( $els.$this.data( 'price' ), 10 )
            }
        };

        var data = eventTypes[eventType];
        if(eventType !== 'getMoreProduct'){
            data.eventLabel += ' #뷰타입=리스트형 #CID=' + $els.$this.data( 'categoryId' )
                + ' #PID=' + $els.$this.data( 'prodCd')
                + addtionalLabelTitle + $els.$label.text().trim();
        };

        return data;
    }
    
    function showRecommendProducts($tab) {			//개인별 명절 추천상품
        var data = {
                apiName : 'getRecommProdList',
                siteLocation : $tab.val(),
                returnPagePath : 'mobile/holidays/includes/productList'
            },
            templateParams = {
                siteLocation : 'temp',
                getContainer : function(){
                    return $recommendPanelContainer.find('[name=productPanel][data-category-id="999999"]');
                },
                successCallback : function(productListHtml, params){
                    var _params = $.extend({
                            productListHtml : productListHtml,
                            classId : 'person-article'},
                        params
                    );

                    _params.categoryId = "999999";
                    html = $.render.mobileHolidaysProductListPanel(_params);

                    $recommendPanelContainer.find("article").remove();
                    $recommendPanelContainer.find(".holi-btn-list-bottom").remove();
                    $recommendPanelContainer.append(html);
                    $recommendPanelContainer.find('.prditem-groups').removeClass('type-list');

                },
                afterRender : function(params){
                    $recommendPanelContainer.find(".holi-btn-list-bottom").remove();
                }
            };
        $.ui.helper.renderProducts(data, templateParams);
    }
    // 2019 추석 명절몰 카테고리 스크롤
    function cateTabScrTop() {
        var $holiTab = $('.holi-tab');
        var $cateTab = $('.tab-menu-category');
        var $priceTab = $('.tab-menu-price');
        var flagDestScrTop = ($('#mainGnb').height() + $('#header').height()) || ($('.ta-main-nav').height() + $('.ta-header-main').height());
        var winScrTop = $('.holi-main-kv').height() + flagDestScrTop;
        var cateScrTop = $('.holi-chu-category').offset().top - flagDestScrTop - $holiTab.height();
        var priceScrTop = $('.holi-chu-price').offset().top - flagDestScrTop - $holiTab.height();

        if($holiTab.offset().top > winScrTop && $cateTab.hasClass('active')){
            $(window).scrollTop(cateScrTop);
            $('body').addClass('toggle-main-header-ta');
        } else if($holiTab.offset().top > winScrTop && $priceTab.hasClass('active')){
            $(window).scrollTop(priceScrTop);
            $('body').addClass('toggle-main-header-ta');
        }
    }

    return {
        init: function (obj) {
            $footer.removeClass(loadingFooterClass);

            _init(obj);
        }
    }
}));