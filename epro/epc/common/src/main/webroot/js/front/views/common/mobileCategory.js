
    //온라인상품유형코드
    var onlineProdMake ="${const:getString('ONLINE_PROD_TYPE_CD_MAKE')}";
    var onlineProdInst = "${const:getString('ONLINE_PROD_TYPE_CD_INST')}";
    var onlineProdReserve ="${const:getString('ONLINE_PROD_TYPE_CD_RESERVE')}";

//"use strict";
$(function() {
    $('button[id^=chkWish]').on('click',   function (event) {
        addWishList(this, event, document.location.href);
    });

    $('button[id^=chkBasket]').on('click',   function (event) {
        addBasket(this, event, document.location.href);
    });

    $('button[id^=maxBenefit]').on('click',   function (event) {
        layerpopTriggerBtn( this, event );
        selectMenu();
    });

    $('.prod-link').on('click', function(e){
        //var link = $(this).closest('article').find('.prod-name a').attr('href'); //href로 가져올때
        var onclick = $(this).closest('article').find('.prod-name a').attr('onclick'); //onclick으로 가져올때
        // location 링크 goProductDetail 로 가져올수 있도록 해야함
        //if (!$(e.target).attr('href')) location.href = link; // href
        if (onclick != null && onclick.length > 0)  eval(onclick); // onclick
    });

    $('.wrap-thumb .prod-link a.dibs').click(function(event) {
        event.preventDefault();
        /* $(this).toggleClass('cuurent'); */
        var prodInfos = $(this).attr('id').split('_');      // "wish_"+prodCd+"_"+idx+"_"+categoryId or "wish_"+prodCd+"_"+categoryId

        if(prodInfos != null && prodInfos.length > 3) {
            addProdWishList(this, event, prodInfos[1], prodInfos[3], 'N', prodInfos[2], document.location.href);
        } else if(prodInfos != null && prodInfos.length > 2) {
            addProdWishList(this, event, prodInfos[1], prodInfos[2], 'N', '', document.location.href);
        }
    });

    //상품목록-옵션 - wrap-spinner, prod-option
    $('.wrap-spinner .layerpop-trigger2').on('click', function(event) {
        event.preventDefault();
        var afterclick = $(this).attr("data-afterclick");
        if (afterclick != null && afterclick.length > 0) {
            eval(afterclick);
         }
    });
    $('.prod-option .layerpop-trigger2').on('click', function(event) {
        event.preventDefault();
        var afterclick = $(this).attr("data-afterclick");
        if (afterclick != null && afterclick.length > 0) {
            eval(afterclick);
         }
    });

    $('div[name=coProd]').unbind('click').on('click', '.basket', function(event) {
        event.preventDefault();
        var afterclick = $(this).attr("data-afterclick");
        if (afterclick != null && afterclick.length > 0) {
            eval(afterclick);
         }
    });

    $('div[name=moProd]').unbind('click').on('click', '.basket', function(event) {
        event.preventDefault();
        var afterclick = $(this).attr("data-afterclick");
        if (afterclick != null && afterclick.length > 0) {
            eval(afterclick);
         }
    });

    $('div[name=coProd]').on('click', 'button[id^=wish_]', function(event) {
        event.preventDefault();
         var prodInfos = $(this).attr('id').split('_');      // "wish_"+prodCd+"_"+idx+"_"+categoryId or "wish_"+prodCd+"_"+categoryId
         goNewWishList(this, event,prodInfos[1], prodInfos[2], prodInfos[3],document.location.href);

    });

    $('div[name=moProd]').on('click', 'button[id^=wish_]', function(event) {
        event.preventDefault();
         var prodInfos = $(this).attr('id').split('_');      // "wish_"+prodCd+"_"+idx+"_"+categoryId or "wish_"+prodCd+"_"+categoryId
         goNewWishList(this, event,prodInfos[1], prodInfos[2], prodInfos[3],document.location.href);

    });

    function mOptLayer(obj) {
        if($(obj).attr('id') != null && $(obj).attr('id').length > 0) {
            var prodInfos = $(obj).attr('id').split('_'); //id="B_${nRow.OPTION_YN}_${nRow.PROD_CD}_${nRow.ONLINE_PROD_TYPE_CD}_${PROD_AREA_IDX }_${nRow.CATEGORY_ID }"
            mOptLayerYn(obj, event, prodInfos[0], prodInfos[1], prodInfos[2], prodInfos[3], prodInfos[4], prodInfos[5],  document.location.href);
        }
    }

    function mOptLayerYn(obj, event, p_gubun, p_optionYn, p_prodCd, p_prodTypeCd, p_area_idx, p_categoryId, p_url) {
        var flag = global.isLogin(p_url);
        if(flag) {
            var p_type = null;
            if(p_gubun == "D") {
                p_type = "order";
            } else {
                p_type = "basket";
            }
            if(p_optionYn=="Y") {
                var params = {
                        'PROD_CD' : p_prodCd
                        ,'TYPE' : p_type
                        ,'PRODTYPECD' : p_prodTypeCd
                        ,'CATEGORYID' : p_categoryId
                        };

                $.ajax({
                    type: "post",
                    url: "/mobile/ajax/cate/mProductOptionListAjax.do",
                    data: params,
                    async: false,
                    cache: false,
                    dataType: "html",
                    success: function(data) {

                            /* 중복 발생 시 삭제 처리 */
                            var  objArray = $('div[id=mCateOpt-list]');
                            if(objArray == null)return;
                            $.each( objArray, function( idx, objData ) {
                                if(idx > 0 )objData.remove();
                            });

                            $('#mCateOpt-list').empty().append(data);
                            schemeLoader.loadScheme({key: 'basketCountUpdate'});


                    },
                    error:function(data) {
                        //console.log(data);
                    }
                });
            } else {
                addBasketProdListOneItem(obj, event, p_gubun, p_prodCd, p_area_idx, p_url);	//장바구니담기
            }
        }
    }

    $('.jq-tab > a').click(function (event) {
        var pt = $(this).parent().siblings();
        if($(pt).find('input[type=checkbox][id^=chk_]') != null && $(pt).find('input[type=checkbox][id^=chk_]').length > 0) {
            $(pt).find('input[type=checkbox][id^=chk_]').parent().removeClass('active');
            $(pt).find('input[type=checkbox][id^=chk_]').removeAttr('checked');
        }
        if($(pt).find('input[type=text][id^=orderQty_]') != null && $(pt).find('input[type=text][id^=orderQty_]').length > 0) {
            $(pt).find('input[type=text][id^=orderQty_]').val("1");
        }
    });
});
//셀렉트박스용 카테고리 리스트 조회-o
function goCategoryListAjax(p_categoryId, p_depth
                                        , p_previewYn, p_previewChCd, p_previewStrCd, p_previewDispDate) {
    var params = {
            'CategoryID' : p_categoryId
            ,'Depth' : p_depth
            ,'PREVIEWYN' : p_previewYn
            ,'PREVIEWCHCD' : p_previewChCd
            ,'PREVIEWSTRCD' : p_previewStrCd
            ,'PREVIEWDISPDATE' : p_previewDispDate
            };
    fn$ajax(_LMAppUrl+"/category/ajax/categoryListAjax.do", params, fnNmGetter().name);
}
function callBack_$goCategoryListAjax(response) {
    $("#catNavi").html(response);
}

