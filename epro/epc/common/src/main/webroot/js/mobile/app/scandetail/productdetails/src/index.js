import React from 'react';
import ReactDOM from 'react-dom';
import ProductDetail from './App';

let rootElement = document.getElementById('productdetails');
let documentElement = document.getElementById('document');
let config = JSON.parse(documentElement.dataset.config);

ReactDOM.render(<ProductDetail {...config}/>, rootElement);