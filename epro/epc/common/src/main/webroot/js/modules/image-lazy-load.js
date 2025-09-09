(function (root, factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD. Register as an anonymous module.
		define([], factory);
	} else if (typeof module === 'object' && module.exports) {
		// Node. Does not work with strict CommonJS, but
		// only CommonJS-like environments that support module.exports,
		// like Node.
		module.exports = factory();
	} else {
		// Browser globals (root is window)
		root.imageLazyLoad = factory();
	}
}(typeof self !== 'undefined' ? self : this, function() {
	var lazy = [];

	function setLazy() {
		lazy = document.getElementsByClassName('lazy');
	}

	function registerListener(event, func) {
		if(window.addEventListener) {
			window.addEventListener(event, func);
		} else {
			window.attachEvent('on' + event, func);
		}
	}

	function isInViewport(el) {
		var rect = el.getBoundingClientRect();
		var spaceBetween = window.innerHeight || document.documentElement.clientHeight;

		return (
			rect.bottom >= -spaceBetween &&
			// rect.right >= 0 &&
			rect.top <= (spaceBetween) * 2
				// window.innerHeight ||
				// document.documentElement.clientHeight) &&
			// rect.left <= (
			// 	window.innerWidth ||
			// 	document.documentElement.clientWidth
			// )
		);

	}

	function lazyLoad() {
		lazy = document.getElementsByClassName('lazy');

		for(var i = 0; i < lazy.length; i++) {
			if(isInViewport(lazy[i])) {
				if(lazy[i].getAttribute('data-src')) {
					lazy[i].src = lazy[i].getAttribute('data-src');

					lazy[i].onload = function () {
						this.removeAttribute('data-src');
					};
				}
			}
		}

		cleanLazy();
	}

	function cleanLazy() {
		lazy = Array.prototype.filter.call(
			lazy,
			function (l) {
				return l.getAttribute('data-srs');
			}
		);
	}

	return {
		load: function () {
			lazyLoad();
		},
		setLazy: function () {
			setLazy();
		},
		registerListener : function (eventName, func) {
			switch (eventName) {
				case 'scroll' :
					registerListener('scroll', lazyLoad);
					break;
				case 'load' :
					registerListener('load', func)
					break;
			}
		}
	};
}));