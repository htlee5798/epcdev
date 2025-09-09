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
        root.recipeMain = factory();
    }
}(typeof self !== 'undefined' ? self : this, function () {
    var $footer = $('#footer');
    var loadingFooterClass = 'footer-loading';
    var hash = '',
        strCd = '',
        $cardtypeWrapper = null,
        $cardtypeLists = null,
        $wrapper = null,
        $list = null,
        $MNOSort = null,
        $frmFilterSearch = null,
        $currentPage = null,
        // $itemSort = null,
        configs = {};

    var xhr = null;
    var isEnd = false;
    var $aboutBest = null;
    var $aboutOther = null;

    var _getRecipe = function () {
        if(window.getHistoryBack && window.getHistoryBack()) {
            window.setHistoryBack(false);
            return;
        }
        xhr = $.api.get({
            url : '/mobile/ajax/recipe/main/list.do',
            isShowMoreBar : true,
            $container : $cardtypeLists,
            cache: false,
            data : {
                recNo : '',
                strCd : strCd
            },
            successCallback : function(res) {
                var $docFrag = document.createDocumentFragment();

                for(var i = 0, len = res.data.mainRecipe.length; i < len; i++) {
                    var $div = document.createElement('div');

                    $div.classList.add('list-cardtype');
                    $div.innerHTML = $.render.mobileRecipeNew(
                        res.data.mainRecipe[i]
                    );
                    $docFrag.appendChild($div);
                }

                $cardtypeLists.html($docFrag)
                _setMainView();
                if(window.imageLazyLoad) {
                    window.imageLazyLoad.setLazy();
                    window.imageLazyLoad.load();
                }
            }
        })
    };

    var _getList = function () {
        if(window.getHistoryBack && window.getHistoryBack()) {
            window.setHistoryBack(false);
            return;
        }
        var params = {};
        if(xhr !== null && xhr.readyState !== 4) {
            return;
        }
        if(isEnd) {
            $wrapper.moreBar(false);
            xhr.readyState = 4;
            return;
        }
        $frmFilterSearch
            .serializeArray()
            .map(function (v) {
                params[v.name] = v.value;
                return v;
            });

        // params.filter = $MNOSort.find('active').data()
        params.rowPerPage = 20;

        xhr = $.api.get({
            url: '/mobile/ajax/recipe/detail/list.do',
            data: params,
            isShowMoreBar: true,
            $container: $wrapper,
            cache : false,
            successCallback: function (res) {
                var currentPage = Number($currentPage.val());

                isEnd = res.data.recipeDetailList.length === 0;

                var strHtml = _render(res.data.recipeDetailList);

                if(isEnd) {
                    $wrapper[0].appendChild(strHtml);
                } else {
                    $list[0].appendChild(strHtml);
                    $currentPage.val(
                        currentPage + 1
                    );
                }
                if (window.imageLazyLoad) {
                    window.imageLazyLoad.setLazy();
                    window.imageLazyLoad.load();
                }
            },
            errorCallback: function(err){
                // console.log('_getList err : ', err);
            }
        });
    };

    var _render = function (data) {
        var $docFrag = document.createDocumentFragment();
        if(isEnd) {
            if(!$wrapper.find('.list-empty').length){
                var $div = document.createElement('div');
                $div.classList.add('list-empty');
                $div.innerText = '마지막 레시피 입니다.';
                $docFrag.appendChild($div);
            }
            $footer.show();
        } else {
            for (var i = 0, len = data.length; i < len; i++) {
                var $div = document.createElement('div');

                // $div.innerHTML = $.render.mobileRecipeDetailForItems(data[i]);
                $div.innerHTML = $.render.mobileRecipeDetailForItems(data[i]);
                $docFrag.appendChild($div);
            }
        }

        return $docFrag;
    };

    var _setMainView = function(){
        $footer.removeClass(loadingFooterClass);
        $aboutBest.removeClass('hidden');
        $aboutOther.addClass('hidden');

    }

    var _setSubView = function(MNO){
        var NNO = $NNO.val() || 'best';
        // var MNO = $MNO.val() || '00001';
        $footer.addClass(loadingFooterClass);
        $aboutOther.removeClass('hidden');
        var $btns = $MNOSort.find('a')
            .addClass('hidden')
            .removeClass('active')
            .filter('[data-title-no='+NNO+']').removeClass('hidden')
            // .filter('[data-m-no='+MNO+']').addClass('active');
        if(MNO){
            var $btn = $btns.filter('[data-m-no='+MNO+']')
            $btn.addClass('active')
            $MNO.val($btn.data('mNo'))
        } else {
            $btns.eq(0).addClass('active');
            $MNO.val($btns.eq(0).data('mNo'))
        }
        $aboutBest.addClass('hidden');
    }

    var _init = function (obj) {
        ITEMSORT =  obj.ITEMSORT;
        hash = obj.hash;
        strCd =  obj.strCd;

        $aboutBest = $('.is-NNO-best');
        $aboutOther = $('.is-NNO-other');
        $cardtypeWrapper = $('[data-pageid="'+ hash +'"]');
        $cardtypeLists = $cardtypeWrapper.find('#wrapListCardType');
        $wrapper = $('.wrap-list-gallery.chg-layout.is-NNO-other');
        $list = $('.wrap-list-gallery.chg-layout.is-NNO-other .list-gallery');
        $frmFilterSearch = $('#frmFilterSearch');
        $MNOSort = $('#MNOSort');
        $currentPage = $frmFilterSearch.find('[name="cPage"]');
        $NNO = $frmFilterSearch.find('[name="NNO"]');
        $MNO = $frmFilterSearch.find('[name="MNO"]');

        $.api.get({
            url: '/env/configs.do',
            cache:false,
            successCallback: function (res) {
                configs = res;
                _addSortEvent();
            }
        });

        if($NNO.val() === 'best'){
            $footer.removeClass(loadingFooterClass);
            _setMainView();
            _getRecipe();
        } else {
            $footer.hide();
            _setSubView($MNO.val());
            _getList();
        }

        var infiniteScroll = new _InfiniteScroll({
            container : $wrapper,
            callback : function () {
                _getList();
            }
        });

        $(window).on('scroll', function () {
            if($NNO.val() !== 'best'){
                infiniteScroll.setPosition(
                    $(window).scrollTop()
                );
            }
        });

    };

    var _addTabEvent = function(){
        var $recipeFilterTap = $('.leaflet-filter.filter-recipe');
        if($recipeFilterTap.length){
            $recipeFilterTap.on('click','a',function(e){
                var $this = $(this);
                var data = $this.data();
                if(data.nno === 'search'){
                    location.href = '/mobile/popup/recipe/search.do';
                } else {
                    $this.addClass('active').siblings().removeClass('active');
                    $NNO.val(data.nno);
                    $MNO.val('00001');
                    isEnd = false;
                    if($NNO.val() === 'best'){
                        _setMainView();
                        _getRecipe();
                    } else {
                        $currentPage.val(1);
                        $list.html('');
                        _setSubView();
                        _getList();
                        $MNOSort.scrollLeft(0)
                    }
                }
            });
        }
    }


    var _addSortEvent = function () {
        $MNOSort.on('click', 'a', function (e) {
            e.preventDefault();
            isEnd = false;
            $(this)
                .addClass('active')
                .siblings()
                .removeClass('active');

            var mNo = $(this).data('mNo');
            $MNO.val(mNo);
            $list.empty();
            $currentPage.val('1');
            _getList();
        });
    };

    return {
        // image404 : function (e) {
        //     $(e).attr('src', $.utils.config('LMCdnV3RootUrl') + '/images/layout/noimg_prod_204x204.jpg')
        //           .removeClass('lazy');
        // },
        // image705_404 : function (e) {
        // 	 $(e).attr('src', $.utils.config('LMCdnV3RootUrl') + '/images/layout/noimg_prod_604x208.jpg');
        // },
        init : function (obj) {
            _init(obj);
            _addTabEvent();
        },
        // image278_404 : function (e) {
        //      if(configs['system.cdn.lm2.path']){
        //          $(e).attr('src', configs['system.cdn.lm2.path'] + '/images/layout/noimg_cook_278x278.jpg')
        //              .removeClass('lazy');
        //      }
        // }
    }
}));