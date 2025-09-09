"use strict";

//선택된 정기배송코드
var periDeliId;

//정기배송 완전취소
function cxlPeri(){
	var params = {
			periDeliId: $('input[name=periDeliId]').val()
			,exPeriDeliId: $('input[name=periDeliId]').val()
	};
	
	$.getJSON("/mymart/api/cancelPeriAll.do", params)
	.done(function(data) {
		if(jResult(data)){
			alert('삭제되었습니다');
			//$('input[name=periDeliId]').val('');
			//window.location.reload();
			$('input[name=periDeliId]').val("");
			var form = $('#periManager').get(0);
			form.submit();			
		}
	});
}

//2017.01.24 이승남
//정기배송 수정
function updatePeri(val){
	var tempMsg = ""
	if(val == 'delyDay'){
		if(!$("input[name^=deliDay]").is(":checked")){
			alert("배송 받을 요일을 선택해 주세요.");
			return;
		}else{
			tempMsg = "변경한 내용을 저장하시겠습니까?";
		}	
	} else if(val == 'extend'){		
		if(!$("input[name^=addMonth]").is(":checked")){
			alert("연장할 기간을 선택해 주세요.");
			return;
		}else{
			tempMsg = $('span.extend_deli_date').text()+"됩니다.\n연장하시겠습니까?";
		}	
	}else{
		tempMsg = "변경한 내용을 저장하시겠습니까?";
	}
	if(!confirm(tempMsg)){
		return;
	}
	$('#updateKind').val(val);
	var form = $('#periManager').get(0);
	form.action = '/mymart/updatePeri.do';
	form.submit();
}
function getExistingPeri(exPeriDeliId) {
	$('input[name=periDeliId]').val(exPeriDeliId);

	var form = $('#periManager').get(0);
	form.submit();
}

