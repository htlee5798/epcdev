import $ from 'jquery';
import Pubsub from "pubsub-js";
import React, {Component} from 'react';
import {
    BTN_BASKET,
    ERROR_MESSAGE_ONLY_MEMBER,
    ERROR_MESSAGE_ONLY_DIRECT_BUY,
    ERROR_MESSAGE_DONT_DELIVERY,
    BTN_BUT_TEXT_RESERVATION
} from "../../i18n/i18n-ko";
import {ONLINE_PROD_TYPE_CD_RESERVE} from "../../const/online-product-code";
import actionMaterial from '../../cores/action-material';

class BtnBasket extends Component {
    constructor(props) {
        super(props);

        this.state = {
            ...props,
            mallDivnCd: props.mallDivnCd ? props.mallDivnCd : '00001',
            isReservation : props.ProductInfo.ONLINE_PROD_TYPE_CD === ONLINE_PROD_TYPE_CD_RESERVE
            && props.ProductInfo.RSERV_ORD_PSBT_YN === 'Y'
        };
    }

    onBasket = (event) => {
        event.preventDefault();

        if(this.state.receivedlink) {
            window.openLoginLayer();
            return;
        }

        const PRODUCT_INFO = this.state.ProductInfo;

        let promise = actionMaterial(event);

        if(this.isValidate(PRODUCT_INFO)) {
            promise.then(() => {
                Pubsub.publish('onSubmitBasket');
            });
        }
    };

    isValidate = (
        {
            PERI_DELI_YN,
            isManufacturingProduct,
            CategoryID,
            PROD_CD,
            liveFish,
            ONLINE_PROD_TYPE_CD,
            DELI_TYPE,
            MD_SRCMK_CD
        }
    ) => {
        let returnValue = true;
        if(window.global.isLogin(window.location.href)) {
            if(ONLINE_PROD_TYPE_CD !== '05' && DELI_TYPE === '') {
                alert(ERROR_MESSAGE_DONT_DELIVERY);
                returnValue = false;
            } else if(MD_SRCMK_CD === null || MD_SRCMK_CD === '') {
                returnValue = false;
            } else if (liveFish === 'Y') {
                alert(ERROR_MESSAGE_ONLY_DIRECT_BUY);
                returnValue = false;
            } else if (PERI_DELI_YN === 'Y') {
                if ($.utils.config('Member_yn') === 'false'
                    && $.utils.config('GuestMember_yn') === 'true') {
                    alert(ERROR_MESSAGE_ONLY_MEMBER);
                    returnValue = false;
                }
            }
        } else {
            returnValue = false;
        }

        return returnValue;
    };

    render () {
        return (
            <button type="button"
                    onClick={this.onBasket}
                    className={this.state.isReservation
                        ? "btn-goods-color1" : "btn-goods-color9" }>
                {this.state.isReservation ? BTN_BUT_TEXT_RESERVATION :BTN_BASKET}
            </button>
        );
    }
}

export default BtnBasket;