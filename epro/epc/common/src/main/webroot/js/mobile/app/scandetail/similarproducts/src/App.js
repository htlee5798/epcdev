import React, { Component } from 'react';
import {
    TITLE_SIMILAR_PRODUCTS
} from "./i18n/i18n-ko";
import List from "./components/lists";

const RenderSimilarProducts = (props) => {
    if(!props.lists) {
        return null;
    }

    return (
        <div className="App">
            <h3 className="title">
                {TITLE_SIMILAR_PRODUCTS}
            </h3>
            <div className="type-groupgoods">
                <List lists={props.lists}/>
            </div>
        </div>
    );
};

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            lists : props.similarProducts
        };
    }

    render() {
       return (
           <RenderSimilarProducts {...this.state}/>
       )
    }
}

export default App;
