
$.getGridPhoneExNo=function(strRoot,objGrid){
	for(var k=0;k<objGrid.GetRowCount();k++){
		if(objGrid.GetCellValue('PHONE_EX_CD',k)){
			$.ajax({
				url:strRoot+'/POCMAGT0036/selectPhoneExNo.do',
				data:'serialNo='+objGrid.GetCellValue('PHONE_EX_CD',k)+'&row='+k,
				dataType:'json',
				success:function(data){
					if(!data || !data.data || typeof(data.data[0].PHONE_EX_NO_1)=='undefined' || typeof(data.row)=='undefined') return false;
					strPhoneExNo='';
					for(i=1;i<10;i++) if( eval('data.data[0].PHONE_EX_NO_'+i) ) for(i=1;i<10;i++) if( eval('data.data[0].PHONE_EX_NO_'+i) ) strPhoneExNo+=', '+eval('data.data[0].PHONE_EX_NO_'+i);
					objGrid.SetCellValue("PHONE_EX_NO",data.row,strPhoneExNo.substr(2));
				}
			}); // $.ajax
		}
	} // for
};

$.popupImage=function(strRoot,strUrl){
	window.open(strRoot+'/image.jsp?url='+strUrl,'ocimage','scrollbars=no,width=200,height=200');
};

$.getPhoneExNo=function(strRoot,ofcCd,phoneExCd){
	$.ajax({
		url:strRoot+'/POCMAGT0036/selectPhoneExNo.do',
		data:'ofcCd='+ofcCd+'&serialNo='+phoneExCd,
		dataType:'json',
		success:function(data){
			if(!data.data || data.data=='' || data.data.length<1) return false;
			strPhoneExNo='';
			for(i=1;i<10;i++) if( eval('data.data[0].PHONE_EX_NO_'+i) ) strPhoneExNo+=','+eval('data.data[0].PHONE_EX_NO_'+i);
			$('input[name="phoneExNo"]').val(strPhoneExNo.substr(1));
		}
	}); // $.ajax
};

$.getDateFormat=function(strDate){
	return strDate.substr(0,4)+'-'+strDate.substr(4,2)+'-'+strDate.substr(6);
};

var aryDays=new Array('일','월','화','수','목','금','토');
var aryDaysColor=new Array('','#BB0000','#005599','#FFBB00','#7700EE','','','','','#999999');
var strColorNotCurrent='#999999';

$.setCalendar=function(intYear,intMonth){
	var dateCal=new Date(intYear,intMonth-1,1);
	if(dateCal.getDay()==0) dateCal.setDate(-6);
	else dateCal.setDate(-dateCal.getDay()+1);
	var intLastDay=new Date(intYear,intMonth,0).getDate();

	var htmlTr='<tr valign="top" height="69">';
	var html='<table cellpadding="0" cellspacing="0" border="0" width="100%" style="border:1px #CCCCCC solid;border-top:0;border-left:0" class="bbs_search"><tr>';
	for(i=0;i<7;i++) html+='<th>'+aryDays[i]+'</th>';
	html+='</tr>'+htmlTr;
	for(i=0;i<42;i++){
		html+='<td style="border:1px #CCCCCC solid;border-bottom:0;border-right:0;padding:3px 6px 0 5px">';
		if(dateCal.getYear()==dateToday.getYear() && dateCal.getMonth()==dateToday.getMonth() && dateCal.getDate()==dateToday.getDate()) html+='<b>';
		if(intMonth-1!=dateCal.getMonth()) html+='<font color="'+strColorNotCurrent+'">';
		else if(((i+1)%7)==1) html+='<font color="'+aryDaysColor[1]+'">';
		else if(((i+1)%7)==0) html+='<font color="'+aryDaysColor[2]+'">';

		if(dateCal.getDate()<10) strId='date0'+dateCal.getDate();
		else strId='date'+dateCal.getDate();

		html+=dateCal.getDate();
		html+='</font></b>&nbsp;<font id="'+strId+'Icon"></font>';
		html+='<font style="float:right" id="'+strId+'Time"></font><br/>';
		if(intMonth-1==dateCal.getMonth()) html+='<div id="'+strId+'" style="padding-left:16px"></div>';
		html+='</td>';
		dateCal.setDate(dateCal.getDate()+1);
		//if(intMonth-1!=dateCal.getMonth() && !((i+1)%7)) break;
		if(!((i+1)%7)) html+='</tr>'+htmlTr;
	}
	html+='</tr>';
	html+='</table>';
	$('#calendar').html(html);
};

$.maxlength=function(obj,intLength){
	obj.bind('keydown',function(event){
		if(event.target.value.length>=intLength){
			return ( event.which==8 || event.which==46 );
		}
	});
};

$.preventNumber=function(obj){
	obj.keypress(function(){
		if((event.keyCode<48)||(event.keyCode>57)) event.returnValue=false;
	});};

$.preventNumberHyphen=function(obj){
	obj.keypress(function(){
		if(event.keyCode!=45 && (event.keyCode<48)||(event.keyCode>57)) event.returnValue=false;
	});};

$.notnull=function(obj,strName){
	if(!obj.val()){
		alert(strName+"을(를) 입력하세요!");
		obj.focus();
		return false;
	}
	return true;
};

// createSelectOptionPhoneExCd(셀렉트박스아이디,지점코드,사무실코드);
// createSelectOptionPhoneExCd('phoneExCd','301','00005');
function createSelectOptionPhoneExCd(toSelectTagID,strCd,ofcCd,selectedCd){
	$.post(
		urlPrefix+"OCPhoneExCdController/createSelectOptionPhoneExCd.do",{'strCd':strCd,'ofcCd':ofcCd,'selectedCd':selectedCd},
		function(data){
			var json=jsonParseCheck(data);
			$('#'+toSelectTagID+" option").remove();
			$('#'+toSelectTagID).append('<option value="">선택</option>');
			if(json==null) return;
			$('#'+toSelectTagID).append(json);
		},
		'text'
	);
}

//createSelectOptionFloorVal(셀렉트박스아이디,지점코드);
function createSelectOptionFloorVal(toSelectTagID,strCd){
	$.post(
		urlPrefix+"OCDynamicSelectController/createSelectOptionFloorVal.do",{'strCd':strCd},
		function(data){
			var json=jsonParseCheck(data);
			$('#'+toSelectTagID+" option").remove();
			$('#'+toSelectTagID).append('<option value="">선택</option>');
			if(json==null) return;
			$('#'+toSelectTagID).append(json);
		},
		'text'
	);
}

//createSelectOptionCode(셀렉트박스아이디,코드아이디);
function createSelectOptionCode(toSelectTagID,majorCd){
	$.post(
		urlPrefix+"OCDynamicSelectController/createSelectOptionCode.do",{'majorCd':majorCd},
		function(data){
			var json=jsonParseCheck(data);
			$('#'+toSelectTagID+" option").remove();
			$('#'+toSelectTagID).append('<option value="">선택</option>');
			if(json==null) return;
			$('#'+toSelectTagID).append(json);
		},
		'text'
	);
}

//createSelectOptionCodeDept(셀렉트박스아이디,코드아이디);
function createSelectOptionCodeDept(toSelectTagID,majorCd){
	$.post(
		urlPrefix+"OCDynamicSelectController/createSelectOptionCodeDept.do",{'majorCd':majorCd},
		function(data){
			var json=jsonParseCheck(data);
			$('#'+toSelectTagID+" option").remove();
			$('#'+toSelectTagID).append('<option value="">선택</option>');
			if(json==null) return;
			$('#'+toSelectTagID).append(json);
		},
		'text'
	);
}




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
