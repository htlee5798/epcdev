import React from 'react';
import ReactDOM from 'react-dom';
import PopularProducts from './App';

let documentElement = document.getElementById('document');
let rootElement = document.getElementById('popularProducts');

let config = JSON.parse(documentElement.dataset.config);

ReactDOM.render(<PopularProducts {...config}/>, rootElement);