//테이블용 카테고리 리스트 조회-o
function goCategorySubListAjax(p_categoryId, p_leafYn
                                            , p_previewYn, p_previewChCd, p_previewStrCd, p_previewDispDate) {
    var params = {
            'CategoryID' : p_categoryId
            ,'LeafYn' : p_leafYn
            ,'PREVIEWYN' : p_previewYn
            ,'PREVIEWCHCD' : p_previewChCd
            ,'PREVIEWSTRCD' : p_previewStrCd
            ,'PREVIEWDISPDATE' : p_previewDispDate
            };
    fn$ajax(_LMAppUrl+"/category/ajax/categorySubListAjax.do", params, fnNmGetter().name);
}
function callBack_$goCategorySubListAjax(response) {
    $("#catSubNavi").html(response);
}

//카테고리 코너정보 조회-o
function goCategoryCornerListAjax(p_categoryId, p_templateId, p_depth, p_leafYn, p_dispTemplateTypeCd, p_categoryTypeCd, p_returnUrl
                                                , p_previewYn, p_previewChCd, p_previewStrCd, p_previewDispDate) {
    var params = {
            'CategoryID' : p_categoryId
            ,'TemplateID' : p_templateId
            ,'Depth' : p_depth
            ,'LeafYn' : p_leafYn
            ,'DispTemplateTypeCd' : p_dispTemplateTypeCd
            ,'CategoryTypeCd' : p_categoryTypeCd
            ,'ReturnUrl' : p_returnUrl
            ,'PREVIEWYN' : p_previewYn
            ,'PREVIEWCHCD' : p_previewChCd
            ,'PREVIEWSTRCD' : p_previewStrCd
            ,'PREVIEWDISPDATE' : p_previewDispDate
            };
    fn$ajax(_LMAppUrl+"/category/ajax/categoryCornerListAjax.do", params, fnNmGetter().name);
}
function callBack_$goCategoryCornerListAjax(response) {
    $("#cornerList").html(response);
}

//3Depth:상품 조회
function goCategoryProdListMainAjax(p_categoryId, p_leafYn
                                                    , p_previewYn, p_previewChCd, p_previewStrCd, p_previewDispDate) {
    var params = {
            'CategoryID' : p_categoryId
            ,'LeafYn' : p_leafYn
            ,'PREVIEWYN' : p_previewYn
            ,'PREVIEWCHCD' : p_previewChCd
            ,'PREVIEWSTRCD' : p_previewStrCd
            ,'PREVIEWDISPDATE' : p_previewDispDate
            };
    fn$ajax(_LMAppUrl+"/category/ajax/categoryProdListMainAjax.do", params, fnNmGetter().name);
}
function callBack_$goCategoryProdListMainAjax(response) {
    $("#divProdListMain").html(response);
}

//3Depth:상품 조회
function goCategoryProdListAjax( ) {
    if($("#aAll").hasClass("active")) {
        $("#p_searchItem_De_01").attr("disabled", true);
        $("#p_searchItem_De_04").attr("disabled", true);
        $("#p_searchItem_De_02").attr("disabled", true);
        $("#p_searchItem_De_06").attr("disabled", true);
    } else if($("#aStr").hasClass("active")) {
        $("#p_searchItem_De_01").attr("disabled", false);
        $("#p_searchItem_De_04").attr("disabled", true);
        $("#p_searchItem_De_02").attr("disabled", true);
        $("#p_searchItem_De_06").attr("disabled", true);
    } else if($("#aVen").hasClass("active")) {
        $("#p_searchItem_De_01").attr("disabled", true);
        $("#p_searchItem_De_04").attr("disabled", false);
        $("#p_searchItem_De_02").attr("disabled", false);
        $("#p_searchItem_De_06").attr("disabled", true);
    } else if($("#aGift").hasClass("active")) {
        $("#p_searchItem_De_01").attr("disabled", true);
        $("#p_searchItem_De_04").attr("disabled", true);
        $("#p_searchItem_De_02").attr("disabled", true);
        $("#p_searchItem_De_06").attr("disabled", false);
    }

    if($("#aList").hasClass("active")) {
        $("#ViewType").val("LIST");
        $("#ReturnUrl").val("category/includes/listTypeProdMoreList");
    } else if($("#aImg").hasClass("active")) {
        $("#ViewType").val("SIMG");
        $("#ReturnUrl").val("category/includes/imgTypeProdMoreList");
    }

    var params = $('#productForm').serialize();
    fn$ajax(_LMAppUrl+"/category/ajax/categoryProdListAjax.do", params, fnNmGetter().name);
}
function callBack_$goCategoryProdListAjax(response) {
    if($("#aList").hasClass("active")) {
        $("#divList").html(response);
        $("#divImg").html("");
    } else if($("#aImg").hasClass("active")) {
        $("#divList").html("");
        $("#divImg").html(response);
    }
}