function periDetail(){
	$("#storeProdList").html("");
	$("#onlineProdList").html("");
	$("#outrangeProdList").html("");
	
	$("#storeItemBox").hide();
	$("#onlineItemBox").hide();
	$("#outrangeItemBox").hide();
	
	if(!periDeliId){
		return;
	}
	var params = {
			periDeliId: periDeliId
	};
	
	$.getJSON("/mymart/api/getPeriProduct.do", params)
		.done(function(data) {
			if(jResult(data)){

				if(!parseInt(data.prodList)) $('#totalProdCnt').text(data.prodList.length);
				
				var storeProdListHtml = "";
				var onlineProdListHtml = "";
				var outrangeProdListHtml = "";
				
				var prodList = data.prodList;
				var prodList_a = data.prodList;

				if(prodList.length > 0){
					var nCnt = 0;
					var vCnt = 0;
					var mCnt = 0;
					for(var i=0;i<prodList.length;i++){
						if(prodList[i].BASKET_TYPE == "11"){							
							nCnt++;
							storeProdListHtml += "<tr>";
							storeProdListHtml += "<input type='hidden' id='prodCd_"+prodList[i].PROD_CD+"' name='prodCd' value='"+prodList[i].PROD_CD+"' />                        ";                        
							storeProdListHtml += "<input type='hidden' id='itemCd_"+prodList[i].PROD_CD+"' name='itemCd' value='"+prodList[i].ITEM_CD+"' />                        ";
							storeProdListHtml += "<input type='hidden' id='ordQty"+prodList[i].PROD_CD+"' name='ordQty' value='"+prodList[i].ORD_QTY+"' />               ";
							storeProdListHtml += "<input type='hidden' id='minQty_"+prodList[i].PROD_CD+"' name='minQty' value='"+prodList[i].MIN_ORD_PSBT_QTY+"' />               ";
							storeProdListHtml += "<input type='hidden' id='maxQty_"+prodList[i].PROD_CD+"' name='maxQty' value='"+prodList[i].MAX_ORD_PSBT_QTY+"' />               ";
							storeProdListHtml += "	<td class='a-left-type2'>";
							storeProdListHtml += "		<article class='clear-after'>";
							storeProdListHtml += "			<div class='wrap-thumb'>";
							if(prodList[i].SELL_PSBT_YN == 'N' ||  prodList[i].PERI_DELI_YN  == 'N'){
								storeProdListHtml += "				<span class='prod-nofixe-del'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_nofixe_del_208x208.png' alt='정기배송 불가'></span>";
							}
							if(prodList[i].SOUT_YN == 'Y'){
								storeProdListHtml += "				<span class='prod-tem-sold'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_tem_sold_208x208.png' alt='일시품절'></span>";
							}
							storeProdListHtml += "				<img src='"+_LMCdnDynamicUrl+"/"+ prodList[i].MD_SRCMK_CD.substring(0,5) +"/"+ prodList[i].MD_SRCMK_CD +"_1_80.jpg' onclick='prodDetailLink(this, event);return false;' onerror='javascript:showNoImage(this,80,80)' alt='"+ prodList[i].PROD_NM + "' class='thumb'>";
							storeProdListHtml += "			</div>";
							storeProdListHtml += "			<div class='wrap-info'>";
							storeProdListHtml += "				<p class='prod-com'>";
							storeProdListHtml += "					<span class='sticker-mall-type1'>"+prodList[i].MALL_DIVN_NM+"</span>";
							storeProdListHtml += "				</p>";
							storeProdListHtml += "				<p class='prod-name'>";
							storeProdListHtml += "					<a href='javascript:;' onclick='goProductDetailView(\""+prodList[i].CATEGORY_ID+"\",\""+prodList[i].PROD_CD+"\");return false;'>"+ prodList[i].PROD_NM + "</a>";
							storeProdListHtml += "				</p>";
							if(prodList[i].PERI_DELI_YN  == 'N'){
								storeProdListHtml += "				<br>이 상품은 더 이상 정기배송이 불가능합니다.";
							}
							storeProdListHtml += "				<ul class='prod-item-detail'>";
							if(prodList[i].OPTION_YN == 'Y'){
								if(prodList[i].OPTION_NM != null){
									storeProdListHtml += "					<li>옵션: "+prodList[i].OPTION_NM+"</li>";
								}
								if(prodList[i].OPTION_NM == null && prodList[i].NFOML_VARIATION_DESC != null){
									storeProdListHtml += "					<li>옵션: "+prodList[i].NFOML_VARIATION_DESC+"</li>";
								}
							}
							
							if (prodList[i].PEST_TYPE_NM != null) {
								var strArray = prodList[i].PEST_TYPE_NM.split('SERVICE_FRONT');
								storeProdListHtml += "				<li>증정품: " + strArray[1] + "</li>";
							}
							
							storeProdListHtml += "				</ul>";
							storeProdListHtml += "			</div>";
							storeProdListHtml += "		</article>";
							storeProdListHtml += "	</td>";
							storeProdListHtml += "	<td>"+prodList[i].ORD_QTY+"개</td>";
							storeProdListHtml += "	<td>매장배송</td>";
							storeProdListHtml += "	<td><em class='txt-wbrown'>"+prodList[i].DELI_TERM_NM+"</em></td>";
							storeProdListHtml += "	<td>";
							storeProdListHtml += "		<div class='wrap-set-btn-incell'>";
							storeProdListHtml += "			<div class='set-btn-incell'>";			
							if(prodList[i].PERI_DELI_YN  != 'N'){				
							if(prodList[i].OPTION_YN != "Y"){
						        storeProdListHtml += "				<button type='button' class='btn-form-type3 layerpop-trigger2' data-trigger='layerpop-basket' onclick='changeItem(this,\""+prodList[i].PERI_DELI_PROD_SEQ+"\",\""+prodList[i].PROD_CD+"\",\""+prodList[i].ITEM_CD+"\");' title='레이어팝업 열림'>상품수량 변경</button>";							
							}else{
							    storeProdListHtml += "             <button title='상품수량 변경' class='btn-form-type3 layerpop-trigger2' id='managePeri_" + "store" + "_" + prodList[i].PROD_CD + "' onclick='managePeriCart.add(this);' type='button'  data-category-id='" + prodList[i].CATEGORY_ID+ "' data-prod-cd='" + prodList[i].PROD_CD + "' data-prod-type-cd='"+ prodList[i].ONLINE_PROD_TYPE_CD +"' data-option-yn='"+prodList[i].OPTION_YN+"' data-gubun='"+prodList[i].GUBUN+"' data-item-cd='"+prodList[i].ITEM_CD+"'  data-str-cd='" + prodList[i].STR_CD+ "'  data-zip-seq='" + prodList[i].RECP_ZIP_SEQ+ "'  data-peri-deli-prod-seq='" + prodList[i].PERI_DELI_PROD_SEQ + "'  data-min-qty='" + prodList[i].MIN_ORD_PSBT_QTY + "' data-max-qty='" + prodList[i].MAX_ORD_PSBT_QTY + "' data-btn-obj-id='managePeri_" + "cancel" + "_" + prodList[i].PROD_CD + "' data-call-back-func='addPeriBasketCallback' disabled>상품수량 변경</button>";
							}			
                            }				
							storeProdListHtml += "				<button type='button' class='btn-form-type2' onclick='cxlPeriItem(\""+prodList[i].PERI_DELI_ID+"\",\""+prodList[i].PERI_DELI_PROD_SEQ+"\");' >정기배송 전체취소</button>";
							storeProdListHtml += "			</div>";
							storeProdListHtml += "		</div>";
							storeProdListHtml += "	</td>";
							storeProdListHtml += "</tr>";
						}
						
						//if(prodList[i].DELI_TYPE == "04"){
						if(prodList[i].BASKET_TYPE == "21"){
							vCnt++;
							onlineProdListHtml += "<tr>";
							onlineProdListHtml += "<input type='hidden' id='prodCd_"+prodList[i].PROD_CD+"' name='prodCd' value='"+prodList[i].PROD_CD+"' />                        ";                        
							onlineProdListHtml += "<input type='hidden' id='itemCd_"+prodList[i].PROD_CD+"' name='itemCd' value='"+prodList[i].ITEM_CD+"' />                        ";
							onlineProdListHtml += "<input type='hidden' id='ordQty"+prodList[i].PROD_CD+"' name='ordQty' value='"+prodList[i].ORD_QTY+"' />               ";
							onlineProdListHtml += "<input type='hidden' id='minQty_"+prodList[i].PROD_CD+"' name='minQty' value='"+prodList[i].MIN_ORD_PSBT_QTY+"' />               ";
							onlineProdListHtml += "<input type='hidden' id='maxQty_"+prodList[i].PROD_CD+"' name='maxQty' value='"+prodList[i].MAX_ORD_PSBT_QTY+"' />               ";
							onlineProdListHtml += "	<td class='a-left-type2'>";
							onlineProdListHtml += "		<article class='clear-after'>";
							onlineProdListHtml += "			<div class='wrap-thumb'>";
							//onlineProdListHtml += "				<a href='#'><img src='/v3/images/layout/img_age19_80x80.jpg' alt='미성년자 구매금지 상품' class='thumb'></a>";
							if(prodList[i].SELL_PSBT_YN == 'N' ||  prodList[i].PERI_DELI_YN  == 'N'){
								onlineProdListHtml += "				<span class='prod-nofixe-del'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_nofixe_del_208x208.png' alt='정기배송 불가'></span>";
							}
							if(prodList[i].SOUT_YN == 'Y'){
								onlineProdListHtml += "				<span class='prod-tem-sold'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_tem_sold_208x208.png' alt='일시품절'></span>";
							}
							onlineProdListHtml += "				<img src='"+_LMCdnDynamicUrl+"/"+ prodList[i].MD_SRCMK_CD.substring(0,5) +"/"+ prodList[i].MD_SRCMK_CD +"_1_80.jpg' onclick='prodDetailLink(this, event);return false;' onerror='javascript:showNoImage(this,80,80)' alt='"+ prodList[i].PROD_NM + "' class='thumb'>";
							onlineProdListHtml += "			</div>";
							onlineProdListHtml += "			<div class='wrap-info'>";
							onlineProdListHtml += "				<p class='prod-com'>";
							onlineProdListHtml += "					<span class='sticker-mall-type1'>"+prodList[i].MALL_DIVN_NM+"</span>";
							onlineProdListHtml += "				</p>";
							onlineProdListHtml += "				<p class='prod-name'>";
							onlineProdListHtml += "					<a href='javascript:;' onclick='goProductDetailView(\""+prodList[i].CATEGORY_ID+"\",\""+prodList[i].PROD_CD+"\");return false;'>"+ prodList[i].PROD_NM + "</a>";
							onlineProdListHtml += "				</p>";
							if(prodList[i].PERI_DELI_YN  == 'N'){
								onlineProdListHtml += "				<br>이 상품은 더 이상 정기배송이 불가능합니다.";
								}
							onlineProdListHtml += "				<ul class='prod-item-detail'>";
							if(prodList[i].OPTION_YN == 'Y'){
								if(prodList[i].OPTION_NM != null){
									onlineProdListHtml += "					<li>옵션: "+prodList[i].OPTION_NM+"</li>";
								}
								if(prodList[i].OPTION_NM == null && prodList[i].NFOML_VARIATION_DESC != null){
									onlineProdListHtml += "					<li>옵션: "+prodList[i].NFOML_VARIATION_DESC+"</li>";
								}
							}
							
							if (prodList[i].PEST_TYPE_NM != null) {
								var strArray = prodList[i].PEST_TYPE_NM.split('SERVICE_FRONT');
								onlineProdListHtml += "				<li>증정품: " + strArray[1] + "</li>";
							}
							
							onlineProdListHtml += "				</ul>";
							onlineProdListHtml += "			</div>";
							onlineProdListHtml += "		</article>";
							onlineProdListHtml += "	</td>";
							onlineProdListHtml += "	<td>"+prodList[i].ORD_QTY+"개</td>";
							var deliCost = prodList[i].DELI_COST;
							if(deliCost == undefined){
								deliCost = 0;
							}
							
							
							var vendor_cnt_a = 0;
							for(var a=0;a<prodList_a.length;a++){
								if(prodList[i].VENDOR_ID == prodList_a[a].VENDOR_ID){
									vendor_cnt_a = vendor_cnt_a + 1;
								}
							}
							
							if(prodList[i].VENDOR_NM != null && deliCost <= 0){
								if(vendor_cnt_a > 1){
								onlineProdListHtml += "	<td><br>"+prodList[i].VENDOR_NM+"</td>";	
								}else{
								onlineProdListHtml += "	<td>무료배송<br>"+prodList[i].VENDOR_NM+"</td>";
								}
							}else if(prodList[i].VENDOR_NM != null && deliCost > 0){
								onlineProdListHtml += "	<td>"+numberWithCommas(deliCost)+"원<br>"+prodList[i].VENDOR_NM+"</td>";
							}else if(prodList[i].VENDOR_NM == null&& deliCost > 0){
								onlineProdListHtml += "	<td>"+numberWithCommas(deliCost)+"원</td>";
							}else{
								onlineProdListHtml += "	<td>무료배송</td>";
							}
							onlineProdListHtml += "	<td><em class='txt-wbrown'>"+prodList[i].DELI_TERM_NM+"</em></td>";
							onlineProdListHtml += "	<td>";
							onlineProdListHtml += "		<div class='wrap-set-btn-incell'>";
							onlineProdListHtml += "			<div class='set-btn-incell'>";
							if(prodList[i].PERI_DELI_YN  != 'N'){
  							if(prodList[i].OPTION_YN != "Y"){
							   onlineProdListHtml += "				<button type='button' class='btn-form-type3 layerpop-trigger2' data-trigger='layerpop-basket' onclick='changeItem(this,\""+prodList[i].PERI_DELI_PROD_SEQ+"\",\""+prodList[i].PROD_CD+"\",\""+prodList[i].ITEM_CD+"\");' title='레이어팝업 열림'>상품수량 변경1</button>";
							}else{
							    onlineProdListHtml += "             <button title='레이어팝업 열림' class='btn-form-type3 layerpop-trigger2' id='managePeri_" + "online" + "_" + prodList[i].PROD_CD + "' onclick='managePeriCart.add(this);' type='button'  data-category-id='" + prodList[i].CATEGORY_ID+ "' data-prod-cd='" + prodList[i].PROD_CD + "' data-prod-type-cd='"+ prodList[i].ONLINE_PROD_TYPE_CD +"' data-option-yn='"+prodList[i].OPTION_YN+"' data-gubun='"+prodList[i].GUBUN+"' data-item-cd='"+prodList[i].ITEM_CD+"'  data-str-cd='" + prodList[i].STR_CD+ "'  data-zip-seq='" + prodList[i].RECP_ZIP_SEQ+ "'  data-peri-deli-prod-seq='" + prodList[i].PERI_DELI_PROD_SEQ + "'  data-min-qty='" + prodList[i].MIN_ORD_PSBT_QTY + "' data-max-qty='" + prodList[i].MAX_ORD_PSBT_QTY + "' data-btn-obj-id='managePeri_" + "cancel" + "_" + prodList[i].PROD_CD + "' data-call-back-func='addPeriBasketCallback' disabled>상품수량 변경</button>";
							}
							}
							onlineProdListHtml += "				<button type='button' class='btn-form-type2' onclick='cxlPeriItem(\""+prodList[i].PERI_DELI_ID+"\",\""+prodList[i].PERI_DELI_PROD_SEQ+"\");' >정기배송 전체취소</button>";
							onlineProdListHtml += "			</div>";
							onlineProdListHtml += "		</div>";
							onlineProdListHtml += "	</td>";
							onlineProdListHtml += "</tr>";
						}						
						
						//if(prodList[i].DELI_TYPE == "02"){
						if(prodList[i].BASKET_TYPE == "13"){
							mCnt++;
							outrangeProdListHtml += "<tr>";
							outrangeProdListHtml += "<input type='hidden' id='prodCd_"+prodList[i].PROD_CD+"' name='prodCd' value='"+prodList[i].PROD_CD+"' />                        ";                        
							outrangeProdListHtml += "<input type='hidden' id='itemCd_"+prodList[i].PROD_CD+"' name='itemCd' value='"+prodList[i].ITEM_CD+"' />                        ";
							outrangeProdListHtml += "<input type='hidden' id='ordQty"+prodList[i].PROD_CD+"' name='ordQty' value='"+prodList[i].ORD_QTY+"' />               ";
							outrangeProdListHtml += "<input type='hidden' id='minQty_"+prodList[i].PROD_CD+"' name='minQty' value='"+prodList[i].MIN_ORD_PSBT_QTY+"' />               ";
							outrangeProdListHtml += "<input type='hidden' id='maxQty_"+prodList[i].PROD_CD+"' name='maxQty' value='"+prodList[i].MAX_ORD_PSBT_QTY+"' />               ";
							outrangeProdListHtml += "	<td class='a-left-type2'>";
							outrangeProdListHtml += "		<article class='clear-after'>";
							outrangeProdListHtml += "			<div class='wrap-thumb'>";
							//outrangeProdListHtml += "				<a href='#'><img src='../../../v3/images/layout/img_age19_80x80.jpg' alt='미성년자 구매금지 상품' class='thumb'></a>";
							if(prodList[i].SELL_PSBT_YN == 'N' ||  prodList[i].PERI_DELI_YN  == 'N'){
								outrangeProdListHtml += "				<span class='prod-nofixe-del'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_nofixe_del_208x208.png' alt='정기배송 불가'></span>";
							}
							if(prodList[i].SOUT_YN == 'Y'){
								outrangeProdListHtml += "				<span class='prod-tem-sold'><img src='" + _LMCdnV3RootUrl + "/images/layout/img_tem_sold_208x208.png' alt='일시품절'></span>";
							}
							outrangeProdListHtml += "				<img src='"+_LMCdnDynamicUrl+"/"+ prodList[i].MD_SRCMK_CD.substring(0,5) +"/"+ prodList[i].MD_SRCMK_CD +"_1_80.jpg' onclick='prodDetailLink(this, event);return false;' onerror='javascript:showNoImage(this,80,80)' alt='"+ prodList[i].PROD_NM + "' class='thumb'>";
							outrangeProdListHtml += "			</div>";
							outrangeProdListHtml += "			<div class='wrap-info'>";
							outrangeProdListHtml += "				<p class='prod-com'>";
							outrangeProdListHtml += "					<span class='sticker-mall-type1'>"+prodList[i].MALL_DIVN_NM+"</span>";
							outrangeProdListHtml += "				</p>";
							outrangeProdListHtml += "				<p class='prod-name'>";
							outrangeProdListHtml += "					<a href='javascript:;' onclick='goProductDetailView(\""+prodList[i].CATEGORY_ID+"\",\""+prodList[i].PROD_CD+"\");return false;'>"+ prodList[i].PROD_NM + "</a>";
							outrangeProdListHtml += "				</p>";
							if(prodList[i].PERI_DELI_YN  == 'N'){
							outrangeProdListHtml += "				<br>이 상품은 더 이상 정기배송이 불가능합니다.";
								}
							outrangeProdListHtml += "				<ul class='prod-item-detail'>";
							if(prodList[i].OPTION_YN == 'Y'){
								if(prodList[i].OPTION_NM != null){
									outrangeProdListHtml += "					<li>옵션: "+prodList[i].OPTION_NM+"</li>";
								}
								if(prodList[i].OPTION_NM == null && prodList[i].NFOML_VARIATION_DESC != null){
									outrangeProdListHtml += "					<li>옵션: "+prodList[i].NFOML_VARIATION_DESC+"</li>";
								}
							}
							
							if (prodList[i].PEST_TYPE_NM != null) {
								var strArray = prodList[i].PEST_TYPE_NM.split('SERVICE_FRONT');
								outrangeProdListHtml += "				<li>증정품: " + strArray[1] + "</li>";
							}
							
							outrangeProdListHtml += "				</ul>";
							outrangeProdListHtml += "			</div>";
							outrangeProdListHtml += "		</article>";
							outrangeProdListHtml += "	</td>";
							outrangeProdListHtml += "	<td>"+prodList[i].ORD_QTY+"개</td>";
							outrangeProdListHtml += "	<td>매장택배</td>";
							outrangeProdListHtml += "	<td><em class='txt-wbrown'>"+prodList[i].DELI_TERM_NM+"</em></td>";
							outrangeProdListHtml += "	<td>";
							outrangeProdListHtml += "		<div class='wrap-set-btn-incell'>";
							outrangeProdListHtml += "			<div class='set-btn-incell'>";
							if(prodList[i].PERI_DELI_YN  != 'N'){
							if(prodList[i].OPTION_YN != "Y"){
						        outrangeProdListHtml += "			<button type='button' class='btn-form-type3 layerpop-trigger2' data-trigger='layerpop-basket' onclick='changeItem(this,\""+prodList[i].PERI_DELI_PROD_SEQ+"\",\""+prodList[i].PROD_CD+"\",\""+prodList[i].ITEM_CD+"\");' title='레이어팝업 열림'>상품수량 변경</button>";
							}else{
							     outrangeProdListHtml += "             <button title='레이어팝업 열림' class='btn-form-type3 layerpop-trigger2' id='managePeri_" + "out" + "_" + prodList[i].PROD_CD + "' onclick='managePeriCart.add(this);' type='button'  data-category-id='" + prodList[i].CATEGORY_ID+ "' data-prod-cd='" + prodList[i].PROD_CD + "' data-prod-type-cd='"+ prodList[i].ONLINE_PROD_TYPE_CD +"' data-option-yn='"+prodList[i].OPTION_YN+"' data-gubun='"+prodList[i].GUBUN+"' data-item-cd='"+prodList[i].ITEM_CD+"'  data-str-cd='" + prodList[i].STR_CD+ "'  data-zip-seq='" + prodList[i].RECP_ZIP_SEQ+ "'  data-peri-deli-prod-seq='" + prodList[i].PERI_DELI_PROD_SEQ + "'  data-min-qty='" + prodList[i].MIN_ORD_PSBT_QTY + "' data-max-qty='" + prodList[i].MAX_ORD_PSBT_QTY + "' data-btn-obj-id='managePeri_" + "cancel" + "_" + prodList[i].PROD_CD + "' data-call-back-func='addPeriBasketCallback' disabled>상품수량 변경</button>";
							}
							}
							outrangeProdListHtml += "				<button type='button' class='btn-form-type2' onclick='cxlPeriItem(\""+prodList[i].PERI_DELI_ID+"\",\""+prodList[i].PERI_DELI_PROD_SEQ+"\");' >정기배송 전체취소</button>";
							outrangeProdListHtml += "			</div>";
							outrangeProdListHtml += "		</div>";
							outrangeProdListHtml += "	</td>";
							outrangeProdListHtml += "</tr>";
						}						
						
						$("#storeProdList").html(storeProdListHtml);
						if(storeProdListHtml != ""){
							$("#NprodCnt").text(nCnt);
							$("#storeItemBox").show();
						}
						$("#onlineProdList").html(onlineProdListHtml);
						if(onlineProdListHtml != ""){
							$("#VprodCnt").text(vCnt);
							$("#onlineItemBox").show();
						}
						$("#outrangeProdList").html(outrangeProdListHtml);
						if(outrangeProdListHtml != ""){
							$("#MprodCnt").text(mCnt);
							$("#outrangeItemBox").show();
						}
					}
				}
				
//				//기본정보 =========================================================
//				
//				//img 태그 사용 시 src값 결정
//				jRender.getImgSrc = function(imgCode) {
//					return getImgPath(imgCode,'60');
//				};
//				jRender.tplModify = function(tpl, obj){
//					if(!obj.BRAND_NM) $('.BRAND_NM', tpl).addClass('hide');
//					if(obj.OPTION_YN=='N') $('*.prod-item-detail', tpl).addClass('hide');
//					if(obj.OPTION_NM==null) $('*.prod-item-detail', tpl).addClass('hide');
//				}
//				if(!parseInt(data.prodList)) $('#totalProdCnt').html(data.prodList.length);
//				$('#storeItemBox em.prodCnt').html(jRender.size());
//				//상품목록 =========================================================
//				var filter = {'DELI_TYPE':'01'};	//매장배송
//				jRender.init('storeProdList', 'prodListTpl', data.prodList, true, filter);
//				if(jRender.size() > 0){
//					jRender.print();	//목록
//					$('#storeItemBox em.prodCnt').html(jRender.size());
//					$('#storeItemBox').removeClass('hide');
//				}else $('#storeItemBox').addClass('hide');
//				
//				filter = {'DELI_TYPE':'04'}; //택배배송
//				jRender.init('onlineProdList', 'prodListTpl', data.prodList, true, filter);
//				if(jRender.size() > 0){
//					jRender.print();	//목록
//					$('#onlineItemBox em.prodCnt').html(jRender.size());
//					$('#onlineItemBox').removeClass('hide');
//				}else $('#onlineItemBox').addClass('hide');
//				
//				filter = {'DELI_TYPE':'02'}; //특정점
//				jRender.init('outrangeProdList', 'prodListTpl', data.prodList, true, filter);
//				if(jRender.size() > 0){
//					jRender.print();	//목록
//					$('#outrangeItemBox em.prodCnt').html(jRender.size());
//					$('#outrangeItemBox').removeClass('hide');
//				}else $('#outrangeItemBox').addClass('hide');
			}
		});
}

