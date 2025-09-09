import React, {Component} from 'react';
import {
    TITLE_SHIPPING_FEE,
    BTN_SAVE_DELI_PRICE_YN,
    DELIVERY_ADDR_KIND_NM,
    TEXT_FREE_DELIVERY
} from '../i18n/i18n-ko';
import getMobilePath from '../cores/get-mobile-path';

class ShippingFee extends Component {
    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    searchLink = () => {
        let vendorNm = this.state.VENDOR_NM;

        if(vendorNm.indexOf('주) ')){
            vendorNm = vendorNm.replace('주) ', '주)');
        }

        let href = getMobilePath({
            url : `/mobile/search/Search.do?searchTerm=${encodeURIComponent(vendorNm)}&viewType=G`
        });

        return href;
    }

    onClick = (event) => {
        event.preventDefault();


    };

    render () {
        if(this.state.deliPriceTxt || this.state.isFreeDeli) {
            return (
                <tr  className="prod-benefit-row shipping-fee">
                    <th scope="row">
                        {TITLE_SHIPPING_FEE}
                    </th>
                    <td>
                        {this.state.isFreeDeli ?
                            (
                                <em className="prod-icon type1">{TEXT_FREE_DELIVERY}</em>
                            )
                            :
                            (
                                <span dangerouslySetInnerHTML={{__html:this.state.deliPriceTxt}}></span>
                            )
                        }
                        {this.state.saveDeliPriceDpYn === 'Y' &&
                        <a href={this.searchLink()}>
                            {BTN_SAVE_DELI_PRICE_YN}
                        </a>
                        }
                        {this.state.ADDR_KIND_NM && this.state.ADDR_KIND_NM !== '' &&
                        <p className="desc">
                            {DELIVERY_ADDR_KIND_NM(this.state.ADDR_KIND_NM)}
                        </p>
                        }
                    </td>
                </tr>
            )
        } else {
            return null;
        }
    }
}

export default ShippingFee;