function goProdListAjax( ) {
    var params = $('#productForm').serialize();
    fn$ajax(_LMAppUrl+"/category/ajax/categoryProdListAjax.do", params, fnNmGetter().name);
}
function callBack_$goProdListAjax(response) {
    if($("#aList").hasClass("active")) {
        $("#divList").append(response);
        $("#divImg").html("");
    } else if($("#aImg").hasClass("active")) {
        $("#divList").html("");
        $("#divImg").append(response);
    }

//	$(window).off('.disableScroll');
    isScroll = true;
}

//1Depth:금주의 인기상품 더보기
function goCategoryTwekPopProdAjax(p_form) {
    var params = $('#'+p_form.name).serialize();
    fn$ajax(_LMAppUrl+"/category/ajax/categoryCornerSetProdListAjax.do", params, fnNmGetter().name);
}
function callBack_$goCategoryTwekPopProdAjax(response) {
    if($("#tab03").hasClass("active")) {
        $("#divTwekPop").append(response);
        isScroll_twekPop = true;
    } else {

    }
}
//2Depth:어제 많이 팔린 상품 더보기
function goCategoryDaySellProdAjax(p_form) {
    var params = $('#'+p_form.name).serialize();
    fn$ajax(_LMAppUrl+"/category/ajax/categoryCornerSetProdListAjax.do", params, fnNmGetter().name);
}
function callBack_$goCategoryDaySellProdAjax(response) {
    if($("#tab01").hasClass("active")) {
        $("#divDySell").append(response);
        isScroll_dySell = true;
    } else if($("#tab02").hasClass("active")) {
        $("#divNew").append(response);
        isScroll_new = true;
    } else {

    }
}

//카테고리 선택-o
function goCategory(p_categoryId, p_depth, p_leafYn) {
    if(p_depth == "${CATEGORYINFO.DEPTH}") {
        goCategoryNavi(p_categoryId, p_depth, p_leafYn);
    } else {
        goCategoryList(p_categoryId);
        //document.location.href = _LMAppUrl+"/category/categoryList.do?CategoryID="+p_categoryId;
    }
}

//카테고리 선택
function goCategoryNavi(p_categoryId, p_depth, p_leafYn) {
    goCategoryListAjax(p_categoryId, p_depth);
    goCategorySubListAjax(p_categoryId, p_leafYn);
    if(p_depth == 2) {
        goCategoryDaySellNewProdAjax(p_categoryId);
        goCategoryTodayHotAjax(p_categoryId);
        goCategoryRecomPlanAjax(p_categoryId);
        goCategoryTwekPopRecomProdAjax(p_categoryId);
    }
    if(p_depth == 3) {
        goCategoryHotProdAjax(p_categoryId);
        goCategoryDaySellNewRecomProdAjax(p_categoryId);
    }
    if(p_depth == 4) {
        goCategoryProdListAjax();
    }
}

function goSearchProdListAjax() {
    var form				= document.pForm;
    var params = {
            'CategoryID' : "${CATEGORYINFO.CATEGORY_ID}"
            };
    fn$ajax(_LMAppUrl+"/category/ajax/searchProdListAjax.do", params, fnNmGetter().name);
}
function callBack_$goSearchProdListAjax(response) {
    $("#prodListType").html(response);
}

function optLayer(obj) {
    if($(obj).attr('id') != null && $(obj).attr('id').length > 0) {
        var prodInfos = $(obj).attr('id').split('_'); //id="B_${nRow.OPTION_YN}_${nRow.PROD_CD}_${nRow.ONLINE_PROD_TYPE_CD}_${PROD_AREA_IDX }_${nRow.CATEGORY_ID }"
        if(prodInfos[3] != null && prodInfos[3] != ""
                && ( prodInfos[3] == onlineProdMake || prodInfos[3] == onlineProdInst || prodInfos[3] == onlineProdReserve ) ) {
            //주문제작상품, 설치상품, 예약상품은 상품상세페이지로 이동
            alert(fnJsMsg(view_messages.confirm.goProductDetail));		//해당 상품은 상품 상세에서 장바구니에 담으실 수 있습니다.
            goProductDetail(prodInfos[5], prodInfos[2], 'N');
        } else {
            optLayerYn(obj, event, prodInfos[0], prodInfos[1], prodInfos[2], prodInfos[3], prodInfos[4], prodInfos[5],  document.location.href);
        }
    }
}

/*
     * 옵션레이어(2차개발 추가)
     * p_gubun : 'D':바로구매, 'B':장바구니
     * p_type : '01'. 장바구니 : '' or null or 'basket', '02'. 바로구매 : 'order'
    * p_prodTypeCd : 딜상품('05')-선택
    * p_categoryId : 카테고리ID-선택
    * p_area_idx : 상품인덱스
*/
function optLayerYn(obj, event, p_gubun, p_optionYn, p_prodCd, p_prodTypeCd, p_area_idx, p_categoryId, p_url) {

        var p_type = null;
        if(p_gubun == "D") {
            p_type = "order";
        } else {
            p_type = "basket";
        }

        if(p_optionYn=="Y") {
            var flag = global.isLogin(p_url);

            if(flag) {
                var params = {
                        'PROD_CD' : p_prodCd
                        ,'TYPE' : p_type
                        ,'PRODTYPECD' : p_prodTypeCd
                        ,'CATEGORYID' : p_categoryId
                        };

                $.ajax({
                    type: "post",
                    url: _LMAppUrl+"/product/ajax/productOptionListAjax.do",
                    data: params,
                    async: false,
                    cache: false,
                    dataType: "html",
                    success: function(data) {
                        //console.log(data);
                        $("#opt-list").html(data);
                        layerpopTriggerBtn( obj, event );
                        selectMenu();
                    },
                    error:function(data) {
                        //console.log(data);
                    }
                });
            }
        } else {
            addBasketProdListOneItem(obj, event, p_gubun, p_prodCd, p_area_idx, p_url);	//장바구니담기
        }

}

