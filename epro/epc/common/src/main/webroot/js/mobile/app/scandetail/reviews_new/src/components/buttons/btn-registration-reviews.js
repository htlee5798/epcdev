import React, {Component} from 'react';
import {BTN_REGISTRATION_REVIEWS} from "../../i18n/i18n-ko";
import isMember from '../../cores/is-member';

class BtnRegistrationReviews extends Component {
    constructor(props) {
        super();

        this.state = {
            url: `/mobile/popup/product/ProductReviewPop.do?ProductCd=${props.productCd}&CategoryID=${props.categoryId}&extendTypeCd=EXPERIENCE`,
            receivedlink : props.receivedlink
        };
    }
    onClick = (event) => {
        event.preventDefault();

        if(this.state.receivedlink){
            window.openLoginLayer();
            return;
        }

        let flag = window.global.isLogin(window.location.href);

        if(flag) {
            isMember(this.state);
        }
    };

    render() {
        let link = this.state.receivedlink ? this.state.receivedlink : '#';
        return (
            <a href={link}
               onClick={this.onClick}
               className="btn-prod-color8">
                <i className="icon-prod-pencil1"></i>
                {BTN_REGISTRATION_REVIEWS}
            </a>
        )
    }

}

export default BtnRegistrationReviews;