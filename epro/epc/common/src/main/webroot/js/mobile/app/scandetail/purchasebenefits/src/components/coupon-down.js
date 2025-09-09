import Pubsub from 'pubsub-js';
import React, {Component} from 'react';
import {
    TITLE_COUPON,
    TITLE_COUPON_LIST,
    BTN_COUPON_DOWN,
    BTN_CLOSE,
    BTN_ALL_COUPON_DOWN,
    UNIT_WON,
    UNIT_PERCENT,
    MESSAGE_DISABLE_DOWN_COUPON
} from "../i18n/i18n-ko";
import setComma from '../cores/set-comma';
import downCoupon from '../cores/down-coupon';

class CouponDown extends Component {
    static defaultProps = {
        couponList: [],
        isShow: true
    };

    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    componentWillReceiveProps (nextPros) {
        if(this.state.active !== nextPros.active) {
            this.setState({
                active: nextPros.active
            });
        }
    }

    componentWillMount () {
        this.setState({
            isShow: this.state.couponList.filter((v) => {
                return v.MY_ISSUED_CNT === 0;
            }).length > 0
        });
    }

    onClose = (event) => {
        event.preventDefault();

        Pubsub.publish('purcahseBenefitsLayerActive', {
            type : 'couponDownLayerActive',
            value : false
        });
    };

    onOpen = (event) => {
        event.preventDefault();

        Pubsub.publish('purcahseBenefitsLayerActive', {
            type : 'couponDownLayerActive',
            value : true
        });
    };

    onDownCoupon = (event, data) => {
        event.preventDefault();

        let promise = downCoupon([data.REP_COUPON_ID], [data.DC_CPBOOK_CD]);

        promise.then((data) => {
            if(data.rtn === 'success') {
                this.successCallback(data);
            }

        });
    };

    onAllDownCoupon = (event) => {
        event.preventDefault();

        let repCouponIds = [];
        let dcCpBookCds = [];

        for(let item of this.state.couponList) {
            repCouponIds.push(item.REP_COUPON_ID);
            dcCpBookCds.push(item.DC_CPBOOK_CD);
        }

        let promise = downCoupon(repCouponIds, dcCpBookCds);

        promise.then((data) => {
            if(data.rtn === 'success') {
                this.successCallback(data);
            }
        });
    };

    successCallback (response) {
        let couponList = [];

        for(let item of this.state.couponList) {
            if(response.rtnData.indexOf(item.REP_COUPON_ID) !== -1) {
                item.MY_ISSUED_CNT = 1;
            }

            couponList.push(item);
        }

        this.setState({
            couponList,
            isShow : couponList.filter((v) => {
                return v.MY_ISSUED_CNT === 0
            }).length > 0
        });
    }

    renderCouponList = () => {
        return this.state.couponList.map((v) => {
                if (v.MY_ISSUED_CNT === 0) {
                    let color = 'color1';
                    let currency = '';

                    if(v.DC_CPBOOK_CD === '09'
                        || v.DC_CPBOOK_CD === '10') {
                        color = 'color3';
                    } else if(v.COUPON_TYPE_CD === '03') {
                        color = 'color2';
                    }

                    if(v.COUPON_TYPE_CD !== '03') {
                        currency = v.DC_UNIT_CD === '01' ? UNIT_WON : (v.DC_UNIT_CD === '02' ? UNIT_PERCENT : '');
                    }

                    let couponClass = `t-coupon ${color}`;
                    return (
                    <div className={couponClass}>
                        <div className="conts">
                            <p className="name">
                                {v.COUPON_NM}
                            </p>
                            <p className="benefit">
                                {v.COUPON_TYPE_CD === '03' ? v.VISIBLE_TTL : setComma(v.COUPON_DC_AMT)}
                                {currency}
                            </p>
                            <p className="desc">
                                {v.VISIBLE_HEADER}
                            </p>
                            <p className="desc">
                                {v.USE_START_DY} ~ {v.USE_END_DY}
                            </p>
                        </div>
                        <div className="action">
                            <a href="#"
                               className="download"
                               onClick={(event) => this.onDownCoupon(event, v)}>
                                {BTN_COUPON_DOWN}
                            </a>
                        </div>
                    </div>
                    );
                } else {
                    return null;
                }
            }
        );
    };

    render () {
        let layerClassName = 'layerpopup-type1 layerpopup-type1-ver2 layerpopup-type1_coupon';

        if(this.state.isShow) {
            return (
                <tr  className="prod-benefit-row">
                    <th>{TITLE_COUPON}</th>
                    <td>
                        <button type="button"
                                className="btn-coupon-down"
                                onClick={this.onOpen}>
                            {BTN_COUPON_DOWN}
                        </button>
                        {this.state.active &&
                        <article className={this.state.active ? 'active ' + layerClassName : layerClassName}>
                            <header className="header">
                                <h3 className="title">{TITLE_COUPON_LIST}</h3>
                                <button type="button"
                                        onClick={this.onClose}
                                        className="icon-close">
                                    {BTN_CLOSE}
                                </button>
                            </header>
                            <div className="wrap-coupon-list">
                                {this.renderCouponList()}
                                {!this.state.isShow &&
                                <p className="list-empty">
                                    {MESSAGE_DISABLE_DOWN_COUPON}
                                </p>
                                }
                            </div>
                            {this.state.isShow &&
                            <div className="layerpopup_coupon_btn">
                                <button type="button"
                                        onClick={this.onAllDownCoupon}>
                                    {BTN_ALL_COUPON_DOWN}
                                </button>
                            </div>
                            }
                        </article>
                        }
                    </td>
                </tr>
            );
        } else {
            return null;
        }
    }
}

export default CouponDown;