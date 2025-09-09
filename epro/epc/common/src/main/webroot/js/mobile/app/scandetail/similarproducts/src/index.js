import React from 'react';
import ReactDOM from 'react-dom';
import SimilarProducts from './App';

let documentElement = document.getElementById('document');
let rootElement = document.getElementById('similarProducts');

let config = JSON.parse(documentElement.dataset.config);

ReactDOM.render(<SimilarProducts {...config}/>, rootElement);
