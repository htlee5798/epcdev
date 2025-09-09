import React, {Component} from 'react';
import {
    UNIT,
    TEXT_CURRPRICE,
    TEXT_MAXPURCHASE,
    TEXT_PERIOD, TITLE_POPUP_MAXPURCHASE, BTN_CLOSE, DESCRIPTION_MAXPURCHASE
} from "../i18n/i18n-ko";
import setComma from '../cores/set-comma';
import getMobilePath from '../cores/get-mobile-path';
import {TAG_NAME_EXPERIENCE} from "../i18n/i18n-ko";

class ProductInfo extends Component {
    static defaultProps = {
        PROD_NM: '',
        active : false,
        strHtml : ''
    };

    constructor(props) {
        super(props);
        this.state = Object.assign({}, props);
    }

    componentDidMount () {
        if(window.ga && this.state.TYPE_CD === 'EXPERIENCE') {
            window.ga('set','dimension16', 'EXPERIENCE_PROD');
        }
    }

    onToggle = (event) => {
        event.preventDefault();

        this.setState({
            active: !this.state.active
        });
    }

    onClose = (event) => {
        event.preventDefault();

        this.setState({
            active: false
        });
    }

    renderPrice = () => {
        let maxPurchase = this.state.MAX_PROMOTION;
        let currSellPrc = this.state.CURR_SELL_PRC;
        let layerClassName = `layerpopup-type1 layerpopup-type1-ver2 ${this.state.active ? "active" :""}`;
        let btnClassName = `icon-common-question ${this.state.active ? "active" :""}`;
        return (
            <div>
                <p className="prod-price">
                    {TEXT_CURRPRICE} <em className="prod-price-num">{setComma(currSellPrc)}</em>{UNIT}
                </p>
                {maxPurchase > 0 && maxPurchase < currSellPrc &&
                <div className="prod-price wrap-prod-layerpopup">
                    <span className="point1">
                        {this.state.MICD !== '' && this.state.MICD !== null ? TEXT_PERIOD:TEXT_MAXPURCHASE} <em className="prod-price-num">
                        {setComma(maxPurchase)}</em>{UNIT}
                    </span>
                    <button type="button"
                            onClick={this.onToggle}
                            className={btnClassName}>?</button>
                    <article className={layerClassName}>
                        <header className="header">
                            <h3 className="title">
                                {TITLE_POPUP_MAXPURCHASE}
                            </h3>
                            <button className="icon-close"
                                    onClick={this.onClose}>
                                {BTN_CLOSE}
                            </button>
                        </header>
                        <div className="maximum-benefit-info">
                            <p className="info-message">
                                {DESCRIPTION_MAXPURCHASE}
                            </p>
                            <dl className="benefit-price" id="benefit-price"></dl>
                        </div>
                    </article>
                </div>
                }
            </div>
        )
    };

    render () {
        return (
            <div className="prod-info">
                {this.state.TYPE_CD === 'EXPERIENCE' &&
                <div className="product-tag">
                    <i className="tag-product type1">
                        {TAG_NAME_EXPERIENCE}
                    </i>
                </div>
                }
                
                <p className="prod-name">
                	{ (!this.state.isAlcoholProduct) 
                		? <a href={getMobilePath({url : `/mobile/cate/PMWMCAT0004_New.do?ProductCD=${this.state.PROD_CD}`})}> {decodeURIComponent(this.state.PROD_NM)} </a> 
                		: decodeURIComponent(this.state.PROD_NM) }
                </p>
                {this.renderPrice()}
                {this.state.isAlcoholProduct && <p class="prod-warn">※ 매장에서만 구매 가능한 상품으로, <br/>매장의 실제 판매가와 다를 수 있습니다.</p>}
            </div>
        )
    }
}

export default ProductInfo;