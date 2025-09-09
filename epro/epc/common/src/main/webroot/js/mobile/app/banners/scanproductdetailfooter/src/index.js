import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';

const rootElement = document.getElementById('scanProductDetailFooterBanner');
const {CT_MOBILE_PROD_DETAIL019 = ''} = JSON.parse(document.getElementById('document').dataset.config);

ReactDOM.render(
    <App html={CT_MOBILE_PROD_DETAIL019}/>,
    rootElement
);