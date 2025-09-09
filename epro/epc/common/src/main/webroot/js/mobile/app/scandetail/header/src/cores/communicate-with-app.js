import getMobilePath from "./get-mobile-path";

const USER_AGENT = window.navigator.userAgent;

const isIOS = () => /iP(hone|od|ad)/.test(USER_AGENT) && !window.MSStream;

const isIOSWithWKWebview = () => {
    return isIOS()
        && window.webkit
        && window.webkit.messageHandlers
        && window.webkit.messageHandlers.LOTTEMART;
};

const communicateWithApp = (methodName, data) => {
    if (window.LOTTEMART && window.LOTTEMART[methodName]) {
        window.LOTTEMART[methodName]();
    } else if (isIOSWithWKWebview()) {
        window.webkit
            .messageHandlers
            .LOTTEMART
            .postMessage(JSON.stringify({
                messageName: methodName,
                messageInfo: (data ? data : {})
            }));
    } else {
        if(document.referrer !== '') {
            window.history.back();
        } else {
            window.location.href = getMobilePath({
                url : '/mobile/corners.do'
            });
        }
    }
};

export default communicateWithApp;