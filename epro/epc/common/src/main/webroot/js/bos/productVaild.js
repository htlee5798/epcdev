/** 아래의 형식대로 맞추어서 ajax 통신을 해야지 validcheck가 가능하다.
 * $.ajax({
			form: form,
			type: Type,
			url: url,
			data: formQueryString,
			success: function(json) {
				try {
				} catch (e) {}
			},
			error: function(e) {
				alert("저장에 실패하였습니다");
			}
		});
 */
/** validchekc 페이지에 선언해야되는 스크립트
 * 	$( "form[name='bosform']" ).data( "applyStartDay", { type: "date"    , required : true  , size: 5 , regx : "", msg : "시작날짜 선택하세요" } );
	$( "form[name='bosform']" ).data( "applyEndDay"  , { type: "date"    , required : true  , size: 5 , regx : "", msg : "종료날짜 선택하세요" } );
	$( "form[name='bosform']" ).data( "jumpo"        , { type: "checkbox", required : true  , size: 5 , regx : "", msg : "점포를 선택하세요" } );
	$( "form[name='bosform']" ).data( "strLimtQty"   , { type: "number"  , required : true  , size: 5 , regx : "", msg : "점포별 한정수량을 입력하세요" } );
	$( "form[name='bosform']" ).data( "idLimtQty"    , { type: "number"  , required : false , size: 2 , regx : "", msg : "아이디 1회 한정수량을 입력하세요" } );
	
	변경방식
	$( "form[name='bosform']" ).customValid( "jumpo"   , { type: "checkbox", required : true  , size: 5 , msg : "점포를 선택하세요" } );
	$( "form[name='bosform']" ).customValid( "strLimtQty"   , { type: "number"  , required : true  , size: 5 , msg : "점포별 한정수량을 입력하세요" } );
	$( "form[name='bosform']" ).customValid( "idLimtQty"    , { type: "number"  , required : false , size: 2 , msg : "아이디 1회 한정수량을 입력하세요" } );
 */
