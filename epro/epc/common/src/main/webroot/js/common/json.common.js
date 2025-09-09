/**
 * 서버에서 넘어온 JSON String을 JSON 객체로 변환해서 리턴함.
 * 더불어서, 에러가 났는지 체크를 해서 에러가 났으면 에러내용을 화면에 보여줌.
 *  
 */
function jsonParseCheck(jsonStr){

	var json = JSON.parse(jsonStr);
	
	if(json==null){
		return null;
	}
	
	var resultCd = json.__RESULT__;
	if(resultCd==null || resultCd!="NG"){
		return json;
		
	}else{
		var errCd = json.__ERR_CD__;
		var errMsg = json.__ERR_MSG__;
		
		alert("[ 에 러 ]\n" + errMsg);
		return null;
	}
}



/**
 * 페이징처리를 할때, 한 페이지당 조회되는 건수를 의미하는 selectbox를 만듦.
 * (수정중.)
 * @param arr
 * @returns
 */
function createPageSizeSelectBox(arr, selectedVal, id){
	if(id==null){
		id = "_sizePerPage";
	}
	if(arr==null){
		arr = [20, 50, 100, 200, 400];
	}
	if(id==null){
		id = "_sizePerPage"
	}
	var result = "페이지당 건수<select id='" + id +"' name='" + id +"' class='filter'>";
	for(var i in arr){
	    result += "<option value='" + arr[i] + "' " + ((arr[i]==selectedVal) ? ' selected ' : '') + ">" + arr[i] + "</option>";
	}
	result += "</select>";
	return result;
}

/*!
 * jQuery serializeObject - v0.2 - 1/20/2010
 * http://benalman.com/projects/jquery-misc-plugins/
 * 
 * Copyright (c) 2010 "Cowboy" Ben Alman
 * Dual licensed under the MIT and GPL licenses.
 * http://benalman.com/about/license/
 */

// Whereas .serializeArray() serializes a form into an array, .serializeObject()
// serializes a form into an (arguably more useful) object.

(function($,undefined){
  '$:nomunge'; // Used by YUI compressor.
  
  $.fn.serializeObject = function(){
    var obj = {};
    
    $.each( this.serializeArray(), function(i,o){
      var n = o.name,
        v = o.value;
        
        obj[n] = obj[n] === undefined ? v
          : $.isArray( obj[n] ) ? obj[n].concat( v )
          : [ obj[n], v ];
    });
    
    return obj;
  };
  
})(jQuery);


function setAjaxLoading(){

	$(document).ajaxStart(
			function() {
				//jQuery("body").showLoading();
			}	    
		).ajaxStop(
			function() {
				//jQuery("body").hideLoading();
			}	
	);
}


jQuery(document).ready(function(){
	setAjaxLoading();
});


jQuery.fn.generalDateFormat = function(){
	
	return this.each(function(){
		var src = null;

		if (jQuery(this).is(":input")){
			src = new String(jQuery(this).val());
		}else{
			src = new String(jQuery(this).text());
		}

		if(src==null || typeof(src.length)==undefined){
			return;
		}
		var len = src.length;
		
		result = convertDateFormat(src);
		
		var result;

		if (jQuery(this).is(":input")){
			$(this).val(result);
		}else{
			$(this).text(result);
		}
	});
	
};

// 날짜 포맷시간으로 변환
function convertDateFormat(src){
	if(src==null){
		return "";
	}
	
	//if(typeof(src)!="string"){
	//	return src;
	//}
	
	// 군더더기는 제거 
	src = src.replace(" /[ \-/\\:]/g ", "");
	
	var len = src.length;
	
	if(len==6){
		return src.substring(0,4) + "/" + src.substring(4,6);
	}else if(len==8){
		return src.substring(0, 4) + "/" + src.substring(4, 6) + "/" + src.substring(6, 8);
	}else if(len==12){
		return src.substring(0, 4) + "/" + src.substring(4, 6) + "/" + src.substring(6, 8) + " " + src.substring(8, 10) + ":" + src.substring(10,12);
	}else if(len==14){
		return src.substring(0, 4) + "/" + src.substring(4, 6) + "/" + src.substring(6, 8) + " " + src.substring(8, 10) + ":" + src.substring(10,12) + ":" + src.substring(12,14);
	}else{
		return src;
	}
}

// 세자리마다 코마를 찍어주는 일반적인 숫자포맷(소수점 이하는 6자리까지)
jQuery.fn.generalNumFormat = function(){
    
    return this.each(function(){
        var num = $(this).parseNumber(); 
        $(this).formatNumber({format:"#,###.######", locale:"kr"});
    });
 	
};


jQuery(document).ready(function(){
	$(document).bind("ajaxError", function(xhr, textStatus, errorThrown){
	    //alert("시스템에러 발생. (" + textStatus + ")");
	});
});

