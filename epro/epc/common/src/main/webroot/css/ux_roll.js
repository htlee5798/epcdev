//<![CDATA[
			/*================================================================================
			/ _w(txt)
			/ 콘솔 확인용.
			================================================================================*/
			function _w(txt){
				if(_ID('printf')){
					_ID('printf').value = '\n'+txt;
				}else{
					var obj = document.createElement('textarea');
					obj.setAttribute('id','printf');
					document.getElementsByTagName('body')[0].appendChild(obj);
					_innerCss(_ID('printf'),'border:1px solid red,width:98%,height:200px,position:fixed,left:0,top:0,opacity:0.3,filter:alpha(opacity=30)');
					_ID('printf').onmouseover=function(){
						this.style.opacity = '1';
						this.style.filter = 'alpha(opacity=100)';
					}
					_ID('printf').onmouseout=function(){
						this.style.opacity = '0.3';
						this.style.filter = 'alpha(opacity=30)';
					}
					_ID('printf').value = txt;
				}
			}
			/*================================================================================
			/ 아이디 객체
			/ id = 문서에서 찾을 아이디명
			/ 해당 아이디를 리턴함.
			================================================================================*/
			function _ID(id){
				return document.getElementById(id);
			}
			/*================================================================================
			/ _innerCss(obj,css)
			/ obj = 해당 스타일이 들어갈 오브젝트
			/ css = 삽입될 스타일ex) 'display:block,border:1px solid red,width:100%,height:100%,text-indent:-9999px,border-top:1px solid red'
			================================================================================*/
			function _innerCss(obj,css){
				var inStyle = css.split(',');
				var reg = new RegExp('-([a-z])');
				for(var i=0;i<inStyle.length; i++){
					var arr = inStyle[i].split(':')[0];
					var regTst = reg.exec(arr);
					obj.style[arr.replace(reg,((regTst != null)?regTst[1].toUpperCase():''))]= inStyle[i].split(':')[1];
				}
			}
			//-=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=--=-=-=-=-
			//dom 객체 추가 (추가 노드, 추가 위치)
			function insertAfter(newNode, referenceNode) { 
					refParentNode = referenceNode.parentNode; 
				if (referenceNode != refParentNode.lastChild) 
					refParentNode.insertBefore(newNode, referenceNode.nextSibling); 
				else
					refParentNode.appendChild(newNode); 
			}
			//dom 객체 삭제
			function deleteObj(obj){
				try{
					document.getElementById(obj).parentNode.removeChild(document.getElementById(obj)); 
				}catch(err){
					alert(err);
				}
			}
			/*================================================================================
			/-- _windowH() --
			/	브라우저 화면 크기를 리턴한다.
			================================================================================*/
			function _windowH(){
				if (typeof window.innerWidth != 'undefined'){
					return window.innerHeight;
				}else if (typeof document.documentElement != 'undefined' && typeof document.documentElement.clientHeight != 'undefined' && document.documentElement.clientHeight != 0){
					return document.documentElement.clientHeight;
				}else{
					return document.getElementsByTagName('body')[0].clientHeight;
				}
			}
			
			/*================================================================================
			/-- _windowW() --
			/	브라우저 화면 크기를 리턴한다.
			================================================================================*/
			function _windowW(){
				if (typeof window.innerWidth != 'undefined'){
					return window.innerWidth;
				}else if (typeof document.documentElement != 'undefined' && typeof document.documentElement.clientWidth != 'undefined' && document.documentElement.clientWidth != 0){
					return document.documentElement.clientWidth;
				}else{
					return document.getElementsByTagName('body')[0].clientWidth;
				}
			}
			/*================================================================================
			/-- addEvent(obj,type,fnc) 
			/	addEventListener 의 호환성을 위한 함수화
			/	obj = object, type = event, fn = functionName
			/	mousewheel 과 DOMMouseScroll 은 별도...
			================================================================================*/
			function addEvent(obj, type, fn) {

				if (obj.addEventListener) {
					obj.addEventListener(type, fn, false);
				} else if (obj.attachEvent) {
					obj.attachEvent("on" + type, fn);
				}
			}
			/*================================================================================
			/-- removeEvent(obj,type,fnc) 
			/	removeEventListener 의 호환성을 위한 함수화
			/	obj = object, type = event, fn = functionName
			================================================================================*/
			function removeEvent(obj, type, fn) {
				if (obj.removeEventListener) {
					obj.removeEventListener(type, fn, false);
				} else if (obj.detachEvent) {
					obj.detachEvent("on" + type,fn);
				}
			}
			/*================================================================================
			/-- findPos(obj) --
			/	object 의 좌표값을 리턴
			/	obj = 객체 / 리턴은 최상위(body)로 부터의 left , top
			================================================================================*/
			function _findPos(obj){
				var curleft = curtop = 0;
				if(obj.offsetParent){
					do{
						curleft += obj.offsetLeft;
						curtop += obj.offsetTop;
					}while(obj = obj.offsetParent);
					return[curleft,curtop];
				}
			}
			/*================================================================================
			/-- createBox(box,obj,opt) 
			/	createBox = css 적용이 가능한 세로 스크롤 바를 생성한다.(가로 미지원)
			/	box = 스크롤 바가 생길 DIV element, obj = 박스 내부의 컨텐츠, opt = 'window' or 'box' : window 옵션일 경우 브라우저 전체에 스크롤생성, box 일경우 특정 사이즈에 스크롤 생성
			/	지원 브라우저 : IE 6이상, FF, Safari, Chrome / opera 미확인
			/	호환성 해결방법 : IE 6.0 의 경우 box 옵션 사용시 box 로 사용될 Element의 style 속성에 overflow:hidden 을 추가해야 함.
			/	.scrollBar 에 스타일을 추가해 주어야 함. (width:6px;background-color:red;opacity:0.5;filter:alpha(opacity=50);border-radius:3px;cursor:pointer) 스크롤 바 디자인.
			/	box.outResize() = 동적으로 obj 내용이 변할 경우 해당 함수를 호출하면 box 의 사이즈가 갱신됨.
			/	box.areaEventOpt() = 지정된 박스 밖으로 마우스가 눌린상태로 나갈시 이벤트 캔슬
			================================================================================*/
			var createBox = function(box,obj,opt){
				var top=null, maxTop=null, minTop=null;//top:현재 obj의 위치, maxTop:obj top속성의 최대값,minTop:obj top속성의 최소값
				var left=null, maxLeft=null, minLeft=null;//left:현재 obj의 위치, maxLeft:obj left속성의 최대값,minLeft:obj left속성의 최소값
				var scrSpeed=40;//휠을 1칸 돌릴시에 이동될 px 값
				var defaultId = box.getAttribute('id');
				var idArr = [defaultId+'_area',defaultId+'_barsArea',defaultId+'_bars',defaultId+'_area2',defaultId+'_barsArea2',defaultId+'_bars2'];//사용될 scrollbar 등의 오브젝트 아이디 저장(세로)
				var domArr = new Array(); // 성능 최적화를 위해 dom 엘리먼트 저장
				var makeCheck = 'false';//스크롤이 생성 된 상태인지를 저장. 생성후에 true 값으로 변경됨.
				var releaseCheck = 'false';//스크롤 바를 누른 상태인지 아닌지를 체크.
				var startDrag = new Array();//[0] = 스크롤바를 누를때 마우스 Y, [1] = 스크롤바를 누를때 _ID(idArr[2] 의 Y
				var fnc = {
					//공통으로 사용될 높이값을 리턴함. opt 에 따라 윈도우 높이값 or box 의 높이값을 이턴함.
					calc : function(option){
						var returnMsg = null;
						switch(option){
							case 'boxH' : returnMsg = (opt=='window')?_windowH():box.offsetHeight;	//boxH  box 의 높이값을 옵션에 따라 리턴
							break;
							case 'boxW' : returnMsg = (opt=='window')?_windowW():box.offsetWidth;	//boxW  box 의 가로값을 옵션에 따라 리턴
							break;
							case 'minTop' : returnMsg = fnc.calc('boxH')-obj.offsetHeight;	 //minTop  box와 obj의 값의 차를 리턴(스크롤 범위 계산, 세로)
							break;
							case 'minLeft' : returnMsg = fnc.calc('boxW')-obj.offsetWidth;	 //minWidth  box와 obj의 값의 차를 리턴(스크롤 범위 계산, 가로)
							break;
							case 'percent' : returnMsg = ((obj.offsetHeight - fnc.calc('boxH')) / obj.offsetHeight) * 100;
							break;
							case 'percentW' : returnMsg = ((obj.offsetWidth - fnc.calc('boxW')) / obj.offsetWidth) * 100;
							break;
							case 'barsH' : returnMsg = (fnc.calc('boxH')/100) * (100 - fnc.calc('percent'));	 //스크롤바 높이
							break;
							case 'barsW' : returnMsg = (fnc.calc('boxW')/100) * (100 - fnc.calc('percentW'));	 //스크롤바 너비
							break;
							case 'barsT' : returnMsg = parseInt(((-(top)/obj.offsetHeight)*100) *  (fnc.calc('boxH')/100));	//스크롤바 top 값
							break;
							case 'barsL' : returnMsg = parseInt(((-(left)/obj.offsetWidth)*100) *  (fnc.calc('boxW')/100));	//스크롤바 left 값
							break;
							case 'barsShow' : returnMsg = (fnc.calc('boxH') > obj.offsetHeight)?'none':'block';	 //스크롤 유무(세로)
							break;
							case 'barsShowW' : returnMsg = (fnc.calc('boxW') >= obj.offsetWidth)?'none':'block';	 //스크롤 유무(가로)
							break;
							default : returnMsg = '0';
						}
						return returnMsg;
					},
					//엘리먼트들에 각각의 속성을 추가 하고, 배치함, 스크롤을 생성함(최초 1회 실행).
					cbnation : function(){
						//1. box 및 obj 속성 변경 및 배치. reSize
						_innerCss(box,'overflow:hidden,height:'+fnc.calc('boxH')+'px,position:relative');
						_innerCss(obj,'position:absolute,left:0,top:0');
						//2. 공통변수 초기화(위치값)
						top=0,maxTop=0,minTop=fnc.calc('minTop');
						left=0,maxLeft=0,minLeft=fnc.calc('minLeft');
						//3. scrollbar 생성 ()
						var htmlH = '<div id="'+idArr[1]+'" class="scrollBg"><div id="'+idArr[2]+'" class="scrollBar"></div></div>';
						var htmlW = '<div id="'+idArr[4]+'" class="scrollBg"><div id="'+idArr[5]+'" class="scrollBarW"></div></div>';
						var newEleH = document.createElement('div');
						var newEleW = document.createElement('div');
						newEleH.setAttribute('id',idArr[0]);
						newEleW.setAttribute('id',idArr[3]);
						insertAfter(newEleH, obj);
						insertAfter(newEleW, obj);
						_ID(idArr[0]).innerHTML = htmlH;
						_ID(idArr[3]).innerHTML = htmlW;
						for(var i=0; i<6;i++){
							domArr[i] = _ID(idArr[i]);
						}
						_innerCss(domArr[0],'position:absolute,right:0,top:0,width:30px,overflow:hidden,display:'+fnc.calc('barsShow')+',height:'+fnc.calc('boxH')+'px');
						_innerCss(domArr[1],'position:relative,width:100%,height:100%')
						_innerCss(domArr[2],'position:absolute,right:0,height:'+ parseInt(fnc.calc('barsH'))+'px');
						_innerCss(domArr[3],'position:absolute,left:0,bottom:0,height:30px,overflow:hidden,display:'+fnc.calc('barsShowW')+',width:'+fnc.calc('boxW')+'px');
						_innerCss(domArr[4],'position:relative,width:100%,height:100%')
						_innerCss(domArr[5],'position:absolute,left:0,bottom:0,width:'+ parseInt(fnc.calc('barsW'))+'px');
						makeCheck = 'true';
					},
					//각각의 생성되어진 오브젝트가 브라우저 리사이즈 및 obj내부 컨텐츠 동적 생성시 재배치 함수 호출
					resizeCtr : function(){
						if(makeCheck == 'false'){
							fnc.cbnation();
							return false;
						}
					//	_w(obj.offsetWidth + ' , ' + fnc.calc('boxW') + ' / ' +obj.offsetHeight + ' , ' + fnc.calc('boxH'))
						minTop=fnc.calc('minTop');
						minLeft=fnc.calc('minLeft');
						if((obj.offsetHeight + top)<fnc.calc('boxH') && top < maxTop){
							fnc.rePosition(-1);
						}else if(top > maxTop){
							fnc.rePosition(1);
						}
						box.style.height = fnc.calc('boxH')+'px';
						box.style.width = fnc.calc('boxW')+'px';
						domArr[0].style.height = fnc.calc('boxH')+'px';
						domArr[0].style.display = fnc.calc('barsShow');
						domArr[2].style.height = parseInt(fnc.calc('barsH'))+'px';
						domArr[3].style.width = fnc.calc('boxW')+'px';
						domArr[3].style.display = fnc.calc('barsShowW');
						domArr[5].style.width = parseInt(fnc.calc('barsW'))+'px';
					},
			//----------------------------------------------------------------------------가로--------------------------------------------------------------------------
					//스크롤바 mousedown 이벤트 삽입(오버시 / 가로)
					scrollBarOn_W : function(){
						addEvent(box,'mousedown',fnc.scrollBarDown_W);
					},
					//스크롤바 누를경우 (가로)
					scrollBarDown_W : function(event){
						event = event?event:window.event;
						releaseCheck = 'true';
						addEvent(box,'mousemove',fnc.scrollBarMove_W);
						if(typeof event.preventDefault != 'undefined'){	// preventDefault (드래그시 선택되는것) 이벤트 활성화(FF및 그외 브라우저)
							event.preventDefault();
						}else{
							fnc.notDrag('return false');
						}
						startDrag[2] = (event.x)?event.x:event.pageX;
						startDrag[3] = _ID(idArr[5]).offsetLeft;
					},
					//스크롤바 이동
					scrollBarMove_W : function(event){
						event = event?event:window.event;
						mpos = (event.x)?event.x:event.pageX;
						var bPos = startDrag[3]+ (mpos - startDrag[2]);
						domArr[5].style.left = (bPos<0)?'0px':((bPos>(fnc.calc('boxW')-fnc.calc('barsW')))?(fnc.calc('boxW')-fnc.calc('barsW'))+'px':bPos+'px');
						left = - (parseInt((obj.offsetWidth/100) * domArr[5].offsetLeft/fnc.calc('boxW') * 100));
						obj.style.left = left+'px'
					},
			//----------------------------------------------------------------------------세로--------------------------------------------------------------------------
					//스크롤바 mousedown 이벤트 삽입(오버시 / 세로)
					scrollBarOn_H : function(){
						addEvent(box,'mousedown',fnc.scrollBarDown_H);
					},
					//스크롤바 누를경우 (세로)
					scrollBarDown_H : function(event){
						event = event?event:window.event;
						releaseCheck = 'true';
						addEvent(box,'mousemove',fnc.scrollBarMove_H);
						if(typeof event.preventDefault != 'undefined'){	// preventDefault (드래그시 선택되는것) 이벤트 활성화(FF및 그외 브라우저)
							event.preventDefault();
						}else{
							fnc.notDrag('return false');
						}
						startDrag[0] = (event.y)?event.y:event.pageY;
						startDrag[1] = _ID(idArr[2]).offsetTop;
					},
					//스크롤바 이동
					scrollBarMove_H : function(event){
						event = event?event:window.event;
						mpos = (event.y)?event.y:event.pageY;
						var bPos = startDrag[1]+ (mpos - startDrag[0]);
						domArr[2].style.top = (bPos<0)?'0px':((bPos>(fnc.calc('boxH')-fnc.calc('barsH')))?(fnc.calc('boxH')-fnc.calc('barsH'))+'px':bPos+'px');
						top = - (parseInt((obj.offsetHeight/100) * domArr[2].offsetTop/fnc.calc('boxH') * 100));
						obj.style.top = top+'px'
					},
			//----------------------------------------------------------------------------공통--------------------------------------------------------------------------
					//스크롤바 드래그 종료
					scrollBarOff_H : function(){
						if(releaseCheck == 'false') {
							removeEvent(box, 'mousedown',fnc.scrollBarDown_H);
							removeEvent(box,'mousemove',fnc.scrollBarMove_H);
						};
					},
					scrollBarOff_W : function(){
						if(releaseCheck == 'false') {
							removeEvent(box, 'mousedown',fnc.scrollBarDown_W);
							removeEvent(box,'mousemove',fnc.scrollBarMove_W);
						}
					},
					//스크롤 이벤트 강제 종료
					scrollCancel : function(event){
						event = event?event:window.event;
						var mposY = (event.y)?event.y:event.pageY;
						var mposX = (event.x)?event.x:event.pageX;
						var pos = _findPos(box);
						if(mposY >= (pos[1] + box.offsetHeight) || mposX >= (pos[0] + box.offsetWidth)){
							releaseCheck = 'false';
							removeEvent(box,'mousedown',fnc.scrollBarDown_H);
							removeEvent(box,'mousemove',fnc.scrollBarMove_H);
							removeEvent(box,'mousedown',fnc.scrollBarDown_W);
							removeEvent(box,'mousemove',fnc.scrollBarMove_W);
							fnc.notDrag('null');
						}
					},
					//스크롤바 업
					scrollBarUp : function(){
						releaseCheck = 'false';
						removeEvent(box, 'mousemove',fnc.scrollBarMove_H);
						removeEvent(box, 'mousedown',fnc.scrollBarDown_H);
						removeEvent(box, 'mousemove',fnc.scrollBarMove_W);
						removeEvent(box, 'mousedown',fnc.scrollBarDown_W);
						fnc.notDrag('null');
					},

					//스크롤바 드래그시 문서 긁힘 방지
					notDrag : function(bool){	 
						document.onselectstart = new Function(bool);
						document.ondragstart = new Function(bool);
					},
					//사용자가 마우스 휠을 동작할 경우
					wheelCtr : function(event){
						if(fnc.calc('barsShow') == 'none') return false;
						var delta = 0;
						event = event?event:window.event;
						if (event.stopPropagation) {	//버블링 제거
							event.stopPropagation();
							//_w('cancelBb');
						}else {
							event.cancelBubble = true;
						}
						if (event.wheelDelta) {
							delta = event.wheelDelta/120;
							if (window.opera) delta = -delta;
						} else if (event.detail) delta = -event.detail/3;
						if (delta) fnc.rePosition(delta);
					},
					// 스크롤 및 페이지의 위치값을 변경
					rePosition : function(delta){
						if(delta < 0 && top !=minTop ){
							if(top <= minTop - (minTop%scrSpeed)){
								top = minTop;
							}else{
								top += -scrSpeed;
							}
						}
						if(delta > 0 && top != maxTop){
							if(top >= maxTop + (maxTop%scrSpeed)){
								top = maxTop;
							}else{
								top += scrSpeed;
							}
						}
						if(delta < 0 && top >= minTop ){
							obj.style.top = top+'px';
							domArr[2].style.top = fnc.calc('barsT') + 'px';
						}else if(delta > 0 && top <= maxTop){
							obj.style.top = top+'px';
							domArr[2].style.top = fnc.calc('barsT') + 'px';
						}
					},
					//터치 디바이스의 터치 스타트
					touchStart : function(event){
						//event.preventDefault();
						releaseCheck = 'true';
						startDrag[0] = event.touches[0].pageY;
						startDrag[1] = _ID(idArr[2]).offsetTop;
						startDrag[2] = event.touches[0].pageX;
						startDrag[3] = _ID(idArr[5]).offsetLeft;
					},
					//터치 디바이스의 터치 이동	//계산다시--------------------------------------------------------------------------------------------------------------
					touchMove : function(event){
						event.preventDefault();
						mpos = event.touches[0].pageY;
						mpos2 = event.touches[0].pageX;
						var bPos = startDrag[1]- (mpos - startDrag[0]);
						var bPos2 = startDrag[3]- (mpos2 - startDrag[2]);
						var pPos = startDrag[0]-mpos;
						var pPos2 = startDrag[2]-mpos2;
						top -= pPos;
						left -= pPos2;
						startDrag[0] = mpos;
						startDrag[2] = mpos2;
						if(maxTop<top){
							obj.style.top = maxTop;
							top=maxTop;
						}else{
							if(minTop>top){
								obj.style.top = minTop;
								top=minTop;
							}else{
								obj.style.top = top+'px';
							}
						}
						if(maxLeft<left){
							obj.style.left = maxLeft;
							left=maxLeft;
						}else{
							if(minLeft>left){
								obj.style.left = minLeft;
								left=minLeft;
							}else{
								obj.style.left = left+'px';
							}
						}
						domArr[2].style.top = - (parseInt((domArr[2].offsetHeight/100) * obj.offsetTop/fnc.calc('boxH') * 100))+'px';
						domArr[5].style.left = - (parseInt((domArr[5].offsetWidth/100) * obj.offsetLeft/fnc.calc('boxW') * 100))+'px';
					}
				}
				//creat Event
				fnc.cbnation();
				//resize Event
				if(opt == 'window'){
					addEvent(window,'resize',fnc.resizeCtr);
					document.body.style.overflow = 'hidden';
				}
				//mouseEvent
				addEvent(box,'DOMMouseScroll',fnc.wheelCtr);//MOZ 휠 이벤트
				addEvent(box,'mousewheel',fnc.wheelCtr);//IE,webkit,opera
				addEvent(domArr[2],'mouseover',fnc.scrollBarOn_H);//우측 스크롤 바 이벤트
				addEvent(domArr[5],'mouseover',fnc.scrollBarOn_W);//하단 스크롤 바 이벤트
				addEvent(domArr[2],'mouseout',fnc.scrollBarOff_H);
				addEvent(domArr[5],'mouseout',fnc.scrollBarOff_W);
				addEvent(document.body,'mouseup',fnc.scrollBarUp);
				
				this.outResize = function(){
					fnc.resizeCtr();//컨텐츠 동적 추가 제거시 사이즈 재설정
				}
				
				this.areaEventOpt = function(){
					addEvent(document.body,'mousemove',fnc.scrollCancel);//영역체크. 영역밖으로 나갈시 이벤트 종료 . 옵션임.
				}
				//touch Event
				addEvent(box,'touchstart',fnc.touchStart);
				addEvent(box,'touchmove',fnc.touchMove);
			}
		//]]>