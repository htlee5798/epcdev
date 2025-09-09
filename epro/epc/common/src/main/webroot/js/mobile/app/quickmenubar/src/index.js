import React from 'react';
import ReactDOM from 'react-dom';
import QuickMenubar from './App';

let rootElement = document.getElementById('quickmenu-bar');
let documentElement = document.getElementById('document');
let config = JSON.parse(documentElement.dataset.config);
let receivedlink = rootElement.dataset.receivedlink || null;

ReactDOM.render(<QuickMenubar {...config} receivedlink={receivedlink}/>,
    rootElement);
