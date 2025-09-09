import React from "react";
import PropTypes from 'prop-types';
import Title from "./ui/Title";
import Paging from "./ui/Paging";
import List from "./ui/List";

const App = ({productShopRelationList = []}) => {
    if(productShopRelationList.length === 0) {
        return null;
    }

    return (
        <div className="wrap-planlist-slider">
            <Title/>
            <List productShopRelationList={productShopRelationList}/>
            <Paging productShopRelationList={productShopRelationList}/>
        </div>
    );
};

App.propTypes = {
    productShopRelationList : PropTypes.array
};

export default App;