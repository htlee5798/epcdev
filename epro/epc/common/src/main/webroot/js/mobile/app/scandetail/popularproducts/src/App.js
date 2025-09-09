import React, { Component } from 'react';
import {TITLE_WITH_PRODUCTS, TITLE_POPULAR_PRODUCTS} from "./i18n/i18n-ko";
import List from "./components/lists";

const RenderPopularProducts = (props) => {
    if(!props.lists) {
        return null;
    }

    return (
        <div className="App">
            <h3 className="title">
                { (props.isAlcoholProduct) ? TITLE_WITH_PRODUCTS : TITLE_POPULAR_PRODUCTS }
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
            lists : props.popularProducts,
            isAlcoholProduct : props.isAlcoholProduct
        };
    }

    render() {
        return (
            <RenderPopularProducts {...this.state} />
        );
    }
}

export default App;
