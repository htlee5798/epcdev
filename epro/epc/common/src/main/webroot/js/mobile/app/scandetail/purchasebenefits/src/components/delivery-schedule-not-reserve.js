import $ from 'jquery';
import React, {Component} from 'react';
import {
    DELIVERY_TIME_INFO_NOT_RESERVE
} from "../i18n/i18n-ko";
import Pubsub from "pubsub-js";

class DeliveryScheduleNotReserve extends Component {
    static defaultProps = {
        enabled: false
    };

    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    componentWillReceiveProps (nextProps) {
        if(this.state.active !== nextProps.active) {
            this.setState({
                active: nextProps.active
            });
        }
    }

    componentDidMount () {
        this.setState({
            enabled: this.isEnabled(this.state)
        });

        $('#purchasebenefits').on('click', '#layerDeliveryScheduleNotReserve .js-close', function (e) {
            e.preventDefault();

            Pubsub.publish('purcahseBenefitsLayerActive', {
                type: 'deliveryScheduleNotReserveLayerActive',
                value: false
            });
        });
    }

    isEnabled (
        {
            deliSpFg_SD = 'N',
            deliSpFg_SP = 'N',
            deliSpFg_CP = 'N'
        }
    ) {
        return deliSpFg_SD === 'N' && deliSpFg_CP === 'N' && deliSpFg_SP === 'N';
    }

    onToggle = (event) => {
        event.preventDefault();

        Pubsub.publish('purcahseBenefitsLayerActive', {
            type: 'deliveryScheduleNotReserveLayerActive',
            value: !this.state.active
        });
    };

    render () {
        if(this.state.enabled && !!this.state.AVG_DELI_DAY) {
            let html = this.state.isBatchDeliTime
                ? this.state.CT_MOBILE_PROD_DETAIL016 : this.state.CT_MOBILE_PROD_DETAIL015;
            return (
                <tr className="prod-benefit-row">
                    <th scope="col"
                        colspan="2"
                        className="acenter">
                        <div className="wrap-prod-layerpopup">
                            <p className="delivery-schedule-desc"
                                dangerouslySetInnerHTML={{__html:DELIVERY_TIME_INFO_NOT_RESERVE(this.state.AVG_DELI_DAY)}}></p>
                            <button type="button"
                               onClick={this.onToggle}
                               className={this.state.active ? "icon-common-question active" : "icon-common-question"}>
                                ?
                            </button>
                            <article id="layerDeliveryScheduleNotReserve"
                                     className={this.state.active ? "layerpopup-type1 active" : "layerpopup-type1"}
                                     dangerouslySetInnerHTML={{__html:decodeURIComponent(html)}}>
                            </article>
                        </div>
                    </th>
                </tr>
            )
        } else {
            return null;
        }
    }
}

export default DeliveryScheduleNotReserve;