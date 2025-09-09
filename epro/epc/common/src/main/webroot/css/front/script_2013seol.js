function mainTabView(objName,type,auto,ran){
	type = type || "base";
	auto = auto || "base";
	ran  = ran  || "base";
	$(objName)[0].repeat=true;
	$(objName)[0].autoRepeat = null;
	var ran_num = (ran == "ran") ? Math.floor(Math.random()*($(objName).children("dt").length)) : 0;
	$(objName).children("dt").each(function(index){
		if(type!="png") $(this).next().css({"opacity":0,"background-color":"#ffffff"});
		$(this).mouseover(function(){
			$(this).parent().children("dd").stop();
			if(type=="base"){
				$(this).parent().children("dt").not(".current").next().stop().css({"display":"none","opacity":"0"});
				$(this).next().css("display","block").animate({opacity:1},400,function(){$(this).addClass("current").css("display","block")}).andSelf().addClass("current").parent().children("dt.current").not(this).removeClass("current").next().animate({opacity:0},400,function(){$(this).removeClass("current").css("display","none")});
			}else if(type=="png"){
				$(this).next().css("display","block").andSelf().addClass("current").parent().children("dt.current").not(this).removeClass("current").next().css("display","none").removeClass("current");
			}
			$(this).parent()[0].curNum = index;
		});
		if(index==ran_num) $(this).mouseover();
	});
	if($(objName).children("dt").length>1 && auto=="auto"){
		aotuRepeat2($(objName)[0],$(objName).children("dt"));
		$(objName).mouseover(function(){clearTimeout($(objName)[0].autoRepeat);})
		$(objName).mouseout(function(){aotuRepeat2($(objName)[0],$(objName).children("dt")); })
	}
}
function tabView(current,tabList){
	var item = (current.nodeName.toUpperCase()=="a") ? $(current).parent().find("a") : $(current).parent().children();
	item.each(function(){
		if(current==this){
			this.className += " current";
			if(this.href.split("#").length>1) util.show(this.href.split("#")[1]);
		}else{
			this.className = this.className.replace(/current/gi,"");
			if(this.href.split("#").length>1) util.hide(this.href.split("#")[1]);
		}
	});
}
function tabView2(current,cName,bName,act){
	$(current).parentsUntil("."+bName).parent().find("."+cName).each(function(index){
		if(current==this.getElementsByTagName("a")[0]){
			this.className += " current";
			if(this.getElementsByTagName("a")[0].href.split("#").length>1){
				util.$(this.getElementsByTagName("a")[0].href.split("#")[1]).style.height = "auto";
				util.show(this.getElementsByTagName("a")[0].href.split("#")[1]);
			}
		}else{
			this.className = this.className.replace(/current/gi,"");
			if(this.getElementsByTagName("a")[0].href.split("#").length>1) util.hide(this.getElementsByTagName("a")[0].href.split("#")[1]);
		}
	});
}
function tabRollAuto(contain,item,auto,ran){
	auto = auto || "base";
	ran  = ran  || "base";
	contain[0].autoRepeat = null;
	contain[0].curNum = (ran == "ran") ? Math.floor(Math.random()*(item.length)) : 0;
	item.each(function(index){
		this.oldOverAct = this.onmouseover;
		$(this).mouseover(function(){
			this.oldOverAct();
			contain[0].curNum = index;
		})
		if(index==contain[0].curNum) this.onmouseover();
	});

	if(item.length>1 && auto=="auto"){
		aotuRepeat2(contain[0],item);
		contain.mouseover(function(){clearTimeout(contain[0].autoRepeat);})
		contain.mouseout(function(){aotuRepeat2(contain[0],item); })
	}
}
function faqTyView(objName,curNum){
	$(objName).children("dt").each(function(index){
		$(this).click(function(){
			$(this).parent().find("dt.current").not(this).next().andSelf().removeClass("current");
			$(this).next().andSelf().toggleClass("current");
		});
		if(curNum) if(index==curNum) $(this).click();
	});
}
function faqTyView2(curTr,cssQ,cssA){
	curTr.parent().find("."+cssQ).not(curTr).removeClass(cssQ).next().hide().removeClass(cssA)
	curTr.toggleClass(cssQ).next().toggle().toggleClass(cssA);
}
function bannerNext(objName,type){
	type = type || "base";
	var obj = $(objName);
	//.header .headAdArea
	
	if(obj.find("li").length==1){
		obj.find("li").css("display","block");
	}else if(obj.find("li").length>1){
		obj.append("<span class='prevNext'><span class='prev'>prev</span><span class='next'>next</span></span>");
		if(type=="png"){
			if (objName == '.header .headAdArea') {
				var result = Math.floor(Math.random() * obj.find("li").length) + 1;
				obj.find("li:nth-child(" + result + ")").addClass("current");
			} else {
				obj.find("li:first-child").addClass("current");
			}
			obj.find(".prev").click(function(){
				if(obj.find(".current").is(":first-child")) obj.find(".current").removeClass("current").end().find("li:last-child").addClass("current");
				else obj.find(".current").removeClass("current").prev().addClass("current");
			})
			obj.find(".next").click(function(){
				if(obj.find(".current").is(":last-child")) obj.find(".current").removeClass("current").end().find("li:first-child").addClass("current");
				else obj.find(".current").removeClass("current").next().addClass("current");
			})
		}else if(type=="base"){
			obj.find("li:first-child").addClass("current").css("opacity",1);
			obj.find(".prev").click(function(){
				if(obj.find(".current").is(":first-child")) obj.find(".current").animate({opacity:0},200,function(){$(this).removeClass("current")}).end().find("li:last-child").addClass("current").animate({opacity:1},200,function(){});
				else obj.find(".current").animate({opacity:0},200,function(){$(this).removeClass("current")}).prev().addClass("current").animate({opacity:1},200,function(){});
			})
			obj.find(".next").click(function(){
				if(obj.find(".current").is(":last-child")) obj.find(".current").animate({opacity:0},200,function(){$(this).removeClass("current")}).end().find("li:first-child").addClass("current").animate({opacity:1},200,function(){});
				else obj.find(".current").animate({opacity:0},200,function(){$(this).removeClass("current")}).next().addClass("current").animate({opacity:1},200,function(){});
			})
		}
	}
}
function aotuRepeat2(contain,item){
	contain.curNum = parseFloat(contain.curNum || 0);
	contain.nextNum = (contain.curNum+1>item.length-1) ? 0 : contain.curNum+1;
	contain.autoRepeat = setTimeout(function(){ contain.curNum = contain.nextNum; $(item[contain.nextNum]).mouseover(); aotuRepeat2(contain,item); },3000);
}