function prodDetailLink(obj, event) {
	var onclick = $(obj).closest('article').find('.prod-name a').attr('onclick'); //onclick으로 가져올때
	var section = $(obj).find('section').length;
	// location 링크 goProductDetail 로 가져올수 있도록 해야함
	if (!$(event.target).attr('href')) {
		if (onclick != null && onclick.length > 0) {
			if(section == '0'){
				var onclickArr = onclick.split(';');
				eval(onclickArr[0]); // onclick
			}
		}
	}
}

function resetSchd(){
	$('#periInfo').empty();
	$('#storeItemBox').addClass('hide');
	$('#onlineItemBox').addClass('hide');
	$('#outrangeItemBox').addClass('hide');
}

//function cxlPeriItem(obj){
//	if(!confirm('취소하시겠습니까?')){
//		return;
//	}
//	
//	var params = jParam(obj);
//	$.getJSON("/mymart/api/cancelPeriItem.do", params)
//		.done(function(data) {
//			if(jResult(data)){
//				periDetail();
//			}
//		});
//}

function cxlPeriItem(periDeliId, periDeliProdSeq){
	var cProdCnt = $("#totalProdCnt").text();
	cProdCnt = cProdCnt.replace(/,/gi, "");
	cProdCnt = Number(cProdCnt);
	
	if(cProdCnt == 1){
		if(!confirm("해당상품을 취소하시면 전체 정기배송이 취소됩니다. 취소하시겠습니까?")){
			return;
		}else{
			cxlPeri();
		}
	}else{
	
	if(!periDeliId || !confirm('해당 상품을 정기배송 받지 않으시겠습니까?')){
		return;
	}
	var params = {
			periDeliId : periDeliId,
			periDeliProdSeq : periDeliProdSeq
	};
	$.getJSON("/mymart/api/cancelPeriItem.do", params)
		.done(function(data) {
			if(data.result == "true"){
				alert("상품이 삭제되었습니다.");
				periDetail();
			}
		});
	}
}


