import $ from 'jquery';
import React, { Component } from 'react';
import {
    ONLINE_PROD_TYPE_CD_CHOICE,
    ONLINE_PROD_TYPE_CD_DEAL,
    ONLINE_PROD_TYPE_CD_MAKE
} from '../../const/online-product-code';
import {
    CURRENCY_UNIT_WON,
    TOTAL_PRICE_TEXT,
    TEXT_FREE_DELIVERY,
    TEXT_PERIOD_DELIVERY
} from '../../i18n/i18n-ko';
import Pick from "./pick";
import Deal from './deal';
import Product from './product';
import ManuFacturing from './manufacturing';
import setComma from '../../cores/set-comma';
import Pubsub from 'pubsub-js';

class Wrapper extends Component {
    constructor(props) {
        super(props);

        this.state = {
            ...props,
            isPickGOODS : props.ProductInfo.ONLINE_PROD_TYPE_CD === ONLINE_PROD_TYPE_CD_CHOICE,
            isFreeDelivery : props.ProductInfo.FREE_DEL === 'Y',
            isPeriodDelivery : props.isPeriodDelivery,
            isCustomOrder : props.ProductInfo.ONLINE_PROD_TYPE_CD === ONLINE_PROD_TYPE_CD_MAKE,
            pType : props.pType,
            totalPrice : 0
        };
    }

    componentWillReceiveProps(nextProps) {
        if(this.state.isPeriodDelivery !== nextProps.isPeriodDelivery) {
            this.setState({
                isPeriodDelivery: nextProps.isPeriodDelivery
            });
        }
    }

    componentDidMount () {
        Pubsub.subscribe('setTotalPrice', (message, totalPrice) => {
            this.setState({
                totalPrice
            });
        });
    }

    onCheckedPeriodDelivery = (event) => {
        Pubsub.publish('checkedPeriodDelivery', $(event.target).is(':checked'));
    };

    renderContent = () => {
        const PRODUCT_INFO = this.state.ProductInfo;

        if(this.state.isPickGOODS) {
            return (
                <Pick prodCd={PRODUCT_INFO.PROD_CD}
                      isPeriodDelivery={this.state.isPeriodDelivery}
                      pType={this.state.pType}
                      categoryId={PRODUCT_INFO.CategoryID}/>
            )
        } else if(PRODUCT_INFO.isManufacturingProduct || this.state.isCustomOrder) {
            return (
                <ManuFacturing productInfo={PRODUCT_INFO}
                               prodCd={PRODUCT_INFO.PROD_CD}
                               categoryId={PRODUCT_INFO.CategoryID}
                               pType={this.state.pType}
                               isPeriodDelivery={this.state.isPeriodDelivery}
                               prodOptionDesc={PRODUCT_INFO.PROD_OPTION_DESC}
                               hopeDeliPsbtdd={PRODUCT_INFO.HOPE_DELI_PSBT_DD}/>
            )
        } else if(PRODUCT_INFO.ONLINE_PROD_TYPE_CD === ONLINE_PROD_TYPE_CD_DEAL) {
            return <Deal/>
        } else {
            return <Product productInfo={PRODUCT_INFO}
                            prodCd={PRODUCT_INFO.PROD_CD}
                            isPeriodDelivery={this.state.isPeriodDelivery}
                            categoryId={PRODUCT_INFO.CategoryID}
                            pType={this.state.pType}/>
        }
    };

    render () {
        return (
            <div className="wrap-optionselect">
                {this.renderContent()}
                <div className="result-option lie-table">
                    <div className="title left">
                        {TOTAL_PRICE_TEXT}
                    </div>
                    <div className="right">
                        <em className="number-point">{setComma(this.state.totalPrice)}</em>
                        {CURRENCY_UNIT_WON}
                    </div>
                </div>
                {this.state.isFreeDelivery &&
                    <div className="result-option lie-table">
                        <div className="right">
                            <i className="freeship">
                                {TEXT_FREE_DELIVERY}
                            </i>
                        </div>
                    </div>
                }
                {this.state.ProductInfo.PERI_DELI_YN === 'Y' &&
                    <div className="result-option type-period lie-table">
                        <div className="left">
                            <label>
                                <input type="checkbox"
                                       onChange={this.onCheckedPeriodDelivery}
                                       name="periDeliYn"/>
                                <span>
                                    {TEXT_PERIOD_DELIVERY}
                                </span>
                            </label>
                        </div>
                    </div>
                }
            </div>
        );
    }
}

export default Wrapper;