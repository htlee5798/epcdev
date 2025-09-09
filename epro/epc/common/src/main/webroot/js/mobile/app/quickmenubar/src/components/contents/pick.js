import $ from 'jquery';
import React, { Component } from 'react';
import BtnMinus from '../buttons/btn-minus';
import BtnPlus from '../buttons/btn-plus';
import Pubsub from 'pubsub-js';
import getOptions from '../../cores/get-options';
import {
    TITLE_PCIK,
    TEXT_QUANTITY,
    ERROR_MESSAGE_ONLY_NUMBER,
    ERROR_MESSAGE_PCIK_QUANTITY,
    ERROR_MESSAGE_EMPTY_TOTALQUANTITY,
    ERROR_MESSAGE_MORE_CHOOSE_PRODUCTION,
    RESULT_MESSAGE_PICK,
    DESCRIPTION_PICK,
    DESCRIPTION_PICK_SELECTED, ERROR_MESSAGE_ONLY_SMARTPICKUP
} from "../../i18n/i18n-ko";
import setComma from '../../cores/set-comma';
import successAddBasket from '../../cores/success-add-basket';

const IME_MODE = {
    imeMode : 'disabled'
};

class Pick extends Component {
    constructor(props) {
        super(props);
        this.state = {
            unit: 0,
            items: [],
            totalQuantity : 0,
            submitType : '',
            prodCd : props.prodCd,
            categoryId : props.categoryId,
            itemCd : props.itemCd ? props.itemCd : '001',
            isPeriodDelivery : props.isPeriodDelivery,
            periodDeliveryYn : props.isPeriodDelivery ? 'Y' : 'N',
            pType : props.pType
        };
    }

    componentWillReceiveProps (nextProps) {
        if(this.state.isPeriodDelivery !== nextProps.isPeriodDelivery) {
            this.setState({
                isPeriodDelivery : nextProps.isPeriodDelivery,
                periodDeliveryYn: nextProps.isPeriodDelivery ? 'Y' : 'N'
            });
        }
    }

    componentWillMount () {
        const promise = getOptions(this.state);

        promise.then((data) => {
            if(data.length > 0) {
                let items = data.map((v) => {
                    v.quantity = 0;
                    return v;
                });
                this.setState({
                    unit: data[0].unit,
                    items: items
                });
            }
        });
    }

    componentDidMount () {
        Pubsub.subscribe('plus', (message, data) => {
            data.quantity += 1;
            this.updateStatus(data);
        });

        Pubsub.subscribe('minus',(message, data) => {
            data.quantity -= 1;
            this.updateStatus(data);
        });

        Pubsub.subscribe('onSubmitBuy', () => {
            this.setState({
                submitType: 'purchase'
            });
            if(!this.validation()) {
                return;
            }

            window.global.addDirectBasket(this.getParams());
        });

        Pubsub.subscribe('onSubmitBasket', () => {
            this.setState({
                submitType: 'basket'
            });
            let _this = this;
            if(!this.validation()) {
                return;
            }

            window.global.addBasket(this.getParams(), function () {
                successAddBasket(_this.state.totalQuantity);
            });
        });
    }

    updateStatus = (data) => {
        let items = [];
        let perPrice = 0;

        for (let i = 0, len = this.state.items.length; i < len; i++) {
            if (this.state.items[i].value === data.value) {
                if(data.quantity > this.state.items[i].maxQuantity) {
                    this.errorMessage({
                        type: 'quantity',
                        minQuantity: this.state.items[i].minQuantity,
                        maxQuantity: this.state.items[i].maxQuantity
                    });

                    data.quantity = this.state.items[i].maxQuantity;
                } else if(data.quantity < 0) {
                    this.errorMessage({
                        type: 'quantity',
                        minQuantity: this.state.items[i].minQuantity,
                        maxQuantity: this.state.items[i].maxQuantity
                    });

                    data.quantity = 0;
                }

                perPrice = this.state.items[i].currSellPrc;

                items.push($.extend({}, this.state.items[i], {
                    quantity: data.quantity
                }));

            } else {
                items.push(this.state.items[i]);
            }
        }

        let promise = new Promise((resolve) => {
            const totalQuantity = items.map((v) => v.quantity)
                .reduce((accumulator, currentValue) => accumulator + currentValue);

            this.setState({
                items,
                totalQuantity
            });
            resolve();
        });

        promise.then(() => {
            Pubsub.publish('setTotalPrice', perPrice * this.state.totalQuantity);
        });
    };

