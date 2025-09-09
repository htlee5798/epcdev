/*******************************************************************************
 * 신상품 등록 validation 체크
 ******************************************************************************/

		function selectTargetObject(currentObj) {
			
			var tmpObject = currentObj;
			var currentNodeTagName = currentObj.parent().get(0).nodeName.toLowerCase();
			if(currentNodeTagName  == 'div') {
				tmpObject = currentObj.parent();
			}
			return tmpObject;
		}
	

		function showErrorMessage(currentObject) {

			var targetObject = selectTargetObject(currentObject); 
			
			var fieldHeaderText = targetObject.parent().prev().text().replace("*","");
			var errorNode = targetObject.prev("div[name=error_msg]").length;
			var fullErrorMessage = getTextMessageWithArgu(fieldHeaderText);
			
			if(currentObject.hasClass("number")) {

				fullErrorMessage = getNumberMessageWithArgu(fieldHeaderText);

				var currentObjectValue = currentObject.val();
				
				if(currentObjectValue == "0")
					fullErrorMessage = fieldHeaderText+"에는 0보다 큰 값을 입력해주시기 바랍니다.";
		
				if( currentObject.hasClass("range") &&
						( currentObjectValue != '' && parseInt(currentObjectValue) > 0) ) {
					if( currentObject.hasClass("cost") ) {
						fullErrorMessage = "원가가 매가 보다 값이 클수 없습니다.";
					}
					if( currentObject.hasClass("maximum") ) {
						fullErrorMessage = "최소 주문량이 최대 주문량 보다 클수 없습니다.";
					}
				}
				
				if( currentObject.hasClass("vicrange") &&
						( currentObjectValue != '' && parseInt(currentObjectValue) > 0) ) {
					if( currentObject.hasClass("viccost") ) {
						fullErrorMessage = "VIC 원가가  VIC 매가 보다 값이 클수 없습니다.";
					}
					
				}
				
				if( currentObject.hasClass("superrange") &&
						( currentObjectValue != '' && parseInt(currentObjectValue) > 0) ) {
					if( currentObject.hasClass("supercost") ) {
						fullErrorMessage = "슈퍼 원가가 슈퍼 매가 보다 값이 클수 없습니다.";
					}
					
				}
				
				if( currentObject.hasClass("ocarange") &&
						( currentObjectValue != '' && parseInt(currentObjectValue) > 0) ) {
					if( currentObject.hasClass("ocacost") ) {
						fullErrorMessage = "CFC 원가가 CFC 매가 보다 값이 클수 없습니다.";
					}
					
				}
			}
			
			if( currentObject.hasClass("hscd")) {
				if(!checkInteger(currentObject.val()) || currentObject.val().length != 10) {
					fullErrorMessage = "HS코드는 10자리 숫자로 입력해야 합니다.";
				}				
			}
			
			if( currentObject.hasClass("decno")) {
				if(!checkDecNo(currentObject.val())) {
					fullErrorMessage = "13자리 숫자+1자리 영문자로 입력하세요.";
				}				
			}
			
			if( errorNode == 0 ) { 
				$("<div name=\"error_msg\" style=\"color:red;\">"
					+fullErrorMessage+
				 "</div>")
				 .insertBefore(targetObject);
			}else{
				$(targetObject).parent().find("div[name=error_msg]").text(fullErrorMessage);
			}
		}

		function deleteErrorMessageIfExist(currentObject) {

			if(!currentObject.hasClass("userValueValidate")) {

				var targetObject = selectTargetObject(currentObject); 
				
				var errorNode = targetObject.prev("div[name=error_msg]").length;
				if( errorNode > 0 ) { 
					targetObject.prev().remove();
				}
			
			}
	
		}
	
		function validateSelectBox(selectBoxObj) {
		
			if( selectBoxObj.val() == '' ||
					selectBoxObj.val() == 'all') {
			
				showErrorMessage(selectBoxObj);
			} else {
			
				deleteErrorMessageIfExist(selectBoxObj);
			}
		}
	
		function checkRangeValue(fieldObj) {
			var resultFlag = "success";
	
			if(fieldObj.hasClass("cost")) 
			{
				var price = parseInt($(".price").val());
				var cost  = parseInt($(".cost").val());
				
				if( cost >= price ) {
					resultFlag = "cost";
					
				} 
			}
			if(fieldObj.hasClass("viccost")) 
			{
				
				var vicprice = parseInt($(".vicprice").val());
				var viccost  = parseInt($(".viccost").val());

				if( viccost >= vicprice ) {
					resultFlag = "viccost";
				} 
			}
			if(fieldObj.hasClass("supercost")) 
			{
				
				var superprice = parseInt($(".superprice").val());
				var supercost  = parseInt($(".supercost").val());

				if( supercost >= superprice ) {
					resultFlag = "supercost";
				} 
			}
			if(fieldObj.hasClass("ocacost")) 
			{
				
				var ocaprice = parseInt($(".ocaprice").val());
				var ocacost  = parseInt($(".ocacost").val());

				if( ocacost >= ocaprice ) {
					resultFlag = "ocacost";
				} 
			}
			
			
			if(fieldObj.hasClass("digit13")) 
			{

				var sellcddigit = $(".digit13").val();

				
				if(sellcddigit.length == 13){
					
				}else{
					alert("판매코드는 13자리입니다.");
					
					resultFlag = "digit13";
				}
		

			}
			
			if(fieldObj.hasClass("maximum")) 
			{
				var minimum = parseInt($(".minimum").val());
				var maximum  = parseInt($(".maximum").val());
				
				if( minimum >= maximum ) {
					
					resultFlag = "maximum";
				} 
			}
			
			return resultFlag;
		}
		
		function validateTextField(textFieldObj) {
		
			var currentNodeValue = textFieldObj.val();
			var limit_char = /[~!\#$^&*\=+|:;?"<,>']/;
			
		
			if(textFieldObj.hasClass("number")) {
				if( !checkInteger(currentNodeValue) ) {

					showErrorMessage(textFieldObj);
				
		
				} else if( parseInt(currentNodeValue) == 0 ) {
						
					showErrorMessage(textFieldObj);
					
					
				} else {
			

					if( textFieldObj.hasClass("range") && checkRangeValue(textFieldObj) != "success"	) {
							showErrorMessage(textFieldObj);
					} else if(textFieldObj.hasClass("vicrange") && checkRangeValue(textFieldObj) != "success")	{						
						showErrorMessage(textFieldObj);
					} else if(textFieldObj.hasClass("superrange") && checkRangeValue(textFieldObj) != "success")	{						
						showErrorMessage(textFieldObj);
					} else if(textFieldObj.hasClass("ocarange") && checkRangeValue(textFieldObj) != "success")	{						
						showErrorMessage(textFieldObj);
					} else {					
						deleteErrorMessageIfExist(textFieldObj);
					}
				}
				
			//비율, 소수점을 포함한 숫자를 입력한 경우	
			} else if(textFieldObj.hasClass("rate")) {
			//	alert("leedb1");
				if( !checkMinus(currentNodeValue) ) {
					showErrorMessage(textFieldObj);
				} else {
					deleteErrorMessageIfExist(textFieldObj);
				}
			//비율, 소수점을 포함한 숫자를 입력한 경우	
			} else if(textFieldObj.hasClass("vicrate")) {
				if( !checkMinus(currentNodeValue) ) {
					showErrorMessage(textFieldObj);
				} else {
					deleteErrorMessageIfExist(textFieldObj);
				}
				
			//비율, 소수점을 포함한 숫자를 입력한 경우	(슈퍼)
			} else if(textFieldObj.hasClass("superrate")) {
				if( !checkMinus(currentNodeValue) ) {
					showErrorMessage(textFieldObj);
				} else {
					deleteErrorMessageIfExist(textFieldObj);
				}
				
			//비율, 소수점을 포함한 숫자를 입력한 경우	(오카도)
			} else if(textFieldObj.hasClass("ocarate")) {
				if( !checkMinus(currentNodeValue) ) {
					showErrorMessage(textFieldObj);
				} else {
					deleteErrorMessageIfExist(textFieldObj);
				}
				
			} else if( textFieldObj.hasClass("hscd")) { 
				if(!checkInteger(currentNodeValue) || textFieldObj.val().length != 10){
					showErrorMessage(textFieldObj);		
				}
				else {
					deleteErrorMessageIfExist(textFieldObj);
				}
				
			} else if( textFieldObj.hasClass("decno")) { 
				if(!checkDecNo(currentNodeValue)){
					showErrorMessage(textFieldObj);		
				}
				else {
					deleteErrorMessageIfExist(textFieldObj);
				}
				
			//숫자가 아닌 일반 문자를 입력한 경우	
			} else  {
				
				if( !checkText(currentNodeValue) ) {
					showErrorMessage(textFieldObj);
				} 
		
				else  {
					deleteErrorMessageIfExist(textFieldObj);
				}
				  
			}
		}
	
		function validateRadioField(jqueryObj) {
			var currentNodeName = jqueryObj.attr("name");
			var errorDiv = jqueryObj.parent().prev("div[name=error_msg]").length;
			
			if( $("input[name="+currentNodeName+"]:checked").length == 0 ) {
					
				showErrorMessage(jqueryObj);
			} else {
				$("#"+currentNodeName).val($("input[name="+currentNodeName+"]:checked").val());
				deleteErrorMessageIfExist(jqueryObj);
			}
	
		}
	
		function validateCommon() {
			var validateFlag = true;
			var focusObj = null;
			
			$(".required").each(function(i) {
				if(!$(this).attr("disabled")) {
					var currentNodeTagName = $(this).get(0).nodeName.toLowerCase();
					var errorNode = $(this).prev("div[name=error_msg]").length;
					var currentNodeValue = $(this).val();
					//console.log("currentNodeValue:"+currentNodeValue);
					switch(currentNodeTagName) {
						case "select" :
							if( currentNodeValue == '' ||
									currentNodeValue == 'all' ) {
								showErrorMessage($(this));
								validateFlag = false;
								focusObj = (focusObj == null)?$(this):focusObj;
							} else {
								deleteErrorMessageIfExist($(this));
							}
						break;
						default :
							//input text박스인 경우
							if($(this).attr("type") == 'text') {

								//validateTextField($(this));
								
								if($(this).hasClass("number")) {
									if( !checkNumber($(this).val()) ) {
										showErrorMessage($(this));
										validateFlag = false;
										focusObj = (focusObj == null)?$(this):focusObj;
									} else if( parseInt($(this).val()) == 0 ) {
										showErrorMessage($(this));
										validateFlag = false;
										focusObj = (focusObj == null)?$(this):focusObj;
									} else {
										//비교값 체크(예: 최대, 최소 값 비교 혹은 상품의 원가 매가 금액 비교
										
										if( 
											(
												$(this).hasClass("range") ||
												$(this).hasClass("vicrange") ||
												$(this).hasClass("superrange") ||
												$(this).hasClass("ocarange")
												
											) && checkRangeValue($(this))  != "success"	) {
											
											showErrorMessage($(this));
											validateFlag = false;
											focusObj = (focusObj == null)?$(this):focusObj;
										} else {
											deleteErrorMessageIfExist($(this));
										}
										//deleteErrorMessageIfExist($(this));
									}
								} else if($(this).hasClass("rate")) {
									
								//	alert("test1");
									
									if( !checkMinus($(this).val())) {
										showErrorMessage($(this));
										validateFlag = false;
										focusObj = (focusObj == null)?$(this):focusObj;
									} else {
										deleteErrorMessageIfExist($(this));
									}
									
									//이동빈추가시작
								} else if($(this).hasClass("vicrate")) {
									if( !checkMinus($(this).val()) ) {
										showErrorMessage($(this));
										validateFlag = false;
										focusObj = (focusObj == null)?$(this):focusObj;
									} else {
										deleteErrorMessageIfExist($(this));
									}
									//이동빈추가끝
									
								} else if($(this).hasClass("superrate")) {	//슈퍼
									if( !checkMinus($(this).val()) ) {
										showErrorMessage($(this));
										validateFlag = false;
										focusObj = (focusObj == null)?$(this):focusObj;
									} else {
										deleteErrorMessageIfExist($(this));
									}
									
								} else if($(this).hasClass("ocarate")) {	//오카도
									if( !checkMinus($(this).val()) ) {
										showErrorMessage($(this));
										validateFlag = false;
										focusObj = (focusObj == null)?$(this):focusObj;
									} else {
										deleteErrorMessageIfExist($(this));
									}
									
								} else if($(this).hasClass("digit13")) {
									if( !checkMinus($(this).val()) ) {
										showErrorMessage($(this));
										validateFlag = false;
										focusObj = (focusObj == null)?$(this):focusObj;
									} else {
										deleteErrorMessageIfExist($(this));
									}
									
								} else {
									if( !checkText($(this).val()) ) {
										showErrorMessage($(this));
										validateFlag = false;
										focusObj = (focusObj == null)?$(this):focusObj;
									} else {
										deleteErrorMessageIfExist($(this));
									}
								}
								
							//radio 버튼인 경우	
							} else {
								var currentNodeName = $(this).attr("name");
								var errorDiv = $(this).parent().prev("div[name=error_msg]").length;
								if( $("input[name="+currentNodeName+"]:checked").length == 0 ) {
										showErrorMessage($(this));
										validateFlag = false;
										focusObj = (focusObj == null)?$(this):focusObj;
								} else {
									deleteErrorMessageIfExist($(this));
								}
							}
						break;	
					}
				}
				//console.log("validateFlag:"+validateFlag);
			});
			
			if(focusObj != null){
				focusObj.focus();
			}
			return validateFlag;
		}
	
		function checkMinus(checkValue) {
			var pattern = /^\-?[0-9]+(.[0-9]+)?$/;
	    	return pattern.test(checkValue) ? true : false;
		}
		
		function checkNumber(checkValue) {
			//alert("11");
			var pattern = /^[0-9]+(.[0-9]+)?$/;
			//alert(pattern.test(checkValue));
	    	return  pattern.test(checkValue) ? true : false;
		}
	
		function checkInteger(checkValue) {
			var pattern = /^[0-9]+$/;
	    	return pattern.test(checkValue) ? true : false;
		}
		
		function checkDecNo(checkValue) {
			var pattern = /([0-9]){13}([A-Z,a-z]){1}/;
	    	return pattern.test(checkValue) ? true : false;
		}
		
		function checkText(checkValue) {
			var trimmedValue = checkValue.replace(/^\s*(\S*(\s+\S+)*)\s*$/, "$1");
			//alert(trimmedValue);
			
			if( checkValue == null || trimmedValue == ''  ) {
	    		return  false;
			} else {
		    	return  true;
			}
		}
		
		function isThisValidSell88Code(sellCodeElement) {
			var sell88CodeResult = true;
			if (sellCodeElement.value != "" )/*88 상품 코리안넷으로 등록*/
			{
			

				
			}
		var ls_chk_dgt, li_cnt, lc_dgt, ll_sum, ll_buf , a_srcmk_cd_str
		ll_sum = 0

			if (sellCodeElement.value == ""){}
				else{
				if ((sellCodeElement.value.length) == 12) {
					a_srcmk_cd_str = "0" + sellCodeElement.value; 
					/*
					alert("판매코드는 13자리로 입력하셔야 합니다.");
					a_srcmk_cd.value = ""
					a_srcmk_cd.focus();
					*/
				}
				else{
				a_srcmk_cd_str = sellCodeElement.value;
				}
				//else{
					var lc_dgt_str
						lc_dgt_str = a_srcmk_cd_str.substring(0,12)
					
					for (li_cnt=1; li_cnt < 13; li_cnt++)
					{
						lc_dgt = lc_dgt_str.substring(li_cnt-1, li_cnt)
						
							if ((li_cnt)%2 == 1){
								ll_sum = ll_sum + parseInt(lc_dgt)}
							else{
								ll_sum = ll_sum + parseInt(lc_dgt) * 3
							}
					}
					

					ll_buf = parseInt(ll_sum) / 10
					ll_buf = (parseInt(ll_buf) + 1) * 10
					ls_chk_dgt = (parseInt(ll_buf) - parseInt(ll_sum))%10
					
				//}

				if (ls_chk_dgt != a_srcmk_cd_str.substring(12,13) ) {
				//alert("잘못된 판매코드를 입력하셨습니다.");
					//sellCodeElement.value= ""
					//sellCodeElement.focus();
					sell88CodeResult = false;
				}
				else {
				  //document.Myform.submit();
				}
			}
		
			return sell88CodeResult;
		}
		
		


		//
		//   입력 문자열 바이트 체크
		//   Obj, VMax
		function calByteProd(Obj, VMax, ObjName, nets)
		{ 
			var tmpStr;
			var temp=0;
			var onechar;
			var tcount;
			tcount = 0;
			var aquery = Obj.value
			
			tmpStr = new String(aquery);
			temp = tmpStr.length;

			for (k=0;k<temp;k++)
			{
				onechar = tmpStr.charAt(k);

				if (escape(onechar).length > 4)
				{
					tcount += 2;
				}
				else tcount ++;
				
				/*
				else if (onechar!='\r')
				{
					tcount++;
				}onkeyup
				*/		
			}
			
			if(tcount>VMax)
			{
				reserve = tcount-VMax;
				if(nets){
					
					alert(ObjName+"은(는) "+VMax+"바이트 이상은 전송하실수 없습니다.\r\n 쓰신 메세지는 "+reserve+"바이트가 초과되었습니다.\r\n 초과된 부분은 자동으로 삭제됩니다."); 
					netsCheckProd(Obj, VMax);
				}
				else{
					alert(ObjName+"은(는) "+VMax+"바이트 이상은 전송하실수 없습니다.\r\n 쓰신 메세지는 "+reserve+"바이트가 초과되었습니다."); 
					Obj.focus();
					Obj.value = aquery;
				}
				return false;
			}
			return true;
		}


		function netsCheckProd(Obj,lsMax)
		{
			var tmpStr;
			var temp=0;
			var onechar;
			var tcount;
			tcount = 0;

			var aquery = Obj.value;

			tmpStr = new String(aquery);
			temp = tmpStr.length;

			for(k=0;k<temp;k++)
			{
				onechar = tmpStr.charAt(k);
				
				if(escape(onechar).length > 4)
				{
					tcount += 2;
				}
				else tcount++;
				/*
				else if(onechar!='\r')
				{
					tcount++;
				}
				*/
				if(tcount>lsMax)
				{
					tmpStr = tmpStr.substring(0,k-1); 
					break;
				}
			}
			Obj.focus();
			Obj.value = tmpStr;
		}

/*    신상품 등록 validation 끝  //      */