var exProdCd = "";
var exItemCd = "";
var exItemSeq = "";
function changeItem(current, itemSeq, prodCd, itemCd){
	layerpopTriggerBtn( current, event );
	var optionHtml = "";
	var params = {
			prodCd : prodCd
		  , itemCd : itemCd
	};
	exProdCd = prodCd;
	exItemCd = itemCd;
	exItemSeq = itemSeq;
	
	$.getJSON("/delivery/api/optionInfo.do", params)
	.done(function(data) {
		if (data.list != "") {
			optionHtml += "<option value=''>선택하세요</option>";
			$.each(data.list, function(){
				if(this.OPTION_CD == params.itemCd){
					$('#option_select').siblings('label').html(this.OPTION_NM);
					optionHtml += "<option value='" + this.OPTION_CD + "' selected='selected'>" + this.OPTION_NM + "</option>"; 
				}else{
					optionHtml += "<option value='" + this.OPTION_CD + "'>" + this.OPTION_NM + "</option>";
				}
			});
			$("#option_select").html(optionHtml);
		}else{
			$('#option_select').parents('dl').find('.option_select').hide();
		}
		$('#optItemCount').val(Number($('#ordQty' + prodCd).val()));
		$('#maxOtQty').val(Number($('#maxQty_' + prodCd).val()));
		$('#minOtQty').val(Number($('#minQty_' + prodCd).val()));
	});
}


