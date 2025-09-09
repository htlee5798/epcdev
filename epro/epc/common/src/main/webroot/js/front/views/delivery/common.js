$.fn.nvl = function(rtnNull){
	if(!this) return rtnNull;
	else return this;
};

String.prototype.nvl = function (rtnNull) {
	if(!this) return rtnNull;
	else return this;
};

var nvl = function(obj, rtnNull){
	if(!obj) return rtnNull;
	else return obj;
}

var refDeliType = function(obj){
	if(!!obj){
		if(obj.length > 2) obj = obj.substring(0,2);	
		return obj;
	}
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}	


//체크박스 컨트롤
$.fn.check = function(flag){
	if($.type(flag) === 'boolean'){
		$(this).prop('checked', flag);
		if(($(this).attr('type') == 'checkbox')){ 
			if(flag) $(this).parent().addClass('active');
			else $(this).parent().removeClass('active');
		}
		if($(this).attr('type') == 'radio'){
			if(flag){
				$(this).parent().parent().parent().find('.active').removeClass('active');
				$(this).parent().addClass('active');
			}else{
				$(this).parent().removeClass('active');
			}
		}
	}else if(flag=='reset'){
		$(this).parent().removeClass('active');
		$(this).prop('checked', false);
	}
	
};

//콤보박스 컨트롤
$.fn.select = function(){
	if($(this).prop("tagName") == 'OPTION'){
		$(this).prop('selected', true);
		$(this).parent('select').trigger('change');
	}
};

//레이어팝업 닫기
function closeLayer(obj){
	$(obj).parents('section.layer-bottom').remove();
	$('#layer_pop1').removeClass('active');
	$('#layer_pop2').removeClass('active');
	$('#layer_pop3').removeClass('active');
	$('#layer_pop4').removeClass('active');
	
}

// 이용자가이드
//$("#userguide_link").click(function(e) {
//	window.open("/delivery/popup/user_guide.do", "DeliveryUserGuidePopup", "width=718px, height=720px");
//});

// 이용자가이드
$("#userguide_link").click(function(e) {
	eventPropagationWrapper(e, function() {
		window.open("/delivery/popup/user_guide.do", "DeliveryUserGuidePopup", "width=718px, height=770px");
	});
});

// FAQ
//$("#faq_link").click(function(e) {
//	eventPropagationWrapper(e, function() {
//		window.open("/happycenter/faqMain.do", "DeliveryFAQPopup", "width=920px, height=720px");
//	});
//});

//상품상세 페이지 이동 함수
function goProductDetailView(cateId,prodCd,smartOfferClickUrl,popupYn){
//	var popYn=popupYn;
	var koost_Yn="N";
	var socialSeqVal="";	
	var url = _LMAppUrl;
//	if(popupYn==""){
//		popYn="N";
//	}
//	
//	
//	//온라인몰 = 00001
//	
//		url = _LMAppUrl;	
//		 
//	if(prodCd=="" || prodCd==null){
//		//alert("상품코드가 존재하지 않습니다.");
//		alert( msg_product_error_noPro);
//		if(popYn=="Y"){
//			//self.close();
//		}
//		return;
//	}
//	
//		socialSeqVal="";
//	
	var dpCode = "";
	if(typeof(smartOfferClickUrl) != "undefined" && smartOfferClickUrl != "") {
		// 스마트오퍼를 통한 상품을 클릭 시 logging 을 위해 다음을 호출하고, 결과는 받을 필요 없다. itemSetId, scnId
		try {
			var codeList = ["dpId", "itemSetId", "scnId"];
			var clickParams = smartOfferClickUrl.substring(smartOfferClickUrl.indexOf("?") + 1).split("&");
			var curParams;
			for (var i = 0 ; i < codeList.length ; i++ ) {
				curParams = $.grep(clickParams, function(obj) {
					return obj.indexOf(codeList[i] + "=") >= 0;
				});
				if (curParams != null && curParams.length > 0) {
					if (codeList[i] === "dpId") {
						dpCode += ("&" + "dp=" + curParams[0].split("=")[1]);
					}
					else {
						dpCode += ("&" + curParams[0]);
					}
				}
			}
			$.get(smartOfferClickUrl);
		}
		catch (e) {}
	}
//	
//
//	if(popYn=="Y"){
//		
//		opener.document.location.href = url+"/product/ProductDetail.do?CategoryID="+cateId+"&ProductCD="+prodCd+"&socialSeq="+socialSeqVal+"&koostYn="+koost_Yn + (dpCode != ""?"&dp=" + dpCode:"");
//		//self.close();
//	}else{
		document.location.href = url+"/product/ProductDetail.do?CategoryID="+cateId+"&ProductCD="+prodCd+"&socialSeq="+socialSeqVal+"&koostYn="+koost_Yn + (dpCode != "" ? dpCode:"");
//	}
  }