// 페이지 로드시 실행
$(document).ready(function(){
	
	/*
	 * 정규식 
	 */
	default_string_regx=/[\{\}\[\]\/?.,;:|\)~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
	default_number_regx=/^[0-9]*$/;
	default_date_regx=/^[0-9]{4}[\/-][0-9]{2}[\/-][0-9]{2}$/;
	default_email_regx=/^([0-9a-zA-Z]+([_.-]?[0-9a-zA-Z]+)*@[0-9a-zA-Z]+[0-9,a-z,A-Z,.,-]*(.){1}[a-zA-Z]{2,4})+$/gi;
	
	// 값을 셋팅하는데 필요하다. form 에 따라서 data를 저장해야되기 때문에 선언한다.
	$.fn.customValid = function( elements, options) {
		 return this.each(function() {
	            var $element = $(this);
	            $( "form[name='"+$element.attr("name")+"']" ).data(elements ,options);
	        });
    };
	
	// ajax 셋팅을 한다. 전송하기 전에 validcheck를 진행한다.
	$(function(){ 
	    $.ajaxSetup({ 
	    	async: false,
	        beforeSend: function(xhr, opts){
	        	formName = $(this).attr("form") ;
	        	var msgs = "";
	        	if( $(this).attr("url").indexOf("Insert") > 0){
	        		
	        		// 필수사항 입력항목체크
	    			$(".validcheck").each(function(i){
//	    				formName = $(".validcheck").eq(i).closest('form').attr("name");
	    				name = $(".validcheck").eq(i).attr("name");
	    				tagName = $(".validcheck").eq(i).get(0).tagName;

	    				if ( typeof $( "form[name='"+formName+"']" ).data( name ) != "undefined" ) {
	    					type = $( "form[name='"+formName+"']" ).data( name ).type;
	    					msg  = $( "form[name='"+formName+"']" ).data( name ).msg;
	    					size = $( "form[name='"+formName+"']" ).data( name ).size;
	    					required = $( "form[name='"+formName+"']" ).data( name ).required;
//	    					regx = $( "form[name='"+formName+"']" ).data( name ).regx;

    						if( tagName.toUpperCase() == "INPUT" ){
    							if( $( "input[name='"+name+"']" ).attr("type").toUpperCase() == "TEXT" ){
    								val = $( "input[name='"+name+"']" ).val();
    							}else if( $( "input[name='"+name+"']" ).attr("type").toUpperCase() == "CHECKBOX" ){
    								val = $( "input[name='"+name+"']"+" option:checked" ).val();
    							}else if( $( "input[name='"+name+"']" ).attr("type").toUpperCase() == "RADIO" ){
    								val = $( "input[name='"+name+"']"+" option:checked" ).val();
    							}
    						}else if( tagName.toUpperCase() == "SELECT" ){
    							val = $( "input[name='"+name+"']"+" option:selected" ).val();
    						}else if( tagName.toUpperCase() == "TEXTAREA" ){
    							val = $( "textarea[name='"+name+"']" ).val();
    						}
    						
    						if( type.toUpperCase() == "string".toUpperCase() ){
    							
								string_regx = default_string_regx;
    							 
								if(string_regx.test(val)) {
									// 문자,숫자 이외 값이 입력되었습니다.
									alert("형식에 맞지 않는 값이 입력되었습니다.\n"+ msg );
									$("input[name='"+name+"']").focus();
			        				xhr.abort();
			        				return false;
			        				
								}else if( val.length > size ){
									// 문자,숫자 이외 값이 입력되었습니다.
									alert("입력값이 초과되었습니다.\n"+ msg );
			        				$(".validcheck").eq(i).focus();
			        				xhr.abort();
			        				return false;
								}else if( val.length == 0 ){
									if( required == true ){
										alert(msg );
										$("input[name='"+name+"']").focus();
										xhr.abort();
										return false;
									}
								}
    						}else if( type.toUpperCase() == "number".toUpperCase() ){
    							
								number_regx = default_number_regx;
    							 
								if(!number_regx.test(val)) {
									// 숫자 이외 값이 입력되었습니다.
									alert("형식에 맞지 않는 값이 입력되었습니다.\n"+ msg );
									$("input[name='"+name+"']").focus();
									xhr.abort();
									return false;
								}else if( val.length > size ){
									// 문자,숫자 이외 값이 입력되었습니다.
									alert("입력값이 초과되었습니다.\n"+ msg );
									$("input[name='"+name+"']").focus();
			        				xhr.abort();
			        				return false;
								}else if( val.length == 0 ){
									if( required == true ){
										alert(msg );
										$("input[name='"+name+"']").focus();
										xhr.abort();
										return false;
									}
								}
    						}else if( type.toUpperCase() == "checkbox".toUpperCase() ){	
    							if( $( "input[name='"+name+"']" ).is(":checked") == false ){
    								alert(msg );
									xhr.abort();
									return false;
    							}
    						}else if( type.toUpperCase() == "date".toUpperCase() ){
    							date_regx = default_date_regx;

    							if(!date_regx.test(val)) {
									alert("날짜형식에 맞지 않는 값이 입력되었습니다.\n"+ msg );
									$("input[name='"+name+"']").focus();
			        				xhr.abort();
			        				return false;
			        				
								}else if( val.length > size ){
									// 문자,숫자 이외 값이 입력되었습니다.
									alert("날짜 입력값이 초과되었습니다.\n"+ msg );
			        				$(".validcheck").eq(i).focus();
			        				xhr.abort();
			        				return false;
								}else if( val.length == 0 ){
									if( required == true ){
										alert(msg );
										$("input[name='"+name+"']").focus();
										xhr.abort();
										return false;
									}
								}
    						}else if( type.toUpperCase() == "email".toUpperCase() ){
    							email_regx = default_email_regx;
    							
    							if(!email_regx.test(val)) {
									alert("이메일형식에 맞지 않는 값이 입력되었습니다.\n"+ msg );
									$("input[name='"+name+"']").focus();
			        				xhr.abort();
			        				return false;
			        				
								}else if( val.length > size ){
									// 문자,숫자 이외 값이 입력되었습니다.
									alert("이메일 입력값이 초과되었습니다.\n"+ msg );
			        				$(".validcheck").eq(i).focus();
			        				xhr.abort();
			        				return false;
								}else if( val.length == 0 ){
									if( required == true ){
										alert(msg );
										$("input[name='"+name+"']").focus();
										xhr.abort();
										return false;
									}
								}
    						}
	    				}
	    			});
	        	}
        	}
	     });
	});
	
	// form submit 방식 제어
	$("form").each(function(i){
		fName = $("form").eq(i).attr("name");
		loadSubmitEvent(fName);
	});
	function loadSubmitEvent(formName){
		$("form[name='"+formName+"'" ).submit(function( event ) {
			
			// 필수사항 입력항목체크
			$(".validcheck").each(function(i){
				name = $(".validcheck").eq(i).attr("name");
				tagName = $(".validcheck").eq(i).get(0).tagName;

				if ( typeof $( "form[name='"+formName+"']" ).data( name ) != "undefined" ) {
					type = $( "form[name='"+formName+"']" ).data( name ).type;
					msg  = $( "form[name='"+formName+"']" ).data( name ).msg;
					size = $( "form[name='"+formName+"']" ).data( name ).size;
					required = $( "form[name='"+formName+"']" ).data( name ).required;
//					regx = $( "form[name='"+formName+"']" ).data( name ).regx;

					if( tagName.toUpperCase() == "INPUT" ){
						if( $( "input[name='"+name+"']" ).attr("type").toUpperCase() == "TEXT" ){
							val = $( "input[name='"+name+"']" ).val();
						}else if( $( "input[name='"+name+"']" ).attr("type").toUpperCase() == "CHECKBOX" ){
							val = $( "input[name='"+name+"']"+" option:checked" ).val();
						}else if( $( "input[name='"+name+"']" ).attr("type").toUpperCase() == "RADIO" ){
							val = $( "input[name='"+name+"']"+" option:checked" ).val();
						}
					}else if( tagName.toUpperCase() == "SELECT" ){
						val = $( "input[name='"+name+"']"+" option:selected" ).val();
					}else if( tagName.toUpperCase() == "TEXTAREA" ){
						val = $( "textarea[name='"+name+"']" ).val();
					}
					
					if( type.toUpperCase() == "string".toUpperCase() ){
						
						string_regx = default_string_regx;
						 
						if(string_regx.test(val)) {
							// 문자,숫자 이외 값이 입력되었습니다.
							alert("형식에 맞지 않는 값이 입력되었습니다.\n"+ msg );
							$("input[name='"+name+"']").focus();
	        				return false;
	        				
						}else if( val.length > size ){
							// 문자,숫자 이외 값이 입력되었습니다.
							alert("입력값이 초과되었습니다.\n"+ msg );
	        				$(".validcheck").eq(i).focus();
	        				return false;
						}else if( val.length == 0 ){
							if( required == true ){
								alert(msg );
								$("input[name='"+name+"']").focus();
								return false;
							}
						}
					}else if( type.toUpperCase() == "number".toUpperCase() ){
						
						number_regx = default_number_regx;
						 
						if(!number_regx.test(val)) {
							// 숫자 이외 값이 입력되었습니다.
							alert("형식에 맞지 않는 값이 입력되었습니다.\n"+ msg );
							$("input[name='"+name+"']").focus();
							return false;
						}else if( val.length > size ){
							// 문자,숫자 이외 값이 입력되었습니다.
							alert("입력값이 초과되었습니다.\n"+ msg );
							$("input[name='"+name+"']").focus();
	        				return false;
						}else if( val.length == 0 ){
							if( required == true ){
								alert(msg );
								$("input[name='"+name+"']").focus();
								return false;
							}
						}
					}else if( type.toUpperCase() == "checkbox".toUpperCase() ){	
						if( $( "input[name='"+name+"']" ).is(":checked") == false ){
							alert(msg );
							return false;
						}
					}else if( type.toUpperCase() == "date".toUpperCase() ){
						date_regx = default_date_regx;
						 
						if(!date_regx.test(val)) {
							alert("날짜형식에 맞지 않는 값이 입력되었습니다.\n"+ msg );
							$("input[name='"+name+"']").focus();
	        				return false;
	        				
						}else if( val.length > size ){
							// 문자,숫자 이외 값이 입력되었습니다.
							alert("날짜 입력값이 초과되었습니다.\n"+ msg );
	        				$(".validcheck").eq(i).focus();
	        				return false;
						}else if( val.length == 0 ){
							if( required == true ){
								alert(msg );
								$("input[name='"+name+"']").focus();
								return false;
							}
						}
					}else if( type.toUpperCase() == "email".toUpperCase() ){
						email_regx = default_email_regx;
						 
						if(!email_regx.test(val)) {
							alert("이메일형식에 맞지 않는 값이 입력되었습니다.\n"+ msg );
							$("input[name='"+name+"']").focus();
	        				return false;
	        				
						}else if( val.length > size ){
							// 문자,숫자 이외 값이 입력되었습니다.
							alert("이메일 입력값이 초과되었습니다.\n"+ msg );
	        				$(".validcheck").eq(i).focus();
	        				return false;
						}else if( val.length == 0 ){
							if( required == true ){
								alert(msg );
								$("input[name='"+name+"']").focus();
								return false;
							}
						}
					}
				}
			});
		});
//		alert( "Handler for .submit() called." );
//		event.preventDefault();
//		return false;
	}
});