//var confirmOption = function(obj) {
//	var params = jParam(obj);
//	$.getJSON("/mymart/api/updatePeriItem.do", params)
//	.done(function(data) {
//		if(jResult(data)){
//			alert('변경되었습니다.');
//			periDetail();
//		}
//	});
//};

function confirmOption(itemSeq, prodCd){
	
	var params = {
			itemCd : $("#itemCd_"+prodCd).val(), 
		    ordQty :	$('#ordQty'+prodCd).val(),
			periDeliId : $('input[name=periDeliId]').val(),
			periDeliProdSeq : itemSeq
	};	
	
	$.getJSON("/mymart/api/updatePeriItem.do", params)
	.done(function(data) {
		var item_max = parseInt($("#maxOtQty").val());
		var item_min = parseInt($("#minOtQty").val());
		var basketQty = $('#optItemCount');
		var ordQty = parseInt(basketQty.val());
		
		if(ordQty < item_min){
			alert("해당 상품 수량은 최소 "+ item_min +" 개 이상부터 가능합니다.");
			ordQty = item_min;
			basketQty.val(ordQty);
			return false;
		}			

		if(ordQty >item_max){
			alert("해당 상품 수량은 최대 "+ item_max +" 개까지 가능합니다.");
			ordQty = item_max;
			basketQty.val(ordQty);
			return false;
		}
		
		
		if(!!data && eval(data.result)){
			alert('변경되었습니다');
			periDetail();
		}
	});
}	

