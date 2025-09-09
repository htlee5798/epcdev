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
    var hash = '',
        cid = '',
        loadingFooterClass = 'footer-loading',
        isLoading = false,
        hasMoreProducts = true,
        categoryName = '',
        $footer = $('#footer'),
        $pageWrapper = '',
        $productContainer = '',
        currentPage = 1,
        infiniteScroll = null;

    var _init = function (obj) {
        var $frmParams = $('#frmParams');

        hash = obj.hash;
        cid = obj.cid;
        categoryName = obj.categoryName;
        isLoading = false;

        $pageWrapper = $('[data-pageid=' + hash + ']');
        $productContainer = $pageWrapper.find('[name=productContainer]');
        currentPage = Number(
            $frmParams.find('[name="currentPage"]').val()
        );
        hasMoreProducts = $('#frmParams')
            .find('[name="hasMoreProducts"]')
            .val() === 'true';

        infiniteScroll = new _InfiniteScroll({
            container: $pageWrapper,
            callback: function () {
                renderMoreProducts();
            }
        });

        setCategoryId();
        setViewConfig();
        renderMoreProducts();

        $(window).on('scroll.mainScroll', function () {
            infiniteScroll.setPosition($(window).scrollTop());
        });

        if (!hasMoreProducts) {
            showFooter();
            sendGAEvent();
        }

        if ($pageWrapper.find('[name=btnSubCategory]').length > 0) {
            renderMoreProducts();
            bindEvent();
        } else {
            showFooter();
        }
    };

    function setCategoryId(categoryId) {
        $('#frmParams').find('[name="CategoryID"]').val(
            categoryId
            || $pageWrapper
                .find('[name=btnSubCategory].active')
                .data('categoryId')
        );
    }

    function setViewConfig() {
        var name = hash + 'categoryName';

        $.utils.config(name, categoryName);
    }

    function bindEvent() {
        $pageWrapper.find('[name=btnSubCategory]').on('click', function () {
            var $this = $(this);

            $this
                .addClass('active')
                .siblings()
                .removeClass('active');

            setCategoryId($this.data('categoryId'));

            $('#frmParams')
                .find('[name="currentPage"]')
                .val(currentPage = 1)
                .end()
                .find('[name="hasMoreProducts"]')
                .val(hasMoreProducts = true)

            $productContainer.empty();
            renderMoreProducts();
        });
    }

    function showFooter() {
        var $footer = $('#footer');

        if ($footer.hasClass(loadingFooterClass)) {
            $footer.removeClass(loadingFooterClass);
        }
    }

    function sendGAEvent() {
        ga('send', 'event', {
            eventAction: '모든상품보기',
            eventCategory: '#몰구분=Mart #기기=M #분류=상품탐색PLP(메인GNB) #페이지명=' + $.utils.config(hash + 'categoryName') + ' #URL=/corners.do?returnCategoryId=' + hash,
        });
    }

    function renderMoreProducts() {
        var $frmParams = $('#frmParams');
        if (window.getHistoryBack()) {
            window.setHistoryBack(false);
            return;
        }
        if (!isLoading && hasMoreProducts) {
            isLoading = true;

            $.api.get({
                apiName: 'mobileCateProductList',
                dataType: 'html',
                data: $.utils.serializeObject($('#frmParams')),
                isShowMoreBar: true,
                $container: $pageWrapper,
                successCallback: function (res) {
                    renderProducts(res);

                    isLoading = false;
                    currentPage = currentPage + 1;
                    $frmParams
                        .find('[name="hasMoreProducts"]')
                        .val(hasMoreProducts = getHasMoreProducts())
                        .end()
                        .find('[name="currentPage"]')
                        .val(
                            currentPage
                        );

                    if (!hasMoreProducts) {
                        showFooter();
                        sendGAEvent();
                    }

                    if(window.imageLazyLoad) {
                        window.imageLazyLoad.setLazy();
                        window.imageLazyLoad.load();
                    }
                }
            });
        }
    }

    function getHasMoreProducts() {
        var $pageData = $productContainer
            .find('.page-data')
            .last();

        return $pageData ?
            $pageData.data('totalcount') > $pageData.data('lastnum')
            : false;
    };

    function renderProducts(productListHtml) {
        $productContainer.append(productListHtml);
    }

    ga('send', 'event', {
        eventAction: '페이지로드성능체크',
        eventCategory: '#몰구분=Mart #기기=M #분류=메인GNB #페이지명=' + categoryName + ' #URL=/corners.do?returnCategoryId=' + cid,
        eventLabel: '선택메뉴=' + categoryName
    });

    return {
        init: function (obj) {
            $footer.addClass(loadingFooterClass);
            _init(obj);
        }
    }
}));