    onChange = (event, value) => {
        let quantity = Number(event.target.value);
        if(isNaN(quantity)) {
            this.errorMessage({type : 'notNumber'});
            return;
        }
        this.updateStatus({
            value,
            quantity
        });
    };

    getParams = () => {
        let formData = $('#formOptionForPick')
            .serializeArray()
            .filter((v) => {
                return Number(v.value) !== 0;
            }).map((v) => {
                return `${v.name}:${v.value}`;
            }).join(';');

        return {
            prodCd: this.state.prodCd,
            itemCd: this.state.itemCd,
            bsketQty: this.state.totalQuantity,
            categoryId: this.state.categoryId,
            nfomlVariation: formData,
            periDeliYn: this.state.periodDeliveryYn,
            isScanDetail : true
        };
    };

    //장바구니 수량 조회
    getBasketCount() {
        $.getJSON("/basket/api/count.do")
            .done(function(data) {
                if (data) {
                    if( data.count > 0 ) {
                        document.querySelector('#basketCountData').dataset.qty = data.count;
                    }
                }
            });
    }

    validation = () => {
        let returnValue = true;
        if (this.state.totalQuantity === 0) {
            alert(ERROR_MESSAGE_EMPTY_TOTALQUANTITY);
            returnValue = false;
        } else if (this.state.totalQuantity % this.state.unit !== 0) {
            alert(ERROR_MESSAGE_MORE_CHOOSE_PRODUCTION(setComma(this.state.totalQuantity - this.state.unit)));
            returnValue = false;
        } else if (this.state.submitType === 'purchase' && this.state.pType !== 'Y') {
            if(this.state.productInfo.PickUpType !== '') {
                alert(ERROR_MESSAGE_ONLY_SMARTPICKUP());
                returnValue = false;
            }
        }

        return returnValue;
    };

    errorMessage = ({
                        type,
                        minQuantity,
                        maxQuantity
                    }) => {
        switch (type) {
            case 'quantity' :
                alert(ERROR_MESSAGE_PCIK_QUANTITY(minQuantity, maxQuantity))
                break;
            case 'notNumber' :
                alert(ERROR_MESSAGE_ONLY_NUMBER);
                break;
        }
    };

    renderOptions = () => {
        return this.state.items.map((v) =>
            <div className="goodsadded lie-table"
                 key={v.value}>
                <div className="left">{v.name}</div>
                <div className="spinner right">
                    <BtnMinus {...v}/>
                    <input type="tel"
                           className="optnCnt"
                           name={v.name}
                           maxLength={v.maxQuantity.toString().length}
                           title={TEXT_QUANTITY}
                           placeholder="0"
                           value={v.quantity}
                           style={IME_MODE}
                           onChange={(event) => this.onChange(event, v.value)}/>
                    <BtnPlus {...v}/>
                </div>
            </div>
        );
    };

    render () {
        return (
            <div className="inner-scroll">
                <div className="option-select">
                    <p className="title">{TITLE_PCIK}</p>
                    <p className="desc">
                        {DESCRIPTION_PICK(setComma(this.state.unit))}
                        {this.state.unit - this.state.totalQuantity > 0 &&
                            <span id="leftQty" dangerouslySetInnerHTML={{__html: DESCRIPTION_PICK_SELECTED(setComma(this.state.unit - this.state.totalQuantity))}}>
                            </span>
                        }
                    </p>
                    <div className="option-select" name="virtual-basket">
                        <form id="formOptionForPick">
                            {this.renderOptions()}
                        </form>
                        <p className="goodsadded result" dangerouslySetInnerHTML={{__html:RESULT_MESSAGE_PICK()}}>
                        </p>
                    </div>
                </div>
            </div>
        );
    }
}

export default Pick;