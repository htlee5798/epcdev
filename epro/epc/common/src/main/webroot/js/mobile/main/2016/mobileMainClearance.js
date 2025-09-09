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
        categoryId = '',
        mkdpSeq = '',
        promotionIconShape = '',
        SubCategoryId = '',
        pdivnSeq = '',
        dispCd ='',
        loadingFooterClass = 'footer-loading',
        $footer = $('#footer'),
        $container = '',
        $subCategory = '',
        $goodsWrapper = '',
        currPage = 1,
        nowLoading = false,
        noMore = false,
        infiniteScroll = null;

    var _init = function (obj) {
        hash = obj.hash;

        var $frm = $('#frmParams');

        categoryId = $frm.find('[name="CategoryId"]').val();
        mkdpSeq = $frm.find('[name="MkdpSeq"]').val();
        promotionIconShape = $frm.find('[name="promotionIconShape"]').val();
        SubCategoryId = $frm.find('[name="SubCategoryId"]').val();
        pdivnSeq = $frm.find('[name="pdivnSeq"]').val();
        dispCd = $frm.find('[name="dispCd"]').val();

        $container = $('[data-pageid="'+ hash +'"]');
        $subCategory = $container.find('div.subcate-boxtype');
        $goodsWrapper = $container.find('div.product-wrapper');

        nowLoading = false;
        noMore = $container.find('.nomore').length > 0;

        infiniteScroll = new _InfiniteScroll({
            container : $container,
            callback : getMore
        });

        var requestUri = location.href.replace(location.origin + '/mobile', '') + (location.search === '' ? '?returnCategoryId=' + hash : '');

        getMore('initPage');

        $(window).on('scroll.mainScroll', function () {
            infiniteScroll
                .setPosition(
                    $(window).scrollTop()
                );
        });

        //sub category call
        $subCategory.on('click', 'a', subCateChange);

        function subCateChange(){
            var $this = $(this);
            var data = $this.data();
            var $frm = $('#frmParams');

            $this
                .addClass( 'active' )
                .siblings()
                .removeClass( 'active' );

            $frm
                .find('[name="CategoryId"]')
                .val(categoryId = data.categoryid)
                .end()
                .find('[name="MkdpSeq"]')
                .val(mkdpSeq = data.mkdpseq)
                .end()
                .find('[name="pdivnSeq"]')
                .val(pdivnSeq = data.divnseq + '')
                .end()
                .find('[name="currentPage"]')
                .val(currPage = 1);

            nowLoading = false;
            noMore = false;

            $goodsWrapper
                .find('.prditem-groups')
                .empty();

            getMore();
        }

        function getGaData ($target) {
            var $li = $target.closest("li");
            return {
                viewType: '갤러리형',
                prdName : $target.data('prodTitle'),
                orderNum : $li.index() +1,
                cid : $.cookie("__categoryId"),
                price: $li.find(".price .last em").text().replace(/\,/gi, '')
            };
        }

        $container.on( 'click', '.product-wrap a', function( e ) {
            e.stopPropagation();
            var data = getGaData($(this));

            ga('send', 'event', {
                eventAction: '상품 상세 탐색',
                eventCategory: '#몰구분=Mart #기기=M #분류=상품탐색PLP(메인GNB) #페이지명=클리어런스 #URL=/corners.do?returnCategoryId='+ data.cid,
                eventLabel : '#SEQ=' + data.orderNum + ' #뷰타입=' + data.viewType +' #CID=' + $(this).data('cid') + ' #PID=' + $(this).data('pid') + ' #상품명='+data.prdName,
                eventValue : data.price
            });

        }).on('click', '.basket', function() {
            var data = getGaData($(this));
            ga('send', 'event', {
                eventAction: '장바구니 담기',
                eventCategory: '#몰구분=Mart #기기=M #분류=상품탐색PLP(메인GNB) #페이지명=클리어런스 #URL=/corners.do?returnCategoryId='+ data.cid,
                eventLabel : '#SEQ=' + data.orderNum + ' #뷰타입=' + data.viewType +' #CID=' + $(this).data('category-id') + ' #PID=' + $(this).data('prod-cd') + ' #상품명='+data.prdName,
                eventValue : data.price
            });
        }).on('click', '.qty', function() {
            var $obj = $(this);
            ga('send', 'event', {
                eventAction: '상품 수량 변경',
                eventCategory: '#몰구분=Mart #기기=M #분류=GNB #페이지명=클리어런스 #URL=' + requestUri,
                eventLabel : '#CID=' + $obj.data('categoryId') + ' #PID=' + $obj.data('prodCd')
            });
        });

        function getMore(initPage) {
            if (window.getHistoryBack()) {
                window.setHistoryBack(false);
                return;
            }
            if(nowLoading || noMore) {	//현재 상품 조회 중이거나, 모든 상품을 조회했다면 더이상 요청 하지 않는다
                if(noMore) {
                    $footer.removeClass(loadingFooterClass);
                }
                return;
            }
            nowLoading = true;
            
            if(initPage == 'initPage' ){
            	currPage = 1;
            }

            var url = $.utils.config('LMAppUrlM') + '/mobile/main/ajax/cateAjaxList.do'
            var params = {
                'mPlanCat' : 'Y',
                'detailYN' : 'Y',
                'CategoryId' : categoryId,
                'SubCategoryId' : SubCategoryId,
                'MkdpSeq' : mkdpSeq,
                'dispCd' : dispCd,
                'currentPage' : currPage,
                'str_Cd' : $.utils.config('main_store_code'),
                'zip_seq' : $.utils.config('zipSeq'),
                'promotionIconShape' : promotionIconShape
            };

            $container.moreBar();

            if(pdivnSeq !== '0' && pdivnSeq !== '') {
                params['pdivnSeq'] = pdivnSeq;
            }

            $.get(url, params, renderMore);
        }

        function renderMore(response) {
            //페이지 수 증가
            currPage++;

            $container.moreBar(false);
            $goodsWrapper
                .find('.prditem-groups')
                .append(response);

            nowLoading = false;
            noMore = $goodsWrapper.find('.nomore').length > 0;

            if(noMore) {
                $footer.removeClass(loadingFooterClass);
            }

            if(window.imageLazyLoad) {
                window.imageLazyLoad.setLazy();
                window.imageLazyLoad.load();
            }
        }
    };

    return {
        init: function (obj) {
            $footer.addClass(loadingFooterClass);
            _init(obj);
        }
    }
}));