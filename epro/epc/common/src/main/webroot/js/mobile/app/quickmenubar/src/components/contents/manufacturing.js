import $ from 'jquery';
import React, {Component} from 'react';
import getOptions from '../../cores/get-options';
import Pubsub from 'pubsub-js';
import {
    TITLE_HOPE_SHIPPING_DATE,
    TITLE_PURCHASE_REQUESTS,
    TITLE_OPTION,
    TITLE_OPTION_SELECT,
    TEXT_SOLD_OUT,
    BTN_SELECT_DEFAULT,
    BTN_REMOVE,
    DESCRIPTION_HOPE_SHIPPING_DATE_CAUTION,
    DESCRIPTION_HOPE_SHIPPING_DATE_EXPLANATION,
    PURCHASE_REQUESTS_TEXT,
    CURRENCY_UNIT_WON, ERROR_MESSAGE_ONLY_NUMBER,
    ERROR_MESSAGE_PCIK_QUANTITY,
    ERROR_MESSAGE_EMPTY_TOTALQUANTITY,
    ERROR_MESSAGE_SELECTED_HOPEDATE,
    ERROR_MESSAGE_ONLY_SMARTPICKUP
} from "../../i18n/i18n-ko";
import BtnMinus from "../buttons/btn-minus";
import BtnPlus from "../buttons/btn-plus";
import setComma from '../../cores/set-comma';
import successAddBasket from '../../cores/success-add-basket';

const IME_MODE = {
    imeMode : 'disabled'
};

class ManuFacturing extends Component {
    constructor(props) {
        super(props);

        this.state = {
            productInfo : props.productInfo,
            prodCd : props.prodCd,
            categoryId : props.categoryId,
            periodDeliveryYn : props.isPeriodDelivery ? 'Y' : 'N',
            prodOptionDesc: props.prodOptionDesc || PURCHASE_REQUESTS_TEXT,
            hopeDeliPsbtdd: props.hopeDeliPsbtdd ? props.hopeDeliPsbtdd : 1,
            hopeDates : [],
            items : [],
            selectedHopeDate : '',
            selectedOption : '',
            totalQuantity : 0,
            isHidden : false,
            submitType : '',
            pType : props.pType
        };
    }

    componentWillReceiveProps (nextProps) {
        if(this.state.isPeriodDelivery !== nextProps.isPeriodDelivery) {
            this.setState({
                periodDeliveryYn: nextProps.isPeriodDelivery ? 'Y' : 'N'
            });
        }
    }

    componentWillMount () {
        let hopeDates = [];

        for(let i = 0, len = 90; i < len; i++) {
            let date = new Date();

            date.setDate(date.getDate() + i + this.state.hopeDeliPsbtdd);

            hopeDates.push({
                value : date.format('yyyyMMdd'),
                name : date.format('yyyy년MM월dd일')
            });
        }

        this.setState({
            hopeDates
        });

        const promise = getOptions(this.state);

        promise.then((data) => {
            if(data.length > 0) {
                this.setState({
                    items: data
                });
            } else {
                this.setState({
                    items: [{
                        currSellPrc : this.state.productInfo.CURR_SELL_PRC,
                        isSoldOut : this.state.productInfo.ABSENCE_YN === 'Y',
                        itemCode : '001',
                        maxQuantity : this.state.productInfo.MAX_ORD_PSBT_QTY,
                        minQuantity : this.state.productInfo.MIN_ORD_PSBT_QTY,
                        name : this.state.productInfo.PROD_NM,
                        originalName : '',
                        productCode : this.state.productInfo.PROD_CD,
                        stockQuantity : 0,
                        value : '001',
                        variationYn : 'Y'
                    }],
                    isHidden : true,
                    selectedOption : '001',
                    totalQuantity : this.state.productInfo.MIN_ORD_PSBT_QTY
                });

                Pubsub.publish('setTotalPrice', this.state.totalQuantity * this.state.items[0].currSellPrc);
            }
        });
    }

    componentDidMount () {
        Pubsub.subscribe('minus', (message, data) => {
            data.quantity = this.state.totalQuantity - 1;
            this.updateTotalQuantity(data);
        });

        Pubsub.subscribe('plus', (message, data) => {
            data.quantity = this.state.totalQuantity + 1;
            this.updateTotalQuantity(data);
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

            window.global.addBasket(this.getParams(), function() {
                successAddBasket(_this.state.totalQuantity);
            });
        });
    }

    getParams = () => {
        let formData = $('#formOptionForManufacturing')
            .serializeArray();
        let item = this.state.items.filter((v) => {
            return v.value === this.state.selectedOption;
        }).map((v) => {
            formData.map((n) => {
                v[n.name] = n.value;
            });

            return v;
        })[0];

        return {
            prodCd: item.productCode,
            itemCd: item.itemCode,
            bsketQty: item.bsketQty,
            categoryId: this.state.categoryId,
            nfomlVariation: item.variationYn === 'Y' ? '' : item.itemCode + ':' + item.bsketQty,
            periDeliYn: this.state.periodDeliveryYn,
            ordReqMsg : item.ordReqMsg,
            hopeDeliDy : item.hopeDeliDy,
            isScanDetail : true
        };
    };

    isHidden = () => {
        return {
            display: this.state.isHidden ? 'none' : 'block'
        };
    };

