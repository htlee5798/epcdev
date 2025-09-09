import React, {Component} from 'react';
import {
    TITLE_DELIVERY_TYPE
} from "../i18n/i18n-ko";

class DeliveryType extends Component {
    static defaultProps = {
        deliveryType: []
    };

    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    componentWillMount () {
        this.setState({
            deliveryType: this.state.DELI_NM ? this.state.DELI_NM.replace(/\|$/, '').split('|') : []
        });
    }


    renderDeliveryIcon = () => {
        return this.state.deliveryType.map((v) =>
            <em className="prod-icon type1">{v}</em>
        );
    }

    render () {
        return (
            <tr className="prod-benefit-row">
                <th id="deliTypeTit">{TITLE_DELIVERY_TYPE}</th>
                <td id="deliType">
                    {this.renderDeliveryIcon()}
                    {this.state.parcelTxt ? this.state.parcelTxt : ''}
                </td>
            </tr>
        );
    }
}

export default DeliveryType;