/**
* lotteMart - dwRolling
* @package {dwRolling}
* @version 20130306 (_khv)
* @modify 20130328_2 (_khv)
* @modify 20130415 (_khv)
* @modify 20131218 (_RaiN)
*/
if (dwRolling) throw Error('[ixError] "dwRolling"\uac00 \uc774\ubbf8 \uc874\uc7ac\ud558\uc5ec \ucda9\ub3cc\uc774 \ubc1c\uc0dd!');
	var dwRolling = function() {
		return {init: function(j) {
		dwRolling.Main(j)
	}}
}();
dwRolling.Main = function(j) {
	function l(a) {
		location.href = dwRoList[a.currentTarget.idx].link
	}
	function g(a) {
		switch (a.type) {
			case "click":
				0 == a.currentTarget.idx ? 0 >= f ? f = e - 1 : f-- : f >= e - 1 ? f = 0 : f++;
				h("mouseenter");
				break;
			case "mouseenter":
				i.reset();
				break;
			case "mouseleave":
				i.start()
		}
	}
	function h(a) {
		var b;
		for (b = 0; b < e; ++b)
			m[b].over("mouseleave");
		m[f].over(a)
	}
	function n(a) {
		switch (a.type) {
			case "mouseenter":
				i.reset();
				break;
			case "mouseleave":
				i.start()
		}
	}
	var c = ixBand,
		k = c.$utils,
		d = c("#right_contents").selectorAll(".dontwory")[0], e = dwRoList.length,
		m = [], f = 0, a = "", b;
		for (b = 0; b < e; ++b)
			a = "y" == dwRoList[b].tong ? a + '\t<div class="item other-bg">' : a + '\t<div class="item">', a += '\t\t\t<div class="product">', 
			a += '\t\t\t\t<div class="pImg"><div class="saleTxt"><span class="t_roman">' + dwRoList[b].saleTxt + '</span><span class="per">%</span></div><img src="' + dwRoList[b].pImg + '" /></div>', a += '\t\t\t\t<div class="pTxt">' + dwRoList[b].title + "</div>", a += '\t\t\t<div class="priceWrap">', a += '\t\t\t\t<div class="beforePrice"><span class="t_roman">' + dwRoList[b].beforePrice + "\uc6d0</span></div>", a += '\t\t\t\t<div class="afterPrice"><span class="t_roman">' + dwRoList[b].afterPrice + "</span>\uc6d0</div>", a += "\t\t\t</div>", a += "\t\t\t</div>", a += "\t</div>";
			d.innerHTML = a + '<div class="arrowWrap"><a href="javascript:;" class="leftArrow"><span>이전</span></a><a href="javascript:;" class="rightArrow"><span>다음</span></a></div>';

			d = c("#right_contents").selectorAll(".arrowWrap > a");

		if (1 < e)
			for (b = 0; b < e; ++b)
				m[b] = new dwRolling.visual(b, {onOver: n});
		else
			d[0].style.display = "none", d[1].style.display = "none";
			c(d[0]).addEvent("mouseenter", g);
			c(d[0]).addEvent("mouseleave", g);
			c(d[0]).addEvent("click", g);
			d[0].idx = 0;
			c(d[1]).addEvent("mouseenter", g);
			c(d[1]).addEvent("mouseleave", g);
			c(d[1]).addEvent("click", g);
			d[1].idx = 1;
			d = c("#right_contents").selectorAll(".beforePrice > .t_roman");
			c("#right_contents").selectorAll(".line > img");
			a = c("#right_contents").selectorAll(".item");
		for (b = 0; b < e; ++b) {
			var o = $(d[b]).width() + 4;
				//d[b].style.width = o + "px";
				c(a[b]).buttonMode(!0);
				c(a[b]).addEvent("click", l);
				a[b].idx = b
	}
	var i = new k.Timer(1E3 * j, 1, {onTimer: function() {
			f == e - 1 ? f = 0 : f++;
			h("mouseenter")
		},onComplete: function() {
			i.reset().start()
	}});
		1 < e && (h("mouseenter"), i.start())
	};
	dwRolling.visual = function(j, l) {
	function g(c) {
		l.onOver.call(this, {type: c.type})
	}
	var h = ixBand, n = h.$utils, c = h("right_contents").selectorAll(".dontwory .item")[j], k = !1;
	h(c).addEvent("mouseenter", g);
	h(c).addEvent("mouseleave", g);
	var d = new n.TweenCore(0.5, 0, 1, {onTween: function(d) {
			h(c).setOpacity(d.currentValue)
		},onComplete: function() {
			k || (c.style.display = "none")
		}});
	this.over = function(e) {
		c.style.display = "block";
		switch (e) {
			case "mouseenter":
				d.stop().setValue(0, 1).start();
				k = !0;
				break;
			case "mouseleave":
				d.stop().setValue(null, 
				0).start(), k = !1
		}
	}
};
