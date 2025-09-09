const paths = require('./gulp-default-path');

const mainJS = [
	paths.commons + '/fnJsMsg.js',
	paths.commons + '/logout.js',
	paths.commons + '/goProductDetailMobile.js',
	paths.commons + '/goLogin.js',
	paths.commons + '/showNoImage.js',
	paths.commons + '/getReturnUrl.js',
	paths.commons + '/lnbShow.js',
	paths.commons + '/lnbOpen.js',
	paths.commons + '/lnbClose.js',
	paths.commons + '/ban_close.js',
	paths.commons + '/areaTooltipLayerPopup.js',
	paths.commons + '/setAdultReturnUrl.js',
	paths.globals + '/familyJoin.js',
	paths.globals + '/login.js',
	paths.globals + '/isLogin.js',
	paths.globals + '/addBasket.js',
	paths.globals + '/addDirectBasket.js',
	paths.globals + '/storeView.js',

	paths.jqueryPlugins + '/jquery.page-event-log.js',
	paths.jqueryPlugins + '/jquery.deparam.js',
	paths.jqueryPlugins + '/jquery.m.enterprise-member-form-btn.js',
	paths.jqueryPlugins + '/jquery.m.loading-bar.js',
	paths.jqueryPlugins + '/jquery.m.more-bar.js',
	paths.jqueryPlugins + '/jquery.basket.js',
	paths.jqueryPlugins + '/jquery.m.product-option-layer.js',
	paths.jqueryPlugins + '/jquery.m.deal-option-layer.js',
	paths.jqueryPlugins + '/jquery.m.pick-option-layer.js',
	paths.jqueryPlugins + '/jquery.m.app-install-layer.js',
	paths.jqueryPlugins + '/jquery.m.video-layer.js',
	paths.jqueryPlugins + '/jquery.store-layer.js',

	paths.commons + '/callAppScheme.js'
];

module.exports = mainJS;