//주문수량
function calOrderQtyProd(obj, p_prodCd, p_gubun, p_area_idx, _minQuantity, _maxQuantity) {
    var minOrderQty	= 0;
    var maxOrderQty 	= 0;
    var tmpOrderQty 	= 0;				//현재 수량
    var prodIdx = "";

    if(p_area_idx != null && p_area_idx != "" && p_area_idx != "undefined" && p_area_idx != undefined) {
        prodIdx = "_" + p_area_idx;
    }

    minOrderQty 		= _minQuantity || $('#minQty_'+p_prodCd+prodIdx).val();
    maxOrderQty 		= _maxQuantity || $('#maxQty_'+p_prodCd+prodIdx).val();
    //tmpOrderQty		= $('#orderQty_'+p_prodCd+prodIdx).val();
    var in_orderQty = "";
    if(p_gubun == "incre" || p_gubun=="decre") {
        if( ($("#aList") != null && $("#aList").length > 0)  && $("#aList").hasClass("active") ) {
            $(obj).parent().siblings().each(function(index) {
                if(("#"+this.id) == ('#orderQty_'+p_prodCd+prodIdx)) {
                    tmpOrderQty = $(this).val();
                    in_orderQty = this;
                }
            });
        } else {
            $(obj).siblings().each(function(index) {
                if(("#"+this.id) == ('#orderQty_'+p_prodCd+prodIdx)) {
                    tmpOrderQty = $(this).val();
                    in_orderQty = this;
                }
            });
        }
    } else {
        tmpOrderQty = $(obj).val();
        in_orderQty = obj;
    }

    //숫자인지확인
    if(!isOnlyNumber(tmpOrderQty)) {
        //alert(fnJsMsg(view_messages.error.orderQtyNumber));	//주문수량은 숫자만 입력 가능합니다.
        return;
    }

    if(p_gubun=="incre") {
        //$('#orderQty_'+p_prodCd+prodIdx).val(Number(tmpOrderQty)+1);
        $(in_orderQty).val(Number(tmpOrderQty)+1);

    }else if(p_gubun=="decre") {
        //$('#orderQty_'+p_prodCd+prodIdx).val(Number(tmpOrderQty) < Number(minOrderQty)? Number(minOrderQty) : Number(tmpOrderQty)-1);
        $(in_orderQty).val(Number(tmpOrderQty) < Number(minOrderQty)? Number(minOrderQty) : Number(tmpOrderQty)-1);
    } else {
        //수량 하드수정
    }

    //현재수량 재셋팅
    //tmpOrderQty		= $('#orderQty_'+p_prodCd+prodIdx).val();
    tmpOrderQty	= $(in_orderQty).val();

    if(Number(tmpOrderQty) < Number(minOrderQty)) {
        alert(fnJsMsg(view_messages.error.productOrderQty, minOrderQty, maxOrderQty));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
        //$("#orderQty_"+p_prodCd+prodIdx).val(minOrderQty);
        $(in_orderQty).val(minOrderQty);
        return;
    } else if(Number(tmpOrderQty) > Number(maxOrderQty)) {
        alert(fnJsMsg(view_messages.error.productOrderQty, minOrderQty, maxOrderQty));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
        //$("#orderQty_"+p_prodCd+prodIdx).val(maxOrderQty);
        $(in_orderQty).val(maxOrderQty);
        return;
    }
}


//장바구니 담기(딜상품이미지의 장바구니)-슬라이드
function dealProdAddBasketPopOption(p_onlineProdTypeCd, p_optionYn, p_categoryId, p_prodCd) {
    goPopupProdOption(p_prodCd, 'basket', p_onlineProdTypeCd, p_categoryId);
}

//장바구니 담기(상품이미지의 장바구니)-슬라이드
function prodAddBasketPopOption(p_onlineProdTypeCd, p_optionYn, p_categoryId, p_prodCd, p_minQty, p_maxQty){
    if(p_optionYn=="Y"){
        goPopupProdOption(p_prodCd, 'basket', p_onlineProdTypeCd, p_categoryId);
    }else{
        //옵션없는경우 바로 장바구니담기
        addBasketProdListOneItem(null, null, 'B', p_prodCd, '', document.location.href);		//addBasketProdListOneItem(obj, event, p_gubun, p_prodCd, p_area_idx, p_url)
    }
}

