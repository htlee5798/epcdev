const LOTTEMART_AGENT_FOR_IOS = 'LOTTEMART-APP-SHOPPING-IOS';
const LOTTEMART_AGENT_FOR_ANDROID = 'LOTTEMART-APP-SHOPPING-ANDROID';
const ITUNES_STORE = 'http://itunes.apple.com/app/id493616309?mt=8';
const ANDRODI_MAKET = 'market://details?id=com.lottemart.shopping';

export default function () {
    const userAgent = navigator.userAgent;

    if(userAgent.toUpperCase().indexOf(LOTTEMART_AGENT_FOR_IOS) > -1
        || userAgent.toUpperCase().indexOf(LOTTEMART_AGENT_FOR_ANDROID) > -1) {
        return;
    }

    if((userAgent.indexOf("iPhone") > -1)
        || (userAgent.indexOf("iPad") > -1)) {
        window.location.href = ITUNES_STORE;
    } else if(userAgent.indexOf("Android") > -1) {
        window.location.href = ANDRODI_MAKET;
    } else {
        alert("지원하지 않는 기기 입니다.");
    }
}