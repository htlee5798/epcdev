import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {
    TITLE_PURCHASE_BENEFITS,
    TABLE_SUMMARY_PURCHASE_BENEFITS,
    TITLE_SELLER_RECOMMEND
} from "./i18n/i18n-ko";
import DeliverySchedule from "./components/delivery-schedule";
import CouponDown from "./components/coupon-down";
import ShippingFee from "./components/shipping-fee";
import DeliveryType from "./components/delivery-type";
import BenefitCard from "./components/benefit-card";
import Lpoint from "./components/lpoint";
import BenefitOrd from "./components/benefit_ord";
import DeliveryScheduleNotReserve from "./components/delivery-schedule-not-reserve";

class App extends Component {
    static defaultProps = {
        layers: {
            couponDownLayerActive: false,
            benefitLayerActive: false,
            benefitOrdLayerActive: false,
            benefitCardLayerActive: false,
            lpointLayerActive: false,
            deliveryScheduleNotReserveLayerActive: false
        }
    };

    constructor (props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    componentDidMount () {
        Pubsub.subscribe('purcahseBenefitsLayerActive', (message, {
            type,
            value
        }) => {
            let newObject = {};

            for(let layer in this.state.layers) {
                newObject[layer] = layer === type ? value : false;
            }

            this.setState({
                layers : newObject
            });
        });
    }

    render() {
        const {
            couponList,
            CT_MOBILE_PROD_DETAIL003,
            CT_MOBILE_PROD_DETAIL016,
            CT_MOBILE_PROD_DETAIL015
        } = this.state;

        const {
            HOLI_MALL_USE_FG,
            VENDOR_NM,
            CCARD_NEW
        } = this.state.ProductInfo;

        const {
            deliSpFg_SD,
            deliSpFg_SP,
            deliSpFg_CP,
            isBatchDeliTime,
            AVG_DELI_DAY,
            deliPriceTxt,
            isFreeDeli,
            saveDeliPriceDpYn,
            ADDR_KIND_NM,
            DELI_NM,
            parcelTxt
        } = this.state.DeliveryInfo;

        const {
            deliveryScheduleNotReserveLayerActive,
            couponDownLayerActive,
            benefitOrdLayerActive,
            benefitCardLayerActive,
            lpointLayerActive
        } = this.state.layers;

        return (
            <div>
                <h2 className="blind">
                    {TITLE_PURCHASE_BENEFITS}
                </h2>
                <details className="wrap-toggle-benefit">
                    <summary className="title">
                        {TITLE_PURCHASE_BENEFITS}
                    </summary>
                    <div className="prod-benefit-table">
                        <table summary={TABLE_SUMMARY_PURCHASE_BENEFITS}>
                            <caption>{TITLE_PURCHASE_BENEFITS}</caption>
                            <tbody>
                            <DeliverySchedule deliSpFg_SD={deliSpFg_SD}
                                              deliSpFg_SP={deliSpFg_SP}
                                              deliSpFg_CP={deliSpFg_CP}
                                              isBatchDeliTime={isBatchDeliTime}
                                              holiMallUserFg={HOLI_MALL_USE_FG}/>
                            <DeliveryScheduleNotReserve active={deliveryScheduleNotReserveLayerActive}
                                                        deliSpFg_SD={deliSpFg_SD}
                                                        deliSpFg_SP={deliSpFg_SP}
                                                        deliSpFg_CP={deliSpFg_CP}
                                                        AVG_DELI_DAY={AVG_DELI_DAY}
                                                        CT_MOBILE_PROD_DETAIL016={CT_MOBILE_PROD_DETAIL016}
                                                        CT_MOBILE_PROD_DETAIL015={CT_MOBILE_PROD_DETAIL015}/>
                            <CouponDown active={couponDownLayerActive}
                                        couponList={couponList}/>
                            <BenefitOrd active={benefitOrdLayerActive}/>
                            <ShippingFee deliPriceTxt={deliPriceTxt}
                                         isFreeDeli={isFreeDeli}
                                         saveDeliPriceDpYn={saveDeliPriceDpYn}
                                         ADDR_KIND_NM={ADDR_KIND_NM}
                                         VENDOR_NM={VENDOR_NM}/>
                            <DeliveryType DELI_NM={DELI_NM}
                                          parcelTxt={parcelTxt}/>
                            <BenefitCard active={benefitCardLayerActive}
                                         CCARD_NEW={CCARD_NEW}/>
                            <Lpoint active={lpointLayerActive}
                                    html={CT_MOBILE_PROD_DETAIL003}/>
                            </tbody>
                        </table>
                    </div>
                </details>
            </div>
        );
    }
}

export default App;
