import React, {Component} from 'react';
import getDeliveryTimeInfo from '../cores/get-delivery-time-info';
import {
    TEXT_START,
    DELIVERY_TIME_INFO_TODAY,
    DELIVERY_TIME_INFO_DAY,
    DELIVERY_TIME_INFO_TOMORROW,
    DELIVERY_TIME_INFO_DAY_AFTER_TOMORROW
} from "../i18n/i18n-ko";

class DeliverySchedule extends Component {
    static defaultProps = {
        isShow: false,
        deliTime: null
    };

    constructor(props) {
        super(props);

        this.state = Object.assign({}, props);
    }

    componentDidMount () {
        this.getTime(this.state);
    }

    getTime (
        {
            deliSpFg_SD = 'N',
            deliSpFg_SP = 'N',
            deliSpFg_CP = 'N'
        }) {

        if(deliSpFg_SD === 'Y'
            || deliSpFg_SP === 'Y'
            || deliSpFg_CP === 'Y') {
            if(this.state.holiMallUserFg === '0') {
                let promise = getDeliveryTimeInfo(
                    deliSpFg_SD,
                    deliSpFg_SP,
                    deliSpFg_CP
                );

                promise.then((v) => {
                    if (v.status === 'success') {
                        this.setState({
                            isShow: true,
                            deliTime: [v]
                        });
                    }
                }).catch(() => {
                    this.setState({
                        isShow: false,
                        deliTime: null
                    });
                });
            }
        }
    }

    renderDeliveryTime = (data) => {
        let convertNowDt = data.nowDt
            .split(/-|\s+|\:/)
            .map((v) => {
                return Number(v);
            });

        let convertAfter2hDt = data.after2hDt
            .split(/-|\s+|\:/)
            .map((v) => {
                return Number(v);
            });

        let currentDate = new Date(
            convertNowDt[0],
            convertNowDt[1]-1,
            convertNowDt[2]
        );
        let currentTime = new Date(
            convertNowDt[0],
            convertNowDt[1]-1,
            convertNowDt[2],
            convertNowDt[3],
            convertNowDt[4]
        );
        let afterTwohour = new Date(
            convertAfter2hDt[0],
            convertAfter2hDt[1]-1,
            convertAfter2hDt[2],
            convertAfter2hDt[3],
            convertAfter2hDt[4]
        );
        let tomorrowTime = data.after1dDt;
        let dayAfterTomorrowTime = data.after2dDt;
        let delivDate = data.deliveryTimeInfo.DELIV_DATE;
        let strHtml = '';
        let deliveryText = this.state.deliSpFg_CP === 'Y' ? '픽업' : '배송';

        if(tomorrowTime) {
            tomorrowTime = new Date(
                Number(tomorrowTime.substring(0, 4)),
                Number(tomorrowTime.substring(4, 6)) - 1,
                Number(tomorrowTime.substring(6))
            );
        }

        if(delivDate) {
            delivDate = new Date(
                Number(delivDate.substring(0, 4)),
                Number(delivDate.substring(4, 6)) - 1,
                Number(delivDate.substring(6))
            );
        }

        if(dayAfterTomorrowTime) {
            dayAfterTomorrowTime = new Date(
                Number(dayAfterTomorrowTime.substring(0, 4)),
                Number(dayAfterTomorrowTime.substring(4, 6)) - 1,
                Number(dayAfterTomorrowTime.substring(6))
            );
        }

        if(delivDate.getTime() === currentDate.getTime()) {
            let cTime = currentTime.getHours() + currentTime.getMinutes();
            let aTime = afterTwohour.getMinutes() + afterTwohour.getMinutes();

            let closeTime = data.deliveryTimeInfo.ORD_CLOSE_TM
                .split(':')
                .reduce((accumulator, v) => {
                    return accumulator + Number(v);
                });

            let startTime = data.deliveryTimeInfo.DELI_PRAR_START_TM
                .split(':')
                .reduce((accumulator, v) => {
                    return accumulator + Number(v);
                });

            if(cTime < closeTime
                && startTime < aTime) {
                strHtml = DELIVERY_TIME_INFO_TODAY(this.state.deliSpFg_CP !== 'Y' ? deliveryText + TEXT_START : deliveryText);
            } else {
                strHtml = DELIVERY_TIME_INFO_DAY(data.deliveryTimeInfo.DELI_PRAR_START_TM, deliveryText);
            }

        } else if (delivDate.getTime() === tomorrowTime.getTime()) {
            strHtml = DELIVERY_TIME_INFO_TOMORROW(data.deliveryTimeInfo.DELI_PRAR_START_TM, deliveryText);
        } else if (delivDate.getTime() === dayAfterTomorrowTime.getTime()) {
            strHtml = DELIVERY_TIME_INFO_DAY_AFTER_TOMORROW(deliveryText);
        } else {
            this.setState({
                isShow: false
            });
        }

        return (
            <th scope="col"
                colSpan="2"
                className="acenter" dangerouslySetInnerHTML={{__html:strHtml}}>
            </th>
        )
    };

    render () {
        return (
            <tr className="prod-benefit-row">
                {(this.state.isShow && this.state.deliTime !== null) &&
                    this.state.deliTime.map((v) =>
                        this.renderDeliveryTime(v)
                    )
                }
            </tr>
        )
    }
}

export default DeliverySchedule;