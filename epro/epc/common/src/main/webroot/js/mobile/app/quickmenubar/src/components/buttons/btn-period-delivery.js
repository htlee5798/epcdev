import $ from 'jquery';
import React, {Component} from 'react';
import {
    BTN_PERIOD_DELIVERY,
    ERROR_MESSAGE_ONLY_MEMBER,
    ERROR_MESSAGE_ONLY_DIRECT_BUY,
    ERROR_MESSAGE_DONT_DELIVERY,
} from "../../i18n/i18n-ko";
import actionMaterial from "../../cores/action-material";
import Pubsub from "pubsub-js";

class BtnPeriodDelivery extends Component {
    constructor(props) {
        super(props);

        this.state = {
            ...props
        };
    }

    onBasket = (event) => {
        event.preventDefault();

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
                    className="btn-goods-color9 basket">
                {BTN_PERIOD_DELIVERY}
            </button>
        )
    }
}

export default BtnPeriodDelivery;