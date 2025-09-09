import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';

let rootElement = document.getElementById('bannersExihibition');
const {productShopRelationList} = JSON.parse(document.getElementById('document').dataset.config);

ReactDOM.render(
    <App productShopRelationList={productShopRelationList}/>,
    rootElement
);