    updateTotalQuantity(
        {
            quantity,
            currSellPrc,
            minQuantity,
            maxQuantity
        }
    ) {
        if(isNaN(quantity)) {
            this.errorMessage({type : 'notNumber'});
            return;
        } else if(quantity > maxQuantity) {
            this.errorMessage({
                type: 'quantity',
                minQuantity: minQuantity,
                maxQuantity: maxQuantity
            });

            quantity = maxQuantity;

        } else if(quantity < minQuantity) {
            this.errorMessage({
                type: 'quantity',
                minQuantity: minQuantity,
                maxQuantity: maxQuantity
            });

            quantity = minQuantity;
        }

        let promise = new Promise((resolve) => {
            this.setState({
                totalQuantity: quantity
            });

            resolve();
        });

        promise.then(() => {
            Pubsub.publish('setTotalPrice', this.state.totalQuantity * currSellPrc);
        });
    }

    onChangeHopeDate = (event) => {
        this.setState({
            selectedHopeDate : $(event.target).find('option:selected').val()
        });
    };

    onChangeProductOption = (event) => {
        let $option = $(event.target).find('option:selected');
        let item = this.state.items.filter((v) => {
            return v.value === $option.val();
        });

        if(item.length === 0) {
            this.onResetSelected();
            return;
        }

        let promise = new Promise((resolve) => {
            this.setState({
                selectedOption: item[0].value,
                totalQuantity: item[0].minQuantity
            });

            resolve();
        });

        promise.then(() => {
            Pubsub.publish('setTotalPrice', this.state.totalQuantity * item[0].currSellPrc);
        });
    };

    onResetSelected = (event) => {
        if(event) {
            event.preventDefault();
        }

        this.setState({
            selectedOption : '',
            totalQuantity : 0
        });

        Pubsub.publish('setTotalPrice', 0);
    };

    onChangeQuantity = (event, item) => {
        item.quantity = Number(event.target.value);
        this.updateTotalQuantity(item);
    };

    validation = () => {
        let returnValue = true;
        if (this.state.totalQuantity === 0) {
            alert(ERROR_MESSAGE_EMPTY_TOTALQUANTITY);
            returnValue = false;
        } else if (this.state.selectedHopeDate === '') {
            alert(ERROR_MESSAGE_SELECTED_HOPEDATE);
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

    renderSelectOptions = () => {
        return this.state.hopeDates.map((v) =>
            <option value={v.value}
                    selected={this.state.selectedHopeDate===v.value}>
                {v.name}
            </option>
        );
    };

    renderProductOptions = () => {
        return this.state.items.map((v) =>
            <option value={v.value}
                    selected={this.state.selectedOption===v.value}
                    disabled={v.isSoldOut}>
                {v.name}{v.isSoldOut?' - ' + TEXT_SOLD_OUT:''}
            </option>
        );
    };

    renderQuantity = () => {
        return this.state.items.filter((v) => {
            return v.value === this.state.selectedOption;
        }).map((v) =>
            <div className="option-select">
                <div className="goodsadded">
                    <p style={this.isHidden()}>
                        {TITLE_OPTION_SELECT(v)}
                    </p>
                    <div className="wrap-spinner">
                        <div className="spinner">
                            <BtnMinus {...v}/>
                            <input type="tel"
                                   className="optnCnt"
                                   name="bsketQty"
                                   value={this.state.totalQuantity}
                                   style={IME_MODE}
                                   maxLength={v.maxQuantity.length}
                                   onChange={(event) => this.onChangeQuantity(event, v)}/>
                            <BtnPlus {...v}/>
                        </div>
                        <p className="point1">
                            <em className="number">
                                {setComma(v.currSellPrc * this.state.totalQuantity)}
                            </em>
                            {CURRENCY_UNIT_WON}
                        </p>
                    </div>
                    <button type="button"
                            onClick={this.onResetSelected}
                            style={this.isHidden()}
                            className="icon-common-delete">
                        {BTN_REMOVE}
                    </button>
                </div>
            </div>
        )
    };

    render () {
        return (
            <div className="inner-scroll">
                <form id="formOptionForManufacturing">
                    <div className="option-select">
                        <p className="title">
                            {TITLE_HOPE_SHIPPING_DATE}
                        </p>
                        <div className="select">
                            <select name="hopeDeliDy"
                                    onChange={this.onChangeHopeDate}>
                                <option value=""
                                        selected={this.state.selectedHopeDate===''?true:false}>
                                    {BTN_SELECT_DEFAULT}
                                </option>
                                {this.renderSelectOptions()}
                            </select>
                        </div>
                    </div>
                    <ul className="textlist">
                        <li>
                            {DESCRIPTION_HOPE_SHIPPING_DATE_CAUTION}
                        </li>
                        <li>
                            {DESCRIPTION_HOPE_SHIPPING_DATE_EXPLANATION}
                        </li>
                    </ul>
                    <div className="option-select">
                        <p className="title">
                            {TITLE_PURCHASE_REQUESTS}
                        </p>
                        <div className="inptext">
                            <input type="text"
                                   name="ordReqMsg"
                                   maxLength="2000"
                                   placeholder={this.state.prodOptionDesc}/>
                        </div>
                    </div>
                    {this.state.items.length > 0 &&
                    <div className="option-select"
                        style={this.isHidden()}>
                        <p class="title">
                            <i className="icon-common-optcheck"></i>
                            {TITLE_OPTION}
                        </p>
                        <div className="select required">
                            <select required={true}
                                    onChange={this.onChangeProductOption}>
                                <option value=""
                                        selected={this.state.selectedOption === '' ? true : false}>
                                    {BTN_SELECT_DEFAULT}
                                </option>
                                {this.renderProductOptions()}
                            </select>
                        </div>
                    </div>
                    }
                    {this.state.selectedOption !== '' &&
                        this.renderQuantity()
                    }
                </form>
            </div>
        );
    }
}

export default ManuFacturing;