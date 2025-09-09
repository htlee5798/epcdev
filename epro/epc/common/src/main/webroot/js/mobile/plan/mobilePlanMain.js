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
    var $footer = $( '#footer' ),
        loaindgFooterClass = 'footer-loading',
        hash = '',
        $container = '',
        $goodsWrapper = '',
        $goodsListUl = '',
        $categoryLayer = '',
        noMore = false,
        nowLoading = false,
        infiniteScroll = null;

    var _init = function (obj) {
        hash = obj.hash;

        nowLoading = false;

        $container = $('[data-pageid="'+ hash +'"]');
        $goodsWrapper = $container.find('div.goods-wrapper');
        $goodsListUl = $goodsWrapper.find('#wrapGoodsListPlan');
        $categoryLayer = $container.find('[data-layer=categoryLayerForPlan]');
        noMore = $goodsListUl.find('.ingpageloading').length > 0;

        infiniteScroll = new _InfiniteScroll({
            container : $container,
            callback : getMore
        });

        render();

        if( noMore ) {
            $footer.removeClass( loaindgFooterClass );
        }

        $(window).on('scroll.mainScroll', function () {
            infiniteScroll.setPosition(
                $(window).scrollTop()
            );
        });

        $categoryLayer.on('click', 'input[name=chkCategory]', function() {
            if(this.checked){
                $(this).attr("checked", "checked");
            }else{
                $(this).removeAttr("checked");
            }
        });

        $categoryLayer.find('.search-plan').on('click', function(){
            $(window).scrollTop(
                $('#settingTopBanner:visible').height()
                + $('#header').outerHeight(true)
                + 1
            );

            nowLoading = true;

            $categoryLayer
                .find('.js-close')
                .trigger('click');

            //2018.01.19 모바일 app의 경우 web용 로딩바가 보지 않도록 분기처리
            if(!$.utils.isiOSLotteMartApp()
                && !$.utils.isAndroidLotteMartApp() ){ //모바일 앱이 아닐경우만 로딩바 표시
                $container.loadingBar();
            }else{
                if (window.LOTTEMARTDID
                    && window.LOTTEMARTDID['isLoading']) {
                    window.LOTTEMARTDID.isLoading(true);
                } else {
                    schemeLoader.loadScheme({key: 'lodingStart'});
                }
            }
            //카테고리 선택 시 초기화
            noMore = false;

            if($goodsWrapper.find(".list-empty").length > 0){
                $goodsWrapper.find(".list-empty").remove();
            }

            $container.find('input[name="currentPage"]').val(1);

            var subCategoryId = [];
            var subCategoryName = "";
            var chkType = $(this).data('type');
            var $checkedCtgr = $categoryLayer.find('input[type=checkbox][name=chkCategory]:checked');

            if(chkType == 'chk'){
                $checkedCtgr.each(function(index){
                    subCategoryId.push($(this).val());
                    subCategoryName += (index != 0 ?  ", " : "") + $(this).next().text();
                });
                $categoryLayer.find('input[name=SubCategoryId]').val(subCategoryId);
            }else{
                $checkedCtgr.removeAttr('checked');
                $checkedCtgr.prop('checked', false);
                $categoryLayer.find('input[name=SubCategoryId]').val('All');
            }
            if(subCategoryName.length > 22) {
                subCategoryName = subCategoryName.substring(0,21) + "...";
            } else {
                if(subCategoryName.length === 0) {
                    subCategoryName = "주제별 보기";
                }
                subCategoryName = subCategoryName;
            }
            $(".cateselect").text(subCategoryName);

            // 웹캐시 적용을 위하여 파라미터 변경
            var fromData = {
                SubCategoryId : $categoryLayer.find('input[name=SubCategoryId]').val(),
                rowPerPage : $container.find('input[name="rowPerPage"]').val(),
                currentPage : $container.find('input[name="currentPage"]').val(),
                str_Cd : $container.find('input[name="str_Cd"]').val()
            };

            $.get($.utils.config('LMAppUrlM') + "/mobile/plan/ajax/planAjaxList.do", fromData)
                .done(function(data) {
                    if(data.trim() != null && data.trim() != ''){
                        $goodsListUl.html(data);
                    }else{
                        $goodsListUl.empty();
                        $goodsWrapper.append("<div class='list-empty'><p class='title-sub'>검색된 결과가 없습니다.</div>");
                        noMore = true;
                    }
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
                    nowLoading = false;
                });
        });

        //카테고리 선택 레이어 팝업
        $container.on('click', '.cateselect', function(e) {
            e.preventDefault();
            var obj = $(this),
                $parent = obj.closest('.main-slide'),
                objTgt = this.hash ? this.hash : obj.attr('data-layer'),
                objPosTop = (obj.offset().top - $parent.offset().top) + obj.outerHeight() + 10;
            if (objTgt) {
                objTgt = objTgt.replace('#', '');
                areaTooltipLayerPopup(obj, objTgt, objPosTop, $container);

                if(obj.hasClass('cateselect')){
                    $parent.find('[data-pageid]').css({
                        minHeight: objPosTop + $('[data-layer='+ objTgt +']').outerHeight(true)
                    });
                }
            }

            var cid = $.cookie("__categoryId");
            ga('send', 'event', {
                eventCategory: '#몰구분=Mart #기기=M #분류=상품탐색PLP(메인GNB) #페이지명=기획전 #URL=/corners.do?returnCategoryId=' +cid,
                eventAction: '컨텐츠 전시 옵션',
                eventLabel: '옵션 보기'
            });

            //레이어 마스크 닫기 ga 이벤트
            $container.find(".mask").on('click' , function(){
                ga('send', 'event', {
                    eventCategory: '#몰구분=Mart #기기=M #분류=상품탐색PLP(메인GNB) #페이지명=기획전 #URL=/corners.do?returnCategoryId=' +cid,
                    eventAction: '컨텐츠 전시 옵션',
                    eventLabel: '옵션 닫기'
                });
            });

        });

        $categoryLayer.find('.js-close').on('click', function(){
            $(this).closest('.main-slide').find('[data-pageid]').css({
                minHeight: 'auto'
            });

            var cid = $.cookie("__categoryId");
            ga('send', 'event', {
                eventCategory: '#몰구분=Mart #기기=M #분류=상품탐색PLP(메인GNB) #페이지명=기획전 #URL=/corners.do?returnCategoryId=' +cid,
                eventAction: '컨텐츠 전시 옵션',
                eventLabel: '옵션 닫기'
            });

            $container.find(".mask").off('click');

        });

        $goodsListUl.on('click', '.planPrdDetail', function(e){
            var _this = $(this),
                requestUri = location.href.replace( location.origin + '/mobile', '' ).replace( '&ishome=true', '' ) + '&returnURL=' + encodeURIComponent(location.href);
            ga('send', 'event', {
                eventCategory: '#몰구분=Mart #기기=M #분류=컨텐츠전시페이지(메인GNB) #페이지명=기획전 #URL=' + requestUri,
                eventAction: '기획전PLP 보기',
                eventLabel: '#SEQ=' + ( _this.closest( 'li' ).index() + 1 ) + ' #콘텐츠=' + _this.find( 'img' ).attr( 'alt' )
            });

            goPlanDetail(_this.attr('data-ctgrid'), _this.attr('data-mkdpseq'), _this.attr('data-divnseq'), 'OF004');
            return false;
        });

        function getMore(){
            if (window.getHistoryBack()) {
                window.setHistoryBack(false);
                return;
            }
            if(nowLoading) {
                return;
            }
            if( noMore ) {
                $footer.removeClass( loaindgFooterClass );
            }

            var currentPage = $container.find('input[name="currentPage"]').val();

            if(!noMore){
                $container.find('input[name="currentPage"]').val( parseInt( currentPage, 10 ) + 1 );

                var goToUrl= $.utils.config('LMAppUrlM') + "/mobile/plan/ajax/planAjaxList.do";
                params = {
                    SubCategoryId: $categoryLayer.find('input[name=SubCategoryId]').val(),
                    rowPerPage: $container.find('input[name="rowPerPage"]').val(),
                    currentPage: $container.find('input[name="currentPage"]').val(),
                    str_Cd: $container.find('input[name="strCD"]').val()
                };

                nowLoading = true;
                $container.moreBar();
                $.ajax({
                    url : goToUrl,
                    data : params,
                    success : renderMore,
                    complete : function( xhr, state ) {
                        if( state === 'abort' ) {
                            nowLoading = false;
                            $container.moreBar( false );

                            if( $container.find('input[name="currentPage"]').length > 0 ) {
                                var currentPage = $container.find('input[name="currentPage"]').val();
                                $container.find('input[name="currentPage"]').val( parseInt( currentPage, 10 ) - 1 );
                            }
                        }
                    }
                });
            }
        }

        function renderMore(response){
            $container.moreBar(false);
            if(response.trim() !== null && response.trim() !== ''){
                $goodsListUl.append(response);
            }else{
                if($goodsWrapper.find(".list-empty").length < 1){
                    $goodsListUl.append("<li class='ingpageloading'><center><h2 class='title-basic nomore'>마지막 리스트입니다.</h2></center></li>");
                }
                noMore = true;
            }
            nowLoading = false;

            if(window.imageLazyLoad) {
                window.imageLazyLoad.setLazy();
                window.imageLazyLoad.load();
            }
        }

        function render() {
            if (window.getHistoryBack()) {
                window.setHistoryBack(false);
                return;
            }
            var init = setListWrapper;	// list 고정형으로 변경. 2017.09.05 by hjson
            init();

            if(window.imageLazyLoad) {
                window.imageLazyLoad.setLazy();
                window.imageLazyLoad.load();
            }
        }

        function setListWrapper () {
            $goodsListUl.removeClass('themesale-image').addClass('themesale-list');
        }
    };
    return {
        init: function (obj) {
            $footer.addClass(loaindgFooterClass);
            _init(obj);
        }
    }
}));