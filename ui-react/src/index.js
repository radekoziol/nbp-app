import React from 'react';
import ReactDOM from 'react-dom';
import {applyMiddleware, createStore} from 'redux';
import promise from 'redux-promise';


const createStoreWithMiddleware = applyMiddleware(promise)(createStore);

ReactDOM.render(
    document.querySelector('.container')
);