function createNextDaily(){
	$('#nextPeriDeliDay').html("");
	var today = new Date();
	//var todayStr = today.getFullYear()+'.'+('0'+(today.getMonth()+1)).slice(-2)+'.'+('0'+today.getDate()).slice(-2);
	//var targetDay = new Date(today.setMonth(today.getMonth()+parseInt($('input[name=useMonth]:checked').val())));
	//				targetDay.setDate(targetDay.getDate() - 1);
	//var targetDayStr = targetDay.getFullYear()+'.'+('0'+(targetDay.getMonth()+1)).slice(-2)+'.'+('0'+targetDay.getDate()).slice(-2);
	var firstDeliDay;
	var tmpDeliDay;
	var deliStartDy;
	var firstDeliDayStr;
	var totalDays = '';

	var stdComp = 99;//비교 기준
	if($('input[name^=deliDay]:checked').length > 0){
		$('input[name^=deliDay]:checked').each(function(){
			var comp = $(this).attr('day')-today.getDay();
			if(comp < 3) comp+=7;//비교대상 요일이 3일 이전일 경우 7일 앞으로 보정해준다
			if(comp < stdComp){//오늘부터 3일 이후에 해당하는 첫 요일을 세팅
				firstDeliDay = new Date();
				//tmpDeliDay = new Date("09/30/2016");
				//if(firstDeliDay < tmpDeliDay){
					if(comp > 2){
						firstDeliDay = new Date(firstDeliDay.setDate(firstDeliDay.getDate()));
					}else{
						firstDeliDay = new Date(firstDeliDay.setDate(firstDeliDay.getDate()+7));
					}
				//}

				firstDeliDay = new Date(firstDeliDay.setDate(firstDeliDay.getDate()+comp));
				deliStartDy = firstDeliDay.getFullYear()+('0'+(firstDeliDay.getMonth()+1)).slice(-2)+('0'+firstDeliDay.getDate()).slice(-2);
				firstDeliDayStr = firstDeliDay.getFullYear()+'.'+('0'+(firstDeliDay.getMonth()+1)).slice(-2)+'.'+('0'+firstDeliDay.getDate()).slice(-2)+' ('+$(this).attr('txt')+')';
			}
			
			if(comp>2){
				stdComp = comp;	//더 최근 날짜를 쓰기 위해 차이를 저장
			}
			
		});	
		$('#nextPeriDeliDay').html(firstDeliDayStr);
		$('#deliStartDy').val(deliStartDy);
	}
}

