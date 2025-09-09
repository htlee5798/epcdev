import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import App from './components/App';
import storeFactory from './store/index';
import Cookie from "js-cookie";
import {COOKIE_NAME} from "./consts/const-cookies";

let rootElement = document.getElementById('bannerAppDownload');
let config = JSON.parse(rootElement.dataset.config);

let isVisible = !(Cookie.get(COOKIE_NAME) && Cookie.get(COOKIE_NAME) === 'Y');

const select = (state) => {
    return state.banner.isVisible;
};

const store = storeFactory({
    banner: {
        ...config,
        isVisible
    }
});

const render = () => {
    let currentIsVisible = select(store.getState());

    if(isVisible !== currentIsVisible) {
        Cookie.set(COOKIE_NAME, 'Y', {
            expires: 1
        });
    }

    return (
        ReactDOM.hydrate(
            <Provider store={store}>
                <App/>
            </Provider>,
            rootElement
        )
    );
}
render();
