import React from 'react';
import ReactDOM from 'react-dom';
import ProductInfo from './App';

let documentElement = document.getElementById('document');
let rootElement = document.getElementById('productInfo');

let config = JSON.parse(documentElement.dataset.config).ProductInfo;

ReactDOM.render(<ProductInfo {...config}/>, rootElement);