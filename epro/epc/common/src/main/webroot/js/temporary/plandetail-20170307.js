if( $('.clearance-fixed-pc > div').length > 0 ) {
	getProductDetails();	
}

function getProductDetails(){
	var planProducts = new Array();
	$('.clearance-fixed-pc > div').each(function(i, _obj){
		var product = new Object();
		product['prodCd'] =  $(_obj).data('prodcd').toString();
		if($(_obj).data('itemcd')){
			product['itemCd'] =  $(_obj).data('itemcd').toString();
		}
		if($(_obj).data('time')){
			product['time'] =  $(_obj).data('time').toString();
		}
		planProducts.push(product);
	});
	planProducts = {'planProducts' : planProducts};
	
	$.api.get({
		apiName : $.utils.config( 'LMAppUrl' ) + 'planDetail',
		data : {
			'products': JSON.stringify(planProducts)
		},
		successCallback : function( data ) {
			setProductPrice(data);
		}
	});
};

function setProductSoldOut(obj){
	obj.removeClass().addClass('soldout');
};

function setProductDim(obj, time){
	var addClass = 'desc-' + time + 'h';
	obj.removeClass().addClass('ready-cont').addClass(addClass);

	if(obj.find('p.info-txt').length < 1){
		$('<p class="info-txt"><em><strong>' + time + '시</strong>에 새로운 가격으로 판매 됩니다.</em></p>').appendTo(obj);
	}
};

function setProductPrice(data){
	if(data.productDetil.length < 1){
		return;
	}

	$('.clearance-fixed-pc > div').each(function(i, _obj){
		var _thisObj = $(_obj),
			_isSale = true;

		$.each(data.productDetil , function(cnt, _product){
			var _this = _product;
				_itemCd = _thisObj.data('itemcd') == null ? '001' : _thisObj.data('itemcd');
				_sellPrc = _this.CURR_SELL_PRC

			if(_this.PROD_CD == _thisObj.data('prodcd') && _this.ITEM_CD == _itemCd ){
				_isSale = false;

				if(!_this.IS_TIME){
					setProductDim(_thisObj, _this.TIME);
					return;
				}

				if(_this.SOUT_YN == 'Y'){
					setProductSoldOut(_thisObj);
					return;
				}

				if(_this.MAX_PROMOTION > 0 && _this.MAX_PROMOTION < _this.CURR_SELL_PRC){
					_sellPrc = _this.MAX_PROMOTION;
				}

				if(_thisObj.find('p.info-txt').length > 0){
					_thisObj.find('p.info-txt').remove();
				}

				_thisObj.removeClass().addClass('ready-cont');
				_thisObj.find('span.goods-price').html('<em>' + setComma(_sellPrc) + '</em>원');
			}
		});

		if(_isSale){
			setProductSoldOut(_thisObj);
		}
    });
};


//개인동의
function insertEnter(param1, param2, param3){
	insert(function(){
		$("#categoryId").val(param1);		 //카테고리
	    $("#subCategoryId").val(param2); 	//하위카테고리
	    $("#pageId").val(param3); 			//기획전
	    $("#agree").val('Y'); 				//3자 동의
	    $("#mkdpSeq").val( $.utils.config( 'mkdpSeq' ) );

	    var url = $.utils.config( 'LMAppUrl' ) + '/event/insertAgree.do';
	    $form.attr('action', url).submit();
	});
};
//사용하는지 여부 파악이 우선 -- 사용하지 않는 거면 삭제해도 되지 않을까 생각됩니다.
//쿠폰 다운로드
function insertCoupon(){
	insert(function(){
		if(confirm('쿠폰을 다운받으시겠습니까?')){
			$("#categoryId").val( $.utils.config( 'evntCategory' ) );

			var url = $.utils.config( 'LMAppUrl' ) + '/event/insertEnter.do';
			$form.attr('action', url).submit();
		};
	});
};

function insert(callback){
	var flag = global.isLogin( $.utils.config( 'LMAppUrl' ) + '/plan/planDetail.do?CategoryID=' + $.utils.config( 'categoryId' ) + '&MkdpSeq=' + $.utils.config( 'mkdpSeq' ) );
	if(flag){
		var notValidMessage = getNotValidMessage();
		if(notValidMessage != ''){
			alert(notValidMessage);
			return;
		};

		callback();

		if(confirm('쿠폰을 다운받으시겠습니까?')){
			$("#categoryId").val( $.utils.config( 'evntCategory' ) );

			var url = $.utils.config( 'LMAppUrl' ) + '/event/insertEnter.do';
			$form.attr('action', url).submit();
		};
	};
};

function getNotValidMessage(){
	var message = '',
		today = $.utils.config( 'today' );

	if( $.utils.config( 'noMemberNo' ) === $.utils.config( 'memberNo' ) ){
		message = view_messages.plandetail.noMember;						//비회원은 이벤트에 응모할 수 없습니다.
  	}else if( parseInt( $.utils.config( 'startdate' ), 10 ) > parseInt( today, 10 ) ){
		message = view_messages.plandetail.startcheck;				//이벤트 준비중입니다.
	}else if( parseInt( $.utils.config( 'enddate' ), 10 ) < parseInt( today, 10 ) ){
		message = view_messages.plandetail.endcheck;					//응모가 마감되었습니다.\\n응모해 주신 모든 분들께 감사 드립니다.
	}else if( $.utils.config( 'lastEventYN' ) === "Y" ){
		message = view_messages.plandetail.endcheck; 					//응모가 마감되었습니다.\\n응모해 주신 모든 분들께 감사 드립니다.
	}else if( $.utils.config( 'attendYN' ) === "Y" ){
		message = view_messages.plandetail.alreadycheck;	//회원님께서는 이벤트에 이미 참여하셨습니다.
		};

	return message;
};