import React from 'react';
import ReactDOM from 'react-dom';
import Header from './App';

let rootElement = document.getElementById('header');
let receivedlink = rootElement.dataset.receivedlink || null;

ReactDOM.render(<Header receivedlink={receivedlink}/>,
    document.getElementById('header'));