import React from 'react';
import ReactDOM from 'react-dom';
import Review from './App';

let rootElement = document.getElementById('reviews');
let documentElement = document.getElementById('document');
let config = JSON.parse(rootElement.dataset.config);
let productInfo = JSON.parse(documentElement.dataset.config).ProductInfo;
let receivedlink = rootElement.dataset.receivedlink || null;

ReactDOM.render(<Review productInfo={productInfo} receivedlink={receivedlink} {...config}/>,
    rootElement);