function setMonth(month){
	var start_date_str = $('#deli_start_dy').val();
	var start_yyyy = parseInt(start_date_str.substring(0,4)); 
	var start_mm = parseInt(start_date_str.substring(4,6));
	var start_dd = parseInt(start_date_str.substring(6,8));
	
	//ex)1월 29일 신청시, 연장 기간이 08월 00일로 끝나는 오류 발생하여 수정
	//var start_date = new Date(start_yyyy, start_mm, start_dd, 0, 0, 0);
	//var extMonth = parseInt($('#use_month').val())+parseInt(month);
	//start_date.setMonth(start_date.getMonth()+extMonth);
	var start_date_str2 = start_mm +"/"+start_dd+"/"+start_yyyy;
	var start_date = new Date(start_date_str2);
	var extMonth = parseInt($('#use_month').val())+parseInt(month);
	start_date.setMonth((start_date.getMonth()+1)+extMonth);
	
	var ext_yyyy = ""+start_date.getFullYear();
	var tmpmm = start_date.getMonth();
	if(tmpmm == 0){
		tmpmm = 12;	
	}
	var ext_mm = ("0"+tmpmm).slice(-2);
	var ext_dd = ("0"+(start_date.getDate()-1)).slice(-2);
	
	
	$('#useMonth').val(extMonth);
	
	//var dispStr = start_yyyy+'.'+start_mm+'.'+start_dd+' ~ '+ext_yyyy+'.'+ext_mm+'.'+ext_dd+"까지 연장";
	var dispStr = start_yyyy+'.'+start_date_str.substring(4,6)+'.'+start_date_str.substring(6,8)+' ~ '+ext_yyyy+'.'+ext_mm+'.'+ext_dd+"까지 연장";
	$('span.extend_deli_date').html(dispStr);
	$('#useMonthNmV').val(dispStr);
}

