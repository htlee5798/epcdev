import $ from 'jquery';
import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {
    BTN_BUY_TEXT_OUTTER,
    BTN_BUY_TEXT_INNER,
    ERROR_MESSAGE_ONLY_MEMBER,
    ERROR_MESSAGE_DONT_DELIVERY
} from "../../i18n/i18n-ko";
import actionMaterial from '../../cores/action-material';

class BtnBuy extends Component {
    constructor(props) {
        super(props);

        this.state = {
            ...props,
            type: props.type,
            mallDivnCd : props.mallDivnCd ? props.mallDivnCd : '00001'
        };
    }

    onBuy = (e) => {
        e.preventDefault();
        const PRODUCT_INFO = this.state.ProductInfo;

        let promise = actionMaterial(e);

        if(this.state.type === 'outter') {
            promise.then(() => {
                Pubsub.publish('onActiveContent', e);
            });
        } else {
            if(this.state.receivedlink) {
                window.openLoginLayer();
                return;
            }
            if(this.isValidate(PRODUCT_INFO)) {
                promise.then(() => {
                    Pubsub.publish('onSubmitBuy');
                });
            }
        }
    };

    isValidate = (
        {
            PERI_DELI_YN,
            isManufacturingProduct,
            CategoryID,
            PROD_CD,
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
            }else if (PERI_DELI_YN === 'Y') {
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

    render() {
        return (
            <button type="button"
                    onClick={this.onBuy}
                    className="btn-goods-color6">
                {this.state.type === 'outter' ? BTN_BUY_TEXT_OUTTER : BTN_BUY_TEXT_INNER}
            </button>
        )
    }

}

export default BtnBuy;