function addBasket(obj, event) {
    alert('addBasket');
    var selectedObj = null;
    $(".prod-list").closest('.active').each(function(index, i) {
        var activeTab = $(i).attr('id');
        if(activeTab != null && activeTab.length > 0) {
            selectedObj = $(i);
        }
    });
    /* 기본설정 */
    //alert($('input[type=checkbox][id^=chk_]:checked').length+"/"+$('input[type=checkbox][id^=chk_]').length);
    if(selectedObj != null && selectedObj.length > 0) {
        if(selectedObj.find('input[type=checkbox][id^=chk_]').length==0){
            alert(fnJsMsg(view_messages.fail.addCartNoProd));	 //장바구니에 담을 수 있는 상품이 없습니다.
            return;
        }
        if(selectedObj.find('input[type=checkbox][id^=chk_]:checked').length==0){
            alert(fnJsMsg(view_messages.error.notSelected));	 	//선택된 항목이 없습니다. (상품을 1개 이상 선택해 주세요.)
            return;
        }
    } else {
        if($('input[type=checkbox][id^=chk_]').length==0){
            alert(fnJsMsg(view_messages.fail.addCartNoProd));	 //장바구니에 담을 수 있는 상품이 없습니다.
            return;
        }
        if($('input[type=checkbox][id^=chk_]:checked').length==0){
            alert(fnJsMsg(view_messages.error.notSelected));	 	//선택된 항목이 없습니다. (상품을 1개 이상 선택해 주세요.)
            return;
        }
    }

    //02.초기화(#divTemp 초기화)
    initTForm();

    //03.PARAM 셋팅
    var checkVali = "Y";
    if(selectedObj != null && selectedObj.length > 0) {
        selectedObj.find('input[type=checkbox][id^=chk_]:checked').each(function(index){
            var prodInfos = ($("#"+this.id).val()).split(';');	//prodCd;idx
            var prodCdThis = prodInfos[0];
            var prodIdx = "";
            if(prodInfos[1] != null && prodInfos[1] != "" && prodInfos[1] != "undefined" && prodInfos[1] != undefined && prodInfos[1].length > 0) {
                prodIdx = "_" + prodInfos[1];
            }
            if(checkVali=="Y"){
                if(!isOnlyNumber(selectedObj.find("#orderQty_"+prodCdThis+prodIdx).val())) {
                    alert(fnJsMsg(view_messages.error.orderQtyNumber));	//주문수량은 숫자만 입력 가능합니다.
                    checkVali = "N";
                    return;
                } else if(Number(selectedObj.find("#orderQty_"+prodCdThis+prodIdx).val()) < Number(selectedObj.find("#minQty_"+prodCdThis+prodIdx).val())){
                    alert(fnJsMsg(view_messages.error.productOrderQty, '['+selectedObj.find("#prodNm_"+prodCdThis+prodIdx).val()+']', selectedObj.find("#minQty_"+prodCdThis+prodIdx).val(), selectedObj.find("#maxQty_"+prodCdThis+prodIdx).val()));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
                    selectedObj.find("#orderQty_"+prodCdThis).val(selectedObj.find("#minQty_"+prodCdThis).val());
                    checkVali = "N";
                    return;
                } else if(Number(selectedObj.find("#orderQty_"+prodCdThis+prodIdx).val()) > Number(selectedObj.find("#maxQty_"+prodCdThis+prodIdx).val())){
                    alert(fnJsMsg(view_messages.error.productOrderQty, '['+selectedObj.find("#prodNm_"+prodCdThis).val()+']', selectedObj.find("#minQty_"+prodCdThis+prodIdx).val(), selectedObj.find("#maxQty_"+prodCdThis+prodIdx).val()));	//주문수량은 {0}개  이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
                    selectedObj.find("#orderQty_"+prodCdThis+prodIdx).val($("#maxQty_"+prodCdThis+prodIdx).val());
                    checkVali = "N";
                    return;
                }
            }

            //alert(prodCdThis+"/"+$("#itemCd_"+prodCdThis).val()+"/"+$("#orderQty_"+prodCdThis).val()+"/"+'${CATEGORYINFO.CATEGORY_ID}');
            $('#divTemp').append(genDomInput("prodCd"			, prodCdThis));
            $('#divTemp').append(genDomInput("itemCd"			, selectedObj.find("#itemCd_"+prodCdThis+prodIdx).val()));
            $('#divTemp').append(genDomInput("bsketQty"			, Number($("#orderQty_"+prodCdThis+prodIdx).val())));
            $('#divTemp').append(genDomInput("categoryId"		, selectedObj.find("#categoryId_"+prodCdThis+prodIdx).val()));
            $('#divTemp').append(genDomInput("overseaYn"		, "N")); 	//해외배송유부
            $('#divTemp').append(genDomInput("nfomlVariation"	, ""));		//옵션
            $('#divTemp').append(genDomInput("prodCouponId"		, ""));
            $('#divTemp').append(genDomInput("oneCouponId"		, ""));
            $('#divTemp').append(genDomInput("cmsCouponId"		, ""));
        });
    } else {
        $('input[type=checkbox][id^=chk_]:checked').each(function(index){
            var prodInfos = ($("#"+this.id).val()).split(';');	//prodCd;idx
            var prodCdThis = prodInfos[0];
            var prodIdx = "";
            if(prodInfos[1] != null && prodInfos[1] != "" && prodInfos[1] != "undefined" && prodInfos[1] != undefined && prodInfos[1].length > 0) {
                prodIdx = "_" + prodInfos[1];
            }
            if(checkVali=="Y"){
                if(!isOnlyNumber($("#orderQty_"+prodCdThis+prodIdx).val())) {
                    alert(fnJsMsg(view_messages.error.orderQtyNumber));	//주문수량은 숫자만 입력 가능합니다.
                    checkVali = "N";
                    return;
                } else if(Number($("#orderQty_"+prodCdThis+prodIdx).val()) < Number($("#minQty_"+prodCdThis+prodIdx).val())){
                    alert(fnJsMsg(view_messages.error.productOrderQty, '['+$("#prodNm_"+prodCdThis+prodIdx).val()+']', $("#minQty_"+prodCdThis+prodIdx).val(), $("#maxQty_"+prodCdThis+prodIdx).val()));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
                    $("#orderQty_"+prodCdThis).val($("#minQty_"+prodCdThis).val());
                    checkVali = "N";
                    return;
                } else if(Number($("#orderQty_"+prodCdThis+prodIdx).val()) > Number($("#maxQty_"+prodCdThis+prodIdx).val())){
                    alert(fnJsMsg(view_messages.error.productOrderQty, '['+$("#prodNm_"+prodCdThis).val()+']', $("#minQty_"+prodCdThis+prodIdx).val(), $("#maxQty_"+prodCdThis+prodIdx).val()));	//주문수량은 {0}개  이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
                    $("#orderQty_"+prodCdThis+prodIdx).val($("#maxQty_"+prodCdThis+prodIdx).val());
                    checkVali = "N";
                    return;
                }
            }

            //alert(prodCdThis+"/"+$("#itemCd_"+prodCdThis).val()+"/"+$("#orderQty_"+prodCdThis).val()+"/"+'${CATEGORYINFO.CATEGORY_ID}');
            $('#divTemp').append(genDomInput("prodCd"			, prodCdThis));
            $('#divTemp').append(genDomInput("itemCd"			, $("#itemCd_"+prodCdThis+prodIdx).val()));
            $('#divTemp').append(genDomInput("bsketQty"			, Number($("#orderQty_"+prodCdThis+prodIdx).val())));
            $('#divTemp').append(genDomInput("categoryId"		, $("#categoryId_"+prodCdThis+prodIdx).val()));
            $('#divTemp').append(genDomInput("overseaYn"		, "N")); 	//해외배송유부
            $('#divTemp').append(genDomInput("nfomlVariation"	, ""));		//옵션
            $('#divTemp').append(genDomInput("prodCouponId"		, ""));
            $('#divTemp').append(genDomInput("oneCouponId"		, ""));
            $('#divTemp').append(genDomInput("cmsCouponId"		, ""));
        });
    }

    /* alert($('#divTemp').html()); */
    if(checkVali=="Y"){
        /* if (!confirm('<spring:message code="msg.category.confirm.addCart"/>')) {	//선택한 상품을 장바구니에 담으시겠습니까?
            return;
        } */
        fnAddBasketItem();
    }
}

