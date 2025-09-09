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
        $container = '',
        $footer = $('#footer'),
        $goodsWrapper = '',
        $form = '',
        $currentPage = '',
        currPage = 1,
        nowLoading = false,
        noMore = false,
        infiniteScroll = null;

    var _init = function (obj) {
        hash = obj.hash;

        nowLoading = false;
        noMore = false;

        $container = $('[data-pageid="'+ hash +'"]');
        $goodsWrapper = $container.find('div.product-wrapper');
        $form = $goodsWrapper.find('form.infinite-form');
        $currentPage = $container.find('.current-page');
        currPage = parseInt($currentPage.val(), 10);
        noMore = $container.find('.nomore').length > 0;
        infiniteScroll = new _InfiniteScroll({
            container: $container,
            callback: getMore
        });

        sessionStorage.setItem(hash + "-viewType", 'type-list');

        $(window).on('scroll', function () {
            infinitScroll.setPosition($(window).scrollTop());
        });

        //보기형식 이벤트 주입
        $container.find('button[name=btn-view-type]').on('click', changeViewType);

        var prevSelectText;
        $container.on('change', 'select[name=subCategories]', function() {
            var value = $(this).val()
                , categoryId = value.split('_')[0]
                , templateId = value.split('_')[1]
                , cornSeq = value.split('_')[2]
                , setSeq = value.split('_')[3]
                , recomCd = value.split('_')[4];
            loadSubCategory(categoryId, templateId, cornSeq, setSeq, recomCd);

            $( this ).find( 'option:selected' ).attr( 'selected', 'selected' ).siblings().removeAttr('selected');

            ga('send', 'event', {
                eventAction: '컨텐츠 전시 옵션',
                eventCategory: '#몰구분=Mart #기기=M #분류=상품탐색PLP(메인GNB) #페이지명=단독특가 #URL=/corners.do?returnCategoryId=' + hash,
                eventLabel : '#카테고리선택=' + $(this).find('option:selected').text()
            });
        }).on('click', 'select[name=subCategories]', function() {
            var selectText = $(this).find('option:selected').text();
            if (prevSelectText !== selectText) {
                return false;
            }

            ga('send', 'event', {
                eventAction: '컨텐츠 전시 옵션',
                eventCategory: '#몰구분=Mart #기기=M #분류=상품탐색PLP(메인GNB) #페이지명=단독특가 #URL=/corners.do?returnCategoryId=' + hash,
            });
        }).on('focus', 'select[name=subCategories]', function() {
            prevSelectText = $(this).find('option:selected').text();
        });
    };

    function loadSubCategory(categoryId, templateId, cornSeq, setSeq, recomCd) {
        var params = {
            'categoryId' : categoryId
            , 'templateId' : templateId
            , 'cornSeq' : cornSeq
            , 'setSeq' : setSeq
            , 'recomCd' : recomCd
        };

        $form.find('input[name="categoryId"]').val(categoryId);
        $form.find('input[name="templateId"]').val(templateId);
        $form.find('input[name="cornSeq"]').val(cornSeq);
        $form.find('input[name="setSeq"]').val(setSeq);
        $form.find('input[name="recomCd"]').val(recomCd);
        currPage = 1;

        //2018.01.19 모바일 app의 경우 web용 로딩바가 보지 않도록 분기처리
        if(!$.utils.isiOSLotteMartApp() && !$.utils.isAndroidLotteMartApp() ){ //모바일 앱이 아닐경우만 로딩바 표시
            $container.loadingBar();
        }else{
            if (window.LOTTEMARTDID && window.LOTTEMARTDID['isLoading']) {
                window.LOTTEMARTDID.isLoading(true);
            } else {
                schemeLoader.loadScheme({key: 'lodingStart'});
            }
        }
        $.get($.utils.config('LMAppUrlM') + '/mobile/mobileMainCommTemp.do', params, renderGoods);
    }

    function renderGoods(response) {
        //2018.01.19 모바일 app의 경우 web용 로딩바가 보지 않도록 분기처리
        if(!$.utils.isiOSLotteMartApp() && !$.utils.isAndroidLotteMartApp() ){ //모바일 앱이 아닐경우만 로딩바 표시
            $container.loadingBar(false);
        }else{
            if (window.LOTTEMARTDID && window.LOTTEMARTDID['isLoading']) {
                window.LOTTEMARTDID.isLoading(false);
            } else {
                schemeLoader.loadScheme({key: 'lodingEnd'});
            }
        }
        var $response = $( response ),
            $newGoods = $response.find('div.product-wrapper');

        $goodsWrapper.html($newGoods.html());
        noMore = $goodsWrapper.find('.nomore').length > 0;
    }

    var lastProductLimit = 0;
    function getMore() {
        if(nowLoading || noMore) {	//현재 상품 조회 중이거나, 모든 상품을 조회했다면 더이상 요청 하지 않는다
            if( noMore ) {
                $footer.removeClass( loaindgFooterClass );

                if (lastProductLimit < 1) {
                    lastProductLimit++;

                    ga('send', 'event', {
                        eventAction: '모든상품보기',
                        eventCategory: '#몰구분=Mart #기기=M #분류=상품탐색PLP(메인GNB) #페이지명=단독특가 #URL=/corners.do?returnCategoryId=' + hash,
                    });
                }
            }
            return;
        }
        nowLoading = true;

        //페이지 수 증가
        currPage++;
        $currentPage.val(currPage);

        var value =  $("#subCategories > option:selected").val();

        var url = $.utils.config('LMAppUrlM') + '/mobile/main/ajax/ajaxProdList.do'  // decorators.xml-> <pattern>**/ajax/*.do**</pattern> -> header부분 제외됨.
            , params = {
            'categoryId' : value.split('_')[0]
            , 'templateId' : value.split('_')[1]
            , 'cornSeq' : value.split('_')[2]
            , 'setSeq' : value.split('_')[3]
            , 'recomCd' : value.split('_')[4]
            , 'currentPage' : currPage
        };

        $container.moreBar();
        $.ajax({
            url : url,
            data : params,
            success: renderMore,
            complete : function( xhr, state ) {
                if( state === 'abort' ) {
                    nowLoading = false;
                    $container.moreBar( false );

                    if( $currentPage.length > 0 ) {
                        currPage = currPage - 1;
                        $currentPage.val( currPage );
                    }
                }
            }
        });
    }

    function renderMore(response) {
        $container.moreBar(false);
        $goodsWrapper.find('.prditem-groups').append(response);
        nowLoading = false;
        noMore = $goodsWrapper.find('.nomore').length > 0;

        if(window.imageLazyLoad) {
            window.imageLazyLoad.setLazy();
            window.imageLazyLoad.load();
        }
    }

    function changeViewType() {
        var currentViewType = sessionStorage.getItem( hash + "-viewType" ) || "type-list";

        if(currentViewType == "type-list") {
            nextViewType = "type-gallery";
            btnViewType = "type-gallery";
        }else if(currentViewType == "type-gallery") {
            nextViewType = "type-image";
            btnViewType = "type-image";
        } else if(currentViewType == "type-image") {
            nextViewType = "type-list";
            btnViewType = "type-list";
        }

        var listWrapper = $goodsWrapper.closest('div');
        listWrapper.removeClass( 'type-list type-gallery type-image' ).addClass( nextViewType );
        $(this).removeClass( 'type-list type-gallery type-image' ).addClass( btnViewType );

        sessionStorage.setItem(hash + "-viewType", nextViewType );
    }

    return {
        init: function (obj) {
            $footer.addClass(loaindgFooterClass);
            _init(obj);
        }
    }
}));