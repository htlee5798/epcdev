import {
    APP_STORE,
    PLAY_STORE
} from "../consts/market-urls";

const goMarket = (
    {
        userAgent
    }) => {
    let href = '';

    if(userAgent.match(/iPhone|iPad/)) {
        href = APP_STORE;
    } else if(userAgent.match(/Android/)) {
        href = PLAY_STORE;
    } else {
        alert('지원하지 않는 기기 입니다.');
    }

    window.location.href = href;
};

export default goMarket;