//"use strict";

//다중배송 관련
var MultiDelivery = {
	pristine: true,
	
	deliveryList: [],
	
	prodList: [],
	
	preOrderItemList: [],
	
	deliveryMessageList: [],
	
	init: function() {
		MultiDelivery.prodList = [];
		MultiDelivery.preOrderItemList = [];
		
		var asProd;
		$(".prod-list-type2 tr[data-PROD_CD]").each(function(i, o) {
			var prod = {};
			for (var i = 0 ;i < $(o).get(0).attributes.length ; i++ ) {
				var attrName = $(o).get(0).attributes[i].name;
				if ((/^data\-.+/g).test(attrName)) {
					prod[attrName.replace("data-", "").toUpperCase()] = $(o).attr(attrName);
				}
			}
			prod["PROD_NM"] = cutByLen(prod["PROD_NM"], 74);
			prod["NFOML_VARIATION"] = cutByLen(prod["NFOML_VARIATION"], 74);
			
			MultiDelivery.preOrderItemList.push(prod);
			asProd = $.grep(MultiDelivery.prodList, function(pObj) {
				return pObj.PROD_CD == prod.PROD_CD && pObj.ITEM_CD == prod.ITEM_CD;
			});
			if (asProd != null && asProd.length > 0) {
				asProd[0].BSKET_QTY = Number(asProd[0].BSKET_QTY) + Number(prod.BSKET_QTY);
				asProd[0].EXTRA_QTY = Number(asProd[0].EXTRA_QTY) + Number(prod.EXTRA_QTY);
				asProd[0].TOT_BSKET_QTY = Number(asProd[0].TOT_BSKET_QTY) + Number(prod.TOT_BSKET_QTY);
				asProd[0].SET_QTY = Number(asProd[0].SET_QTY) + Number(prod.SET_QTY);
			}
			else {
				MultiDelivery.prodList.push(prod);
			}
		});
		MultiDelivery.renderDeliveryLayer();
		MultiDelivery.setBasketQty();
		MultiDelivery.pristine = true;
	},
	
	// 배송지 생성
	addFirst: function() {
		MultiDelivery.init();
		if (preOrderDeliveryList != null && preOrderDeliveryList.length > 0) {
			$.each(preOrderDeliveryList, function(idx, delivItem) {
				$("#myDeliveryListLayer").find("[id^=checkDeliv_][data-DELYPL_NM='" + delivItem.DELYPL_NM + "'][data-ZIP_SEQ='" + delivItem.ZIP_SEQ + "']").propChecked(true);
			});
		}
		else {
			$("#myDeliveryListLayer").find("[id^=checkDeliv_][data-MAIN_DELYPL_YN='Y']").propChecked(true);
		}
		MultiDelivery.addDelivery();
		var $target = $("#multiDelivLayer");
		$("[name=itemPreOrderItemSeq]").each(function(idx, item) {
			var itemQty = Number($("[name=itemBsketQty]").eq(idx).val()) + Number($("[name=realItemExtraQty]").eq(idx).val());
			$($target).find("[name=bsketQty_multi][PRE_ORDER_ITEM_SEQ='" + $(item).val() + "']").val(itemQty);
		});
		MultiDelivery.setBasketQty();
		MultiDelivery.pristine = true;
	},
	
	// 배송지 목록 다시 조회
	load: function(refresh) {
		MultiDelivery.deliveryList = [];
		var orgCache = $.ajaxSetup().cache != null ? $.ajaxSetup().cache : true;
		$.ajaxSetup({ cache:false });
		$.getJSON("/order/myDeliveryList.do")
		.done(function(data) {
			if (data && data.list) {
				$.each(data.list, function(i, o) {
					// TODO 특정점 택배배송 불가 지역은 제외
					MultiDelivery.deliveryList.push(o);
				});
			}
			MultiDelivery.init();
			if (refresh) {
				setTimeout(function() {
					$("#myDeliveryListLayer").hide();
					$("#mask").hide();
					MultiDelivery.add(refresh);
				}, 500);
			}
			$.ajaxSetup({ cache:orgCache });
		})
		.fail(function(xhr, status) {
			alert(status.error);
			$.ajaxSetup({ cache:orgCache });
		});
	},
	
	// 배송지 추가
	add: function(refresh) {
		$("#myDeliveryListLayer").find("[id^=checkDeliv_]").propChecked(false).propDisabled(false);
		$("#multiDelivLayer [data-delivery]").each(function(i, o) {
			var delCd = $(o).attr("data-delivery");
			$("#myDeliveryListLayer").find("#checkDeliv_" + delCd).propChecked(true).propDisabled(true);
		});
		
		$("[id^=checkDeliv_]:not(:disabled)", $("#myDeliveryListLayer")).each(function(i, o) {
			var pickYN = $(o).attr("data-pick_type");
			if( pickYN != "" && pickYN != "Y" && pickYN != "N" ) {
				$(o).propChecked(false).propDisabled(true);
			}
		});
		
		layerPopCheckbox();

		if (!refresh) {
			$("#mask").show();
			$("#myDeliveryListLayer").show();
			setLayerPopup("myDeliveryListLayer");
		}
		
		MultiDelivery.count();
		
	},
	cancel: function() {
		$("#myDeliveryListLayer").hide();
		$("#mask").hide();
	},
	count : function() {
		var count = $("[id^=checkDeliv_]:checked", "#myDeliveryListLayer" ).length;
		
		$("#multiDelivSelCount").html("[총 " + count + "개]");
	},
	renderDeliveryLayer: function() {
		var template = $.templates("#myDeliveryListTemplate");
		var $target = $("#myDeliveryListLayer");
		
		$target.html(template.render({
			delivList: MultiDelivery.deliveryList
		}));
		
		$("#multiDelivLayer [data-delivery]").each(function(i, o) {
			var delCd = $(o).attr("data-delivery");
			$("#myDeliveryListLayer").find("#checkDeliv_" + delCd).propChecked(true).propDisabled(true);
		});
		

		$("[id^=checkDeliv_]:not(:disabled)", $target).each(function(i, o) {
			var pickYN = $(o).attr("data-pick_type");
			if( pickYN != "" && pickYN != "Y" && pickYN != "N" ) {
				$(o).propChecked(false).propDisabled(true);
			}
		});
		
		$("#AllCheckDeliv", $target).click(function(e) {
			var checked = $(this).is(":checked");
			
			$target.find("[id^=checkDeliv_]:not(:disabled)")
				.propChecked(checked)
				.each(function(i, obj) {
					inputCheckbox($(obj));
				});
			
			MultiDelivery.count();
		});
		
		$("[id^=checkDeliv_]:not(:disabled)", $target).click(function(e) {
			MultiDelivery.count();
		});
		
		layerPopCheckbox();
		MultiDelivery.count();
	},

	// 배송지 추가 action
	addDelivery: function() {
		var template = $.templates("#multiDeliveryItemTemplate");
		var itemHtml = "";
		var lastIndex = $("#multiDelivLayer delivplace").length; 
		var list = [];
		// 선택된 다중 배송지 목록을 가져 온다.
		var haveDeli = true;
		var $target = $("#myDeliveryListLayer");
		$("[id^=checkDeliv_]:checked:not(:disabled)", $target).each(function(i, o) {
			var item = $.grep(MultiDelivery.deliveryList, function(deli) {
				return deli.DELIVERY_CD === $(o).val();
			});
			if (item != null && item.length > 0) {
				if (preOrderDeliveryList != null) {
					$.each(preOrderDeliveryList, function(idx,deliv) {
						if (item[0].DELYPL_NM === deliv.DELYPL_NM && $.trim(item[0].ZIP_SEQ) === $.trim(deliv.ZIP_SEQ)) {
							item[0].DELIVERY_ID = deliv.DELIVERY_ID;
							return false;
						}
					});
				}
				list.push(item[0]);
			}
		});
		
		var itemList = [];
		$.each(list, function(i, obj) {
			itemList = $.grep(MultiDelivery.preOrderItemList, function(item) {
				return obj.DELIVERY_ID === item.DELIVERY_ID;
			});
			if (itemList == null || itemList.length < 1 ) {
				haveDeli = false;
				itemList = MultiDelivery.prodList;
			}
			itemHtml = template.render({
				deliv: obj,
				delivNo: lastIndex + i,
				itemList: itemList,
				deliveryMessageList: MultiDelivery.deliveryMessageList,
				phoneSList: ["02", "031", "032", "033", "041", "042", "043", "044", "051", "052", "053", "054", "055", "061", "062", "063", "064"]
			}, {
				util: {
					parseInt: parseInt
				}
			});
			
			$("#multiDelivFooter").before(itemHtml);
			
			// 추가된 배송메시지가 등록되어 있다면 선택.
			var objItem = haveDeli ? itemList[0] : {};
			var exists = false;
			if( objItem.DELI_RQST_SBJT_CONTENT ) {
				$( "select[id^=selectDeliRqstSbjtContent_]").last().find("option").each(function() {
					if( $(this).text() == objItem.DELI_RQST_SBJT_CONTENT ) {
						$(this).attr("selected", "selected");
						$("input[name=deliRqstSbjtContent]").last().val( $(this).text() );
						$( "label[id^=selectDeliRqstSbjtContentLabel_]").last().text( $(this).text() );
						exists = true;
						return false;
				    }
				});
				if( !exists ) {
					//$("input[name=deliRqstSbjtContent]").last().attr("placeholder", "*상품관련 요청사항 및 특정 배송시각 요청사항은 반영이 불가합니다(30자 이내 작성)");
		 			$("div[id^=selectDeliRqstSbjtContentDiv]").last().hide();
		 			$("div[id^=deliRqstSbjtContentDiv]").last().show();
		 			$("input[name=deliRqstSbjtContent]").last().val( objItem.DELI_RQST_SBJT_CONTENT );
		 			$("span[id^=deliRqstSbjtContentCnt]").last().html( objItem.DELI_RQST_SBJT_CONTENT.length + " / 30");
				}
			}
		});
		
		selectMenu();
		
		$("#multiDelivLayer .wrap-delivplace-h3 h3").each(function(i, o) {
			$(o).html($(o).html().replace("##", i+1));
		});
		
		MultiDelivery.setBasketQty();
		MultiDelivery.onDeliveryEvent();
		MultiDelivery.pristine = false;
		// 마스크 제거
		$("#myDeliveryListLayer").hide();
		$("#mask").hide();
	},
	
	// 배송지 삭제
	remove: function(obj, e) {
		var $par = $(obj).closest(".wrap-delivplace-h3");
		var delCd = $par.attr("data-delivery");
		var $target = $("#multiDelivLayer");
		$("#delivHeader_" + delCd, $target).remove();
		$("#delivBody_" + delCd, $target).remove();
		MultiDelivery.setBasketQty();
		MultiDelivery.setDeliveryTitle();
		MultiDelivery.onDeliveryEvent();
		MultiDelivery.pristine = false;
	},
	
	// 신규 배송지 등록
	newDelivery: function() {
		$("#fromBasketCmd").val("opener.MultiDelivery.load(); self.close();");
		var url = "/mymart/popup/selectMemberDeliveryForm.do";
		openwindow(url, "POP_MY_DELIVERY", 600, 690);
	},
	
	setBasketQty: function() {
		var $target = $("#multiDelivLayer");
		var bsketNo, count = 0, item, bQty = 0, eQty = 0;
		
		$.each(MultiDelivery.prodList, function(i, p) {
			p.SET_QTY = 0;
			p.REST_QTY = Number(p.BSKET_QTY) + Number(p.EXTRA_QTY);
			p.TEMP_BSKET_QTY = Number(p.BSKET_QTY);
			p.TOT_BSKET_QTY = Number(p.BSKET_QTY) + Number(p.EXTRA_QTY);
		});
		                                            
		$("[name=bsketQty_multi]", $target).each(function(i, bObj) {
			bsketNo = $(bObj).attr("data-basket");
			item = $.grep(MultiDelivery.prodList, function(p) {
				return p.BSKET_NO === bsketNo;
			});
			if (item != null && item.length > 0) {
				bQty = Number($(bObj).val());
				eQty = 0;
				
				item[0].SET_QTY += bQty;

				if (item[0].TEMP_BSKET_QTY <= 0 ) {	// 장바구니 수량 소진시
					eQty = bQty;
					bQty = 0;
					item[0].TEMP_BSKET_QTY -= bQty;
				}
				else {
					if ( (item[0].TEMP_BSKET_QTY - bQty) <= 0 ) {	// 잔여 수량보다 클거나 같을 경우
						eQty = (bQty - item[0].TEMP_BSKET_QTY);
						bQty -= eQty;
						item[0].TEMP_BSKET_QTY = 0;
					}
					else {
						eQty = 0;
						item[0].TEMP_BSKET_QTY -= bQty;
					}
				}
				if ( (bQty + eQty) > 0) {
					var orderItem = [item[0].PROD_CD, item[0].ITEM_CD, bQty, eQty];
					$(bObj).closest("tr[data-basket]").attr("data-item", orderItem.join(CONST.get("GUBUN02")));
				}
				else {
					$(bObj).closest("tr[data-basket]").attr("data-item", "");
				}
			}
		});
		$.each(MultiDelivery.prodList, function(i, p) {
			p.REST_QTY = (Number(p.BSKET_QTY) + Number(p.EXTRA_QTY)) - Number(p.SET_QTY);
			$(".mDelivOrdQty[data-basket=" + p.BSKET_NO + "]", $target).html(p.REST_QTY);
		});
	},
	                      
	
	// 상품 수량 수정
	editQty: function(obj, e) {
		var bsketNo = $(obj).attr("data-basket");
		$(obj).val(Number($(obj).val().replace(/[^0-9]/g, "")));		// 숫자외 무시
		
		if ($(obj).val() == "" || Number($(obj).val()) < 0) {
			$(obj).val("0");
			MultiDelivery.pristine = false;
			return false;
		}
		
		if (MultiDelivery.checkQty(bsketNo)) {
			MultiDelivery.pristine = false;
			MultiDelivery.setBasketQty();
		}
		else {
			$(obj).val("0");
			MultiDelivery.setBasketQty();
			MultiDelivery.pristine = false;
			return false;
		}
	},
	
	// 상품 수량 증가
	upQty: function(obj, e) {
		var $par = $(obj).closest("tr[data-basket]");
		var bsketNo = $par.attr("data-basket");
		$("[name=bsketQty_multi]", $par).val(Number($("[name=bsketQty_multi]", $par).val()) + 1);
		
		if (MultiDelivery.checkQty(bsketNo)) {
			MultiDelivery.setBasketQty();
		}
		else {
			$("[name=bsketQty_multi]", $par).val(Number($("[name=bsketQty_multi]", $par).val()) - 1);
		}
		MultiDelivery.pristine = false;
	},
	
	// 상품 수량 감소
	downQty: function(obj, e) {
		var $par = $(obj).closest("tr[data-basket]");
		var bsketNo = $par.attr("data-basket");
		$("[name=bsketQty_multi]", $par).val(Number($("[name=bsketQty_multi]", $par).val()) - 1);
		
		if (Number($("[name=bsketQty_multi]", $par).val()) >= 0) {
			MultiDelivery.setBasketQty();
		}
		else {
			$("[name=bsketQty_multi]", $par).val(Number($("[name=bsketQty_multi]", $par).val()) + 1);
		}
		MultiDelivery.pristine = false;
	},
	
	checkQty: function(bsketNo) {
		var $target = $("#multiDelivLayer");
		var itemList = $.grep(MultiDelivery.prodList, function(p) {
			return p.BSKET_NO === bsketNo;
		});
		var count = 0;
		$("[name=bsketQty_multi][data-basket=" + bsketNo + "]", $target).each(function(i, o) {
			count += Number($(o).val()); 
		});
		if (itemList != null && itemList.length > 0) {
			var item = itemList[0];
			if (Number(item.TOT_BSKET_QTY) < count) {
				// 배송주문수량이 주문수량보다 많을 수 없습니다.
				alert(messages['msg.preorder.deliorderqty.orderqty.over']);
				return false;
			}
			return true;
		}
		return false;
	},
	
	// 주문 전 배송지 설정 검증
	validateOnBeforeSubmit: function() {
		var $target = $("#multiDelivLayer");
		var result = true;
		var errorMsg = "";
		var errorObj = null;
		var validObj = null;

		var checkSpecialType = function(in_str){
			var re = /[~!@\#$%^&*\()\=+_'\"]/gi;

			if(re.test(in_str)) {
				return false;
			}else{
				return true;
			}
		}
		if( $("[id^=delivHeader_]").length == 0 ) {
			// 선택하신 배송지가 없습니다. 배송지를 선택해 주세요.
			errorMsg = messages['msg.preorder.deliverychoose.null'];
			result = false;
		}
		if ( result ) {
			// 수량 체크
			$.each(MultiDelivery.prodList, function(i, item) {
				if (item.REST_QTY != 0) {
					// 배송지 별 주문 수량 선택 후 배송비 적용하기 버튼을 클릭 하셔야 주문 수량이 적용 됩니다. 주문수량 선택 후 결제를 진행해주세요
					errorMsg = messages['msg.preorder.check.deliorderqty.remain'];
					result = false;
					return false;
				}
			});
		}
		if ( result ) {
			$("[id^=delivBody_]", $target).each(function(i, obj) {
				var count = 0;
				$("[name=bsketQty_multi]", $(obj)).each(function(idx, qtyObj) {
					count += Number($(qtyObj).val());
				});
				if ( count <= 0 ) {
					// 선택한 배송지중 배송주문수량이 모두 0개인 배송지가 있습니다.
					errorMsg = messages["msg.preorder.check.deliorderqty.zero"];
					result = false;
					return false;
				}
			});
		}
		if ( result ) {
			// 받는분 이름 체크
			$("[name=delyplCustNm]", $target).each(function(i, obj) {
				if ($.trim($(obj).val()) === "" ) {	// 공백
					// 받으시는분 이름을 입력해 주시기 바랍니다.
					errorMsg = messages['msg.preorder.dlvnm.null'];
					errorObj = $(obj);
					result = false;
					return false;
				}
				if (!checkSpecialType($(obj).val())) {
					// 받으시는분 이름에는 특수문자가 불가합니다.
					errorMsg = messages['msg.preorder.check.delyplCustNm.checkSpecialType'];	// 특수문자
					errorObj = $(obj);
					result = false;
					return false;
				}
			});
		}
		if ( result ) {
			// 받는분 휴대폰 체크
			var phone1 = $("[name=dlvCellNoS]", $target);
			var phone2 = $("[name=dlvCellNoM]", $target);
			var phone3 = $("[name=dlvCellNoE]", $target);
			var phoneLen = phone1.length;
			for( var i = 0 ; i < phoneLen; i++ ) {
				validObj = validPhoneNumber(phone1[i], phone2[i], phone3[i], "C");
				if (validObj != null && !validObj.success) {
					errorMsg = validObj.message;
					errorObj = validObj.phone;
					result = validObj.success;
					break;
				}
			}
		}
		if ( result ) {
			// 받는분 전화 체크
			var phone1 = $("[name=dlvTellNoS]", $target);
			var phone2 = $("[name=dlvTellNoM]", $target);
			var phone3 = $("[name=dlvTellNoE]", $target);
			var phoneLen = phone1.length;
			for( var i = 0 ; i < phoneLen; i++ ) {
				if($(phone1[i]).val() != '' || $(phone2[i]).val() != '' || $(phone3[i]).val() != '' ) {
					validObj = validPhoneNumber(phone1[i], phone2[i], phone3[i], "T");
					if (validObj != null && !validObj.success) {
						errorMsg = validObj.message;
						errorObj = validObj.phone;
						result = validObj.success;
						break;
					}
				}
			}
		}
		if (!result) {
			if (errorObj != null) {
				$(errorObj).select().focus();
			}
		}
		
		return errorMsg;
	},
	
	accept: function(callback) {
		if (MultiDelivery.pristine) {
			// 다중 배송 상품이 모두 적용되어 있습니다.
			alert("다중 배송 상품이 모두 적용되어 있습니다.");
			return;
		}
		
		initTForm();
		var errMsg = MultiDelivery.validateOnBeforeSubmit();
		if (errMsg != "") {
			alert(errMsg);
			return;
		}
		var $target = $("#multiDelivLayer");
		var deliItemListArr = new Array();
		var orderList;
		var deliItemHtml;
		$("[id^=delivBody_]", $target).each(function(index, obj) {
			
			deliItemListArr = new Array();
			
			// 상품 수량 셋팅한 리스트 생성
			orderList = fnSetOrderList(index, MultiDelivery.prodList, deliItemListArr);
			
			// 배송지 정보 및 배송상품 선택확인 HTML 디자인
			//deliItemHtml = fnSetDeliItemHtml(index, deliItemListArr, deliItemHtml);
			
			//deliItemHtml += '<input type="hidden" name="deliveryList" value="' + $(obj).attr("data-delivery") + '" />';
			//deliItemHtml += '<input type="hidden" name="orderList" value="' + orderList + '" />';
			
			$('#divTemp').append(genDomInput("orderListChkData", orderList));
			$('#divTemp').append(genDomInput("deliveryCdChkData", $(obj).attr("data-delivery")));
		});
		
		fnNewMultiOrderItemChk();
	},

	onDeliveryEvent : function() {
		// delivery message select box change event
		$("select[id^=selectDeliRqstSbjtContent_]").off("change").on("change", function(event){
	 		var $selectItem = $(this);
	 		var idx = $("select[id^=selectDeliRqstSbjtContent]").index(this);
	 		
	 		$("input[name=deliRqstSbjtContent]").eq(idx).val("");
	 		$("span[id^=deliRqstSbjtContentCnt]").eq(idx).empty();

	 		$("label[id^=selectDeliRqstSbjtContentLabel]").eq(idx).text($selectItem.find("option:selected").text());

			$("input[name=deliRqstSbjtContent]").eq(idx).attr("placeholder", "*상품관련 요청사항 및 특정 배송시각 요청사항은 반영이 불가합니다(30자 이내 작성)");
	 		if($selectItem.val() == "0" || $selectItem.val() == "4"){
	 			$("div[id^=selectDeliRqstSbjtContentDiv]").eq(idx).hide();
	 			$("div[id^=deliRqstSbjtContentDiv]").eq(idx).show();
	 			$("span[id^=deliRqstSbjtContentCnt]").eq(idx).html("0 / 30");
	 	 		if($selectItem.val() == "4"){
		 			$("input[name=deliRqstSbjtContent]").eq(idx).attr("placeholder", $selectItem.find("option:selected").text());
		 			$("span[id^=deliRqstSbjtContentCnt]").eq(idx).html("0 / 10");
	 	 		}
	 		}else{
	 			$("div[id^=selectDeliRqstSbjtContentDiv]").eq(idx).show();
	 			$("div[id^=deliRqstSbjtContentDiv]").eq(idx).hide();
	 			if ( $selectItem.find("option:selected").val() != "") {
	 				$('input[name=deliRqstSbjtContent]').eq(idx).val( $selectItem.find("option:selected").text());
	 			}
	 			var count = $('input[name=deliRqstSbjtContent]').eq(idx).val().length;
	 			$("span[id^=deliRqstSbjtContentCnt]").eq(idx).html(count + " / 30");
	 		}
		});
		// 배송메시지 직접입력 시
		$("input[name=deliRqstSbjtContent]").off("change keyup").on("change keyup", function(event) {
			var $inputItem = $(this);
			var idx = $("input[name=deliRqstSbjtContent]").index(this);
			var count = $inputItem.val().length;
			$("span[id^=deliRqstSbjtContentCnt]").eq(idx).html("");
			$("span[id^=deliRqstSbjtContentCnt]").eq(idx).html(count + " / 30");
			if( '004' == $('input[name=extStrType]').val()){
				$("span[id^=deliRqstSbjtContentCnt]").eq(idx).html(count + " / 10");
			}
		});

		$("button[id^=deliRqstSbjtContentBtn]").off("click").on("click", function(event){
			var $buttonItem = $(this);
			var idx = $("button[id^=deliRqstSbjtContentBtn]").index(this);
			
			$("div[id^=selectDeliRqstSbjtContentDiv]").eq(idx).show();
			$("div[id^=deliRqstSbjtContentDiv]").eq(idx).hide();
			
			$("input[name=deliRqstSbjtContent]").eq(idx).val("");
	 		$("span[id^=deliRqstSbjtContentCnt]").eq(idx).empty();
	 		$("select[id^=selectDeliRqstSbjtContent]").eq(idx).val("").trigger("change");
		});
		
	},
	
	setDeliveryTitle : function() {
		$(".wrap-delivplace-h3 .set-complex-align .a-left h3", "#multiDelivLayer").each(function(i, obj) {
			var $tmpStrong = $(obj).find("strong");
			$(obj).html(" 배송지 " + (i+1) + " " )
				  .append($tmpStrong);
		});	
	},

};

/** ********************************************************
 * TForm 을 사용하기 전에 초기화 처리를 위해 호출해야함
 ******************************************************** */
 function initTForm(){
	var inHtml = "<form name=\"tForm\" id=\"tForm\" method=\"POST\" action=\"\">"
			+ "<div id=\"divTemp\"></div>";
	$("#tFormDiv").html(inHtml);
 }

/**********************************************************
 * 멀티배송 상품 재고 및 판매 유무 등 체크
 ******************************************************** */
function fnNewMultiOrderItemChk(){
	var params = $('#tForm').serialize();
	$.post(_LMAppSSLUrl+"/basket/ajaxMultiDeliItemChk.do", params, "json")
	.done(callBackFnNewMultiOrderItemChk);
}

/** ********************************************************
 *  멀티배송 상품 재고 및 판매 유무 등 체크 후 실행 Function
 ******************************************************** */
function callBackFnNewMultiOrderItemChk(jsonData){
	initTForm();
	var param = jsonData[0];

	if(param.ERR_NO != "0") {
		alert(param.ERR_MSG);
		return false;
	}//if
		
	if(param.ERR_NO == "0") {
		// preOrder 정보를 update
		$("#tForm").html(genPreOrderFormData(""));
		//return;
		$.post(_LMAppSSLUrl+"/basket/api/updatePreOrder.do", $("#tForm").serialize(), "json")
		.done(callbackFnUpdatePreOrder)
		.fail(function(xhr) {
			try {
				var data = $.parseJSON(xhr.responseText);
				if (data != null) {
					alert(data.message);
				}
				else {
					throw exception();
				}
			}
			catch ( e ) {
				// internal error
				alert("배송지를 적용하는 중 오류가 발생했습니다.");
				return;
			}
		});
	}//if
}

function callbackFnUpdatePreOrder(jsonData) {
	//console.log(jsonData);
	//return;

	// 상품목록 재생성
	var templates = $.templates("#preOrderItemTemplate");
	var html = templates.render({
		itemList: jsonData.itemList,
		BSKET_TYPE_CD: BSKET_TYPE_CD
	}, {
		currency: util.currency,
		getItemImagePath: function(prodCd, width, scheme) {
			return utils.getItemImagePath(prodCd, width, _LMCdnStaticRootUrl);
		},
		getConst: function(constNm) {
			return CONST[constNm];
		},
		Number: Number,
		renderPromotion: function(promoStr) {
			if ($.trim(promoStr) == "") {
				return "";
			}
			var html = "";
			var iconSets = promoStr.split('|');
			for (var i = 0 ; i < iconSets.length ; i++ ) {
				var iconInfo = iconSets[i].split(":");
				if (iconInfo != null && iconInfo.length > 2 ) {
					if (iconInfo[1].indexOf("discount") >= 0) {
						html += "<i class=\"icon-band-discount\">" + iconInfo[3] + "% 할인</i>";
					}
					else if (iconInfo[1].indexOf("won") >= 0) {
						html += "<i class=\"icon-band-won\">" + (iconInfo[3]) + "원 할인</i>";
					}
					else if (iconInfo[1].indexOf("bundle") >= 0) {
						html += "<i class=\"icon-band-bundle\">" + (iconInfo[3]) + "</i>";
					}
					else if (iconInfo[1].indexOf("evt") >= 0) {
					//	html += "<i class=\"icon-tag-benefit-custom\" style=\"background-image: url('" + _LMCdnStaticUrl + "/images/front/common/flag-s-" + iconInfo[1] + "_bg.gif');\">온라인단독</i>";
						html += "<i><img src='"+_LMCdnStaticUrl+"'/images/front/common/flag-s-"+iconInfo[1] +"_bg.gif' alt='온라인단독'></i>"
					}
					else {
						if (iconInfo[4].indexOf("-1") >= 0) {
							html += "<i class=\"icon-band-tag" + iconInfo[4] + "\">" ;
							if( iconInfo[1] == "type6"){
								html += "다동이 ";
							}
							html +=  iconInfo[3] + "% 할인</i>";
						}else if (iconInfo[4].indexOf("-2") >= 0) {
							html += "<i class=\"icon-band-tag" + iconInfo[4] + "\">";
							if( iconInfo[1] == "type6"){
								html += "다동이 ";
							}
							html += iconInfo[3] + "원 할인</i>";
						}else{
						       html += "<i class=\"icon-band-tag" + iconInfo[1].replace("type", "") + "\">" + iconInfo[2] + "</i>";
						}
					}
				}
				html += " ";	// icon 간격을 위해 여백 추가
			}
			return html;
		}
	});
	$("#preOrderItemBody").html(html);
	
	// 배송비 묶음 처리
	var rowCntByDeliv = {};
	$("#preOrderItemBody tr").each(function(idx, trObj) {
		if (typeof rowCntByDeliv[$(trObj).attr("data-DELIVERY_ID")] === "undefined") {
			rowCntByDeliv[$(trObj).attr("data-DELIVERY_ID")] = 1;
		}
		else {
			rowCntByDeliv[$(trObj).attr("data-DELIVERY_ID")] += 1;
		}
	});
	$.each(rowCntByDeliv, function(idx, rObj) {
		if (rObj > 1) {
			$("#preOrderItemBody").find("tr[data-DELIVERY_ID='" + idx + "']:first td.tdProdDelivAmt").attr("rowspan", rObj);
			$("#preOrderItemBody").find("tr[data-DELIVERY_ID='" + idx + "']").not(":first").find("td.tdProdDelivAmt").remove();
		}
	});
	
	// 더보기 show/hide
	$("#preOrderItemViewMore").toggle($("#preOrderItemBody tr").length > 1);

	// 배송비 rowspan 설정
	if ($("#preOrderItemBody tr").length > 0) {
		var delivIds = [];
		$("#preOrderItemBody tr").each(function(i, tr) {
			var delivId = $(tr).attr("data-DELIVERY_ID");
			if (delivIds.indexOf(delivId) < 0 ) {
				delivIds.push(delivId);
			}
		});
		
		$.each(delivIds, function(i, deliveryId) {
			var arrTr = $("#preOrderItemBody tr[data-DELIVERY_ID='" + deliveryId + "']");
			if (arrTr.length > 1 ) {
				$("#preOrderItemBody tr[data-DELIVERY_ID='" + deliveryId + "'] .tdProdDelivAmt:first").attr("rowspan", arrTr.length);
				$("#preOrderItemBody tr[data-DELIVERY_ID='" + deliveryId + "'] .tdProdDelivAmt").not(":first").remove();
			}
		});
		
		$("#preOrderItemBody tr").not(":first").hide();
	}

	// 배송비 설정
	var itemDelivAmt = 0;
	var itemExtDelivAmt = 0;
	var itemAmt = 0;
	
	$("[name=itemPreOrderItemSeq]").remove();
	$("[name=itemProdCd]").remove();
	$("[name=itemItemCd]").remove();
	$("[name=itemBsketQty]").remove();
	$("[name=itemCalcStaffDcRate]").remove();
	$("[name=itemBsketAmt]").remove();
	$("[name=itemDcBasicAmt]").remove();
	$("[name=itemDcCardAmt]").remove();
	$("[name=itemDcStaffAmt]").remove();
	$("[name=realItemDcBasicAmt]").remove();
	$("[name=realItemDcCardAmt]").remove();
	$("[name=realItemExtraQty]").remove();
	$("[name=preOrderItemSeq]").remove();
	$("[name=ordItemDivnCd]").remove();
	
	var preOrderForm = $("#preOrderForm");
	var itemHtml = "";
	$.each(jsonData.itemList, function(i, item) {
		if (item.ORD_ITEM_DIVN_CD == "11" ) {
			itemDelivAmt += Number(item.BSKET_AMT);
		}
		else if (item.ORD_ITEM_DIVN_CD == "12" ) {
			itemExtDelivAmt += Number(item.BSKET_AMT);
		}
		else {
			itemAmt += Number(item.BSKET_AMT);
			itemHtml += genInputHidden("itemPreOrderItemSeq", item.PRE_ORDER_ITEM_SEQ);
			itemHtml += genInputHidden("itemProdCd", item.PROD_CD);
			itemHtml += genInputHidden("itemItemCd", item.ITEM_CD);
			itemHtml += genInputHidden("itemBsketQty", item.BSKET_QTY);
			itemHtml += genInputHidden("itemCalcStaffDcRate", item.CALC_STAFF_DC_RATE);
			itemHtml += genInputHidden("itemBsketAmt", item.BSKET_AMT);
			itemHtml += genInputHidden("itemDcBasicAmt", Number(item.TOT_DC_AMT) + Number(item.TOT_COUPON_DC_AMT) + Number(item.ONE_COUPON_DC_AMT) + Number(item.CMS_COUPON_DC_AMT));
			itemHtml += genInputHidden("itemDcCardAmt", "0");
			itemHtml += genInputHidden("itemDcStaffAmt", "0");
			itemHtml += genInputHidden("realItemDcBasicAmt", "0");
			itemHtml += genInputHidden("realItemDcCardAmt", "0");
			itemHtml += genInputHidden("realItemExtraQty", item.EXTRA_QTY);
			itemHtml += genInputHidden("preOrderItemSeq", item.PRE_ORDER_ITEM_SEQ);
			itemHtml += genInputHidden("ordItemDivnCd", item.ORD_ITEM_DIVN_CD);
		}
	});
	$(preOrderForm).append(itemHtml);
	
	TOTAL_ORG_ORDER_AMT = itemAmt + (itemDelivAmt + itemExtDelivAmt);
	TOTAL_ORG_DELIV_AMT = itemDelivAmt;
	TOTAL_ORG_DELIV2_AMT = itemExtDelivAmt;
	$("#TOTAL_ORG_ORDER_AMT").val(TOTAL_ORG_ORDER_AMT);
	$("#TOTAL_ORG_DELIV_AMT").val(TOTAL_ORG_DELIV_AMT);
	$("#TOTAL_ORG_DELIV2_AMT").val(TOTAL_ORG_DELIV2_AMT);
	$("#delivTotalView").html(util.currency(itemDelivAmt + itemExtDelivAmt) + "<span class=\"won\">원</span>"); 
	// 금액재설정
	fnRecalcPromotion('First');
	MultiDelivery.pristine = true;
	
	if ($('#isMultiDelivery').val() == 'Y') {
		alert("배송지 적용이\n완료되었습니다.");
	}
}

/**********************************************************
 *  멀티배송 상품 수량 체크시 사용할 상품리스트
 ******************************************************** */
function fnSetOrderList(idx, itemQtyChkArr, deliItemListArr){
	var orderList = '';
	var firstYn = true;
	var itemOrderQty = 0;
	var itemCnt = 0;
	var itemObj;
	
	$.each(itemQtyChkArr, function(i, item) {
		$(item).attr("SET_BSKET_QTY", $(item).attr("BSKET_QTY"));
	});

	for(var i = 0 ; i < itemQtyChkArr.length ; i++) {
		itemObj = $("[name=bsketQty_multi][PROD_CD='" + itemQtyChkArr[i].PROD_CD + "'][ITEM_CD='" + itemQtyChkArr[i].ITEM_CD + "']:eq("+idx+")");
		itemOrderQty = Number($(itemObj).val());
		
		if( itemOrderQty > 0 ) {
			deliItemListArr[itemCnt] = new Array();
			deliItemListArr[itemCnt] = {
	                 PROD_CD : itemQtyChkArr[i].PROD_CD
	  	           , PROD_NM : itemQtyChkArr[i].PROD_NM
	  	           , ITEM_CD : itemQtyChkArr[i].ITEM_CD
	  	           , ORD_QTY : itemOrderQty
	  	           , CATEGORY_ID : itemQtyChkArr[i].CATEGORY_ID
	  	           , CURR_SELL_PRC : itemQtyChkArr[i].CURR_SELL_PRC
			}
			 
	
			if(firstYn){
				orderList += $(itemObj).attr("PROD_CD") + CONST.get("GUBUN02");
				firstYn = false;
			}else{
				orderList += CONST.get("GUBUN01") + $(itemObj).attr("PROD_CD") + CONST.get("GUBUN02");
			}
	
			orderList += $(itemObj).attr("ITEM_CD") + CONST.get("GUBUN02");
	
			if( Number(itemQtyChkArr[i].SET_BSKET_QTY) > 0 && (Number(itemQtyChkArr[i].SET_BSKET_QTY)-itemOrderQty) >=0){
				orderList += itemOrderQty + CONST.get("GUBUN02");
				orderList += '0';
				itemQtyChkArr[i].SET_BSKET_QTY = Number(itemQtyChkArr[i].SET_BSKET_QTY) - itemOrderQty;
			}else{
				orderList += Number(itemQtyChkArr[i].SET_BSKET_QTY) + CONST.get("GUBUN02");
				orderList += (itemOrderQty - Number(itemQtyChkArr[i].SET_BSKET_QTY));
				itemQtyChkArr[i].SET_BSKET_QTY = 0;
			}
			 
			itemCnt++;
		}
	}
	 
	return orderList;
}

var validPhoneNumber = function(phone1, phone2, phone3, phoneDiv) {
	var phoneType = "cellNo";
	if (phoneDiv == "T") {
		phoneType = "telNo";
	}
	var result = {
		message: "",
		success: true,
		phone: null
	};
	if ( result.success ) {
		if (!isOnlyNumber($.trim($(phone1).val()))) {	// 공백
			result.message = fnJsMsg(messages['errors.number'], messages['msg.mymart.alert.' + phoneType + 'S']);
			result.phone = $(phone1);
			result.success = false;
		}
	}
	if ( result.success ) {
		if(phoneType)
		if($.trim($(phone1).val()).length < 2 ){
			result.message = fnJsMsg(messages['errors.length'], messages['msg.mymart.alert.' + phoneType + 'S'], '2'); 
			result.phone = $(phone1);
			result.success = false;
		}
	}
	if ( result.success ) {
		if (!isOnlyNumber($.trim($(phone2).val()))) {	// 공백
			result.message = fnJsMsg(messages['errors.number'], messages['msg.mymart.alert.' + phoneType + 'M']);
			result.phone = $(phone2);
			result.success = false;
		}
	}
	if ( result.success ) {
		if($.trim($(phone1).val()) == "010" && $.trim($(phone2).val()).length < 3 ){
			result.message = fnJsMsg(messages['errors.length'], messages['msg.mymart.alert.' + phoneType + 'M'], '3'); //가운데자리를 4자 입력해야 합니다.
			result.phone = $(phone2);
			result.success = false;
		}
		else if ($.trim($(phone1).val()) != "010" && $.trim($(phone2).val()).length < 3 ) {
			result.message = fnJsMsg(messages['errors.minlength'], messages['msg.mymart.alert.' + phoneType + 'M'], '3'); //가운데자리를 3자 이상 입력해야 합니다.
			result.phone = $(phone2);
			result.success = false;
		}
	}
	if ( result.success ) {
		if (!isOnlyNumber($.trim($(phone3).val()))) {	// 공백
			result.message = fnJsMsg(messages['errors.number'], messages['msg.mymart.alert.' + phoneType + 'E']);
			result.phone = $(phone3);
			result.success = false;
		}
	}
	if ( result.success ) {
		if($.trim($(phone3).val()).length < 4 ){
			result.message = fnJsMsg(messages['errors.length'], messages['msg.mymart.alert.' + phoneType + 'E'], '4'); //뒷자리는 4자 입력해야 합니다.
			result.phone = $(phone3);
			result.success = false;
		}
	}
	
	return result;
};

/**
 * 프리오더 수정을 위한 form data 생성
 */
var genPreOrderFormData = function(prefix) {
    var basketType =  $('[name=bsketTypeCd]').val();
	var selDeliType  = $('input[name=scrDeliType]:checked').val();
	var deliType = $('[name=deliTypeCd]').val();
	var tmpStr = "";
	
	if ($.trim(prefix) != "") {
		prefix += ".";
	}

    if (deliType != CONST.get("DELI_TYPE_COMBINE") && $('[name=scrDeliType]').length > 0 ) {
        tmpStr += genInputHidden(prefix + "deliTypeCd", $.trim($('[name=scrDeliType]:checked').val()));
        // 외부 픽업
        if ($.trim($('[name=scrDeliType]:checked').attr("data-ext_str_type")) != "" ) {
            tmpStr += genInputHidden(prefix + "extStrType", $('[name=scrDeliType]:checked').attr("data-ext_str_type"));
            if ($('[name=scrDeliType]:checked').attr("data-ext_str_type") === CONST.get("DRIVE_THRU_PICKUP") ) {
	            tmpStr += genInputHidden(prefix + "extStrCd", STR_CD);
       		}
            else {
            	var scrDeliType = $('[name=scrDeliType]:checked').attr("data-delitype");
	            tmpStr += genInputHidden(prefix + "extStrCd", $.trim($("[id='scrStrCd_" + scrDeliType + "'][name=scrStrCd_pickup]").val()));
       		}
   		}
	}
    else {
        tmpStr += genInputHidden(prefix + "deliTypeCd", $.trim(deliType));
	}
    tmpStr += genInputHidden(prefix + "bsketTypeCd", $.trim($('[name=bsketTypeCd]').val()));
    tmpStr += genInputHidden(prefix + "smsRecvYn", $('[name=smsRecvYn]:radio:checked').val());
    
    if ($.trim($('[name=deliRqstSbjtContent]').val()) != "") {
  		tmpStr += genInputHidden(prefix + "deliRqstSbjtContent", $.trim($('[name=deliRqstSbjtContent]').val()));

    }
    
	if ($.trim($('[name=giftMsg]').val()) != "") {
		tmpStr += genInputHidden(prefix + "giftMsg", $.trim($('[name=giftMsg]').val()));
	}
    // 19.6.26 지류영수증 주문서 금액 표기 'Y'로만 받기 송누리
	//if($('input[name=prcDispYn]').is(":checked")) {
		tmpStr += genInputHidden(prefix + "prcDispYn", "Y");
	//} else {
	//	tmpStr += genInputHidden(prefix + "prcDispYn", "N");
	//}
	
	
  	if( basketType == CONST.get("BASKET_TYPE_MART_INST")) {
  		if( isInstGrpOrderYn != 'Y' ){
  			tmpStr += genInputHidden(prefix + "strCd", $.trim($("#scrStrCd_InstPickup").val()));
  		} else {
  			tmpStr += genInputHidden(prefix + "strCd", InstGrpOrder_STR_CD);
  		}
  	}
    // 법인주문에 따를 품절처리 방법 수정
  	if ($("input[name=corporationInsuffProcMethodCd]").val() === "") {
	  	tmpStr += genInputHidden(prefix + "insuffProcMethodCd", $.trim($('[name=insuffProcMethodCd]').val()));
  	}	  	
  	else {
	  	tmpStr += genInputHidden(prefix + "insuffProcMethodCd", $.trim($('[name=corporationInsuffProcMethodCd]').val()));
  	}
 	
	if( (deliType == CONST.get("DELI_TYPE_RESERVE") || deliType == CONST.get("DELI_TYPE_COMBINE")) && isMartDeli == false) {
		var $td = $('[name=hopeDeli]:radio:checked').closest("td");
		var hopeDeli = $('[name=hopeDeli]:radio:checked').val();
		var grpDeliYn = $('[name=grpDeliYn]', $td).val();
		tmpStr += genInputHidden(prefix + "grpDeliYn", grpDeliYn);
	}
  	
  	if(basketType == CONST.get("BASKET_TYPE_MART_NORMAL") || basketType == CONST.get("BASKET_TYPE_MART_INST") || basketType == CONST.get("BASKET_TYPE_MART_IMPORT") || basketType == CONST.get("BASKET_TYPE_COMBINE")){
  		tmpStr += genInputHidden(prefix + "hopeDeliDy", $.trim($('[name=hopeDeliDy]').val()));
  		tmpStr += genInputHidden(prefix + "hopeDeliTm", $.trim($('[name=hopeDeliTm]').val()));
	}
  	if (reserveItemOrder) { 
	  	// 예약배송 상품일 경우
  		tmpStr += genInputHidden(prefix + "hopeDeliDy", reserveItemHopeDeliDt.substring(0, 8));
  		tmpStr += genInputHidden(prefix + "hopeDeliTm", reserveItemHopeDeliDt.substring(8, 10) + "00");
  	}
  	
	// 주문자 정보 셋팅
  	tmpStr += genInputHidden(prefix + "email", $.trim($('[name=emailS]').val()) + "@" + $.trim($('[name=emailE]').val()));
  	tmpStr += genInputHidden(prefix + "adminNm", $.trim($('[name=adminCustNm]').val()));
  	tmpStr += genInputHidden(prefix + "adminAddr1Nm", $.trim($('[name=adminAddr1Nm]').val()));
  	tmpStr += genInputHidden(prefix + "adminAddr2Nm", $.trim($('[name=adminAddr2Nm]').val()));
  	tmpStr += genInputHidden(prefix + "adminZipSeq", $.trim($('[name=adminZipSeq]').val()));
  	tmpStr += genInputHidden(prefix + "adminZipCd", $.trim($('[name=adminZipCd]').val()));
  	tmpStr += genInputHidden(prefix + "adminTellNoS", $.trim($('[name=adminTellNoS]').val()));
  	tmpStr += genInputHidden(prefix + "adminTellNoM", $.trim($('[name=adminTellNoM]').val()));
  	tmpStr += genInputHidden(prefix + "adminTellNoE", $.trim($('[name=adminTellNoE]').val()));
  	tmpStr += genInputHidden(prefix + "adminCellNoS", $.trim($('[name=adminCellNoS]').val()));
  	tmpStr += genInputHidden(prefix + "adminCellNoM", $.trim($('[name=adminCellNoM]').val()));
  	tmpStr += genInputHidden(prefix + "adminCellNoE", $.trim($('[name=adminCellNoE]').val()));
  	
  	if(basketType != CONST.get("BASKET_TYPE_MART_SPEC") && basketType != CONST.get("BASKET_TYPE_MART_GIFTMALL")){		// 특정점, 선물몰 외
	  	//구매대행 입력값이 있으면 넣기
  		if(null != $('[name=cursClrnNum]').val() ){
  			tmpStr += genInputHidden(prefix + "cursClrnNum", $.trim($('[name=cursClrnNum]').val()));
  		}
		
  		
  		
		// 받으시는 분 정보 셋팅
	  	if(deliType != CONST.get("DELI_TYPE_IMPORT")) {	// 해외배송 외
		  	if(deliType != CONST.get("DELI_TYPE_INST_PICKUP") && deliType != CONST.get("DELI_TYPE_NORMAL_PICKUP")) {	// 조리픽업, 매장픽업이 아닐 경우
		  		tmpStr += genInputHidden(prefix + "dlvTellNoS", $.trim($('[name=dlvTellNoS]').val()));
		  		tmpStr += genInputHidden(prefix + "dlvTellNoM", $.trim($('[name=dlvTellNoM]').val()));
		  		tmpStr += genInputHidden(prefix + "dlvTellNoE", $.trim($('[name=dlvTellNoE]').val()));
		  		tmpStr += genInputHidden(prefix + "dlvCellNoS", $.trim($('[name=dlvCellNoS]').val()));
		  		tmpStr += genInputHidden(prefix + "dlvCellNoM", $.trim($('[name=dlvCellNoM]').val()));
		  		tmpStr += genInputHidden(prefix + "dlvCellNoE", $.trim($('[name=dlvCellNoE]').val()));
		  		
		  		if( basketType == CONST.get("BASKET_TYPE_SOCIAL_COUPON") ) {	// 소셜쿠폰 일 경우
			  		tmpStr += genInputHidden(prefix + "dlvDelyplNm", $.trim($('[name=delyplCustNm]').val()));
			  		tmpStr += genInputHidden(prefix + "dlvCustNm", $.trim($('[name=delyplCustNm]').val()));
		  			tmpStr += genInputHidden(prefix + "dlvZipCd", $.trim($('[name=adminZipCd]').val()));
		  			tmpStr += genInputHidden(prefix + "dlvZipSeq", $.trim($('[name=adminZipSeq]').val()));
		  			tmpStr += genInputHidden(prefix + "dlvAddr1Nm", $.trim($('[name=adminAddr1]').val()));
		  			tmpStr += genInputHidden(prefix + "dlvAddr2Nm", $.trim($('[name=adminAddr2]').val()));
		  		}
		  		else if (deliType == CONST.get("DELI_TYPE_VENDOR_DELI")) {
		  			tmpStr += genInputHidden(prefix + "dlvZipCd", $.trim($('[name=senderZipCd]').val()));
		  			tmpStr += genInputHidden(prefix + "dlvZipSeq", $.trim($('[name=senderZipSeq]').val()));
		  			tmpStr += genInputHidden(prefix + "dlvAddr1Nm", $.trim($('[name=senderAddr1]').val()));
		  			tmpStr += genInputHidden(prefix + "dlvAddr2Nm", $.trim($('[name=senderAddr2]').val()));
		  		}
		  	}
		  	else {	// 픽업
		  		tmpStr += genInputHidden(prefix + "dlvTellNoS", $.trim($('[name=adminTellNoS]').val()));
		  		tmpStr += genInputHidden(prefix + "dlvTellNoM", $.trim($('[name=adminTellNoM]').val()));
		  		tmpStr += genInputHidden(prefix + "dlvTellNoE", $.trim($('[name=adminTellNoE]').val()));
		  		tmpStr += genInputHidden(prefix + "dlvCellNoS", $.trim($('[name=adminCellNoS]').val()));
		  		tmpStr += genInputHidden(prefix + "dlvCellNoM", $.trim($('[name=adminCellNoM]').val()));
		  		tmpStr += genInputHidden(prefix + "dlvCellNoE", $.trim($('[name=adminCellNoE]').val()));
		  		tmpStr += genInputHidden(prefix + "dlvDelyplNm", $.trim($('[name=adminCustNm]').val()));
		  		tmpStr += genInputHidden(prefix + "dlvCustNm", $.trim($('[name=adminCustNm]').val()));
	  			tmpStr += genInputHidden(prefix + "dlvZipCd", $.trim($('[name=dlvZipCd]').val()));
	  			tmpStr += genInputHidden(prefix + "dlvZipSeq", $.trim($('[name=dlvZipSeq]').val()));
	  			tmpStr += genInputHidden(prefix + "dlvAddr1Nm", $.trim($('[name=dlvAddr1Nm]').val()));
	  			tmpStr += genInputHidden(prefix + "dlvAddr2Nm", $.trim($('[name=dlvAddr2Nm]').val()));
		  	}
	  	}
	  	else {	// 해외배송 	
	  		tmpStr += genInputHidden(prefix + "forgnDeliTellNo", $.trim($('[name=forgnDeliTellNo]').val()));
	  		tmpStr += genInputHidden(prefix + "forgnDeliCellNo", $.trim($('[name=forgnDeliCellNo]').val()));
	  		tmpStr += genInputHidden(prefix + "forgnDeliAddr1", $.trim($('[name=forgnDeliAddr1]').val()));
	  		tmpStr += genInputHidden(prefix + "forgnDeliZipCd", $.trim($('[name=forgnDeliZipCd]').val()));
	  	}
		  	
	  	if( deliType == CONST.get("DELI_TYPE_RESERVE") ||deliType == CONST.get("DELI_TYPE_MART_DELI") || basketType == CONST.get("BASKET_TYPE_VENDOR_NORMAL")|| basketType == CONST.get("BASKET_TYPE_MART_IMPORT") || basketType == CONST.get("BASKET_TYPE_COMBINE")) { 	
	  		tmpStr += genInputHidden(prefix + "dlvDelyplNm", $.trim($('[name=delyplCustNm]').val()));
	  		tmpStr += genInputHidden(prefix + "dlvCustNm", $.trim($('[name=delyplCustNm]').val()));
	  	}
  	}
  	else {	// 특정점, 선물몰
		// 멀티배송 정보 셋팅
  		var orderList = [];
  		var orderItem;
		  	var $target;
		  	var deliCd;
  		$("[id^=delivBody_]").each(function(i, deliv) {
  			deliCd = $.trim($(deliv).attr("data-delivery"));
  			deliCd += (CONST.get("GUBUN01") + $.trim($("[name=dlvTellNoS]", $(deliv)).val()) + "," + $.trim($("[name=dlvTellNoM]", $(deliv)).val()) + "," + $.trim($("[name=dlvTellNoE]", $(deliv)).val()));
  			deliCd += (CONST.get("GUBUN01") + $.trim($("[name=dlvCellNoS]", $(deliv)).val()) + "," + $.trim($("[name=dlvCellNoM]", $(deliv)).val()) + "," + $.trim($("[name=dlvCellNoE]", $(deliv)).val()));
  			deliCd += (CONST.get("GUBUN01") + $("input[name=deliRqstSbjtContent]").eq(i).val());
  			tmpStr += genInputHidden(prefix + "arrDeliveryCd", deliCd);
  		  	
  		  	var bsketQty = 0;
  		  	var extraQty = 0;
  		  	orderList = [];
  		  	$("tr[data-basket]", $(deliv)).each(function(ind, tr) {
  		  		orderItem = $.trim($(tr).attr("data-item"));
  		  		if (orderItem != "" ) {
  		  			orderList.push(orderItem);
  		  		}
  		  	});
//  		  	$('#isMultiDelivery').val("Y");
  		  	tmpStr += genInputHidden(prefix + "isMultiDelivery", $.trim($('[name=isMultiDelivery]').val()));
	  		tmpStr += genInputHidden(prefix + "orderList", $.trim(orderList.join(CONST.get("GUBUN01"))));
  		});
  	}

  	return tmpStr;
};

function cutByLen(str, maxByte) {
	for(b=i=0;c=str.charCodeAt(i);) {
		b+=c>>7?2:1;
		if (b > maxByte)
			break;
		i++;
	}
	return b < maxByte ? str 
			            : str.length > 0 ? str.substring(0,i) + "…"
			            		         : "";
}