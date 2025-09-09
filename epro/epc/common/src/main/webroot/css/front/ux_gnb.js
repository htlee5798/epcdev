// GNB
(function ($) {
	function initGnb() {
		var UxGnb = $('div.UxGnb'),
			menuItems = UxGnb.find('ul.menu > li, ul.menu-list > li'),
			subDepths = UxGnb.find('div.sub-depth'),
			speed = 200;

		function removeAllOpenedDepths(depth2) {
			if (depth2 && depth2.length) {
				var regexp = new RegExp('depth[0-9]+\\-opened', 'g'),
					match = depth2[0].className.match(regexp);

				if (match) {
					depth2.removeClass(match.join(' '));
				}
			}
		}

		subDepths.bind({
			'open.gnb': function (e) {
				e.stopPropagation();

				var depth2,
					subDepth = $(this),
					isDepth2 = subDepth.hasClass('depth2'),
					depth = (function () {
						if (isDepth2) {
							depth2 = subDepth;
							return 2;
						}
						var parents = subDepth.parents('div.sub-depth');
						depth2 = parents.last();
						return parents.length + 2;
					}());

				if (!subDepth.data('isOpen')) {
					if (isDepth2) {
						subDepth
							.stop(true, true)
							.slideDown(speed, function () {
								subDepth.data('isOpen', true);
							});
					} else {
						depth2.addClass('depth' + depth + '-opened');
						subDepth.data('isOpen', true);
					}
				}
			},
			'close.gnb mouseleave.gnb': function (e) {
				e.stopPropagation();

				var depth2,
					subDepth = $(this),
					isDepth2 = subDepth.hasClass('depth2'),
					depth = (function () {
						if (isDepth2) {
							depth2 = subDepth;
							return 2;
						}
						var parents = subDepth.parents('div.sub-depth');
						depth2 = parents.last();
						return parents.length + 2;
					}());

				if (subDepth.data('isOpen')) {
					if (isDepth2) {
						subDepth
							.stop(true, true)
							.slideUp(speed, function () {
								subDepth.data('isOpen', false);
							});
						
						removeAllOpenedDepths(depth2);
					} else {
						depth2.removeClass('depth' + depth + '-opened');
						subDepth.data('isOpen', false);
					}
				}
			}
		});

		menuItems.bind({
			'mouseenter.gnb': function (e) {
				var menuItem = $(this),
					subDepth = menuItem.find('> div.sub-depth');

				menuItem.addClass('sub-depth-opened');
				subDepth.trigger('open.gnb');
			},
			'mouseleave.gnb': function (e) {
				var menuItem = $(this),
					subDepth = menuItem.find('> div.sub-depth');

				menuItem.removeClass('sub-depth-opened');
				subDepth.trigger('close.gnb');
			}
		});
	}

	$(function () {
		initGnb();
	});
}(jQuery));