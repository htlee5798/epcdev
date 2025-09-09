$.extend($.fn,{
	EnterTab:function(o){
		var Focus_Move = function(df,i,ln,shift){
			//포커스를 취득 할수 없는 것은 넘어간다.
			var mv;
			if (shift){
				mv	= -1;
			}else{
				mv	= 1;
			}
			var j = (i+mv) % ln;
			var Fo,Fs;
			while(i=j){
				
				Fo	=	df[j];
				Fs	=	Fo.style;
				if	(Fo.type!="hidden" &&
					!Fo.disabled &&
					Fo.tabIndex!=-1 &&
					Fs.visibility!="hidden" &&
					Fs.display!="none"){
					// 대상 오브젝트로 돌린다.
					return Fo;
				}
				j=(j+mv) % ln;
			}
			//Hit하지 않은경우
			return df[i];
		}
		var d = document;
		var $input = $(":input",this)
		$input.keyup(function(e){
			var	k	=	e.keyCode;
			var	s	=	e.shiftKey;
			var	obj	=	e.target;
			var blKey	=	true;
			if (!o.Enter && k==13) return true;
			if (!o.Tab && k==9) return true;
			
			var exChkBool = false;
			if(k==46 || k==8) {
				exChkBool= true;
			}
//			if (!o.Del && k==46) return true;
//			if (!o.Tab && k==8) return true;
			
			var maxLen = this.maxLength;
			var inpVal = this.value;
			var byteLen = getByteLen(inpVal);
			
			
			
			
			if(exChkBool == false) {
				if (k == 13 ||k == 9 || (byteLen >= maxLen)){
				//Enter 탭으로 포커스 이동
					switch(obj.tagName){
					case "TEXTAREA":
						if (k!=13) blKey = false;
						break;
					case "INPUT":	case "SELECT":
						//file제외
						if (obj.type!="file")	blKey = false;
						break;
					default:
					}
					//key이벤트를 처리할것만 추출
					if (!blKey){
						// form오브젝트가 몇번째인지 찾는다
						var ln = $input.length-1;
						var i;
						for (i=0;i<ln;i++){
							if ($input[i]==obj) break;
						}
						// 다음 폼오브젝트를 찾는다.
						obj = Focus_Move($input,i,ln,s);
					}
				}
			}

			if (!blKey){
				// 이벤트를 전파 안함.
				if (obj.type!="file"){
					//IE規定の動作キャンセル
					if(d.all) window.event.keyCode = 0;
					obj.focus();
					if (obj.select && obj.type!="button") obj.select();
				}else{
					blKey = true;
				}
			}
			return blKey;
		});
		
		getByteLen = function(chkStr) {
             if (chkStr == null || chkStr.length == 0) {
		          return 0;
		        }
		        var cnt = 0;
				var ch = 0;
		        for (var idx = 0; idx < chkStr.length; idx++) {
		          
					ch = chkStr.charAt(idx);
		
					if (ch == null || ch.length == 0) {
					  cnt += 0;
					}
					var charCode = ch.charCodeAt(0);
		
					if (charCode <= 0x00007F) {
					  cnt += 1;
					} else if (charCode <= 0x0007FF) {
					  cnt += 2;
					} else if (charCode <= 0x00FFFF) {
					  cnt += 3;
					} else {
					  cnt += 4;
					}
		        }

		  	return cnt;
		}
	}
});
