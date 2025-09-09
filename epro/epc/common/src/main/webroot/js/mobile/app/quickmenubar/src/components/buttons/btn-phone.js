import React, {Component} from 'react';
import {
    BTN_PHONE
} from "../../i18n/i18n-ko";

class BtnPhone extends Component {
    render () {
        return (
            <a href="tel:15772500"
               className="btn-goods-color2">
                <i className="icon-common-footertel">
                    {BTN_PHONE}
                </i>
            </a>
        )
    }
}

export default BtnPhone;