import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import App from './components/App';
import storeFactory from './store/index';

let rootElement = document.getElementById('notice');
let documentElement = document.getElementById('document');

let {
    forceStrCd = '',
    forceStrNm = '',
    LoginYN = 'N',
    STR_CD = ''
} = JSON.parse(documentElement.dataset.config).ProductInfo;

const store = storeFactory({
    notice: {
        forceStrCd,
        forceStrNm,
        LoginYN : LoginYN === null ? 'N' : LoginYN,
        STR_CD,
        active : true
    }
});

const render = () => {
    return (
        ReactDOM.hydrate(
            <Provider store={store}>
                <App/>
            </Provider>,
            rootElement
        )
    );
};

render();