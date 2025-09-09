import React from 'react';
import ReactDOM from 'react-dom';
import PurchaseBenefits from './App';

let documentElement = document.getElementById('document');
let rootElement = document.getElementById('purchasebenefits');

let config = JSON.parse(documentElement.dataset.config);

ReactDOM.render(<PurchaseBenefits {...config}/>, rootElement);