/*
 * 장바구니 담기1(옵션 없는 상품)
*/
function addBasketProdListOneItem(obj, event, p_gubun, p_prodCd, p_area_idx, p_url) {
    var flag = global.isLogin(p_url);

    if(flag) {
        var prodIdx = "";
        if(p_area_idx != null && p_area_idx != "" && p_area_idx != "undefined" && p_area_idx != undefined) {
            prodIdx = "_" + p_area_idx;
        }
        /* 기본설정 */
        var prodCd		 		= $("#prodCd_"+p_prodCd+prodIdx).val();
        var categoryId	 		= $("#categoryId_"+p_prodCd+prodIdx).val();
        var tmpOrdedQty 		= 1;
        if(prodIdx != "") {
            tmpOrdedQty 		= $("#orderQty_"+p_prodCd+prodIdx).val();
        }
        var minOrderQty 		= $("#minQty_"+p_prodCd+prodIdx).val();			//최소구매수량
        var maxOrderQty 		= $("#maxQty_"+p_prodCd+prodIdx).val();			//현재 구매할수 있는 수량

        var itemCd	 			= "001";								//단품코드

        //01.수량 체크
        if(isOnlyNumber(tmpOrdedQty) == false) {
            alert(fnJsMsg(view_messages.error.orderQtyNumber));	//주문수량은 숫자만 입력 가능합니다.
            return;
        } else if(Number(tmpOrdedQty) < Number(minOrderQty)){
            alert(fnJsMsg(view_messages.error.productOrderQty, minOrderQty, maxOrderQty));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
            $("#orderQty_"+p_prodCd).val(minOrderQty);
            return;
        } else if(Number(tmpOrdedQty) > Number(maxOrderQty)){
            alert(fnJsMsg(view_messages.error.productOrderQty, minOrderQty, maxOrderQty));	//주문수량은 {0}개 이상 {1}이하 가능합니다.\\n대량 주문을 원하시는 고객님께서는 \\n다음 번호로 전화주시면 친절하게 상담해 드리겠습니다.\\n(TEL. 1577-2500)
            $("#orderQty_"+p_prodCd).val(maxOrderQty);
            return;
        }
        var basketItems=[];
        basketItems.push({
            prodCd: prodCd,				// 상품코드
            itemCd: itemCd,				// 단품코드
            bsketQty: Number(tmpOrdedQty),		// 주문수량
            categoryId: categoryId,			// 카테고리ID
            nfomlVariation: null,				// 옵션명, 골라담기의 경우 옵션명:수량
            overseaYn: 'N',						// 해외배송여부
            prodCouponId: null,				// 즉시할인쿠폰ID
            oneCouponId: null,				// ONE 쿠폰ID
            cmsCouponId: null,				// CMS 쿠폰ID
            markCouponId: null,				// 마케팅제휴쿠폰ID
            periDeliYn: "N"						// 정기배송여부
        });

        /* alert($('#divTemp').html()); */
        if(p_gubun == "B") {
            /*if (!confirm(fnJsMsg(view_messages.confirm.addCart))) {	//선택한 상품을 장바구니에 담으시겠습니까?
                return;
            }*/
//			fnAddBasketItem();	//as-is
            global.addBasket(basketItems, function(data) {
//                fn.basketCnt.call();
//				if( mobile == 'ios' ){
//					location.href="lottemartapp://basketcountupdate";
//				} else if( mobile == 'android'){
//					location.href="lottemartmall://basketcountupdate";
//				}
                schemeLoader.loadScheme({key: 'basketCountUpdate'});
                alert("선택하신 상품이 장바구니에 등록되었습니다.");
                
                //2016.11.01 GA 장바구니담기 스크립트
                //2016.11.22 GA Script 주석처리
				/*var basketGaItem = data.basketItem;
				ga('require', 'ec');
				ga('ec:addProduct',{
					'id' : basketGaItem.prodCd[0],
					'name' : basketGaItem.prodNm[0],
					'category' : basketGaItem.categoryId[0],
					'brand' : '',
					'variant' : '',
					'price' : '',
					'quantity' : basketGaItem.bsketQty
				});
				ga('ec:setAction', 'add');
				ga('send', 'event', location.pathname, 'add to cart');*/
				
//				var obj_layer = $("#basket-confirm");
                // 장바구니 상품 담긴 후 호출
                //layerpopTriggerBtn($(obj_layer), event);
            });
        } else if(p_gubun == "D" ) {
            //fnDirectOrderItem();
            /*global.addDirectBasket(basketItems, function(data) {
                fn.basketCnt.call();
            });*/

            global.addDirectBasket(basketItems, function(xhr){
//                fn.basketCnt.call();

                var data = $.parseJSON(xhr.responseText);
                if (data && data.message) {
                    alert(data.message);
                    return;
                }
            });	//바로구매
        }


    }
}
//상품리스트 스크립트 E=================
//상품 개별 찜/찜취소하기
function addProdWishList(obj, event, p_prodCd, p_categoryId, p_forgnDelyplYn, p_area_idx, p_url) {
    if("${const:getString('NO_MEMBER_NO')}" == "${_FrontSessionEntity.member_no}"){
          alert(fnJsMsg(view_messages.fail.noMember));		//비회원은 찜하기가 안됩니다.
          return;
    }

    var flag = global.isLogin(p_url);

    if(flag) {
        var prodIdx = "";
        var prodIdx2 = "";
        if(p_area_idx != null && p_area_idx != "" && p_area_idx != "undefined" && p_area_idx != undefined) {
            prodIdx = "_" + p_area_idx;
            prodIdx2 = "-" + p_area_idx;
        }
        var cateId = "";
        if(p_categoryId != null && p_categoryId != "" && p_categoryId != "undefined" && p_categoryId != undefined) {
            cateId = "_" +p_categoryId;
        }
        var wishYn = "Y";		// 'Y':찜, 'N':찜취소
        var defaultDatas = {};
        if(p_prodCd != "") {
            defaultDatas = {
                    categoryId:p_categoryId
                    ,prodCd: p_prodCd            // 상품코드
                    ,forgnDelyplYn: p_forgnDelyplYn           // 해외배송여부
                };
            if($("#aList") != null && $("#aList").length > 0) {
                if($("#aList").hasClass("active")) {
                    if($("#wish_"+p_prodCd+prodIdx+cateId).html() == "찜") {
                        wishYn = "Y";
                    } else {
                        wishYn = "N";
                    }
                } else {
                    if($("#wish_"+p_prodCd+prodIdx+cateId).hasClass("active")) {
                        wishYn = "N";
                    } else {
                        wishYn = "Y";
                    }
                }
            } else {
                if($("#wish_"+p_prodCd+prodIdx+cateId).hasClass("active")) {
                    wishYn = "N";
                } else {
                    wishYn = "Y";
                }
            }
        } else {

        }
        var classNm = (wishYn=="Y" ? "selected":"selected-cancel");
        if(wishYn == "N") {
            defaultDatas = {
                    prodCd: p_prodCd            // 상품코드
                };
            global.deleteWish(defaultDatas, function(data) {
                if(data == "") {
                    data = {message : "찜하기가 취소되었습니다"};
                }
                if (typeof(RNB) != "undefined") {
                    RNB.wish.load();
                }
                wishResult(obj, event, p_prodCd, p_categoryId, p_area_idx, classNm, data);
            },
            function(xhr, status, error) {
                if(xhr.status == "200" && xhr.statusText == "OK") {
                    data = {message : "찜하기가 취소되었습니다"};
                }
                if (typeof(RNB) != "undefined") {
                    RNB.wish.load();
                }
                wishResult(obj, event, p_prodCd, p_categoryId, p_area_idx, classNm, data);
            }
            );
        } else {
            global.addWish(defaultDatas, function(data) {
                wishResult(obj, event, p_prodCd, p_categoryId, p_area_idx, classNm, data);
            });
        }
    }
}

