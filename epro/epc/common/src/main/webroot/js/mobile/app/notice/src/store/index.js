import {
    createStore,
    combineReducers,
    applyMiddleware
} from 'redux';
import {composeWithDevTools} from 'redux-devtools-extension';
import {notice} from "./reducers";

const stateData = {
    notice : {
        forceStrCd : '',
        forceStrNm: '',
        LoginYN: 'N',
        STR_CD : '',
        active : true
    }
};

const logger = store => next => action => {
    let result;

    // console.groupCollapsed('디스패칭 :', action.type);
    // console.log('이전 상태 :', store.getState());
    // console.log('액선 :', action);

    result = next(action);

    // console.log('다음 상태 : ', store.getState());
    // console.groupEnd();
};

const storeFactory = (initialState=stateData) =>
    applyMiddleware(logger)(createStore)(
        combineReducers({notice}),
        initialState,
        composeWithDevTools()
    )

export default storeFactory;