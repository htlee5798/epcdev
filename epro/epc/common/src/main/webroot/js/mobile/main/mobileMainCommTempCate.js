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
        loaindgFooterClass = 'footer-loading',
        cid = '',
        categoryName = '',
        $container = '',
        $goodsWrapper = '',
        $goodsListUl = '',
        $form = '',
        $footer = $( '#footer' ),
        $currentPage = '',
        currPage = 1,
        nowLoading = false,
        isEnd = false,
        infiniteScroll = null;

    var _init = function (obj) {
        hash = obj.hash;

        nowLoading = false;
        isEnd = false;

        cid = obj.cid;
        categoryName = obj.categoryName;

        $container = $('[data-pageid="' + hash + '"]');
        $goodsWrapper = $container.find('div.product-wrapper');
        $form = $container.find('#productForm');
        $currentPage = $form.find('[name="currentPage"]');
        currPage = Number($currentPage.val());

        infiniteScroll = new _InfiniteScroll({
            container: $container,
            callback: function () {
                getList(true);
            }
        });

        $goodsListUl = $goodsWrapper.find('.prditem-groups');
        isEnd = $goodsListUl.find('.list-lastgoods, .list-empty').length > 0;

        getList();

        $(window).on('scroll.mainScroll', function () {
            infiniteScroll.setPosition(
                $(window).scrollTop()
            );
        });

        $container.on('click', 'div.subcate-boxtype a', function () {
            var $this = $(this);

            $this.addClass('active')
                .siblings()
                .removeClass('active');

            $form
                .find('input[name="CategoryID"]')
                .val(
                    $this.data('categoryid')
                );

            $currentPage.val(
                currPage = 1
            );
            isEnd = false;
            $goodsListUl.empty();
            getList();

            return false;
        });

        function getList(isMore) {
            if (window.getHistoryBack()) {
                window.setHistoryBack(false);
                return;
            }
            if(nowLoading){
                return;
            }
            if(isEnd) {
                if($goodsListUl.find('.list-lastgoods').length > 0) {
                    $footer.removeClass( loaindgFooterClass );

                    ga('send', 'event', {
                        eventAction: '모든상품보기',
                        eventCategory: '#몰구분=Mart #기기=M #분류=상품탐색PLP(메인GNB) #페이지명=단독특가 #URL=/corners.do?returnCategoryId=' + hash,
                    });
                }
                return false;
            }

            nowLoading = true;

            var apiOptions = {
                apiName : 'mobileSellingPriceList',
                data : $.utils.serializeObject($form),
                dataType : 'html',
                successCallback : function (data) {
                    if (isMore) {
                        $container.moreBar(false);
                    }
                    nowLoading = false;
                    currPage = currPage + 1;
                    $currentPage.val(currPage);
                    renderList(data, isMore);
                }
            };

            if(isMore) {
                $container.moreBar();
            }
            $.api.get(apiOptions);
        }

        function renderList (data, isAppend) {
            if (isAppend) {
                $goodsListUl.append(data);
            } else {
                $goodsListUl.html(data);
            }

            isEnd = $goodsListUl.find('.list-lastgoods, .list-empty').length > 0;

            if(window.imageLazyLoad) {
                window.imageLazyLoad.setLazy();
                window.imageLazyLoad.load();
            }
        }

        ga('send', 'event', {
            eventAction: '페이지로드성능체크',
            eventCategory: '#몰구분=Mart #기기=M #분류=메인GNB #페이지명='+ categoryName +' #URL=/corners.do?returnCategoryId=' + cid,
            eventLabel: '선택메뉴=' + categoryName
        });
    };

    return {
        init: function (obj) {
            $footer.addClass(loaindgFooterClass);
            _init(obj);
        }
    };
}));