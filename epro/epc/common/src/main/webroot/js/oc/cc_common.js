
/*
 * 사용예)
 * generateCommonCodeOptionTag("QA001", "#typeBig", "전체01", "005");
 * 
 *  commonMajorCode : MAJOR_CD
 *  toSelectTagID : 대상 select tag ID를 넣으면 된다  
 *                   <select id="typeBig"> #typeBig가 된다
 *  firstOptionMessage : 콤보박스 첫 번째를 임의로 집어넣을 데이타..
 *                       안넣으려면 "none" or null or "null"을 넣어주면 된다
 *  selectViewCode     : 최초 선택된 값이 필요시 코드값 입력.(optional)
 */
 
function generateCommonCodeOptionTag(commonMajorCode, toSelectTagID, firstOptionMessage, selectViewCode) {
	$.post(
			urlPrefix+"CommonCodeHelperController/generateOptionTag.do",
			{"selectedCode": commonMajorCode,
			 "selectViewCode": selectViewCode 			
			},
			function(data){
				var json = jsonParseCheck(data);
				if(json==null)
				{
					return;
				}
				$(toSelectTagID + " option").remove();
				if(firstOptionMessage == "none" || firstOptionMessage == null || firstOptionMessage == "null")
				{
					$(toSelectTagID).append(json);
				}
				else
				{
					$(toSelectTagID).append("<option value=''>" + firstOptionMessage + "</option>").append(json);
				}
			},		
			"text"
		);
}


/*
 * 생성일 : 2011-10.04
 * 
 * 사용예)
 * generateCommonCodeOptionTagVer2("QA001", "#typeBig", "전체01", MethodA);
 * 
 * function MethodA() {
 * 	// do something..
 * }
 * 
 *  commonMajorCode : MAJOR_CD
 *  toSelectTagID : 대상 select tag ID를 넣으면 된다  
 *                   <select id="typeBig"> #typeBig가 된다
 *  firstOptionMessage : 콤보박스 첫 번째를 임의로 집어넣을 데이타..
 *                       안넣으려면 "none" or null or "null"을 넣어주면 된다
 *  selectViewCode     : 최초 선택된 값이 필요시 코드값 입력.
 *  finallyMethod	   : 함수 호출 후 맨 마지막에 콜백할 함수(optional)
 */
 
function generateCommonCodeOptionTagVer2(commonMajorCode, toSelectTagID, firstOptionMessage, finallyMethod) {
	$.post(
			urlPrefix+"CommonCodeHelperController/generateOptionTag.do",
			{"selectedCode": commonMajorCode
			},
			function(data){
				var json = jsonParseCheck(data);
				if(json==null)
				{
					return;
				}
				$(toSelectTagID + " option").remove();
				if(firstOptionMessage == "none" || firstOptionMessage == null || firstOptionMessage == "null")
				{
					$(toSelectTagID).append(json);
				}
				else
				{
					$(toSelectTagID).append("<option value=''>" + firstOptionMessage + "</option>").append(json);
				}
				if(finallyMethod != null)
				{
					finallyMethod();
				}
			},		
			"text"
		);
}

/*
 * 사용예)
 * var retCD = getCommonCodeColumn("QA001", "200", "LET_1_REF");
 * generateCommonCodeOptionTag(retCD, "#typeSmall", "문의유형(소)");
 * 
 * 설명)MAJOR_CD 가 "QA001" 이고 MINOR_CD 가 "200"인 결과행에서 "LET_1_REF" 컬럼 값을 가져온다
 * 
 * majorCD : MAJOR_CD
 * minorCD : MINOR_CD
 * columnNM : 얻고자 하는 컬럼 이름, 예) "LET_1_REF"
 * 
 */

function getCommonCodeColumn(majorCD, minorCD, columnNM) {
	var retVal = "dummy";
		$.ajax({ 
			type : "POST",
			async : false,
			url : urlPrefix+"CommonCodeHelperController/getCommonCodeColumn.do",
			dataType : "json",
			timeout : 5000,
			cache : true,
			data : {
				"majorCD":majorCD,
				"minorCD":minorCD,
				"columnNM":columnNM
			},
		      success: function(data){ 
		    	  retVal = data.RETURN_VALUE;
		      } 
		   } 
	);
	if(retVal == "dummy")
		return "error";
	return retVal;
}

// 크로스도메인문제 해결을 위해
//document.domain="lottemart.com";

function onlyNumber() {
    var code = window.event.keyCode;
    if (((96<=code) && (code<=105))
          || ((48<=code) && (code<=57))
          || (code==8)
          || (code==37)
          || (code==39)
          || (code==9)){

         window.event.returnValue=true;
    } else {
         window.event.returnValue=false;
    }
 }

/**
@ 자리수 체크후 자동 포커스 이동함수 - 텍스트박스로 이동시
예) onKeyUp="return moveNextFocus(this, this.size, obj_name);"
*/
function moveNextFocus(basicCtrlName,strMaxNumber,targetCtrlName){

if((basicCtrlName.value.length==strMaxNumber && event.keyCode != 37 && event.keyCode != 39) || (event.keyCode == 13)){
	targetCtrlName.select();
	return;
}

}