function wishResult(obj, event, p_prodCd, p_categoryId, p_area_idx, classNm, data) {
    var msg = data.message;
    var templateStr = $.templates("#wish-prod");
    var resultLayer = templateStr.render({
        "msg": msg,
        "classNm": classNm
    });
    $("#wish-prod").text(resultLayer);

    layerpopTriggerBtn( $(obj), event );

    if($("#aList") != null && $("#aList").length > 0) {
        if($("#aList").hasClass("active")) {
            if($("#wish_"+p_prodCd+"_"+p_area_idx+"_"+p_categoryId).html() == "찜") {
                $("button[id^=wish_"+p_prodCd+"]").html("찜취소");
            } else {
                $("button[id^=wish_"+p_prodCd+"]").html("찜");
            }
        } else {
            if(classNm == "selected") {
                    $("a[id^=wish_"+p_prodCd+"]").addClass("active");
                } else {
                    $("a[id^=wish_"+p_prodCd+"]").removeClass("active");
                }
        }
    } else {
        if(classNm == "selected") {
            $("a[id^=wish_"+p_prodCd+"]").addClass("active");
        } else {
            $("a[id^=wish_"+p_prodCd+"]").removeClass("active");
        }
        }

}

//상품목록 상,하단- 찜하기(선택된 상품 찜하기)
function addWishList(obj, event, _url) {
    var chkCnt = $('.compare-check').find('input[type=checkbox][id^=chk_]:checked').length;

    if($('.compare-check').find('input[type=checkbox][id^=chk_]').length==0) {
        alert(fnJsMsg(view_messages.fail.addWishNoProd));	//찜바구니에 담을 수 있는 상품이 없습니다.
        return;
    }
    if($('.compare-check').find('input[type=checkbox][id^=chk_]:checked').length==0) {
        alert(fnJsMsg(view_messages.error.notSelected));		//선택된 항목이 없습니다. (상품을 1개 이상 선택해 주세요.)
        return;
    }

    var flag = global.isLogin(_url);
    if(flag) {

        var params = {};
        params["prodCds"] = [];
        params["forgnDelyplYns"] = [];
        params["categoryIds"] = [];

        $('.compare-check').find('input[type=checkbox][id^=chk_]:checked').each(function(index){
            var prodInfos = ($('.compare-check').find("#"+this.id).val()).split(';');
            params["prodCds"].push(prodInfos[0]);
            params["forgnDelyplYns"].push("N");
            params["categoryIds"].push(prodInfos[2]);
        });

        var classNm = "selected";

        $.ajax({
            url: "/quickmenu/api/mywish/add.do",
            data: params,
            method: "post",
            cache: false,
            dataType: "json",
            traditional: true,
            success: function(data) {
                var templateStr = $.templates("#wish-prod");
                var resultLayer = templateStr.render({
                    "msg": data.message,
                    "classNm": classNm
                });
                $("#wish-prod").text(resultLayer);

                layerpopTriggerBtn( $(obj), event );

                $.each(params["prodCds"], function(i, value) {
                     if($("#aList") != null && $("#aList").length > 0) {
                         if($("#aList").hasClass("active")) {
                             $("button[id^=wish_"+value+"]").html("찜취소");
                         } else {
                             $("a[id^=wish_"+value+"_").addClass("active");
                         }
                     } else {
                        $("a[id^=wish_"+value+"_").addClass("active");
                     }
                });

            },
            error:function(data) {
                //console.log(data);
            }
        });
    }
}

