import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import App from './components/App';
import storeFactory from './store/index';

let {CategoryID, MD_SRCMK_CD} = JSON.parse(
    document
        .getElementById('document')
        .dataset
        .config
).ProductInfo;

const store = storeFactory({
    sideBar : {
        categoryId : CategoryID,
        mdSrcmkCd : MD_SRCMK_CD,
        active : false
    }
});

const render = () =>
    ReactDOM.hydrate(
        <Provider store={store}>
            <App/>
        </Provider>,
        document.getElementById('pagemovetop')
    );

render();