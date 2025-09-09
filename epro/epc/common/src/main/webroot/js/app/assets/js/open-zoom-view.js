import {
    GOODS_ZOOM_AOS_VERSION,
    GOODS_ZOOM_IOS_VERSION
} from "./const-app-version";

const VIEW_TYPE_TITLE = {
    defaults : '상품 이미지 확대보기',
    other : '상품정보 확대보기'
};

const APP_VERSION = {
    ios: GOODS_ZOOM_IOS_VERSION,
    aos: GOODS_ZOOM_AOS_VERSION
};

export default function (
    event,
    {
        title = VIEW_TYPE_TITLE.defaults,
        currentUserAppVersion = $.utils.isiOSLotteMartApp() || $.utils.isAndroidLotteMartApp()
            ? $.utils.getAppVersion()
            : '',
        appVersion = $.utils.isiOSLotteMartApp()
            ? APP_VERSION.ios
            : ($.utils.isAndroidLotteMartApp()
                ? APP_VERSION.aos
                : '')
    }
) {
    let $target = $(event.currentTarget);
    let openUrl = $target.attr('href');

    if(appVersion === '' || currentUserAppVersion === '') {
        location.href = openUrl;
    } else if ($.utils.isIOS() && window.webkit && window.webkit.messageHandlers) {
        let postJSONString = JSON.stringify({
            messageName: 'showZoomViewUrl',
            messageInfo: {title, openUrl}
        });

        window.webkit.messageHandlers.LOTTEMART.postMessage(postJSONString);
    } else if (window.LOTTEMART && window.LOTTEMART['showZoomViewUrl']
        && $.utils.checkAppVersion(currentUserAppVersion, appVersion)) {
        window.LOTTEMART.showZoomViewUrl(title, openUrl);
    }
};