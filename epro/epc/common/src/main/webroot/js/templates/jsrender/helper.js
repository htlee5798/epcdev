$.views.helpers({
	numberFormat : function( str ) {
		return $.utils.comma( str );
	},
	substr : function(str, index, length) {
		if(str == null || str == '') {
			return '';
		}
		return str.substr(index, length);
	},
	className : function( str ) {
		return str === undefined ? '' : str.replace( '.', '' );
	},
	imageCdnPath : function() {
		return '//simage.lottemart.com';
	},
	sImageCdnPath : function() {
		return '//simage.lottemart.com';
	},
	lmCdnV3RootPath : function() {
		return $.utils.config('LMCdnV3RootUrl');
	},
    setStringSplit : function(str, delimeter) {
		"use strict";
		return str.split(delimeter);
	},
	isYoutubleUrl : function(url) {
		return url && url.indexOf('youtube') > -1;
	},
	getLMAppUrlM : function() {
		return $.utils.config( 'LMAppUrlM' );
	},
	getImageContentBase : function () {
		return 	$.utils.config('imageContentBase');
    },
	getLmCdnStaticImagePath : function( imagePath ) {
		return $.utils.config( 'LMCdnStaticUrl' ) + imagePath;
	},
	getLmCdnStaticItemImagePath : function( prodCd ) {
		return $.utils.config('LMCdnStaticRootUrl') + "/images/prodimg/" + prodCd.substring(0, 5).trim() + "/" + prodCd + "_1_80.jpg";
	},
	getLmCdnStaticItemImageHotPath : function( prodCd ) {
		return $.utils.config('LMCdnStaticRootUrl') + "/images/prodimg/" + prodCd.substring(0, 5).trim() + "/" + prodCd + "_1_500.jpg";
	},
	getSimageStaticProductImagePath : function(prodCd, size) {
        return "//simage.lottemart.com/lim/static_root/images/prodimg/" + prodCd.substring(0, 5).trim() + "/" + prodCd + "_1_"+ size +".jpg";
	},
	getSoldOutImagePath : function() {
		return $.utils.config('LMCdnV3RootUrl') + '/images/layout/img_soldout_80x80.png';
	},
	getAdultImagePath : function() {
		return $.utils.config('LMCdnV3RootUrl') + '/images/layout/img_age19_80x80.jpg';
	},
	getRNBHistoryLink : function( typeCode, categoryId, contentsNo, prodCd, mallDivnCd ) {
		var link = '';

		switch( typeCode ) {
			case '01' :
				link = 'javascript:goProductDetail(' + "'" + categoryId + "'," + "'" + prodCd + "'," + "'N','',''," + "'" + mallDivnCd + "'" + ');';
				break;
			case '02' :
				link = '/category/categoryList.do?CategoryID=' +  categoryId;
				break;
			case '03' :
				link = '/plan/planDetail.do?CategoryID=' + categoryId + '&MkdpSeq=' + contentsNo;
				break;
			case '04' :
				link = '/event/detail.do?categoryId=' + categoryId;
				break;
			case '05' :
				link = '/trend/trendDetail.do?categoryId=' + categoryId + '&trendNo=' + contentsNo;
				break;
 		}

		return link;
	},
	getRNBHistroyClass: function( typeCode ) {
		var cls = '';

		switch( typeCode ) {
			case '02' :
				cls = 'hidden';
				break;
			case '03' :
				cls = 'class-tag  tag-exhibition';
				break;
			case '04' :
				cls = "class-tag tag-event";
				break;
			case '05' :
				cls = "class-tag tag-trend";
				break;
		}

		return cls;
	},
	getRNBHistroyTitle : function( typeCode ) {
		var title = '';

		switch( typeCode ) {
			case '02' :
				title = "카테고리";
				break;
			case '03' :
				title = "기획전";
				break;
			case '04' :
				title = "이벤트";
				break;
			case '05' :
				title = "트렌드";
				break;
		}

		return title;
	},
	getRNBHistoryErrorImage : function( typeCode ) {
		var imageName = '';

		switch( typeCode ) {
			case '03' :
				imageName = "'noimg_plan_list_'";
				break;
			case '04' :
				imageName = "'noimg_event_'";
				break;
			case '05' :
				imageName = "'noimg_trend_'";
				break;
 		}

		return imageName;
	},
	getNoImagePath : function(width, height) {
		var _width = width || 120
		  , _height = height || 120;

		return $.utils.config('LMCdnV3RootUrl') + '/images/layout/noimg_prod_' + _width + 'x' + _height + '.jpg';
	},
	getRNBHistoryImage : function( typeCode, imgSrc ) {
		var src = '';

		switch( typeCode ) {
			case '02' :
				src = $.utils.config('LMCdnV3RootUrl') + '/images/temp/category_80x80.jpg';
				break;
			default :
				src = $.views.helpers.getLmCdnStaticImagePath( imgSrc );
				break;
		}

		return src;
	},
    getManuFacturingProduct : function (onlineProdTypeCd) {
		"use strict";

		return onlineProdTypeCd === $.utils.config('const_ONLINE_PROD_TYPE_CD_MAKE') ||
		onlineProdTypeCd === $.utils.config('const_ONLINE_PROD_TYPE_CD_INST') ? true : false;
	},
	getMobileBage : function (promotionProductIcon, maxLength) {
		"use strict";
		if(!promotionProductIcon) {return;}

		var icons = promotionProductIcon.split('|'),
			badgeHtml = '';

		for(var i = 0; i < maxLength; i++) {
			var infos = icons[i].split(':'),
				orderNo = infos[0],
				iconType = infos[1],
				iconNm = infos[2],
				iconDesc = infos[3],
				html = '';

			if(infos[4] !== null && infos[4].indexOf('-') !== -1){
				iconDesc = 'type' + infos[4].trim();
			}

			switch (iconType) {
				case '04':
					html = iconDesc;
					break;
				case 'won':
					html = '<em class="won">' + $.utils.comma(iconDesc) + '</em> 할인';
					break;
				case 'discount':
					html = '<em class="number">' + iconDesc +'</em> 할인';
					break;
				case 'bundle':
					html = '<em class="plus">' + iconDesc +'</em>';
					break;
				case 'type6-1':
					html = '<em class="number">' + iconDesc + '</em> 다둥이';
					break;
				case 'type6-2':
					html = '<em class="won">' + $.utils.comma(iconDesc) + '</em> 다둥이';
					break;
				case 'type4-1':
					html = '<em class="number">' + iconDesc + '</em> L.POINT';
					break;
				case 'type4-2':
					html = '<em class="won">' + $.utils.comma(iconDesc) + '</em> L.POINT';
					break;
				default:
					if(iconType !== undefined && iconType !== 'type12') {
						html = iconNm;
					}
					break;
			}

            badgeHtml = badgeHtml + '<div class="type02">' + html + '</div>';
		}

		return badgeHtml;
	},
	isAdult : function( adultYN ) {
		return $.utils.config('LMAdultPccvYn') !== 'Y' && adultYN === 'Y';
	},
	isSoldOut : function(soutYNReal) {
		return soutYNReal === 'Y' ? true : false;
	},
	isLogin : function() {
		return _Login_yn === 'Y';
	},
	isAppendPlanDetailOption : function( categoryId ) {
		return $.utils.config( 'lotteEmpYn' ) === 'Y' || categoryId !== '';
	},
	isSoldProductItem : function( productCode , stockQuantity ) {
		return /^D/.test( productCode ) && stockQuantity === 0;
	},
	isDealProductItem : function(onlineProdTypeCd){
		"use strict";

		return onlineProdTypeCd === $.utils.config('const_ONLINE_PROD_TYPE_CD_DEAL') ? onlineProdTypeCd : "";
	},
	isDealTypeProductItem : function (onlineProdTypeCd, deliType) {
		"use strict";

		var isDealProduct = onlineProdTypeCd === $.utils.config('const_ONLINE_PROD_TYPE_CD_DEAL') ? onlineProdTypeCd : "",
			reserve = $.utils.config('const_DELI_TYPE_RESERVE'),
			vendorDeli = $.utils.config('const_DELI_TYPE_VENDOR_DELI'),
			startsWith = function (sourse, destination) {
				return sourse.substr(0, destination.length) === destination;
			};

		return isDealProduct != 'N' &&
			startsWith(deliType, reserve) ||
            startsWith(deliType, vendorDeli) ||
			onlineProdTypeCd === $.utils.config('const_ONLINE_PROD_TYPE_CD_ETC');
	},
	onToggleBtnMovePlanDetail : function( isShow ) {
		$( '#btnMove' ).toggle( isShow );
	},
	lPad : function(str, totalLength, altStr){
		if(typeof str !== 'string'){
			str = str.toString();
		}

		return lpad(str, totalLength, altStr);
	},
    isCheckedMobileSearchFilterCheckbox : function(type, value) {
        var params = location.search.replace('?', '').split('&');
        var returnValue = false;

        for(var i = 0, len = params.length; i < len; i++) {
            var param = params[i].split('=');

            if(param[0] === type && param[1].indexOf(value) != -1) {
                returnValue = true;
			}
        }
        return returnValue;
	},
	isDeal : function(onlineProdCd) {
		return onlineProdCd === '05';
	},
    isShowDelivery : function(onlineProdCd, deliType) {
		return deliType.indexOf('01') === 0
			|| deliType.indexOf('02') === 0
			|| deliType.indexOf('04') === 0
			|| (onlineProdCd === '08')
	},
	formatTimeStamp : function(timeStamp, format) {
		var date = new Date(timeStamp);

		return date.format(format);
	},
	deliveryIcons : function (onlineProdCd, deliType, store) {
        var SMS = {
            name : '문자발송',
            css : 'icon-band-delivery8'
        };
        var HOLIDAY_STORE = {
            name : '명절매장배송',
            css : 'icon-band-holi-1'
        };
        var HOLIDAY_STORE_PARCEL = {
            name : '명절택배배송',
            css : 'icon-band-holi-4'
        };
        var HOLIDAY_VENDOR_PARCEL = {
            name : '명절업체배송',
            css : 'icon-band-holi-5'
        };
        var HOLIDAY_COLD_STORAGE = {
            name : '명절냉장배송',
            css : 'icon-band-holi-3'
        };
        var HOLIDAY_REFRIGERATION = {
            name : '명절냉동배송',
            css : 'icon-band-holi-2'
        };
        var STORE = {
            name : '매장배송' ,
            css : 'icon-band-delivery1'
        };
        var STORE_PARCEL = {
            name : '매장택배',
            css : 'icon-band-delivery2'
        };
        var FRESH_STORE = {
                name : '신선센터택배',
                css : 'icon-band-delivery979'
            };
        var STORE_PICK_UP = {
            name : '스마트픽',
            css : 'icon-band-delivery3'
        };
        var OVERSEA = {
            name : '해외배송',
            css : 'icon-band-delivery15'
        };
        var VENDOR_PARCEL = {
            name : '업체택배',
            css : 'icon-band-delivery4'
        };

        var DELIVERY_TYPES = {
        	'01' : STORE,
			'02' : VENDOR_PARCEL,
			'03' : SMS,
			'07' : STORE_PARCEL,
			'08' : OVERSEA
		};

        var HOLIDAY_DELIVERY_TYPE_CODES = {
        	'00' : HOLIDAY_STORE,
			'01' : HOLIDAY_STORE_PARCEL,
            '05' : HOLIDAY_STORE_PARCEL,
			'03' : HOLIDAY_VENDOR_PARCEL,
			'02' : HOLIDAY_COLD_STORAGE,
			'04' : HOLIDAY_REFRIGERATION
		};

		if(onlineProdCd === '05') {
            return DELIVERY_TYPES[deliType];
		} else if(onlineProdCd === '08') {
			return SMS;
		} else if(deliType.indexOf('06') === 0) {
            return HOLIDAY_DELIVERY_TYPE_CODES[deliType.replace('06[!@!]', '')];
		} else if(onlineProdCd === '09') {
            return OVERSEA;
		} else if(store && store == '979'){
			return FRESH_STORE;
		} else if(deliType.indexOf('01') === 0) {
			return STORE;
		} else if(deliType === '02') {
			return STORE_PARCEL;
		} else if(deliType === '03' || deliType === '07') {
			return STORE_PICK_UP;
		} else if(deliType === '04') {
			return VENDOR_PARCEL;
		} else {
			return {
				css : '',
				name : ''
			}
		}
	},
    isGiftMall : function (onlineProdCd, deliType) {
		return deliType.indexOf('06') !== -1;
	},
	promotions : function (promoProdIcon) {
		if(!promoProdIcon) {
			return '';
		}

		var promotions = promoProdIcon.split('|');
		var returnValue = [];

		for(var i = 0, len = promotions.length; i < len; i++) {
			if(promotions[i] !== '') {
				var icons = promotions[i].split(':');

                returnValue.push({
                    orderNo: icons[0],
                    iconType: icons[4].indexOf('-') !== -1 ? 'type' + icons[4] : icons[1],
                    iconNm: icons[2],
                    iconDesc: icons[3]
                });
			}
		}

		return returnValue;
	},
	isSoldOut : function(soutYn, onlineProdTypeCd) {
        return soutYn === 'Y' && !(onlineProdTypeCd === $.utils.config('onlineProdTypeCdDeal'));
	},
    getItemImagePath : function(mdSrcmkCd, sizeCode, seq, fileType) {
        if(!mdSrcmkCd) {
            return '';
        }

        switch (sizeCode) {
            case '400' :
                sizeCode = '500';
                break;
            case '220' :
                sizeCode = '250';
                break;
            case '154' :
                sizeCode = '160';
                break;
            case '100' :
                sizeCode = '100';
                break;
            case '90' :
                sizeCode = '100';
                break;
        }

        var dirName = mdSrcmkCd.substring(0, 5).trim();

        return $.utils.config('LMCdnStaticRootUrl') + '/images/prodimg/' + dirName + '/' + mdSrcmkCd + '_' + seq + '_' + sizeCode + (fileType || '.jpg');
	},
	getItemWideImagePath : function(mdSrcmkCd) {
        if (!mdSrcmkCd) return '';

        var dirName = mdSrcmkCd.substring(0, 5).trim();
        var dirSubName = mdSrcmkCd.substring(5, 9).trim();
        
        return $.utils.config('LMCdnStaticRootUrl') + '/images/prodimg/wide/' + dirName + '/' + dirSubName + '/' + mdSrcmkCd + '_00_720_405.jpg';
	},
	replaceCounselContentCharacter : function(str) {
		if(str) {
			str = str.replace(/\[\]/g,'');
			str = str.replace(/\=\=\[/g,'<!--');
			str = str.replace(/\]\=\=/g,'-->');
		}

		return str;
	},
	lineBreakCheck: function(str, line, breaker) {
		var breaker = breaker || '\n',
			str = str.split(breaker),
			maxLine = (str.length > line) ? line : str.length,
			reStr = '';
		for (;maxLine > 0;maxLine--) {
			reStr = str[maxLine-1] + reStr;
			if (maxLine > 1) reStr = '<br>' + reStr;
		}

		return reStr;
	},
	saleRatio: function ( num1, num2 ) {
		return 100 - Math.ceil(num1 / num2 * 100);
	}
});