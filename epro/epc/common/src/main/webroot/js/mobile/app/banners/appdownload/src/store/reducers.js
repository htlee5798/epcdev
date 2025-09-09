import C from '../constant';
import Cookie from 'js-cookie';
import goMarket from "../cores/go-market";
import {COOKIE_NAME} from "../consts/const-cookies";

const banner = (state = {}, action) => {
    switch (action.type) {
        case C.GO_MARKET :
            return goMarket(window.navigator);
        case C.REMOVE_BANNER :
            Cookie.set(COOKIE_NAME, 'Y', {
                expires: 1
            });
            return Object.assign({}, state, {
                isVisible: false
            });
        default:
            return state;
    }
};

export default banner;