/**
 * 약관 열기
 */
function openAgreement(id) {
	window.open("/peribasket/popup/agree.do?id=" + id, "PeriAgreement"+id, "width=1010px, height=965px, scrollbars=yes");
}

var setDcLayer = function(obj) {
	var params = jParam(obj);
	$.getJSON("/peribasket/api/getProdPromoList.do", params)
		.done(function(data) {
			$.each(data.promoList, function(){
				var li = "<li>"+this.PROMO_NM+"<em class='txt-red flt-right'>"+this.DC_AMT+this.DC_UNIT+"</em></li>";
				$('#dcList').append(li);
			});
		});
};

var setOptionLayer = function(obj) {
	var params = jParam(obj);
	var optionYn = $(obj).attr('OPTION_YN') == 'Y';
	$.getJSON("/delivery/api/optionInfo.do", params)
		.done(function(data) {
			var select = $('#option_select');
			if (!!data.list && optionYn && !!params.optionNm) {
				$.each(data.list, function(){
					var option;
					if(this.OPTION_CD == params.itemCd){
						select.siblings('label').html(this.OPTION_NM);
						option = $('<option>', {value:this.OPTION_CD, text:this.OPTION_NM, selected:'selected'});
					}else{
						option = $('<option>', {value:this.OPTION_CD, text:this.OPTION_NM});
					}
					select.append(option);
				});
				select.parents('dl').find('.option_select').show();
			}else{
				select.parents('dl').find('.option_select').hide();
			}
		});
	
	$('#optItemCount').val($(obj).siblings('param.ORD_QTY').val());
	$('#optItemCount').attr('MAX_ORD_PSBT_QTY', $(obj).siblings('param.ORD_QTY').attr('MAX_ORD_PSBT_QTY'));
	$('#optItemCount').attr('MIN_ORD_PSBT_QTY', $(obj).siblings('param.ORD_QTY').attr('MIN_ORD_PSBT_QTY'));
};

function getImgPath(imgCode,imgSize){
	var dirName=imgCode.substring(0,5).trim();
	//returnURL = _LMCdnDynamicUrl + "/" + dirName + "/" + imgCode + "_1_" + imgSize + ".jpg";
	returnURL = _LMCdnStaticUrl + "/images/prodimg/" + dirName + "/" + imgCode + "_1_" + imgSize + ".jpg";
	return returnURL;
}

function managePeri(tab){
	var f = $('#periManager').get(0);
	f.action = '/mymart/managePeri'+tab+'.do';
	f.submit();
}

function addWishProd(obj, event) {
	if("Y" == previewYn) {
        alert(fnJsMsg(view_messages.fail.doesNotFnc));        //미리보기에서는 제한되는 기능입니다.
        return;
    } else {
        event.preventDefault();
        
        /* $(this).toggleClass('cuurent'); */
        var prodInfos = $(obj).attr('id').split('_');      // "wish_"+prodCd+"_"+idx+"_"+categoryId or "wish_"+prodCd+"_"+categoryId
        if(prodInfos != null && prodInfos.length > 3) {
            addProdWishList(obj, event, prodInfos[1], prodInfos[3], 'N', prodInfos[2], document.location.href);
        } else if(prodInfos != null && prodInfos.length > 2) {
            addProdWishList(obj, event, prodInfos[1], prodInfos[2], 'N', '', document.location.href);
        }
    }
}

