import React, { Component } from 'react';
import CategoryRank from './components/category-rank';
import {
    TITLE_PRODUCTINFO
} from "./i18n/i18n-ko";
import ProductImage from "./components/product-image";
import ProductInfo from "./components/product-info";

class App extends Component {
    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    renderCategoryRank = () => {
        if(this.state.CATEGORY_NM && this.state.categoryRanking) {
            return (
                <CategoryRank itemName={this.state.CATEGORY_NM}
                              rankInfo={this.state.categoryRanking}/>
            );
        } else {
            return null;
        }
    };

    renderProductImage = () => {
        const {
            MD_SRCMK_CD,
            CategoryID,
            imgQty,
            PROD_NM
        } = this.state;

        return (
            <ProductImage MD_SRCMK_CD={MD_SRCMK_CD}
                          CategoryID={CategoryID}
                          imgQty={imgQty}
                          PROD_NM={PROD_NM}/>
        );
    };

    renderProductInfo = () => {
        const {
            MAX_PROMOTION,
            CURR_SELL_PRC,
            MICD,
            TYPE_CD,
            PROD_NM,
            PROD_CD,
            isAlcoholProduct
        } = this.state;

        return (
            <ProductInfo MAX_PROMOTION={MAX_PROMOTION}
                         CURR_SELL_PRC={CURR_SELL_PRC}
                         MICD={MICD}
                         TYPE_CD={TYPE_CD}
                         PROD_NM={PROD_NM}
                         PROD_CD={PROD_CD}
            			 isAlcoholProduct={isAlcoholProduct}/>
        );
    };

    render() {
        return (
            <div>
                <h2 className="blind">
                    {TITLE_PRODUCTINFO}
                </h2>
                {this.renderCategoryRank()}
                <div className="wrap-prod-info">
                    {this.renderProductImage()}
                    {this.renderProductInfo()}
                </div>
            </div>
        );
    }
}

export default App;