//page on load
$(function() {
	//선택된 정기배송코드 세팅
	periDeliId = $('input[name=periDeliId]').val();
	
	//기존 건 선택 처리
	if(periDeliId != ""){
		$('div.img-select-type2 > a').html($('a[periDeliId='+periDeliId+']').html());
	}
	
	// 메인페이지로
	$("#main_link").click(function(e) {
		window.location.href = "/delivery/main.do";
	});
	
	//기존 건 클릭
	$('ul.periAddr > li > a').on("click", function(){
		getExistingPeri($(this).attr('periDeliId'));
	});
	
	//옵션 버튼 클릭 - 공통과 구조가 달라 따로 설정
//	$('section.dynamic').on('click', 'button.optionView', function(){
//		layerpopTriggerBtn( this, event );
//		setOptionLayer(this);
//	});
	
//	$(document).on('click.f', '.layerpop-trigger2', function (event) {
//		 layerpopTriggerBtn( this, event );
//	});
	
	//수량UP

//$(document).on("click", 'button.sp-plus', function(){
  $(document).on("click", '#spPlus', function(){	
		var basketQty = $('#optItemCount');
		var ordQty = parseInt(basketQty.val());
		var limit = parseInt($("#maxOtQty").val());
		
		if(ordQty < limit){
			ordQty++;
		}else{
			alert("해당 상품 수량은 최대 "+ limit +" 개까지 가능합니다.");
			ordQty = limit;
		}
		basketQty.val(ordQty);
	});
	
	//수량DOWN
//$(document).on("click", 'button.sp-minus', function(){
  $(document).on("click", '#spMinus', function(){	
		var basketQty = $('#optItemCount');
		var ordQty = parseInt(basketQty.val());
		var limit = parseInt($("#minOtQty").val());
		
		if(ordQty > limit){
			ordQty--;
		}else{
			alert("해당 상품 수량은 최소 "+ limit +" 개 이상부터 가능합니다.");
			ordQty = limit;
		}			
		basketQty.val(ordQty);
	});	
	
	
	$(document).on('click', 'button.optionConfirm', function(){
		var optionItemCd  = $('#option_select option:selected').val();
		if(optionItemCd != undefined){
			$("#itemCd_"+exProdCd).val($('#option_select option:selected').val());
		}else{
			$("#itemCd_"+exProdCd).val(exItemCd);
		}
		$('#ordQty'+exProdCd).val($("#optItemCount").val());
		confirmOption(exItemSeq , exProdCd);
	});	
	
	/*
	$(document).on('change', '.select-type1 select', function (event) {
		var select_name = $(this).children('option:selected').text();
		$(this).siblings('label').text(select_name);
	});
	*/
	
	$('#optionTable').on('click', 'input[type=radio], input[type=checkbox]', function (event) {
		$(this).check(this.checked);
	});

	
//	$(document).on('click', 'button.cxlPeriItem', function(){
//		cxlPeriItem(this);
//	});
	
	$(document).on('change', '#deliTermSelect', function(){
		if($('#deliTermSelect option:selected').val() == "30"){
			$(".closeday").hide();
			$("input[name^=deliDay]").parent().addClass("active");
			$("input[name^=deliDay]").prop("checked",true);
		}else{
			$(".closeday").show();
			$("input[name^=deliDay]").parent().removeClass("active");
			$("input[name^=deliDay]").prop("checked",false);
		}
		createNextDaily();
	});
	
	$(document).on('click', 'input[name^=deliDay]', function(){
		if($('#deliTermSelect option:selected').val() == "30"){
			alert("상품주기를 매일로 선택하셨습니다. \n상품주기를 먼저 변경해주세요.");
			$("input[name^=deliDay]").parent().addClass("active");
			$("input[name^=deliDay]").prop("checked",true);
			return;
		}
		createNextDaily();
	});	
	
	periDetail();
});

function addPeriBasketCallback(obj, event){	
	
	var params = {			
			itemCd : $(obj).data("periBasketItem").itemCd, 
		    ordQty : $(obj).data("periBasketItem").bsketQty,		    
			periDeliId : $('input[name=periDeliId]').val(),
			periDeliProdSeq : $(obj).data("periBasketItem").periDeliProdSeq,
			nfomlVariation : ''
	};			

	if($(obj).data("periBasketItem").nfomlVariation != null && $(obj).data("periBasketItem").nfomlVariation != '' &&
	   $(obj).data("periBasketItem").nfomlVariation != "undefined"){	   	
		params.nfomlVariation = $(obj).data("periBasketItem").nfomlVariation; 
	}	
		
	$.ajax({
		type: "POST",
		cache: false,
		url: "/mymart/api/updatePeriItem.do",
		data: params,
		dataType: "json",
		success: function(data) {
			if(!!data && eval(data.result)){
				alert('변경되었습니다.');
				periDetail();
			}
		}
	});	
	
	$("#opt_peri_cart").remove();
	
}	
