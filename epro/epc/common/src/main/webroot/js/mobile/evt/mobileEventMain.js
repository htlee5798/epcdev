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
        errorRequired = '',
        errorNumber = '',
        errorNotSelected = '',
        alertCouponNo = '',
        scpIsMembers = '',
        sessionCouponId = '',
        loadingFooterClass = 'footer-loading',
        $container = '',
        $tabs = '',
        $body = $('body'),
        $footer = $( '#footer' ),
        $couponList = '',
        $couponregist = "",
        $mcouponList = '',
        $mcouponLayer = "",
        $ecouponList = '',
        nowLoading = false,
        selectedTabId = "",
        infiniteScroll = null;

    var url = document.location.href;

    var _init = function (obj) {
        hash = obj.hash;
        errorRequired = obj.errorRequired;
        errorNumber = obj.errorNumber;
        errorNotSelected = obj.errorNotSelected;
        alertCouponNo = obj.alertCouponNo;
        scpIsMembers = obj.scpIsMembers;
        sessionCouponId = obj.sessionCouponId;

        $container = $('[data-pageid="'+ hash +'"]');
        $tabs = $container.find('.tabmenu-type4');

        $couponList = $container.find('div#mainCouponList');
        $couponregist = "";
        $mcouponList = $container.find('div#mainMCouponList');
        $mcouponLayer = "";
        $ecouponList = $container.find('div#mainAliveList');
        nowLoading = false;
        selectedTabId = "";
        infiniteScroll = new _InfiniteScroll({
            container : $container,
            mcouponList : $mcouponList,
            callback : getMore
        });

        if(url.indexOf("tabId") > -1){
            var para = url.split("tabId=");
            var tabId = para[1].substring(0,1);
            if( $("#tabid"+tabId).length ) {
                $(".tabmenu-type4 a").removeClass('active');
                $("#tabid"+tabId).addClass('active');
            }
        }

        if ($container.find('.eventcoupon-slider .swiper-wrapper li').length > 1) {
            //모바일 상단 콘텐츠 스와이프
            $container.find('.eventcoupon-slider').swiper({
                pagination:$container.find( '.eventcoupon-slider .swiper-pagination' )[0]
                , centeredSlides:true
                , slidesPerView:'auto'
                , loop:true
            });
        }

        $(window).on('scroll.mainScroll', function () {
            infiniteScroll.setPosition($(window).scrollTop());
        });

        $tabs.on('click', 'a', goTabChange);

        $container
            .on('click', 'a[name=fnAddMyCoupon]', InsertCoupon)
            .on('click', 'div.action > a', downCoupon)
            .on('click', 'div.action > button', downCoupon)
            .on('click', '.mcouponGoMStamp', goMStamp)
            .on('click', '.select-mcouponcatetory .select a', openlayerForMcoupon)
            .on('click', 'div.mcoupon-search > a', goMCouponSearch)
            .on('click', 'button[name=goCouponLogin]', goCouponLogin)
            .on('click', 'button[name=goMcouponLogin]', goMcouponLogin)
            .on('click', 'button[name=goMcouponJoin]', goMcouponJoin)
            .on('click', 'button[name=loadMcoupon]', loadMcoupon)
            .on('click', '.mcouponDetailItem', goMCouponDetail)
            .on('click', 'div.appDown > a', appDown);
        $body
            .on('click', 'button[name=goCouponCategoryBtn]', goCouponCategory)
            .on('click', 'button[name=goMyMart]', goMyMart);

        $container.find("input[id^=chkCategory_]").on('click', function(){
            var allLen = $container.find("input[id^=chkCategory_]").length;
            var Chk = $container.find("input[id^=chkCategory_]:checked").length;
            if(allLen !== Chk){
                $container.find("#MAllCheck").prop("checked", false);
            }
        });

        $('.main-slide').on('click', '.event-toggle', function(e) {
            if (!$(e.target).is('a')) $(this).toggleClass('active');
        });

        setPageIndex();

        if(isEmptyContent( $container.find('#mainCouponList .couponregist'))) {
            getList(selectedTabId);
        }
    };

    function setPageIndex() {
        selectedTabId = $tabs.find('a[data-tabid].active').data('tabid');
        $('form[name="searchForm"] > [name="tabId"]', $container).val(selectedTabId);
    }

    function getMore(container){
        if (window.getHistoryBack()) {
            window.setHistoryBack(false);
            return;
        }
        if(nowLoading) {
            return;
        }

        var tabId = $("form[name='searchForm'] > [name='tabId']", $container).val();
        var rowPage = 5;
        var cPage = 1;
        var totAll = 1;
        var strCd = $("form[name='searchForm'] > [name='str_Cd']", $container).val();
        var params = {};
        params = {'tabId' : tabId };

        // url 분리
        var goToUrl = "";
        if(tabId == 1){
            goToUrl = $.utils.config('LMAppUrlM') + "/mobile/evt/ajax/couponAjaxList.do";
            rowPage = $("form[name='cFrm'] > [name='rowPage']", $couponList).val();
            totAll = $("form[name='cFrm'] > [name='totAll']", $couponList).val();
            cPage = $("form[name='cFrm'] > [name='cPage']", $couponList).val();
            cPage = parseInt(cPage) +1;
            params = {'tabId' : tabId, 'cPage': cPage };
            $("form[name='cFrm'] > [name='cPage']", $couponList).val(cPage);
        }else if(tabId == 2){
            goToUrl = $.utils.config('LMAppUrlM') + "/mobile/evt/ajax/mCouponAjaxList.do";
            var searchName = $("input[name=tmpSearchName]", $mcouponList).val();
            var categoryId = $('form[name="mfrm"] > [name="categoryId"]', $mcouponList).val();

            rowPage = $("form[name='mfrm'] > [name='rowPage']", $mcouponList).val();
            totAll = $("form[name='mfrm'] > [name='totAll']", $mcouponList).val();
            cPage = $("form[name='mfrm'] > [name='cPage']", $mcouponList).val();
            cPage = parseInt(cPage) +1;
            $("form[name='mfrm'] > [name='cPage']", $mcouponList).val(cPage);
            params = {'tabId' : tabId, 'searchName' : searchName, 'categoryId' : categoryId, 'cPage': cPage };

            if(!installMcouponAppChk()){
                return;
            }
        }else{
            rowPage = $("form[name='eFrm'] > [name='rowPage']", $ecouponList).val();
            totAll = $("form[name='eFrm'] > [name='totAll']", $ecouponList).val();
            cPage = $("form[name='eFrm'] > [name='cPage']", $ecouponList).val();
            cPage = parseInt(cPage) +1
            params = {'tabId' : tabId, 'cPage': cPage, 'str_Cd' : strCd };
            $("form[name='eFrm'] > [name='cPage']", $ecouponList).val(cPage);
            goToUrl = $.utils.config('LMAppUrlM') + "/mobile/evt/ajax/eventAjaxList.do";
        }

        var totalPageCount = Math.ceil(parseInt(totAll) / parseInt(rowPage));

        if(cPage <= totalPageCount){
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
                        if( $("form[name='cFrm'] > [name='cPage']", $couponList).length > 0 ) {
                            var cPage = $("form[name='cFrm'] > [name='cPage']", $couponList).val();

                            $("form[name='cFrm'] > [name='cPage']", $couponList).val( cPage - 1 );
                        }
                    }
                }
            });
        }else{
            $footer.removeClass( loadingFooterClass );
            $container.find( '.msg-info' ).show();
        }
    }

    function renderMore(response){
        $container.moreBar(false);
        nowLoading = false;
        switch (selectedTabId) {
            case 1:
                $couponList.find('.couponList').append(response);
                break;
            case 2:
                $mcouponList.find('.mcouponList').append(response);
                break;
            default :
                $ecouponList
                    .find('.eventList')
                    .append(response)
                    .promise()
                    .done(function() {
                        $ecouponList
                            .find('.eventList')
                            .find( '> a' )
                            .each(function() {
                                $( this ).attr( 'data-gtm', selectedTabId === 3 ? 'M062' : 'M063' );
                            });
                    });
                break;
        }
    }

    //이벤트 탭 선택
    function goTabChange(e){
        e.preventDefault();
        var _this = $(this);

        $footer.addClass( loadingFooterClass );

        _this.addClass('active').siblings().removeClass('active');

        var tabValue = $(this).data("tabid");

        if(tabValue == 'undefined'){ tabValue = 1; }
        selectedTabId = tabValue;
        setPageIndex();
        getList(tabValue);
    }

    function getList(tabId){
        if (window.getHistoryBack()) {
            window.setHistoryBack(false);
            return;
        }
        var pUrl = "";
        var strCd = $("form[name='searchForm'] > [name='str_Cd']", $container).val();
        if(tabId == '1'){
            pUrl = $.utils.config('LMAppUrlM') +"/mobile/evt/ajax/eventMainCouponList.do";
        }else if(tabId == '2'){
            pUrl = $.utils.config('LMAppUrlM') +"/mobile/evt/ajax/mobileEventMcoupon.do";

        }else{
            pUrl = $.utils.config('LMAppUrlM') +"/mobile/evt/ajax/mobileEventAliveList.do";
        }

        $.ajax({
            type : "GET",
            data : {"tabId" : tabId, 'str_Cd' : strCd},
            url : pUrl,
            dataType : "html",
            success : function(data){
                $(".tabmenu-type4", $container).addClass('active').siblings().removeClass('active');
                $(".tabcontents", $container).addClass('active').siblings().removeClass('active');
                $('div[id^=eczoneTab]', $container).removeClass("active").hide();

                if(tabId == '1'){
                    $('#eczoneTab'+tabId, $container).show();
                    $("#mainCouponList", $container).html(data);
                    $couponregist = $('div.couponregist', $container);
                    var $article = $couponregist.find('div.Insert');
                    var $form1 = $couponregist.find("form[name=form1]");

                    if(sessionCouponId) {
                        sessionStorage.setItem('sessionCouponId', sessionCouponId);
                    }

                    $form1.find("input[name=mdCouponId]").val(sessionStorage.getItem('sessionCouponId'));

                }else if(tabId == '2'){
                    $('#eczoneTab'+tabId, $container).show();
                    $("#mainMCouponList", $container).html(data);
                    $mcouponLayer = $('div.mcategoryLayer1', $container);
                    var $mcList = $container.find('div#mcouponList');
                    if( $("#mainMCouponList", $container).find( '.list-empty' ).length > 0 ) {
                        $footer.removeClass( loadingFooterClass );
                    }

                }else{
                    var $mainAliveList = $container.find("#mainAliveList");
                    $mainAliveList.html(data).show();
                    $container
                        .find('#eczoneTab'+tabId)
                        .show()
                        .find( '.eventList' )
                        .find( '> a' )
                        .each(function() {
                            $( this ).attr( 'data-gtm', selectedTabId === 3 ? 'M062' : 'M063' );
                        });
                }


                $("form[name='cFrm'] > [name='cPage']", $couponList).val(1);
                $("form[name='mFrm'] > [name='cPage']", $mcouponList).val(1);
                $("form[name='eFrm'] > [name='cPage']", $ecouponList).val(1);

            }
        });
        $footer.removeClass( loadingFooterClass );
    }

    //쿠폰 등록
    function InsertCoupon(){
        var requestUri = location.href.replace( location.origin + '/mobile', '' ).replace( '&ishome=true', '' );


        ga('send', 'event', {
            eventCategory: '#몰구분=Mart #기기=M #분류=컨텐츠전시페이지(메인GNB) #페이지명=이벤트/쿠폰 #URL=' + requestUri,
            eventAction: '혜택/참여컨텐츠 보기',
            eventLabel: '#타입=쿠폰 #고객액션=쿠폰등록시도'
        });

        $couponregist = $('div.couponregist', $container);
        if ( !global.isLogin($.utils.config('LMAppUrlM') + "/mobile/evt/eventMain.do") ){
            setTimeout( function () {
                $couponregist.find(".btn-form-color1.dotline-v.js-ttlayer").removeClass('active');
                $couponregist.find(".layerpopup-type1.Insert").hide();
            },1);

            ga('send', 'event', {
                eventCategory: '#몰구분=Mart #기기=M #분류=컨텐츠전시페이지(메인GNB) #페이지명=이벤트/쿠폰 #URL=' + requestUri,
                eventAction: '혜택/참여컨텐츠 보기',
                eventLabel: '#타입=쿠폰 #고객액션=쿠폰등록실패'
            });
            return;
        }

        var $form1 = $couponregist.find("form[name=form1]");
        var mdCouponId = $form1.find("input[name=mdCouponId]").val();
        if(mdCouponId.trim() == "" || mdCouponId == "발급받으신 쿠폰번호를 입력해주세요."){
            alert(fnJsMsg(errorRequired, alertCouponNo));
            $form1.find("input[name=mdCouponId]").select();
            ga('send', 'event', {
                eventCategory: '#몰구분=Mart #기기=M #분류=컨텐츠전시페이지(메인GNB) #페이지명=이벤트/쿠폰 #URL=' + requestUri,
                eventAction: '혜택/참여컨텐츠 보기',
                eventLabel: '#타입=쿠폰 #고객액션=쿠폰등록실패'
            });
            return;
        }

        if( isNaN(mdCouponId) ){
            alert(fnJsMsg(errorNumber, alertCouponNo));
            $form1.find("input[name=mdCouponId]").select();
            ga('send', 'event', {
                eventCategory: '#몰구분=Mart #기기=M #분류=컨텐츠전시페이지(메인GNB) #페이지명=이벤트/쿠폰 #URL=' + requestUri,
                eventAction: '혜택/참여컨텐츠 보기',
                eventLabel: '#타입=쿠폰 #고객액션=쿠폰등록실패'
            });
            return;
        }

        $.ajax({
            type       : "POST" ,
            url        :  $.utils.config('LMAppUrlM') + "/mobile/evt/insertMyCoupon.do",
            data       : $form1.serialize() ,
            async      : false ,
            dataType   : "json" ,
            success    : function(data){
                sessionStorage.setItem('sessionCouponId', '');
                if((data.rtnCode == "Done" )){
                    $container.find(".couponMessage").html('쿠폰을 이미 등록하셨습니다.<br><span class="point">‘마이롯데 > 쿠폰’</span>에서 확인하세요.' );
                    $container.find(".acenter").show();
                    insertCouponLayer();

                    ga('send', 'event', {
                        eventCategory: '#몰구분=Mart #기기=M #분류=컨텐츠전시페이지(메인GNB) #페이지명=이벤트/쿠폰 #URL=' + requestUri,
                        eventAction: '혜택/참여컨텐츠 보기',
                        eventLabel: '#타입=쿠폰 #고객액션=쿠폰등록실패'
                    });

                }else if((data.rtnCode == "Success")){
                    $container.find(".couponMessage").html('쿠폰이 등록 완료되었습니다.<br><span class="point">‘마이롯데 > 쿠폰’</span>에서 확인하세요.' );
                    $container.find(".acenter").show();
                    insertCouponLayer();

                    ga('send', 'event', {
                        eventCategory: '#몰구분=Mart #기기=M #분류=컨텐츠전시페이지(메인GNB) #페이지명=이벤트/쿠폰 #URL=' + requestUri,
                        eventAction: '혜택/참여컨텐츠 보기',
                        eventLabel: '#타입=쿠폰 #고객액션=쿠폰등록성공'
                    });
                }else if((data.rtnCode == "Fail" && data.rtnMsg.indexOf('통합회원') > -1)){
                    alert(data.rtnMsg);
                }else{
                    alert('해당 쿠폰이 존재하지 않습니다.');
                    $form1.find("input[name=mdCouponId]").select();

                    ga('send', 'event', {
                        eventCategory: '#몰구분=Mart #기기=M #분류=컨텐츠전시페이지(메인GNB) #페이지명=이벤트/쿠폰 #URL=' + requestUri,
                        eventAction: '혜택/참여컨텐츠 보기',
                        eventLabel: '#타입=쿠폰 #고객액션=쿠폰등록실패'
                    });
                }
            },
            error : function(){
                alert(data.rtnMsg);
                $form1.find("input[name=mdCouponId]").select();
            }
        });
    }

    function insertCouponLayer(e){
        var obj = $container.find('a[name=fnAddMyCoupon]'),
            $parent = obj.closest('.main-slide'),
            objTgt = obj.attr('data-layer'),
            objPosTop = (obj.offset().top - $parent.offset().top) + obj.outerHeight() + 10;

        if (objTgt) {
            objTgt = objTgt.replace('#', '');
            areaTooltipLayerPopup(obj, objTgt, objPosTop, $container);
        }
    }

    function downCoupon(){
        var $wrapper = $couponList.find('#page-wrapper');
        var masterCouponID = $(this).data("couponid");
        var offset = $(this).offset();
        var top = offset.top - $wrapper.offset().top;

        $(this).find('.layerpopup-type1').hide();

        if ( !global.isLogin($.utils.config('LMAppUrlM') +"/mobile/evt/eventMain.do") ){
            setTimeout( function () {
                $couponList.find(".txt.js-ttlayer").removeClass('active');
                $couponList.find("#Down_"+masterCouponID).hide();
            },1);
            return;
        }

        $couponList.find(".btn-form-color1.dotline-v.js-ttlayer").removeClass('active');

        $.ajax({
            url: $.utils.config('LMAppUrlM') +"/mobile/evt/mDownCoupon.do?masterCouponID="+masterCouponID,
            type: "GET",
            dataType:"json",
            success:function(data){
            	$(".layerpopup-type1").hide();

            	//$(this).parents().find('.layerpopup-type1').hide();
                if((data.rtnMsg.indexOf("이미 다운") > -1 )){
                    $("#Down_"+masterCouponID).css('top', top).show();
                    $("#couponDownDone_"+masterCouponID).show();
                    $("#couponDownOK_"+masterCouponID).hide();

                    $('.js-close').on('click', function() {
                        $(this).closest('.layerpopup-type1').hide();
                    });
                } else if ( (data.rtnCode == "SUCC") ) {
                    var $el = $('#couponZone_' + masterCouponID + ' .desc');
                    var cpEnd = $el.eq(1).html();
                    cpEnd = cpEnd.slice(cpEnd.length-10, cpEnd.length);
                    var now = new Date();
                    var then = new Date(cpEnd.replace(/\./gi,'/')); // 쿠폰만료일자
                    var dday = then.getTime() - now.getTime();
                    dday = Math.floor(parseInt(dday) / (1000 * 60 * 60 * 24)) + 1;

                    $('#couponZone_' + masterCouponID).addClass('complate');
                    $('#couponDownBtn_' + masterCouponID).find('button').remove();
                    $('#couponDownBtn_' + masterCouponID).append('<p class="dday">D-' + dday + '</p>');
                    $('#couponDownBtn_' + masterCouponID).append('<p class="desc">다운 완료</p>');
                    $("#couponDownOK_"+masterCouponID).show();
                    $("#couponDownDone_"+masterCouponID).hide();
                    $("#Down_"+masterCouponID).css('top', top).show();

                    $couponList.find('.js-close').on('click', function() {
                        $(this).closest('.layerpopup-type1').hide();
                    });
                } else {
                    $("#couponDownDone_"+masterCouponID).hide();
                    $("#couponDownOK_"+masterCouponID).hide();
                    $("#Down_"+masterCouponID).css('top', top).hide();
                    alert(data.rtnMsg);
                    $(".txt.js-ttlayer").removeClass('active');
                }

                if(data.rtnMsg[0] == '\r') {
                    goLogin("MMARTSHOP", $.utils.config('LMAppUrlM') +"/mobile/evt/mobileEventMain.do");
                }
            }
        });
        return false;
    }

    //Mstamp list
    function goMStamp(){
        var flag = global.isLogin($.utils.config('LMAppUrlM') +'/mobile/evt/eventMain.do?tabId=2');
        if(flag){
            location.href=$.utils.config('LMAppUrlM') +"/mobile/evt/mobileEventMStampList.do";
        }
    }

    function goMyMart(){
        $container.find('.layerpopup-type1').css('display', 'none');
        $container.find('.mask').removeClass();
        location.href = $.utils.config('LMAppUrlM') +"/mobile/mypage/myCoupon.do";
    }

    //M쿠폰 상세 이동
    function goMCouponDetail(){ location.href = $.utils.config('LMAppUrlM') +"/mobile/evt/mobileEventMCouponDetail.do?couponId=" + $(this).data('couponid'); }

    function goCouponLogin(){
        if ( !global.isLogin($.utils.config('LMAppUrlM') +"/mobile/evt/eventMain.do?tabId=1") ) { return };
    }

    function goMcouponLogin(){
        if ( !global.isLogin($.utils.config('LMAppUrlM') +"/mobile/evt/eventMain.do?tabId=2") ) { return };
    }

    function goMcouponJoin(){
        location.href= "https://member.lpoint.com/door/user/login_common.jsp?sid=MMARTSHOP&mallcode=null&SITELOC=LG010";
    }

    function appDown () {
        var userAgent = navigator.userAgent.toUpperCase();

        if(userAgent.toUpperCase().indexOf("LOTTEMART-APP-SHOPPING-ANDROID") !== -1
            || userAgent.toUpperCase().indexOf("TOYSRUS.ANDROID.SHOPPING") !== -1 ){
            location.replace('lmscp://m.coupon.lottemart');
        } else {
            if (userAgent.toUpperCase().indexOf("IPHONE") !== -1
                || userAgent.toUpperCase().indexOf("IPAD") !== -1) {
                location.replace('http://itunes.apple.com/app/id987435592');
            } else {
                location.replace('market://details?id=com.lottemart.lmscp');
            }
        }

        return false;
    }

    function loadMcoupon(){
        if(!installMcouponAppChk()){
            alert("M쿠폰 앱이 아직 \n설치완료 되지 않았습니다. \n다시 확인해 주세요. ");
            return;
        }else{
            getList(2);
        }
    }

    function installMcouponAppChk(){
        return scpIsMembers === 'Y';
    }

    function openlayerForMcoupon(e){ // tooltip 변경
        e.preventDefault();
        var obj = $(this),
            $parent = obj.closest('.main-slide'),
            objTgt = this.hash ? this.hash : obj.attr('data-layer'),
            objPosTop = (obj.offset().top - $parent.offset().top) + obj.outerHeight() + 10;
        if (objTgt) {
            objTgt = objTgt.replace('#', '');
            areaTooltipLayerPopup(obj, objTgt, objPosTop, $container);
        }
        return false;
    }

    //M쿠폰 카테고리 이동
    function goCouponCategory(){
        var categoryId = "";
        var searchType = $(this).data('searchtype');
        var divBenefit = $( '#divBenefit' );
        $mcouponList.find("div.mcoupon-search > input[name=tmpSearchName]", $mcouponList).val('');
        var benefitChkLength = divBenefit.children().length-1;

        var chkState = $container.find("input:radio[id='mcouponAllCheck']").is(":checked") ;
        if(!chkState){
            divBenefit.children().each(function(index){
                if($(this).children('input:checkbox').is(":checked") == true){
                    categoryId = categoryId + $(this).children('input:checkbox').val() + ',';
                }
            });
            if(categoryId == '' || categoryId == undefined){
                alert(errorNotSelected);	 	//선택된 항목이 없습니다. (상품을 1개 이상 선택해 주세요.)
                return;
            }

            $("div.mcoupon-search > input[name=tmpSearchName]", $mcouponList).val('');
            $('form[name=mfrm] > input[name=categoryId]', $mcouponList).val(categoryId);
        }

        var params = {};
        params = {
            'cPage' : 1,
            'categoryId' : categoryId,
            'searchType' : searchType
        };
        $.get($.utils.config('LMAppUrlM') +"/mobile/evt/ajax/mobileEventMcoupon.do", params, renderCouponList);
    }

    //M쿠폰 검색
    function goMCouponSearch(){
        var curPage = 1;
        var searchName = $mcouponList.find("input[name=tmpSearchName]").val();
        if(searchName.trim() == ''){
            // 			return;
        }
        var params = {};
        params = {
            'cPage' : curPage,
            'searchName' : searchName
        };
        $.get($.utils.config('LMAppUrlM') +"/mobile/evt/ajax/mobileEventMcoupon.do", params, renderCouponList);
    }

    function getMCouponList(){
        var curPage = 1;
        var searchName = $mcouponList.find("input[name=tmpSearchName]").val();
        if(searchName.trim() == ''){
            // 			return;
        }
        var params = {};
        params = {
            'cPage' : curPage,
            'searchName' : searchName
        };
        $.get($.utils.config('LMAppUrlM') +"/mobile/evt/ajax/mobileEventMcoupon.do", params, renderCouponList);
    }

    function renderCouponList(response){
        $container.find('.mcategoryLayer1').remove();
        $container.find('.mask').removeClass();
        $mcouponList.find("#mcouponList").html(response);
    }

    function isEmptyContent( $el ) {
        return $el.length === 0;
    }


    return {
        init: function (obj) {
            $footer.addClass(loadingFooterClass);
            if(url.indexOf('tabId') > -1){
                var cnt = url.indexOf('tabId');
                var parms = url.substring(cnt);
                var parm = parms.substring(0,8);

                url.replace(parm, '');

                history.pushState(null, null, url.replace(parm,''));
            }

            _init(obj);
        }
    }
}));