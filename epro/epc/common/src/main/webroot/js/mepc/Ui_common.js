/*******************************************************************************
 * 계약상담시스템 공통 js
 ******************************************************************************/
//ID / CLASS 로 SHOW HIDE 효과 주기 1.
	function objChangeClass(objId, objClass){
	  document.getElementById(objId).className = objClass;
	}
	 
/*       top navi 시작        */
				function MM_reloadPage(init) {  //reloads the window if Nav4 resized
				  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
					document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
				  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
				}
				MM_reloadPage(true);

				function MM_preloadImages() { //v3.0
				  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
					var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
					if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
				}

				function MM_swapImgRestore() { //v3.0
				  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
				}

				function MM_findObj(n, d) { //v4.01
				  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
					d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
				  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
				  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
				  if(!x && d.getElementById) x=d.getElementById(n); return x;
				}

				function MM_swapImage() { //v3.0
				  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
				   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
				}


	 			//Top : Navigation
						function topMenuShow(sub,obj){
						  var obj = document.getElementById(obj);
						  var src = obj.src;
						  var preSrc = src.substring(0, src.lastIndexOf("."));
						  var subSrc = src.substring(src.lastIndexOf("."));
						  obj.src = preSrc + "over" + subSrc;
						  document.getElementById(sub).className = "hmenu1_on";
						}
						//Top : Navigation
						function topMenuHide(sub,obj){
						  var obj = document.getElementById(obj);
						  var src = obj.src;
						  var preSrc = src.substring(0, src.lastIndexOf("over."));
						  var subSrc = src.substring(src.lastIndexOf("."));
						  obj.src = preSrc + subSrc;
						  document.getElementById(sub).className = "hmenu1_off";
						}
						//Top : Navigation Sub
						function topSubShow(obj){
						  document.getElementById(obj).className="hmenu2_on";
						}
						//Top : Navigation Sub
						function topSubHide(obj){
						  document.getElementById(obj).className = "hmenu2_off";
						}
/*       top navi 시작  //      */