//상품 개별 찜/찜취소하기
function addProdWishList(obj, event, p_prodCd, p_categoryId, p_forgnDelyplYn, p_area_idx, p_url) {
	if("Y" == previewYn) {
		alert(fnJsMsg(view_messages.fail.doesNotFnc));		//미리보기에서는 제한되는 기능입니다.
		return;
	} else {
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
					wishResult(obj, event, p_prodCd, p_categoryId, p_area_idx, classNm, data);
	            },
	            function(xhr, status, error) {
	            	if(xhr.status == "200" && xhr.statusText == "OK") {
						data = {message : "찜하기가 취소되었습니다"};
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

/**
 * JSON 데이터 루프 출력
 * @param target 대상 태그
 * @param template 템플릿 스크립트
 * @param data JSON 데이터
 * @param reset [optional] 대상 내용 초기화 유무
 */
var jRender = {
	orgTargetHouse : undefined,
	targetHouse : undefined,
	targetObject : undefined,
	data : undefined,
	reset : undefined,
	condition : undefined,
	
	getImgSrc : function(imgPath){
		return imgPath;
	},
	filter : function(record){
		var result = true;
		if(!!jRender.condition){
			if(!!record){
				$.each(jRender.condition, function(key, val){
					if(record[key] != val) result = false;
				});
			}else result = false;
		}
		return result;
	},
	jFormat : function(fmt, val){
		var rtnValue;
		if(fmt.indexOf('date') > -1) {
			if(val.length < 8) rtnValue = val;
			else {
				rtnValue = fmt.split('=')[1];
				rtnValue.replace('yyyy', val.substring(0,4));
				rtnValue.replace('mm', val.substring(4,6));
				rtnValue.replace('dd', val.substring(6,8));
			}
		}else if(fmt.indexOf('time') > -1) {
			if(val.length < 6) rtnValue = val;
			else {
				rtnValue = fmt.split('=')[1];
				rtnValue.replace('hh', val.substring(0,2));
				rtnValue.replace('mi', val.substring(2,4));
				rtnValue.replace('ss', val.substring(4,6));
			}
		}else if(fmt.indexOf('currency') > -1) {
			rtnValue = val.toString().replace(/[^0-9]/g,'').replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}
		if(fmt.indexOf('post') > -1) {
			rtnValue += fmt.split('=')[1];
		}
		if(fmt.indexOf('pre') > -1) {
			rtnValue += fmt.split('=')[1];
		}	
		return rtnValue;
	},
	setValue : function(tar, val){
		var tag = $(tar).prop("tagName");
		if(tag == 'INPUT' || tag == 'PARAM'){
			var type = $(tar).attr("type");
			if(!!$(tar).val() && !!type && (type.toLowerCase() == 'checkbox' || type.toLowerCase() == 'radio')){
				if($(tar).val() == val) $(tar).prop('checked', true);
			}else{
				$(tar).val(val);
			}
		}else if(tag == 'SELECT'){
			$('option[value='+val+']', tar).prop('selected', true);
		}else if(tag == 'TEXTAREA'){
			$(tar).val(val);
		}else if(tag == 'IMG'){
			$(tar).attr('src', jRender.getImgSrc(val));
		}else{
			if(!!$(tar).attr('fmt')){
				val = jRender.jFormat($(tar).attr('fmt'), val);
			}
			$(tar).text(val);
		}
		if(!!$(tar).attr('func')){
			var funcName = $(tar).attr('func');
			jRender[funcName](tar, val);
		}
	},
	render : function(target, singleData){
		$.each(singleData, function(key, value){
			$.each($(target).find('*.'+key), function(){
				jRender.setValue(this, value);
			});
			$.each($(target).find('*['+key+']'), function(){
				$(this).attr(key, value);
			});
			$.each($(target).find('*[css'+key+']'), function(){
				$(this).addClass(value);
			});
		});
	},
	init : function(){
		jRender.targetHouse = $('#'+arguments[0]);
		jRender.targetObject = $('#'+arguments[1]);
		jRender.data = arguments[2];
		jRender.reset = arguments[3];
		jRender.condition = arguments[4];
		jRender.orgTargetHouse = jRender.targetHouse;
		
		return jRender;
	},
	tplModify : function(tpl, obj){
	},
	print : function(){
		if(jRender.reset) jRender.targetHouse.empty();
		if(!jRender.data || jRender.data.length == 0) return;
	
		if(!$.isArray(jRender.data)) jRender.data = $.makeArray(jRender.data);
		
		$.each(jRender.data, function(idx){
			if(jRender.filter(this)){
				var template = $(jRender.targetObject.html()).clone();
				jRender.render(template, this);
				jRender.tplModify(template, this, idx);
				jRender.targetHouse.append(template);
			}
		});
	},
	sum : function(map){
		var rtnMap = {};
		if(!!jRender.data && jRender.data.length > 0){
			$.each(map, function(){
				var key = this;
				var sumVal = 0;
				$.each(jRender.data, function(){
					if(jRender.filter(this)){
						if(!!this[key]) sumVal += parseInt(this[key].toString().replace(/[^0-9]/g,''));
					};
				});
				rtnMap[key] = sumVal;
			});
		}
		return rtnMap;
	},
	size : function(){
		var count = 0;
		if(!!jRender.data && jRender.data.length > 0){
			$.each(jRender.data, function(){
				if(jRender.filter(this)){
					count++;
				};
			});
		}
		return count;
	},
	list : function(){
		var list = [];
		if(!!jRender.data && jRender.data.length > 0){
			$.each(jRender.data, function(){
				if(jRender.filter(this)){
					list.push(this);
				};
			});
		}
		return list;
	}
}

var strCount = function(s,b,i,c){for(b=i=0;c=s.charCodeAt(i++);b+=c>>11?3:c>>7?2:1);return b}

var jParam = function(obj){
	var rtnMap = {};
	var paramList = $(obj).siblings('param');
	$.each(paramList, function(){
		var ref = $(this).attr('ref');
		if(!!ref){
			rtnMap[this.name] = $(ref).val();
		}else{
			rtnMap[this.name] = this.value;
		}
	});
	return rtnMap;
}

/**
 * jComboBox
 * valKey 옵션값으로 쓰일 data의 키
 * txtKey 옵션텍스트로 쓰일 data의 키
 * selVal 기본 선택 될 옵션값
 * allVal 기본옵션으로 쓰일 값
 * allTxt 기본옵션으로 쓰일 텍스트
 */
var jComboBox = function(targetId, data, comboSet, reset){
	var valKey = comboSet.valKey;
	var txtKey = comboSet.txtKey;
	var selVal = comboSet.selVal;
	var allVal = comboSet.allVal;
	var allTxt = comboSet.allTxt;
	var filterSet = comboSet.filterSet;
	var comboBox = $('#'+targetId);
	
	if(reset) comboBox.empty();
	if(!!allTxt){
		allVal = !allVal?'':allVal;
		comboBox.append($('<option>', {value:allVal, text:allTxt}));
	}
	$.each($.makeArray(data), function(){
		if(!!filterSet){
			var pass = true;
			var item = this;
			$.each(filterSet, function(key, val){
				if($.inArray(item[key], val) < 0) pass = false;
			});
			if(pass){
				if(item[valKey] == selVal)
					comboBox.append($('<option>', {value:item[valKey], text:item[txtKey], selected:'selected'}));
				else
					comboBox.append($('<option>', {value:item[valKey], text:item[txtKey]}));
			}
		}
	});
}

var jResult = function(data){
	var result = eval(data.result);
	if(!result){
		alert(data.error);
	}
	return result;
}

//셀병합
var jCellMerge = function(table, attribute){
	var table = $('#'+table);
	var cells = $('td['+attribute+']', table);
	for(c=0;c<cells.length;c++){
		var cell = cells.get(c);
		if(!$(cell).prop('rmv')){
			var cellAttr = $(cell).attr(attribute);
			var rowspan = 1;
			for(n=c+1;n<cells.length;n++){
				var nextCell = cells.get(n);
				var nextAttr = $(nextCell).attr(attribute);
				if(cellAttr == nextAttr) {
					rowspan++;
					$(nextCell).prop('rmv', true);
				}
			}
			if(rowspan > 1){
				$(cell).attr('rowspan', rowspan);
			}
		}
	}
	cells.each(function(){
		if($(this).prop('rmv')) $(this).remove();
	});
}

$(document).ready(function() {
	//옵션 버튼 클릭
	$('.dynamic').on('click', 'button.optionLayer', function(){
		if($(this).attr('OPTION_YN') == 'Y'){
			layerpopTriggerBtn( this, event );
			setOptionLayer(this);
		}else
			confirmOption(this);
	});
	
//	$('.dynamic').on('click', 'button.optionConfirm', function(){
//		if($('#optItemCount').val() > -1)
//			$(this).parents('.dynamic').find('param.ORD_QTY').val($('#optItemCount').val());
//		if(!!$('#option_select').val())
//			$(this).parents('.dynamic').find('param.ITEM_CD').val($('#option_select').val());
//		confirmOption($(this).parents('.itemCell').find('button.changeItem'));
//		closeLayer(this);
//	});
	
//	//수량UP
//	$('.dynamic').on("click", 'button.sp-plus', function(){
//		var basketQty = $('#optItemCount');
//		var ordQty = parseInt(basketQty.val());
//		var limit = parseInt(basketQty.attr('MAX_ORD_PSBT_QTY'));
//		
//		if(ordQty < limit) ordQty++;
//		basketQty.val(ordQty);
//	});
//	
//	//수량DOWN
//	$('.dynamic').on("click", 'button.sp-minus', function(){
//		var basketQty = $('#optItemCount');
//		var ordQty = parseInt(basketQty.val());
//		var limit = parseInt(basketQty.attr('MIN_ORD_PSBT_QTY'));
//		
//		if(ordQty > limit) ordQty--;
//		basketQty.val(ordQty);
//	});
	
	//혜택버튼
	$('.dynamic').on('click', '.dcLayer', function(){
		if(!$('section.layer-bottom').length)
			layerpopTriggerBtn( this, event );
		
		setDcLayer(this);
	});
});