//상품 찜/찜취소하기
function fnProdAddWishList(prodCds, forgnDelyplYns, categoryIds, wishYn){
    var returnValue = "";
    var params = {};
    params["prodCds"] = [];
    params["forgnDelyplYns"] = [];
    params["categoryIds"] = [];

    $.each(prodCds, function(i, value){
        params["prodCds"].push( value );
    });
    $.each(forgnDelyplYns, function(i, value){
        params["forgnDelyplYns"].push( value );
    });
    $.each(categoryIds, function(i, value){
        params["categoryIds"].push( value );
    });
    var _url = "";
    if(wishYn == "Y") {
        _url = _LMAppSSLUrl+"/mymart/ajax/insertWishList.do";
    } else {
        _url = _LMAppSSLUrl+"/mymart/ajax/deleteProdWishList.do";
    }

    $.ajax({
        type       : "POST" ,
        url        :  _url ,
        data       : params ,
        async      : false ,
        dataType   : "text" ,
        success    : function(response){
            var jsonData = eval( "(" + response + ")" );
                var result = jsonData[0];

             returnValue = result.ERR_NO+":" +result.SHOW_MSG;
        } ,
        cache      : true ,
        error : function(request,status,error){
            //alert("code:"+request.status+"error:"+error+"\n"+"message:"+request.responseText+"\n");
            returnValue =  request.status;
        }
    });

    return returnValue;
}

//상품목록 상,하단 - 비교하기
function goCompare() {
    //01.수량 체크
    var chkCnt = $('input[type=checkbox][id^=chk_]:checked').length;

    if($('input[type=checkbox][id^=chk_]').length==0){
        alert(fnJsMsg(view_messages.error.compareNoProd));   //비교하기 할 수 있는 상품이 없습니다.
        return;
    }

    if($('input[type=checkbox][id^=chk_]:checked').length==0){
        alert(fnJsMsg(view_messages.error.notSelected));     //선택된 항목이 없습니다. (상품을 1개 이상 선택해 주세요.)
        return;
    }

    //2,3개만 가능
    var maxCount =  "";
    if($('input[type=checkbox][id^=chk_]:checked').length < 2
            || $('input[type=checkbox][id^=chk_]:checked').length > 3
    ){
        alert(fnJsMsg(view_messages.fail.compareProdCount));    //비교하기는 2개 이상 3개 이하만 가능합니다.
        return;
        }

    //02.PARAM 셋팅
    var prods       = "";   //비교할 상품들
    $('input[type=checkbox][id^=chk_]:checked').each(function(index){
        var prodInfos = ($('.compare-check').find("#"+this.id).val()).split(';');
        prods += (index==0) ? prodInfos[0] : "|^|"+prodInfos[0];
    });

    //03.전송
    goProductCompare(prods);
}

// 2차 개발 : 찜하기
function goNewWishList(obj, event,prodCd,p_area_idx,categoryId,url) {
    var overseaYn ="N";

        var flag = global.isLogin(url);
        if(flag) {//로그인여부
            if(!global.isMember()){
                //alert("비회원은 찜바구니에 상품을 담을수 없습니다.");
                alert( view_messages.member.no_member);
                return;
            }

            var actGb = "access";
             if($(obj).hasClass("active")) {
                 actGb = "cancel";
             }

            wishItems = ({
                "prodCd": prodCd,
                "categoryId": categoryId,
                "forgnDelyplYn": overseaYn
            });

            if(actGb == "cancel") {
                global.deleteWish(wishItems, function(data) {
                    $('.pop-toast-'+actGb).text("찜하기가 취소되었습니다.").addClass('active');
                    $("#wish_"+prodCd+"_"+p_area_idx+"_"+categoryId).removeClass("active");
                });
            } else {
                global.addWish(wishItems, function(data) {
                    $('.pop-toast-'+actGb).text(data.message).addClass('active');
                    $("#wish_"+prodCd+"_"+p_area_idx+"_"+categoryId).addClass("active");
                });
            }

            $('[class|=pop-toast]').on('animationend webkitAnimationEnd', function() {
                $(this).removeClass('active');
            });
    }
}
