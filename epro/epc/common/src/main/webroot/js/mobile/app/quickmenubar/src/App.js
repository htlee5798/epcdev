import $ from 'jquery';
import React, { Component } from 'react';
import Pubsub from 'pubsub-js';
import BtnWish from './components/buttons/btn-wish';
import BtnBuy from './components/buttons/btn-buy';
import BtnSoldout from './components/buttons/btn-soldout';
import BtnPhone from './components/buttons/btn-phone';
import BtnBasket from './components/buttons/btn-basket';
import BtnPeriodDelivery from './components/buttons/btn-period-delivery';
import ContentsWrapper from './components/contents/wrapper';
import {
    BTN_ARROW
} from "./i18n/i18n-ko";

class QuickMenubar extends Component {
    constructor(props) {
        super(props);

        this.state = {
            receivedlink : null,
            ...props,
            isSoldOut : false,
            isActiveWish : props.ProductInfo.WISH_CNT !== 0,
            isPeriodDelivery : false,
            pType : props.DeliveryInfo.pType || 'N'
        };
    }

    componentWillMount () {
        const ITEMS = this.state.items;

        this.setState({
            isSoldOut: ITEMS.filter((v) => {
                return v.ABSENCE_YN === 'N';
            }).length === 0
        });
    }

    componentDidMount () {
        Pubsub.subscribe('onActiveContent', (message, event) => {
            this.onActiveContent(event);
        });

        Pubsub.subscribe('checkedPeriodDelivery', (message, type) => {
            this.setState({
                isPeriodDelivery: type
            });
        });
    }

    onActiveContent = (event) => {
        event.preventDefault();

        $('#quickmenu-bar, .icon-common-footertab').toggleClass('active');
    };

    render() {
        const OUTTER_BUTTONS = this.state.isSoldOut ? (
            <div className="wrap-button btnSoldOut-default">
                <BtnSoldout type="outter"/>
            </div>
        ) : (
            <div className="wrap-button btnBuy-default">
                <BtnWish receivedlink={this.state.receivedlink}
                         isActive={this.state.isActiveWish}
                         prodCd={this.state.ProductInfo.PROD_CD}
                         categoryId={this.state.ProductInfo.CategoryID}/>
                <BtnBuy type="outter" {...this.state}/>
            </div>
        );

        const INNTER_BUTTONS = () => {
            if(this.state.isSoldOut) {
                return (
                    <div className="wrap-button btnSoldOut">
                        <BtnSoldout type="inner"/>
                    </div>
                );
            } else if(this.state.isPeriodDelivery) {
                return (
                    <div className="wrap-button btnPeri">
                        <BtnPeriodDelivery {...this.state}/>
                    </div>
                );
            } else {
                return (
                    <div className="wrap-button btnBuy">
                        <BtnPhone/>
                        <BtnBasket {...this.state}/>
                        <BtnBuy type="inner" {...this.state}/>
                    </div>
                )
            }
        };
        return (
            <div>
                <div className="phase-default">{OUTTER_BUTTONS}</div>
                <div className="phase-hide">
                    <ContentsWrapper
                        isPeriodDelivery={this.state.isPeriodDelivery}
                        pType={this.state.pType}
                        {...this.state}/>
                    {INNTER_BUTTONS()}
                </div>
                <a href="#"
                   onClick={this.onActiveContent}
                   className="icon-common-footertab">
                    <i className="icon-common-footertip">
                        {BTN_ARROW}
                    </i>
                </a>
            </div>
        );
    }
}

export default QuickMenubar;
