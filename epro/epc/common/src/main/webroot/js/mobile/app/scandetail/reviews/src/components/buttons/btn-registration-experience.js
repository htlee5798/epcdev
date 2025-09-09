import React, {Component} from 'react';
import Pubsub from 'pubsub-js';
import {
    BTN_REGISTRATION
} from '../../i18n/i18n-ko';
import getIsWrote from "../../cores/get-is-wrote";
import getMobilePath from "../../cores/get-mobile-path";

class BtnRegistrationExperience extends Component {
    constructor (props) {
        super(props);

        this.state = {
            urls: {
                review : `/mobile/popup/scan/products/${props.productCd}/reviews/save.do`,
                product : `/mobile/popup/product/ProductReviewPop.do?ProductCd=${props.productCd}&CategoryID=${props.categoryId}&extendTypeCd=EXPERIENCE`
            },
            receivedlink : props.receivedlink
        };
    }

    onClick = (event) => {
        event.preventDefault();

        if(this.state.receivedlink) {
            window.openLoginLayer();
            return;
        }
        let flag = window.global.isLogin(window.location.href);

        if(flag) {
            getIsWrote(this.props.productCd)
                .then((responseData) => {
                    if(window.global.isMember && !window.global.isMember()) {
                        alert('비회원은 작성할 수 없습니다.');
                        return;
                    }

                    let {reviewType} = responseData.data;

                    switch (reviewType) {
                        case 'PRODUCT' :
                            window.location.href = getMobilePath({url : this.state.urls.product});
                            break;
                        case 'EXPERIENCE' :
                            window.location.href = getMobilePath({url : this.state.urls.review});
                            break;
                        case 'EXPERIENCE_NONE':
                            Pubsub.publish('activeToastRegisterIsAlreadyForExperience');
                            break;
                        case 'NONE':
                            Pubsub.publish('activeToastRegisterIsAlready');
                            break;
                        default:
                            break;
                    }
                });
        }
    };
    
    render () {
        return (
            <button
               onClick={this.onClick}
               className="btn-prod-color8">
                {BTN_REGISTRATION}
            </button>
        );
    }
}

export default BtnRegistrationExperience;